package com.example.addhayon;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.addhayon.databinding.ActivityChatMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class ChatMainActivity extends AppCompatActivity {
    ActivityChatMainBinding binding;
    FirebaseDatabase database;
    ArrayList<User> users;
    UserAdapter userAdapter;
    TopStatusAdapter statusAdapter;
    ArrayList<UserStatus> userStatuses;
    ProgressDialog dialog;
    User user;
    private Toolbar toolbar;
    private TextView titleBar, collection;
    private MeowBottomNavigation btm;
    DatabaseReference ref;
    String key;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();

        ref = FirebaseDatabase.getInstance().getReference().child("stories").child(uid).child("statuses");
        key = ref.push().getKey();


        titleBar = findViewById(R.id.titleBar);
        collection = findViewById(R.id.collection);

        if(DBQurey.myProfile.getLanguage().equals("Bangla")){
           titleBar.setText("অধ্যয়ন");
           collection.setText("ছবি সংগ্রহ");

        }

        FirebaseMessaging.getInstance()
                .getToken()
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String token) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("token",token);
                        database.getReference().child("users")
                                .child(FirebaseAuth.getInstance().getUid())
                                .updateChildren(map);
                    }
                });

        dialog = new ProgressDialog(this);
        dialog.setMessage("Uploading Image. . .");
        dialog.setCancelable(false);


        users = new ArrayList<>();
        userStatuses = new ArrayList<>();


        database.getReference().child("users").child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user = snapshot.getValue(User.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
        userAdapter = new UserAdapter(this, users);
        statusAdapter = new TopStatusAdapter(this, userStatuses);



        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        binding.statusList.setLayoutManager(layoutManager);
        binding.statusList.setAdapter(statusAdapter);
        binding.recycleView.setAdapter(userAdapter);

        binding.recycleView.showShimmerAdapter();
        binding.statusList.showShimmerAdapter();
        database.getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    User user= snapshot1.getValue((User.class));
                    if(!user.getUid().equals(FirebaseAuth.getInstance().getUid()))
                    {
                        users.add(user);
                    }
                }

                binding.recycleView.hideShimmerAdapter();
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        database.getReference().child("stories").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    userStatuses.clear();
                    for(DataSnapshot storySnapshot : snapshot.getChildren()){
                        UserStatus status = new UserStatus();
                        status.setName(storySnapshot.child("name").getValue(String.class));
                        status.setProfileImage(storySnapshot.child("profileImage").getValue(String.class));
                        //s.setImageUrl(storySnapshot.child("profileImage").getValue(String.class));
                        status.setLastUpdated(storySnapshot.child("lastUpdated").getValue(Long.class));
                        status.setKey(storySnapshot.child(key).getValue(String.class));
                        //s.setTimeStamp(storySnapshot.child("lastUpdated").getValue(Long.class));


                        ArrayList<Status> statuses = new ArrayList<>();
                        for(DataSnapshot statusSnapshot : storySnapshot.child("statuses").getChildren()){
                            Status sampleStatus = statusSnapshot.getValue(Status.class);
                            statuses.add(sampleStatus);
                        }


                        status.setStatuses(statuses);
                        userStatuses.add(status);
                    }
                    binding.statusList.hideShimmerAdapter();
                    statusAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btm = findViewById(R.id.bottom_nav);
        btm.add(new MeowBottomNavigation.Model(1,R.drawable.ic_baseline_home));
        btm.add(new MeowBottomNavigation.Model(2,R.drawable.ic_baseline_chat_bubble_24));
        btm.add(new MeowBottomNavigation.Model(3,R.drawable.baseline_add_a_photo_24));
        btm.add(new MeowBottomNavigation.Model(4,R.drawable.ic_baseline_call));
        btm.add(new MeowBottomNavigation.Model(5,R.drawable.ic_baseline_person));



        btm.show(2,true);
        btm.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {
                switch (model.getId()){
                    case 1:
                        openHome();
                        break;
                    case 2:
                        openChat();
                        break;
                    case 3:
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent,75);


                        break;
                    case 4:
                        if(DBQurey.myProfile.getLanguage().equals("Bangla")){
                            Toast.makeText(ChatMainActivity.this, "কল সিস্টেমে এখনও কাজ চলছে ", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ChatMainActivity.this, "Working on call system", Toast.LENGTH_SHORT).show();
                        }//openMap();
                        break;
                    case 5:
                        openUserProfile();;
                        break;

                }
                return null;
            }
        });

