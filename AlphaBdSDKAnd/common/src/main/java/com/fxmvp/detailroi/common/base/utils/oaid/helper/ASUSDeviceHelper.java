package com.fxmvp.detailroi.common.base.utils.oaid.helper;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;


import com.fxmvp.detailroi.common.base.utils.oaid.OaidCallback;
import com.fxmvp.detailroi.common.base.utils.oaid.interfaces.ASUSIDInterface;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Paul Luv on 2020/9/23
 */
public class ASUSDeviceHelper {

    private Context mContext;
    public final LinkedBlockingQueue<IBinder> linkedBlockingQueue = new LinkedBlockingQueue(1);

    public ASUSDeviceHelper(Context ctx) {
        mContext = ctx;
    }

    public void getID(OaidCallback callback) {
        try {
            mContext.getPackageManager().getPackageInfo("com.asus.msa.SupplementaryDID", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = new Intent();
        intent.setAction("com.asus.msa.action.ACCESS_DID");
        ComponentName componentName = new ComponentName("com.asus.msa.SupplementaryDID", "com.asus.msa.SupplementaryDID.SupplementaryDIDService");
        intent.setComponent(componentName);

        boolean isBin = mContext.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        if (isBin) {
            try {
                IBinder iBinder = linkedBlockingQueue.take();
                ASUSIDInterface.ASUSID asusID = new ASUSIDInterface.ASUSID(iBinder);
                String asusOAID = asusID.getID();

                if (callback != null) {
                    callback.onSuccuss(asusOAID, false);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Throwable t){
                t.printStackTrace();
            }
        }
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                linkedBlockingQueue.put(service);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };
}

