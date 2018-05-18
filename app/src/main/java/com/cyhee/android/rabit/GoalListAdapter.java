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
    public Object getItem(int position) { return goalList.get(position); }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.goal, null);
        TextView nameText = v.findViewById(R.id.nameText);
        TextView titleText = v.findViewById(R.id.titleText);
        TextView dateText = v.findViewById(R.id.dateText);
        TextView participantText = v.findViewById(R.id.participantText);
        TextView dailyLogText = v.findViewById(R.id.dailyLogText);

        String date = goalList.get(position).getgStartDate() + "~ " + goalList.get(position).getgDays() + "days";
        String ment1 = " ... ";
        String ment2 = " days";
        String parti = goalList.get(position).getgPname(0)+ment1+goalList.get(position).getgPday(0)+ment2
                +"\n"+goalList.get(position).getgPname(1)+ment1+goalList.get(position).getgPday(1)+ment2;

        nameText.setText(goalList.get(position).getgName());
        titleText.setText(goalList.get(position).getgTitle());
        dateText.setText(date);
        participantText.setText(parti);
        dailyLogText.setText(goalList.get(position).getgRecent());

        v.setTag(goalList.get(position).getgEmail()+goalList.get(position).getgId());
        return v;
    }

}
