package com.pkg.Test;

import com.pkg.conference.tracking.impl.ConferenceTrackingSystemImpl;

import java.util.*;

public class ApplicationTest {
    public static void main(String[] args) throws Exception {
        Scanner scn = new Scanner(System.in);
        ConferenceTrackingSystemImpl tracking = new ConferenceTrackingSystemImpl();
        List<String> subjects = new ArrayList<>();

        // System.out.println("enter a Query :");
        subjects.add("how to get job:" + 1 + " 60min");
        subjects.add("how to get job:" + 2 + " 45min");
        subjects.add("how to get job:" + 3 + " 30min");
        subjects.add("how to get job:" + 4 + " 45min");
        subjects.add("how to get job:" + 5 + " 45min");
        subjects.add("how to get job:" + 6 + " 60min");
        subjects.add("how to get job:" + 7 + " 45min");
        subjects.add("how to get job:" + 8 + " 30min");
        subjects.add("how to get job:" + 9 + " 30min");
        subjects.add("how to get job:" + 10 + " 45min");
        subjects.add("how to get job:" + 11 + " 60min");
        subjects.add("how to get job:" + 12 + " 60min");
        subjects.add("how to get job:" + 13 + " 60min");
        subjects.add("how to get job:" + 14 + " 30min");
        subjects.add("how to get job:" + 15 + " 30min");
        subjects.add("how to get job:" + 16 + " 60min");
        subjects.add("how to get job:" + 17 + " 30min");
        subjects.add("how to get job:" + 18 + " 30min");
        subjects.add("how to get job:" + 19 + " 45min");
        subjects.add("how to get job:" + 20 + " 55min");
        subjects.add("how to get job:" + 21 + " 35min");


        tracking.gatherConferenceBookingProposal(subjects);
        scn.close();
    }
}
