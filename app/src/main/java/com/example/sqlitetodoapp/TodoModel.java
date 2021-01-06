package com.example.sqlitetodoapp;

public class TodoModel {
    private int id;
    private String title;
    private String content;
    public TodoModel(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public TodoModel(String title, String content) {
        this.id = -1;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
