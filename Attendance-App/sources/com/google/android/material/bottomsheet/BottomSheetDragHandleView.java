package com.google.android.material.bottomsheet;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import com.google.android.material.C1087R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class BottomSheetDragHandleView extends AppCompatImageView implements AccessibilityManager.AccessibilityStateChangeListener {
    private static final int DEF_STYLE_RES = C1087R.C1093style.Widget_Material3_BottomSheet_DragHandle;
    private final AccessibilityManager accessibilityManager;
    private boolean accessibilityServiceEnabled;
    private BottomSheetBehavior<?> bottomSheetBehavior;
    private final BottomSheetBehavior.BottomSheetCallback bottomSheetCallback;
    private final String clickFeedback;
    private final String clickToCollapseActionLabel;
    private boolean clickToExpand;
    private final String clickToExpandActionLabel;
    private boolean interactable;

    public BottomSheetDragHandleView(Context context) {
        this(context, (AttributeSet) null);
    }

    public BottomSheetDragHandleView(Context context, AttributeSet attrs) {
        this(context, attrs, C1087R.attr.bottomSheetDragHandleStyle);
    }

    public BottomSheetDragHandleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(MaterialThemeOverlay.wrap(context, attrs, defStyleAttr, DEF_STYLE_RES), attrs, defStyleAttr);
        this.clickToExpandActionLabel = getResources().getString(C1087R.string.bottomsheet_action_expand);
        this.clickToCollapseActionLabel = getResources().getString(C1087R.string.bottomsheet_action_collapse);
        this.clickFeedback = getResources().getString(C1087R.string.bottomsheet_drag_handle_clicked);
        this.bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
            public void onStateChanged(View bottomSheet, int newState) {
                BottomSheetDragHandleView.this.onBottomSheetStateChanged(newState);
            }

            public void onSlide(View bottomSheet, float slideOffset) {
            }
        };
        this.accessibilityManager = (AccessibilityManager) getContext().getSystemService("accessibility");
        updateInteractableState();
        ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegateCompat() {
            public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
                super.onPopulateAccessibilityEvent(host, event);
                if (event.getEventType() == 1) {
                    boolean unused = BottomSheetDragHandleView.this.expandOrCollapseBottomSheetIfPossible();
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setBottomSheetBehavior(findParentBottomSheetBehavior());
        AccessibilityManager accessibilityManager2 = this.accessibilityManager;
        if (accessibilityManager2 != null) {
            accessibilityManager2.addAccessibilityStateChangeListener(this);
            onAccessibilityStateChanged(this.accessibilityManager.isEnabled());
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        AccessibilityManager accessibilityManager2 = this.accessibilityManager;
        if (accessibilityManager2 != null) {
            accessibilityManager2.removeAccessibilityStateChangeListener(this);
        }
        setBottomSheetBehavior((BottomSheetBehavior<?>) null);
        super.onDetachedFromWindow();
    }

    public void onAccessibilityStateChanged(boolean enabled) {
        this.accessibilityServiceEnabled = enabled;
        updateInteractableState();
    }

    private void setBottomSheetBehavior(BottomSheetBehavior<?> behavior) {
        BottomSheetBehavior<?> bottomSheetBehavior2 = this.bottomSheetBehavior;
        if (bottomSheetBehavior2 != null) {
            bottomSheetBehavior2.removeBottomSheetCallback(this.bottomSheetCallback);
            this.bottomSheetBehavior.setAccessibilityDelegateView((View) null);
        }
        this.bottomSheetBehavior = behavior;
        if (behavior != null) {
            behavior.setAccessibilityDelegateView(this);
            onBottomSheetStateChanged(this.bottomSheetBehavior.getState());
            this.bottomSheetBehavior.addBottomSheetCallback(this.bottomSheetCallback);
        }
        updateInteractableState();
    }

    /* access modifiers changed from: private */
    public void onBottomSheetStateChanged(int state) {
        if (state == 4) {
            this.clickToExpand = true;
        } else if (state == 3) {
            this.clickToExpand = false;
        }
        ViewCompat.replaceAccessibilityAction(this, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK, this.clickToExpand ? this.clickToExpandActionLabel : this.clickToCollapseActionLabel, new BottomSheetDragHandleView$$ExternalSyntheticLambda0(this));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$onBottomSheetStateChanged$0$com-google-android-material-bottomsheet-BottomSheetDragHandleView */
    public /* synthetic */ boolean mo21306xa7b4c95f(View v, AccessibilityViewCommand.CommandArguments args) {
        return expandOrCollapseBottomSheetIfPossible();
    }

    private void updateInteractableState() {
        int i = 1;
        this.interactable = this.accessibilityServiceEnabled && this.bottomSheetBehavior != null;
        if (this.bottomSheetBehavior == null) {
            i = 2;
        }
        ViewCompat.setImportantForAccessibility(this, i);
        setClickable(this.interactable);
    }

    /* access modifiers changed from: private */
    public boolean expandOrCollapseBottomSheetIfPossible() {
        boolean z = false;
        if (!this.interactable) {
            return false;
        }
        announceAccessibilityEvent(this.clickFeedback);
        if (!this.bottomSheetBehavior.isFitToContents() && !this.bottomSheetBehavior.shouldSkipHalfExpandedStateWhenDragging()) {
            z = true;
        }
        boolean canHalfExpand = z;
        int currentState = this.bottomSheetBehavior.getState();
        int nextState = 6;
        int i = 3;
        if (currentState == 4) {
            if (!canHalfExpand) {
                nextState = 3;
            }
        } else if (currentState != 3) {
            if (this.clickToExpand == 0) {
                i = 4;
            }
            nextState = i;
        } else if (!canHalfExpand) {
            nextState = 4;
        }
        this.bottomSheetBehavior.setState(nextState);
        return true;
    }

    private void announceAccessibilityEvent(String announcement) {
        if (this.accessibilityManager != null) {
            AccessibilityEvent announce = AccessibilityEvent.obtain(16384);
            announce.getText().add(announcement);
            this.accessibilityManager.sendAccessibilityEvent(announce);
        }
    }

    private BottomSheetBehavior<?> findParentBottomSheetBehavior() {
        View parent = this;
        while (true) {
            View parentView = getParentView(parent);
            parent = parentView;
            if (parentView == null) {
                return null;
            }
            ViewGroup.LayoutParams layoutParams = parent.getLayoutParams();
            if (layoutParams instanceof CoordinatorLayout.LayoutParams) {
                CoordinatorLayout.Behavior<?> behavior = ((CoordinatorLayout.LayoutParams) layoutParams).getBehavior();
                if (behavior instanceof BottomSheetBehavior) {
                    return (BottomSheetBehavior) behavior;
                }
            }
        }
    }

    private static View getParentView(View view) {
        ViewParent parent = view.getParent();
        if (parent instanceof View) {
            return (View) parent;
        }
        return null;
    }
}
