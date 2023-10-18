package androidx.constraintlayout.motion.widget;

import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.constraintlayout.motion.widget.ViewTransition;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.SharedValues;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class ViewTransitionController {
    private String TAG = "ViewTransitionController";
    ArrayList<ViewTransition.Animate> animations;
    /* access modifiers changed from: private */
    public final MotionLayout mMotionLayout;
    private HashSet<View> mRelatedViews;
    ArrayList<ViewTransition.Animate> removeList = new ArrayList<>();
    private ArrayList<ViewTransition> viewTransitions = new ArrayList<>();

    public ViewTransitionController(MotionLayout layout) {
        this.mMotionLayout = layout;
    }

    public void add(ViewTransition viewTransition) {
        this.viewTransitions.add(viewTransition);
        this.mRelatedViews = null;
        if (viewTransition.getStateTransition() == 4) {
            listenForSharedVariable(viewTransition, true);
        } else if (viewTransition.getStateTransition() == 5) {
            listenForSharedVariable(viewTransition, false);
        }
    }

    /* access modifiers changed from: package-private */
    public void remove(int id) {
        ViewTransition del = null;
        Iterator<ViewTransition> it = this.viewTransitions.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            ViewTransition viewTransition = it.next();
            if (viewTransition.getId() == id) {
                del = viewTransition;
                break;
            }
        }
        if (del != null) {
            this.mRelatedViews = null;
            this.viewTransitions.remove(del);
        }
    }

    private void viewTransition(ViewTransition vt, View... view) {
        int currentId = this.mMotionLayout.getCurrentState();
        if (vt.mViewTransitionMode == 2) {
            vt.applyTransition(this, this.mMotionLayout, currentId, (ConstraintSet) null, view);
        } else if (currentId == -1) {
            Log.w(this.TAG, "No support for ViewTransition within transition yet. Currently: " + this.mMotionLayout.toString());
        } else {
            ConstraintSet current = this.mMotionLayout.getConstraintSet(currentId);
            if (current != null) {
                vt.applyTransition(this, this.mMotionLayout, currentId, current, view);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void enableViewTransition(int id, boolean enable) {
        Iterator<ViewTransition> it = this.viewTransitions.iterator();
        while (it.hasNext()) {
            ViewTransition viewTransition = it.next();
            if (viewTransition.getId() == id) {
                viewTransition.setEnabled(enable);
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isViewTransitionEnabled(int id) {
        Iterator<ViewTransition> it = this.viewTransitions.iterator();
        while (it.hasNext()) {
            ViewTransition viewTransition = it.next();
            if (viewTransition.getId() == id) {
                return viewTransition.isEnabled();
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void viewTransition(int id, View... views) {
        ViewTransition vt = null;
        ArrayList<View> list = new ArrayList<>();
        Iterator<ViewTransition> it = this.viewTransitions.iterator();
        while (it.hasNext()) {
            ViewTransition viewTransition = it.next();
            if (viewTransition.getId() == id) {
                vt = viewTransition;
                for (View view : views) {
                    if (viewTransition.checkTags(view)) {
                        list.add(view);
                    }
                }
                if (!list.isEmpty()) {
                    viewTransition(vt, (View[]) list.toArray(new View[0]));
                    list.clear();
                }
            }
        }
        if (vt == null) {
            Log.e(this.TAG, " Could not find ViewTransition");
        }
    }

    /* access modifiers changed from: package-private */
    public void touchEvent(MotionEvent event) {
        int currentId = this.mMotionLayout.getCurrentState();
        if (currentId != -1) {
            if (this.mRelatedViews == null) {
                this.mRelatedViews = new HashSet<>();
                Iterator<ViewTransition> it = this.viewTransitions.iterator();
                while (it.hasNext()) {
                    ViewTransition viewTransition = it.next();
                    int count = this.mMotionLayout.getChildCount();
                    for (int i = 0; i < count; i++) {
                        View view = this.mMotionLayout.getChildAt(i);
                        if (viewTransition.matchesView(view)) {
                            int id = view.getId();
                            this.mRelatedViews.add(view);
                        }
                    }
                }
            }
            float x = event.getX();
            float y = event.getY();
            Rect rec = new Rect();
            int action = event.getAction();
            ArrayList<ViewTransition.Animate> arrayList = this.animations;
            if (arrayList != null && !arrayList.isEmpty()) {
                Iterator<ViewTransition.Animate> it2 = this.animations.iterator();
                while (it2.hasNext()) {
                    it2.next().reactTo(action, x, y);
                }
            }
            switch (action) {
                case 0:
                case 1:
                    ConstraintSet current = this.mMotionLayout.getConstraintSet(currentId);
                    Iterator<ViewTransition> it3 = this.viewTransitions.iterator();
                    while (it3.hasNext()) {
                        ViewTransition viewTransition2 = it3.next();
                        if (viewTransition2.supports(action)) {
                            Iterator<View> it4 = this.mRelatedViews.iterator();
                            while (it4.hasNext()) {
                                View view2 = it4.next();
                                if (viewTransition2.matchesView(view2)) {
                                    view2.getHitRect(rec);
                                    if (rec.contains((int) x, (int) y)) {
                                        View view3 = view2;
                                        viewTransition2.applyTransition(this, this.mMotionLayout, currentId, current, view2);
                                    }
                                }
                            }
                        }
                    }
                    return;
                default:
                    return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void addAnimation(ViewTransition.Animate animation) {
        if (this.animations == null) {
            this.animations = new ArrayList<>();
        }
        this.animations.add(animation);
    }

    /* access modifiers changed from: package-private */
    public void removeAnimation(ViewTransition.Animate animation) {
        this.removeList.add(animation);
    }

    /* access modifiers changed from: package-private */
    public void animate() {
        ArrayList<ViewTransition.Animate> arrayList = this.animations;
        if (arrayList != null) {
            Iterator<ViewTransition.Animate> it = arrayList.iterator();
            while (it.hasNext()) {
                it.next().mutate();
            }
            this.animations.removeAll(this.removeList);
            this.removeList.clear();
            if (this.animations.isEmpty()) {
                this.animations = null;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void invalidate() {
        this.mMotionLayout.invalidate();
    }

    /* access modifiers changed from: package-private */
    public boolean applyViewTransition(int viewTransitionId, MotionController motionController) {
        Iterator<ViewTransition> it = this.viewTransitions.iterator();
        while (it.hasNext()) {
            ViewTransition viewTransition = it.next();
            if (viewTransition.getId() == viewTransitionId) {
                viewTransition.mKeyFrames.addAllFrames(motionController);
                return true;
            }
        }
        return false;
    }

    private void listenForSharedVariable(ViewTransition viewTransition, boolean isSet) {
        int listen_for_id = viewTransition.getSharedValueID();
        final ViewTransition viewTransition2 = viewTransition;
        final int i = listen_for_id;
        final boolean z = isSet;
        final int sharedValue = viewTransition.getSharedValue();
        ConstraintLayout.getSharedValues().addListener(viewTransition.getSharedValueID(), new SharedValues.SharedValuesListener() {
            public void onNewValue(int id, int value, int oldValue) {
                int i = value;
                int current_value = viewTransition2.getSharedValueCurrent();
                viewTransition2.setSharedValueCurrent(i);
                if (i == id && current_value != i) {
                    if (z) {
                        if (sharedValue == i) {
                            int count = ViewTransitionController.this.mMotionLayout.getChildCount();
                            for (int i2 = 0; i2 < count; i2++) {
                                View view = ViewTransitionController.this.mMotionLayout.getChildAt(i2);
                                if (viewTransition2.matchesView(view)) {
                                    int currentId = ViewTransitionController.this.mMotionLayout.getCurrentState();
                                    ConstraintSet current = ViewTransitionController.this.mMotionLayout.getConstraintSet(currentId);
                                    ViewTransition viewTransition = viewTransition2;
                                    ViewTransitionController viewTransitionController = ViewTransitionController.this;
                                    viewTransition.applyTransition(viewTransitionController, viewTransitionController.mMotionLayout, currentId, current, view);
                                }
                            }
                        }
                    } else if (sharedValue != i) {
                        int count2 = ViewTransitionController.this.mMotionLayout.getChildCount();
                        for (int i3 = 0; i3 < count2; i3++) {
                            View view2 = ViewTransitionController.this.mMotionLayout.getChildAt(i3);
                            if (viewTransition2.matchesView(view2)) {
                                int currentId2 = ViewTransitionController.this.mMotionLayout.getCurrentState();
                                ConstraintSet current2 = ViewTransitionController.this.mMotionLayout.getConstraintSet(currentId2);
                                ViewTransition viewTransition2 = viewTransition2;
                                ViewTransitionController viewTransitionController2 = ViewTransitionController.this;
                                viewTransition2.applyTransition(viewTransitionController2, viewTransitionController2.mMotionLayout, currentId2, current2, view2);
                            }
                        }
                    }
                }
            }
        });
    }
}
