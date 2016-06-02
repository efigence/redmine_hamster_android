package com.efigence.redhamster.domain.usecase;

import com.efigence.redhamster.domain.datasource.IssueDataSource;

import javax.inject.Inject;


public class StopIssueUseCase implements UseCase<Void,String> {

    private final IssueDataSource issueDataSource;

    @Inject
    public StopIssueUseCase(IssueDataSource issueDataSource) {
        this.issueDataSource = issueDataSource;
    }

    @Override
    public Void execute(String issueId) {
        issueDataSource.stopIssue(issueId);
        return null;
    }
}
