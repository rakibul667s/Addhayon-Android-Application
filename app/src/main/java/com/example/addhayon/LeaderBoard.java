package com.example.addhayon;

import static com.example.addhayon.DBQurey.g_userCount;
import static com.example.addhayon.DBQurey.g_usersList;
import static com.example.addhayon.DBQurey.myPerformance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.addhayon.Adapters.RankAdapter;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class LeaderBoard extends AppCompatActivity {
    private TextView totalUsers, myScore, myRank;
    private CircleImageView myProfileImg;
    private RecyclerView usersView;
    private RankAdapter adapter;
    private MeowBottomNavigation btm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        initViews();
        btm = findViewById(R.id.bottom_nav);
        btm.add(new MeowBottomNavigation.Model(1, R.drawable.ic_baseline_home));
        btm.add(new MeowBottomNavigation.Model(2, R.drawable.ic_exam));
        btm.add(new MeowBottomNavigation.Model(3, R.drawable.baseline_mark3));
        btm.add(new MeowBottomNavigation.Model(4, R.drawable.ic_baseline_dashborad));
        btm.add(new MeowBottomNavigation.Model(5, R.drawable.ic_baseline_person));


        btm.show(4, true);
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

        LinearLayoutManager layoutManager =new LinearLayoutManager(LeaderBoard.this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        usersView.setLayoutManager(layoutManager);
        adapter = new RankAdapter(DBQurey.g_usersList);
        usersView.setAdapter(adapter);
        DBQurey.getTopUsers(new MyCompleteListener(){
            @Override
            public void onSuccess(){
                adapter.notifyDataSetChanged();
                if(myPerformance.getScore() != 0){
                    if(! DBQurey.isMeOnTopList){
                        calcukateRank();
                    }
                    myScore.setText("Total Score : "+myPerformance.getScore());
                    myRank.setText("Rank : "+myPerformance.getRank());
                }
            }
            @Override
            public void onFailure(){
                Toast.makeText(LeaderBoard.this, "Something went weong ! Please try agian",
                        Toast.LENGTH_SHORT).show();

            }
        });
        totalUsers.setText("Total students : " +DBQurey.g_userCount);
        Glide.with(this)
                .load(DBQurey.myProfile.getProfileImg())
                .into(myProfileImg);

    }

    private void initViews(){
        totalUsers = findViewById(R.id.total_user);
        myScore = findViewById(R.id.total_score);
        myRank = findViewById(R.id.rank);
        myProfileImg = findViewById(R.id.img_txt);
        usersView = findViewById(R.id.users_view);
    }
    private void calcukateRank(){
        int lowTopScore = g_usersList.get(g_usersList.size()-1).getScore();
        int remaining_slots = g_userCount - 20;
        int myslot = (myPerformance.getScore()*remaining_slots)/lowTopScore;
        int rank;

        if(lowTopScore != myPerformance.getScore()){
            rank = g_userCount - myslot;
        }else{
            rank = 21;
        }
        myPerformance.setRank(rank);

    }
    private  void  replace(Fragment fragment){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }
    public void openExamCat(){
        Intent intent = new Intent(this, AllExamCatActivity.class);
        startActivity(intent);
        LeaderBoard.this.finish();
    }
    public void openLeaderBoard(){
        Intent intent = new Intent(this, LeaderBoard.class);
        startActivity(intent);
        LeaderBoard.this.finish();
    }
    public void openDashboard(){
        Intent intent = new Intent(this, dashboard.class);
        startActivity(intent);
        LeaderBoard.this.finish();
    }

    public void openUserProfile(){
        Intent intent = new Intent(this, userProfile.class);
        startActivity(intent);
        LeaderBoard.this.finish();
    }
    public  void openMark(){
        Intent intent = new Intent(this, BookMaksActivity.class);
        startActivity(intent);
        LeaderBoard.this.finish();
    }
}