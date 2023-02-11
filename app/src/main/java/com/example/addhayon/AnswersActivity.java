package com.example.addhayon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.addhayon.Adapters.AnswersAdapter;
import com.example.addhayon.Adapters.TestAdapter;

public class AnswersActivity extends AppCompatActivity {
    private RecyclerView textView;
    private Toolbar toolbar;
    private TestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);

        toolbar =findViewById(R.id.aa_toolbar);
        textView = findViewById(R.id.aa_recyler_view);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        if(DBQurey.myProfile.getLanguage().equals("Bangla")){
            getSupportActionBar().setTitle("উত্তর দেখুন");
        }else {
            getSupportActionBar().setTitle("View Answers");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        textView.setLayoutManager(layoutManager);

        AnswersAdapter answersAdapter = new AnswersAdapter(DBQurey.g_quesList);
        textView.setAdapter(answersAdapter);


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            AnswersActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}