package com.fxmvp.detailroi.common.network;

import com.mbridge.alpha.thrid.okhttp.Call;
import com.mbridge.alpha.thrid.okhttp.Callback;
import com.mbridge.alpha.thrid.okhttp.OkHttpClient;
import com.mbridge.alpha.thrid.okhttp.Request;
import com.fxmvp.detailroi.common.base.utils.SameLogTool;
import com.fxmvp.detailroi.common.network.interceptor.NetworkLogInterceptor;

public class NetworkHelper {

    private static  OkHttpClient okHttpClient;

    private NetworkHelper(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if(SameLogTool.DBG_D){
            builder.addInterceptor(new NetworkLogInterceptor());
        }
        okHttpClient = builder.build();

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
