package com.example.addhayon;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class AboutActivity extends AppCompatActivity {
    private MeowBottomNavigation btm;
    private TextView bio, name;
    private TextView details;
    private TextView family;
    private TextView contact;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        bio = findViewById(R.id.bio);
        String text = "<font color=#888484>I'm a </font><font color=#FA9E9E>Developer</font><font color=#888484> from</font><font color=#FA9E9E> Bogura, Bangladesh</font><font color=#888484>. Good knowledge in </font><font color=#FA9E9E>Font-End </font><font color=#888484>and </font><font color=#FA9E9E>Back-End </font><font color=#888484>Development.</font><font color=#888484> I love time spending for</font><font color=#FA9E9E> Programming</font><font color=#888484>. And also like to work with</font><font color=#FA9E9E> Team</font><font color=#888484>. In this time,</font><font color=#FA9E9E> Addhayon </font><font color=#888484>mobile application it's my latest project.</font>";
        bio.setText(Html.fromHtml(text));

        details = findViewById(R.id.details2);
        family = findViewById(R.id.family2);
        contact = findViewById(R.id.contact2);
        name = findViewById(R.id.name);

        if(DBQurey.myProfile.getLanguage().equals("Bangla")){
            name.setText("মো: রাকিবুল ইসলাম");
            details.setText("বিস্তারিত");
            family.setText("পারিবারিক তথ্য");
            contact.setText("যোগাযোগ করুন");

        }


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