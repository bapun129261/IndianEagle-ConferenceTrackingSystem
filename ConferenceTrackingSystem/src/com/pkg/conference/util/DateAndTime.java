package com.pkg.conference.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateAndTime {
    public static final int MORNING_SESSION = 180;
    public static final int AFTER_NOON_SESSION = 240;
    private static Calendar calender = Calendar.getInstance();

    public static Calendar getCalender() {
        return calender;
    }

    static SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");



    public static String getHourAndMinute(int conferenceRequireMin) {
        String time;
        time = dateFormat.format(calender.getTime());
        calender.add(Calendar.MINUTE, conferenceRequireMin);
        return time;
    }
}
