package com.efigence.redhamster.ui.model;


public class IssueViewModel {

    private final String id;
    private final String subject;

    public IssueViewModel(String id, String subject) {
        this.id = id;
        this.subject = subject;
    }

    public String getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

}
