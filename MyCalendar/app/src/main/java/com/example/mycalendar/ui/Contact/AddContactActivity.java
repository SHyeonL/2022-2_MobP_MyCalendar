package com.example.mycalendar.ui.Contact;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
//
//
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mycalendar.DataBase;
import com.example.mycalendar.R;

public class AddContactActivity extends AppCompatActivity {

    DataBase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
    }

}