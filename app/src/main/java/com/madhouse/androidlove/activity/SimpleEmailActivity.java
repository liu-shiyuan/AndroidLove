package com.madhouse.androidlove.activity;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.os.Bundle;
import android.content.Intent;
import android.widget.EditText;
import com.madhouse.androidlove.R;

public class SimpleEmailActivity extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_email);
    }

    public void sendEmail(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_TITLE, "My Title");
        intent.putExtra(Intent.EXTRA_SUBJECT, "My Subject");
        EditText text = (EditText) findViewById(R.id.text_to_email);
        if (TextUtils.isEmpty(text.getText()))
            intent.putExtra(Intent.EXTRA_TEXT, "Empty Email.");
        else
            intent.putExtra(Intent.EXTRA_TEXT, text.getText());

        // 附件
        /*intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File("/path/to/file")));
        intent.setType("text/plain");

        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        ArrayList<Uri> uris = new ArrayList<Uri>();
        uris.add(Uri.fromFile(new File("/path/to/first/file")));
        uris.add(Uri.fromFile(new File("/path/to/second/file")));
        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris)*/

        startActivity(intent);
    }

}
