package com.example.addhayon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class NewsActivity extends AppCompatActivity {
    private MeowBottomNavigation btm;
    LinearLayout prothomalo,somoi,bbc,bdnews,kalerkontho,samakal;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        prothomalo = findViewById(R.id.prothomalo);
        somoi = findViewById(R.id.somoi);
        bbc = findViewById(R.id.bbc);
        bdnews = findViewById(R.id.bdnews);
        kalerkontho = findViewById(R.id.kalerkontho);
        samakal = findViewById(R.id.samakal);

        prothomalo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsActivity.this,NewsPageActivity.class);
                intent.putExtra("newslink", "https://www.prothomalo.com/");
                startActivity(intent);
                NewsActivity.this.finish();
            }
        });
        somoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsActivity.this,NewsPageActivity.class);
                intent.putExtra("newslink", "https://www.somoynews.tv/");
                startActivity(intent);
                NewsActivity.this.finish();
            }
        });
        bbc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsActivity.this,NewsPageActivity.class);
                intent.putExtra("newslink", "https://www.bbc.com/bengali");
                startActivity(intent);
                NewsActivity.this.finish();
            }
        });
        bdnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsActivity.this,NewsPageActivity.class);
                intent.putExtra("newslink", "https://bangla.bdnews24.com/");
                startActivity(intent);
                NewsActivity.this.finish();
            }
        });
        kalerkontho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsActivity.this,NewsPageActivity.class);
                intent.putExtra("newslink", "https://www.allbanglanewspapersbd.com/kalerkantho/");
                startActivity(intent);
                NewsActivity.this.finish();
            }
        });
        samakal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsActivity.this,NewsPageActivity.class);
                intent.putExtra("newslink", "https://www.allbanglanewspapersbd.com/samakal/");
                startActivity(intent);
                NewsActivity.this.finish();
            }
        });


        btm = findViewById(R.id.bottom_nav);
        btm.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_home));
        btm.add(new MeowBottomNavigation.Model(2,R.drawable.baseline_apps_24));
        btm.add(new MeowBottomNavigation.Model(3,R.drawable.baseline_art_track_24));
        btm.add(new MeowBottomNavigation.Model(4,R.drawable.ic_baseline_location));
        btm.add(new MeowBottomNavigation.Model(5,R.drawable.ic_baseline_person));



        btm.show(3,true);
        btm.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()){
                    case 1:
                        openHome();
                        break;
                    case 2:
                        openExam();
                        break;
                    case 3:
                        openNews();
                        break;
                    case 4:
                        openMap();
                        break;
                    case 5:
                        openUserProfile();;
                        break;

                }
                return null;
            }
        });
    }
    private void openHome(){
        Intent intent = new Intent(this,dashboard.class);
        startActivity(intent);
        NewsActivity.this.finish();
    }

    public void openUserProfile(){
        Intent intent = new Intent(this, userProfile.class);
        startActivity(intent);
        NewsActivity.this.finish();
    }
    public void openExam(){
        Intent intent = new Intent(this, SeeMoreActivity.class);
        startActivity(intent);
        NewsActivity.this.finish();
    }
    public  void openNews(){
        Intent intent = new Intent(this, NewsActivity.class);
        startActivity(intent);
        NewsActivity.this.finish();
    }
    public void openMap(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
        NewsActivity.this.finish();
    }
}