package androidx.constraintlayout.core;

public class GoalRow extends ArrayRow {
    public GoalRow(Cache cache) {
        super(cache);
    }

    public void addError(SolverVariable error) {
        super.addError(error);
        error.usageInRowCount--;
    }
}
