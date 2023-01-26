package com.example.addhayon;

import static com.example.addhayon.DBQurey.g_firestore;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {
    private EditText name, bio, address, sclClg, phn;
    private CircleImageView saveB, cancelB;


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

        saveB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                saveData();
            }
        });


    }


    private void saveData(){
        String sname = name.getText().toString().trim();
        FirebaseFirestore.getInstance()
                .collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .update("NAME", sname);

    }
}