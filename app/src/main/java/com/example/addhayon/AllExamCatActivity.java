package com.example.addhayon;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.addhayon.Adapters.CategoryAdapter;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class AllExamCatActivity extends AppCompatActivity {
    private MeowBottomNavigation btm;
    private GridView catView;
    //public static List<CategoryModel> catList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_exam_cat);

        catView = findViewById(R.id.cat_grid);
        //loadCategories();
        CategoryAdapter adapter = new CategoryAdapter(DBQurey.g_catList);
        catView.setAdapter(adapter);

        btm = findViewById(R.id.bottom_nav);
        btm.add(new MeowBottomNavigation.Model(1, R.drawable.ic_baseline_home));
        btm.add(new MeowBottomNavigation.Model(2, R.drawable.ic_exam));
        btm.add(new MeowBottomNavigation.Model(3, R.drawable.baseline_mark3));
        btm.add(new MeowBottomNavigation.Model(4, R.drawable.ic_baseline_dashborad));
        btm.add(new MeowBottomNavigation.Model(5, R.drawable.ic_baseline_person));


        btm.show(2, true);
        // replace(new com.example.addhayon.examDashboardFragment());
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
                        openBookmark();
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
    }
//    private void loadCategories(){
//        catList.clear();
//        catList.add(new CategoryModel("1","Bangla", 20));
//        catList.add(new CategoryModel("2","HISTORY", 30));
//        catList.add(new CategoryModel("3","ENGLISH", 10));
//        catList.add(new CategoryModel("4","MATH", 25));
//        catList.add(new CategoryModel("5","ICT", 10));
//        catList.add(new CategoryModel("6","GC", 10));
//
//
//    }
    private  void  replace(Fragment fragment){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }
    public void openExamCat(){
        Intent intent = new Intent(this, AllExamCatActivity.class);
        startActivity(intent);
        AllExamCatActivity.this.finish();
    }
    public void openLeaderBoard(){
        Intent intent = new Intent(this, LeaderBoard.class);
        startActivity(intent);
        AllExamCatActivity.this.finish();
    }
    public void openDashboard(){
        Intent intent = new Intent(this, dashboard.class);
        startActivity(intent);
        AllExamCatActivity.this.finish();
    }

    public void openUserProfile(){
        Intent intent = new Intent(this, userProfile.class);
        startActivity(intent);
        AllExamCatActivity.this.finish();
    }
    public  void openBookmark(){
        Intent intent = new Intent(this, BookMaksActivity.class);
        startActivity(intent);
        AllExamCatActivity.this.finish();
    }

}
