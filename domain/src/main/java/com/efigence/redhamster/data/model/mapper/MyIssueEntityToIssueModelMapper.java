package com.efigence.redhamster.data.model.mapper;


import com.efigence.redhamster.data.model.MyIssueEntity;
import com.efigence.redhamster.data.model.ProjectEntity;
import com.efigence.redhamster.data.store.IssueEntityStore;
import com.efigence.redhamster.data.store.ProjectEntityStore;
import com.efigence.redhamster.domain.model.Issue;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class MyIssueEntityToIssueModelMapper {

    private final IssueEntityStore issueEntityStore;
    private final ProjectEntityStore projectEntityStore;

    @Inject
    public MyIssueEntityToIssueModelMapper(IssueEntityStore issueEntityStore, ProjectEntityStore projectEntityStore){
        this.issueEntityStore = issueEntityStore;
        this.projectEntityStore = projectEntityStore;
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
        issue.setSubject(entity.getSubject());
        ProjectEntity project = projectEntityStore.getProject(entity.getProject_id());
        if (project != null){
            issue.setProject(project.getName());
        }
        return issue;
    }

}
