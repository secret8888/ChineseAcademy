package com.stroke.academy.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;

import com.stroke.academy.R;
import com.stroke.academy.activity.video.MeetingDayActivity;
import com.stroke.academy.adapter.MeetingDayAdapter;
import com.stroke.academy.annotation.ViewId;
import com.stroke.academy.common.util.IntentManager;
import com.stroke.academy.fragment.base.BaseFragment;
import com.stroke.academy.model.HandleInfo;
import com.stroke.academy.model.MeetingDayData;
import com.stroke.academy.model.MeetingDayItem;

import java.util.ArrayList;

public class MeetingDayFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    @ViewId(R.id.list_content)
    private ListView contentView;

    private ArrayList<MeetingDayItem> meetingDays;

    private boolean isDataLoaded = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_meeting_day;
    }

    @Override
    protected void readIntent() {

    }

    @Override
    protected void initControls(Bundle savedInstanceState) {
        setAdapter();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!isDataLoaded) {
            ((MeetingDayActivity) getActivity()).getMeetingDays(new MeetingDayActivity.OnMeetingDayLoadListener() {
                @Override
                public void onMeetingDayLoaded(MeetingDayData meetingDayData) {
                    meetingDays = meetingDayData.getMeetingDays();
                    setAdapter();
                }
            });
        }
    }

    @Override
    protected void setListeners() {
        contentView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        IntentManager.startMeetingListActivity(getActivity(), meetingDays.get(position).getId(),
                meetingDays.get(position).getMeetingId());
    }

    private void setAdapter() {
        if(meetingDays != null && meetingDays.size() > 0) {
            isDataLoaded = true;
            MeetingDayAdapter meetingDayAdapter = new MeetingDayAdapter(getActivity(), meetingDays);
            contentView.setAdapter(meetingDayAdapter);
        }
    }

    public static MeetingDayFragment newInstance(ArrayList<MeetingDayItem> items) {
        MeetingDayFragment fragment = new MeetingDayFragment();
        fragment.meetingDays = items;
        return fragment;
    }
}
