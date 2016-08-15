package com.efigence.redhamster.data.store;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.efigence.redhamster.data.ApiAccessKeyProvider;
import com.efigence.redhamster.data.model.KeyValue;
import com.efigence.redhamster.data.model.ProjectEntity;
import com.google.gson.Gson;
import okhttp3.Call;
import okhttp3.OkHttpClient;

import javax.inject.Named;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ProjectEntityStoreRest extends BaseRestApi implements ProjectEntityStore {

    private static final String PROJECTS = "projects.json?limit=%s&offset=%s";
    private static final long RELOAD_FREQUENCY = TimeUnit.MINUTES.toMillis(60);

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
            loadAllProjects();
        }
        return projects.get(id);
    }

    @Override
    public List<KeyValue> getProjects() {
        return Stream.of(projects.values())
                .map(project -> new KeyValue(project.getId(), project.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public void loadAllProjects() {
        projects.clear();
        int limit = 20;
        int offset = 0;
        ResponseProjectsWrapper response;
        do {
            Call call = getCall(String.format(PROJECTS, limit, offset));
            response = gson.fromJson(execute(call), ResponseProjectsWrapper.class);
            Stream.of(response.projects)
                    .filter(project -> project.getStatus().equals(1))
                    .forEach(project -> projects.put(project.getId(), project));
            offset += limit;
        } while (response.total_count > offset);
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

    private static class ResponseProjectsWrapper {
        List<ProjectEntity> projects = Collections.emptyList();
        long total_count;
        long offset;
        long limit;
    }
}
