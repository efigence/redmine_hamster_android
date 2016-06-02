package com.efigence.redhamster.domain.usecase;

import com.efigence.redhamster.domain.datasource.IssueDataSource;
import com.efigence.redhamster.domain.model.Issue;

import javax.inject.Inject;
import java.util.List;

public class GetAssignedToMeIssues implements UseCaseArgumentless<List<Issue>> {

    private final IssueDataSource issueDataSource;

    @Inject
    public GetAssignedToMeIssues(IssueDataSource issueDataSource){
        this.issueDataSource = issueDataSource;
    }

    @Override
    public List<Issue> execute() {
        return issueDataSource.getAssignedToMe();
    }

}
