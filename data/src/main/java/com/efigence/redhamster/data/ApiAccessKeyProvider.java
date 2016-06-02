package com.efigence.redhamster.data;

import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class ApiAccessKeyProvider {

    private final ApiAccessKeyValidator accessKeyValidator;
    private final SharedPreferences sharedPreferences;
    private final String accessKeyPropertyName;

    @Inject
    public ApiAccessKeyProvider(ApiAccessKeyValidator accessKeyValidator, SharedPreferences sharedPreferences, String accessKeyPropertyName) {
        this.accessKeyValidator = accessKeyValidator;
        this.sharedPreferences = sharedPreferences;
        this.accessKeyPropertyName = accessKeyPropertyName;
    }

    public String getApiAccessKey(){
        return sharedPreferences.getString(accessKeyPropertyName, null);
    }

    public boolean isAccessKeySet(){
        return getApiAccessKey() != null;
    }

    public boolean setApiAccessKey(String apiAccessKey){
        if (!accessKeyValidator.isValidApiAccessKey(apiAccessKey)){
            return false;
        }
        return sharedPreferences
                .edit()
                .putString(accessKeyPropertyName, apiAccessKey)
                .commit();
    }
}
