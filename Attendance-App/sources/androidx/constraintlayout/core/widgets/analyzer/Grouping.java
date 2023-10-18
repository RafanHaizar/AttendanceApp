package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.Barrier;
import androidx.constraintlayout.core.widgets.ConstraintAnchor;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.core.widgets.Flow;
import androidx.constraintlayout.core.widgets.Guideline;
import androidx.constraintlayout.core.widgets.HelperWidget;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import java.util.ArrayList;
import java.util.Iterator;

public class Grouping {
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_GROUPING = false;

    public static boolean validInGroup(ConstraintWidget.DimensionBehaviour layoutHorizontal, ConstraintWidget.DimensionBehaviour layoutVertical, ConstraintWidget.DimensionBehaviour widgetHorizontal, ConstraintWidget.DimensionBehaviour widgetVertical) {
        return (widgetHorizontal == ConstraintWidget.DimensionBehaviour.FIXED || widgetHorizontal == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || (widgetHorizontal == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && layoutHorizontal != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT)) || (widgetVertical == ConstraintWidget.DimensionBehaviour.FIXED || widgetVertical == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT || (widgetVertical == ConstraintWidget.DimensionBehaviour.MATCH_PARENT && layoutVertical != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT));
    }

    public static boolean simpleSolvingPass(ConstraintWidgetContainer layout, BasicMeasure.Measurer measurer) {
        ConstraintWidgetContainer constraintWidgetContainer = layout;
        ArrayList<ConstraintWidget> children = layout.getChildren();
        int count = children.size();
        ArrayList<Guideline> verticalGuidelines = null;
        ArrayList<Guideline> horizontalGuidelines = null;
        ArrayList<HelperWidget> horizontalBarriers = null;
        ArrayList<HelperWidget> verticalBarriers = null;
        ArrayList<ConstraintWidget> isolatedHorizontalChildren = null;
        ArrayList<ConstraintWidget> isolatedVerticalChildren = null;
        for (int i = 0; i < count; i++) {
            ConstraintWidget child = children.get(i);
            if (!validInGroup(layout.getHorizontalDimensionBehaviour(), layout.getVerticalDimensionBehaviour(), child.getHorizontalDimensionBehaviour(), child.getVerticalDimensionBehaviour()) || (child instanceof Flow)) {
                return false;
            }
        }
        if (constraintWidgetContainer.mMetrics != null) {
            constraintWidgetContainer.mMetrics.grouping++;
        }
        for (int i2 = 0; i2 < count; i2++) {
            ConstraintWidget child2 = children.get(i2);
            if (!validInGroup(layout.getHorizontalDimensionBehaviour(), layout.getVerticalDimensionBehaviour(), child2.getHorizontalDimensionBehaviour(), child2.getVerticalDimensionBehaviour())) {
                ConstraintWidgetContainer.measure(0, child2, measurer, constraintWidgetContainer.mMeasure, BasicMeasure.Measure.SELF_DIMENSIONS);
            } else {
                BasicMeasure.Measurer measurer2 = measurer;
            }
            if (child2 instanceof Guideline) {
                Guideline guideline = (Guideline) child2;
                if (guideline.getOrientation() == 0) {
                    if (horizontalGuidelines == null) {
                        horizontalGuidelines = new ArrayList<>();
                    }
                    horizontalGuidelines.add(guideline);
                }
                if (guideline.getOrientation() == 1) {
                    if (verticalGuidelines == null) {
                        verticalGuidelines = new ArrayList<>();
                    }
                    verticalGuidelines.add(guideline);
                }
            }
            if (child2 instanceof HelperWidget) {
                if (child2 instanceof Barrier) {
                    Barrier barrier = (Barrier) child2;
                    if (barrier.getOrientation() == 0) {
                        if (horizontalBarriers == null) {
                            horizontalBarriers = new ArrayList<>();
                        }
                        horizontalBarriers.add(barrier);
                    }
                    if (barrier.getOrientation() == 1) {
                        if (verticalBarriers == null) {
                            verticalBarriers = new ArrayList<>();
                        }
                        verticalBarriers.add(barrier);
                    }
                } else {
                    HelperWidget helper = (HelperWidget) child2;
                    if (horizontalBarriers == null) {
                        horizontalBarriers = new ArrayList<>();
                    }
                    horizontalBarriers.add(helper);
                    if (verticalBarriers == null) {
                        verticalBarriers = new ArrayList<>();
                    }
                    verticalBarriers.add(helper);
                }
            }
            if (child2.mLeft.mTarget == null && child2.mRight.mTarget == null && !(child2 instanceof Guideline) && !(child2 instanceof Barrier)) {
                if (isolatedHorizontalChildren == null) {
                    isolatedHorizontalChildren = new ArrayList<>();
                }
                isolatedHorizontalChildren.add(child2);
            }
            if (child2.mTop.mTarget == null && child2.mBottom.mTarget == null && child2.mBaseline.mTarget == null && !(child2 instanceof Guideline) && !(child2 instanceof Barrier)) {
                if (isolatedVerticalChildren == null) {
                    isolatedVerticalChildren = new ArrayList<>();
                }
                isolatedVerticalChildren.add(child2);
            }
        }
        BasicMeasure.Measurer measurer3 = measurer;
        ArrayList<WidgetGroup> allDependencyLists = new ArrayList<>();
        ArrayList<WidgetGroup> dependencyLists = allDependencyLists;
        if (verticalGuidelines != null) {
            Iterator<Guideline> it = verticalGuidelines.iterator();
            while (it.hasNext()) {
                findDependents(it.next(), 0, dependencyLists, (WidgetGroup) null);
            }
        }
        if (horizontalBarriers != null) {
            Iterator<HelperWidget> it2 = horizontalBarriers.iterator();
            while (it2.hasNext()) {
                HelperWidget barrier2 = it2.next();
                ArrayList<Guideline> verticalGuidelines2 = verticalGuidelines;
                WidgetGroup group = findDependents(barrier2, 0, dependencyLists, (WidgetGroup) null);
                barrier2.addDependents(dependencyLists, 0, group);
                group.cleanup(dependencyLists);
                verticalGuidelines = verticalGuidelines2;
            }
        }
        ConstraintAnchor left = constraintWidgetContainer.getAnchor(ConstraintAnchor.Type.LEFT);
        if (left.getDependents() != null) {
            Iterator<ConstraintAnchor> it3 = left.getDependents().iterator();
            while (it3.hasNext()) {
                findDependents(it3.next().mOwner, 0, dependencyLists, (WidgetGroup) null);
                left = left;
            }
        }
        ConstraintAnchor right = constraintWidgetContainer.getAnchor(ConstraintAnchor.Type.RIGHT);
        if (right.getDependents() != null) {
            Iterator<ConstraintAnchor> it4 = right.getDependents().iterator();
            while (it4.hasNext()) {
                findDependents(it4.next().mOwner, 0, dependencyLists, (WidgetGroup) null);
                right = right;
            }
        }
        ConstraintAnchor center = constraintWidgetContainer.getAnchor(ConstraintAnchor.Type.CENTER);
        if (center.getDependents() != null) {
            Iterator<ConstraintAnchor> it5 = center.getDependents().iterator();
            while (it5.hasNext()) {
                findDependents(it5.next().mOwner, 0, dependencyLists, (WidgetGroup) null);
                center = center;
            }
        }
        if (isolatedHorizontalChildren != null) {
            Iterator<ConstraintWidget> it6 = isolatedHorizontalChildren.iterator();
            while (it6.hasNext()) {
                findDependents(it6.next(), 0, dependencyLists, (WidgetGroup) null);
            }
        }
        ArrayList<WidgetGroup> dependencyLists2 = allDependencyLists;
        if (horizontalGuidelines != null) {
            Iterator<Guideline> it7 = horizontalGuidelines.iterator();
            while (it7.hasNext()) {
                findDependents(it7.next(), 1, dependencyLists2, (WidgetGroup) null);
            }
        }
        if (verticalBarriers != null) {
            Iterator<HelperWidget> it8 = verticalBarriers.iterator();
            while (it8.hasNext()) {
                HelperWidget barrier3 = it8.next();
                WidgetGroup group2 = findDependents(barrier3, 1, dependencyLists2, (WidgetGroup) null);
                barrier3.addDependents(dependencyLists2, 1, group2);
                group2.cleanup(dependencyLists2);
            }
        }
        ConstraintAnchor top = constraintWidgetContainer.getAnchor(ConstraintAnchor.Type.TOP);
        if (top.getDependents() != null) {
            Iterator<ConstraintAnchor> it9 = top.getDependents().iterator();
            while (it9.hasNext()) {
                findDependents(it9.next().mOwner, 1, dependencyLists2, (WidgetGroup) null);
                horizontalGuidelines = horizontalGuidelines;
            }
        }
        ConstraintAnchor baseline = constraintWidgetContainer.getAnchor(ConstraintAnchor.Type.BASELINE);
        if (baseline.getDependents() != null) {
            Iterator<ConstraintAnchor> it10 = baseline.getDependents().iterator();
            while (it10.hasNext()) {
                findDependents(it10.next().mOwner, 1, dependencyLists2, (WidgetGroup) null);
                baseline = baseline;
            }
        }
        ConstraintAnchor bottom = constraintWidgetContainer.getAnchor(ConstraintAnchor.Type.BOTTOM);
        if (bottom.getDependents() != null) {
            Iterator<ConstraintAnchor> it11 = bottom.getDependents().iterator();
            while (it11.hasNext()) {
                findDependents(it11.next().mOwner, 1, dependencyLists2, (WidgetGroup) null);
                bottom = bottom;
            }
        }
        ConstraintAnchor center2 = constraintWidgetContainer.getAnchor(ConstraintAnchor.Type.CENTER);
        if (center2.getDependents() != null) {
            Iterator<ConstraintAnchor> it12 = center2.getDependents().iterator();
            while (it12.hasNext()) {
                findDependents(it12.next().mOwner, 1, dependencyLists2, (WidgetGroup) null);
                center2 = center2;
            }
        }
        if (isolatedVerticalChildren != null) {
            Iterator<ConstraintWidget> it13 = isolatedVerticalChildren.iterator();
            while (it13.hasNext()) {
                findDependents(it13.next(), 1, dependencyLists2, (WidgetGroup) null);
            }
        }
        for (int i3 = 0; i3 < count; i3++) {
            ConstraintWidget child3 = children.get(i3);
            if (child3.oppositeDimensionsTied()) {
                WidgetGroup horizontalGroup = findGroup(allDependencyLists, child3.horizontalGroup);
                WidgetGroup verticalGroup = findGroup(allDependencyLists, child3.verticalGroup);
                if (!(horizontalGroup == null || verticalGroup == null)) {
                    horizontalGroup.moveTo(0, verticalGroup);
                    verticalGroup.setOrientation(2);
                    allDependencyLists.remove(horizontalGroup);
                }
            }
        }
        if (allDependencyLists.size() <= 1) {
            return false;
        }
        WidgetGroup horizontalPick = null;
        WidgetGroup verticalPick = null;
        if (layout.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
            int maxWrap = 0;
            WidgetGroup picked = null;
            Iterator<WidgetGroup> it14 = allDependencyLists.iterator();
            while (it14.hasNext()) {
                WidgetGroup list = it14.next();
                ArrayList<ConstraintWidget> children2 = children;
                if (list.getOrientation() == 1) {
                    children = children2;
                } else {
                    list.setAuthoritative(false);
                    int wrap = list.measureWrap(layout.getSystem(), 0);
                    if (wrap > maxWrap) {
                        maxWrap = wrap;
                        picked = list;
                    }
                    children = children2;
                }
            }
            if (picked != null) {
                constraintWidgetContainer.setHorizontalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                constraintWidgetContainer.setWidth(maxWrap);
                picked.setAuthoritative(true);
                horizontalPick = picked;
            }
        }
        if (layout.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.WRAP_CONTENT) {
            int maxWrap2 = 0;
            WidgetGroup picked2 = null;
            Iterator<WidgetGroup> it15 = allDependencyLists.iterator();
            while (it15.hasNext()) {
                WidgetGroup list2 = it15.next();
                if (list2.getOrientation() != 0) {
                    list2.setAuthoritative(false);
                    int wrap2 = list2.measureWrap(layout.getSystem(), 1);
                    if (wrap2 > maxWrap2) {
                        picked2 = list2;
                        maxWrap2 = wrap2;
                    }
                }
            }
            if (picked2 != null) {
                constraintWidgetContainer.setVerticalDimensionBehaviour(ConstraintWidget.DimensionBehaviour.FIXED);
                constraintWidgetContainer.setHeight(maxWrap2);
                picked2.setAuthoritative(true);
                verticalPick = picked2;
            }
        }
        return (horizontalPick == null && verticalPick == null) ? false : true;
    }

