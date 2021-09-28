package com.mintegral.detailroi.common.ids;

import android.text.TextUtils;

public class SessionIdsManager {
    public static String sessionId;
    public static String createSessionId(){
        sessionId = "";
        return sessionId;
    }

    public static String getSessionId(){
        if(TextUtils.isEmpty(sessionId)){
            sessionId =createSessionId();
        }
        return sessionId;
    }

}
