package com.efigence.redhamster.data.store;

import com.efigence.redhamster.data.model.KeyValue;
import com.efigence.redhamster.data.model.ProjectEntity;

import java.util.List;

public interface ProjectEntityStore {

    ProjectEntity getProject(String id);
    List<KeyValue> getProjects();
    void loadAllProjects();

}
