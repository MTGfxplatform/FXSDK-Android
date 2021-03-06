package com.fxmvp.detailroi.common.ids;

import android.os.SystemClock;
import android.text.TextUtils;

import com.mbridge.alpha.thrid.okhttp.Request;
import com.fxmvp.detailroi.common.GlobalObject;
import com.fxmvp.detailroi.common.base.utils.SameDeviceTool;
import com.fxmvp.detailroi.common.base.utils.SharedPreferencesUtils;
import com.fxmvp.detailroi.common.network.NetworkConstant;
import com.fxmvp.detailroi.common.network.NetworkHelper;
import com.fxmvp.detailroi.common.network.callback.BaseCallBack;
import com.fxmvp.detailroi.common.network.request.GetRequest;

import org.json.JSONObject;

public class BDIdsManager {
    private static String fxId;
    private static String backupId;
    public static int failedNum = 0;
    public static int MAX_FAILED_NUM = 3;
    private static boolean isRequestingFXID;

    public static void updateSelfId(){
        if(isRequestingFXID){
            return;
        }
        isRequestingFXID = true;
        GetRequest getRequest = new GetRequest(NetworkConstant.SETTING_URL);
        getRequest.addQueryParameter("device_id", SameDeviceTool.getBase64DeviceIdsString());
        getRequest.addQueryParameter("fx_id",getFxId());
        long currentTime = System.currentTimeMillis();
        getRequest.addQueryParameter("out",currentTime+"");
        long startTime = currentTime - SystemClock.elapsedRealtime();
        getRequest.addQueryParameter("upt", startTime+"");
        getRequest.addQueryParameter("platform","1");
        Request request = getRequest.getRequest();

        NetworkHelper.getInstance().get(request,new BaseCallBack(){

            @Override
            protected void onFailed(String msg) {
                failedNum++;
                isRequestingFXID =false;
            }

            @Override
            public void onSucceed(JSONObject responseJsonObj) {
                failedNum = 0;
                isRequestingFXID =false;
                if(responseJsonObj != null){
                    fxId = responseJsonObj.optString("fx_id");
                    backupId = responseJsonObj.optString("backup_id");
                    SharedPreferencesUtils.setParam(GlobalObject.application,"fx_id",fxId);
                    SharedPreferencesUtils.setParam(GlobalObject.application,"backup_id",backupId);
                }
            }
        });
    }

    public static String getFxId(){
        if(TextUtils.isEmpty(fxId)){
            fxId = (String) SharedPreferencesUtils.getParam(GlobalObject.application,"fx_id","");
        }
        return fxId;
    }

}
