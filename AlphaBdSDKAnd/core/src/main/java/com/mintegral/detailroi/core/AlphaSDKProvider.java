package com.mintegral.detailroi.core;

import android.app.Application;

import com.mintegral.detailroi.common.BuildConfig;
import com.mintegral.detailroi.common.GlobalObject;
import com.mintegral.detailroi.common.able.IUserEvent;
import com.mintegral.detailroi.common.base.utils.SameLogTool;
import com.mintegral.detailroi.common.ids.BDIdsManager;
import com.mintegral.detailroi.event.UserEventManager;
import com.mintegral.detailroi.preset.PresetManager;


public class AlphaSDKProvider implements AlphaSDK{

    public static boolean debugState = BuildConfig.DEBUG;

    @Override
    public void init(Application application, String channel,String appId) {
        GlobalObject.application = application;
        GlobalObject.appId = appId;
        BDIdsManager.updateSelfId();
        updateChannel(channel);
        initPresetModule();

    }

    @Override
    public void enDebug(boolean debug) {
        debugState = debug;
        SameLogTool.DBG_D = debug;
    }

    @Override
    public void updateChannel(String channel) {
        GlobalObject.channel = channel;

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
            Class clz = Class.forName("com.mintegral.detailroi.event.UserEventManager");
            return UserEventManager.getInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
