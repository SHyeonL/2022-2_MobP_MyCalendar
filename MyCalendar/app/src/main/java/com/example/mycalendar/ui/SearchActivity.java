package com.example.mycalendar.ui;

import static com.example.mycalendar.DiaryDatabase.DATABASE_NAME;
import static com.example.mycalendar.DiaryDatabase.TABLE_CONTACT_INFO;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;
import com.example.mycalendar.R;

public class SearchActivity extends AppCompatActivity {
    TextView textView8;
    SQLiteDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        textView8 = findViewById(R.id.textView);

        openDatabase(DATABASE_NAME);//데이터베이스 이름 설정
        createTable(TABLE_CONTACT_INFO);//테이블 이름설정
        selectData(TABLE_CONTACT_INFO); //테이블설정
    }


    public void openDatabase(String DATABASE_NAME){
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null) ; //보안때문에 요즘은 대부분 PRIVATE 사용,
        // SQLiteDatabase 객체가 반환됨
        if(database !=null){
           //println("데이터베이스 오픈됨");
        }
    }

    public void createTable(String TABLE_CONTACT_INFO){
        if(database!= null) {
            String sql = "create table if not exists " + TABLE_CONTACT_INFO + "(_id integer PRIMARY KEY autoincrement, name text, mobile text)";
            database.execSQL(sql);
        }else {
            println("데이터베이스를 먼저 오픈하세요");
        }
    }

    public  void selectData(String TABLE_CONTACT_INFO){
        if(database != null){
            String sql = "select name, number from "+TABLE_CONTACT_INFO;
            Cursor cursor = database.rawQuery(sql, null);
            for( int i = 0; i< cursor.getCount(); i++){
                cursor.moveToNext();//다음 레코드로 넘어간다.
                String name = cursor.getString(0);
               String number = cursor.getString(1);
                println("" +  name + ", " + number );
            }
            cursor.close();
        }
        if(database==null){
            println("db 비어 있어");
        }
    }
    public void println(String data){textView8.append(data + "\n");}



}