package com.mintegral.detailroi.common.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mintegral.detailroi.common.base.CommonConstant;
import com.mintegral.detailroi.common.base.utils.SameLogTool;


public abstract class CommonAbsDBHelper {


    private DatabaseOpenHelper mDatabaseOpenHelper;

    public CommonAbsDBHelper(Context context) {
        this.mDatabaseOpenHelper = new DatabaseOpenHelper(context, getDBName(), getDBVersion());
    }

    public SQLiteDatabase getReadableDatabase() {
        return mDatabaseOpenHelper.getReadableDatabase();
    }

    public synchronized SQLiteDatabase getWritableDatabase() {
        SQLiteDatabase database = null;
        try {
            database = mDatabaseOpenHelper.getWritableDatabase();
        } catch (Exception e) {

        }
        return database;
    }

    protected abstract String getDBName();

    protected abstract int getDBVersion();

    protected abstract void onCreateDB(SQLiteDatabase db);

    protected abstract void onUpdateDB(SQLiteDatabase db, int oldVersion, int newVersion);

    protected abstract void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion);

    private class DatabaseOpenHelper extends SQLiteOpenHelper {

        public DatabaseOpenHelper(Context context, String databaseName, int databaseVersion) {
            super(context, databaseName, null, databaseVersion);
            if (CommonConstant.DEBUG_STATE) {
                SameLogTool.d("DatabaseOpenHelper", "数据库： name :" + databaseName + "  " + databaseVersion);
            }
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            if (CommonConstant.DEBUG_STATE) {
                SameLogTool.d("DatabaseOpenHelper", "数据库创建了");
            }
            CommonAbsDBHelper.this.onCreateDB(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (CommonConstant.DEBUG_STATE) {
                SameLogTool.d("DatabaseOpenHelper", "数据库升级了");
            }
            CommonAbsDBHelper.this.onUpdateDB(db, oldVersion, newVersion);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            CommonAbsDBHelper.this.onDowngrade(db, oldVersion, newVersion);
        }


    }


}
