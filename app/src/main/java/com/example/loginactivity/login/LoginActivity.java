package com.example.loginactivity.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.loginactivity.Admin.HomeAdminActivity;
import com.example.loginactivity.BaseActivity;
import com.example.loginactivity.HomeActivity;
import com.example.loginactivity.PrefManager;
import com.example.loginactivity.R;
import com.example.loginactivity.feedbackActivity.FeedBackActivity;
import com.example.loginactivity.register.RegisterationActivity;
import com.example.loginactivity.splash.SplashActivity;
import com.example.loginactivity.utility.Constants;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    EditText etPassword, etMobile;
    TextView tvNewRegistration;
    Button btnSignIn;
    private FirebaseAuth mAuth;
    String TAG = "FIREBASE";
    PrefManager prefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        initialize();
    }

    private void initialize() {

        mAuth = FirebaseAuth.getInstance();
        etPassword = (EditText) findViewById(R.id.etPassword);
        etMobile = (EditText) findViewById(R.id.etMobile);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        tvNewRegistration = (TextView) findViewById(R.id.tvNewRegistration);

        btnSignIn = (Button) findViewById(R.id.btnSignIn);
        tvNewRegistration.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);

    }
    private void dbUserVerify(){
        if (!Constants.checkInternetStatus(LoginActivity.this)) {

            showAlert("No internet connection!");
            return;
        }
        showDialog("Please wait..\nVerifying the credential.");
       String strPassword = etPassword.getText().toString().trim();
       String strMob = etMobile.getText().toString().trim();

        /********************************************************************
        >> Just like tree root
        In Our Firebase Node : Main Root is "users"
         and Mobile No is  root of the user details
       *//////////////////////////////////////////////
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(strMob);

       // Query checkUser = reference.orderByChild("mobile").equalTo(strPassword);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {

                    cancelDialog();

                    String passFromFireBaseDb =snapshot.child("password").getValue().toString();

                    if(passFromFireBaseDb.equals(strPassword)){

                        String nameFromDb = snapshot.child("name").getValue().toString();
                        String emailFromDb = snapshot.child("email").getValue().toString();
                        String mobFromDb = snapshot.child("mobile").getValue().toString();

                        //SharedPreferences:  Save data in key value pair , used for saving and retrieving the data
                        prefManager = new PrefManager(LoginActivity.this);
                        prefManager.setUserName(nameFromDb);
                        prefManager.setMobile(mobFromDb);
                        prefManager.setEmailID(emailFromDb);

                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();

                    }
                    else{

                        Snackbar.make(etMobile, "either mbile no. or password is incorrect", Snackbar.LENGTH_LONG).show();

                        return;
                    }

                }

                else{

                    cancelDialog();

                    Snackbar.make(etMobile, "either mbile no. or password is incorrect", Snackbar.LENGTH_LONG).show();

                    return;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Snackbar.make(etMobile, "No Such user exist", Snackbar.LENGTH_LONG).show();
            }
        });
    }




    @Override
    public void onClick(View view) {
        Constants.hideKeyBoard(view, LoginActivity.this);
        switch (view.getId()) {
            case R.id.tvNewRegistration:

                startActivity(new Intent(LoginActivity.this, RegisterationActivity.class));
                break;

            case R.id.btnSignIn:
                if (!isEmpty(etMobile)) {
                    etMobile.requestFocus();
                    Snackbar.make(etMobile, "Enter Mobile", Snackbar.LENGTH_LONG).show();
                    // etMobile.setError("Enter Mobile");
                    return;
                }
                if (!isEmpty(etPassword)) {
                    etPassword.requestFocus();
                    Snackbar.make(etMobile, "Enter Password", Snackbar.LENGTH_LONG).show();
                    //  etPassword.setError("Enter Password");
                    return;
                }
                if (etPassword.getText().toString().equals("admin")  && etMobile.getText().toString().equals("8286755329")) {

                    startActivity(new Intent(LoginActivity.this, HomeAdminActivity.class));
                    finish();

                    return;
                }
                dbUserVerify();


                break;
        }
    }
}