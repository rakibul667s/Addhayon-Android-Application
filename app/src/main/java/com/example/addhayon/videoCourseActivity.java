package com.example.addhayon;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class videoCourseActivity extends AppCompatActivity {
    private MeowBottomNavigation btm;
    WebView webView;
    private String link;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_course);

        Intent intent = getIntent();

        link = intent.getStringExtra("link");
        webView = findViewById(R.id.webView);

        btm = findViewById(R.id.bottom_nav);
        btm.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_home));
        btm.add(new MeowBottomNavigation.Model(2,R.drawable.baseline_burst_mode_24));
        btm.add(new MeowBottomNavigation.Model(3,R.drawable.baseline_auto_awesome_motion_24));
        btm.add(new MeowBottomNavigation.Model(4,R.drawable.baseline_check_box_outline_blank_24));
        btm.add(new MeowBottomNavigation.Model(5,R.drawable.baseline_play_circle_outline_24));



        btm.show(5,true);
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
                        openV();;
                        break;

                }
                return null;
            }
        });
        ProgressDialog progressDialog = ProgressDialog.show(videoCourseActivity.this, "Loading. . .","Please wait",true);
        progressDialog.setCancelable(false);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progressDialog.show();
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressDialog.dismiss();
            }
        });
        webView.loadUrl(link);
    }
    private void openHome(){
        Intent intent = new Intent(this,dashboard.class);
        startActivity(intent);
        videoCourseActivity.this.finish();
    }

    public void openV(){
        Intent intent = new Intent(this, videoCourseActivity.class);
        startActivity(intent);
        videoCourseActivity.this.finish();
    }
    public void openCourse(){
        Intent intent = new Intent(this,CourseActivity.class);
        startActivity(intent);
        videoCourseActivity.this.finish();
    }
    public  void openCat(){
        Intent intent = new Intent(this, sSSCActivity.class);
        startActivity(intent);
        videoCourseActivity.this.finish();
    }
    public void openSSC(){
        Intent intent = new Intent(this, SSCActivity.class);
        startActivity(intent);
        videoCourseActivity.this.finish();
    }
}