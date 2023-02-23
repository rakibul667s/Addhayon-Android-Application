package com.example.addhayon;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.codesgood.views.JustifiedTextView;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.io.File;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class dashboard extends AppCompatActivity {

    private MeowBottomNavigation btm;
    private Button btnID;
    private ImageView chat;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference root = database.getReference().child("users");
    private FirebaseStorage storage;
    private FirebaseAuth mAuth, auth;
    private TextView menuTitle;
    private static final String DOWNLOAD_URL = "https://drive.google.com/uc?export=1QBMsMy31D9xDeXOiL53L3yLQZ2gz1iHO";
    private long downloadId;
    BroadcastReceiver downloadReceiver;
    TextView update2;




    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        menuTitle = findViewById(R.id.menuTitle);

        if(DBQurey.myProfile.getVname().equals(String.valueOf(BuildConfig.VERSION_NAME))){


        }else{
            if(DBQurey.myProfile.getUpcheck().equals(String.valueOf(BuildConfig.VERSION_CODE))){

            }else {

                AlertDialog.Builder builder = new AlertDialog.Builder(dashboard.this);
                builder.setCancelable(true);

                View view = getLayoutInflater().inflate(R.layout.update, null);
                update2 = view.findViewById(R.id.update);
                TextView Mb = view.findViewById(R.id.Mb);
                TextView version = view.findViewById(R.id.version);
                TextView title = view.findViewById(R.id.title);
                TextView thanks = view.findViewById(R.id.thanks);
                JustifiedTextView pra1 = view.findViewById(R.id.pra1);
                JustifiedTextView pra2 = view.findViewById(R.id.pra2);

                if(DBQurey.myProfile.getLanguage().equals("Bangla")){
                    title.setText("আপডেট অধ্যয়ন");
                    Mb.setText("ডাউনলোড সাইজ: " + DBQurey.myProfile.getMb() + " এমবি");
                    version.setText("সংস্করণ: " + DBQurey.myProfile.getVname());
                    pra1.setText("'অধ্যয়ন' আপনাকে সর্বশেষ সংস্করণে আপডেট করার পরামর্শ দেয়। দয়া করে এই অ্যাপটি আপডেট করুন এবং সর্বশেষ বৈশিষ্ট্যগুলি ব্যবহার করুন।");
                    pra2.setText("ডাউনলোড শেষ হলে। তারপর ডাউনলোড ফাইল 'Addhayon.apk' বা আপনার ডিভাইস Storage > Download > Addhayon > 'Addhayon.apk' চেক করুন এবং এটি ইনস্টল করুন।");
                    update2.setText("আপডেট");
                    thanks.setText("ধন্যবাদ");

                }else{
                    Mb.setText("Download size: " + DBQurey.myProfile.getMb() + " MB");
                    version.setText("Version: " + DBQurey.myProfile.getVname());
                }


                builder.setView(view);
                AlertDialog alertDialog = builder.create();

                update2.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
                    @Override
                    public void onClick(View v) {
                        String url2 = DBQurey.myProfile.getUplink();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (ContextCompat.checkSelfPermission(dashboard.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                download(url2);
                            } else {
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
                            }
                        } else {
                            download(url2);
                        }

                    }
                });
                alertDialog.show();
                alertDialog.setCancelable(false);
            }
        }

        if(DBQurey.myProfile.getLanguage().equals("Bangla")) {
            menuTitle.setText("অধ্যয়ন");
        }else{
            menuTitle.setText("Addhayon");
        }

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        String phone = DBQurey.myProfile.getPhn();
        String name = DBQurey.myProfile.getName();


        root.child(uid).child("name").setValue(name);
        root.child(uid).child("uid").setValue(uid);
        chat = findViewById(R.id.chat);

        FirebaseFirestore.getInstance()
                .collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .update("ID", uid);

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
        Intent intent = new Intent(this,ChatMainActivity.class);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                update2.performClick();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void download(String link){
        String filename = URLUtil.guessFileName(link,null,null);
        String folder = Environment.getExternalStorageDirectory()+"/Download/Addhayon/";
        File newFile = new File(folder, filename);
        try {

                DownloadManager.Request request =new DownloadManager.Request(Uri.parse(link));
                request.allowScanningByMediaScanner();
                request.setDescription(filename)
                        .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI)
                        .setDestinationUri(Uri.fromFile(newFile))
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setVisibleInDownloadsUi(true)
                        .setTitle(getResources().getString(R.string.app_name));
                DownloadManager manager = (DownloadManager)  getSystemService(DOWNLOAD_SERVICE);
                assert manager != null;
                long downloadID = manager.enqueue(request);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}