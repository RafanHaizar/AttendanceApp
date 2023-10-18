package com.google.android.material.datepicker;

import android.content.Context;
import android.os.Build;
import android.text.format.DateUtils;
import androidx.core.util.Pair;
import com.google.android.material.C1087R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

class DateStrings {
    private DateStrings() {
    }

    static String getYearMonth(long timeInMillis) {
        return DateUtils.formatDateTime((Context) null, timeInMillis, 8228);
    }

    static String getYearMonthDay(long timeInMillis) {
        return getYearMonthDay(timeInMillis, Locale.getDefault());
    }

    static String getYearMonthDay(long timeInMillis, Locale locale) {
        if (Build.VERSION.SDK_INT >= 24) {
            return UtcDates.getYearAbbrMonthDayFormat(locale).format(new Date(timeInMillis));
        }
        return UtcDates.getMediumFormat(locale).format(new Date(timeInMillis));
    }

    static String getMonthDay(long timeInMillis) {
        return getMonthDay(timeInMillis, Locale.getDefault());
    }

    static String getMonthDay(long timeInMillis, Locale locale) {
        if (Build.VERSION.SDK_INT >= 24) {
            return UtcDates.getAbbrMonthDayFormat(locale).format(new Date(timeInMillis));
        }
        return UtcDates.getMediumNoYear(locale).format(new Date(timeInMillis));
    }

    static String getMonthDayOfWeekDay(long timeInMillis) {
        return getMonthDayOfWeekDay(timeInMillis, Locale.getDefault());
    }

    static String getMonthDayOfWeekDay(long timeInMillis, Locale locale) {
        if (Build.VERSION.SDK_INT >= 24) {
            return UtcDates.getAbbrMonthWeekdayDayFormat(locale).format(new Date(timeInMillis));
        }
        return UtcDates.getFullFormat(locale).format(new Date(timeInMillis));
    }

    static String getYearMonthDayOfWeekDay(long timeInMillis) {
        return getYearMonthDayOfWeekDay(timeInMillis, Locale.getDefault());
    }

    static String getYearMonthDayOfWeekDay(long timeInMillis, Locale locale) {
        if (Build.VERSION.SDK_INT >= 24) {
            return UtcDates.getYearAbbrMonthWeekdayDayFormat(locale).format(new Date(timeInMillis));
        }
        return UtcDates.getFullFormat(locale).format(new Date(timeInMillis));
    }

    static String getOptionalYearMonthDayOfWeekDay(long timeInMillis) {
        if (isDateWithinCurrentYear(timeInMillis)) {
            return getMonthDayOfWeekDay(timeInMillis);
        }
        return getYearMonthDayOfWeekDay(timeInMillis);
    }

    static String getDateString(long timeInMillis) {
        return getDateString(timeInMillis, (SimpleDateFormat) null);
    }

    static String getDateString(long timeInMillis, SimpleDateFormat userDefinedDateFormat) {
        if (userDefinedDateFormat != null) {
            return userDefinedDateFormat.format(new Date(timeInMillis));
        }
        if (isDateWithinCurrentYear(timeInMillis)) {
            return getMonthDay(timeInMillis);
        }
        return getYearMonthDay(timeInMillis);
    }

    private static boolean isDateWithinCurrentYear(long timeInMillis) {
        Calendar currentCalendar = UtcDates.getTodayCalendar();
        Calendar calendarDate = UtcDates.getUtcCalendar();
        calendarDate.setTimeInMillis(timeInMillis);
        return currentCalendar.get(1) == calendarDate.get(1);
    }

    static Pair<String, String> getDateRangeString(Long start, Long end) {
        return getDateRangeString(start, end, (SimpleDateFormat) null);
    }

    static Pair<String, String> getDateRangeString(Long start, Long end, SimpleDateFormat userDefinedDateFormat) {
        if (start == null && end == null) {
            return Pair.create(null, null);
        }
        if (start == null) {
            return Pair.create(null, getDateString(end.longValue(), userDefinedDateFormat));
        }
        if (end == null) {
            return Pair.create(getDateString(start.longValue(), userDefinedDateFormat), null);
        }
        Calendar currentCalendar = UtcDates.getTodayCalendar();
        Calendar startCalendar = UtcDates.getUtcCalendar();
        startCalendar.setTimeInMillis(start.longValue());
        Calendar endCalendar = UtcDates.getUtcCalendar();
        endCalendar.setTimeInMillis(end.longValue());
        if (userDefinedDateFormat != null) {
            return Pair.create(userDefinedDateFormat.format(new Date(start.longValue())), userDefinedDateFormat.format(new Date(end.longValue())));
        } else if (startCalendar.get(1) != endCalendar.get(1)) {
            return Pair.create(getYearMonthDay(start.longValue(), Locale.getDefault()), getYearMonthDay(end.longValue(), Locale.getDefault()));
        } else {
            if (startCalendar.get(1) == currentCalendar.get(1)) {
                return Pair.create(getMonthDay(start.longValue(), Locale.getDefault()), getMonthDay(end.longValue(), Locale.getDefault()));
            }
            return Pair.create(getMonthDay(start.longValue(), Locale.getDefault()), getYearMonthDay(end.longValue(), Locale.getDefault()));
        }
    }

    static String getDayContentDescription(Context context, long dayInMillis, boolean isToday, boolean isStartOfRange, boolean isEndOfRange) {
        String dayContentDescription = getOptionalYearMonthDayOfWeekDay(dayInMillis);
        if (isToday) {
            dayContentDescription = String.format(context.getString(C1087R.string.mtrl_picker_today_description), new Object[]{dayContentDescription});
        }
        if (isStartOfRange) {
            return String.format(context.getString(C1087R.string.mtrl_picker_start_date_description), new Object[]{dayContentDescription});
        } else if (!isEndOfRange) {
            return dayContentDescription;
        } else {
            return String.format(context.getString(C1087R.string.mtrl_picker_end_date_description), new Object[]{dayContentDescription});
        }
    }

    static String getYearContentDescription(Context context, int year) {
        if (UtcDates.getTodayCalendar().get(1) == year) {
            return String.format(context.getString(C1087R.string.mtrl_picker_navigate_to_current_year_description), new Object[]{Integer.valueOf(year)});
        }
        return String.format(context.getString(C1087R.string.mtrl_picker_navigate_to_year_description), new Object[]{Integer.valueOf(year)});
    }
}
