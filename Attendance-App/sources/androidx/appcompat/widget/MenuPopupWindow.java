package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.transition.Transition;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import androidx.appcompat.view.menu.ListMenuItemView;
import androidx.appcompat.view.menu.MenuAdapter;
import androidx.appcompat.view.menu.MenuBuilder;
import java.lang.reflect.Method;

public class MenuPopupWindow extends ListPopupWindow implements MenuItemHoverListener {
    private static final String TAG = "MenuPopupWindow";
    private static Method sSetTouchModalMethod;
    private MenuItemHoverListener mHoverListener;

    static {
        try {
            if (Build.VERSION.SDK_INT <= 28) {
                sSetTouchModalMethod = PopupWindow.class.getDeclaredMethod("setTouchModal", new Class[]{Boolean.TYPE});
            }
        } catch (NoSuchMethodException e) {
            Log.i(TAG, "Could not find method setTouchModal() on PopupWindow. Oh well.");
        }
    }

    public MenuPopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /* access modifiers changed from: package-private */
    public DropDownListView createDropDownListView(Context context, boolean hijackFocus) {
        MenuDropDownListView view = new MenuDropDownListView(context, hijackFocus);
        view.setHoverListener(this);
        return view;
    }

    public void setEnterTransition(Object enterTransition) {
        if (Build.VERSION.SDK_INT >= 23) {
            Api23Impl.setEnterTransition(this.mPopup, (Transition) enterTransition);
        }
    }

    public void setExitTransition(Object exitTransition) {
        if (Build.VERSION.SDK_INT >= 23) {
            Api23Impl.setExitTransition(this.mPopup, (Transition) exitTransition);
        }
    }

    public void setHoverListener(MenuItemHoverListener hoverListener) {
        this.mHoverListener = hoverListener;
    }

    public void setTouchModal(boolean touchModal) {
        if (Build.VERSION.SDK_INT <= 28) {
            Method method = sSetTouchModalMethod;
            if (method != null) {
                try {
                    method.invoke(this.mPopup, new Object[]{Boolean.valueOf(touchModal)});
                } catch (Exception e) {
                    Log.i(TAG, "Could not invoke setTouchModal() on PopupWindow. Oh well.");
                }
            }
        } else {
            Api29Impl.setTouchModal(this.mPopup, touchModal);
        }
    }

    public void onItemHoverEnter(MenuBuilder menu, MenuItem item) {
        MenuItemHoverListener menuItemHoverListener = this.mHoverListener;
        if (menuItemHoverListener != null) {
            menuItemHoverListener.onItemHoverEnter(menu, item);
        }
    }

    public void onItemHoverExit(MenuBuilder menu, MenuItem item) {
        MenuItemHoverListener menuItemHoverListener = this.mHoverListener;
        if (menuItemHoverListener != null) {
            menuItemHoverListener.onItemHoverExit(menu, item);
        }
    }

    public static class MenuDropDownListView extends DropDownListView {
        final int mAdvanceKey;
        private MenuItemHoverListener mHoverListener;
        private MenuItem mHoveredMenuItem;
        final int mRetreatKey;

        public /* bridge */ /* synthetic */ boolean hasFocus() {
            return super.hasFocus();
        }

        public /* bridge */ /* synthetic */ boolean hasWindowFocus() {
            return super.hasWindowFocus();
        }

        public /* bridge */ /* synthetic */ boolean isFocused() {
            return super.isFocused();
        }

        public /* bridge */ /* synthetic */ boolean isInTouchMode() {
            return super.isInTouchMode();
        }

        public /* bridge */ /* synthetic */ int lookForSelectablePosition(int i, boolean z) {
            return super.lookForSelectablePosition(i, z);
        }

        public /* bridge */ /* synthetic */ int measureHeightOfChildrenCompat(int i, int i2, int i3, int i4, int i5) {
            return super.measureHeightOfChildrenCompat(i, i2, i3, i4, i5);
        }

        public /* bridge */ /* synthetic */ boolean onForwardedEvent(MotionEvent motionEvent, int i) {
            return super.onForwardedEvent(motionEvent, i);
        }

        public /* bridge */ /* synthetic */ boolean onTouchEvent(MotionEvent motionEvent) {
            return super.onTouchEvent(motionEvent);
        }

        public /* bridge */ /* synthetic */ void setSelector(Drawable drawable) {
            super.setSelector(drawable);
        }

        public MenuDropDownListView(Context context, boolean hijackFocus) {
            super(context, hijackFocus);
            if (1 == Api17Impl.getLayoutDirection(context.getResources().getConfiguration())) {
                this.mAdvanceKey = 21;
                this.mRetreatKey = 22;
                return;
            }
            this.mAdvanceKey = 22;
            this.mRetreatKey = 21;
        }

