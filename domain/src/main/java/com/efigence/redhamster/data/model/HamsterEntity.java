package com.efigence.redhamster.data.model;

/**
 * Created by lgorbski on 15.04.16.
 *
 * "id": 4201,
 * "user_id": 532,
 * "issue_id": 133122,
 * "start_at": "2016-04-15T09:27:31.000Z",
 * "end_at": "2016-04-15T09:33:19.000Z",
 * "spend_time": 0.08,
 * "created_at": "2016-04-15T09:31:34.000Z",
 * "updated_at": "2016-04-15T09:33:19.000Z"
 *
 */

public class HamsterEntity {

    private String id;
    private String user_id;
    private String issue_id;
    private String start_at;
    private String end_at;
    private String spend_time;
    private String created_at;
    private String updated_at;

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getIssue_id() {
        return issue_id;
    }

    public String getStart_at() {
        return start_at;
    }

    public String getEnd_at() {
        return end_at;
    }

    public String getSpend_time() {
        return spend_time;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }
}
