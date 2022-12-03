package com.example.mycalendar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataBase {
    SQLiteDatabase database;

    public int insertRecord(String name) {
        int count = 3;
        database.execSQL("insert into " + name + "(name, number) values ('John', '010-7788-1234');");
        database.execSQL("insert into " + name + "(name, number) values ('Mike', '010-8888-1111');");
        database.execSQL("insert into " + name + "(name, number) values ('Sean', '010-6677-4321');");

        return count;
    }

    public void openDatabase(Context context, String DATABASE_NAME) {
        database = context.openOrCreateDatabase(DATABASE_NAME, context.MODE_PRIVATE, null); //보안때문에 요즘은 대부분 PRIVATE 사용,
        Log.d("open", "데이터베이스 오픈");
        // SQLiteDatabase 객체가 반환됨
        if (database != null) {
            Log.d("open", "데이터베이스 존재하지 않음");
        }
    }

    public void createTable(String name) {
        if (database != null) {
            String sql = "create table if not exists " + name + "(_id integer PRIMARY KEY autoincrement, name text, mobile text)";
            database.execSQL(sql);
            Log.d("open", "테이블 오픈");
        } else {
            Log.d("테스트", "테이블 오픈되지 않음");
        }
    }

    public void selectData(String TABLE_CONTACT_INFO) {
        Log.d("open", "데이터 오픈되지는 않음");
        if (database != null) {
            String sql = "select name, number from " + TABLE_CONTACT_INFO;
            Cursor cursor = database.rawQuery(sql, null);
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToNext();//다음 레코드로 넘어간다.
                String name = cursor.getString(0);
                String number = cursor.getString(1);
                Log.d("데이터 name", name);
                Log.d("데이터 number", number);
                Log.d("open", "데이터 오픈");
            }
            cursor.close();
        }
        if (database == null) {
            Log.d("테스트", "db 비어 있음");
        }
    }
}
