package com.google.android.material.datepicker;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import androidx.core.util.Pair;
import com.android.tools.r8.annotations.SynthesizedClassV2;
import com.google.android.material.internal.ViewUtils;
import java.text.SimpleDateFormat;
import java.util.Collection;

public interface DateSelector<S> extends Parcelable {
    int getDefaultThemeResId(Context context);

    int getDefaultTitleResId();

    String getError();

    Collection<Long> getSelectedDays();

    Collection<Pair<Long, Long>> getSelectedRanges();

    S getSelection();

    String getSelectionContentDescription(Context context);

    String getSelectionDisplayString(Context context);

    boolean isSelectionComplete();

    View onCreateTextInputView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle, CalendarConstraints calendarConstraints, OnSelectionChangedListener<S> onSelectionChangedListener);

    void select(long j);

    void setSelection(S s);

    void setTextInputFormat(SimpleDateFormat simpleDateFormat);

    @SynthesizedClassV2(kind = 8, versionHash = "ea87655719898b9807d7a88878e9de051d12af172d2fab563c9881b5e404e7d4")
    /* renamed from: com.google.android.material.datepicker.DateSelector$-CC  reason: invalid class name */
    public final /* synthetic */ class CC<S> {
        public static void showKeyboardWithAutoHideBehavior(EditText... editTexts) {
            if (editTexts.length != 0) {
                View.OnFocusChangeListener listener = new DateSelector$$ExternalSyntheticLambda0(editTexts);
                for (EditText editText : editTexts) {
                    editText.setOnFocusChangeListener(listener);
                }
                ViewUtils.requestFocusAndShowKeyboard(editTexts[0]);
            }
        }

        public static /* synthetic */ void lambda$showKeyboardWithAutoHideBehavior$0(EditText[] editTexts, View view, boolean hasFocus) {
            int length = editTexts.length;
            int i = 0;
            while (i < length) {
                if (!editTexts[i].hasFocus()) {
                    i++;
                } else {
                    return;
                }
            }
            ViewUtils.hideKeyboard(view);
        }
    }
}
