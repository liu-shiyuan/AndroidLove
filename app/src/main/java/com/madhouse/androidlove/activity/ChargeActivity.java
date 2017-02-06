package com.madhouse.androidlove.activity;

import android.app.Activity;
import android.os.Bundle;
import com.madhouse.androidlove.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import android.content.IntentFilter;

public class ChargeActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge);

        this.registerReceiver(this.myBatteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    private BroadcastReceiver myBatteryReceiver = new BroadcastReceiver() {
      public void onReceive(Context context, Intent intent) {
          Toast.makeText(ChargeActivity.this, "Device is charging", Toast.LENGTH_SHORT).show();
      }
    };
}
