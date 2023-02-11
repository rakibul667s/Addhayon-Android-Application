package com.example.addhayon;

import static com.example.addhayon.DBQurey.g_catList;
import static com.example.addhayon.DBQurey.g_selected_cat_index;
import static com.example.addhayon.DBQurey.loadquestions;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StartTestActivity extends AppCompatActivity {
    private TextView catName, testNo, totalq, bestScore, time, q, s, t;
    private Button startTestB;
    private ImageView backB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_test);
        init();

        if(DBQurey.myProfile.getLanguage().equals("Bangla")){
            q.setText("প্রশ্ন");
            s.setText("আপনার সেরা স্কোর");
            t.setText("সময়");
            startTestB.setText( "শুরু");
        }

        loadquestions(new MyCompleteListener(){
            @Override
            public void onSuccess(){
                setData();
            }
            @Override
            public void onFailure(){
                Toast.makeText(StartTestActivity.this, "Something went wrong ! Please try Later.",
                        Toast.LENGTH_SHORT).show();

            }

        });
    }
    private void init(){
        catName = findViewById(R.id.st_cat_name);
        testNo = findViewById(R.id.st_test_no);
        totalq =findViewById(R.id.st_total_quesno);
        bestScore = findViewById(R.id.st_best_score);
        time = findViewById(R.id.st_time);
        startTestB = findViewById(R.id.start_test);
        backB = findViewById(R.id.st_back);
        q = findViewById(R.id.q);
        s = findViewById(R.id.s);
        t = findViewById(R.id.t);


        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartTestActivity.this.finish();

            }
        });

        startTestB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartTestActivity.this, QuestionsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void setData(){
        catName.setText(g_catList.get(g_selected_cat_index).getName());
        testNo.setText("Test No."+String.valueOf(DBQurey.g_selected_test_index + 1));
        totalq.setText(String.valueOf(DBQurey.g_quesList.size()));
        bestScore.setText(String.valueOf(DBQurey.g_testList.get(DBQurey.g_selected_test_index).getTopScore()));
        time.setText(String.valueOf(DBQurey.g_testList.get(DBQurey.g_selected_test_index).getTime()));
    }
}