package com.efigence.redhamster.data.model;

public class KeyValue {

    private final String id;
    private final String name;

    public KeyValue(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
