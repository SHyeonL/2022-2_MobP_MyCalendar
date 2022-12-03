//package com.example.mycalendar;
//
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;
//
//public class DiaryDatabase {
////    private static final String TAG = "DiaryDatabase";
////
////    /**
////     * Singleton instance
////     */
////    public static DiaryDatabase database;
////
////
////    /**
////     * database name
////     */
////    public static String DATABASE_NAME = "database.db";
////
////    /**
////     * table name for diary_INFO
////     */
////    public static String TABLE_DIARY_INFO = "DIARY_INFO";
////    public static String TABLE_CONTACT_INFO = "CONTACT_INFO";
////
////    /**
////     * version
////     */
////    public static int DATABASE_VERSION = 1;
////
////    /**
////     * Helper class defined
////     */
////    private DatabaseHelper dbHelper;
//
//    /**
//     * Database object
//     */
//    private SQLiteDatabase db;
//    private Context context;
//
//    /**
//     * Constructor
//     */
//    private DiaryDatabase(Context context) {
//        this.context = context;
//    }
//
//
//    public static DiaryDatabase getInstance(Context context) {
//        if (database == null) {
//            database = new DiaryDatabase(context);
//        }
//        return database;
//    }
//
//    /**
//     * open database
//     */
//    public boolean open() {
//        println("opening database [" + DATABASE_NAME + "].");
//        dbHelper = new DatabaseHelper(context);
//        db = dbHelper.getWritableDatabase();
//        return true;
//    }
//
//    /**
//     * close database
//     */
//    public void close() {
//        println("closing database [" + DATABASE_NAME + "].");
//        db.close();
//        database = null;
//    }
//
//    /**
//     * execute raw query using the input SQL
//     * close the cursor after fetching any result
//     */
//    public Cursor rawQuery(String SQL, Object o) {
//        println("\nexecuteQuery called.\n");
//
//        Cursor c1 = null;
//        try {
//            c1 = db.rawQuery(SQL, null);
//            println("cursor count : " + c1.getCount());
//        } catch(Exception ex) {
//            Log.e(TAG, "Exception in executeQuery", ex);
//        }
//
//        return c1;
//    }
//
//    public boolean execSQL(String SQL) {
//        println("\nexecute called.\n");
//
//        try {
//            Log.d(TAG, "SQL : " + SQL);
//            db.execSQL(SQL);
//        } catch(Exception ex) {
//            Log.e(TAG, "Exception in executeQuery", ex);
//            return false;
//        }
//
//        return true;
//    }
//
//    private class DatabaseHelper extends SQLiteOpenHelper {
//        public DatabaseHelper(Context context) {
//            super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        }
//
//
//        public void onCreate(SQLiteDatabase _db) {
//            // TABLE_DIARY_INFO
//            println("creating table [" + TABLE_DIARY_INFO + "].");
//            // drop existing table
//            String DROP_SQL = "drop table if exists " + TABLE_DIARY_INFO;
//            try {
//                _db.execSQL(DROP_SQL);
//            } catch(Exception ex) {
//                Log.e(TAG, "Exception in DROP_SQL", ex);
//            }
//
//            // TABLE_CONTACT_INFO
//            println("creating table [" + TABLE_CONTACT_INFO + "].");
//            // drop existing table
//            String DROP_SQL2 = "drop table if exists " + TABLE_CONTACT_INFO;
//            try {
//                _db.execSQL(DROP_SQL2);
//            } catch(Exception ex) {
//                Log.e(TAG, "Exception in DROP_SQL", ex);
//            }
//
//            // create table
//            String CREATE_SQL = "create table " + TABLE_DIARY_INFO + "("
//                    + "  _id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, "
//                    + "  CREATE_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
//                    + "  SUBJECT TEXT, "
//                    + "  CONTENTS TEXT "
//                    + ")";
//            try {
//                _db.execSQL(CREATE_SQL);
//            } catch(Exception ex) {
//                Log.e(TAG, "Exception in CREATE_SQL", ex);
//            }
//
//            // create table2
//            String CREATE_SQL2 = "create table " + TABLE_CONTACT_INFO + "("
//                    + "  _id INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT, "
//                    + "  NAME TEXT, "
//                    + "  NUMBER TEXT "
//                    + ")";
//
//            try {
//                _db.execSQL(CREATE_SQL2);
//            } catch(Exception ex) {
//                Log.e(TAG, "Exception in CREATE_SQL", ex);
//            }
//
//        }
//
//        public void onOpen(SQLiteDatabase db) {
//            println("opened database [" + DATABASE_NAME + "].");
//
//        }
//
//        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            println("Upgrading database from version " + oldVersion + " to " + newVersion + ".");
//
//            if (oldVersion < 2) {   // version 1
//
//            }
//
//        }
//
//        private void insertRecord(SQLiteDatabase _db, String subject, String contents) {
//            try {
//                _db.execSQL( "insert into " + TABLE_DIARY_INFO + "(SUBJECT, CONTENTS) values ('" + subject + "', '" + contents + "');" );
//            } catch(Exception ex) {
//                Log.e(TAG, "Exception in executing insert SQL.", ex);
//            }
//        }
//
//        private void insertRecord2(SQLiteDatabase _db, String name, String number) {
//            try {
//                _db.execSQL( "insert into " + TABLE_CONTACT_INFO + "(NAME, NUMBER) values ('" + name + "', '" + number + "');" );
//            } catch(Exception ex) {
//                Log.e(TAG, "Exception in executing insert SQL.", ex);
//            }
//        }
//
//    }
//
//    public void insertRecord(String subject, String contents) {
//        try {
//            db.execSQL( "insert into " + TABLE_DIARY_INFO + "( SUBJECT, CONTENTS) values ('" + subject + "', '" + contents + "');" );
//        } catch(Exception ex) {
//            Log.e(TAG, "Exception in executing insert SQL.", ex);
//        }
//    }
//
//    public void insertRecord2(String name, String number) {
//        try {
//            db.execSQL( "insert into " + TABLE_CONTACT_INFO + "( NAME, NUMBER) values ('" + name + "', '" + number + "');" );
//        } catch(Exception ex) {
//            Log.e(TAG, "Exception in executing insert SQL.", ex);
//        }
//    }
//
//    private void println(String msg) {
//        Log.d(TAG, msg);
//    }
//
//    public void clearDatabase(String TABLE_NAME) {
//        String clearDBQuery = "DELETE FROM "+TABLE_NAME;
//        db.execSQL(clearDBQuery);
//    }
//}
