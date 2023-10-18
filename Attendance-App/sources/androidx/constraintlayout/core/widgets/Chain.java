package androidx.constraintlayout.core.widgets;

import androidx.constraintlayout.core.LinearSystem;
import java.util.ArrayList;

public class Chain {
    private static final boolean DEBUG = false;
    public static final boolean USE_CHAIN_OPTIMIZATION = false;

    public static void applyChainConstraints(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem system, ArrayList<ConstraintWidget> widgets, int orientation) {
        ChainHead[] chainsArray;
        int chainsSize;
        int offset;
        if (orientation == 0) {
            offset = 0;
            chainsSize = constraintWidgetContainer.mHorizontalChainsSize;
            chainsArray = constraintWidgetContainer.mHorizontalChainsArray;
        } else {
            offset = 2;
            chainsSize = constraintWidgetContainer.mVerticalChainsSize;
            chainsArray = constraintWidgetContainer.mVerticalChainsArray;
        }
        for (int i = 0; i < chainsSize; i++) {
            ChainHead first = chainsArray[i];
            first.define();
            if (widgets == null || (widgets != null && widgets.contains(first.mFirst))) {
                applyChainConstraints(constraintWidgetContainer, system, orientation, offset, first);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:313:0x069c  */
    /* JADX WARNING: Removed duplicated region for block: B:316:0x06a8  */
    /* JADX WARNING: Removed duplicated region for block: B:317:0x06ad  */
    /* JADX WARNING: Removed duplicated region for block: B:320:0x06b3  */
    /* JADX WARNING: Removed duplicated region for block: B:321:0x06b8  */
    /* JADX WARNING: Removed duplicated region for block: B:323:0x06bb  */
    /* JADX WARNING: Removed duplicated region for block: B:328:0x06d3  */
    /* JADX WARNING: Removed duplicated region for block: B:330:0x06d7  */
    /* JADX WARNING: Removed duplicated region for block: B:331:0x06e4  */
    /* JADX WARNING: Removed duplicated region for block: B:333:0x06e8 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void applyChainConstraints(androidx.constraintlayout.core.widgets.ConstraintWidgetContainer r44, androidx.constraintlayout.core.LinearSystem r45, int r46, int r47, androidx.constraintlayout.core.widgets.ChainHead r48) {
        /*
            r0 = r44
            r10 = r45
            r11 = r46
            r12 = r48
            androidx.constraintlayout.core.widgets.ConstraintWidget r13 = r12.mFirst
            androidx.constraintlayout.core.widgets.ConstraintWidget r14 = r12.mLast
            androidx.constraintlayout.core.widgets.ConstraintWidget r15 = r12.mFirstVisibleWidget
            androidx.constraintlayout.core.widgets.ConstraintWidget r9 = r12.mLastVisibleWidget
            androidx.constraintlayout.core.widgets.ConstraintWidget r8 = r12.mHead
            r1 = r13
            r2 = 0
            r3 = 0
            float r4 = r12.mTotalWeight
            androidx.constraintlayout.core.widgets.ConstraintWidget r7 = r12.mFirstMatchConstraintWidget
            androidx.constraintlayout.core.widgets.ConstraintWidget r6 = r12.mLastMatchConstraintWidget
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r5 = r0.mListDimensionBehaviors
            r5 = r5[r11]
            r16 = r1
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r1 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            r17 = r2
            if (r5 != r1) goto L_0x0029
            r1 = 1
            goto L_0x002a
        L_0x0029:
            r1 = 0
        L_0x002a:
            r19 = r1
            r1 = 0
            r5 = 0
            r20 = 0
            if (r11 != 0) goto L_0x0057
            int r2 = r8.mHorizontalChainStyle
            if (r2 != 0) goto L_0x0038
            r2 = 1
            goto L_0x0039
        L_0x0038:
            r2 = 0
        L_0x0039:
            r1 = r2
            int r2 = r8.mHorizontalChainStyle
            r23 = r1
            r1 = 1
            if (r2 != r1) goto L_0x0043
            r1 = 1
            goto L_0x0044
        L_0x0043:
            r1 = 0
        L_0x0044:
            int r2 = r8.mHorizontalChainStyle
            r5 = 2
            if (r2 != r5) goto L_0x004b
            r2 = 1
            goto L_0x004c
        L_0x004b:
            r2 = 0
        L_0x004c:
            r22 = r3
            r5 = r16
            r20 = r17
            r16 = r1
            r17 = r2
            goto L_0x007b
        L_0x0057:
            int r2 = r8.mVerticalChainStyle
            if (r2 != 0) goto L_0x005d
            r2 = 1
            goto L_0x005e
        L_0x005d:
            r2 = 0
        L_0x005e:
            r1 = r2
            int r2 = r8.mVerticalChainStyle
            r23 = r1
            r1 = 1
            if (r2 != r1) goto L_0x0068
            r1 = 1
            goto L_0x0069
        L_0x0068:
            r1 = 0
        L_0x0069:
            int r2 = r8.mVerticalChainStyle
            r5 = 2
            if (r2 != r5) goto L_0x0070
            r2 = 1
            goto L_0x0071
        L_0x0070:
            r2 = 0
        L_0x0071:
            r22 = r3
            r5 = r16
            r20 = r17
            r16 = r1
            r17 = r2
        L_0x007b:
            if (r22 != 0) goto L_0x017a
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r5.mListAnchors
            r1 = r1[r47]
            r25 = 4
            if (r17 == 0) goto L_0x0087
            r25 = 1
        L_0x0087:
            int r26 = r1.getMargin()
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r3 = r5.mListDimensionBehaviors
            r3 = r3[r11]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r2 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r3 != r2) goto L_0x009b
            int[] r2 = r5.mResolvedMatchConstraintDefault
            r2 = r2[r11]
            if (r2 != 0) goto L_0x009b
            r2 = 1
            goto L_0x009c
        L_0x009b:
            r2 = 0
        L_0x009c:
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r1.mTarget
            if (r3 == 0) goto L_0x00ad
            if (r5 == r13) goto L_0x00ad
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r1.mTarget
            int r3 = r3.getMargin()
            int r26 = r26 + r3
            r3 = r26
            goto L_0x00af
        L_0x00ad:
            r3 = r26
        L_0x00af:
            if (r17 == 0) goto L_0x00b7
            if (r5 == r13) goto L_0x00b7
            if (r5 == r15) goto L_0x00b7
            r25 = 8
        L_0x00b7:
            r26 = r4
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r1.mTarget
            if (r4 == 0) goto L_0x00fd
            if (r5 != r15) goto L_0x00ce
            androidx.constraintlayout.core.SolverVariable r4 = r1.mSolverVariable
            r29 = r6
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r1.mTarget
            androidx.constraintlayout.core.SolverVariable r6 = r6.mSolverVariable
            r30 = r7
            r7 = 6
            r10.addGreaterThan(r4, r6, r3, r7)
            goto L_0x00dd
        L_0x00ce:
            r29 = r6
            r30 = r7
            androidx.constraintlayout.core.SolverVariable r4 = r1.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r1.mTarget
            androidx.constraintlayout.core.SolverVariable r6 = r6.mSolverVariable
            r7 = 8
            r10.addGreaterThan(r4, r6, r3, r7)
        L_0x00dd:
            if (r2 == 0) goto L_0x00e3
            if (r17 != 0) goto L_0x00e3
            r25 = 5
        L_0x00e3:
            if (r5 != r15) goto L_0x00ef
            if (r17 == 0) goto L_0x00ef
            boolean r4 = r5.isInBarrier(r11)
            if (r4 == 0) goto L_0x00ef
            r4 = 5
            goto L_0x00f1
        L_0x00ef:
            r4 = r25
        L_0x00f1:
            androidx.constraintlayout.core.SolverVariable r6 = r1.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor r7 = r1.mTarget
            androidx.constraintlayout.core.SolverVariable r7 = r7.mSolverVariable
            r10.addEquality(r6, r7, r3, r4)
            r25 = r4
            goto L_0x0101
        L_0x00fd:
            r29 = r6
            r30 = r7
        L_0x0101:
            if (r19 == 0) goto L_0x013e
            int r4 = r5.getVisibility()
            r6 = 8
            if (r4 == r6) goto L_0x0129
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r4 = r5.mListDimensionBehaviors
            r4 = r4[r11]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r6 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r4 != r6) goto L_0x0129
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r4 = r5.mListAnchors
            int r6 = r47 + 1
            r4 = r4[r6]
            androidx.constraintlayout.core.SolverVariable r4 = r4.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r6 = r5.mListAnchors
            r6 = r6[r47]
            androidx.constraintlayout.core.SolverVariable r6 = r6.mSolverVariable
            r24 = r1
            r1 = 0
            r7 = 5
            r10.addGreaterThan(r4, r6, r1, r7)
            goto L_0x012b
        L_0x0129:
            r24 = r1
        L_0x012b:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r5.mListAnchors
            r1 = r1[r47]
            androidx.constraintlayout.core.SolverVariable r1 = r1.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r4 = r0.mListAnchors
            r4 = r4[r47]
            androidx.constraintlayout.core.SolverVariable r4 = r4.mSolverVariable
            r6 = 8
            r7 = 0
            r10.addGreaterThan(r1, r4, r7, r6)
            goto L_0x0140
        L_0x013e:
            r24 = r1
        L_0x0140:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r5.mListAnchors
            int r4 = r47 + 1
            r1 = r1[r4]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r1.mTarget
            if (r1 == 0) goto L_0x0166
            androidx.constraintlayout.core.widgets.ConstraintWidget r4 = r1.mOwner
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r6 = r4.mListAnchors
            r6 = r6[r47]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r6.mTarget
            if (r6 == 0) goto L_0x0162
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r6 = r4.mListAnchors
            r6 = r6[r47]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r6.mTarget
            androidx.constraintlayout.core.widgets.ConstraintWidget r6 = r6.mOwner
            if (r6 == r5) goto L_0x015f
            goto L_0x0162
        L_0x015f:
            r20 = r4
            goto L_0x0169
        L_0x0162:
            r4 = 0
            r20 = r4
            goto L_0x0169
        L_0x0166:
            r4 = 0
            r20 = r4
        L_0x0169:
            if (r20 == 0) goto L_0x016f
            r4 = r20
            r5 = r4
            goto L_0x0172
        L_0x016f:
            r4 = 1
            r22 = r4
        L_0x0172:
            r4 = r26
            r6 = r29
            r7 = r30
            goto L_0x007b
        L_0x017a:
            r26 = r4
            r29 = r6
            r30 = r7
            if (r9 == 0) goto L_0x01e9
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r2 = r14.mListAnchors
            int r3 = r47 + 1
            r2 = r2[r3]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r2.mTarget
            if (r2 == 0) goto L_0x01e9
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r2 = r9.mListAnchors
            int r3 = r47 + 1
            r2 = r2[r3]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour[] r3 = r9.mListDimensionBehaviors
            r3 = r3[r11]
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r4 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r3 != r4) goto L_0x01a2
            int[] r3 = r9.mResolvedMatchConstraintDefault
            r3 = r3[r11]
            if (r3 != 0) goto L_0x01a2
            r3 = 1
            goto L_0x01a3
        L_0x01a2:
            r3 = 0
        L_0x01a3:
            if (r3 == 0) goto L_0x01bd
            if (r17 != 0) goto L_0x01bd
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r2.mTarget
            androidx.constraintlayout.core.widgets.ConstraintWidget r4 = r4.mOwner
            if (r4 != r0) goto L_0x01bd
            androidx.constraintlayout.core.SolverVariable r4 = r2.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor r6 = r2.mTarget
            androidx.constraintlayout.core.SolverVariable r6 = r6.mSolverVariable
            int r7 = r2.getMargin()
            int r7 = -r7
            r1 = 5
            r10.addEquality(r4, r6, r7, r1)
            goto L_0x01d4
        L_0x01bd:
            if (r17 == 0) goto L_0x01d4
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r2.mTarget
            androidx.constraintlayout.core.widgets.ConstraintWidget r1 = r1.mOwner
            if (r1 != r0) goto L_0x01d4
            androidx.constraintlayout.core.SolverVariable r1 = r2.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r2.mTarget
            androidx.constraintlayout.core.SolverVariable r4 = r4.mSolverVariable
            int r6 = r2.getMargin()
            int r6 = -r6
            r7 = 4
            r10.addEquality(r1, r4, r6, r7)
        L_0x01d4:
            androidx.constraintlayout.core.SolverVariable r1 = r2.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r4 = r14.mListAnchors
            int r6 = r47 + 1
            r4 = r4[r6]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r4 = r4.mTarget
            androidx.constraintlayout.core.SolverVariable r4 = r4.mSolverVariable
            int r6 = r2.getMargin()
            int r6 = -r6
            r7 = 6
            r10.addLowerThan(r1, r4, r6, r7)
        L_0x01e9:
            if (r19 == 0) goto L_0x020a
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r0.mListAnchors
            int r2 = r47 + 1
            r1 = r1[r2]
            androidx.constraintlayout.core.SolverVariable r1 = r1.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r2 = r14.mListAnchors
            int r3 = r47 + 1
            r2 = r2[r3]
            androidx.constraintlayout.core.SolverVariable r2 = r2.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r3 = r14.mListAnchors
            int r4 = r47 + 1
            r3 = r3[r4]
            int r3 = r3.getMargin()
            r4 = 8
            r10.addGreaterThan(r1, r2, r3, r4)
        L_0x020a:
            java.util.ArrayList<androidx.constraintlayout.core.widgets.ConstraintWidget> r7 = r12.mWeightedMatchConstraintsWidgets
            if (r7 == 0) goto L_0x02f3
            int r1 = r7.size()
            r2 = 1
            if (r1 <= r2) goto L_0x02ea
            r3 = 0
            r4 = 0
            boolean r6 = r12.mHasUndefinedWeights
            if (r6 == 0) goto L_0x0223
            boolean r6 = r12.mHasComplexMatchWeights
            if (r6 != 0) goto L_0x0223
            int r6 = r12.mWidgetsMatchCount
            float r6 = (float) r6
            goto L_0x0225
        L_0x0223:
            r6 = r26
        L_0x0225:
            r21 = 0
            r2 = r21
        L_0x0229:
            if (r2 >= r1) goto L_0x02dd
            java.lang.Object r24 = r7.get(r2)
            r0 = r24
            androidx.constraintlayout.core.widgets.ConstraintWidget r0 = (androidx.constraintlayout.core.widgets.ConstraintWidget) r0
            r24 = r1
            float[] r1 = r0.mWeight
            r1 = r1[r11]
            r26 = 0
            int r28 = (r1 > r26 ? 1 : (r1 == r26 ? 0 : -1))
            if (r28 >= 0) goto L_0x0269
            r28 = r1
            boolean r1 = r12.mHasComplexMatchWeights
            if (r1 == 0) goto L_0x0261
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r0.mListAnchors
            int r26 = r47 + 1
            r1 = r1[r26]
            androidx.constraintlayout.core.SolverVariable r1 = r1.mSolverVariable
            r39 = r5
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r5 = r0.mListAnchors
            r5 = r5[r47]
            androidx.constraintlayout.core.SolverVariable r5 = r5.mSolverVariable
            r40 = r7
            r7 = 4
            r12 = 0
            r10.addEquality(r1, r5, r12, r7)
            r18 = r8
            r8 = 0
            goto L_0x02cd
        L_0x0261:
            r39 = r5
            r40 = r7
            r7 = 4
            r1 = 1065353216(0x3f800000, float:1.0)
            goto L_0x0270
        L_0x0269:
            r28 = r1
            r39 = r5
            r40 = r7
            r7 = 4
        L_0x0270:
            int r5 = (r1 > r26 ? 1 : (r1 == r26 ? 0 : -1))
            if (r5 != 0) goto L_0x028b
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r5 = r0.mListAnchors
            int r12 = r47 + 1
            r5 = r5[r12]
            androidx.constraintlayout.core.SolverVariable r5 = r5.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r12 = r0.mListAnchors
            r12 = r12[r47]
            androidx.constraintlayout.core.SolverVariable r12 = r12.mSolverVariable
            r18 = r8
            r7 = 8
            r8 = 0
            r10.addEquality(r5, r12, r8, r7)
            goto L_0x02cd
        L_0x028b:
            r18 = r8
            r8 = 0
            if (r3 == 0) goto L_0x02c9
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r5 = r3.mListAnchors
            r5 = r5[r47]
            androidx.constraintlayout.core.SolverVariable r5 = r5.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r7 = r3.mListAnchors
            int r12 = r47 + 1
            r7 = r7[r12]
            androidx.constraintlayout.core.SolverVariable r7 = r7.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r12 = r0.mListAnchors
            r12 = r12[r47]
            androidx.constraintlayout.core.SolverVariable r12 = r12.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r8 = r0.mListAnchors
            int r26 = r47 + 1
            r8 = r8[r26]
            androidx.constraintlayout.core.SolverVariable r8 = r8.mSolverVariable
            r26 = r3
            androidx.constraintlayout.core.ArrayRow r3 = r45.createRow()
            r31 = r3
            r32 = r4
            r33 = r6
            r34 = r1
            r35 = r5
            r36 = r7
            r37 = r12
            r38 = r8
            r31.createRowEqualMatchDimensions(r32, r33, r34, r35, r36, r37, r38)
            r10.addConstraint(r3)
            goto L_0x02cb
        L_0x02c9:
            r26 = r3
        L_0x02cb:
            r3 = r0
            r4 = r1
        L_0x02cd:
            int r2 = r2 + 1
            r0 = r44
            r12 = r48
            r8 = r18
            r1 = r24
            r5 = r39
            r7 = r40
            goto L_0x0229
        L_0x02dd:
            r24 = r1
            r26 = r3
            r39 = r5
            r40 = r7
            r18 = r8
            r26 = r6
            goto L_0x02f9
        L_0x02ea:
            r24 = r1
            r39 = r5
            r40 = r7
            r18 = r8
            goto L_0x02f9
        L_0x02f3:
            r39 = r5
            r40 = r7
            r18 = r8
        L_0x02f9:
            if (r15 == 0) goto L_0x0391
            if (r15 == r9) goto L_0x030b
            if (r17 == 0) goto L_0x0300
            goto L_0x030b
        L_0x0300:
            r0 = r9
            r32 = r29
            r31 = r39
            r33 = r40
            r29 = r18
            goto L_0x039a
        L_0x030b:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r13.mListAnchors
            r1 = r1[r47]
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r2 = r14.mListAnchors
            int r3 = r47 + 1
            r2 = r2[r3]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r1.mTarget
            if (r3 == 0) goto L_0x031e
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r1.mTarget
            androidx.constraintlayout.core.SolverVariable r3 = r3.mSolverVariable
            goto L_0x031f
        L_0x031e:
            r3 = 0
        L_0x031f:
            r12 = r3
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r2.mTarget
            if (r3 == 0) goto L_0x0329
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r2.mTarget
            androidx.constraintlayout.core.SolverVariable r3 = r3.mSolverVariable
            goto L_0x032a
        L_0x0329:
            r3 = 0
        L_0x032a:
            r21 = r3
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r3 = r15.mListAnchors
            r8 = r3[r47]
            if (r9 == 0) goto L_0x033a
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r9.mListAnchors
            int r3 = r47 + 1
            r2 = r1[r3]
            r7 = r2
            goto L_0x033b
        L_0x033a:
            r7 = r2
        L_0x033b:
            if (r12 == 0) goto L_0x0380
            if (r21 == 0) goto L_0x0380
            r1 = 1056964608(0x3f000000, float:0.5)
            if (r11 != 0) goto L_0x034a
            r6 = r18
            float r1 = r6.mHorizontalBiasPercent
            r18 = r1
            goto L_0x0350
        L_0x034a:
            r6 = r18
            float r1 = r6.mVerticalBiasPercent
            r18 = r1
        L_0x0350:
            int r24 = r8.getMargin()
            int r25 = r7.getMargin()
            androidx.constraintlayout.core.SolverVariable r2 = r8.mSolverVariable
            androidx.constraintlayout.core.SolverVariable r5 = r7.mSolverVariable
            r27 = 7
            r1 = r45
            r3 = r12
            r4 = r24
            r28 = r5
            r31 = r39
            r5 = r18
            r32 = r29
            r29 = r6
            r6 = r21
            r34 = r7
            r33 = r40
            r7 = r28
            r28 = r8
            r8 = r25
            r0 = r9
            r9 = r27
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x038d
        L_0x0380:
            r34 = r7
            r28 = r8
            r0 = r9
            r32 = r29
            r31 = r39
            r33 = r40
            r29 = r18
        L_0x038d:
            r12 = r48
            goto L_0x068e
        L_0x0391:
            r0 = r9
            r32 = r29
            r31 = r39
            r33 = r40
            r29 = r18
        L_0x039a:
            if (r23 == 0) goto L_0x04e9
            if (r15 == 0) goto L_0x04e9
            r1 = r15
            r2 = r15
            r12 = r48
            int r3 = r12.mWidgetsMatchCount
            if (r3 <= 0) goto L_0x03af
            int r3 = r12.mWidgetsCount
            int r4 = r12.mWidgetsMatchCount
            if (r3 != r4) goto L_0x03af
            r28 = 1
            goto L_0x03b1
        L_0x03af:
            r28 = 0
        L_0x03b1:
            r18 = r28
            r8 = r1
            r9 = r2
        L_0x03b5:
            if (r8 == 0) goto L_0x04e1
            androidx.constraintlayout.core.widgets.ConstraintWidget[] r1 = r8.mNextChainWidget
            r1 = r1[r11]
            r7 = r1
        L_0x03bc:
            if (r7 == 0) goto L_0x03cb
            int r1 = r7.getVisibility()
            r3 = 8
            if (r1 != r3) goto L_0x03cd
            androidx.constraintlayout.core.widgets.ConstraintWidget[] r1 = r7.mNextChainWidget
            r7 = r1[r11]
            goto L_0x03bc
        L_0x03cb:
            r3 = 8
        L_0x03cd:
            if (r7 != 0) goto L_0x03da
            if (r8 != r0) goto L_0x03d2
            goto L_0x03da
        L_0x03d2:
            r39 = r7
            r40 = r8
            r41 = r9
            goto L_0x04cd
        L_0x03da:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r8.mListAnchors
            r6 = r1[r47]
            androidx.constraintlayout.core.SolverVariable r5 = r6.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r6.mTarget
            if (r1 == 0) goto L_0x03e9
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r6.mTarget
            androidx.constraintlayout.core.SolverVariable r1 = r1.mSolverVariable
            goto L_0x03ea
        L_0x03e9:
            r1 = 0
        L_0x03ea:
            if (r9 == r8) goto L_0x03f7
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r2 = r9.mListAnchors
            int r4 = r47 + 1
            r2 = r2[r4]
            androidx.constraintlayout.core.SolverVariable r1 = r2.mSolverVariable
            r20 = r1
            goto L_0x0411
        L_0x03f7:
            if (r8 != r15) goto L_0x040f
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r2 = r13.mListAnchors
            r2 = r2[r47]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r2.mTarget
            if (r2 == 0) goto L_0x040a
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r2 = r13.mListAnchors
            r2 = r2[r47]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r2.mTarget
            androidx.constraintlayout.core.SolverVariable r2 = r2.mSolverVariable
            goto L_0x040b
        L_0x040a:
            r2 = 0
        L_0x040b:
            r1 = r2
            r20 = r1
            goto L_0x0411
        L_0x040f:
            r20 = r1
        L_0x0411:
            r1 = 0
            r2 = 0
            r4 = 0
            int r21 = r6.getMargin()
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r3 = r8.mListAnchors
            int r24 = r47 + 1
            r3 = r3[r24]
            int r3 = r3.getMargin()
            if (r7 == 0) goto L_0x0431
            r24 = r1
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r7.mListAnchors
            r1 = r1[r47]
            androidx.constraintlayout.core.SolverVariable r2 = r1.mSolverVariable
            r24 = r1
            r25 = r2
            goto L_0x0448
        L_0x0431:
            r24 = r1
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r14.mListAnchors
            int r25 = r47 + 1
            r1 = r1[r25]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r1.mTarget
            if (r1 == 0) goto L_0x0444
            androidx.constraintlayout.core.SolverVariable r2 = r1.mSolverVariable
            r24 = r1
            r25 = r2
            goto L_0x0448
        L_0x0444:
            r24 = r1
            r25 = r2
        L_0x0448:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r8.mListAnchors
            int r2 = r47 + 1
            r1 = r1[r2]
            androidx.constraintlayout.core.SolverVariable r4 = r1.mSolverVariable
            if (r24 == 0) goto L_0x045a
            int r1 = r24.getMargin()
            int r3 = r3 + r1
            r28 = r3
            goto L_0x045c
        L_0x045a:
            r28 = r3
        L_0x045c:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r9.mListAnchors
            int r2 = r47 + 1
            r1 = r1[r2]
            int r1 = r1.getMargin()
            int r21 = r21 + r1
            if (r5 == 0) goto L_0x04c1
            if (r20 == 0) goto L_0x04c1
            if (r25 == 0) goto L_0x04c1
            if (r4 == 0) goto L_0x04c1
            r1 = r21
            if (r8 != r15) goto L_0x047f
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r2 = r15.mListAnchors
            r2 = r2[r47]
            int r1 = r2.getMargin()
            r31 = r1
            goto L_0x0481
        L_0x047f:
            r31 = r1
        L_0x0481:
            r1 = r28
            if (r8 != r0) goto L_0x0492
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r2 = r0.mListAnchors
            int r3 = r47 + 1
            r2 = r2[r3]
            int r1 = r2.getMargin()
            r34 = r1
            goto L_0x0494
        L_0x0492:
            r34 = r1
        L_0x0494:
            r1 = 5
            if (r18 == 0) goto L_0x049c
            r1 = 8
            r36 = r1
            goto L_0x049e
        L_0x049c:
            r36 = r1
        L_0x049e:
            r37 = 1056964608(0x3f000000, float:0.5)
            r1 = r45
            r2 = r5
            r3 = r20
            r27 = r4
            r4 = r31
            r38 = r5
            r5 = r37
            r37 = r6
            r6 = r25
            r39 = r7
            r7 = r27
            r40 = r8
            r8 = r34
            r41 = r9
            r9 = r36
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x04cd
        L_0x04c1:
            r27 = r4
            r38 = r5
            r37 = r6
            r39 = r7
            r40 = r8
            r41 = r9
        L_0x04cd:
            int r1 = r40.getVisibility()
            r9 = 8
            if (r1 == r9) goto L_0x04d8
            r1 = r40
            goto L_0x04da
        L_0x04d8:
            r1 = r41
        L_0x04da:
            r8 = r39
            r9 = r1
            r20 = r39
            goto L_0x03b5
        L_0x04e1:
            r40 = r8
            r41 = r9
            r31 = r40
            goto L_0x068e
        L_0x04e9:
            r12 = r48
            r9 = 8
            if (r16 == 0) goto L_0x068e
            if (r15 == 0) goto L_0x068e
            r1 = r15
            r2 = r15
            int r3 = r12.mWidgetsMatchCount
            if (r3 <= 0) goto L_0x0500
            int r3 = r12.mWidgetsCount
            int r4 = r12.mWidgetsMatchCount
            if (r3 != r4) goto L_0x0500
            r28 = 1
            goto L_0x0502
        L_0x0500:
            r28 = 0
        L_0x0502:
            r18 = r28
            r7 = r1
            r8 = r2
        L_0x0506:
            if (r7 == 0) goto L_0x05fd
            androidx.constraintlayout.core.widgets.ConstraintWidget[] r1 = r7.mNextChainWidget
            r1 = r1[r11]
        L_0x050c:
            if (r1 == 0) goto L_0x0519
            int r2 = r1.getVisibility()
            if (r2 != r9) goto L_0x0519
            androidx.constraintlayout.core.widgets.ConstraintWidget[] r2 = r1.mNextChainWidget
            r1 = r2[r11]
            goto L_0x050c
        L_0x0519:
            if (r7 == r15) goto L_0x05e1
            if (r7 == r0) goto L_0x05e1
            if (r1 == 0) goto L_0x05e1
            if (r1 != r0) goto L_0x0524
            r1 = 0
            r6 = r1
            goto L_0x0525
        L_0x0524:
            r6 = r1
        L_0x0525:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r7.mListAnchors
            r5 = r1[r47]
            androidx.constraintlayout.core.SolverVariable r4 = r5.mSolverVariable
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r5.mTarget
            if (r1 == 0) goto L_0x0534
            androidx.constraintlayout.core.widgets.ConstraintAnchor r1 = r5.mTarget
            androidx.constraintlayout.core.SolverVariable r1 = r1.mSolverVariable
            goto L_0x0535
        L_0x0534:
            r1 = 0
        L_0x0535:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r2 = r8.mListAnchors
            int r3 = r47 + 1
            r2 = r2[r3]
            androidx.constraintlayout.core.SolverVariable r3 = r2.mSolverVariable
            r1 = 0
            r2 = 0
            r20 = 0
            int r21 = r5.getMargin()
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r9 = r7.mListAnchors
            int r24 = r47 + 1
            r9 = r9[r24]
            int r9 = r9.getMargin()
            if (r6 == 0) goto L_0x056c
            r24 = r1
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r6.mListAnchors
            r1 = r1[r47]
            androidx.constraintlayout.core.SolverVariable r2 = r1.mSolverVariable
            r24 = r2
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r1.mTarget
            if (r2 == 0) goto L_0x0564
            androidx.constraintlayout.core.widgets.ConstraintAnchor r2 = r1.mTarget
            androidx.constraintlayout.core.SolverVariable r2 = r2.mSolverVariable
            goto L_0x0565
        L_0x0564:
            r2 = 0
        L_0x0565:
            r20 = r2
            r25 = r24
            r24 = r1
            goto L_0x0584
        L_0x056c:
            r24 = r1
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r0.mListAnchors
            r1 = r1[r47]
            if (r1 == 0) goto L_0x0576
            androidx.constraintlayout.core.SolverVariable r2 = r1.mSolverVariable
        L_0x0576:
            r24 = r1
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r7.mListAnchors
            int r25 = r47 + 1
            r1 = r1[r25]
            androidx.constraintlayout.core.SolverVariable r1 = r1.mSolverVariable
            r20 = r1
            r25 = r2
        L_0x0584:
            if (r24 == 0) goto L_0x058e
            int r1 = r24.getMargin()
            int r9 = r9 + r1
            r27 = r9
            goto L_0x0590
        L_0x058e:
            r27 = r9
        L_0x0590:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r8.mListAnchors
            int r2 = r47 + 1
            r1 = r1[r2]
            int r1 = r1.getMargin()
            int r21 = r21 + r1
            r1 = 4
            if (r18 == 0) goto L_0x05a4
            r1 = 8
            r28 = r1
            goto L_0x05a6
        L_0x05a4:
            r28 = r1
        L_0x05a6:
            if (r4 == 0) goto L_0x05d0
            if (r3 == 0) goto L_0x05d0
            if (r25 == 0) goto L_0x05d0
            if (r20 == 0) goto L_0x05d0
            r9 = 1056964608(0x3f000000, float:0.5)
            r1 = r45
            r2 = r4
            r31 = r3
            r34 = r4
            r4 = r21
            r36 = r5
            r5 = r9
            r37 = r6
            r6 = r25
            r38 = r7
            r7 = r20
            r39 = r8
            r8 = r27
            r11 = 8
            r9 = r28
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x05de
        L_0x05d0:
            r31 = r3
            r34 = r4
            r36 = r5
            r37 = r6
            r38 = r7
            r39 = r8
            r11 = 8
        L_0x05de:
            r20 = r37
            goto L_0x05e9
        L_0x05e1:
            r38 = r7
            r39 = r8
            r11 = 8
            r20 = r1
        L_0x05e9:
            int r1 = r38.getVisibility()
            if (r1 == r11) goto L_0x05f3
            r1 = r38
            r8 = r1
            goto L_0x05f5
        L_0x05f3:
            r8 = r39
        L_0x05f5:
            r7 = r20
            r11 = r46
            r9 = 8
            goto L_0x0506
        L_0x05fd:
            r38 = r7
            r39 = r8
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r15.mListAnchors
            r11 = r1[r47]
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r13.mListAnchors
            r1 = r1[r47]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r9 = r1.mTarget
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r0.mListAnchors
            int r2 = r47 + 1
            r8 = r1[r2]
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r14.mListAnchors
            int r2 = r47 + 1
            r1 = r1[r2]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r7 = r1.mTarget
            r6 = 5
            if (r9 == 0) goto L_0x0669
            if (r15 == r0) goto L_0x0632
            androidx.constraintlayout.core.SolverVariable r1 = r11.mSolverVariable
            androidx.constraintlayout.core.SolverVariable r2 = r9.mSolverVariable
            int r3 = r11.getMargin()
            r10.addEquality(r1, r2, r3, r6)
            r27 = r6
            r42 = r7
            r43 = r8
            r21 = r9
            goto L_0x0671
        L_0x0632:
            if (r7 == 0) goto L_0x0660
            androidx.constraintlayout.core.SolverVariable r2 = r11.mSolverVariable
            androidx.constraintlayout.core.SolverVariable r3 = r9.mSolverVariable
            int r4 = r11.getMargin()
            androidx.constraintlayout.core.SolverVariable r1 = r8.mSolverVariable
            androidx.constraintlayout.core.SolverVariable r5 = r7.mSolverVariable
            int r24 = r8.getMargin()
            r25 = r1
            r1 = r45
            r21 = r5
            r5 = 1056964608(0x3f000000, float:0.5)
            r27 = r6
            r6 = r25
            r42 = r7
            r7 = r21
            r43 = r8
            r8 = r24
            r21 = r9
            r9 = r27
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x0671
        L_0x0660:
            r27 = r6
            r42 = r7
            r43 = r8
            r21 = r9
            goto L_0x0671
        L_0x0669:
            r27 = r6
            r42 = r7
            r43 = r8
            r21 = r9
        L_0x0671:
            r1 = r42
            if (r1 == 0) goto L_0x0688
            if (r15 == r0) goto L_0x0688
            r2 = r43
            androidx.constraintlayout.core.SolverVariable r3 = r2.mSolverVariable
            androidx.constraintlayout.core.SolverVariable r4 = r1.mSolverVariable
            int r5 = r2.getMargin()
            int r5 = -r5
            r6 = r27
            r10.addEquality(r3, r4, r5, r6)
            goto L_0x068c
        L_0x0688:
            r6 = r27
            r2 = r43
        L_0x068c:
            r31 = r38
        L_0x068e:
            if (r23 != 0) goto L_0x0692
            if (r16 == 0) goto L_0x0719
        L_0x0692:
            if (r15 == 0) goto L_0x0719
            if (r15 == r0) goto L_0x0719
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r15.mListAnchors
            r1 = r1[r47]
            if (r0 != 0) goto L_0x069e
            r9 = r15
            r0 = r9
        L_0x069e:
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r2 = r0.mListAnchors
            int r3 = r47 + 1
            r2 = r2[r3]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r1.mTarget
            if (r3 == 0) goto L_0x06ad
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r1.mTarget
            androidx.constraintlayout.core.SolverVariable r3 = r3.mSolverVariable
            goto L_0x06ae
        L_0x06ad:
            r3 = 0
        L_0x06ae:
            r11 = r3
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r2.mTarget
            if (r3 == 0) goto L_0x06b8
            androidx.constraintlayout.core.widgets.ConstraintAnchor r3 = r2.mTarget
            androidx.constraintlayout.core.SolverVariable r3 = r3.mSolverVariable
            goto L_0x06b9
        L_0x06b8:
            r3 = 0
        L_0x06b9:
            if (r14 == r0) goto L_0x06d3
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r4 = r14.mListAnchors
            int r5 = r47 + 1
            r4 = r4[r5]
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r4.mTarget
            if (r5 == 0) goto L_0x06cc
            androidx.constraintlayout.core.widgets.ConstraintAnchor r5 = r4.mTarget
            androidx.constraintlayout.core.SolverVariable r5 = r5.mSolverVariable
            r35 = r5
            goto L_0x06ce
        L_0x06cc:
            r35 = 0
        L_0x06ce:
            r3 = r35
            r18 = r3
            goto L_0x06d5
        L_0x06d3:
            r18 = r3
        L_0x06d5:
            if (r15 != r0) goto L_0x06e4
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r3 = r15.mListAnchors
            r1 = r3[r47]
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r3 = r15.mListAnchors
            int r4 = r47 + 1
            r2 = r3[r4]
            r9 = r1
            r8 = r2
            goto L_0x06e6
        L_0x06e4:
            r9 = r1
            r8 = r2
        L_0x06e6:
            if (r11 == 0) goto L_0x0715
            if (r18 == 0) goto L_0x0715
            r21 = 1056964608(0x3f000000, float:0.5)
            int r24 = r9.getMargin()
            androidx.constraintlayout.core.widgets.ConstraintAnchor[] r1 = r0.mListAnchors
            int r2 = r47 + 1
            r1 = r1[r2]
            int r25 = r1.getMargin()
            androidx.constraintlayout.core.SolverVariable r2 = r9.mSolverVariable
            androidx.constraintlayout.core.SolverVariable r7 = r8.mSolverVariable
            r27 = 5
            r1 = r45
            r3 = r11
            r4 = r24
            r5 = r21
            r6 = r18
            r28 = r8
            r8 = r25
            r34 = r9
            r9 = r27
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x0719
        L_0x0715:
            r28 = r8
            r34 = r9
        L_0x0719:
            r9 = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.widgets.Chain.applyChainConstraints(androidx.constraintlayout.core.widgets.ConstraintWidgetContainer, androidx.constraintlayout.core.LinearSystem, int, int, androidx.constraintlayout.core.widgets.ChainHead):void");
    }
}
