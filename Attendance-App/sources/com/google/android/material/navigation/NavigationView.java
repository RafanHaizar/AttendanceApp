package com.google.android.material.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.appcompat.C0503R;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.customview.view.AbsSavedState;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.C1087R;
import com.google.android.material.internal.ContextUtils;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.internal.NavigationMenuPresenter;
import com.google.android.material.internal.ScrimInsetsFrameLayout;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.MaterialShapeUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.ShapeAppearancePathProvider;

public class NavigationView extends ScrimInsetsFrameLayout {
    private static final int[] CHECKED_STATE_SET = {16842912};
    private static final int DEF_STYLE_RES = C1087R.C1093style.Widget_Design_NavigationView;
    private static final int[] DISABLED_STATE_SET = {-16842910};
    private static final int PRESENTER_NAVIGATION_VIEW_ID = 1;
    private boolean bottomInsetScrimEnabled;
    private int drawerLayoutCornerSize;
    private int layoutGravity;
    OnNavigationItemSelectedListener listener;
    private final int maxWidth;
    private final NavigationMenu menu;
    private MenuInflater menuInflater;
    private ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener;
    /* access modifiers changed from: private */
    public final NavigationMenuPresenter presenter;
    private final RectF shapeClipBounds;
    private Path shapeClipPath;
    /* access modifiers changed from: private */
    public final int[] tmpLocation;
    private boolean topInsetScrimEnabled;

    public interface OnNavigationItemSelectedListener {
        boolean onNavigationItemSelected(MenuItem menuItem);
    }

    public NavigationView(Context context) {
        this(context, (AttributeSet) null);
    }

