package com.mintegral.detailroi.core;

import android.app.Application;

import com.mintegral.detailroi.common.BuildConfig;
import com.mintegral.detailroi.common.ids.BDIdsManager;


public class AlphaSDKProvider implements AlphaSDK{

    private Application mApplication;
    public static String mChannel;
    public static boolean debugState = BuildConfig.DEBUG;

    @Override
    public void init(Application application, String channel) {
        mApplication = application;
        mChannel = channel;
        BDIdsManager.updateSelfId();
    }

    @Override
    public void enDebug(boolean debug) {
        debugState = debug;
    }

    @Override
    public void updateChannel(String channel) {
        mChannel = channel;
    }
}
