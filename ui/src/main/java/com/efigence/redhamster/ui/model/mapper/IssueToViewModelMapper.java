package com.efigence.redhamster.ui.model.mapper;


import com.efigence.redhamster.domain.model.Issue;
import com.efigence.redhamster.ui.model.IssueViewModel;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class IssueToViewModelMapper {

    @Inject
    public IssueToViewModelMapper(){
        // do nothing
    }

    public IssueViewModel toViewModel(Issue issue){
        return new IssueViewModel(
                String.valueOf(issue.getId()),
                issue.getSubject()
        );
    }

    public List<IssueViewModel> toViewModel(List<Issue> issues){
        List<IssueViewModel> result = new ArrayList<>(issues.size());
        for (Issue issue: issues) {
            result.add(toViewModel(issue));
        }
        return result;
    }
}
