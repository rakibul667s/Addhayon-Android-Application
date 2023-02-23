package com.example.addhayon;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.addhayon.databinding.ItemStatusBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

import omari.hamza.storyview.StoryView;
import omari.hamza.storyview.callback.StoryClickListeners;
import omari.hamza.storyview.model.MyStory;

public class TopStatusAdapter extends RecyclerView.Adapter<TopStatusAdapter.TopStatusViewHolder> {

    Context context;
    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<UserStatus> userStatuses;
    String sTime;

    public TopStatusAdapter(Context context, ArrayList<UserStatus> userStatuses) {
        this.context = context;
        this.userStatuses = userStatuses;
    }

    @NonNull
    @Override
    public TopStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_status, parent, false);
        return new TopStatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopStatusViewHolder holder, int position) {

        UserStatus userStatus = userStatuses.get(position);

        Status lastStatus = userStatus.getStatuses().get(userStatus.getStatuses().size() - 1);

        Glide.with(context).load(lastStatus.getImageUrl()).into(holder.binding.image);

        holder.binding.circularStatusView.setPortionsCount(userStatus.getStatuses().size());

        holder.binding.circularStatusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                ArrayList<MyStory> myStories = new ArrayList<>();
                for(Status status : userStatus.getStatuses()) {
                    Date date = new Date();
                    sTime = String.valueOf(status.getTimeStamp());
                    long diff =   date.getTime()-status.getTimeStamp();

                    long diffHours = diff / (60*60*1000);



                            myStories.add(new MyStory(status.getImageUrl()));





                }


                new StoryView.Builder(((ChatMainActivity)context).getSupportFragmentManager())
                        .setStoriesList(myStories) // Required
                        .setStoryDuration(5000) // Default is 2000 Millis (2 Seconds)
                        .setTitleText(userStatus.getName()) // Default is Hidden
                        .setSubtitleText("") // Default is Hidden
                        .setTitleLogoUrl(userStatus.getProfileImage())// Default is Hidden
                        .setStoryClickListeners(new StoryClickListeners() {
                            @Override
                            public void onDescriptionClickListener(int position) {
                                //your action

                            }

                            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
                            @Override
                            public void onTitleIconClickListener(int position) {
                                //PopupMenu popupMenu = new PopupMenu(context, holder.binding.image);
//                                PopupMenu popupMenu = new PopupMenu();
//                                popupMenu.getMenuInflater().inflate(R.menu.view_profile, popupMenu.getMenu());
//
//                                popupMenu.show();
                                //your action
                                if(userStatus.getName().equals(DBQurey.myProfile.getName())) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                    builder.setMessage("Are you sure you want to delete this story?");
                                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Delete the story from the database
                                            reference = FirebaseDatabase.getInstance().getReference().child("stories");
                                            reference.child(uid).child("statuses").child(userStatus.getKey()).removeValue();
                                            dialog.dismiss();
                                        }
                                    });
                                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    builder.show();
                                }
                            }


                        }) // Optional Listeners
                        .build() // Must be called before calling show method
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userStatuses.size();
    }

    public class TopStatusViewHolder extends RecyclerView.ViewHolder {

        ItemStatusBinding binding;

        public TopStatusViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemStatusBinding.bind(itemView);
        }
    }
}
