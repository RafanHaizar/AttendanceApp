package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.core.Metrics;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Guideline;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.ViewCompat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ConstraintLayout extends ViewGroup {
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_DRAW_CONSTRAINTS = false;
    public static final int DESIGN_INFO_ID = 0;
    private static final boolean MEASURE = false;
    private static final boolean OPTIMIZE_HEIGHT_CHANGE = false;
    private static final String TAG = "ConstraintLayout";
    private static final boolean USE_CONSTRAINTS_HELPER = true;
    public static final String VERSION = "ConstraintLayout-2.1.4";
    private static SharedValues sSharedValues = null;
    SparseArray<View> mChildrenByIds = new SparseArray<>();
    /* access modifiers changed from: private */
    public ArrayList<ConstraintHelper> mConstraintHelpers = new ArrayList<>(4);
    protected ConstraintLayoutStates mConstraintLayoutSpec = null;
    private ConstraintSet mConstraintSet = null;
    private int mConstraintSetId = -1;
    private ConstraintsChangedListener mConstraintsChangedListener;
    private HashMap<String, Integer> mDesignIds = new HashMap<>();
    protected boolean mDirtyHierarchy = USE_CONSTRAINTS_HELPER;
    private int mLastMeasureHeight = -1;
    int mLastMeasureHeightMode = 0;
    int mLastMeasureHeightSize = -1;
    private int mLastMeasureWidth = -1;
    int mLastMeasureWidthMode = 0;
    int mLastMeasureWidthSize = -1;
    /* access modifiers changed from: protected */
    public ConstraintWidgetContainer mLayoutWidget = new ConstraintWidgetContainer();
    private int mMaxHeight = Integer.MAX_VALUE;
    private int mMaxWidth = Integer.MAX_VALUE;
    Measurer mMeasurer = new Measurer(this);
    private Metrics mMetrics;
    private int mMinHeight = 0;
    private int mMinWidth = 0;
    private int mOnMeasureHeightMeasureSpec = 0;
    private int mOnMeasureWidthMeasureSpec = 0;
    /* access modifiers changed from: private */
    public int mOptimizationLevel = 257;
    private SparseArray<ConstraintWidget> mTempMapIdToWidget = new SparseArray<>();

    public static SharedValues getSharedValues() {
        if (sSharedValues == null) {
            sSharedValues = new SharedValues();
        }
        return sSharedValues;
    }

    public void setDesignInformation(int type, Object value1, Object value2) {
        if (type == 0 && (value1 instanceof String) && (value2 instanceof Integer)) {
            if (this.mDesignIds == null) {
                this.mDesignIds = new HashMap<>();
            }
            String name = (String) value1;
            int index = name.indexOf("/");
            if (index != -1) {
                name = name.substring(index + 1);
            }
            this.mDesignIds.put(name, Integer.valueOf(((Integer) value2).intValue()));
        }
    }

    public Object getDesignInformation(int type, Object value) {
        if (type != 0 || !(value instanceof String)) {
            return null;
        }
        String name = (String) value;
        HashMap<String, Integer> hashMap = this.mDesignIds;
        if (hashMap == null || !hashMap.containsKey(name)) {
            return null;
        }
        return this.mDesignIds.get(name);
    }

    public ConstraintLayout(Context context) {
        super(context);
        init((AttributeSet) null, 0, 0);
    }

    public ConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    public ConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);
    }

    public ConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }

    public void setId(int id) {
        this.mChildrenByIds.remove(getId());
        super.setId(id);
        this.mChildrenByIds.put(getId(), this);
    }

    class Measurer implements BasicMeasure.Measurer {
        ConstraintLayout layout;
        int layoutHeightSpec;
        int layoutWidthSpec;
        int paddingBottom;
        int paddingHeight;
        int paddingTop;
        int paddingWidth;

        public void captureLayoutInfo(int widthSpec, int heightSpec, int top, int bottom, int width, int height) {
            this.paddingTop = top;
            this.paddingBottom = bottom;
            this.paddingWidth = width;
            this.paddingHeight = height;
            this.layoutWidthSpec = widthSpec;
            this.layoutHeightSpec = heightSpec;
        }

        public Measurer(ConstraintLayout l) {
            this.layout = l;
        }

        /* JADX WARNING: Removed duplicated region for block: B:127:0x01f4  */
        /* JADX WARNING: Removed duplicated region for block: B:130:0x01fe  */
        /* JADX WARNING: Removed duplicated region for block: B:133:0x0203 A[RETURN] */
        /* JADX WARNING: Removed duplicated region for block: B:134:0x0204  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void measure(androidx.constraintlayout.core.widgets.ConstraintWidget r29, androidx.constraintlayout.core.widgets.analyzer.BasicMeasure.Measure r30) {
            /*
                r28 = this;
                r0 = r28
                r1 = r29
                r2 = r30
                if (r1 != 0) goto L_0x0009
                return
            L_0x0009:
                int r3 = r29.getVisibility()
                r4 = 8
                r5 = 0
                if (r3 != r4) goto L_0x001f
                boolean r3 = r29.isInPlaceholder()
                if (r3 != 0) goto L_0x001f
                r2.measuredWidth = r5
                r2.measuredHeight = r5
                r2.measuredBaseline = r5
                return
            L_0x001f:
                androidx.constraintlayout.core.widgets.ConstraintWidget r3 = r29.getParent()
                if (r3 != 0) goto L_0x0026
                return
            L_0x0026:
                androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r3 = r2.horizontalBehavior
                androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r4 = r2.verticalBehavior
                int r6 = r2.horizontalDimension
                int r7 = r2.verticalDimension
                r8 = 0
                r9 = 0
                int r10 = r0.paddingTop
                int r11 = r0.paddingBottom
                int r10 = r10 + r11
                int r11 = r0.paddingWidth
                java.lang.Object r12 = r29.getCompanionWidget()
                android.view.View r12 = (android.view.View) r12
                int[] r13 = androidx.constraintlayout.widget.ConstraintLayout.C06561.f1024x6d00e4a2
                int r14 = r3.ordinal()
                r13 = r13[r14]
                r15 = -2
                r14 = 1
                switch(r13) {
                    case 1: goto L_0x00b3;
                    case 2: goto L_0x00ab;
                    case 3: goto L_0x009e;
                    case 4: goto L_0x004c;
                    default: goto L_0x004a;
                }
            L_0x004a:
                goto L_0x00ba
            L_0x004c:
                int r13 = r0.layoutWidthSpec
                int r8 = android.view.ViewGroup.getChildMeasureSpec(r13, r11, r15)
                int r13 = r1.mMatchConstraintDefaultWidth
                if (r13 != r14) goto L_0x0058
                r13 = 1
                goto L_0x0059
            L_0x0058:
                r13 = 0
            L_0x0059:
                int r14 = r2.measureStrategy
                int r15 = androidx.constraintlayout.core.widgets.analyzer.BasicMeasure.Measure.TRY_GIVEN_DIMENSIONS
                if (r14 == r15) goto L_0x0065
                int r14 = r2.measureStrategy
                int r15 = androidx.constraintlayout.core.widgets.analyzer.BasicMeasure.Measure.USE_GIVEN_DIMENSIONS
                if (r14 != r15) goto L_0x00ba
            L_0x0065:
                int r14 = r12.getMeasuredHeight()
                int r15 = r29.getHeight()
                if (r14 != r15) goto L_0x0071
                r14 = 1
                goto L_0x0072
            L_0x0071:
                r14 = 0
            L_0x0072:
                int r15 = r2.measureStrategy
                int r5 = androidx.constraintlayout.core.widgets.analyzer.BasicMeasure.Measure.USE_GIVEN_DIMENSIONS
                if (r15 == r5) goto L_0x008b
                if (r13 == 0) goto L_0x008b
                if (r13 == 0) goto L_0x007e
                if (r14 != 0) goto L_0x008b
            L_0x007e:
                boolean r5 = r12 instanceof androidx.constraintlayout.widget.Placeholder
                if (r5 != 0) goto L_0x008b
                boolean r5 = r29.isResolvedHorizontally()
                if (r5 == 0) goto L_0x0089
                goto L_0x008b
            L_0x0089:
                r5 = 0
                goto L_0x008c
            L_0x008b:
                r5 = 1
            L_0x008c:
                if (r5 == 0) goto L_0x009b
                int r15 = r29.getWidth()
                r17 = r5
                r5 = 1073741824(0x40000000, float:2.0)
                int r8 = android.view.View.MeasureSpec.makeMeasureSpec(r15, r5)
                goto L_0x00ba
            L_0x009b:
                r17 = r5
                goto L_0x00ba
            L_0x009e:
                int r5 = r0.layoutWidthSpec
                int r13 = r29.getHorizontalMargin()
                int r13 = r13 + r11
                r14 = -1
                int r8 = android.view.ViewGroup.getChildMeasureSpec(r5, r13, r14)
                goto L_0x00ba
            L_0x00ab:
                int r5 = r0.layoutWidthSpec
                r13 = -2
                int r8 = android.view.ViewGroup.getChildMeasureSpec(r5, r11, r13)
                goto L_0x00ba
            L_0x00b3:
                r5 = 1073741824(0x40000000, float:2.0)
                int r8 = android.view.View.MeasureSpec.makeMeasureSpec(r6, r5)
            L_0x00ba:
                int[] r5 = androidx.constraintlayout.widget.ConstraintLayout.C06561.f1024x6d00e4a2
                int r13 = r4.ordinal()
                r5 = r5[r13]
                switch(r5) {
                    case 1: goto L_0x0130;
                    case 2: goto L_0x0128;
                    case 3: goto L_0x011b;
                    case 4: goto L_0x00c7;
                    default: goto L_0x00c5;
                }
            L_0x00c5:
                goto L_0x0137
            L_0x00c7:
                int r5 = r0.layoutHeightSpec
                r13 = -2
                int r9 = android.view.ViewGroup.getChildMeasureSpec(r5, r10, r13)
                int r5 = r1.mMatchConstraintDefaultHeight
                r13 = 1
                if (r5 != r13) goto L_0x00d5
                r5 = 1
                goto L_0x00d6
            L_0x00d5:
                r5 = 0
            L_0x00d6:
                int r13 = r2.measureStrategy
                int r14 = androidx.constraintlayout.core.widgets.analyzer.BasicMeasure.Measure.TRY_GIVEN_DIMENSIONS
                if (r13 == r14) goto L_0x00e2
                int r13 = r2.measureStrategy
                int r14 = androidx.constraintlayout.core.widgets.analyzer.BasicMeasure.Measure.USE_GIVEN_DIMENSIONS
                if (r13 != r14) goto L_0x0137
            L_0x00e2:
                int r13 = r12.getMeasuredWidth()
                int r14 = r29.getWidth()
                if (r13 != r14) goto L_0x00ee
                r13 = 1
                goto L_0x00ef
            L_0x00ee:
                r13 = 0
            L_0x00ef:
                int r14 = r2.measureStrategy
                int r15 = androidx.constraintlayout.core.widgets.analyzer.BasicMeasure.Measure.USE_GIVEN_DIMENSIONS
                if (r14 == r15) goto L_0x0108
                if (r5 == 0) goto L_0x0108
                if (r5 == 0) goto L_0x00fb
                if (r13 != 0) goto L_0x0108
            L_0x00fb:
                boolean r14 = r12 instanceof androidx.constraintlayout.widget.Placeholder
                if (r14 != 0) goto L_0x0108
                boolean r14 = r29.isResolvedVertically()
                if (r14 == 0) goto L_0x0106
                goto L_0x0108
            L_0x0106:
                r14 = 0
                goto L_0x0109
            L_0x0108:
                r14 = 1
            L_0x0109:
                if (r14 == 0) goto L_0x0118
                int r15 = r29.getHeight()
                r16 = r5
                r5 = 1073741824(0x40000000, float:2.0)
                int r9 = android.view.View.MeasureSpec.makeMeasureSpec(r15, r5)
                goto L_0x0137
            L_0x0118:
                r16 = r5
                goto L_0x0137
            L_0x011b:
                int r5 = r0.layoutHeightSpec
                int r13 = r29.getVerticalMargin()
                int r13 = r13 + r10
                r14 = -1
                int r9 = android.view.ViewGroup.getChildMeasureSpec(r5, r13, r14)
                goto L_0x0137
            L_0x0128:
                int r5 = r0.layoutHeightSpec
                r13 = -2
                int r9 = android.view.ViewGroup.getChildMeasureSpec(r5, r10, r13)
                goto L_0x0137
            L_0x0130:
                r5 = 1073741824(0x40000000, float:2.0)
                int r9 = android.view.View.MeasureSpec.makeMeasureSpec(r7, r5)
            L_0x0137:
                androidx.constraintlayout.core.widgets.ConstraintWidget r5 = r29.getParent()
                androidx.constraintlayout.core.widgets.ConstraintWidgetContainer r5 = (androidx.constraintlayout.core.widgets.ConstraintWidgetContainer) r5
                if (r5 == 0) goto L_0x01b9
                androidx.constraintlayout.widget.ConstraintLayout r13 = androidx.constraintlayout.widget.ConstraintLayout.this
                int r13 = r13.mOptimizationLevel
                r14 = 256(0x100, float:3.59E-43)
                boolean r13 = androidx.constraintlayout.core.widgets.Optimizer.enabled(r13, r14)
                if (r13 == 0) goto L_0x01b9
                int r13 = r12.getMeasuredWidth()
                int r14 = r29.getWidth()
                if (r13 != r14) goto L_0x01b9
                int r13 = r12.getMeasuredWidth()
                int r14 = r5.getWidth()
                if (r13 >= r14) goto L_0x01b9
                int r13 = r12.getMeasuredHeight()
                int r14 = r29.getHeight()
                if (r13 != r14) goto L_0x01b9
                int r13 = r12.getMeasuredHeight()
                int r14 = r5.getHeight()
                if (r13 >= r14) goto L_0x01b9
                int r13 = r12.getBaseline()
                int r14 = r29.getBaselineDistance()
                if (r13 != r14) goto L_0x01b9
                boolean r13 = r29.isMeasureRequested()
                if (r13 != 0) goto L_0x01b9
                int r13 = r29.getLastHorizontalMeasureSpec()
                int r14 = r29.getWidth()
                boolean r13 = r0.isSimilarSpec(r13, r8, r14)
                if (r13 == 0) goto L_0x01a3
                int r13 = r29.getLastVerticalMeasureSpec()
                int r14 = r29.getHeight()
                boolean r13 = r0.isSimilarSpec(r13, r9, r14)
                if (r13 == 0) goto L_0x01a3
                r13 = 1
                goto L_0x01a4
            L_0x01a3:
                r13 = 0
            L_0x01a4:
                if (r13 == 0) goto L_0x01b9
                int r14 = r29.getWidth()
                r2.measuredWidth = r14
                int r14 = r29.getHeight()
                r2.measuredHeight = r14
                int r14 = r29.getBaselineDistance()
                r2.measuredBaseline = r14
                return
            L_0x01b9:
                androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r13 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
                if (r3 != r13) goto L_0x01bf
                r13 = 1
                goto L_0x01c0
            L_0x01bf:
                r13 = 0
            L_0x01c0:
                androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r14 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
                if (r4 != r14) goto L_0x01c6
                r14 = 1
                goto L_0x01c7
            L_0x01c6:
                r14 = 0
            L_0x01c7:
                androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r15 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_PARENT
                if (r4 == r15) goto L_0x01d2
                androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r15 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.FIXED
                if (r4 != r15) goto L_0x01d0
                goto L_0x01d2
            L_0x01d0:
                r15 = 0
                goto L_0x01d3
            L_0x01d2:
                r15 = 1
            L_0x01d3:
                r16 = r4
                androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r4 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_PARENT
                if (r3 == r4) goto L_0x01e0
                androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r4 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.FIXED
                if (r3 != r4) goto L_0x01de
                goto L_0x01e0
            L_0x01de:
                r4 = 0
                goto L_0x01e1
            L_0x01e0:
                r4 = 1
            L_0x01e1:
                r17 = 0
                if (r13 == 0) goto L_0x01ef
                r18 = r3
                float r3 = r1.mDimensionRatio
                int r3 = (r3 > r17 ? 1 : (r3 == r17 ? 0 : -1))
                if (r3 <= 0) goto L_0x01f1
                r3 = 1
                goto L_0x01f2
            L_0x01ef:
                r18 = r3
            L_0x01f1:
                r3 = 0
            L_0x01f2:
                if (r14 == 0) goto L_0x01fe
                r19 = r5
                float r5 = r1.mDimensionRatio
                int r5 = (r5 > r17 ? 1 : (r5 == r17 ? 0 : -1))
                if (r5 <= 0) goto L_0x0200
                r5 = 1
                goto L_0x0201
            L_0x01fe:
                r19 = r5
            L_0x0200:
                r5 = 0
            L_0x0201:
                if (r12 != 0) goto L_0x0204
                return
            L_0x0204:
                android.view.ViewGroup$LayoutParams r17 = r12.getLayoutParams()
                r20 = r6
                r6 = r17
                androidx.constraintlayout.widget.ConstraintLayout$LayoutParams r6 = (androidx.constraintlayout.widget.ConstraintLayout.LayoutParams) r6
                r17 = 0
                r21 = 0
                r22 = 0
                r23 = r7
                int r7 = r2.measureStrategy
                r24 = r10
                int r10 = androidx.constraintlayout.core.widgets.analyzer.BasicMeasure.Measure.TRY_GIVEN_DIMENSIONS
                if (r7 == r10) goto L_0x023d
                int r7 = r2.measureStrategy
                int r10 = androidx.constraintlayout.core.widgets.analyzer.BasicMeasure.Measure.USE_GIVEN_DIMENSIONS
                if (r7 == r10) goto L_0x023d
                if (r13 == 0) goto L_0x023d
                int r7 = r1.mMatchConstraintDefaultWidth
                if (r7 != 0) goto L_0x023d
                if (r14 == 0) goto L_0x023d
                int r7 = r1.mMatchConstraintDefaultHeight
                if (r7 == 0) goto L_0x0231
                goto L_0x023d
            L_0x0231:
                r27 = r3
                r25 = r11
                r0 = r17
                r11 = r21
                r3 = r22
                goto L_0x0314
            L_0x023d:
                boolean r7 = r12 instanceof androidx.constraintlayout.widget.VirtualLayout
                if (r7 == 0) goto L_0x024f
                boolean r7 = r1 instanceof androidx.constraintlayout.core.widgets.VirtualLayout
                if (r7 == 0) goto L_0x024f
                r7 = r1
                androidx.constraintlayout.core.widgets.VirtualLayout r7 = (androidx.constraintlayout.core.widgets.VirtualLayout) r7
                r10 = r12
                androidx.constraintlayout.widget.VirtualLayout r10 = (androidx.constraintlayout.widget.VirtualLayout) r10
                r10.onMeasure(r7, r8, r9)
                goto L_0x0252
            L_0x024f:
                r12.measure(r8, r9)
            L_0x0252:
                r1.setLastMeasureSpec(r8, r9)
                int r7 = r12.getMeasuredWidth()
                int r10 = r12.getMeasuredHeight()
                int r22 = r12.getBaseline()
                r25 = r7
                r17 = r10
                r21 = r8
                int r8 = r1.mMatchConstraintMinWidth
                if (r8 <= 0) goto L_0x0278
                int r8 = r1.mMatchConstraintMinWidth
                r26 = r9
                r9 = r25
                int r25 = java.lang.Math.max(r8, r9)
                r9 = r25
                goto L_0x027c
            L_0x0278:
                r26 = r9
                r9 = r25
            L_0x027c:
                int r8 = r1.mMatchConstraintMaxWidth
                if (r8 <= 0) goto L_0x0286
                int r8 = r1.mMatchConstraintMaxWidth
                int r9 = java.lang.Math.min(r8, r9)
            L_0x0286:
                int r8 = r1.mMatchConstraintMinHeight
                if (r8 <= 0) goto L_0x0297
                int r8 = r1.mMatchConstraintMinHeight
                r25 = r11
                r11 = r17
                int r17 = java.lang.Math.max(r8, r11)
                r11 = r17
                goto L_0x029b
            L_0x0297:
                r25 = r11
                r11 = r17
            L_0x029b:
                int r8 = r1.mMatchConstraintMaxHeight
                if (r8 <= 0) goto L_0x02a5
                int r8 = r1.mMatchConstraintMaxHeight
                int r11 = java.lang.Math.min(r8, r11)
            L_0x02a5:
                androidx.constraintlayout.widget.ConstraintLayout r8 = androidx.constraintlayout.widget.ConstraintLayout.this
                int r8 = r8.mOptimizationLevel
                r0 = 1
                boolean r8 = androidx.constraintlayout.core.widgets.Optimizer.enabled(r8, r0)
                if (r8 != 0) goto L_0x02d5
                if (r3 == 0) goto L_0x02c4
                if (r15 == 0) goto L_0x02c4
                float r0 = r1.mDimensionRatio
                r27 = r3
                float r3 = (float) r11
                float r3 = r3 * r0
                r17 = 1056964608(0x3f000000, float:0.5)
                float r3 = r3 + r17
                int r0 = (int) r3
                r9 = r0
                goto L_0x02d7
            L_0x02c4:
                r27 = r3
                if (r5 == 0) goto L_0x02d7
                if (r4 == 0) goto L_0x02d7
                float r0 = r1.mDimensionRatio
                float r3 = (float) r9
                float r3 = r3 / r0
                r17 = 1056964608(0x3f000000, float:0.5)
                float r3 = r3 + r17
                int r3 = (int) r3
                r11 = r3
                goto L_0x02d7
            L_0x02d5:
                r27 = r3
            L_0x02d7:
                if (r7 != r9) goto L_0x02e4
                if (r10 == r11) goto L_0x02dc
                goto L_0x02e4
            L_0x02dc:
                r0 = r9
                r8 = r21
                r3 = r22
                r9 = r26
                goto L_0x0314
            L_0x02e4:
                if (r7 == r9) goto L_0x02ed
                r0 = 1073741824(0x40000000, float:2.0)
                int r3 = android.view.View.MeasureSpec.makeMeasureSpec(r9, r0)
                goto L_0x02f1
            L_0x02ed:
                r0 = 1073741824(0x40000000, float:2.0)
                r3 = r21
            L_0x02f1:
                if (r10 == r11) goto L_0x02f8
                int r0 = android.view.View.MeasureSpec.makeMeasureSpec(r11, r0)
                goto L_0x02fa
            L_0x02f8:
                r0 = r26
            L_0x02fa:
                r12.measure(r3, r0)
                r1.setLastMeasureSpec(r3, r0)
                int r17 = r12.getMeasuredWidth()
                int r21 = r12.getMeasuredHeight()
                int r22 = r12.getBaseline()
                r9 = r0
                r8 = r3
                r0 = r17
                r11 = r21
                r3 = r22
            L_0x0314:
                r7 = -1
                if (r3 == r7) goto L_0x0319
                r7 = 1
                goto L_0x031a
            L_0x0319:
                r7 = 0
            L_0x031a:
                int r10 = r2.horizontalDimension
                if (r0 != r10) goto L_0x0325
                int r10 = r2.verticalDimension
                if (r11 == r10) goto L_0x0323
                goto L_0x0325
            L_0x0323:
                r10 = 0
                goto L_0x0326
            L_0x0325:
                r10 = 1
            L_0x0326:
                r2.measuredNeedsSolverPass = r10
                boolean r10 = r6.needsBaseline
                if (r10 == 0) goto L_0x032d
                r7 = 1
            L_0x032d:
                if (r7 == 0) goto L_0x033b
                r10 = -1
                if (r3 == r10) goto L_0x033b
                int r10 = r29.getBaselineDistance()
                if (r10 == r3) goto L_0x033b
                r10 = 1
                r2.measuredNeedsSolverPass = r10
            L_0x033b:
                r2.measuredWidth = r0
                r2.measuredHeight = r11
                r2.measuredHasBaseline = r7
                r2.measuredBaseline = r3
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.widget.ConstraintLayout.Measurer.measure(androidx.constraintlayout.core.widgets.ConstraintWidget, androidx.constraintlayout.core.widgets.analyzer.BasicMeasure$Measure):void");
        }

        private boolean isSimilarSpec(int lastMeasureSpec, int spec, int widgetSize) {
            if (lastMeasureSpec == spec) {
                return ConstraintLayout.USE_CONSTRAINTS_HELPER;
            }
            int lastMode = View.MeasureSpec.getMode(lastMeasureSpec);
            int size = View.MeasureSpec.getSize(lastMeasureSpec);
            int mode = View.MeasureSpec.getMode(spec);
            int size2 = View.MeasureSpec.getSize(spec);
            if (mode != 1073741824) {
                return false;
            }
            if ((lastMode == Integer.MIN_VALUE || lastMode == 0) && widgetSize == size2) {
                return ConstraintLayout.USE_CONSTRAINTS_HELPER;
            }
            return false;
        }

        public final void didMeasures() {
            int widgetsCount = this.layout.getChildCount();
            for (int i = 0; i < widgetsCount; i++) {
                View child = this.layout.getChildAt(i);
                if (child instanceof Placeholder) {
                    ((Placeholder) child).updatePostMeasure(this.layout);
                }
            }
            int helperCount = this.layout.mConstraintHelpers.size();
            if (helperCount > 0) {
                for (int i2 = 0; i2 < helperCount; i2++) {
                    ((ConstraintHelper) this.layout.mConstraintHelpers.get(i2)).updatePostMeasure(this.layout);
                }
            }
        }
    }

    /* renamed from: androidx.constraintlayout.widget.ConstraintLayout$1 */
    static /* synthetic */ class C06561 {

        /* renamed from: $SwitchMap$androidx$constraintlayout$core$widgets$ConstraintWidget$DimensionBehaviour */
        static final /* synthetic */ int[] f1024x6d00e4a2;

        static {
            int[] iArr = new int[ConstraintWidget.DimensionBehaviour.values().length];
            f1024x6d00e4a2 = iArr;
            try {
                iArr[ConstraintWidget.DimensionBehaviour.FIXED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1024x6d00e4a2[ConstraintWidget.DimensionBehaviour.WRAP_CONTENT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1024x6d00e4a2[ConstraintWidget.DimensionBehaviour.MATCH_PARENT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1024x6d00e4a2[ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this.mLayoutWidget.setCompanionWidget(this);
        this.mLayoutWidget.setMeasurer(this.mMeasurer);
        this.mChildrenByIds.put(getId(), this);
        this.mConstraintSet = null;
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, C0657R.styleable.ConstraintLayout_Layout, defStyleAttr, defStyleRes);
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                if (attr == C0657R.styleable.ConstraintLayout_Layout_android_minWidth) {
                    this.mMinWidth = a.getDimensionPixelOffset(attr, this.mMinWidth);
                } else if (attr == C0657R.styleable.ConstraintLayout_Layout_android_minHeight) {
                    this.mMinHeight = a.getDimensionPixelOffset(attr, this.mMinHeight);
                } else if (attr == C0657R.styleable.ConstraintLayout_Layout_android_maxWidth) {
                    this.mMaxWidth = a.getDimensionPixelOffset(attr, this.mMaxWidth);
                } else if (attr == C0657R.styleable.ConstraintLayout_Layout_android_maxHeight) {
                    this.mMaxHeight = a.getDimensionPixelOffset(attr, this.mMaxHeight);
                } else if (attr == C0657R.styleable.ConstraintLayout_Layout_layout_optimizationLevel) {
                    this.mOptimizationLevel = a.getInt(attr, this.mOptimizationLevel);
                } else if (attr == C0657R.styleable.ConstraintLayout_Layout_layoutDescription) {
                    int id = a.getResourceId(attr, 0);
                    if (id != 0) {
                        try {
                            parseLayoutDescription(id);
                        } catch (Resources.NotFoundException e) {
                            this.mConstraintLayoutSpec = null;
                        }
                    }
                } else if (attr == C0657R.styleable.ConstraintLayout_Layout_constraintSet) {
                    int id2 = a.getResourceId(attr, 0);
                    try {
                        ConstraintSet constraintSet = new ConstraintSet();
                        this.mConstraintSet = constraintSet;
                        constraintSet.load(getContext(), id2);
                    } catch (Resources.NotFoundException e2) {
                        this.mConstraintSet = null;
                    }
                    this.mConstraintSetId = id2;
                }
            }
            a.recycle();
        }
        this.mLayoutWidget.setOptimizationLevel(this.mOptimizationLevel);
    }

    /* access modifiers changed from: protected */
    public void parseLayoutDescription(int id) {
        this.mConstraintLayoutSpec = new ConstraintLayoutStates(getContext(), this, id);
    }

    public void onViewAdded(View view) {
        super.onViewAdded(view);
        ConstraintWidget widget = getViewWidget(view);
        if ((view instanceof Guideline) && !(widget instanceof Guideline)) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.widget = new Guideline();
            layoutParams.isGuideline = USE_CONSTRAINTS_HELPER;
            ((Guideline) layoutParams.widget).setOrientation(layoutParams.orientation);
        }
        if (view instanceof ConstraintHelper) {
            ConstraintHelper helper = (ConstraintHelper) view;
            helper.validateParams();
            ((LayoutParams) view.getLayoutParams()).isHelper = USE_CONSTRAINTS_HELPER;
            if (!this.mConstraintHelpers.contains(helper)) {
                this.mConstraintHelpers.add(helper);
            }
        }
        this.mChildrenByIds.put(view.getId(), view);
        this.mDirtyHierarchy = USE_CONSTRAINTS_HELPER;
    }

    public void onViewRemoved(View view) {
        super.onViewRemoved(view);
        this.mChildrenByIds.remove(view.getId());
        this.mLayoutWidget.remove(getViewWidget(view));
        this.mConstraintHelpers.remove(view);
        this.mDirtyHierarchy = USE_CONSTRAINTS_HELPER;
    }

    public void setMinWidth(int value) {
        if (value != this.mMinWidth) {
            this.mMinWidth = value;
            requestLayout();
        }
    }

    public void setMinHeight(int value) {
        if (value != this.mMinHeight) {
            this.mMinHeight = value;
            requestLayout();
        }
    }

    public int getMinWidth() {
        return this.mMinWidth;
    }

    public int getMinHeight() {
        return this.mMinHeight;
    }

    public void setMaxWidth(int value) {
        if (value != this.mMaxWidth) {
            this.mMaxWidth = value;
            requestLayout();
        }
    }

    public void setMaxHeight(int value) {
        if (value != this.mMaxHeight) {
            this.mMaxHeight = value;
            requestLayout();
        }
    }

    public int getMaxWidth() {
        return this.mMaxWidth;
    }

    public int getMaxHeight() {
        return this.mMaxHeight;
    }

    private boolean updateHierarchy() {
        int count = getChildCount();
        boolean recompute = false;
        int i = 0;
        while (true) {
            if (i >= count) {
                break;
            } else if (getChildAt(i).isLayoutRequested()) {
                recompute = USE_CONSTRAINTS_HELPER;
                break;
            } else {
                i++;
            }
        }
        if (recompute) {
            setChildrenConstraints();
        }
        return recompute;
    }

    private void setChildrenConstraints() {
        boolean isInEditMode = isInEditMode();
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            ConstraintWidget widget = getViewWidget(getChildAt(i));
            if (widget != null) {
                widget.reset();
            }
        }
        if (isInEditMode) {
            for (int i2 = 0; i2 < count; i2++) {
                View view = getChildAt(i2);
                try {
                    String IdAsString = getResources().getResourceName(view.getId());
                    setDesignInformation(0, IdAsString, Integer.valueOf(view.getId()));
                    int slashIndex = IdAsString.indexOf(47);
                    if (slashIndex != -1) {
                        IdAsString = IdAsString.substring(slashIndex + 1);
                    }
                    getTargetWidget(view.getId()).setDebugName(IdAsString);
                } catch (Resources.NotFoundException e) {
                }
            }
        }
        if (this.mConstraintSetId != -1) {
            for (int i3 = 0; i3 < count; i3++) {
                View child = getChildAt(i3);
                if (child.getId() == this.mConstraintSetId && (child instanceof Constraints)) {
                    this.mConstraintSet = ((Constraints) child).getConstraintSet();
                }
            }
        }
        ConstraintSet constraintSet = this.mConstraintSet;
        if (constraintSet != null) {
            constraintSet.applyToInternal(this, USE_CONSTRAINTS_HELPER);
        }
        this.mLayoutWidget.removeAllChildren();
        int helperCount = this.mConstraintHelpers.size();
        if (helperCount > 0) {
            for (int i4 = 0; i4 < helperCount; i4++) {
                this.mConstraintHelpers.get(i4).updatePreLayout(this);
            }
        }
        for (int i5 = 0; i5 < count; i5++) {
            View child2 = getChildAt(i5);
            if (child2 instanceof Placeholder) {
                ((Placeholder) child2).updatePreLayout(this);
            }
        }
        this.mTempMapIdToWidget.clear();
        this.mTempMapIdToWidget.put(0, this.mLayoutWidget);
        this.mTempMapIdToWidget.put(getId(), this.mLayoutWidget);
        for (int i6 = 0; i6 < count; i6++) {
            View child3 = getChildAt(i6);
            this.mTempMapIdToWidget.put(child3.getId(), getViewWidget(child3));
        }
        for (int i7 = 0; i7 < count; i7++) {
            View child4 = getChildAt(i7);
            ConstraintWidget widget2 = getViewWidget(child4);
            if (widget2 != null) {
                this.mLayoutWidget.add(widget2);
                applyConstraintsFromLayoutParams(isInEditMode, child4, widget2, (LayoutParams) child4.getLayoutParams(), this.mTempMapIdToWidget);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void applyConstraintsFromLayoutParams(boolean isInEditMode, View child, ConstraintWidget widget, LayoutParams layoutParams, SparseArray<ConstraintWidget> idToWidget) {
        int resolvedRightToRight;
        int resolveGoneRightMargin;
        float resolvedHorizontalBias;
        ConstraintWidget target;
        ConstraintWidget target2;
        ConstraintWidget target3;
        ConstraintWidget target4;
        View view = child;
        ConstraintWidget constraintWidget = widget;
        LayoutParams layoutParams2 = layoutParams;
        SparseArray<ConstraintWidget> sparseArray = idToWidget;
        layoutParams.validate();
        layoutParams2.helped = false;
        constraintWidget.setVisibility(child.getVisibility());
        if (layoutParams2.isInPlaceholder) {
            constraintWidget.setInPlaceholder(USE_CONSTRAINTS_HELPER);
            constraintWidget.setVisibility(8);
        }
        constraintWidget.setCompanionWidget(view);
        if (view instanceof ConstraintHelper) {
            ((ConstraintHelper) view).resolveRtl(constraintWidget, this.mLayoutWidget.isRtl());
        }
        if (layoutParams2.isGuideline) {
            Guideline guideline = (Guideline) constraintWidget;
            int resolvedGuideBegin = layoutParams2.resolvedGuideBegin;
            int resolvedGuideEnd = layoutParams2.resolvedGuideEnd;
            float resolvedGuidePercent = layoutParams2.resolvedGuidePercent;
            if (resolvedGuidePercent != -1.0f) {
                guideline.setGuidePercent(resolvedGuidePercent);
            } else if (resolvedGuideBegin != -1) {
                guideline.setGuideBegin(resolvedGuideBegin);
            } else if (resolvedGuideEnd != -1) {
                guideline.setGuideEnd(resolvedGuideEnd);
            }
        } else {
            int resolvedLeftToLeft = layoutParams2.resolvedLeftToLeft;
            int resolvedLeftToRight = layoutParams2.resolvedLeftToRight;
            int resolvedRightToLeft = layoutParams2.resolvedRightToLeft;
            int resolvedRightToRight2 = layoutParams2.resolvedRightToRight;
            int resolveGoneLeftMargin = layoutParams2.resolveGoneLeftMargin;
            int resolveGoneRightMargin2 = layoutParams2.resolveGoneRightMargin;
            float resolvedHorizontalBias2 = layoutParams2.resolvedHorizontalBias;
            if (layoutParams2.circleConstraint != -1) {
                ConstraintWidget target5 = sparseArray.get(layoutParams2.circleConstraint);
                if (target5 != null) {
                    constraintWidget.connectCircularConstraint(target5, layoutParams2.circleAngle, layoutParams2.circleRadius);
                }
                float f = resolvedHorizontalBias2;
                int i = resolveGoneRightMargin2;
                int i2 = resolveGoneLeftMargin;
                int i3 = resolvedRightToRight2;
            } else {
                if (resolvedLeftToLeft != -1) {
                    ConstraintWidget target6 = sparseArray.get(resolvedLeftToLeft);
                    if (target6 != null) {
                        ConstraintAnchor.Type type = ConstraintAnchor.Type.LEFT;
                        ConstraintAnchor.Type type2 = ConstraintAnchor.Type.LEFT;
                        resolvedHorizontalBias = resolvedHorizontalBias2;
                        resolveGoneRightMargin = resolveGoneRightMargin2;
                        ConstraintAnchor.Type type3 = type2;
                        resolvedRightToRight = resolvedRightToRight2;
                        widget.immediateConnect(type, target6, type3, layoutParams2.leftMargin, resolveGoneLeftMargin);
                    } else {
                        resolvedHorizontalBias = resolvedHorizontalBias2;
                        resolveGoneRightMargin = resolveGoneRightMargin2;
                        int i4 = resolveGoneLeftMargin;
                        resolvedRightToRight = resolvedRightToRight2;
                    }
                } else {
                    resolvedHorizontalBias = resolvedHorizontalBias2;
                    resolveGoneRightMargin = resolveGoneRightMargin2;
                    int resolveGoneLeftMargin2 = resolveGoneLeftMargin;
                    resolvedRightToRight = resolvedRightToRight2;
                    if (!(resolvedLeftToRight == -1 || (target4 = sparseArray.get(resolvedLeftToRight)) == null)) {
                        widget.immediateConnect(ConstraintAnchor.Type.LEFT, target4, ConstraintAnchor.Type.RIGHT, layoutParams2.leftMargin, resolveGoneLeftMargin2);
                    }
                }
                if (resolvedRightToLeft != -1) {
                    ConstraintWidget target7 = sparseArray.get(resolvedRightToLeft);
                    if (target7 != null) {
                        widget.immediateConnect(ConstraintAnchor.Type.RIGHT, target7, ConstraintAnchor.Type.LEFT, layoutParams2.rightMargin, resolveGoneRightMargin);
                    }
                    int i5 = resolvedRightToRight;
                } else {
                    int resolvedRightToRight3 = resolvedRightToRight;
                    if (!(resolvedRightToRight3 == -1 || (target3 = sparseArray.get(resolvedRightToRight3)) == null)) {
                        widget.immediateConnect(ConstraintAnchor.Type.RIGHT, target3, ConstraintAnchor.Type.RIGHT, layoutParams2.rightMargin, resolveGoneRightMargin);
                    }
                }
                if (layoutParams2.topToTop != -1) {
                    ConstraintWidget target8 = sparseArray.get(layoutParams2.topToTop);
                    if (target8 != null) {
                        widget.immediateConnect(ConstraintAnchor.Type.TOP, target8, ConstraintAnchor.Type.TOP, layoutParams2.topMargin, layoutParams2.goneTopMargin);
                    }
                } else if (!(layoutParams2.topToBottom == -1 || (target2 = sparseArray.get(layoutParams2.topToBottom)) == null)) {
                    widget.immediateConnect(ConstraintAnchor.Type.TOP, target2, ConstraintAnchor.Type.BOTTOM, layoutParams2.topMargin, layoutParams2.goneTopMargin);
                }
                if (layoutParams2.bottomToTop != -1) {
                    ConstraintWidget target9 = sparseArray.get(layoutParams2.bottomToTop);
                    if (target9 != null) {
                        widget.immediateConnect(ConstraintAnchor.Type.BOTTOM, target9, ConstraintAnchor.Type.TOP, layoutParams2.bottomMargin, layoutParams2.goneBottomMargin);
                    }
                } else if (!(layoutParams2.bottomToBottom == -1 || (target = sparseArray.get(layoutParams2.bottomToBottom)) == null)) {
                    widget.immediateConnect(ConstraintAnchor.Type.BOTTOM, target, ConstraintAnchor.Type.BOTTOM, layoutParams2.bottomMargin, layoutParams2.goneBottomMargin);
                }
                if (layoutParams2.baselineToBaseline != -1) {
                    setWidgetBaseline(widget, layoutParams, idToWidget, layoutParams2.baselineToBaseline, ConstraintAnchor.Type.BASELINE);
                } else if (layoutParams2.baselineToTop != -1) {
                    setWidgetBaseline(widget, layoutParams, idToWidget, layoutParams2.baselineToTop, ConstraintAnchor.Type.TOP);
                } else if (layoutParams2.baselineToBottom != -1) {
                    setWidgetBaseline(widget, layoutParams, idToWidget, layoutParams2.baselineToBottom, ConstraintAnchor.Type.BOTTOM);
                }
                float resolvedHorizontalBias3 = resolvedHorizontalBias;
                if (resolvedHorizontalBias3 >= 0.0f) {
                    constraintWidget.setHorizontalBiasPercent(resolvedHorizontalBias3);
                }
                if (layoutParams2.verticalBias >= 0.0f) {
                    constraintWidget.setVerticalBiasPercent(layoutParams2.verticalBias);
                }
            }
            if (isInEditMode && !(layoutParams2.editorAbsoluteX == -1 && layoutParams2.editorAbsoluteY == -1)) {
                constraintWidget.setOrigin(layoutParams2.editorAbsoluteX, layoutParams2.editorAbsoluteY);
            }
            if (layoutParams2.horizontalDimensionFixed) {
                constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                constraintWidget.setWidth(layoutParams2.width);
                if (layoutParams2.width == -2) {
                    constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
                }
            } else if (layoutParams2.width == -1) {
                if (layoutParams2.constrainedWidth) {
                    constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
                } else {
                    constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
                }
                constraintWidget.getAnchor(ConstraintAnchor.Type.LEFT).mMargin = layoutParams2.leftMargin;
                constraintWidget.getAnchor(ConstraintAnchor.Type.RIGHT).mMargin = layoutParams2.rightMargin;
            } else {
                constraintWidget.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
                constraintWidget.setWidth(0);
            }
            if (layoutParams2.verticalDimensionFixed) {
                constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                constraintWidget.setHeight(layoutParams2.height);
                if (layoutParams2.height == -2) {
                    constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.WRAP_CONTENT);
                }
            } else if (layoutParams2.height == -1) {
                if (layoutParams2.constrainedHeight) {
                    constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
                } else {
                    constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_PARENT);
                }
                constraintWidget.getAnchor(ConstraintAnchor.Type.TOP).mMargin = layoutParams2.topMargin;
                constraintWidget.getAnchor(ConstraintAnchor.Type.BOTTOM).mMargin = layoutParams2.bottomMargin;
            } else {
                constraintWidget.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT);
                constraintWidget.setHeight(0);
            }
            constraintWidget.setDimensionRatio(layoutParams2.dimensionRatio);
            constraintWidget.setHorizontalWeight(layoutParams2.horizontalWeight);
            constraintWidget.setVerticalWeight(layoutParams2.verticalWeight);
            constraintWidget.setHorizontalChainStyle(layoutParams2.horizontalChainStyle);
            constraintWidget.setVerticalChainStyle(layoutParams2.verticalChainStyle);
            constraintWidget.setWrapBehaviorInParent(layoutParams2.wrapBehaviorInParent);
            constraintWidget.setHorizontalMatchStyle(layoutParams2.matchConstraintDefaultWidth, layoutParams2.matchConstraintMinWidth, layoutParams2.matchConstraintMaxWidth, layoutParams2.matchConstraintPercentWidth);
            constraintWidget.setVerticalMatchStyle(layoutParams2.matchConstraintDefaultHeight, layoutParams2.matchConstraintMinHeight, layoutParams2.matchConstraintMaxHeight, layoutParams2.matchConstraintPercentHeight);
        }
    }

    private void setWidgetBaseline(ConstraintWidget widget, LayoutParams layoutParams, SparseArray<ConstraintWidget> idToWidget, int baselineTarget, ConstraintAnchor.Type type) {
        View view = this.mChildrenByIds.get(baselineTarget);
        ConstraintWidget target = idToWidget.get(baselineTarget);
        if (target != null && view != null && (view.getLayoutParams() instanceof LayoutParams)) {
            layoutParams.needsBaseline = USE_CONSTRAINTS_HELPER;
            if (type == ConstraintAnchor.Type.BASELINE) {
                LayoutParams targetParams = (LayoutParams) view.getLayoutParams();
                targetParams.needsBaseline = USE_CONSTRAINTS_HELPER;
                targetParams.widget.setHasBaseline(USE_CONSTRAINTS_HELPER);
            }
            widget.getAnchor(ConstraintAnchor.Type.BASELINE).connect(target.getAnchor(type), layoutParams.baselineMargin, layoutParams.goneBaselineMargin, USE_CONSTRAINTS_HELPER);
            widget.setHasBaseline(USE_CONSTRAINTS_HELPER);
            widget.getAnchor(ConstraintAnchor.Type.TOP).reset();
            widget.getAnchor(ConstraintAnchor.Type.BOTTOM).reset();
        }
    }

    private final ConstraintWidget getTargetWidget(int id) {
        if (id == 0) {
            return this.mLayoutWidget;
        }
        View view = this.mChildrenByIds.get(id);
        if (view == null && (view = findViewById(id)) != null && view != this && view.getParent() == this) {
            onViewAdded(view);
        }
        if (view == this) {
            return this.mLayoutWidget;
        }
        if (view == null) {
            return null;
        }
        return ((LayoutParams) view.getLayoutParams()).widget;
    }

    public final ConstraintWidget getViewWidget(View view) {
        if (view == this) {
            return this.mLayoutWidget;
        }
        if (view == null) {
            return null;
        }
        if (view.getLayoutParams() instanceof LayoutParams) {
            return ((LayoutParams) view.getLayoutParams()).widget;
        }
        view.setLayoutParams(generateLayoutParams(view.getLayoutParams()));
        if (view.getLayoutParams() instanceof LayoutParams) {
            return ((LayoutParams) view.getLayoutParams()).widget;
        }
        return null;
    }

    public void fillMetrics(Metrics metrics) {
        this.mMetrics = metrics;
        this.mLayoutWidget.fillMetrics(metrics);
    }

    /* access modifiers changed from: protected */
    public void resolveSystem(ConstraintWidgetContainer layout, int optimizationLevel, int widthMeasureSpec, int heightMeasureSpec) {
        int paddingX;
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = View.MeasureSpec.getSize(heightMeasureSpec);
        int paddingY = Math.max(0, getPaddingTop());
        int paddingBottom = Math.max(0, getPaddingBottom());
        int paddingHeight = paddingY + paddingBottom;
        int paddingWidth = getPaddingWidth();
        this.mMeasurer.captureLayoutInfo(widthMeasureSpec, heightMeasureSpec, paddingY, paddingBottom, paddingWidth, paddingHeight);
        int paddingStart = Math.max(0, getPaddingStart());
        int paddingEnd = Math.max(0, getPaddingEnd());
        if (paddingStart <= 0 && paddingEnd <= 0) {
            paddingX = Math.max(0, getPaddingLeft());
        } else if (isRtl() != 0) {
            paddingX = paddingEnd;
        } else {
            paddingX = paddingStart;
        }
        int widthSize2 = widthSize - paddingWidth;
        int heightSize2 = heightSize - paddingHeight;
        setSelfDimensionBehaviour(layout, widthMode, widthSize2, heightMode, heightSize2);
        layout.measure(optimizationLevel, widthMode, widthSize2, heightMode, heightSize2, this.mLastMeasureWidth, this.mLastMeasureHeight, paddingX, paddingY);
    }

    /* access modifiers changed from: protected */
    public void resolveMeasuredDimension(int widthMeasureSpec, int heightMeasureSpec, int measuredWidth, int measuredHeight, boolean isWidthMeasuredTooSmall, boolean isHeightMeasuredTooSmall) {
        int heightPadding = this.mMeasurer.paddingHeight;
        int resolvedWidthSize = resolveSizeAndState(measuredWidth + this.mMeasurer.paddingWidth, widthMeasureSpec, 0);
        int resolvedHeightSize = resolveSizeAndState(measuredHeight + heightPadding, heightMeasureSpec, 0 << 16);
        int resolvedWidthSize2 = resolvedWidthSize & ViewCompat.MEASURED_SIZE_MASK;
        int resolvedHeightSize2 = resolvedHeightSize & ViewCompat.MEASURED_SIZE_MASK;
        int resolvedWidthSize3 = Math.min(this.mMaxWidth, resolvedWidthSize2);
        int resolvedHeightSize3 = Math.min(this.mMaxHeight, resolvedHeightSize2);
        if (isWidthMeasuredTooSmall) {
            resolvedWidthSize3 |= 16777216;
        }
        if (isHeightMeasuredTooSmall) {
            resolvedHeightSize3 |= 16777216;
        }
        setMeasuredDimension(resolvedWidthSize3, resolvedHeightSize3);
        this.mLastMeasureWidth = resolvedWidthSize3;
        this.mLastMeasureHeight = resolvedHeightSize3;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mOnMeasureWidthMeasureSpec == widthMeasureSpec && this.mOnMeasureHeightMeasureSpec == heightMeasureSpec) {
        }
        if (!this.mDirtyHierarchy && 0 == 0) {
            int count = getChildCount();
            int i = 0;
            while (true) {
                if (i >= count) {
                    break;
                } else if (getChildAt(i).isLayoutRequested()) {
                    this.mDirtyHierarchy = USE_CONSTRAINTS_HELPER;
                    break;
                } else {
                    i++;
                }
            }
        }
        if (this.mDirtyHierarchy || 0 == 0) {
            this.mOnMeasureWidthMeasureSpec = widthMeasureSpec;
            this.mOnMeasureHeightMeasureSpec = heightMeasureSpec;
            this.mLayoutWidget.setRtl(isRtl());
            if (this.mDirtyHierarchy) {
                this.mDirtyHierarchy = false;
                if (updateHierarchy()) {
                    this.mLayoutWidget.updateHierarchy();
                }
            }
            resolveSystem(this.mLayoutWidget, this.mOptimizationLevel, widthMeasureSpec, heightMeasureSpec);
            resolveMeasuredDimension(widthMeasureSpec, heightMeasureSpec, this.mLayoutWidget.getWidth(), this.mLayoutWidget.getHeight(), this.mLayoutWidget.isWidthMeasuredTooSmall(), this.mLayoutWidget.isHeightMeasuredTooSmall());
            return;
        }
        resolveMeasuredDimension(widthMeasureSpec, heightMeasureSpec, this.mLayoutWidget.getWidth(), this.mLayoutWidget.getHeight(), this.mLayoutWidget.isWidthMeasuredTooSmall(), this.mLayoutWidget.isHeightMeasuredTooSmall());
    }

    /* access modifiers changed from: protected */
    public boolean isRtl() {
        if (!((getContext().getApplicationInfo().flags & 4194304) != 0 ? USE_CONSTRAINTS_HELPER : false) || 1 != getLayoutDirection()) {
            return false;
        }
        return USE_CONSTRAINTS_HELPER;
    }

    private int getPaddingWidth() {
        int widthPadding = Math.max(0, getPaddingLeft()) + Math.max(0, getPaddingRight());
        int rtlPadding = Math.max(0, getPaddingStart()) + Math.max(0, getPaddingEnd());
        if (rtlPadding > 0) {
            return rtlPadding;
        }
        return widthPadding;
    }

    /* access modifiers changed from: protected */
    public void setSelfDimensionBehaviour(ConstraintWidgetContainer layout, int widthMode, int widthSize, int heightMode, int heightSize) {
        int heightPadding = this.mMeasurer.paddingHeight;
        int widthPadding = this.mMeasurer.paddingWidth;
        ConstraintWidget.DimensionBehaviour widthBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
        ConstraintWidget.DimensionBehaviour heightBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
        int desiredWidth = 0;
        int desiredHeight = 0;
        int childCount = getChildCount();
        switch (widthMode) {
            case Integer.MIN_VALUE:
                widthBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                desiredWidth = widthSize;
                if (childCount == 0) {
                    desiredWidth = Math.max(0, this.mMinWidth);
                    break;
                }
                break;
            case 0:
                widthBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                if (childCount == 0) {
                    desiredWidth = Math.max(0, this.mMinWidth);
                    break;
                }
                break;
            case 1073741824:
                desiredWidth = Math.min(this.mMaxWidth - widthPadding, widthSize);
                break;
        }
        switch (heightMode) {
            case Integer.MIN_VALUE:
                heightBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                desiredHeight = heightSize;
                if (childCount == 0) {
                    desiredHeight = Math.max(0, this.mMinHeight);
                    break;
                }
                break;
            case 0:
                heightBehaviour = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
                if (childCount == 0) {
                    desiredHeight = Math.max(0, this.mMinHeight);
                    break;
                }
                break;
            case 1073741824:
                desiredHeight = Math.min(this.mMaxHeight - heightPadding, heightSize);
                break;
        }
        if (!(desiredWidth == layout.getWidth() && desiredHeight == layout.getHeight())) {
            layout.invalidateMeasures();
        }
        layout.setX(0);
        layout.setY(0);
        layout.setMaxWidth(this.mMaxWidth - widthPadding);
        layout.setMaxHeight(this.mMaxHeight - heightPadding);
        layout.setMinWidth(0);
        layout.setMinHeight(0);
        layout.setHorizontalDimensionBehaviour(widthBehaviour);
        layout.setWidth(desiredWidth);
        layout.setVerticalDimensionBehaviour(heightBehaviour);
        layout.setHeight(desiredHeight);
        layout.setMinWidth(this.mMinWidth - widthPadding);
        layout.setMinHeight(this.mMinHeight - heightPadding);
    }

    public void setState(int id, int screenWidth, int screenHeight) {
        ConstraintLayoutStates constraintLayoutStates = this.mConstraintLayoutSpec;
        if (constraintLayoutStates != null) {
            constraintLayoutStates.updateConstraints(id, (float) screenWidth, (float) screenHeight);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
        View content;
        int widgetsCount = getChildCount();
        boolean isInEditMode = isInEditMode();
        for (int i = 0; i < widgetsCount; i++) {
            View child = getChildAt(i);
            LayoutParams params = (LayoutParams) child.getLayoutParams();
            ConstraintWidget widget = params.widget;
            if ((child.getVisibility() != 8 || params.isGuideline || params.isHelper || params.isVirtualGroup || isInEditMode) && !params.isInPlaceholder) {
                int l = widget.getX();
                int t = widget.getY();
                int r = widget.getWidth() + l;
                int b = widget.getHeight() + t;
                child.layout(l, t, r, b);
                if ((child instanceof Placeholder) && (content = ((Placeholder) child).getContent()) != null) {
                    content.setVisibility(0);
                    content.layout(l, t, r, b);
                }
            }
        }
        int helperCount = this.mConstraintHelpers.size();
        if (helperCount > 0) {
            for (int i2 = 0; i2 < helperCount; i2++) {
                this.mConstraintHelpers.get(i2).updatePostLayout(this);
            }
        }
    }

    public void setOptimizationLevel(int level) {
        this.mOptimizationLevel = level;
        this.mLayoutWidget.setOptimizationLevel(level);
    }

    public int getOptimizationLevel() {
        return this.mLayoutWidget.getOptimizationLevel();
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    /* access modifiers changed from: protected */
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public void setConstraintSet(ConstraintSet set) {
        this.mConstraintSet = set;
    }

    public View getViewById(int id) {
        return this.mChildrenByIds.get(id);
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        float ow;
        float ch;
        float cw;
        int helperCount;
        ConstraintLayout constraintLayout = this;
        ArrayList<ConstraintHelper> arrayList = constraintLayout.mConstraintHelpers;
        if (arrayList != null && (helperCount = arrayList.size()) > 0) {
            for (int i = 0; i < helperCount; i++) {
                constraintLayout.mConstraintHelpers.get(i).updatePreDraw(constraintLayout);
            }
        }
        super.dispatchDraw(canvas);
        if (isInEditMode()) {
            float cw2 = (float) getWidth();
            float ch2 = (float) getHeight();
            float ow2 = 1080.0f;
            int count = getChildCount();
            int i2 = 0;
            while (i2 < count) {
                View child = constraintLayout.getChildAt(i2);
                if (child.getVisibility() == 8) {
                    cw = cw2;
                    ch = ch2;
                    ow = ow2;
                } else {
                    Object tag = child.getTag();
                    if (tag == null || !(tag instanceof String)) {
                        cw = cw2;
                        ch = ch2;
                        ow = ow2;
                    } else {
                        String[] split = ((String) tag).split(",");
                        if (split.length == 4) {
                            int x = Integer.parseInt(split[0]);
                            int y = Integer.parseInt(split[1]);
                            int x2 = (int) ((((float) x) / ow2) * cw2);
                            int y2 = (int) ((((float) y) / 1920.0f) * ch2);
                            int w = (int) ((((float) Integer.parseInt(split[2])) / ow2) * cw2);
                            int h = (int) ((((float) Integer.parseInt(split[3])) / 1920.0f) * ch2);
                            Paint paint = new Paint();
                            paint.setColor(SupportMenu.CATEGORY_MASK);
                            cw = cw2;
                            ch = ch2;
                            ow = ow2;
                            Canvas canvas2 = canvas;
                            Paint paint2 = paint;
                            canvas2.drawLine((float) x2, (float) y2, (float) (x2 + w), (float) y2, paint2);
                            canvas2.drawLine((float) (x2 + w), (float) y2, (float) (x2 + w), (float) (y2 + h), paint2);
                            canvas2.drawLine((float) (x2 + w), (float) (y2 + h), (float) x2, (float) (y2 + h), paint2);
                            canvas2.drawLine((float) x2, (float) (y2 + h), (float) x2, (float) y2, paint2);
                            paint.setColor(-16711936);
                            canvas2.drawLine((float) x2, (float) y2, (float) (x2 + w), (float) (y2 + h), paint2);
                            canvas2.drawLine((float) x2, (float) (y2 + h), (float) (x2 + w), (float) y2, paint2);
                        } else {
                            cw = cw2;
                            ch = ch2;
                            ow = ow2;
                        }
                    }
                }
                i2++;
                constraintLayout = this;
                cw2 = cw;
                ch2 = ch;
                ow2 = ow;
            }
            float f = ch2;
            float f2 = ow2;
        }
    }

    public void setOnConstraintsChanged(ConstraintsChangedListener constraintsChangedListener) {
        this.mConstraintsChangedListener = constraintsChangedListener;
        ConstraintLayoutStates constraintLayoutStates = this.mConstraintLayoutSpec;
        if (constraintLayoutStates != null) {
            constraintLayoutStates.setOnConstraintsChanged(constraintsChangedListener);
        }
    }

    public void loadLayoutDescription(int layoutDescription) {
        if (layoutDescription != 0) {
            try {
                this.mConstraintLayoutSpec = new ConstraintLayoutStates(getContext(), this, layoutDescription);
            } catch (Resources.NotFoundException e) {
                this.mConstraintLayoutSpec = null;
            }
        } else {
            this.mConstraintLayoutSpec = null;
        }
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public static final int BASELINE = 5;
        public static final int BOTTOM = 4;
        public static final int CHAIN_PACKED = 2;
        public static final int CHAIN_SPREAD = 0;
        public static final int CHAIN_SPREAD_INSIDE = 1;
        public static final int CIRCLE = 8;
        public static final int END = 7;
        public static final int GONE_UNSET = Integer.MIN_VALUE;
        public static final int HORIZONTAL = 0;
        public static final int LEFT = 1;
        public static final int MATCH_CONSTRAINT = 0;
        public static final int MATCH_CONSTRAINT_PERCENT = 2;
        public static final int MATCH_CONSTRAINT_SPREAD = 0;
        public static final int MATCH_CONSTRAINT_WRAP = 1;
        public static final int PARENT_ID = 0;
        public static final int RIGHT = 2;
        public static final int START = 6;
        public static final int TOP = 3;
        public static final int UNSET = -1;
        public static final int VERTICAL = 1;
        public static final int WRAP_BEHAVIOR_HORIZONTAL_ONLY = 1;
        public static final int WRAP_BEHAVIOR_INCLUDED = 0;
        public static final int WRAP_BEHAVIOR_SKIPPED = 3;
        public static final int WRAP_BEHAVIOR_VERTICAL_ONLY = 2;
        public int baselineMargin = 0;
        public int baselineToBaseline = -1;
        public int baselineToBottom = -1;
        public int baselineToTop = -1;
        public int bottomToBottom = -1;
        public int bottomToTop = -1;
        public float circleAngle = 0.0f;
        public int circleConstraint = -1;
        public int circleRadius = 0;
        public boolean constrainedHeight = false;
        public boolean constrainedWidth = false;
        public String constraintTag = null;
        public String dimensionRatio = null;
        int dimensionRatioSide = 1;
        float dimensionRatioValue = 0.0f;
        public int editorAbsoluteX = -1;
        public int editorAbsoluteY = -1;
        public int endToEnd = -1;
        public int endToStart = -1;
        public int goneBaselineMargin = Integer.MIN_VALUE;
        public int goneBottomMargin = Integer.MIN_VALUE;
        public int goneEndMargin = Integer.MIN_VALUE;
        public int goneLeftMargin = Integer.MIN_VALUE;
        public int goneRightMargin = Integer.MIN_VALUE;
        public int goneStartMargin = Integer.MIN_VALUE;
        public int goneTopMargin = Integer.MIN_VALUE;
        public int guideBegin = -1;
        public int guideEnd = -1;
        public float guidePercent = -1.0f;
        public boolean guidelineUseRtl = ConstraintLayout.USE_CONSTRAINTS_HELPER;
        boolean heightSet = ConstraintLayout.USE_CONSTRAINTS_HELPER;
        public boolean helped = false;
        public float horizontalBias = 0.5f;
        public int horizontalChainStyle = 0;
        boolean horizontalDimensionFixed = ConstraintLayout.USE_CONSTRAINTS_HELPER;
        public float horizontalWeight = -1.0f;
        boolean isGuideline = false;
        boolean isHelper = false;
        boolean isInPlaceholder = false;
        boolean isVirtualGroup = false;
        public int leftToLeft = -1;
        public int leftToRight = -1;
        public int matchConstraintDefaultHeight = 0;
        public int matchConstraintDefaultWidth = 0;
        public int matchConstraintMaxHeight = 0;
        public int matchConstraintMaxWidth = 0;
        public int matchConstraintMinHeight = 0;
        public int matchConstraintMinWidth = 0;
        public float matchConstraintPercentHeight = 1.0f;
        public float matchConstraintPercentWidth = 1.0f;
        boolean needsBaseline = false;
        public int orientation = -1;
        int resolveGoneLeftMargin = Integer.MIN_VALUE;
        int resolveGoneRightMargin = Integer.MIN_VALUE;
        int resolvedGuideBegin;
        int resolvedGuideEnd;
        float resolvedGuidePercent;
        float resolvedHorizontalBias = 0.5f;
        int resolvedLeftToLeft = -1;
        int resolvedLeftToRight = -1;
        int resolvedRightToLeft = -1;
        int resolvedRightToRight = -1;
        public int rightToLeft = -1;
        public int rightToRight = -1;
        public int startToEnd = -1;
        public int startToStart = -1;
        public int topToBottom = -1;
        public int topToTop = -1;
        public float verticalBias = 0.5f;
        public int verticalChainStyle = 0;
        boolean verticalDimensionFixed = ConstraintLayout.USE_CONSTRAINTS_HELPER;
        public float verticalWeight = -1.0f;
        ConstraintWidget widget = new ConstraintWidget();
        boolean widthSet = ConstraintLayout.USE_CONSTRAINTS_HELPER;
        public int wrapBehaviorInParent = 0;

        public ConstraintWidget getConstraintWidget() {
            return this.widget;
        }

        public void setWidgetDebugName(String text) {
            this.widget.setDebugName(text);
        }

        public void reset() {
            ConstraintWidget constraintWidget = this.widget;
            if (constraintWidget != null) {
                constraintWidget.reset();
            }
        }

        public LayoutParams(LayoutParams source) {
            super(source);
            this.guideBegin = source.guideBegin;
            this.guideEnd = source.guideEnd;
            this.guidePercent = source.guidePercent;
            this.guidelineUseRtl = source.guidelineUseRtl;
            this.leftToLeft = source.leftToLeft;
            this.leftToRight = source.leftToRight;
            this.rightToLeft = source.rightToLeft;
            this.rightToRight = source.rightToRight;
            this.topToTop = source.topToTop;
            this.topToBottom = source.topToBottom;
            this.bottomToTop = source.bottomToTop;
            this.bottomToBottom = source.bottomToBottom;
            this.baselineToBaseline = source.baselineToBaseline;
            this.baselineToTop = source.baselineToTop;
            this.baselineToBottom = source.baselineToBottom;
            this.circleConstraint = source.circleConstraint;
            this.circleRadius = source.circleRadius;
            this.circleAngle = source.circleAngle;
            this.startToEnd = source.startToEnd;
            this.startToStart = source.startToStart;
            this.endToStart = source.endToStart;
            this.endToEnd = source.endToEnd;
            this.goneLeftMargin = source.goneLeftMargin;
            this.goneTopMargin = source.goneTopMargin;
            this.goneRightMargin = source.goneRightMargin;
            this.goneBottomMargin = source.goneBottomMargin;
            this.goneStartMargin = source.goneStartMargin;
            this.goneEndMargin = source.goneEndMargin;
            this.goneBaselineMargin = source.goneBaselineMargin;
            this.baselineMargin = source.baselineMargin;
            this.horizontalBias = source.horizontalBias;
            this.verticalBias = source.verticalBias;
            this.dimensionRatio = source.dimensionRatio;
            this.dimensionRatioValue = source.dimensionRatioValue;
            this.dimensionRatioSide = source.dimensionRatioSide;
            this.horizontalWeight = source.horizontalWeight;
            this.verticalWeight = source.verticalWeight;
            this.horizontalChainStyle = source.horizontalChainStyle;
            this.verticalChainStyle = source.verticalChainStyle;
            this.constrainedWidth = source.constrainedWidth;
            this.constrainedHeight = source.constrainedHeight;
            this.matchConstraintDefaultWidth = source.matchConstraintDefaultWidth;
            this.matchConstraintDefaultHeight = source.matchConstraintDefaultHeight;
            this.matchConstraintMinWidth = source.matchConstraintMinWidth;
            this.matchConstraintMaxWidth = source.matchConstraintMaxWidth;
            this.matchConstraintMinHeight = source.matchConstraintMinHeight;
            this.matchConstraintMaxHeight = source.matchConstraintMaxHeight;
            this.matchConstraintPercentWidth = source.matchConstraintPercentWidth;
            this.matchConstraintPercentHeight = source.matchConstraintPercentHeight;
            this.editorAbsoluteX = source.editorAbsoluteX;
            this.editorAbsoluteY = source.editorAbsoluteY;
            this.orientation = source.orientation;
            this.horizontalDimensionFixed = source.horizontalDimensionFixed;
            this.verticalDimensionFixed = source.verticalDimensionFixed;
            this.needsBaseline = source.needsBaseline;
            this.isGuideline = source.isGuideline;
            this.resolvedLeftToLeft = source.resolvedLeftToLeft;
            this.resolvedLeftToRight = source.resolvedLeftToRight;
            this.resolvedRightToLeft = source.resolvedRightToLeft;
            this.resolvedRightToRight = source.resolvedRightToRight;
            this.resolveGoneLeftMargin = source.resolveGoneLeftMargin;
            this.resolveGoneRightMargin = source.resolveGoneRightMargin;
            this.resolvedHorizontalBias = source.resolvedHorizontalBias;
            this.constraintTag = source.constraintTag;
            this.wrapBehaviorInParent = source.wrapBehaviorInParent;
            this.widget = source.widget;
            this.widthSet = source.widthSet;
            this.heightSet = source.heightSet;
        }

        private static class Table {
            public static final int ANDROID_ORIENTATION = 1;
            public static final int GUIDELINE_USE_RTL = 67;
            public static final int LAYOUT_CONSTRAINED_HEIGHT = 28;
            public static final int LAYOUT_CONSTRAINED_WIDTH = 27;
            public static final int LAYOUT_CONSTRAINT_BASELINE_CREATOR = 43;
            public static final int LAYOUT_CONSTRAINT_BASELINE_TO_BASELINE_OF = 16;
            public static final int LAYOUT_CONSTRAINT_BASELINE_TO_BOTTOM_OF = 53;
            public static final int LAYOUT_CONSTRAINT_BASELINE_TO_TOP_OF = 52;
            public static final int LAYOUT_CONSTRAINT_BOTTOM_CREATOR = 42;
            public static final int LAYOUT_CONSTRAINT_BOTTOM_TO_BOTTOM_OF = 15;
            public static final int LAYOUT_CONSTRAINT_BOTTOM_TO_TOP_OF = 14;
            public static final int LAYOUT_CONSTRAINT_CIRCLE = 2;
            public static final int LAYOUT_CONSTRAINT_CIRCLE_ANGLE = 4;
            public static final int LAYOUT_CONSTRAINT_CIRCLE_RADIUS = 3;
            public static final int LAYOUT_CONSTRAINT_DIMENSION_RATIO = 44;
            public static final int LAYOUT_CONSTRAINT_END_TO_END_OF = 20;
            public static final int LAYOUT_CONSTRAINT_END_TO_START_OF = 19;
            public static final int LAYOUT_CONSTRAINT_GUIDE_BEGIN = 5;
            public static final int LAYOUT_CONSTRAINT_GUIDE_END = 6;
            public static final int LAYOUT_CONSTRAINT_GUIDE_PERCENT = 7;
            public static final int LAYOUT_CONSTRAINT_HEIGHT = 65;
            public static final int LAYOUT_CONSTRAINT_HEIGHT_DEFAULT = 32;
            public static final int LAYOUT_CONSTRAINT_HEIGHT_MAX = 37;
            public static final int LAYOUT_CONSTRAINT_HEIGHT_MIN = 36;
            public static final int LAYOUT_CONSTRAINT_HEIGHT_PERCENT = 38;
            public static final int LAYOUT_CONSTRAINT_HORIZONTAL_BIAS = 29;
            public static final int LAYOUT_CONSTRAINT_HORIZONTAL_CHAINSTYLE = 47;
            public static final int LAYOUT_CONSTRAINT_HORIZONTAL_WEIGHT = 45;
            public static final int LAYOUT_CONSTRAINT_LEFT_CREATOR = 39;
            public static final int LAYOUT_CONSTRAINT_LEFT_TO_LEFT_OF = 8;
            public static final int LAYOUT_CONSTRAINT_LEFT_TO_RIGHT_OF = 9;
            public static final int LAYOUT_CONSTRAINT_RIGHT_CREATOR = 41;
            public static final int LAYOUT_CONSTRAINT_RIGHT_TO_LEFT_OF = 10;
            public static final int LAYOUT_CONSTRAINT_RIGHT_TO_RIGHT_OF = 11;
            public static final int LAYOUT_CONSTRAINT_START_TO_END_OF = 17;
            public static final int LAYOUT_CONSTRAINT_START_TO_START_OF = 18;
            public static final int LAYOUT_CONSTRAINT_TAG = 51;
            public static final int LAYOUT_CONSTRAINT_TOP_CREATOR = 40;
            public static final int LAYOUT_CONSTRAINT_TOP_TO_BOTTOM_OF = 13;
            public static final int LAYOUT_CONSTRAINT_TOP_TO_TOP_OF = 12;
            public static final int LAYOUT_CONSTRAINT_VERTICAL_BIAS = 30;
            public static final int LAYOUT_CONSTRAINT_VERTICAL_CHAINSTYLE = 48;
            public static final int LAYOUT_CONSTRAINT_VERTICAL_WEIGHT = 46;
            public static final int LAYOUT_CONSTRAINT_WIDTH = 64;
            public static final int LAYOUT_CONSTRAINT_WIDTH_DEFAULT = 31;
            public static final int LAYOUT_CONSTRAINT_WIDTH_MAX = 34;
            public static final int LAYOUT_CONSTRAINT_WIDTH_MIN = 33;
            public static final int LAYOUT_CONSTRAINT_WIDTH_PERCENT = 35;
            public static final int LAYOUT_EDITOR_ABSOLUTEX = 49;
            public static final int LAYOUT_EDITOR_ABSOLUTEY = 50;
            public static final int LAYOUT_GONE_MARGIN_BASELINE = 55;
            public static final int LAYOUT_GONE_MARGIN_BOTTOM = 24;
            public static final int LAYOUT_GONE_MARGIN_END = 26;
            public static final int LAYOUT_GONE_MARGIN_LEFT = 21;
            public static final int LAYOUT_GONE_MARGIN_RIGHT = 23;
            public static final int LAYOUT_GONE_MARGIN_START = 25;
            public static final int LAYOUT_GONE_MARGIN_TOP = 22;
            public static final int LAYOUT_MARGIN_BASELINE = 54;
            public static final int LAYOUT_WRAP_BEHAVIOR_IN_PARENT = 66;
            public static final int UNUSED = 0;
            public static final SparseIntArray map;

            private Table() {
            }

            static {
                SparseIntArray sparseIntArray = new SparseIntArray();
                map = sparseIntArray;
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintWidth, 64);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintHeight, 65);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintLeft_toLeftOf, 8);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintLeft_toRightOf, 9);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintRight_toLeftOf, 10);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintRight_toRightOf, 11);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintTop_toTopOf, 12);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintTop_toBottomOf, 13);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintBottom_toTopOf, 14);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintBottom_toBottomOf, 15);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_toBaselineOf, 16);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_toTopOf, 52);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_toBottomOf, 53);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintCircle, 2);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintCircleRadius, 3);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintCircleAngle, 4);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_editor_absoluteX, 49);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_editor_absoluteY, 50);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintGuide_begin, 5);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintGuide_end, 6);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintGuide_percent, 7);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_guidelineUseRtl, 67);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_android_orientation, 1);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintStart_toEndOf, 17);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintStart_toStartOf, 18);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintEnd_toStartOf, 19);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintEnd_toEndOf, 20);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_goneMarginLeft, 21);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_goneMarginTop, 22);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_goneMarginRight, 23);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_goneMarginBottom, 24);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_goneMarginStart, 25);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_goneMarginEnd, 26);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_goneMarginBaseline, 55);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_marginBaseline, 54);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_bias, 29);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintVertical_bias, 30);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintDimensionRatio, 44);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_weight, 45);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintVertical_weight, 46);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_chainStyle, 47);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintVertical_chainStyle, 48);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constrainedWidth, 27);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constrainedHeight, 28);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintWidth_default, 31);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintHeight_default, 32);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintWidth_min, 33);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintWidth_max, 34);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintWidth_percent, 35);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintHeight_min, 36);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintHeight_max, 37);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintHeight_percent, 38);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintLeft_creator, 39);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintTop_creator, 40);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintRight_creator, 41);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintBottom_creator, 42);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_creator, 43);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_constraintTag, 51);
                sparseIntArray.append(C0657R.styleable.ConstraintLayout_Layout_layout_wrapBehaviorInParent, 66);
            }
        }

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray a = c.obtainStyledAttributes(attrs, C0657R.styleable.ConstraintLayout_Layout);
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                switch (Table.map.get(attr)) {
                    case 1:
                        this.orientation = a.getInt(attr, this.orientation);
                        break;
                    case 2:
                        int resourceId = a.getResourceId(attr, this.circleConstraint);
                        this.circleConstraint = resourceId;
                        if (resourceId != -1) {
                            break;
                        } else {
                            this.circleConstraint = a.getInt(attr, -1);
                            break;
                        }
                    case 3:
                        this.circleRadius = a.getDimensionPixelSize(attr, this.circleRadius);
                        break;
                    case 4:
                        float f = a.getFloat(attr, this.circleAngle) % 360.0f;
                        this.circleAngle = f;
                        if (f >= 0.0f) {
                            break;
                        } else {
                            this.circleAngle = (360.0f - f) % 360.0f;
                            break;
                        }
                    case 5:
                        this.guideBegin = a.getDimensionPixelOffset(attr, this.guideBegin);
                        break;
                    case 6:
                        this.guideEnd = a.getDimensionPixelOffset(attr, this.guideEnd);
                        break;
                    case 7:
                        this.guidePercent = a.getFloat(attr, this.guidePercent);
                        break;
                    case 8:
                        int resourceId2 = a.getResourceId(attr, this.leftToLeft);
                        this.leftToLeft = resourceId2;
                        if (resourceId2 != -1) {
                            break;
                        } else {
                            this.leftToLeft = a.getInt(attr, -1);
                            break;
                        }
                    case 9:
                        int resourceId3 = a.getResourceId(attr, this.leftToRight);
                        this.leftToRight = resourceId3;
                        if (resourceId3 != -1) {
                            break;
                        } else {
                            this.leftToRight = a.getInt(attr, -1);
                            break;
                        }
                    case 10:
                        int resourceId4 = a.getResourceId(attr, this.rightToLeft);
                        this.rightToLeft = resourceId4;
                        if (resourceId4 != -1) {
                            break;
                        } else {
                            this.rightToLeft = a.getInt(attr, -1);
                            break;
                        }
                    case 11:
                        int resourceId5 = a.getResourceId(attr, this.rightToRight);
                        this.rightToRight = resourceId5;
                        if (resourceId5 != -1) {
                            break;
                        } else {
                            this.rightToRight = a.getInt(attr, -1);
                            break;
                        }
                    case 12:
                        int resourceId6 = a.getResourceId(attr, this.topToTop);
                        this.topToTop = resourceId6;
                        if (resourceId6 != -1) {
                            break;
                        } else {
                            this.topToTop = a.getInt(attr, -1);
                            break;
                        }
                    case 13:
                        int resourceId7 = a.getResourceId(attr, this.topToBottom);
                        this.topToBottom = resourceId7;
                        if (resourceId7 != -1) {
                            break;
                        } else {
                            this.topToBottom = a.getInt(attr, -1);
                            break;
                        }
                    case 14:
                        int resourceId8 = a.getResourceId(attr, this.bottomToTop);
                        this.bottomToTop = resourceId8;
                        if (resourceId8 != -1) {
                            break;
                        } else {
                            this.bottomToTop = a.getInt(attr, -1);
                            break;
                        }
                    case 15:
                        int resourceId9 = a.getResourceId(attr, this.bottomToBottom);
                        this.bottomToBottom = resourceId9;
                        if (resourceId9 != -1) {
                            break;
                        } else {
                            this.bottomToBottom = a.getInt(attr, -1);
                            break;
                        }
                    case 16:
                        int resourceId10 = a.getResourceId(attr, this.baselineToBaseline);
                        this.baselineToBaseline = resourceId10;
                        if (resourceId10 != -1) {
                            break;
                        } else {
                            this.baselineToBaseline = a.getInt(attr, -1);
                            break;
                        }
                    case 17:
                        int resourceId11 = a.getResourceId(attr, this.startToEnd);
                        this.startToEnd = resourceId11;
                        if (resourceId11 != -1) {
                            break;
                        } else {
                            this.startToEnd = a.getInt(attr, -1);
                            break;
                        }
                    case 18:
                        int resourceId12 = a.getResourceId(attr, this.startToStart);
                        this.startToStart = resourceId12;
                        if (resourceId12 != -1) {
                            break;
                        } else {
                            this.startToStart = a.getInt(attr, -1);
                            break;
                        }
                    case 19:
                        int resourceId13 = a.getResourceId(attr, this.endToStart);
                        this.endToStart = resourceId13;
                        if (resourceId13 != -1) {
                            break;
                        } else {
                            this.endToStart = a.getInt(attr, -1);
                            break;
                        }
                    case 20:
                        int resourceId14 = a.getResourceId(attr, this.endToEnd);
                        this.endToEnd = resourceId14;
                        if (resourceId14 != -1) {
                            break;
                        } else {
                            this.endToEnd = a.getInt(attr, -1);
                            break;
                        }
                    case 21:
                        this.goneLeftMargin = a.getDimensionPixelSize(attr, this.goneLeftMargin);
                        break;
                    case 22:
                        this.goneTopMargin = a.getDimensionPixelSize(attr, this.goneTopMargin);
                        break;
                    case 23:
                        this.goneRightMargin = a.getDimensionPixelSize(attr, this.goneRightMargin);
                        break;
                    case 24:
                        this.goneBottomMargin = a.getDimensionPixelSize(attr, this.goneBottomMargin);
                        break;
                    case 25:
                        this.goneStartMargin = a.getDimensionPixelSize(attr, this.goneStartMargin);
                        break;
                    case 26:
                        this.goneEndMargin = a.getDimensionPixelSize(attr, this.goneEndMargin);
                        break;
                    case 27:
                        this.constrainedWidth = a.getBoolean(attr, this.constrainedWidth);
                        break;
                    case 28:
                        this.constrainedHeight = a.getBoolean(attr, this.constrainedHeight);
                        break;
                    case 29:
                        this.horizontalBias = a.getFloat(attr, this.horizontalBias);
                        break;
                    case 30:
                        this.verticalBias = a.getFloat(attr, this.verticalBias);
                        break;
                    case 31:
                        int i2 = a.getInt(attr, 0);
                        this.matchConstraintDefaultWidth = i2;
                        if (i2 != 1) {
                            break;
                        } else {
                            Log.e(ConstraintLayout.TAG, "layout_constraintWidth_default=\"wrap\" is deprecated.\nUse layout_width=\"WRAP_CONTENT\" and layout_constrainedWidth=\"true\" instead.");
                            break;
                        }
                    case 32:
                        int i3 = a.getInt(attr, 0);
                        this.matchConstraintDefaultHeight = i3;
                        if (i3 != 1) {
                            break;
                        } else {
                            Log.e(ConstraintLayout.TAG, "layout_constraintHeight_default=\"wrap\" is deprecated.\nUse layout_height=\"WRAP_CONTENT\" and layout_constrainedHeight=\"true\" instead.");
                            break;
                        }
                    case 33:
                        try {
                            this.matchConstraintMinWidth = a.getDimensionPixelSize(attr, this.matchConstraintMinWidth);
                            break;
                        } catch (Exception e) {
                            if (a.getInt(attr, this.matchConstraintMinWidth) != -2) {
                                break;
                            } else {
                                this.matchConstraintMinWidth = -2;
                                break;
                            }
                        }
                    case 34:
                        try {
                            this.matchConstraintMaxWidth = a.getDimensionPixelSize(attr, this.matchConstraintMaxWidth);
                            break;
                        } catch (Exception e2) {
                            if (a.getInt(attr, this.matchConstraintMaxWidth) != -2) {
                                break;
                            } else {
                                this.matchConstraintMaxWidth = -2;
                                break;
                            }
                        }
                    case 35:
                        this.matchConstraintPercentWidth = Math.max(0.0f, a.getFloat(attr, this.matchConstraintPercentWidth));
                        this.matchConstraintDefaultWidth = 2;
                        break;
                    case 36:
                        try {
                            this.matchConstraintMinHeight = a.getDimensionPixelSize(attr, this.matchConstraintMinHeight);
                            break;
                        } catch (Exception e3) {
                            if (a.getInt(attr, this.matchConstraintMinHeight) != -2) {
                                break;
                            } else {
                                this.matchConstraintMinHeight = -2;
                                break;
                            }
                        }
                    case 37:
                        try {
                            this.matchConstraintMaxHeight = a.getDimensionPixelSize(attr, this.matchConstraintMaxHeight);
                            break;
                        } catch (Exception e4) {
                            if (a.getInt(attr, this.matchConstraintMaxHeight) != -2) {
                                break;
                            } else {
                                this.matchConstraintMaxHeight = -2;
                                break;
                            }
                        }
                    case 38:
                        this.matchConstraintPercentHeight = Math.max(0.0f, a.getFloat(attr, this.matchConstraintPercentHeight));
                        this.matchConstraintDefaultHeight = 2;
                        break;
                    case 44:
                        ConstraintSet.parseDimensionRatioString(this, a.getString(attr));
                        break;
                    case 45:
                        this.horizontalWeight = a.getFloat(attr, this.horizontalWeight);
                        break;
                    case 46:
                        this.verticalWeight = a.getFloat(attr, this.verticalWeight);
                        break;
                    case 47:
                        this.horizontalChainStyle = a.getInt(attr, 0);
                        break;
                    case 48:
                        this.verticalChainStyle = a.getInt(attr, 0);
                        break;
                    case 49:
                        this.editorAbsoluteX = a.getDimensionPixelOffset(attr, this.editorAbsoluteX);
                        break;
                    case 50:
                        this.editorAbsoluteY = a.getDimensionPixelOffset(attr, this.editorAbsoluteY);
                        break;
                    case 51:
                        this.constraintTag = a.getString(attr);
                        break;
                    case 52:
                        int resourceId15 = a.getResourceId(attr, this.baselineToTop);
                        this.baselineToTop = resourceId15;
                        if (resourceId15 != -1) {
                            break;
                        } else {
                            this.baselineToTop = a.getInt(attr, -1);
                            break;
                        }
                    case 53:
                        int resourceId16 = a.getResourceId(attr, this.baselineToBottom);
                        this.baselineToBottom = resourceId16;
                        if (resourceId16 != -1) {
                            break;
                        } else {
                            this.baselineToBottom = a.getInt(attr, -1);
                            break;
                        }
                    case 54:
                        this.baselineMargin = a.getDimensionPixelSize(attr, this.baselineMargin);
                        break;
                    case 55:
                        this.goneBaselineMargin = a.getDimensionPixelSize(attr, this.goneBaselineMargin);
                        break;
                    case 64:
                        ConstraintSet.parseDimensionConstraints(this, a, attr, 0);
                        this.widthSet = ConstraintLayout.USE_CONSTRAINTS_HELPER;
                        break;
                    case 65:
                        ConstraintSet.parseDimensionConstraints(this, a, attr, 1);
                        this.heightSet = ConstraintLayout.USE_CONSTRAINTS_HELPER;
                        break;
                    case 66:
                        this.wrapBehaviorInParent = a.getInt(attr, this.wrapBehaviorInParent);
                        break;
                    case 67:
                        this.guidelineUseRtl = a.getBoolean(attr, this.guidelineUseRtl);
                        break;
                }
            }
            a.recycle();
            validate();
        }

        public void validate() {
            this.isGuideline = false;
            this.horizontalDimensionFixed = ConstraintLayout.USE_CONSTRAINTS_HELPER;
            this.verticalDimensionFixed = ConstraintLayout.USE_CONSTRAINTS_HELPER;
            if (this.width == -2 && this.constrainedWidth) {
                this.horizontalDimensionFixed = false;
                if (this.matchConstraintDefaultWidth == 0) {
                    this.matchConstraintDefaultWidth = 1;
                }
            }
            if (this.height == -2 && this.constrainedHeight) {
                this.verticalDimensionFixed = false;
                if (this.matchConstraintDefaultHeight == 0) {
                    this.matchConstraintDefaultHeight = 1;
                }
            }
            if (this.width == 0 || this.width == -1) {
                this.horizontalDimensionFixed = false;
                if (this.width == 0 && this.matchConstraintDefaultWidth == 1) {
                    this.width = -2;
                    this.constrainedWidth = ConstraintLayout.USE_CONSTRAINTS_HELPER;
                }
            }
            if (this.height == 0 || this.height == -1) {
                this.verticalDimensionFixed = false;
                if (this.height == 0 && this.matchConstraintDefaultHeight == 1) {
                    this.height = -2;
                    this.constrainedHeight = ConstraintLayout.USE_CONSTRAINTS_HELPER;
                }
            }
            if (this.guidePercent != -1.0f || this.guideBegin != -1 || this.guideEnd != -1) {
                this.isGuideline = ConstraintLayout.USE_CONSTRAINTS_HELPER;
                this.horizontalDimensionFixed = ConstraintLayout.USE_CONSTRAINTS_HELPER;
                this.verticalDimensionFixed = ConstraintLayout.USE_CONSTRAINTS_HELPER;
                if (!(this.widget instanceof Guideline)) {
                    this.widget = new Guideline();
                }
                ((Guideline) this.widget).setOrientation(this.orientation);
            }
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public void resolveLayoutDirection(int layoutDirection) {
            int originalLeftMargin = this.leftMargin;
            int originalRightMargin = this.rightMargin;
            super.resolveLayoutDirection(layoutDirection);
            boolean isRtl = 1 == getLayoutDirection() ? ConstraintLayout.USE_CONSTRAINTS_HELPER : false;
            this.resolvedRightToLeft = -1;
            this.resolvedRightToRight = -1;
            this.resolvedLeftToLeft = -1;
            this.resolvedLeftToRight = -1;
            this.resolveGoneLeftMargin = -1;
            this.resolveGoneRightMargin = -1;
            this.resolveGoneLeftMargin = this.goneLeftMargin;
            this.resolveGoneRightMargin = this.goneRightMargin;
            float f = this.horizontalBias;
            this.resolvedHorizontalBias = f;
            int i = this.guideBegin;
            this.resolvedGuideBegin = i;
            int i2 = this.guideEnd;
            this.resolvedGuideEnd = i2;
            float f2 = this.guidePercent;
            this.resolvedGuidePercent = f2;
            if (isRtl) {
                boolean startEndDefined = false;
                int i3 = this.startToEnd;
                if (i3 != -1) {
                    this.resolvedRightToLeft = i3;
                    startEndDefined = ConstraintLayout.USE_CONSTRAINTS_HELPER;
                } else {
                    int i4 = this.startToStart;
                    if (i4 != -1) {
                        this.resolvedRightToRight = i4;
                        startEndDefined = ConstraintLayout.USE_CONSTRAINTS_HELPER;
                    }
                }
                int i5 = this.endToStart;
                if (i5 != -1) {
                    this.resolvedLeftToRight = i5;
                    startEndDefined = ConstraintLayout.USE_CONSTRAINTS_HELPER;
                }
                int i6 = this.endToEnd;
                if (i6 != -1) {
                    this.resolvedLeftToLeft = i6;
                    startEndDefined = ConstraintLayout.USE_CONSTRAINTS_HELPER;
                }
                int i7 = this.goneStartMargin;
                if (i7 != Integer.MIN_VALUE) {
                    this.resolveGoneRightMargin = i7;
                }
                int i8 = this.goneEndMargin;
                if (i8 != Integer.MIN_VALUE) {
                    this.resolveGoneLeftMargin = i8;
                }
                if (startEndDefined) {
                    this.resolvedHorizontalBias = 1.0f - f;
                }
                if (this.isGuideline && this.orientation == 1 && this.guidelineUseRtl) {
                    if (f2 != -1.0f) {
                        this.resolvedGuidePercent = 1.0f - f2;
                        this.resolvedGuideBegin = -1;
                        this.resolvedGuideEnd = -1;
                    } else if (i != -1) {
                        this.resolvedGuideEnd = i;
                        this.resolvedGuideBegin = -1;
                        this.resolvedGuidePercent = -1.0f;
                    } else if (i2 != -1) {
                        this.resolvedGuideBegin = i2;
                        this.resolvedGuideEnd = -1;
                        this.resolvedGuidePercent = -1.0f;
                    }
                }
            } else {
                int i9 = this.startToEnd;
                if (i9 != -1) {
                    this.resolvedLeftToRight = i9;
                }
                int i10 = this.startToStart;
                if (i10 != -1) {
                    this.resolvedLeftToLeft = i10;
                }
                int i11 = this.endToStart;
                if (i11 != -1) {
                    this.resolvedRightToLeft = i11;
                }
                int i12 = this.endToEnd;
                if (i12 != -1) {
                    this.resolvedRightToRight = i12;
                }
                int i13 = this.goneStartMargin;
                if (i13 != Integer.MIN_VALUE) {
                    this.resolveGoneLeftMargin = i13;
                }
                int i14 = this.goneEndMargin;
                if (i14 != Integer.MIN_VALUE) {
                    this.resolveGoneRightMargin = i14;
                }
            }
            if (this.endToStart == -1 && this.endToEnd == -1 && this.startToStart == -1 && this.startToEnd == -1) {
                int i15 = this.rightToLeft;
                if (i15 != -1) {
                    this.resolvedRightToLeft = i15;
                    if (this.rightMargin <= 0 && originalRightMargin > 0) {
                        this.rightMargin = originalRightMargin;
                    }
                } else {
                    int i16 = this.rightToRight;
                    if (i16 != -1) {
                        this.resolvedRightToRight = i16;
                        if (this.rightMargin <= 0 && originalRightMargin > 0) {
                            this.rightMargin = originalRightMargin;
                        }
                    }
                }
                int i17 = this.leftToLeft;
                if (i17 != -1) {
                    this.resolvedLeftToLeft = i17;
                    if (this.leftMargin <= 0 && originalLeftMargin > 0) {
                        this.leftMargin = originalLeftMargin;
                        return;
                    }
                    return;
                }
                int i18 = this.leftToRight;
                if (i18 != -1) {
                    this.resolvedLeftToRight = i18;
                    if (this.leftMargin <= 0 && originalLeftMargin > 0) {
                        this.leftMargin = originalLeftMargin;
                    }
                }
            }
        }

        public String getConstraintTag() {
            return this.constraintTag;
        }
    }

    public void requestLayout() {
        markHierarchyDirty();
        super.requestLayout();
    }

    public void forceLayout() {
        markHierarchyDirty();
        super.forceLayout();
    }

    private void markHierarchyDirty() {
        this.mDirtyHierarchy = USE_CONSTRAINTS_HELPER;
        this.mLastMeasureWidth = -1;
        this.mLastMeasureHeight = -1;
        this.mLastMeasureWidthSize = -1;
        this.mLastMeasureHeightSize = -1;
        this.mLastMeasureWidthMode = 0;
        this.mLastMeasureHeightMode = 0;
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    public String getSceneString() {
        int id;
        StringBuilder ret = new StringBuilder();
        if (this.mLayoutWidget.stringId == null) {
            int id2 = getId();
            if (id2 != -1) {
                this.mLayoutWidget.stringId = getContext().getResources().getResourceEntryName(id2);
            } else {
                this.mLayoutWidget.stringId = "parent";
            }
        }
        if (this.mLayoutWidget.getDebugName() == null) {
            ConstraintWidgetContainer constraintWidgetContainer = this.mLayoutWidget;
            constraintWidgetContainer.setDebugName(constraintWidgetContainer.stringId);
            Log.v(TAG, " setDebugName " + this.mLayoutWidget.getDebugName());
        }
        Iterator<ConstraintWidget> it = this.mLayoutWidget.getChildren().iterator();
        while (it.hasNext()) {
            ConstraintWidget child = it.next();
            View v = (View) child.getCompanionWidget();
            if (v != null) {
                if (child.stringId == null && (id = v.getId()) != -1) {
                    child.stringId = getContext().getResources().getResourceEntryName(id);
                }
                if (child.getDebugName() == null) {
                    child.setDebugName(child.stringId);
                    Log.v(TAG, " setDebugName " + child.getDebugName());
                }
            }
        }
        this.mLayoutWidget.getSceneString(ret);
        return ret.toString();
    }
}
