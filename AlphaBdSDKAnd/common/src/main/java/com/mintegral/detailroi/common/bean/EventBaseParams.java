package com.mintegral.detailroi.common.bean;

import com.mintegral.detailroi.common.GlobalObject;
import com.mintegral.detailroi.common.able.IEventBean;
import com.mintegral.detailroi.common.base.NoProguard;
import com.mintegral.detailroi.common.base.SDKConfig;
import com.mintegral.detailroi.common.base.utils.CommonTool;
import com.mintegral.detailroi.common.base.utils.SameDeviceTool;
import com.mintegral.detailroi.common.ids.BDIdsManager;
import com.mintegral.detailroi.common.ids.SessionIdsManager;

import org.json.JSONException;

public class EventBaseParams extends IEventBean implements NoProguard {

    public EventBaseParams(){
        init();
    }
    private void init(){
        try {
            jsonObject.put("fx_id", BDIdsManager.getFxId());
            jsonObject.put("sdk_version", SDKConfig.SDK_VERSION);
            jsonObject.put("app_id",GlobalObject.appId);
            jsonObject.put("app_name", SameDeviceTool.getAN(GlobalObject.application));
            jsonObject.put("package_name",SameDeviceTool.getPN(GlobalObject.application));
            jsonObject.put("platform","1");
            jsonObject.put("os_version",android.os.Build.VERSION.RELEASE);
            jsonObject.put("ua", SameDeviceTool.getDUA_UI());
            jsonObject.put("app_version",
                    SameDeviceTool.getVN(GlobalObject.application));
            jsonObject.put("app_version_code",
                    SameDeviceTool.getVC(GlobalObject.application) + "");
            jsonObject.put("brand", SameDeviceTool.getPB());
            jsonObject.put("model", SameDeviceTool.getMd());
            jsonObject.put("language", SameDeviceTool.getLag(GlobalObject.application));
            jsonObject.put("timezone", SameDeviceTool.getTZ());
            jsonObject.put("screen_size", SameDeviceTool.getDpW(GlobalObject.application) + "x"
                    + SameDeviceTool.getDpH(GlobalObject.application));
            jsonObject.put("channel",GlobalObject.channel);
            jsonObject.put("is_first_day", CommonTool.isFirstDay()?"1":"0");
            int network = SameDeviceTool.getNwT();
            jsonObject.put("network_type", network + "");
            jsonObject.put("network_str",SameDeviceTool.getMNWS(GlobalObject.application,network)+"");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}