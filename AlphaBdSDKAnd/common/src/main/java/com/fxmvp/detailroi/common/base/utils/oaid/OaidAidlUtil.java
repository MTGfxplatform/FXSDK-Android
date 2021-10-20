package com.fxmvp.detailroi.common.base.utils.oaid;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

public class OaidAidlUtil {
    private static final String TAG = "OaidAidlUtil";
    private static final String SERVICE_PACKAGE_NAME = "com.huawei.hwid";
    private static final String SERVICE_ACTION = "com.uodis.opendevice.OPENIDS_SERVICE";
    private Context mContext;
    private ServiceConnection mServiceConnection;
    private OpenDeviceIdentifierService mService;
    private OaidCallback mCallback;

    public OaidAidlUtil(Context context) {
        mContext = context;
    }

    private boolean bindService() {
        if (null == mContext) {
            return false;
        }
        mServiceConnection = new IdentifierServiceConnection();
        Intent intent = new Intent(SERVICE_ACTION);
        intent.setPackage(SERVICE_PACKAGE_NAME);
        boolean result = mContext.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
        return result;
    }

    private void unbindService() {
        if (null == mContext) {
            return;
        }
        if (null != mServiceConnection) {
            mContext.unbindService(mServiceConnection);
            mService = null;
            mContext = null;
            mCallback = null;
        }
    }

    public void getOaid(OaidCallback callback) {
        try {
            if (null == callback) {
                return;
            }
            mCallback = callback;
            bindService();
        } catch (Throwable t) {
        }
    }

    private final class IdentifierServiceConnection implements ServiceConnection {

        private IdentifierServiceConnection() {
        }

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mService = OpenDeviceIdentifierService.Stub.asInterface(iBinder);
            if (null != mService) {
                try {
                    if (null != mCallback) {
                        mCallback.onSuccuss(mService.getOaid(), mService.isOaidTrackLimited());
                    }
                } catch (RemoteException e) {
                    if (null != mCallback) {
                        mCallback.onFail(e.getMessage());
                    }
                } catch (Exception e) {
                    if (null != mCallback) {
                        mCallback.onFail(e.getMessage());
                    }
                } finally {
                    unbindService();
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mService = null;
        }
    }
}
