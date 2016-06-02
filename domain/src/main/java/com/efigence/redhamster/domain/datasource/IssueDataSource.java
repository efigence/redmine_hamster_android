package com.efigence.redhamster.domain.datasource;

import com.efigence.redhamster.domain.model.HamsterIssue;
import com.efigence.redhamster.domain.model.Issue;

import java.util.List;
import java.util.Map;

public interface IssueDataSource {

    HamsterIssue getStartedIssue();
    Map<String, List<HamsterIssue>> getStoppedIssues();
    List<Issue> getAssignedToMe();
    List<Issue> getMyRecentlyUsed();

    void startIssue(String issueId);
    void stopIssue(String issueId);

    void deleteIssue(String issueId);
    void reportIssue(String issue);

}
