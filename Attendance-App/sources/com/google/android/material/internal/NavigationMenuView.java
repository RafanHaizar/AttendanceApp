package com.google.android.material.internal;

import android.content.Context;
import android.util.AttributeSet;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NavigationMenuView extends RecyclerView implements MenuView {
    public NavigationMenuView(Context context) {
        this(context, (AttributeSet) null);
    }

    public NavigationMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutManager(new LinearLayoutManager(context, 1, false));
    }

    public void initialize(MenuBuilder menu) {
    }

    public int getWindowAnimations() {
        return 0;
    }
}
