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
    public Object getItem(int idx) { return participantList.get(idx); };

    @Override
    public long getItemId(int idx) { return idx; }

    @Override
    public View getView(int idx, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.participant, null);
        TextView nameText = v.findViewById(R.id.nameText);
        TextView daysText = v.findViewById(R.id.daysText);

        nameText.setText(participantList.get(idx).getpName());
        daysText.setText(participantList.get(idx).getpDays());

        v.setTag(participantList.get(idx).getgId()+participantList.get(idx).getgEmail()+participantList.get(idx).getpEmail());
        return v;
    }
}
