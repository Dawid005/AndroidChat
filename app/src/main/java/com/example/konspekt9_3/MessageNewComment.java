package com.example.konspekt9_3;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MessageNewComment {

    private String content;

    @SerializedName("login")
    private String login;

    public String getContent() {
        return content;
    }

    public String getText() {
        return login;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public MessageNewComment(String content, String login){
        this.content = content;
        this.login = login;
    }
}
