package com.mintegral.detailroi.common.network.request;

import static com.mintegral.detailroi.common.network.NetworkConstant.REPORT_URL;

import com.mbridge.alpha.thrid.okhttp.Request;
import com.mbridge.alpha.thrid.okhttp.RequestBody;

public class PostRequest extends MyRequest{
    Request.Builder builder;
    public PostRequest(){
        builder = new Request.Builder();
        addCommonHeader(builder);
        url(REPORT_URL);
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
