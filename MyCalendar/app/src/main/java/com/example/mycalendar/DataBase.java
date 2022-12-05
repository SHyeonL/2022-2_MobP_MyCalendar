package com.example.mycalendar;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mycalendar.ui.Contact.ContactListView;
import com.example.mycalendar.ui.Contact.DetailContactViewActivity;
import com.example.mycalendar.ui.home.HomeFragment;

import java.util.ArrayList;

public class DataBase {
    public static String DATABASE_NAME = "diary.db";
    public static String TABLE_DIARY_INFO = "DIARY_INFO";
    public static String TABLE_CONTACT_INFO = "CONTACT_INFO";

    HomeFragment homeFragment;
    SQLiteDatabase database;

    public void insertContactRecord(String name, String number) {
        database.execSQL("insert into " + TABLE_CONTACT_INFO + "(name, number) values ('" + name + "', '" + number + "');");
    }

    public void insertDiaryRecord(String name, String date, String title, String content) {
        database.execSQL("insert into " + TABLE_DIARY_INFO + "(CREATE_DATE, SUBJECT, CONTENTS) values ('" + date + "', '" + title + "', '" + content + "');");
    }

    public void openDatabase(Context context, String DATABASE_NAME) {
        database = context.openOrCreateDatabase(DATABASE_NAME, context.MODE_PRIVATE, null); //보안때문에 요즘은 대부분 PRIVATE 사용,
        // SQLiteDatabase 객체가 반환됨
        if (database != null) {
            Log.d("open", "데이터베이스 오픈");
        }
    }

    public void createContactTable(String name) {
        if (database != null) {
            String sql = "create table if not exists " + name + "(_id integer PRIMARY KEY autoincrement, name text, mobile text)";
            database.execSQL(sql);
            Log.d("open", "테이블 오픈");
        } else {
            Log.d("테스트", "테이블 오픈되지 않음");
        }
    }

    public void createDiaryTable(String name) {
        if (database != null) {
            String sql = "create table if not exists " + name + "(_id integer PRIMARY KEY autoincrement, CREATE_DATE text, subject text, content text)";
            database.execSQL(sql);
            Log.d("open", "테이블 오픈");
        } else {
            Log.d("테스트", "테이블 오픈되지 않음");
        }
    }

    public void DeleteDiaryRecord() {

    }

    public boolean deltest(String id) {
        database.execSQL("DELETE FROM CONTACT_INFO WHERE _id = "+id);
        return false;
    }


    public ArrayList getDiaryInfo() {
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res = database.rawQuery("select * from " + TABLE_DIARY_INFO + " ORDER BY CREATE_DATE", null);
        res.moveToFirst();

        while(res.isAfterLast() == false) {
            array_list.add(res.getString(res.getColumnIndex("_id")));
            array_list.add(res.getString(res.getColumnIndex("CREATE_DATE")));
            array_list.add(res.getString(res.getColumnIndex("SUBJECT")));
            array_list.add(res.getString(res.getColumnIndex("CONTENTS")));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList getContactInfo() {
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res = database.rawQuery("select * from " + TABLE_CONTACT_INFO + " ORDER BY NAME", null);
        res.moveToFirst();

        while(res.isAfterLast() == false) {
            array_list.add(res.getString(0));
            array_list.add(res.getString(1));
            array_list.add(res.getString(2));
            res.moveToNext();
        }
        return array_list;
    }

    public void selectData(String TABLE_CONTACT_INFO) {
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
