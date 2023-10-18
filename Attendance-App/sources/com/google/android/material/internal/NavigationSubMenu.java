package com.google.android.material.internal;

import android.content.Context;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.SubMenuBuilder;

public class NavigationSubMenu extends SubMenuBuilder {
    public NavigationSubMenu(Context context, NavigationMenu menu, MenuItemImpl item) {
        super(context, menu, item);
    }

    public void onItemsChanged(boolean structureChanged) {
        super.onItemsChanged(structureChanged);
        ((MenuBuilder) getParentMenu()).onItemsChanged(structureChanged);
    }
}
