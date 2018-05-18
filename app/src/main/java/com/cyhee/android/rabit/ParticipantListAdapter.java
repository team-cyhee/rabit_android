package com.cyhee.android.rabit;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ParticipantListAdapter extends BaseAdapter {
    private Context context;
    private List<Participant> participantList;

    public ParticipantListAdapter(Context context, List<Participant> participantList) {
        this.context = context;
        this.participantList = participantList;
    }

    @Override
    public int getCount() { return participantList.size(); }

    @Override
    public Participant getItem(int position) { return participantList.get(position); };

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.participant, null);
        TextView nameText = v.findViewById(R.id.nameText);
        TextView daysText = v.findViewById(R.id.daysText);

        nameText.setText(participantList.get(position).getpName());
        daysText.setText(participantList.get(position).getpDays());

        v.setTag(participantList.get(position).getgId()+participantList.get(position).getgEmail()+participantList.get(position).getpEmail());
        return v;
    }
}
