package com.example.addhayon;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class forgot_final_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_final_page);

        TextView textView = (TextView) findViewById(R.id.sign_in_page);
        String text = "<font color=#5E5D5D>Completed! </font><font color=#F52525>Sign in</font>";
        textView.setText(Html.fromHtml(text));

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignInPage();
            }
        });
    }
    public void openSignInPage() {
        Intent intent = new Intent(this,sign_in_page.class);
        startActivity(intent);
    }
}