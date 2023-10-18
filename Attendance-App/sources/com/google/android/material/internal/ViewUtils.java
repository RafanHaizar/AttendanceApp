package com.google.android.material.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import com.google.android.material.C1087R;
import java.util.ArrayList;
import java.util.List;

public class ViewUtils {
    public static final int EDGE_TO_EDGE_FLAGS = 768;

    public interface OnApplyWindowInsetsListener {
        WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat, RelativePadding relativePadding);
    }

    private ViewUtils() {
    }

    public static void showKeyboard(View view) {
        showKeyboard(view, true);
    }

    public static void showKeyboard(View view, boolean useWindowInsetsController) {
        WindowInsetsControllerCompat windowController;
        if (!useWindowInsetsController || (windowController = ViewCompat.getWindowInsetsController(view)) == null) {
            getInputMethodManager(view).showSoftInput(view, 1);
        } else {
            windowController.show(WindowInsetsCompat.Type.ime());
        }
    }

    public static void hideKeyboard(View view) {
        hideKeyboard(view, true);
    }

    public static void hideKeyboard(View view, boolean useWindowInsetsController) {
        WindowInsetsControllerCompat windowController;
        if (!useWindowInsetsController || (windowController = ViewCompat.getWindowInsetsController(view)) == null) {
            InputMethodManager imm = getInputMethodManager(view);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                return;
            }
            return;
        }
        windowController.hide(WindowInsetsCompat.Type.ime());
    }

    private static InputMethodManager getInputMethodManager(View view) {
        return (InputMethodManager) ContextCompat.getSystemService(view.getContext(), InputMethodManager.class);
    }

    public static void setBoundsFromRect(View view, Rect rect) {
        view.setLeft(rect.left);
        view.setTop(rect.top);
        view.setRight(rect.right);
        view.setBottom(rect.bottom);
    }

    public static Rect calculateRectFromBounds(View view) {
        return calculateRectFromBounds(view, 0);
    }

    public static Rect calculateRectFromBounds(View view, int offsetY) {
        return new Rect(view.getLeft(), view.getTop() + offsetY, view.getRight(), view.getBottom() + offsetY);
    }

    public static List<View> getChildren(View view) {
        List<View> children = new ArrayList<>();
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                children.add(viewGroup.getChildAt(i));
            }
        }
        return children;
    }

    public static PorterDuff.Mode parseTintMode(int value, PorterDuff.Mode defaultMode) {
        switch (value) {
            case 3:
                return PorterDuff.Mode.SRC_OVER;
            case 5:
                return PorterDuff.Mode.SRC_IN;
            case 9:
                return PorterDuff.Mode.SRC_ATOP;
            case 14:
                return PorterDuff.Mode.MULTIPLY;
            case 15:
                return PorterDuff.Mode.SCREEN;
            case 16:
                return PorterDuff.Mode.ADD;
            default:
                return defaultMode;
        }
    }

    public static boolean isLayoutRtl(View view) {
        return ViewCompat.getLayoutDirection(view) == 1;
    }

    public static float dpToPx(Context context, int dp) {
        return TypedValue.applyDimension(1, (float) dp, context.getResources().getDisplayMetrics());
    }

    public static void requestFocusAndShowKeyboard(final View view) {
        view.requestFocus();
        view.post(new Runnable() {
            public void run() {
                ((InputMethodManager) view.getContext().getSystemService("input_method")).showSoftInput(view, 1);
            }
        });
    }

    public static class RelativePadding {
        public int bottom;
        public int end;
        public int start;
        public int top;

        public RelativePadding(int start2, int top2, int end2, int bottom2) {
            this.start = start2;
            this.top = top2;
            this.end = end2;
            this.bottom = bottom2;
        }

        public RelativePadding(RelativePadding other) {
            this.start = other.start;
            this.top = other.top;
            this.end = other.end;
            this.bottom = other.bottom;
        }

        public void applyToView(View view) {
            ViewCompat.setPaddingRelative(view, this.start, this.top, this.end, this.bottom);
        }
    }

    public static void doOnApplyWindowInsets(View view, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        doOnApplyWindowInsets(view, attrs, defStyleAttr, defStyleRes, (OnApplyWindowInsetsListener) null);
    }

    public static void doOnApplyWindowInsets(View view, AttributeSet attrs, int defStyleAttr, int defStyleRes, final OnApplyWindowInsetsListener listener) {
        TypedArray a = view.getContext().obtainStyledAttributes(attrs, C1087R.styleable.Insets, defStyleAttr, defStyleRes);
        final boolean paddingBottomSystemWindowInsets = a.getBoolean(C1087R.styleable.Insets_paddingBottomSystemWindowInsets, false);
        final boolean paddingLeftSystemWindowInsets = a.getBoolean(C1087R.styleable.Insets_paddingLeftSystemWindowInsets, false);
        final boolean paddingRightSystemWindowInsets = a.getBoolean(C1087R.styleable.Insets_paddingRightSystemWindowInsets, false);
        a.recycle();
        doOnApplyWindowInsets(view, new OnApplyWindowInsetsListener() {
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets, RelativePadding initialPadding) {
                if (paddingBottomSystemWindowInsets) {
                    initialPadding.bottom += insets.getSystemWindowInsetBottom();
                }
                boolean isRtl = ViewUtils.isLayoutRtl(view);
                if (paddingLeftSystemWindowInsets) {
                    if (isRtl) {
                        initialPadding.end += insets.getSystemWindowInsetLeft();
                    } else {
                        initialPadding.start += insets.getSystemWindowInsetLeft();
                    }
                }
                if (paddingRightSystemWindowInsets) {
                    if (isRtl) {
                        initialPadding.start += insets.getSystemWindowInsetRight();
                    } else {
                        initialPadding.end += insets.getSystemWindowInsetRight();
                    }
                }
                initialPadding.applyToView(view);
                OnApplyWindowInsetsListener onApplyWindowInsetsListener = listener;
                if (onApplyWindowInsetsListener != null) {
                    return onApplyWindowInsetsListener.onApplyWindowInsets(view, insets, initialPadding);
                }
                return insets;
            }
        });
    }

    public static void doOnApplyWindowInsets(View view, final OnApplyWindowInsetsListener listener) {
        final RelativePadding initialPadding = new RelativePadding(ViewCompat.getPaddingStart(view), view.getPaddingTop(), ViewCompat.getPaddingEnd(view), view.getPaddingBottom());
        ViewCompat.setOnApplyWindowInsetsListener(view, new androidx.core.view.OnApplyWindowInsetsListener() {
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat insets) {
                return OnApplyWindowInsetsListener.this.onApplyWindowInsets(view, insets, new RelativePadding(initialPadding));
            }
        });
        requestApplyInsetsWhenAttached(view);
    }

    public static void requestApplyInsetsWhenAttached(View view) {
        if (ViewCompat.isAttachedToWindow(view)) {
            ViewCompat.requestApplyInsets(view);
        } else {
            view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                public void onViewAttachedToWindow(View v) {
                    v.removeOnAttachStateChangeListener(this);
                    ViewCompat.requestApplyInsets(v);
                }

                public void onViewDetachedFromWindow(View v) {
                }
            });
        }
    }

    public static float getParentAbsoluteElevation(View view) {
        float absoluteElevation = 0.0f;
        for (ViewParent viewParent = view.getParent(); viewParent instanceof View; viewParent = viewParent.getParent()) {
            absoluteElevation += ViewCompat.getElevation((View) viewParent);
        }
        return absoluteElevation;
    }

    public static ViewOverlayImpl getOverlay(View view) {
        if (view == null) {
            return null;
        }
        return new ViewOverlayApi18(view);
    }

    public static ViewGroup getContentView(View view) {
        if (view == null) {
            return null;
        }
        View rootView = view.getRootView();
        ViewGroup contentView = (ViewGroup) rootView.findViewById(16908290);
        if (contentView != null) {
            return contentView;
        }
        if (rootView == view || !(rootView instanceof ViewGroup)) {
            return null;
        }
        return (ViewGroup) rootView;
    }

    public static ViewOverlayImpl getContentViewOverlay(View view) {
        return getOverlay(getContentView(view));
    }

    public static void addOnGlobalLayoutListener(View view, ViewTreeObserver.OnGlobalLayoutListener victim) {
        if (view != null) {
            view.getViewTreeObserver().addOnGlobalLayoutListener(victim);
        }
    }

    public static void removeOnGlobalLayoutListener(View view, ViewTreeObserver.OnGlobalLayoutListener victim) {
        if (view != null) {
            removeOnGlobalLayoutListener(view.getViewTreeObserver(), victim);
        }
    }

    public static void removeOnGlobalLayoutListener(ViewTreeObserver viewTreeObserver, ViewTreeObserver.OnGlobalLayoutListener victim) {
        viewTreeObserver.removeOnGlobalLayoutListener(victim);
    }

    public static Integer getBackgroundColor(View view) {
        if (view.getBackground() instanceof ColorDrawable) {
            return Integer.valueOf(((ColorDrawable) view.getBackground()).getColor());
        }
        return null;
    }
}
