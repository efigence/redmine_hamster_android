package com.efigence.redhamster.data.store;

import android.content.Context;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.efigence.redhamster.data.R;
import com.efigence.redhamster.data.model.KeyValue;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lgorbski on 06.08.16.
 */
public class EnumerationStoreImpl implements EnumerationStore {

    private final Context context;
    private List<KeyValue> statuses = Collections.emptyList();

    public EnumerationStoreImpl(Context context){
        this.context = context;
    }

    @Override
    public List<KeyValue> getStatuses() {
        return statuses;
    }

    @Override
    public void loadAllStatuses() {
        Map<String, String> dict = getDict(R.array.statuses_values, R.array.statuses_names);
        statuses = Stream.of(dict)
                .map(value -> new KeyValue(value.getKey(), value.getValue()))
                .collect(Collectors.toList());
    }

    private Map<String, String> getDict(int keyId, int valId) {
        String[] keys = context.getResources().getStringArray(keyId);
        String[] values = context.getResources().getStringArray(valId);

        Map<String, String> dict = new HashMap<>();
        for (int i = 0, l = keys.length; i < l; i++) {
            dict.put(keys[i], values[i]);
        }
        return dict;
    }

}
