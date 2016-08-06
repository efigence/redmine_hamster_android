package com.efigence.redhamster.domain.usecase;

import com.efigence.redhamster.data.store.EnumerationStore;

import javax.inject.Inject;

/**
 * Created by lgorbski on 06.08.16.
 */
public class LoadStatusesUseCase implements UseCaseArgumentless<Void> {

    private final EnumerationStore enumerationStore;

    @Inject
    public LoadStatusesUseCase(EnumerationStore enumerationStore){
        this.enumerationStore = enumerationStore;
    }

    @Override
    public Void execute() {
        enumerationStore.loadAllStatuses();
        return null;
    }
}
