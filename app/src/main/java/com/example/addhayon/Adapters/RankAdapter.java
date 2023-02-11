package com.example.addhayon.Adapters;

import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.addhayon.AllUserActivity;
import com.example.addhayon.DBQurey;
import com.example.addhayon.Models.RankModel;
import com.example.addhayon.MyCompleteListener;
import com.example.addhayon.R;
import com.example.addhayon.userProfile;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {
    private List<RankModel> userList;
    private PopupMenu popupMenu;
    private MenuItem vProfile;
    //private PopupMenu popupMenu;

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
        String cImg = userList.get(position).getCoverImg();
        String bio = userList.get(position).getBio();







        String sclclg = userList.get(position).getSclclg();
        String address = userList.get(position).getAddress();
        String dateOfbirth = userList.get(position).getDateOfbirth();
        String emailId = userList.get(position).getEmailId();
        String phone = userList.get(position).getPhone();

        holder.setData(name,score, rank, pImg, id, cImg, bio, sclclg, address, dateOfbirth,emailId, phone);
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
        private void setData(String name, int score, int rank, String pImg, String id, String cImg,String bio,String sclclg,String address,String dateOfbirth,String emailId,String phone){

            itemView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(v.getContext(), itemView);
                    popupMenu.getMenuInflater().inflate(R.menu.view_profile, popupMenu.getMenu());
                    vProfile = popupMenu.getMenu().findItem(R.id.user_p);
                    if(DBQurey.myProfile.getLanguage().equals("Bangla")){
                         vProfile.setTitle("প্রোফাইল দেখুন");
                    }
                    popupMenu.show();
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.user_p:
                                {
                                    Intent intent = new Intent(v.getContext(),AllUserActivity.class);
                                    intent.putExtra("name",name);
                                    intent.putExtra("score",score);
                                    intent.putExtra("rank",rank);
                                    intent.putExtra("pImg",pImg);
                                    intent.putExtra("id",id);
                                    intent.putExtra("cImg",cImg);
                                    intent.putExtra("bio",bio);
                                    intent.putExtra("sclclg",sclclg);
                                    intent.putExtra("address",address);
                                    intent.putExtra("dateOfbirth",dateOfbirth);
                                    intent.putExtra("emailId",emailId);
                                    intent.putExtra("phone",phone);
                                    v.getContext().startActivity(intent);
                                }

                                    break;
                            }
                            return true;
                        }
                    });

//                    popupMenu = new PopupMenu(v.getContext(), imgTv);
//                    popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());

//                    Intent intent = new Intent(v.getContext(), userProfile.class);
//                    v.getContext().startActivity(intent);
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
