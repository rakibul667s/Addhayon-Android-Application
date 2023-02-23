package com.example.addhayon;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class NewsPageActivity extends AppCompatActivity {
    private MeowBottomNavigation btm;
    WebView webView;
    private String link;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_page);
        Intent intent = getIntent();

         link = intent.getStringExtra("newslink");
        webView = findViewById(R.id.webView);


        btm = findViewById(R.id.bottom_nav);
        btm.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_home));
        btm.add(new MeowBottomNavigation.Model(2,R.drawable.baseline_apps_24));
        btm.add(new MeowBottomNavigation.Model(3,R.drawable.baseline_art_track_24));
        btm.add(new MeowBottomNavigation.Model(4,R.drawable.baseline_article_24));
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
                        openExam();
                        break;
                    case 3:
                        openNews();
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

        ProgressDialog progressDialog = ProgressDialog.show(NewsPageActivity.this, "Loading. . .","Please wait",true);
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
        NewsPageActivity.this.finish();
    }

    public void openUserProfile(){
        Intent intent = new Intent(this, userProfile.class);
        startActivity(intent);
        NewsPageActivity.this.finish();
    }
    public void openExam(){
        Intent intent = new Intent(this, SeeMoreActivity.class);
        startActivity(intent);
        NewsPageActivity.this.finish();
    }
    public  void openNews(){
        Intent intent = new Intent(this, NewsActivity.class);
        startActivity(intent);
        NewsPageActivity.this.finish();
    }
    public void openMap(){
        Intent intent = new Intent(this, NewsPageActivity.class);
        startActivity(intent);
        NewsPageActivity.this.finish();
    }
}