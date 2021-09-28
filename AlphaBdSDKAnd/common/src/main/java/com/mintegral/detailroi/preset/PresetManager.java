package com.mintegral.detailroi.preset;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.mintegral.detailroi.common.base.utils.SameLogTool;
import com.mintegral.detailroi.common.ids.SessionIdsManager;

public class PresetManager implements Application.ActivityLifecycleCallbacks {

    private  String tag = getClass().getSimpleName();
    private Application mApplication;
    private long inBackgroundTime = 0l;
    private int foreGroundAcNum = 0;
    private PresetManager(){}

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        if(foreGroundAcNum==0){
            checkBackgroundTime();
        }
        foreGroundAcNum++;
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        foreGroundAcNum--;
        if(foreGroundAcNum==0){
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


    private static class Holder{
        private static PresetManager presetManager = new PresetManager();
    }

    public static PresetManager getInstance() {
        return Holder.presetManager;
    }

    public void initPresetModule(Application application){
        mApplication = application;
        init();
    }

    private void init(){
        SessionIdsManager.createSessionId();
        mApplication.registerActivityLifecycleCallbacks(this);
        SameLogTool.d(tag,"preset module init ok");
    }




    private void checkBackgroundTime(){
        long currentTime = System.currentTimeMillis();
        if(inBackgroundTime != 0 && currentTime-inBackgroundTime >30 *1000){
            SessionIdsManager.createSessionId();
            reportEndEvent();
            reportStartEvent();
        }else {
            inBackgroundTime = 0l;
        }
    }

    public void sendExitEvent(){
        reportEndEvent();
    }

    private void reportStartEvent(){
        //todo: 添加上报流程
    }
    private void reportEndEvent(){
        //todo：添加上报流程
    }

}
