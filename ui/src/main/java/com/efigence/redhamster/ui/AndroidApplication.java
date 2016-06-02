package com.efigence.redhamster.ui;

import android.app.Application;
import com.efigence.redhamster.ui.di.components.ApplicationComponent;
import com.efigence.redhamster.ui.di.components.DaggerApplicationComponent;
import com.efigence.redhamster.ui.di.modules.ApplicationModule;

public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

}
