package th.in.phompang.todobullet.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import java.util.logging.Handler;

import th.in.phompang.todobullet.R;

public class SplashScreenActivity extends AppCompatActivity {

    android.os.Handler handler;
    Runnable runnable;
    long delay_time;
    long time = 3000L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        handler = new android.os.Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };

    }

    public void onResume() {
        super.onResume();
        delay_time = time;
        handler.postDelayed(runnable, delay_time);
        time = System.currentTimeMillis();
    }

    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
        time = delay_time - (System.currentTimeMillis() - time);
    }
}
