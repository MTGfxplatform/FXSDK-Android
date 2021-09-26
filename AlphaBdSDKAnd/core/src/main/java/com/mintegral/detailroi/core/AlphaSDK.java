package com.mintegral.detailroi.core;

import android.app.Application;

public interface AlphaSDK {
    void init(Application application,String channel);
    void enDebug(boolean debug);
    void updateChannel(String channel);
}
