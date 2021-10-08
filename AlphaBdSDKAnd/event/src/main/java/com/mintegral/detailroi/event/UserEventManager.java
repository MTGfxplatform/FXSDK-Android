package com.mintegral.detailroi.event;

import com.mintegral.detailroi.common.able.IUserEvent;
import com.mintegral.detailroi.common.bean.EventBean;
import com.mintegral.detailroi.common.bean.EventCommonParams;
import com.mintegral.detailroi.event.bean.IAPEventBean;
import com.mintegral.detailroi.report.ReportManager;

import org.json.JSONObject;

public class UserEventManager implements IUserEvent {
    private static class Holder{
        private static UserEventManager instance = new UserEventManager();
    }
    private UserEventManager(){}
    public static UserEventManager getInstance() {
        return Holder.instance;
    }

    @Override
    public void sendCustomEvent(JSONObject jsonObject) {

    }

    public void sendIAPEvent(IAPEventBean iapEventBean){
        EventCommonParams eventCommonParams = new EventCommonParams("IAP",System.currentTimeMillis(),0);
        EventBean eventBean = new EventBean();
        eventBean.setEventBussBean(iapEventBean);
        eventBean.setEventCommonParams(eventCommonParams);
        ReportManager.getInstance().sendRealTimeEvent(eventBean);
    }

}
