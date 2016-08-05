package com.efigence.redhamster.ui.model.mapper;


import com.efigence.redhamster.domain.model.HamsterIssue;
import com.efigence.redhamster.ui.model.HamsterIssueViewModel;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Singleton
public class HamsterIssueToViewModelMapper {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());

    private final IssueToViewModelMapper issueToViewModelMapper;

    @Inject
    public HamsterIssueToViewModelMapper(IssueToViewModelMapper issueToViewModelMapper){
        this.issueToViewModelMapper = issueToViewModelMapper;
    }

    public HamsterIssueViewModel toViewModel(final HamsterIssue issue){
        if (issue == null){
            return null;
        }
        return new HamsterIssueViewModel(
                String.valueOf(issue.getId()),
                DATE_FORMAT.format(issue.getStartDate()),
                issueToViewModelMapper.toViewModel(issue.getIssue()),
                () -> {
                    Date startDate = issue.getStartDate();
                    long diff = new Date().getTime() - startDate.getTime();
                    int passedSeconds = (int) (diff / 1000);
                    int seconds = passedSeconds % 60;
                    int minutes = (passedSeconds / 60) % 60;
                    int hours = (passedSeconds / 3600);
                    return String.format("%02d : %02d : %02d", hours, minutes, seconds);
                });
    }

}
