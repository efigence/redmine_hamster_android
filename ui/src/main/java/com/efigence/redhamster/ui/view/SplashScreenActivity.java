package com.efigence.redhamster.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import com.efigence.redhamster.ui.R;


public class SplashScreenActivity extends AppCompatActivity {
    private static final int STOP_SPLASH = 0;
    //time in milliseconds
    private static final int SPLASH_TIME = 3000;

    private final Handler splashHandler = new Handler() {
        /* (non-Javadoc)
         * @see android.os.Handler#handleMessage(android.os.Message)
         */
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case STOP_SPLASH:
                    startActivity(new Intent(SplashScreenActivity.this, ApplicationActivity.class));
                    finish();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        delayedHide(SPLASH_TIME);
    }

    private void delayedHide(int delayMillis) {
        Message msg = new Message();
        msg.what = STOP_SPLASH;
        splashHandler.sendMessageDelayed(msg, delayMillis);
    }

}
