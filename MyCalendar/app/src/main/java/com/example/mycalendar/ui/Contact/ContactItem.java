package com.example.mycalendar.ui.Contact;

public class ContactItem {
    String name;
    String phoneNum;

    public ContactItem(String name, String phoneNum) {
        this.name = name;
        this.phoneNum = phoneNum;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void SetName(String name) {
        this.name = name;
    }

    public void SetPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}