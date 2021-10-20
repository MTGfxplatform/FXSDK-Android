package com.fxmvp.detailroi.common.network.request;

import com.mbridge.alpha.thrid.okhttp.HttpUrl;
import com.mbridge.alpha.thrid.okhttp.Request;

public class GetRequest extends MyRequest{
    Request.Builder builder;
    HttpUrl.Builder httpBuilder;
    public GetRequest(String url){
        url(url);
        builder = new Request.Builder();
        addCommonHeader(builder);
    }

    public GetRequest url(String url){
        httpBuilder = HttpUrl.parse(url).newBuilder();
        return this;
    }

    public void addQueryParameter(String name,  String value){
        httpBuilder.addQueryParameter(name, value);
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
        builder.url(httpBuilder.build());
        return builder.build();
    }



}
