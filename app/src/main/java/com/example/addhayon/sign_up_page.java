package com.example.addhayon;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class sign_up_page extends AppCompatActivity {
    private TextView sign_in_page, checkBox, error_msg2;
    private EditText name2, email2, password2, cPassword2;
    private Button signIn2;
    private FirebaseAuth mAuth;
    private CheckBox check;
    private String emailStr, passStr, confimPassStr, nameStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        name2 = findViewById(R.id.name2);
        email2 = findViewById(R.id.email2);
        password2 = findViewById(R.id.password2);
        cPassword2 = findViewById(R.id.cPassword2);
        signIn2 = findViewById(R.id.signIn2);
        error_msg2 = findViewById(R.id.error_msg2);
        check = findViewById(R.id.checkBox);

        mAuth = FirebaseAuth.getInstance();

        sign_in_page =  findViewById(R.id.sign_in_page3);
        String text = "<font color=#5E5D5D>Already have a account. </font><font color=#F52525>Sign in</font>";
        sign_in_page.setText(Html.fromHtml(text));

        checkBox = findViewById(R.id.checkBox);
        String text2 = "<font color=#5E5D5D>I agree to the </font><font color=#00663d>trems and conditions</font>";
        checkBox.setText(Html.fromHtml(text2));

        sign_in_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUpPage();
            }
        });

        signIn2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View v) {
               // openSignUpOtpPage();
                if(validate2()){
                    signupNewUser();
                }
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public boolean validate2(){
        nameStr = name2.getText().toString().trim();
        emailStr = email2.getText().toString().trim();
        passStr = password2.getText().toString().trim();
        confimPassStr = cPassword2.getText().toString().trim();
        if(nameStr.isEmpty() && emailStr.isEmpty() && passStr.isEmpty() && confimPassStr.isEmpty() && !check.isChecked()){
            error_msg2.setText("Please enter all data *");
            return false;
        }else if(nameStr.isEmpty()){
            error_msg2.setText("Please enter name *");
            return false;
        }else if(emailStr.isEmpty()){
            error_msg2.setText("Please enter email *");
            return false;
        }else if(passStr.isEmpty() ){
            error_msg2.setText("Please enter password *");
            return false;
        }else if(passStr.length() <6){
            error_msg2.setText("Password must be 6 charaters *");
            return false;
        }else if(confimPassStr.isEmpty() ){
            error_msg2.setText("Please enter confirm password *");
            return false;
        }else if(confimPassStr.length() <6){
            error_msg2.setText("Confirm password must be 6 charaters *");
            return false;
        }else if(passStr.compareTo(confimPassStr) != 0){
            error_msg2.setText("password not matched *");
            return false;
        }else if(!check.isChecked()){
            error_msg2.setText("Click I aggree checkbox *");
            return false;
        }else {
            return true;

        }

    }
    public void signupNewUser(){
        mAuth.createUserWithEmailAndPassword(emailStr, passStr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(sign_up_page.this,"sign up Successfull", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();

                            DBQurey.createUserData(emailStr,nameStr, new MyCompleteListener(){
                                @Override
                                public void onSuccess(){
                                    //-------call Sign in page--------------
                                    openSignUpPage();
                                }
                                @Override
                                public void onFailure(){
                                    Toast.makeText(sign_up_page.this, "Something went wrong ! Please try Later.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });



                        } else {

                            Toast.makeText(sign_up_page.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void openSignUpPage() {
        Intent intent = new Intent(this, sign_in_page.class);
        startActivity(intent);
    }
    public void openSignUpOtpPage(){
        Intent intent = new Intent(this, sign_up_otp_page.class);
        startActivity(intent);
    }
}