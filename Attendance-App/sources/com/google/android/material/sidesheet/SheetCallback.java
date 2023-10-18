package com.google.android.material.sidesheet;

import android.view.View;

interface SheetCallback {
    void onSlide(View view, float f);

    void onStateChanged(View view, int i);
}
