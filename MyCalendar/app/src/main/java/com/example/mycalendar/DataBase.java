package com.example.mycalendar;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mycalendar.ui.Calendar.toDoItem;
import com.example.mycalendar.ui.Contact.ContactItem;
import com.example.mycalendar.ui.home.HomeFragment;

import java.util.ArrayList;

public class DataBase {
    public static String DATABASE_NAME = "diary.db";
    public static String TABLE_DIARY_INFO = "DIARY_INFO";
    public static String TABLE_CONTACT_INFO = "CONTACT_INFO";

    HomeFragment homeFragment;
    SQLiteDatabase database;

    public void insertContactRecord(String name, String number) {
        database.execSQL("insert into " + TABLE_CONTACT_INFO + "(NAME, NUMBER) values ('" + name + "', '" + number + "');");
    }

    public void insertDiaryRecord(String name, String date, String title, String content) {
        database.execSQL("insert into " + TABLE_DIARY_INFO + "(CREATE_DATE, SUBJECT, CONTENT) values ('" + date + "', '" + title + "', '" + content + "');");
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
            String sql = "create table if not exists " + name + "(_id integer PRIMARY KEY autoincrement, NAME text, NUMBER text)";
            database.execSQL(sql);
            Log.d("open", "테이블 오픈");
        } else {
            Log.d("테스트", "테이블 오픈되지 않음");
        }
    }

    public void createDiaryTable(String name) {
        if (database != null) {
            String sql = "create table if not exists " + name + "(_id integer PRIMARY KEY autoincrement, CREATE_DATE text, SUBJECT text, CONTENT text)";
            database.execSQL(sql);
            Log.d("open", "테이블 오픈");
        } else {
            Log.d("테스트", "테이블 오픈되지 않음");
        }
    }

    public ArrayList searchContactRecord(String name) {
        String[] arr = {"%" + name + "%"};
        Cursor res = database.rawQuery("SELECT * FROM " + TABLE_CONTACT_INFO + " WHERE NAME LIKE ?", arr);
        ArrayList<ContactItem> list = new ArrayList<ContactItem>();
        res.moveToFirst();

        while (res.isAfterLast() == false) {

            String id = res.getString(0);
            String name2 = res.getString(1);
            String number = res.getString(2);

            ContactItem vo = new ContactItem(id, name2, number);
            list.add(vo);
            res.moveToNext();
        }
        return list;
    }

    public void updateDiaryRecord(String id, String subject, String content) {
        database.execSQL("UPDATE DIARY_INFO SET SUBJECT='" + subject + "', CONTENT='" + content + "' WHERE _id=" + id);
    }

    public void updateContactRecord(String id, String name, String number) {
        database.execSQL("UPDATE CONTACT_INFO SET NAME='" + name + "', NUMBER='" + number + "' WHERE _id=" + id);
    }

    public ArrayList searchDiaryRecord(String subject) {
        String[] arr = {"%" + subject + "%"};
        Cursor res = database.rawQuery("SELECT * FROM " + TABLE_DIARY_INFO + " WHERE SUBJECT LIKE ?", arr);
        ArrayList<toDoItem> list = new ArrayList<toDoItem>();
        res.moveToFirst();

        while (res.isAfterLast() == false) {

            String id = res.getString(0);
            String date = res.getString(1);
            String subject2 = res.getString(2);
            String contents = res.getString(3);

            toDoItem vo = new toDoItem(id, date, subject2, contents);
            list.add(vo);
            res.moveToNext();
        }
        return list;
    }

    /////////////////
    public boolean deleteContactById(String id) {
        database.execSQL("DELETE FROM CONTACT_INFO WHERE _id = " + id);
        return false;
    }

    public boolean deleteDiaryById(String id) {
        database.execSQL("DELETE FROM DIARY_INFO WHERE _id = " + id);
        return false;
    }
//////////

    public ArrayList getDiaryInfo() {
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res = database.rawQuery("select * from " + TABLE_DIARY_INFO + " ORDER BY CREATE_DATE", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(0));
            array_list.add(res.getString(1));
            array_list.add(res.getString(2));
            array_list.add(res.getString(3));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList getContactInfo() {
        ArrayList<String> array_list = new ArrayList<String>();
        Cursor res = database.rawQuery("select * from " + TABLE_CONTACT_INFO + " ORDER BY NAME", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(res.getString(0));
            array_list.add(res.getString(1));
            array_list.add(res.getString(2));
            res.moveToNext();
        }
        return array_list;
    }

//    public void selectData(String TABLE_CONTACT_INFO) {
//        if (database != null) {
//            String sql = "select name, number from " + TABLE_CONTACT_INFO;
//            Cursor cursor = database.rawQuery(sql, null);
//            for (int i = 0; i < cursor.getCount(); i++) {
//                cursor.moveToNext();//다음 레코드로 넘어간다.
//                String name = cursor.getString(0);
//                String number = cursor.getString(1);
//                Log.d("데이터 name", name);
//                Log.d("데이터 number", number);
//                Log.d("open", "데이터 오픈");
//            }
//            cursor.close();
//        }
//        if (database == null) {
//            Log.d("테스트", "db 비어 있음");
//        }
//    }

}
