package com.efigence.redhamster.ui;

import android.app.Activity;
import com.efigence.redhamster.ui.di.components.ApplicationComponent;

public final class ApplicationHelper {

    public static ApplicationComponent getApplicationComponent(Activity activity){
        return getAndroidApplication(activity).getApplicationComponent();
    }

    public static AndroidApplication getAndroidApplication(Activity activity){
        return (AndroidApplication)activity.getApplication();
    }

}
