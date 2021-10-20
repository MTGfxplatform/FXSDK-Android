package com.fxmvp.detailroi.common.base.utils.oaid.helper;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;

import com.fxmvp.detailroi.common.base.utils.oaid.OaidCallback;


/**
 * @author Paul Luv on 2020/9/23
 */
public class MeizuDeviceHelper {

    private Context mContext;

    public MeizuDeviceHelper(Context ctx) {
        mContext = ctx;
    }


    private boolean isMeizuSupport() {
        try {
            PackageManager pm = mContext.getPackageManager();
            if (pm != null) {
                ProviderInfo pi = pm.resolveContentProvider("com.meizu.flyme.openidsdk", 0);        // "com.meizu.flyme.openidsdk"
                if (pi != null) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void getMeizuID(OaidCallback callback) {
        try {
            mContext.getPackageManager().getPackageInfo("com.meizu.flyme.openidsdk", 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Uri uri = Uri.parse("content://com.meizu.flyme.openidsdk/");

        Cursor cursor;
        ContentResolver contentResolver = mContext.getContentResolver();
        try {
            cursor = contentResolver.query(uri, null, null, new String[]{"oaid"}, null);
            String oaid = getOaid(cursor);

            if (callback != null) {
                callback.onSuccuss(oaid, false);
            }
            if (cursor != null) {
                cursor.close();
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private String getOaid(Cursor cursor) {
        String oaid = null;
        if (cursor == null) {
            return null;
        }
        if (cursor.isClosed()) {
            return null;
        }
        cursor.moveToFirst();
        int valueIdx = cursor.getColumnIndex("value");
        if (valueIdx > 0) {
            oaid = cursor.getString(valueIdx);
        }
        valueIdx = cursor.getColumnIndex("code");
        if (valueIdx > 0) {
            int codeID = cursor.getInt(valueIdx);
        }
        valueIdx = cursor.getColumnIndex("expired");
        if (valueIdx > 0) {
            long timeC = cursor.getLong(valueIdx);
        }
        return oaid;
    }
}

