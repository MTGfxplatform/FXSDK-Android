package com.fxmvp.detailroi.common.able;

import androidx.annotation.NonNull;

import org.json.JSONObject;

public  class IEventBean {
    public JSONObject jsonObject = new JSONObject();
    @NonNull
    public String toString(){
        return jsonObject.toString();
    }
}
