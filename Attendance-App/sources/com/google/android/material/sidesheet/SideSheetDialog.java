package com.google.android.material.sidesheet;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.material.C1087R;

public class SideSheetDialog extends SheetDialog<SideSheetCallback> {
    private static final int SIDE_SHEET_DIALOG_DEFAULT_THEME_RES = C1087R.C1093style.Theme_Material3_Light_SideSheetDialog;
    private static final int SIDE_SHEET_DIALOG_THEME_ATTR = C1087R.attr.sideSheetDialogTheme;

    public /* bridge */ /* synthetic */ void cancel() {
        super.cancel();
    }

    public /* bridge */ /* synthetic */ boolean isDismissWithSheetAnimationEnabled() {
        return super.isDismissWithSheetAnimationEnabled();
    }

    public /* bridge */ /* synthetic */ void setCancelable(boolean z) {
        super.setCancelable(z);
    }

    public /* bridge */ /* synthetic */ void setCanceledOnTouchOutside(boolean z) {
        super.setCanceledOnTouchOutside(z);
    }

    public /* bridge */ /* synthetic */ void setContentView(int i) {
        super.setContentView(i);
    }

    public /* bridge */ /* synthetic */ void setContentView(View view) {
        super.setContentView(view);
    }

    public /* bridge */ /* synthetic */ void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        super.setContentView(view, layoutParams);
    }

    public /* bridge */ /* synthetic */ void setDismissWithSheetAnimationEnabled(boolean z) {
        super.setDismissWithSheetAnimationEnabled(z);
    }

    public SideSheetDialog(Context context) {
        this(context, 0);
    }

    /* access modifiers changed from: package-private */
    public void addSheetCancelOnHideCallback(Sheet<SideSheetCallback> behavior) {
        behavior.addCallback(new SideSheetCallback() {
            public void onStateChanged(View sheet, int newState) {
                if (newState == 5) {
                    SideSheetDialog.this.cancel();
                }
            }

            public void onSlide(View sheet, float slideOffset) {
            }
        });
    }

    public SideSheetDialog(Context context, int theme) {
        super(context, theme, SIDE_SHEET_DIALOG_THEME_ATTR, SIDE_SHEET_DIALOG_DEFAULT_THEME_RES);
        supportRequestWindowFeature(1);
    }

    /* access modifiers changed from: package-private */
    public int getLayoutResId() {
        return C1087R.C1092layout.m3_side_sheet_dialog;
    }

    /* access modifiers changed from: package-private */
    public int getDialogId() {
        return C1087R.C1090id.m3_side_sheet;
    }

    /* access modifiers changed from: package-private */
    public Sheet<SideSheetCallback> getBehaviorFromSheet(FrameLayout sheet) {
        return SideSheetBehavior.from(sheet);
    }

    /* access modifiers changed from: package-private */
    public int getStateOnStart() {
        return 3;
    }

    public SideSheetBehavior<? extends View> getBehavior() {
        Sheet<SideSheetCallback> sheetBehavior = super.getBehavior();
        if (sheetBehavior instanceof SideSheetBehavior) {
            return (SideSheetBehavior) sheetBehavior;
        }
        throw new IllegalStateException("The view is not associated with SideSheetBehavior");
    }
}
