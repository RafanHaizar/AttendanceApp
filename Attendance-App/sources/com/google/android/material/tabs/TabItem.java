package com.google.android.material.tabs;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.appcompat.widget.TintTypedArray;
import com.google.android.material.C1087R;

public class TabItem extends View {
    public final int customLayout;
    public final Drawable icon;
    public final CharSequence text;

    public TabItem(Context context) {
        this(context, (AttributeSet) null);
    }

    public TabItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        TintTypedArray a = TintTypedArray.obtainStyledAttributes(context, attrs, C1087R.styleable.TabItem);
        this.text = a.getText(C1087R.styleable.TabItem_android_text);
        this.icon = a.getDrawable(C1087R.styleable.TabItem_android_icon);
        this.customLayout = a.getResourceId(C1087R.styleable.TabItem_android_layout, 0);
        a.recycle();
    }
}
