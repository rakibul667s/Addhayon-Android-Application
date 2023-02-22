package com.example.addhayon;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class sign_up_page extends AppCompatActivity {
    private TextView sign_in_page, checkBox, error_msg2,terms;
    private EditText name2, email2, password2, cPassword2;
    private Button signIn2;
    private FirebaseAuth mAuth;
    private CheckBox check;
    private String emailStr, passStr, confimPassStr, nameStr;
    private int code;
    private Random random;
    private boolean a, b;
    private boolean checkEmail;
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
        terms =findViewById(R.id.terms);

        random = new Random();
        code = 100000 + random.nextInt(900000);

        mAuth = FirebaseAuth.getInstance();

        sign_in_page =  findViewById(R.id.sign_in_page3);
        String text = "<font color=#5E5D5D>Already have a account. </font><font color=#F52525>Sign in</font>";
        sign_in_page.setText(Html.fromHtml(text));


        String text2 = "<font color=#5E5D5D>I agree to the </font><font color=#00663d>trems and conditions</font>";
        terms.setText(Html.fromHtml(text2));

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                termsAndCondition();
            }
        });
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                termsAndCondition();
            }
        });
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
                   if (emailStr.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {

                        mAuth.fetchSignInMethodsForEmail(emailStr)
                                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                        checkEmail = !task.getResult().getSignInMethods().isEmpty();
                                        if (checkEmail) {
                                            error_msg2.setText("This email already use *");

                                        }else{
                                            error_msg2.setText("");
                                            sendEmail();
                                            openSignUpOtpPage();
                                        }
                                    }
                                });

                    }
                }
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    public boolean validate2() {
        nameStr = name2.getText().toString().trim();
        emailStr = email2.getText().toString().trim();
        passStr = password2.getText().toString().trim();
        confimPassStr = cPassword2.getText().toString().trim();
        if (nameStr.isEmpty() && emailStr.isEmpty() && passStr.isEmpty() && confimPassStr.isEmpty() && !check.isChecked()) {
            error_msg2.setText("Please enter all data *");
            return false;
        } else if (nameStr.isEmpty()) {
            error_msg2.setText("Please enter name *");
            return false;
        } else if (!nameStr.matches("^[a-zA-Z\\s.]+$")) {
            error_msg2.setText("Name only alphadeticat characters *");
            return false;
        } else if (emailStr.isEmpty()) {
            error_msg2.setText("Please enter email *");
            return false;
        } else if (!emailStr.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            error_msg2.setText("Please enter valid email *");
            return false;
        }else if (passStr.isEmpty()) {
                error_msg2.setText("");
                error_msg2.setText("Please enter password *");
                return false;
            } else if (passStr.length() < 6) {
                error_msg2.setText("Password must be 6 charaters *");
                return false;
            } else if (confimPassStr.isEmpty()) {
                error_msg2.setText("Please enter confirm password *");
                return false;
            } else if (confimPassStr.length() < 6) {
                error_msg2.setText("Confirm password must be 6 charaters *");
                return false;
            } else if (passStr.compareTo(confimPassStr) != 0) {
                error_msg2.setText("password not matched *");
                return false;
            } else if (!check.isChecked()) {
                error_msg2.setText("Click I aggree checkbox *");
                return false;
            }else{
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


    public void sendEmail(){

        try {
            String stringSenderEmail = "addhayon.application@gmail.com";
            String stringReceiverEmail = emailStr;
            String stringPasswordSenderEmail = "qlrlujngpzmmhmeq";

            String stringHost = "smtp.gmail.com";

            Properties properties = System.getProperties();

            properties.put("mail.smtp.host", stringHost);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
                }
            });

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(stringReceiverEmail));

            mimeMessage.setSubject("Verification Code");
            String emailText1 = "Contact Us:";
            String emailText2 = "Developed by MD. RAKIBUL ISLAM";
            mimeMessage.setText("Hello "+nameStr+", \n\nDon't shear your varification code... \n\nVerification code is : "+code+"\n\n\n"+emailText2+"\n\n"+emailText1+"\nmd.rakibulislam_1@yahoo.com\n+8801704081993");

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Transport.send(mimeMessage);
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public void openSignUpOtpPage(){
        Intent intent = new Intent(sign_up_page.this, sign_up_otp_page.class);
        intent.putExtra("name", nameStr);
        intent.putExtra("email",emailStr);
        intent.putExtra("password",passStr);
        intent.putExtra("otp", code);
        startActivity(intent);
    }

    private void termsAndCondition(){
        AlertDialog.Builder builder = new AlertDialog.Builder(sign_up_page.this);
        builder.setCancelable(true);

        View view = getLayoutInflater().inflate(R.layout.tconditons,null);

       TextView oklB = view.findViewById(R.id.okB);
        TextView cancellB = view.findViewById(R.id.cancelB);

        builder.setView(view);
        AlertDialog alertDialog = builder.create();

        oklB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                check.setChecked(true);
                
            }
        });
        cancellB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                check.setChecked(false);

            }
        });
        alertDialog.show();
    }
}