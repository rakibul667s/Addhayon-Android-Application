package com.example.addhayon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.addhayon.Adapters.AnswersAdapter;
import com.example.addhayon.Adapters.BookmarksAdapter;
import com.example.addhayon.Adapters.TestAdapter;

public class BookMaksActivity extends AppCompatActivity {
    private RecyclerView questionsView;
    private RecyclerView textView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_maks);
        toolbar = findViewById(R.id.book_toolbar);
        textView = findViewById(R.id.book_recyler_view);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Mark Questions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
}