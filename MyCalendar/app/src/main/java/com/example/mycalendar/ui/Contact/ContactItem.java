package com.example.mycalendar.ui.Contact;

public class ContactItem {
    private String id;
    private String name;
    private String phoneNum;

    public ContactItem(String id, String name, String phoneNum) {
        this.id = id;
        this.name = name;
        this.phoneNum = phoneNum;
    }
    public String getId() { return id; }

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