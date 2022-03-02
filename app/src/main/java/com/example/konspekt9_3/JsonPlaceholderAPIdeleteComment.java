package com.example.konspekt9_3;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceholderAPIdeleteComment {

    @DELETE("message/{id}")
    Call<MessageNewComment> deleteMessage(@Path("id") String id);
}
