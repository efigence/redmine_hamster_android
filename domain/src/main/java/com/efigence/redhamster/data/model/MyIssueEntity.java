package com.efigence.redhamster.data.model;

public class MyIssueEntity extends IssueBaseEntity {

    private String tracker_id;
    private String project_id;
    private String category_id;
    private String status_id;
    private String assigned_to_id;
    private String priority_id;
    private String author_id;

    public String getTracker_id() {
        return tracker_id;
    }

    public String getProject_id() {
        return project_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getStatus_id() {
        return status_id;
    }

    public String getAssigned_to_id() {
        return assigned_to_id;
    }

    public String getPriority_id() {
        return priority_id;
    }

    public String getAuthor_id() {
        return author_id;
    }
}
