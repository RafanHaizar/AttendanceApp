package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.widget.BaseAdapter;
import android.widget.TextView;
import androidx.core.util.Pair;
import java.util.Collection;

class MonthAdapter extends BaseAdapter {
    private static final int MAXIMUM_GRID_CELLS = ((UtcDates.getUtcCalendar().getMaximum(5) + UtcDates.getUtcCalendar().getMaximum(7)) - 1);
    static final int MAXIMUM_WEEKS = UtcDates.getUtcCalendar().getMaximum(4);
    private static final int NO_DAY_NUMBER = -1;
    final CalendarConstraints calendarConstraints;
    CalendarStyle calendarStyle;
    final DateSelector<?> dateSelector;
    final DayViewDecorator dayViewDecorator;
    final Month month;
    private Collection<Long> previouslySelectedDates;

    MonthAdapter(Month month2, DateSelector<?> dateSelector2, CalendarConstraints calendarConstraints2, DayViewDecorator dayViewDecorator2) {
        this.month = month2;
        this.dateSelector = dateSelector2;
        this.calendarConstraints = calendarConstraints2;
        this.dayViewDecorator = dayViewDecorator2;
        this.previouslySelectedDates = dateSelector2.getSelectedDays();
    }

    public boolean hasStableIds() {
        return true;
    }

    public Long getItem(int position) {
        if (position < firstPositionInMonth() || position > lastPositionInMonth()) {
            return null;
        }
        return Long.valueOf(this.month.getDay(positionToDay(position)));
    }

    public long getItemId(int position) {
        return (long) (position / this.month.daysInWeek);
    }

    public int getCount() {
        return MAXIMUM_GRID_CELLS;
    }

