package androidx.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.xmlpull.v1.XmlPullParser;

public class Slide extends Visibility {
    private static final String PROPNAME_SCREEN_POSITION = "android:slide:screenPosition";
    private static final TimeInterpolator sAccelerate = new AccelerateInterpolator();
    private static final CalculateSlide sCalculateBottom = new CalculateSlideVertical() {
        public float getGoneY(ViewGroup sceneRoot, View view) {
            return view.getTranslationY() + ((float) sceneRoot.getHeight());
        }
    };
    private static final CalculateSlide sCalculateEnd = new CalculateSlideHorizontal() {
        public float getGoneX(ViewGroup sceneRoot, View view) {
            boolean isRtl = true;
            if (ViewCompat.getLayoutDirection(sceneRoot) != 1) {
                isRtl = false;
            }
            if (isRtl) {
                return view.getTranslationX() - ((float) sceneRoot.getWidth());
            }
            return view.getTranslationX() + ((float) sceneRoot.getWidth());
        }
    };
    private static final CalculateSlide sCalculateLeft = new CalculateSlideHorizontal() {
        public float getGoneX(ViewGroup sceneRoot, View view) {
            return view.getTranslationX() - ((float) sceneRoot.getWidth());
        }
    };
    private static final CalculateSlide sCalculateRight = new CalculateSlideHorizontal() {
        public float getGoneX(ViewGroup sceneRoot, View view) {
            return view.getTranslationX() + ((float) sceneRoot.getWidth());
        }
    };
    private static final CalculateSlide sCalculateStart = new CalculateSlideHorizontal() {
        public float getGoneX(ViewGroup sceneRoot, View view) {
            boolean isRtl = true;
            if (ViewCompat.getLayoutDirection(sceneRoot) != 1) {
                isRtl = false;
            }
            if (isRtl) {
                return view.getTranslationX() + ((float) sceneRoot.getWidth());
            }
            return view.getTranslationX() - ((float) sceneRoot.getWidth());
        }
    };
    private static final CalculateSlide sCalculateTop = new CalculateSlideVertical() {
        public float getGoneY(ViewGroup sceneRoot, View view) {
            return view.getTranslationY() - ((float) sceneRoot.getHeight());
        }
    };
    private static final TimeInterpolator sDecelerate = new DecelerateInterpolator();
    private CalculateSlide mSlideCalculator = sCalculateBottom;
    private int mSlideEdge = 80;

    @Retention(RetentionPolicy.SOURCE)
    public @interface GravityFlag {
    }

    private interface CalculateSlide {
        float getGoneX(ViewGroup viewGroup, View view);

        float getGoneY(ViewGroup viewGroup, View view);
    }

    private static abstract class CalculateSlideHorizontal implements CalculateSlide {
        private CalculateSlideHorizontal() {
        }

        public float getGoneY(ViewGroup sceneRoot, View view) {
            return view.getTranslationY();
        }
    }

    private static abstract class CalculateSlideVertical implements CalculateSlide {
        private CalculateSlideVertical() {
        }

        public float getGoneX(ViewGroup sceneRoot, View view) {
            return view.getTranslationX();
        }
    }

    public Slide() {
        setSlideEdge(80);
    }

    public Slide(int slideEdge) {
        setSlideEdge(slideEdge);
    }

    public Slide(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, Styleable.SLIDE);
        int edge = TypedArrayUtils.getNamedInt(a, (XmlPullParser) attrs, "slideEdge", 0, 80);
        a.recycle();
        setSlideEdge(edge);
    }

    private void captureValues(TransitionValues transitionValues) {
        int[] position = new int[2];
        transitionValues.view.getLocationOnScreen(position);
        transitionValues.values.put(PROPNAME_SCREEN_POSITION, position);
    }

    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        captureValues(transitionValues);
    }

    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        captureValues(transitionValues);
    }

    public void setSlideEdge(int slideEdge) {
        switch (slideEdge) {
            case 3:
                this.mSlideCalculator = sCalculateLeft;
                break;
            case 5:
                this.mSlideCalculator = sCalculateRight;
                break;
            case 48:
                this.mSlideCalculator = sCalculateTop;
                break;
            case 80:
                this.mSlideCalculator = sCalculateBottom;
                break;
            case GravityCompat.START:
                this.mSlideCalculator = sCalculateStart;
                break;
            case GravityCompat.END:
                this.mSlideCalculator = sCalculateEnd;
                break;
            default:
                throw new IllegalArgumentException("Invalid slide direction");
        }
        this.mSlideEdge = slideEdge;
        SidePropagation propagation = new SidePropagation();
        propagation.setSide(slideEdge);
        setPropagation(propagation);
    }

    public int getSlideEdge() {
        return this.mSlideEdge;
    }

    public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        ViewGroup viewGroup = sceneRoot;
        View view2 = view;
        TransitionValues transitionValues = endValues;
        if (transitionValues == null) {
            return null;
        }
        int[] position = (int[]) transitionValues.values.get(PROPNAME_SCREEN_POSITION);
        float endX = view.getTranslationX();
        float endY = view.getTranslationY();
        float startX = this.mSlideCalculator.getGoneX(viewGroup, view2);
        return TranslationAnimationCreator.createAnimation(view, endValues, position[0], position[1], startX, this.mSlideCalculator.getGoneY(viewGroup, view2), endX, endY, sDecelerate, this);
    }

    public Animator onDisappear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
        ViewGroup viewGroup = sceneRoot;
        View view2 = view;
        TransitionValues transitionValues = startValues;
        if (transitionValues == null) {
            return null;
        }
        int[] position = (int[]) transitionValues.values.get(PROPNAME_SCREEN_POSITION);
        float startX = view.getTranslationX();
        float startY = view.getTranslationY();
        float endX = this.mSlideCalculator.getGoneX(viewGroup, view2);
        return TranslationAnimationCreator.createAnimation(view, startValues, position[0], position[1], startX, startY, endX, this.mSlideCalculator.getGoneY(viewGroup, view2), sAccelerate, this);
    }
}
