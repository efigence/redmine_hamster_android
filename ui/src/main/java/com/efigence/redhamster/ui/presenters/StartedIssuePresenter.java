package com.efigence.redhamster.ui.presenters;

import android.os.AsyncTask;
import com.efigence.redhamster.domain.model.HamsterIssue;
import com.efigence.redhamster.domain.usecase.GetStartedIssueUseCase;
import com.efigence.redhamster.domain.usecase.StopIssueUseCase;
import com.efigence.redhamster.ui.BasePresenter;
import com.efigence.redhamster.ui.UI;
import com.efigence.redhamster.ui.model.HamsterIssueViewModel;
import com.efigence.redhamster.ui.model.mapper.HamsterIssueToViewModelMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StartedIssuePresenter extends BasePresenter<StartedIssuePresenter.StartedIssueUI> {

    private final GetStartedIssueUseCase startedIssueUseCase;
    private final StopIssueUseCase stopIssueUseCase;
    private final HamsterIssueToViewModelMapper modelMapper;

    @Inject
    public StartedIssuePresenter(GetStartedIssueUseCase startedIssueUseCase,
                                 StopIssueUseCase stopIssueUseCase,
                                 HamsterIssueToViewModelMapper modelMapper) {
        this.startedIssueUseCase = startedIssueUseCase;
        this.stopIssueUseCase = stopIssueUseCase;
        this.modelMapper = modelMapper;
    }

    public void onDisplay() {
        new AsyncTask<Void, Void, HamsterIssue>() {
            @Override
            protected HamsterIssue doInBackground(Void... params) {
                return startedIssueUseCase.execute();
            }

            @Override
            protected void onPostExecute(HamsterIssue hamsterIssue) {
                ui.display(modelMapper.toViewModel(hamsterIssue));
            }
        }.execute();
    }

    public void stopIssue(String issueId) {
        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                stopIssueUseCase.execute(params[0]);
                StartedIssuePresenter.this.onDisplay();
                return null;
            }
        }.execute(issueId);
    }

    public interface StartedIssueUI extends UI {

        void display(HamsterIssueViewModel viewModel);

    }
}



