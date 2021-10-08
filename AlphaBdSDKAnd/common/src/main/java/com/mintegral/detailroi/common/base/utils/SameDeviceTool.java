package com.mintegral.detailroi.common.base.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Looper;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.core.app.ActivityCompat;

import com.mintegral.detailroi.common.GlobalObject;
import com.mintegral.detailroi.common.base.CommonConstant;
import com.mintegral.detailroi.common.threadpool.ThreadPoolUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

public class SameDeviceTool {
    private static final String TAG = "SameDeviceTool";
    private static String acid;
    private static String b64GID;
    private static String uAt = "";
    private static String ovsn = "";
    private static String pkN = "";
    private static String vsnName = "";
    private static int vsnCode = 0;
    private static String luge = "";
    private static String tiZe = "";
    private static String an = "";
    private static volatile int nWType = CommonConstant.NET_TYPE_UNDEFAAULT;


    public static String getAN(Context context) {
        if (!TextUtils.isEmpty(an)) {
            return an;
        }
        if (context == null) {
            an = "";
            return an;
        }
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(getPN(context), 0);
            an = (String) pm.getApplicationLabel(ai);
            return an;
        } catch (Exception e) {
            an = "";
            return an;
        }
    }

    public static void setGId(String ad) {
        b64GID = SameBase64Tool.newBase64Encode(ad);
        acid = ad;
    }

    public static String getGId() {

        if (acid == null) {
            return "";
        }
        return acid;
    }

    public static String getB64GID() {

        if (b64GID == null) {
            return "";
        }
        return b64GID;

    }

    public static String getPN(Context context) {
        if (context == null) {
            return pkN;
        }
        try {
            if (TextUtils.isEmpty(pkN)) {
                PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                pkN = pi.packageName;
                return pkN;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return pkN;
    }

    public static String getTZ() {
        try {
            if (TextUtils.isEmpty(tiZe)) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            TimeZone tz = TimeZone.getDefault();
                            tiZe = tz.getDisplayName(false, TimeZone.SHORT, Locale.ENGLISH);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                return tiZe;
            }
        } catch (Throwable e) {
            SameLogTool.e(TAG, e.getMessage(), e);
        }
        return tiZe;
    }

    public static String getDUA_UI() {
        if (TextUtils.isEmpty(uAt)) {
            getDUA_UI(GlobalObject.application);
        }
        return uAt;
    }

    public static int getVC(Context context) {
        if (context == null) {
            return vsnCode;
        }
        if (vsnCode == 0) {
            try {
                PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                vsnCode = pi.versionCode;
                return vsnCode;
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }
        return vsnCode;
    }

    public static String getVN(Context context) {
        if (context == null) {
            return vsnName;
        }
        try {
            if (TextUtils.isEmpty(vsnName)) {
                PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                vsnName = pi.versionName;
                return vsnName;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return vsnName;

    }

    public static String getDUA_UI(final Context context) {
        fillUAFromSP(context);
        try {
            boolean isInMainThread = Looper.myLooper() == Looper.getMainLooper();
            if (isInMainThread) {
                if (TextUtils.isEmpty(uAt)) {
                    try {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                            uAt = WebSettings.getDefaultUserAgent(context);
                        }
                    } catch (Throwable e) {
                    }

                    if (TextUtils.isEmpty(uAt)) {
                        try {
                            Constructor<WebSettings> constructor = WebSettings.class.getDeclaredConstructor(Context.class, WebView.class);
                            constructor.setAccessible(true);
                            WebSettings settings = constructor.newInstance(context, null);
                            uAt = settings.getUserAgentString();
                            constructor.setAccessible(false);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                        if (TextUtils.isEmpty(uAt)) {
                            try {
                                uAt = new WebView(context).getSettings().getUserAgentString();
                            } catch (Throwable t) {
                                t.printStackTrace();
                            }
                        }
                        if (TextUtils.isEmpty(uAt)) {
                            setAUA();
                        }
                    }
                } else {
                    try {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String currentUa = null;
                                    try {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                                            currentUa = WebSettings.getDefaultUserAgent(context);
                                        }
                                    } catch (Exception e) {
                                    }

                                    if (!TextUtils.isEmpty(currentUa) && !currentUa.equals(uAt)) {
                                        uAt = currentUa;
                                        saveUA(context);
                                    }
                                } catch (Throwable e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                setAUA();
            }
        } catch (Throwable t) {
            SameLogTool.e(TAG, t.getMessage(), t);
        }
        saveUA(context);
        return uAt;
    }

    private static void fillUAFromSP(Context context) {
        if (TextUtils.isEmpty(uAt)) {
            try {
                uAt = SharedPreferencesUtils.getParam(context, "alpha_ua", "").toString();
            } catch (Throwable t) {
                SameLogTool.e(TAG, t.getMessage(), t);
            }
        }
    }

    private static void saveUA(Context context) {
        try {
            SharedPreferencesUtils.setParam(context, "alpha_ua", uAt);
        } catch (Throwable t) {
            SameLogTool.e(TAG, t.getMessage(), t);
        }

    }

    private static void setAUA() {
        String version = Build.VERSION.RELEASE;
        String phoneModel = getMd();
        if (!TextUtils.isEmpty(version) && !TextUtils.isEmpty(phoneModel)) {
            uAt = "Mozilla/5.0 (Linux; Android " + version + "; " + phoneModel + " Build/"
                    + ") AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.133 Mobile Safari/535.19";
        } else {
            uAt = "Mozilla/5.0 (Linux; Android 4.0.4; Galaxy Nexus Build/IMM76B) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.133 Mobile Safari/535.19";
        }
    }

    public static String getLag(Context context) {

        if (TextUtils.isEmpty(luge)) {
            if (context == null) {
                return "en-US";
            }
            try {
                if (context.getResources() == null || context.getResources().getConfiguration() == null) {
                    return "en-US";
                }
                Locale locale = context.getResources().getConfiguration().locale;
                if (locale == null) {
                    return "en-US";
                }
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    luge = locale.toLanguageTag();
                } else {
                    luge = locale.getLanguage() + "-" + locale.getCountry();
                }
                return luge;
            } catch (Throwable t) {
                SameLogTool.d(TAG, t.getMessage());
                luge = "en-US";
            }
        }
        return luge;
    }

    public static int getDpW(Context context) {

        if (context == null) {
            return 0;
        }
        try {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            HashMap sizeMap = getSDp(context);
            return sizeMap.get("width") == null ? dm.widthPixels : (int) sizeMap.get("width");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static int getDpH(Context context) {

        if (context == null) {
            return 0;
        }
        try {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            HashMap sizeMap = getSDp(context);
            return sizeMap.get("height") == null ? dm.heightPixels : (int) sizeMap.get("height");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static HashMap getSDp(Context context) {
        HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
        if (context == null) {
            return hashMap;
        }
        try {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            DisplayMetrics displayMetrics = new DisplayMetrics();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                display.getRealMetrics(displayMetrics);
            } else {
                display.getMetrics(displayMetrics);
            }

            if (displayMetrics != null) {
                hashMap.put("height", displayMetrics.heightPixels);
                hashMap.put("width", displayMetrics.widthPixels);
            }
        } catch (Exception e) {
            SameLogTool.e(TAG, e.getMessage(), e);
        }
        return hashMap;
    }


    public static int getNwT() {
        try {
            Context context = GlobalObject.application;

            if (context == null) {
                return nWType;
            }
            if (nWType != CommonConstant.NET_TYPE_UNDEFAAULT) {
                asynGNwT(context);
                return nWType;
            }
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm == null) {
                return nWType;
            }
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) {

                NetworkInfo netInfo = cm.getActiveNetworkInfo();
                if (netInfo == null) {
                    nWType = CommonConstant.NET_TYPE_UNKNOW;
                    return nWType;
                }
                if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    nWType = CommonConstant.NET_TYPE_WIFI;
                    return nWType;
                }
                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (tm == null) {
                    nWType = CommonConstant.NET_TYPE_UNKNOW;
                    return nWType;
                }
                nWType = CommonConstant.NET_TYPE_MOBI;
                return nWType;
            } else {
                nWType = CommonConstant.NET_TYPE_UNKNOW;
            }
        } catch (Exception e) {
            SameLogTool.e(TAG, e.getMessage(), e);
            nWType = CommonConstant.NET_TYPE_UNKNOW;
            return nWType;
        }
        return nWType;
    }

    public static String getMNWS(Context context, int network) {
        String state = "";
        if(context == null){
            return state;
        }

        if (network == CommonConstant.NET_TYPE_UNKNOW || network == CommonConstant.NET_TYPE_WIFI) {
            return state;
        }
        try {

                TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (tm == null) {
                    return state;
                }
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return state;
                }
                int netType = tm.getNetworkType();
                state = String.valueOf(netType);

        } catch (Throwable t) {
            SameLogTool.e(TAG, t.getMessage(), t);
        }
        return state;
    }
    public static void asynGNwT(final Context context) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    if (context == null) {
                        return;
                    }
                    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (cm == null) {
                        return;
                    }
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) {

                        NetworkInfo netInfo = cm.getActiveNetworkInfo();
                        if (netInfo == null) {
                            nWType = CommonConstant.NET_TYPE_UNKNOW;
                            return;
                        }
                        if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                            nWType = CommonConstant.NET_TYPE_WIFI;
                            return;
                        }
                        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                        if (tm == null) {
                            nWType = CommonConstant.NET_TYPE_UNKNOW;
                            return;
                        }
                        nWType = CommonConstant.NET_TYPE_MOBI;
                    }
                } catch (Exception e) {
                    SameLogTool.e(TAG, e.getMessage(), e);
                    nWType = CommonConstant.NET_TYPE_UNKNOW;
                }
            }
        };
        ThreadPoolUtils.getCommonThreadPool().execute(runnable);
    }

    public static String getDeviceIdsString(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("gaid",getGId());
            jsonObject.put("oaid",SensitiveDataUtil.getOaid(GlobalObject.application));
            jsonObject.put("android_id",SensitiveDataUtil.getAndroidID(GlobalObject.application));
            jsonObject.put("imei",SensitiveDataUtil.getIMEI(GlobalObject.application));
            jsonObject.put("idfa","");
            jsonObject.put("idfv","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    public static String getMd() {

        return Build.MODEL;
    }
    public static String getPB() {
        return Build.BRAND;
    }
}