    /* JADX WARNING: type inference failed for: r3v4, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.widget.TextView getView(int r9, android.view.View r10, android.view.ViewGroup r11) {
        /*
            r8 = this;
            android.content.Context r0 = r11.getContext()
            r8.initializeStyles(r0)
            r0 = r10
            android.widget.TextView r0 = (android.widget.TextView) r0
            r1 = 0
            if (r10 != 0) goto L_0x001e
            android.content.Context r2 = r11.getContext()
            android.view.LayoutInflater r2 = android.view.LayoutInflater.from(r2)
            int r3 = com.google.android.material.C1087R.C1092layout.mtrl_calendar_day
            android.view.View r3 = r2.inflate(r3, r11, r1)
            r0 = r3
            android.widget.TextView r0 = (android.widget.TextView) r0
        L_0x001e:
            int r2 = r8.firstPositionInMonth()
            int r2 = r9 - r2
            r3 = -1
            if (r2 < 0) goto L_0x0058
            com.google.android.material.datepicker.Month r4 = r8.month
            int r4 = r4.daysInMonth
            if (r2 < r4) goto L_0x002e
            goto L_0x0058
        L_0x002e:
            int r3 = r2 + 1
            com.google.android.material.datepicker.Month r4 = r8.month
            r0.setTag(r4)
            android.content.res.Resources r4 = r0.getResources()
            android.content.res.Configuration r4 = r4.getConfiguration()
            java.util.Locale r4 = r4.locale
            r5 = 1
            java.lang.Object[] r6 = new java.lang.Object[r5]
            java.lang.Integer r7 = java.lang.Integer.valueOf(r3)
            r6[r1] = r7
            java.lang.String r7 = "%d"
            java.lang.String r6 = java.lang.String.format(r4, r7, r6)
            r0.setText(r6)
            r0.setVisibility(r1)
            r0.setEnabled(r5)
            goto L_0x0060
        L_0x0058:
            r4 = 8
            r0.setVisibility(r4)
            r0.setEnabled(r1)
        L_0x0060:
            java.lang.Long r1 = r8.getItem((int) r9)
            if (r1 != 0) goto L_0x0067
            return r0
        L_0x0067:
            long r4 = r1.longValue()
            r8.updateSelectedState(r0, r4, r3)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.datepicker.MonthAdapter.getView(int, android.view.View, android.view.ViewGroup):android.widget.TextView");
    }

    public void updateSelectedStates(MaterialCalendarGridView monthGrid) {
        for (Long date : this.previouslySelectedDates) {
            updateSelectedStateForDate(monthGrid, date.longValue());
        }
        DateSelector<?> dateSelector2 = this.dateSelector;
        if (dateSelector2 != null) {
            for (Long date2 : dateSelector2.getSelectedDays()) {
                updateSelectedStateForDate(monthGrid, date2.longValue());
            }
            this.previouslySelectedDates = this.dateSelector.getSelectedDays();
        }
    }

    private void updateSelectedStateForDate(MaterialCalendarGridView monthGrid, long date) {
        if (Month.create(date).equals(this.month)) {
            int day = this.month.getDayOfMonth(date);
            updateSelectedState((TextView) monthGrid.getChildAt(monthGrid.getAdapter().dayToPosition(day) - monthGrid.getFirstVisiblePosition()), date, day);
        }
    }

    private void updateSelectedState(TextView dayTextView, long date, int dayNumber) {
        boolean selected;
        CalendarItemStyle style;
        TextView textView = dayTextView;
        long j = date;
        if (textView != null) {
            Context context = dayTextView.getContext();
            String contentDescription = getDayContentDescription(context, j);
            textView.setContentDescription(contentDescription);
            boolean valid = this.calendarConstraints.getDateValidator().isValid(j);
            if (valid) {
                textView.setEnabled(true);
                boolean selected2 = isSelected(j);
                textView.setSelected(selected2);
                if (selected2) {
                    selected = selected2;
                    style = this.calendarStyle.selectedDay;
                } else if (isToday(j)) {
                    selected = selected2;
                    style = this.calendarStyle.todayDay;
                } else {
                    selected = selected2;
                    style = this.calendarStyle.day;
                }
            } else {
                textView.setEnabled(false);
                selected = false;
                style = this.calendarStyle.invalidDay;
            }
            if (this.dayViewDecorator == null || dayNumber == -1) {
                style.styleItem(textView);
                return;
            }
            int year = this.month.year;
            int month2 = this.month.month;
            Context context2 = context;
            int i = year;
            int i2 = month2;
            int month3 = month2;
            int month4 = dayNumber;
            int year2 = year;
            boolean z = valid;
            ColorStateList backgroundColorOverride = this.dayViewDecorator.getBackgroundColor(context2, i, i2, month4, z, selected);
            style.styleItem(textView, backgroundColorOverride);
            int i3 = year2;
            int i4 = month3;
            ColorStateList colorStateList = backgroundColorOverride;
            Drawable drawableLeft = this.dayViewDecorator.getCompoundDrawableLeft(context2, i3, i4, month4, z, selected);
            boolean z2 = selected;
            Drawable drawableTop = this.dayViewDecorator.getCompoundDrawableTop(context2, i3, i4, month4, z, z2);
            CalendarItemStyle style2 = style;
            Drawable drawableRight = this.dayViewDecorator.getCompoundDrawableRight(context2, i3, i4, month4, z, z2);
            Drawable drawableBottom = this.dayViewDecorator.getCompoundDrawableBottom(context2, i3, i4, month4, z, selected);
            textView.setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom);
            Drawable drawable = drawableBottom;
            CalendarItemStyle calendarItemStyle = style2;
            Drawable drawable2 = drawableRight;
            textView.setContentDescription(this.dayViewDecorator.getContentDescription(context2, i3, i4, month4, z, selected, contentDescription));
        }
    }

    private String getDayContentDescription(Context context, long date) {
        return DateStrings.getDayContentDescription(context, date, isToday(date), isStartOfRange(date), isEndOfRange(date));
    }

    private boolean isToday(long date) {
        return UtcDates.getTodayCalendar().getTimeInMillis() == date;
    }

    /* access modifiers changed from: package-private */
    public boolean isStartOfRange(long date) {
        for (Pair<Long, Long> range : this.dateSelector.getSelectedRanges()) {
            if (range.first != null && ((Long) range.first).longValue() == date) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean isEndOfRange(long date) {
        for (Pair<Long, Long> range : this.dateSelector.getSelectedRanges()) {
            if (range.second != null && ((Long) range.second).longValue() == date) {
                return true;
            }
        }
        return false;
    }

    private boolean isSelected(long date) {
        for (Long longValue : this.dateSelector.getSelectedDays()) {
            if (UtcDates.canonicalYearMonthDay(date) == UtcDates.canonicalYearMonthDay(longValue.longValue())) {
                return true;
            }
        }
        return false;
    }

    private void initializeStyles(Context context) {
        if (this.calendarStyle == null) {
            this.calendarStyle = new CalendarStyle(context);
        }
    }

    /* access modifiers changed from: package-private */
    public int firstPositionInMonth() {
        return this.month.daysFromStartOfWeekToFirstOfMonth(this.calendarConstraints.getFirstDayOfWeek());
    }

    /* access modifiers changed from: package-private */
    public int lastPositionInMonth() {
        return (firstPositionInMonth() + this.month.daysInMonth) - 1;
    }

    /* access modifiers changed from: package-private */
    public int positionToDay(int position) {
        return (position - firstPositionInMonth()) + 1;
    }

    /* access modifiers changed from: package-private */
    public int dayToPosition(int day) {
        return firstPositionInMonth() + (day - 1);
    }

    /* access modifiers changed from: package-private */
    public boolean withinMonth(int position) {
        return position >= firstPositionInMonth() && position <= lastPositionInMonth();
    }

    /* access modifiers changed from: package-private */
    public boolean isFirstInRow(int position) {
        return position % this.month.daysInWeek == 0;
    }

    /* access modifiers changed from: package-private */
    public boolean isLastInRow(int position) {
        return (position + 1) % this.month.daysInWeek == 0;
    }
}
