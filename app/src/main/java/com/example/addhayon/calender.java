package com.example.addhayon;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnDateSelectedListener;
import org.naishadhparmar.zcustomcalendar.Property;

import java.util.Calendar;
import java.util.HashMap;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class calender extends AppCompatActivity {
    CustomCalendar customCalendar;
    private TextView shedule, cls,exm, cd;
    private MeowBottomNavigation btm;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);


        shedule = findViewById(R.id.shedule);
        cls = findViewById(R.id.cls);
        exm =findViewById(R.id.exm2);
        cd = findViewById(R.id.cd);

        if(DBQurey.myProfile.getLanguage().equals("Bangla")){
            shedule.setText("সময়সূচী");
            cls.setText("ক্লাস");
            exm.setText("পরীক্ষা");
            cd.setText("বর্তমান তারিখ");
        }

        //---------------------Calendar--------------------------
        customCalendar = findViewById(R.id.custom_calendar);

        HashMap<Object, Property> descHashMap = new HashMap<>();

        Property defaultProperty = new Property();
        defaultProperty.layoutResource = R.layout.default_view;
        defaultProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("default",defaultProperty);

        Property currentProperty = new Property();
      currentProperty.layoutResource = R.layout.current_view;
        currentProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("current",currentProperty);

        Property examProperty = new Property();
        examProperty.layoutResource = R.layout.exam_view;
        examProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("exam",examProperty);

        Property classProperty = new Property();
        classProperty.layoutResource = R.layout.class_view;
        classProperty.dateTextViewResource = R.id.text_view;
        descHashMap.put("class",classProperty);


        customCalendar.setMapDescToProp(descHashMap);

        HashMap<Integer,Object>dateHashMap = new HashMap<>();

        Calendar calendar = Calendar.getInstance();

        dateHashMap.put(calendar.get(Calendar.DAY_OF_MONTH),"current");
        dateHashMap.put(1,"class");
        dateHashMap.put(2,"exam");
        dateHashMap.put(5,"class");
        dateHashMap.put(7,"exam");
        dateHashMap.put(14,"class");
        dateHashMap.put(15,"exam");
        dateHashMap.put(16,"class");
        dateHashMap.put(17,"exam");
        dateHashMap.put(19,"class");
        dateHashMap.put(25,"exam");
        dateHashMap.put(26,"exam");
        dateHashMap.put(29,"class");
        dateHashMap.put(30,"class");
        dateHashMap.put(31,"exam");
        customCalendar.setDate(calendar,dateHashMap);

        customCalendar.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(View view, Calendar selectedDate, Object desc) {
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                String sDate = "";
                boolean dateCheck = false;
                if(day == 1) {
                    dateCheck = true;
                    if(DBQurey.myProfile.getLanguage().equals("Bangla")){
                        sDate = "সকাল ১০টায় গণিত ক্লাস";
                    }else{
                        sDate = "Math class at 10.00am";
                    }


                } else if(day == 2 ){
                    dateCheck = true;
                    if(DBQurey.myProfile.getLanguage().equals("Bangla")){
                        sDate = "সকাল ১০টায় গণিত পরীক্ষা";
                    }else {
                        sDate = "Math exam at 10.00am";
                    }

                } else if(day == 3 ){
                    dateCheck = true;
                    if(DBQurey.myProfile.getLanguage().equals("Bangla")){
                        sDate = "সকাল ১০টায় ইংরেজি ক্লাস";
                    }else {
                        sDate = "English class at 10.00am";
                    }

                }else if(day == 4 ){
                    dateCheck = true;
                    if(DBQurey.myProfile.getLanguage().equals("Bangla")){
                        sDate = "ইংরেজি পরীক্ষা সকাল ১০টায়";
                    }else {
                        sDate = "English exam at 10.00am";
                    }

                }else if(day == 5 ){
                    dateCheck = true;
                    if(DBQurey.myProfile.getLanguage().equals("Bangla")){
                        sDate = " দুপুর ১টায় ইংরেজি ক্লাস";
                    }else {
                        sDate = "English class at 10.00am";
                    }

                }else if(day == 7 ){
                    dateCheck = true;
                    sDate ="English exam at 1.00am";

                } else if(day == 15 || day == 17 || day == 25 || day == 26  ){
                    dateCheck = true;
                    if(DBQurey.myProfile.getLanguage().equals("Bangla")){
                        sDate = " সকাল ১০টায় পরীক্ষা";
                    }else {
                        sDate = "Class at 10.00am";
                    }

                }else if(day == 14 || day == 16 || day == 19 || day == 29  ){
                    dateCheck = true;
                    if(DBQurey.myProfile.getLanguage().equals("Bangla")){
                        sDate = " সকাল ১০টায় ক্লাস";
                    }else {
                        sDate = "Class at 10.00am";
                    }

                }

                if(dateCheck){
                    Toast.makeText(getApplicationContext(),
                            sDate,Toast.LENGTH_SHORT).show();
                }


            }
        });
        //------------------------------------------------------------------


        //-------------Meow Buttom--------------------------------
        btm = findViewById(R.id.bottom_nav);
        btm.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_home));
        btm.add(new MeowBottomNavigation.Model(2,R.drawable.ic_exam));
        btm.add(new MeowBottomNavigation.Model(3,R.drawable.ic_baseline_calendar));
        btm.add(new MeowBottomNavigation.Model(4,R.drawable.ic_baseline_location));
        btm.add(new MeowBottomNavigation.Model(5,R.drawable.ic_baseline_person));

        btm.show(3,true);
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
        //-------------------------------------------------------------------------------
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
    public void openMap(){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}