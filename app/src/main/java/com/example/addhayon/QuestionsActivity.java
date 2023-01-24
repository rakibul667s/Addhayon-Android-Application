package com.example.addhayon;

import static com.example.addhayon.DBQurey.g_quesList;
import static com.example.addhayon.DBQurey.g_catList;
import static com.example.addhayon.DBQurey.g_selected_cat_index;
import static com.example.addhayon.DBQurey.g_selected_test_index;
import static com.example.addhayon.DBQurey.g_testList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class QuestionsActivity extends AppCompatActivity {
    private RecyclerView questionView;
    private TextView tvQuesID, timerTV, catNameTV;
    private Button submitB, markB, clearSelB;
    private ImageView prevQuesB, nextQuesB;
    private ImageView quesListB;
    private int quesID;

    private DrawerLayout drawer;

    private ImageView drawerCloseB;

    QuestionsAdapter questionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions_list_layout);
        init();
        questionsAdapter = new QuestionsAdapter(g_quesList);
        questionView.setAdapter(questionsAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        questionView.setLayoutManager(layoutManager);

        //-------------call all ques----------------
        setSnapHelper();

        //------------prev and next btn call--------------------
        setClickListeners();

        //------------set Timer----------------------
        startTimer();



    }


    //------------------initialize----------------------------------------------
    private void init(){
        questionView = findViewById(R.id.questions_view);
        tvQuesID = findViewById(R.id.tv_quesID);
        timerTV = findViewById(R.id.tv_timer);
        catNameTV = findViewById(R.id.qa_catName);
        submitB = findViewById(R.id.submit);
        markB = findViewById(R.id.markB);
        clearSelB = findViewById(R.id.clear_selB);
        prevQuesB = findViewById(R.id.prev_quesB);
        nextQuesB = findViewById(R.id.next_quesB);
        quesListB = findViewById(R.id.ques_list_gridB);
        drawer = findViewById(R.id.drawer_layout);
        drawerCloseB =findViewById(R.id.drawableCloseB);

        quesID = 0;
        tvQuesID.setText("1/" + String.valueOf(g_quesList.size()));
        catNameTV.setText(g_catList.get(g_selected_cat_index).getName());
    }


    //------------------------ques items set funtion-----------------------------------
    private void setSnapHelper(){
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(questionView);
        questionView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                View view = snapHelper.findSnapView(recyclerView.getLayoutManager());
                quesID = recyclerView.getLayoutManager().getPosition(view);
                tvQuesID.setText(String.valueOf(quesID + 1) + "/" +String.valueOf(g_quesList.size()));
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }


    //------------------------prev and next btn funtion----------------------------------
    private void setClickListeners(){
        prevQuesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quesID > 0){
                    questionView.smoothScrollToPosition(quesID-1);
                }

            }
        });
        nextQuesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quesID < g_quesList.size() - 1){
                    questionView.smoothScrollToPosition(quesID+1);
                }

            }
        });
        clearSelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                g_quesList.get(quesID).setSelectedAns(-1);
                questionsAdapter.notifyDataSetChanged();

            }
        });

        quesListB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!drawer.isDrawerOpen(GravityCompat.END)){
                    drawer.openDrawer(GravityCompat.END);
                }
            }
        });
        drawerCloseB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(drawer.isDrawerOpen(GravityCompat.END)){
                    drawer.closeDrawer(GravityCompat.END);
                }
            }
        });
    }


    //------------------------Time Function------------------------------------
    public void startTimer(){
        long totalTime = g_testList.get(g_selected_test_index).getTime()*60*1000;
        CountDownTimer timer = new CountDownTimer(totalTime +1000,1000) {
            @Override
            public void onTick(long remainingTime) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD) {
                    String time = String.format("%02d : %02d min",
                            TimeUnit.MILLISECONDS.toMinutes(remainingTime),
                            TimeUnit.MILLISECONDS.toSeconds(remainingTime) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime))

                            );
                    timerTV.setText(time);
                }
            }

            @Override
            public void onFinish() {


            }
        };
        timer.start();
    }
}