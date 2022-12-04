package com.example.mycalendar.ui.Calendar;

public class toDoItem {
    String title;
    String date;
    String content;

    public toDoItem(String title, String date, String content) {
        this.title = title;
        this.date = date;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getContent() { return content; }

    public void SetTitle(String title) {
        this.title = title;
    }

    public void SetDate(String date) {
        this.date = date;
    }

    public void SetContent(String content) { this.content = content; }
}
