package com.efigence.redhamster.data.model.mapper;


import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
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

import static java.lang.Long.parseLong;

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
        hamsterIssue.setId(parseLong(entity.getId()));
        hamsterIssue.setStartDate(nullSafeParseDate(entity.getStart_at()));
        hamsterIssue.setEndDate(nullSafeParseDate(entity.getEnd_at()));
        hamsterIssue.setSpendTime(entity.getSpend_time());

        IssueEntity issueEntity = issueEntityStore.getIssueEntity(entity.getIssue_id());
        hamsterIssue.setIssue(toModel(issueEntity));
        return hamsterIssue;
    }

    public Issue toModel(IssueEntity entity){
        Issue issue = new Issue();
        issue.setId(parseLong(entity.getId()));
        issue.setSubject(entity.getSubject());
        issue.setProject(entity.getProject().getName());
        issue.setType(entity.getTracker().getName());
        return issue;
    }

    public List<Issue> toModel(List<IssueEntity> entities){
        return Stream.of(entities)
                .map(entity -> toModel(entity))
                .collect(Collectors.toList());
    }

    public Map<String, List<HamsterIssue>> toModel(Map<String, List<HamsterEntity>> entities){
        Map<String, List<HamsterIssue>> result = new HashMap<>(entities.size());
        for (Map.Entry<String, List<HamsterEntity>> entry : entities.entrySet()){
            result.put(entry.getKey(),
                    Stream.of(entry.getValue())
                            .map(entity -> toModel(entity))
                            .collect(Collectors.toList()));
        }
        return result;
    }

    private Date nullSafeParseDate(String date) {
        if (date == null){
            return null;
        }
        try {
            Date dateInUTC = DATE_FORMAT.parse(date);
            TimeZone timeZone = TimeZone.getDefault();
            long time = dateInUTC.getTime() + timeZone.getRawOffset();
            boolean daylightTime = timeZone.inDaylightTime(dateInUTC);
            if (daylightTime){
                time += timeZone.getDSTSavings();
            }
            return new Date(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
