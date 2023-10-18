package com.google.android.material.datepicker;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import com.google.android.material.C1087R;
import com.google.android.material.internal.TextWatcherAdapter;
import com.google.android.material.textfield.TextInputLayout;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import kotlin.text.Typography;

abstract class DateFormatTextWatcher extends TextWatcherAdapter {
    private static final int VALIDATION_DELAY = 1000;
    private final CalendarConstraints constraints;
    private final DateFormat dateFormat;
    private final String outOfRange;
    private final Runnable setErrorCallback;
    private Runnable setRangeErrorCallback;
    private final TextInputLayout textInputLayout;

    /* access modifiers changed from: package-private */
    public abstract void onValidDate(Long l);

    DateFormatTextWatcher(String formatHint, DateFormat dateFormat2, TextInputLayout textInputLayout2, CalendarConstraints constraints2) {
        this.dateFormat = dateFormat2;
        this.textInputLayout = textInputLayout2;
        this.constraints = constraints2;
        this.outOfRange = textInputLayout2.getContext().getString(C1087R.string.mtrl_picker_out_of_range);
        this.setErrorCallback = new DateFormatTextWatcher$$ExternalSyntheticLambda0(this, formatHint);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$com-google-android-material-datepicker-DateFormatTextWatcher */
    public /* synthetic */ void mo22168x5657fb8e(String formatHint) {
        TextInputLayout textLayout = this.textInputLayout;
        DateFormat df = this.dateFormat;
        Context context = textLayout.getContext();
        String invalidFormat = context.getString(C1087R.string.mtrl_picker_invalid_format);
        String useLine = String.format(context.getString(C1087R.string.mtrl_picker_invalid_format_use), new Object[]{sanitizeDateString(formatHint)});
        textLayout.setError(invalidFormat + "\n" + useLine + "\n" + String.format(context.getString(C1087R.string.mtrl_picker_invalid_format_example), new Object[]{sanitizeDateString(df.format(new Date(UtcDates.getTodayCalendar().getTimeInMillis())))}));
        onInvalidDate();
    }

    /* access modifiers changed from: package-private */
    public void onInvalidDate() {
    }

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.textInputLayout.removeCallbacks(this.setErrorCallback);
        this.textInputLayout.removeCallbacks(this.setRangeErrorCallback);
        this.textInputLayout.setError((CharSequence) null);
        onValidDate((Long) null);
        if (!TextUtils.isEmpty(s)) {
            try {
                Date date = this.dateFormat.parse(s.toString());
                this.textInputLayout.setError((CharSequence) null);
                long milliseconds = date.getTime();
                if (!this.constraints.getDateValidator().isValid(milliseconds) || !this.constraints.isWithinBounds(milliseconds)) {
                    Runnable createRangeErrorCallback = createRangeErrorCallback(milliseconds);
                    this.setRangeErrorCallback = createRangeErrorCallback;
                    runValidation(this.textInputLayout, createRangeErrorCallback);
                    return;
                }
                onValidDate(Long.valueOf(date.getTime()));
            } catch (ParseException e) {
                runValidation(this.textInputLayout, this.setErrorCallback);
            }
        }
    }

    private Runnable createRangeErrorCallback(long milliseconds) {
        return new DateFormatTextWatcher$$ExternalSyntheticLambda1(this, milliseconds);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$createRangeErrorCallback$1$com-google-android-material-datepicker-DateFormatTextWatcher */
    public /* synthetic */ void mo22167x14d77527(long milliseconds) {
        String dateString = DateStrings.getDateString(milliseconds);
        this.textInputLayout.setError(String.format(this.outOfRange, new Object[]{sanitizeDateString(dateString)}));
        onInvalidDate();
    }

    private String sanitizeDateString(String dateString) {
        return dateString.replace(' ', Typography.nbsp);
    }

    public void runValidation(View view, Runnable validation) {
        view.postDelayed(validation, 1000);
    }
}
