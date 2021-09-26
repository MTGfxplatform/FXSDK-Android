package com.mintegral.detailroi.common.network;

import com.mbridge.msdk.thrid.okhttp.Call;
import com.mbridge.msdk.thrid.okhttp.Callback;
import com.mbridge.msdk.thrid.okhttp.OkHttpClient;
import com.mbridge.msdk.thrid.okhttp.Request;
import com.mbridge.msdk.thrid.okhttp.RequestBody;

public class NetworkHelper {

    private static  OkHttpClient okHttpClient;

    private NetworkHelper(){
        okHttpClient = new OkHttpClient();
    }
    static class Holder{
        private static final NetworkHelper instance = new NetworkHelper();
    }
    public static NetworkHelper getInstance(){
        return Holder.instance;
    }

    public void get(Request request, Callback callback){
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }

    public void post(Request request,Callback callback){
        Call call = okHttpClient.newCall(request);
        call.enqueue(callback);
    }



}
