package com.efigence.redhamster.ui.presenters;

import com.efigence.redhamster.domain.usecase.GetStartedIssueUseCase;
import com.efigence.redhamster.domain.usecase.StopIssueUseCase;
import com.efigence.redhamster.ui.BasePresenter;
import com.efigence.redhamster.ui.UI;
import com.efigence.redhamster.ui.model.HamsterIssueViewModel;
import com.efigence.redhamster.ui.model.mapper.HamsterIssueToViewModelMapper;
import rx.Observable;
import rx.Subscription;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;

@Singleton
public class StartedIssuePresenter extends BasePresenter<StartedIssuePresenter.StartedIssueUI> {

    private final GetStartedIssueUseCase startedIssueUseCase;
    private final StopIssueUseCase stopIssueUseCase;
    private final HamsterIssueToViewModelMapper modelMapper;

    private Subscription timer;

    @Inject
    public StartedIssuePresenter(GetStartedIssueUseCase startedIssueUseCase,
                                 StopIssueUseCase stopIssueUseCase,
                                 HamsterIssueToViewModelMapper modelMapper) {
        this.startedIssueUseCase = startedIssueUseCase;
        this.stopIssueUseCase = stopIssueUseCase;
        this.modelMapper = modelMapper;
    }

    public void onDisplay() {
        createObservableOnUi(startedIssueUseCase)
                .map(issue -> modelMapper.toViewModel(issue))
                .subscribe(issue -> {
                    ui.display(issue);
                    if (issue != null){
                        startTimer(issue);
                    }
                }, catchException());
    }

    private void startTimer(HamsterIssueViewModel issueViewModel) {
        stopTimer();
        timer = subscribeOnUi(Observable.interval(1, TimeUnit.SECONDS))
                .subscribe(time -> ui.updateSpentTime(issueViewModel.getSpentTime()));
    }

    public void stopIssue(String issueId) {
        createObservableOnUi(stopIssueUseCase, issueId)
                .subscribe(aVoid -> {
                    stopTimer();
                    onDisplay();
                }, catchException());
    }

    private void stopTimer(){
        if (timer != null && !timer.isUnsubscribed()){
            timer.unsubscribe();
        }
    }

    public interface StartedIssueUI extends UI {

        void display(HamsterIssueViewModel viewModel);
        void updateSpentTime(String spentTime);

    }
}



