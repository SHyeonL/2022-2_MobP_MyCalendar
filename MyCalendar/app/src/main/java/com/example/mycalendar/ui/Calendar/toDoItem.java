package com.example.mycalendar.ui.Calendar;

public class toDoItem {
    String title;
    String date;

    public toDoItem(String title, String date) {
        this.title = title;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public void SetTitle(String title) {
        this.title = title;
    }

    public void SetDate(String date) {
        this.date = date;
    }
}
