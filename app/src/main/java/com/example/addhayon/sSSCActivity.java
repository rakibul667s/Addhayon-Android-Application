package com.example.addhayon;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class sSSCActivity extends AppCompatActivity {
    private MeowBottomNavigation btm;
    private LinearLayout bn1st,bn2nd,en1st,en2nd,math,islam,ict,gs,phy,chem,bio,hm;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssscactivity);
        bn1st = findViewById(R.id.bn1st);
        bn2nd = findViewById(R.id.bn2nd);
        en1st = findViewById(R.id.en1st);
        en2nd = findViewById(R.id.en2nd);
        math = findViewById(R.id.math);
        islam = findViewById(R.id.islam);
        ict = findViewById(R.id.ict);
        gs = findViewById(R.id.gs);
        phy = findViewById(R.id.phy);
        chem = findViewById(R.id.chem);
        bio = findViewById(R.id.bio);
        hm = findViewById(R.id.hm);

        bn1st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sSSCActivity.this,videoCourseActivity.class);
                intent.putExtra("link", "https://youtube.com/playlist?list=PLuaHF6yUT-7388l6TKoRfchY5eR33HGub");
                startActivity(intent);
                sSSCActivity.this.finish();
            }
        });
        bn2nd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sSSCActivity.this,videoCourseActivity.class);
                intent.putExtra("link", "https://youtube.com/playlist?list=PLuaHF6yUT-73XbJr_mi0MPm-nYJjNgtB4");
                startActivity(intent);
                sSSCActivity.this.finish();
            }
        });
        en1st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sSSCActivity.this,videoCourseActivity.class);
                intent.putExtra("link", "https://youtube.com/playlist?list=PLuaHF6yUT-703uSJPab0zVP0Yn3KWn57X");
                startActivity(intent);
                sSSCActivity.this.finish();
            }
        });
        en2nd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sSSCActivity.this,videoCourseActivity.class);
                intent.putExtra("link", "https://youtube.com/playlist?list=PLuaHF6yUT-73RBZgg1tWQCgAC09DUbcXe");
                startActivity(intent);
                sSSCActivity.this.finish();
            }
        });
        math.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sSSCActivity.this,videoCourseActivity.class);
                intent.putExtra("link", "https://youtube.com/playlist?list=PLEnvNvpjnLbFmUD42XqdhAF6vegnw0jZ1");
                startActivity(intent);
                sSSCActivity.this.finish();
            }
        });
        islam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sSSCActivity.this,videoCourseActivity.class);
                intent.putExtra("link", "https://youtube.com/playlist?list=PLEnvNvpjnLbGMILeddo2bSKSzyiI-4n7u");
                startActivity(intent);
                sSSCActivity.this.finish();
            }
        });
        ict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sSSCActivity.this,videoCourseActivity.class);
                intent.putExtra("link", "https://youtube.com/playlist?list=PLuaHF6yUT-73Zonnu6QXq_aOxUIDPoiv_");
                startActivity(intent);
                sSSCActivity.this.finish();
            }
        });
        gs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sSSCActivity.this,videoCourseActivity.class);
                intent.putExtra("link", "https://youtube.com/playlist?list=PLuaHF6yUT-72aypDYG646V6GhUFtcBFTo");
                startActivity(intent);
                sSSCActivity.this.finish();
            }
        });
        phy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sSSCActivity.this,videoCourseActivity.class);
                intent.putExtra("link", "https://youtube.com/playlist?list=PLuaHF6yUT-71lTwNfpc7av_k4ZS6vbcmt");
                startActivity(intent);
                sSSCActivity.this.finish();
            }
        });
        chem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sSSCActivity.this,videoCourseActivity.class);
                intent.putExtra("link", "https://youtube.com/playlist?list=PLuaHF6yUT-73vbQ2tMvZQHa0lKJBTqkek");
                startActivity(intent);
                sSSCActivity.this.finish();
            }
        });
        bio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sSSCActivity.this,videoCourseActivity.class);
                intent.putExtra("link", "https://youtube.com/playlist?list=PLuaHF6yUT-7297TYrojVjWkn-WNVqKVPI");
                startActivity(intent);
                sSSCActivity.this.finish();
            }
        });
        hm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sSSCActivity.this,videoCourseActivity.class);
                intent.putExtra("link", "https://youtube.com/playlist?list=PLEnvNvpjnLbF7a_TrG4wml1kRN7QaRxeX");
                startActivity(intent);
                sSSCActivity.this.finish();
            }
        });

        btm = findViewById(R.id.bottom_nav);
        btm.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_home));
        btm.add(new MeowBottomNavigation.Model(2,R.drawable.baseline_burst_mode_24));
        btm.add(new MeowBottomNavigation.Model(3,R.drawable.baseline_auto_awesome_motion_24));
        btm.add(new MeowBottomNavigation.Model(4,R.drawable.baseline_check_box_outline_blank_24));
        btm.add(new MeowBottomNavigation.Model(5,R.drawable.ic_baseline_person));



        btm.show(4,true);
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
                        openCat();
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
        sSSCActivity.this.finish();
    }

    public void openUserProfile(){
        Intent intent = new Intent(this, userProfile.class);
        startActivity(intent);
        sSSCActivity.this.finish();
    }
    public void openCourse(){
        Intent intent = new Intent(this,CourseActivity.class);
        startActivity(intent);
        sSSCActivity.this.finish();
    }
    public  void openCat(){
        Intent intent = new Intent(this, sSSCActivity.class);
        startActivity(intent);
        sSSCActivity.this.finish();
    }
    public void openSSC(){
        Intent intent = new Intent(this, SSCActivity.class);
        startActivity(intent);
        sSSCActivity.this.finish();
    }
}