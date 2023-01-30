package com.example.addhayon;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class dashboard extends AppCompatActivity {

    private MeowBottomNavigation btm;
    private Button btnID;
    private ImageView chat;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        chat = findViewById(R.id.chat);

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAIChat();
            }
        });

        btm = findViewById(R.id.bottom_nav);
        btm.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_home));
        btm.add(new MeowBottomNavigation.Model(2,R.drawable.ic_exam));
        btm.add(new MeowBottomNavigation.Model(3,R.drawable.ic_baseline_calendar));
        btm.add(new MeowBottomNavigation.Model(4,R.drawable.ic_baseline_location));
        btm.add(new MeowBottomNavigation.Model(5,R.drawable.ic_baseline_person));



        btm.show(1,true);
        replace(new com.example.addhayon.HomeFragment());
        btm.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()){
                    case 1:
                        replace(new HomeFragment());
                        break;
                    case 2:
                        openExam();
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
    }

    private void openAIChat(){
        Intent intent = new Intent(this,AIChatActivity.class);
        startActivity(intent);
        dashboard.this.finish();
    }
    private  void  replace(Fragment fragment){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }

    public void openUserProfile(){
        Intent intent = new Intent(this, userProfile.class);
        startActivity(intent);
        dashboard.this.finish();
    }
    public void openExam(){
        Intent intent = new Intent(this, AllExamCatActivity.class);
        startActivity(intent);
        dashboard.this.finish();
    }
    public  void openCalendar(){
        Intent intent = new Intent(this, calender.class);
        startActivity(intent);
        dashboard.this.finish();
    }
    public void openMap(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
        dashboard.this.finish();
    }

}