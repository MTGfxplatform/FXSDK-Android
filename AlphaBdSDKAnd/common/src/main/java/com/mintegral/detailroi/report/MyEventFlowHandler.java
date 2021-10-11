package com.mintegral.detailroi.report;

import static com.mintegral.detailroi.common.ids.BDIdsManager.MAX_FAILED_NUM;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.mbridge.msdk.thrid.okhttp.MediaType;
import com.mbridge.msdk.thrid.okhttp.Request;
import com.mbridge.msdk.thrid.okhttp.RequestBody;
import com.mintegral.detailroi.common.GlobalObject;
import com.mintegral.detailroi.common.base.utils.SameLogTool;

import com.mintegral.detailroi.common.bean.EventBean;
import com.mintegral.detailroi.common.db.CommonSDKDBHelper;
import com.mintegral.detailroi.common.db.EventDao;
import com.mintegral.detailroi.common.ids.BDIdsManager;
import com.mintegral.detailroi.common.network.NetworkHelper;
import com.mintegral.detailroi.common.network.callback.BaseCallBack;
import com.mintegral.detailroi.common.network.request.PostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;


public class MyEventFlowHandler extends Handler {
    private String tag = "MyEventFlowHandler";
    public static final int CHECK_BATCH_CACHE = 2;
    public static final int INSERT_DB_THEN_REPORT = 1;
    private static final int INTERVAL_BATCH_CHECK_TIME = 5 * 1000;

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
                sendEmptyMessageDelayed(CHECK_BATCH_CACHE,INTERVAL_BATCH_CHECK_TIME);
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
            jsonArray = eventDao.queryAllEvent();
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
                return;
            }
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
                SameLogTool.i("====",msg);
                updateReportStateToDB(eventList, EventBean.REPORT_STATE_FAILED);
            }

            @Override
            public void onSucceed(JSONObject responseStr) {
                deleteDataToDB(eventList);
            }
        });
    }
}
