package com.efigence.redhamster.data.store;

import com.efigence.redhamster.data.ApiAccessKeyProvider;
import com.efigence.redhamster.data.ApiExecutionException;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;


public class BaseRestApi {

    public static final String X_REDMINE_API_KEY = "X-Redmine-API-Key";

    protected final Gson gson;
    private final OkHttpClient httpClient;
    private final String baseApiUrl;
    private final ApiAccessKeyProvider apiAccessKeyProvider;


    protected BaseRestApi(OkHttpClient httpClient,
                          Gson gson,
                          String baseApiUrl,
                          ApiAccessKeyProvider apiAccessKeyProvider){
        this.httpClient = httpClient;
        this.gson = gson;
        this.apiAccessKeyProvider = apiAccessKeyProvider;
        this.baseApiUrl = baseApiUrl;
    }

    protected String execute(final Call call){
        try {
            Response response = call.execute();
            if (response.isSuccessful()){
                return response.body().string();
            }
            throw new ApiExecutionException(response.message(), response.code());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Request.Builder getBuilder(){
        return new Request.Builder()
                .addHeader(X_REDMINE_API_KEY, apiAccessKeyProvider.getApiAccessKey());
    }


    protected Call getCall(String path){
        return httpClient.newCall(getBuilder()
                .url(getUrl(path))
                .get()
                .build());
    }

    protected Call postCall(String path, String key, String value){
        return httpClient.newCall(getBuilder()
                .url(getUrl(path))
                .post(new FormBody.Builder()
                        .add(key, value)
                        .build())
                .build());
    }

    protected Call deleteCall(String path) {
        return httpClient.newCall(getBuilder()
                .url(getUrl(path))
                .delete()
                .build());
    }

    private String getUrl(String path) {
        return baseApiUrl + path;
    }


}
