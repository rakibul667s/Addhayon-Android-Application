package com.example.addhayon;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.addhayon.Models.QuestionModel;

import java.util.concurrent.TimeUnit;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class ScoreActivity extends AppCompatActivity {
    private TextView scoreTV, timeTV, totalQTV, correctQTV, worngQTV, unattemtedQTV;
    private Button learderB, reAttemptB, viewAnsB;
    private long timeTaken;
    private int finalScore;
    private MeowBottomNavigation btm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

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
        init();
        loadData();
        setBookMark();
        viewAnsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScoreActivity.this, AnswersActivity.class);
                startActivity(intent);


            }
        });
        reAttemptB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reAttempt();
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
        reAttemptB = findViewById(R.id.reattamB);
        viewAnsB = findViewById(R.id.view_answerB);

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

    @SuppressLint("NewApi")
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
    private void setBookMark(){
        for(int i=0; i<DBQurey.g_quesList.size();i++){
            QuestionModel questionModel = DBQurey.g_quesList.get(i);
            if(questionModel.isBookmark()){
                if(! DBQurey.g_bmIdList.contains(questionModel.getqId())){
                    DBQurey.g_bmIdList.add(questionModel.getqId());
                    DBQurey.myProfile.setBookmarksCount(DBQurey.g_bmIdList.size());
                }
            }else{
                if(DBQurey.g_bmIdList.contains(questionModel.getqId())){
                    DBQurey.g_bmIdList.remove(questionModel.getqId());
                    DBQurey.myProfile.setBookmarksCount(DBQurey.g_bmIdList.size());
                }

            }
        }
    }
    public void openExamCat(){
        Intent intent = new Intent(this, AllExamCatActivity.class);
        startActivity(intent);
        ScoreActivity.this.finish();
    }
    public void openLeaderBoard(){
        Intent intent = new Intent(this, LeaderBoard.class);
        startActivity(intent);
        ScoreActivity.this.finish();
    }
    public void openDashboard(){
        Intent intent = new Intent(this, dashboard.class);
        startActivity(intent);
        ScoreActivity.this.finish();
    }

    public void openUserProfile(){
        Intent intent = new Intent(this, userProfile.class);
        startActivity(intent);
        ScoreActivity.this.finish();
    }
    public  void openMark(){
        Intent intent = new Intent(this, BookMaksActivity.class);
        startActivity(intent);
        ScoreActivity.this.finish();
    }
}