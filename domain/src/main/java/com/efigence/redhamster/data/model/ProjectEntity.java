package com.efigence.redhamster.data.model;

/**
 * Created by lgorbski on 15.04.16.
 * "id": 555,
 * "name": "Alior Launch",
 * "identifier": "alior-launch",
 * "description": "",
 * "parent": {
 * "id": 149,
 * "name": "Idea bank - development"
 * }, ...
 */
public class ProjectEntity {

    private String id;
    private String name;
    private String identifier;
    private String description;
    private Integer status;
    private ProjectEntity parent;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getDescription() {
        return description;
    }

    public Integer getStatus() {
        return status;
    }

    public ProjectEntity getParent() {
        return parent;
    }
}
