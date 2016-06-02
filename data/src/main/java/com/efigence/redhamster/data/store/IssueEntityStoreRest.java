package com.efigence.redhamster.data.store;

import com.efigence.redhamster.data.ApiAccessKeyProvider;
import com.efigence.redhamster.data.model.IssueEntity;
import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.OkHttpClient;

import javax.inject.Named;

public class IssueEntityStoreRest extends BaseRestApi implements IssueEntityStore {

    private static final String ISSUE = "issues/%s.json";

    public IssueEntityStoreRest(OkHttpClient httpClient,
                                Gson gson,
                                ApiAccessKeyProvider apiAccessKeyProvider,
                                @Named("BaseApiUrl") String baseApiUrl){
        super(httpClient, gson, baseApiUrl, apiAccessKeyProvider);
    }

    @Override
    public IssueEntity getIssueEntity(String id){
        Call call = getCall(String.format(ISSUE, id));
        return gson.fromJson(execute(call), ResponseWrapper.class).issue;
    }

    private static class ResponseWrapper {
        IssueEntity issue;
    }

}
