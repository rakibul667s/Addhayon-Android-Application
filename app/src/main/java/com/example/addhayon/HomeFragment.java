package com.example.addhayon;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

    private LinearLayout exam2, aboutMe;


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
        aboutMe = view.findViewById(R.id.about);



        //setData();
        exam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExam();
            }
        });
        aboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAbout();
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
