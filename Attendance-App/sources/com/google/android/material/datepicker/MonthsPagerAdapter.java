package com.google.android.material.datepicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.C1087R;
import com.google.android.material.datepicker.MaterialCalendar;

class MonthsPagerAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final CalendarConstraints calendarConstraints;
    private final DateSelector<?> dateSelector;
    private final DayViewDecorator dayViewDecorator;
    private final int itemHeight;
    /* access modifiers changed from: private */
    public final MaterialCalendar.OnDayClickListener onDayClickListener;

    MonthsPagerAdapter(Context context, DateSelector<?> dateSelector2, CalendarConstraints calendarConstraints2, DayViewDecorator dayViewDecorator2, MaterialCalendar.OnDayClickListener onDayClickListener2) {
        Month firstPage = calendarConstraints2.getStart();
        Month lastPage = calendarConstraints2.getEnd();
        Month currentPage = calendarConstraints2.getOpenAt();
        if (firstPage.compareTo(currentPage) > 0) {
            throw new IllegalArgumentException("firstPage cannot be after currentPage");
        } else if (currentPage.compareTo(lastPage) <= 0) {
            this.itemHeight = (MonthAdapter.MAXIMUM_WEEKS * MaterialCalendar.getDayHeight(context)) + (MaterialDatePicker.isFullscreen(context) ? MaterialCalendar.getDayHeight(context) : 0);
            this.calendarConstraints = calendarConstraints2;
            this.dateSelector = dateSelector2;
            this.dayViewDecorator = dayViewDecorator2;
            this.onDayClickListener = onDayClickListener2;
            setHasStableIds(true);
        } else {
            throw new IllegalArgumentException("currentPage cannot be after lastPage");
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final MaterialCalendarGridView monthGrid;
        final TextView monthTitle;

        ViewHolder(LinearLayout container, boolean showLabel) {
            super(container);
            TextView textView = (TextView) container.findViewById(C1087R.C1090id.month_title);
            this.monthTitle = textView;
            ViewCompat.setAccessibilityHeading(textView, true);
            this.monthGrid = (MaterialCalendarGridView) container.findViewById(C1087R.C1090id.month_grid);
            if (!showLabel) {
                textView.setVisibility(8);
            }
        }
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LinearLayout container = (LinearLayout) LayoutInflater.from(viewGroup.getContext()).inflate(C1087R.C1092layout.mtrl_calendar_month_labeled, viewGroup, false);
        if (!MaterialDatePicker.isFullscreen(viewGroup.getContext())) {
            return new ViewHolder(container, false);
        }
        container.setLayoutParams(new RecyclerView.LayoutParams(-1, this.itemHeight));
        return new ViewHolder(container, true);
    }

    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        Month month = this.calendarConstraints.getStart().monthsLater(position);
        viewHolder.monthTitle.setText(month.getLongName());
        final MaterialCalendarGridView monthGrid = (MaterialCalendarGridView) viewHolder.monthGrid.findViewById(C1087R.C1090id.month_grid);
        if (monthGrid.getAdapter() == null || !month.equals(monthGrid.getAdapter().month)) {
            MonthAdapter monthAdapter = new MonthAdapter(month, this.dateSelector, this.calendarConstraints, this.dayViewDecorator);
            monthGrid.setNumColumns(month.daysInWeek);
            monthGrid.setAdapter((ListAdapter) monthAdapter);
        } else {
            monthGrid.invalidate();
            monthGrid.getAdapter().updateSelectedStates(monthGrid);
        }
        monthGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (monthGrid.getAdapter().withinMonth(position)) {
                    MonthsPagerAdapter.this.onDayClickListener.onDayClick(monthGrid.getAdapter().getItem(position).longValue());
                }
            }
        });
    }

    public long getItemId(int position) {
        return this.calendarConstraints.getStart().monthsLater(position).getStableId();
    }

    public int getItemCount() {
        return this.calendarConstraints.getMonthSpan();
    }

    /* access modifiers changed from: package-private */
    public CharSequence getPageTitle(int position) {
        return getPageMonth(position).getLongName();
    }

    /* access modifiers changed from: package-private */
    public Month getPageMonth(int position) {
        return this.calendarConstraints.getStart().monthsLater(position);
    }

    /* access modifiers changed from: package-private */
    public int getPosition(Month month) {
        return this.calendarConstraints.getStart().monthsUntil(month);
    }
}
