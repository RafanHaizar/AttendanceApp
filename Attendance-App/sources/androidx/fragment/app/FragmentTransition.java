package androidx.fragment.app;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.collection.ArrayMap;
import androidx.core.app.SharedElementCallback;
import androidx.core.p001os.CancellationSignal;
import androidx.core.view.OneShotPreDrawListener;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentTransaction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class FragmentTransition {
    private static final int[] INVERSE_OPS = {0, 3, 0, 1, 5, 4, 7, 6, 9, 8, 10};
    static final FragmentTransitionImpl PLATFORM_IMPL;
    static final FragmentTransitionImpl SUPPORT_IMPL = resolveSupportImpl();

    interface Callback {
        void onComplete(Fragment fragment, CancellationSignal cancellationSignal);

        void onStart(Fragment fragment, CancellationSignal cancellationSignal);
    }

    static {
        FragmentTransitionCompat21 fragmentTransitionCompat21;
        if (Build.VERSION.SDK_INT >= 21) {
            fragmentTransitionCompat21 = new FragmentTransitionCompat21();
        } else {
            fragmentTransitionCompat21 = null;
        }
        PLATFORM_IMPL = fragmentTransitionCompat21;
    }

    private static FragmentTransitionImpl resolveSupportImpl() {
        try {
            return (FragmentTransitionImpl) Class.forName("androidx.transition.FragmentTransitionSupport").getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            return null;
        }
    }

    static void startTransitions(Context context, FragmentContainer fragmentContainer, ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop, int startIndex, int endIndex, boolean isReordered, Callback callback) {
        ViewGroup container;
        SparseArray<FragmentContainerTransition> transitioningFragments = new SparseArray<>();
        for (int i = startIndex; i < endIndex; i++) {
            BackStackRecord record = records.get(i);
            if (isRecordPop.get(i).booleanValue()) {
                calculatePopFragments(record, transitioningFragments, isReordered);
            } else {
                calculateFragments(record, transitioningFragments, isReordered);
            }
        }
        if (transitioningFragments.size() != 0) {
            View nonExistentView = new View(context);
            int numContainers = transitioningFragments.size();
            for (int i2 = 0; i2 < numContainers; i2++) {
                int containerId = transitioningFragments.keyAt(i2);
                ArrayMap<String, String> nameOverrides = calculateNameOverrides(containerId, records, isRecordPop, startIndex, endIndex);
                FragmentContainerTransition containerTransition = transitioningFragments.valueAt(i2);
                if (fragmentContainer.onHasView() && (container = (ViewGroup) fragmentContainer.onFindViewById(containerId)) != null) {
                    if (isReordered) {
                        configureTransitionsReordered(container, containerTransition, nonExistentView, nameOverrides, callback);
                    } else {
                        configureTransitionsOrdered(container, containerTransition, nonExistentView, nameOverrides, callback);
                    }
                }
            }
        }
    }

    private static ArrayMap<String, String> calculateNameOverrides(int containerId, ArrayList<BackStackRecord> records, ArrayList<Boolean> isRecordPop, int startIndex, int endIndex) {
        ArrayList<String> sources;
        ArrayList<String> targets;
        ArrayMap<String, String> nameOverrides = new ArrayMap<>();
        for (int recordNum = endIndex - 1; recordNum >= startIndex; recordNum--) {
            BackStackRecord record = records.get(recordNum);
            if (record.interactsWith(containerId)) {
                boolean isPop = isRecordPop.get(recordNum).booleanValue();
                if (record.mSharedElementSourceNames != null) {
                    int numSharedElements = record.mSharedElementSourceNames.size();
                    if (isPop) {
                        targets = record.mSharedElementSourceNames;
                        sources = record.mSharedElementTargetNames;
                    } else {
                        sources = record.mSharedElementSourceNames;
                        targets = record.mSharedElementTargetNames;
                    }
                    for (int i = 0; i < numSharedElements; i++) {
                        String sourceName = sources.get(i);
                        String targetName = targets.get(i);
                        String previousTarget = nameOverrides.remove(targetName);
                        if (previousTarget != null) {
                            nameOverrides.put(sourceName, previousTarget);
                        } else {
                            nameOverrides.put(sourceName, targetName);
                        }
                    }
                }
            }
        }
        return nameOverrides;
    }

    private static void configureTransitionsReordered(ViewGroup container, FragmentContainerTransition fragments, View nonExistentView, ArrayMap<String, String> nameOverrides, Callback callback) {
        Object exitTransition;
        ArrayList<View> sharedElementsIn;
        FragmentContainerTransition fragmentContainerTransition = fragments;
        View view = nonExistentView;
        Callback callback2 = callback;
        Fragment inFragment = fragmentContainerTransition.lastIn;
        final Fragment outFragment = fragmentContainerTransition.firstOut;
        FragmentTransitionImpl impl = chooseImpl(outFragment, inFragment);
        if (impl != null) {
            boolean inIsPop = fragmentContainerTransition.lastInIsPop;
            boolean outIsPop = fragmentContainerTransition.firstOutIsPop;
            ArrayList<View> sharedElementsIn2 = new ArrayList<>();
            ArrayList<View> sharedElementsOut = new ArrayList<>();
            Object enterTransition = getEnterTransition(impl, inFragment, inIsPop);
            Object exitTransition2 = getExitTransition(impl, outFragment, outIsPop);
            Object enterTransition2 = enterTransition;
            ArrayList<View> arrayList = sharedElementsOut;
            ArrayList<View> sharedElementsOut2 = sharedElementsOut;
            ArrayList<View> sharedElementsOut3 = sharedElementsIn2;
            ArrayList<View> sharedElementsIn3 = sharedElementsIn2;
            Object enterTransition3 = enterTransition2;
            boolean z = outIsPop;
            Object sharedElementTransition = configureSharedElementsReordered(impl, container, nonExistentView, nameOverrides, fragments, arrayList, sharedElementsOut3, enterTransition3, exitTransition2);
            if (enterTransition3 == null && sharedElementTransition == null) {
                exitTransition = exitTransition2;
                if (exitTransition == null) {
                    return;
                }
            } else {
                exitTransition = exitTransition2;
            }
            ArrayList<View> exitingViews = configureEnteringExitingViews(impl, exitTransition, outFragment, sharedElementsOut2, view);
            ArrayList<View> enteringViews = configureEnteringExitingViews(impl, enterTransition3, inFragment, sharedElementsIn3, view);
            setViewVisibility(enteringViews, 4);
            ArrayList<View> enteringViews2 = enteringViews;
            ArrayList<View> exitingViews2 = exitingViews;
            Object transition = mergeTransitions(impl, enterTransition3, exitTransition, sharedElementTransition, inFragment, inIsPop);
            if (outFragment == null || exitingViews2 == null) {
                sharedElementsIn = sharedElementsIn3;
                Callback callback3 = callback;
            } else if (exitingViews2.size() > 0 || sharedElementsOut2.size() > 0) {
                final CancellationSignal signal = new CancellationSignal();
                sharedElementsIn = sharedElementsIn3;
                final Callback callback4 = callback;
                callback4.onStart(outFragment, signal);
                impl.setListenerForTransitionEnd(outFragment, transition, signal, new Runnable() {
                    public void run() {
                        Callback.this.onComplete(outFragment, signal);
                    }
                });
            } else {
                sharedElementsIn = sharedElementsIn3;
                Callback callback5 = callback;
            }
            if (transition != null) {
                replaceHide(impl, exitTransition, outFragment, exitingViews2);
                ArrayList<String> inNames = impl.prepareSetNameOverridesReordered(sharedElementsIn);
                FragmentTransitionImpl fragmentTransitionImpl = impl;
                ArrayList<View> sharedElementsIn4 = sharedElementsIn;
                Object obj = exitTransition;
                Object obj2 = enterTransition3;
                fragmentTransitionImpl.scheduleRemoveTargets(transition, enterTransition3, enteringViews2, exitTransition, exitingViews2, sharedElementTransition, sharedElementsIn4);
                impl.beginDelayedTransition(container, transition);
                fragmentTransitionImpl.setNameOverridesReordered(container, sharedElementsOut2, sharedElementsIn4, inNames, nameOverrides);
                setViewVisibility(enteringViews2, 0);
                impl.swapSharedElementTargets(sharedElementTransition, sharedElementsOut2, sharedElementsIn4);
                return;
            }
            Object obj3 = transition;
            Object obj4 = exitTransition;
            Object obj5 = enterTransition3;
            ArrayList<View> arrayList2 = enteringViews2;
            Object exitTransition3 = container;
        }
    }

    private static void replaceHide(FragmentTransitionImpl impl, Object exitTransition, Fragment exitingFragment, final ArrayList<View> exitingViews) {
        if (exitingFragment != null && exitTransition != null && exitingFragment.mAdded && exitingFragment.mHidden && exitingFragment.mHiddenChanged) {
            exitingFragment.setHideReplaced(true);
            impl.scheduleHideFragmentView(exitTransition, exitingFragment.getView(), exitingViews);
            OneShotPreDrawListener.add(exitingFragment.mContainer, new Runnable() {
                public void run() {
                    FragmentTransition.setViewVisibility(exitingViews, 4);
                }
            });
        }
    }

    private static void configureTransitionsOrdered(ViewGroup container, FragmentContainerTransition fragments, View nonExistentView, ArrayMap<String, String> nameOverrides, Callback callback) {
        Object exitTransition;
        Object exitTransition2;
        ViewGroup viewGroup = container;
        FragmentContainerTransition fragmentContainerTransition = fragments;
        View view = nonExistentView;
        ArrayMap<String, String> arrayMap = nameOverrides;
        final Callback callback2 = callback;
        Fragment inFragment = fragmentContainerTransition.lastIn;
        final Fragment outFragment = fragmentContainerTransition.firstOut;
        FragmentTransitionImpl impl = chooseImpl(outFragment, inFragment);
        if (impl != null) {
            boolean inIsPop = fragmentContainerTransition.lastInIsPop;
            boolean outIsPop = fragmentContainerTransition.firstOutIsPop;
            Object enterTransition = getEnterTransition(impl, inFragment, inIsPop);
            Object exitTransition3 = getExitTransition(impl, outFragment, outIsPop);
            ArrayList<View> sharedElementsOut = new ArrayList<>();
            ArrayList<View> sharedElementsIn = new ArrayList<>();
            ArrayList<View> sharedElementsOut2 = sharedElementsOut;
            Object exitTransition4 = exitTransition3;
            Object enterTransition2 = enterTransition;
            boolean z = outIsPop;
            boolean z2 = inIsPop;
            FragmentTransitionImpl impl2 = impl;
            Object sharedElementTransition = configureSharedElementsOrdered(impl, container, nonExistentView, nameOverrides, fragments, sharedElementsOut2, sharedElementsIn, enterTransition2, exitTransition4);
            Object enterTransition3 = enterTransition2;
            if (enterTransition3 == null && sharedElementTransition == null) {
                exitTransition = exitTransition4;
                if (exitTransition == null) {
                    return;
                }
            } else {
                exitTransition = exitTransition4;
            }
            ArrayList<View> sharedElementsOut3 = sharedElementsOut2;
            ArrayList<View> sharedElementsOut4 = configureEnteringExitingViews(impl2, exitTransition, outFragment, sharedElementsOut3, view);
            if (sharedElementsOut4 == null || sharedElementsOut4.isEmpty()) {
                exitTransition2 = null;
            } else {
                exitTransition2 = exitTransition;
            }
            impl2.addTarget(enterTransition3, view);
            Object transition = mergeTransitions(impl2, enterTransition3, exitTransition2, sharedElementTransition, inFragment, fragmentContainerTransition.lastInIsPop);
            if (!(outFragment == null || sharedElementsOut4 == null || (sharedElementsOut4.size() <= 0 && sharedElementsOut3.size() <= 0))) {
                final CancellationSignal signal = new CancellationSignal();
                callback2.onStart(outFragment, signal);
                impl2.setListenerForTransitionEnd(outFragment, transition, signal, new Runnable() {
                    public void run() {
                        Callback.this.onComplete(outFragment, signal);
                    }
                });
            }
            if (transition != null) {
                ArrayList<View> enteringViews = new ArrayList<>();
                FragmentTransitionImpl impl3 = impl2;
                impl3.scheduleRemoveTargets(transition, enterTransition3, enteringViews, exitTransition2, sharedElementsOut4, sharedElementTransition, sharedElementsIn);
                ArrayList<View> arrayList = sharedElementsOut3;
                scheduleTargetChange(impl2, container, inFragment, nonExistentView, sharedElementsIn, enterTransition3, enteringViews, exitTransition2, sharedElementsOut4);
                ViewGroup viewGroup2 = container;
                FragmentTransitionImpl impl4 = impl3;
                ArrayList<View> sharedElementsIn2 = sharedElementsIn;
                impl4.setNameOverridesOrdered(viewGroup2, sharedElementsIn2, arrayMap);
                impl4.beginDelayedTransition(viewGroup2, transition);
                impl4.scheduleNameReset(viewGroup2, sharedElementsIn2, arrayMap);
                return;
            }
            ViewGroup viewGroup3 = container;
            ArrayList<View> arrayList2 = sharedElementsOut3;
            Object obj = enterTransition3;
            FragmentTransitionImpl fragmentTransitionImpl = impl2;
            ArrayList<View> arrayList3 = sharedElementsIn;
            Object obj2 = transition;
        }
    }

    private static void scheduleTargetChange(FragmentTransitionImpl impl, ViewGroup sceneRoot, Fragment inFragment, View nonExistentView, ArrayList<View> sharedElementsIn, Object enterTransition, ArrayList<View> enteringViews, Object exitTransition, ArrayList<View> exitingViews) {
        final Object obj = enterTransition;
        final FragmentTransitionImpl fragmentTransitionImpl = impl;
        final View view = nonExistentView;
        final Fragment fragment = inFragment;
        final ArrayList<View> arrayList = sharedElementsIn;
        final ArrayList<View> arrayList2 = enteringViews;
        final ArrayList<View> arrayList3 = exitingViews;
        final Object obj2 = exitTransition;
        ViewGroup viewGroup = sceneRoot;
        OneShotPreDrawListener.add(sceneRoot, new Runnable() {
            public void run() {
                Object obj = obj;
                if (obj != null) {
                    fragmentTransitionImpl.removeTarget(obj, view);
                    arrayList2.addAll(FragmentTransition.configureEnteringExitingViews(fragmentTransitionImpl, obj, fragment, arrayList, view));
                }
                if (arrayList3 != null) {
                    if (obj2 != null) {
                        ArrayList<View> tempExiting = new ArrayList<>();
                        tempExiting.add(view);
                        fragmentTransitionImpl.replaceTargets(obj2, arrayList3, tempExiting);
                    }
                    arrayList3.clear();
                    arrayList3.add(view);
                }
            }
        });
    }

    private static FragmentTransitionImpl chooseImpl(Fragment outFragment, Fragment inFragment) {
        ArrayList<Object> transitions = new ArrayList<>();
        if (outFragment != null) {
            Object exitTransition = outFragment.getExitTransition();
            if (exitTransition != null) {
                transitions.add(exitTransition);
            }
            Object returnTransition = outFragment.getReturnTransition();
            if (returnTransition != null) {
                transitions.add(returnTransition);
            }
            Object sharedReturnTransition = outFragment.getSharedElementReturnTransition();
            if (sharedReturnTransition != null) {
                transitions.add(sharedReturnTransition);
            }
        }
        if (inFragment != null) {
            Object enterTransition = inFragment.getEnterTransition();
            if (enterTransition != null) {
                transitions.add(enterTransition);
            }
            Object reenterTransition = inFragment.getReenterTransition();
            if (reenterTransition != null) {
                transitions.add(reenterTransition);
            }
            Object sharedEnterTransition = inFragment.getSharedElementEnterTransition();
            if (sharedEnterTransition != null) {
                transitions.add(sharedEnterTransition);
            }
        }
        if (transitions.isEmpty()) {
            return null;
        }
        FragmentTransitionImpl fragmentTransitionImpl = PLATFORM_IMPL;
        if (fragmentTransitionImpl != null && canHandleAll(fragmentTransitionImpl, transitions)) {
            return fragmentTransitionImpl;
        }
        FragmentTransitionImpl fragmentTransitionImpl2 = SUPPORT_IMPL;
        if (fragmentTransitionImpl2 != null && canHandleAll(fragmentTransitionImpl2, transitions)) {
            return fragmentTransitionImpl2;
        }
        if (fragmentTransitionImpl == null && fragmentTransitionImpl2 == null) {
            return null;
        }
        throw new IllegalArgumentException("Invalid Transition types");
    }

    private static boolean canHandleAll(FragmentTransitionImpl impl, List<Object> transitions) {
        int size = transitions.size();
        for (int i = 0; i < size; i++) {
            if (!impl.canHandle(transitions.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static Object getSharedElementTransition(FragmentTransitionImpl impl, Fragment inFragment, Fragment outFragment, boolean isPop) {
        Object obj;
        if (inFragment == null || outFragment == null) {
            return null;
        }
        if (isPop) {
            obj = outFragment.getSharedElementReturnTransition();
        } else {
            obj = inFragment.getSharedElementEnterTransition();
        }
        return impl.wrapTransitionInSet(impl.cloneTransition(obj));
    }

    private static Object getEnterTransition(FragmentTransitionImpl impl, Fragment inFragment, boolean isPop) {
        Object obj;
        if (inFragment == null) {
            return null;
        }
        if (isPop) {
            obj = inFragment.getReenterTransition();
        } else {
            obj = inFragment.getEnterTransition();
        }
        return impl.cloneTransition(obj);
    }

    private static Object getExitTransition(FragmentTransitionImpl impl, Fragment outFragment, boolean isPop) {
        Object obj;
        if (outFragment == null) {
            return null;
        }
        if (isPop) {
            obj = outFragment.getReturnTransition();
        } else {
            obj = outFragment.getExitTransition();
        }
        return impl.cloneTransition(obj);
    }

    private static Object configureSharedElementsReordered(FragmentTransitionImpl impl, ViewGroup sceneRoot, View nonExistentView, ArrayMap<String, String> nameOverrides, FragmentContainerTransition fragments, ArrayList<View> sharedElementsOut, ArrayList<View> sharedElementsIn, Object enterTransition, Object exitTransition) {
        Object sharedElementTransition;
        Object sharedElementTransition2;
        Object sharedElementTransition3;
        View epicenterView;
        Rect epicenter;
        ArrayMap<String, View> inSharedElements;
        FragmentTransitionImpl fragmentTransitionImpl = impl;
        View view = nonExistentView;
        ArrayMap<String, String> arrayMap = nameOverrides;
        FragmentContainerTransition fragmentContainerTransition = fragments;
        ArrayList<View> arrayList = sharedElementsOut;
        ArrayList<View> arrayList2 = sharedElementsIn;
        Object obj = enterTransition;
        Fragment inFragment = fragmentContainerTransition.lastIn;
        Fragment outFragment = fragmentContainerTransition.firstOut;
        if (inFragment != null) {
            inFragment.requireView().setVisibility(0);
        }
        if (inFragment == null) {
            ViewGroup viewGroup = sceneRoot;
            Fragment fragment = outFragment;
        } else if (outFragment == null) {
            ViewGroup viewGroup2 = sceneRoot;
            Fragment fragment2 = outFragment;
        } else {
            boolean inIsPop = fragmentContainerTransition.lastInIsPop;
            if (nameOverrides.isEmpty()) {
                sharedElementTransition = null;
            } else {
                sharedElementTransition = getSharedElementTransition(fragmentTransitionImpl, inFragment, outFragment, inIsPop);
            }
            ArrayMap<String, View> outSharedElements = captureOutSharedElements(fragmentTransitionImpl, arrayMap, sharedElementTransition, fragmentContainerTransition);
            ArrayMap<String, View> inSharedElements2 = captureInSharedElements(fragmentTransitionImpl, arrayMap, sharedElementTransition, fragmentContainerTransition);
            if (nameOverrides.isEmpty()) {
                if (outSharedElements != null) {
                    outSharedElements.clear();
                }
                if (inSharedElements2 != null) {
                    inSharedElements2.clear();
                }
                sharedElementTransition2 = null;
            } else {
                addSharedElementsWithMatchingNames(arrayList, outSharedElements, nameOverrides.keySet());
                addSharedElementsWithMatchingNames(arrayList2, inSharedElements2, nameOverrides.values());
                sharedElementTransition2 = sharedElementTransition;
            }
            if (obj == null && exitTransition == null && sharedElementTransition2 == null) {
                return null;
            }
            callSharedElementStartEnd(inFragment, outFragment, inIsPop, outSharedElements, true);
            if (sharedElementTransition2 != null) {
                arrayList2.add(view);
                fragmentTransitionImpl.setSharedElementTargets(sharedElementTransition2, view, arrayList);
                sharedElementTransition3 = sharedElementTransition2;
                inSharedElements = inSharedElements2;
                ArrayMap<String, View> arrayMap2 = outSharedElements;
                setOutEpicenter(impl, sharedElementTransition2, exitTransition, outSharedElements, fragmentContainerTransition.firstOutIsPop, fragmentContainerTransition.firstOutTransaction);
                Rect epicenter2 = new Rect();
                View epicenterView2 = getInEpicenterView(inSharedElements, fragmentContainerTransition, obj, inIsPop);
                if (epicenterView2 != null) {
                    fragmentTransitionImpl.setEpicenter(obj, epicenter2);
                }
                epicenter = epicenter2;
                epicenterView = epicenterView2;
            } else {
                sharedElementTransition3 = sharedElementTransition2;
                inSharedElements = inSharedElements2;
                ArrayMap<String, View> arrayMap3 = outSharedElements;
                epicenter = null;
                epicenterView = null;
            }
            final Fragment fragment3 = inFragment;
            final Fragment fragment4 = outFragment;
            final boolean z = inIsPop;
            final ArrayMap<String, View> arrayMap4 = inSharedElements;
            C08615 r8 = r0;
            final View view2 = epicenterView;
            boolean z2 = inIsPop;
            final FragmentTransitionImpl fragmentTransitionImpl2 = impl;
            Fragment fragment5 = outFragment;
            final Rect rect = epicenter;
            C08615 r0 = new Runnable() {
                public void run() {
                    FragmentTransition.callSharedElementStartEnd(Fragment.this, fragment4, z, arrayMap4, false);
                    View view = view2;
                    if (view != null) {
                        fragmentTransitionImpl2.getBoundsOnScreen(view, rect);
                    }
                }
            };
            OneShotPreDrawListener.add(sceneRoot, r8);
            return sharedElementTransition3;
        }
        return null;
    }

    private static void addSharedElementsWithMatchingNames(ArrayList<View> views, ArrayMap<String, View> sharedElements, Collection<String> nameOverridesSet) {
        for (int i = sharedElements.size() - 1; i >= 0; i--) {
            View view = sharedElements.valueAt(i);
            if (nameOverridesSet.contains(ViewCompat.getTransitionName(view))) {
                views.add(view);
            }
        }
    }

    private static Object configureSharedElementsOrdered(FragmentTransitionImpl impl, ViewGroup sceneRoot, View nonExistentView, ArrayMap<String, String> nameOverrides, FragmentContainerTransition fragments, ArrayList<View> sharedElementsOut, ArrayList<View> sharedElementsIn, Object enterTransition, Object exitTransition) {
        Object sharedElementTransition;
        Object sharedElementTransition2;
        Rect inEpicenter;
        FragmentTransitionImpl fragmentTransitionImpl = impl;
        FragmentContainerTransition fragmentContainerTransition = fragments;
        ArrayList<View> arrayList = sharedElementsOut;
        Object obj = enterTransition;
        Fragment inFragment = fragmentContainerTransition.lastIn;
        Fragment outFragment = fragmentContainerTransition.firstOut;
        if (inFragment == null) {
            ViewGroup viewGroup = sceneRoot;
            Fragment fragment = outFragment;
            Fragment fragment2 = inFragment;
        } else if (outFragment == null) {
            ViewGroup viewGroup2 = sceneRoot;
            Fragment fragment3 = outFragment;
            Fragment fragment4 = inFragment;
        } else {
            final boolean inIsPop = fragmentContainerTransition.lastInIsPop;
            if (nameOverrides.isEmpty()) {
                sharedElementTransition = null;
            } else {
                sharedElementTransition = getSharedElementTransition(fragmentTransitionImpl, inFragment, outFragment, inIsPop);
            }
            ArrayMap<String, View> outSharedElements = captureOutSharedElements(fragmentTransitionImpl, nameOverrides, sharedElementTransition, fragmentContainerTransition);
            if (nameOverrides.isEmpty()) {
                sharedElementTransition2 = null;
            } else {
                arrayList.addAll(outSharedElements.values());
                sharedElementTransition2 = sharedElementTransition;
            }
            if (obj == null && exitTransition == null && sharedElementTransition2 == null) {
                return null;
            }
            callSharedElementStartEnd(inFragment, outFragment, inIsPop, outSharedElements, true);
            if (sharedElementTransition2 != null) {
                Rect inEpicenter2 = new Rect();
                fragmentTransitionImpl.setSharedElementTargets(sharedElementTransition2, nonExistentView, arrayList);
                ArrayMap<String, View> arrayMap = outSharedElements;
                Rect inEpicenter3 = inEpicenter2;
                setOutEpicenter(impl, sharedElementTransition2, exitTransition, outSharedElements, fragmentContainerTransition.firstOutIsPop, fragmentContainerTransition.firstOutTransaction);
                if (obj != null) {
                    fragmentTransitionImpl.setEpicenter(obj, inEpicenter3);
                }
                inEpicenter = inEpicenter3;
            } else {
                inEpicenter = null;
            }
            final Object finalSharedElementTransition = sharedElementTransition2;
            final FragmentTransitionImpl fragmentTransitionImpl2 = impl;
            final ArrayMap<String, String> arrayMap2 = nameOverrides;
            final FragmentContainerTransition fragmentContainerTransition2 = fragments;
            final ArrayList<View> arrayList2 = sharedElementsIn;
            Object sharedElementTransition3 = sharedElementTransition2;
            final View view = nonExistentView;
            C08626 r13 = r0;
            final Fragment fragment5 = inFragment;
            final Fragment fragment6 = outFragment;
            boolean z = inIsPop;
            Fragment fragment7 = outFragment;
            final ArrayList<View> arrayList3 = sharedElementsOut;
            Fragment fragment8 = inFragment;
            final Object obj2 = enterTransition;
            final Rect rect = inEpicenter;
            C08626 r0 = new Runnable() {
                public void run() {
                    ArrayMap<String, View> inSharedElements = FragmentTransition.captureInSharedElements(FragmentTransitionImpl.this, arrayMap2, finalSharedElementTransition, fragmentContainerTransition2);
                    if (inSharedElements != null) {
                        arrayList2.addAll(inSharedElements.values());
                        arrayList2.add(view);
                    }
                    FragmentTransition.callSharedElementStartEnd(fragment5, fragment6, inIsPop, inSharedElements, false);
                    Object obj = finalSharedElementTransition;
                    if (obj != null) {
                        FragmentTransitionImpl.this.swapSharedElementTargets(obj, arrayList3, arrayList2);
                        View inEpicenterView = FragmentTransition.getInEpicenterView(inSharedElements, fragmentContainerTransition2, obj2, inIsPop);
                        if (inEpicenterView != null) {
                            FragmentTransitionImpl.this.getBoundsOnScreen(inEpicenterView, rect);
                        }
                    }
                }
            };
            OneShotPreDrawListener.add(sceneRoot, r13);
            return sharedElementTransition3;
        }
        return null;
    }

    private static ArrayMap<String, View> captureOutSharedElements(FragmentTransitionImpl impl, ArrayMap<String, String> nameOverrides, Object sharedElementTransition, FragmentContainerTransition fragments) {
        ArrayList<String> names;
        SharedElementCallback sharedElementCallback;
        if (nameOverrides.isEmpty() || sharedElementTransition == null) {
            nameOverrides.clear();
            return null;
        }
        Fragment outFragment = fragments.firstOut;
        ArrayMap<String, View> outSharedElements = new ArrayMap<>();
        impl.findNamedViews(outSharedElements, outFragment.requireView());
        BackStackRecord outTransaction = fragments.firstOutTransaction;
        if (fragments.firstOutIsPop) {
            sharedElementCallback = outFragment.getEnterTransitionCallback();
            names = outTransaction.mSharedElementTargetNames;
        } else {
            sharedElementCallback = outFragment.getExitTransitionCallback();
            names = outTransaction.mSharedElementSourceNames;
        }
        if (names != null) {
            outSharedElements.retainAll(names);
        }
        if (sharedElementCallback != null) {
            sharedElementCallback.onMapSharedElements(names, outSharedElements);
            for (int i = names.size() - 1; i >= 0; i--) {
                String name = names.get(i);
                View view = outSharedElements.get(name);
                if (view == null) {
                    nameOverrides.remove(name);
                } else if (!name.equals(ViewCompat.getTransitionName(view))) {
                    nameOverrides.put(ViewCompat.getTransitionName(view), nameOverrides.remove(name));
                }
            }
        } else {
            nameOverrides.retainAll(outSharedElements.keySet());
        }
        return outSharedElements;
    }

    static ArrayMap<String, View> captureInSharedElements(FragmentTransitionImpl impl, ArrayMap<String, String> nameOverrides, Object sharedElementTransition, FragmentContainerTransition fragments) {
        ArrayList<String> names;
        SharedElementCallback sharedElementCallback;
        String key;
        Fragment inFragment = fragments.lastIn;
        View fragmentView = inFragment.getView();
        if (nameOverrides.isEmpty() || sharedElementTransition == null || fragmentView == null) {
            nameOverrides.clear();
            return null;
        }
        ArrayMap<String, View> inSharedElements = new ArrayMap<>();
        impl.findNamedViews(inSharedElements, fragmentView);
        BackStackRecord inTransaction = fragments.lastInTransaction;
        if (fragments.lastInIsPop) {
            sharedElementCallback = inFragment.getExitTransitionCallback();
            names = inTransaction.mSharedElementSourceNames;
        } else {
            sharedElementCallback = inFragment.getEnterTransitionCallback();
            names = inTransaction.mSharedElementTargetNames;
        }
        if (names != null) {
            inSharedElements.retainAll(names);
            inSharedElements.retainAll(nameOverrides.values());
        }
        if (sharedElementCallback != null) {
            sharedElementCallback.onMapSharedElements(names, inSharedElements);
            for (int i = names.size() - 1; i >= 0; i--) {
                String name = names.get(i);
                View view = inSharedElements.get(name);
                if (view == null) {
                    String key2 = findKeyForValue(nameOverrides, name);
                    if (key2 != null) {
                        nameOverrides.remove(key2);
                    }
                } else if (!name.equals(ViewCompat.getTransitionName(view)) && (key = findKeyForValue(nameOverrides, name)) != null) {
                    nameOverrides.put(key, ViewCompat.getTransitionName(view));
                }
            }
        } else {
            retainValues(nameOverrides, inSharedElements);
        }
        return inSharedElements;
    }

    static String findKeyForValue(ArrayMap<String, String> map, String value) {
        int numElements = map.size();
        for (int i = 0; i < numElements; i++) {
            if (value.equals(map.valueAt(i))) {
                return map.keyAt(i);
            }
        }
        return null;
    }

    static View getInEpicenterView(ArrayMap<String, View> inSharedElements, FragmentContainerTransition fragments, Object enterTransition, boolean inIsPop) {
        String targetName;
        BackStackRecord inTransaction = fragments.lastInTransaction;
        if (enterTransition == null || inSharedElements == null || inTransaction.mSharedElementSourceNames == null || inTransaction.mSharedElementSourceNames.isEmpty()) {
            return null;
        }
        if (inIsPop) {
            targetName = (String) inTransaction.mSharedElementSourceNames.get(0);
        } else {
            targetName = (String) inTransaction.mSharedElementTargetNames.get(0);
        }
        return inSharedElements.get(targetName);
    }

    private static void setOutEpicenter(FragmentTransitionImpl impl, Object sharedElementTransition, Object exitTransition, ArrayMap<String, View> outSharedElements, boolean outIsPop, BackStackRecord outTransaction) {
        String sourceName;
        if (outTransaction.mSharedElementSourceNames != null && !outTransaction.mSharedElementSourceNames.isEmpty()) {
            if (outIsPop) {
                sourceName = (String) outTransaction.mSharedElementTargetNames.get(0);
            } else {
                sourceName = (String) outTransaction.mSharedElementSourceNames.get(0);
            }
            View outEpicenterView = outSharedElements.get(sourceName);
            impl.setEpicenter(sharedElementTransition, outEpicenterView);
            if (exitTransition != null) {
                impl.setEpicenter(exitTransition, outEpicenterView);
            }
        }
    }

    static void retainValues(ArrayMap<String, String> nameOverrides, ArrayMap<String, View> namedViews) {
        for (int i = nameOverrides.size() - 1; i >= 0; i--) {
            if (!namedViews.containsKey(nameOverrides.valueAt(i))) {
                nameOverrides.removeAt(i);
            }
        }
    }

    static void callSharedElementStartEnd(Fragment inFragment, Fragment outFragment, boolean isPop, ArrayMap<String, View> sharedElements, boolean isStart) {
        SharedElementCallback sharedElementCallback;
        if (isPop) {
            sharedElementCallback = outFragment.getEnterTransitionCallback();
        } else {
            sharedElementCallback = inFragment.getEnterTransitionCallback();
        }
        if (sharedElementCallback != null) {
            ArrayList<View> views = new ArrayList<>();
            ArrayList<String> names = new ArrayList<>();
            int count = sharedElements == null ? 0 : sharedElements.size();
            for (int i = 0; i < count; i++) {
                names.add(sharedElements.keyAt(i));
                views.add(sharedElements.valueAt(i));
            }
            if (isStart) {
                sharedElementCallback.onSharedElementStart(names, views, (List<View>) null);
            } else {
                sharedElementCallback.onSharedElementEnd(names, views, (List<View>) null);
            }
        }
    }

    static ArrayList<View> configureEnteringExitingViews(FragmentTransitionImpl impl, Object transition, Fragment fragment, ArrayList<View> sharedElements, View nonExistentView) {
        ArrayList<View> viewList = null;
        if (transition != null) {
            viewList = new ArrayList<>();
            View root = fragment.getView();
            if (root != null) {
                impl.captureTransitioningViews(viewList, root);
            }
            if (sharedElements != null) {
                viewList.removeAll(sharedElements);
            }
            if (!viewList.isEmpty()) {
                viewList.add(nonExistentView);
                impl.addTargets(transition, viewList);
            }
        }
        return viewList;
    }

    static void setViewVisibility(ArrayList<View> views, int visibility) {
        if (views != null) {
            for (int i = views.size() - 1; i >= 0; i--) {
                views.get(i).setVisibility(visibility);
            }
        }
    }

    private static Object mergeTransitions(FragmentTransitionImpl impl, Object enterTransition, Object exitTransition, Object sharedElementTransition, Fragment inFragment, boolean isPop) {
        boolean z;
        boolean overlap = true;
        if (!(enterTransition == null || exitTransition == null || inFragment == null)) {
            if (isPop) {
                z = inFragment.getAllowReturnTransitionOverlap();
            } else {
                z = inFragment.getAllowEnterTransitionOverlap();
            }
            overlap = z;
        }
        if (overlap) {
            return impl.mergeTransitionsTogether(exitTransition, enterTransition, sharedElementTransition);
        }
        return impl.mergeTransitionsInSequence(exitTransition, enterTransition, sharedElementTransition);
    }

    public static void calculateFragments(BackStackRecord transaction, SparseArray<FragmentContainerTransition> transitioningFragments, boolean isReordered) {
        int numOps = transaction.mOps.size();
        for (int opNum = 0; opNum < numOps; opNum++) {
            addToFirstInLastOut(transaction, (FragmentTransaction.C0856Op) transaction.mOps.get(opNum), transitioningFragments, false, isReordered);
        }
    }

    public static void calculatePopFragments(BackStackRecord transaction, SparseArray<FragmentContainerTransition> transitioningFragments, boolean isReordered) {
        if (transaction.mManager.getContainer().onHasView()) {
            for (int opNum = transaction.mOps.size() - 1; opNum >= 0; opNum--) {
                addToFirstInLastOut(transaction, (FragmentTransaction.C0856Op) transaction.mOps.get(opNum), transitioningFragments, true, isReordered);
            }
        }
    }

    static boolean supportsTransition() {
        return (PLATFORM_IMPL == null && SUPPORT_IMPL == null) ? false : true;
    }

    private static void addToFirstInLastOut(BackStackRecord transaction, FragmentTransaction.C0856Op op, SparseArray<FragmentContainerTransition> transitioningFragments, boolean isPop, boolean isReorderedTransaction) {
        int containerId;
        boolean setLastIn;
        boolean setFirstOut;
        BackStackRecord backStackRecord = transaction;
        FragmentTransaction.C0856Op op2 = op;
        SparseArray<FragmentContainerTransition> sparseArray = transitioningFragments;
        boolean z = isPop;
        Fragment fragment = op2.mFragment;
        if (fragment != null && (containerId = fragment.mContainerId) != 0) {
            boolean setLastIn2 = false;
            boolean wasRemoved = false;
            boolean setFirstOut2 = false;
            boolean wasAdded = false;
            boolean z2 = true;
            switch (z ? INVERSE_OPS[op2.mCmd] : op2.mCmd) {
                case 1:
                case 7:
                    if (isReorderedTransaction) {
                        setLastIn = fragment.mIsNewlyAdded;
                    } else {
                        if (fragment.mAdded || fragment.mHidden) {
                            z2 = false;
                        }
                        setLastIn = z2;
                    }
                    wasAdded = true;
                    break;
                case 3:
                case 6:
                    if (isReorderedTransaction) {
                        if (fragment.mAdded || fragment.mView == null || fragment.mView.getVisibility() != 0 || fragment.mPostponedAlpha < 0.0f) {
                            z2 = false;
                        }
                        setFirstOut = z2;
                    } else {
                        if (!fragment.mAdded || fragment.mHidden) {
                            z2 = false;
                        }
                        setFirstOut = z2;
                    }
                    wasRemoved = true;
                    break;
                case 4:
                    if (isReorderedTransaction) {
                        if (!fragment.mHiddenChanged || !fragment.mAdded || !fragment.mHidden) {
                            z2 = false;
                        }
                        setFirstOut2 = z2;
                    } else {
                        if (!fragment.mAdded || fragment.mHidden) {
                            z2 = false;
                        }
                        setFirstOut2 = z2;
                    }
                    wasRemoved = true;
                    break;
                case 5:
                    if (isReorderedTransaction) {
                        if (!fragment.mHiddenChanged || fragment.mHidden || !fragment.mAdded) {
                            z2 = false;
                        }
                        setLastIn2 = z2;
                    } else {
                        setLastIn2 = fragment.mHidden;
                    }
                    wasAdded = true;
                    break;
            }
            FragmentContainerTransition containerTransition = sparseArray.get(containerId);
            if (setLastIn2) {
                containerTransition = ensureContainer(containerTransition, sparseArray, containerId);
                containerTransition.lastIn = fragment;
                containerTransition.lastInIsPop = z;
                containerTransition.lastInTransaction = backStackRecord;
            }
            if (!isReorderedTransaction && wasAdded) {
                if (containerTransition != null && containerTransition.firstOut == fragment) {
                    containerTransition.firstOut = null;
                }
                if (!backStackRecord.mReorderingAllowed) {
                    FragmentManager manager = backStackRecord.mManager;
                    manager.getFragmentStore().makeActive(manager.createOrGetFragmentStateManager(fragment));
                    manager.moveToState(fragment);
                }
            }
            if (setFirstOut2 && (containerTransition == null || containerTransition.firstOut == null)) {
                containerTransition = ensureContainer(containerTransition, sparseArray, containerId);
                containerTransition.firstOut = fragment;
                containerTransition.firstOutIsPop = z;
                containerTransition.firstOutTransaction = backStackRecord;
            }
            if (!isReorderedTransaction && wasRemoved && containerTransition != null && containerTransition.lastIn == fragment) {
                containerTransition.lastIn = null;
            }
        }
    }

    private static FragmentContainerTransition ensureContainer(FragmentContainerTransition containerTransition, SparseArray<FragmentContainerTransition> transitioningFragments, int containerId) {
        if (containerTransition != null) {
            return containerTransition;
        }
        FragmentContainerTransition containerTransition2 = new FragmentContainerTransition();
        transitioningFragments.put(containerId, containerTransition2);
        return containerTransition2;
    }

    static class FragmentContainerTransition {
        public Fragment firstOut;
        public boolean firstOutIsPop;
        public BackStackRecord firstOutTransaction;
        public Fragment lastIn;
        public boolean lastInIsPop;
        public BackStackRecord lastInTransaction;

        FragmentContainerTransition() {
        }
    }

    private FragmentTransition() {
    }
}
