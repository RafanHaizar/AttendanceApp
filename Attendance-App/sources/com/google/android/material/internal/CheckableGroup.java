package com.google.android.material.internal;

import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.internal.MaterialCheckable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CheckableGroup<T extends MaterialCheckable<T>> {
    private final Map<Integer, T> checkables = new HashMap();
    private final Set<Integer> checkedIds = new HashSet();
    private OnCheckedStateChangeListener onCheckedStateChangeListener;
    /* access modifiers changed from: private */
    public boolean selectionRequired;
    private boolean singleSelection;

    public interface OnCheckedStateChangeListener {
        void onCheckedStateChanged(Set<Integer> set);
    }

    public void setSingleSelection(boolean singleSelection2) {
        if (this.singleSelection != singleSelection2) {
            this.singleSelection = singleSelection2;
            clearCheck();
        }
    }

    public boolean isSingleSelection() {
        return this.singleSelection;
    }

    public void setSelectionRequired(boolean selectionRequired2) {
        this.selectionRequired = selectionRequired2;
    }

    public boolean isSelectionRequired() {
        return this.selectionRequired;
    }

    public void setOnCheckedStateChangeListener(OnCheckedStateChangeListener listener) {
        this.onCheckedStateChangeListener = listener;
    }

    public void addCheckable(T checkable) {
        this.checkables.put(Integer.valueOf(checkable.getId()), checkable);
        if (checkable.isChecked()) {
            checkInternal(checkable);
        }
        checkable.setInternalOnCheckedChangeListener(new MaterialCheckable.OnCheckedChangeListener<T>() {
            public void onCheckedChanged(T checkable, boolean isChecked) {
                CheckableGroup checkableGroup = CheckableGroup.this;
                if (isChecked) {
                    if (!checkableGroup.checkInternal(checkable)) {
                        return;
                    }
                } else if (!checkableGroup.uncheckInternal(checkable, checkableGroup.selectionRequired)) {
                    return;
                }
                CheckableGroup.this.onCheckedStateChanged();
            }
        });
    }

    public void removeCheckable(T checkable) {
        checkable.setInternalOnCheckedChangeListener((MaterialCheckable.OnCheckedChangeListener) null);
        this.checkables.remove(Integer.valueOf(checkable.getId()));
        this.checkedIds.remove(Integer.valueOf(checkable.getId()));
    }

    public void check(int id) {
        MaterialCheckable<T> checkable = this.checkables.get(Integer.valueOf(id));
        if (checkable != null && checkInternal(checkable)) {
            onCheckedStateChanged();
        }
    }

    public void uncheck(int id) {
        MaterialCheckable<T> checkable = this.checkables.get(Integer.valueOf(id));
        if (checkable != null && uncheckInternal(checkable, this.selectionRequired)) {
            onCheckedStateChanged();
        }
    }

    public void clearCheck() {
        boolean checkedStateChanged = !this.checkedIds.isEmpty();
        for (T checkable : this.checkables.values()) {
            uncheckInternal(checkable, false);
        }
        if (checkedStateChanged) {
            onCheckedStateChanged();
        }
    }

    public int getSingleCheckedId() {
        if (!this.singleSelection || this.checkedIds.isEmpty()) {
            return -1;
        }
        return this.checkedIds.iterator().next().intValue();
    }

    public Set<Integer> getCheckedIds() {
        return new HashSet(this.checkedIds);
    }

    public List<Integer> getCheckedIdsSortedByChildOrder(ViewGroup parent) {
        Set<Integer> checkedIds2 = getCheckedIds();
        List<Integer> sortedCheckedIds = new ArrayList<>();
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            if ((child instanceof MaterialCheckable) && checkedIds2.contains(Integer.valueOf(child.getId()))) {
                sortedCheckedIds.add(Integer.valueOf(child.getId()));
            }
        }
        return sortedCheckedIds;
    }

    /* access modifiers changed from: private */
    public boolean checkInternal(MaterialCheckable<T> checkable) {
        int id = checkable.getId();
        if (this.checkedIds.contains(Integer.valueOf(id))) {
            return false;
        }
        MaterialCheckable<T> singleCheckedItem = this.checkables.get(Integer.valueOf(getSingleCheckedId()));
        if (singleCheckedItem != null) {
            uncheckInternal(singleCheckedItem, false);
        }
        boolean checkedStateChanged = this.checkedIds.add(Integer.valueOf(id));
        if (!checkable.isChecked()) {
            checkable.setChecked(true);
        }
        return checkedStateChanged;
    }

    /* access modifiers changed from: private */
    public boolean uncheckInternal(MaterialCheckable<T> checkable, boolean selectionRequired2) {
        int id = checkable.getId();
        if (!this.checkedIds.contains(Integer.valueOf(id))) {
            return false;
        }
        if (!selectionRequired2 || this.checkedIds.size() != 1 || !this.checkedIds.contains(Integer.valueOf(id))) {
            boolean checkedStateChanged = this.checkedIds.remove(Integer.valueOf(id));
            if (checkable.isChecked()) {
                checkable.setChecked(false);
            }
            return checkedStateChanged;
        }
        checkable.setChecked(true);
        return false;
    }

    /* access modifiers changed from: private */
    public void onCheckedStateChanged() {
        OnCheckedStateChangeListener onCheckedStateChangeListener2 = this.onCheckedStateChangeListener;
        if (onCheckedStateChangeListener2 != null) {
            onCheckedStateChangeListener2.onCheckedStateChanged(getCheckedIds());
        }
    }
}
