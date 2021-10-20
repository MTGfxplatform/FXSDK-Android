package com.fxmvp.detailroi.common.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * OR转换，最基本的CRUD Created by chenzhf on 2014/12/25.
 */
public class BaseDao<T> {

	protected CommonAbsDBHelper mHelper = null;

	public BaseDao(CommonAbsDBHelper helper) {
		mHelper = helper;
	}

	protected synchronized SQLiteDatabase getReadableDatabase() {
		return mHelper.getReadableDatabase();
	}

	protected synchronized SQLiteDatabase getWritableDatabase() {
		return mHelper.getWritableDatabase();
	}

}
