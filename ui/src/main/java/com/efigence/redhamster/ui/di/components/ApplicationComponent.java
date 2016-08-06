package com.efigence.redhamster.ui.di.components;

import android.content.Context;
import com.efigence.redhamster.ui.di.modules.ApiModule;
import com.efigence.redhamster.ui.di.modules.ApplicationModule;
import com.efigence.redhamster.ui.view.ApplicationActivity;
import com.efigence.redhamster.ui.view.SplashScreenActivity;
import com.efigence.redhamster.ui.view.StartedIssueFragment;
import com.efigence.redhamster.ui.view.list.FilterableIssuesListFragment;
import com.efigence.redhamster.ui.view.list.IssuesListFragment;
import com.efigence.redhamster.ui.view.list.ReadyToReportIssuesFragment;
import com.efigence.redhamster.ui.view.settings.SettingsFragment;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {ApplicationModule.class, ApiModule.class})
public interface ApplicationComponent {

    void inject(SplashScreenActivity splashScreenActivity);
    void inject(ApplicationActivity activity);
    void inject(StartedIssueFragment fragment);
    void inject(IssuesListFragment fragment);
    void inject(SettingsFragment fragment);
    void inject(ReadyToReportIssuesFragment fragment);

    void inject(FilterableIssuesListFragment fragment);

    //Exposed to sub-graphs.
    Context context();
}
