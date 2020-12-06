package com.example.loginactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chaos.view.PinView;
import com.example.loginactivity.model.RegisterationEntity;
import com.example.loginactivity.register.RegisterationActivity;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends BaseActivity {
    TextView alert;
    PinView pinView;
    Button btnverify;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    String mobile;
    String OTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_t_p);
        alert = findViewById(R.id.alert);
        pinView = findViewById(R.id.pinView);
        btnverify = findViewById(R.id.btnVerify);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        alert.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();
       // String VerificationId = getIntent().getStringExtra("VerificationId");
        String mobile = getIntent().getStringExtra("MOBILE");

        initOTP(mobile);
        btnverify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pinView.getText().toString().isEmpty()){
                    showAlert("Blank Field");
                }
                  PhoneAuthCredential credential = PhoneAuthProvider.getCredential(OTP, pinView.getText().toString());
                  signInWithPhoneAuthCredential(credential);

            }
        });
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks  = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String strOTP, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(strOTP, forceResendingToken);
            OTP = strOTP;
            showAlert(""+OTP);

        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            String code = phoneAuthCredential.getSmsCode();
            if(code != null){
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            showAlert("Verification Failed");
        }
    };




    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this,task -> {
                    if(task.isSuccessful()){

                        startActivity(new Intent(OTPActivity.this, HomeActivity.class));
                        finish();
                    }else{
                        if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                            showAlert("Verification Not Completed! Try Again.");
                        }
                    }

                });

    }



    private void initOTP(String mobNo){

        mobNo = "+91" + mobNo;
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mobNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    //region comment
    //    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d("TAG", "signInWithCredential:success");
//
//                            FirebaseUser user = task.getResult().getUser();
//
//                            SendToHomeActivity();
//
//                            // ...
//                        } else {
//                            // Sign in failed, display a message and update the UI
//                            Log.w("TAG", "signInWithCredential:failure", task.getException());
//                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                                // The verification code entered was invalid
//                            }
//                        }
//                    }
//                });
//    }
    //endregion


}

  //  }
    //        }
//private void SendToHomeActivity(){
//        Intent intent=new Intent(MainActivity.this,HomeActivity.class);
//        startActivity(intent);
//        finish();
//        }
//
//        }