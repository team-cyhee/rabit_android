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

        partListView = findViewById(R.id.participantListView);
        partList = new ArrayList<Participant>();
/*        partList.add(new Participant("0","admin@gmail.com","cy118@naver.com",
                        "cyp","20180201",107,"process"));
        partList.add(new Participant("0","admin@gmail.com","whgksdyd23@naver.com",
                "hy1","20180202",106,"process"));
        partList.add(new Participant("0","admin@gmail.com","whgksdyd112@naver.com",
                "hy2","20180203",105,"process"));
        partList.add(new Participant("0","admin@gmail.com","whgksdyd123@naver.com",
                "hy3","20180204",104,"process"));
        partList.add(new Participant("0","admin@gmail.com","whgksdyd222@naver.com",
                "hy4","20180205",103,"process"));*/
        partListAdp = new ParticipantListAdapter(getApplicationContext(), partList);
        partListView.setAdapter(partListAdp);
    }
}
