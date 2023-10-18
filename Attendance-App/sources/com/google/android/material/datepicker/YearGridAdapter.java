package com.google.android.material.datepicker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.C1087R;
import com.google.android.material.datepicker.MaterialCalendar;
import java.util.Calendar;
import java.util.Locale;

class YearGridAdapter extends RecyclerView.Adapter<ViewHolder> {
    /* access modifiers changed from: private */
    public final MaterialCalendar<?> materialCalendar;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textView;

        ViewHolder(TextView view) {
            super(view);
            this.textView = view;
        }
    }

    YearGridAdapter(MaterialCalendar<?> materialCalendar2) {
        this.materialCalendar = materialCalendar2;
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ViewHolder((TextView) LayoutInflater.from(viewGroup.getContext()).inflate(C1087R.C1092layout.mtrl_calendar_year, viewGroup, false));
    }

    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        int year = getYearForPosition(position);
        viewHolder.textView.setText(String.format(Locale.getDefault(), TimeModel.NUMBER_FORMAT, new Object[]{Integer.valueOf(year)}));
        viewHolder.textView.setContentDescription(DateStrings.getYearContentDescription(viewHolder.textView.getContext(), year));
        CalendarStyle styles = this.materialCalendar.getCalendarStyle();
        Calendar calendar = UtcDates.getTodayCalendar();
        CalendarItemStyle style = calendar.get(1) == year ? styles.todayYear : styles.year;
        for (Long day : this.materialCalendar.getDateSelector().getSelectedDays()) {
            calendar.setTimeInMillis(day.longValue());
            if (calendar.get(1) == year) {
                style = styles.selectedYear;
            }
        }
        style.styleItem(viewHolder.textView);
        viewHolder.textView.setOnClickListener(createYearClickListener(year));
    }

    private View.OnClickListener createYearClickListener(final int year) {
        return new View.OnClickListener() {
            public void onClick(View view) {
                YearGridAdapter.this.materialCalendar.setCurrentMonth(YearGridAdapter.this.materialCalendar.getCalendarConstraints().clamp(Month.create(year, YearGridAdapter.this.materialCalendar.getCurrentMonth().month)));
                YearGridAdapter.this.materialCalendar.setSelector(MaterialCalendar.CalendarSelector.DAY);
            }
        };
    }

    public int getItemCount() {
        return this.materialCalendar.getCalendarConstraints().getYearSpan();
    }

    /* access modifiers changed from: package-private */
    public int getPositionForYear(int year) {
        return year - this.materialCalendar.getCalendarConstraints().getStart().year;
    }

    /* access modifiers changed from: package-private */
    public int getYearForPosition(int position) {
        return this.materialCalendar.getCalendarConstraints().getStart().year + position;
    }
}
