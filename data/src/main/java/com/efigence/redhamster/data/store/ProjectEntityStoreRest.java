package com.efigence.redhamster.data.store;

import com.efigence.redhamster.data.ApiAccessKeyProvider;
import com.efigence.redhamster.data.model.ProjectEntity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Call;
import okhttp3.OkHttpClient;

import javax.inject.Named;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProjectEntityStoreRest extends BaseRestApi implements ProjectEntityStore {

    private static final String PROJECTS = "projects.json";
    private static final long RELOAD_FREQUENCY = 1000*60*15;

    private final Map<String, ProjectEntity> projects = new HashMap<>();
    private long lastReloadTime = -1;

    public ProjectEntityStoreRest(OkHttpClient httpClient,
                                  Gson gson,
                                  ApiAccessKeyProvider apiAccessKeyProvider,
                                  @Named("BaseApiUrl") String baseApiUrl){
        super(httpClient, gson, baseApiUrl, apiAccessKeyProvider);
    }

    @Override
    public ProjectEntity getProject(String id){
        if (shouldReloadProjects()){
            reloadProjects();
        }
        return projects.get(id);
    }

    private List<ProjectEntity> getProjects() {
        Call call = getCall(PROJECTS);
        return gson.fromJson(execute(call), projectsListType());
    }

    private void reloadProjects() {
        projects.clear();
        for (ProjectEntity project: getProjects()) {
            projects.put(project.getId(), project);
        }
        lastReloadTime = getTime();
    }

    private boolean shouldReloadProjects() {
        if (lastReloadTime == -1){
            return true;
        }
        if (getTime() - lastReloadTime > RELOAD_FREQUENCY){
            return true;
        }
        return false;
    }

    private long getTime() {
        return new Date().getTime();
    }

    private Type projectsListType() {
        return new TypeToken<Map<String, List<ProjectEntity>>>(){}.getType();
    }
}
