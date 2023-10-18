package com.google.android.material.badge;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.material.C1087R;
import com.google.android.material.badge.BadgeState;
import com.google.android.material.internal.ParcelableSparseArray;
import com.google.android.material.internal.ToolbarUtils;

public class BadgeUtils {
    private static final String LOG_TAG = "BadgeUtils";
    public static final boolean USE_COMPAT_PARENT = false;

    private BadgeUtils() {
    }

    public static void updateBadgeBounds(Rect rect, float centerX, float centerY, float halfWidth, float halfHeight) {
        rect.set((int) (centerX - halfWidth), (int) (centerY - halfHeight), (int) (centerX + halfWidth), (int) (centerY + halfHeight));
    }

    public static void attachBadgeDrawable(BadgeDrawable badgeDrawable, View anchor) {
        attachBadgeDrawable(badgeDrawable, anchor, (FrameLayout) null);
    }

    public static void attachBadgeDrawable(BadgeDrawable badgeDrawable, View anchor, FrameLayout customBadgeParent) {
        setBadgeDrawableBounds(badgeDrawable, anchor, customBadgeParent);
        if (badgeDrawable.getCustomBadgeParent() != null) {
            badgeDrawable.getCustomBadgeParent().setForeground(badgeDrawable);
        } else if (!USE_COMPAT_PARENT) {
            anchor.getOverlay().add(badgeDrawable);
        } else {
            throw new IllegalArgumentException("Trying to reference null customBadgeParent");
        }
    }

    public static void attachBadgeDrawable(BadgeDrawable badgeDrawable, Toolbar toolbar, int menuItemId) {
        attachBadgeDrawable(badgeDrawable, toolbar, menuItemId, (FrameLayout) null);
    }

    public static void attachBadgeDrawable(final BadgeDrawable badgeDrawable, final Toolbar toolbar, final int menuItemId, final FrameLayout customBadgeParent) {
        toolbar.post(new Runnable() {
            public void run() {
                ActionMenuItemView menuItemView = ToolbarUtils.getActionMenuItemView(Toolbar.this, menuItemId);
                if (menuItemView != null) {
                    BadgeUtils.setToolbarOffset(badgeDrawable, Toolbar.this.getResources());
                    BadgeUtils.attachBadgeDrawable(badgeDrawable, (View) menuItemView, customBadgeParent);
                    BadgeUtils.attachBadgeContentDescription(badgeDrawable, menuItemView);
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public static void attachBadgeContentDescription(final BadgeDrawable badgeDrawable, View view) {
        if (Build.VERSION.SDK_INT < 29 || !ViewCompat.hasAccessibilityDelegate(view)) {
            ViewCompat.setAccessibilityDelegate(view, new AccessibilityDelegateCompat() {
                public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
                    super.onInitializeAccessibilityNodeInfo(host, info);
                    info.setContentDescription(BadgeDrawable.this.getContentDescription());
                }
            });
        } else {
            ViewCompat.setAccessibilityDelegate(view, new AccessibilityDelegateCompat(view.getAccessibilityDelegate()) {
                public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
                    super.onInitializeAccessibilityNodeInfo(host, info);
                    info.setContentDescription(badgeDrawable.getContentDescription());
                }
            });
        }
    }

    public static void detachBadgeDrawable(BadgeDrawable badgeDrawable, View anchor) {
        if (badgeDrawable != null) {
            if (USE_COMPAT_PARENT || badgeDrawable.getCustomBadgeParent() != null) {
                badgeDrawable.getCustomBadgeParent().setForeground((Drawable) null);
            } else {
                anchor.getOverlay().remove(badgeDrawable);
            }
        }
    }

    public static void detachBadgeDrawable(BadgeDrawable badgeDrawable, Toolbar toolbar, int menuItemId) {
        if (badgeDrawable != null) {
            ActionMenuItemView menuItemView = ToolbarUtils.getActionMenuItemView(toolbar, menuItemId);
            if (menuItemView != null) {
                removeToolbarOffset(badgeDrawable);
                detachBadgeDrawable(badgeDrawable, menuItemView);
                detachBadgeContentDescription(menuItemView);
                return;
            }
            Log.w(LOG_TAG, "Trying to remove badge from a null menuItemView: " + menuItemId);
        }
    }

    private static void detachBadgeContentDescription(View view) {
        if (Build.VERSION.SDK_INT < 29 || !ViewCompat.hasAccessibilityDelegate(view)) {
            ViewCompat.setAccessibilityDelegate(view, (AccessibilityDelegateCompat) null);
        } else {
            ViewCompat.setAccessibilityDelegate(view, new AccessibilityDelegateCompat(view.getAccessibilityDelegate()) {
                public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
                    super.onInitializeAccessibilityNodeInfo(host, info);
                    info.setContentDescription((CharSequence) null);
                }
            });
        }
    }

    static void setToolbarOffset(BadgeDrawable badgeDrawable, Resources resources) {
        badgeDrawable.setAdditionalHorizontalOffset(resources.getDimensionPixelOffset(C1087R.dimen.mtrl_badge_toolbar_action_menu_item_horizontal_offset));
        badgeDrawable.setAdditionalVerticalOffset(resources.getDimensionPixelOffset(C1087R.dimen.mtrl_badge_toolbar_action_menu_item_vertical_offset));
    }

    static void removeToolbarOffset(BadgeDrawable badgeDrawable) {
        badgeDrawable.setAdditionalHorizontalOffset(0);
        badgeDrawable.setAdditionalVerticalOffset(0);
    }

    public static void setBadgeDrawableBounds(BadgeDrawable badgeDrawable, View anchor, FrameLayout compatBadgeParent) {
        Rect badgeBounds = new Rect();
        anchor.getDrawingRect(badgeBounds);
        badgeDrawable.setBounds(badgeBounds);
        badgeDrawable.updateBadgeCoordinates(anchor, compatBadgeParent);
    }

    public static ParcelableSparseArray createParcelableBadgeStates(SparseArray<BadgeDrawable> badgeDrawables) {
        ParcelableSparseArray badgeStates = new ParcelableSparseArray();
        int i = 0;
        while (i < badgeDrawables.size()) {
            int key = badgeDrawables.keyAt(i);
            BadgeDrawable badgeDrawable = badgeDrawables.valueAt(i);
            if (badgeDrawable != null) {
                badgeStates.put(key, badgeDrawable.getSavedState());
                i++;
            } else {
                throw new IllegalArgumentException("badgeDrawable cannot be null");
            }
        }
        return badgeStates;
    }

    public static SparseArray<BadgeDrawable> createBadgeDrawablesFromSavedStates(Context context, ParcelableSparseArray badgeStates) {
        SparseArray<BadgeDrawable> badgeDrawables = new SparseArray<>(badgeStates.size());
        int i = 0;
        while (i < badgeStates.size()) {
            int key = badgeStates.keyAt(i);
            BadgeState.State savedState = (BadgeState.State) badgeStates.valueAt(i);
            if (savedState != null) {
                badgeDrawables.put(key, BadgeDrawable.createFromSavedState(context, savedState));
                i++;
            } else {
                throw new IllegalArgumentException("BadgeDrawable's savedState cannot be null");
            }
        }
        return badgeDrawables;
    }
}
