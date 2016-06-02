package com.efigence.redhamster.data.store;

import com.efigence.redhamster.data.ApiAccessKeyProvider;
import com.efigence.redhamster.data.model.HamsterEntity;
import com.efigence.redhamster.data.model.MyIssueEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Call;
import okhttp3.OkHttpClient;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@Singleton
public class HamsterEntityStoreRest extends BaseRestApi implements HamsterEntityStore {

    private static final String STARTED = "api/hamster/my_active_hamsters";
    private static final String STOPPED = "api/hamster/my_ready_to_raport_hamsters";
    private static final String ASSIGN_TO_ME = "api/hamster/my_issues";
    private static final String MY_RECENTLY_USED = "api/hamster/mru";

    private static final String START = "api/hamster/start";
    private static final String STOP = "api/hamster/stop";
    private static final String DELETE = "api/hamster/delete?id=%s";
    private static final String REPORT_TIME = "api/hamster/raport_time";

    private static final String START_ISSUE_PARAM_NAME = "issue_id";
    private static final String STOP_ISSUE_PARAM_NAME = "hamster_issue_id";
    private static final String REPORT_TIME_ENTRY_PARAM_NAME = "time_entry";

    @Inject
    public HamsterEntityStoreRest(OkHttpClient httpClient,
                                  Gson gson,
                                  ApiAccessKeyProvider apiAccessKeyProvider,
                                  @Named("BaseApiUrl") String baseApiUrl){
        super(httpClient, gson, baseApiUrl, apiAccessKeyProvider);
    }

    @Override
    public HamsterEntity getStartedIssue() {
        Call call = getCall(STARTED);
        String result = execute(call);
        List<HamsterEntity> o = gson.fromJson(result, hamstersListType());
        if (o.isEmpty()){
            return null;
        }
        return o.get(0);
    }

    @Override
    public Map<String, List<HamsterEntity>> getStoppedIssues() {
        Call call = getCall(STOPPED);
        return gson.fromJson(execute(call), hamstersMapType());
    }

    @Override
    public List<MyIssueEntity> getAssignedToMe(){
        Call call = getCall(ASSIGN_TO_ME);
        return gson.fromJson(execute(call), listOfMyIssueEntities());
    }

    @Override
    public List<MyIssueEntity> getMyRecentlyUsed() {
        Call call = getCall(MY_RECENTLY_USED);
        return gson.fromJson(execute(call), listOfMyIssueEntities());
    }

    @Override
    public void startIssue(String issueId) {
        Call call = postCall(START, START_ISSUE_PARAM_NAME, issueId);
        execute(call);
    }

    @Override
    public void stopHamsterIssue(String hamsterIssueId) {
        Call call = postCall(STOP, STOP_ISSUE_PARAM_NAME, hamsterIssueId);
        execute(call);
    }

    @Override
    public void deleteIssue(String issueId) {
        Call call = deleteCall(String.format(DELETE, issueId));
        execute(call);
    }

    @Override
    public void reportIssue(String issueId) {
        HamsterEntity hamsterEntity = getHamsterEntity(issueId);
        if (hamsterEntity == null){
            throw new IllegalArgumentException("Doesn't exist hamster issue with id: " + issueId);
        }
        Call call = postCall(REPORT_TIME, REPORT_TIME_ENTRY_PARAM_NAME, gson.toJson(HamsterTimeEntry.from(hamsterEntity)));
        execute(call);
    }

    private HamsterEntity getHamsterEntity(String id){
        for (List<HamsterEntity> issues : getStoppedIssues().values()){
            for (HamsterEntity issue: issues) {
                if (id.equals(issue.getId())){
                    return issue;
                }
            }
        }
        return null;
    }

    private Type listOfMyIssueEntities() {
        return new TypeToken<List<MyIssueEntity>>(){}.getType();
    }

    private Type hamstersMapType() {
        return new TypeToken<Map<String, List<HamsterEntity>>>(){}.getType();
    }

    private Type hamstersListType() {
        return new TypeToken<List<HamsterEntity>>(){}.getType();
    }

    private static class HamsterTimeEntry {

        private int issue_id;
        private String spent_on;
        private int hamster_id;
        private double hours;

        private HamsterTimeEntry(){
            // do nothing
        }

        public int getIssue_id() {
            return issue_id;
        }

        public String getSpent_on() {
            return spent_on;
        }

        public int getHamster_id() {
            return hamster_id;
        }

        public double getHours() {
            return hours;
        }

        public static HamsterTimeEntry from(HamsterEntity entity){
            HamsterTimeEntry timeEntry = new HamsterTimeEntry();
            timeEntry.issue_id = Integer.parseInt(entity.getIssue_id());
            timeEntry.hamster_id = Integer.parseInt(entity.getId());
            timeEntry.spent_on = entity.getStart_at().substring(0, entity.getStart_at().indexOf('T'));
            timeEntry.hours = Double.parseDouble(entity.getSpend_time());
            return timeEntry;
        }
    }
}
