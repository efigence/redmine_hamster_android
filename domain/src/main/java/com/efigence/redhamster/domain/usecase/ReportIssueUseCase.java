package com.efigence.redhamster.domain.usecase;

import com.efigence.redhamster.domain.datasource.IssueDataSource;

import javax.inject.Inject;


public class ReportIssueUseCase implements UseCase<Void,String> {

    private final IssueDataSource issueDataSource;

    @Inject
    public ReportIssueUseCase(IssueDataSource issueDataSource) {
        this.issueDataSource = issueDataSource;
    }

    @Override
    public Void execute(String issueId) {
        issueDataSource.reportIssue(issueId);
        return null;
    }
}
