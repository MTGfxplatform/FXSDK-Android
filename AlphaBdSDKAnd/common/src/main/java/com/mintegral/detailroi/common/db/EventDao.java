package com.mintegral.detailroi.common.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.TextUtils;


import com.mintegral.detailroi.common.base.utils.SameLogTool;
import com.mintegral.detailroi.common.bean.EventBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventDao extends BaseDao{

    private String tag = "EventDao";
        private static EventDao instance;
        private EventDao(CommonAbsDBHelper helper) {
            super(helper);
        }

        public static EventDao getInstance(CommonAbsDBHelper helper) {
            if (instance == null) {
                synchronized (EventDao.class){
                    if (instance == null) {
                        instance = new EventDao(helper);
                    }
                }
            }
            return instance;
        }

        public void updateReportStateByEventId(String eventId,int state){
            if(getWritableDatabase() == null){
                return;
            }
            ContentValues contentValues = new ContentValues();
            contentValues.put(Table.E_REPORT_STATE,state);
            getWritableDatabase().update(Table.TABLE_NAME,contentValues,Table.E_EVENT_ID+ " = ?",new String[]{eventId});
        }
        public int queryReportStateByEventId(String eventId){
            int result = 0;
            if(getReadableDatabase() == null){
                return result;
            }
            Cursor cursor = null;
            try {
                cursor = getReadableDatabase().query(Table.TABLE_NAME, new String[]{Table.E_EVENT_ID}, Table.E_EVENT_ID+" = ?", new String[]{eventId}, null, null, null);

                if(cursor != null && !cursor.isClosed() && cursor.getCount() >0 ){
                    result = cursor.getInt(cursor.getColumnIndex(Table.E_REPORT_STATE));
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(cursor != null && !cursor.isClosed()){
                    cursor.close();
                }
            }
                return result;
            }

        public JSONArray queryAllEvent(){
            JSONArray jsonArray = null;
            if(getReadableDatabase() == null){
                return jsonArray;
            }
            Cursor cursor = null;
            try {
                cursor = getReadableDatabase().query(Table.TABLE_NAME,null,Table.E_REPORT_STATE+" = ?", new String[]{EventBean.REPORT_STATE_FAILED+""},null,null,null,"100");
                jsonArray = new JSONArray();
                if(cursor != null && !cursor.isClosed() && cursor.getCount() >0 ){
                    while (cursor.moveToNext()){
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put(Table.EVENT_NAME,cursor.getString(cursor.getColumnIndex(Table.EVENT_NAME)));
                        jsonObject.put(Table.E_SESSION_ID,cursor.getString(cursor.getColumnIndex(Table.E_SESSION_ID)));
                        jsonObject.put(Table.E_EVENT_ID,cursor.getString(cursor.getColumnIndex(Table.E_EVENT_ID)));
                        jsonObject.put(Table.E_PAGE_NAME,cursor.getString(cursor.getColumnIndex(Table.E_PAGE_NAME)));
                        jsonObject.put(Table.E_PAGE_TITLE,cursor.getString(cursor.getColumnIndex(Table.E_PAGE_TITLE)));
                        jsonObject.put(Table.E_REFERER_PAGE_NAME,cursor.getString(cursor.getColumnIndex(Table.E_REFERER_PAGE_NAME)));
                        jsonObject.put(Table.E_REFERER_PAGE_TITLE,cursor.getString(cursor.getColumnIndex(Table.E_REFERER_PAGE_TITLE)));
                        try {
                            String ext =cursor.getString(cursor.getColumnIndex(Table.E_EXT_PARAMS));
                            if (!TextUtils.isEmpty(ext)) {
                                JSONObject jsonObject1 = new JSONObject(ext);
                                jsonObject.put(Table.E_EXT_PARAMS,jsonObject1);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        jsonObject.put(Table.E_DURATION,cursor.getLong(cursor.getColumnIndex(Table.E_DURATION)));
                        jsonObject.put(Table.E_LOG_COUNT,cursor.getInt(cursor.getColumnIndex(Table.E_LOG_COUNT)));
                        jsonObject.put(Table.E_TIME,cursor.getLong(cursor.getColumnIndex(Table.E_TIME)));
                        jsonObject.put(Table.E_FX_ID,cursor.getString(cursor.getColumnIndex(Table.E_FX_ID)));
                        jsonObject.put(Table.E_SDK_VERSION,cursor.getString(cursor.getColumnIndex(Table.E_SDK_VERSION)));
                        jsonObject.put(Table.E_DEVICE_ID,cursor.getString(cursor.getColumnIndex(Table.E_DEVICE_ID)));
                        jsonObject.put(Table.E_APP_ID,cursor.getString(cursor.getColumnIndex(Table.E_APP_ID)));
                        jsonObject.put(Table.E_APP_NAME,cursor.getString(cursor.getColumnIndex(Table.E_APP_NAME)));
                        jsonObject.put(Table.E_PACKAGE_NAME,cursor.getString(cursor.getColumnIndex(Table.E_PACKAGE_NAME)));
                        jsonObject.put(Table.E_PLATFORM,cursor.getInt(cursor.getColumnIndex(Table.E_PLATFORM)));
                        jsonObject.put(Table.E_OS_VERSION,cursor.getString(cursor.getColumnIndex(Table.E_OS_VERSION)));
                        jsonObject.put(Table.E_UA,cursor.getString(cursor.getColumnIndex(Table.E_UA)));
                        jsonObject.put(Table.E_APP_VERSION,cursor.getString(cursor.getColumnIndex(Table.E_APP_VERSION)));
                        jsonObject.put(Table.E_APP_VERSION_CODE,cursor.getInt(cursor.getColumnIndex(Table.E_APP_VERSION_CODE)));
                        jsonObject.put(Table.E_BRAND,cursor.getString(cursor.getColumnIndex(Table.E_BRAND)));
                        jsonObject.put(Table.E_MODEL,cursor.getString(cursor.getColumnIndex(Table.E_MODEL)));
                        jsonObject.put(Table.E_LANGUAGE,cursor.getString(cursor.getColumnIndex(Table.E_LANGUAGE)));
                        jsonObject.put(Table.E_TIME_ZONE,cursor.getString(cursor.getColumnIndex(Table.E_TIME_ZONE)));
                        jsonObject.put(Table.E_SCREEN_SIZE,cursor.getString(cursor.getColumnIndex(Table.E_SCREEN_SIZE)));
                        jsonObject.put(Table.E_CHANNEL,cursor.getString(cursor.getColumnIndex(Table.E_CHANNEL)));
                        jsonObject.put(Table.E_NETWORK_TYPE,cursor.getString(cursor.getColumnIndex(Table.E_NETWORK_TYPE)));
                        jsonObject.put(Table.E_NETWORK_TYPE_STR,cursor.getString(cursor.getColumnIndex(Table.E_NETWORK_TYPE_STR)));
                        jsonObject.put(Table.E_IS_FIRST_DAY,cursor.getInt(cursor.getColumnIndex(Table.E_IS_FIRST_DAY)));
                        jsonArray.put(jsonObject);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(cursor != null && !cursor.isClosed()){
                    cursor.close();
                }
            }
            return jsonArray;
        }

        public long deleteRecordByEventId(String eventId){
            if(getWritableDatabase() == null){
                return -1;
            }
            String where = Table.E_EVENT_ID + " = '"+eventId+"'";
            return getWritableDatabase().delete(Table.TABLE_NAME,where,null);
        }

        public long insert(JSONObject jsonObject){
            try {
                if (jsonObject == null) {
                    return 0;
                }
                if (getWritableDatabase() == null) {
                    return -1;
                }
                ContentValues cv = new ContentValues();
                cv.put(Table.EVENT_NAME,jsonObject.optString(Table.EVENT_NAME));
                cv.put(Table.E_SESSION_ID,jsonObject.optString(Table.E_SESSION_ID));
                cv.put(Table.E_EVENT_ID,jsonObject.optString(Table.E_EVENT_ID));
                cv.put(Table.E_PAGE_NAME,jsonObject.optString(Table.E_PAGE_NAME));
                cv.put(Table.E_PAGE_TITLE,jsonObject.optString(Table.E_PAGE_TITLE));
                cv.put(Table.E_REFERER_PAGE_NAME,jsonObject.optString(Table.E_REFERER_PAGE_NAME));
                cv.put(Table.E_REFERER_PAGE_TITLE,jsonObject.optString(Table.E_REFERER_PAGE_TITLE));
                cv.put(Table.E_EXT_PARAMS,jsonObject.optString(Table.E_EXT_PARAMS));

                cv.put(Table.E_FX_ID,jsonObject.optString(Table.E_FX_ID));
                cv.put(Table.E_SDK_VERSION,jsonObject.optString(Table.E_SDK_VERSION));
                cv.put(Table.E_DEVICE_ID,jsonObject.optString(Table.E_DEVICE_ID));
                cv.put(Table.E_APP_ID,jsonObject.optString(Table.E_APP_ID));
                cv.put(Table.E_APP_NAME,jsonObject.optString(Table.E_APP_NAME));
                cv.put(Table.E_PACKAGE_NAME,jsonObject.optString(Table.E_PACKAGE_NAME));
                cv.put(Table.E_PLATFORM,jsonObject.optInt(Table.E_PLATFORM));
                cv.put(Table.E_OS_VERSION,jsonObject.optString(Table.E_OS_VERSION));
                cv.put(Table.E_UA,jsonObject.optString(Table.E_UA));
                cv.put(Table.E_BRAND,jsonObject.optString(Table.E_BRAND));
                cv.put(Table.E_MODEL,jsonObject.optString(Table.E_MODEL));
                cv.put(Table.E_LANGUAGE,jsonObject.optString(Table.E_LANGUAGE));
                cv.put(Table.E_TIME_ZONE,jsonObject.optString(Table.E_TIME_ZONE));
                cv.put(Table.E_SCREEN_SIZE,jsonObject.optString(Table.E_SCREEN_SIZE));
                cv.put(Table.E_CHANNEL,jsonObject.optString(Table.E_CHANNEL));
                cv.put(Table.E_NETWORK_TYPE,jsonObject.optString(Table.E_NETWORK_TYPE));
                cv.put(Table.E_NETWORK_TYPE_STR,jsonObject.optString(Table.E_NETWORK_TYPE_STR));

                cv.put(Table.E_TIME,jsonObject.optLong(Table.E_TIME));
                cv.put(Table.E_APP_VERSION,jsonObject.optLong(Table.E_APP_VERSION));
                cv.put(Table.E_APP_VERSION_CODE,jsonObject.optInt(Table.E_APP_VERSION_CODE));
                cv.put(Table.E_IS_FIRST_DAY,jsonObject.optInt(Table.E_IS_FIRST_DAY));
                cv.put(Table.E_DURATION,jsonObject.optLong(Table.E_DURATION));
                cv.put(Table.E_LOG_COUNT,jsonObject.optInt(Table.E_LOG_COUNT));
                int s = queryReportStateByEventId(jsonObject.optString(Table.E_EVENT_ID));
                if(s == 0){
                    cv.put(Table.E_REPORT_STATE,0);
                }else {
                    SameLogTool.e(tag,"event exist");
                    return -1;
                }
                return getWritableDatabase().insert(Table.TABLE_NAME, null, cv);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return -1;
        }

    static public class Table {
        public static String EVENT_NAME = "event";
        public static String E_SESSION_ID="session_id";
        public static String E_EVENT_ID = "event_id";
        public static String E_PAGE_TITLE = "page_title";
        public static String E_PAGE_NAME = "page_name";
        public static String E_REFERER_PAGE_TITLE = "referer_page_title";
        public static String E_REFERER_PAGE_NAME = "referer_page_name";
        public static String E_EXT_PARAMS = "ext_params";

        public static String E_FX_ID = "fx_id";
        public static String E_SDK_VERSION = "sdk_version";
        public static String E_DEVICE_ID  ="device_id";
        public static String E_APP_ID  ="app_id";
        public static String E_APP_NAME  ="app_name";
        public static String E_PACKAGE_NAME  ="package_name";
        public static String E_OS_VERSION  ="os_version";
        public static String E_UA  ="ua";
        public static String E_APP_VERSION ="app_version";
        public static String E_BRAND  ="brand";
        public static String E_MODEL  ="model";
        public static String E_LANGUAGE  ="language";
        public static String E_TIME_ZONE  ="timezone";
        public static String E_SCREEN_SIZE  ="screen_size";
        public static String E_CHANNEL  ="channel";
        public static String E_NETWORK_TYPE  ="network_type";
        public static String E_NETWORK_TYPE_STR  ="network_str";


        //下面都是数字型
        public static String E_APP_VERSION_CODE  ="app_version_code";
        public static String E_PLATFORM  ="platform";
        public static String E_IS_FIRST_DAY  ="is_first_day";
        public static String E_TIME ="time";
        public static String E_DURATION = "duration";
        public static String E_LOG_COUNT = "log_count";
        public static String E_REPORT_STATE = "report_state";





        public static final String TABLE_NAME = "EventDao";
        public static final String TABLE_CREATE_SQL = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + E_FX_ID +" TEXT,"
                + E_SDK_VERSION +" TEXT,"
                + E_DEVICE_ID +" TEXT,"
                + E_APP_ID +" TEXT,"
                + E_APP_NAME +" TEXT,"
                + E_PACKAGE_NAME +" TEXT,"
                + E_OS_VERSION +" TEXT,"
                + E_UA +" TEXT,"
                + E_APP_VERSION +" TEXT,"
                + E_BRAND +" TEXT,"
                + E_MODEL +" TEXT,"
                + E_LANGUAGE +" TEXT,"
                + E_TIME_ZONE +" TEXT,"
                + E_SCREEN_SIZE +" TEXT,"
                + E_CHANNEL +" TEXT,"
                + E_NETWORK_TYPE +" TEXT,"
                + E_NETWORK_TYPE_STR +" TEXT,"
                + EVENT_NAME +" TEXT,"
                + E_SESSION_ID +" TEXT,"
                + E_EVENT_ID +" TEXT,"
                + E_PAGE_NAME +" TEXT,"
                + E_PAGE_TITLE +" TEXT,"
                + E_REFERER_PAGE_NAME +" TEXT,"
                + E_REFERER_PAGE_TITLE +" TEXT,"
                + E_EXT_PARAMS +" TEXT,"
                + E_REPORT_STATE +" TEXT,"


                + E_APP_VERSION_CODE +" INTEGER,"
                + E_PLATFORM +" INTEGER,"
                + E_IS_FIRST_DAY + " INTEGER,"
                + E_DURATION +" INTEGER,"
                + E_LOG_COUNT +" INTEGER,"
                + E_TIME + " INTEGER"
                + " )";

    }
}
