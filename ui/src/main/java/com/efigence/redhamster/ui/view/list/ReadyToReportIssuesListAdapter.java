package com.efigence.redhamster.ui.view.list;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.efigence.redhamster.domain.model.HamsterIssue;
import com.efigence.redhamster.ui.R;
import dagger.internal.Preconditions;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ReadyToReportIssuesListAdapter extends BaseExpandableListAdapter {

    private final Context context;

    private List<String> header = Collections.emptyList();
    private Map<String, List<HamsterIssue>> issues = Collections.emptyMap();

    @Inject
    public ReadyToReportIssuesListAdapter(Context context){
        this.context = context;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.issues.get(header.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.ready_to_report_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        }

        HamsterIssue issue = (HamsterIssue) getChild(groupPosition, childPosition);
        holder.issueId.setText(String.valueOf(issue.getId()));
        holder.issueSubject.setText(issue.getIssue().getSubject());
        String spendTime = issue.getSpendTime() + " h";
        holder.issueSpendTime.setText(spendTime);
        return view;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.ready_to_report_list_group, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.issues.get(this.header.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.header.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.header.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void updateIssues(Map<String, List<HamsterIssue>> issues){
        Preconditions.checkNotNull(issues, "Issues cannot be null.");
        this.issues = issues;
        header = new ArrayList<>(issues.keySet());
        notifyDataSetChanged();
    }

    static class ViewHolder {

        @Bind(R.id.issue_id) TextView issueId;
        @Bind(R.id.issue_subject) TextView issueSubject;
        @Bind(R.id.issue_spend_time) TextView issueSpendTime;

        public ViewHolder (View view){
            ButterKnife.bind(this, view);
        }
    }

}
