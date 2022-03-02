package com.example.konspekt9_3;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;

public interface JsonPlaceholderAPI {

    @GET("messages")
    Call<List<Message>> getMessages();
}
