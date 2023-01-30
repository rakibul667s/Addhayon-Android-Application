package com.example.addhayon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class ScoreActivity extends AppCompatActivity {
    private TextView scoreTV, timeTV, totalQTV, correctQTV, worngQTV, unattemtedQTV;
    private Button learderB, reAttemptB, viewAnsB;
    private long timeTaken;
    private int finalScore;

    private ImageView backB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        init();
        loadData();
        viewAnsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
        reAttemptB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reAttempt();
            }
        });

        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this, dashboard.class);
                startActivity(intent);
                finish();
            }
        });
        learderB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this, LeaderBoard.class);
                startActivity(intent);
                finish();
            }
        });


        saveResult();

    }

    public void init(){
        scoreTV = findViewById(R.id.score);
        timeTV = findViewById(R.id.time);
        totalQTV = findViewById(R.id.totalQ);
        correctQTV = findViewById(R.id.correctQ);
        worngQTV = findViewById(R.id.wrongQ);
        unattemtedQTV = findViewById(R.id.un_attemted);
        learderB = findViewById(R.id.leaderB);
        reAttemptB = findViewById(R.id.reattamB);
        viewAnsB = findViewById(R.id.view_answerB);
        backB = findViewById(R.id.st_back2);

    }

    private  void loadData(){
        int correctQ = 0, worngQ = 0, unattemtQ =0;
        for(int i = 0; i < DBQurey.g_quesList.size(); i++){
            if(DBQurey.g_quesList.get(i).getSelectedAns() == -1){
                unattemtQ++;

            }else{
                if(DBQurey.g_quesList.get(i).getSelectedAns() == DBQurey.g_quesList.get(i).getCorrectAns()){
                    correctQ++;
                }else{
                    worngQ++;
                }
            }
        }
        correctQTV.setText(String.valueOf(correctQ));
        worngQTV.setText(String.valueOf(worngQ));
        unattemtedQTV.setText(String.valueOf(unattemtQ));
        totalQTV.setText(String.valueOf(DBQurey.g_quesList.size()));
        finalScore = (correctQ*100)/DBQurey.g_quesList.size();

        scoreTV.setText(String.valueOf(finalScore));

        timeTaken = getIntent().getLongExtra("TIME_TAKEN", 0);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD) {
            String time = String.format("%02d : %02d min",
                    TimeUnit.MILLISECONDS.toMinutes(timeTaken),
                    TimeUnit.MILLISECONDS.toSeconds(timeTaken) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeTaken))

            );
            timeTV.setText(time);
        }

    }

    private void reAttempt(){
        for(int i=0; i< DBQurey.g_quesList.size(); i++){
            DBQurey.g_quesList.get(i).setSelectedAns(-1);
            DBQurey.g_quesList.get(i).setStatus(DBQurey.NOT_VIDITED);
        }
        Intent intent = new Intent(ScoreActivity.this,StartTestActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveResult(){
        DBQurey.saveResult(finalScore,new MyCompleteListener(){
            @Override
            public void onSuccess(){

            }
            @Override
            public void onFailure(){
                Toast.makeText(ScoreActivity.this,"Something went worng !",Toast.LENGTH_SHORT).show();

            }
        });
    }
}