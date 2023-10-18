package androidx.constraintlayout.core.state.helpers;

import androidx.constraintlayout.core.state.ConstraintReference;
import androidx.constraintlayout.core.state.HelperReference;
import androidx.constraintlayout.core.state.State;
import androidx.constraintlayout.core.widgets.Barrier;
import androidx.constraintlayout.core.widgets.HelperWidget;

public class BarrierReference extends HelperReference {
    private Barrier mBarrierWidget;
    private State.Direction mDirection;
    private int mMargin;

    public BarrierReference(State state) {
        super(state, State.Helper.BARRIER);
    }

    public void setBarrierDirection(State.Direction barrierDirection) {
        this.mDirection = barrierDirection;
    }

    public ConstraintReference margin(Object value) {
        margin(this.mState.convertDimension(value));
        return this;
    }

    public ConstraintReference margin(int value) {
        this.mMargin = value;
        return this;
    }

    public HelperWidget getHelperWidget() {
        if (this.mBarrierWidget == null) {
            this.mBarrierWidget = new Barrier();
        }
        return this.mBarrierWidget;
    }

    public void apply() {
        getHelperWidget();
        int direction = 0;
        switch (C06241.$SwitchMap$androidx$constraintlayout$core$state$State$Direction[this.mDirection.ordinal()]) {
            case 3:
            case 4:
                direction = 1;
                break;
            case 5:
                direction = 2;
                break;
            case 6:
                direction = 3;
                break;
        }
        this.mBarrierWidget.setBarrierType(direction);
        this.mBarrierWidget.setMargin(this.mMargin);
    }

    /* renamed from: androidx.constraintlayout.core.state.helpers.BarrierReference$1 */
    static /* synthetic */ class C06241 {
        static final /* synthetic */ int[] $SwitchMap$androidx$constraintlayout$core$state$State$Direction;

        static {
            int[] iArr = new int[State.Direction.values().length];
            $SwitchMap$androidx$constraintlayout$core$state$State$Direction = iArr;
            try {
                iArr[State.Direction.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$state$State$Direction[State.Direction.START.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$state$State$Direction[State.Direction.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$state$State$Direction[State.Direction.END.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$state$State$Direction[State.Direction.TOP.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$androidx$constraintlayout$core$state$State$Direction[State.Direction.BOTTOM.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }
}