    private static WidgetGroup findGroup(ArrayList<WidgetGroup> horizontalDependencyLists, int groupId) {
        int count = horizontalDependencyLists.size();
        for (int i = 0; i < count; i++) {
            WidgetGroup group = horizontalDependencyLists.get(i);
            if (groupId == group.f1013id) {
                return group;
            }
        }
        return null;
    }

    public static WidgetGroup findDependents(ConstraintWidget constraintWidget, int orientation, ArrayList<WidgetGroup> list, WidgetGroup group) {
        int groupId;
        int groupId2;
        if (orientation == 0) {
            groupId = constraintWidget.horizontalGroup;
        } else {
            groupId = constraintWidget.verticalGroup;
        }
        if (groupId != -1 && (group == null || groupId != group.f1013id)) {
            int i = 0;
            while (true) {
                if (i >= list.size()) {
                    break;
                }
                WidgetGroup widgetGroup = list.get(i);
                if (widgetGroup.getId() == groupId) {
                    if (group != null) {
                        group.moveTo(orientation, widgetGroup);
                        list.remove(group);
                    }
                    group = widgetGroup;
                } else {
                    i++;
                }
            }
        } else if (groupId != -1) {
            return group;
        }
        if (group == null) {
            if ((constraintWidget instanceof HelperWidget) && (groupId2 = ((HelperWidget) constraintWidget).findGroupInDependents(orientation)) != -1) {
                int i2 = 0;
                while (true) {
                    if (i2 >= list.size()) {
                        break;
                    }
                    WidgetGroup widgetGroup2 = list.get(i2);
                    if (widgetGroup2.getId() == groupId2) {
                        group = widgetGroup2;
                        break;
                    }
                    i2++;
                }
            }
            if (group == null) {
                group = new WidgetGroup(orientation);
            }
            list.add(group);
        }
        if (group.add(constraintWidget)) {
            if (constraintWidget instanceof Guideline) {
                Guideline guideline = (Guideline) constraintWidget;
                guideline.getAnchor().findDependents(guideline.getOrientation() == 0 ? 1 : 0, list, group);
            }
            if (orientation == 0) {
                constraintWidget.horizontalGroup = group.getId();
                constraintWidget.mLeft.findDependents(orientation, list, group);
                constraintWidget.mRight.findDependents(orientation, list, group);
            } else {
                constraintWidget.verticalGroup = group.getId();
                constraintWidget.mTop.findDependents(orientation, list, group);
                constraintWidget.mBaseline.findDependents(orientation, list, group);
                constraintWidget.mBottom.findDependents(orientation, list, group);
            }
            constraintWidget.mCenter.findDependents(orientation, list, group);
        }
        return group;
    }
}
