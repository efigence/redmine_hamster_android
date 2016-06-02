package com.efigence.redhamster.ui.presenters.settings;

import android.os.AsyncTask;
import com.efigence.redhamster.data.ApiAccessKeyProvider;
import com.efigence.redhamster.ui.BasePresenter;
import com.efigence.redhamster.ui.UI;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SettingsPresenter extends BasePresenter<SettingsPresenter.SettingsUI> {

    private final ApiAccessKeyProvider apiAccessKeyProvider;

    @Inject
    public SettingsPresenter(ApiAccessKeyProvider apiAccessKeyProvider) {
        this.apiAccessKeyProvider = apiAccessKeyProvider;
    }

    @Override
    public void onAttach(SettingsUI ui) {
        super.onAttach(ui);
        ui.setCurrentApiAccessKey(apiAccessKeyProvider.getApiAccessKey());
    }

    public void updateApiAccessKey(final String apiAccessKey){
        new AsyncTask<Void, Void, Boolean>(){
            @Override
            protected Boolean doInBackground(Void... params) {
                return apiAccessKeyProvider.setApiAccessKey(apiAccessKey);
            }
            @Override
            protected void onPostExecute(Boolean result) {
                if (result){
                    ui.displaySuccessMessage("Access key saved");
                } else {
                    ui.displayErrorMessage("Invalid access key");
                }
            }
        }.execute();
    }

    public interface SettingsUI extends UI {

        void setCurrentApiAccessKey(String apiAccessKey);
        void displayErrorMessage(String message);
        void displaySuccessMessage(String message);

    }
}


