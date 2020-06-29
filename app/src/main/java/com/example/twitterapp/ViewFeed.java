package com.example.twitterapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewFeed extends AppCompatActivity {
    ArrayList<String> users = new ArrayList<>();
    DatabaseHelper db;
    SharedPreferences preferences;
    ArrayAdapter adapter;
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feed);

        ListView viewTweets=findViewById(R.id.viewfeed);
        setTitle("Tweets");

        db = new DatabaseHelper(ViewFeed.this);

        preferences = getApplicationContext().getSharedPreferences("USER",MODE_PRIVATE);
        user = preferences.getString("username","");

        Cursor cursor  =  db.viewTweet(user);
        if (cursor.getCount() == 0)
        {
            Toast.makeText(this, "no data to shoow", Toast.LENGTH_SHORT).show();

        }
        else {
            while (cursor.moveToNext())
            { users.add(cursor.getString(0)); }
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, users);
            viewTweets.setAdapter(adapter);
            }
    }
}
