package com.stroke.academy.model;

import java.util.ArrayList;

/**
 * Created by emilyu on 6/11/15.
 */
public class MeetingData {

    private String totalPage;

    private ArrayList<MeetingItem> meetings;

    public ArrayList<MeetingItem> getMeetings() {
        return meetings;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public void setMeetings(ArrayList<MeetingItem> meetings) {
        this.meetings = meetings;
    }

    public void addMeeting(MeetingItem item) {
        if(meetings == null) {
            meetings = new ArrayList<>();
        } else {
            meetings.add(item);
        }
    }

    public void addMeetings(ArrayList<MeetingItem> items) {
        if(meetings == null) {
            meetings = items;
        } else {
            meetings.addAll(items);
        }
    }
}
