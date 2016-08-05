package com.efigence.redhamster.ui.view;

import android.content.Context;
import android.os.Bundle;
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
        if (viewModel == null){
            layout.setVisibility(View.GONE);
            startedIssueInfo.setVisibility(View.VISIBLE);
        } else {
            issueId.setText(viewModel.getId());
            subject.setText(viewModel.getIssueViewModel().getSubject());
            startDate.setText(viewModel.getStartDate());
            layout.setVisibility(View.VISIBLE);
            startedIssueInfo.setVisibility(View.GONE);
        }
    }

    @Override
    public void updateSpentTime(String spentTime) {
        startedIssueSpentTime.setText(spentTime);
    }

    @OnClick(R.id.startedIssueStopButton)
    public void stop(){
        presenter.stopIssue(issueId.getText().toString());
    }

}
