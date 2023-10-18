package com.google.android.material.datepicker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import com.google.android.material.C1087R;
import com.google.android.material.dialog.InsetDialogOnTouchListener;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.shape.MaterialShapeDrawable;

public class MaterialStyledDatePickerDialog extends DatePickerDialog {
    private static final int DEF_STYLE_ATTR = 16843612;
    private static final int DEF_STYLE_RES = C1087R.C1093style.MaterialAlertDialog_MaterialComponents_Picker_Date_Spinner;
    private final Drawable background;
    private final Rect backgroundInsets;

    public MaterialStyledDatePickerDialog(Context context) {
        this(context, 0);
    }

    public MaterialStyledDatePickerDialog(Context context, int themeResId) {
        this(context, themeResId, (DatePickerDialog.OnDateSetListener) null, -1, -1, -1);
    }

    public MaterialStyledDatePickerDialog(Context context, DatePickerDialog.OnDateSetListener listener, int year, int month, int dayOfMonth) {
        this(context, 0, listener, year, month, dayOfMonth);
    }

    public MaterialStyledDatePickerDialog(Context context, int themeResId, DatePickerDialog.OnDateSetListener listener, int year, int monthOfYear, int dayOfMonth) {
        super(context, themeResId, listener, year, monthOfYear, dayOfMonth);
        Context context2 = getContext();
        int surfaceColor = MaterialAttributes.resolveOrThrow(getContext(), C1087R.attr.colorSurface, getClass().getCanonicalName());
        int i = DEF_STYLE_RES;
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(context2, (AttributeSet) null, DEF_STYLE_ATTR, i);
        if (Build.VERSION.SDK_INT >= 21) {
            materialShapeDrawable.setFillColor(ColorStateList.valueOf(surfaceColor));
        } else {
            materialShapeDrawable.setFillColor(ColorStateList.valueOf(0));
        }
        Rect dialogBackgroundInsets = MaterialDialogs.getDialogBackgroundInsets(context2, DEF_STYLE_ATTR, i);
        this.backgroundInsets = dialogBackgroundInsets;
        this.background = MaterialDialogs.insetDrawable(materialShapeDrawable, dialogBackgroundInsets);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(this.background);
        getWindow().getDecorView().setOnTouchListener(new InsetDialogOnTouchListener(this, this.backgroundInsets));
    }
}
