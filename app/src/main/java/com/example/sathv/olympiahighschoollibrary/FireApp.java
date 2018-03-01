package com.example.sathv.olympiahighschoollibrary;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by sathv on 2/9/2018.
 */

public class FireApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
