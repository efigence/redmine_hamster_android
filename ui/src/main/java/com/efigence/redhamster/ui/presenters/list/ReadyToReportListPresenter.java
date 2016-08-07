package com.efigence.redhamster.ui.presenters.list;

import com.efigence.redhamster.domain.model.HamsterIssue;
import com.efigence.redhamster.domain.usecase.UseCase;
import com.efigence.redhamster.domain.usecase.UseCaseArgumentless;
import com.efigence.redhamster.ui.BasePresenter;
import com.efigence.redhamster.ui.UI;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class ReadyToReportListPresenter extends BasePresenter<ReadyToReportListPresenter.ReadyToReportListUI> {

    private final UseCaseArgumentless<Map<String, List<HamsterIssue>>> loadIssuesUseCase;
    private final UseCase<Void, String> deleteIssueUseCase;
    private final UseCase<Void, String> reportIssueUseCase;

    @Inject
    public ReadyToReportListPresenter(UseCaseArgumentless<Map<String, List<HamsterIssue>>> loadIssuesUseCase,
                                      UseCase<Void, String> deleteIssueUseCase,
                                      UseCase<Void, String> reportIssueUseCase){
        this.loadIssuesUseCase = loadIssuesUseCase;
        this.deleteIssueUseCase = deleteIssueUseCase;
        this.reportIssueUseCase = reportIssueUseCase;
    }

    public void onDisplayIssues(){
        createObservableOnUi(loadIssuesUseCase)
                .subscribe(issues -> ui.refreshIssues(issues), catchException());
    }

    public void reportIssue(HamsterIssue issue) {
        createObservableOnUi(reportIssueUseCase, String.valueOf(issue.getId()))
                .subscribe(aVoid -> onDisplayIssues(), catchException());
    }

    public void deleteIssue(HamsterIssue issue) {
        createObservableOnUi(deleteIssueUseCase, String.valueOf(issue.getId()))
                .subscribe(aVoid -> onDisplayIssues(), catchException());
    }

    public interface ReadyToReportListUI extends UI {

        void refreshIssues(Map<String, List<HamsterIssue>> issues);

    }

}
