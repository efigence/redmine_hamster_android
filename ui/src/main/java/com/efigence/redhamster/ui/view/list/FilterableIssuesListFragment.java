package com.efigence.redhamster.ui.view.list;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.efigence.redhamster.data.model.KeyValue;
import com.efigence.redhamster.ui.R;
import com.efigence.redhamster.ui.model.IssueViewModel;
import com.efigence.redhamster.ui.presenters.ApplicationPresenter;
import com.efigence.redhamster.ui.presenters.list.FilterableIssuesListPresenter;
import com.efigence.redhamster.ui.view.base.BaseFragment;

import java.util.Comparator;
import java.util.List;

public class FilterableIssuesListFragment extends BaseFragment implements FilterableIssuesListPresenter.IssueListUI {

    @Bind(R.id.section_label) TextView textView;
    @Bind(R.id.show_filters_container) ImageButton showFilters;
    @Bind(R.id.container_filters) LinearLayout filtersContainer;
    @Bind(R.id.spinner_projects) Spinner projects;
    @Bind(R.id.spinner_statuses) Spinner statuses;
    @Bind(R.id.listView) ListView listView;
    @Bind(R.id.progress_bar) ProgressBar progressBar;

    private ArrayAdapter<KeyValue> projectsAdapter;
    private ArrayAdapter<KeyValue> statusesAdapter;

    private final int sectionNumber;
    private final IssuesListAdapter adapter;
    private final FilterableIssuesListPresenter presenter;
    private final ApplicationPresenter applicationPresenter;

    private final InfiniteScrollListener infiniteScrollListener = new InfiniteScrollListener(5) {
        @Override
        public void loadMore(int page, int totalItemsCount) {
            loadByPageAndFilters(page);
        }
    };

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

    public FilterableIssuesListFragment(int sectionNumber, IssuesListAdapter adapter, FilterableIssuesListPresenter presenter, ApplicationPresenter applicationPresenter) {
        this.sectionNumber = sectionNumber;
        this.adapter = adapter;
        this.presenter = presenter;
        this.applicationPresenter = applicationPresenter;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getApplicationComponent().inject(this);
        View rootView = inflater.inflate(R.layout.fragment_filterable_issues, container, false);
        ButterKnife.bind(this, rootView);
        int identifier = getResources().getIdentifier("issues_tab_" + sectionNumber, "string", getContext().getPackageName());
        textView.setText(getString(identifier));

        projectsAdapter = new ArrayAdapter<>(this.getContext(), R.layout.simple_spinner_item);
        projectsAdapter.sort(getKeyValueComparator());
        projects.setAdapter(projectsAdapter);
        projects.setOnItemSelectedListener(createListener());

        statusesAdapter = new ArrayAdapter<>(this.getContext(), R.layout.simple_spinner_item);
        statusesAdapter.sort(getKeyValueComparator());
        statuses.setAdapter(statusesAdapter);
        statuses.setOnItemSelectedListener(createListener());


        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            IssueViewModel item = (IssueViewModel) adapter.getItem(position);
            applicationPresenter.onDisplayStartIssue(item.getId());
        });
        listView.setOnScrollListener(infiniteScrollListener);

        showFilters.setOnClickListener(v -> {
            if (filtersContainer.getVisibility() == View.VISIBLE){
                filtersContainer.setVisibility(View.GONE);
                showFilters.setImageDrawable(getResources().getDrawable(R.drawable.ic_keyboard_arrow_down_black_48dp));
            } else {
                filtersContainer.setVisibility(View.VISIBLE);
                showFilters.setImageDrawable(getResources().getDrawable(R.drawable.ic_keyboard_arrow_up_black_48dp));
            }
        });

        return rootView;
    }

    private AdapterView.OnItemSelectedListener createListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadByPageAndFilters(1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                loadByPageAndFilters(1);
            }
        };
    }

    private void loadByPageAndFilters(int page) {
        if (page == 1){
            infiniteScrollListener.reset();
        }
        presenter.displayIssues(
                page,
                projectsAdapter.getItem((int) projects.getSelectedItemId()),
                statusesAdapter.getItem((int) statuses.getSelectedItemId())
        );
    }

    private Comparator<KeyValue> getKeyValueComparator() {
        return (f, s) -> {
            if (f.getId() == null){
                return -1;
            }
            if (s.getId() == null){
                return 1;
            }
            return f.getId().compareTo(s.getId());
        };
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void refreshIssues(List<IssueViewModel> issues) {
        adapter.updateIssues(issues);
    }

    @Override
    public void addIssues(List<IssueViewModel> issues) {
        adapter.addIssues(issues);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void updateProjects(List<KeyValue> projects) {
        projectsAdapter.clear();
        projectsAdapter.add(new KeyValue(null, getString(R.string.select_project)));
        projectsAdapter.addAll(projects);
    }

    @Override
    public void updateStatuses(List<KeyValue> statuses) {
        statusesAdapter.clear();
        statusesAdapter.add(new KeyValue(null, getString(R.string.select_status)));
        statusesAdapter.addAll(statuses);
    }
}
