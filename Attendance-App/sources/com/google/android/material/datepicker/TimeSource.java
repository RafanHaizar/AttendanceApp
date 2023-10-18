package com.google.android.material.datepicker;

import java.util.Calendar;
import java.util.TimeZone;

class TimeSource {
    private static final TimeSource SYSTEM_TIME_SOURCE = new TimeSource((Long) null, (TimeZone) null);
    private final Long fixedTimeMs;
    private final TimeZone fixedTimeZone;

    private TimeSource(Long fixedTimeMs2, TimeZone fixedTimeZone2) {
        this.fixedTimeMs = fixedTimeMs2;
        this.fixedTimeZone = fixedTimeZone2;
    }

    static TimeSource system() {
        return SYSTEM_TIME_SOURCE;
    }

    static TimeSource fixed(long epochMs, TimeZone timeZone) {
        return new TimeSource(Long.valueOf(epochMs), timeZone);
    }

    static TimeSource fixed(long epochMs) {
        return new TimeSource(Long.valueOf(epochMs), (TimeZone) null);
    }

    /* access modifiers changed from: package-private */
    public Calendar now() {
        return now(this.fixedTimeZone);
    }

    /* access modifiers changed from: package-private */
    public Calendar now(TimeZone timeZone) {
        Calendar calendar = timeZone == null ? Calendar.getInstance() : Calendar.getInstance(timeZone);
        Long l = this.fixedTimeMs;
        if (l != null) {
            calendar.setTimeInMillis(l.longValue());
        }
        return calendar;
    }
}
