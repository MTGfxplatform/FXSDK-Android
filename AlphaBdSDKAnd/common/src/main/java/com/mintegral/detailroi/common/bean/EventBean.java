package com.mintegral.detailroi.common.bean;

import com.mintegral.detailroi.common.able.IEventBean;
import com.mintegral.detailroi.common.able.IEventBussBean;
import com.mintegral.detailroi.common.base.NoProguard;
import com.mintegral.detailroi.common.ids.SessionIdsManager;

import org.json.JSONException;
import org.json.JSONObject;

public class EventBean extends IEventBean implements NoProguard {

    public static final int REPORT_STATE_DEFAULT = 0;
    public static final int REPORT_STATE_REPORTING = 1;
    public static final int REPORT_STATE_OK = 2;
    public static final int REPORT_STATE_FAILED = 3;

    public void setEventCommonParams(EventCommonParams eventCommonParams) {
        if(jsonObject != null && jsonObject.has("ext_params")){
            try {
                eventCommonParams.jsonObject.put("ext_params",jsonObject.getJSONObject("ext_params"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        jsonObject = eventCommonParams.jsonObject;
        jsonObject =EventBaseParams.addBaseParams(jsonObject);
    }
    public void setCustomEventJsonObject(JSONObject extJsonObject){
        try {
            jsonObject.put("ext_params",extJsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void setEventBussBean(IEventBussBean eventBussBean) {
        setCustomEventJsonObject(eventBussBean.jsonObject);
    }

    public void setLogCount(long logCount){
        if(jsonObject != null){
            try {
                jsonObject.put("log_count",logCount);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
