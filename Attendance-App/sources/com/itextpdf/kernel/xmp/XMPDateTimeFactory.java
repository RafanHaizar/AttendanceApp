package com.itextpdf.kernel.xmp;

import com.itextpdf.kernel.xmp.impl.XMPDateTimeImpl;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public final class XMPDateTimeFactory {
    private static final TimeZone UTC = TimeZone.getTimeZone("UTC");

    private XMPDateTimeFactory() {
    }

    public static XMPDateTime createFromCalendar(Calendar calendar) {
        return new XMPDateTimeImpl(calendar);
    }

    public static XMPDateTime create() {
        return new XMPDateTimeImpl();
    }

    public static XMPDateTime create(int year, int month, int day) {
        XMPDateTime dt = new XMPDateTimeImpl();
        dt.setYear(year);
        dt.setMonth(month);
        dt.setDay(day);
        return dt;
    }

    public static XMPDateTime create(int year, int month, int day, int hour, int minute, int second, int nanoSecond) {
        XMPDateTime dt = new XMPDateTimeImpl();
        dt.setYear(year);
        dt.setMonth(month);
        dt.setDay(day);
        dt.setHour(hour);
        dt.setMinute(minute);
        dt.setSecond(second);
        dt.setNanoSecond(nanoSecond);
        return dt;
    }

    public static XMPDateTime createFromISO8601(String strValue) throws XMPException {
        return new XMPDateTimeImpl(strValue);
    }

    public static XMPDateTime getCurrentDateTime() {
        return new XMPDateTimeImpl((Calendar) new GregorianCalendar());
    }

    public static XMPDateTime setLocalTimeZone(XMPDateTime dateTime) {
        Calendar cal = dateTime.getCalendar();
        cal.setTimeZone(TimeZone.getDefault());
        return new XMPDateTimeImpl(cal);
    }

    public static XMPDateTime convertToUTCTime(XMPDateTime dateTime) {
        long timeInMillis = dateTime.getCalendar().getTimeInMillis();
        GregorianCalendar cal = new GregorianCalendar(UTC);
        cal.setGregorianChange(new Date(Long.MIN_VALUE));
        cal.setTimeInMillis(timeInMillis);
        return new XMPDateTimeImpl((Calendar) cal);
    }

    public static XMPDateTime convertToLocalTime(XMPDateTime dateTime) {
        long timeInMillis = dateTime.getCalendar().getTimeInMillis();
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTimeInMillis(timeInMillis);
        return new XMPDateTimeImpl((Calendar) cal);
    }
}
