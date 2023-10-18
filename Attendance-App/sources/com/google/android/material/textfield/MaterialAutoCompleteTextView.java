package com.google.android.material.textfield;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.C1087R;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.ManufacturerUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;

public class MaterialAutoCompleteTextView extends AppCompatAutoCompleteTextView {
    private static final int MAX_ITEMS_MEASURED = 15;
    private final AccessibilityManager accessibilityManager;
    /* access modifiers changed from: private */
    public final ListPopupWindow modalListPopup;
    private final float popupElevation;
    private final int simpleItemLayout;
    /* access modifiers changed from: private */
    public int simpleItemSelectedColor;
    /* access modifiers changed from: private */
    public ColorStateList simpleItemSelectedRippleColor;
    private final Rect tempRect;

    public MaterialAutoCompleteTextView(Context context) {
        this(context, (AttributeSet) null);
    }

    public MaterialAutoCompleteTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, C1087R.attr.autoCompleteTextViewStyle);
    }

    public MaterialAutoCompleteTextView(Context context, AttributeSet attributeSet, int defStyleAttr) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, defStyleAttr, 0), attributeSet, defStyleAttr);
        this.tempRect = new Rect();
        Context context2 = getContext();
        TypedArray attributes = ThemeEnforcement.obtainStyledAttributes(context2, attributeSet, C1087R.styleable.MaterialAutoCompleteTextView, defStyleAttr, C1087R.C1093style.Widget_AppCompat_AutoCompleteTextView, new int[0]);
        if (attributes.hasValue(C1087R.styleable.MaterialAutoCompleteTextView_android_inputType) && attributes.getInt(C1087R.styleable.MaterialAutoCompleteTextView_android_inputType, 0) == 0) {
            setKeyListener((KeyListener) null);
        }
        this.simpleItemLayout = attributes.getResourceId(C1087R.styleable.MaterialAutoCompleteTextView_simpleItemLayout, C1087R.C1092layout.mtrl_auto_complete_simple_item);
        this.popupElevation = (float) attributes.getDimensionPixelOffset(C1087R.styleable.MaterialAutoCompleteTextView_android_popupElevation, C1087R.dimen.mtrl_exposed_dropdown_menu_popup_elevation);
        this.simpleItemSelectedColor = attributes.getColor(C1087R.styleable.MaterialAutoCompleteTextView_simpleItemSelectedColor, 0);
        this.simpleItemSelectedRippleColor = MaterialResources.getColorStateList(context2, attributes, C1087R.styleable.MaterialAutoCompleteTextView_simpleItemSelectedRippleColor);
        this.accessibilityManager = (AccessibilityManager) context2.getSystemService("accessibility");
        ListPopupWindow listPopupWindow = new ListPopupWindow(context2);
        this.modalListPopup = listPopupWindow;
        listPopupWindow.setModal(true);
        listPopupWindow.setAnchorView(this);
        listPopupWindow.setInputMethodMode(2);
        listPopupWindow.setAdapter(getAdapter());
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View selectedView, int position, long id) {
                MaterialAutoCompleteTextView materialAutoCompleteTextView = MaterialAutoCompleteTextView.this;
                MaterialAutoCompleteTextView.this.updateText(position < 0 ? materialAutoCompleteTextView.modalListPopup.getSelectedItem() : materialAutoCompleteTextView.getAdapter().getItem(position));
                AdapterView.OnItemClickListener userOnItemClickListener = MaterialAutoCompleteTextView.this.getOnItemClickListener();
                if (userOnItemClickListener != null) {
                    if (selectedView == null || position < 0) {
                        selectedView = MaterialAutoCompleteTextView.this.modalListPopup.getSelectedView();
                        position = MaterialAutoCompleteTextView.this.modalListPopup.getSelectedItemPosition();
                        id = MaterialAutoCompleteTextView.this.modalListPopup.getSelectedItemId();
                    }
                    userOnItemClickListener.onItemClick(MaterialAutoCompleteTextView.this.modalListPopup.getListView(), selectedView, position, id);
                }
                MaterialAutoCompleteTextView.this.modalListPopup.dismiss();
            }
        });
        if (attributes.hasValue(C1087R.styleable.MaterialAutoCompleteTextView_simpleItems)) {
            setSimpleItems(attributes.getResourceId(C1087R.styleable.MaterialAutoCompleteTextView_simpleItems, 0));
        }
        attributes.recycle();
    }

    public void showDropDown() {
        if (isTouchExplorationEnabled()) {
            this.modalListPopup.show();
        } else {
            super.showDropDown();
        }
    }

    public void dismissDropDown() {
        if (isTouchExplorationEnabled()) {
            this.modalListPopup.dismiss();
        } else {
            super.dismissDropDown();
        }
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (!isTouchExplorationEnabled()) {
            super.onWindowFocusChanged(hasWindowFocus);
        }
    }

    private boolean isTouchExplorationEnabled() {
        AccessibilityManager accessibilityManager2 = this.accessibilityManager;
        return accessibilityManager2 != null && accessibilityManager2.isTouchExplorationEnabled();
    }

    public <T extends ListAdapter & Filterable> void setAdapter(T adapter) {
        super.setAdapter(adapter);
        this.modalListPopup.setAdapter(getAdapter());
    }

    public void setRawInputType(int type) {
        super.setRawInputType(type);
        onInputTypeChanged();
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener) {
        super.setOnItemSelectedListener(listener);
        this.modalListPopup.setOnItemSelectedListener(getOnItemSelectedListener());
    }

    public void setSimpleItems(int stringArrayResId) {
        setSimpleItems(getResources().getStringArray(stringArrayResId));
    }

    public void setSimpleItems(String[] stringArray) {
        setAdapter(new MaterialArrayAdapter(getContext(), this.simpleItemLayout, stringArray));
    }

    public void setSimpleItemSelectedColor(int simpleItemSelectedColor2) {
        this.simpleItemSelectedColor = simpleItemSelectedColor2;
        if (getAdapter() instanceof MaterialArrayAdapter) {
            ((MaterialArrayAdapter) getAdapter()).updateSelectedItemColorStateList();
        }
    }

    public int getSimpleItemSelectedColor() {
        return this.simpleItemSelectedColor;
    }

    public void setSimpleItemSelectedRippleColor(ColorStateList simpleItemSelectedRippleColor2) {
        this.simpleItemSelectedRippleColor = simpleItemSelectedRippleColor2;
        if (getAdapter() instanceof MaterialArrayAdapter) {
            ((MaterialArrayAdapter) getAdapter()).updateSelectedItemColorStateList();
        }
    }

    public ColorStateList getSimpleItemSelectedRippleColor() {
        return this.simpleItemSelectedRippleColor;
    }

    public void setDropDownBackgroundDrawable(Drawable d) {
        super.setDropDownBackgroundDrawable(d);
        ListPopupWindow listPopupWindow = this.modalListPopup;
        if (listPopupWindow != null) {
            listPopupWindow.setBackgroundDrawable(d);
        }
    }

    public float getPopupElevation() {
        return this.popupElevation;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        TextInputLayout layout = findTextInputLayoutAncestor();
        if (layout != null && layout.isProvidingHint() && super.getHint() == null && ManufacturerUtils.isMeizuDevice()) {
            setHint("");
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.modalListPopup.dismiss();
    }

    public CharSequence getHint() {
        TextInputLayout textInputLayout = findTextInputLayoutAncestor();
        if (textInputLayout == null || !textInputLayout.isProvidingHint()) {
            return super.getHint();
        }
        return textInputLayout.getHint();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (View.MeasureSpec.getMode(widthMeasureSpec) == Integer.MIN_VALUE) {
            setMeasuredDimension(Math.min(Math.max(getMeasuredWidth(), measureContentWidth()), View.MeasureSpec.getSize(widthMeasureSpec)), getMeasuredHeight());
        }
    }

    private int measureContentWidth() {
        ListAdapter adapter = getAdapter();
        TextInputLayout textInputLayout = findTextInputLayoutAncestor();
        if (adapter == null || textInputLayout == null) {
            return 0;
        }
        int width = 0;
        View itemView = null;
        int itemType = 0;
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 0);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 0);
        int end = Math.min(adapter.getCount(), Math.max(0, this.modalListPopup.getSelectedItemPosition()) + 15);
        for (int i = Math.max(0, end - 15); i < end; i++) {
            int positionType = adapter.getItemViewType(i);
            if (positionType != itemType) {
                itemType = positionType;
                itemView = null;
            }
            itemView = adapter.getView(i, itemView, textInputLayout);
            if (itemView.getLayoutParams() == null) {
                itemView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            }
            itemView.measure(widthMeasureSpec, heightMeasureSpec);
            width = Math.max(width, itemView.getMeasuredWidth());
        }
        Drawable background = this.modalListPopup.getBackground();
        if (background != null) {
            background.getPadding(this.tempRect);
            width += this.tempRect.left + this.tempRect.right;
        }
        return width + textInputLayout.getEndIconView().getMeasuredWidth();
    }

    private void onInputTypeChanged() {
        TextInputLayout textInputLayout = findTextInputLayoutAncestor();
        if (textInputLayout != null) {
            textInputLayout.updateEditTextBoxBackgroundIfNeeded();
        }
    }

    private TextInputLayout findTextInputLayoutAncestor() {
        for (ViewParent parent = getParent(); parent != null; parent = parent.getParent()) {
            if (parent instanceof TextInputLayout) {
                return (TextInputLayout) parent;
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    public <T extends ListAdapter & Filterable> void updateText(Object selectedItem) {
        setText(convertSelectionToString(selectedItem), false);
    }

    private class MaterialArrayAdapter<T> extends ArrayAdapter<String> {
        private ColorStateList pressedRippleColor;
        private ColorStateList selectedItemRippleOverlaidColor;

        MaterialArrayAdapter(Context context, int resource, String[] objects) {
            super(context, resource, objects);
            updateSelectedItemColorStateList();
        }

        /* access modifiers changed from: package-private */
        public void updateSelectedItemColorStateList() {
            this.pressedRippleColor = sanitizeDropdownItemSelectedRippleColor();
            this.selectedItemRippleOverlaidColor = createItemSelectedColorStateList();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                ViewCompat.setBackground(textView, MaterialAutoCompleteTextView.this.getText().toString().contentEquals(textView.getText()) ? getSelectedItemDrawable() : null);
            }
            return view;
        }

        private Drawable getSelectedItemDrawable() {
            if (!hasSelectedColor() || Build.VERSION.SDK_INT < 21) {
                return null;
            }
            Drawable colorDrawable = new ColorDrawable(MaterialAutoCompleteTextView.this.simpleItemSelectedColor);
            if (this.pressedRippleColor == null) {
                return colorDrawable;
            }
            DrawableCompat.setTintList(colorDrawable, this.selectedItemRippleOverlaidColor);
            return new RippleDrawable(this.pressedRippleColor, colorDrawable, (Drawable) null);
        }

        private ColorStateList createItemSelectedColorStateList() {
            if (!hasSelectedColor() || !hasSelectedRippleColor() || Build.VERSION.SDK_INT < 21) {
                return null;
            }
            int[] stateHovered = {16843623, -16842919};
            int[] stateSelected = {16842913, -16842919};
            return new ColorStateList(new int[][]{stateSelected, stateHovered, new int[0]}, new int[]{MaterialColors.layer(MaterialAutoCompleteTextView.this.simpleItemSelectedColor, MaterialAutoCompleteTextView.this.simpleItemSelectedRippleColor.getColorForState(stateSelected, 0)), MaterialColors.layer(MaterialAutoCompleteTextView.this.simpleItemSelectedColor, MaterialAutoCompleteTextView.this.simpleItemSelectedRippleColor.getColorForState(stateHovered, 0)), MaterialAutoCompleteTextView.this.simpleItemSelectedColor});
        }

        private ColorStateList sanitizeDropdownItemSelectedRippleColor() {
            if (!hasSelectedRippleColor()) {
                return null;
            }
            int[] statePressed = {16842919};
            return new ColorStateList(new int[][]{statePressed, new int[0]}, new int[]{MaterialAutoCompleteTextView.this.simpleItemSelectedRippleColor.getColorForState(statePressed, 0), 0});
        }

        private boolean hasSelectedColor() {
            return MaterialAutoCompleteTextView.this.simpleItemSelectedColor != 0;
        }

        private boolean hasSelectedRippleColor() {
            return MaterialAutoCompleteTextView.this.simpleItemSelectedRippleColor != null;
        }
    }
}
