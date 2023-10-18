package androidx.fragment.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewTreeLifecycleOwner;
import androidx.lifecycle.ViewTreeViewModelStoreOwner;
import androidx.savedstate.ViewTreeSavedStateRegistryOwner;

public class DialogFragment extends Fragment implements DialogInterface.OnCancelListener, DialogInterface.OnDismissListener {
    private static final String SAVED_BACK_STACK_ID = "android:backStackId";
    private static final String SAVED_CANCELABLE = "android:cancelable";
    private static final String SAVED_DIALOG_STATE_TAG = "android:savedDialogState";
    private static final String SAVED_INTERNAL_DIALOG_SHOWING = "android:dialogShowing";
    private static final String SAVED_SHOWS_DIALOG = "android:showsDialog";
    private static final String SAVED_STYLE = "android:style";
    private static final String SAVED_THEME = "android:theme";
    public static final int STYLE_NORMAL = 0;
    public static final int STYLE_NO_FRAME = 2;
    public static final int STYLE_NO_INPUT = 3;
    public static final int STYLE_NO_TITLE = 1;
    private int mBackStackId = -1;
    private boolean mCancelable = true;
    private boolean mCreatingDialog;
    /* access modifiers changed from: private */
    public Dialog mDialog;
    private boolean mDialogCreated = false;
    private Runnable mDismissRunnable = new Runnable() {
        public void run() {
            DialogFragment.this.mOnDismissListener.onDismiss(DialogFragment.this.mDialog);
        }
    };
    private boolean mDismissed;
    private Handler mHandler;
    private Observer<LifecycleOwner> mObserver = new Observer<LifecycleOwner>() {
        public void onChanged(LifecycleOwner lifecycleOwner) {
            if (lifecycleOwner != null && DialogFragment.this.mShowsDialog) {
                View view = DialogFragment.this.requireView();
                if (view.getParent() != null) {
                    throw new IllegalStateException("DialogFragment can not be attached to a container view");
                } else if (DialogFragment.this.mDialog != null) {
                    if (FragmentManager.isLoggingEnabled(3)) {
                        Log.d("FragmentManager", "DialogFragment " + this + " setting the content view on " + DialogFragment.this.mDialog);
                    }
                    DialogFragment.this.mDialog.setContentView(view);
                }
            }
        }
    };
    private DialogInterface.OnCancelListener mOnCancelListener = new DialogInterface.OnCancelListener() {
        public void onCancel(DialogInterface dialog) {
            if (DialogFragment.this.mDialog != null) {
                DialogFragment dialogFragment = DialogFragment.this;
                dialogFragment.onCancel(dialogFragment.mDialog);
            }
        }
    };
    /* access modifiers changed from: private */
    public DialogInterface.OnDismissListener mOnDismissListener = new DialogInterface.OnDismissListener() {
        public void onDismiss(DialogInterface dialog) {
            if (DialogFragment.this.mDialog != null) {
                DialogFragment dialogFragment = DialogFragment.this;
                dialogFragment.onDismiss(dialogFragment.mDialog);
            }
        }
    };
    private boolean mShownByMe;
    /* access modifiers changed from: private */
    public boolean mShowsDialog = true;
    private int mStyle = 0;
    private int mTheme = 0;
    private boolean mViewDestroyed;

    public DialogFragment() {
    }

    public DialogFragment(int contentLayoutId) {
        super(contentLayoutId);
    }

