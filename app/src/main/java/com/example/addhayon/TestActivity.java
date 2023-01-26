package com.example.addhayon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.addhayon.Adapters.TestAdapter;

public class TestActivity extends AppCompatActivity {
    private RecyclerView textView;
    private Toolbar toolbar;
    private TestAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        textView =findViewById(R.id.test_recyler_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(DBQurey.g_catList.get(DBQurey.g_selected_cat_index).getName());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        textView.setLayoutManager(layoutManager);

        DBQurey.loadTestData(new MyCompleteListener(){
            @Override
            public void onSuccess(){
                DBQurey.loadMyScore(new MyCompleteListener(){
                    @Override
                    public void onSuccess(){
                        adapter = new TestAdapter(DBQurey.g_testList);
                        textView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(){
                        Toast.makeText(TestActivity.this, "Something went wrong ! Please try Later.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

            }
            @Override
            public void onFailure(){
                Toast.makeText(TestActivity.this, "Something went wrong ! Please try Later.",
                        Toast.LENGTH_SHORT).show();

            }
        });




    }
//    private void loadTestData(){
//        testList = new ArrayList<>();
//        testList.add(new TestModel("1",50,20));
//        testList.add(new TestModel("2",60,20));
//        testList.add(new TestModel("3",50,20));
//        testList.add(new TestModel("4",10,20));
//
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            TestActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}