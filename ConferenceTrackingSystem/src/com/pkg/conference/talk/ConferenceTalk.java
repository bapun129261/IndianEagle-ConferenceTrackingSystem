package com.pkg.conference.talk;

import java.util.Comparator;

public class ConferenceTalk {

    private String conferenceSubject;
    private String startTime;
    private int minute;
    private boolean bookedOrNot=false;




    public String getConferenceSubject() {
        return conferenceSubject;
    }

    public void setConferenceSubject(String conferenceSubject) {
        this.conferenceSubject = conferenceSubject;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean isBookedOrNot() {
        return bookedOrNot;
    }

    public void setBookedOrNot(boolean bookedOrNot) {
        this.bookedOrNot = bookedOrNot;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "ConferenceTalk{" +
                "conferenceSubject='" + conferenceSubject + '\'' +
                ", startTime='" + startTime + '\'' +
                ", minute=" + minute +
                ", bookedOrNot=" + bookedOrNot +
                '}';
    }
}
