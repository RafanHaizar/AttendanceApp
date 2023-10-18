package androidx.appcompat.app;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.appcompat.C0503R;
import androidx.appcompat.app.AlertController;

public class AlertDialog extends AppCompatDialog implements DialogInterface {
    static final int LAYOUT_HINT_NONE = 0;
    static final int LAYOUT_HINT_SIDE = 1;
    final AlertController mAlert;

    protected AlertDialog(Context context) {
        this(context, 0);
    }

    protected AlertDialog(Context context, int themeResId) {
        super(context, resolveDialogTheme(context, themeResId));
        this.mAlert = new AlertController(getContext(), this, getWindow());
    }

    protected AlertDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        this(context, 0);
        setCancelable(cancelable);
        setOnCancelListener(cancelListener);
    }

    static int resolveDialogTheme(Context context, int resid) {
        if (((resid >>> 24) & 255) >= 1) {
            return resid;
        }
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(C0503R.attr.alertDialogTheme, outValue, true);
        return outValue.resourceId;
    }

    public Button getButton(int whichButton) {
        return this.mAlert.getButton(whichButton);
    }

    public ListView getListView() {
        return this.mAlert.getListView();
    }

    public void setTitle(CharSequence title) {
        super.setTitle(title);
        this.mAlert.setTitle(title);
    }

    public void setCustomTitle(View customTitleView) {
        this.mAlert.setCustomTitle(customTitleView);
    }

    public void setMessage(CharSequence message) {
        this.mAlert.setMessage(message);
    }

    public void setView(View view) {
        this.mAlert.setView(view);
    }

    public void setView(View view, int viewSpacingLeft, int viewSpacingTop, int viewSpacingRight, int viewSpacingBottom) {
        this.mAlert.setView(view, viewSpacingLeft, viewSpacingTop, viewSpacingRight, viewSpacingBottom);
    }

    /* access modifiers changed from: package-private */
    public void setButtonPanelLayoutHint(int layoutHint) {
        this.mAlert.setButtonPanelLayoutHint(layoutHint);
    }

    public void setButton(int whichButton, CharSequence text, Message msg) {
        this.mAlert.setButton(whichButton, text, (DialogInterface.OnClickListener) null, msg, (Drawable) null);
    }

    public void setButton(int whichButton, CharSequence text, DialogInterface.OnClickListener listener) {
        this.mAlert.setButton(whichButton, text, listener, (Message) null, (Drawable) null);
    }

    public void setButton(int whichButton, CharSequence text, Drawable icon, DialogInterface.OnClickListener listener) {
        this.mAlert.setButton(whichButton, text, listener, (Message) null, icon);
    }

    public void setIcon(int resId) {
        this.mAlert.setIcon(resId);
    }

    public void setIcon(Drawable icon) {
        this.mAlert.setIcon(icon);
    }

    public void setIconAttribute(int attrId) {
        TypedValue out = new TypedValue();
        getContext().getTheme().resolveAttribute(attrId, out, true);
        this.mAlert.setIcon(out.resourceId);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mAlert.installContent();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (this.mAlert.onKeyDown(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (this.mAlert.onKeyUp(keyCode, event)) {
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    public static class Builder {

        /* renamed from: P */
        private final AlertController.AlertParams f971P;
        private final int mTheme;

        public Builder(Context context) {
            this(context, AlertDialog.resolveDialogTheme(context, 0));
        }

        public Builder(Context context, int themeResId) {
            this.f971P = new AlertController.AlertParams(new ContextThemeWrapper(context, AlertDialog.resolveDialogTheme(context, themeResId)));
            this.mTheme = themeResId;
        }

        public Context getContext() {
            return this.f971P.mContext;
        }

        public Builder setTitle(int titleId) {
            AlertController.AlertParams alertParams = this.f971P;
            alertParams.mTitle = alertParams.mContext.getText(titleId);
            return this;
        }

        public Builder setTitle(CharSequence title) {
            this.f971P.mTitle = title;
            return this;
        }

        public Builder setCustomTitle(View customTitleView) {
            this.f971P.mCustomTitleView = customTitleView;
            return this;
        }

        public Builder setMessage(int messageId) {
            AlertController.AlertParams alertParams = this.f971P;
            alertParams.mMessage = alertParams.mContext.getText(messageId);
            return this;
        }

        public Builder setMessage(CharSequence message) {
            this.f971P.mMessage = message;
            return this;
        }

        public Builder setIcon(int iconId) {
            this.f971P.mIconId = iconId;
            return this;
        }

        public Builder setIcon(Drawable icon) {
            this.f971P.mIcon = icon;
            return this;
        }

        public Builder setIconAttribute(int attrId) {
            TypedValue out = new TypedValue();
            this.f971P.mContext.getTheme().resolveAttribute(attrId, out, true);
            this.f971P.mIconId = out.resourceId;
            return this;
        }

        public Builder setPositiveButton(int textId, DialogInterface.OnClickListener listener) {
            AlertController.AlertParams alertParams = this.f971P;
            alertParams.mPositiveButtonText = alertParams.mContext.getText(textId);
            this.f971P.mPositiveButtonListener = listener;
            return this;
        }

        public Builder setPositiveButton(CharSequence text, DialogInterface.OnClickListener listener) {
            this.f971P.mPositiveButtonText = text;
            this.f971P.mPositiveButtonListener = listener;
            return this;
        }

        public Builder setPositiveButtonIcon(Drawable icon) {
            this.f971P.mPositiveButtonIcon = icon;
            return this;
        }

        public Builder setNegativeButton(int textId, DialogInterface.OnClickListener listener) {
            AlertController.AlertParams alertParams = this.f971P;
            alertParams.mNegativeButtonText = alertParams.mContext.getText(textId);
            this.f971P.mNegativeButtonListener = listener;
            return this;
        }

        public Builder setNegativeButton(CharSequence text, DialogInterface.OnClickListener listener) {
            this.f971P.mNegativeButtonText = text;
            this.f971P.mNegativeButtonListener = listener;
            return this;
        }

        public Builder setNegativeButtonIcon(Drawable icon) {
            this.f971P.mNegativeButtonIcon = icon;
            return this;
        }

        public Builder setNeutralButton(int textId, DialogInterface.OnClickListener listener) {
            AlertController.AlertParams alertParams = this.f971P;
            alertParams.mNeutralButtonText = alertParams.mContext.getText(textId);
            this.f971P.mNeutralButtonListener = listener;
            return this;
        }

        public Builder setNeutralButton(CharSequence text, DialogInterface.OnClickListener listener) {
            this.f971P.mNeutralButtonText = text;
            this.f971P.mNeutralButtonListener = listener;
            return this;
        }

        public Builder setNeutralButtonIcon(Drawable icon) {
            this.f971P.mNeutralButtonIcon = icon;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.f971P.mCancelable = cancelable;
            return this;
        }

        public Builder setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
            this.f971P.mOnCancelListener = onCancelListener;
            return this;
        }

        public Builder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
            this.f971P.mOnDismissListener = onDismissListener;
            return this;
        }

        public Builder setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
            this.f971P.mOnKeyListener = onKeyListener;
            return this;
        }

        public Builder setItems(int itemsId, DialogInterface.OnClickListener listener) {
            AlertController.AlertParams alertParams = this.f971P;
            alertParams.mItems = alertParams.mContext.getResources().getTextArray(itemsId);
            this.f971P.mOnClickListener = listener;
            return this;
        }

        public Builder setItems(CharSequence[] items, DialogInterface.OnClickListener listener) {
            this.f971P.mItems = items;
            this.f971P.mOnClickListener = listener;
            return this;
        }

        public Builder setAdapter(ListAdapter adapter, DialogInterface.OnClickListener listener) {
            this.f971P.mAdapter = adapter;
            this.f971P.mOnClickListener = listener;
            return this;
        }

        public Builder setCursor(Cursor cursor, DialogInterface.OnClickListener listener, String labelColumn) {
            this.f971P.mCursor = cursor;
            this.f971P.mLabelColumn = labelColumn;
            this.f971P.mOnClickListener = listener;
            return this;
        }

        public Builder setMultiChoiceItems(int itemsId, boolean[] checkedItems, DialogInterface.OnMultiChoiceClickListener listener) {
            AlertController.AlertParams alertParams = this.f971P;
            alertParams.mItems = alertParams.mContext.getResources().getTextArray(itemsId);
            this.f971P.mOnCheckboxClickListener = listener;
            this.f971P.mCheckedItems = checkedItems;
            this.f971P.mIsMultiChoice = true;
            return this;
        }

        public Builder setMultiChoiceItems(CharSequence[] items, boolean[] checkedItems, DialogInterface.OnMultiChoiceClickListener listener) {
            this.f971P.mItems = items;
            this.f971P.mOnCheckboxClickListener = listener;
            this.f971P.mCheckedItems = checkedItems;
            this.f971P.mIsMultiChoice = true;
            return this;
        }

        public Builder setMultiChoiceItems(Cursor cursor, String isCheckedColumn, String labelColumn, DialogInterface.OnMultiChoiceClickListener listener) {
            this.f971P.mCursor = cursor;
            this.f971P.mOnCheckboxClickListener = listener;
            this.f971P.mIsCheckedColumn = isCheckedColumn;
            this.f971P.mLabelColumn = labelColumn;
            this.f971P.mIsMultiChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(int itemsId, int checkedItem, DialogInterface.OnClickListener listener) {
            AlertController.AlertParams alertParams = this.f971P;
            alertParams.mItems = alertParams.mContext.getResources().getTextArray(itemsId);
            this.f971P.mOnClickListener = listener;
            this.f971P.mCheckedItem = checkedItem;
            this.f971P.mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(Cursor cursor, int checkedItem, String labelColumn, DialogInterface.OnClickListener listener) {
            this.f971P.mCursor = cursor;
            this.f971P.mOnClickListener = listener;
            this.f971P.mCheckedItem = checkedItem;
            this.f971P.mLabelColumn = labelColumn;
            this.f971P.mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(CharSequence[] items, int checkedItem, DialogInterface.OnClickListener listener) {
            this.f971P.mItems = items;
            this.f971P.mOnClickListener = listener;
            this.f971P.mCheckedItem = checkedItem;
            this.f971P.mIsSingleChoice = true;
            return this;
        }

        public Builder setSingleChoiceItems(ListAdapter adapter, int checkedItem, DialogInterface.OnClickListener listener) {
            this.f971P.mAdapter = adapter;
            this.f971P.mOnClickListener = listener;
            this.f971P.mCheckedItem = checkedItem;
            this.f971P.mIsSingleChoice = true;
            return this;
        }

        public Builder setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener) {
            this.f971P.mOnItemSelectedListener = listener;
            return this;
        }

        public Builder setView(int layoutResId) {
            this.f971P.mView = null;
            this.f971P.mViewLayoutResId = layoutResId;
            this.f971P.mViewSpacingSpecified = false;
            return this;
        }

        public Builder setView(View view) {
            this.f971P.mView = view;
            this.f971P.mViewLayoutResId = 0;
            this.f971P.mViewSpacingSpecified = false;
            return this;
        }

        @Deprecated
        public Builder setView(View view, int viewSpacingLeft, int viewSpacingTop, int viewSpacingRight, int viewSpacingBottom) {
            this.f971P.mView = view;
            this.f971P.mViewLayoutResId = 0;
            this.f971P.mViewSpacingSpecified = true;
            this.f971P.mViewSpacingLeft = viewSpacingLeft;
            this.f971P.mViewSpacingTop = viewSpacingTop;
            this.f971P.mViewSpacingRight = viewSpacingRight;
            this.f971P.mViewSpacingBottom = viewSpacingBottom;
            return this;
        }

        @Deprecated
        public Builder setInverseBackgroundForced(boolean useInverseBackground) {
            this.f971P.mForceInverseBackground = useInverseBackground;
            return this;
        }

        public Builder setRecycleOnMeasureEnabled(boolean enabled) {
            this.f971P.mRecycleOnMeasure = enabled;
            return this;
        }

        public AlertDialog create() {
            AlertDialog dialog = new AlertDialog(this.f971P.mContext, this.mTheme);
            this.f971P.apply(dialog.mAlert);
            dialog.setCancelable(this.f971P.mCancelable);
            if (this.f971P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(this.f971P.mOnCancelListener);
            dialog.setOnDismissListener(this.f971P.mOnDismissListener);
            if (this.f971P.mOnKeyListener != null) {
                dialog.setOnKeyListener(this.f971P.mOnKeyListener);
            }
            return dialog;
        }

        public AlertDialog show() {
            AlertDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }
}
