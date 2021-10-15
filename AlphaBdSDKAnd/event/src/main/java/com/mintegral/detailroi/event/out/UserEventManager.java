package com.mintegral.detailroi.event.out;

import android.text.TextUtils;

import com.mintegral.detailroi.common.able.IUserEvent;
import com.mintegral.detailroi.common.base.utils.SameLogTool;
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
    public void track(String name, JSONObject jsonObject) {
        EventCommonParams eventCommonParams = new EventCommonParams(name,System.currentTimeMillis());
        EventBean eventBean = new EventBean();
        eventBean.setCustomEventJsonObject(jsonObject);
        eventBean.setEventCommonParams(eventCommonParams);
        ReportManager.getInstance().sendRealTimeEvent(eventBean);
    }

    public void sendIAPEvent(IAPEventBean iapEventBean){
        if(iapEventBean != null){
            if(TextUtils.isEmpty(iapEventBean.getCurrency()) || iapEventBean.getAmount() == 0){
                SameLogTool.e("","iapEventBean is error :"+iapEventBean.jsonObject.toString());
                return;
            }
            EventCommonParams eventCommonParams = new EventCommonParams("$IAP",System.currentTimeMillis());
            EventBean eventBean = new EventBean();
            eventBean.setEventBussBean(iapEventBean);
            eventBean.setEventCommonParams(eventCommonParams);
            ReportManager.getInstance().sendRealTimeEvent(eventBean);
        }else {
            SameLogTool.e("","iapEventBean is error,iapEventBean is null");
        }

    }

}
