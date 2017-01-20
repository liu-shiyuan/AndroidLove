package com.madhouse.androidlove.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.madhouse.androidlove.R;
import com.madhouse.androidlove.adapter.TimeTrackerAdapter;
import com.madhouse.androidlove.domain.TimeRecord;
import com.madhouse.androidlove.helper.DatabaseHelper;

public class TimeTracker extends AppCompatActivity {

    public static final int TIME_ENTRY_REQUEST_CODE = 1;
    public static final int EDIT_CONTACT_REQUEST_CODE = 2;
    private TimeTrackerAdapter adapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_tracker);

        databaseHelper = new DatabaseHelper(this);

        ListView listView = (ListView) findViewById(R.id.times_list);
        adapter = new TimeTrackerAdapter(this, databaseHelper.getAllTimeRecords());
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (R.id.add_menu_button == item.getItemId()) {
            Intent intent = new Intent(this, AddRecordActivity.class);
            startActivityForResult(intent, TIME_ENTRY_REQUEST_CODE);
        } else if (R.id.menu_edit_contact == item.getItemId()) {
            startActivity(new Intent(this, EditContactActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TIME_ENTRY_REQUEST_CODE) {
            if (RESULT_OK == resultCode) {
                String notes = data.getStringExtra("notes");
                String time = data.getStringExtra("time");
                databaseHelper.saveTimeRecord(time, notes);
                adapter.changeCursor(databaseHelper.getAllTimeRecords());
                adapter.notifyDataSetChanged();
            }
        } else if (requestCode == EDIT_CONTACT_REQUEST_CODE) {
            if (RESULT_OK == resultCode) {

            }
        }

    }
}
