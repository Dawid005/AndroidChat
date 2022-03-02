package com.example.konspekt9_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateNewComment extends AppCompatActivity {

    protected void newComment(){
        EditText editTextnick = findViewById(R.id.nick);
        EditText editTextContent = findViewById(R.id.content);

        String nick = editTextnick.getText().toString();
        String content = editTextContent.getText().toString();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tgryl.pl/shoutbox/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceholderAPInewComment jsonPlaceholderAPI = retrofit.create(
                JsonPlaceholderAPInewComment.class);

        MessageNewComment messageNewComment = new MessageNewComment(content,nick);

        Call<MessageNewComment> call = jsonPlaceholderAPI.createMessage(messageNewComment);

        call.enqueue(new Callback<MessageNewComment>() {
            @Override
            public void onResponse(Call<MessageNewComment> call, Response<MessageNewComment> response) {
                Intent intent = new Intent(CreateNewComment.this, GalleryAction.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<MessageNewComment> call, Throwable t) {
                Toast.makeText(CreateNewComment.this, "Error", Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_comment);

        Button buttonNewComment = findViewById(R.id.buttonNewComment);

        buttonNewComment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                newComment();
            }
        });

    }
}