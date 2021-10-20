package com.fxmvp.detailroi.report;

import android.os.HandlerThread;

public class EventFlowThread extends HandlerThread {
    public EventFlowThread(String name) {
        super(name);
    }

    public EventFlowThread(String name, int priority) {
        super(name, priority);
    }

}
