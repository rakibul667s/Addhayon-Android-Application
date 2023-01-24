package com.example.addhayon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

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

        //-------------firebase add-----------------
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DBQurey.g_firestore = FirebaseFirestore.getInstance();

        //------------------Splash Screen--------------------
        new Thread(){
            @Override
            public  void run(){
                try {
                    sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                if(mAuth.getCurrentUser() != null){
                    DBQurey.loadData(new MyCompleteListener(){
                        @Override
                        public void onSuccess(){
                            Intent intent = new Intent(LoadingPage.this,dashboard.class);
                            startActivity(intent);
                            LoadingPage.this.finish();
                        }
                        @Override
                        public void onFailure(){
                            Toast.makeText(LoadingPage.this, "Something went wrong ! Please try Later.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });


                }else{
                    Intent intent = new Intent(LoadingPage.this, sign_in_page.class);
                    startActivity(intent);
                    LoadingPage.this.finish();
               }


            }
        }.start();
    }
    public void openSignPage(){

        if(mAuth.getCurrentUser() != null){
            DBQurey.loadData(new MyCompleteListener(){
                @Override
                public void onSuccess(){
                    Intent intent = new Intent(LoadingPage.this,dashboard.class);
                    startActivity(intent);
                    LoadingPage.this.finish();
                }
                @Override
                public void onFailure(){
                    Toast.makeText(LoadingPage.this, "Something went wrong ! Please try Later.",
                            Toast.LENGTH_SHORT).show();
                }
            });


        }else{
            Intent intent = new Intent(LoadingPage.this, sign_in_page.class);
            startActivity(intent);
            LoadingPage.this.finish();
        }
    }
}