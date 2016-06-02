package com.efigence.redhamster.ui.view.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.efigence.redhamster.ui.R;
import com.efigence.redhamster.ui.model.IssueViewModel;
import dagger.internal.Preconditions;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

public class IssuesListAdapter extends BaseAdapter {

    private final Context context;

    private List<IssueViewModel> issues = Collections.emptyList();

    @Inject
    public IssuesListAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return issues.size();
    }

    @Override
    public Object getItem(int position) {
        return issues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_issue_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        IssueViewModel issue = issues.get(position);
        holder.issueId.setText(String.valueOf(issue.getId()));
        holder.issueSubject.setText(issue.getSubject());
        return view;
    }

    public void updateIssues(List<IssueViewModel> issues){
        Preconditions.checkNotNull(issues, "Issues cannot be null.");
        this.issues = issues;
        notifyDataSetChanged();
    }

    static class ViewHolder {

        @Bind(R.id.issue_id) TextView issueId;
        @Bind(R.id.issue_subject) TextView issueSubject;

        ViewHolder (View view){
            ButterKnife.bind(this, view);
        }
    }

}
