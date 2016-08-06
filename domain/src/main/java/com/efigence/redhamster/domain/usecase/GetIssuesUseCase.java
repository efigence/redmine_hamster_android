package com.efigence.redhamster.domain.usecase;

import com.efigence.redhamster.domain.datasource.IssueDataSource;
import com.efigence.redhamster.domain.model.Issue;
import com.efigence.redhamster.domain.model.IssuesQuery;

import javax.inject.Inject;
import java.util.List;

public class GetIssuesUseCase implements UseCase<List<Issue>, IssuesQuery> {

    private final IssueDataSource issueDataSource;

    @Inject
    public GetIssuesUseCase(IssueDataSource issueDataSource){
        this.issueDataSource = issueDataSource;
    }

    @Override
    public List<Issue> execute(IssuesQuery query) {
        return issueDataSource.getIssues(query);
    }

}
