package com.example.loginactivity.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.loginactivity.HomeActivity;
import com.example.loginactivity.PrefManager;
import com.example.loginactivity.R;
import com.example.loginactivity.login.LoginActivity;
import com.example.loginactivity.utility.Constants;
import com.google.android.material.snackbar.Snackbar;
//import com.example.xircls.R;

public class SplashActivity extends AppCompatActivity {


    PrefManager prefManager;
    Animation topAnim,bottomAnim;
    ImageView imageView;
    TextView textView;

    private final int SPLASH_DISPLAY_LENGTH = 2500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView=findViewById(R.id.imageView);
        //  textView=findViewById(R.id.welcometxt);
        //imageView.setAnimation(topAnim);
        prefManager = new PrefManager(this);
        verify();
    }


    public void verify() {
        if (!Constants.checkInternetStatus(SplashActivity.this)) {

            Snackbar snackbar = Snackbar.make(imageView, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            verify();
                        }
                    });

            snackbar.show();

        } else {


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {




                        if (prefManager.geMobile().isEmpty()) {
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));

                        } else {
                            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                        }

                    }
                }, SPLASH_DISPLAY_LENGTH);

            }

    }
}
