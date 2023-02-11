package com.example.addhayon;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class Delete_diolog extends AppCompatActivity {
    private TextView title, c, content;
    private Button yes, cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_diolog);

        title.findViewById(R.id.title2);

        if(DBQurey.myProfile.getLanguage().equals("Bangla")){
            title.setText("মুছিয়া ফেলা");
        }
    }
}
