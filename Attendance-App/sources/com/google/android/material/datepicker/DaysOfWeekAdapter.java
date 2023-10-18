package com.google.android.material.datepicker;

import android.os.Build;
import android.widget.BaseAdapter;
import java.util.Calendar;

class DaysOfWeekAdapter extends BaseAdapter {
    private static final int CALENDAR_DAY_STYLE = (Build.VERSION.SDK_INT >= 26 ? 4 : 1);
    private static final int NARROW_FORMAT = 4;
    private final Calendar calendar;
    private final int daysInWeek;
    private final int firstDayOfWeek;

    public DaysOfWeekAdapter() {
        Calendar utcCalendar = UtcDates.getUtcCalendar();
        this.calendar = utcCalendar;
        this.daysInWeek = utcCalendar.getMaximum(7);
        this.firstDayOfWeek = utcCalendar.getFirstDayOfWeek();
    }

    public DaysOfWeekAdapter(int firstDayOfWeek2) {
        Calendar utcCalendar = UtcDates.getUtcCalendar();
        this.calendar = utcCalendar;
        this.daysInWeek = utcCalendar.getMaximum(7);
        this.firstDayOfWeek = firstDayOfWeek2;
    }

    public Integer getItem(int position) {
        if (position >= this.daysInWeek) {
            return null;
        }
        return Integer.valueOf(positionToDayOfWeek(position));
    }

    public long getItemId(int position) {
        return 0;
    }

    public int getCount() {
        return this.daysInWeek;
    }

    /* JADX WARNING: type inference failed for: r3v6, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View getView(int r10, android.view.View r11, android.view.ViewGroup r12) {
        /*
            r9 = this;
            r0 = r11
            android.widget.TextView r0 = (android.widget.TextView) r0
            r1 = 0
            if (r11 != 0) goto L_0x0017
            android.content.Context r2 = r12.getContext()
            android.view.LayoutInflater r2 = android.view.LayoutInflater.from(r2)
            int r3 = com.google.android.material.C1087R.C1092layout.mtrl_calendar_day_of_week
            android.view.View r3 = r2.inflate(r3, r12, r1)
            r0 = r3
            android.widget.TextView r0 = (android.widget.TextView) r0
        L_0x0017:
            java.util.Calendar r2 = r9.calendar
            int r3 = r9.positionToDayOfWeek(r10)
            r4 = 7
            r2.set(r4, r3)
            android.content.res.Resources r2 = r0.getResources()
            android.content.res.Configuration r2 = r2.getConfiguration()
            java.util.Locale r2 = r2.locale
            java.util.Calendar r3 = r9.calendar
            int r5 = CALENDAR_DAY_STYLE
            java.lang.String r3 = r3.getDisplayName(r4, r5, r2)
            r0.setText(r3)
            android.content.Context r3 = r12.getContext()
            int r5 = com.google.android.material.C1087R.string.mtrl_picker_day_of_week_column_header
            java.lang.String r3 = r3.getString(r5)
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]
            java.util.Calendar r6 = r9.calendar
            java.util.Locale r7 = java.util.Locale.getDefault()
            r8 = 2
            java.lang.String r4 = r6.getDisplayName(r4, r8, r7)
            r5[r1] = r4
            java.lang.String r1 = java.lang.String.format(r3, r5)
            r0.setContentDescription(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.datepicker.DaysOfWeekAdapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
    }

    private int positionToDayOfWeek(int position) {
        int dayConstant = this.firstDayOfWeek + position;
        int i = this.daysInWeek;
        if (dayConstant > i) {
            return dayConstant - i;
        }
        return dayConstant;
    }
}
