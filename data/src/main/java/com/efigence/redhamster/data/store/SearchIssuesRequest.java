package com.efigence.redhamster.data.store;

/**
 * Created by lgorbski on 06.08.16.
 */
public class SearchIssuesRequest {

    private final String statusId;
    private final String projectId;
    private final int limit;
    private final int offset;

    public SearchIssuesRequest(String statusId, String projectId, int limit, int offset) {
        this.statusId = statusId;
        this.projectId = projectId;
        this.limit = limit;
        this.offset = offset;
    }

    public String getQueryParams(){
        StringBuilder sb = new StringBuilder();
        if (statusId != null){
            sb.append("status_id=")
                    .append(statusId)
                    .append("&");
        }
        if (projectId != null){
            sb.append("project_id=")
                    .append(projectId)
                    .append("&");
        }
        sb.append("limit=")
                .append(limit == 0 ? 20 : limit)
                .append("&")
                .append("offset=")
                .append(offset);
        return sb.toString();
    }
}
