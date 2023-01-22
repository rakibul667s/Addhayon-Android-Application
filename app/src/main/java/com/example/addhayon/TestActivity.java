package com.example.addhayon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity {
    private RecyclerView textView;
    private Toolbar toolbar;
    private List<TestModel> testList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        textView =findViewById(R.id.test_recyler_view);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        int cat_index = getIntent().getIntExtra("CAT_INDEX",0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(AllExamCatActivity.catList.get(cat_index).getName());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        textView.setLayoutManager(layoutManager);

        loadTestData();
        TestAdapter adapter = new TestAdapter(testList);
        textView.setAdapter(adapter);


    }
    private void loadTestData(){
        testList = new ArrayList<>();
        testList.add(new TestModel("1",50,20));
        testList.add(new TestModel("2",60,20));
        testList.add(new TestModel("3",50,20));
        testList.add(new TestModel("4",10,20));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            TestActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}