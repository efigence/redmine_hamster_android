package com.efigence.redhamster.ui.presenters.settings;

import com.efigence.redhamster.data.ApiAccessKeyProvider;
import com.efigence.redhamster.ui.BasePresenter;
import com.efigence.redhamster.ui.UI;
import rx.Observable;

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
        ui.setCurrentApiAccessKey(apiAccessKeyProvider.isAccessKeySet() ? "*****" : "");
    }

    public void updateApiAccessKey(final String apiAccessKey){
        subscribeOnUi(Observable.<Boolean>create(subscriber -> {
            subscriber.onNext(apiAccessKeyProvider.setApiAccessKey(apiAccessKey));
            subscriber.onCompleted();
        }))
        .subscribe(success -> {
            if (success){
                ui.displaySuccessMessage("Access key saved");
            } else {
                ui.displayErrorMessage("Invalid access key");
            }
        }, catchException());
    }

    public interface SettingsUI extends UI {

        void setCurrentApiAccessKey(String apiAccessKey);
        void displayErrorMessage(String message);
        void displaySuccessMessage(String message);

    }
}


