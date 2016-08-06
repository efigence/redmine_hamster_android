package com.efigence.redhamster.data.store;

import com.efigence.redhamster.data.model.KeyValue;

import java.util.List;

/**
 * Created by lgorbski on 06.08.16.
 */
public interface EnumerationStore {

    List<KeyValue> getStatuses();
    void loadAllStatuses();

}
