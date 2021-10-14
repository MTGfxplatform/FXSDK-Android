package com.mintegral.detailroi.core;

import android.app.Application;
import android.text.TextUtils;

import com.mintegral.detailroi.common.BuildConfig;
import com.mintegral.detailroi.common.GlobalObject;
import com.mintegral.detailroi.common.able.IUserEvent;
import com.mintegral.detailroi.common.base.utils.SameLogTool;
import com.mintegral.detailroi.common.ids.BDIdsManager;
import com.mintegral.detailroi.event.out.UserEventManager;
import com.mintegral.detailroi.preset.PresetManager;
import com.mintegral.detailroi.report.ReportManager;


public class AlphaSDKProvider implements AlphaSDK{
    private String tag = "AlphaSDK";
    private boolean initComplete;

    @Override
    public void init(Application application, String channel,String appId) {
        if(application == null){
            SameLogTool.e(tag,"application is null, alpha sdk init failed");
            return;
        }
        if(TextUtils.isEmpty(appId)){
            SameLogTool.e(tag,"appId is null/empty, alpha sdk init failed");
            return;
        }
        GlobalObject.application = application;
        GlobalObject.appId = appId;
        BDIdsManager.updateSelfId();
        updateChannel(channel);
        initPresetModule();
        ReportManager.getInstance().checkBatchReport();
        initComplete = true;
    }

    @Override
    public void enDebug(boolean debug) {
        if(!initComplete){
            SameLogTool.e(tag,"alpha sdk not init,please init it before do other things");
            return;
        }
        SameLogTool.DBG_D = debug;
    }

    @Override
    public void updateChannel(String channel) {
        if(!initComplete){
            SameLogTool.e(tag,"alpha sdk not init,please init it before do other things");
            return;
        }
        if (!TextUtils.isEmpty(channel) && !TextUtils.isEmpty(channel.trim()) ) {
            GlobalObject.channel = channel;
        }else {
            SameLogTool.e(tag,"channel is error:"+channel);
        }

    }

    private void initPresetModule(){

        PresetManager.getInstance().initPresetModule(GlobalObject.application);
    }

    @Override
    public void exit() {
        if(!initComplete){
            SameLogTool.e(tag,"alpha sdk not init,please init it before do other things");
            return;
        }
        PresetManager.getInstance().sendExitEvent();
    }

    @Override
    public IUserEvent getUserEventManager() {
        if(!initComplete){
            SameLogTool.e(tag,"alpha sdk not init,please init it before do other things");
            return null;
        }
        try {
            Class.forName("com.mintegral.detailroi.event.out.UserEventManager");
            return UserEventManager.getInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            SameLogTool.e(tag,"Event module can't find");
        }
        return null;
    }
}
