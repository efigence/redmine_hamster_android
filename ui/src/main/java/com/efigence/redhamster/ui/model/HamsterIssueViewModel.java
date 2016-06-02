package com.efigence.redhamster.ui.model;


public class HamsterIssueViewModel {

    private final String id;
    private final String startDate;
    private final IssueViewModel issueViewModel;
    private final SpentTimeProvider spentTimeProvider;

    public HamsterIssueViewModel(String id, String startDate, IssueViewModel issueViewModel, SpentTimeProvider spentTimeProvider) {
        this.id = id;
        this.startDate = startDate;
        this.issueViewModel = issueViewModel;
        this.spentTimeProvider = spentTimeProvider;
    }

    public String getId() {
        return id;
    }

    public String getStartDate() {
        return startDate;
    }

    public IssueViewModel getIssueViewModel() {
        return issueViewModel;
    }

    public String getSpentTime(){
        return spentTimeProvider.getSpentTime();
    }


    public interface SpentTimeProvider {

        String getSpentTime();
    }
}
