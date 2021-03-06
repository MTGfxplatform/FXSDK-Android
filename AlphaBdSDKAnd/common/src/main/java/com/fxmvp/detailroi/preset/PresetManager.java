package com.fxmvp.detailroi.preset;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fxmvp.detailroi.common.GlobalObject;
import com.fxmvp.detailroi.common.base.utils.SameLogTool;
import com.fxmvp.detailroi.common.base.utils.SharedPreferencesUtils;
import com.fxmvp.detailroi.common.bean.EventBean;
import com.fxmvp.detailroi.common.bean.EventCommonParams;
import com.fxmvp.detailroi.common.ids.SessionIdsManager;
import com.fxmvp.detailroi.report.ReportManager;

public class PresetManager implements Application.ActivityLifecycleCallbacks {

    private String tag = getClass().getSimpleName();
    private Application mApplication;
    private long inBackgroundTime = 0l;
    private int foreGroundAcNum = 0;
    private long processStartTime;

    private PresetManager() {
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        updateAcInfo(activity);
        if (foreGroundAcNum == 0) {
            checkBackgroundTime();
        }else if(foreGroundAcNum < 0){
            foreGroundAcNum = 0;
        }
        foreGroundAcNum++;
    }

    private void updateAcInfo(Activity activity) {
        GlobalObject.refererAcTitle = GlobalObject.currentAcTitle;
        GlobalObject.refererAcName = GlobalObject.currentAcName;
        GlobalObject.currentAcName = activity.getClass().getName();
        GlobalObject.currentAcTitle = activity.getTitle().toString();
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        foreGroundAcNum--;
        if (foreGroundAcNum <= 0) {
            foreGroundAcNum = 0;
            inBackgroundTime = System.currentTimeMillis();
        }
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }


    private static class Holder {
        private static final PresetManager presetManager = new PresetManager();
    }

    public static PresetManager getInstance() {
        return Holder.presetManager;
    }

    public void initPresetModule(Application application) {
        mApplication = application;
        init();
    }

    private void init() {
        SessionIdsManager.createSessionId();
        checkBackgroundTime();
        foreGroundAcNum--;
        mApplication.registerActivityLifecycleCallbacks(this);
        SameLogTool.d(tag, "preset module init ok");
        checkInstallState();
    }


    private void checkInstallState() {
        String s = (String) SharedPreferencesUtils.getParam(GlobalObject.application, "alpha_ins_label", "");
        if (TextUtils.isEmpty(s)) {
            SharedPreferencesUtils.setParam(GlobalObject.application, "alpha_ins_label", "ok");
            reportInstallEvent();
        }
    }


    private void checkBackgroundTime() {
        long currentTime = System.currentTimeMillis();
        if (inBackgroundTime != 0 && currentTime - inBackgroundTime > 30 * 1000) {
            SessionIdsManager.createSessionId();
            reportEndEvent();
            reportStartEvent();
        } else if (inBackgroundTime == 0) {
            reportStartEvent();
        } else {
            inBackgroundTime = 0l;
        }
    }

    public void sendExitEvent() {
        reportEndEvent();
    }

    private void reportStartEvent() {
        processStartTime = System.currentTimeMillis();
        EventCommonParams eventCommonParams = new EventCommonParams("$AppStart", System.currentTimeMillis());
        EventBean eventBean = new EventBean();
        eventBean.setEventCommonParams(eventCommonParams);
        ReportManager.getInstance().sendRealTimeEvent(eventBean);
    }

    private void reportEndEvent() {
        long duration = System.currentTimeMillis() - processStartTime;
        EventCommonParams eventCommonParams = new EventCommonParams("$AppEnd", System.currentTimeMillis(), duration / 1000);
        EventBean eventBean = new EventBean();
        eventBean.setEventCommonParams(eventCommonParams);
        ReportManager.getInstance().sendRealTimeEvent(eventBean);
    }

    public void reportInstallEvent() {
        EventCommonParams eventCommonParams = new EventCommonParams("$AppInstall", System.currentTimeMillis());
        EventBean eventBean = new EventBean();
        eventBean.setEventCommonParams(eventCommonParams);
        ReportManager.getInstance().sendRealTimeEvent(eventBean);
    }
}
