package com.example.addhayon;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class forgot_otp_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_otp_page);
        Button btn = (Button) findViewById(R.id.fp_final_page);
        TextView textView = (TextView) findViewById(R.id.otp);
        String text = "<font color=#5E5D5D>Cancel and </font><font color=#F52525>Sign in</font>";
        textView.setText(Html.fromHtml(text));

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignInPage();
            }
        });


    }
    public void openSignInPage() {
        Intent intent = new Intent(this, sign_in_page.class);
        startActivity(intent);
    }

}