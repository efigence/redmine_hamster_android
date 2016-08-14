package com.efigence.redhamster.ui.model;


public class IssueViewModel {

    private final String id;
    private final String subject;
    private final String project;

    public IssueViewModel(String id, String subject, String project) {
        this.id = id;
        this.subject = subject;
        this.project = project;
    }

    public String getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getProject() {
        return project;
    }
}
