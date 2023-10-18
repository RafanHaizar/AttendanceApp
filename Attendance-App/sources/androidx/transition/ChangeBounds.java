package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.ViewCompat;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import java.util.Map;

public class ChangeBounds extends Transition {
    private static final Property<View, PointF> BOTTOM_RIGHT_ONLY_PROPERTY = new Property<View, PointF>(PointF.class, "bottomRight") {
        public void set(View view, PointF bottomRight) {
            ViewUtils.setLeftTopRightBottom(view, view.getLeft(), view.getTop(), Math.round(bottomRight.x), Math.round(bottomRight.y));
        }

        public PointF get(View view) {
            return null;
        }
    };
    private static final Property<ViewBounds, PointF> BOTTOM_RIGHT_PROPERTY = new Property<ViewBounds, PointF>(PointF.class, "bottomRight") {
        public void set(ViewBounds viewBounds, PointF bottomRight) {
            viewBounds.setBottomRight(bottomRight);
        }

        public PointF get(ViewBounds viewBounds) {
            return null;
        }
    };
    private static final Property<Drawable, PointF> DRAWABLE_ORIGIN_PROPERTY = new Property<Drawable, PointF>(PointF.class, "boundsOrigin") {
        private Rect mBounds = new Rect();

        public void set(Drawable object, PointF value) {
            object.copyBounds(this.mBounds);
            this.mBounds.offsetTo(Math.round(value.x), Math.round(value.y));
            object.setBounds(this.mBounds);
        }

        public PointF get(Drawable object) {
            object.copyBounds(this.mBounds);
            return new PointF((float) this.mBounds.left, (float) this.mBounds.top);
        }
    };
    private static final Property<View, PointF> POSITION_PROPERTY = new Property<View, PointF>(PointF.class, CommonCssConstants.POSITION) {
        public void set(View view, PointF topLeft) {
            int left = Math.round(topLeft.x);
            int top = Math.round(topLeft.y);
            ViewUtils.setLeftTopRightBottom(view, left, top, view.getWidth() + left, view.getHeight() + top);
        }

        public PointF get(View view) {
            return null;
        }
    };
    private static final String PROPNAME_BOUNDS = "android:changeBounds:bounds";
    private static final String PROPNAME_CLIP = "android:changeBounds:clip";
    private static final String PROPNAME_PARENT = "android:changeBounds:parent";
    private static final String PROPNAME_WINDOW_X = "android:changeBounds:windowX";
    private static final String PROPNAME_WINDOW_Y = "android:changeBounds:windowY";
    private static final Property<View, PointF> TOP_LEFT_ONLY_PROPERTY = new Property<View, PointF>(PointF.class, "topLeft") {
        public void set(View view, PointF topLeft) {
            ViewUtils.setLeftTopRightBottom(view, Math.round(topLeft.x), Math.round(topLeft.y), view.getRight(), view.getBottom());
        }

        public PointF get(View view) {
            return null;
        }
    };
    private static final Property<ViewBounds, PointF> TOP_LEFT_PROPERTY = new Property<ViewBounds, PointF>(PointF.class, "topLeft") {
        public void set(ViewBounds viewBounds, PointF topLeft) {
            viewBounds.setTopLeft(topLeft);
        }

        public PointF get(ViewBounds viewBounds) {
            return null;
        }
    };
    private static RectEvaluator sRectEvaluator = new RectEvaluator();
    private static final String[] sTransitionProperties = {PROPNAME_BOUNDS, PROPNAME_CLIP, PROPNAME_PARENT, PROPNAME_WINDOW_X, PROPNAME_WINDOW_Y};
    private boolean mReparent = false;
    private boolean mResizeClip = false;
    private int[] mTempLocation = new int[2];

    public ChangeBounds() {
    }

