package com.efigence.redhamster.ui.view.list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.efigence.redhamster.ui.R;
import com.efigence.redhamster.ui.model.IssueViewModel;
import com.efigence.redhamster.ui.presenters.ApplicationPresenter;
import com.efigence.redhamster.ui.presenters.list.IssuesListPresenter;
import com.efigence.redhamster.ui.view.base.BaseFragment;

import java.util.List;

public class IssuesListFragment extends BaseFragment implements IssuesListPresenter.IssueListUI {

    @Bind(R.id.section_label) TextView textView;
    @Bind(R.id.listView) ListView listView;

    private final int sectionNumber;
    private final IssuesListAdapter adapter;
    private final IssuesListPresenter presenter;
    private final ApplicationPresenter applicationPresenter;

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

    public IssuesListFragment(int sectionNumber, IssuesListAdapter adapter, IssuesListPresenter presenter, ApplicationPresenter applicationPresenter) {
        this.sectionNumber = sectionNumber;
        this.adapter = adapter;
        this.presenter = presenter;
        this.applicationPresenter = applicationPresenter;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getApplicationComponent().inject(this);
        View rootView = inflater.inflate(R.layout.fragment_issues, container, false);
        ButterKnife.bind(this, rootView);
        int identifier = getResources().getIdentifier("issues_tab_" + sectionNumber, "string", getContext().getPackageName());
        textView.setText(getString(identifier));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IssueViewModel item = (IssueViewModel) adapter.getItem(position);
                applicationPresenter.onDisplayStartIssue(item.getId());
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        presenter.onDisplayIssues();
    }

    @Override
    public void refreshIssues(List<IssueViewModel> issues) {
        adapter.updateIssues(issues);
    }
}
