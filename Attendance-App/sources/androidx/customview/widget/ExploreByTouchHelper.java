package androidx.customview.widget;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import androidx.collection.SparseArrayCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityEventCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeProviderCompat;
import androidx.core.view.accessibility.AccessibilityRecordCompat;
import androidx.customview.widget.FocusStrategy;
import java.util.ArrayList;
import java.util.List;

public abstract class ExploreByTouchHelper extends AccessibilityDelegateCompat {
    private static final String DEFAULT_CLASS_NAME = "android.view.View";
    public static final int HOST_ID = -1;
    public static final int INVALID_ID = Integer.MIN_VALUE;
    private static final Rect INVALID_PARENT_BOUNDS = new Rect(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    private static final FocusStrategy.BoundsAdapter<AccessibilityNodeInfoCompat> NODE_ADAPTER = new FocusStrategy.BoundsAdapter<AccessibilityNodeInfoCompat>() {
        public void obtainBounds(AccessibilityNodeInfoCompat node, Rect outBounds) {
            node.getBoundsInParent(outBounds);
        }
    };
    private static final FocusStrategy.CollectionAdapter<SparseArrayCompat<AccessibilityNodeInfoCompat>, AccessibilityNodeInfoCompat> SPARSE_VALUES_ADAPTER = new FocusStrategy.CollectionAdapter<SparseArrayCompat<AccessibilityNodeInfoCompat>, AccessibilityNodeInfoCompat>() {
        public AccessibilityNodeInfoCompat get(SparseArrayCompat<AccessibilityNodeInfoCompat> collection, int index) {
            return collection.valueAt(index);
        }

        public int size(SparseArrayCompat<AccessibilityNodeInfoCompat> collection) {
            return collection.size();
        }
    };
    int mAccessibilityFocusedVirtualViewId = Integer.MIN_VALUE;
    private final View mHost;
    private int mHoveredVirtualViewId = Integer.MIN_VALUE;
    int mKeyboardFocusedVirtualViewId = Integer.MIN_VALUE;
    private final AccessibilityManager mManager;
    private MyNodeProvider mNodeProvider;
    private final int[] mTempGlobalRect = new int[2];
    private final Rect mTempParentRect = new Rect();
    private final Rect mTempScreenRect = new Rect();
    private final Rect mTempVisibleRect = new Rect();

    /* access modifiers changed from: protected */
    public abstract int getVirtualViewAt(float f, float f2);

    /* access modifiers changed from: protected */
    public abstract void getVisibleVirtualViews(List<Integer> list);

    /* access modifiers changed from: protected */
    public abstract boolean onPerformActionForVirtualView(int i, int i2, Bundle bundle);

    /* access modifiers changed from: protected */
    public abstract void onPopulateNodeForVirtualView(int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat);

    public ExploreByTouchHelper(View host) {
        if (host != null) {
            this.mHost = host;
            this.mManager = (AccessibilityManager) host.getContext().getSystemService("accessibility");
            host.setFocusable(true);
            if (ViewCompat.getImportantForAccessibility(host) == 0) {
                ViewCompat.setImportantForAccessibility(host, 1);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("View may not be null");
    }

    public AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View host) {
        if (this.mNodeProvider == null) {
            this.mNodeProvider = new MyNodeProvider();
        }
        return this.mNodeProvider;
    }

    public final boolean dispatchHoverEvent(MotionEvent event) {
        if (!this.mManager.isEnabled() || !this.mManager.isTouchExplorationEnabled()) {
            return false;
        }
        switch (event.getAction()) {
            case 7:
            case 9:
                int virtualViewId = getVirtualViewAt(event.getX(), event.getY());
                updateHoveredVirtualView(virtualViewId);
                if (virtualViewId != Integer.MIN_VALUE) {
                    return true;
                }
                return false;
            case 10:
                if (this.mHoveredVirtualViewId == Integer.MIN_VALUE) {
                    return false;
                }
                updateHoveredVirtualView(Integer.MIN_VALUE);
                return true;
            default:
                return false;
        }
    }

    public final boolean dispatchKeyEvent(KeyEvent event) {
        boolean handled = false;
        if (event.getAction() == 1) {
            return false;
        }
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case 19:
            case 20:
            case 21:
            case 22:
                if (!event.hasNoModifiers()) {
                    return false;
                }
                int direction = keyToDirection(keyCode);
                int count = event.getRepeatCount() + 1;
                for (int i = 0; i < count && moveFocus(direction, (Rect) null); i++) {
                    handled = true;
                }
                return handled;
            case 23:
            case 66:
                if (!event.hasNoModifiers() || event.getRepeatCount() != 0) {
                    return false;
                }
                clickKeyboardFocusedVirtualView();
                return true;
            case 61:
                if (event.hasNoModifiers()) {
                    return moveFocus(2, (Rect) null);
                }
                if (event.hasModifiers(1)) {
                    return moveFocus(1, (Rect) null);
                }
                return false;
            default:
                return false;
        }
    }

    public final void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        int i = this.mKeyboardFocusedVirtualViewId;
        if (i != Integer.MIN_VALUE) {
            clearKeyboardFocusForVirtualView(i);
        }
        if (gainFocus) {
            moveFocus(direction, previouslyFocusedRect);
        }
    }

