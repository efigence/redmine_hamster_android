package com.efigence.redhamster.ui.di.modules;

import android.content.Context;
import android.content.SharedPreferences;
import com.efigence.redhamster.data.model.mapper.HamsterEntityToHamsterIssueModelMapper;
import com.efigence.redhamster.data.model.mapper.MyIssueEntityToIssueModelMapper;
import com.efigence.redhamster.data.store.HamsterEntityStore;
import com.efigence.redhamster.data.store.IssueEntityStore;
import com.efigence.redhamster.domain.datasource.IssueDataSource;
import com.efigence.redhamster.domain.datasource.IssueDataSourceImpl;
import com.efigence.redhamster.domain.usecase.*;
import com.efigence.redhamster.ui.AndroidApplication;
import com.efigence.redhamster.ui.model.mapper.IssueToViewModelMapper;
import com.efigence.redhamster.ui.presenters.ApplicationPresenter;
import com.efigence.redhamster.ui.presenters.list.IssuesListPresenter;
import com.efigence.redhamster.ui.presenters.list.ReadyToReportListPresenter;
import com.efigence.redhamster.ui.view.list.IssuesListAdapter;
import com.efigence.redhamster.ui.view.list.IssuesListFragment;
import com.efigence.redhamster.ui.view.list.ReadyToReportIssuesFragment;
import com.efigence.redhamster.ui.view.list.ReadyToReportIssuesListAdapter;
import dagger.Module;
import dagger.Provides;

import javax.inject.Named;
import javax.inject.Singleton;

@Module(includes = ApiModule.class)
public class ApplicationModule {

    private final String PREFERENCES_NAME = ApiModule.class.getName() + ".PREFERENCES";

    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    IssueDataSource provideIssueDataSource(HamsterEntityStore hamsterEntityStore, HamsterEntityToHamsterIssueModelMapper mapper, MyIssueEntityToIssueModelMapper myIssueEntityMapper){
        return new IssueDataSourceImpl(hamsterEntityStore, mapper, myIssueEntityMapper);
    }

    @Provides
    @Singleton
    HamsterEntityToHamsterIssueModelMapper provideHamsterEntityToIssueModelMapper(IssueEntityStore issueEntityStore){
        return new HamsterEntityToHamsterIssueModelMapper(issueEntityStore);
    }

    @Provides
    @Singleton
    MyIssueEntityToIssueModelMapper provideMyIssueEntityToIssueModelMapper(IssueEntityStore issueEntityStore){
        return new MyIssueEntityToIssueModelMapper(issueEntityStore);
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context context){
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    @Provides
    @Named("AssignedToMePresenter")
    @Singleton
    IssuesListPresenter provideAssignedToMeIssuesListPresenter(IssueToViewModelMapper mapper, GetAssignedToMeIssues useCase){
        return new IssuesListPresenter(mapper, useCase);
    }

    @Provides
    @Named("AssignedToMeFragment")
    @Singleton
    IssuesListFragment provideAssignedToMeFragment(IssuesListAdapter listAdapter,
                                                   @Named("AssignedToMePresenter") IssuesListPresenter presenter,
                                                   ApplicationPresenter applicationPresenter){
        return new IssuesListFragment(0, listAdapter, presenter, applicationPresenter);
    }

    @Provides
    @Named("MyRecentlyUsedPresenter")
    @Singleton
    IssuesListPresenter provideMyRecentlyUsedIssuesListPresenter(IssueToViewModelMapper mapper, GetMyRecentlyUsedIssues useCase){
        return new IssuesListPresenter(mapper, useCase);
    }

    @Provides
    @Named("MyRecentlyUsedFragment")
    @Singleton
    IssuesListFragment provideMyRecentlyUsedFragment(IssuesListAdapter listAdapter,
                                                   @Named("MyRecentlyUsedPresenter") IssuesListPresenter presenter,
                                                     ApplicationPresenter applicationPresenter){
        return new IssuesListFragment(1, listAdapter, presenter, applicationPresenter);
    }

    @Provides
    @Singleton
    ReadyToReportListPresenter provideReadyToReportListPresenter(GetStoppedIssuesUseCase useCase,
                                                                 DeleteIssueUseCase deleteIssueUseCase,
                                                                 ReportIssueUseCase reportIssueUseCase){
        return new ReadyToReportListPresenter(useCase, deleteIssueUseCase, reportIssueUseCase);
    }

    @Provides
    @Singleton
    ReadyToReportIssuesFragment provideReadyToReportIssuesFragment(ReadyToReportIssuesListAdapter listAdapter,
                                                                   ReadyToReportListPresenter presenter){
        return new ReadyToReportIssuesFragment(presenter, listAdapter);
    }

}
