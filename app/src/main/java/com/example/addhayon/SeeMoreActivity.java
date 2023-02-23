package com.example.addhayon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class SeeMoreActivity extends AppCompatActivity {
    private MeowBottomNavigation btm;
    private LinearLayout exam2, aboutMe, course, shop,note,cal;

    private TextView seeMore1,seeMore2, bExam, bCourse,menuTitle,
            bEvent, bLocation, bRank, bShop, bNote,bAbout,
            bLetest,bExtra, bCal,bnote2, bNews, bHealth,bnews2,
            bC1,bC2,bC3,bC4,bC5,bC6,
            bP1,bP2,bP3,bP4,bP5,bP6;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_more);

        exam2 = findViewById(R.id.rank);
        //aboutMe = view.findViewById(R.id.about);
        course = findViewById(R.id.course);
        bExam = findViewById(R.id. bExam);
        bCourse = findViewById(R.id.bCourse);
        bEvent = findViewById(R.id.bEvent);
        bLocation = findViewById(R.id.bLocation);
        bRank = findViewById(R.id.bRank);
        bShop = findViewById(R.id.bShop);
        bNote = findViewById(R.id.bNote);
        bCal = findViewById(R.id.bCal);
        bnote2 = findViewById(R.id.bnote2);
        bNews = findViewById(R.id.bNews);
        bHealth = findViewById(R.id.bHealth);
        shop = findViewById(R.id.shop);
        note = findViewById(R.id.note);
        cal = findViewById(R.id.cal);
        menuTitle = findViewById(R.id.menuTitle);
        bnews2 = findViewById(R.id.bnews2);

        if(DBQurey.myProfile.getLanguage().equals("Bangla")){
            bExam.setText("পরীক্ষা");
            bCourse.setText("কোর্স");
            bEvent.setText("ঘটনা");
            bLocation.setText("অবস্থান");
            bRank.setText("পদমর্যাদা");
            bShop.setText("দোকান");
            bNote.setText( "বিঃদ্রঃ");
            bCal.setText("হিসাব");
            bnote2.setText("বিঃদ্রঃ");
            bNews.setText("খবর");
            bnews2.setText("খবর");
            bHealth.setText("স্বাস্থ্য");
            menuTitle.setText("অধ্যয়ন");
        }

        btm = findViewById(R.id.bottom_nav);
        btm.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_home));
        btm.add(new MeowBottomNavigation.Model(2,R.drawable.baseline_apps_24));
        btm.add(new MeowBottomNavigation.Model(3,R.drawable.ic_baseline_calendar));
        btm.add(new MeowBottomNavigation.Model(4,R.drawable.ic_baseline_location));
        btm.add(new MeowBottomNavigation.Model(5,R.drawable.ic_baseline_person));



        btm.show(2,true);
        btm.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()){
                    case 1:
                        openHome();
                        break;
                    case 2:
                        openSeeMore();
                        break;
                    case 3:
                        openCalendar();
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
        exam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExam();
            }
        });
        // aboutMe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //openAbout();
//            }
//        });
        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCourse();
            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DBQurey.myProfile.getLanguage().equals("Bangla")){
                    Toast.makeText(SeeMoreActivity.this, "এখনও কাজ চলছে ", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SeeMoreActivity.this, "Working on system", Toast.LENGTH_SHORT).show();
                }
            }
        });
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DBQurey.myProfile.getLanguage().equals("Bangla")){
                    Toast.makeText(SeeMoreActivity.this, "এখনও কাজ চলছে ", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SeeMoreActivity.this, "Working on system", Toast.LENGTH_SHORT).show();
                }
            }
        });
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DBQurey.myProfile.getLanguage().equals("Bangla")){
                    Toast.makeText(SeeMoreActivity.this, "এখনও কাজ চলছে ", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(SeeMoreActivity.this, "Working on system", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void openHome(){
        Intent intent = new Intent(this, dashboard.class);
        startActivity(intent);
        SeeMoreActivity.this.finish();
    }

    public void openUserProfile(){
        Intent intent = new Intent(this, userProfile.class);
        startActivity(intent);
        SeeMoreActivity.this.finish();
    }
    public void openSeeMore(){
        Intent intent = new Intent(this, SeeMoreActivity.class);
        startActivity(intent);
        SeeMoreActivity.this.finish();
    }
    public  void openCalendar(){
        Intent intent = new Intent(this, calender.class);
        startActivity(intent);
        SeeMoreActivity.this.finish();
    }
    public void openMap(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
        SeeMoreActivity.this.finish();
    }
    private void openExam(){
        Intent intent = new Intent(this,LeaderBoard.class);
        startActivity(intent);
    }
    private  void openCourse(){
        Intent intent = new Intent(this,CourseActivity.class);
        startActivity(intent);
    }
    public void onStart() {
        super.onStart();
        LinearLayout calenderLayout=(LinearLayout) this.findViewById(R.id.calender);
        calenderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeeMoreActivity.this,calender.class);
                startActivity(intent);
            }
        });
        LinearLayout location=(LinearLayout) this.findViewById(R.id.location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeeMoreActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout exam=(LinearLayout) this.findViewById(R.id.exam);
        exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeeMoreActivity.this,AllExamCatActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout news=(LinearLayout) this.findViewById(R.id.news);
        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeeMoreActivity.this,NewsActivity.class);
                startActivity(intent);
            }
        });
    }

}