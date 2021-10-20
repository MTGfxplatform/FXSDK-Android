package com.fxmvp.detailroi.report;

import static com.fxmvp.detailroi.common.ids.BDIdsManager.MAX_FAILED_NUM;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.mbridge.alpha.thrid.okhttp.MediaType;
import com.mbridge.alpha.thrid.okhttp.Request;
import com.mbridge.alpha.thrid.okhttp.RequestBody;
import com.fxmvp.detailroi.common.GlobalObject;
import com.fxmvp.detailroi.common.base.utils.SameLogTool;

import com.fxmvp.detailroi.common.bean.EventBean;
import com.fxmvp.detailroi.common.db.CommonSDKDBHelper;
import com.fxmvp.detailroi.common.db.EventDao;
import com.fxmvp.detailroi.common.ids.BDIdsManager;
import com.fxmvp.detailroi.common.network.NetworkHelper;
import com.fxmvp.detailroi.common.network.callback.BaseCallBack;
import com.fxmvp.detailroi.common.network.request.PostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class MyEventFlowHandler extends Handler {
    private String tag = "MyEventFlowHandler";
    public static final int CHECK_BATCH_CACHE = 2;
    public static final int INSERT_DB_THEN_REPORT = 1;
    private static final int REPORT_WAIT_TIME = 2 * 60 * 1000;

    private final EventDao eventDao;
    public MyEventFlowHandler(Looper looper){
        super(looper);
        eventDao = EventDao.getInstance(CommonSDKDBHelper.getInstance(GlobalObject.application));
    }
    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case INSERT_DB_THEN_REPORT:
                JSONArray jsonArray = (JSONArray) msg.obj;
                insertDataToDB(jsonArray);
                requestService(jsonArray);
                break;
            case CHECK_BATCH_CACHE:
                JSONArray jsonArray1 = queryAllDataInCache();
                if (jsonArray1 != null && jsonArray1.length() >0) {
                    requestService(jsonArray1);
                }

                break;
        }
    }

    private void insertDataToDB(JSONArray jsonArray){
        for (int i=0;i<jsonArray.length();i++) {
            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (eventDao != null) {
                eventDao.insert(jsonObject);
            }
        }
    }
    private void updateReportStateToDB(JSONArray jsonArray,int state){
        if(jsonArray == null){
            return;
        }
        for (int i=0;i<jsonArray.length();i++) {
            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(jsonObject == null){
                continue;
            }
            if (eventDao != null) {
                String eventId = jsonObject.optString("event_id");
                eventDao.updateReportStateByEventId(eventId,state);
            }
        }
    }
    private void deleteDataToDB(JSONArray jsonArray){
        if(jsonArray == null){
         return;
        }
        for (int i=0;i<jsonArray.length();i++) {
            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(jsonObject == null){
                continue;
            }
            if (eventDao != null) {
                String eventId = jsonObject.optString("event_id");
                long result = eventDao.deleteRecordByEventId(eventId);
                SameLogTool.i(tag,"delete db-  event_id:"+eventId+"-state:"+result);
            }
        }
    }
    private JSONArray queryAllDataInCache(){
        JSONArray jsonArray = new JSONArray();
        if (eventDao != null) {
            jsonArray = eventDao.queryAllEvent(REPORT_WAIT_TIME);
        }
        return jsonArray;
    }

    private boolean checkFXID(){
        boolean result = false;
        String s = BDIdsManager.getFxId();
        if(!TextUtils.isEmpty(s)){
            result = true;
        }
        return result;
    }
    private JSONArray fillFXID(JSONArray jsonArray){
        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String fx = jsonObject.optString("fx_id");
                if(TextUtils.isEmpty(fx)){
                    jsonObject.put("fx_id",BDIdsManager.getFxId());
                }
                String appID = jsonObject.optString("app_id");
                if(TextUtils.isEmpty(appID)){
                    jsonObject.put("app_id",GlobalObject.appId);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
    private void requestService(JSONArray eventList){
        if(!checkFXID()){
            BDIdsManager.updateSelfId();
            if(BDIdsManager.failedNum < MAX_FAILED_NUM ){
                SameLogTool.e(tag,"fx_id is null,we will retry request,events are cached");
                return;
            }
        }
        if(TextUtils.isEmpty(GlobalObject.appId)){
            SameLogTool.e(tag,"app_id is "+GlobalObject.appId +", so it can't report");
            return;
        }
        fillFXID(eventList);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("event_list",eventList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.get("application/json"),jsonObject.toString());
        Request request = new PostRequest().post(requestBody).getRequest();
        updateReportStateToDB(eventList, EventBean.REPORT_STATE_REPORTING);
        NetworkHelper.getInstance().post(request,new BaseCallBack(){

            @Override
            protected void onFailed(String msg) {
                SameLogTool.d(tag,msg);
                updateReportStateToDB(eventList, EventBean.REPORT_STATE_FAILED);
            }

            @Override
            public void onSucceed(JSONObject responseStr) {
                deleteDataToDB(eventList);
                sendEmptyMessage(CHECK_BATCH_CACHE);
            }
        });
    }
}
