package com.efigence.redhamster.ui.view.list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.PopupMenu;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.efigence.redhamster.domain.model.HamsterIssue;
import com.efigence.redhamster.ui.R;
import com.efigence.redhamster.ui.presenters.list.ReadyToReportListPresenter;
import com.efigence.redhamster.ui.view.base.BaseFragment;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class ReadyToReportIssuesFragment extends BaseFragment implements ReadyToReportListPresenter.ReadyToReportListUI {

    @Bind(R.id.expandableListView)
    ExpandableListView listView;

    private final ReadyToReportListPresenter presenter;
    private final ReadyToReportIssuesListAdapter adapter;

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

    @Inject
    public ReadyToReportIssuesFragment(ReadyToReportListPresenter presenter,
                                       ReadyToReportIssuesListAdapter adapter) {
        this.presenter = presenter;
        this.adapter = adapter;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getApplicationComponent().inject(this);
        View rootView = inflater.inflate(R.layout.ready_to_report_fragment, container, false);
        ButterKnife.bind(this, rootView);
        listView.setAdapter(adapter);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                final HamsterIssue issue = (HamsterIssue) adapter.getChild(groupPosition, childPosition);
                final PopupMenu popup = new PopupMenu(getContext(), v);
                popup.getMenuInflater().inflate(R.menu.ready_to_report_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int i = item.getItemId();
                        if (i == R.id.menu_ready_to_report_report) {
                            presenter.reportIssue(issue);
                            return true;
                        }
                        else if (i == R.id.menu_ready_to_report_delete){
                            presenter.deleteIssue(issue);
                            return true;
                        }
                        else {
                            return onMenuItemClick(item);
                        }
                    }
                });
                popup.show();
                return false;
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
    public void refreshIssues(Map<String, List<HamsterIssue>> issues) {
        adapter.updateIssues(issues);
    }
}
