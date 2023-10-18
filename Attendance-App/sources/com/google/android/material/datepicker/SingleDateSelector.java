package com.google.android.material.datepicker;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.core.util.Pair;
import com.google.android.material.C1087R;
import com.google.android.material.datepicker.DateSelector;
import com.google.android.material.internal.ManufacturerUtils;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.textfield.TextInputLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

public class SingleDateSelector implements DateSelector<Long> {
    public static final Parcelable.Creator<SingleDateSelector> CREATOR = new Parcelable.Creator<SingleDateSelector>() {
        public SingleDateSelector createFromParcel(Parcel source) {
            SingleDateSelector singleDateSelector = new SingleDateSelector();
            Long unused = singleDateSelector.selectedItem = (Long) source.readValue(Long.class.getClassLoader());
            return singleDateSelector;
        }

        public SingleDateSelector[] newArray(int size) {
            return new SingleDateSelector[size];
        }
    };
    /* access modifiers changed from: private */
    public CharSequence error;
    /* access modifiers changed from: private */
    public Long selectedItem;
    private SimpleDateFormat textInputFormat;

    public void select(long selection) {
        this.selectedItem = Long.valueOf(selection);
    }

    /* access modifiers changed from: private */
    public void clearSelection() {
        this.selectedItem = null;
    }

    public void setSelection(Long selection) {
        this.selectedItem = selection == null ? null : Long.valueOf(UtcDates.canonicalYearMonthDay(selection.longValue()));
    }

    public boolean isSelectionComplete() {
        return this.selectedItem != null;
    }

    public Collection<Pair<Long, Long>> getSelectedRanges() {
        return new ArrayList();
    }

    public Collection<Long> getSelectedDays() {
        ArrayList<Long> selections = new ArrayList<>();
        Long l = this.selectedItem;
        if (l != null) {
            selections.add(l);
        }
        return selections;
    }

    public Long getSelection() {
        return this.selectedItem;
    }

    public void setTextInputFormat(SimpleDateFormat format) {
        this.textInputFormat = format;
    }

    public View onCreateTextInputView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle, CalendarConstraints constraints, OnSelectionChangedListener<Long> listener) {
        String str;
        View root = layoutInflater.inflate(C1087R.C1092layout.mtrl_picker_text_input_date, viewGroup, false);
        TextInputLayout dateTextInput = (TextInputLayout) root.findViewById(C1087R.C1090id.mtrl_picker_text_input_date);
        dateTextInput.setErrorAccessibilityLiveRegion(0);
        EditText dateEditText = dateTextInput.getEditText();
        if (ManufacturerUtils.isDateInputKeyboardMissingSeparatorCharacters()) {
            dateEditText.setInputType(17);
        }
        SimpleDateFormat simpleDateFormat = this.textInputFormat;
        boolean hasCustomFormat = simpleDateFormat != null;
        if (!hasCustomFormat) {
            simpleDateFormat = UtcDates.getDefaultTextInputFormat();
        }
        SimpleDateFormat format = simpleDateFormat;
        if (hasCustomFormat) {
            str = format.toPattern();
        } else {
            str = UtcDates.getDefaultTextInputHint(root.getResources(), format);
        }
        String formatHint = str;
        dateTextInput.setPlaceholderText(formatHint);
        Long l = this.selectedItem;
        if (l != null) {
            dateEditText.setText(format.format(l));
        }
        C11891 r9 = r0;
        String str2 = formatHint;
        final OnSelectionChangedListener<Long> onSelectionChangedListener = listener;
        SimpleDateFormat simpleDateFormat2 = format;
        final TextInputLayout textInputLayout = dateTextInput;
        C11891 r0 = new DateFormatTextWatcher(formatHint, format, dateTextInput, constraints) {
            /* access modifiers changed from: package-private */
            public void onValidDate(Long day) {
                if (day == null) {
                    SingleDateSelector.this.clearSelection();
                } else {
                    SingleDateSelector.this.select(day.longValue());
                }
                CharSequence unused = SingleDateSelector.this.error = null;
                onSelectionChangedListener.onSelectionChanged(SingleDateSelector.this.getSelection());
            }

            /* access modifiers changed from: package-private */
            public void onInvalidDate() {
                CharSequence unused = SingleDateSelector.this.error = textInputLayout.getError();
                onSelectionChangedListener.onIncompleteSelectionChanged();
            }
        };
        dateEditText.addTextChangedListener(r9);
        DateSelector.CC.showKeyboardWithAutoHideBehavior(dateEditText);
        return root;
    }

    public int getDefaultThemeResId(Context context) {
        return MaterialAttributes.resolveOrThrow(context, C1087R.attr.materialCalendarTheme, MaterialDatePicker.class.getCanonicalName());
    }

    public String getSelectionDisplayString(Context context) {
        Resources res = context.getResources();
        Long l = this.selectedItem;
        if (l == null) {
            return res.getString(C1087R.string.mtrl_picker_date_header_unselected);
        }
        String startString = DateStrings.getYearMonthDay(l.longValue());
        return res.getString(C1087R.string.mtrl_picker_date_header_selected, new Object[]{startString});
    }

    public String getSelectionContentDescription(Context context) {
        String placeholder;
        Resources res = context.getResources();
        Long l = this.selectedItem;
        if (l == null) {
            placeholder = res.getString(C1087R.string.mtrl_picker_announce_current_selection_none);
        } else {
            placeholder = DateStrings.getYearMonthDay(l.longValue());
        }
        return res.getString(C1087R.string.mtrl_picker_announce_current_selection, new Object[]{placeholder});
    }

    public String getError() {
        if (TextUtils.isEmpty(this.error)) {
            return null;
        }
        return this.error.toString();
    }

    public int getDefaultTitleResId() {
        return C1087R.string.mtrl_picker_date_header_title;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.selectedItem);
    }
}
