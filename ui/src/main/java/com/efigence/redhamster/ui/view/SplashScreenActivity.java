package com.efigence.redhamster.ui.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import com.efigence.redhamster.ui.R;
import com.efigence.redhamster.ui.presenters.SplashPresenter;
import com.efigence.redhamster.ui.view.base.BaseAppCompatActivity;

import javax.inject.Inject;


public class SplashScreenActivity extends BaseAppCompatActivity implements SplashPresenter.SplashUI {

    @Inject SplashPresenter presenter;

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onAttach(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onDetach();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent().inject(this);

        presenter.initData();

        setContentView(R.layout.activity_splash_screen);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }
    }

    @Override
    public void openApplication() {
        startActivity(new Intent(this, ApplicationActivity.class));
        finish();
    }
}
