package com.stroke.academy.model;

import java.util.ArrayList;

/**
 * Created by emilyu on 6/11/15.
 */
public class MeetingDayData {

    private String totalPage;

    private ArrayList<MeetingDayItem> meetingDays;

    public ArrayList<MeetingDayItem> getMeetingDays() {
        return meetingDays;
    }

    public void setMeetingDays(ArrayList<MeetingDayItem> meetingDays) {
        this.meetingDays = meetingDays;
    }

    public void addMeetingDay(MeetingDayItem item) {
        if(meetingDays == null) {
            meetingDays = new ArrayList<>();
        } else {
            meetingDays.add(item);
        }
    }

    public void addMeetingDays(ArrayList<MeetingDayItem> items) {
        if(meetingDays == null) {
            meetingDays = items;
        } else {
            meetingDays.addAll(items);
        }
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }
}
