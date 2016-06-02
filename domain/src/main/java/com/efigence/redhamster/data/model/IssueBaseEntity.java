package com.efigence.redhamster.data.model;

public class IssueBaseEntity {

    private String id;
    private String subject;
    private String description;
    private String start_date;

    private boolean is_private;
    private String created_on;
    private String updated_on;

    public String getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public String getStart_date() {
        return start_date;
    }

    public boolean is_private() {
        return is_private;
    }

    public String getCreated_on() {
        return created_on;
    }

    public String getUpdated_on() {
        return updated_on;
    }
}
