package com.example.addhayon;

import static com.example.addhayon.DBQurey.g_firestore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.addhayon.Models.AllUserModel;
import com.example.addhayon.Models.ProfileModel;
import com.example.addhayon.Models.RankModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class AllUserActivity extends AppCompatActivity {
    private TextView name,bio,rank,totalScore,sclclg,address2,birth,email,phone,rankText,scoreText,contactText;
    ImageView cover_img2;
    CircleImageView pImg;
    private MeowBottomNavigation btm;
    private LinearLayout rankB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user);
        name = findViewById(R.id.name);
        pImg =findViewById(R.id.profile_image);
        bio =findViewById(R.id.bio);
        rank =findViewById(R.id.rank);
        totalScore =findViewById(R.id.totalScore);
        sclclg =findViewById(R.id.sclclg);
        cover_img2 =findViewById(R.id.cover_img);
        address2 =findViewById(R.id.address2);
        birth =findViewById(R.id.birth);
        email =findViewById(R.id.email);
        phone =findViewById(R.id.phone);
        rankText =findViewById(R.id.rankText);
        scoreText=findViewById(R.id.scoreText);
        contactText = findViewById(R.id.contactText);
        rankB = findViewById(R.id.rankB);

        rankB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AllUserActivity.this, LeaderBoard.class);
                startActivity(intent);
                AllUserActivity.this.finish();
            }
        });

        if(DBQurey.myProfile.getLanguage().equals("Bangla")){
            rankText.setText("পদমর্যাদা");
            scoreText.setText("সম্পূর্ণ ফলাফল");
            contactText.setText("যোগাযোগ করুন :");
        }




        Intent intent = getIntent();
       // String userId = intent.getStringExtra("user_id");

        Glide.with(this)
                .load(intent.getStringExtra("pImg"))
                .into(pImg);
        Glide.with(this)
                .load(intent.getStringExtra("cImg"))
                .into(cover_img2);

        name.setText(intent.getStringExtra("name"));
        bio.setText(intent.getStringExtra("bio"));
        rank.setText(""+intent.getIntExtra("rank",0));
        totalScore.setText(""+intent.getIntExtra("score",0));
        sclclg.setText(intent.getStringExtra("sclclg"));
        address2.setText(intent.getStringExtra("address"));
        birth.setText(intent.getStringExtra("dateOfbirth"));
        email.setText(intent.getStringExtra("emailId"));
        phone.setText(intent.getStringExtra("phone"));

        btm = findViewById(R.id.bottom_nav);
        btm.add(new MeowBottomNavigation.Model(1, R.drawable.ic_baseline_home));
        btm.add(new MeowBottomNavigation.Model(2, R.drawable.ic_exam));
        btm.add(new MeowBottomNavigation.Model(3, R.drawable.baseline_co_present));
        btm.add(new MeowBottomNavigation.Model(4, R.drawable.ic_baseline_dashborad));
        btm.add(new MeowBottomNavigation.Model(5, R.drawable.ic_baseline_person));


        btm.show(3, true);
        btm.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()) {
                    case 1:
                        openDashboard();
                        break;
                    case 2:
                        openExamCat();
                        break;
                    case 3:
                        break;
                    case 4:
                        openLeaderBoard();
                        break;
                    case 5:
                        openUserProfile();
                        break;

                }
                return null;
            }
        });






    }
    public void openExamCat(){
        Intent intent = new Intent(this, AllExamCatActivity.class);
        startActivity(intent);
        AllUserActivity.this.finish();
    }
    public void openLeaderBoard(){
        Intent intent = new Intent(this, LeaderBoard.class);
        startActivity(intent);
        AllUserActivity.this.finish();
    }
    public void openDashboard(){
        Intent intent = new Intent(this, dashboard.class);
        startActivity(intent);
        AllUserActivity.this.finish();
    }

    public void openUserProfile(){
        Intent intent = new Intent(this, userProfile.class);
        startActivity(intent);
        AllUserActivity.this.finish();
    }
    public  void openMark(){
        Intent intent = new Intent(this, BookMaksActivity.class);
        startActivity(intent);
        AllUserActivity.this.finish();
    }
}