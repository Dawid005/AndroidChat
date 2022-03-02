package com.example.konspekt9_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HomeAction extends AppCompatActivity {

    private String login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        Button setLogin = (Button) findViewById(R.id.button);

        setLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editTextLogin = (EditText) findViewById(R.id.textView3);

                login = editTextLogin.getText().toString();
                saveData(login);

            }
        });
        loadData();
    }

    private void saveData(String output){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("login", output);
        editor.apply();
    }

    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        login = sharedPreferences.getString("login","");
        EditText editTextLogin = (EditText) findViewById(R.id.textView3);
        editTextLogin.setText(login);

    }
}