package androidx.appcompat.view.menu;

import android.content.Context;
import android.view.MenuItem;
import android.view.SubMenu;
import androidx.collection.SimpleArrayMap;
import androidx.core.internal.view.SupportMenuItem;
import androidx.core.internal.view.SupportSubMenu;

abstract class BaseMenuWrapper {
    final Context mContext;
    private SimpleArrayMap<SupportMenuItem, MenuItem> mMenuItems;
    private SimpleArrayMap<SupportSubMenu, SubMenu> mSubMenus;

    BaseMenuWrapper(Context context) {
        this.mContext = context;
    }

    /* access modifiers changed from: package-private */
    public final MenuItem getMenuItemWrapper(MenuItem menuItem) {
        if (!(menuItem instanceof SupportMenuItem)) {
            return menuItem;
        }
        SupportMenuItem supportMenuItem = (SupportMenuItem) menuItem;
        if (this.mMenuItems == null) {
            this.mMenuItems = new SimpleArrayMap<>();
        }
        MenuItem wrappedItem = this.mMenuItems.get(supportMenuItem);
        if (wrappedItem != null) {
            return wrappedItem;
        }
        MenuItem wrappedItem2 = new MenuItemWrapperICS(this.mContext, supportMenuItem);
        this.mMenuItems.put(supportMenuItem, wrappedItem2);
        return wrappedItem2;
    }

    /* access modifiers changed from: package-private */
    public final SubMenu getSubMenuWrapper(SubMenu subMenu) {
        if (!(subMenu instanceof SupportSubMenu)) {
            return subMenu;
        }
        SupportSubMenu supportSubMenu = (SupportSubMenu) subMenu;
        if (this.mSubMenus == null) {
            this.mSubMenus = new SimpleArrayMap<>();
        }
        SubMenu wrappedMenu = this.mSubMenus.get(supportSubMenu);
        if (wrappedMenu != null) {
            return wrappedMenu;
        }
        SubMenu wrappedMenu2 = new SubMenuWrapperICS(this.mContext, supportSubMenu);
        this.mSubMenus.put(supportSubMenu, wrappedMenu2);
        return wrappedMenu2;
    }

    /* access modifiers changed from: package-private */
    public final void internalClear() {
        SimpleArrayMap<SupportMenuItem, MenuItem> simpleArrayMap = this.mMenuItems;
        if (simpleArrayMap != null) {
            simpleArrayMap.clear();
        }
        SimpleArrayMap<SupportSubMenu, SubMenu> simpleArrayMap2 = this.mSubMenus;
        if (simpleArrayMap2 != null) {
            simpleArrayMap2.clear();
        }
    }

    /* access modifiers changed from: package-private */
    public final void internalRemoveGroup(int groupId) {
        if (this.mMenuItems != null) {
            int i = 0;
            while (i < this.mMenuItems.size()) {
                if (this.mMenuItems.keyAt(i).getGroupId() == groupId) {
                    this.mMenuItems.removeAt(i);
                    i--;
                }
                i++;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public final void internalRemoveItem(int id) {
        if (this.mMenuItems != null) {
            for (int i = 0; i < this.mMenuItems.size(); i++) {
                if (this.mMenuItems.keyAt(i).getItemId() == id) {
                    this.mMenuItems.removeAt(i);
                    return;
                }
            }
        }
    }
}
