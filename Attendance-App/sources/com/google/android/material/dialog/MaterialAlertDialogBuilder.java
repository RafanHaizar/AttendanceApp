package com.google.android.material.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.core.view.ViewCompat;
import com.google.android.material.C1087R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class MaterialAlertDialogBuilder extends AlertDialog.Builder {
    private static final int DEF_STYLE_ATTR = C1087R.attr.alertDialogStyle;
    private static final int DEF_STYLE_RES = C1087R.C1093style.MaterialAlertDialog_MaterialComponents;
    private static final int MATERIAL_ALERT_DIALOG_THEME_OVERLAY = C1087R.attr.materialAlertDialogTheme;
    private Drawable background;
    private final Rect backgroundInsets;

    private static int getMaterialAlertDialogThemeOverlay(Context context) {
        TypedValue materialAlertDialogThemeOverlay = MaterialAttributes.resolve(context, MATERIAL_ALERT_DIALOG_THEME_OVERLAY);
        if (materialAlertDialogThemeOverlay == null) {
            return 0;
        }
        return materialAlertDialogThemeOverlay.data;
    }

    private static Context createMaterialAlertDialogThemedContext(Context context) {
        int themeOverlayId = getMaterialAlertDialogThemeOverlay(context);
        Context themedContext = MaterialThemeOverlay.wrap(context, (AttributeSet) null, DEF_STYLE_ATTR, DEF_STYLE_RES);
        if (themeOverlayId == 0) {
            return themedContext;
        }
        return new ContextThemeWrapper(themedContext, themeOverlayId);
    }

    private static int getOverridingThemeResId(Context context, int overrideThemeResId) {
        if (overrideThemeResId == 0) {
            return getMaterialAlertDialogThemeOverlay(context);
        }
        return overrideThemeResId;
    }

    public MaterialAlertDialogBuilder(Context context) {
        this(context, 0);
    }

    public MaterialAlertDialogBuilder(Context context, int overrideThemeResId) {
        super(createMaterialAlertDialogThemedContext(context), getOverridingThemeResId(context, overrideThemeResId));
        Context context2 = getContext();
        Resources.Theme theme = context2.getTheme();
        int i = DEF_STYLE_ATTR;
        int i2 = DEF_STYLE_RES;
        this.backgroundInsets = MaterialDialogs.getDialogBackgroundInsets(context2, i, i2);
        int surfaceColor = MaterialColors.getColor(context2, C1087R.attr.colorSurface, getClass().getCanonicalName());
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(context2, (AttributeSet) null, i, i2);
        materialShapeDrawable.initializeElevationOverlay(context2);
        materialShapeDrawable.setFillColor(ColorStateList.valueOf(surfaceColor));
        if (Build.VERSION.SDK_INT >= 28) {
            TypedValue dialogCornerRadiusValue = new TypedValue();
            theme.resolveAttribute(16844145, dialogCornerRadiusValue, true);
            float dialogCornerRadius = dialogCornerRadiusValue.getDimension(getContext().getResources().getDisplayMetrics());
            if (dialogCornerRadiusValue.type == 5 && dialogCornerRadius >= 0.0f) {
                materialShapeDrawable.setCornerSize(dialogCornerRadius);
            }
        }
        this.background = materialShapeDrawable;
    }

    public AlertDialog create() {
        AlertDialog alertDialog = super.create();
        Window window = alertDialog.getWindow();
        View decorView = window.getDecorView();
        Drawable drawable = this.background;
        if (drawable instanceof MaterialShapeDrawable) {
            ((MaterialShapeDrawable) drawable).setElevation(ViewCompat.getElevation(decorView));
        }
        window.setBackgroundDrawable(MaterialDialogs.insetDrawable(this.background, this.backgroundInsets));
        decorView.setOnTouchListener(new InsetDialogOnTouchListener(alertDialog, this.backgroundInsets));
        return alertDialog;
    }

    public Drawable getBackground() {
        return this.background;
    }

    public MaterialAlertDialogBuilder setBackground(Drawable background2) {
        this.background = background2;
        return this;
    }

    public MaterialAlertDialogBuilder setBackgroundInsetStart(int backgroundInsetStart) {
        if (getContext().getResources().getConfiguration().getLayoutDirection() == 1) {
            this.backgroundInsets.right = backgroundInsetStart;
        } else {
            this.backgroundInsets.left = backgroundInsetStart;
        }
        return this;
    }

    public MaterialAlertDialogBuilder setBackgroundInsetTop(int backgroundInsetTop) {
        this.backgroundInsets.top = backgroundInsetTop;
        return this;
    }

    public MaterialAlertDialogBuilder setBackgroundInsetEnd(int backgroundInsetEnd) {
        if (getContext().getResources().getConfiguration().getLayoutDirection() == 1) {
            this.backgroundInsets.left = backgroundInsetEnd;
        } else {
            this.backgroundInsets.right = backgroundInsetEnd;
        }
        return this;
    }

    public MaterialAlertDialogBuilder setBackgroundInsetBottom(int backgroundInsetBottom) {
        this.backgroundInsets.bottom = backgroundInsetBottom;
        return this;
    }

    public MaterialAlertDialogBuilder setTitle(int titleId) {
        return (MaterialAlertDialogBuilder) super.setTitle(titleId);
    }

    public MaterialAlertDialogBuilder setTitle(CharSequence title) {
        return (MaterialAlertDialogBuilder) super.setTitle(title);
    }

    public MaterialAlertDialogBuilder setCustomTitle(View customTitleView) {
        return (MaterialAlertDialogBuilder) super.setCustomTitle(customTitleView);
    }

    public MaterialAlertDialogBuilder setMessage(int messageId) {
        return (MaterialAlertDialogBuilder) super.setMessage(messageId);
    }

    public MaterialAlertDialogBuilder setMessage(CharSequence message) {
        return (MaterialAlertDialogBuilder) super.setMessage(message);
    }

    public MaterialAlertDialogBuilder setIcon(int iconId) {
        return (MaterialAlertDialogBuilder) super.setIcon(iconId);
    }

    public MaterialAlertDialogBuilder setIcon(Drawable icon) {
        return (MaterialAlertDialogBuilder) super.setIcon(icon);
    }

    public MaterialAlertDialogBuilder setIconAttribute(int attrId) {
        return (MaterialAlertDialogBuilder) super.setIconAttribute(attrId);
    }

    public MaterialAlertDialogBuilder setPositiveButton(int textId, DialogInterface.OnClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setPositiveButton(textId, listener);
    }

    public MaterialAlertDialogBuilder setPositiveButton(CharSequence text, DialogInterface.OnClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setPositiveButton(text, listener);
    }

    public MaterialAlertDialogBuilder setPositiveButtonIcon(Drawable icon) {
        return (MaterialAlertDialogBuilder) super.setPositiveButtonIcon(icon);
    }

    public MaterialAlertDialogBuilder setNegativeButton(int textId, DialogInterface.OnClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setNegativeButton(textId, listener);
    }

    public MaterialAlertDialogBuilder setNegativeButton(CharSequence text, DialogInterface.OnClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setNegativeButton(text, listener);
    }

    public MaterialAlertDialogBuilder setNegativeButtonIcon(Drawable icon) {
        return (MaterialAlertDialogBuilder) super.setNegativeButtonIcon(icon);
    }

    public MaterialAlertDialogBuilder setNeutralButton(int textId, DialogInterface.OnClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setNeutralButton(textId, listener);
    }

    public MaterialAlertDialogBuilder setNeutralButton(CharSequence text, DialogInterface.OnClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setNeutralButton(text, listener);
    }

    public MaterialAlertDialogBuilder setNeutralButtonIcon(Drawable icon) {
        return (MaterialAlertDialogBuilder) super.setNeutralButtonIcon(icon);
    }

    public MaterialAlertDialogBuilder setCancelable(boolean cancelable) {
        return (MaterialAlertDialogBuilder) super.setCancelable(cancelable);
    }

    public MaterialAlertDialogBuilder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        return (MaterialAlertDialogBuilder) super.setOnCancelListener(onCancelListener);
    }

    public MaterialAlertDialogBuilder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        return (MaterialAlertDialogBuilder) super.setOnDismissListener(onDismissListener);
    }

    public MaterialAlertDialogBuilder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        return (MaterialAlertDialogBuilder) super.setOnKeyListener(onKeyListener);
    }

    public MaterialAlertDialogBuilder setItems(int itemsId, DialogInterface.OnClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setItems(itemsId, listener);
    }

    public MaterialAlertDialogBuilder setItems(CharSequence[] items, DialogInterface.OnClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setItems(items, listener);
    }

    public MaterialAlertDialogBuilder setAdapter(ListAdapter adapter, DialogInterface.OnClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setAdapter(adapter, listener);
    }

    public MaterialAlertDialogBuilder setCursor(Cursor cursor, DialogInterface.OnClickListener listener, String labelColumn) {
        return (MaterialAlertDialogBuilder) super.setCursor(cursor, listener, labelColumn);
    }

    public MaterialAlertDialogBuilder setMultiChoiceItems(int itemsId, boolean[] checkedItems, DialogInterface.OnMultiChoiceClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setMultiChoiceItems(itemsId, checkedItems, listener);
    }

    public MaterialAlertDialogBuilder setMultiChoiceItems(CharSequence[] items, boolean[] checkedItems, DialogInterface.OnMultiChoiceClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setMultiChoiceItems(items, checkedItems, listener);
    }

    public MaterialAlertDialogBuilder setMultiChoiceItems(Cursor cursor, String isCheckedColumn, String labelColumn, DialogInterface.OnMultiChoiceClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setMultiChoiceItems(cursor, isCheckedColumn, labelColumn, listener);
    }

    public MaterialAlertDialogBuilder setSingleChoiceItems(int itemsId, int checkedItem, DialogInterface.OnClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setSingleChoiceItems(itemsId, checkedItem, listener);
    }

    public MaterialAlertDialogBuilder setSingleChoiceItems(Cursor cursor, int checkedItem, String labelColumn, DialogInterface.OnClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setSingleChoiceItems(cursor, checkedItem, labelColumn, listener);
    }

    public MaterialAlertDialogBuilder setSingleChoiceItems(CharSequence[] items, int checkedItem, DialogInterface.OnClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setSingleChoiceItems(items, checkedItem, listener);
    }

    public MaterialAlertDialogBuilder setSingleChoiceItems(ListAdapter adapter, int checkedItem, DialogInterface.OnClickListener listener) {
        return (MaterialAlertDialogBuilder) super.setSingleChoiceItems(adapter, checkedItem, listener);
    }

    public MaterialAlertDialogBuilder setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener) {
        return (MaterialAlertDialogBuilder) super.setOnItemSelectedListener(listener);
    }

    public MaterialAlertDialogBuilder setView(int layoutResId) {
        return (MaterialAlertDialogBuilder) super.setView(layoutResId);
    }

    public MaterialAlertDialogBuilder setView(View view) {
        return (MaterialAlertDialogBuilder) super.setView(view);
    }
}
