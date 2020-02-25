package com.pkg.conference.tracking;

import com.pkg.conference.talk.ConferenceTalk;

import java.util.List;

public interface ConferenceTrackingSystem {
    public void gatherConferenceBookingProposal(List<String> subjects) throws Exception;

    public void displayData();
}
