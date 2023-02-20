package com.example.addhayon;

import static com.example.addhayon.DBQurey.g_selected_test_index;
import static com.example.addhayon.DBQurey.g_testList;
import static com.example.addhayon.DBQurey.g_userCount;
import static com.example.addhayon.DBQurey.g_usersList;
import static com.example.addhayon.DBQurey.myPerformance;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.PopupMenu;
import android.content.Intent;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.addhayon.Adapters.BookmarksAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
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
    private FirebaseAuth mAuth, userAuth;
    private FirebaseUser firebaseUser;
    private TextView name;
    private TextView rank;
    private TextView score;
    private TextView bio;
    private TextView sclClg;
    private TextView rankText, scoreText, contactText;

    private TextView birth, address, email2, phn;
    private String pdata;
    private Uri uri;

    public Bitmap bitmap;
    private String pName ,pEmail , pPhn, pAddress, pBirth, pScl;
    private FirebaseDatabase database;
    private String userId, userAccount;
    private FirebaseStorage storage;
    private StorageReference storage1Ref, storage2Ref;
    private  ExecutorService service;
    private LinearLayout rankB;
    private PopupMenu popupMenu;
    private MenuItem lanMenu, actMenu, accountMenu,rankMenu, sqMenu, tMenu, aboutMenu, editMenu, deleteMenu,logMenu;




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
        rankB =findViewById(R.id.rankB);

        button1 = (ImageView) findViewById(R.id.button1);
        popupMenu = new PopupMenu(this, button1);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
        lanMenu = popupMenu.getMenu().findItem(R.id.lan);
        actMenu = popupMenu.getMenu().findItem(R.id.act);
        accountMenu = popupMenu.getMenu().findItem(R.id.accountMenu);
        rankMenu = popupMenu.getMenu().findItem(R.id.rank);
        sqMenu = popupMenu.getMenu().findItem(R.id.mark);
        tMenu = popupMenu.getMenu().findItem(R.id.timeS);
        aboutMenu = popupMenu.getMenu().findItem(R.id.about);
        editMenu = popupMenu.getMenu().findItem(R.id.editProfile);
        deleteMenu = popupMenu.getMenu().findItem(R.id.delete);
        logMenu = popupMenu.getMenu().findItem(R.id.logout);



        scoreText = findViewById(R.id.scoreText);
        rankText = findViewById(R.id.rankText);
        contactText = findViewById(R.id.contactText);


        if(DBQurey.myProfile.getLanguage().equals("Bangla")){
            rankText.setText("পদমর্যাদা");
            scoreText.setText("সম্পূর্ণ ফলাফল");
            contactText.setText("যোগাযোগ করুন :");
            lanMenu.setTitle("ভাষা সেট করুন");
            actMenu.setTitle("সমস্ত কার্যক্রম");
            accountMenu.setTitle("অ্যাকাউন্ট সেটিংস");
            rankMenu.setTitle("পদমর্যাদা");
            sqMenu.setTitle("চিহ্নিত প্রশ্ন");
            tMenu.setTitle("সময়সূচী");
            aboutMenu.setTitle("সম্পর্কিত !");
            editMenu.setTitle("প্রোফাইল সম্পাদনা করুন");
            deleteMenu.setTitle("একাউন্ট মুছে ফেলুন");
            logMenu.setTitle("প্রস্থান");

        }else {
            rankText.setText("Rank");
            scoreText.setText("Total Score");
            contactText.setText("Contact us :");

        }


        userAuth = FirebaseAuth.getInstance();
        firebaseUser = userAuth.getCurrentUser();

        pScl = DBQurey.myProfile.getSclClg();
        pAddress =DBQurey.myProfile.getAddress();
        pBirth = DBQurey.myProfile.getDateofBirth();
        pPhn = DBQurey.myProfile.getPhn();
        pEmail = DBQurey.myProfile.getEmail();
        pName = DBQurey.myProfile.getName();


        storage = FirebaseStorage.getInstance();


