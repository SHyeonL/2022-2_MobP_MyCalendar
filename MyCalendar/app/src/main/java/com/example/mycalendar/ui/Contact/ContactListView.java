package com.example.mycalendar.ui.Contact;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mycalendar.R;

public class ContactListView extends LinearLayout {
    TextView textName;
    TextView textPhoneNum;

    public ContactListView(Context context) {
        super(context);
        init(context);
    }

    public ContactListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.contact_list, this, true);

        textName = (TextView) findViewById(R.id.textName);
        textPhoneNum = (TextView) findViewById(R.id.textPhoneNum);
    }

    public void setTextName(String name) {
        textName.setText(name);
    }

    public void setTextPhoneNum(String phoneNum) {
        textPhoneNum.setText(phoneNum);
    }
}
