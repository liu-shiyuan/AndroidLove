
package com.madhouse.androidlove.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;

import com.madhouse.androidlove.R;

public class AddRecordActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);
    }

    public void onSave(View view) {
        Intent intent = getIntent();
        EditText timeText = (EditText) findViewById(R.id.time_input);
        EditText notesText = (EditText) findViewById(R.id.notes_input);

        intent.putExtra("time", timeText.getText().toString());
        intent.putExtra("notes", notesText.getText().toString());

        this.setResult(RESULT_OK, intent);
        finish();
    }

    public void onCancel(View view) {
        finish();
    }

}

