package com.example.niklas.backendtagebuch.model;

import java.util.Calendar;

public class Entry {
    private String title;
    private Calendar date;

    public Entry(String title, Calendar date) {
        this.title = title;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}