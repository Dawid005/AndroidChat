package com.example.konspekt9_3;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceholderAPIeditComment {

    @PUT("message/{id}")
    Call<MessageNewComment> editMessage(@Path("id") String id, @Field("content") String content, @Field("login") String login);
}
