package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;
import java.util.Iterator;

public class ChainRun extends WidgetRun {
    private int chainStyle;
    ArrayList<WidgetRun> widgets = new ArrayList<>();

    public ChainRun(ConstraintWidget widget, int orientation) {
        super(widget);
        this.orientation = orientation;
        build();
    }

    public String toString() {
        StringBuilder log = new StringBuilder("ChainRun ");
        log.append(this.orientation == 0 ? "horizontal : " : "vertical : ");
        Iterator<WidgetRun> it = this.widgets.iterator();
        while (it.hasNext()) {
            log.append("<");
            log.append(it.next());
            log.append("> ");
        }
        return log.toString();
    }

    /* access modifiers changed from: package-private */
    public boolean supportsWrapComputation() {
        int count = this.widgets.size();
        for (int i = 0; i < count; i++) {
            if (!this.widgets.get(i).supportsWrapComputation()) {
                return false;
            }
        }
        return true;
    }

    public long getWrapDimension() {
        int count = this.widgets.size();
        long wrapDimension = 0;
        for (int i = 0; i < count; i++) {
            WidgetRun run = this.widgets.get(i);
            wrapDimension = wrapDimension + ((long) run.start.margin) + run.getWrapDimension() + ((long) run.end.margin);
        }
        return wrapDimension;
    }

    private void build() {
        ConstraintWidget current = this.widget;
        ConstraintWidget previous = current.getPreviousChainMember(this.orientation);
        while (previous != null) {
            current = previous;
            previous = current.getPreviousChainMember(this.orientation);
        }
        this.widget = current;
        this.widgets.add(current.getRun(this.orientation));
        ConstraintWidget next = current.getNextChainMember(this.orientation);
        while (next != null) {
            ConstraintWidget current2 = next;
            this.widgets.add(current2.getRun(this.orientation));
            next = current2.getNextChainMember(this.orientation);
        }
        Iterator<WidgetRun> it = this.widgets.iterator();
        while (it.hasNext()) {
            WidgetRun run = it.next();
            if (this.orientation == 0) {
                run.widget.horizontalChainRun = this;
            } else if (this.orientation == 1) {
                run.widget.verticalChainRun = this;
            }
        }
        if ((this.orientation == 0 && ((ConstraintWidgetContainer) this.widget.getParent()).isRtl()) && this.widgets.size() > 1) {
            ArrayList<WidgetRun> arrayList = this.widgets;
            this.widget = arrayList.get(arrayList.size() - 1).widget;
        }
        this.chainStyle = this.orientation == 0 ? this.widget.getHorizontalChainStyle() : this.widget.getVerticalChainStyle();
    }

    /* access modifiers changed from: package-private */
    public void clear() {
        this.runGroup = null;
        Iterator<WidgetRun> it = this.widgets.iterator();
        while (it.hasNext()) {
            it.next().clear();
        }
    }

