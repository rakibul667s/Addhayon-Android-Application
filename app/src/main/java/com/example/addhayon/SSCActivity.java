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

public class SSCActivity extends AppCompatActivity {
    private MeowBottomNavigation btm;
    private LinearLayout ssc;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sscactivity);
        ssc =findViewById(R.id.ssc);

        btm = findViewById(R.id.bottom_nav);
        btm.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_home));
        btm.add(new MeowBottomNavigation.Model(2,R.drawable.baseline_burst_mode_24));
        btm.add(new MeowBottomNavigation.Model(3,R.drawable.baseline_auto_awesome_motion_24));
        btm.add(new MeowBottomNavigation.Model(4,R.drawable.ic_exam));
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
                        openCourse();
                        break;
                    case 3:
                        openSSC();
                        break;
                    case 4:
                        openExam();
                        break;
                    case 5:
                        openUserProfile();;
                        break;

                }
                return null;
            }
        });
        ssc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SSCActivity.this,sSSCActivity.class);
                startActivity(intent);
                SSCActivity.this.finish();
            }
        });
    }
    private void openHome(){
        Intent intent = new Intent(this,dashboard.class);
        startActivity(intent);
        SSCActivity.this.finish();
    }

    public void openUserProfile(){
        Intent intent = new Intent(this, userProfile.class);
        startActivity(intent);
        SSCActivity.this.finish();
    }
    public void openCourse(){
        Intent intent = new Intent(this,CourseActivity.class);
        startActivity(intent);
        SSCActivity.this.finish();
    }
    public  void openExam(){
        Intent intent = new Intent(this, AllExamCatActivity.class);
        startActivity(intent);
        SSCActivity.this.finish();
    }
    public void openSSC(){
        Intent intent = new Intent(this, SSCActivity.class);
        startActivity(intent);
        SSCActivity.this.finish();
    }
}