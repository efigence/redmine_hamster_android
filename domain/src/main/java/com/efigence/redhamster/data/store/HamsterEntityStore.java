package com.efigence.redhamster.data.store;


import com.efigence.redhamster.data.model.HamsterEntity;
import com.efigence.redhamster.data.model.MyIssueEntity;

import java.util.List;
import java.util.Map;

public interface HamsterEntityStore {

    HamsterEntity getStartedIssue();
    Map<String, List<HamsterEntity>> getStoppedIssues();
    List<MyIssueEntity> getAssignedToMe();
    List<MyIssueEntity> getMyRecentlyUsed();
    void startIssue(String issueId);
    void stopHamsterIssue(String hamsterIssueId);
    void deleteIssue(String issueId);
    void reportIssue(String issueId);

}
