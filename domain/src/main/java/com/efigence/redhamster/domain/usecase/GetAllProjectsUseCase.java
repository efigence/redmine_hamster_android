package com.efigence.redhamster.domain.usecase;

import com.efigence.redhamster.data.model.KeyValue;
import com.efigence.redhamster.data.store.ProjectEntityStore;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lgorbski on 06.08.16.
 */
public class GetAllProjectsUseCase implements UseCaseArgumentless<List<KeyValue>> {

    private final ProjectEntityStore projectEntityStore;

    @Inject
    public GetAllProjectsUseCase(ProjectEntityStore projectEntityStore){
        this.projectEntityStore = projectEntityStore;
    }

    @Override
    public List<KeyValue> execute() {
        return projectEntityStore.getProjects();
    }

}
