package com.mintegral.detailroi.common.base.utils.oaid.helper;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.mintegral.detailroi.common.base.utils.oaid.OaidCallback;
import com.mintegral.detailroi.common.base.utils.oaid.interfaces.LenovoIDInterface;


/**
 * @author Paul Luv on 2020/9/23
 */
public class LenovoDeviceHelper {

    private Context mContext;
    LenovoIDInterface lenovoIDInterface;

    public LenovoDeviceHelper(Context ctx) {
        mContext = ctx;
    }

    public void getIdRun(OaidCallback callback) {
        try {
            String result = null;
            String pkgName = mContext.getPackageName();
            Intent intent = new Intent();
            intent.setClassName("com.zui.deviceidservice", "com.zui.deviceidservice.DeviceidService");
            boolean seu = mContext.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            if (seu) {
                if (lenovoIDInterface != null) {
                    String oaid = lenovoIDInterface.a();
                    if (callback != null) {
                        callback.onSuccuss(oaid, false);
                    }
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            lenovoIDInterface = new LenovoIDInterface.len_up.len_down(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}

