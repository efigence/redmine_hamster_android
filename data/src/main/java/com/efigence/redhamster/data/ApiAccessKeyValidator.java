package com.efigence.redhamster.data;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

import static com.efigence.redhamster.data.store.BaseRestApi.X_REDMINE_API_KEY;

@Singleton
public class ApiAccessKeyValidator {

    private final OkHttpClient httpClient;
    private final String baseApiUrl;

    @Inject
    public ApiAccessKeyValidator(OkHttpClient httpClient, String baseApiUrl) {
        this.httpClient = httpClient;
        this.baseApiUrl = baseApiUrl;
    }

    public boolean isValidApiAccessKey(String apiAccessKey){
        Call call = headCall(apiAccessKey);
        try {
            Response response = call.execute();
            return response.isSuccessful();
        } catch (IOException ignored) {
        }
        return false;
    }

    private Request.Builder getBuilder(String accessKey){
        return new Request.Builder()
                .addHeader(X_REDMINE_API_KEY, accessKey);
    }

    private Call headCall(String key){
        return httpClient.newCall(getBuilder(key)
                .url(baseApiUrl + "projects.json")
                .head()
                .build());
    }
}
