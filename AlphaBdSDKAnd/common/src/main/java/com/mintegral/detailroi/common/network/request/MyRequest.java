package com.mintegral.detailroi.common.network.request;

import com.mbridge.msdk.thrid.okhttp.Request;

public class MyRequest {
    public void addCommonHeader(Request.Builder builder){
        builder.addHeader("ceshi","7777");
    }
}
