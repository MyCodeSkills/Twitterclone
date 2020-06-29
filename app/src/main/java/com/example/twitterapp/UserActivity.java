package com.example.twitterapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity
{
    ArrayList<String> users = new ArrayList<>();
    ArrayAdapter adapter;
    DatabaseHelper db;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        db = new DatabaseHelper(UserActivity.this);

        setContentView(R.layout.activity_user);
        ListView listView=findViewById(R.id.listView);
        setTitle("Follow people");

        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);

       SharedPreferences preferences = getApplicationContext().getSharedPreferences("USER",MODE_PRIVATE);
       username = preferences.getString("username","");

        Cursor cursor = db.viewData(username);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "no data to shoow", Toast.LENGTH_SHORT).show();


        } else {
            while (cursor.moveToNext()) {
                listView.setAdapter(adapter);
                users.add(cursor.getString(0));
            }

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_expandable_list_item_1, users);
            listView.setAdapter(adapter);


        }
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_checked,users);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView checkedTextView = (CheckedTextView) view;
                if (checkedTextView.isChecked()) {
                    Log.i("info", "Checked");

                    String follower=users.get(position);
                    db.follow(username,follower);


                } else
                    {
                    Log.i("info", "Not checked");
                        String follwer=users.get(position);
                        db.unfollow(username,follwer);
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.tweet_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.tweet) {

            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Send a Tweet");
            final EditText tweetEditText = new EditText(this);

            int maxLength = 70;
            tweetEditText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
            builder.setView(tweetEditText);
            System.out.println("data"+tweetEditText);
            builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {

                    db.insertTweet(tweetEditText.getText().toString(), username);

                    Toast.makeText(UserActivity.this, "Successfully posted tweet ", Toast.LENGTH_SHORT).show();

                    Log.i("info", tweetEditText.getText().toString());

                }
            });
            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    Log.i("info", "I dont wanna tweet");

                    dialog.dismiss();

                }
            });
            builder.show();
        } else if (item.getItemId() == R.id.logout) {
            Intent logout = new Intent(UserActivity.this, MainActivity.class);
            startActivity(logout);
            finish();

        }else if (item.getItemId() == R.id.viewfeed) {
            Intent intent = new Intent(UserActivity.this, ViewFeed.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }


}