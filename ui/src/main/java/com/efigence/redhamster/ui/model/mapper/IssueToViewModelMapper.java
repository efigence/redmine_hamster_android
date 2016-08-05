package com.efigence.redhamster.ui.model.mapper;


import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.efigence.redhamster.domain.model.Issue;
import com.efigence.redhamster.ui.model.IssueViewModel;

import javax.inject.Inject;
import javax.inject.Singleton;
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
        return Stream.of(issues)
                .map(issue -> toViewModel(issue))
                .collect(Collectors.toList());
    }
}
