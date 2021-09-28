package com.mintegral.detailroi.common.bean;

import com.mintegral.detailroi.common.GlobalObject;
import com.mintegral.detailroi.common.able.IEventBean;
import com.mintegral.detailroi.common.base.NoProguard;
import com.mintegral.detailroi.common.ids.SessionIdsManager;

public class EventCommonParams implements IEventBean,NoProguard {

    private String event;
    private String fxId;
    private String sessionId;
    private long time;

    public EventCommonParams(){}

    public EventCommonParams(String event, long time){
        this.event = event;
        this.time = time;
        sessionId = SessionIdsManager.getSessionId();
        fxId = GlobalObject.appId;
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


    @Override
    public String toJsonTypeString() {
        return "{" +
                "\"event\":'" + event + '\'' +
                ", \"fxId\":'" + fxId + '\'' +
                ", \"sessionId\":'" + sessionId + '\'' +
                ", \"time\":" + time +
                '}';
    }
}
