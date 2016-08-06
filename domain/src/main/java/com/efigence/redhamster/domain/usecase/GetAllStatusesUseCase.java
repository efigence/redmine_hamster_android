package com.efigence.redhamster.domain.usecase;

import com.efigence.redhamster.data.model.KeyValue;
import com.efigence.redhamster.data.store.EnumerationStore;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by lgorbski on 06.08.16.
 */
public class GetAllStatusesUseCase implements UseCaseArgumentless<List<KeyValue>> {

    private final EnumerationStore enumerationStore;

    @Inject
    public GetAllStatusesUseCase(EnumerationStore enumerationStore){
        this.enumerationStore = enumerationStore;
    }

    @Override
    public List<KeyValue> execute() {
        return enumerationStore.getStatuses();
    }
}
