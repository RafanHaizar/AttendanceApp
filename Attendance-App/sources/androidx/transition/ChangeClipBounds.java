package androidx.transition;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.view.ViewCompat;

public class ChangeClipBounds extends Transition {
    private static final String PROPNAME_BOUNDS = "android:clipBounds:bounds";
    private static final String PROPNAME_CLIP = "android:clipBounds:clip";
    private static final String[] sTransitionProperties = {PROPNAME_CLIP};

    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public ChangeClipBounds() {
    }

    public ChangeClipBounds(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void captureValues(TransitionValues values) {
        View view = values.view;
        if (view.getVisibility() != 8) {
            Rect clip = ViewCompat.getClipBounds(view);
            values.values.put(PROPNAME_CLIP, clip);
            if (clip == null) {
                values.values.put(PROPNAME_BOUNDS, new Rect(0, 0, view.getWidth(), view.getHeight()));
            }
        }
    }

    public void captureStartValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    public void captureEndValues(TransitionValues transitionValues) {
        captureValues(transitionValues);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v4, resolved type: android.graphics.Rect} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v8, resolved type: android.graphics.Rect} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.animation.Animator createAnimator(android.view.ViewGroup r10, androidx.transition.TransitionValues r11, androidx.transition.TransitionValues r12) {
        /*
            r9 = this;
            r0 = 0
            if (r11 == 0) goto L_0x007f
            if (r12 == 0) goto L_0x007f
            java.util.Map<java.lang.String, java.lang.Object> r1 = r11.values
            java.lang.String r2 = "android:clipBounds:clip"
            boolean r1 = r1.containsKey(r2)
            if (r1 == 0) goto L_0x007f
            java.util.Map<java.lang.String, java.lang.Object> r1 = r12.values
            boolean r1 = r1.containsKey(r2)
            if (r1 != 0) goto L_0x0018
            goto L_0x007f
        L_0x0018:
            java.util.Map<java.lang.String, java.lang.Object> r1 = r11.values
            java.lang.Object r1 = r1.get(r2)
            android.graphics.Rect r1 = (android.graphics.Rect) r1
            java.util.Map<java.lang.String, java.lang.Object> r3 = r12.values
            java.lang.Object r2 = r3.get(r2)
            android.graphics.Rect r2 = (android.graphics.Rect) r2
            r3 = 1
            r4 = 0
            if (r2 != 0) goto L_0x002e
            r5 = 1
            goto L_0x002f
        L_0x002e:
            r5 = 0
        L_0x002f:
            if (r1 != 0) goto L_0x0034
            if (r2 != 0) goto L_0x0034
            return r0
        L_0x0034:
            java.lang.String r6 = "android:clipBounds:bounds"
            if (r1 != 0) goto L_0x0042
            java.util.Map<java.lang.String, java.lang.Object> r7 = r11.values
            java.lang.Object r6 = r7.get(r6)
            r1 = r6
            android.graphics.Rect r1 = (android.graphics.Rect) r1
            goto L_0x004d
        L_0x0042:
            if (r2 != 0) goto L_0x004d
            java.util.Map<java.lang.String, java.lang.Object> r7 = r12.values
            java.lang.Object r6 = r7.get(r6)
            r2 = r6
            android.graphics.Rect r2 = (android.graphics.Rect) r2
        L_0x004d:
            boolean r6 = r1.equals(r2)
            if (r6 == 0) goto L_0x0054
            return r0
        L_0x0054:
            android.view.View r0 = r12.view
            androidx.core.view.ViewCompat.setClipBounds(r0, r1)
            androidx.transition.RectEvaluator r0 = new androidx.transition.RectEvaluator
            android.graphics.Rect r6 = new android.graphics.Rect
            r6.<init>()
            r0.<init>(r6)
            android.view.View r6 = r12.view
            android.util.Property<android.view.View, android.graphics.Rect> r7 = androidx.transition.ViewUtils.CLIP_BOUNDS
            r8 = 2
            android.graphics.Rect[] r8 = new android.graphics.Rect[r8]
            r8[r4] = r1
            r8[r3] = r2
            android.animation.ObjectAnimator r3 = android.animation.ObjectAnimator.ofObject(r6, r7, r0, r8)
            if (r5 == 0) goto L_0x007e
            android.view.View r4 = r12.view
            androidx.transition.ChangeClipBounds$1 r6 = new androidx.transition.ChangeClipBounds$1
            r6.<init>(r4)
            r3.addListener(r6)
        L_0x007e:
            return r3
        L_0x007f:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.transition.ChangeClipBounds.createAnimator(android.view.ViewGroup, androidx.transition.TransitionValues, androidx.transition.TransitionValues):android.animation.Animator");
    }
}
