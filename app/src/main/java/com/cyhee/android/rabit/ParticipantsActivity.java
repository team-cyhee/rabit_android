package com.cyhee.android.rabit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ParticipantsActivity extends AppCompatActivity {

    private ListView partListView;
    private ParticipantListAdapter partListAdp;
    private List<Participant> partList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participants);

        /* TODO: connect Mypage_Goal with Participant List */
        partListView = findViewById(R.id.participantListView);
        partList = new ArrayList<Participant>();
        partListAdp = new ParticipantListAdapter(getApplicationContext(), partList);
        partListView.setAdapter(partListAdp);
    }
}
