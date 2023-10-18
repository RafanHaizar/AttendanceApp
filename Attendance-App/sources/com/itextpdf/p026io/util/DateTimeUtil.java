package com.itextpdf.p026io.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/* renamed from: com.itextpdf.io.util.DateTimeUtil */
public final class DateTimeUtil {
    public static double getUtcMillisFromEpoch(Calendar calendar) {
        if (calendar == null) {
            calendar = new GregorianCalendar();
        }
        return (double) calendar.getTimeInMillis();
    }

    public static Calendar getCurrentTimeCalendar() {
        return new GregorianCalendar();
    }

    public static Date getCurrentTimeDate() {
        return new Date();
    }

    public static Calendar addDaysToCalendar(Calendar calendar, int days) {
        calendar.add(6, days);
        return calendar;
    }

    public static Date addDaysToDate(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(6, days);
        return cal.getTime();
    }

    public static Date parseSimpleFormat(String date, String format) {
        try {
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
