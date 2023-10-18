package com.google.android.material.navigation;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.ViewGroup;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.view.menu.SubMenuBuilder;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.internal.ParcelableSparseArray;

public class NavigationBarPresenter implements MenuPresenter {

    /* renamed from: id */
    private int f1123id;
    private MenuBuilder menu;
    private NavigationBarMenuView menuView;
    private boolean updateSuspended = false;

    public void setMenuView(NavigationBarMenuView menuView2) {
        this.menuView = menuView2;
    }

    public void initForMenu(Context context, MenuBuilder menu2) {
        this.menu = menu2;
        this.menuView.initialize(menu2);
    }

    public MenuView getMenuView(ViewGroup root) {
        return this.menuView;
    }

    public void updateMenuView(boolean cleared) {
        if (!this.updateSuspended) {
            if (cleared) {
                this.menuView.buildMenuView();
            } else {
                this.menuView.updateMenuView();
            }
        }
    }

    public void setCallback(MenuPresenter.Callback cb) {
    }

    public boolean onSubMenuSelected(SubMenuBuilder subMenu) {
        return false;
    }

    public void onCloseMenu(MenuBuilder menu2, boolean allMenusAreClosing) {
    }

    public boolean flagActionItems() {
        return false;
    }

    public boolean expandItemActionView(MenuBuilder menu2, MenuItemImpl item) {
        return false;
    }

    public boolean collapseItemActionView(MenuBuilder menu2, MenuItemImpl item) {
        return false;
    }

    public void setId(int id) {
        this.f1123id = id;
    }

    public int getId() {
        return this.f1123id;
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState();
        savedState.selectedItemId = this.menuView.getSelectedItemId();
        savedState.badgeSavedStates = BadgeUtils.createParcelableBadgeStates(this.menuView.getBadgeDrawables());
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof SavedState) {
            this.menuView.tryRestoreSelectedItemId(((SavedState) state).selectedItemId);
            this.menuView.restoreBadgeDrawables(BadgeUtils.createBadgeDrawablesFromSavedStates(this.menuView.getContext(), ((SavedState) state).badgeSavedStates));
        }
    }

    public void setUpdateSuspended(boolean updateSuspended2) {
        this.updateSuspended = updateSuspended2;
    }

    static class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        ParcelableSparseArray badgeSavedStates;
        int selectedItemId;

        SavedState() {
        }

        SavedState(Parcel in) {
            this.selectedItemId = in.readInt();
            this.badgeSavedStates = (ParcelableSparseArray) in.readParcelable(getClass().getClassLoader());
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(this.selectedItemId);
            out.writeParcelable(this.badgeSavedStates, 0);
        }
    }
}
