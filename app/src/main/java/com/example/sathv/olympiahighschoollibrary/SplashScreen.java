package com.example.sathv.olympiahighschoollibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //allow the activity to display when running on thread
        Thread thread = new Thread() {

            @Override
            public void run() {
                try {
                    //1.5 seconds
                    sleep(1500);
                    //then start the next activity
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}
