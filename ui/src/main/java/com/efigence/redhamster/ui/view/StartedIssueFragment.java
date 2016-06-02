package com.efigence.redhamster.ui.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.efigence.redhamster.ui.R;
import com.efigence.redhamster.ui.model.HamsterIssueViewModel;
import com.efigence.redhamster.ui.presenters.StartedIssuePresenter;
import com.efigence.redhamster.ui.view.base.BaseFragment;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Timer;
import java.util.TimerTask;

@Singleton
public class StartedIssueFragment extends BaseFragment implements StartedIssuePresenter.StartedIssueUI {

    @Bind(R.id.startedIssueLayout)
    TableLayout layout;
    @Bind(R.id.startedIssueId)
    TextView issueId;
    @Bind(R.id.startedIssueSubject)
    TextView subject;
    @Bind(R.id.startedIssueStartDate)
    TextView startDate;
    @Bind(R.id.startedIssueInfo)
    TextView startedIssueInfo;
    @Bind(R.id.startedIssueSpentTime)
    TextView startedIssueSpentTime;
    @Bind(R.id.startedIssueStopButton)
    Button stopButton;

    @Inject
    StartedIssuePresenter presenter;
    private HamsterIssueViewModel viewModel;

    private Timer timer;
    private TimerTask timerTask;

    @Inject
    public StartedIssueFragment(){
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onAttach(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        cancelTimer();
        presenter.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getApplicationComponent().inject(this);
        View rootView = inflater.inflate(R.layout.started_issue_fragment, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        presenter.onDisplay();
    }

    @Override
    public void display(HamsterIssueViewModel viewModel) {
        this.viewModel = viewModel;
        cancelTimer();
        if (viewModel == null){
            layout.setVisibility(View.INVISIBLE);
            startedIssueInfo.setVisibility(View.VISIBLE);
        } else {
            issueId.setText(viewModel.getId());
            subject.setText(viewModel.getIssueViewModel().getSubject());
            startDate.setText(viewModel.getStartDate());
            layout.setVisibility(View.VISIBLE);
            startedIssueInfo.setVisibility(View.INVISIBLE);
            reScheduleTimer();
        }
    }

    @OnClick(R.id.startedIssueStopButton)
    public void stop(){
        presenter.stopIssue(issueId.getText().toString());
    }

    private void refreshSpentTime() {
        if (viewModel != null) {
            FragmentActivity activity = getActivity();
            if (activity == null){
                return;
            }
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    startedIssueSpentTime.setText(viewModel.getSpentTime());
                }
            });
        }
    }

    public void reScheduleTimer(){
        cancelTimer();
        timer = new Timer();
        timerTask = new RefreshSpentTimerTask();
        timer.schedule(timerTask, 0, 1000);
    }

    public void cancelTimer(){
        if (timer != null){
            timer.cancel();
        }
    }

    private class RefreshSpentTimerTask extends TimerTask {
        @Override
        public void run() {
            refreshSpentTime();
        }
    }
}
