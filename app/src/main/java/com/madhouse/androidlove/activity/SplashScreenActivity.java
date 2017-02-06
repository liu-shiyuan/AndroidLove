package com.madhouse.androidlove.activity;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import com.madhouse.androidlove.R;

public class SplashScreenActivity  extends Activity {

    private long ms = 0;
    private long splashTime = 2000;
    private static boolean splashActive = true;
    private boolean paused = false;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        showSplashScreen();
    }

    public void showSplashScreen() {
        new Thread() {
            public void run() {
                try {
                    while (splashActive && ms < splashTime) {
                        if (!paused)
                            ms = ms + 100;
                        Thread.sleep(100);
                    }
                } catch (Exception e) {
                } finally {
                    splashActive = false;
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                }
            }
        }.start();
    }


}
