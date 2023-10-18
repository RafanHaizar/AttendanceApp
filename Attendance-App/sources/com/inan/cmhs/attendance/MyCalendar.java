package com.inan.cmhs.attendance;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;

public class MyCalendar extends DialogFragment {
    Calendar calendar = Calendar.getInstance();
    public OnCalendarClickListener onCalendarClickListener;

    public interface OnCalendarClickListener {
        void OnClickCalendar(int i, int i2, int i3);
    }

    public void setOnCalendarClickListener(OnCalendarClickListener onCalendarClickListener2) {
        this.onCalendarClickListener = onCalendarClickListener2;
    }

    /* access modifiers changed from: package-private */
    public void SetData(int year, int month, int day) {
        this.calendar.set(1, year);
        this.calendar.set(2, month);
        this.calendar.set(5, day);
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), new MyCalendar$$ExternalSyntheticLambda0(this), this.calendar.get(1), this.calendar.get(2), this.calendar.get(5));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$onCreateDialog$0$com-inan-cmhs-attendance-MyCalendar  reason: not valid java name */
    public /* synthetic */ void m1328lambda$onCreateDialog$0$cominancmhsattendanceMyCalendar(DatePicker view, int year, int month, int dayOfMonth) {
        this.onCalendarClickListener.OnClickCalendar(year, month, dayOfMonth);
    }

    /* access modifiers changed from: package-private */
    public String getData() {
        return DateFormat.format("dd.MM.yyyy", this.calendar).toString();
    }
}
