package com.example.mycalendar.ui.Calendar;

public class toDoItem {
    String id;
    String title;
    String date;
    String content;

    public toDoItem(String id, String title, String date, String content) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.content = content;
    }
    public String getId() { return id; }

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
