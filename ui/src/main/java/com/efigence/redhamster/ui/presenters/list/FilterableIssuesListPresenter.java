package com.efigence.redhamster.ui.presenters.list;

import com.efigence.redhamster.data.model.KeyValue;
import com.efigence.redhamster.domain.model.IssuesQuery;
import com.efigence.redhamster.domain.usecase.GetAllProjectsUseCase;
import com.efigence.redhamster.domain.usecase.GetAllStatusesUseCase;
import com.efigence.redhamster.domain.usecase.GetIssuesUseCase;
import com.efigence.redhamster.ui.BasePresenter;
import com.efigence.redhamster.ui.UI;
import com.efigence.redhamster.ui.model.IssueViewModel;
import com.efigence.redhamster.ui.model.mapper.IssueToViewModelMapper;

import javax.inject.Inject;
import java.util.List;

public class FilterableIssuesListPresenter extends BasePresenter<FilterableIssuesListPresenter.IssueListUI> {

    public static final KeyValue EMPTY = new KeyValue(null, null);
    public static final int FIRST_PAGE = 1;
    private final IssueToViewModelMapper mapper;
    private final GetIssuesUseCase useCase;
    private final GetAllProjectsUseCase allProjectsUseCase;
    private final GetAllStatusesUseCase allStatusesUseCase;

    @Inject
    public FilterableIssuesListPresenter(IssueToViewModelMapper mapper,
                                         GetIssuesUseCase useCase,
                                         GetAllProjectsUseCase allProjectsUseCase,
                                         GetAllStatusesUseCase allStatusesUseCase){
        this.mapper = mapper;
        this.useCase = useCase;
        this.allProjectsUseCase = allProjectsUseCase;
        this.allStatusesUseCase = allStatusesUseCase;
    }

    @Override
    public void onAttach(IssueListUI ui) {
        super.onAttach(ui);

        createObservableOnUi(allProjectsUseCase)
                .subscribe(projects -> ui.updateProjects(projects));

        createObservableOnUi(allStatusesUseCase)
                .subscribe(statuses -> ui.updateStatuses(statuses));

        displayIssues(FIRST_PAGE, EMPTY, EMPTY);
    }

    public void displayIssues(int page, KeyValue project, KeyValue status){
        int limit = 20;
        int offset = (page * limit) - limit;
        IssuesQuery issuesQuery = new IssuesQuery(status.getId(), project.getId(), limit, offset);
        if (ui != null){
            ui.showProgressBar();
        }
        createObservableOnUi(useCase, issuesQuery)
                .filter(issues -> ui != null)
                .subscribe(issues -> {
                    List<IssueViewModel> issueViewModels = mapper.toViewModel(issues);
                    if (page == FIRST_PAGE){
                        ui.refreshIssues(issueViewModels);
                    } else {
                        ui.addIssues(issueViewModels);
                    }
                    ui.hideProgressBar();
                }, catchException());
    }

    public interface IssueListUI extends UI {

        void refreshIssues(List<IssueViewModel> issues);
        void addIssues(List<IssueViewModel> issues);

        void showProgressBar();
        void hideProgressBar();

        void updateProjects(List<KeyValue> projects);
        void updateStatuses(List<KeyValue> statuses);
    }

}
