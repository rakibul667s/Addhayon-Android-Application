package com.example.addhayon;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class sign_up_otp_page extends AppCompatActivity {
    private String nameStr,emailStr,passStr, s;
    private int otp2;
    private int otpN;
    private TextView emailT, wrongT;
    private RelativeLayout verify;
    private TextView buttonText;
    private LottieAnimationView buttonAnim;

    private FirebaseAuth mAuth;



    @SuppressLint("MissingInflatedId")
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_otp_page);

        verify = findViewById(R.id.verify);
        emailT=findViewById(R.id.email);
        wrongT =findViewById(R.id.wrongMsg);

        buttonText = findViewById(R.id.button_text);
        buttonAnim = findViewById(R.id.animation_view);

        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();

        nameStr = intent.getStringExtra("name");
        emailStr =intent.getStringExtra("email");
        passStr = intent.getStringExtra("password");
        otp2 = intent.getIntExtra("otp",0);
        emailT.setText(emailStr);

        TextView textView = (TextView) findViewById(R.id.sign_in_page);
        String text = "<font color=#5E5D5D>Already have a account. </font><font color=#F52525>Sign in</font>";
        textView.setText(Html.fromHtml(text));

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonAnim.setVisibility(View.VISIBLE);
                buttonAnim.playAnimation();
                buttonText.setVisibility(View.GONE);

                final EditText otpTV =findViewById(R.id.otpT);
                s =otpTV.getText().toString();
                if (s != null && !s.isEmpty()) {
                    otpN =Integer.parseInt(s);
                } else {
                    wrongT.setText("Please set your OTP code");
                }

                if(valid()){
                    signupNewUser();
                }
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignInPage();
            }
        });
    }
    public void openSignInPage() {
        Intent intent = new Intent(this, sign_in_page.class);
        startActivity(intent);
    }
    public boolean valid(){
        if(otpN!= 0){
            if(otp2 == otpN){
                return true;
            }else{
                buttonAnim.pauseAnimation();
                buttonAnim.setVisibility(View.GONE);
                buttonText.setVisibility(View.VISIBLE);
                wrongT.setText("OTP code not match..");
            }

        }
        return false;
    }
    public void signupNewUser(){
        mAuth.createUserWithEmailAndPassword(emailStr, passStr)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            DBQurey.createUserData(emailStr,nameStr, new MyCompleteListener(){
                                @Override
                                public void onSuccess(){
                                    buttonAnim.pauseAnimation();
                                    buttonAnim.setVisibility(View.GONE);
                                    buttonText.setVisibility(View.VISIBLE);
                                    Toast.makeText(sign_up_otp_page.this,"Successfully create your Account", Toast.LENGTH_SHORT).show();
                                    //-------call Sign in page--------------
                                    openSignInPage();
                                }
                                @Override
                                public void onFailure(){
                                    Toast.makeText(sign_up_otp_page.this, "Something went wrong ! Please try Later.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });



                        } else {

                            Toast.makeText(sign_up_otp_page.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}