    /* access modifiers changed from: package-private */
    public void reset() {
        this.start.resolved = false;
        this.end.resolved = false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:56:0x00e4  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00f7  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void update(androidx.constraintlayout.core.widgets.analyzer.Dependency r28) {
        /*
            r27 = this;
            r0 = r27
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r0.start
            boolean r1 = r1.resolved
            if (r1 == 0) goto L_0x048a
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r0.end
            boolean r1 = r1.resolved
            if (r1 != 0) goto L_0x0010
            goto L_0x048a
        L_0x0010:
            androidx.constraintlayout.core.widgets.ConstraintWidget r1 = r0.widget
            androidx.constraintlayout.core.widgets.ConstraintWidget r1 = r1.getParent()
            r2 = 0
            boolean r3 = r1 instanceof androidx.constraintlayout.core.widgets.ConstraintWidgetContainer
            if (r3 == 0) goto L_0x0022
            r3 = r1
            androidx.constraintlayout.core.widgets.ConstraintWidgetContainer r3 = (androidx.constraintlayout.core.widgets.ConstraintWidgetContainer) r3
            boolean r2 = r3.isRtl()
        L_0x0022:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r3 = r0.end
            int r3 = r3.value
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r4 = r0.start
            int r4 = r4.value
            int r3 = r3 - r4
            r4 = 0
            r5 = 0
            r6 = 0
            r7 = 0
            java.util.ArrayList<androidx.constraintlayout.core.widgets.analyzer.WidgetRun> r8 = r0.widgets
            int r8 = r8.size()
            r9 = -1
            r10 = 0
        L_0x0037:
            r11 = 8
            if (r10 >= r8) goto L_0x0051
            java.util.ArrayList<androidx.constraintlayout.core.widgets.analyzer.WidgetRun> r12 = r0.widgets
            java.lang.Object r12 = r12.get(r10)
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r12 = (androidx.constraintlayout.core.widgets.analyzer.WidgetRun) r12
            androidx.constraintlayout.core.widgets.ConstraintWidget r13 = r12.widget
            int r13 = r13.getVisibility()
            if (r13 != r11) goto L_0x004f
            int r10 = r10 + 1
            goto L_0x0037
        L_0x004f:
            r9 = r10
        L_0x0051:
            r10 = -1
            int r12 = r8 + -1
        L_0x0054:
            if (r12 < 0) goto L_0x006c
            java.util.ArrayList<androidx.constraintlayout.core.widgets.analyzer.WidgetRun> r13 = r0.widgets
            java.lang.Object r13 = r13.get(r12)
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r13 = (androidx.constraintlayout.core.widgets.analyzer.WidgetRun) r13
            androidx.constraintlayout.core.widgets.ConstraintWidget r14 = r13.widget
            int r14 = r14.getVisibility()
            if (r14 != r11) goto L_0x006a
            int r12 = r12 + -1
            goto L_0x0054
        L_0x006a:
            r10 = r12
        L_0x006c:
            r12 = 0
        L_0x006d:
            r15 = 2
            if (r12 >= r15) goto L_0x0121
            r17 = 0
            r15 = r17
        L_0x0074:
            if (r15 >= r8) goto L_0x010e
            java.util.ArrayList<androidx.constraintlayout.core.widgets.analyzer.WidgetRun> r13 = r0.widgets
            java.lang.Object r13 = r13.get(r15)
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r13 = (androidx.constraintlayout.core.widgets.analyzer.WidgetRun) r13
            androidx.constraintlayout.core.widgets.ConstraintWidget r14 = r13.widget
            int r14 = r14.getVisibility()
            if (r14 != r11) goto L_0x008a
            r19 = r1
            goto L_0x0106
        L_0x008a:
            int r7 = r7 + 1
            if (r15 <= 0) goto L_0x0095
            if (r15 < r9) goto L_0x0095
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            int r14 = r14.margin
            int r4 = r4 + r14
        L_0x0095:
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r14 = r13.dimension
            int r14 = r14.value
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r11 = r13.dimensionBehavior
            r19 = r1
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r1 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r11 == r1) goto L_0x00a3
            r1 = 1
            goto L_0x00a4
        L_0x00a3:
            r1 = 0
        L_0x00a4:
            if (r1 == 0) goto L_0x00c7
            int r11 = r0.orientation
            if (r11 != 0) goto L_0x00b5
            androidx.constraintlayout.core.widgets.ConstraintWidget r11 = r13.widget
            androidx.constraintlayout.core.widgets.analyzer.HorizontalWidgetRun r11 = r11.horizontalRun
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r11 = r11.dimension
            boolean r11 = r11.resolved
            if (r11 != 0) goto L_0x00b5
            return
        L_0x00b5:
            int r11 = r0.orientation
            r20 = r1
            r1 = 1
            if (r11 != r1) goto L_0x00e0
            androidx.constraintlayout.core.widgets.ConstraintWidget r1 = r13.widget
            androidx.constraintlayout.core.widgets.analyzer.VerticalWidgetRun r1 = r1.verticalRun
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r1 = r1.dimension
            boolean r1 = r1.resolved
            if (r1 != 0) goto L_0x00e0
            return
        L_0x00c7:
            r20 = r1
            int r1 = r13.matchConstraintsType
            r11 = 1
            if (r1 != r11) goto L_0x00d8
            if (r12 != 0) goto L_0x00d8
            r1 = 1
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r11 = r13.dimension
            int r14 = r11.wrapValue
            int r5 = r5 + 1
            goto L_0x00e2
        L_0x00d8:
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r1 = r13.dimension
            boolean r1 = r1.resolved
            if (r1 == 0) goto L_0x00e0
            r1 = 1
            goto L_0x00e2
        L_0x00e0:
            r1 = r20
        L_0x00e2:
            if (r1 != 0) goto L_0x00f7
            int r5 = r5 + 1
            androidx.constraintlayout.core.widgets.ConstraintWidget r11 = r13.widget
            float[] r11 = r11.mWeight
            r20 = r1
            int r1 = r0.orientation
            r1 = r11[r1]
            r11 = 0
            int r21 = (r1 > r11 ? 1 : (r1 == r11 ? 0 : -1))
            if (r21 < 0) goto L_0x00f6
            float r6 = r6 + r1
        L_0x00f6:
            goto L_0x00fa
        L_0x00f7:
            r20 = r1
            int r4 = r4 + r14
        L_0x00fa:
            int r1 = r8 + -1
            if (r15 >= r1) goto L_0x0106
            if (r15 >= r10) goto L_0x0106
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r13.end
            int r1 = r1.margin
            int r1 = -r1
            int r4 = r4 + r1
        L_0x0106:
            int r15 = r15 + 1
            r1 = r19
            r11 = 8
            goto L_0x0074
        L_0x010e:
            r19 = r1
            if (r4 < r3) goto L_0x0123
            if (r5 != 0) goto L_0x0115
            goto L_0x0123
        L_0x0115:
            r7 = 0
            r5 = 0
            r4 = 0
            r6 = 0
            int r12 = r12 + 1
            r1 = r19
            r11 = 8
            goto L_0x006d
        L_0x0121:
            r19 = r1
        L_0x0123:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r0.start
            int r1 = r1.value
            if (r2 == 0) goto L_0x012d
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r11 = r0.end
            int r1 = r11.value
        L_0x012d:
            r11 = 1056964608(0x3f000000, float:0.5)
            if (r4 <= r3) goto L_0x0144
            r12 = 1073741824(0x40000000, float:2.0)
            if (r2 == 0) goto L_0x013d
            int r13 = r4 - r3
            float r13 = (float) r13
            float r13 = r13 / r12
            float r13 = r13 + r11
            int r12 = (int) r13
            int r1 = r1 + r12
            goto L_0x0144
        L_0x013d:
            int r13 = r4 - r3
            float r13 = (float) r13
            float r13 = r13 / r12
            float r13 = r13 + r11
            int r12 = (int) r13
            int r1 = r1 - r12
        L_0x0144:
            r12 = 0
            if (r5 <= 0) goto L_0x0255
            int r13 = r3 - r4
            float r13 = (float) r13
            float r14 = (float) r5
            float r13 = r13 / r14
            float r13 = r13 + r11
            int r12 = (int) r13
            r13 = 0
            r14 = 0
        L_0x0150:
            if (r14 >= r8) goto L_0x0203
            java.util.ArrayList<androidx.constraintlayout.core.widgets.analyzer.WidgetRun> r15 = r0.widgets
            java.lang.Object r15 = r15.get(r14)
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r15 = (androidx.constraintlayout.core.widgets.analyzer.WidgetRun) r15
            androidx.constraintlayout.core.widgets.ConstraintWidget r11 = r15.widget
            int r11 = r11.getVisibility()
            r21 = r1
            r1 = 8
            if (r11 != r1) goto L_0x0170
            r25 = r2
            r22 = r4
            r23 = r6
            r24 = r12
            goto L_0x01f3
        L_0x0170:
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r1 = r15.dimensionBehavior
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r11 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r1 != r11) goto L_0x01eb
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r1 = r15.dimension
            boolean r1 = r1.resolved
            if (r1 != 0) goto L_0x01eb
            r1 = r12
            r11 = 0
            int r18 = (r6 > r11 ? 1 : (r6 == r11 ? 0 : -1))
            if (r18 <= 0) goto L_0x0199
            androidx.constraintlayout.core.widgets.ConstraintWidget r11 = r15.widget
            float[] r11 = r11.mWeight
            r22 = r1
            int r1 = r0.orientation
            r1 = r11[r1]
            int r11 = r3 - r4
            float r11 = (float) r11
            float r11 = r11 * r1
            float r11 = r11 / r6
            r20 = 1056964608(0x3f000000, float:0.5)
            float r11 = r11 + r20
            int r11 = (int) r11
            r1 = r11
            goto L_0x019b
        L_0x0199:
            r22 = r1
        L_0x019b:
            r11 = r1
            r22 = r4
            int r4 = r0.orientation
            if (r4 != 0) goto L_0x01b4
            androidx.constraintlayout.core.widgets.ConstraintWidget r4 = r15.widget
            int r4 = r4.mMatchConstraintMaxWidth
            r23 = r4
            androidx.constraintlayout.core.widgets.ConstraintWidget r4 = r15.widget
            int r4 = r4.mMatchConstraintMinWidth
            r26 = r6
            r6 = r4
            r4 = r23
            r23 = r26
            goto L_0x01c5
        L_0x01b4:
            androidx.constraintlayout.core.widgets.ConstraintWidget r4 = r15.widget
            int r4 = r4.mMatchConstraintMaxHeight
            r23 = r4
            androidx.constraintlayout.core.widgets.ConstraintWidget r4 = r15.widget
            int r4 = r4.mMatchConstraintMinHeight
            r26 = r6
            r6 = r4
            r4 = r23
            r23 = r26
        L_0x01c5:
            r24 = r12
            int r12 = r15.matchConstraintsType
            r25 = r2
            r2 = 1
            if (r12 != r2) goto L_0x01d6
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r2 = r15.dimension
            int r2 = r2.wrapValue
            int r11 = java.lang.Math.min(r11, r2)
        L_0x01d6:
            int r2 = java.lang.Math.max(r6, r11)
            if (r4 <= 0) goto L_0x01e0
            int r2 = java.lang.Math.min(r4, r2)
        L_0x01e0:
            if (r2 == r1) goto L_0x01e5
            int r13 = r13 + 1
            r1 = r2
        L_0x01e5:
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r11 = r15.dimension
            r11.resolve(r1)
            goto L_0x01f3
        L_0x01eb:
            r25 = r2
            r22 = r4
            r23 = r6
            r24 = r12
        L_0x01f3:
            int r14 = r14 + 1
            r1 = r21
            r4 = r22
            r6 = r23
            r12 = r24
            r2 = r25
            r11 = 1056964608(0x3f000000, float:0.5)
            goto L_0x0150
        L_0x0203:
            r21 = r1
            r25 = r2
            r22 = r4
            r23 = r6
            r24 = r12
            if (r13 <= 0) goto L_0x0246
            int r5 = r5 - r13
            r1 = 0
            r2 = 0
        L_0x0212:
            if (r2 >= r8) goto L_0x0244
            java.util.ArrayList<androidx.constraintlayout.core.widgets.analyzer.WidgetRun> r4 = r0.widgets
            java.lang.Object r4 = r4.get(r2)
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r4 = (androidx.constraintlayout.core.widgets.analyzer.WidgetRun) r4
            androidx.constraintlayout.core.widgets.ConstraintWidget r6 = r4.widget
            int r6 = r6.getVisibility()
            r11 = 8
            if (r6 != r11) goto L_0x0227
            goto L_0x0241
        L_0x0227:
            if (r2 <= 0) goto L_0x0230
            if (r2 < r9) goto L_0x0230
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r6 = r4.start
            int r6 = r6.margin
            int r1 = r1 + r6
        L_0x0230:
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r6 = r4.dimension
            int r6 = r6.value
            int r1 = r1 + r6
            int r6 = r8 + -1
            if (r2 >= r6) goto L_0x0241
            if (r2 >= r10) goto L_0x0241
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r6 = r4.end
            int r6 = r6.margin
            int r6 = -r6
            int r1 = r1 + r6
        L_0x0241:
            int r2 = r2 + 1
            goto L_0x0212
        L_0x0244:
            r4 = r1
            goto L_0x0248
        L_0x0246:
            r4 = r22
        L_0x0248:
            int r1 = r0.chainStyle
            r2 = 2
            if (r1 != r2) goto L_0x0252
            if (r13 != 0) goto L_0x0252
            r1 = 0
            r0.chainStyle = r1
        L_0x0252:
            r12 = r24
            goto L_0x025d
        L_0x0255:
            r21 = r1
            r25 = r2
            r22 = r4
            r23 = r6
        L_0x025d:
            if (r4 <= r3) goto L_0x0263
            r1 = 2
            r0.chainStyle = r1
            goto L_0x0264
        L_0x0263:
            r1 = 2
        L_0x0264:
            if (r7 <= 0) goto L_0x026c
            if (r5 != 0) goto L_0x026c
            if (r9 != r10) goto L_0x026c
            r0.chainStyle = r1
        L_0x026c:
            int r1 = r0.chainStyle
            r2 = 1
            if (r1 != r2) goto L_0x0321
            r1 = 0
            if (r7 <= r2) goto L_0x027b
            int r6 = r3 - r4
            int r11 = r7 + -1
            int r1 = r6 / r11
            goto L_0x0282
        L_0x027b:
            if (r7 != r2) goto L_0x0282
            int r2 = r3 - r4
            r6 = 2
            int r1 = r2 / 2
        L_0x0282:
            if (r5 <= 0) goto L_0x0285
            r1 = 0
        L_0x0285:
            r2 = 0
            r6 = r2
            r2 = r21
        L_0x0289:
            if (r6 >= r8) goto L_0x031c
            r11 = r6
            if (r25 == 0) goto L_0x0292
            int r13 = r6 + 1
            int r11 = r8 - r13
        L_0x0292:
            java.util.ArrayList<androidx.constraintlayout.core.widgets.analyzer.WidgetRun> r13 = r0.widgets
            java.lang.Object r13 = r13.get(r11)
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r13 = (androidx.constraintlayout.core.widgets.analyzer.WidgetRun) r13
            androidx.constraintlayout.core.widgets.ConstraintWidget r14 = r13.widget
            int r14 = r14.getVisibility()
            r15 = 8
            if (r14 != r15) goto L_0x02b2
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            r14.resolve(r2)
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.end
            r14.resolve(r2)
            r16 = r1
            goto L_0x0316
        L_0x02b2:
            if (r6 <= 0) goto L_0x02b9
            if (r25 == 0) goto L_0x02b8
            int r2 = r2 - r1
            goto L_0x02b9
        L_0x02b8:
            int r2 = r2 + r1
        L_0x02b9:
            if (r6 <= 0) goto L_0x02ca
            if (r6 < r9) goto L_0x02ca
            if (r25 == 0) goto L_0x02c5
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            int r14 = r14.margin
            int r2 = r2 - r14
            goto L_0x02ca
        L_0x02c5:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            int r14 = r14.margin
            int r2 = r2 + r14
        L_0x02ca:
            if (r25 == 0) goto L_0x02d2
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.end
            r14.resolve(r2)
            goto L_0x02d7
        L_0x02d2:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            r14.resolve(r2)
        L_0x02d7:
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r14 = r13.dimension
            int r14 = r14.value
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r15 = r13.dimensionBehavior
            r16 = r1
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r1 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r15 != r1) goto L_0x02ec
            int r1 = r13.matchConstraintsType
            r15 = 1
            if (r1 != r15) goto L_0x02ec
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r1 = r13.dimension
            int r14 = r1.wrapValue
        L_0x02ec:
            if (r25 == 0) goto L_0x02f0
            int r2 = r2 - r14
            goto L_0x02f1
        L_0x02f0:
            int r2 = r2 + r14
        L_0x02f1:
            if (r25 == 0) goto L_0x02f9
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r13.start
            r1.resolve(r2)
            goto L_0x02fe
        L_0x02f9:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r13.end
            r1.resolve(r2)
        L_0x02fe:
            r1 = 1
            r13.resolved = r1
            int r1 = r8 + -1
            if (r6 >= r1) goto L_0x0316
            if (r6 >= r10) goto L_0x0316
            if (r25 == 0) goto L_0x0310
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r13.end
            int r1 = r1.margin
            int r1 = -r1
            int r2 = r2 - r1
            goto L_0x0316
        L_0x0310:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r13.end
            int r1 = r1.margin
            int r1 = -r1
            int r2 = r2 + r1
        L_0x0316:
            int r6 = r6 + 1
            r1 = r16
            goto L_0x0289
        L_0x031c:
            r16 = r1
            r1 = r2
            goto L_0x0489
        L_0x0321:
            if (r1 != 0) goto L_0x03c5
            int r1 = r3 - r4
            int r2 = r7 + 1
            int r1 = r1 / r2
            if (r5 <= 0) goto L_0x032b
            r1 = 0
        L_0x032b:
            r2 = 0
            r6 = r2
            r2 = r21
        L_0x032f:
            if (r6 >= r8) goto L_0x03c0
            r11 = r6
            if (r25 == 0) goto L_0x0338
            int r13 = r6 + 1
            int r11 = r8 - r13
        L_0x0338:
            java.util.ArrayList<androidx.constraintlayout.core.widgets.analyzer.WidgetRun> r13 = r0.widgets
            java.lang.Object r13 = r13.get(r11)
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r13 = (androidx.constraintlayout.core.widgets.analyzer.WidgetRun) r13
            androidx.constraintlayout.core.widgets.ConstraintWidget r14 = r13.widget
            int r14 = r14.getVisibility()
            r15 = 8
            if (r14 != r15) goto L_0x0357
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            r14.resolve(r2)
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.end
            r14.resolve(r2)
            r16 = r1
            goto L_0x03ba
        L_0x0357:
            if (r25 == 0) goto L_0x035b
            int r2 = r2 - r1
            goto L_0x035c
        L_0x035b:
            int r2 = r2 + r1
        L_0x035c:
            if (r6 <= 0) goto L_0x036d
            if (r6 < r9) goto L_0x036d
            if (r25 == 0) goto L_0x0368
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            int r14 = r14.margin
            int r2 = r2 - r14
            goto L_0x036d
        L_0x0368:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            int r14 = r14.margin
            int r2 = r2 + r14
        L_0x036d:
            if (r25 == 0) goto L_0x0375
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.end
            r14.resolve(r2)
            goto L_0x037a
        L_0x0375:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r14 = r13.start
            r14.resolve(r2)
        L_0x037a:
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r14 = r13.dimension
            int r14 = r14.value
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r15 = r13.dimensionBehavior
            r16 = r1
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r1 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r15 != r1) goto L_0x0393
            int r1 = r13.matchConstraintsType
            r15 = 1
            if (r1 != r15) goto L_0x0393
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r1 = r13.dimension
            int r1 = r1.wrapValue
            int r14 = java.lang.Math.min(r14, r1)
        L_0x0393:
            if (r25 == 0) goto L_0x0397
            int r2 = r2 - r14
            goto L_0x0398
        L_0x0397:
            int r2 = r2 + r14
        L_0x0398:
            if (r25 == 0) goto L_0x03a0
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r13.start
            r1.resolve(r2)
            goto L_0x03a5
        L_0x03a0:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r13.end
            r1.resolve(r2)
        L_0x03a5:
            int r1 = r8 + -1
            if (r6 >= r1) goto L_0x03ba
            if (r6 >= r10) goto L_0x03ba
            if (r25 == 0) goto L_0x03b4
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r13.end
            int r1 = r1.margin
            int r1 = -r1
            int r2 = r2 - r1
            goto L_0x03ba
        L_0x03b4:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r1 = r13.end
            int r1 = r1.margin
            int r1 = -r1
            int r2 = r2 + r1
        L_0x03ba:
            int r6 = r6 + 1
            r1 = r16
            goto L_0x032f
        L_0x03c0:
            r16 = r1
            r1 = r2
            goto L_0x0489
        L_0x03c5:
            r2 = 2
            if (r1 != r2) goto L_0x0487
            int r1 = r0.orientation
            if (r1 != 0) goto L_0x03d3
            androidx.constraintlayout.core.widgets.ConstraintWidget r1 = r0.widget
            float r1 = r1.getHorizontalBiasPercent()
            goto L_0x03d9
        L_0x03d3:
            androidx.constraintlayout.core.widgets.ConstraintWidget r1 = r0.widget
            float r1 = r1.getVerticalBiasPercent()
        L_0x03d9:
            if (r25 == 0) goto L_0x03e0
            r2 = 1065353216(0x3f800000, float:1.0)
            float r1 = r2 - r1
        L_0x03e0:
            int r2 = r3 - r4
            float r2 = (float) r2
            float r2 = r2 * r1
            r6 = 1056964608(0x3f000000, float:0.5)
            float r2 = r2 + r6
            int r2 = (int) r2
            if (r2 < 0) goto L_0x03ed
            if (r5 <= 0) goto L_0x03ee
        L_0x03ed:
            r2 = 0
        L_0x03ee:
            if (r25 == 0) goto L_0x03f3
            int r6 = r21 - r2
            goto L_0x03f5
        L_0x03f3:
            int r6 = r21 + r2
        L_0x03f5:
            r11 = 0
        L_0x03f6:
            if (r11 >= r8) goto L_0x0483
            r13 = r11
            if (r25 == 0) goto L_0x03ff
            int r14 = r11 + 1
            int r13 = r8 - r14
        L_0x03ff:
            java.util.ArrayList<androidx.constraintlayout.core.widgets.analyzer.WidgetRun> r14 = r0.widgets
            java.lang.Object r14 = r14.get(r13)
            androidx.constraintlayout.core.widgets.analyzer.WidgetRun r14 = (androidx.constraintlayout.core.widgets.analyzer.WidgetRun) r14
            androidx.constraintlayout.core.widgets.ConstraintWidget r15 = r14.widget
            int r15 = r15.getVisibility()
            r0 = 8
            if (r15 != r0) goto L_0x041f
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r15 = r14.start
            r15.resolve(r6)
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r15 = r14.end
            r15.resolve(r6)
            r16 = r1
            r1 = 1
            goto L_0x047b
        L_0x041f:
            if (r11 <= 0) goto L_0x0430
            if (r11 < r9) goto L_0x0430
            if (r25 == 0) goto L_0x042b
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r15 = r14.start
            int r15 = r15.margin
            int r6 = r6 - r15
            goto L_0x0430
        L_0x042b:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r15 = r14.start
            int r15 = r15.margin
            int r6 = r6 + r15
        L_0x0430:
            if (r25 == 0) goto L_0x0438
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r15 = r14.end
            r15.resolve(r6)
            goto L_0x043d
        L_0x0438:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r15 = r14.start
            r15.resolve(r6)
        L_0x043d:
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r15 = r14.dimension
            int r15 = r15.value
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r0 = r14.dimensionBehavior
            r16 = r1
            androidx.constraintlayout.core.widgets.ConstraintWidget$DimensionBehaviour r1 = androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r0 != r1) goto L_0x0453
            int r0 = r14.matchConstraintsType
            r1 = 1
            if (r0 != r1) goto L_0x0454
            androidx.constraintlayout.core.widgets.analyzer.DimensionDependency r0 = r14.dimension
            int r15 = r0.wrapValue
            goto L_0x0454
        L_0x0453:
            r1 = 1
        L_0x0454:
            if (r25 == 0) goto L_0x0458
            int r6 = r6 - r15
            goto L_0x0459
        L_0x0458:
            int r6 = r6 + r15
        L_0x0459:
            if (r25 == 0) goto L_0x0461
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r14.start
            r0.resolve(r6)
            goto L_0x0466
        L_0x0461:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r14.end
            r0.resolve(r6)
        L_0x0466:
            int r0 = r8 + -1
            if (r11 >= r0) goto L_0x047b
            if (r11 >= r10) goto L_0x047b
            if (r25 == 0) goto L_0x0475
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r14.end
            int r0 = r0.margin
            int r0 = -r0
            int r6 = r6 - r0
            goto L_0x047b
        L_0x0475:
            androidx.constraintlayout.core.widgets.analyzer.DependencyNode r0 = r14.end
            int r0 = r0.margin
            int r0 = -r0
            int r6 = r6 + r0
        L_0x047b:
            int r11 = r11 + 1
            r0 = r27
            r1 = r16
            goto L_0x03f6
        L_0x0483:
            r16 = r1
            r1 = r6
            goto L_0x0489
        L_0x0487:
            r1 = r21
        L_0x0489:
            return
        L_0x048a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.core.widgets.analyzer.ChainRun.update(androidx.constraintlayout.core.widgets.analyzer.Dependency):void");
    }

    public void applyToWidget() {
        for (int i = 0; i < this.widgets.size(); i++) {
            this.widgets.get(i).applyToWidget();
        }
    }

    private ConstraintWidget getFirstVisibleWidget() {
        for (int i = 0; i < this.widgets.size(); i++) {
            WidgetRun run = this.widgets.get(i);
            if (run.widget.getVisibility() != 8) {
                return run.widget;
            }
        }
        return null;
    }

    private ConstraintWidget getLastVisibleWidget() {
        for (int i = this.widgets.size() - 1; i >= 0; i--) {
            WidgetRun run = this.widgets.get(i);
            if (run.widget.getVisibility() != 8) {
                return run.widget;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void apply() {
        Iterator<WidgetRun> it = this.widgets.iterator();
        while (it.hasNext()) {
            it.next().apply();
        }
        int count = this.widgets.size();
        if (count >= 1) {
            ConstraintWidget firstWidget = this.widgets.get(0).widget;
            ConstraintWidget lastWidget = this.widgets.get(count - 1).widget;
            if (this.orientation == 0) {
                ConstraintAnchor startAnchor = firstWidget.mLeft;
                ConstraintAnchor endAnchor = lastWidget.mRight;
                DependencyNode startTarget = getTarget(startAnchor, 0);
                int startMargin = startAnchor.getMargin();
                ConstraintWidget firstVisibleWidget = getFirstVisibleWidget();
                if (firstVisibleWidget != null) {
                    startMargin = firstVisibleWidget.mLeft.getMargin();
                }
                if (startTarget != null) {
                    addTarget(this.start, startTarget, startMargin);
                }
                DependencyNode endTarget = getTarget(endAnchor, 0);
                int endMargin = endAnchor.getMargin();
                ConstraintWidget lastVisibleWidget = getLastVisibleWidget();
                if (lastVisibleWidget != null) {
                    endMargin = lastVisibleWidget.mRight.getMargin();
                }
                if (endTarget != null) {
                    addTarget(this.end, endTarget, -endMargin);
                }
            } else {
                ConstraintAnchor startAnchor2 = firstWidget.mTop;
                ConstraintAnchor endAnchor2 = lastWidget.mBottom;
                DependencyNode startTarget2 = getTarget(startAnchor2, 1);
                int startMargin2 = startAnchor2.getMargin();
                ConstraintWidget firstVisibleWidget2 = getFirstVisibleWidget();
                if (firstVisibleWidget2 != null) {
                    startMargin2 = firstVisibleWidget2.mTop.getMargin();
                }
                if (startTarget2 != null) {
                    addTarget(this.start, startTarget2, startMargin2);
                }
                DependencyNode endTarget2 = getTarget(endAnchor2, 1);
                int endMargin2 = endAnchor2.getMargin();
                ConstraintWidget lastVisibleWidget2 = getLastVisibleWidget();
                if (lastVisibleWidget2 != null) {
                    endMargin2 = lastVisibleWidget2.mBottom.getMargin();
                }
                if (endTarget2 != null) {
                    addTarget(this.end, endTarget2, -endMargin2);
                }
            }
            this.start.updateDelegate = this;
            this.end.updateDelegate = this;
        }
    }
}
