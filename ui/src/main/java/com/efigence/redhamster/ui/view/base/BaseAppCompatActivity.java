package com.efigence.redhamster.ui.view.base;

import android.support.v7.app.AppCompatActivity;
import com.efigence.redhamster.ui.ApplicationHelper;
import com.efigence.redhamster.ui.di.components.ApplicationComponent;

public class BaseAppCompatActivity extends AppCompatActivity {

    protected ApplicationComponent getApplicationComponent(){
        return ApplicationHelper.getApplicationComponent(this);
    }
}
