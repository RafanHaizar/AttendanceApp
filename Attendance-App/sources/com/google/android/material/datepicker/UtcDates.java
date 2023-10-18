package com.google.android.material.datepicker;

import android.content.res.Resources;
import android.icu.text.DateFormat;
import com.google.android.material.C1087R;
import com.itextpdf.svg.SvgConstants;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicReference;

class UtcDates {
    static final String UTC = "UTC";
    static AtomicReference<TimeSource> timeSourceRef = new AtomicReference<>();

    static void setTimeSource(TimeSource timeSource) {
        timeSourceRef.set(timeSource);
    }

    static TimeSource getTimeSource() {
        TimeSource timeSource = timeSourceRef.get();
        return timeSource == null ? TimeSource.system() : timeSource;
    }

    private UtcDates() {
    }

    private static TimeZone getTimeZone() {
        return TimeZone.getTimeZone(UTC);
    }

    private static android.icu.util.TimeZone getUtcAndroidTimeZone() {
        return android.icu.util.TimeZone.getTimeZone(UTC);
    }

    static Calendar getTodayCalendar() {
        Calendar today = getTimeSource().now();
        today.set(11, 0);
        today.set(12, 0);
        today.set(13, 0);
        today.set(14, 0);
        today.setTimeZone(getTimeZone());
        return today;
    }

    static Calendar getUtcCalendar() {
        return getUtcCalendarOf((Calendar) null);
    }

    static Calendar getUtcCalendarOf(Calendar rawCalendar) {
        Calendar utc = Calendar.getInstance(getTimeZone());
        if (rawCalendar == null) {
            utc.clear();
        } else {
            utc.setTimeInMillis(rawCalendar.getTimeInMillis());
        }
        return utc;
    }

    static Calendar getDayCopy(Calendar rawCalendar) {
        Calendar rawCalendarInUtc = getUtcCalendarOf(rawCalendar);
        Calendar utcCalendar = getUtcCalendar();
        utcCalendar.set(rawCalendarInUtc.get(1), rawCalendarInUtc.get(2), rawCalendarInUtc.get(5));
        return utcCalendar;
    }

    static long canonicalYearMonthDay(long rawDate) {
        Calendar rawCalendar = getUtcCalendar();
        rawCalendar.setTimeInMillis(rawDate);
        return getDayCopy(rawCalendar).getTimeInMillis();
    }

    private static DateFormat getAndroidFormat(String pattern, Locale locale) {
        DateFormat format = DateFormat.getInstanceForSkeleton(pattern, locale);
        format.setTimeZone(getUtcAndroidTimeZone());
        return format;
    }

    private static java.text.DateFormat getFormat(int style, Locale locale) {
        java.text.DateFormat format = java.text.DateFormat.getDateInstance(style, locale);
        format.setTimeZone(getTimeZone());
        return format;
    }

    static SimpleDateFormat getDefaultTextInputFormat() {
        SimpleDateFormat format = new SimpleDateFormat(((SimpleDateFormat) java.text.DateFormat.getDateInstance(3, Locale.getDefault())).toPattern().replaceAll("\\s+", ""), Locale.getDefault());
        format.setTimeZone(getTimeZone());
        format.setLenient(false);
        return format;
    }

    static String getDefaultTextInputHint(Resources res, SimpleDateFormat format) {
        String formatHint = format.toPattern();
        String yearChar = res.getString(C1087R.string.mtrl_picker_text_input_year_abbr);
        String monthChar = res.getString(C1087R.string.mtrl_picker_text_input_month_abbr);
        String dayChar = res.getString(C1087R.string.mtrl_picker_text_input_day_abbr);
        if (formatHint.replaceAll("[^y]", "").length() == 1) {
            formatHint = formatHint.replace(SvgConstants.Attributes.f1644Y, "yyyy");
        }
        return formatHint.replace(SvgConstants.Attributes.f1634D, dayChar).replace(SvgConstants.Attributes.PATH_DATA_MOVE_TO, monthChar).replace(SvgConstants.Attributes.f1644Y, yearChar);
    }

    static SimpleDateFormat getSimpleFormat(String pattern) {
        return getSimpleFormat(pattern, Locale.getDefault());
    }

    private static SimpleDateFormat getSimpleFormat(String pattern, Locale locale) {
        SimpleDateFormat format = new SimpleDateFormat(pattern, locale);
        format.setTimeZone(getTimeZone());
        return format;
    }

    static DateFormat getYearAbbrMonthDayFormat(Locale locale) {
        return getAndroidFormat("yMMMd", locale);
    }

    static DateFormat getAbbrMonthDayFormat(Locale locale) {
        return getAndroidFormat("MMMd", locale);
    }

    static DateFormat getAbbrMonthWeekdayDayFormat(Locale locale) {
        return getAndroidFormat("MMMEd", locale);
    }

    static DateFormat getYearAbbrMonthWeekdayDayFormat(Locale locale) {
        return getAndroidFormat("yMMMEd", locale);
    }

    static java.text.DateFormat getMediumFormat() {
        return getMediumFormat(Locale.getDefault());
    }

    static java.text.DateFormat getMediumFormat(Locale locale) {
        return getFormat(2, locale);
    }

    static java.text.DateFormat getMediumNoYear() {
        return getMediumNoYear(Locale.getDefault());
    }

    static java.text.DateFormat getMediumNoYear(Locale locale) {
        SimpleDateFormat format = (SimpleDateFormat) getMediumFormat(locale);
        format.applyPattern(removeYearFromDateFormatPattern(format.toPattern()));
        return format;
    }

    static java.text.DateFormat getFullFormat() {
        return getFullFormat(Locale.getDefault());
    }

    static java.text.DateFormat getFullFormat(Locale locale) {
        return getFormat(0, locale);
    }

    private static String removeYearFromDateFormatPattern(String pattern) {
        int yearPosition = findCharactersInDateFormatPattern(pattern, "yY", 1, 0);
        if (yearPosition >= pattern.length()) {
            return pattern;
        }
        String monthDayCharacters = "EMd";
        int yearEndPosition = findCharactersInDateFormatPattern(pattern, monthDayCharacters, 1, yearPosition);
        if (yearEndPosition < pattern.length()) {
            monthDayCharacters = monthDayCharacters + ",";
        }
        return pattern.replace(pattern.substring(findCharactersInDateFormatPattern(pattern, monthDayCharacters, -1, yearPosition) + 1, yearEndPosition), " ").trim();
    }

    private static int findCharactersInDateFormatPattern(String pattern, String characterSequence, int increment, int initialPosition) {
        int position;
        int position2 = initialPosition;
        while (position >= 0 && position < pattern.length() && characterSequence.indexOf(pattern.charAt(position)) == -1) {
            if (pattern.charAt(position) == '\'') {
                position += increment;
                while (position >= 0 && position < pattern.length() && pattern.charAt(position) != '\'') {
                    position += increment;
                }
            }
            position2 = position + increment;
        }
        return position;
    }
}
