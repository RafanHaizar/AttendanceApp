package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.LinearLayoutCompat;

public class ActionMenuView extends LinearLayoutCompat implements MenuBuilder.ItemInvoker, MenuView {
    static final int GENERATED_ITEM_PADDING = 4;
    static final int MIN_CELL_SIZE = 56;
    private static final String TAG = "ActionMenuView";
    private MenuPresenter.Callback mActionMenuPresenterCallback;
    private boolean mFormatItems;
    private int mFormatItemsWidth;
    private int mGeneratedItemPadding;
    private MenuBuilder mMenu;
    MenuBuilder.Callback mMenuBuilderCallback;
    private int mMinCellSize;
    OnMenuItemClickListener mOnMenuItemClickListener;
    private Context mPopupContext;
    private int mPopupTheme;
    private ActionMenuPresenter mPresenter;
    private boolean mReserveOverflow;

    public interface ActionMenuChildView {
        boolean needsDividerAfter();

        boolean needsDividerBefore();
    }

    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    public ActionMenuView(Context context) {
        this(context, (AttributeSet) null);
    }

    public ActionMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBaselineAligned(false);
        float density = context.getResources().getDisplayMetrics().density;
        this.mMinCellSize = (int) (56.0f * density);
        this.mGeneratedItemPadding = (int) (4.0f * density);
        this.mPopupContext = context;
        this.mPopupTheme = 0;
    }

    public void setPopupTheme(int resId) {
        if (this.mPopupTheme != resId) {
            this.mPopupTheme = resId;
            if (resId == 0) {
                this.mPopupContext = getContext();
            } else {
                this.mPopupContext = new ContextThemeWrapper(getContext(), resId);
            }
        }
    }

    public int getPopupTheme() {
        return this.mPopupTheme;
    }

    public void setPresenter(ActionMenuPresenter presenter) {
        this.mPresenter = presenter;
        presenter.setMenuView(this);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.updateMenuView(false);
            if (this.mPresenter.isOverflowMenuShowing()) {
                this.mPresenter.hideOverflowMenu();
                this.mPresenter.showOverflowMenu();
            }
        }
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        this.mOnMenuItemClickListener = listener;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        MenuBuilder menuBuilder;
        boolean wasFormatted = this.mFormatItems;
        boolean z = View.MeasureSpec.getMode(widthMeasureSpec) == 1073741824;
        this.mFormatItems = z;
        if (wasFormatted != z) {
            this.mFormatItemsWidth = 0;
        }
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        if (!(!this.mFormatItems || (menuBuilder = this.mMenu) == null || widthSize == this.mFormatItemsWidth)) {
            this.mFormatItemsWidth = widthSize;
            menuBuilder.onItemsChanged(true);
        }
        int childCount = getChildCount();
        if (!this.mFormatItems || childCount <= 0) {
            for (int i = 0; i < childCount; i++) {
                LayoutParams lp = (LayoutParams) getChildAt(i).getLayoutParams();
                lp.rightMargin = 0;
                lp.leftMargin = 0;
            }
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        onMeasureExactFormat(widthMeasureSpec, heightMeasureSpec);
    }

    /* JADX WARNING: Removed duplicated region for block: B:131:0x0287  */
    /* JADX WARNING: Removed duplicated region for block: B:139:0x02b5  */
    /* JADX WARNING: Removed duplicated region for block: B:142:0x02bd  */
    /* JADX WARNING: Removed duplicated region for block: B:143:0x02bf  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void onMeasureExactFormat(int r41, int r42) {
        /*
            r40 = this;
            r0 = r40
            int r1 = android.view.View.MeasureSpec.getMode(r42)
            int r2 = android.view.View.MeasureSpec.getSize(r41)
            int r3 = android.view.View.MeasureSpec.getSize(r42)
            int r4 = r40.getPaddingLeft()
            int r5 = r40.getPaddingRight()
            int r4 = r4 + r5
            int r5 = r40.getPaddingTop()
            int r6 = r40.getPaddingBottom()
            int r5 = r5 + r6
            r6 = -2
            r7 = r42
            int r6 = getChildMeasureSpec(r7, r5, r6)
            int r2 = r2 - r4
            int r8 = r0.mMinCellSize
            int r9 = r2 / r8
            int r10 = r2 % r8
            r11 = 0
            if (r9 != 0) goto L_0x0035
            r0.setMeasuredDimension(r2, r11)
            return
        L_0x0035:
            int r12 = r10 / r9
            int r8 = r8 + r12
            r12 = r9
            r13 = 0
            r14 = 0
            r15 = 0
            r16 = 0
            r17 = 0
            r18 = 0
            int r11 = r40.getChildCount()
            r21 = 0
            r38 = r16
            r16 = r3
            r3 = r38
            r39 = r21
            r21 = r4
            r4 = r39
        L_0x0054:
            if (r4 >= r11) goto L_0x00e6
            android.view.View r7 = r0.getChildAt(r4)
            r23 = r9
            int r9 = r7.getVisibility()
            r24 = r10
            r10 = 8
            if (r9 != r10) goto L_0x0068
            goto L_0x00dc
        L_0x0068:
            boolean r9 = r7 instanceof androidx.appcompat.view.menu.ActionMenuItemView
            int r3 = r3 + 1
            if (r9 == 0) goto L_0x0077
            int r10 = r0.mGeneratedItemPadding
            r25 = r3
            r3 = 0
            r7.setPadding(r10, r3, r10, r3)
            goto L_0x007a
        L_0x0077:
            r25 = r3
            r3 = 0
        L_0x007a:
            android.view.ViewGroup$LayoutParams r10 = r7.getLayoutParams()
            androidx.appcompat.widget.ActionMenuView$LayoutParams r10 = (androidx.appcompat.widget.ActionMenuView.LayoutParams) r10
            r10.expanded = r3
            r10.extraPixels = r3
            r10.cellsUsed = r3
            r10.expandable = r3
            r10.leftMargin = r3
            r10.rightMargin = r3
            if (r9 == 0) goto L_0x0099
            r3 = r7
            androidx.appcompat.view.menu.ActionMenuItemView r3 = (androidx.appcompat.view.menu.ActionMenuItemView) r3
            boolean r3 = r3.hasText()
            if (r3 == 0) goto L_0x0099
            r3 = 1
            goto L_0x009a
        L_0x0099:
            r3 = 0
        L_0x009a:
            r10.preventEdgeOffset = r3
            boolean r3 = r10.isOverflowButton
            if (r3 == 0) goto L_0x00a2
            r3 = 1
            goto L_0x00a3
        L_0x00a2:
            r3 = r12
        L_0x00a3:
            r26 = r9
            int r9 = measureChildForCells(r7, r8, r3, r6, r5)
            int r14 = java.lang.Math.max(r14, r9)
            r27 = r3
            boolean r3 = r10.expandable
            if (r3 == 0) goto L_0x00b5
            int r15 = r15 + 1
        L_0x00b5:
            boolean r3 = r10.isOverflowButton
            if (r3 == 0) goto L_0x00bb
            r17 = 1
        L_0x00bb:
            int r12 = r12 - r9
            int r3 = r7.getMeasuredHeight()
            int r3 = java.lang.Math.max(r13, r3)
            r13 = 1
            if (r9 != r13) goto L_0x00d5
            int r13 = r13 << r4
            r28 = r9
            r22 = r10
            long r9 = (long) r13
            long r9 = r18 | r9
            r13 = r3
            r18 = r9
            r3 = r25
            goto L_0x00dc
        L_0x00d5:
            r28 = r9
            r22 = r10
            r13 = r3
            r3 = r25
        L_0x00dc:
            int r4 = r4 + 1
            r7 = r42
            r9 = r23
            r10 = r24
            goto L_0x0054
        L_0x00e6:
            r23 = r9
            r24 = r10
            r4 = 2
            if (r17 == 0) goto L_0x00f1
            if (r3 != r4) goto L_0x00f1
            r7 = 1
            goto L_0x00f2
        L_0x00f1:
            r7 = 0
        L_0x00f2:
            r9 = 0
        L_0x00f3:
            r25 = 1
            r27 = 0
            if (r15 <= 0) goto L_0x01a8
            if (r12 <= 0) goto L_0x01a8
            r10 = 2147483647(0x7fffffff, float:NaN)
            r29 = 0
            r31 = 0
            r32 = 0
            r4 = r31
            r38 = r32
            r32 = r5
            r5 = r38
        L_0x010c:
            if (r5 >= r11) goto L_0x013e
            android.view.View r33 = r0.getChildAt(r5)
            android.view.ViewGroup$LayoutParams r34 = r33.getLayoutParams()
            r35 = r9
            r9 = r34
            androidx.appcompat.widget.ActionMenuView$LayoutParams r9 = (androidx.appcompat.widget.ActionMenuView.LayoutParams) r9
            r34 = r15
            boolean r15 = r9.expandable
            if (r15 != 0) goto L_0x0123
            goto L_0x0137
        L_0x0123:
            int r15 = r9.cellsUsed
            if (r15 >= r10) goto L_0x012d
            int r10 = r9.cellsUsed
            long r29 = r25 << r5
            r4 = 1
            goto L_0x0137
        L_0x012d:
            int r15 = r9.cellsUsed
            if (r15 != r10) goto L_0x0137
            long r36 = r25 << r5
            long r29 = r29 | r36
            int r4 = r4 + 1
        L_0x0137:
            int r5 = r5 + 1
            r15 = r34
            r9 = r35
            goto L_0x010c
        L_0x013e:
            r35 = r9
            r34 = r15
            long r18 = r18 | r29
            if (r4 <= r12) goto L_0x014c
            r36 = r1
            r37 = r2
            goto L_0x01b2
        L_0x014c:
            int r10 = r10 + 1
            r5 = 0
        L_0x014f:
            if (r5 >= r11) goto L_0x019a
            android.view.View r9 = r0.getChildAt(r5)
            android.view.ViewGroup$LayoutParams r15 = r9.getLayoutParams()
            androidx.appcompat.widget.ActionMenuView$LayoutParams r15 = (androidx.appcompat.widget.ActionMenuView.LayoutParams) r15
            r33 = r4
            r22 = 1
            int r4 = r22 << r5
            r36 = r1
            r37 = r2
            long r1 = (long) r4
            long r1 = r29 & r1
            int r4 = (r1 > r27 ? 1 : (r1 == r27 ? 0 : -1))
            if (r4 != 0) goto L_0x0176
            int r1 = r15.cellsUsed
            if (r1 != r10) goto L_0x0191
            int r1 = r22 << r5
            long r1 = (long) r1
            long r18 = r18 | r1
            goto L_0x0191
        L_0x0176:
            if (r7 == 0) goto L_0x0187
            boolean r1 = r15.preventEdgeOffset
            if (r1 == 0) goto L_0x0187
            r1 = 1
            if (r12 != r1) goto L_0x0187
            int r1 = r0.mGeneratedItemPadding
            int r2 = r1 + r8
            r4 = 0
            r9.setPadding(r2, r4, r1, r4)
        L_0x0187:
            int r1 = r15.cellsUsed
            r2 = 1
            int r1 = r1 + r2
            r15.cellsUsed = r1
            r15.expanded = r2
            int r12 = r12 + -1
        L_0x0191:
            int r5 = r5 + 1
            r4 = r33
            r1 = r36
            r2 = r37
            goto L_0x014f
        L_0x019a:
            r36 = r1
            r37 = r2
            r33 = r4
            r9 = 1
            r5 = r32
            r15 = r34
            r4 = 2
            goto L_0x00f3
        L_0x01a8:
            r36 = r1
            r37 = r2
            r32 = r5
            r35 = r9
            r34 = r15
        L_0x01b2:
            if (r17 != 0) goto L_0x01b9
            r1 = 1
            if (r3 != r1) goto L_0x01b9
            r1 = 1
            goto L_0x01ba
        L_0x01b9:
            r1 = 0
        L_0x01ba:
            if (r12 <= 0) goto L_0x0282
            int r2 = (r18 > r27 ? 1 : (r18 == r27 ? 0 : -1))
            if (r2 == 0) goto L_0x0282
            int r2 = r3 + -1
            if (r12 < r2) goto L_0x01cd
            if (r1 != 0) goto L_0x01cd
            r2 = 1
            if (r14 <= r2) goto L_0x01ca
            goto L_0x01cd
        L_0x01ca:
            r10 = r1
            goto L_0x0283
        L_0x01cd:
            int r2 = java.lang.Long.bitCount(r18)
            float r2 = (float) r2
            if (r1 != 0) goto L_0x020b
            long r4 = r18 & r25
            r9 = 1056964608(0x3f000000, float:0.5)
            int r10 = (r4 > r27 ? 1 : (r4 == r27 ? 0 : -1))
            if (r10 == 0) goto L_0x01ed
            r4 = 0
            android.view.View r5 = r0.getChildAt(r4)
            android.view.ViewGroup$LayoutParams r5 = r5.getLayoutParams()
            androidx.appcompat.widget.ActionMenuView$LayoutParams r5 = (androidx.appcompat.widget.ActionMenuView.LayoutParams) r5
            boolean r10 = r5.preventEdgeOffset
            if (r10 != 0) goto L_0x01ee
            float r2 = r2 - r9
            goto L_0x01ee
        L_0x01ed:
            r4 = 0
        L_0x01ee:
            int r5 = r11 + -1
            r10 = 1
            int r5 = r10 << r5
            long r4 = (long) r5
            long r4 = r18 & r4
            int r10 = (r4 > r27 ? 1 : (r4 == r27 ? 0 : -1))
            if (r10 == 0) goto L_0x020b
            int r4 = r11 + -1
            android.view.View r4 = r0.getChildAt(r4)
            android.view.ViewGroup$LayoutParams r4 = r4.getLayoutParams()
            androidx.appcompat.widget.ActionMenuView$LayoutParams r4 = (androidx.appcompat.widget.ActionMenuView.LayoutParams) r4
            boolean r5 = r4.preventEdgeOffset
            if (r5 != 0) goto L_0x020b
            float r2 = r2 - r9
        L_0x020b:
            r4 = 0
            int r4 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1))
            if (r4 <= 0) goto L_0x0218
            int r4 = r12 * r8
            float r4 = (float) r4
            float r4 = r4 / r2
            int r4 = (int) r4
            r20 = r4
            goto L_0x021a
        L_0x0218:
            r20 = 0
        L_0x021a:
            r4 = r20
            r5 = 0
            r9 = r35
        L_0x021f:
            if (r5 >= r11) goto L_0x027d
            r10 = 1
            int r15 = r10 << r5
            r10 = r1
            r20 = r2
            long r1 = (long) r15
            long r1 = r18 & r1
            int r15 = (r1 > r27 ? 1 : (r1 == r27 ? 0 : -1))
            if (r15 != 0) goto L_0x0231
            r25 = 2
            goto L_0x0277
        L_0x0231:
            android.view.View r1 = r0.getChildAt(r5)
            android.view.ViewGroup$LayoutParams r2 = r1.getLayoutParams()
            androidx.appcompat.widget.ActionMenuView$LayoutParams r2 = (androidx.appcompat.widget.ActionMenuView.LayoutParams) r2
            boolean r15 = r1 instanceof androidx.appcompat.view.menu.ActionMenuItemView
            if (r15 == 0) goto L_0x0255
            r2.extraPixels = r4
            r15 = 1
            r2.expanded = r15
            if (r5 != 0) goto L_0x0251
            boolean r15 = r2.preventEdgeOffset
            if (r15 != 0) goto L_0x0251
            int r15 = -r4
            r25 = 2
            int r15 = r15 / 2
            r2.leftMargin = r15
        L_0x0251:
            r9 = 1
            r25 = 2
            goto L_0x0277
        L_0x0255:
            boolean r15 = r2.isOverflowButton
            if (r15 == 0) goto L_0x0267
            r2.extraPixels = r4
            r15 = 1
            r2.expanded = r15
            int r15 = -r4
            r25 = 2
            int r15 = r15 / 2
            r2.rightMargin = r15
            r9 = 1
            goto L_0x0277
        L_0x0267:
            r25 = 2
            if (r5 == 0) goto L_0x026f
            int r15 = r4 / 2
            r2.leftMargin = r15
        L_0x026f:
            int r15 = r11 + -1
            if (r5 == r15) goto L_0x0277
            int r15 = r4 / 2
            r2.rightMargin = r15
        L_0x0277:
            int r5 = r5 + 1
            r1 = r10
            r2 = r20
            goto L_0x021f
        L_0x027d:
            r10 = r1
            r20 = r2
            r12 = 0
            goto L_0x0285
        L_0x0282:
            r10 = r1
        L_0x0283:
            r9 = r35
        L_0x0285:
            if (r9 == 0) goto L_0x02b5
            r2 = 0
        L_0x0288:
            if (r2 >= r11) goto L_0x02b2
            android.view.View r4 = r0.getChildAt(r2)
            android.view.ViewGroup$LayoutParams r5 = r4.getLayoutParams()
            androidx.appcompat.widget.ActionMenuView$LayoutParams r5 = (androidx.appcompat.widget.ActionMenuView.LayoutParams) r5
            boolean r15 = r5.expanded
            if (r15 != 0) goto L_0x029b
            r22 = r3
            goto L_0x02ad
        L_0x029b:
            int r15 = r5.cellsUsed
            int r15 = r15 * r8
            int r1 = r5.extraPixels
            int r15 = r15 + r1
            r22 = r3
            r1 = 1073741824(0x40000000, float:2.0)
            int r3 = android.view.View.MeasureSpec.makeMeasureSpec(r15, r1)
            r4.measure(r3, r6)
        L_0x02ad:
            int r2 = r2 + 1
            r3 = r22
            goto L_0x0288
        L_0x02b2:
            r22 = r3
            goto L_0x02b7
        L_0x02b5:
            r22 = r3
        L_0x02b7:
            r1 = r36
            r2 = 1073741824(0x40000000, float:2.0)
            if (r1 == r2) goto L_0x02bf
            r3 = r13
            goto L_0x02c1
        L_0x02bf:
            r3 = r16
        L_0x02c1:
            r2 = r37
            r0.setMeasuredDimension(r2, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ActionMenuView.onMeasureExactFormat(int, int):void");
    }

    static int measureChildForCells(View child, int cellSize, int cellsRemaining, int parentHeightMeasureSpec, int parentHeightPadding) {
        View view = child;
        int i = cellsRemaining;
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        int childHeightSpec = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(parentHeightMeasureSpec) - parentHeightPadding, View.MeasureSpec.getMode(parentHeightMeasureSpec));
        ActionMenuItemView itemView = view instanceof ActionMenuItemView ? (ActionMenuItemView) view : null;
        boolean expandable = false;
        boolean hasText = itemView != null && itemView.hasText();
        int cellsUsed = 0;
        if (i > 0 && (!hasText || i >= 2)) {
            child.measure(View.MeasureSpec.makeMeasureSpec(cellSize * i, Integer.MIN_VALUE), childHeightSpec);
            int measuredWidth = child.getMeasuredWidth();
            cellsUsed = measuredWidth / cellSize;
            if (measuredWidth % cellSize != 0) {
                cellsUsed++;
            }
            if (hasText && cellsUsed < 2) {
                cellsUsed = 2;
            }
        }
        if (!lp.isOverflowButton && hasText) {
            expandable = true;
        }
        lp.expandable = expandable;
        lp.cellsUsed = cellsUsed;
        child.measure(View.MeasureSpec.makeMeasureSpec(cellsUsed * cellSize, 1073741824), childHeightSpec);
        return cellsUsed;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int i;
        int nonOverflowCount;
        int overflowWidth;
        int dividerWidth;
        int r;
        int l;
        ActionMenuView actionMenuView = this;
        if (!actionMenuView.mFormatItems) {
            super.onLayout(changed, left, top, right, bottom);
            return;
        }
        int childCount = getChildCount();
        int midVertical = (bottom - top) / 2;
        int dividerWidth2 = getDividerWidth();
        int overflowWidth2 = 0;
        int nonOverflowCount2 = 0;
        int widthRemaining = ((right - left) - getPaddingRight()) - getPaddingLeft();
        boolean hasOverflow = false;
        boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
        int i2 = 0;
        while (true) {
            i = 8;
            if (i2 >= childCount) {
                break;
            }
            View v = actionMenuView.getChildAt(i2);
            if (v.getVisibility() == 8) {
                dividerWidth = dividerWidth2;
            } else {
                LayoutParams p = (LayoutParams) v.getLayoutParams();
                if (p.isOverflowButton) {
                    overflowWidth2 = v.getMeasuredWidth();
                    if (actionMenuView.hasSupportDividerBeforeChildAt(i2)) {
                        overflowWidth2 += dividerWidth2;
                    }
                    int height = v.getMeasuredHeight();
                    if (isLayoutRtl) {
                        l = getPaddingLeft() + p.leftMargin;
                        r = l + overflowWidth2;
                    } else {
                        r = (getWidth() - getPaddingRight()) - p.rightMargin;
                        l = r - overflowWidth2;
                    }
                    int t = midVertical - (height / 2);
                    dividerWidth = dividerWidth2;
                    v.layout(l, t, r, t + height);
                    widthRemaining -= overflowWidth2;
                    hasOverflow = true;
                } else {
                    dividerWidth = dividerWidth2;
                    widthRemaining -= (v.getMeasuredWidth() + p.leftMargin) + p.rightMargin;
                    actionMenuView.hasSupportDividerBeforeChildAt(i2);
                    nonOverflowCount2++;
                }
            }
            i2++;
            dividerWidth2 = dividerWidth;
        }
        if (childCount != 1 || hasOverflow) {
            int spacerCount = nonOverflowCount2 - (!hasOverflow);
            int spacerSize = Math.max(0, spacerCount > 0 ? widthRemaining / spacerCount : 0);
            if (isLayoutRtl) {
                int startRight = getWidth() - getPaddingRight();
                int i3 = 0;
                while (i3 < childCount) {
                    View v2 = actionMenuView.getChildAt(i3);
                    LayoutParams lp = (LayoutParams) v2.getLayoutParams();
                    if (v2.getVisibility() == i) {
                        overflowWidth = overflowWidth2;
                        nonOverflowCount = nonOverflowCount2;
                    } else if (lp.isOverflowButton) {
                        overflowWidth = overflowWidth2;
                        nonOverflowCount = nonOverflowCount2;
                    } else {
                        int startRight2 = startRight - lp.rightMargin;
                        int width = v2.getMeasuredWidth();
                        int height2 = v2.getMeasuredHeight();
                        int t2 = midVertical - (height2 / 2);
                        overflowWidth = overflowWidth2;
                        nonOverflowCount = nonOverflowCount2;
                        v2.layout(startRight2 - width, t2, startRight2, t2 + height2);
                        startRight = startRight2 - ((lp.leftMargin + width) + spacerSize);
                    }
                    i3++;
                    overflowWidth2 = overflowWidth;
                    nonOverflowCount2 = nonOverflowCount;
                    i = 8;
                }
                int i4 = nonOverflowCount2;
                return;
            }
            int i5 = nonOverflowCount2;
            int startLeft = getPaddingLeft();
            int i6 = 0;
            while (i6 < childCount) {
                View v3 = actionMenuView.getChildAt(i6);
                LayoutParams lp2 = (LayoutParams) v3.getLayoutParams();
                if (v3.getVisibility() != 8 && !lp2.isOverflowButton) {
                    int startLeft2 = startLeft + lp2.leftMargin;
                    int width2 = v3.getMeasuredWidth();
                    int height3 = v3.getMeasuredHeight();
                    int t3 = midVertical - (height3 / 2);
                    v3.layout(startLeft2, t3, startLeft2 + width2, t3 + height3);
                    startLeft = startLeft2 + lp2.rightMargin + width2 + spacerSize;
                }
                i6++;
                actionMenuView = this;
            }
            return;
        }
        View v4 = actionMenuView.getChildAt(0);
        int width3 = v4.getMeasuredWidth();
        int height4 = v4.getMeasuredHeight();
        int l2 = ((right - left) / 2) - (width3 / 2);
        int t4 = midVertical - (height4 / 2);
        v4.layout(l2, t4, l2 + width3, t4 + height4);
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        dismissPopupMenus();
    }

    public void setOverflowIcon(Drawable icon) {
        getMenu();
        this.mPresenter.setOverflowIcon(icon);
    }

    public Drawable getOverflowIcon() {
        getMenu();
        return this.mPresenter.getOverflowIcon();
    }

    public boolean isOverflowReserved() {
        return this.mReserveOverflow;
    }

    public void setOverflowReserved(boolean reserveOverflow) {
        this.mReserveOverflow = reserveOverflow;
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        LayoutParams params = new LayoutParams(-2, -2);
        params.gravity = 16;
        return params;
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        LayoutParams result;
        if (p == null) {
            return generateDefaultLayoutParams();
        }
        if (p instanceof LayoutParams) {
            result = new LayoutParams((LayoutParams) p);
        } else {
            result = new LayoutParams(p);
        }
        if (result.gravity <= 0) {
            result.gravity = 16;
        }
        return result;
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public LayoutParams generateOverflowButtonLayoutParams() {
        LayoutParams result = generateDefaultLayoutParams();
        result.isOverflowButton = true;
        return result;
    }

    public boolean invokeItem(MenuItemImpl item) {
        return this.mMenu.performItemAction(item, 0);
    }

    public int getWindowAnimations() {
        return 0;
    }

    public void initialize(MenuBuilder menu) {
        this.mMenu = menu;
    }

    public Menu getMenu() {
        if (this.mMenu == null) {
            Context context = getContext();
            MenuBuilder menuBuilder = new MenuBuilder(context);
            this.mMenu = menuBuilder;
            menuBuilder.setCallback(new MenuBuilderCallback());
            ActionMenuPresenter actionMenuPresenter = new ActionMenuPresenter(context);
            this.mPresenter = actionMenuPresenter;
            actionMenuPresenter.setReserveOverflow(true);
            ActionMenuPresenter actionMenuPresenter2 = this.mPresenter;
            MenuPresenter.Callback callback = this.mActionMenuPresenterCallback;
            if (callback == null) {
                callback = new ActionMenuPresenterCallback();
            }
            actionMenuPresenter2.setCallback(callback);
            this.mMenu.addMenuPresenter(this.mPresenter, this.mPopupContext);
            this.mPresenter.setMenuView(this);
        }
        return this.mMenu;
    }

    public void setMenuCallbacks(MenuPresenter.Callback pcb, MenuBuilder.Callback mcb) {
        this.mActionMenuPresenterCallback = pcb;
        this.mMenuBuilderCallback = mcb;
    }

    public MenuBuilder peekMenu() {
        return this.mMenu;
    }

    public boolean showOverflowMenu() {
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        return actionMenuPresenter != null && actionMenuPresenter.showOverflowMenu();
    }

    public boolean hideOverflowMenu() {
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        return actionMenuPresenter != null && actionMenuPresenter.hideOverflowMenu();
    }

    public boolean isOverflowMenuShowing() {
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        return actionMenuPresenter != null && actionMenuPresenter.isOverflowMenuShowing();
    }

    public boolean isOverflowMenuShowPending() {
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        return actionMenuPresenter != null && actionMenuPresenter.isOverflowMenuShowPending();
    }

    public void dismissPopupMenus() {
        ActionMenuPresenter actionMenuPresenter = this.mPresenter;
        if (actionMenuPresenter != null) {
            actionMenuPresenter.dismissPopupMenus();
        }
    }

    /* access modifiers changed from: protected */
    public boolean hasSupportDividerBeforeChildAt(int childIndex) {
        if (childIndex == 0) {
            return false;
        }
        View childBefore = getChildAt(childIndex - 1);
        View child = getChildAt(childIndex);
        boolean result = false;
        if (childIndex < getChildCount() && (childBefore instanceof ActionMenuChildView)) {
            result = false | ((ActionMenuChildView) childBefore).needsDividerAfter();
        }
        if (childIndex <= 0 || !(child instanceof ActionMenuChildView)) {
            return result;
        }
        return result | ((ActionMenuChildView) child).needsDividerBefore();
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        return false;
    }

    public void setExpandedActionViewsExclusive(boolean exclusive) {
        this.mPresenter.setExpandedActionViewsExclusive(exclusive);
    }

    private class MenuBuilderCallback implements MenuBuilder.Callback {
        MenuBuilderCallback() {
        }

        public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
            return ActionMenuView.this.mOnMenuItemClickListener != null && ActionMenuView.this.mOnMenuItemClickListener.onMenuItemClick(item);
        }

        public void onMenuModeChange(MenuBuilder menu) {
            if (ActionMenuView.this.mMenuBuilderCallback != null) {
                ActionMenuView.this.mMenuBuilderCallback.onMenuModeChange(menu);
            }
        }
    }

    private static class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        ActionMenuPresenterCallback() {
        }

        public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
        }

        public boolean onOpenSubMenu(MenuBuilder subMenu) {
            return false;
        }
    }

    public static class LayoutParams extends LinearLayoutCompat.LayoutParams {
        @ViewDebug.ExportedProperty
        public int cellsUsed;
        @ViewDebug.ExportedProperty
        public boolean expandable;
        boolean expanded;
        @ViewDebug.ExportedProperty
        public int extraPixels;
        @ViewDebug.ExportedProperty
        public boolean isOverflowButton;
        @ViewDebug.ExportedProperty
        public boolean preventEdgeOffset;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(ViewGroup.LayoutParams other) {
            super(other);
        }

        public LayoutParams(LayoutParams other) {
            super((ViewGroup.LayoutParams) other);
            this.isOverflowButton = other.isOverflowButton;
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.isOverflowButton = false;
        }

        LayoutParams(int width, int height, boolean isOverflowButton2) {
            super(width, height);
            this.isOverflowButton = isOverflowButton2;
        }
    }
}
