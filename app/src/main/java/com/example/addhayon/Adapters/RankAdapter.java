package com.example.addhayon.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.addhayon.DBQurey;
import com.example.addhayon.EditProfileActivity;
import com.example.addhayon.Models.RankModel;
import com.example.addhayon.R;
import com.example.addhayon.userProfile;

import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {
    private List<RankModel> userList;

    public RankAdapter(List<RankModel> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public RankAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RankAdapter.ViewHolder holder, int position) {
        String name = userList.get(position).getName();
        int score = userList.get(position).getScore();
        int rank = userList.get(position).getRank();
        String pImg = userList.get(position).getpImg();
        String id = userList.get(position).getId();

        holder.setData(name,score, rank, pImg, id);
    }

    @Override
    public int getItemCount() {
        if(userList.size() > 10){
            return 10;
        }else{
            return userList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTv, rankTv, scoreTv;
        private CircleImageView imgTv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.name2);
            rankTv = itemView.findViewById(R.id.rank2);
            scoreTv = itemView.findViewById(R.id.score2);
            imgTv = itemView.findViewById(R.id.img_txt);

        }
        private void setData(String name, int score, int rank, String pImg, String id){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), userProfile.class);
                    v.getContext().startActivity(intent);
                }
            });
            nameTv.setText(name);
            if(DBQurey.myProfile.getLanguage().equals("Bangla")){
                scoreTv.setText("স্কোর : " + score);
                rankTv.setText("পদমর্যাদা : " + rank);
            }else {
                scoreTv.setText("Score : " + score);
                rankTv.setText("Rank : " + rank);
            }
            if(pImg.length() == 0){
                imgTv.setImageResource(R.drawable.baseline_puser);
            }
            if(pImg.length() != 0) {
                Glide.with(itemView.getContext())
                        .load(pImg)
                        .into(imgTv);
            }
        }
    }
}
