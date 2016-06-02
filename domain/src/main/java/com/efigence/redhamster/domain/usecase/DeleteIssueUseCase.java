package com.efigence.redhamster.domain.usecase;

import com.efigence.redhamster.domain.datasource.IssueDataSource;

import javax.inject.Inject;


public class DeleteIssueUseCase implements UseCase<Void,String> {

    private final IssueDataSource issueDataSource;

    @Inject
    public DeleteIssueUseCase(IssueDataSource issueDataSource) {
        this.issueDataSource = issueDataSource;
    }

    @Override
    public Void execute(String issueId) {
        issueDataSource.deleteIssue(issueId);
        return null;
    }
}
