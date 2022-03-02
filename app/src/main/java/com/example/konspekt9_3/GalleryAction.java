package com.example.konspekt9_3;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class GalleryAction extends AppCompatActivity {


    private TextView textViewResult;
    private ArrayAdapter adapter;
    private TextView textView;
    private String editButtonFlag;

    private Boolean flagToEditComment=false;

    private SwipeRefreshLayout swipeRefreshLayout;

    private RecyclerView mRecycleView;
    private ContactAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<ExampleComment> arrayList;

    Handler handler = new Handler();

    Runnable runnable = new Runnable(){
        public void run() {
            arrayList.removeAll(arrayList);
        seeComents();
        handler.postDelayed(this,60000);
        }
        };

    protected void seeComents(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tgryl.pl/shoutbox/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceholderAPI jsonPlaceholderAPI = retrofit.create(JsonPlaceholderAPI.class);
        Call<List<Message>> call = jsonPlaceholderAPI.getMessages();
        System.out.println("getMessages()");
        System.out.println(call);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                System.out.println("seeComents");
                if(!response.isSuccessful()){
                    return;
                }
                List<Message> messages = response.body();
                int j=0;
                int i=0;
                arrayList.removeAll(arrayList);
                for(Message message : messages){
                    i = i + 1;
                    if(messages.size()>22){
                        if(i>messages.size()-22) {
                            j=j+1;
                            System.out.println(j);
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
                            System.out.println("haha");
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

                mRecycleView = findViewById(R.id.recyclerViewComments);
                mRecycleView.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(GalleryAction.this);
                mAdapter = new ContactAdapter(arrayList);
                mRecycleView.setLayoutManager(mLayoutManager);
                mRecycleView.setAdapter(mAdapter);

                SwipeToDeleteCallback swipeController = new SwipeToDeleteCallback();

                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
                itemTouchHelper.attachToRecyclerView(mRecycleView);
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
            }
        });
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_action);

        if (isNetworkAvailable()) {

            arrayList = new ArrayList<ExampleComment>();

            seeComents();

            Button buttonNewComment = (Button) findViewById(R.id.buttonNewComment);
            buttonNewComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentnewComment = new Intent(GalleryAction.this, CreateNewComment.class);
                    startActivity(intentnewComment);
                }
            });
            Intent intentEditComment = getIntent();
            flagToEditComment = intentEditComment.getBooleanExtra("FlagToRefresh", false);
            if (flagToEditComment) {
                arrayList.removeAll(arrayList);
                seeComents();
                flagToEditComment = false;
            }
            Thread thread = new Thread(runnable);
            thread.start();

            swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Stop animation (This will be after 3 seconds)
                            swipeRefreshLayout.setRefreshing(false);
                            arrayList.removeAll(arrayList);
                            seeComents();
                        }
                    }, 4000);
                }
            });

        }
    }
}
