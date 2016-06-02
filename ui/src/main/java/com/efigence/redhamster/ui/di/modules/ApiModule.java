package com.efigence.redhamster.ui.di.modules;

import android.content.SharedPreferences;
import com.efigence.redhamster.data.ApiAccessKeyProvider;
import com.efigence.redhamster.data.ApiAccessKeyValidator;
import com.efigence.redhamster.data.store.HamsterEntityStore;
import com.efigence.redhamster.data.store.HamsterEntityStoreRest;
import com.efigence.redhamster.data.store.IssueEntityStore;
import com.efigence.redhamster.data.store.IssueEntityStoreRest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.concurrent.TimeUnit;

@Module
public class ApiModule {

    @Provides
    @Named("AccessKeyPropertyName")
    @Singleton
    String provideAccessKeyPropertyName(){
        return "accessKeyProperty";
    }

    @Provides
    @Named("BaseApiUrl")
    @Singleton
    String provideBaseApiUrl(){
        return "https://secure.artegence.com/redmine/";
    }

    @Provides
    @Singleton
    Gson provideGson(){
        return new GsonBuilder()
                .create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient()
                .newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .build();
    }
    @Provides
    @Singleton
    ApiAccessKeyValidator provideApiAccessKeyValidator(OkHttpClient httpClient, @Named("BaseApiUrl") String baseApiUrl){
        return new ApiAccessKeyValidator(httpClient, baseApiUrl);
    }
    @Provides
    @Singleton
    ApiAccessKeyProvider provideApiAccessKeyProvider(SharedPreferences sharedPreferences,
                                                     @Named("AccessKeyPropertyName") String accessKeyPropertyName,
                                                     ApiAccessKeyValidator accessKeyValidator){
        return new ApiAccessKeyProvider(accessKeyValidator, sharedPreferences, accessKeyPropertyName);
    }
    @Provides
    @Singleton
    HamsterEntityStore provideHamsterEntityStoreRest(OkHttpClient httpClient,
                                                     Gson gson,
                                                     ApiAccessKeyProvider apiAccessKeyProvider,
                                                     @Named("BaseApiUrl") String baseApiUrl){
        return new HamsterEntityStoreRest(httpClient, gson, apiAccessKeyProvider, baseApiUrl);
    }

    @Provides
    @Singleton
    IssueEntityStore provideIssueEntityStoreRest(OkHttpClient httpClient,
                                                 Gson gson,
                                                 ApiAccessKeyProvider apiAccessKeyProvider,
                                                 @Named("BaseApiUrl") String baseApiUrl){
        return new IssueEntityStoreRest(httpClient, gson, apiAccessKeyProvider, baseApiUrl);
    }

}
