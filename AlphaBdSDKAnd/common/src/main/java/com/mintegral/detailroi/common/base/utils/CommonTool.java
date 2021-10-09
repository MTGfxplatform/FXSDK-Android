package com.mintegral.detailroi.common.base.utils;



import com.mintegral.detailroi.common.GlobalObject;

import java.util.Calendar;

public class CommonTool {
    private static boolean firstDayLabel = true;
    public static boolean isFirstDay(){
        if(!firstDayLabel){
            return false;
        }
        int s = (int) SharedPreferencesUtils.getParam(GlobalObject.application,"alpha_first_day_label",0);
        int timeResult = getCurrentDayForInt();
        if(s == 0){
            SharedPreferencesUtils.setParam(GlobalObject.application,"alpha_first_day_label",timeResult);
            firstDayLabel = true;

        }else {
            firstDayLabel = s == timeResult;
        }
        return firstDayLabel;
    }

    public static int getCurrentDayForInt(){
        Calendar calendar = Calendar.getInstance();
        int cy = calendar.get(Calendar.YEAR);
        int cm = calendar.get(Calendar.MONTH)+1;
        int cd = calendar.get(Calendar.DAY_OF_MONTH);
        return cy*10000+cm*100+cd;
    }
    public static boolean isNeedResetLogCount(int lastDayForInt){
        Calendar calendar = Calendar.getInstance();
        int cy = calendar.get(Calendar.YEAR);
        int cm = calendar.get(Calendar.MONTH)+1;
        int cd = calendar.get(Calendar.DAY_OF_MONTH);
        int currentInt = cy*10000+cm*100+cd;
        return currentInt != lastDayForInt;
    }
}
