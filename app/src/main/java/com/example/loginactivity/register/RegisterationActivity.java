package com.example.loginactivity.register;

import android.content.Intent;
import android.os.Bundle;

import com.example.loginactivity.BaseActivity;
import com.example.loginactivity.HomeActivity;
import com.example.loginactivity.OTPActivity;
import com.example.loginactivity.PrefManager;
import com.example.loginactivity.login.LoginActivity;
import com.example.loginactivity.model.RegisterationEntity;
import com.example.loginactivity.utility.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.loginactivity.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class RegisterationActivity extends BaseActivity implements View.OnClickListener {

    EditText etName, etMobile, etEmail, etPassword, etconfirmPassword;
    Button  btnSubmit;
    boolean isMobileAlreadyExist = false;
    // TextView txtModel;

    FirebaseDatabase rootNode ;
    DatabaseReference reference ;
    private FirebaseAuth mAuth;
    String OTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        mAuth = FirebaseAuth.getInstance();
        Constants.hideKeyBoard(etName, RegisterationActivity.this);

    }
    private void init() {
        etName =  findViewById(R.id.etName);
        etMobile =  findViewById(R.id.etMobile);
        etEmail =  findViewById(R.id.etEmail);
        etPassword =  findViewById(R.id.etPassword);
        etconfirmPassword =  findViewById(R.id.etconfirmPassword);
        btnSubmit =  findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(this);
    }

    private boolean validateRegistration() {
        if (!isEmpty(etName)) {
            etName.requestFocus();
            etName.setError("Enter Name");
            return false;
        }



        if (!isEmpty(etMobile)) {
            etMobile.requestFocus();
            etMobile.setError("Enter Mobile");
            return false;
        }
        if (!isValidePhoneNumber(etMobile)) {
            etMobile.requestFocus();
            etMobile.setError("Enter Valid Mobile");
            return false;
        }
        if (!isEmpty(etEmail)) {
            etEmail.requestFocus();
            etEmail.setError("Enter Email");
            return false;
        }
        if (!isValideEmailID(etEmail)) {
            etEmail.requestFocus();
            etEmail.setError("Enter Valid Email");
            return false;
        }

        if (!isEmpty(etPassword)) {
            etPassword.requestFocus();
            etPassword.setError("Enter Password");
            return false;
        }
//        if (etPassword.getText().toString().trim().length() < 3) {
//            etPassword.requestFocus();
//            etPassword.setError("Minimum length should be 3");
//            return false;
//        }
        if (!isEmpty(etconfirmPassword)) {
            etconfirmPassword.requestFocus();
            etconfirmPassword.setError("Confirm Password");
            return false;
        }
        if (!etPassword.getText().toString().equals(etconfirmPassword.getText().toString())) {
            etconfirmPassword.requestFocus();
            etconfirmPassword.setError("Password Mismatch");
            return false;
        }
        return true;
    }


    private void dbMobVerify(){

        showDialog();
        String strMob = etMobile.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(strMob);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    cancelDialog();
                    showAlert("Mobile no. is already used by Existing User!!" );  // Mob No. already Exist

                }else{

                    saveDataInFireBaseDB();                        // Save in FireBase Database
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Snackbar.make(etMobile, error.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void saveDataInFireBaseDB(){


        // get All data

        String name = etName.getText().toString();
        String mobile = etMobile.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();


        rootNode =  FirebaseDatabase.getInstance();
        DatabaseReference reference = rootNode.getReference("users");    // Firebase base Main Root is  users"

        RegisterationEntity registerationEntity = new RegisterationEntity(name,mobile,email,password);

        reference.child(mobile).setValue(registerationEntity);

        cancelDialog();
        getCustomToast("User has been registered Successfully !");

        startActivity(new Intent(this, LoginActivity.class));
        finish();



    }



    @Override
    public void onClick(View view) {
        Constants.hideKeyBoard(view, this);
        switch (view.getId()) {

            case R.id.btnSubmit:

                if (validateRegistration() == true) {

                    if (!Constants.checkInternetStatus(RegisterationActivity.this)) {

                        showAlert("No internet connection!");
                        return;
                    }

                    dbMobVerify();



               }

                break;
        }


    }

    private void  mailAuthentication(){
        //        mAuth.createUserWithEmailAndPassword(email,password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//
//                        if(task.isSuccessful()){
//
//                            RegisterationEntity registerationEntity = new RegisterationEntity(name,mobile,email,password);
//
//                            reference.child(FirebaseAuth.getInstance().getUid())
//                                    .setValue(registerationEntity).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                     cancelDialog();
//
//                                    if(task.isSuccessful()){
//
//                                        showAlert("User has been registered Successfully !");
//                                    }else {
//
//                                        showAlert("Failed in registered!  Try Again");
//                                    }
//
//                                }
//                            });
//                        }else{
//
//                            cancelDialog();
//                        }
//
//                    }
//                });

    }
}