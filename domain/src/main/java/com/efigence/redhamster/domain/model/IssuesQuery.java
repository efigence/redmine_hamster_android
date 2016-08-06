package com.efigence.redhamster.domain.model;

/**
 * Created by lgorbski on 06.08.16.
 */
public class IssuesQuery {

    private final String statusId;
    private final String projectId;
    private final int limit;
    private final int offset;

    public IssuesQuery(String statusId, String projectId, int limit, int offset) {
        this.statusId = statusId;
        this.projectId = projectId;
        this.limit = limit;
        this.offset = offset;
    }

    public String getStatusId() {
        return statusId;
    }

    public String getProjectId() {
        return projectId;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }
}
