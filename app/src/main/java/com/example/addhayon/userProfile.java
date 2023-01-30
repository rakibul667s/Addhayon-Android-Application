package com.example.addhayon;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.PopupMenu;
import android.content.Intent;


import com.bumptech.glide.Glide;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class userProfile extends AppCompatActivity {
    private ImageView cover_img;
    public CircleImageView profile_image;
    private ImageView button1;
    private MeowBottomNavigation btm;
    private FirebaseAuth mAuth;
    private TextView name;
    private TextView rank;
    private TextView score;
    private TextView bio;
    private TextView sclClg;

    private TextView birth, address, email2, phn;
    private String pdata;
    private Uri uri;

    public Bitmap bitmap;
    private String pName ,pEmail , pPhn, pAddress, pBirth, pScl;
    private FirebaseDatabase database;
    private String userId;
    private FirebaseStorage storage;
    private StorageReference storage1Ref, storage2Ref;
    private  ExecutorService service;



    @SuppressLint("MissingInflatedId")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        cover_img =findViewById(R.id.cover_img);
        profile_image =findViewById(R.id.profile_image);
        name = findViewById(R.id.name);
        bio = findViewById(R.id.bio);
        rank = findViewById(R.id.rank);
        score = findViewById(R.id.totalScore);
        sclClg = findViewById(R.id.sclclg);
        address = findViewById(R.id.address2);
        birth = findViewById(R.id.birth);
        phn = findViewById(R.id.phone);
        email2 = findViewById(R.id.email);

        pScl = DBQurey.myProfile.getSclClg();
        pAddress =DBQurey.myProfile.getAddress();
        pBirth = DBQurey.myProfile.getDateofBirth();
        pPhn = DBQurey.myProfile.getPhn();
        pEmail = DBQurey.myProfile.getEmail();
        pName = DBQurey.myProfile.getName();


        storage = FirebaseStorage.getInstance();
        storage1Ref = storage.getReference().child("allUsers/"+userId+"/image_profile.jpg");
        storage2Ref = storage.getReference().child("allUsers/"+userId+"/image_cover.jpg");


        service = Executors.newSingleThreadExecutor();

        service.execute(new Runnable() {
            @Override
            public void run() {
                allImageSet();
            }

        });


        loadProfile();





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
                if(id == R.id.editProfile){
                    Intent intent = new Intent(userProfile.this, EditProfileActivity.class);
                    startActivity(intent);
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
        btm.add(new MeowBottomNavigation.Model(1, R.drawable.ic_baseline_home));
        btm.add(new MeowBottomNavigation.Model(2, R.drawable.ic_exam));
        btm.add(new MeowBottomNavigation.Model(3, R.drawable.ic_baseline_calendar));
        btm.add(new MeowBottomNavigation.Model(4, R.drawable.ic_baseline_location));
        btm.add(new MeowBottomNavigation.Model(5, R.drawable.ic_baseline_person));

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

    private void loadProfile(){
        name.setText(pName);
        bio.setText(DBQurey.myProfile.getBio());
        sclClg.setText(pScl);
        address.setText(pAddress);
        birth.setText(pBirth);
        email2.setText(pEmail);
        phn.setText(pPhn);
        score.setText(String.valueOf(DBQurey.myPerformance.getScore()));


    }
    public void allImageSet(){
        storage1Ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                Glide.with(userProfile.this)
                        .load(url)
                        .into(profile_image);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        storage2Ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                Glide.with(userProfile.this)
                        .load(url)
                        .into(cover_img);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

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
        userProfile.this.finish();

    }
    public void openExam(){
        Intent intent = new Intent(this, AllExamCatActivity.class);
        startActivity(intent);
        userProfile.this.finish();
    }
    public void dashBorad(){
        Intent intent = new Intent(this, dashboard.class);
        startActivity(intent);
        userProfile.this.finish();
    }
    public void openCalender(){
        Intent intent = new Intent(this, calender
                .class);
        startActivity(intent);
        userProfile.this.finish();
    }
    public void openMap(){
        Intent intent = new Intent(this, MapsActivity
                .class);
        startActivity(intent);
        userProfile.this.finish();
    }
    public void openLoginPage(){
        mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent intent = new Intent(this, sign_in_page.class);
        startActivity(intent);
        userProfile.this.finish();
    }
    //---------------------Meow btn end--------------------------------------------





}