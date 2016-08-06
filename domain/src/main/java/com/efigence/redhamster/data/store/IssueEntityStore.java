package com.efigence.redhamster.data.store;


import com.efigence.redhamster.data.model.IssueEntity;
import com.efigence.redhamster.domain.model.IssuesQuery;

import java.util.List;

public interface IssueEntityStore {

    IssueEntity getIssueEntity(String id);
    List<IssueEntity> getIssues(IssuesQuery query);

}
