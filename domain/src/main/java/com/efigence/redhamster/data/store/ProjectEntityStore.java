package com.efigence.redhamster.data.store;

import com.efigence.redhamster.data.model.ProjectEntity;

public interface ProjectEntityStore {

    ProjectEntity getProject(String id);

}
