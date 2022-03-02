package com.example.konspekt9_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeleteCommentActivity extends AppCompatActivity {

    private ArrayList<ExampleComment> arrayList = new ArrayList<ExampleComment>();

    private int position;
    private String id;
    private boolean flagToDelete = true;

    protected void seeComents(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tgryl.pl/shoutbox/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceholderAPI jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI.class);
        Call<List<Message>> call = jsonPlaceholderAPI.getMessages();
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if(!response.isSuccessful()){
                    return;
                }
                List<Message> messages = response.body();
                arrayList.removeAll(arrayList);
                int i=0;
                for(Message message : messages){
                    i = i + 1;
                    if(messages.size()>22){
                        if(i>messages.size()-22) {
                            String content = "";
                            String login = "";
                            String date = "";
                            String id = "";

                            content += "content: " + message.getContent();
                            login += "login: " + message.getLogin();
                            date += "date: " + message.getDate();
                            id += "id: " + message.getText();
                            //arrayList.add(new ExampleComment(content));
                            arrayList.add(new ExampleComment(content, login, date, id));
                        }
                    }
                    else{
                        String content = "";
                        String login = "";
                        String date = "";
                        String id = "";

                        content += "content: " + message.getContent();
                        login += "login: " + message.getLogin();
                        date += "date: " + message.getDate();
                        id += "id: " + message.getText();
                        arrayList.add(new ExampleComment(content, login, date, id));
                    }

                }

                deleteCommentsRetrofit();
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
            }
        });
    }

    protected void deleteCommentsRetrofit(){
        if(flagToDelete) {
            System.out.println("array" + arrayList);
            System.out.println("array" + arrayList);
            System.out.println("haha");
            System.out.println(arrayList.size());
            Intent intentGetPosition = getIntent();
            position = intentGetPosition.getIntExtra("positionInArray", -1);
            System.out.println("position: " + position + "arrayzsize: " + arrayList.size());
            if (position != -1) {
                id = arrayList.get(position).getId();
                id = id.substring(4);
                Retrofit retrofit2 = new Retrofit.Builder()
                        .baseUrl("https://tgryl.pl/shoutbox/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                JsonPlaceholderAPIdeleteComment jsonPlaceholderAPI2 = retrofit2.create(
                        JsonPlaceholderAPIdeleteComment.class);
                System.out.println("id: ::");
                System.out.println(id);

                Call call2 = jsonPlaceholderAPI2.deleteMessage(id);
                call2.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        System.out.println("response:!");
                        System.out.println(response);
                        if (!response.isSuccessful()) {
                            System.out.println("!response.isSuccessful()");
                            System.out.println(call2);
                            return;
                        }
                        Intent intentGoToGallery = new Intent(DeleteCommentActivity.this, GalleryAction.class);
                        seeComents();
                        System.out.println("hehe");
                        startActivity(intentGoToGallery);
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(DeleteCommentActivity.this,
                                "Error", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });

            }
            flagToDelete = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_comment);
        flagToDelete = true;
        seeComents();
    }
}