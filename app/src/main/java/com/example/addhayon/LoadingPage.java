package com.example.addhayon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class LoadingPage extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private RelativeLayout button2;
    private TextView buttonText;
    private LottieAnimationView buttonAnim;
    private boolean check = false;
    String btnText, toastMst1, s1= "Bangla";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_page);


        btnText = DBQurey.myProfile.getLanguage();

        buttonText = findViewById(R.id.button_text);
        buttonAnim = findViewById(R.id.animation_view);


        button2 = findViewById(R.id.loading);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check = true;

                buttonAnim.setVisibility(View.VISIBLE);
                buttonAnim.playAnimation();
                buttonText.setVisibility(View.GONE);
                Toast.makeText(LoadingPage.this, "Please wait a few seconds", Toast.LENGTH_SHORT).show();
            }
        });
        mAuth = FirebaseAuth.getInstance();

        //-------------firebase add-----------------
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DBQurey.g_firestore = FirebaseFirestore.getInstance();

        //------------------Splash Screen--------------------
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (mAuth.getCurrentUser() != null) {

                    DBQurey.loadData(new MyCompleteListener() {
                        @Override
                        public void onSuccess() {
                            buttonAnim.pauseAnimation();
                            buttonAnim.setVisibility(View.GONE);
                            buttonText.setVisibility(View.VISIBLE);
                            Intent intent = new Intent(LoadingPage.this, dashboard.class);
                            startActivity(intent);
                            LoadingPage.this.finish();
                            Toast.makeText(LoadingPage.this, "Build up your skills", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure() {
                            Toast.makeText(LoadingPage.this, "Something went wrong ! Please try Later.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Intent intent = new Intent(LoadingPage.this, sign_in_page.class);
                    startActivity(intent);
                    LoadingPage.this.finish();
                }


            }
        }.start();
    }




}