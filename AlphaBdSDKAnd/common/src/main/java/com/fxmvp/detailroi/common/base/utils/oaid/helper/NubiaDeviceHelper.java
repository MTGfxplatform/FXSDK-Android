package com.fxmvp.detailroi.common.base.utils.oaid.helper;

import android.content.ContentProviderClient;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

/**
 * @author Paul Luv on 2020/9/23
 */
public class NubiaDeviceHelper {

    private Context mConetxt;

    public NubiaDeviceHelper(Context ctx) {
        mConetxt = ctx;
    }

    public String getNubiaID() {
        String oaid = null;
        Bundle bundle;
        try {
            Uri uri = Uri.parse("content://cn.nubia.identity/identity");
            if (Build.VERSION.SDK_INT > 17) {
                ContentProviderClient contentProviderClient = mConetxt.getContentResolver().acquireContentProviderClient(uri);
                bundle = contentProviderClient.call("getOAID", null, null);
                if (contentProviderClient != null) {
                    if (Build.VERSION.SDK_INT >= 24) {
                        contentProviderClient.close();
                    } else {
                        contentProviderClient.release();
                    }
                }
            } else {
                bundle = mConetxt.getContentResolver().call(uri, "getOAID", null, null);
            }
            int code = -1;
            if (bundle != null){
                code = bundle.getInt("code", -1);
            }
            if (code == 0) {
                oaid = bundle.getString("id");
                return oaid;
            }
            return oaid;
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable t){
            t.printStackTrace();
        }
        return oaid;
    }
}

