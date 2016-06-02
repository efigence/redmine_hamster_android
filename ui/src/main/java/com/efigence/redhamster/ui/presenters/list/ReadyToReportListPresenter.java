package com.efigence.redhamster.ui.presenters.list;

import android.os.AsyncTask;
import com.efigence.redhamster.domain.model.HamsterIssue;
import com.efigence.redhamster.domain.usecase.UseCase;
import com.efigence.redhamster.domain.usecase.UseCaseArgumentless;
import com.efigence.redhamster.ui.BasePresenter;
import com.efigence.redhamster.ui.UI;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class ReadyToReportListPresenter extends BasePresenter<ReadyToReportListPresenter.ReadyToReportListUI> {

    private final UseCaseArgumentless<Map<String, List<HamsterIssue>>> loadIssuesUseCase;
    private final UseCase<Void, String> deleteIssueUseCase;
    private final UseCase<Void, String> reportIssueUseCase;

    @Inject
    public ReadyToReportListPresenter(UseCaseArgumentless<Map<String, List<HamsterIssue>>> loadIssuesUseCase,
                                      UseCase<Void, String> deleteIssueUseCase,
                                      UseCase<Void, String> reportIssueUseCase){
        this.loadIssuesUseCase = loadIssuesUseCase;
        this.deleteIssueUseCase = deleteIssueUseCase;
        this.reportIssueUseCase = reportIssueUseCase;
    }

    public void onDisplayIssues(){
        new LoadIssuesTask(loadIssuesUseCase).execute();
    }

    public void reportIssue(HamsterIssue issue) {
        createExecutableAsyncTask(reportIssueUseCase)
                .execute(String.valueOf(issue.getId()));
    }

    public void deleteIssue(HamsterIssue issue) {
        createExecutableAsyncTask(deleteIssueUseCase)
                .execute(String.valueOf(issue.getId()));
    }

    private AsyncTask<String, Void, Void> createExecutableAsyncTask(final UseCase<Void, String> useCase) {
        return new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                useCase.execute(params[0]);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                onDisplayIssues();
            }
        };
    }

    public interface ReadyToReportListUI extends UI {

        void refreshIssues(Map<String, List<HamsterIssue>> issues);

    }

    private class LoadIssuesTask extends AsyncTask<Void, Void, Map<String, List<HamsterIssue>>> {

        private final UseCaseArgumentless<Map<String, List<HamsterIssue>>> useCase;

        private LoadIssuesTask(UseCaseArgumentless<Map<String, List<HamsterIssue>>> useCase){
            this.useCase = useCase;
        }

        @Override
        protected Map<String, List<HamsterIssue>> doInBackground(Void... params) {
            return useCase.execute();
        }

        @Override
        protected void onPostExecute(Map<String, List<HamsterIssue>> issues) {
            ui.refreshIssues(issues);
        }

    }


}