    public void setStyle(int style, int theme) {
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.d("FragmentManager", "Setting style and theme for DialogFragment " + this + " to " + style + ", " + theme);
        }
        this.mStyle = style;
        if (style == 2 || style == 3) {
            this.mTheme = 16973913;
        }
        if (theme != 0) {
            this.mTheme = theme;
        }
    }

    public void show(FragmentManager manager, String tag) {
        this.mDismissed = false;
        this.mShownByMe = true;
        FragmentTransaction ft = manager.beginTransaction();
        ft.add((Fragment) this, tag);
        ft.commit();
    }

    public int show(FragmentTransaction transaction, String tag) {
        this.mDismissed = false;
        this.mShownByMe = true;
        transaction.add((Fragment) this, tag);
        this.mViewDestroyed = false;
        int commit = transaction.commit();
        this.mBackStackId = commit;
        return commit;
    }

    public void showNow(FragmentManager manager, String tag) {
        this.mDismissed = false;
        this.mShownByMe = true;
        FragmentTransaction ft = manager.beginTransaction();
        ft.add((Fragment) this, tag);
        ft.commitNow();
    }

    public void dismiss() {
        dismissInternal(false, false);
    }

    public void dismissAllowingStateLoss() {
        dismissInternal(true, false);
    }

    private void dismissInternal(boolean allowStateLoss, boolean fromOnDismiss) {
        if (!this.mDismissed) {
            this.mDismissed = true;
            this.mShownByMe = false;
            Dialog dialog = this.mDialog;
            if (dialog != null) {
                dialog.setOnDismissListener((DialogInterface.OnDismissListener) null);
                this.mDialog.dismiss();
                if (!fromOnDismiss) {
                    if (Looper.myLooper() == this.mHandler.getLooper()) {
                        onDismiss(this.mDialog);
                    } else {
                        this.mHandler.post(this.mDismissRunnable);
                    }
                }
            }
            this.mViewDestroyed = true;
            if (this.mBackStackId >= 0) {
                getParentFragmentManager().popBackStack(this.mBackStackId, 1);
                this.mBackStackId = -1;
                return;
            }
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            ft.remove(this);
            if (allowStateLoss) {
                ft.commitAllowingStateLoss();
            } else {
                ft.commit();
            }
        }
    }

    public Dialog getDialog() {
        return this.mDialog;
    }

    public final Dialog requireDialog() {
        Dialog dialog = getDialog();
        if (dialog != null) {
            return dialog;
        }
        throw new IllegalStateException("DialogFragment " + this + " does not have a Dialog.");
    }

    public int getTheme() {
        return this.mTheme;
    }

    public void setCancelable(boolean cancelable) {
        this.mCancelable = cancelable;
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            dialog.setCancelable(cancelable);
        }
    }

    public boolean isCancelable() {
        return this.mCancelable;
    }

    public void setShowsDialog(boolean showsDialog) {
        this.mShowsDialog = showsDialog;
    }

    public boolean getShowsDialog() {
        return this.mShowsDialog;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        getViewLifecycleOwnerLiveData().observeForever(this.mObserver);
        if (!this.mShownByMe) {
            this.mDismissed = false;
        }
    }

    public void onDetach() {
        super.onDetach();
        if (!this.mShownByMe && !this.mDismissed) {
            this.mDismissed = true;
        }
        getViewLifecycleOwnerLiveData().removeObserver(this.mObserver);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mHandler = new Handler();
        this.mShowsDialog = this.mContainerId == 0;
        if (savedInstanceState != null) {
            this.mStyle = savedInstanceState.getInt(SAVED_STYLE, 0);
            this.mTheme = savedInstanceState.getInt(SAVED_THEME, 0);
            this.mCancelable = savedInstanceState.getBoolean(SAVED_CANCELABLE, true);
            this.mShowsDialog = savedInstanceState.getBoolean(SAVED_SHOWS_DIALOG, this.mShowsDialog);
            this.mBackStackId = savedInstanceState.getInt(SAVED_BACK_STACK_ID, -1);
        }
    }

    /* access modifiers changed from: package-private */
    public void performCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle dialogState;
        super.performCreateView(inflater, container, savedInstanceState);
        if (this.mView == null && this.mDialog != null && savedInstanceState != null && (dialogState = savedInstanceState.getBundle(SAVED_DIALOG_STATE_TAG)) != null) {
            this.mDialog.onRestoreInstanceState(dialogState);
        }
    }

    /* access modifiers changed from: package-private */
    public FragmentContainer createFragmentContainer() {
        final FragmentContainer fragmentContainer = super.createFragmentContainer();
        return new FragmentContainer() {
            public View onFindViewById(int id) {
                if (fragmentContainer.onHasView()) {
                    return fragmentContainer.onFindViewById(id);
                }
                return DialogFragment.this.onFindViewById(id);
            }

            public boolean onHasView() {
                return fragmentContainer.onHasView() || DialogFragment.this.onHasView();
            }
        };
    }

    /* access modifiers changed from: package-private */
    public View onFindViewById(int id) {
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            return dialog.findViewById(id);
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public boolean onHasView() {
        return this.mDialogCreated;
    }

    public LayoutInflater onGetLayoutInflater(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = super.onGetLayoutInflater(savedInstanceState);
        if (!this.mShowsDialog || this.mCreatingDialog) {
            if (FragmentManager.isLoggingEnabled(2)) {
                String message = "getting layout inflater for DialogFragment " + this;
                if (!this.mShowsDialog) {
                    Log.d("FragmentManager", "mShowsDialog = false: " + message);
                } else {
                    Log.d("FragmentManager", "mCreatingDialog = true: " + message);
                }
            }
            return layoutInflater;
        }
        prepareDialog(savedInstanceState);
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.d("FragmentManager", "get layout inflater for DialogFragment " + this + " from dialog context");
        }
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            return layoutInflater.cloneInContext(dialog.getContext());
        }
        return layoutInflater;
    }

    public void setupDialog(Dialog dialog, int style) {
        switch (style) {
            case 1:
            case 2:
                break;
            case 3:
                Window window = dialog.getWindow();
                if (window != null) {
                    window.addFlags(24);
                    break;
                }
                break;
            default:
                return;
        }
        dialog.requestWindowFeature(1);
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (FragmentManager.isLoggingEnabled(3)) {
            Log.d("FragmentManager", "onCreateDialog called for DialogFragment " + this);
        }
        return new Dialog(requireContext(), getTheme());
    }

    public void onCancel(DialogInterface dialog) {
    }

    public void onDismiss(DialogInterface dialog) {
        if (!this.mViewDestroyed) {
            if (FragmentManager.isLoggingEnabled(3)) {
                Log.d("FragmentManager", "onDismiss called for DialogFragment " + this);
            }
            dismissInternal(true, true);
        }
    }

    /* JADX INFO: finally extract failed */
    private void prepareDialog(Bundle savedInstanceState) {
        if (this.mShowsDialog && !this.mDialogCreated) {
            try {
                this.mCreatingDialog = true;
                Dialog onCreateDialog = onCreateDialog(savedInstanceState);
                this.mDialog = onCreateDialog;
                if (this.mShowsDialog) {
                    setupDialog(onCreateDialog, this.mStyle);
                    Context context = getContext();
                    if (context instanceof Activity) {
                        this.mDialog.setOwnerActivity((Activity) context);
                    }
                    this.mDialog.setCancelable(this.mCancelable);
                    this.mDialog.setOnCancelListener(this.mOnCancelListener);
                    this.mDialog.setOnDismissListener(this.mOnDismissListener);
                    this.mDialogCreated = true;
                } else {
                    this.mDialog = null;
                }
                this.mCreatingDialog = false;
            } catch (Throwable th) {
                this.mCreatingDialog = false;
                throw th;
            }
        }
    }

    public void onViewStateRestored(Bundle savedInstanceState) {
        Bundle dialogState;
        super.onViewStateRestored(savedInstanceState);
        if (this.mDialog != null && savedInstanceState != null && (dialogState = savedInstanceState.getBundle(SAVED_DIALOG_STATE_TAG)) != null) {
            this.mDialog.onRestoreInstanceState(dialogState);
        }
    }

    public void onStart() {
        super.onStart();
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            this.mViewDestroyed = false;
            dialog.show();
            View decorView = this.mDialog.getWindow().getDecorView();
            ViewTreeLifecycleOwner.set(decorView, this);
            ViewTreeViewModelStoreOwner.set(decorView, this);
            ViewTreeSavedStateRegistryOwner.set(decorView, this);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            Bundle dialogState = dialog.onSaveInstanceState();
            dialogState.putBoolean(SAVED_INTERNAL_DIALOG_SHOWING, false);
            outState.putBundle(SAVED_DIALOG_STATE_TAG, dialogState);
        }
        int i = this.mStyle;
        if (i != 0) {
            outState.putInt(SAVED_STYLE, i);
        }
        int i2 = this.mTheme;
        if (i2 != 0) {
            outState.putInt(SAVED_THEME, i2);
        }
        boolean z = this.mCancelable;
        if (!z) {
            outState.putBoolean(SAVED_CANCELABLE, z);
        }
        boolean z2 = this.mShowsDialog;
        if (!z2) {
            outState.putBoolean(SAVED_SHOWS_DIALOG, z2);
        }
        int i3 = this.mBackStackId;
        if (i3 != -1) {
            outState.putInt(SAVED_BACK_STACK_ID, i3);
        }
    }

    public void onStop() {
        super.onStop();
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            dialog.hide();
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        Dialog dialog = this.mDialog;
        if (dialog != null) {
            this.mViewDestroyed = true;
            dialog.setOnDismissListener((DialogInterface.OnDismissListener) null);
            this.mDialog.dismiss();
            if (!this.mDismissed) {
                onDismiss(this.mDialog);
            }
            this.mDialog = null;
            this.mDialogCreated = false;
        }
    }
}