    public ChangeBounds(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, Styleable.CHANGE_BOUNDS);
        boolean resizeClip = TypedArrayUtils.getNamedBoolean(a, (XmlResourceParser) attrs, "resizeClip", 0, false);
        a.recycle();
        setResizeClip(resizeClip);
    }

    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public void setResizeClip(boolean resizeClip) {
        this.mResizeClip = resizeClip;
    }

    public boolean getResizeClip() {
        return this.mResizeClip;
    }

    private void captureValues(TransitionValues values) {
        View view = values.view;
        if (ViewCompat.isLaidOut(view) || view.getWidth() != 0 || view.getHeight() != 0) {
            values.values.put(PROPNAME_BOUNDS, new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
            values.values.put(PROPNAME_PARENT, values.view.getParent());
            if (this.mReparent) {
                values.view.getLocationInWindow(this.mTempLocation);
                values.values.put(PROPNAME_WINDOW_X, Integer.valueOf(this.mTempLocation[0]));
                values.values.put(PROPNAME_WINDOW_Y, Integer.valueOf(this.mTempLocation[1]));
            }
            if (this.mResizeClip) {
                values.values.put(PROPNAME_CLIP, ViewCompat.getClipBounds(view));
            }
        }
    }

    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    private boolean parentMatches(View startParent, View endParent) {
        if (!this.mReparent) {
            return true;
        }
        boolean parentMatches = true;
        TransitionValues endValues = getMatchedTransitionValues(startParent, true);
        if (endValues == null) {
            if (startParent != endParent) {
                parentMatches = false;
            }
            return parentMatches;
        }
        if (endParent != endValues.view) {
            parentMatches = false;
        }
        return parentMatches;
    }

    public Animator createAnimator(ViewGroup sceneRoot, TransitionValues startValues, TransitionValues endValues) {
        boolean z;
        View view;
        Animator anim;
        int endLeft;
        int startTop;
        int startLeft;
        ObjectAnimator positionAnimator;
        Rect startClip;
        int i;
        Rect endClip;
        Rect startClip2;
        TransitionValues transitionValues = startValues;
        TransitionValues transitionValues2 = endValues;
        if (transitionValues == null) {
            ViewGroup viewGroup = sceneRoot;
            TransitionValues transitionValues3 = transitionValues2;
            return null;
        } else if (transitionValues2 == null) {
            ViewGroup viewGroup2 = sceneRoot;
            TransitionValues transitionValues4 = transitionValues2;
            return null;
        } else {
            Map<String, Object> startParentVals = transitionValues.values;
            Map<String, Object> endParentVals = transitionValues2.values;
            ViewGroup startParent = (ViewGroup) startParentVals.get(PROPNAME_PARENT);
            ViewGroup endParent = (ViewGroup) endParentVals.get(PROPNAME_PARENT);
            if (startParent == null) {
                ViewGroup viewGroup3 = sceneRoot;
                Map<String, Object> map = startParentVals;
                Map<String, Object> map2 = endParentVals;
                ViewGroup viewGroup4 = startParent;
                ViewGroup viewGroup5 = endParent;
                TransitionValues transitionValues5 = transitionValues2;
                return null;
            } else if (endParent == null) {
                ViewGroup viewGroup6 = sceneRoot;
                Map<String, Object> map3 = startParentVals;
                Map<String, Object> map4 = endParentVals;
                ViewGroup viewGroup7 = startParent;
                ViewGroup viewGroup8 = endParent;
                TransitionValues transitionValues6 = transitionValues2;
                return null;
            } else {
                View view2 = transitionValues2.view;
                if (parentMatches(startParent, endParent)) {
                    Rect startBounds = (Rect) transitionValues.values.get(PROPNAME_BOUNDS);
                    Rect endBounds = (Rect) transitionValues2.values.get(PROPNAME_BOUNDS);
                    int startLeft2 = startBounds.left;
                    int endLeft2 = endBounds.left;
                    int startTop2 = startBounds.top;
                    int endTop = endBounds.top;
                    int startRight = startBounds.right;
                    Map<String, Object> map5 = startParentVals;
                    int endRight = endBounds.right;
                    Map<String, Object> map6 = endParentVals;
                    int startBottom = startBounds.bottom;
                    ViewGroup viewGroup9 = startParent;
                    int endBottom = endBounds.bottom;
                    ViewGroup viewGroup10 = endParent;
                    int startWidth = startRight - startLeft2;
                    Rect rect = startBounds;
                    int startHeight = startBottom - startTop2;
                    Rect rect2 = endBounds;
                    int endWidth = endRight - endLeft2;
                    int endHeight = endBottom - endTop;
                    View view3 = view2;
                    Rect startClip3 = (Rect) transitionValues.values.get(PROPNAME_CLIP);
                    Rect endClip2 = (Rect) transitionValues2.values.get(PROPNAME_CLIP);
                    int numChanges = 0;
                    if (!((startWidth == 0 || startHeight == 0) && (endWidth == 0 || endHeight == 0))) {
                        if (!(startLeft2 == endLeft2 && startTop2 == endTop)) {
                            numChanges = 0 + 1;
                        }
                        if (!(startRight == endRight && startBottom == endBottom)) {
                            numChanges++;
                        }
                    }
                    if ((startClip3 != null && !startClip3.equals(endClip2)) || (startClip3 == null && endClip2 != null)) {
                        numChanges++;
                    }
                    if (numChanges > 0) {
                        Rect startClip4 = startClip3;
                        Rect endClip3 = endClip2;
                        if (!this.mResizeClip) {
                            View view4 = view3;
                            ViewUtils.setLeftTopRightBottom(view4, startLeft2, startTop2, startRight, startBottom);
                            if (numChanges != 2) {
                                int endHeight2 = endHeight;
                                int endWidth2 = endWidth;
                                int startHeight2 = startHeight;
                                int i2 = numChanges;
                                int startWidth2 = startWidth;
                                View view5 = view4;
                                if (startLeft2 != endLeft2) {
                                    view = view5;
                                } else if (startTop2 != endTop) {
                                    view = view5;
                                } else {
                                    view = view5;
                                    anim = ObjectAnimatorUtils.ofPointF(view, BOTTOM_RIGHT_ONLY_PROPERTY, getPathMotion().getPath((float) startRight, (float) startBottom, (float) endRight, (float) endBottom));
                                    int i3 = endLeft2;
                                    int i4 = startRight;
                                    int i5 = endTop;
                                    int i6 = endRight;
                                    Rect rect3 = startClip4;
                                    int i7 = endHeight2;
                                    int i8 = startHeight2;
                                    int i9 = endWidth2;
                                    int i10 = startWidth2;
                                    z = true;
                                    int endWidth3 = startTop2;
                                    int endHeight3 = startLeft2;
                                    int startHeight3 = startBottom;
                                }
                                anim = ObjectAnimatorUtils.ofPointF(view, TOP_LEFT_ONLY_PROPERTY, getPathMotion().getPath((float) startLeft2, (float) startTop2, (float) endLeft2, (float) endTop));
                                int i11 = endLeft2;
                                int i12 = startRight;
                                int i13 = endTop;
                                int i14 = endRight;
                                Rect rect4 = startClip4;
                                int i15 = endHeight2;
                                int i16 = startHeight2;
                                int i17 = endWidth2;
                                int i18 = startWidth2;
                                z = true;
                                int endWidth4 = startTop2;
                                int endHeight4 = startLeft2;
                                int startHeight4 = startBottom;
                            } else if (startWidth == endWidth && startHeight == endHeight) {
                                int i19 = numChanges;
                                int endHeight5 = endHeight;
                                anim = ObjectAnimatorUtils.ofPointF(view4, POSITION_PROPERTY, getPathMotion().getPath((float) startLeft2, (float) startTop2, (float) endLeft2, (float) endTop));
                                int i20 = endLeft2;
                                int i21 = startRight;
                                int i22 = endTop;
                                int i23 = endRight;
                                int i24 = startWidth;
                                view = view4;
                                Rect rect5 = startClip4;
                                int i25 = endHeight5;
                                int i26 = startHeight;
                                int i27 = endWidth;
                                z = true;
                                int endWidth5 = startTop2;
                                int endHeight6 = startLeft2;
                                int startHeight5 = startBottom;
                            } else {
                                int endHeight7 = endHeight;
                                int endWidth6 = endWidth;
                                int startHeight6 = startHeight;
                                int i28 = numChanges;
                                ViewBounds viewBounds = new ViewBounds(view4);
                                Path topLeftPath = getPathMotion().getPath((float) startLeft2, (float) startTop2, (float) endLeft2, (float) endTop);
                                ObjectAnimator topLeftAnimator = ObjectAnimatorUtils.ofPointF(viewBounds, TOP_LEFT_PROPERTY, topLeftPath);
                                Path path = topLeftPath;
                                View view6 = view4;
                                ObjectAnimator bottomRightAnimator = ObjectAnimatorUtils.ofPointF(viewBounds, BOTTOM_RIGHT_PROPERTY, getPathMotion().getPath((float) startRight, (float) startBottom, (float) endRight, (float) endBottom));
                                AnimatorSet set = new AnimatorSet();
                                set.playTogether(new Animator[]{topLeftAnimator, bottomRightAnimator});
                                set.addListener(new AnimatorListenerAdapter(viewBounds) {
                                    private ViewBounds mViewBounds;
                                    final /* synthetic */ ViewBounds val$viewBounds;

                                    {
                                        this.val$viewBounds = r2;
                                        this.mViewBounds = r2;
                                    }
                                });
                                int i29 = startRight;
                                int i30 = endTop;
                                int i31 = endRight;
                                anim = set;
                                Rect rect6 = startClip4;
                                int i32 = endHeight7;
                                int i33 = startHeight6;
                                int i34 = endWidth6;
                                int i35 = startWidth;
                                view = view6;
                                z = true;
                                int i36 = endLeft2;
                                int endWidth7 = startTop2;
                                int endHeight8 = startLeft2;
                                int startHeight7 = startBottom;
                            }
                        } else {
                            int i37 = endWidth;
                            int i38 = startHeight;
                            view = view3;
                            int i39 = numChanges;
                            int startWidth3 = startWidth;
                            int maxWidth = Math.max(startWidth3, endWidth);
                            int startRight2 = startRight;
                            int i40 = startBottom;
                            ViewUtils.setLeftTopRightBottom(view, startLeft2, startTop2, startLeft2 + maxWidth, startTop2 + Math.max(startHeight, endHeight));
                            if (startLeft2 == endLeft2 && startTop2 == endTop) {
                                endLeft = endLeft2;
                                positionAnimator = null;
                                startTop = startTop2;
                                startLeft = startLeft2;
                            } else {
                                startLeft = startLeft2;
                                startTop = startTop2;
                                endLeft = endLeft2;
                                positionAnimator = ObjectAnimatorUtils.ofPointF(view, POSITION_PROPERTY, getPathMotion().getPath((float) startLeft2, (float) startTop2, (float) endLeft2, (float) endTop));
                            }
                            int i41 = startTop;
                            final Rect finalClip = endClip3;
                            if (startClip4 == null) {
                                i = 0;
                                startClip = new Rect(0, 0, startWidth3, startHeight);
                            } else {
                                i = 0;
                                startClip = startClip4;
                            }
                            if (endClip3 == null) {
                                endClip = new Rect(i, i, endWidth, endHeight);
                            } else {
                                endClip = endClip3;
                            }
                            ObjectAnimator clipAnimator = null;
                            if (!startClip.equals(endClip)) {
                                ViewCompat.setClipBounds(view, startClip);
                                int i42 = endHeight;
                                int i43 = startWidth3;
                                C09948 r9 = r0;
                                int i44 = maxWidth;
                                Rect rect7 = endClip;
                                int i45 = startRight2;
                                ObjectAnimator clipAnimator2 = ObjectAnimator.ofObject(view, "clipBounds", sRectEvaluator, new Object[]{startClip, endClip});
                                final View view7 = view;
                                int i46 = startLeft;
                                startClip2 = startClip;
                                final int i47 = endLeft;
                                int i48 = endWidth;
                                final int endWidth8 = endTop;
                                int i49 = startHeight;
                                final int startHeight8 = endRight;
                                int i50 = endTop;
                                int i51 = endRight;
                                z = true;
                                final int endTop2 = endBottom;
                                C09948 r0 = new AnimatorListenerAdapter() {
                                    private boolean mIsCanceled;

                                    public void onAnimationCancel(Animator animation) {
                                        this.mIsCanceled = true;
                                    }

                                    public void onAnimationEnd(Animator animation) {
                                        if (!this.mIsCanceled) {
                                            ViewCompat.setClipBounds(view7, finalClip);
                                            ViewUtils.setLeftTopRightBottom(view7, i47, endWidth8, startHeight8, endTop2);
                                        }
                                    }
                                };
                                clipAnimator2.addListener(r9);
                                clipAnimator = clipAnimator2;
                            } else {
                                Rect rect8 = endClip;
                                int i52 = endWidth;
                                int i53 = startHeight;
                                int i54 = endTop;
                                int i55 = startWidth3;
                                int i56 = endRight;
                                int i57 = maxWidth;
                                int i58 = startRight2;
                                int startRight3 = startLeft;
                                z = true;
                                startClip2 = startClip;
                            }
                            anim = TransitionUtils.mergeAnimators(positionAnimator, clipAnimator);
                            Rect rect9 = startClip2;
                        }
                        if (view.getParent() instanceof ViewGroup) {
                            final ViewGroup parent = (ViewGroup) view.getParent();
                            ViewGroupUtils.suppressLayout(parent, z);
                            addListener(new TransitionListenerAdapter() {
                                boolean mCanceled = false;

                                public void onTransitionCancel(Transition transition) {
                                    ViewGroupUtils.suppressLayout(parent, false);
                                    this.mCanceled = true;
                                }

                                public void onTransitionEnd(Transition transition) {
                                    if (!this.mCanceled) {
                                        ViewGroupUtils.suppressLayout(parent, false);
                                    }
                                    transition.removeListener(this);
                                }

                                public void onTransitionPause(Transition transition) {
                                    ViewGroupUtils.suppressLayout(parent, false);
                                }

                                public void onTransitionResume(Transition transition) {
                                    ViewGroupUtils.suppressLayout(parent, true);
                                }
                            });
                        }
                        return anim;
                    }
                    int i59 = endHeight;
                    int i60 = endLeft2;
                    int i61 = startRight;
                    int i62 = startTop2;
                    int i63 = startLeft2;
                    int i64 = endWidth;
                    int i65 = startHeight;
                    int i66 = endTop;
                    Rect rect10 = endClip2;
                    int i67 = endRight;
                    int i68 = startBottom;
                    int i69 = startWidth;
                    Rect rect11 = startClip3;
                    View view8 = view3;
                    int i70 = numChanges;
                    TransitionValues transitionValues7 = startValues;
                    TransitionValues transitionValues8 = endValues;
                    return null;
                }
                Map<String, Object> map7 = startParentVals;
                Map<String, Object> map8 = endParentVals;
                ViewGroup viewGroup11 = startParent;
                ViewGroup viewGroup12 = endParent;
                View view9 = view2;
                TransitionValues transitionValues9 = startValues;
                int startX = ((Integer) transitionValues9.values.get(PROPNAME_WINDOW_X)).intValue();
                int startY = ((Integer) transitionValues9.values.get(PROPNAME_WINDOW_Y)).intValue();
                TransitionValues transitionValues10 = endValues;
                int endX = ((Integer) transitionValues10.values.get(PROPNAME_WINDOW_X)).intValue();
                int endY = ((Integer) transitionValues10.values.get(PROPNAME_WINDOW_Y)).intValue();
                if (startX == endX && startY == endY) {
                    return null;
                }
                sceneRoot.getLocationInWindow(this.mTempLocation);
                Bitmap bitmap = Bitmap.createBitmap(view9.getWidth(), view9.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                view9.draw(canvas);
                final BitmapDrawable drawable = new BitmapDrawable(bitmap);
                float transitionAlpha = ViewUtils.getTransitionAlpha(view9);
                ViewUtils.setTransitionAlpha(view9, 0.0f);
                ViewUtils.getOverlay(sceneRoot).add(drawable);
                PathMotion pathMotion = getPathMotion();
                int[] iArr = this.mTempLocation;
                int i71 = iArr[0];
                int i72 = iArr[1];
                C098710 r6 = r0;
                final ViewGroup viewGroup13 = sceneRoot;
                BitmapDrawable bitmapDrawable = drawable;
                Canvas canvas2 = canvas;
                int i73 = startX;
                ObjectAnimator anim2 = ObjectAnimator.ofPropertyValuesHolder(drawable, new PropertyValuesHolder[]{PropertyValuesHolderUtils.ofPointF(DRAWABLE_ORIGIN_PROPERTY, pathMotion.getPath((float) (startX - i71), (float) (startY - i72), (float) (endX - i71), (float) (endY - i72)))});
                final View view10 = view9;
                Bitmap bitmap2 = bitmap;
                final float f = transitionAlpha;
                C098710 r02 = new AnimatorListenerAdapter() {
                    public void onAnimationEnd(Animator animation) {
                        ViewUtils.getOverlay(viewGroup13).remove(drawable);
                        ViewUtils.setTransitionAlpha(view10, f);
                    }
                };
                anim2.addListener(r6);
                return anim2;
            }
        }
    }

    private static class ViewBounds {
        private int mBottom;
        private int mBottomRightCalls;
        private int mLeft;
        private int mRight;
        private int mTop;
        private int mTopLeftCalls;
        private View mView;

        ViewBounds(View view) {
            this.mView = view;
        }

        /* access modifiers changed from: package-private */
        public void setTopLeft(PointF topLeft) {
            this.mLeft = Math.round(topLeft.x);
            this.mTop = Math.round(topLeft.y);
            int i = this.mTopLeftCalls + 1;
            this.mTopLeftCalls = i;
            if (i == this.mBottomRightCalls) {
                setLeftTopRightBottom();
            }
        }

        /* access modifiers changed from: package-private */
        public void setBottomRight(PointF bottomRight) {
            this.mRight = Math.round(bottomRight.x);
            this.mBottom = Math.round(bottomRight.y);
            int i = this.mBottomRightCalls + 1;
            this.mBottomRightCalls = i;
            if (this.mTopLeftCalls == i) {
                setLeftTopRightBottom();
            }
        }

        private void setLeftTopRightBottom() {
            ViewUtils.setLeftTopRightBottom(this.mView, this.mLeft, this.mTop, this.mRight, this.mBottom);
            this.mTopLeftCalls = 0;
            this.mBottomRightCalls = 0;
        }
    }
}