        public void setHoverListener(MenuItemHoverListener hoverListener) {
            this.mHoverListener = hoverListener;
        }

        public void clearSelection() {
            setSelection(-1);
        }

        public boolean onKeyDown(int keyCode, KeyEvent event) {
            MenuAdapter menuAdapter;
            ListMenuItemView selectedItem = (ListMenuItemView) getSelectedView();
            if (selectedItem != null && keyCode == this.mAdvanceKey) {
                if (selectedItem.isEnabled() && selectedItem.getItemData().hasSubMenu()) {
                    performItemClick(selectedItem, getSelectedItemPosition(), getSelectedItemId());
                }
                return true;
            } else if (selectedItem == null || keyCode != this.mRetreatKey) {
                return super.onKeyDown(keyCode, event);
            } else {
                setSelection(-1);
                ListAdapter adapter = getAdapter();
                if (adapter instanceof HeaderViewListAdapter) {
                    menuAdapter = (MenuAdapter) ((HeaderViewListAdapter) adapter).getWrappedAdapter();
                } else {
                    menuAdapter = (MenuAdapter) adapter;
                }
                menuAdapter.getAdapterMenu().close(false);
                return true;
            }
        }

        /* JADX WARNING: type inference failed for: r3v3, types: [android.widget.ListAdapter] */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean onHoverEvent(android.view.MotionEvent r8) {
            /*
                r7 = this;
                androidx.appcompat.widget.MenuItemHoverListener r0 = r7.mHoverListener
                if (r0 == 0) goto L_0x005f
                android.widget.ListAdapter r0 = r7.getAdapter()
                boolean r1 = r0 instanceof android.widget.HeaderViewListAdapter
                if (r1 == 0) goto L_0x001b
                r1 = r0
                android.widget.HeaderViewListAdapter r1 = (android.widget.HeaderViewListAdapter) r1
                int r2 = r1.getHeadersCount()
                android.widget.ListAdapter r3 = r1.getWrappedAdapter()
                r1 = r3
                androidx.appcompat.view.menu.MenuAdapter r1 = (androidx.appcompat.view.menu.MenuAdapter) r1
                goto L_0x001f
            L_0x001b:
                r2 = 0
                r1 = r0
                androidx.appcompat.view.menu.MenuAdapter r1 = (androidx.appcompat.view.menu.MenuAdapter) r1
            L_0x001f:
                r3 = 0
                int r4 = r8.getAction()
                r5 = 10
                if (r4 == r5) goto L_0x0047
                float r4 = r8.getX()
                int r4 = (int) r4
                float r5 = r8.getY()
                int r5 = (int) r5
                int r4 = r7.pointToPosition(r4, r5)
                r5 = -1
                if (r4 == r5) goto L_0x0047
                int r5 = r4 - r2
                if (r5 < 0) goto L_0x0047
                int r6 = r1.getCount()
                if (r5 >= r6) goto L_0x0047
                androidx.appcompat.view.menu.MenuItemImpl r3 = r1.getItem((int) r5)
            L_0x0047:
                android.view.MenuItem r4 = r7.mHoveredMenuItem
                if (r4 == r3) goto L_0x005f
                androidx.appcompat.view.menu.MenuBuilder r5 = r1.getAdapterMenu()
                if (r4 == 0) goto L_0x0056
                androidx.appcompat.widget.MenuItemHoverListener r6 = r7.mHoverListener
                r6.onItemHoverExit(r5, r4)
            L_0x0056:
                r7.mHoveredMenuItem = r3
                if (r3 == 0) goto L_0x005f
                androidx.appcompat.widget.MenuItemHoverListener r6 = r7.mHoverListener
                r6.onItemHoverEnter(r5, r3)
            L_0x005f:
                boolean r0 = super.onHoverEvent(r8)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.MenuPopupWindow.MenuDropDownListView.onHoverEvent(android.view.MotionEvent):boolean");
        }

        static class Api17Impl {
            private Api17Impl() {
            }

            static int getLayoutDirection(Configuration configuration) {
                return configuration.getLayoutDirection();
            }
        }
    }

    static class Api23Impl {
        private Api23Impl() {
        }

        static void setEnterTransition(PopupWindow popupWindow, Transition enterTransition) {
            popupWindow.setEnterTransition(enterTransition);
        }

        static void setExitTransition(PopupWindow popupWindow, Transition exitTransition) {
            popupWindow.setExitTransition(exitTransition);
        }
    }

    static class Api29Impl {
        private Api29Impl() {
        }

        static void setTouchModal(PopupWindow popupWindow, boolean touchModal) {
            popupWindow.setTouchModal(touchModal);
        }
    }
}
