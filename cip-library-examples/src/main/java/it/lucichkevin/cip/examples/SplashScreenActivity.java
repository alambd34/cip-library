package it.lucichkevin.cip.examples;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import it.lucichkevin.cip.Utils;


public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //  First and unique instance - set the Context into Utils class
        Utils.init(getBaseContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Animation shake = AnimationUtils.loadAnimation(this, R.anim.animation_splashscreen);
        findViewById(R.id.imgLogo).startAnimation(shake);
    }

    @Override
    protected void onStart() {
        super.onStart();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreenActivity.this, TestMainActivity.class);
                startActivity(i);
                finish();
            }
        }, 3000);

    }

}