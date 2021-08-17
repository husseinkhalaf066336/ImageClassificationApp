package com.eng_hussein_khalaf066336.tensorimageclassification.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.eng_hussein_khalaf066336.tensorimageclassification.R;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView imageView_background,imageView_logo;
    LottieAnimationView lottieAnimationView,lottieAnimationView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        imageView_background = findViewById(R.id.SplashScreen_imageView_background);
        imageView_logo = findViewById(R.id.SplashScreen_imageView_logo);
        lottieAnimationView = findViewById(R.id.animationView);
        lottieAnimationView2 = findViewById(R.id.animationView);

        imageView_background.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        imageView_logo.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        lottieAnimationView2.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, StartingActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4500);


    }
}
