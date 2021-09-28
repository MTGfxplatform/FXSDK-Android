package com.mintegral.detailroi.common.db;

public class EventDao extends BaseDao{

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

    static public class Table {
        public static final String TABLE_NAME = "EventDao";
        public static final String TABLE_CREATE_SQL = "CREATE TABLE IF NOT EXISTS ";

    }
}
