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
    public static boolean debugState = BuildConfig.DEBUG;

    @Override
    public void init(Application application, String channel,String appId) {
        GlobalObject.application = application;
        GlobalObject.appId = appId;
        BDIdsManager.updateSelfId();
        updateChannel(channel);
        initPresetModule();
        ReportManager.getInstance().checkBatchReport();
    }

    @Override
    public void enDebug(boolean debug) {
        debugState = debug;
        SameLogTool.DBG_D = debug;
    }

    @Override
    public void updateChannel(String channel) {
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
        PresetManager.getInstance().sendExitEvent();
    }

    @Override
    public IUserEvent getUserEventManager() {
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
