package com.keisardevs.mymovieswithsql.Controllers.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.keisardevs.mymovieswithsql.R;

public class SplashActivity extends AppCompatActivity {

    private ImageView splashImage;
    private static int timer = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashActivity.this, MoviesPageActivity.class);
                startActivity(intent);
                finish();
            }
        },timer);

        splashImage = findViewById(R.id.splash_image_view);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.splash_animation);
        splashImage.startAnimation(animation);
    }



}