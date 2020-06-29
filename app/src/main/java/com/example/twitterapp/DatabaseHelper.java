package com.example.twitterapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;



public class DatabaseHelper extends SQLiteOpenHelper {

    Context context;

    public static final String DATABASE_NAME = "register.db";
    public static final String TABLE_NAME = "registeruser";
    public static final String COL_2 = "username";
    public static final String COL_3 = "password";

    public static final String TBL_FOLLOWERS = "followers";
    public static final String USERNAME = "userName";
    public static final String FOLLOWER = "followerName";

    public static final String TBL_TWEET="tweetTable";
    public static final String TWEET = "tweet";
    public static final String USER = "userName";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        System.out.println("Table creating.........");
        sqLiteDatabase.execSQL("CREATE TABLE  registeruser (username TEXT primary key not null unique,password TEXT )");
        sqLiteDatabase.execSQL("CREATE TABLE followers (userName Text,followerName Text)");
        sqLiteDatabase.execSQL("CREATE TABLE  tweetTable (userName TEXT,tweet TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long adduser(String user, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user);
        contentValues.put("password", password);
        long res = db.insert("registeruser", null, contentValues);
        db.close();
        return res;
    }

    public Cursor checkuser(String usernam, String password) {

        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from " + TABLE_NAME + " where username ='" + usernam + "' and password = '" + password + "'";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;

    }
    public Cursor viewData(String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select userName from " + TABLE_NAME + " where username !='" + user + "'";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public void insertTweet(String msg, String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userName", username);
        contentValues.put("tweet", msg);

        long res = db.insert("tweetTable", null, contentValues);
        db.close();


    }

    public Cursor viewTweet(String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select tweet from tweetTable where username in (select followerName from followers where username='" + user +"' union select userName from tweetTable where username='" + user +"' )";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public void follow(String username,String follower) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userName", username);
        contentValues.put("followerName", follower);


        long res = db.insert("followers", null, contentValues);
        db.close();
    }

    public void unfollow(String username,String follower) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        db.execSQL("delete from followers where userName ='"+username+"' and followerName ='"+follower+"'");
        db.close();

    }


}
