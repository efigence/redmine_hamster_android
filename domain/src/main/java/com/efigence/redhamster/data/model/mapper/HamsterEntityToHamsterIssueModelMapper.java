package com.efigence.redhamster.data.model.mapper;


import com.efigence.redhamster.data.model.HamsterEntity;
import com.efigence.redhamster.data.model.IssueEntity;
import com.efigence.redhamster.data.store.IssueEntityStore;
import com.efigence.redhamster.domain.model.HamsterIssue;
import com.efigence.redhamster.domain.model.Issue;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Singleton
public class HamsterEntityToHamsterIssueModelMapper {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
    private final IssueEntityStore issueEntityStore;

    @Inject
    public HamsterEntityToHamsterIssueModelMapper(IssueEntityStore issueEntityStore){
        this.issueEntityStore = issueEntityStore;
    }

    public HamsterIssue toModel(HamsterEntity entity){
        HamsterIssue hamsterIssue = new HamsterIssue();
        hamsterIssue.setId(Long.parseLong(entity.getId()));
        hamsterIssue.setStartDate(nullSafeParseDate(entity.getStart_at()));
        hamsterIssue.setEndDate(nullSafeParseDate(entity.getEnd_at()));
        hamsterIssue.setSpendTime(entity.getSpend_time());

        Issue issue = new Issue();
        issue.setId(Long.parseLong(entity.getIssue_id()));
        IssueEntity issueEntity = issueEntityStore.getIssueEntity(entity.getIssue_id());
        issue.setType(issueEntity.getTracker().getName());
        issue.setSubject(issueEntity.getSubject());
        issue.setProject(issueEntity.getProject().getName());

        hamsterIssue.setIssue(issue);
        return hamsterIssue;
    }

    public Map<String, List<HamsterIssue>> toModel(Map<String, List<HamsterEntity>> entities){
        Map<String, List<HamsterIssue>> result = new HashMap<>(entities.size());
        for (Map.Entry<String, List<HamsterEntity>> entry : entities.entrySet()){
            List<HamsterIssue> r = new ArrayList<>(entities.size());
            for (HamsterEntity entity: entry.getValue()) {
                r.add(toModel(entity));
            }
            result.put(entry.getKey(), r);
        }
        return result;
    }

    private Date nullSafeParseDate(String date) {
        if (date == null){
            return null;
        }
        try {
            return DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
