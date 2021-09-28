package com.mintegral.detailroi.common.ids;

import com.mbridge.msdk.thrid.okhttp.Request;
import com.mintegral.detailroi.common.network.NetworkConstant;
import com.mintegral.detailroi.common.network.NetworkHelper;
import com.mintegral.detailroi.common.network.callback.BaseCallBack;
import com.mintegral.detailroi.common.network.request.GetRequest;

public class BDIdsManager {

    public static void updateSelfId(){
        GetRequest getRequest = new GetRequest();
        getRequest.url(NetworkConstant.SETTING_URL);
        Request request = getRequest.getRequest();
        NetworkHelper.getInstance().get(request,new BaseCallBack(){

            @Override
            protected void onFailed(String msg) {

            }

            @Override
            public void onSucceed(String responseStr) {

            }
        });
    }
}
