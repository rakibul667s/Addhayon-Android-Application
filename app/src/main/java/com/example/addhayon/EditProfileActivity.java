package com.example.addhayon;

import static com.example.addhayon.DBQurey.g_firestore;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaCodec;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.ArrayMap;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.units.qual.C;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {
    private EditText name, bio, address, sclClg, phn, dateofBirth;
    private ImageView saveB, cancelB,coverB,profileB, coverImg, dates;
    private CircleImageView  profileImg;

    private final int pData = 1, cData=2000;

    private String pIF, cIF;

    private boolean pBoolean, cBoolean, all;

    private Bitmap bitmap;
    private Uri imageUri;

    private FirebaseDatabase database;
    private FirebaseStorage storage;

    private Uri pfilePath, cfilePath;

    private StorageReference image1Ref,image2Ref, storageRef ;
    private UploadTask uploadTask1, uploadTask2;

    private String userId;

    private  Bundle extras;
    private Bitmap imageBitmap;

    private byte[] imageData;
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private int year;
    private int month;
    private int day;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        name = findViewById(R.id.name);
        bio = findViewById(R.id.bio);
        address = findViewById(R.id.address);
        sclClg = findViewById(R.id.scl_clg);
        phn = findViewById(R.id.phn);
        saveB = findViewById(R.id.save);
        cancelB = findViewById(R.id.cancel);
        coverImg = findViewById(R.id.cover_img);
        profileImg = findViewById(R.id.profile_image);
        profileB = findViewById(R.id.profileB);
        coverB = findViewById(R.id.coverB);
        calendar = Calendar.getInstance();

        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();



        dateofBirth = findViewById(R.id.dateOfBirth);

        dateofBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(EditProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateofBirth.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        profileB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedProfileImage();
            }
        });

        coverB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedCoverImage();
            }
        });

        saveB.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profilePage();
            }
        });


    }

    private void profilePage(){
        Intent intent = new Intent(this, userProfile.class);
        startActivity(intent);
        EditProfileActivity.this.finish();
    }


    private void selectedProfileImage(){
        pBoolean = true;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), pData);

    }

    private void selectedCoverImage(){
        cBoolean = true;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), cData);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == pData && resultCode == RESULT_OK && data != null && data.getData() != null) {

            pfilePath = data.getData();
            profileImg.setImageURI(pfilePath);
        }
        if (requestCode == cData && resultCode == RESULT_OK && data != null && data.getData() != null) {

            cfilePath = data.getData();
            coverImg.setImageURI(cfilePath);
        }

    }




    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private void saveData(){
        String sname = name.getText().toString().trim();
        String sbio = bio.getText().toString().trim();
        String saddress = address.getText().toString().trim();
        String ssclClg = sclClg.getText().toString().trim();
        String sdateofBirth= dateofBirth.getText().toString().trim();
        String sphn = phn.getText().toString().trim();

        if(!sname.isEmpty()){
            if(!sname.matches("^[a-zA-Z\\s.]+$")){
                Toast.makeText(EditProfileActivity.this, "Name only alphadeticat characters ",
                        Toast.LENGTH_SHORT).show();
            }else{
                FirebaseFirestore.getInstance()
                        .collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .update("NAME", sname);
                all= true;
            }
        }
        if(!sbio.isEmpty()){
            FirebaseFirestore.getInstance()
                    .collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .update("BIO", sbio);
            all= true;
        }
        if(!saddress.isEmpty()){
            FirebaseFirestore.getInstance()
                    .collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .update("ADDRESS", saddress);
            all= true;
        }
        if(!ssclClg.isEmpty()){
            FirebaseFirestore.getInstance()
                    .collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .update("SCL_CLG", ssclClg);
            all= true;
        }
        if(!sdateofBirth.isEmpty()){
            FirebaseFirestore.getInstance()
                    .collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .update("DATE_OF_BIRTH", sdateofBirth);
            all= true;
        }
        if(!sphn.isEmpty()){
            FirebaseFirestore.getInstance()
                    .collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .update("PHONE", sphn);
            all= true;
        }
        if(pBoolean){
            all= true;
            StorageReference storageRef = storage.getReference().child("allUsers/"+userId+"/image_profile.jpg");
            UploadTask uploadTask = storageRef.putFile(pfilePath);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Get the download URL of the uploaded image
                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String downloadUrl = uri.toString();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(EditProfileActivity.this, "Something went wrong ! Please try Later.",
                            Toast.LENGTH_SHORT).show();
                }
            });


            }


        if(cBoolean){
            all= true;
            StorageReference storageRef = storage.getReference().child("allUsers/"+userId+"/image_cover.jpg");
            UploadTask uploadTask = storageRef.putFile(cfilePath);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Get the download URL of the uploaded image
                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String downloadUrl = uri.toString();

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(EditProfileActivity.this, "Something went wrong ! Please try Later.",
                            Toast.LENGTH_SHORT).show();
                    // Handle the failure of the upload
                }
            });


        }
        if(all){
            Toast.makeText(EditProfileActivity.this, "Safely update  your data",
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, userProfile.class);
            startActivity(intent);
            EditProfileActivity.this.finish();
        }if(!all){
            Toast.makeText(EditProfileActivity.this, "Empty data not update",
                    Toast.LENGTH_SHORT).show();
        }


    }


}