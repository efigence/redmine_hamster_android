package com.efigence.redhamster.ui.view.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.efigence.redhamster.ui.R;
import com.efigence.redhamster.ui.presenters.settings.SettingsPresenter;
import com.efigence.redhamster.ui.view.base.BaseFragment;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SettingsFragment extends BaseFragment implements SettingsPresenter.SettingsUI {

    @Bind(R.id.settings_api_access_key)
    EditText apiAccessKeyEditText;
    @Bind(R.id.settings_update)
    Button updateButton;
    @Inject
    SettingsPresenter presenter;

    @Inject
    public SettingsFragment(){

    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onAttach(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getApplicationComponent().inject(this);
        View rootView = inflater.inflate(R.layout.settings_fragment, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void setCurrentApiAccessKey(String apiAccessKey) {
        apiAccessKeyEditText.setText(apiAccessKey);
    }

    @Override
    public void displayErrorMessage(String message) {
        apiAccessKeyEditText.setError(message);
    }

    @Override
    public void displaySuccessMessage(String message) {
        apiAccessKeyEditText.setError(null);
    }

    @OnClick(R.id.settings_update)
    public void update(){
        String value = apiAccessKeyEditText.getText().toString();
        if (value.isEmpty()){
            displayErrorMessage("Value cannot be empty");
        } else {
            presenter.updateApiAccessKey(value);
        }
    }

}