    public final int getAccessibilityFocusedVirtualViewId() {
        return this.mAccessibilityFocusedVirtualViewId;
    }

    public final int getKeyboardFocusedVirtualViewId() {
        return this.mKeyboardFocusedVirtualViewId;
    }

    private static int keyToDirection(int keyCode) {
        switch (keyCode) {
            case 19:
                return 33;
            case 21:
                return 17;
            case 22:
                return 66;
            default:
                return 130;
        }
    }

    private void getBoundsInParent(int virtualViewId, Rect outBounds) {
        obtainAccessibilityNodeInfo(virtualViewId).getBoundsInParent(outBounds);
    }

    private boolean moveFocus(int direction, Rect previouslyFocusedRect) {
        AccessibilityNodeInfoCompat nextFocusedNode;
        int index;
        SparseArrayCompat<AccessibilityNodeInfoCompat> allNodes = getAllNodes();
        int focusedNodeId = this.mKeyboardFocusedVirtualViewId;
        AccessibilityNodeInfoCompat focusedNode = focusedNodeId == Integer.MIN_VALUE ? null : allNodes.get(focusedNodeId);
        switch (direction) {
            case 1:
            case 2:
                nextFocusedNode = (AccessibilityNodeInfoCompat) FocusStrategy.findNextFocusInRelativeDirection(allNodes, SPARSE_VALUES_ADAPTER, NODE_ADAPTER, focusedNode, direction, ViewCompat.getLayoutDirection(this.mHost) == 1, false);
                break;
            case 17:
            case 33:
            case 66:
            case 130:
                Rect selectedRect = new Rect();
                int i = this.mKeyboardFocusedVirtualViewId;
                if (i != Integer.MIN_VALUE) {
                    getBoundsInParent(i, selectedRect);
                } else if (previouslyFocusedRect != null) {
                    selectedRect.set(previouslyFocusedRect);
                } else {
                    guessPreviouslyFocusedRect(this.mHost, direction, selectedRect);
                }
                nextFocusedNode = (AccessibilityNodeInfoCompat) FocusStrategy.findNextFocusInAbsoluteDirection(allNodes, SPARSE_VALUES_ADAPTER, NODE_ADAPTER, focusedNode, selectedRect, direction);
                break;
            default:
                throw new IllegalArgumentException("direction must be one of {FOCUS_FORWARD, FOCUS_BACKWARD, FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
        if (nextFocusedNode == null) {
            index = Integer.MIN_VALUE;
        } else {
            index = allNodes.keyAt(allNodes.indexOfValue(nextFocusedNode));
        }
        return requestKeyboardFocusForVirtualView(index);
    }

    private SparseArrayCompat<AccessibilityNodeInfoCompat> getAllNodes() {
        List<Integer> virtualViewIds = new ArrayList<>();
        getVisibleVirtualViews(virtualViewIds);
        SparseArrayCompat<AccessibilityNodeInfoCompat> allNodes = new SparseArrayCompat<>();
        for (int virtualViewIdIndex = 0; virtualViewIdIndex < virtualViewIds.size(); virtualViewIdIndex++) {
            allNodes.put(virtualViewIds.get(virtualViewIdIndex).intValue(), createNodeForChild(virtualViewIds.get(virtualViewIdIndex).intValue()));
        }
        return allNodes;
    }

    private static Rect guessPreviouslyFocusedRect(View host, int direction, Rect outBounds) {
        int w = host.getWidth();
        int h = host.getHeight();
        switch (direction) {
            case 17:
                outBounds.set(w, 0, w, h);
                break;
            case 33:
                outBounds.set(0, h, w, h);
                break;
            case 66:
                outBounds.set(-1, 0, -1, h);
                break;
            case 130:
                outBounds.set(0, -1, w, -1);
                break;
            default:
                throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
        return outBounds;
    }

    private boolean clickKeyboardFocusedVirtualView() {
        int i = this.mKeyboardFocusedVirtualViewId;
        return i != Integer.MIN_VALUE && onPerformActionForVirtualView(i, 16, (Bundle) null);
    }

    public final boolean sendEventForVirtualView(int virtualViewId, int eventType) {
        ViewParent parent;
        if (virtualViewId == Integer.MIN_VALUE || !this.mManager.isEnabled() || (parent = this.mHost.getParent()) == null) {
            return false;
        }
        return parent.requestSendAccessibilityEvent(this.mHost, createEvent(virtualViewId, eventType));
    }

    public final void invalidateRoot() {
        invalidateVirtualView(-1, 1);
    }

    public final void invalidateVirtualView(int virtualViewId) {
        invalidateVirtualView(virtualViewId, 0);
    }

    public final void invalidateVirtualView(int virtualViewId, int changeTypes) {
        ViewParent parent;
        if (virtualViewId != Integer.MIN_VALUE && this.mManager.isEnabled() && (parent = this.mHost.getParent()) != null) {
            AccessibilityEvent event = createEvent(virtualViewId, 2048);
            AccessibilityEventCompat.setContentChangeTypes(event, changeTypes);
            parent.requestSendAccessibilityEvent(this.mHost, event);
        }
    }

    @Deprecated
    public int getFocusedVirtualView() {
        return getAccessibilityFocusedVirtualViewId();
    }

    /* access modifiers changed from: protected */
    public void onVirtualViewKeyboardFocusChanged(int virtualViewId, boolean hasFocus) {
    }

    private void updateHoveredVirtualView(int virtualViewId) {
        if (this.mHoveredVirtualViewId != virtualViewId) {
            int previousVirtualViewId = this.mHoveredVirtualViewId;
            this.mHoveredVirtualViewId = virtualViewId;
            sendEventForVirtualView(virtualViewId, 128);
            sendEventForVirtualView(previousVirtualViewId, 256);
        }
    }

    private AccessibilityEvent createEvent(int virtualViewId, int eventType) {
        switch (virtualViewId) {
            case -1:
                return createEventForHost(eventType);
            default:
                return createEventForChild(virtualViewId, eventType);
        }
    }

    private AccessibilityEvent createEventForHost(int eventType) {
        AccessibilityEvent event = AccessibilityEvent.obtain(eventType);
        this.mHost.onInitializeAccessibilityEvent(event);
        return event;
    }

    public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(host, event);
        onPopulateEventForHost(event);
    }

    private AccessibilityEvent createEventForChild(int virtualViewId, int eventType) {
        AccessibilityEvent event = AccessibilityEvent.obtain(eventType);
        AccessibilityNodeInfoCompat node = obtainAccessibilityNodeInfo(virtualViewId);
        event.getText().add(node.getText());
        event.setContentDescription(node.getContentDescription());
        event.setScrollable(node.isScrollable());
        event.setPassword(node.isPassword());
        event.setEnabled(node.isEnabled());
        event.setChecked(node.isChecked());
        onPopulateEventForVirtualView(virtualViewId, event);
        if (!event.getText().isEmpty() || event.getContentDescription() != null) {
            event.setClassName(node.getClassName());
            AccessibilityRecordCompat.setSource(event, this.mHost, virtualViewId);
            event.setPackageName(this.mHost.getContext().getPackageName());
            return event;
        }
        throw new RuntimeException("Callbacks must add text or a content description in populateEventForVirtualViewId()");
    }

    /* access modifiers changed from: package-private */
    public AccessibilityNodeInfoCompat obtainAccessibilityNodeInfo(int virtualViewId) {
        if (virtualViewId == -1) {
            return createNodeForHost();
        }
        return createNodeForChild(virtualViewId);
    }

    private AccessibilityNodeInfoCompat createNodeForHost() {
        AccessibilityNodeInfoCompat info = AccessibilityNodeInfoCompat.obtain(this.mHost);
        ViewCompat.onInitializeAccessibilityNodeInfo(this.mHost, info);
        ArrayList<Integer> virtualViewIds = new ArrayList<>();
        getVisibleVirtualViews(virtualViewIds);
        if (info.getChildCount() <= 0 || virtualViewIds.size() <= 0) {
            int count = virtualViewIds.size();
            for (int i = 0; i < count; i++) {
                info.addChild(this.mHost, virtualViewIds.get(i).intValue());
            }
            return info;
        }
        throw new RuntimeException("Views cannot have both real and virtual children");
    }

    public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
        super.onInitializeAccessibilityNodeInfo(host, info);
        onPopulateNodeForHost(info);
    }

    private AccessibilityNodeInfoCompat createNodeForChild(int virtualViewId) {
        AccessibilityNodeInfoCompat node = AccessibilityNodeInfoCompat.obtain();
        node.setEnabled(true);
        node.setFocusable(true);
        node.setClassName(DEFAULT_CLASS_NAME);
        Rect rect = INVALID_PARENT_BOUNDS;
        node.setBoundsInParent(rect);
        node.setBoundsInScreen(rect);
        node.setParent(this.mHost);
        onPopulateNodeForVirtualView(virtualViewId, node);
        if (node.getText() == null && node.getContentDescription() == null) {
            throw new RuntimeException("Callbacks must add text or a content description in populateNodeForVirtualViewId()");
        }
        node.getBoundsInParent(this.mTempParentRect);
        if (!this.mTempParentRect.equals(rect)) {
            int actions = node.getActions();
            if ((actions & 64) != 0) {
                throw new RuntimeException("Callbacks must not add ACTION_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
            } else if ((actions & 128) == 0) {
                node.setPackageName(this.mHost.getContext().getPackageName());
                node.setSource(this.mHost, virtualViewId);
                if (this.mAccessibilityFocusedVirtualViewId == virtualViewId) {
                    node.setAccessibilityFocused(true);
                    node.addAction(128);
                } else {
                    node.setAccessibilityFocused(false);
                    node.addAction(64);
                }
                boolean isFocused = this.mKeyboardFocusedVirtualViewId == virtualViewId;
                if (isFocused) {
                    node.addAction(2);
                } else if (node.isFocusable()) {
                    node.addAction(1);
                }
                node.setFocused(isFocused);
                this.mHost.getLocationOnScreen(this.mTempGlobalRect);
                node.getBoundsInScreen(this.mTempScreenRect);
                if (this.mTempScreenRect.equals(rect)) {
                    node.getBoundsInParent(this.mTempScreenRect);
                    if (node.mParentVirtualDescendantId != -1) {
                        AccessibilityNodeInfoCompat parentNode = AccessibilityNodeInfoCompat.obtain();
                        for (int virtualDescendantId = node.mParentVirtualDescendantId; virtualDescendantId != -1; virtualDescendantId = parentNode.mParentVirtualDescendantId) {
                            parentNode.setParent(this.mHost, -1);
                            parentNode.setBoundsInParent(INVALID_PARENT_BOUNDS);
                            onPopulateNodeForVirtualView(virtualDescendantId, parentNode);
                            parentNode.getBoundsInParent(this.mTempParentRect);
                            this.mTempScreenRect.offset(this.mTempParentRect.left, this.mTempParentRect.top);
                        }
                        parentNode.recycle();
                    }
                    this.mTempScreenRect.offset(this.mTempGlobalRect[0] - this.mHost.getScrollX(), this.mTempGlobalRect[1] - this.mHost.getScrollY());
                }
                if (this.mHost.getLocalVisibleRect(this.mTempVisibleRect)) {
                    this.mTempVisibleRect.offset(this.mTempGlobalRect[0] - this.mHost.getScrollX(), this.mTempGlobalRect[1] - this.mHost.getScrollY());
                    if (this.mTempScreenRect.intersect(this.mTempVisibleRect)) {
                        node.setBoundsInScreen(this.mTempScreenRect);
                        if (isVisibleToUser(this.mTempScreenRect)) {
                            node.setVisibleToUser(true);
                        }
                    }
                }
                return node;
            } else {
                throw new RuntimeException("Callbacks must not add ACTION_CLEAR_ACCESSIBILITY_FOCUS in populateNodeForVirtualViewId()");
            }
        } else {
            throw new RuntimeException("Callbacks must set parent bounds in populateNodeForVirtualViewId()");
        }
    }

    /* access modifiers changed from: package-private */
    public boolean performAction(int virtualViewId, int action, Bundle arguments) {
        switch (virtualViewId) {
            case -1:
                return performActionForHost(action, arguments);
            default:
                return performActionForChild(virtualViewId, action, arguments);
        }
    }

    private boolean performActionForHost(int action, Bundle arguments) {
        return ViewCompat.performAccessibilityAction(this.mHost, action, arguments);
    }

    private boolean performActionForChild(int virtualViewId, int action, Bundle arguments) {
        switch (action) {
            case 1:
                return requestKeyboardFocusForVirtualView(virtualViewId);
            case 2:
                return clearKeyboardFocusForVirtualView(virtualViewId);
            case 64:
                return requestAccessibilityFocus(virtualViewId);
            case 128:
                return clearAccessibilityFocus(virtualViewId);
            default:
                return onPerformActionForVirtualView(virtualViewId, action, arguments);
        }
    }

    private boolean isVisibleToUser(Rect localRect) {
        if (localRect == null || localRect.isEmpty() || this.mHost.getWindowVisibility() != 0) {
            return false;
        }
        ViewParent viewParent = this.mHost.getParent();
        while (viewParent instanceof View) {
            View view = (View) viewParent;
            if (view.getAlpha() <= 0.0f || view.getVisibility() != 0) {
                return false;
            }
            viewParent = view.getParent();
        }
        if (viewParent != null) {
            return true;
        }
        return false;
    }

    private boolean requestAccessibilityFocus(int virtualViewId) {
        int i;
        if (!this.mManager.isEnabled() || !this.mManager.isTouchExplorationEnabled() || (i = this.mAccessibilityFocusedVirtualViewId) == virtualViewId) {
            return false;
        }
        if (i != Integer.MIN_VALUE) {
            clearAccessibilityFocus(i);
        }
        this.mAccessibilityFocusedVirtualViewId = virtualViewId;
        this.mHost.invalidate();
        sendEventForVirtualView(virtualViewId, 32768);
        return true;
    }

    private boolean clearAccessibilityFocus(int virtualViewId) {
        if (this.mAccessibilityFocusedVirtualViewId != virtualViewId) {
            return false;
        }
        this.mAccessibilityFocusedVirtualViewId = Integer.MIN_VALUE;
        this.mHost.invalidate();
        sendEventForVirtualView(virtualViewId, 65536);
        return true;
    }

    public final boolean requestKeyboardFocusForVirtualView(int virtualViewId) {
        int i;
        if ((!this.mHost.isFocused() && !this.mHost.requestFocus()) || (i = this.mKeyboardFocusedVirtualViewId) == virtualViewId) {
            return false;
        }
        if (i != Integer.MIN_VALUE) {
            clearKeyboardFocusForVirtualView(i);
        }
        if (virtualViewId == Integer.MIN_VALUE) {
            return false;
        }
        this.mKeyboardFocusedVirtualViewId = virtualViewId;
        onVirtualViewKeyboardFocusChanged(virtualViewId, true);
        sendEventForVirtualView(virtualViewId, 8);
        return true;
    }

    public final boolean clearKeyboardFocusForVirtualView(int virtualViewId) {
        if (this.mKeyboardFocusedVirtualViewId != virtualViewId) {
            return false;
        }
        this.mKeyboardFocusedVirtualViewId = Integer.MIN_VALUE;
        onVirtualViewKeyboardFocusChanged(virtualViewId, false);
        sendEventForVirtualView(virtualViewId, 8);
        return true;
    }

    /* access modifiers changed from: protected */
    public void onPopulateEventForVirtualView(int virtualViewId, AccessibilityEvent event) {
    }

    /* access modifiers changed from: protected */
    public void onPopulateEventForHost(AccessibilityEvent event) {
    }

    /* access modifiers changed from: protected */
    public void onPopulateNodeForHost(AccessibilityNodeInfoCompat node) {
    }

    private class MyNodeProvider extends AccessibilityNodeProviderCompat {
        MyNodeProvider() {
        }

        public AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int virtualViewId) {
            return AccessibilityNodeInfoCompat.obtain(ExploreByTouchHelper.this.obtainAccessibilityNodeInfo(virtualViewId));
        }

        public boolean performAction(int virtualViewId, int action, Bundle arguments) {
            return ExploreByTouchHelper.this.performAction(virtualViewId, action, arguments);
        }

        public AccessibilityNodeInfoCompat findFocus(int focusType) {
            int focusedId = focusType == 2 ? ExploreByTouchHelper.this.mAccessibilityFocusedVirtualViewId : ExploreByTouchHelper.this.mKeyboardFocusedVirtualViewId;
            if (focusedId == Integer.MIN_VALUE) {
                return null;
            }
            return createAccessibilityNodeInfo(focusedId);
        }
    }
}
