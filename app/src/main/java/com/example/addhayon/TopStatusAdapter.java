package com.example.addhayon;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;

import omari.hamza.storyview.StoryView;
import omari.hamza.storyview.callback.StoryClickListeners;
import omari.hamza.storyview.model.MyStory;

public class TopStatusAdapter extends RecyclerView.Adapter<TopStatusAdapter.TopStatusViewHolder> {
    String sl;
    Context context;
    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<UserStatus> userStatuses;
    String sTime, sImg,sKey, childKey;


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

        if(userStatus.getStatuses().size() != 0) {
            Status lastStatus = userStatus.getStatuses().get(userStatus.getStatuses().size() - 1);
            Glide.with(context).load(lastStatus.getImageUrl()).into(holder.binding.image);
            holder.binding.circularStatusView.setPortionsCount(userStatus.getStatuses().size());
        }else{
            holder.binding.circularStatusView.setPortionsCount(0);
        }



        holder.binding.circularStatusView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                ArrayList<MyStory> myStories = new ArrayList<>();
                ArrayList<String> newS = new ArrayList<>();
                for(Status status : userStatus.getStatuses()) {
                    Date date = new Date();
                    sTime = String.valueOf(status.getTimeStamp());
                    sImg = String.valueOf(status.getImageUrl());
                    sKey = String.valueOf(status.getKey());
                    long diff =   date.getTime()-status.getTimeStamp();
                    newS.add(status.getKey());

                    long diffHours = diff / (60*60*1000);
                    if (diffHours >=0){
                        myStories.add(new MyStory(status.getImageUrl()));
                    }


                }


                    new StoryView.Builder(((ChatMainActivity) context).getSupportFragmentManager())
                            .setStoriesList(myStories)// Required
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

                                    int storyPosition = position;

                                    // Get the URL of the story image




                                    // sl = userStatus2.getName();
                                    if (userStatus.getName().equals(DBQurey.myProfile.getName())) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                        builder.setMessage("Delete story. . .");
                                        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                // Delete the story from the database
                                                if(userStatus.getStatuses().size() ==1){
                                                    FirebaseStorage storage = FirebaseStorage.getInstance();
                                                    StorageReference reference2 = storage.getReference().child("stories/"+uid+"/"+newS.get(position));
                                                    reference2.delete();

                                                    reference = FirebaseDatabase.getInstance().getReference().child("stories");
                                                    reference.child(uid).removeValue();

                                                    dialog.dismiss();
                                                    Intent intent2 = new Intent(v.getContext(),ChatMainActivity.class);
                                                    context.startActivity(intent2);

                                                }else {
                                                    FirebaseStorage storage = FirebaseStorage.getInstance();
                                                    StorageReference reference2 = storage.getReference().child("stories/"+uid+"/"+newS.get(position));
                                                    reference2.delete();
                                                    reference = FirebaseDatabase.getInstance().getReference().child("stories");
                                                    reference.child(uid).child("statuses").child(newS.get(position)).removeValue();
                                                    dialog.dismiss();
                                                }
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
