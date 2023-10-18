package androidx.constraintlayout.core.state.helpers;

import androidx.constraintlayout.core.state.ConstraintReference;
import androidx.constraintlayout.core.state.HelperReference;
import androidx.constraintlayout.core.state.State;
import java.util.Iterator;

public class AlignVerticallyReference extends HelperReference {
    private float mBias = 0.5f;

    public AlignVerticallyReference(State state) {
        super(state, State.Helper.ALIGN_VERTICALLY);
    }

    public void apply() {
        Iterator it = this.mReferences.iterator();
        while (it.hasNext()) {
            ConstraintReference reference = this.mState.constraints(it.next());
            reference.clearVertical();
            if (this.mTopToTop != null) {
                reference.topToTop(this.mTopToTop);
            } else if (this.mTopToBottom != null) {
                reference.topToBottom(this.mTopToBottom);
            } else {
                reference.topToTop(State.PARENT);
            }
            if (this.mBottomToTop != null) {
                reference.bottomToTop(this.mBottomToTop);
            } else if (this.mBottomToBottom != null) {
                reference.bottomToBottom(this.mBottomToBottom);
            } else {
                reference.bottomToBottom(State.PARENT);
            }
            float f = this.mBias;
            if (f != 0.5f) {
                reference.verticalBias(f);
            }
        }
    }
}
