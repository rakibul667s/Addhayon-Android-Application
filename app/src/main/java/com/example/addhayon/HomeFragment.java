package com.example.addhayon;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    ImageSlider imageSlider;
    ImageSlider imageSlider2;

    private LinearLayout exam2, aboutMe, course, shop,note,cal;

    private TextView seeMore1,seeMore2, bExam, bCourse,
            bEvent, bLocation, bRank, bShop, bNote,bAbout,
            bLetest,bExtra, bCal,bnote2, bNews, bHealth,
            bC1,bC2,bC3,bC4,bC5,bC6,
            bP1,bP2,bP3,bP4,bP5,bP6;


    Activity context;
    public HomeFragment() {
        // Required empty public constructor
    }
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        exam2 = view.findViewById(R.id.rank);
        //aboutMe = view.findViewById(R.id.about);
        course = view.findViewById(R.id.course);
        seeMore1 = view.findViewById(R.id.seeMore1);
        seeMore2 = view.findViewById(R.id.seeMore2);
        bExam = view.findViewById(R.id. bExam);
        bCourse = view.findViewById(R.id.bCourse);
        bEvent = view.findViewById(R.id.bEvent);
        bLocation = view.findViewById(R.id.bLocation);
        bRank = view.findViewById(R.id.bRank);
        bShop = view.findViewById(R.id.bShop);
        bNote = view.findViewById(R.id.bNote);
        bAbout = view.findViewById(R.id.bAbout);
        bLetest = view.findViewById(R.id.bLetest);
        bExtra = view.findViewById(R.id.bExtra);
        bCal = view.findViewById(R.id.bCal);
        bnote2 = view.findViewById(R.id.bnote2);
        bNews = view.findViewById(R.id.bNews);
        bHealth = view.findViewById(R.id.bHealth);
        shop = view.findViewById(R.id.shop);
        note = view.findViewById(R.id.note);
        cal = view.findViewById(R.id.cal);

        bC1 = view.findViewById(R.id.bC1);
        bC2 = view.findViewById(R.id.bC2);
        bC3 = view.findViewById(R.id.bC3);
        bC4 = view.findViewById(R.id.bC4);
        bC5 = view.findViewById(R.id.bC5);
        bC6 = view.findViewById(R.id.bC6);


        bP1 = view.findViewById(R.id.bP1);
        bP2 = view.findViewById(R.id.bP2);
        bP3 = view.findViewById(R.id.bP3);
        bP4 = view.findViewById(R.id.bP4);
        bP5 = view.findViewById(R.id.bP5);
        bP6 = view.findViewById(R.id.bP6);





        if(DBQurey.myProfile.getLanguage().equals("Bangla")){
          seeMore1.setText("আরো দেখুন");
          seeMore2.setText("আরো দেখুন");
          bExam.setText("পরীক্ষা");
          bCourse.setText("কোর্স");
          bEvent.setText("ঘটনা");
          bLocation.setText("অবস্থান");
          bRank.setText("পদমর্যাদা");
          bShop.setText("দোকান");
          bNote.setText( "বিঃদ্রঃ");
          bAbout.setText("সম্পর্কিত");
          bLetest.setText("জনপ্রিয় কোর্স");
          bExtra.setText("অতিরিক্ত সুবিধাগুলি");
          bCal.setText("হিসাব");
          bnote2.setText("বিঃদ্রঃ");
          bNews.setText("খবর");
          bHealth.setText("স্বাস্থ্য");

            bC1.setText("বিসিএস প্রিমিয়াম কোর্স");
            bC2.setText("আইসিটি এইচএসসি কোর্স");
            bC3.setText("মৌখিক ইংরেজি");
            bC4.setText("মোবাইল অ্যাপ্লিকেশন");
            bC5.setText("এসএসসি বাংলা");
            bC6.setText("ওয়েব অ্যাপ্লিকেশনয");

            bP1.setText("ক্রয় 10.67k+");
            bP2.setText("ক্রয় 25.4k+");
            bP3.setText("ক্রয় 13.65k+");
            bP4.setText("ক্রয় 8.45k+");
            bP5.setText("ক্রয় 31.28k+");
            bP6.setText("ক্রয় 13.65k+");

        }




        //setData();
        exam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExam();
            }
        });
       // aboutMe.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //openAbout();
//            }
//        });
        course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCourse();
            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Working on this proccess",
                        Toast.LENGTH_SHORT).show();
            }
        });
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Working on this proccess",
                        Toast.LENGTH_SHORT).show();
            }
        });
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Working on this proccess",
                        Toast.LENGTH_SHORT).show();
            }
        });

        imageSlider = view.findViewById(R.id.image_slider);
        ArrayList<SlideModel> imagelist= new ArrayList<> ();
        imagelist.add(new SlideModel(R.drawable.slider_img, ScaleTypes.CENTER_CROP));
        imagelist.add(new SlideModel(R.drawable.slider_img2,ScaleTypes.CENTER_CROP));
        imagelist.add(new SlideModel(R.drawable.slider_img3,ScaleTypes.CENTER_CROP));
        imagelist.add(new SlideModel(R.drawable.slider_img4,ScaleTypes.CENTER_CROP));
        imagelist.add(new SlideModel(R.drawable.slider_img5,ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(imagelist);


        return  view;
    }
    private void setData(){

    }
    private  void openCourse(){
        Intent intent = new Intent(context,CourseActivity.class);
        startActivity(intent);
    }
    private void openAbout(){
        Intent intent = new Intent(context,AboutActivity.class);
        startActivity(intent);
    }
    private void openExam(){
        Intent intent = new Intent(context,LeaderBoard.class);
        startActivity(intent);
    }
    public void onStart() {
       super.onStart();
        LinearLayout calenderLayout=(LinearLayout) context.findViewById(R.id.calender);
        calenderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,calender.class);
                startActivity(intent);
            }
        });
      LinearLayout location=(LinearLayout) context.findViewById(R.id.location);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,MapsActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout exam=(LinearLayout) context.findViewById(R.id.exam);
        exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,AllExamCatActivity.class);
                startActivity(intent);
            }
        });
    }
}
