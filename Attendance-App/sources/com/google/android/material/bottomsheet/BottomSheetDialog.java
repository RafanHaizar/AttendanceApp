package com.google.android.material.bottomsheet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.C1087R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.EdgeToEdgeUtils;
import com.google.android.material.shape.MaterialShapeDrawable;

public class BottomSheetDialog extends AppCompatDialog {
    /* access modifiers changed from: private */
    public BottomSheetBehavior<FrameLayout> behavior;
    /* access modifiers changed from: private */
    public FrameLayout bottomSheet;
    private BottomSheetBehavior.BottomSheetCallback bottomSheetCallback;
    boolean cancelable;
    private boolean canceledOnTouchOutside;
    private boolean canceledOnTouchOutsideSet;
    private FrameLayout container;
    private CoordinatorLayout coordinator;
    boolean dismissWithAnimation;
    /* access modifiers changed from: private */
    public EdgeToEdgeCallback edgeToEdgeCallback;
    private boolean edgeToEdgeEnabled;

    public BottomSheetDialog(Context context) {
        this(context, 0);
        this.edgeToEdgeEnabled = getContext().getTheme().obtainStyledAttributes(new int[]{C1087R.attr.enableEdgeToEdge}).getBoolean(0, false);
    }

    public BottomSheetDialog(Context context, int theme) {
        super(context, getThemeResId(context, theme));
        this.cancelable = true;
        this.canceledOnTouchOutside = true;
        this.bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
            public void onStateChanged(View bottomSheet, int newState) {
                if (newState == 5) {
                    BottomSheetDialog.this.cancel();
                }
            }

            public void onSlide(View bottomSheet, float slideOffset) {
            }
        };
        supportRequestWindowFeature(1);
        this.edgeToEdgeEnabled = getContext().getTheme().obtainStyledAttributes(new int[]{C1087R.attr.enableEdgeToEdge}).getBoolean(0, false);
    }

    protected BottomSheetDialog(Context context, boolean cancelable2, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable2, cancelListener);
        this.cancelable = true;
        this.canceledOnTouchOutside = true;
        this.bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
            public void onStateChanged(View bottomSheet, int newState) {
                if (newState == 5) {
                    BottomSheetDialog.this.cancel();
                }
            }

            public void onSlide(View bottomSheet, float slideOffset) {
            }
        };
        supportRequestWindowFeature(1);
        this.cancelable = cancelable2;
        this.edgeToEdgeEnabled = getContext().getTheme().obtainStyledAttributes(new int[]{C1087R.attr.enableEdgeToEdge}).getBoolean(0, false);
    }

    public void setContentView(int layoutResId) {
        super.setContentView(wrapInBottomSheet(layoutResId, (View) null, (ViewGroup.LayoutParams) null));
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            if (Build.VERSION.SDK_INT >= 21) {
                window.setStatusBarColor(0);
                window.addFlags(Integer.MIN_VALUE);
                if (Build.VERSION.SDK_INT < 23) {
                    window.addFlags(PagedChannelRandomAccessSource.DEFAULT_TOTAL_BUFSIZE);
                }
            }
            window.setLayout(-1, -1);
        }
    }

    public void setContentView(View view) {
        super.setContentView(wrapInBottomSheet(0, view, (ViewGroup.LayoutParams) null));
    }

    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(wrapInBottomSheet(0, view, params));
    }

    public void setCancelable(boolean cancelable2) {
        super.setCancelable(cancelable2);
        if (this.cancelable != cancelable2) {
            this.cancelable = cancelable2;
            BottomSheetBehavior<FrameLayout> bottomSheetBehavior = this.behavior;
            if (bottomSheetBehavior != null) {
                bottomSheetBehavior.setHideable(cancelable2);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        BottomSheetBehavior<FrameLayout> bottomSheetBehavior = this.behavior;
        if (bottomSheetBehavior != null && bottomSheetBehavior.getState() == 5) {
            this.behavior.setState(4);
        }
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        if (window != null) {
            if (Build.VERSION.SDK_INT >= 21) {
                boolean z = true;
                boolean drawEdgeToEdge = this.edgeToEdgeEnabled && Color.alpha(window.getNavigationBarColor()) < 255;
                FrameLayout frameLayout = this.container;
                if (frameLayout != null) {
                    frameLayout.setFitsSystemWindows(!drawEdgeToEdge);
                }
                CoordinatorLayout coordinatorLayout = this.coordinator;
                if (coordinatorLayout != null) {
                    coordinatorLayout.setFitsSystemWindows(!drawEdgeToEdge);
                }
                if (drawEdgeToEdge) {
                    z = false;
                }
                WindowCompat.setDecorFitsSystemWindows(window, z);
            }
            EdgeToEdgeCallback edgeToEdgeCallback2 = this.edgeToEdgeCallback;
            if (edgeToEdgeCallback2 != null) {
                edgeToEdgeCallback2.setWindow(window);
            }
        }
    }

    public void onDetachedFromWindow() {
        EdgeToEdgeCallback edgeToEdgeCallback2 = this.edgeToEdgeCallback;
        if (edgeToEdgeCallback2 != null) {
            edgeToEdgeCallback2.setWindow((Window) null);
        }
    }

    public void cancel() {
        BottomSheetBehavior<FrameLayout> behavior2 = getBehavior();
        if (!this.dismissWithAnimation || behavior2.getState() == 5) {
            super.cancel();
        } else {
            behavior2.setState(5);
        }
    }

    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
        if (cancel && !this.cancelable) {
            this.cancelable = true;
        }
        this.canceledOnTouchOutside = cancel;
        this.canceledOnTouchOutsideSet = true;
    }

    public BottomSheetBehavior<FrameLayout> getBehavior() {
        if (this.behavior == null) {
            ensureContainerAndBehavior();
        }
        return this.behavior;
    }

    public void setDismissWithAnimation(boolean dismissWithAnimation2) {
        this.dismissWithAnimation = dismissWithAnimation2;
    }

    public boolean getDismissWithAnimation() {
        return this.dismissWithAnimation;
    }

    public boolean getEdgeToEdgeEnabled() {
        return this.edgeToEdgeEnabled;
    }

    private FrameLayout ensureContainerAndBehavior() {
        if (this.container == null) {
            FrameLayout frameLayout = (FrameLayout) View.inflate(getContext(), C1087R.C1092layout.design_bottom_sheet_dialog, (ViewGroup) null);
            this.container = frameLayout;
            this.coordinator = (CoordinatorLayout) frameLayout.findViewById(C1087R.C1090id.coordinator);
            FrameLayout frameLayout2 = (FrameLayout) this.container.findViewById(C1087R.C1090id.design_bottom_sheet);
            this.bottomSheet = frameLayout2;
            BottomSheetBehavior<FrameLayout> from = BottomSheetBehavior.from(frameLayout2);
            this.behavior = from;
            from.addBottomSheetCallback(this.bottomSheetCallback);
            this.behavior.setHideable(this.cancelable);
        }
        return this.container;
    }

    private View wrapInBottomSheet(int layoutResId, View view, ViewGroup.LayoutParams params) {
        ensureContainerAndBehavior();
        CoordinatorLayout coordinator2 = (CoordinatorLayout) this.container.findViewById(C1087R.C1090id.coordinator);
        if (layoutResId != 0 && view == null) {
            view = getLayoutInflater().inflate(layoutResId, coordinator2, false);
        }
        if (this.edgeToEdgeEnabled) {
            ViewCompat.setOnApplyWindowInsetsListener(this.bottomSheet, new OnApplyWindowInsetsListener() {
                public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets) {
                    if (BottomSheetDialog.this.edgeToEdgeCallback != null) {
                        BottomSheetDialog.this.behavior.removeBottomSheetCallback(BottomSheetDialog.this.edgeToEdgeCallback);
                    }
                    if (insets != null) {
                        BottomSheetDialog bottomSheetDialog = BottomSheetDialog.this;
                        EdgeToEdgeCallback unused = bottomSheetDialog.edgeToEdgeCallback = new EdgeToEdgeCallback(bottomSheetDialog.bottomSheet, insets);
                        BottomSheetDialog.this.edgeToEdgeCallback.setWindow(BottomSheetDialog.this.getWindow());
                        BottomSheetDialog.this.behavior.addBottomSheetCallback(BottomSheetDialog.this.edgeToEdgeCallback);
                    }
                    return insets;
                }
            });
        }
        this.bottomSheet.removeAllViews();
        if (params == null) {
            this.bottomSheet.addView(view);
        } else {
            this.bottomSheet.addView(view, params);
        }
        coordinator2.findViewById(C1087R.C1090id.touch_outside).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (BottomSheetDialog.this.cancelable && BottomSheetDialog.this.isShowing() && BottomSheetDialog.this.shouldWindowCloseOnTouchOutside()) {
                    BottomSheetDialog.this.cancel();
                }
            }
        });
        ViewCompat.setAccessibilityDelegate(this.bottomSheet, new AccessibilityDelegateCompat() {
            public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
                super.onInitializeAccessibilityNodeInfo(host, info);
                if (BottomSheetDialog.this.cancelable) {
                    info.addAction(1048576);
                    info.setDismissable(true);
                    return;
                }
                info.setDismissable(false);
            }

            public boolean performAccessibilityAction(View host, int action, Bundle args) {
                if (action != 1048576 || !BottomSheetDialog.this.cancelable) {
                    return super.performAccessibilityAction(host, action, args);
                }
                BottomSheetDialog.this.cancel();
                return true;
            }
        });
        this.bottomSheet.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                return true;
            }
        });
        return this.container;
    }

    /* access modifiers changed from: package-private */
    public boolean shouldWindowCloseOnTouchOutside() {
        if (!this.canceledOnTouchOutsideSet) {
            TypedArray a = getContext().obtainStyledAttributes(new int[]{16843611});
            this.canceledOnTouchOutside = a.getBoolean(0, true);
            a.recycle();
            this.canceledOnTouchOutsideSet = true;
        }
        return this.canceledOnTouchOutside;
    }

    private static int getThemeResId(Context context, int themeId) {
        if (themeId != 0) {
            return themeId;
        }
        TypedValue outValue = new TypedValue();
        if (context.getTheme().resolveAttribute(C1087R.attr.bottomSheetDialogTheme, outValue, true)) {
            return outValue.resourceId;
        }
        return C1087R.C1093style.Theme_Design_Light_BottomSheetDialog;
    }

    /* access modifiers changed from: package-private */
    public void removeDefaultCallback() {
        this.behavior.removeBottomSheetCallback(this.bottomSheetCallback);
    }

    private static class EdgeToEdgeCallback extends BottomSheetBehavior.BottomSheetCallback {
        private final WindowInsetsCompat insetsCompat;
        private final Boolean lightBottomSheet;
        private boolean lightStatusBar;
        private Window window;

        private EdgeToEdgeCallback(View bottomSheet, WindowInsetsCompat insetsCompat2) {
            ColorStateList backgroundTint;
            this.insetsCompat = insetsCompat2;
            MaterialShapeDrawable msd = BottomSheetBehavior.from(bottomSheet).getMaterialShapeDrawable();
            if (msd != null) {
                backgroundTint = msd.getFillColor();
            } else {
                backgroundTint = ViewCompat.getBackgroundTintList(bottomSheet);
            }
            if (backgroundTint != null) {
                this.lightBottomSheet = Boolean.valueOf(MaterialColors.isColorLight(backgroundTint.getDefaultColor()));
            } else if (bottomSheet.getBackground() instanceof ColorDrawable) {
                this.lightBottomSheet = Boolean.valueOf(MaterialColors.isColorLight(((ColorDrawable) bottomSheet.getBackground()).getColor()));
            } else {
                this.lightBottomSheet = null;
            }
        }

        public void onStateChanged(View bottomSheet, int newState) {
            setPaddingForPosition(bottomSheet);
        }

        public void onSlide(View bottomSheet, float slideOffset) {
            setPaddingForPosition(bottomSheet);
        }

        /* access modifiers changed from: package-private */
        public void onLayout(View bottomSheet) {
            setPaddingForPosition(bottomSheet);
        }

        /* access modifiers changed from: package-private */
        public void setWindow(Window window2) {
            if (this.window != window2) {
                this.window = window2;
                if (window2 != null) {
                    this.lightStatusBar = WindowCompat.getInsetsController(window2, window2.getDecorView()).isAppearanceLightStatusBars();
                }
            }
        }

        private void setPaddingForPosition(View bottomSheet) {
            if (bottomSheet.getTop() < this.insetsCompat.getSystemWindowInsetTop()) {
                Window window2 = this.window;
                if (window2 != null) {
                    Boolean bool = this.lightBottomSheet;
                    EdgeToEdgeUtils.setLightStatusBar(window2, bool == null ? this.lightStatusBar : bool.booleanValue());
                }
                bottomSheet.setPadding(bottomSheet.getPaddingLeft(), this.insetsCompat.getSystemWindowInsetTop() - bottomSheet.getTop(), bottomSheet.getPaddingRight(), bottomSheet.getPaddingBottom());
            } else if (bottomSheet.getTop() != 0) {
                Window window3 = this.window;
                if (window3 != null) {
                    EdgeToEdgeUtils.setLightStatusBar(window3, this.lightStatusBar);
                }
                bottomSheet.setPadding(bottomSheet.getPaddingLeft(), 0, bottomSheet.getPaddingRight(), bottomSheet.getPaddingBottom());
            }
        }
    }

    @Deprecated
    public static void setLightStatusBar(View view, boolean isLight) {
        int flags;
        if (Build.VERSION.SDK_INT >= 23) {
            int flags2 = view.getSystemUiVisibility();
            if (isLight) {
                flags = flags2 | 8192;
            } else {
                flags = flags2 & -8193;
            }
            view.setSystemUiVisibility(flags);
        }
    }
}
