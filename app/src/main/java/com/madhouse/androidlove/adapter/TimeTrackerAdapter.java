package com.madhouse.androidlove.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.madhouse.androidlove.R;
import com.madhouse.androidlove.domain.TimeRecord;

import java.util.LinkedList;

public class TimeTrackerAdapter extends CursorAdapter {

    public TimeTrackerAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTextView = (TextView) view.findViewById(R.id.time_view);
        String time = cursor.getString(cursor.getColumnIndex("time"));
        nameTextView.setText(time);

        TextView valueTextView = (TextView) view.findViewById(R.id.notes_view);
        String notes = cursor.getString(cursor.getColumnIndex("notes"));
        valueTextView.setText(notes);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.time_list_item, parent, false);
        return view;
    }

}
