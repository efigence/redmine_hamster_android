package com.efigence.redhamster.ui.presenters;

import com.efigence.redhamster.data.ApiAccessKeyProvider;
import com.efigence.redhamster.domain.usecase.StartIssueUseCase;
import com.efigence.redhamster.ui.BasePresenter;
import com.efigence.redhamster.ui.UI;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ApplicationPresenter extends BasePresenter<ApplicationPresenter.ApplicationUI> {

    private final ApiAccessKeyProvider apiAccessKeyProvider;
    private final StartIssueUseCase startIssueUseCase;
    private boolean started;

    @Inject
    public ApplicationPresenter(ApiAccessKeyProvider apiAccessKeyProvider, StartIssueUseCase startIssueUseCase){
        this.apiAccessKeyProvider = apiAccessKeyProvider;
        this.startIssueUseCase = startIssueUseCase;
    }

    @Override
    public void onAttach(ApplicationUI ui) {
        super.onAttach(ui);
        if (!isAccessKeySpecified()){
            ui.displaySettings();
        } else if (!started){
            started = true;
            ui.displayStaredIssue();
        }
    }

    public boolean isAccessKeySpecified(){
        return apiAccessKeyProvider.isAccessKeySet();
    }

    public void onDisplayStartedIssue() {
        ui.displayStaredIssue();
    }

    public void onDisplayAssignedToMe(){
        ui.displayAssignedToMe();
    }

    public void onDisplayMyRecentlyUsed(){
        ui.displayRecentlyUsed();
    }

    public void onDisplaySettings() {
        ui.displaySettings();
    }

    public void onDisplayStartIssue(String issueId){
        ui.displayStartIssue(issueId);
    }

    public void onDisReadyToReport() {
        ui.displayReadyToReport();
    }

    public void startIssue(String issueId){
        createObservableOnUi(startIssueUseCase, issueId)
                .subscribe(aVoid -> ui.displayStaredIssue());
    }

    public void onDisplayAllIssues() {
        ui.displayAllIssues();
    }

    public interface ApplicationUI extends UI {

        void displayStaredIssue();
        void displayReadyToReport();
        void displayAssignedToMe();
        void displayRecentlyUsed();
        void displaySettings();
        void displayStartIssue(String issueId);
        void displayAllIssues();
    }

}


