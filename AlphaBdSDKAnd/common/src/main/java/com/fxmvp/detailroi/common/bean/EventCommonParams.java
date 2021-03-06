package com.fxmvp.detailroi.common.bean;

import android.util.Base64;

import com.fxmvp.detailroi.common.GlobalObject;
import com.fxmvp.detailroi.common.able.IEventBean;
import com.fxmvp.detailroi.common.base.NoProguard;
import com.fxmvp.detailroi.common.base.utils.SameDeviceTool;
import com.fxmvp.detailroi.common.base.utils.SameMD5;
import com.fxmvp.detailroi.common.ids.SessionIdsManager;

import org.json.JSONException;
import org.json.JSONObject;

public class EventCommonParams extends  IEventBean implements NoProguard {

    private String event;
    private String fxId;
    private String sessionId;
    private long time;



    public EventCommonParams(String event, long time) {
        this.event = event;
        this.time = time;
        try {
            jsonObject.put("event",event);
            jsonObject.put("time",System.currentTimeMillis());
            jsonObject.put("session_id",SessionIdsManager.getSessionId());
            JSONObject eventJsonObj = new JSONObject();
            try {
                eventJsonObj.put("package",SameDeviceTool.getPN(GlobalObject.application));
                eventJsonObj.put("app_version",SameDeviceTool.getVN(GlobalObject.application));
                eventJsonObj.put("deviceIds",SameDeviceTool.getDeviceIdsString());
                eventJsonObj.put("temp",System.currentTimeMillis());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            jsonObject.put("event_id", SameMD5.getMD5(Base64.encodeToString(eventJsonObj.toString().getBytes(),Base64.NO_WRAP)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public EventCommonParams(String event, long time,long duration) {
        this(event,time);
        try {
            jsonObject.put("duration",duration);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getFxId() {
        return fxId;
    }

    public void setFxId(String fxId) {
        this.fxId = fxId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }


}
