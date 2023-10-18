package androidx.appcompat.app;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.ComponentDialog;
import androidx.appcompat.C0503R;
import androidx.appcompat.view.ActionMode;
import androidx.core.view.KeyEventDispatcher;

public class AppCompatDialog extends ComponentDialog implements AppCompatCallback {
    private AppCompatDelegate mDelegate;
    private final KeyEventDispatcher.Component mKeyDispatcher;

    public AppCompatDialog(Context context) {
        this(context, 0);
    }

    public AppCompatDialog(Context context, int theme) {
        super(context, getThemeResId(context, theme));
        this.mKeyDispatcher = new AppCompatDialog$$ExternalSyntheticLambda0(this);
        AppCompatDelegate delegate = getDelegate();
        delegate.setTheme(getThemeResId(context, theme));
        delegate.onCreate((Bundle) null);
    }

    protected AppCompatDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context);
        this.mKeyDispatcher = new AppCompatDialog$$ExternalSyntheticLambda0(this);
        setCancelable(cancelable);
        setOnCancelListener(cancelListener);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        getDelegate().installViewFactory();
        super.onCreate(savedInstanceState);
        getDelegate().onCreate(savedInstanceState);
    }

    public ActionBar getSupportActionBar() {
        return getDelegate().getSupportActionBar();
    }

    public void setContentView(int layoutResID) {
        getDelegate().setContentView(layoutResID);
    }

    public void setContentView(View view) {
        getDelegate().setContentView(view);
    }

    public void setContentView(View view, ViewGroup.LayoutParams params) {
        getDelegate().setContentView(view, params);
    }

    public <T extends View> T findViewById(int id) {
        return getDelegate().findViewById(id);
    }

    public void setTitle(CharSequence title) {
        super.setTitle(title);
        getDelegate().setTitle(title);
    }

    public void setTitle(int titleId) {
        super.setTitle(titleId);
        getDelegate().setTitle(getContext().getString(titleId));
    }

    public void addContentView(View view, ViewGroup.LayoutParams params) {
        getDelegate().addContentView(view, params);
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        getDelegate().onStop();
    }

    public void dismiss() {
        super.dismiss();
        getDelegate().onDestroy();
    }

    public boolean supportRequestWindowFeature(int featureId) {
        return getDelegate().requestWindowFeature(featureId);
    }

    public void invalidateOptionsMenu() {
        getDelegate().invalidateOptionsMenu();
    }

    public AppCompatDelegate getDelegate() {
        if (this.mDelegate == null) {
            this.mDelegate = AppCompatDelegate.create((Dialog) this, (AppCompatCallback) this);
        }
        return this.mDelegate;
    }

    private static int getThemeResId(Context context, int themeId) {
        if (themeId != 0) {
            return themeId;
        }
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(C0503R.attr.dialogTheme, outValue, true);
        return outValue.resourceId;
    }

    public void onSupportActionModeStarted(ActionMode mode) {
    }

    public void onSupportActionModeFinished(ActionMode mode) {
    }

    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }

    /* access modifiers changed from: package-private */
    public boolean superDispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        return KeyEventDispatcher.dispatchKeyEvent(this.mKeyDispatcher, getWindow().getDecorView(), this, event);
    }
}
