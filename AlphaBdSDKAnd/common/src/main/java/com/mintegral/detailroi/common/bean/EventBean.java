package com.mintegral.detailroi.common.bean;

import com.mintegral.detailroi.common.able.IEventBean;
import com.mintegral.detailroi.common.able.IEventBussBean;
import com.mintegral.detailroi.common.base.NoProguard;
import com.mintegral.detailroi.common.ids.SessionIdsManager;

public class EventBean implements IEventBean,NoProguard {
    EventCommonParams eventCommonParams;
    IEventBussBean eventBussBean;


    public void setEventCommonParams(EventCommonParams eventCommonParams) {
        this.eventCommonParams = eventCommonParams;
        eventCommonParams.setSessionId(SessionIdsManager.getSessionId());

    }

    public void setEventBussBean(IEventBussBean eventBussBean) {
        this.eventBussBean = eventBussBean;
    }

    @Override
    public String toJsonTypeString() {
        return "{" +
                "\"commonParams\":"+eventCommonParams.toJsonTypeString()+","+
                "\"bussParams\":"+eventBussBean.toJsonTypeString()+
                "}";
    }
}
