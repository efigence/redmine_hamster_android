package com.efigence.redhamster.data.model;

/**
 * Created by lgorbski on 15.04.16.
 * <p>
 * {
 * "id": 134223,
 * "project": {
 * "id": 429,
 * "name": "CDM - SDIG upgrade - Development"
 * },
 * "tracker": {
 * "id": 1,
 * "name": "Bug"
 * },
 * "status": {
 * "id": 1,
 * "name": "New"
 * },
 * "priority": {
 * "id": 2,
 * "name": "Normal"
 * },
 * "author": {
 * "id": 1120,
 * "name": "Kamil"
 * },
 * "assigned_to": {
 * "id": 613,
 * "name": "Krystian"
 * },
 * "subject": "Błędna zawartość sekcji Notowania",
 * "description": "Notowania (prawa kolumna, dół)",
 * "start_date": "2016-04-15",
 * "is_private": true,
 * "created_on": "2016-04-15T10:20:23Z",
 * "updated_on": "2016-04-15T10:20:23Z"
 * },
 */
public class IssueEntity extends IssueBaseEntity {

    private KeyValue project;
    private KeyValue tracker;
    private KeyValue status;
    private KeyValue priority;
    private KeyValue author;
    private KeyValue assigned_to;

    public KeyValue getProject() {
        return project;
    }

    public KeyValue getTracker() {
        return tracker;
    }

    public KeyValue getStatus() {
        return status;
    }

    public KeyValue getPriority() {
        return priority;
    }

    public KeyValue getAuthor() {
        return author;
    }

    public KeyValue getAssigned_to() {
        return assigned_to;
    }

}
