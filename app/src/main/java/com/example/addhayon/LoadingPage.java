package com.example.addhayon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class LoadingPage extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);

        button2 =(Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignPage();
            }
        });
        mAuth = FirebaseAuth.getInstance();
        new Thread(){
            @Override
            public  void run(){
                try {
                    sleep(1200);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                if(mAuth.getCurrentUser() != null){
                    Intent intent = new Intent(LoadingPage.this, dashboard.class);
                    startActivity(intent);


                }else{
                    Intent intent = new Intent(LoadingPage.this, sign_in_page.class);
                    startActivity(intent);
               }


            }
        }.start();
    }
    public void openSignPage(){
        Intent intent = new Intent(LoadingPage.this, sign_in_page.class);
        startActivity(intent);
    }
}