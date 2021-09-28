package com.mintegral.detailroi.core;

import android.app.Application;

import com.mintegral.detailroi.common.able.IUserEvent;

public interface AlphaSDK {
    void init(Application application,String channel,String appId);
    void enDebug(boolean debug);
    void updateChannel(String channel);
    void exit();
    IUserEvent getUserEventManager();
}
