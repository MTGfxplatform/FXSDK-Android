package com.fxmvp.detailroi.common.base.utils;

import static android.content.Context.WIFI_SERVICE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;


import com.fxmvp.detailroi.common.base.utils.oaid.OaidAidlUtil;
import com.fxmvp.detailroi.common.base.utils.oaid.OaidCallback;
import com.fxmvp.detailroi.common.base.utils.oaid.helper.ASUSDeviceHelper;
import com.fxmvp.detailroi.common.base.utils.oaid.helper.LenovoDeviceHelper;
import com.fxmvp.detailroi.common.base.utils.oaid.helper.MeizuDeviceHelper;
import com.fxmvp.detailroi.common.base.utils.oaid.helper.NubiaDeviceHelper;
import com.fxmvp.detailroi.common.base.utils.oaid.helper.OnePlusDeviceHelper;
import com.fxmvp.detailroi.common.base.utils.oaid.helper.OppoDeviceHelper;
import com.fxmvp.detailroi.common.base.utils.oaid.helper.SamsungDeviceHelper;
import com.fxmvp.detailroi.common.base.utils.oaid.helper.VivoDeviceHelper;
import com.fxmvp.detailroi.common.base.utils.oaid.helper.ZTEDeviceHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by songjunjun on 2018/6/19.
 */

public class SensitiveDataUtil {

    private static String deviceid;
    private static String macAddress;
    private static String androidID;
    private static String mSelfId;
    private static String imsi;
    private static String oaid;

