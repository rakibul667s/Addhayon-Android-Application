package com.example.addhayon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class CourseActivity extends AppCompatActivity {
    private TextView w;
    private MeowBottomNavigation btm;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
        w = findViewById(R.id.w);
        if(DBQurey.myProfile.getLanguage().equals("Bangla")){
            w.setText("এই সিস্টেমে এখনও কাজ চলছে");
        }

        btm = findViewById(R.id.bottom_nav);
        btm.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_home));
        btm.add(new MeowBottomNavigation.Model(2,R.drawable.baseline_burst_mode_24));
        btm.add(new MeowBottomNavigation.Model(3,R.drawable.ic_exam));
        btm.add(new MeowBottomNavigation.Model(4,R.drawable.ic_baseline_dashborad));
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
                        openCourse();
                        break;
                    case 3:
                        openExam();
                        break;
                    case 4:
                        openDashboard();
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
        CourseActivity.this.finish();
    }
    private  void  replace(Fragment fragment){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }

    public void openUserProfile(){
        Intent intent = new Intent(this, userProfile.class);
        startActivity(intent);
        CourseActivity.this.finish();
    }
    public void openCourse(){
        Intent intent = new Intent(this,CourseActivity.class);
        startActivity(intent);
        CourseActivity.this.finish();
    }
    public  void openExam(){
        Intent intent = new Intent(this, AllExamCatActivity.class);
        startActivity(intent);
        CourseActivity.this.finish();
    }
    public void openDashboard(){
        Intent intent = new Intent(this, LeaderBoard.class);
        startActivity(intent);
        CourseActivity.this.finish();
    }
}