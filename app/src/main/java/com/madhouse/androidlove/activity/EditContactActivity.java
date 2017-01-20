package com.madhouse.androidlove.activity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.madhouse.androidlove.R;

import java.io.InputStream;

public class EditContactActivity extends AppCompatActivity {

    private static final int PICK_CONTACT_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        renderContact(null);
    }

    private void renderContact(Uri uri) {
        TextView name = (TextView) findViewById(R.id.contact_name);
        TextView phone = (TextView) findViewById(R.id.contact_phone);
        ImageView portrait = (ImageView) findViewById(R.id.contact_portrait);

        if (null == uri) {
            name.setText("Select a contact");
            phone.setText("Phone number");
            portrait.setImageBitmap(null);
        } else {
            name.setText(getDisplayName(uri));
            phone.setText(getPhoneNumber(uri));
            portrait.setImageBitmap(getContactPortrait(uri));
        }
    }

    private String getDisplayName(Uri uri) {
        String name = null;
        ContentResolver content = getContentResolver();
        Cursor cursor = content.query(uri, null, null, null, null);
        if (cursor.moveToFirst()) {
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        }
        cursor.close();
        return name;
    }

    private String getPhoneNumber(Uri uri) {
        String phone = null;
        Cursor contactCursor = getContentResolver().query(uri, new String[]{ContactsContract.Contacts._ID}
                , null, null, null);
        String id = null;
        if (contactCursor.moveToFirst()) {
            id = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts._ID));
        }
        contactCursor.close();

        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                , new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER}
                , ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " + ContactsContract.CommonDataKinds.Phone.TYPE
                        + " = " + ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE
                , new String[]{id}, null);
        if (cursor.moveToFirst()) {
            phone = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }
        cursor.close();

        return phone;
    }

    private Bitmap getContactPortrait(Uri uri) {
        Bitmap portrait = null;

        String id = null;
        Cursor cursor = getContentResolver().query(uri, new String[] {ContactsContract.Contacts._ID}, null, null, null);
        if (cursor.moveToFirst()) {
            id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
        }
        cursor.close();

        try {
            InputStream is = ContactsContract.Contacts.openContactPhotoInputStream(getContentResolver()
                , ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id).longValue()));
            if (null != is) {
                portrait = BitmapFactory.decodeStream(is);
            }
            is.close();
        } catch (Exception e) {
        }
        return portrait;
    }

    public void onUpdateContact(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_CONTACT_REQUEST) {
            if (resultCode == RESULT_OK) {
                renderContact(getIntent().getData());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
