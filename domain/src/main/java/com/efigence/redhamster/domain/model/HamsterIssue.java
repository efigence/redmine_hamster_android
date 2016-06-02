package com.efigence.redhamster.domain.model;

import java.util.Date;

public class HamsterIssue {

    private long id;
    private Issue issue;
    private Date startDate;
    private Date endDate;
    private Double spendTime;

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public Issue getIssue() {
        return issue;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setSpendTime(String spendTime) {
        this.spendTime = spendTime == null ? null : Double.valueOf(spendTime);
    }

    public Double getSpendTime() {
        return spendTime;
    }
}
