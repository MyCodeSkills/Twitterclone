package com.example.twitterapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText tTextUsername;
    EditText tTextPassword;
    EditText tCnfPassword;
    Button tButtonRegister;
    TextView tTextviewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db=new DatabaseHelper(this);
        tTextUsername = (EditText) findViewById(R.id.edit_username);
        tTextPassword = (EditText) findViewById(R.id.edit_password);
        tCnfPassword=(EditText)findViewById(R.id.edit_cnf_password);
        tButtonRegister = (Button) findViewById(R.id.button_register);
        tTextviewLogin = (TextView) findViewById(R.id.textview_login);

            //login button
        tTextviewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent loginIntent=new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(loginIntent);
            }
        });

        //button register
        tButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = tTextUsername.getText().toString().trim();
                String pwd = tTextPassword.getText().toString().trim();
                String cnf_pwd = tCnfPassword.getText().toString().trim();

                if (pwd.equals(cnf_pwd)) {
                    long val = db.adduser(user, pwd);
                    if (val > 0) {
                        Toast.makeText(RegisterActivity.this, "you have Registered Successfull", Toast.LENGTH_SHORT).show();
                        Intent movetologin = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(movetologin);
                    } else {
                        Toast.makeText(RegisterActivity.this, "registration error", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(RegisterActivity.this, "password not match", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
