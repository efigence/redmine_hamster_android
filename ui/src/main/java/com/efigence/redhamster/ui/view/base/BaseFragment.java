package com.efigence.redhamster.ui.view.base;

import android.support.v4.app.Fragment;
import com.efigence.redhamster.ui.ApplicationHelper;
import com.efigence.redhamster.ui.di.components.ApplicationComponent;


public abstract class BaseFragment extends Fragment {

    protected ApplicationComponent getApplicationComponent(){
        return ApplicationHelper.getApplicationComponent(this.getActivity());
    }

}
