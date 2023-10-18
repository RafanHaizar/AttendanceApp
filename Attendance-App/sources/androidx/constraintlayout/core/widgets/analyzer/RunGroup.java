package androidx.constraintlayout.core.widgets.analyzer;

import androidx.constraintlayout.core.widgets.ConstraintWidgetContainer;
import java.util.ArrayList;
import java.util.Iterator;

class RunGroup {
    public static final int BASELINE = 2;
    public static final int END = 1;
    public static final int START = 0;
    public static int index;
    int direction;
    public boolean dual = false;
    WidgetRun firstRun = null;
    int groupIndex = 0;
    WidgetRun lastRun = null;
    public int position = 0;
    ArrayList<WidgetRun> runs = new ArrayList<>();

    public RunGroup(WidgetRun run, int dir) {
        int i = index;
        this.groupIndex = i;
        index = i + 1;
        this.firstRun = run;
        this.lastRun = run;
        this.direction = dir;
    }

    public void add(WidgetRun run) {
        this.runs.add(run);
        this.lastRun = run;
    }

    private long traverseStart(DependencyNode node, long startPosition) {
        WidgetRun run = node.run;
        if (run instanceof HelperReferences) {
            return startPosition;
        }
        long position2 = startPosition;
        int count = node.dependencies.size();
        for (int i = 0; i < count; i++) {
            Dependency dependency = node.dependencies.get(i);
            if (dependency instanceof DependencyNode) {
                DependencyNode nextNode = (DependencyNode) dependency;
                if (nextNode.run != run) {
                    position2 = Math.max(position2, traverseStart(nextNode, ((long) nextNode.margin) + startPosition));
                }
            }
        }
        if (node != run.start) {
            return position2;
        }
        long dimension = run.getWrapDimension();
        return Math.max(Math.max(position2, traverseStart(run.end, startPosition + dimension)), (startPosition + dimension) - ((long) run.end.margin));
    }

    private long traverseEnd(DependencyNode node, long startPosition) {
        WidgetRun run = node.run;
        if (run instanceof HelperReferences) {
            return startPosition;
        }
        long position2 = startPosition;
        int count = node.dependencies.size();
        for (int i = 0; i < count; i++) {
            Dependency dependency = node.dependencies.get(i);
            if (dependency instanceof DependencyNode) {
                DependencyNode nextNode = (DependencyNode) dependency;
                if (nextNode.run != run) {
                    position2 = Math.min(position2, traverseEnd(nextNode, ((long) nextNode.margin) + startPosition));
                }
            }
        }
        if (node != run.end) {
            return position2;
        }
        long dimension = run.getWrapDimension();
        return Math.min(Math.min(position2, traverseEnd(run.start, startPosition - dimension)), (startPosition - dimension) - ((long) run.start.margin));
    }

