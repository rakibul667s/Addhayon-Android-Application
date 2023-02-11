package com.example.addhayon;

import static com.example.addhayon.DBQurey.g_firestore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.addhayon.Models.AllUserModel;
import com.example.addhayon.Models.ProfileModel;
import com.example.addhayon.Models.RankModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllUserActivity extends AppCompatActivity {
    private TextView name;
    CircleImageView pImg;

    public static RankModel myPerformance = new RankModel("NULL",0,-1,"NULL","NULL");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user);
        name = findViewById(R.id.name);
        pImg =findViewById(R.id.profile_image);

        Intent intent = getIntent();
        String userId = intent.getStringExtra("user_id");
        Toast.makeText(AllUserActivity.this,userId, Toast.LENGTH_SHORT).show();

        Glide.with(this)
                .load(userId)
                .into(pImg);



    }
}