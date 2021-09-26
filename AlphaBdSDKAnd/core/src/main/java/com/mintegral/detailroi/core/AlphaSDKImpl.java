package com.mintegral.detailroi.core;

import android.app.Application;

public class AlphaSDKImpl implements AlphaSDK {

    private final AlphaSDKProvider provider;

    public AlphaSDKImpl() {
        provider = new AlphaSDKProvider();
    }

    @Override
    public void init(Application application, String channel) {
        provider.init(application, channel);
    }

    @Override
    public void enDebug(boolean debug) {
        provider.enDebug(debug);
    }

    @Override
    public void updateChannel(String channel) {
        provider.updateChannel(channel);
    }
}
