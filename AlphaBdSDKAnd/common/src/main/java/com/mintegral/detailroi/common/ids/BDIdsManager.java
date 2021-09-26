package com.mintegral.detailroi.common.ids;

import com.mbridge.msdk.thrid.okhttp.Request;
import com.mintegral.detailroi.common.network.NetworkConstant;
import com.mintegral.detailroi.common.network.NetworkHelper;
import com.mintegral.detailroi.common.network.callback.BaseCallBack;
import com.mintegral.detailroi.common.network.request.GetRequest;

public class BDIdsManager {

    public static void updateSelfId(){
        GetRequest getRequest = new GetRequest();
        getRequest.url(NetworkConstant.BASE_HOST);
        Request request = getRequest.getRequest();
        NetworkHelper.getInstance().get(request,new BaseCallBack());
    }
}
