package com.example.niklas.backendtagebuch.model;

import java.io.Serializable;
import java.util.Calendar;

public class Entry implements Serializable{
    private String title;
    private Calendar date;
    private String content;
    private long id;

    public Entry (){
        this(null,null,null);
    }
    public Entry(String title){
            this(title,null, null);

        }

    public Entry(String title, Calendar date, String content) {
        this.title = title;
        this.date = date;
        this.content = content;
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

    public long getId(){
        return this.id;
    }

    public void setId(long ID){ this.id = ID;}

    public String getContent() {return content; }

    public void setContent(String content) {
        this.content = content;
    }
}