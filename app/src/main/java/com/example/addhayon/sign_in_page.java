package com.example.addhayon;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class sign_in_page extends AppCompatActivity {
    private EditText email, password;
    private Button login_button;
    private TextView forgotPassword, signupPage, error_msg;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);

        mAuth = FirebaseAuth.getInstance();
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login_button = findViewById(R.id.logoin_button);
        forgotPassword = findViewById(R.id.forgot_password);
        signupPage = findViewById(R.id.sign_up_page);
        error_msg = findViewById(R.id.error_msg);

        String text = "<font color=#5E5D5D>New User? </font><font color=#F52525>Register Now</font>";
        signupPage.setText(Html.fromHtml(text));


        signupPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignInPage();
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfpPage();
            }
        });



        //---------------Login check-----------------------------
        login_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
            @Override
            public void onClick(View v) {


                if(validateData()){
                    login();
                    //openDashboradPage();
                }
            }

        });
    }
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public boolean validateData(){
        boolean check = false;
       boolean status = false;
       if(email.getText().toString().isEmpty() && password.getText().toString().isEmpty() ){
           error_msg.setText("Please enter mail and password *");
           return false;
       }else if(email.getText().toString().isEmpty()){
           error_msg.setText("Please enter mail *");
           return false;
       }else if(password.getText().toString().isEmpty() ){

           error_msg.setText("Please enter password *");
           return false;
       }else if(password.getText().toString().length() <6){
           error_msg.setText("Password must be 6 charaters *");
           return false;
       }else {
           return true;

       }

    }
    public void login(){
        mAuth.signInWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString().trim())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            DBQurey.loadCategories(new MyCompleteListener(){
                                @Override
                                public void onSuccess(){
                                    Intent intent = new Intent(sign_in_page.this,dashboard.class);
                                    startActivity(intent);
                                    sign_in_page.this.finish();
                                }
                                @Override
                                public void onFailure(){
                                    Toast.makeText(sign_in_page.this, "Something went wrong ! Please try Later.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            error_msg.setText("Please correct mail and password *");
                        }
                    }
                });
    }

//    public void  googleSingin(){
//        Intent signInIntent = mGoogleSignInClient.getSingInIntent();
//        startActivity(signInIntent.RC_SING_IN);
//
//    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        switch (requestCode) {
//            case REQ_ONE_TAP:
//                try {
//                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(data);
//                    String idToken = credential.getGoogleIdToken();
//                    if (idToken !=  null) {
//                        // Got an ID token from Google. Use it to authenticate
//                        // with Firebase.
//                        Log.d(TAG, "Got ID token.");
//                    }
//                } catch (ApiException e) {
//                    // ...
//                }
//                break;
//        }
//    }

    public void openSignInPage() {
        Intent intent = new Intent(this,sign_up_page.class);
        startActivity(intent);
    }
    public void openfpPage(){
        Intent intent = new Intent(this,forgot_password_page1.class);
        startActivity(intent);
    }
    public void openDashboradPage(){
        Intent intent = new Intent(this,dashboard.class);
        startActivity(intent);
    }
}