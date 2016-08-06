package com.efigence.redhamster.data.store;

import com.efigence.redhamster.data.ApiAccessKeyProvider;
import com.efigence.redhamster.data.model.IssueEntity;
import com.efigence.redhamster.domain.model.IssuesQuery;
import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.OkHttpClient;

import javax.inject.Named;
import java.util.List;

public class IssueEntityStoreRest extends BaseRestApi implements IssueEntityStore {

    private static final String ISSUE = "issues/%s.json";
    private static final String ISSUES = "issues.json?%s";

    public IssueEntityStoreRest(OkHttpClient httpClient,
                                Gson gson,
                                ApiAccessKeyProvider apiAccessKeyProvider,
                                @Named("BaseApiUrl") String baseApiUrl){
        super(httpClient, gson, baseApiUrl, apiAccessKeyProvider);
    }

    @Override
    public IssueEntity getIssueEntity(String id){
        Call call = getCall(String.format(ISSUE, id));
        return gson.fromJson(execute(call), ResponseIssueWrapper.class).issue;
    }

    @Override
    public List<IssueEntity> getIssues(IssuesQuery query){
        SearchIssuesRequest request = new SearchIssuesRequest(query.getStatusId(), query.getProjectId(), query.getLimit(), query.getOffset());
        Call call = getCall(String.format(ISSUES, request.getQueryParams()));
        return gson.fromJson(execute(call), ResponseIssuesWrapper.class).issues;
    }

    private static class ResponseIssueWrapper {
        IssueEntity issue;
    }

    private static class ResponseIssuesWrapper {
        List<IssueEntity> issues;
        long total_count;
        long offset;
        long limit;
    }

}
