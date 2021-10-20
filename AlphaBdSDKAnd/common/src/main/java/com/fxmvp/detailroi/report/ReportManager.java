package com.fxmvp.detailroi.report;

import android.os.Handler;
import android.os.Message;

import com.fxmvp.detailroi.common.base.utils.CommonTool;
import com.fxmvp.detailroi.common.bean.EventBean;

import org.json.JSONArray;

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

    public void checkBatchReport(){
        Message msg = handler.obtainMessage();
        msg.what =MyEventFlowHandler.CHECK_BATCH_CACHE;
        handler.sendMessage(msg);
    }
}
