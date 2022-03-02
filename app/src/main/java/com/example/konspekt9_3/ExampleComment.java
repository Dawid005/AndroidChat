package com.example.konspekt9_3;

public class ExampleComment {

    private String content;
    private String login;
    private String date;
    private String id;
    private String exampleComment;

    public String getContent() {
        return content;
    }

    public String getLogin() {
        return login;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public ExampleComment(String content, String login, String date, String id){
        this.content = content;
        this.login = login;
        this.date = date;
        this.id = id;
    }

}
