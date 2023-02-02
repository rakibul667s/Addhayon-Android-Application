package com.example.addhayon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class AboutActivity extends AppCompatActivity {
    private MeowBottomNavigation btm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        btm = findViewById(R.id.bottom_nav);
        btm.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_home));
        btm.add(new MeowBottomNavigation.Model(2,R.drawable.ic_exam));
        btm.add(new MeowBottomNavigation.Model(3, R.drawable.baseline_remember_me_24));
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
                        openCalendar();
                        break;
                    case 3:
                        openAbout();
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
    public void openHome(){
        Intent intent = new Intent(AboutActivity.this, dashboard.class);
        startActivity(intent);
        AboutActivity.this.finish();
    }
    public void openUserProfile(){
        Intent intent = new Intent(AboutActivity.this, userProfile.class);
        startActivity(intent);
        AboutActivity.this.finish();
    }
    public void openAbout(){
        Intent intent = new Intent(AboutActivity.this, AboutActivity.class);
        startActivity(intent);
        AboutActivity.this.finish();
    }
    public  void openCalendar(){
        Intent intent = new Intent(AboutActivity.this, calender.class);
        startActivity(intent);
        AboutActivity.this.finish();
    }
    public void openMap(){
        Intent intent = new Intent(AboutActivity.this, MapsActivity.class);
        startActivity(intent);
        AboutActivity.this.finish();
    }
}