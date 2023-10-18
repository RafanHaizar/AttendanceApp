package androidx.appcompat.view;

import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.view.menu.SubMenuBuilder;
import androidx.appcompat.widget.ActionBarContextView;
import java.lang.ref.WeakReference;

public class StandaloneActionMode extends ActionMode implements MenuBuilder.Callback {
    private ActionMode.Callback mCallback;
    private Context mContext;
    private ActionBarContextView mContextView;
    private WeakReference<View> mCustomView;
    private boolean mFinished;
    private boolean mFocusable;
    private MenuBuilder mMenu;

    public StandaloneActionMode(Context context, ActionBarContextView view, ActionMode.Callback callback, boolean isFocusable) {
        this.mContext = context;
        this.mContextView = view;
        this.mCallback = callback;
        MenuBuilder defaultShowAsAction = new MenuBuilder(view.getContext()).setDefaultShowAsAction(1);
        this.mMenu = defaultShowAsAction;
        defaultShowAsAction.setCallback(this);
        this.mFocusable = isFocusable;
    }

    public void setTitle(CharSequence title) {
        this.mContextView.setTitle(title);
    }

    public void setSubtitle(CharSequence subtitle) {
        this.mContextView.setSubtitle(subtitle);
    }

    public void setTitle(int resId) {
        setTitle((CharSequence) this.mContext.getString(resId));
    }

    public void setSubtitle(int resId) {
        setSubtitle((CharSequence) this.mContext.getString(resId));
    }

    public void setTitleOptionalHint(boolean titleOptional) {
        super.setTitleOptionalHint(titleOptional);
        this.mContextView.setTitleOptional(titleOptional);
    }

    public boolean isTitleOptional() {
        return this.mContextView.isTitleOptional();
    }

    public void setCustomView(View view) {
        this.mContextView.setCustomView(view);
        this.mCustomView = view != null ? new WeakReference<>(view) : null;
    }

    public void invalidate() {
        this.mCallback.onPrepareActionMode(this, this.mMenu);
    }

    public void finish() {
        if (!this.mFinished) {
            this.mFinished = true;
            this.mCallback.onDestroyActionMode(this);
        }
    }

    public Menu getMenu() {
        return this.mMenu;
    }

    public CharSequence getTitle() {
        return this.mContextView.getTitle();
    }

    public CharSequence getSubtitle() {
        return this.mContextView.getSubtitle();
    }

    public View getCustomView() {
        WeakReference<View> weakReference = this.mCustomView;
        if (weakReference != null) {
            return (View) weakReference.get();
        }
        return null;
    }

    public MenuInflater getMenuInflater() {
        return new SupportMenuInflater(this.mContextView.getContext());
    }

    public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
        return this.mCallback.onActionItemClicked(this, item);
    }

    public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
    }

    public boolean onSubMenuSelected(SubMenuBuilder subMenu) {
        if (!subMenu.hasVisibleItems()) {
            return true;
        }
        new MenuPopupHelper(this.mContextView.getContext(), subMenu).show();
        return true;
    }

    public void onCloseSubMenu(SubMenuBuilder menu) {
    }

    public void onMenuModeChange(MenuBuilder menu) {
        invalidate();
        this.mContextView.showOverflowMenu();
    }

    public boolean isUiFocusable() {
        return this.mFocusable;
    }
}
