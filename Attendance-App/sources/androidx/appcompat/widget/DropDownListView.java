package androidx.appcompat.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import androidx.appcompat.C0503R;
import androidx.appcompat.graphics.drawable.DrawableWrapperCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.p001os.BuildCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import androidx.core.widget.ListViewAutoScrollHelper;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class DropDownListView extends ListView {
    public static final int INVALID_POSITION = -1;
    public static final int NO_POSITION = -1;
    private ViewPropertyAnimatorCompat mClickAnimation;
    private boolean mDrawsInPressedState;
    private boolean mHijackFocus;
    private boolean mListSelectionHidden;
    private int mMotionPosition;
    ResolveHoverRunnable mResolveHoverRunnable;
    private ListViewAutoScrollHelper mScrollHelper;
    private int mSelectionBottomPadding = 0;
    private int mSelectionLeftPadding = 0;
    private int mSelectionRightPadding = 0;
    private int mSelectionTopPadding = 0;
    private GateKeeperDrawable mSelector;
    private final Rect mSelectorRect = new Rect();

    DropDownListView(Context context, boolean hijackFocus) {
        super(context, (AttributeSet) null, C0503R.attr.dropDownListViewStyle);
        this.mHijackFocus = hijackFocus;
        setCacheColorHint(0);
    }

    private boolean superIsSelectedChildViewEnabled() {
        if (BuildCompat.isAtLeastT()) {
            return Api33Impl.isSelectedChildViewEnabled(this);
        }
        return PreApi33Impl.isSelectedChildViewEnabled(this);
    }

    private void superSetSelectedChildViewEnabled(boolean enabled) {
        if (BuildCompat.isAtLeastT()) {
            Api33Impl.setSelectedChildViewEnabled(this, enabled);
        } else {
            PreApi33Impl.setSelectedChildViewEnabled(this, enabled);
        }
    }

    public boolean isInTouchMode() {
        return (this.mHijackFocus && this.mListSelectionHidden) || super.isInTouchMode();
    }

    public boolean hasWindowFocus() {
        return this.mHijackFocus || super.hasWindowFocus();
    }

    public boolean isFocused() {
        return this.mHijackFocus || super.isFocused();
    }

    public boolean hasFocus() {
        return this.mHijackFocus || super.hasFocus();
    }

    public void setSelector(Drawable sel) {
        GateKeeperDrawable gateKeeperDrawable = sel != null ? new GateKeeperDrawable(sel) : null;
        this.mSelector = gateKeeperDrawable;
        super.setSelector(gateKeeperDrawable);
        Rect padding = new Rect();
        if (sel != null) {
            sel.getPadding(padding);
        }
        this.mSelectionLeftPadding = padding.left;
        this.mSelectionTopPadding = padding.top;
        this.mSelectionRightPadding = padding.right;
        this.mSelectionBottomPadding = padding.bottom;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        if (this.mResolveHoverRunnable == null) {
            super.drawableStateChanged();
            setSelectorEnabled(true);
            updateSelectorStateCompat();
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        drawSelectorCompat(canvas);
        super.dispatchDraw(canvas);
    }

    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case 0:
                this.mMotionPosition = pointToPosition((int) ev.getX(), (int) ev.getY());
                break;
        }
        ResolveHoverRunnable resolveHoverRunnable = this.mResolveHoverRunnable;
        if (resolveHoverRunnable != null) {
            resolveHoverRunnable.cancel();
        }
        return super.onTouchEvent(ev);
    }

    public int lookForSelectablePosition(int position, boolean lookDown) {
        int position2;
        ListAdapter adapter = getAdapter();
        if (adapter == null || isInTouchMode()) {
            return -1;
        }
        int count = adapter.getCount();
        if (!getAdapter().areAllItemsEnabled()) {
            if (lookDown) {
                position2 = Math.max(0, position);
                while (position2 < count && !adapter.isEnabled(position2)) {
                    position2++;
                }
            } else {
                int position3 = Math.min(position, count - 1);
                while (position2 >= 0 && !adapter.isEnabled(position2)) {
                    position3 = position2 - 1;
                }
            }
            if (position2 < 0 || position2 >= count) {
                return -1;
            }
            return position2;
        } else if (position < 0 || position >= count) {
            return -1;
        } else {
            return position;
        }
    }

    public int measureHeightOfChildrenCompat(int widthMeasureSpec, int startPosition, int endPosition, int maxHeight, int disallowPartialChildPosition) {
        int paddingTop;
        ViewGroup.LayoutParams childLp;
        int heightMeasureSpec;
        int i = maxHeight;
        int i2 = disallowPartialChildPosition;
        int paddingTop2 = getListPaddingTop();
        int paddingBottom = getListPaddingBottom();
        int reportedDividerHeight = getDividerHeight();
        Drawable divider = getDivider();
        ListAdapter adapter = getAdapter();
        if (adapter == null) {
            return paddingTop2 + paddingBottom;
        }
        int returnedHeight = paddingTop2 + paddingBottom;
        int dividerHeight = (reportedDividerHeight <= 0 || divider == null) ? 0 : reportedDividerHeight;
        int prevHeightWithoutPartialChild = 0;
        View child = null;
        int viewType = 0;
        int count = adapter.getCount();
        int i3 = 0;
        while (i3 < count) {
            int newType = adapter.getItemViewType(i3);
            if (newType != viewType) {
                child = null;
                viewType = newType;
            }
            child = adapter.getView(i3, child, this);
            ViewGroup.LayoutParams childLp2 = child.getLayoutParams();
            if (childLp2 == null) {
                paddingTop = paddingTop2;
                childLp = generateDefaultLayoutParams();
                child.setLayoutParams(childLp);
            } else {
                paddingTop = paddingTop2;
                childLp = childLp2;
            }
            int paddingBottom2 = paddingBottom;
            if (childLp.height > 0) {
                ViewGroup.LayoutParams layoutParams = childLp;
                heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(childLp.height, 1073741824);
            } else {
                heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
            }
            child.measure(widthMeasureSpec, heightMeasureSpec);
            child.forceLayout();
            if (i3 > 0) {
                returnedHeight += dividerHeight;
            }
            returnedHeight += child.getMeasuredHeight();
            if (returnedHeight < i) {
                if (i2 >= 0 && i3 >= i2) {
                    prevHeightWithoutPartialChild = returnedHeight;
                }
                i3++;
                paddingBottom = paddingBottom2;
                paddingTop2 = paddingTop;
            } else if (i2 < 0 || i3 <= i2 || prevHeightWithoutPartialChild <= 0 || returnedHeight == i) {
                return i;
            } else {
                return prevHeightWithoutPartialChild;
            }
        }
        return returnedHeight;
    }

    private void setSelectorEnabled(boolean enabled) {
        GateKeeperDrawable gateKeeperDrawable = this.mSelector;
        if (gateKeeperDrawable != null) {
            gateKeeperDrawable.setEnabled(enabled);
        }
    }

    private static class GateKeeperDrawable extends DrawableWrapperCompat {
        private boolean mEnabled = true;

        GateKeeperDrawable(Drawable drawable) {
            super(drawable);
        }

        /* access modifiers changed from: package-private */
        public void setEnabled(boolean enabled) {
            this.mEnabled = enabled;
        }

        public boolean setState(int[] stateSet) {
            if (this.mEnabled) {
                return super.setState(stateSet);
            }
            return false;
        }

        public void draw(Canvas canvas) {
            if (this.mEnabled) {
                super.draw(canvas);
            }
        }

        public void setHotspot(float x, float y) {
            if (this.mEnabled) {
                super.setHotspot(x, y);
            }
        }

        public void setHotspotBounds(int left, int top, int right, int bottom) {
            if (this.mEnabled) {
                super.setHotspotBounds(left, top, right, bottom);
            }
        }

        public boolean setVisible(boolean visible, boolean restart) {
            if (this.mEnabled) {
                return super.setVisible(visible, restart);
            }
            return false;
        }
    }

    public boolean onHoverEvent(MotionEvent ev) {
        if (Build.VERSION.SDK_INT < 26) {
            return super.onHoverEvent(ev);
        }
        int action = ev.getActionMasked();
        if (action == 10 && this.mResolveHoverRunnable == null) {
            ResolveHoverRunnable resolveHoverRunnable = new ResolveHoverRunnable();
            this.mResolveHoverRunnable = resolveHoverRunnable;
            resolveHoverRunnable.post();
        }
        boolean handled = super.onHoverEvent(ev);
        if (action == 9 || action == 7) {
            int position = pointToPosition((int) ev.getX(), (int) ev.getY());
            if (!(position == -1 || position == getSelectedItemPosition())) {
                View hoveredItem = getChildAt(position - getFirstVisiblePosition());
                if (hoveredItem.isEnabled()) {
                    requestFocus();
                    if (Build.VERSION.SDK_INT < 30 || !Api30Impl.canPositionSelectorForHoveredItem()) {
                        setSelectionFromTop(position, hoveredItem.getTop() - getTop());
                    } else {
                        Api30Impl.positionSelectorForHoveredItem(this, position, hoveredItem);
                    }
                }
                updateSelectorStateCompat();
            }
        } else {
            setSelection(-1);
        }
        return handled;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        this.mResolveHoverRunnable = null;
        super.onDetachedFromWindow();
    }

    public boolean onForwardedEvent(MotionEvent event, int activePointerId) {
        boolean handledEvent = true;
        boolean clearPressedItem = false;
        int actionMasked = event.getActionMasked();
        switch (actionMasked) {
            case 1:
                handledEvent = false;
                break;
            case 2:
                break;
            case 3:
                handledEvent = false;
                break;
        }
        int activeIndex = event.findPointerIndex(activePointerId);
        if (activeIndex < 0) {
            handledEvent = false;
        } else {
            int x = (int) event.getX(activeIndex);
            int y = (int) event.getY(activeIndex);
            int position = pointToPosition(x, y);
            if (position == -1) {
                clearPressedItem = true;
            } else {
                View child = getChildAt(position - getFirstVisiblePosition());
                setPressedItem(child, position, (float) x, (float) y);
                handledEvent = true;
                if (actionMasked == 1) {
                    clickPressedItem(child, position);
                }
            }
        }
        if (!handledEvent || clearPressedItem) {
            clearPressedItem();
        }
        if (handledEvent) {
            if (this.mScrollHelper == null) {
                this.mScrollHelper = new ListViewAutoScrollHelper(this);
            }
            this.mScrollHelper.setEnabled(true);
            this.mScrollHelper.onTouch(this, event);
        } else {
            ListViewAutoScrollHelper listViewAutoScrollHelper = this.mScrollHelper;
            if (listViewAutoScrollHelper != null) {
                listViewAutoScrollHelper.setEnabled(false);
            }
        }
        return handledEvent;
    }

    private void clickPressedItem(View child, int position) {
        performItemClick(child, position, getItemIdAtPosition(position));
    }

    /* access modifiers changed from: package-private */
    public void setListSelectionHidden(boolean hideListSelection) {
        this.mListSelectionHidden = hideListSelection;
    }

    private void updateSelectorStateCompat() {
        Drawable selector = getSelector();
        if (selector != null && touchModeDrawsInPressedStateCompat() && isPressed()) {
            selector.setState(getDrawableState());
        }
    }

    private void drawSelectorCompat(Canvas canvas) {
        Drawable selector;
        if (!this.mSelectorRect.isEmpty() && (selector = getSelector()) != null) {
            selector.setBounds(this.mSelectorRect);
            selector.draw(canvas);
        }
    }

    private void positionSelectorLikeTouchCompat(int position, View sel, float x, float y) {
        positionSelectorLikeFocusCompat(position, sel);
        Drawable selector = getSelector();
        if (selector != null && position != -1) {
            DrawableCompat.setHotspot(selector, x, y);
        }
    }

    private void positionSelectorLikeFocusCompat(int position, View sel) {
        Drawable selector = getSelector();
        boolean z = true;
        boolean manageState = (selector == null || position == -1) ? false : true;
        if (manageState) {
            selector.setVisible(false, false);
        }
        positionSelectorCompat(position, sel);
        if (manageState) {
            Rect bounds = this.mSelectorRect;
            float x = bounds.exactCenterX();
            float y = bounds.exactCenterY();
            if (getVisibility() != 0) {
                z = false;
            }
            selector.setVisible(z, false);
            DrawableCompat.setHotspot(selector, x, y);
        }
    }

    private void positionSelectorCompat(int position, View sel) {
        Rect selectorRect = this.mSelectorRect;
        selectorRect.set(sel.getLeft(), sel.getTop(), sel.getRight(), sel.getBottom());
        selectorRect.left -= this.mSelectionLeftPadding;
        selectorRect.top -= this.mSelectionTopPadding;
        selectorRect.right += this.mSelectionRightPadding;
        selectorRect.bottom += this.mSelectionBottomPadding;
        boolean isChildViewEnabled = superIsSelectedChildViewEnabled();
        if (sel.isEnabled() != isChildViewEnabled) {
            superSetSelectedChildViewEnabled(!isChildViewEnabled);
            if (position != -1) {
                refreshDrawableState();
            }
        }
    }

    private void clearPressedItem() {
        this.mDrawsInPressedState = false;
        setPressed(false);
        drawableStateChanged();
        View motionView = getChildAt(this.mMotionPosition - getFirstVisiblePosition());
        if (motionView != null) {
            motionView.setPressed(false);
        }
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = this.mClickAnimation;
        if (viewPropertyAnimatorCompat != null) {
            viewPropertyAnimatorCompat.cancel();
            this.mClickAnimation = null;
        }
    }

    private void setPressedItem(View child, int position, float x, float y) {
        View motionView;
        this.mDrawsInPressedState = true;
        if (Build.VERSION.SDK_INT >= 21) {
            Api21Impl.drawableHotspotChanged(this, x, y);
        }
        if (!isPressed()) {
            setPressed(true);
        }
        layoutChildren();
        int i = this.mMotionPosition;
        if (!(i == -1 || (motionView = getChildAt(i - getFirstVisiblePosition())) == null || motionView == child || !motionView.isPressed())) {
            motionView.setPressed(false);
        }
        this.mMotionPosition = position;
        float childX = x - ((float) child.getLeft());
        float childY = y - ((float) child.getTop());
        if (Build.VERSION.SDK_INT >= 21) {
            Api21Impl.drawableHotspotChanged(child, childX, childY);
        }
        if (!child.isPressed()) {
            child.setPressed(true);
        }
        positionSelectorLikeTouchCompat(position, child, x, y);
        setSelectorEnabled(false);
        refreshDrawableState();
    }

    private boolean touchModeDrawsInPressedStateCompat() {
        return this.mDrawsInPressedState;
    }

    private class ResolveHoverRunnable implements Runnable {
        ResolveHoverRunnable() {
        }

        public void run() {
            DropDownListView.this.mResolveHoverRunnable = null;
            DropDownListView.this.drawableStateChanged();
        }

        public void cancel() {
            DropDownListView.this.mResolveHoverRunnable = null;
            DropDownListView.this.removeCallbacks(this);
        }

        public void post() {
            DropDownListView.this.post(this);
        }
    }

    static class Api30Impl {
        private static boolean sHasMethods = true;
        private static Method sPositionSelector;
        private static Method sSetNextSelectedPositionInt;
        private static Method sSetSelectedPositionInt;

        static {
            Class<AbsListView> cls = AbsListView.class;
            try {
                Method declaredMethod = cls.getDeclaredMethod("positionSelector", new Class[]{Integer.TYPE, View.class, Boolean.TYPE, Float.TYPE, Float.TYPE});
                sPositionSelector = declaredMethod;
                declaredMethod.setAccessible(true);
                Method declaredMethod2 = AdapterView.class.getDeclaredMethod("setSelectedPositionInt", new Class[]{Integer.TYPE});
                sSetSelectedPositionInt = declaredMethod2;
                declaredMethod2.setAccessible(true);
                Method declaredMethod3 = AdapterView.class.getDeclaredMethod("setNextSelectedPositionInt", new Class[]{Integer.TYPE});
                sSetNextSelectedPositionInt = declaredMethod3;
                declaredMethod3.setAccessible(true);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        private Api30Impl() {
        }

        static boolean canPositionSelectorForHoveredItem() {
            return sHasMethods;
        }

        static void positionSelectorForHoveredItem(DropDownListView view, int position, View sel) {
            try {
                sPositionSelector.invoke(view, new Object[]{Integer.valueOf(position), sel, false, -1, -1});
                sSetSelectedPositionInt.invoke(view, new Object[]{Integer.valueOf(position)});
                sSetNextSelectedPositionInt.invoke(view, new Object[]{Integer.valueOf(position)});
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e2) {
                e2.printStackTrace();
            }
        }
    }

    static class Api21Impl {
        private Api21Impl() {
        }

        static void drawableHotspotChanged(View view, float x, float y) {
            view.drawableHotspotChanged(x, y);
        }
    }

    static class PreApi33Impl {
        private static final Field sIsChildViewEnabled;

        static {
            Field isChildViewEnabled = null;
            try {
                isChildViewEnabled = AbsListView.class.getDeclaredField("mIsChildViewEnabled");
                isChildViewEnabled.setAccessible(true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            sIsChildViewEnabled = isChildViewEnabled;
        }

        private PreApi33Impl() {
        }

        static boolean isSelectedChildViewEnabled(AbsListView view) {
            Field field = sIsChildViewEnabled;
            if (field == null) {
                return false;
            }
            try {
                return field.getBoolean(view);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        }

        static void setSelectedChildViewEnabled(AbsListView view, boolean enabled) {
            Field field = sIsChildViewEnabled;
            if (field != null) {
                try {
                    field.set(view, Boolean.valueOf(enabled));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Api33Impl {
        private Api33Impl() {
        }

        static boolean isSelectedChildViewEnabled(AbsListView view) {
            return view.isSelectedChildViewEnabled();
        }

        static void setSelectedChildViewEnabled(AbsListView view, boolean enabled) {
            view.setSelectedChildViewEnabled(enabled);
        }
    }
}
