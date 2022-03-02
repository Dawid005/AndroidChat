package com.example.konspekt9_3;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.POST;

public interface JsonPlaceholderAPInewComment {

    @POST("message")
    Call<MessageNewComment> createMessage(@Body MessageNewComment messageNewComment);
}
