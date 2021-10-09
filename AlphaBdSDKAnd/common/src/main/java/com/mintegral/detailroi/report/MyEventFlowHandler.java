package com.mintegral.detailroi.report;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.mbridge.msdk.thrid.okhttp.MediaType;
import com.mbridge.msdk.thrid.okhttp.Request;
import com.mbridge.msdk.thrid.okhttp.RequestBody;
import com.mintegral.detailroi.common.GlobalObject;
import com.mintegral.detailroi.common.base.utils.SameLogTool;
import com.mintegral.detailroi.common.bean.EventBaseParams;
import com.mintegral.detailroi.common.db.CommonSDKDBHelper;
import com.mintegral.detailroi.common.db.EventDao;
import com.mintegral.detailroi.common.network.NetworkHelper;
import com.mintegral.detailroi.common.network.callback.BaseCallBack;
import com.mintegral.detailroi.common.network.request.PostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyEventFlowHandler extends Handler {
    private static final int CHECK_BATCH_CACHE = 2;
    public static final int INSERT_DB_THEN_REPORT = 1;
    private static final int INTERVAL_BATCH_CHECK_TIME = 5 * 1000;

    private EventDao eventDao;
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
                //todo: 先查数据库缓存，
                sendEmptyMessageDelayed(CHECK_BATCH_CACHE,INTERVAL_BATCH_CHECK_TIME);
                break;
        }
    }

    private void insertDataToDB(JSONArray jsonArray){

    }
    private void deleteDataToDB(JSONArray eventList){

    }
    private List<String> queryAllDataInCache(){
        return new ArrayList<String>(1);
    }
    private void requestService(JSONArray eventList){
        JSONObject jsonObject = new JSONObject();
        EventBaseParams eventBaseParams = new EventBaseParams();
        try {
            jsonObject.put("base", eventBaseParams.jsonObject);
            jsonObject.put("event_list",eventList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.get("application/json"),jsonObject.toString());
        Request request = new PostRequest().post(requestBody).getRequest();

        NetworkHelper.getInstance().post(request,new BaseCallBack(){

            @Override
            protected void onFailed(String msg) {
                SameLogTool.i("====",msg);
            }

            @Override
            public void onSucceed(JSONObject responseStr) {
                deleteDataToDB(eventList);
            }
        });
    }
}
