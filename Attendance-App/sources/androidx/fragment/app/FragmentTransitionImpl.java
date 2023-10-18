package androidx.fragment.app;

import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.core.p001os.CancellationSignal;
import androidx.core.view.OneShotPreDrawListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewGroupCompat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class FragmentTransitionImpl {
    public abstract void addTarget(Object obj, View view);

    public abstract void addTargets(Object obj, ArrayList<View> arrayList);

    public abstract void beginDelayedTransition(ViewGroup viewGroup, Object obj);

    public abstract boolean canHandle(Object obj);

    public abstract Object cloneTransition(Object obj);

    public abstract Object mergeTransitionsInSequence(Object obj, Object obj2, Object obj3);

    public abstract Object mergeTransitionsTogether(Object obj, Object obj2, Object obj3);

    public abstract void removeTarget(Object obj, View view);

    public abstract void replaceTargets(Object obj, ArrayList<View> arrayList, ArrayList<View> arrayList2);

    public abstract void scheduleHideFragmentView(Object obj, View view, ArrayList<View> arrayList);

    public abstract void scheduleRemoveTargets(Object obj, Object obj2, ArrayList<View> arrayList, Object obj3, ArrayList<View> arrayList2, Object obj4, ArrayList<View> arrayList3);

    public abstract void setEpicenter(Object obj, Rect rect);

    public abstract void setEpicenter(Object obj, View view);

    public abstract void setSharedElementTargets(Object obj, View view, ArrayList<View> arrayList);

    public abstract void swapSharedElementTargets(Object obj, ArrayList<View> arrayList, ArrayList<View> arrayList2);

    public abstract Object wrapTransitionInSet(Object obj);

    /* access modifiers changed from: protected */
    public void getBoundsOnScreen(View view, Rect epicenter) {
        if (ViewCompat.isAttachedToWindow(view)) {
            RectF rect = new RectF();
            rect.set(0.0f, 0.0f, (float) view.getWidth(), (float) view.getHeight());
            view.getMatrix().mapRect(rect);
            rect.offset((float) view.getLeft(), (float) view.getTop());
            ViewParent parent = view.getParent();
            while (parent instanceof View) {
                View parentView = (View) parent;
                rect.offset((float) (-parentView.getScrollX()), (float) (-parentView.getScrollY()));
                parentView.getMatrix().mapRect(rect);
                rect.offset((float) parentView.getLeft(), (float) parentView.getTop());
                parent = parentView.getParent();
            }
            int[] loc = new int[2];
            view.getRootView().getLocationOnScreen(loc);
            rect.offset((float) loc[0], (float) loc[1]);
            epicenter.set(Math.round(rect.left), Math.round(rect.top), Math.round(rect.right), Math.round(rect.bottom));
        }
    }

    /* access modifiers changed from: package-private */
    public ArrayList<String> prepareSetNameOverridesReordered(ArrayList<View> sharedElementsIn) {
        ArrayList<String> names = new ArrayList<>();
        int numSharedElements = sharedElementsIn.size();
        for (int i = 0; i < numSharedElements; i++) {
            View view = sharedElementsIn.get(i);
            names.add(ViewCompat.getTransitionName(view));
            ViewCompat.setTransitionName(view, (String) null);
        }
        return names;
    }

    /* access modifiers changed from: package-private */
    public void setNameOverridesReordered(View sceneRoot, ArrayList<View> sharedElementsOut, ArrayList<View> sharedElementsIn, ArrayList<String> inNames, Map<String, String> nameOverrides) {
        int numSharedElements = sharedElementsIn.size();
        ArrayList<String> outNames = new ArrayList<>();
        for (int i = 0; i < numSharedElements; i++) {
            View view = sharedElementsOut.get(i);
            String name = ViewCompat.getTransitionName(view);
            outNames.add(name);
            if (name != null) {
                ViewCompat.setTransitionName(view, (String) null);
                String inName = nameOverrides.get(name);
                int j = 0;
                while (true) {
                    if (j >= numSharedElements) {
                        break;
                    } else if (inName.equals(inNames.get(j))) {
                        ViewCompat.setTransitionName(sharedElementsIn.get(j), name);
                        break;
                    } else {
                        j++;
                    }
                }
            }
        }
        final int i2 = numSharedElements;
        final ArrayList<View> arrayList = sharedElementsIn;
        final ArrayList<String> arrayList2 = inNames;
        final ArrayList<View> arrayList3 = sharedElementsOut;
        final ArrayList<String> arrayList4 = outNames;
        OneShotPreDrawListener.add(sceneRoot, new Runnable() {
            public void run() {
                for (int i = 0; i < i2; i++) {
                    ViewCompat.setTransitionName((View) arrayList.get(i), (String) arrayList2.get(i));
                    ViewCompat.setTransitionName((View) arrayList3.get(i), (String) arrayList4.get(i));
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void captureTransitioningViews(ArrayList<View> transitioningViews, View view) {
        if (view.getVisibility() != 0) {
            return;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (ViewGroupCompat.isTransitionGroup(viewGroup)) {
                transitioningViews.add(viewGroup);
                return;
            }
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                captureTransitioningViews(transitioningViews, viewGroup.getChildAt(i));
            }
            return;
        }
        transitioningViews.add(view);
    }

    /* access modifiers changed from: package-private */
    public void findNamedViews(Map<String, View> namedViews, View view) {
        if (view.getVisibility() == 0) {
            String transitionName = ViewCompat.getTransitionName(view);
            if (transitionName != null) {
                namedViews.put(transitionName, view);
            }
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                int count = viewGroup.getChildCount();
                for (int i = 0; i < count; i++) {
                    findNamedViews(namedViews, viewGroup.getChildAt(i));
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setNameOverridesOrdered(View sceneRoot, final ArrayList<View> sharedElementsIn, final Map<String, String> nameOverrides) {
        OneShotPreDrawListener.add(sceneRoot, new Runnable() {
            public void run() {
                int numSharedElements = sharedElementsIn.size();
                for (int i = 0; i < numSharedElements; i++) {
                    View view = (View) sharedElementsIn.get(i);
                    String name = ViewCompat.getTransitionName(view);
                    if (name != null) {
                        ViewCompat.setTransitionName(view, FragmentTransitionImpl.findKeyForValue(nameOverrides, name));
                    }
                }
            }
        });
    }

    public void setListenerForTransitionEnd(Fragment outFragment, Object transition, CancellationSignal signal, Runnable transitionCompleteRunnable) {
        transitionCompleteRunnable.run();
    }

    /* access modifiers changed from: package-private */
    public void scheduleNameReset(ViewGroup sceneRoot, final ArrayList<View> sharedElementsIn, final Map<String, String> nameOverrides) {
        OneShotPreDrawListener.add(sceneRoot, new Runnable() {
            public void run() {
                int numSharedElements = sharedElementsIn.size();
                for (int i = 0; i < numSharedElements; i++) {
                    View view = (View) sharedElementsIn.get(i);
                    ViewCompat.setTransitionName(view, (String) nameOverrides.get(ViewCompat.getTransitionName(view)));
                }
            }
        });
    }

    protected static void bfsAddViewChildren(List<View> views, View startView) {
        int startIndex = views.size();
        if (!containedBeforeIndex(views, startView, startIndex)) {
            if (ViewCompat.getTransitionName(startView) != null) {
                views.add(startView);
            }
            for (int index = startIndex; index < views.size(); index++) {
                View view = views.get(index);
                if (view instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) view;
                    int childCount = viewGroup.getChildCount();
                    for (int childIndex = 0; childIndex < childCount; childIndex++) {
                        View child = viewGroup.getChildAt(childIndex);
                        if (!containedBeforeIndex(views, child, startIndex) && ViewCompat.getTransitionName(child) != null) {
                            views.add(child);
                        }
                    }
                }
            }
        }
    }

    private static boolean containedBeforeIndex(List<View> views, View view, int maxIndex) {
        for (int i = 0; i < maxIndex; i++) {
            if (views.get(i) == view) {
                return true;
            }
        }
        return false;
    }

    protected static boolean isNullOrEmpty(List list) {
        return list == null || list.isEmpty();
    }

    static String findKeyForValue(Map<String, String> map, String value) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
