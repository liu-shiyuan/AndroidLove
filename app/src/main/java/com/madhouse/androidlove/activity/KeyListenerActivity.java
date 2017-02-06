package com.madhouse.androidlove.activity;

import android.app.Activity;
import android.os.Bundle;
import com.madhouse.androidlove.R;
import android.widget.EditText;
import android.text.method.DigitsKeyListener;
import android.text.method.DateKeyListener;
import android.text.method.MultiTapKeyListener;
import android.text.method.TextKeyListener;
import android.text.method.QwertyKeyListener;

public class KeyListenerActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_key_listener);
        ((EditText)findViewById(R.id.key_listener_digits_edit)).setKeyListener(new DigitsKeyListener(true, true));
        ((EditText)findViewById(R.id.key_listener_digits_without_edit)).setKeyListener(new DigitsKeyListener());
        ((EditText)findViewById(R.id.key_listener_date_edit)).setKeyListener(new DateKeyListener());
        ((EditText)findViewById(R.id.key_listener_multitap_listener_edit))
                .setKeyListener(new MultiTapKeyListener(TextKeyListener.Capitalize.WORDS, true));
        ((EditText)findViewById(R.id.key_listener_qwerty_listener_edit))
                .setKeyListener(new QwertyKeyListener(TextKeyListener.Capitalize.SENTENCES, true));
    }

}
