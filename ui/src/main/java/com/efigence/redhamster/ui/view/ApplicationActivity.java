package com.efigence.redhamster.ui.view;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.efigence.redhamster.ui.R;
import com.efigence.redhamster.ui.di.components.ApplicationComponent;
import com.efigence.redhamster.ui.presenters.ApplicationPresenter;
import com.efigence.redhamster.ui.view.base.BaseAppCompatActivity;
import com.efigence.redhamster.ui.view.list.IssuesListFragment;
import com.efigence.redhamster.ui.view.list.ReadyToReportIssuesFragment;
import com.efigence.redhamster.ui.view.settings.SettingsFragment;

import javax.inject.Inject;
import javax.inject.Named;

public class ApplicationActivity extends BaseAppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ApplicationPresenter.ApplicationUI {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.drawer_layout) DrawerLayout drawer;
    @Bind(R.id.nav_view) NavigationView navigationView;

    @Inject ApplicationPresenter presenter;

    @Inject StartedIssueFragment startedFragment;
    @Inject ReadyToReportIssuesFragment readyToReport;
    @Inject @Named("AssignedToMeFragment") IssuesListFragment assignedToMe;
    @Inject @Named("MyRecentlyUsedFragment") IssuesListFragment recentlyUsed;
    @Inject SettingsFragment settingsFragment;

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
        ApplicationComponent applicationComponent = getApplicationComponent();
        applicationComponent.inject(this);

        setContentView(R.layout.activity_application);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        FloatingActionButton startIssueActionButton = (FloatingActionButton) findViewById(R.id.startIssueActionButton);
        startIssueActionButton.setOnClickListener(view -> presenter.onDisplayStartIssue(null));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.application, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        if (!presenter.isAccessKeySpecified()){
            new AlertDialog.Builder(this)
                    .setCancelable(true)
                    .setMessage("Please fill api access key")
                    .create()
                    .show();
            return false;
        }
        int id = item.getItemId();
        if (id == R.id.nav_started) {
            presenter.onDisplayStartedIssue();
        } else if (id == R.id.nav_ready_to_report) {
            presenter.onDisReadyToReport();
        } else if (id == R.id.nav_recently_used) {
            presenter.onDisplayMyRecentlyUsed();
        } else if (id == R.id.nav_assigned_to_me) {
            presenter.onDisplayAssignedToMe();
        } else if (id == R.id.nav_settings) {
            presenter.onDisplaySettings();
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void displayStaredIssue() {
        displayFragment(startedFragment);
        navigationView.setCheckedItem(R.id.nav_started);
    }

    @Override
    public void displayReadyToReport() {
        displayFragment(readyToReport);
    }

    @Override
    public void displayAssignedToMe() {
        displayFragment(assignedToMe);
    }

    @Override
    public void displayRecentlyUsed() {
        displayFragment(recentlyUsed);
    }

    @Override
    public void displaySettings() {
        displayFragment(settingsFragment);
    }

    @Override
    public void displayStartIssue(String issueId) {
        LayoutInflater inflater = getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.start_issue_dialog, null);
        final TextView issueIdView = (TextView) dialogView.findViewById(R.id.startIssueId);
        issueIdView.setText(issueId);
        new AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton(R.string.startIssueStart, (dialog, which) -> {
                    String issue = issueIdView.getText().toString();
                    if (issue.isEmpty()){
                        issueIdView.setError("Filed cannot be empty.");
                        return;
                    }
                    presenter.startIssue(issue);
                })
                .create()
                .show();
    }


    private void displayFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.app_container, fragment)
                .commit();
    }
}
