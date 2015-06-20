package com.stroke.academy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.stroke.academy.fragment.MeetingDayFragment;
import com.stroke.academy.model.MeetingDayData;

public class MeetingFragmentAdapter extends FragmentPagerAdapter{

	private MeetingDayData meetingDayData;
	
	public MeetingFragmentAdapter(FragmentManager fm, MeetingDayData item) {
		super(fm);
		this.meetingDayData = item;
	}

	@Override
	public Fragment getItem(int position) {
		return MeetingDayFragment.newInstance(position == 0? meetingDayData.getMeetingDays() : null);
	}

	@Override
	public int getCount() {
		return Integer.parseInt(meetingDayData.getTotalPage());
	}

}