//        service = Executors.newSingleThreadExecutor();
//
//        service.execute(new Runnable() {
//            @Override
//            public void run() {
//                allImageSet();
//            }
//
//        });


        loadProfile();

        rankB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLeaderBoard();
            }
        });





        //---------------------------memu item--------------------------------------------

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
                if(id == R.id.rank){
                    Intent intent = new Intent(userProfile.this, LeaderBoard.class);
                    startActivity(intent);
                }
                if(id == R.id.timeS){
                    Intent intent = new Intent(userProfile.this, calender.class);
                    startActivity(intent);
                }
                if(id == R.id.mark){
                    Intent intent = new Intent(userProfile.this, BookMaksActivity.class);
                    startActivity(intent);
                }
                if(id == R.id.delete){
                    deleteAccount();
                }
                if(id == R.id.about){
                    about();
                }
                if(id == R.id.Bangla){
                    DBQurey.myProfile.setLanguage("Bangla");
                    FirebaseFirestore.getInstance()
                    .collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .update("LANGUAGE", "Bangla");
                    Intent intent = new Intent(userProfile.this, userProfile.class);
                    startActivity(intent);

                }
                if(id == R.id.English){
                    DBQurey.myProfile.setLanguage("English");
                    FirebaseFirestore.getInstance()
                            .collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .update("LANGUAGE", "English");
                    Intent intent = new Intent(userProfile.this, userProfile.class);
                    startActivity(intent);

                }
                if(id == R.id.Hindi){
                    if(DBQurey.myProfile.getLanguage().equals("Bangla")) {
                        Toast.makeText(userProfile.this, "উপলব্ধ নয়",
                                Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(userProfile.this, "Not Available",
                                Toast.LENGTH_SHORT).show();
                    }
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
        String pI = DBQurey.myProfile.getProfileImg();

        if(pI.length() == 0){
            profile_image.setImageResource(R.drawable.baseline_puser);
        }
        if(pI.length() != 0){
            Glide.with(userProfile.this)
                    .load(DBQurey.myProfile.getProfileImg())
                    .into(profile_image);
        }
        Glide.with(userProfile.this)
                .load(DBQurey.myProfile.getCoverImg())
                .into(cover_img);
        if(DBQurey.g_usersList.size() == 0){
            DBQurey.getTopUsers(new MyCompleteListener(){
                @Override
                public void onSuccess(){
                    if(myPerformance.getScore() != 0){
                        if(!DBQurey.isMeOnTopList){
                            calcukateRank();
                        }
                        score.setText(""+myPerformance.getScore());
                        rank.setText(""+myPerformance.getRank());
                    }
                }
                @Override
                public void onFailure(){
                    if(DBQurey.myProfile.getLanguage().equals("Bangla")) {
                        Toast.makeText(userProfile.this, "কিছু ভুল হয়েছে ! পরে চেষ্টা করুন",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(userProfile.this, "Something went wrong ! Please try Later.",
                                Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }else{
            score.setText(""+myPerformance.getScore());
            if(myPerformance.getScore() != 0) {
                rank.setText("" + myPerformance.getRank());
            }
        }

    }
    public void allImageSet(){
        storage1Ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onSuccess(Uri uri) {
                String url = uri.toString();
                if(url.isEmpty()){

                }
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

    private void calcukateRank(){
        int lowTopScore = g_usersList.get(g_usersList.size()-1).getScore();
        int remaining_slots = g_userCount - 20;
        int myslot = (myPerformance.getScore()*remaining_slots)/lowTopScore;

        int rank;

        if(lowTopScore != myPerformance.getScore()){
            rank = g_userCount - myslot;
        }else{
            rank = 21;
        }
        myPerformance.setRank(rank);

    }



    public void openLeaderBoard(){
        Intent intent = new Intent(this, LeaderBoard.class);
        startActivity(intent);
        userProfile.this.finish();
    }
    public void about(){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
        userProfile.this.finish();
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

    private void deleteAccount(){
        AlertDialog.Builder builder = new AlertDialog.Builder(userProfile.this);
        builder.setCancelable(true);

        View view = getLayoutInflater().inflate(R.layout.delete_diolog,null);

        Button cancelB = view.findViewById(R.id.cancelB);
        Button confirmB =view.findViewById(R.id.confrimB);
        TextView title = view.findViewById(R.id.title2);
        TextView content = view.findViewById(R.id.content);
        TextView c = view.findViewById(R.id.c);

        if(DBQurey.myProfile.getLanguage().equals("Bangla")){
            title.setText("মুছে ফেলা");
            content.setText("আপনি কি নিশ্চিত ?");
            c.setText("আপনার একাউন্ট মুছে ফেলুন !");
            cancelB.setText("বাতিল");
            confirmB.setText("হ্যাঁ");

        }

        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        confirmB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                firebaseUser.delete();
                if(DBQurey.myProfile.getLanguage().equals("Bangla")) {
                    Toast.makeText(userProfile.this, "সফলভাবে আপনার অ্যাকাউন্ট মুছে ফেলা হয়েছে",
                            Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(userProfile.this, "Delete your account successfully",
                            Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(userProfile.this, sign_in_page.class);
                startActivity(intent);
                userProfile.this.finish();
            }
        });
        alertDialog.show();

    }



}