    private static void writeFile(Context context, File file, String content) throws IOException {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(content.getBytes());
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    private static String writeInstallationFile(Context context, File installation) throws IOException {
        UUID uuid = UUID.randomUUID();
        writeFile(context, installation, uuid.toString());
        return uuid.toString();
    }

    private static String readInstallationFile(File installation) throws IOException {
        RandomAccessFile accessFile = null;
        try {
            accessFile = new RandomAccessFile(installation, "r");
            byte[] bs = new byte[(int) accessFile.length()];
            accessFile.readFully(bs);
            return new String(bs);
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (accessFile != null) {
                accessFile.close();
            }
        }
        return null;
    }

    public static String getSelfId(Context context) {
        if (mSelfId == null) {
            File installation = new File(Environment.getExternalStorageDirectory().toString(), "/.a/track_id.bin");
            try {
                if (!installation.exists()) {
                    mSelfId = writeInstallationFile(context, installation);
                } else {
                    mSelfId = readInstallationFile(installation);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (mSelfId == null) {
            return "";
        }

        return mSelfId;
    }

    public static String getIMEI(Context context) {
        try {
            if (TextUtils.isEmpty(deviceid)) {
                TelephonyManager telephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                deviceid = telephonyMgr.getDeviceId();
                if (deviceid == null) {
//                    deviceid = getSelfId(context);
//                    if (deviceid == null) {
                    deviceid = "";
//                    }
                }
            }
        } catch (Throwable e) {
            deviceid = "";
        }
        return deviceid;
    }

    public static String getMacAddress(Context context) {
        try {
            if (TextUtils.isEmpty(macAddress)) {
                String mac;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mac = getMac();
                } else {
                    WifiManager wifi = (WifiManager) context.getSystemService(WIFI_SERVICE);
                    WifiInfo info = wifi.getConnectionInfo();
                    mac = info.getMacAddress();
                }
                if (mac == null) {
                    return "";
                }
                mac = mac.replaceAll(":", "");

                macAddress = mac.toLowerCase();
                return macAddress;
            }
        } catch (Exception e) {
            return "";
        }
        return macAddress;
    }


    private static String getMac() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:", b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }


    public static String getAndroidID(Context context) {
        try {
            if (TextUtils.isEmpty(androidID)) {
                androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

                if (androidID == null) {
                    androidID = "";
                }
                return androidID;
            }
        } catch (Exception e) {
            androidID = "";
        }
        return androidID;
    }

    public static String getImsi(Context context) {
        try {
            if (TextUtils.isEmpty(imsi)) {
                TelephonyManager telephonyMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                imsi = telephonyMgr.getSubscriberId();
                if (imsi == null) {
                    imsi = "";
                }
            }
        } catch (Exception e) {
            imsi = "";
        }
        return imsi;
    }

    public static String getOaid(Context context) {
        if (!TextUtils.isEmpty(oaid)) {
            return oaid;
        }
        try {
            oaid = new a(context).b;
            if (!TextUtils.isEmpty(oaid)) {
                return oaid;
            }
            String MANUFACTURER = Build.MANUFACTURER;
            if (isFreeMeOS()) {
                MANUFACTURER = "FERRMEOS";
            } else if (isSSUIOS()) {
                MANUFACTURER = "SSUI";
            }
            if (!TextUtils.isEmpty(MANUFACTURER)) {
                MANUFACTURER = MANUFACTURER.toUpperCase();
                if (Arrays.asList("ASUS", "HUAWEI", "OPPO", "ONEPLUS", "ZTE", "FERRMEOS", "SSUI", "SAMSUNG", "MEIZU", "MOTOLORA", "LENOVO").contains(MANUFACTURER)) {
                    getFormNewThread(context, MANUFACTURER);
                } else if ("VIVO".equals(MANUFACTURER)) {
                    oaid = new VivoDeviceHelper(context).getOaid();
                } else if ("NUBIA".equals(MANUFACTURER)) {
                    oaid = new NubiaDeviceHelper(context).getNubiaID();
                }
            }
        } catch (Throwable t) {

        }
        return "";
    }

    public static boolean isFreeMeOS() {
        String pro = getProperty("ro.build.freeme.label");      // "ro.build.freeme.label"
        if ((!TextUtils.isEmpty(pro)) && pro.equalsIgnoreCase("FREEMEOS")) {      // "FreemeOS"  FREEMEOS
            return true;
        }
        return false;
    }

    public static boolean isSSUIOS() {
        String pro = getProperty("ro.ssui.product");    // "ro.ssui.product"
        if ((!TextUtils.isEmpty(pro)) && (!pro.equalsIgnoreCase("unknown"))) {
            return true;
        }
        return false;
    }

    private static String getProperty(String property) {
        String res = null;
        if (property == null) {
            return null;
        }
        try {
            Class clazz = Class.forName("android.os.SystemProperties");
            Method method = clazz.getMethod("get", new Class[]{String.class, String.class});
            res = (String) method.invoke(clazz, new Object[]{property, "unknown"});
        } catch (Exception e) {
            // ignore
        }
        return res;
    }

    @SuppressLint({"PrivateApi"})
    static final class a {
        private static Object e;
        private static Class<?> f;
        private static Method g;
        private static Method h;
        private static Method i;
        private static Method j;
        final String a;
        final String b;
        final String c;
        final String d;

        static {
            g = null;
            h = null;
            i = null;
            j = null;
            try {
                f = Class.forName("com.android.id.impl.IdProviderImpl");
                e = f.newInstance();
                g = f.getMethod("getUDID", new Class[]{Context.class});
                h = f.getMethod("getOAID", new Class[]{Context.class});
                i = f.getMethod("getVAID", new Class[]{Context.class});
                j = f.getMethod("getAAID", new Class[]{Context.class});
            } catch (Throwable e) {

            }
        }

        a(Context context) {
            this.a = a(context, g);
            this.b = a(context, h);
            this.c = a(context, i);
            this.d = a(context, j);
        }

        static boolean a() {
            return (f == null || e == null) ? false : true;
        }

        private static String a(Context context, Method method) {
            if (!(e == null || method == null)) {
                try {
                    Object invoke = method.invoke(e, new Object[]{context});
                    if (invoke != null) {
                        return (String) invoke;
                    }
                } catch (Throwable e) {

                }
            }
            return null;
        }
    }

    private static void getFormNewThread(final Context context, final String manu) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OaidCallback callback = new OaidCallback() {
                    @Override
                    public void onSuccuss(String oaid, boolean isOaidTrackLimited) {
                        SensitiveDataUtil.oaid = oaid;
                    }

                    @Override
                    public void onFail(String errMsg) {

                    }
                };
                if ("ASUS".equals(manu)) {
                    new ASUSDeviceHelper(context).getID(callback);
                } else if ("OPPO".equals(manu)) {
                    new OppoDeviceHelper(context).getID(callback);
                } else if ("ONEPLUS".equals(manu)) {
                    new OnePlusDeviceHelper(context).getID(callback);
                } else if ("ZTE".equals(manu) || "FERRMEOS".equals(manu) || "SSUI".equals(manu)) {
                    new ZTEDeviceHelper(context).getID(callback);
                } else if ("HUAWEI".equals(manu)) {
                    new OaidAidlUtil(context).getOaid(callback);
                } else if ("SAMSUNG".equals(manu)) {
                    new SamsungDeviceHelper(context).getSumsungID(callback);
                } else if ("LENOVO".equals(manu) || "MOTOLORA".equals(manu)) {
                    new LenovoDeviceHelper(context).getIdRun(callback);
                } else if ("MEIZU".equals(manu)) {
                    new MeizuDeviceHelper(context).getMeizuID(callback);
                }
            }
        }).start();
    }
}
