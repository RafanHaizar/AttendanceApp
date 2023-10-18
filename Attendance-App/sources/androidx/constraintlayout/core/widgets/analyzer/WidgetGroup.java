package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.LinearSystem;
import androidx.constraintlayout.core.widgets.Chain;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

public class WidgetGroup {
    private static final boolean DEBUG = false;
    static int count = 0;
    boolean authoritative = false;

    /* renamed from: id */
    int f1013id = -1;
    private int moveTo = -1;
    int orientation = 0;
    ArrayList<MeasureResult> results = null;
    ArrayList<ConstraintWidget> widgets = new ArrayList<>();

    public WidgetGroup(int orientation2) {
        int i = count;
        count = i + 1;
        this.f1013id = i;
        this.orientation = orientation2;
    }

    public int getOrientation() {
        return this.orientation;
    }

    public int getId() {
        return this.f1013id;
    }

    public boolean add(ConstraintWidget widget) {
        if (this.widgets.contains(widget)) {
            return false;
        }
        this.widgets.add(widget);
        return true;
    }

    public void setAuthoritative(boolean isAuthoritative) {
        this.authoritative = isAuthoritative;
    }

    public boolean isAuthoritative() {
        return this.authoritative;
    }

    private String getOrientationString() {
        int i = this.orientation;
        if (i == 0) {
            return "Horizontal";
        }
        if (i == 1) {
            return "Vertical";
        }
        if (i == 2) {
            return "Both";
        }
        return "Unknown";
    }

    public String toString() {
        String ret = getOrientationString() + " [" + this.f1013id + "] <";
        Iterator<ConstraintWidget> it = this.widgets.iterator();
        while (it.hasNext()) {
            ret = ret + " " + it.next().getDebugName();
        }
        return ret + " >";
    }

    public void moveTo(int orientation2, WidgetGroup widgetGroup) {
        Iterator<ConstraintWidget> it = this.widgets.iterator();
        while (it.hasNext()) {
            ConstraintWidget widget = it.next();
            widgetGroup.add(widget);
            if (orientation2 == 0) {
                widget.horizontalGroup = widgetGroup.getId();
            } else {
                widget.verticalGroup = widgetGroup.getId();
            }
        }
        this.moveTo = widgetGroup.f1013id;
    }

    public void clear() {
        this.widgets.clear();
    }

    private int measureWrap(int orientation2, ConstraintWidget widget) {
        ConstraintWidget.DimensionBehaviour behaviour = widget.getDimensionBehaviour(orientation2);
        if (behaviour != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && behaviour != ConstraintWidget.DimensionBehaviour.MATCH_PARENT && behaviour != ConstraintWidget.DimensionBehaviour.FIXED) {
            return -1;
        }
        if (orientation2 == 0) {
            return widget.getWidth();
        }
        return widget.getHeight();
    }

    public int measureWrap(LinearSystem system, int orientation2) {
        if (this.widgets.size() == 0) {
            return 0;
        }
        return solverMeasure(system, this.widgets, orientation2);
    }

    private int solverMeasure(LinearSystem system, ArrayList<ConstraintWidget> widgets2, int orientation2) {
        ConstraintWidgetContainer container = (ConstraintWidgetContainer) widgets2.get(0).getParent();
        system.reset();
        container.addToSolver(system, false);
        for (int i = 0; i < widgets2.size(); i++) {
            widgets2.get(i).addToSolver(system, false);
        }
        if (orientation2 == 0 && container.mHorizontalChainsSize > 0) {
            Chain.applyChainConstraints(container, system, widgets2, 0);
        }
        if (orientation2 == 1 && container.mVerticalChainsSize > 0) {
            Chain.applyChainConstraints(container, system, widgets2, 1);
        }
        try {
            system.minimize();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.results = new ArrayList<>();
        for (int i2 = 0; i2 < widgets2.size(); i2++) {
            this.results.add(new MeasureResult(widgets2.get(i2), system, orientation2));
        }
        if (orientation2 == 0) {
            int left = system.getObjectVariableValue(container.mLeft);
            int right = system.getObjectVariableValue(container.mRight);
            system.reset();
            return right - left;
        }
        int top = system.getObjectVariableValue(container.mTop);
        int bottom = system.getObjectVariableValue(container.mBottom);
        system.reset();
        return bottom - top;
    }

    public void setOrientation(int orientation2) {
        this.orientation = orientation2;
    }

    public void apply() {
        if (this.results != null && this.authoritative) {
            for (int i = 0; i < this.results.size(); i++) {
                this.results.get(i).apply();
            }
        }
    }

    public boolean intersectWith(WidgetGroup group) {
        for (int i = 0; i < this.widgets.size(); i++) {
            if (group.contains(this.widgets.get(i))) {
                return true;
            }
        }
        return false;
    }

    private boolean contains(ConstraintWidget widget) {
        return this.widgets.contains(widget);
    }

    public int size() {
        return this.widgets.size();
    }

    public void cleanup(ArrayList<WidgetGroup> dependencyLists) {
        int count2 = this.widgets.size();
        if (this.moveTo != -1 && count2 > 0) {
            for (int i = 0; i < dependencyLists.size(); i++) {
                WidgetGroup group = dependencyLists.get(i);
                if (this.moveTo == group.f1013id) {
                    moveTo(this.orientation, group);
                }
            }
        }
        if (count2 == 0) {
            dependencyLists.remove(this);
        }
    }

    class MeasureResult {
        int baseline;
        int bottom;
        int left;
        int orientation;
        int right;
        int top;
        WeakReference<ConstraintWidget> widgetRef;

        public MeasureResult(ConstraintWidget widget, LinearSystem system, int orientation2) {
            this.widgetRef = new WeakReference<>(widget);
            this.left = system.getObjectVariableValue(widget.mLeft);
            this.top = system.getObjectVariableValue(widget.mTop);
            this.right = system.getObjectVariableValue(widget.mRight);
            this.bottom = system.getObjectVariableValue(widget.mBottom);
            this.baseline = system.getObjectVariableValue(widget.mBaseline);
            this.orientation = orientation2;
        }

        public void apply() {
            ConstraintWidget widget = (ConstraintWidget) this.widgetRef.get();
            if (widget != null) {
                widget.setFinalFrame(this.left, this.top, this.right, this.bottom, this.baseline, this.orientation);
            }
        }
    }
}
