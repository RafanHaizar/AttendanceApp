package androidx.constraintlayout.core.state.helpers;

import androidx.constraintlayout.core.state.HelperReference;
import androidx.constraintlayout.core.state.State;

public class ChainReference extends HelperReference {
    protected float mBias = 0.5f;
    protected State.Chain mStyle = State.Chain.SPREAD;

    public ChainReference(State state, State.Helper type) {
        super(state, type);
    }

    public State.Chain getStyle() {
        return State.Chain.SPREAD;
    }

    public ChainReference style(State.Chain style) {
        this.mStyle = style;
        return this;
    }

    public float getBias() {
        return this.mBias;
    }

    public ChainReference bias(float bias) {
        this.mBias = bias;
        return this;
    }
}
