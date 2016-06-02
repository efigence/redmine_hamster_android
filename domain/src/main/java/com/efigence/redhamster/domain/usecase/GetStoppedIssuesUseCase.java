package com.efigence.redhamster.domain.usecase;

import com.efigence.redhamster.domain.datasource.IssueDataSource;
import com.efigence.redhamster.domain.model.HamsterIssue;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class GetStoppedIssuesUseCase implements UseCaseArgumentless<Map<String, List<HamsterIssue>>> {

    private final IssueDataSource issueDataSource;

    @Inject
    public GetStoppedIssuesUseCase(IssueDataSource issueDataSource){
        this.issueDataSource = issueDataSource;
    }

    @Override
    public Map<String, List<HamsterIssue>> execute() {
        return issueDataSource.getStoppedIssues();
    }

}
