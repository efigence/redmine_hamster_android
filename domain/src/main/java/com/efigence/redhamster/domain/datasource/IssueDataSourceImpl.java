package com.efigence.redhamster.domain.datasource;

import com.efigence.redhamster.data.model.HamsterEntity;
import com.efigence.redhamster.data.model.MyIssueEntity;
import com.efigence.redhamster.data.model.mapper.HamsterEntityToHamsterIssueModelMapper;
import com.efigence.redhamster.data.model.mapper.MyIssueEntityToIssueModelMapper;
import com.efigence.redhamster.data.store.HamsterEntityStore;
import com.efigence.redhamster.domain.model.HamsterIssue;
import com.efigence.redhamster.domain.model.Issue;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Map;

@Singleton
public class IssueDataSourceImpl implements IssueDataSource {

    private final HamsterEntityStore hamsterEntityStore;
    private final HamsterEntityToHamsterIssueModelMapper hamsterEntityMapper;
    private final MyIssueEntityToIssueModelMapper myIssueEntityMapper;

    @Inject
    public IssueDataSourceImpl(HamsterEntityStore hamsterEntityStore, HamsterEntityToHamsterIssueModelMapper mapper, MyIssueEntityToIssueModelMapper myIssueEntityMapper){
        this.hamsterEntityStore = hamsterEntityStore;
        this.hamsterEntityMapper = mapper;
        this.myIssueEntityMapper = myIssueEntityMapper;
    }

    @Override
    public HamsterIssue getStartedIssue() {
        HamsterEntity startedIssue = hamsterEntityStore.getStartedIssue();
        if (startedIssue == null){
            return null;
        }
        return hamsterEntityMapper.toModel(startedIssue);
    }

    @Override
    public Map<String, List<HamsterIssue>>  getStoppedIssues() {
        return hamsterEntityMapper.toModel(hamsterEntityStore.getStoppedIssues());
    }

    @Override
    public List<Issue> getAssignedToMe() {
        List<MyIssueEntity> assignedToMe = hamsterEntityStore.getAssignedToMe();
        return myIssueEntityMapper.toModel(assignedToMe);
    }

    @Override
    public List<Issue> getMyRecentlyUsed() {
        List<MyIssueEntity> myRecentlyUsed = hamsterEntityStore.getMyRecentlyUsed();
        return myIssueEntityMapper.toModel(myRecentlyUsed);
    }

    @Override
    public void startIssue(String issueId) {
        hamsterEntityStore.startIssue(issueId);
    }

    @Override
    public void stopIssue(String issueId) {
        hamsterEntityStore.stopHamsterIssue(issueId);
    }

    @Override
    public void deleteIssue(String issueId) {
        hamsterEntityStore.deleteIssue(issueId);
    }

    @Override
    public void reportIssue(String issueId) {
        hamsterEntityStore.reportIssue(issueId);
    }

}
