package it.tabaccaiofurbo.com.tabaccaiofurboapp.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import it.tabaccaiofurbo.com.tabaccaiofurboapp.R;
import it.tabaccaiofurbo.com.tabaccaiofurboapp.utils.Constants;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        TextView tvTabaccaio = (TextView) findViewById(R.id.textTabaccaio);
        TextView tvFurbo = (TextView) findViewById(R.id.textFurbo);
        Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/Neuropolitical.ttf");
        tvTabaccaio.setTypeface(typeFace);
        tvFurbo.setTypeface(typeFace);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent i = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(i);
                // close this activity
                finish();
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
            }
        }, Constants.SPLASH_TIMEOUT);
    }
}
