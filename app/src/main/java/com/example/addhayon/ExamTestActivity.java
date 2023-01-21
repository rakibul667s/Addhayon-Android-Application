package com.example.addhayon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class ExamTestActivity extends AppCompatActivity {
    private RecyclerView text_recyclerview;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_test);

        text_recyclerview = findViewById(R.id.text_recyclerview);

        toolbar = findViewById(R.id.text_recyclerview);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Exam Cat");
    }
}