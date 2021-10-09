package com.mintegral.detailroi.report;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import com.mintegral.detailroi.common.base.utils.CommonTool;
import com.mintegral.detailroi.common.bean.EventBean;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class ReportManager {

    private final Handler handler ;
    private static long logCount = 0;
    private static int logCountDayForInt;
    private ReportManager(){
        EventFlowThread eventFlowThread = new EventFlowThread("report_flow_thread");
        eventFlowThread.start();
        handler = new MyEventFlowHandler(eventFlowThread.getLooper());
    }
    private static class Holder{
        private static final ReportManager instance = new ReportManager();
    }

    public static ReportManager getInstance() {
        return Holder.instance;
    }

    public void sendRealTimeEvent(EventBean eventBean){
        if(CommonTool.isNeedResetLogCount(logCountDayForInt)){
            logCountDayForInt = CommonTool.getCurrentDayForInt();
            logCount=0;
        }
        Message msg = handler.obtainMessage();
        msg.what =MyEventFlowHandler.INSERT_DB_THEN_REPORT;
        JSONArray jsonArray = new JSONArray();
        eventBean.setLogCount(logCount);
        logCount++;
        jsonArray.put(eventBean.jsonObject);
        msg.obj = jsonArray;
        handler.sendMessage(msg);
    }

    public void insertEventToBatch(){

    }
}
