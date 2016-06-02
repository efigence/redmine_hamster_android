package com.efigence.redhamster.data.model.mapper;


import com.efigence.redhamster.data.model.MyIssueEntity;
import com.efigence.redhamster.data.store.IssueEntityStore;
import com.efigence.redhamster.domain.model.Issue;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class MyIssueEntityToIssueModelMapper {

    private final IssueEntityStore issueEntityStore;

    @Inject
    public MyIssueEntityToIssueModelMapper(IssueEntityStore issueEntityStore){
        this.issueEntityStore = issueEntityStore;
    }

    public List<Issue> toModel(List<MyIssueEntity> entities){
        List<Issue> result = new ArrayList<>(entities.size());
        for (MyIssueEntity entity: entities) {
            result.add(toModel(entity));
        }
        return result;
    }

    private Issue toModel(MyIssueEntity entity){
        Issue issue = new Issue();
        issue.setId(Long.parseLong(entity.getId()));
       // IssueEntity issueEntity = issueEntityStore.getIssueEntity(entity.getId());
       // issue.setType(issueEntity.getTracker().getName());
        issue.setSubject(entity.getSubject());
       // issue.setProject(issueEntity.getProject().getName());

        return issue;
    }

}
