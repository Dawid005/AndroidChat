package com.example.konspekt9_3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditComment extends AppCompatActivity {

    public int position=-100;
    private ArrayList<ExampleComment> arrayList = new ArrayList<ExampleComment>();
    private  ArrayList<ExampleComment> arrayList2;
    public Boolean flagToEditComment = true;

    private String idStringReal;
    private TextView newLogin;
    private TextView content;
    private String stringNewLogin;
    private String stringContent;
    private TextView oldLogin;
    private String stringOldLogin;
    private Boolean flagToDeleteComment = true;

    private String id;

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

                editCommentRetrofit();
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
            }
        });
    }

    protected void editCommentRetrofit(){

        String stringContent2 = stringContent;
        String stringNewLogin2 = stringNewLogin;
        String idStringReal2 = idStringReal;

        if(stringContent2==null){
            stringContent2="";
        }

        if(stringNewLogin2 == null){
            stringNewLogin2 = "";
        }

            Retrofit retrofit2 = new Retrofit.Builder()
                    .baseUrl("https://tgryl.pl/shoutbox/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        idStringReal2 = idStringReal2.substring(4);
            JsonPlaceholderAPIeditComment jsonPlaceholderAPI2 = retrofit2.create(
                    JsonPlaceholderAPIeditComment.class);
            System.out.println("JSON");
        MessageNewComment messageNewComment = new MessageNewComment(stringContent2, stringNewLogin2);
            System.out.println(jsonPlaceholderAPI2.editMessage(idStringReal2, messageNewComment.getContent(),
                    messageNewComment.getText()));

            System.out.println("PLUS" +messageNewComment.getContent() + messageNewComment.getText());
            Call<MessageNewComment> call2 = jsonPlaceholderAPI2.editMessage(
                    idStringReal2, messageNewComment.getContent(),
                    messageNewComment.getText());
            call2.enqueue(new Callback<MessageNewComment>() {
                @Override
                public void onResponse(Call<MessageNewComment> call, Response<MessageNewComment> response) {
                    System.out.println("Teraz to:");
                    System.out.println(response);
                    if(!response.isSuccessful()){
                        return;
                    }

                    Intent intentGoToGallery = new Intent(EditComment.this, GalleryAction.class);
                    startActivity(intentGoToGallery);
                }

                @Override
                public void onFailure(Call<MessageNewComment> call, Throwable t) {
                    Toast.makeText(EditComment.this, "Error", Toast.LENGTH_SHORT).show();
                }

            });
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_comment);

        arrayList = new ArrayList<ExampleComment>();
        arrayList2 = new ArrayList<ExampleComment>();

        Intent intentRecycler = getIntent();
        position =  intentRecycler.getIntExtra("position",-100);
        if(position!=-100){
    /*
                Button buttonEditComment = findViewById(R.id.buttonEditComment);
                buttonEditComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        newLogin = (TextView) findViewById(R.id.newLogin);
                        content = (TextView) findViewById(R.id.content);
                        oldLogin = (TextView) findViewById(R.id.oldLogin);

                        stringNewLogin = newLogin.getText().toString();
                        stringContent = content.getText().toString();
                        stringOldLogin = oldLogin.getText().toString();
                       // System.out.println(arrayList.size());
                        //System.out.println(position);
                        System.out.println(arrayList.get(position).getLogin());
                        stringOldLogin = "login: "+stringOldLogin;

                       if((arrayList.get(position).getLogin()).equals(stringOldLogin)){
                            idStringReal=arrayList.get(position).getId();
                           seeComents();
                            Intent intent = new Intent(EditComment.this, GalleryAction.class);
                            intent.putExtra("FlagToRefresh",true);
                            startActivity(intent);
                        }


                    }
                });
                */
                Button buttonDeleteComment = findViewById(R.id.buttonDeleteComment);
                buttonDeleteComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        seeComentsToDelete();
                        flagToDeleteComment = true;
                    }
                });

        }
    }

    protected void seeComentsToDelete(){
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
                arrayList2.removeAll(arrayList2);
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
                            arrayList2.add(new ExampleComment(content, login, date, id));
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
                        arrayList2.add(new ExampleComment(content, login, date, id));
                    }

                }

                deleteCommentsRetrofit();
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
            }
        });
    }

    protected void deleteCommentsRetrofit() {
        System.out.println("position -" + position);
        System.out.println(arrayList2.size());
        if (flagToDeleteComment) {
            if (position != -1) {
                id = arrayList2.get(position).getId();
                Retrofit retrofit2 = new Retrofit.Builder()
                        .baseUrl("https://tgryl.pl/shoutbox/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                JsonPlaceholderAPIdeleteComment jsonPlaceholderAPI2 = retrofit2.create(
                        JsonPlaceholderAPIdeleteComment.class);
                id = id.substring(4);
                System.out.println(id);
                Call call2 = jsonPlaceholderAPI2.deleteMessage(id);
                call2.enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if (!response.isSuccessful()) {
                            System.out.println("!response.isSuccessful()");
                            System.out.println(id);
                            return;
                        }
                        Intent intentGoToGallery = new Intent(EditComment.this, GalleryAction.class);
                        System.out.println("hehe");
                        startActivity(intentGoToGallery);
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(EditComment.this,
                                "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        flagToDeleteComment = false;
    }
}