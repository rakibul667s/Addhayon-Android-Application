package com.example.addhayon;

import static com.example.addhayon.DBQurey.ANSWERED;
import static com.example.addhayon.DBQurey.NOT_VIDITED;
import static com.example.addhayon.DBQurey.REVIEW;
import static com.example.addhayon.DBQurey.UNANSWERED;
import static com.example.addhayon.DBQurey.g_quesList;
import static com.example.addhayon.DBQurey.g_catList;
import static com.example.addhayon.DBQurey.g_selected_cat_index;
import static com.example.addhayon.DBQurey.g_selected_test_index;
import static com.example.addhayon.DBQurey.g_testList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class QuestionsActivity extends AppCompatActivity {
    private RecyclerView questionView;
    private TextView tvQuesID, timerTV, catNameTV;
    private Button submitB, clearSelB;

    private ImageView  markB;
    private ImageView prevQuesB, nextQuesB;
    private ImageView quesListB;
    private int quesID;

    private DrawerLayout drawer;

    private ImageView drawerCloseB;

    private GridView quesListGV;
    private ImageView markImage;

    private QuestionGridAdapter gridAdapter;

    QuestionsAdapter questionsAdapter;

    private CountDownTimer timer;

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

        gridAdapter = new QuestionGridAdapter(this,g_quesList.size());
        quesListGV.setAdapter(gridAdapter);

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
        markImage = findViewById(R.id.mark_image);
        quesListGV =findViewById(R.id.ques_list_gv);
        quesID = 0;
        tvQuesID.setText("1/" + String.valueOf(g_quesList.size()));
        catNameTV.setText(g_catList.get(g_selected_cat_index).getName());

        g_quesList.get(0).setStatus(UNANSWERED);
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
                if(g_quesList.get(quesID).getStatus() == NOT_VIDITED){
                    g_quesList.get(quesID).setStatus(UNANSWERED);
                }

                if(g_quesList.get(quesID).getStatus() == REVIEW){
                    markImage.setVisibility(View.VISIBLE);
                }else{
                    markImage.setVisibility(View.GONE);
                }

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
                g_quesList.get(quesID).setStatus(UNANSWERED);
                markImage.setVisibility(v.GONE);
                questionsAdapter.notifyDataSetChanged();

            }
        });

        quesListB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!drawer.isDrawerOpen(GravityCompat.END)){
                    gridAdapter.notifyDataSetChanged();
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
        markB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(markImage.getVisibility() != View.VISIBLE){
                    markImage.setVisibility(View.VISIBLE);
                    g_quesList.get(quesID).setStatus(REVIEW);
                }else{
                    markImage.setVisibility(View.GONE);
                    if(g_quesList.get(quesID).getSelectedAns() != -1){
                        g_quesList.get(quesID).setStatus(ANSWERED);
                    }else{
                        g_quesList.get(quesID).setStatus(UNANSWERED);
                    }
                }
            }
        });
        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitText();
            }
        });
    }
    private void submitText(){
        AlertDialog.Builder builder = new AlertDialog.Builder(QuestionsActivity.this);
        builder.setCancelable(true);

        View view = getLayoutInflater().inflate(R.layout.alert_dialog_layout,null);

        Button cancelB = view.findViewById(R.id.cancelB);
        Button confirmB =view.findViewById(R.id.confrimB);

        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        confirmB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                alertDialog.dismiss();
                Intent intent = new Intent(QuestionsActivity.this, ScoreActivity.class);
                startActivity(intent);
                QuestionsActivity.this.finish();
            }
        });
        alertDialog.show();

    }

    public void goToQuestion(int position){
        questionView.smoothScrollToPosition(position);
        if(drawer.isDrawerOpen(GravityCompat.END)){
            drawer.closeDrawer(GravityCompat.END);
        }
    }


    //------------------------Time Function------------------------------------
    public void startTimer(){
        long totalTime = g_testList.get(g_selected_test_index).getTime()*60*1000;
        timer = new CountDownTimer(totalTime +1000,1000) {
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
                Intent intent = new Intent(QuestionsActivity.this, ScoreActivity.class);
                startActivity(intent);
                QuestionsActivity.this.finish();

            }
        };
        timer.start();
    }
}