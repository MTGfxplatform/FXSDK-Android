package com.fxmvp.detailroi.event.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class IAPItemPair {
    public JSONObject jsonObject = new JSONObject();
    public IAPItemPair(String name,int count){
        try {
            jsonObject.put("name",name);
            jsonObject.put("count",count);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
