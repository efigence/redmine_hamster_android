package com.efigence.redhamster.ui.presenters;

import com.efigence.redhamster.domain.usecase.LoadProjectsUseCase;
import com.efigence.redhamster.domain.usecase.LoadStatusesUseCase;
import com.efigence.redhamster.ui.BasePresenter;
import com.efigence.redhamster.ui.UI;
import rx.Observable;

import javax.inject.Inject;

/**
 * Created by lgorbski on 06.08.16.
 */
public class SplashPresenter extends BasePresenter<SplashPresenter.SplashUI> {

    private final LoadProjectsUseCase loadProjectsUseCase;
    private final LoadStatusesUseCase loadStatusesUseCase;

    @Inject
    public SplashPresenter(LoadProjectsUseCase loadProjectsUseCase, LoadStatusesUseCase loadStatusesUseCase) {
        this.loadProjectsUseCase = loadProjectsUseCase;
        this.loadStatusesUseCase = loadStatusesUseCase;
    }

    public void initData() {
        subscribeOnUi(Observable.zip(
                createObservableOnUi(loadProjectsUseCase),
                createObservableOnUi(loadStatusesUseCase),
                (first, second) -> null
        )).subscribe(aVoid -> ui.openApplication());
    }

    public interface SplashUI extends UI {

        void openApplication();

    }
}
