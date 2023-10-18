package com.google.android.material.internal;

import android.view.View;

interface ViewGroupOverlayImpl extends ViewOverlayImpl {
    void add(View view);

    void remove(View view);
}
