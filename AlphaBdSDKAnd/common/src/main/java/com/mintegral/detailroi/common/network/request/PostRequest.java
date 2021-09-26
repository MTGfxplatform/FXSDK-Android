package com.mintegral.detailroi.common.network.request;

import com.mbridge.msdk.thrid.okhttp.Request;
import com.mbridge.msdk.thrid.okhttp.RequestBody;

public class PostRequest extends MyRequest{
    Request.Builder builder;
    public PostRequest(){
        builder = new Request.Builder();
        addCommonHeader(builder);
    }

    public PostRequest url(String url){
        builder.url(url);
        return this;
    }
    public PostRequest post(RequestBody body){
        builder.post(body);
        return this;
    }

    public PostRequest addHeader(String name, String value){
        builder.addHeader(name,value);
        return this;
    }

    public PostRequest setHeader(String name, String value){
        builder.header(name, value);
        return this;
    }


    public Request getRequest(){
        return builder.build();
    }
}
