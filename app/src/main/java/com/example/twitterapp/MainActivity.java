package com.example.twitterapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.icu.text.TimeZoneFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText tTextUsername;
    EditText tTextPassword;
    Button   tButtonLogin;
    TextView tTextviewRegister;
    DatabaseHelper db;
    private static  int SPLASH_TIME_OUT=40000;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db =new DatabaseHelper(this);

        tTextUsername = (EditText) findViewById(R.id.edit_username);
        tTextPassword = (EditText) findViewById(R.id.edit_password);
        tButtonLogin = (Button) findViewById(R.id.button_login);
        tTextviewRegister = (TextView) findViewById(R.id.textview_register);
        //register button
        tTextviewRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent registerIntent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });
        //login button
        tButtonLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String user=tTextUsername.getText().toString().trim();
                String pwd=tTextPassword.getText().toString().trim();
                Cursor res=db.checkuser(user,pwd);


                SharedPreferences preferences = getApplicationContext().getSharedPreferences("USER",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("username", user);
                editor.putString("password",pwd);
                editor.commit();


                if(res.getCount()>0)
                {

                    Toast.makeText(MainActivity.this, "suessfull logIn", Toast.LENGTH_SHORT).show();
                    Intent Homepage=new Intent(MainActivity.this, UserActivity.class);
                    startActivity(Homepage);
                    finish();

                }
                else
                {
                    Toast.makeText(MainActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}