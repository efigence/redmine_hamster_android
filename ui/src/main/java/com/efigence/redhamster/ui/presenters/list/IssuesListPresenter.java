package com.efigence.redhamster.ui.presenters.list;

import com.efigence.redhamster.domain.model.Issue;
import com.efigence.redhamster.domain.usecase.UseCaseArgumentless;
import com.efigence.redhamster.ui.BasePresenter;
import com.efigence.redhamster.ui.UI;
import com.efigence.redhamster.ui.model.IssueViewModel;
import com.efigence.redhamster.ui.model.mapper.IssueToViewModelMapper;

import javax.inject.Inject;
import java.util.List;

public class IssuesListPresenter extends BasePresenter<IssuesListPresenter.IssueListUI> {

    private final IssueToViewModelMapper mapper;
    private final UseCaseArgumentless<List<Issue>> useCase;

    @Inject
    public IssuesListPresenter(IssueToViewModelMapper mapper, UseCaseArgumentless<List<Issue>> useCase){
        this.mapper = mapper;
        this.useCase = useCase;
    }

    public void onDisplayIssues(){
        createObservableOnUi(useCase)
                .subscribe(issues -> ui.refreshIssues(mapper.toViewModel(issues)));
    }

    public interface IssueListUI extends UI {

        void refreshIssues(List<IssueViewModel> issues);

    }

}
