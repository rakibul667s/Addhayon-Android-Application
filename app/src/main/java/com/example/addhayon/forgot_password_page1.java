package com.example.addhayon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgot_password_page1 extends AppCompatActivity {
    private EditText email;
    private String emailStr;
    private TextView wrong, checkMail;
    private FirebaseAuth auth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_page1);

        auth =FirebaseAuth.getInstance();

        wrong =findViewById(R.id.wrong);

        email=findViewById(R.id.email);


        Button btn = (Button) findViewById(R.id.fp_otp);
        TextView textView = (TextView) findViewById(R.id.sign_in_page);
        checkMail = findViewById(R.id.checkMail);

        String text = "<font color=#5E5D5D>Now no need. </font><font color=#F52525>Sign in</font>";
        textView.setText(Html.fromHtml(text));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignInPage();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

    }
    public void openSignInPage() {
        Intent intent = new Intent(this, sign_in_page.class);
        startActivity(intent);
    }
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public void validateData(){
        emailStr = email.getText().toString().trim();
        if(emailStr.isEmpty()){
            wrong.setText("Enter your email *");
        }else if(!emailStr.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            wrong.setText("Enter your valid email *");
        }else{
            forgotPass();
        }
    }
    public void forgotPass(){
        auth.sendPasswordResetEmail(emailStr)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful()){
                           checkMail.setText("Check your mail :'"+emailStr+"'");
                       }else{
                           Toast.makeText(forgot_password_page1.this, "Error : "+task.getException().getMessage(),
                                   Toast.LENGTH_SHORT).show();
                       }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(forgot_password_page1.this, "Something is wrong",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

}