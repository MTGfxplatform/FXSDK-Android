package com.mintegral.detailroi.core;

import android.app.Application;

import com.mintegral.detailroi.common.able.IUserEvent;

public class AlphaSDKImpl implements AlphaSDK {

    private final AlphaSDKProvider provider;

    public AlphaSDKImpl() {
        provider = new AlphaSDKProvider();
    }

    @Override
    public void init(Application application, String channel,String appId) {
        provider.init(application, channel,appId);
    }

    @Override
    public void enDebug(boolean debug) {
        provider.enDebug(debug);
    }

    @Override
    public void updateChannel(String channel) {
        provider.updateChannel(channel);
    }

    @Override
    public void exit() {
        provider.exit();
    }

    @Override
    public IUserEvent getUserEventManager() {
        return provider.getUserEventManager();
    }
}
