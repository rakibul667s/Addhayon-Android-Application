package com.example.addhayon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.addhayon.Adapters.AnswersAdapter;
import com.example.addhayon.Adapters.BookmarksAdapter;
import com.example.addhayon.Adapters.TestAdapter;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class BookMaksActivity extends AppCompatActivity {
    private RecyclerView questionsView;
    private RecyclerView textView;
    private Toolbar toolbar;
    private MeowBottomNavigation btm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_maks);
        textView = findViewById(R.id.book_recyler_view);



        btm = findViewById(R.id.bottom_nav);
        btm.add(new MeowBottomNavigation.Model(1, R.drawable.ic_baseline_home));
        btm.add(new MeowBottomNavigation.Model(2, R.drawable.ic_exam));
        btm.add(new MeowBottomNavigation.Model(3, R.drawable.baseline_mark3));
        btm.add(new MeowBottomNavigation.Model(4, R.drawable.ic_baseline_dashborad));
        btm.add(new MeowBottomNavigation.Model(5, R.drawable.ic_baseline_person));

        btm.show(3, true);
        btm.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()) {
                    case 1:
                        openDashboard();
                        break;
                    case 2:
                        openExamCat();
                        break;
                    case 3:
                        openMark();
                        break;
                    case 4:
                        openLeaderBoard();
                        break;
                    case 5:
                        openUserProfile();
                        break;

                }
                return null;
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        textView.setLayoutManager(layoutManager);

        DBQurey.loadBookmarks(new MyCompleteListener(){
            @Override
            public void onSuccess(){
                BookmarksAdapter answersAdapter = new BookmarksAdapter(DBQurey.g_bookmarkList);
                textView.setAdapter(answersAdapter);
            }
            @Override
            public void onFailure(){

            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            BookMaksActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void openExamCat(){
        Intent intent = new Intent(this, AllExamCatActivity.class);
        startActivity(intent);
        BookMaksActivity.this.finish();
    }
    public void openLeaderBoard(){
        Intent intent = new Intent(this, LeaderBoard.class);
        startActivity(intent);
        BookMaksActivity.this.finish();
    }
    public void openDashboard(){
        Intent intent = new Intent(this, dashboard.class);
        startActivity(intent);
        BookMaksActivity.this.finish();
    }

    public void openUserProfile(){
        Intent intent = new Intent(this, userProfile.class);
        startActivity(intent);
        BookMaksActivity.this.finish();
    }
    public  void openMark(){
        Intent intent = new Intent(this, BookMaksActivity.class);
        startActivity(intent);
        BookMaksActivity.this.finish();
    }
}