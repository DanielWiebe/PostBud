package com.shiftdev.postbud;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

          setTitle("");
          Thread welcomeThread = new Thread() {
               @Override
               public void run() {
                    try {
                         super.run();
                         sleep(500);
                    } catch (Exception e) {

                } finally {
                    Intent i = new Intent(WelcomeActivity.this,
                            MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        };
        welcomeThread.start();
    }
}