    public long computeWrapSize(ConstraintWidgetContainer container, int orientation) {
        long gap;
        ConstraintWidgetContainer constraintWidgetContainer = container;
        int i = orientation;
        WidgetRun widgetRun = this.firstRun;
        if (widgetRun instanceof ChainRun) {
            if (((ChainRun) widgetRun).orientation != i) {
                return 0;
            }
        } else if (i == 0) {
            if (!(widgetRun instanceof HorizontalWidgetRun)) {
                return 0;
            }
        } else if (!(widgetRun instanceof VerticalWidgetRun)) {
            return 0;
        }
        DependencyNode containerStart = i == 0 ? constraintWidgetContainer.horizontalRun.start : constraintWidgetContainer.verticalRun.start;
        DependencyNode containerEnd = i == 0 ? constraintWidgetContainer.horizontalRun.end : constraintWidgetContainer.verticalRun.end;
        boolean runWithStartTarget = this.firstRun.start.targets.contains(containerStart);
        boolean runWithEndTarget = this.firstRun.end.targets.contains(containerEnd);
        long dimension = this.firstRun.getWrapDimension();
        if (!runWithStartTarget || !runWithEndTarget) {
            DependencyNode dependencyNode = containerEnd;
            if (runWithStartTarget) {
                return Math.max(traverseStart(this.firstRun.start, (long) this.firstRun.start.margin), ((long) this.firstRun.start.margin) + dimension);
            }
            if (!runWithEndTarget) {
                return (((long) this.firstRun.start.margin) + this.firstRun.getWrapDimension()) - ((long) this.firstRun.end.margin);
            }
            return Math.max(-traverseEnd(this.firstRun.end, (long) this.firstRun.end.margin), ((long) (-this.firstRun.end.margin)) + dimension);
        }
        long maxPosition = traverseStart(this.firstRun.start, 0);
        long minPosition = traverseEnd(this.firstRun.end, 0);
        long endGap = maxPosition - dimension;
        DependencyNode dependencyNode2 = containerStart;
        DependencyNode dependencyNode3 = containerEnd;
        if (endGap >= ((long) (-this.firstRun.end.margin))) {
            endGap += (long) this.firstRun.end.margin;
        }
        long j = minPosition;
        long startGap = ((-minPosition) - dimension) - ((long) this.firstRun.start.margin);
        if (startGap >= ((long) this.firstRun.start.margin)) {
            startGap -= (long) this.firstRun.start.margin;
        }
        float bias = this.firstRun.widget.getBiasPercent(i);
        if (bias > 0.0f) {
            gap = (long) ((((float) startGap) / bias) + (((float) endGap) / (1.0f - bias)));
        } else {
            gap = 0;
        }
        long startGap2 = (long) ((((float) gap) * bias) + 0.5f);
        long j2 = gap;
        long j3 = startGap2;
        return (((long) this.firstRun.start.margin) + ((startGap2 + dimension) + ((long) ((((float) gap) * (1.0f - bias)) + 0.5f)))) - ((long) this.firstRun.end.margin);
    }

    private boolean defineTerminalWidget(WidgetRun run, int orientation) {
        if (!run.widget.isTerminalWidget[orientation]) {
            return false;
        }
        for (Dependency dependency : run.start.dependencies) {
            if (dependency instanceof DependencyNode) {
                DependencyNode node = (DependencyNode) dependency;
                if (node.run != run && node == node.run.start) {
                    if (run instanceof ChainRun) {
                        Iterator<WidgetRun> it = ((ChainRun) run).widgets.iterator();
                        while (it.hasNext()) {
                            defineTerminalWidget(it.next(), orientation);
                        }
                    } else if (!(run instanceof HelperReferences)) {
                        run.widget.isTerminalWidget[orientation] = false;
                    }
                    defineTerminalWidget(node.run, orientation);
                }
            }
        }
        for (Dependency dependency2 : run.end.dependencies) {
            if (dependency2 instanceof DependencyNode) {
                DependencyNode node2 = (DependencyNode) dependency2;
                if (node2.run != run && node2 == node2.run.start) {
                    if (run instanceof ChainRun) {
                        Iterator<WidgetRun> it2 = ((ChainRun) run).widgets.iterator();
                        while (it2.hasNext()) {
                            defineTerminalWidget(it2.next(), orientation);
                        }
                    } else if (!(run instanceof HelperReferences)) {
                        run.widget.isTerminalWidget[orientation] = false;
                    }
                    defineTerminalWidget(node2.run, orientation);
                }
            }
        }
        return false;
    }

    public void defineTerminalWidgets(boolean horizontalCheck, boolean verticalCheck) {
        if (horizontalCheck) {
            WidgetRun widgetRun = this.firstRun;
            if (widgetRun instanceof HorizontalWidgetRun) {
                defineTerminalWidget(widgetRun, 0);
            }
        }
        if (verticalCheck) {
            WidgetRun widgetRun2 = this.firstRun;
            if (widgetRun2 instanceof VerticalWidgetRun) {
                defineTerminalWidget(widgetRun2, 1);
            }
        }
    }
}
