package com.mintegral.detailroi.common.base.utils;

import static com.mintegral.detailroi.common.base.CommonConstant.DEBUG_STATE;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


public class SameLogTool {
    private static final String TAG_PREFIX = "fx_sdk_";

    public static boolean DBG_V = true;
    public static boolean DBG_D = true;
    public static boolean DBG_I = true;
    public static boolean DBG_W = true;
    public static boolean DBG_E = true;
    public static boolean DBG_TOAST = false;
    public static boolean DBG_THROWEXCETON = true;
    public static boolean DBG_LOG_E = false;

    static {
        if (!DEBUG_STATE) {
            DBG_V = false;
            DBG_D = false;
            DBG_I = false;
            DBG_W = false;
            DBG_E = false;
            DBG_TOAST = false;
            DBG_THROWEXCETON = false;
            DBG_LOG_E = false;
        }
    }

    private SameLogTool() {

    }

    /**
     * Send a VERBOSE log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void v(String tag, String msg) {
        if (DBG_V) {
            Log.v(revertTag(tag), msg);
        }
    }

    /**
     * Send a VERBOSE log message and log the exception.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void v(String tag, String msg, Throwable tr) {
        if (DBG_V) {
            Log.v(revertTag(tag), msg, tr);
        }
    }

    /**
     * Send a DEBUG log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void d(String tag, String msg) {
        if (DBG_D) {
            Log.d(revertTag(tag), msg);
        }
    }

    /**
     * Send a DEBUG log message and log the exception.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void d(String tag, String msg, Throwable tr) {
        if (DBG_D) {
            Log.d(revertTag(tag), msg, tr);
        }
    }

    /**
     * Send an INFO log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void i(String tag, String msg) {
        if (DBG_I) {
            Log.i(revertTag(tag), msg);
        }
    }

    /**
     * Send a INFO log message and log the exception.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void i(String tag, String msg, Throwable tr) {
        if (DBG_I) {
            Log.i(revertTag(tag), msg, tr);
        }
    }

    /**
     * Send a WARN log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void w(String tag, String msg) {
        if (DBG_W) {
            Log.w(revertTag(tag), msg);
        }
    }

    /**
     * Send a WARN log message and log the exception.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void w(String tag, String msg, Throwable tr) {
        if (DBG_W) {
            Log.w(revertTag(tag), msg, tr);
        }
    }

    /*
     * Send a {@link #WARN} log message and log the exception.
     *
     * @param tag Used to identify the source of a log message. It usually
     * identifies the class or activity where the log call occurs.
     *
     * @param tr An exception to log
     */
    public static void w(String tag, Throwable tr) {
        if (DBG_W) {
            Log.w(revertTag(tag), tr);
        }
    }

    /**
     * Send an ERROR log message.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void e(String tag, String msg) {
        if (DBG_E && msg != null) {
            Log.e(revertTag(tag), msg);
        }
    }

    /**
     * Send a ERROR log message and log the exception.
     *
     * @param tag Used to identify the source of a log message. It usually
     *            identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr  An exception to log
     */
    public static void e(String tag, String msg, Throwable tr) {
        if (DBG_E && msg != null && tr != null) {
            Log.e(revertTag(tag), msg, tr);
        }
    }

    public static void toast(Context ctx, String msg) {
        if (DBG_TOAST) {
            Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
        }
    }

    private static String revertTag(String tag) {
        if (!TextUtils.isEmpty(tag)) {
            tag = TAG_PREFIX + tag;
        }
        return tag;
    }
}