    public NavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, C1087R.attr.navigationViewStyle);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public NavigationView(android.content.Context r18, android.util.AttributeSet r19, int r20) {
        /*
            r17 = this;
            r0 = r17
            r7 = r19
            r8 = r20
            int r9 = DEF_STYLE_RES
            r1 = r18
            android.content.Context r2 = com.google.android.material.theme.overlay.MaterialThemeOverlay.wrap(r1, r7, r8, r9)
            r0.<init>(r2, r7, r8)
            com.google.android.material.internal.NavigationMenuPresenter r10 = new com.google.android.material.internal.NavigationMenuPresenter
            r10.<init>()
            r0.presenter = r10
            r2 = 2
            int[] r2 = new int[r2]
            r0.tmpLocation = r2
            r11 = 1
            r0.topInsetScrimEnabled = r11
            r0.bottomInsetScrimEnabled = r11
            r12 = 0
            r0.layoutGravity = r12
            r0.drawerLayoutCornerSize = r12
            android.graphics.RectF r2 = new android.graphics.RectF
            r2.<init>()
            r0.shapeClipBounds = r2
            android.content.Context r13 = r17.getContext()
            com.google.android.material.internal.NavigationMenu r14 = new com.google.android.material.internal.NavigationMenu
            r14.<init>(r13)
            r0.menu = r14
            int[] r3 = com.google.android.material.C1087R.styleable.NavigationView
            int[] r6 = new int[r12]
            r1 = r13
            r2 = r19
            r4 = r20
            r5 = r9
            androidx.appcompat.widget.TintTypedArray r1 = com.google.android.material.internal.ThemeEnforcement.obtainTintedStyledAttributes(r1, r2, r3, r4, r5, r6)
            int r2 = com.google.android.material.C1087R.styleable.NavigationView_android_background
            boolean r2 = r1.hasValue(r2)
            if (r2 == 0) goto L_0x0058
            int r2 = com.google.android.material.C1087R.styleable.NavigationView_android_background
            android.graphics.drawable.Drawable r2 = r1.getDrawable(r2)
            androidx.core.view.ViewCompat.setBackground(r0, r2)
        L_0x0058:
            int r2 = com.google.android.material.C1087R.styleable.NavigationView_drawerLayoutCornerSize
            int r2 = r1.getDimensionPixelSize(r2, r12)
            r0.drawerLayoutCornerSize = r2
            int r2 = com.google.android.material.C1087R.styleable.NavigationView_android_layout_gravity
            int r2 = r1.getInt(r2, r12)
            r0.layoutGravity = r2
            android.graphics.drawable.Drawable r2 = r17.getBackground()
            if (r2 == 0) goto L_0x0076
            android.graphics.drawable.Drawable r2 = r17.getBackground()
            boolean r2 = r2 instanceof android.graphics.drawable.ColorDrawable
            if (r2 == 0) goto L_0x00a0
        L_0x0076:
            com.google.android.material.shape.ShapeAppearanceModel$Builder r2 = com.google.android.material.shape.ShapeAppearanceModel.builder((android.content.Context) r13, (android.util.AttributeSet) r7, (int) r8, (int) r9)
            com.google.android.material.shape.ShapeAppearanceModel r2 = r2.build()
            android.graphics.drawable.Drawable r3 = r17.getBackground()
            com.google.android.material.shape.MaterialShapeDrawable r4 = new com.google.android.material.shape.MaterialShapeDrawable
            r4.<init>((com.google.android.material.shape.ShapeAppearanceModel) r2)
            boolean r5 = r3 instanceof android.graphics.drawable.ColorDrawable
            if (r5 == 0) goto L_0x009a
            r5 = r3
            android.graphics.drawable.ColorDrawable r5 = (android.graphics.drawable.ColorDrawable) r5
            int r5 = r5.getColor()
            android.content.res.ColorStateList r5 = android.content.res.ColorStateList.valueOf(r5)
            r4.setFillColor(r5)
        L_0x009a:
            r4.initializeElevationOverlay(r13)
            androidx.core.view.ViewCompat.setBackground(r0, r4)
        L_0x00a0:
            int r2 = com.google.android.material.C1087R.styleable.NavigationView_elevation
            boolean r2 = r1.hasValue(r2)
            if (r2 == 0) goto L_0x00b2
            int r2 = com.google.android.material.C1087R.styleable.NavigationView_elevation
            int r2 = r1.getDimensionPixelSize(r2, r12)
            float r2 = (float) r2
            r0.setElevation(r2)
        L_0x00b2:
            int r2 = com.google.android.material.C1087R.styleable.NavigationView_android_fitsSystemWindows
            boolean r2 = r1.getBoolean(r2, r12)
            r0.setFitsSystemWindows(r2)
            int r2 = com.google.android.material.C1087R.styleable.NavigationView_android_maxWidth
            int r2 = r1.getDimensionPixelSize(r2, r12)
            r0.maxWidth = r2
            r2 = 0
            int r3 = com.google.android.material.C1087R.styleable.NavigationView_subheaderColor
            boolean r3 = r1.hasValue(r3)
            if (r3 == 0) goto L_0x00d2
            int r3 = com.google.android.material.C1087R.styleable.NavigationView_subheaderColor
            android.content.res.ColorStateList r2 = r1.getColorStateList(r3)
        L_0x00d2:
            r3 = 0
            int r4 = com.google.android.material.C1087R.styleable.NavigationView_subheaderTextAppearance
            boolean r4 = r1.hasValue(r4)
            if (r4 == 0) goto L_0x00e1
            int r4 = com.google.android.material.C1087R.styleable.NavigationView_subheaderTextAppearance
            int r3 = r1.getResourceId(r4, r12)
        L_0x00e1:
            r4 = 16842808(0x1010038, float:2.3693715E-38)
            if (r3 != 0) goto L_0x00ec
            if (r2 != 0) goto L_0x00ec
            android.content.res.ColorStateList r2 = r0.createDefaultColorStateList(r4)
        L_0x00ec:
            int r5 = com.google.android.material.C1087R.styleable.NavigationView_itemIconTint
            boolean r5 = r1.hasValue(r5)
            if (r5 == 0) goto L_0x00fb
            int r4 = com.google.android.material.C1087R.styleable.NavigationView_itemIconTint
            android.content.res.ColorStateList r4 = r1.getColorStateList(r4)
            goto L_0x00ff
        L_0x00fb:
            android.content.res.ColorStateList r4 = r0.createDefaultColorStateList(r4)
        L_0x00ff:
            r5 = 0
            int r6 = com.google.android.material.C1087R.styleable.NavigationView_itemTextAppearance
            boolean r6 = r1.hasValue(r6)
            if (r6 == 0) goto L_0x010e
            int r6 = com.google.android.material.C1087R.styleable.NavigationView_itemTextAppearance
            int r5 = r1.getResourceId(r6, r12)
        L_0x010e:
            int r6 = com.google.android.material.C1087R.styleable.NavigationView_itemIconSize
            boolean r6 = r1.hasValue(r6)
            if (r6 == 0) goto L_0x011f
            int r6 = com.google.android.material.C1087R.styleable.NavigationView_itemIconSize
            int r6 = r1.getDimensionPixelSize(r6, r12)
            r0.setItemIconSize(r6)
        L_0x011f:
            r6 = 0
            int r9 = com.google.android.material.C1087R.styleable.NavigationView_itemTextColor
            boolean r9 = r1.hasValue(r9)
            if (r9 == 0) goto L_0x012e
            int r9 = com.google.android.material.C1087R.styleable.NavigationView_itemTextColor
            android.content.res.ColorStateList r6 = r1.getColorStateList(r9)
        L_0x012e:
            if (r5 != 0) goto L_0x0139
            if (r6 != 0) goto L_0x0139
            r9 = 16842806(0x1010036, float:2.369371E-38)
            android.content.res.ColorStateList r6 = r0.createDefaultColorStateList(r9)
        L_0x0139:
            int r9 = com.google.android.material.C1087R.styleable.NavigationView_itemBackground
            android.graphics.drawable.Drawable r9 = r1.getDrawable(r9)
            if (r9 != 0) goto L_0x016c
            boolean r15 = r0.hasShapeAppearance(r1)
            if (r15 == 0) goto L_0x016c
            android.graphics.drawable.Drawable r9 = r0.createDefaultItemBackground(r1)
            int r15 = com.google.android.material.C1087R.styleable.NavigationView_itemRippleColor
            android.content.res.ColorStateList r15 = com.google.android.material.resources.MaterialResources.getColorStateList((android.content.Context) r13, (androidx.appcompat.widget.TintTypedArray) r1, (int) r15)
            int r11 = android.os.Build.VERSION.SDK_INT
            r12 = 21
            if (r11 < r12) goto L_0x016c
            if (r15 == 0) goto L_0x016c
            r11 = 0
            android.graphics.drawable.Drawable r12 = r0.createDefaultItemDrawable(r1, r11)
            android.graphics.drawable.RippleDrawable r11 = new android.graphics.drawable.RippleDrawable
            android.content.res.ColorStateList r7 = com.google.android.material.ripple.RippleUtils.sanitizeRippleDrawableColor(r15)
            r8 = 0
            r11.<init>(r7, r8, r12)
            r7 = r11
            r10.setItemForeground(r7)
        L_0x016c:
            int r7 = com.google.android.material.C1087R.styleable.NavigationView_itemHorizontalPadding
            boolean r7 = r1.hasValue(r7)
            if (r7 == 0) goto L_0x017e
            int r7 = com.google.android.material.C1087R.styleable.NavigationView_itemHorizontalPadding
            r8 = 0
            int r7 = r1.getDimensionPixelSize(r7, r8)
            r0.setItemHorizontalPadding(r7)
        L_0x017e:
            int r7 = com.google.android.material.C1087R.styleable.NavigationView_itemVerticalPadding
            boolean r7 = r1.hasValue(r7)
            if (r7 == 0) goto L_0x0191
            int r7 = com.google.android.material.C1087R.styleable.NavigationView_itemVerticalPadding
            r8 = 0
            int r7 = r1.getDimensionPixelSize(r7, r8)
            r0.setItemVerticalPadding(r7)
            goto L_0x0192
        L_0x0191:
            r8 = 0
        L_0x0192:
            int r7 = com.google.android.material.C1087R.styleable.NavigationView_dividerInsetStart
            int r7 = r1.getDimensionPixelSize(r7, r8)
            r0.setDividerInsetStart(r7)
            int r11 = com.google.android.material.C1087R.styleable.NavigationView_dividerInsetEnd
            int r11 = r1.getDimensionPixelSize(r11, r8)
            r0.setDividerInsetEnd(r11)
            int r12 = com.google.android.material.C1087R.styleable.NavigationView_subheaderInsetStart
            int r12 = r1.getDimensionPixelSize(r12, r8)
            r0.setSubheaderInsetStart(r12)
            int r15 = com.google.android.material.C1087R.styleable.NavigationView_subheaderInsetEnd
            int r15 = r1.getDimensionPixelSize(r15, r8)
            r0.setSubheaderInsetEnd(r15)
            int r8 = com.google.android.material.C1087R.styleable.NavigationView_topInsetScrimEnabled
            r18 = r7
            boolean r7 = r0.topInsetScrimEnabled
            boolean r7 = r1.getBoolean(r8, r7)
            r0.setTopInsetScrimEnabled(r7)
            int r7 = com.google.android.material.C1087R.styleable.NavigationView_bottomInsetScrimEnabled
            boolean r8 = r0.bottomInsetScrimEnabled
            boolean r7 = r1.getBoolean(r7, r8)
            r0.setBottomInsetScrimEnabled(r7)
            int r7 = com.google.android.material.C1087R.styleable.NavigationView_itemIconPadding
            r8 = 0
            int r7 = r1.getDimensionPixelSize(r7, r8)
            int r8 = com.google.android.material.C1087R.styleable.NavigationView_itemMaxLines
            r16 = r11
            r11 = 1
            int r8 = r1.getInt(r8, r11)
            r0.setItemMaxLines(r8)
            com.google.android.material.navigation.NavigationView$1 r8 = new com.google.android.material.navigation.NavigationView$1
            r8.<init>()
            r14.setCallback(r8)
            r10.setId(r11)
            r10.initForMenu(r13, r14)
            if (r3 == 0) goto L_0x01f4
            r10.setSubheaderTextAppearance(r3)
        L_0x01f4:
            r10.setSubheaderColor(r2)
            r10.setItemIconTintList(r4)
            int r8 = r17.getOverScrollMode()
            r10.setOverScrollMode(r8)
            if (r5 == 0) goto L_0x0206
            r10.setItemTextAppearance(r5)
        L_0x0206:
            r10.setItemTextColor(r6)
            r10.setItemBackground(r9)
            r10.setItemIconPadding(r7)
            r14.addMenuPresenter(r10)
            androidx.appcompat.view.menu.MenuView r8 = r10.getMenuView(r0)
            android.view.View r8 = (android.view.View) r8
            r0.addView(r8)
            int r8 = com.google.android.material.C1087R.styleable.NavigationView_menu
            boolean r8 = r1.hasValue(r8)
            if (r8 == 0) goto L_0x022e
            int r8 = com.google.android.material.C1087R.styleable.NavigationView_menu
            r10 = 0
            int r8 = r1.getResourceId(r8, r10)
            r0.inflateMenu(r8)
            goto L_0x022f
        L_0x022e:
            r10 = 0
        L_0x022f:
            int r8 = com.google.android.material.C1087R.styleable.NavigationView_headerLayout
            boolean r8 = r1.hasValue(r8)
            if (r8 == 0) goto L_0x0240
            int r8 = com.google.android.material.C1087R.styleable.NavigationView_headerLayout
            int r8 = r1.getResourceId(r8, r10)
            r0.inflateHeaderView(r8)
        L_0x0240:
            r1.recycle()
            r17.setupInsetScrimsListener()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.navigation.NavigationView.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    public void setOverScrollMode(int overScrollMode) {
        super.setOverScrollMode(overScrollMode);
        NavigationMenuPresenter navigationMenuPresenter = this.presenter;
        if (navigationMenuPresenter != null) {
            navigationMenuPresenter.setOverScrollMode(overScrollMode);
        }
    }

    private void maybeUpdateCornerSizeForDrawerLayout(int width, int height) {
        if (!(getParent() instanceof DrawerLayout) || this.drawerLayoutCornerSize <= 0 || !(getBackground() instanceof MaterialShapeDrawable)) {
            this.shapeClipPath = null;
            this.shapeClipBounds.setEmpty();
            return;
        }
        MaterialShapeDrawable background = (MaterialShapeDrawable) getBackground();
        ShapeAppearanceModel.Builder builder = background.getShapeAppearanceModel().toBuilder();
        if (GravityCompat.getAbsoluteGravity(this.layoutGravity, ViewCompat.getLayoutDirection(this)) == 3) {
            builder.setTopRightCornerSize((float) this.drawerLayoutCornerSize);
            builder.setBottomRightCornerSize((float) this.drawerLayoutCornerSize);
        } else {
            builder.setTopLeftCornerSize((float) this.drawerLayoutCornerSize);
            builder.setBottomLeftCornerSize((float) this.drawerLayoutCornerSize);
        }
        background.setShapeAppearanceModel(builder.build());
        if (this.shapeClipPath == null) {
            this.shapeClipPath = new Path();
        }
        this.shapeClipPath.reset();
        this.shapeClipBounds.set(0.0f, 0.0f, (float) width, (float) height);
        ShapeAppearancePathProvider.getInstance().calculatePath(background.getShapeAppearanceModel(), background.getInterpolation(), this.shapeClipBounds, this.shapeClipPath);
        invalidate();
    }

    private boolean hasShapeAppearance(TintTypedArray a) {
        return a.hasValue(C1087R.styleable.NavigationView_itemShapeAppearance) || a.hasValue(C1087R.styleable.NavigationView_itemShapeAppearanceOverlay);
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        MaterialShapeUtils.setParentAbsoluteElevation(this);
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        maybeUpdateCornerSizeForDrawerLayout(w, h);
    }

    public void setElevation(float elevation) {
        if (Build.VERSION.SDK_INT >= 21) {
            super.setElevation(elevation);
        }
        MaterialShapeUtils.setElevation(this, elevation);
    }

    private Drawable createDefaultItemBackground(TintTypedArray a) {
        return createDefaultItemDrawable(a, MaterialResources.getColorStateList(getContext(), a, C1087R.styleable.NavigationView_itemShapeFillColor));
    }

    private Drawable createDefaultItemDrawable(TintTypedArray a, ColorStateList fillColor) {
        TintTypedArray tintTypedArray = a;
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable(ShapeAppearanceModel.builder(getContext(), tintTypedArray.getResourceId(C1087R.styleable.NavigationView_itemShapeAppearance, 0), tintTypedArray.getResourceId(C1087R.styleable.NavigationView_itemShapeAppearanceOverlay, 0)).build());
        materialShapeDrawable.setFillColor(fillColor);
        return new InsetDrawable(materialShapeDrawable, tintTypedArray.getDimensionPixelSize(C1087R.styleable.NavigationView_itemShapeInsetStart, 0), tintTypedArray.getDimensionPixelSize(C1087R.styleable.NavigationView_itemShapeInsetTop, 0), tintTypedArray.getDimensionPixelSize(C1087R.styleable.NavigationView_itemShapeInsetEnd, 0), tintTypedArray.getDimensionPixelSize(C1087R.styleable.NavigationView_itemShapeInsetBottom, 0));
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        SavedState state = new SavedState(super.onSaveInstanceState());
        state.menuState = new Bundle();
        this.menu.savePresenterStates(state.menuState);
        return state;
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable savedState) {
        if (!(savedState instanceof SavedState)) {
            super.onRestoreInstanceState(savedState);
            return;
        }
        SavedState state = (SavedState) savedState;
        super.onRestoreInstanceState(state.getSuperState());
        this.menu.restorePresenterStates(state.menuState);
    }

    public void setNavigationItemSelectedListener(OnNavigationItemSelectedListener listener2) {
        this.listener = listener2;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthSpec, int heightSpec) {
        switch (View.MeasureSpec.getMode(widthSpec)) {
            case Integer.MIN_VALUE:
                widthSpec = View.MeasureSpec.makeMeasureSpec(Math.min(View.MeasureSpec.getSize(widthSpec), this.maxWidth), 1073741824);
                break;
            case 0:
                widthSpec = View.MeasureSpec.makeMeasureSpec(this.maxWidth, 1073741824);
                break;
        }
        super.onMeasure(widthSpec, heightSpec);
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        if (this.shapeClipPath == null) {
            super.dispatchDraw(canvas);
            return;
        }
        int save = canvas.save();
        canvas.clipPath(this.shapeClipPath);
        super.dispatchDraw(canvas);
        canvas.restoreToCount(save);
    }

    /* access modifiers changed from: protected */
    public void onInsetsChanged(WindowInsetsCompat insets) {
        this.presenter.dispatchApplyWindowInsets(insets);
    }

    public void inflateMenu(int resId) {
        this.presenter.setUpdateSuspended(true);
        getMenuInflater().inflate(resId, this.menu);
        this.presenter.setUpdateSuspended(false);
        this.presenter.updateMenuView(false);
    }

    public Menu getMenu() {
        return this.menu;
    }

    public View inflateHeaderView(int res) {
        return this.presenter.inflateHeaderView(res);
    }

    public void addHeaderView(View view) {
        this.presenter.addHeaderView(view);
    }

    public void removeHeaderView(View view) {
        this.presenter.removeHeaderView(view);
    }

    public int getHeaderCount() {
        return this.presenter.getHeaderCount();
    }

    public View getHeaderView(int index) {
        return this.presenter.getHeaderView(index);
    }

    public ColorStateList getItemIconTintList() {
        return this.presenter.getItemTintList();
    }

    public void setItemIconTintList(ColorStateList tint) {
        this.presenter.setItemIconTintList(tint);
    }

    public ColorStateList getItemTextColor() {
        return this.presenter.getItemTextColor();
    }

    public void setItemTextColor(ColorStateList textColor) {
        this.presenter.setItemTextColor(textColor);
    }

    public Drawable getItemBackground() {
        return this.presenter.getItemBackground();
    }

    public void setItemBackgroundResource(int resId) {
        setItemBackground(ContextCompat.getDrawable(getContext(), resId));
    }

    public void setItemBackground(Drawable itemBackground) {
        this.presenter.setItemBackground(itemBackground);
    }

    public int getItemHorizontalPadding() {
        return this.presenter.getItemHorizontalPadding();
    }

    public void setItemHorizontalPadding(int padding) {
        this.presenter.setItemHorizontalPadding(padding);
    }

    public void setItemHorizontalPaddingResource(int paddingResource) {
        this.presenter.setItemHorizontalPadding(getResources().getDimensionPixelSize(paddingResource));
    }

    public int getItemVerticalPadding() {
        return this.presenter.getItemVerticalPadding();
    }

    public void setItemVerticalPadding(int padding) {
        this.presenter.setItemVerticalPadding(padding);
    }

    public void setItemVerticalPaddingResource(int paddingResource) {
        this.presenter.setItemVerticalPadding(getResources().getDimensionPixelSize(paddingResource));
    }

    public int getItemIconPadding() {
        return this.presenter.getItemIconPadding();
    }

    public void setItemIconPadding(int padding) {
        this.presenter.setItemIconPadding(padding);
    }

    public void setItemIconPaddingResource(int paddingResource) {
        this.presenter.setItemIconPadding(getResources().getDimensionPixelSize(paddingResource));
    }

    public void setCheckedItem(int id) {
        MenuItem item = this.menu.findItem(id);
        if (item != null) {
            this.presenter.setCheckedItem((MenuItemImpl) item);
        }
    }

    public void setCheckedItem(MenuItem checkedItem) {
        MenuItem item = this.menu.findItem(checkedItem.getItemId());
        if (item != null) {
            this.presenter.setCheckedItem((MenuItemImpl) item);
            return;
        }
        throw new IllegalArgumentException("Called setCheckedItem(MenuItem) with an item that is not in the current menu.");
    }

    public MenuItem getCheckedItem() {
        return this.presenter.getCheckedItem();
    }

    public void setItemTextAppearance(int resId) {
        this.presenter.setItemTextAppearance(resId);
    }

    public void setItemIconSize(int iconSize) {
        this.presenter.setItemIconSize(iconSize);
    }

    public void setItemMaxLines(int itemMaxLines) {
        this.presenter.setItemMaxLines(itemMaxLines);
    }

    public int getItemMaxLines() {
        return this.presenter.getItemMaxLines();
    }

    public boolean isTopInsetScrimEnabled() {
        return this.topInsetScrimEnabled;
    }

    public void setTopInsetScrimEnabled(boolean enabled) {
        this.topInsetScrimEnabled = enabled;
    }

    public boolean isBottomInsetScrimEnabled() {
        return this.bottomInsetScrimEnabled;
    }

    public void setBottomInsetScrimEnabled(boolean enabled) {
        this.bottomInsetScrimEnabled = enabled;
    }

    public int getDividerInsetStart() {
        return this.presenter.getDividerInsetStart();
    }

    public void setDividerInsetStart(int dividerInsetStart) {
        this.presenter.setDividerInsetStart(dividerInsetStart);
    }

    public int getDividerInsetEnd() {
        return this.presenter.getDividerInsetEnd();
    }

    public void setDividerInsetEnd(int dividerInsetEnd) {
        this.presenter.setDividerInsetEnd(dividerInsetEnd);
    }

    public int getSubheaderInsetStart() {
        return this.presenter.getSubheaderInsetStart();
    }

    public void setSubheaderInsetStart(int subheaderInsetStart) {
        this.presenter.setSubheaderInsetStart(subheaderInsetStart);
    }

    public int getSubheaderInsetEnd() {
        return this.presenter.getSubheaderInsetEnd();
    }

    public void setSubheaderInsetEnd(int subheaderInsetEnd) {
        this.presenter.setSubheaderInsetEnd(subheaderInsetEnd);
    }

    private MenuInflater getMenuInflater() {
        if (this.menuInflater == null) {
            this.menuInflater = new SupportMenuInflater(getContext());
        }
        return this.menuInflater;
    }

    private ColorStateList createDefaultColorStateList(int baseColorThemeAttr) {
        TypedValue value = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(baseColorThemeAttr, value, true)) {
            return null;
        }
        ColorStateList baseColor = AppCompatResources.getColorStateList(getContext(), value.resourceId);
        if (!getContext().getTheme().resolveAttribute(C0503R.attr.colorPrimary, value, true)) {
            return null;
        }
        int colorPrimary = value.data;
        int defaultColor = baseColor.getDefaultColor();
        int[] iArr = DISABLED_STATE_SET;
        return new ColorStateList(new int[][]{iArr, CHECKED_STATE_SET, EMPTY_STATE_SET}, new int[]{baseColor.getColorForState(iArr, defaultColor), colorPrimary, defaultColor});
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this.onGlobalLayoutListener);
    }

    private void setupInsetScrimsListener() {
        this.onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                NavigationView navigationView = NavigationView.this;
                navigationView.getLocationOnScreen(navigationView.tmpLocation);
                boolean isOnRightSide = true;
                boolean isBehindStatusBar = NavigationView.this.tmpLocation[1] == 0;
                NavigationView.this.presenter.setBehindStatusBar(isBehindStatusBar);
                NavigationView navigationView2 = NavigationView.this;
                navigationView2.setDrawTopInsetForeground(isBehindStatusBar && navigationView2.isTopInsetScrimEnabled());
                NavigationView.this.setDrawLeftInsetForeground(NavigationView.this.tmpLocation[0] == 0 || NavigationView.this.tmpLocation[0] + NavigationView.this.getWidth() == 0);
                Activity activity = ContextUtils.getActivity(NavigationView.this.getContext());
                if (activity != null && Build.VERSION.SDK_INT >= 21) {
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
                    boolean isBehindSystemNav = displayMetrics.heightPixels - NavigationView.this.getHeight() == NavigationView.this.tmpLocation[1];
                    boolean hasNonZeroAlpha = Color.alpha(activity.getWindow().getNavigationBarColor()) != 0;
                    NavigationView navigationView3 = NavigationView.this;
                    navigationView3.setDrawBottomInsetForeground(isBehindSystemNav && hasNonZeroAlpha && navigationView3.isBottomInsetScrimEnabled());
                    if (!(displayMetrics.widthPixels == NavigationView.this.tmpLocation[0] || displayMetrics.widthPixels - NavigationView.this.getWidth() == NavigationView.this.tmpLocation[0])) {
                        isOnRightSide = false;
                    }
                    NavigationView.this.setDrawRightInsetForeground(isOnRightSide);
                }
            }
        };
        getViewTreeObserver().addOnGlobalLayoutListener(this.onGlobalLayoutListener);
    }

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() {
            public SavedState createFromParcel(Parcel in, ClassLoader loader) {
                return new SavedState(in, loader);
            }

            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in, (ClassLoader) null);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        public Bundle menuState;

        public SavedState(Parcel in, ClassLoader loader) {
            super(in, loader);
            this.menuState = in.readBundle(loader);
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeBundle(this.menuState);
        }
    }
}
