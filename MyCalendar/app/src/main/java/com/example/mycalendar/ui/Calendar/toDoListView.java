package com.example.mycalendar.ui.Calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mycalendar.R;

public class toDoListView extends LinearLayout {
    TextView textTitle;
    TextView textDate;

    public toDoListView(Context context) {
        super(context);
        init(context);
    }

    public toDoListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.todo_list, this, true);

        textTitle = (TextView) findViewById(R.id.textName);
        textDate = (TextView) findViewById(R.id.textPhoneNum);
    }

    public void setTitle(String title) {
        textTitle.setText(title);
    }

    public void setDate(String date) {
        textDate.setText(date);
    }
}

