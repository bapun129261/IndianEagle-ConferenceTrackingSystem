package com.pkg.conference.tracking.impl;

import com.pkg.conference.talk.ConferenceTalk;
import com.pkg.conference.tracking.ConferenceTrackingSystem;
import com.pkg.conference.util.DateAndTime;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ConferenceTrackingSystemImpl implements ConferenceTrackingSystem {

    private static int assignQueryCount = 0;
    private static int totalMinute = 0;
    private List<ConferenceTalk> talkList = new ArrayList<>();
    private List<ConferenceTalk> sessionData = new ArrayList<>();
    private List<List<ConferenceTalk>> totalBookedSlot = new ArrayList<>();

    private boolean validateQuery(String query) {
        return query.endsWith("min") || query.endsWith("minute");
    }

    private ConferenceTalk separateQueryAndMin(String query) {
        String[] strArr = query.split(" ");
        StringBuilder sb = new StringBuilder();
        int min;
        ConferenceTalk conferenceTalk = new ConferenceTalk();
        for (int i = 0; i < strArr.length - 1; i++) {
            sb.append(strArr[i]).append(" ");
        }
        conferenceTalk.setConferenceSubject(sb.toString());
        min = Integer.parseInt(strArr[strArr.length - 1].split("min")[0]);
        if (min > 60) {
            min = 0;
            conferenceTalk.setMinute(min);
            System.err.println(query + " <- not a valid minute..!");
        } else {
            totalMinute = totalMinute + min;
            conferenceTalk.setMinute(min);
        }
        return conferenceTalk;
    }

    private void processData(List<String> subjects) {
        int queryCount = 0;
        for (String query : subjects) {
            queryCount++;
            if (validateQuery(query)) {
                talkList.add(separateQueryAndMin(query));
            } else {
                subjects.remove(queryCount - 1);
                System.err.println(queryCount + "   : " + query + " <--NOT A VALID QUERY");
            }
        }
        talkList = talkList.stream().sorted(Comparator.comparingInt(ConferenceTalk::getMinute).reversed()).collect(Collectors.toList());
        assignSlotToClient(talkList);
        displayData();
    }

    @Override
    public void gatherConferenceBookingProposal(List<String> subjects) throws Exception {
        if (subjects.size() > 20) {
            System.err.println("request more then 20 so we are you are going to remove all request coming after count 20");
            for (int i = 20; i < subjects.size(); i++) {
                subjects.remove(i);
            }
        }
        if (subjects.size() < 1) {
            throw new Exception("having less then one request");
        }
        processData(subjects);
    }


    private void assignSlotToClient(List<ConferenceTalk> talkList) {
        List<ConferenceTalk> assignedSlotForOneDay = new ArrayList<>();
        boolean isAfternoon = false;
        if (!talkList.isEmpty()) {
            int noOfTracks = 0;
            Double totalMinutesInDouble = totalMinute * 1.0;
            Double numberOfTracks = totalMinutesInDouble / 420;
            double fractionalPart = numberOfTracks % 1;
            double integralPart = numberOfTracks - fractionalPart;
            int leftMinutes = totalMinute - (int) integralPart * 420;

            if (leftMinutes > 0) {
                noOfTracks = (int) integralPart + 1;
            } else {
                noOfTracks = (int) integralPart;
            }

            for (int i = 1; i <= noOfTracks; i++) {

                assignSlotForSession(talkList);
            }
        }//if
    }


    private void assignSlotForSession(List<ConferenceTalk> talkList) {
        int morningRemainMinute = DateAndTime.MORNING_SESSION;
        int afterNoonRemainMinute = DateAndTime.AFTER_NOON_SESSION;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm a");
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.AM_PM, Calendar.AM);

        for (int i = 0; i < talkList.size(); i++) {
            if (!talkList.get(i).isBookedOrNot()) {
                if (talkList.get(i).getMinute() < morningRemainMinute) {
                    morningRemainMinute = morningRemainMinute - talkList.get(i).getMinute();
                    talkList.get(i).setBookedOrNot(true);
                    talkList.get(i).setStartTime(sdf.format(calendar.getTime()));
                    sessionData.add(talkList.get(i));
                    calendar.add(Calendar.MINUTE, talkList.get(i).getMinute());
                    // System.out.println("morning out " + i);
                }
                if (morningRemainMinute < talkList.get(i).getMinute()) {
                    // System.out.println("morning out break" + i);
                    ConferenceTalk lunchTalk = new ConferenceTalk();
                    lunchTalk.setStartTime("12:00PM");
                    lunchTalk.setMinute(60);
                    lunchTalk.setConferenceSubject("LUNCH-TIME....!! ");
                    sessionData.add(lunchTalk);
                    calendar.set(Calendar.HOUR, 1);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.AM_PM, Calendar.PM);
                    break;
                }
                if (morningRemainMinute > 0) {
                    continue;
                }
                if (morningRemainMinute <= 0) {
                    break;
                }
            }
        }
        for (int i = 0; i < talkList.size(); i++) {
            if (!talkList.get(i).isBookedOrNot()) {
                if (talkList.get(i).getMinute() < afterNoonRemainMinute) {
                    afterNoonRemainMinute = afterNoonRemainMinute - talkList.get(i).getMinute();
                    talkList.get(i).setBookedOrNot(true);

                    talkList.get(i).setStartTime(sdf.format(calendar.getTime()));
                    sessionData.add(talkList.get(i));
                    calendar.add(Calendar.MINUTE, talkList.get(i).getMinute());
                }
                if (afterNoonRemainMinute < talkList.get(i).getMinute()) {
                    ConferenceTalk snackTalk = new ConferenceTalk();
                    snackTalk.setStartTime("17:00PM");
                    snackTalk.setMinute(60);
                    snackTalk.setConferenceSubject("SNACK-TIME....!! ");
                    sessionData.add(snackTalk);
                    break;
                }
                if (afterNoonRemainMinute > 0) {
                    continue;
                }
                if (afterNoonRemainMinute <= 0) {
                    break;
                }
            }
        }


    }


    @Override
    public void displayData() {
        System.out.println("TRACK");
        for (int i = 0; i < sessionData.size(); i++) {
            System.out.println(sessionData.get(i).getStartTime() + "   " + sessionData.get(i).getConferenceSubject() + "" + sessionData.get(i).getMinute() + "min");
        }
    }
}
