package com.efigence.redhamster.domain.usecase;

import com.efigence.redhamster.data.store.ProjectEntityStore;

import javax.inject.Inject;

/**
 * Created by lgorbski on 06.08.16.
 */
public class LoadProjectsUseCase implements UseCaseArgumentless<Void> {

    private final ProjectEntityStore projectEntityStore;

    @Inject
    public LoadProjectsUseCase(ProjectEntityStore projectEntityStore){
        this.projectEntityStore = projectEntityStore;
    }

    @Override
    public Void execute() {
        projectEntityStore.loadAllProjects();
        return null;
    }

}
