package com.mintegral.detailroi.common.network.request;

import com.mbridge.msdk.thrid.okhttp.Request;

public class GetRequest extends MyRequest{
    Request.Builder builder;
    public GetRequest(){
        builder = new Request.Builder();
        addCommonHeader(builder);
    }

    public GetRequest url(String url){
        builder.url(url);
        return this;
    }

    public GetRequest addHeader(String name, String value){
        builder.addHeader(name,value);
        return this;
    }

    public GetRequest setHeader(String name, String value){
        builder.header(name, value);
        return this;
    }

    public Request getRequest(){
        return builder.build();
    }


}
