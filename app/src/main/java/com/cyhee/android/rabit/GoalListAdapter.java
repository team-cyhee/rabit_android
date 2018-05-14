package com.cyhee.android.rabit;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class GoalListAdapter extends BaseAdapter {
    private Context context;
    private List<Goal> goalList;

    public GoalListAdapter(Context context, List<Goal> goalList) {
        this.context = context;
        this.goalList = goalList;
    }

    @Override
    public int getCount() {
        return goalList.size();
    }

    @Override
    public Object getItem(int idx) {
        return goalList.get(idx);
    }

    @Override
    public long getItemId(int idx) {
        return idx;
    }

    @Override
    public View getView(int idx, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.goal, null);
        TextView nameText = v.findViewById(R.id.nameText);
        TextView titleText = v.findViewById(R.id.titleText);
        TextView dateText = v.findViewById(R.id.dateText);
        TextView participantText = v.findViewById(R.id.participantText);
        TextView dailyLogText = v.findViewById(R.id.dailyLogText);

        String date = goalList.get(idx).getgStartDate() + "~ " + goalList.get(idx).getgDays() + "days";
        String ment1 = " ... ";
        String ment2 = " days";
        String parti = goalList.get(idx).getgPname(0)+ment1+goalList.get(idx).getgPday(0)+ment2
                +"\n"+goalList.get(idx).getgPname(1)+ment1+goalList.get(idx).getgPday(1)+ment2;

        nameText.setText(goalList.get(idx).getgName());
        titleText.setText(goalList.get(idx).getgTitle());
        dateText.setText(date);
        participantText.setText(parti);
        dailyLogText.setText(goalList.get(idx).getgRecent());

        v.setTag(goalList.get(idx).getgEmail()+goalList.get(idx).getgId());
        return v;
    }
}
