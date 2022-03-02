package com.example.konspekt9_3;
import com.google.gson.annotations.SerializedName;

public class Message {

    private String content;
    private String login;
    private String date;

    @SerializedName("id")
    private String text;

    public String getContent() {
        return content;
    }

    public String getLogin() {
        return login;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }
}
