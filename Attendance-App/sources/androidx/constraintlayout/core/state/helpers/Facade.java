package androidx.constraintlayout.core.state.helpers;

import androidx.constraintlayout.core.widgets.ConstraintWidget;

public interface Facade {
    void apply();

    ConstraintWidget getConstraintWidget();
}
