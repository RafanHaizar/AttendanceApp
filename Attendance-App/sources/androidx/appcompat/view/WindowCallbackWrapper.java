package androidx.appcompat.view;

import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import java.util.List;

public class WindowCallbackWrapper implements Window.Callback {
    final Window.Callback mWrapped;

    public WindowCallbackWrapper(Window.Callback wrapped) {
        if (wrapped != null) {
            this.mWrapped = wrapped;
            return;
        }
        throw new IllegalArgumentException("Window callback may not be null");
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        return this.mWrapped.dispatchKeyEvent(event);
    }

    public boolean dispatchKeyShortcutEvent(KeyEvent event) {
        return this.mWrapped.dispatchKeyShortcutEvent(event);
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        return this.mWrapped.dispatchTouchEvent(event);
    }

    public boolean dispatchTrackballEvent(MotionEvent event) {
        return this.mWrapped.dispatchTrackballEvent(event);
    }

    public boolean dispatchGenericMotionEvent(MotionEvent event) {
        return this.mWrapped.dispatchGenericMotionEvent(event);
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        return this.mWrapped.dispatchPopulateAccessibilityEvent(event);
    }

    public View onCreatePanelView(int featureId) {
        return this.mWrapped.onCreatePanelView(featureId);
    }

    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        return this.mWrapped.onCreatePanelMenu(featureId, menu);
    }

    public boolean onPreparePanel(int featureId, View view, Menu menu) {
        return this.mWrapped.onPreparePanel(featureId, view, menu);
    }

    public boolean onMenuOpened(int featureId, Menu menu) {
        return this.mWrapped.onMenuOpened(featureId, menu);
    }

    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        return this.mWrapped.onMenuItemSelected(featureId, item);
    }

    public void onWindowAttributesChanged(WindowManager.LayoutParams attrs) {
        this.mWrapped.onWindowAttributesChanged(attrs);
    }

    public void onContentChanged() {
        this.mWrapped.onContentChanged();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        this.mWrapped.onWindowFocusChanged(hasFocus);
    }

    public void onAttachedToWindow() {
        this.mWrapped.onAttachedToWindow();
    }

    public void onDetachedFromWindow() {
        this.mWrapped.onDetachedFromWindow();
    }

    public void onPanelClosed(int featureId, Menu menu) {
        this.mWrapped.onPanelClosed(featureId, menu);
    }

    public boolean onSearchRequested(SearchEvent searchEvent) {
        return Api23Impl.onSearchRequested(this.mWrapped, searchEvent);
    }

    public boolean onSearchRequested() {
        return this.mWrapped.onSearchRequested();
    }

    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
        return this.mWrapped.onWindowStartingActionMode(callback);
    }

    public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int type) {
        return Api23Impl.onWindowStartingActionMode(this.mWrapped, callback, type);
    }

    public void onActionModeStarted(ActionMode mode) {
        this.mWrapped.onActionModeStarted(mode);
    }

    public void onActionModeFinished(ActionMode mode) {
        this.mWrapped.onActionModeFinished(mode);
    }

    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, Menu menu, int deviceId) {
        Api24Impl.onProvideKeyboardShortcuts(this.mWrapped, data, menu, deviceId);
    }

    public void onPointerCaptureChanged(boolean hasCapture) {
        Api26Impl.onPointerCaptureChanged(this.mWrapped, hasCapture);
    }

    public final Window.Callback getWrapped() {
        return this.mWrapped;
    }

    static class Api23Impl {
        private Api23Impl() {
        }

        static boolean onSearchRequested(Window.Callback callback, SearchEvent searchEvent) {
            return callback.onSearchRequested(searchEvent);
        }

        static ActionMode onWindowStartingActionMode(Window.Callback windowCallback, ActionMode.Callback actionModeCallback, int i) {
            return windowCallback.onWindowStartingActionMode(actionModeCallback, i);
        }
    }

    static class Api24Impl {
        private Api24Impl() {
        }

        static void onProvideKeyboardShortcuts(Window.Callback callback, List<KeyboardShortcutGroup> data, Menu menu, int deviceId) {
            callback.onProvideKeyboardShortcuts(data, menu, deviceId);
        }
    }

    static class Api26Impl {
        private Api26Impl() {
        }

        static void onPointerCaptureChanged(Window.Callback callback, boolean hasCapture) {
            callback.onPointerCaptureChanged(hasCapture);
        }
    }
}