//        binding.bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
//            @Override
//            public void onNavigationItemReselected(@NonNull MenuItem item) {
//
//                switch (item.getItemId()){
//                    case R.id.status:
//                        Intent intent = new Intent();
//                        intent.setType("image/*");
//                        intent.setAction(Intent.ACTION_GET_CONTENT);
//                        startActivityForResult(intent,75);
//                }
//            }
//        });







    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null){
            if(data.getData()!= null){
                dialog.show();
                FirebaseStorage storage = FirebaseStorage.getInstance();
                Date date = new Date();
                StorageReference reference = storage.getReference().child("stories").child(FirebaseAuth.getInstance().getUid()).child(key);
                reference.putFile(data.getData()).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if(task.isSuccessful()){
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    UserStatus userStatus = new UserStatus();
                                    userStatus.setName(user.getName());
                                    userStatus.setProfileImage(user.getProfileImg());
                                    userStatus.setLastUpdated(date.getTime());
                                    userStatus.setKey(key);

                                    String imageUrl = uri.toString();
                                    HashMap<String, Object> obj = new HashMap<>();
                                    obj.put("name",userStatus.getName());
                                    obj.put("profileImage",userStatus.getProfileImage());
                                    obj.put("lastUpdated",userStatus.getLastUpdated());
                                    obj.put(key,imageUrl);
                                    Date date1 = null;


                                    Status status = new Status(imageUrl, userStatus.getLastUpdated(),userStatus.getKey(),date1);



                                    database.getReference()
                                                    .child("stories")
                                                            .child(FirebaseAuth.getInstance().getUid())
                                                                    .updateChildren(obj);
                                    database.getReference().child("stories")
                                                    .child(FirebaseAuth.getInstance().getUid())
                                                            .child("statuses")
                                                                    .child(key)
                                                                            .setValue(status);
                                    dialog.dismiss();
                                    Intent intent2 = new Intent(ChatMainActivity.this,ChatMainActivity.class);
                                    startActivity(intent2);
                                    ChatMainActivity.this.finish();
                                }
                            });

                        }
                    }
                });
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String currentId = FirebaseAuth.getInstance().getUid();

        database.getReference().child("presence").child(currentId).setValue("Online");
    }


    @Override
    protected void onPause() {
        super.onPause();
        String currentId = FirebaseAuth.getInstance().getUid();
      database.getReference().child("presence").child(currentId).setValue("Offline");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                if(DBQurey.myProfile.getLanguage().equals("Bangla")){
                    Toast.makeText(ChatMainActivity.this, "অনুসন্ধান সিস্টেমে এখনও কাজ চলছে ", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "Working on search system", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.settings:
                Toast.makeText(this,"Search",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    private void  openHome(){
        Intent intent = new Intent(this,dashboard.class);
        startActivity(intent);
        ChatMainActivity.this.finish();
    }
    private  void  replace(Fragment fragment){
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame,fragment);
        transaction.commit();
    }

    public void openUserProfile(){
        Intent intent = new Intent(this, userProfile.class);
        startActivity(intent);
        ChatMainActivity.this.finish();
    }
    public void openChat(){
        Intent intent = new Intent(this, ChatMainActivity.class);
        startActivity(intent);
        ChatMainActivity.this.finish();
    }

    public void  openStory(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,75);
    }

}