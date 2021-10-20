package com.fxmvp.detailroi.common.ids;

import com.fxmvp.detailroi.common.GlobalObject;
import com.fxmvp.detailroi.common.base.utils.SameDeviceTool;
import com.fxmvp.detailroi.common.base.utils.SameMD5;
import com.fxmvp.detailroi.common.base.utils.SensitiveDataUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class SessionIdsManager {
    public static String sessionId;
    public static String createSessionId(){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("androidID", SensitiveDataUtil.getAndroidID(GlobalObject.application));
            jsonObject.put("IMEI", SensitiveDataUtil.getIMEI(GlobalObject.application));
            jsonObject.put("Oaid", SensitiveDataUtil.getOaid(GlobalObject.application));
            jsonObject.put("gaid",SameDeviceTool.getGId());
            jsonObject.put("packageName",SameDeviceTool.getPN(GlobalObject.application));
            sessionId = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sessionId = SameMD5.getMD5(sessionId+System.currentTimeMillis());
        return sessionId;
    }

    public static String getSessionId(){
        return sessionId;
    }

}
