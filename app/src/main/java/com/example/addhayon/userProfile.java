package com.example.addhayon;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.PopupMenu;
import android.content.Intent;


import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class userProfile extends AppCompatActivity {
    private ImageView cover_img;
    private CircleImageView profile_image;
    private ImageView button1;
    private MeowBottomNavigation btm;
    private FirebaseAuth mAuth;
    private TextView name,rank,score;




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        cover_img =findViewById(R.id.cover_img);
        profile_image =findViewById(R.id.profile_image);
        name = findViewById(R.id.name);
        rank = findViewById(R.id.rank);
        score = findViewById(R.id.totalScore);

        //-------------------set Profile----------------------
        String pName = DBQurey.myProfile.getName();
        name.setText(pName);
        score.setText(String.valueOf(DBQurey.myPerformance.getScore()));




        //---------------------------memu item--------------------------------------------
        button1 = (ImageView) findViewById(R.id.button1);
        PopupMenu popupMenu = new PopupMenu(this, button1);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.logout){
                    openLoginPage();
                }


                return false;
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });
        //-------------------------------------------------------------------------------



        //--------------------------------meow btn---------------------------------------
        btm = findViewById(R.id.bottom_nav);
        btm.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_home));
        btm.add(new MeowBottomNavigation.Model(2,R.drawable.ic_exam));
        btm.add(new MeowBottomNavigation.Model(3,R.drawable.ic_baseline_calendar));
        btm.add(new MeowBottomNavigation.Model(4,R.drawable.ic_baseline_location));
        btm.add(new MeowBottomNavigation.Model(5,R.drawable.ic_baseline_person));

        btm.show(5,true);
       // replace(new com.example.addhayon.HomeFragment());
        btm.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()){
                    case 1:
                        dashBorad();
                        break;
                    case 2:
                        openExam();
                        break;
                    case 3:
                        openCalender();
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
        //-----------------------------------------------------------------------------------------


    }




    //---------------------Meow btn-------------------------------------------------
    private  void  replace(Fragment fragment){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }

    public void openUserProfile(){
        Intent intent = new Intent(this, userProfile.class);
        startActivity(intent);
    }
    public void openExam(){
        Intent intent = new Intent(this, AllExamCatActivity.class);
        startActivity(intent);
    }
    public void dashBorad(){
        Intent intent = new Intent(this, dashboard.class);
        startActivity(intent);
    }
    public void openCalender(){
        Intent intent = new Intent(this, calender
                .class);
        startActivity(intent);
    }
    public void openMap(){
        Intent intent = new Intent(this, MapsActivity
                .class);
        startActivity(intent);
    }
    public void openLoginPage(){
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent intent = new Intent(this, sign_in_page.class);
        startActivity(intent);
    }
    //---------------------Meow btn end--------------------------------------------





}