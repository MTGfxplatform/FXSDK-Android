package com.fxmvp.detailroi.common.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.fxmvp.detailroi.common.base.CommonConstant;
import com.fxmvp.detailroi.common.base.SDKConfig;


/**
 * Created by chenzhf on 2014/12/29.
 */
public class CommonSDKDBHelper extends CommonAbsDBHelper {

    private static CommonSDKDBHelper mHelper;

    private CommonSDKDBHelper(Context context) {
        super(context);
    }

    public static CommonSDKDBHelper getInstance(Context context) {
        if (mHelper == null) {
            synchronized (CommonSDKDBHelper.class) {
                if (mHelper == null) {
                    mHelper = new CommonSDKDBHelper(context.getApplicationContext());
                }
            }
        }

        return mHelper;
    }

    @Override
    protected String getDBName() {
        return "mbridge.msdk.and.db";
    }

    @Override

    protected int getDBVersion() {
        return SDKConfig.SDK_DB_VERSION;
    }

    @Override
    protected void onCreateDB(SQLiteDatabase db) {
        createTable(db);
    }

    @Override
    protected void onUpdateDB(SQLiteDatabase db, int oldVersion, int newVersion) {
        renameTable(db);
        createTable(db);
        if(oldVersion < newVersion){
            transferData(db);
        }
        dropTableTemp(db);
        dropTable(db);
    }

    private void createTable(SQLiteDatabase db) {
        try {
            db.execSQL(EventDao.Table.TABLE_CREATE_SQL);

        } catch (Exception e) {
            if (CommonConstant.DEBUG_STATE) {
                e.printStackTrace();
            }
        }
    }

    private void renameTable(SQLiteDatabase db){
        try {
            String tempTableName = EventDao.Table.TABLE_NAME + "_temp";
            db.execSQL("ALTER TABLE  '" + EventDao.Table.TABLE_NAME + "'"+" RENAME TO "+tempTableName);
        } catch (Exception e) {
            if (CommonConstant.DEBUG_STATE) {
                e.printStackTrace();
            }
        }
    }
    private void transferData(SQLiteDatabase db){
        try {
//            String tempTableName = EventDao.Table.TABLE_NAME + "_temp";
//            db.execSQL("INSERT INTO  '" + EventDao.Table.TABLE_NAME + "'"+" RENAME TO "+tempTableName);
        } catch (Exception e) {
            if (CommonConstant.DEBUG_STATE) {
                e.printStackTrace();
            }
        }
    }
    private void dropTable(SQLiteDatabase db) {
        try {
            db.execSQL("DROP TABLE IF EXISTS '" + EventDao.Table.TABLE_NAME + "'");
        } catch (Exception e) {
            if (CommonConstant.DEBUG_STATE) {
                e.printStackTrace();
            }
        }

    }
    private void dropTableTemp(SQLiteDatabase db) {
        try {
            String tempTableName = EventDao.Table.TABLE_NAME + "_temp";
            db.execSQL("DROP TABLE IF EXISTS '" + tempTableName+ "'");
        } catch (Exception e) {
            if (CommonConstant.DEBUG_STATE) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTable(db);
        createTable(db);
    }

}
