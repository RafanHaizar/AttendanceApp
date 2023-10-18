package com.google.android.material.datepicker;

public abstract class OnSelectionChangedListener<S> {
    public abstract void onSelectionChanged(S s);

    public void onIncompleteSelectionChanged() {
    }
}
