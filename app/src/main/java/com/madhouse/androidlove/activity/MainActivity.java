package com.madhouse.androidlove.activity;

import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;

import com.madhouse.androidlove.R;

import static com.madhouse.androidlove.R.*;

public class MainActivity extends AppCompatActivity {

    private Bitmap image;
    private int COMPLETE = 0;
    private int FAIL = 1;
    public final static String EXTRA_MESSAGE = "extra_message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        ApplicationInfo appInfo = getApplicationInfo();
        int resID = getResources().getIdentifier("chrysanthemum", "drawable", appInfo.packageName);
        image = BitmapFactory.decodeResource(getResources(), resID);
    }

    public void clickToRefresh(View view) {
        final ProgressDialog dialog = ProgressDialog.show(this, "Waiting", "Refreshing headline news.");

        new Thread() {
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {}

                dialog.dismiss();
            }
        }.start();
    }

    public void showButtonClicked(View view) {
        TextView textView = (TextView) findViewById(R.id.textView1);
        if (View.VISIBLE == textView.getVisibility())
            textView.setVisibility(View.INVISIBLE);
        else
            textView.setVisibility(View.VISIBLE);
    }

    private Handler setWallpaperHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETE)
                Toast.makeText(MainActivity.this, "Wallpaper set.", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(MainActivity.this, "Error occurred while setting wallpaper.", Toast.LENGTH_SHORT).show();
        }
    };

    public void clickToSetWallpaper(View view) {
        new Thread() {
            public void run() {
                WallpaperManager manager = WallpaperManager.getInstance(MainActivity.this);
                Message msg = new Message();
                try {
                    manager.setBitmap(image);
                    msg.what = COMPLETE;
                    setWallpaperHandler.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    msg.what = FAIL;
                    setWallpaperHandler.sendMessage(msg);
                }
            }
        }.start();
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, TimeTracker.class);
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (id.main_menu_email == item.getItemId())
            startActivity(new Intent(this, SimpleEmailActivity.class));
        return super.onOptionsItemSelected(item);
    }

}
