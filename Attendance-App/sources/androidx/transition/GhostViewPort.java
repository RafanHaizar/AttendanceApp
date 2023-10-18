package androidx.transition;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.core.view.ViewCompat;

class GhostViewPort extends ViewGroup implements GhostView {
    private Matrix mMatrix;
    private final ViewTreeObserver.OnPreDrawListener mOnPreDrawListener = new ViewTreeObserver.OnPreDrawListener() {
        public boolean onPreDraw() {
            ViewCompat.postInvalidateOnAnimation(GhostViewPort.this);
            if (GhostViewPort.this.mStartParent == null || GhostViewPort.this.mStartView == null) {
                return true;
            }
            GhostViewPort.this.mStartParent.endViewTransition(GhostViewPort.this.mStartView);
            ViewCompat.postInvalidateOnAnimation(GhostViewPort.this.mStartParent);
            GhostViewPort.this.mStartParent = null;
            GhostViewPort.this.mStartView = null;
            return true;
        }
    };
    int mReferences;
    ViewGroup mStartParent;
    View mStartView;
    final View mView;

    GhostViewPort(View view) {
        super(view.getContext());
        this.mView = view;
        setWillNotDraw(false);
        setLayerType(2, (Paint) null);
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (getGhostView(this.mView) == this) {
            ViewUtils.setTransitionVisibility(this.mView, visibility == 0 ? 4 : 0);
        }
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
    }

    /* access modifiers changed from: package-private */
    public void setMatrix(Matrix matrix) {
        this.mMatrix = matrix;
    }

    public void reserveEndViewTransition(ViewGroup viewGroup, View view) {
        this.mStartParent = viewGroup;
        this.mStartView = view;
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setGhostView(this.mView, this);
        this.mView.getViewTreeObserver().addOnPreDrawListener(this.mOnPreDrawListener);
        ViewUtils.setTransitionVisibility(this.mView, 4);
        if (this.mView.getParent() != null) {
            ((View) this.mView.getParent()).invalidate();
        }
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        this.mView.getViewTreeObserver().removeOnPreDrawListener(this.mOnPreDrawListener);
        ViewUtils.setTransitionVisibility(this.mView, 0);
        setGhostView(this.mView, (GhostViewPort) null);
        if (this.mView.getParent() != null) {
            ((View) this.mView.getParent()).invalidate();
        }
        super.onDetachedFromWindow();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        CanvasUtils.enableZ(canvas, true);
        canvas.setMatrix(this.mMatrix);
        ViewUtils.setTransitionVisibility(this.mView, 0);
        this.mView.invalidate();
        ViewUtils.setTransitionVisibility(this.mView, 4);
        drawChild(canvas, this.mView, getDrawingTime());
        CanvasUtils.enableZ(canvas, false);
    }

    static void copySize(View from, View to) {
        ViewUtils.setLeftTopRightBottom(to, to.getLeft(), to.getTop(), to.getLeft() + from.getWidth(), to.getTop() + from.getHeight());
    }

    static GhostViewPort getGhostView(View view) {
        return (GhostViewPort) view.getTag(C1010R.C1013id.ghost_view);
    }

    static void setGhostView(View view, GhostViewPort ghostView) {
        view.setTag(C1010R.C1013id.ghost_view, ghostView);
    }

    static void calculateMatrix(View view, ViewGroup host, Matrix matrix) {
        ViewGroup parent = (ViewGroup) view.getParent();
        matrix.reset();
        ViewUtils.transformMatrixToGlobal(parent, matrix);
        matrix.preTranslate((float) (-parent.getScrollX()), (float) (-parent.getScrollY()));
        ViewUtils.transformMatrixToLocal(host, matrix);
    }

    static GhostViewPort addGhost(View view, ViewGroup viewGroup, Matrix matrix) {
        GhostViewHolder oldHolder;
        if (view.getParent() instanceof ViewGroup) {
            GhostViewHolder holder = GhostViewHolder.getHolder(viewGroup);
            GhostViewPort ghostView = getGhostView(view);
            int previousRefCount = 0;
            if (!(ghostView == null || (oldHolder = (GhostViewHolder) ghostView.getParent()) == holder)) {
                previousRefCount = ghostView.mReferences;
                oldHolder.removeView(ghostView);
                ghostView = null;
            }
            if (ghostView == null) {
                if (matrix == null) {
                    matrix = new Matrix();
                    calculateMatrix(view, viewGroup, matrix);
                }
                ghostView = new GhostViewPort(view);
                ghostView.setMatrix(matrix);
                if (holder == null) {
                    holder = new GhostViewHolder(viewGroup);
                } else {
                    holder.popToOverlayTop();
                }
                copySize(viewGroup, holder);
                copySize(viewGroup, ghostView);
                holder.addGhostView(ghostView);
                ghostView.mReferences = previousRefCount;
            } else if (matrix != null) {
                ghostView.setMatrix(matrix);
            }
            ghostView.mReferences++;
            return ghostView;
        }
        throw new IllegalArgumentException("Ghosted views must be parented by a ViewGroup");
    }

    static void removeGhost(View view) {
        GhostViewPort ghostView = getGhostView(view);
        if (ghostView != null) {
            int i = ghostView.mReferences - 1;
            ghostView.mReferences = i;
            if (i <= 0) {
                ((GhostViewHolder) ghostView.getParent()).removeView(ghostView);
            }
        }
    }
}
