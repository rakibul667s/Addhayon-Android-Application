package com.example.addhayon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class ExamDashborad extends AppCompatActivity {
    private Fragment frame;
    private MeowBottomNavigation btm;
    private Button btnID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_dashborad);


        btm = findViewById(R.id.bottom_nav);
        btm.add(new MeowBottomNavigation.Model(1, R.drawable.ic_baseline_home));
        btm.add(new MeowBottomNavigation.Model(2, R.drawable.ic_exam));
        btm.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_calendar));
        btm.add(new MeowBottomNavigation.Model(4, R.drawable.ic_baseline_dashborad));
        btm.add(new MeowBottomNavigation.Model(5, R.drawable.ic_baseline_person));


        btm.show(2, true);
      // replace(new com.example.addhayon.examDashboardFragment());
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
                        openCalendar();
                        break;
                    case 4:
                        openMap();
                        break;
                    case 5:
                        openUserProfile();
                        break;

                }
                return null;
            }
        });

    }
        private  void  replace(Fragment fragment){
            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame,fragment);
            transaction.commit();
        }
        public void openExamCat(){
            Intent intent = new Intent(this, AllExamCatActivity.class);
            startActivity(intent);
        }
        public void openDashboard(){
             Intent intent = new Intent(this, dashboard.class);
             startActivity(intent);
        }

        public void openUserProfile(){
            Intent intent = new Intent(this, userProfile.class);
            startActivity(intent);
        }
        public  void openCalendar(){
            Intent intent = new Intent(this, calender.class);
            startActivity(intent);
        }
        public void openMap(){
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        }
}