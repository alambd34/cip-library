package it.lucichkevin.cip.examples;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import it.lucichkevin.cip.Utils;
import it.lucichkevin.cip.preferences.PreferencesManager;


public class SplashScreenActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_splash_screen);

		//  First and unique instance - set the Context into Utils class
		Utils.init(getBaseContext());
		PreferencesManager.setShouldBeLogToLogcat(true);

		((TextView) findViewById(R.id.app_version)).setText( getString(R.string.version_text,Utils.App.getVersionName()) );

//		Animation shake = AnimationUtils.loadAnimation(this, R.anim.animation_splashscreen);
//		findViewById(R.id.imgLogo).startAnimation(shake);
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
		}, 1500);

	}

}