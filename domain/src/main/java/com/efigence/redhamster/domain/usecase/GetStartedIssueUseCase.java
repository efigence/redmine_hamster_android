package com.efigence.redhamster.domain.usecase;

import com.efigence.redhamster.domain.datasource.IssueDataSource;
import com.efigence.redhamster.domain.model.HamsterIssue;

import javax.inject.Inject;

public class GetStartedIssueUseCase implements UseCaseArgumentless<HamsterIssue> {

    private final IssueDataSource issueDataSource;

    @Inject
    public GetStartedIssueUseCase(IssueDataSource issueDataSource){
        this.issueDataSource = issueDataSource;
    }

    @Override
    public HamsterIssue execute() {
        return issueDataSource.getStartedIssue();
    }

}
