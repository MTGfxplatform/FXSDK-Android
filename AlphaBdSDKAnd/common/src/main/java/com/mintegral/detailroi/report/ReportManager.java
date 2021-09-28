package com.mintegral.detailroi.report;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.mintegral.detailroi.common.bean.EventBean;

import org.json.JSONArray;

public class ReportManager {

    private Handler handler ;
    private ReportManager(){
        EventFlowThread eventFlowThread = new EventFlowThread("report_flow_thread");
        eventFlowThread.start();
        handler = new MyEventFlowHandler(eventFlowThread.getLooper());
    }
    private static class Holder{
        private static ReportManager instance = new ReportManager();
    }

    public static ReportManager getInstance() {
        return Holder.instance;
    }

    public void sendRealTimeEvent(EventBean eventBean){
        Message msg = handler.obtainMessage();
        msg.what =MyEventFlowHandler.INSERT_DB_THEN_REPORT;
        JSONArray jsonArray = new JSONArray();
        jsonArray.put(eventBean.toJsonTypeString());
        jsonArray.put(eventBean.toJsonTypeString());
        msg.obj = jsonArray.toString();
        handler.sendMessage(msg);
    }

    public void insertEventToBatch(){

    }
}
