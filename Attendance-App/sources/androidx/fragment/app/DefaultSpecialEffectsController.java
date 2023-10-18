package androidx.fragment.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import androidx.collection.ArrayMap;
import androidx.core.p001os.CancellationSignal;
import androidx.core.util.Preconditions;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewGroupCompat;
import androidx.fragment.app.FragmentAnim;
import androidx.fragment.app.SpecialEffectsController;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class DefaultSpecialEffectsController extends SpecialEffectsController {
    DefaultSpecialEffectsController(ViewGroup container) {
        super(container);
    }

    /* access modifiers changed from: package-private */
    public void executeOperations(List<SpecialEffectsController.Operation> operations, boolean isPop) {
        SpecialEffectsController.Operation firstOut = null;
        SpecialEffectsController.Operation lastIn = null;
        for (SpecialEffectsController.Operation operation : operations) {
            SpecialEffectsController.Operation.State currentState = SpecialEffectsController.Operation.State.from(operation.getFragment().mView);
            switch (C080610.f1055xe493b431[operation.getFinalState().ordinal()]) {
                case 1:
                case 2:
                case 3:
                    if (currentState == SpecialEffectsController.Operation.State.VISIBLE && firstOut == null) {
                        firstOut = operation;
                        break;
                    }
                case 4:
                    if (currentState == SpecialEffectsController.Operation.State.VISIBLE) {
                        break;
                    } else {
                        lastIn = operation;
                        break;
                    }
            }
        }
        List<AnimationInfo> animations = new ArrayList<>();
        List<TransitionInfo> transitions = new ArrayList<>();
        final List<SpecialEffectsController.Operation> awaitingContainerChanges = new ArrayList<>(operations);
        Iterator<SpecialEffectsController.Operation> it = operations.iterator();
        while (true) {
            boolean z = true;
            if (it.hasNext()) {
                final SpecialEffectsController.Operation operation2 = it.next();
                CancellationSignal animCancellationSignal = new CancellationSignal();
                operation2.markStartedSpecialEffect(animCancellationSignal);
                animations.add(new AnimationInfo(operation2, animCancellationSignal, isPop));
                CancellationSignal transitionCancellationSignal = new CancellationSignal();
                operation2.markStartedSpecialEffect(transitionCancellationSignal);
                if (isPop) {
                    if (operation2 == firstOut) {
                        transitions.add(new TransitionInfo(operation2, transitionCancellationSignal, isPop, z));
                        operation2.addCompletionListener(new Runnable() {
                            public void run() {
                                if (awaitingContainerChanges.contains(operation2)) {
                                    awaitingContainerChanges.remove(operation2);
                                    DefaultSpecialEffectsController.this.applyContainerChanges(operation2);
                                }
                            }
                        });
                    }
                } else if (operation2 == lastIn) {
                    transitions.add(new TransitionInfo(operation2, transitionCancellationSignal, isPop, z));
                    operation2.addCompletionListener(new Runnable() {
                        public void run() {
                            if (awaitingContainerChanges.contains(operation2)) {
                                awaitingContainerChanges.remove(operation2);
                                DefaultSpecialEffectsController.this.applyContainerChanges(operation2);
                            }
                        }
                    });
                }
                z = false;
                transitions.add(new TransitionInfo(operation2, transitionCancellationSignal, isPop, z));
                operation2.addCompletionListener(new Runnable() {
                    public void run() {
                        if (awaitingContainerChanges.contains(operation2)) {
                            awaitingContainerChanges.remove(operation2);
                            DefaultSpecialEffectsController.this.applyContainerChanges(operation2);
                        }
                    }
                });
            } else {
                Map<SpecialEffectsController.Operation, Boolean> startedTransitions = startTransitions(transitions, awaitingContainerChanges, isPop, firstOut, lastIn);
                startAnimations(animations, awaitingContainerChanges, startedTransitions.containsValue(true), startedTransitions);
                for (SpecialEffectsController.Operation operation3 : awaitingContainerChanges) {
                    applyContainerChanges(operation3);
                }
                awaitingContainerChanges.clear();
                return;
            }
        }
    }

    /* renamed from: androidx.fragment.app.DefaultSpecialEffectsController$10 */
    static /* synthetic */ class C080610 {

        /* renamed from: $SwitchMap$androidx$fragment$app$SpecialEffectsController$Operation$State */
        static final /* synthetic */ int[] f1055xe493b431;

        static {
            int[] iArr = new int[SpecialEffectsController.Operation.State.values().length];
            f1055xe493b431 = iArr;
            try {
                iArr[SpecialEffectsController.Operation.State.GONE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1055xe493b431[SpecialEffectsController.Operation.State.INVISIBLE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1055xe493b431[SpecialEffectsController.Operation.State.REMOVED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1055xe493b431[SpecialEffectsController.Operation.State.VISIBLE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    private void startAnimations(List<AnimationInfo> animationInfos, List<SpecialEffectsController.Operation> awaitingContainerChanges, boolean startedAnyTransition, Map<SpecialEffectsController.Operation, Boolean> startedTransitions) {
        final ViewGroup container = getContainer();
        Context context = container.getContext();
        ArrayList arrayList = new ArrayList();
        View viewToAnimate = null;
        Iterator<AnimationInfo> it = animationInfos.iterator();
        while (it.hasNext()) {
            AnimationInfo animationInfo = it.next();
            if (animationInfo.isVisibilityUnchanged()) {
                animationInfo.completeSpecialEffect();
                Map<SpecialEffectsController.Operation, Boolean> map = startedTransitions;
            } else {
                FragmentAnim.AnimationOrAnimator anim = animationInfo.getAnimation(context);
                if (anim == null) {
                    animationInfo.completeSpecialEffect();
                    Map<SpecialEffectsController.Operation, Boolean> map2 = startedTransitions;
                } else {
                    final Animator animator = anim.animator;
                    if (animator == null) {
                        arrayList.add(animationInfo);
                        Map<SpecialEffectsController.Operation, Boolean> map3 = startedTransitions;
                    } else {
                        SpecialEffectsController.Operation operation = animationInfo.getOperation();
                        Fragment fragment = operation.getFragment();
                        if (Boolean.TRUE.equals(startedTransitions.get(operation))) {
                            if (FragmentManager.isLoggingEnabled(2)) {
                                Log.v("FragmentManager", "Ignoring Animator set on " + fragment + " as this Fragment was involved in a Transition.");
                            }
                            animationInfo.completeSpecialEffect();
                        } else {
                            boolean isHideOperation = operation.getFinalState() == SpecialEffectsController.Operation.State.GONE;
                            if (isHideOperation) {
                                awaitingContainerChanges.remove(operation);
                            } else {
                                List<SpecialEffectsController.Operation> list = awaitingContainerChanges;
                            }
                            final View viewToAnimate2 = fragment.mView;
                            container.startViewTransition(viewToAnimate2);
                            Iterator<AnimationInfo> it2 = it;
                            C08072 r11 = r0;
                            final ViewGroup viewGroup = container;
                            final boolean z = isHideOperation;
                            final SpecialEffectsController.Operation operation2 = operation;
                            Fragment fragment2 = fragment;
                            final AnimationInfo animationInfo2 = animationInfo;
                            C08072 r0 = new AnimatorListenerAdapter() {
                                public void onAnimationEnd(Animator anim) {
                                    viewGroup.endViewTransition(viewToAnimate2);
                                    if (z) {
                                        operation2.getFinalState().applyState(viewToAnimate2);
                                    }
                                    animationInfo2.completeSpecialEffect();
                                }
                            };
                            animator.addListener(r11);
                            animator.setTarget(viewToAnimate2);
                            animator.start();
                            animationInfo.getSignal().setOnCancelListener(new CancellationSignal.OnCancelListener() {
                                public void onCancel() {
                                    animator.end();
                                }
                            });
                            viewToAnimate = 1;
                            it = it2;
                        }
                    }
                }
            }
        }
        Iterator it3 = arrayList.iterator();
        while (it3.hasNext()) {
            final AnimationInfo animationInfo3 = (AnimationInfo) it3.next();
            SpecialEffectsController.Operation operation3 = animationInfo3.getOperation();
            Fragment fragment3 = operation3.getFragment();
            if (startedAnyTransition) {
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "Ignoring Animation set on " + fragment3 + " as Animations cannot run alongside Transitions.");
                }
                animationInfo3.completeSpecialEffect();
            } else if (viewToAnimate != null) {
                if (FragmentManager.isLoggingEnabled(2)) {
                    Log.v("FragmentManager", "Ignoring Animation set on " + fragment3 + " as Animations cannot run alongside Animators.");
                }
                animationInfo3.completeSpecialEffect();
            } else {
                final View viewToAnimate3 = fragment3.mView;
                Animation anim2 = (Animation) Preconditions.checkNotNull(((FragmentAnim.AnimationOrAnimator) Preconditions.checkNotNull(animationInfo3.getAnimation(context))).animation);
                if (operation3.getFinalState() != SpecialEffectsController.Operation.State.REMOVED) {
                    viewToAnimate3.startAnimation(anim2);
                    animationInfo3.completeSpecialEffect();
                } else {
                    container.startViewTransition(viewToAnimate3);
                    Animation animation = new FragmentAnim.EndViewTransitionAnimation(anim2, container, viewToAnimate3);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        public void onAnimationStart(Animation animation) {
                        }

                        public void onAnimationEnd(Animation animation) {
                            container.post(new Runnable() {
                                public void run() {
                                    container.endViewTransition(viewToAnimate3);
                                    animationInfo3.completeSpecialEffect();
                                }
                            });
                        }

                        public void onAnimationRepeat(Animation animation) {
                        }
                    });
                    viewToAnimate3.startAnimation(animation);
                }
                animationInfo3.getSignal().setOnCancelListener(new CancellationSignal.OnCancelListener() {
                    public void onCancel() {
                        viewToAnimate3.clearAnimation();
                        container.endViewTransition(viewToAnimate3);
                        animationInfo3.completeSpecialEffect();
                    }
                });
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v22, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v20, resolved type: android.view.View} */
    /* JADX WARNING: Code restructure failed: missing block: B:141:0x0514, code lost:
        if (r11 == r43) goto L_0x051b;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:151:0x0536  */
    /* JADX WARNING: Removed duplicated region for block: B:156:0x056d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Map<androidx.fragment.app.SpecialEffectsController.Operation, java.lang.Boolean> startTransitions(java.util.List<androidx.fragment.app.DefaultSpecialEffectsController.TransitionInfo> r39, java.util.List<androidx.fragment.app.SpecialEffectsController.Operation> r40, boolean r41, androidx.fragment.app.SpecialEffectsController.Operation r42, androidx.fragment.app.SpecialEffectsController.Operation r43) {
        /*
            r38 = this;
            r6 = r38
            r7 = r41
            r8 = r42
            r9 = r43
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>()
            r10 = r0
            r0 = 0
            java.util.Iterator r1 = r39.iterator()
            r15 = r0
        L_0x0014:
            boolean r0 = r1.hasNext()
            if (r0 == 0) goto L_0x006b
            java.lang.Object r0 = r1.next()
            androidx.fragment.app.DefaultSpecialEffectsController$TransitionInfo r0 = (androidx.fragment.app.DefaultSpecialEffectsController.TransitionInfo) r0
            boolean r2 = r0.isVisibilityUnchanged()
            if (r2 == 0) goto L_0x0027
            goto L_0x0014
        L_0x0027:
            androidx.fragment.app.FragmentTransitionImpl r2 = r0.getHandlingImpl()
            if (r15 != 0) goto L_0x0030
            r3 = r2
            r15 = r3
            goto L_0x006a
        L_0x0030:
            if (r2 == 0) goto L_0x006a
            if (r15 != r2) goto L_0x0035
            goto L_0x006a
        L_0x0035:
            java.lang.IllegalArgumentException r1 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "Mixing framework transitions and AndroidX transitions is not allowed. Fragment "
            java.lang.StringBuilder r3 = r3.append(r4)
            androidx.fragment.app.SpecialEffectsController$Operation r4 = r0.getOperation()
            androidx.fragment.app.Fragment r4 = r4.getFragment()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = " returned Transition "
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.Object r4 = r0.getTransition()
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = " which uses a different Transition  type than other Fragments."
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            r1.<init>(r3)
            throw r1
        L_0x006a:
            goto L_0x0014
        L_0x006b:
            r14 = 0
            if (r15 != 0) goto L_0x008e
            java.util.Iterator r0 = r39.iterator()
        L_0x0072:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x008d
            java.lang.Object r1 = r0.next()
            androidx.fragment.app.DefaultSpecialEffectsController$TransitionInfo r1 = (androidx.fragment.app.DefaultSpecialEffectsController.TransitionInfo) r1
            androidx.fragment.app.SpecialEffectsController$Operation r2 = r1.getOperation()
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r14)
            r10.put(r2, r3)
            r1.completeSpecialEffect()
            goto L_0x0072
        L_0x008d:
            return r10
        L_0x008e:
            android.view.View r0 = new android.view.View
            android.view.ViewGroup r1 = r38.getContainer()
            android.content.Context r1 = r1.getContext()
            r0.<init>(r1)
            r13 = r0
            r0 = 0
            r1 = 0
            r2 = 0
            android.graphics.Rect r3 = new android.graphics.Rect
            r3.<init>()
            r12 = r3
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r11 = r3
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r5 = r3
            androidx.collection.ArrayMap r3 = new androidx.collection.ArrayMap
            r3.<init>()
            r4 = r3
            java.util.Iterator r19 = r39.iterator()
            r3 = r1
            r20 = r2
        L_0x00be:
            boolean r1 = r19.hasNext()
            if (r1 == 0) goto L_0x0372
            java.lang.Object r1 = r19.next()
            r21 = r1
            androidx.fragment.app.DefaultSpecialEffectsController$TransitionInfo r21 = (androidx.fragment.app.DefaultSpecialEffectsController.TransitionInfo) r21
            boolean r22 = r21.hasSharedElementTransition()
            if (r22 == 0) goto L_0x034e
            if (r8 == 0) goto L_0x034e
            if (r9 == 0) goto L_0x034e
            java.lang.Object r1 = r21.getSharedElementTransition()
            java.lang.Object r1 = r15.cloneTransition(r1)
            java.lang.Object r1 = r15.wrapTransitionInSet(r1)
            androidx.fragment.app.Fragment r0 = r43.getFragment()
            java.util.ArrayList r0 = r0.getSharedElementSourceNames()
            androidx.fragment.app.Fragment r16 = r42.getFragment()
            java.util.ArrayList r14 = r16.getSharedElementSourceNames()
            androidx.fragment.app.Fragment r16 = r42.getFragment()
            java.util.ArrayList r2 = r16.getSharedElementTargetNames()
            r16 = 0
            r23 = r1
            r1 = r16
        L_0x0101:
            r16 = r3
            int r3 = r2.size()
            if (r1 >= r3) goto L_0x0124
            java.lang.Object r3 = r2.get(r1)
            int r3 = r0.indexOf(r3)
            r24 = r2
            r2 = -1
            if (r3 == r2) goto L_0x011d
            java.lang.Object r2 = r14.get(r1)
            r0.set(r3, r2)
        L_0x011d:
            int r1 = r1 + 1
            r3 = r16
            r2 = r24
            goto L_0x0101
        L_0x0124:
            r24 = r2
            androidx.fragment.app.Fragment r1 = r43.getFragment()
            java.util.ArrayList r3 = r1.getSharedElementTargetNames()
            if (r7 != 0) goto L_0x0146
            androidx.fragment.app.Fragment r1 = r42.getFragment()
            androidx.core.app.SharedElementCallback r1 = r1.getExitTransitionCallback()
            androidx.fragment.app.Fragment r2 = r43.getFragment()
            androidx.core.app.SharedElementCallback r2 = r2.getEnterTransitionCallback()
            r37 = r2
            r2 = r1
            r1 = r37
            goto L_0x015b
        L_0x0146:
            androidx.fragment.app.Fragment r1 = r42.getFragment()
            androidx.core.app.SharedElementCallback r1 = r1.getEnterTransitionCallback()
            androidx.fragment.app.Fragment r2 = r43.getFragment()
            androidx.core.app.SharedElementCallback r2 = r2.getExitTransitionCallback()
            r37 = r2
            r2 = r1
            r1 = r37
        L_0x015b:
            r25 = r14
            int r14 = r0.size()
            r26 = 0
            r9 = r26
        L_0x0165:
            if (r9 >= r14) goto L_0x0183
            java.lang.Object r26 = r0.get(r9)
            r27 = r14
            r14 = r26
            java.lang.String r14 = (java.lang.String) r14
            java.lang.Object r26 = r3.get(r9)
            r8 = r26
            java.lang.String r8 = (java.lang.String) r8
            r4.put(r14, r8)
            int r9 = r9 + 1
            r8 = r42
            r14 = r27
            goto L_0x0165
        L_0x0183:
            r27 = r14
            androidx.collection.ArrayMap r8 = new androidx.collection.ArrayMap
            r8.<init>()
            androidx.fragment.app.Fragment r9 = r42.getFragment()
            android.view.View r9 = r9.mView
            r6.findNamedViews(r8, r9)
            r8.retainAll(r0)
            if (r2 == 0) goto L_0x01e3
            r2.onMapSharedElements(r0, r8)
            int r9 = r0.size()
            r14 = 1
            int r9 = r9 - r14
        L_0x01a1:
            if (r9 < 0) goto L_0x01de
            java.lang.Object r14 = r0.get(r9)
            java.lang.String r14 = (java.lang.String) r14
            java.lang.Object r26 = r8.get(r14)
            android.view.View r26 = (android.view.View) r26
            if (r26 != 0) goto L_0x01b9
            r4.remove(r14)
            r28 = r0
            r29 = r2
            goto L_0x01d7
        L_0x01b9:
            r28 = r0
            java.lang.String r0 = androidx.core.view.ViewCompat.getTransitionName(r26)
            boolean r0 = r14.equals(r0)
            if (r0 != 0) goto L_0x01d5
            java.lang.Object r0 = r4.remove(r14)
            java.lang.String r0 = (java.lang.String) r0
            r29 = r2
            java.lang.String r2 = androidx.core.view.ViewCompat.getTransitionName(r26)
            r4.put(r2, r0)
            goto L_0x01d7
        L_0x01d5:
            r29 = r2
        L_0x01d7:
            int r9 = r9 + -1
            r0 = r28
            r2 = r29
            goto L_0x01a1
        L_0x01de:
            r28 = r0
            r29 = r2
            goto L_0x01ee
        L_0x01e3:
            r28 = r0
            r29 = r2
            java.util.Set r0 = r8.keySet()
            r4.retainAll(r0)
        L_0x01ee:
            androidx.collection.ArrayMap r0 = new androidx.collection.ArrayMap
            r0.<init>()
            r9 = r0
            androidx.fragment.app.Fragment r0 = r43.getFragment()
            android.view.View r0 = r0.mView
            r6.findNamedViews(r9, r0)
            r9.retainAll(r3)
            java.util.Collection r0 = r4.values()
            r9.retainAll(r0)
            if (r1 == 0) goto L_0x0258
            r1.onMapSharedElements(r3, r9)
            int r0 = r3.size()
            r2 = 1
            int r0 = r0 - r2
        L_0x0212:
            if (r0 < 0) goto L_0x0255
            java.lang.Object r2 = r3.get(r0)
            java.lang.String r2 = (java.lang.String) r2
            java.lang.Object r14 = r9.get(r2)
            android.view.View r14 = (android.view.View) r14
            if (r14 != 0) goto L_0x022e
            r26 = r1
            java.lang.String r1 = androidx.fragment.app.FragmentTransition.findKeyForValue(r4, r2)
            if (r1 == 0) goto L_0x022d
            r4.remove(r1)
        L_0x022d:
            goto L_0x0250
        L_0x022e:
            r26 = r1
            java.lang.String r1 = androidx.core.view.ViewCompat.getTransitionName(r14)
            boolean r1 = r2.equals(r1)
            if (r1 != 0) goto L_0x024e
            java.lang.String r1 = androidx.fragment.app.FragmentTransition.findKeyForValue(r4, r2)
            if (r1 == 0) goto L_0x024b
            r30 = r2
            java.lang.String r2 = androidx.core.view.ViewCompat.getTransitionName(r14)
            r4.put(r1, r2)
            goto L_0x0250
        L_0x024b:
            r30 = r2
            goto L_0x0250
        L_0x024e:
            r30 = r2
        L_0x0250:
            int r0 = r0 + -1
            r1 = r26
            goto L_0x0212
        L_0x0255:
            r26 = r1
            goto L_0x025d
        L_0x0258:
            r26 = r1
            androidx.fragment.app.FragmentTransition.retainValues(r4, r9)
        L_0x025d:
            java.util.Set r0 = r4.keySet()
            r6.retainMatchingViews(r8, r0)
            java.util.Collection r0 = r4.values()
            r6.retainMatchingViews(r9, r0)
            boolean r0 = r4.isEmpty()
            if (r0 == 0) goto L_0x028c
            r0 = 0
            r11.clear()
            r5.clear()
            r33 = r4
            r30 = r5
            r14 = r10
            r10 = r11
            r34 = r12
            r2 = r13
            r35 = r15
            r3 = r16
            r4 = 0
            r15 = r42
            r13 = r43
            goto L_0x0360
        L_0x028c:
            androidx.fragment.app.Fragment r0 = r43.getFragment()
            androidx.fragment.app.Fragment r1 = r42.getFragment()
            r2 = 1
            androidx.fragment.app.FragmentTransition.callSharedElementStartEnd(r0, r1, r7, r8, r2)
            android.view.ViewGroup r14 = r38.getContainer()
            androidx.fragment.app.DefaultSpecialEffectsController$6 r1 = new androidx.fragment.app.DefaultSpecialEffectsController$6
            r30 = r28
            r0 = r1
            r7 = r23
            r23 = r26
            r26 = r10
            r10 = r1
            r1 = r38
            r28 = r29
            r29 = 1
            r2 = r43
            r32 = r3
            r31 = r16
            r3 = r42
            r33 = r4
            r4 = r41
            r16 = r13
            r13 = r5
            r5 = r9
            r0.<init>(r2, r3, r4, r5)
            androidx.core.view.OneShotPreDrawListener.add(r14, r10)
            java.util.Collection r0 = r8.values()
            r11.addAll(r0)
            boolean r0 = r30.isEmpty()
            if (r0 != 0) goto L_0x02e7
            r0 = r30
            r1 = 0
            java.lang.Object r2 = r0.get(r1)
            r1 = r2
            java.lang.String r1 = (java.lang.String) r1
            java.lang.Object r2 = r8.get(r1)
            r3 = r2
            android.view.View r3 = (android.view.View) r3
            r15.setEpicenter((java.lang.Object) r7, (android.view.View) r3)
            goto L_0x02eb
        L_0x02e7:
            r0 = r30
            r3 = r31
        L_0x02eb:
            java.util.Collection r1 = r9.values()
            r13.addAll(r1)
            boolean r1 = r32.isEmpty()
            if (r1 != 0) goto L_0x0319
            r1 = r32
            r2 = 0
            java.lang.Object r4 = r1.get(r2)
            java.lang.String r4 = (java.lang.String) r4
            java.lang.Object r5 = r9.get(r4)
            android.view.View r5 = (android.view.View) r5
            if (r5 == 0) goto L_0x031b
            r20 = 1
            r10 = r15
            android.view.ViewGroup r14 = r38.getContainer()
            androidx.fragment.app.DefaultSpecialEffectsController$7 r2 = new androidx.fragment.app.DefaultSpecialEffectsController$7
            r2.<init>(r10, r5, r12)
            androidx.core.view.OneShotPreDrawListener.add(r14, r2)
            goto L_0x031b
        L_0x0319:
            r1 = r32
        L_0x031b:
            r2 = r16
            r15.setSharedElementTargets(r7, r2, r11)
            r4 = 0
            r14 = 0
            r5 = 0
            r16 = 0
            r10 = r11
            r11 = r15
            r34 = r12
            r12 = r7
            r30 = r13
            r13 = r4
            r4 = 0
            r35 = r15
            r15 = r5
            r17 = r7
            r18 = r30
            r11.scheduleRemoveTargets(r12, r13, r14, r15, r16, r17, r18)
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r29)
            r15 = r42
            r14 = r26
            r14.put(r15, r5)
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r29)
            r13 = r43
            r14.put(r13, r5)
            r0 = r7
            goto L_0x0360
        L_0x034e:
            r31 = r3
            r33 = r4
            r30 = r5
            r14 = r10
            r10 = r11
            r34 = r12
            r2 = r13
            r35 = r15
            r4 = 0
            r15 = r8
            r13 = r9
            r3 = r31
        L_0x0360:
            r7 = r41
            r11 = r10
            r9 = r13
            r10 = r14
            r8 = r15
            r5 = r30
            r4 = r33
            r12 = r34
            r15 = r35
            r14 = 0
            r13 = r2
            goto L_0x00be
        L_0x0372:
            r31 = r3
            r33 = r4
            r30 = r5
            r14 = r10
            r10 = r11
            r34 = r12
            r2 = r13
            r35 = r15
            r4 = 0
            r29 = 1
            r15 = r8
            r13 = r9
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r3 = 0
            r5 = 0
            java.util.Iterator r7 = r39.iterator()
        L_0x038f:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L_0x04d9
            java.lang.Object r8 = r7.next()
            androidx.fragment.app.DefaultSpecialEffectsController$TransitionInfo r8 = (androidx.fragment.app.DefaultSpecialEffectsController.TransitionInfo) r8
            boolean r9 = r8.isVisibilityUnchanged()
            if (r9 == 0) goto L_0x03b0
            androidx.fragment.app.SpecialEffectsController$Operation r9 = r8.getOperation()
            java.lang.Boolean r11 = java.lang.Boolean.valueOf(r4)
            r14.put(r9, r11)
            r8.completeSpecialEffect()
            goto L_0x038f
        L_0x03b0:
            java.lang.Object r9 = r8.getTransition()
            r12 = r35
            java.lang.Object r9 = r12.cloneTransition(r9)
            androidx.fragment.app.SpecialEffectsController$Operation r11 = r8.getOperation()
            if (r0 == 0) goto L_0x03c7
            if (r11 == r15) goto L_0x03c4
            if (r11 != r13) goto L_0x03c7
        L_0x03c4:
            r16 = 1
            goto L_0x03c9
        L_0x03c7:
            r16 = 0
        L_0x03c9:
            r19 = r16
            if (r9 != 0) goto L_0x03ef
            if (r19 != 0) goto L_0x03dc
            r21 = r7
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r4)
            r14.put(r11, r7)
            r8.completeSpecialEffect()
            goto L_0x03de
        L_0x03dc:
            r21 = r7
        L_0x03de:
            r25 = r2
            r26 = r10
            r2 = r14
            r10 = r15
            r4 = r30
            r13 = r34
            r15 = r40
            r14 = r12
            r12 = r31
            goto L_0x04c4
        L_0x03ef:
            r21 = r7
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            androidx.fragment.app.Fragment r4 = r11.getFragment()
            android.view.View r4 = r4.mView
            r6.captureTransitioningViews(r7, r4)
            if (r19 == 0) goto L_0x0410
            if (r11 != r15) goto L_0x040a
            r7.removeAll(r10)
            r4 = r30
            goto L_0x0412
        L_0x040a:
            r4 = r30
            r7.removeAll(r4)
            goto L_0x0412
        L_0x0410:
            r4 = r30
        L_0x0412:
            boolean r16 = r7.isEmpty()
            if (r16 == 0) goto L_0x0426
            r12.addTarget(r9, r2)
            r25 = r2
            r26 = r10
            r2 = r14
            r10 = r15
            r15 = r40
            r14 = r12
            goto L_0x0488
        L_0x0426:
            r12.addTargets(r9, r7)
            r16 = 0
            r17 = 0
            r18 = 0
            r23 = 0
            r24 = r11
            r11 = r12
            r36 = r12
            r12 = r9
            r13 = r9
            r25 = r2
            r2 = r14
            r14 = r7
            r26 = r10
            r10 = r15
            r15 = r16
            r16 = r17
            r17 = r18
            r18 = r23
            r11.scheduleRemoveTargets(r12, r13, r14, r15, r16, r17, r18)
            androidx.fragment.app.SpecialEffectsController$Operation$State r11 = r24.getFinalState()
            androidx.fragment.app.SpecialEffectsController$Operation$State r12 = androidx.fragment.app.SpecialEffectsController.Operation.State.GONE
            if (r11 != r12) goto L_0x0482
            r15 = r40
            r11 = r24
            r15.remove(r11)
            java.util.ArrayList r12 = new java.util.ArrayList
            r12.<init>(r7)
            androidx.fragment.app.Fragment r13 = r11.getFragment()
            android.view.View r13 = r13.mView
            r12.remove(r13)
            androidx.fragment.app.Fragment r13 = r11.getFragment()
            android.view.View r13 = r13.mView
            r14 = r36
            r14.scheduleHideFragmentView(r9, r13, r12)
            android.view.ViewGroup r13 = r38.getContainer()
            r16 = r12
            androidx.fragment.app.DefaultSpecialEffectsController$8 r12 = new androidx.fragment.app.DefaultSpecialEffectsController$8
            r12.<init>(r7)
            androidx.core.view.OneShotPreDrawListener.add(r13, r12)
            goto L_0x0488
        L_0x0482:
            r15 = r40
            r11 = r24
            r14 = r36
        L_0x0488:
            androidx.fragment.app.SpecialEffectsController$Operation$State r12 = r11.getFinalState()
            androidx.fragment.app.SpecialEffectsController$Operation$State r13 = androidx.fragment.app.SpecialEffectsController.Operation.State.VISIBLE
            if (r12 != r13) goto L_0x04a2
            r1.addAll(r7)
            if (r20 == 0) goto L_0x049d
            r13 = r34
            r14.setEpicenter((java.lang.Object) r9, (android.graphics.Rect) r13)
            r12 = r31
            goto L_0x04a9
        L_0x049d:
            r13 = r34
            r12 = r31
            goto L_0x04a9
        L_0x04a2:
            r13 = r34
            r12 = r31
            r14.setEpicenter((java.lang.Object) r9, (android.view.View) r12)
        L_0x04a9:
            r16 = r7
            java.lang.Boolean r7 = java.lang.Boolean.valueOf(r29)
            r2.put(r11, r7)
            boolean r7 = r8.isOverlapAllowed()
            r17 = r8
            r8 = 0
            if (r7 == 0) goto L_0x04c0
            java.lang.Object r3 = r14.mergeTransitionsTogether(r3, r9, r8)
            goto L_0x04c4
        L_0x04c0:
            java.lang.Object r5 = r14.mergeTransitionsTogether(r5, r9, r8)
        L_0x04c4:
            r30 = r4
            r15 = r10
            r31 = r12
            r34 = r13
            r35 = r14
            r7 = r21
            r10 = r26
            r4 = 0
            r13 = r43
            r14 = r2
            r2 = r25
            goto L_0x038f
        L_0x04d9:
            r25 = r2
            r26 = r10
            r2 = r14
            r10 = r15
            r4 = r30
            r12 = r31
            r13 = r34
            r14 = r35
            r15 = r40
            java.lang.Object r3 = r14.mergeTransitionsInSequence(r3, r5, r0)
            java.util.Iterator r7 = r39.iterator()
        L_0x04f1:
            boolean r8 = r7.hasNext()
            if (r8 == 0) goto L_0x058b
            java.lang.Object r8 = r7.next()
            androidx.fragment.app.DefaultSpecialEffectsController$TransitionInfo r8 = (androidx.fragment.app.DefaultSpecialEffectsController.TransitionInfo) r8
            boolean r9 = r8.isVisibilityUnchanged()
            if (r9 == 0) goto L_0x0504
            goto L_0x04f1
        L_0x0504:
            java.lang.Object r9 = r8.getTransition()
            androidx.fragment.app.SpecialEffectsController$Operation r11 = r8.getOperation()
            if (r0 == 0) goto L_0x051e
            if (r11 == r10) goto L_0x0517
            r17 = r5
            r5 = r43
            if (r11 != r5) goto L_0x0522
            goto L_0x051b
        L_0x0517:
            r17 = r5
            r5 = r43
        L_0x051b:
            r16 = 1
            goto L_0x0524
        L_0x051e:
            r17 = r5
            r5 = r43
        L_0x0522:
            r16 = 0
        L_0x0524:
            if (r9 != 0) goto L_0x052c
            if (r16 == 0) goto L_0x0529
            goto L_0x052c
        L_0x0529:
            r18 = r7
            goto L_0x0585
        L_0x052c:
            android.view.ViewGroup r18 = r38.getContainer()
            boolean r18 = androidx.core.view.ViewCompat.isLaidOut(r18)
            if (r18 != 0) goto L_0x056d
            r18 = 2
            boolean r18 = androidx.fragment.app.FragmentManager.isLoggingEnabled(r18)
            if (r18 == 0) goto L_0x0567
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            r18 = r7
            java.lang.String r7 = "SpecialEffectsController: Container "
            java.lang.StringBuilder r5 = r5.append(r7)
            android.view.ViewGroup r7 = r38.getContainer()
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.String r7 = " has not been laid out. Completing operation "
            java.lang.StringBuilder r5 = r5.append(r7)
            java.lang.StringBuilder r5 = r5.append(r11)
            java.lang.String r5 = r5.toString()
            java.lang.String r7 = "FragmentManager"
            android.util.Log.v(r7, r5)
            goto L_0x0569
        L_0x0567:
            r18 = r7
        L_0x0569:
            r8.completeSpecialEffect()
            goto L_0x0585
        L_0x056d:
            r18 = r7
            androidx.fragment.app.SpecialEffectsController$Operation r5 = r8.getOperation()
            androidx.fragment.app.Fragment r5 = r5.getFragment()
            androidx.core.os.CancellationSignal r7 = r8.getSignal()
            r19 = r9
            androidx.fragment.app.DefaultSpecialEffectsController$9 r9 = new androidx.fragment.app.DefaultSpecialEffectsController$9
            r9.<init>(r8)
            r14.setListenerForTransitionEnd(r5, r3, r7, r9)
        L_0x0585:
            r5 = r17
            r7 = r18
            goto L_0x04f1
        L_0x058b:
            r17 = r5
            android.view.ViewGroup r5 = r38.getContainer()
            boolean r5 = androidx.core.view.ViewCompat.isLaidOut(r5)
            if (r5 != 0) goto L_0x0598
            return r2
        L_0x0598:
            r5 = 4
            androidx.fragment.app.FragmentTransition.setViewVisibility(r1, r5)
            java.util.ArrayList r5 = r14.prepareSetNameOverridesReordered(r4)
            android.view.ViewGroup r7 = r38.getContainer()
            r14.beginDelayedTransition(r7, r3)
            android.view.ViewGroup r7 = r38.getContainer()
            r11 = r14
            r8 = r12
            r12 = r7
            r7 = r13
            r13 = r26
            r9 = r14
            r14 = r4
            r15 = r5
            r16 = r33
            r11.setNameOverridesReordered(r12, r13, r14, r15, r16)
            r11 = 0
            androidx.fragment.app.FragmentTransition.setViewVisibility(r1, r11)
            r11 = r26
            r9.swapSharedElementTargets(r0, r11, r4)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.fragment.app.DefaultSpecialEffectsController.startTransitions(java.util.List, java.util.List, boolean, androidx.fragment.app.SpecialEffectsController$Operation, androidx.fragment.app.SpecialEffectsController$Operation):java.util.Map");
    }

    /* access modifiers changed from: package-private */
    public void retainMatchingViews(ArrayMap<String, View> sharedElementViews, Collection<String> transitionNames) {
        Iterator<Map.Entry<String, View>> iterator = sharedElementViews.entrySet().iterator();
        while (iterator.hasNext()) {
            if (!transitionNames.contains(ViewCompat.getTransitionName(iterator.next().getValue()))) {
                iterator.remove();
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void captureTransitioningViews(ArrayList<View> transitioningViews, View view) {
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            if (!ViewGroupCompat.isTransitionGroup(viewGroup)) {
                int count = viewGroup.getChildCount();
                for (int i = 0; i < count; i++) {
                    View child = viewGroup.getChildAt(i);
                    if (child.getVisibility() == 0) {
                        captureTransitioningViews(transitioningViews, child);
                    }
                }
            } else if (!transitioningViews.contains(view)) {
                transitioningViews.add(viewGroup);
            }
        } else if (!transitioningViews.contains(view)) {
            transitioningViews.add(view);
        }
    }

    /* access modifiers changed from: package-private */
    public void findNamedViews(Map<String, View> namedViews, View view) {
        String transitionName = ViewCompat.getTransitionName(view);
        if (transitionName != null) {
            namedViews.put(transitionName, view);
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = viewGroup.getChildAt(i);
                if (child.getVisibility() == 0) {
                    findNamedViews(namedViews, child);
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void applyContainerChanges(SpecialEffectsController.Operation operation) {
        operation.getFinalState().applyState(operation.getFragment().mView);
    }

    private static class SpecialEffectsInfo {
        private final SpecialEffectsController.Operation mOperation;
        private final CancellationSignal mSignal;

        SpecialEffectsInfo(SpecialEffectsController.Operation operation, CancellationSignal signal) {
            this.mOperation = operation;
            this.mSignal = signal;
        }

        /* access modifiers changed from: package-private */
        public SpecialEffectsController.Operation getOperation() {
            return this.mOperation;
        }

        /* access modifiers changed from: package-private */
        public CancellationSignal getSignal() {
            return this.mSignal;
        }

        /* access modifiers changed from: package-private */
        public boolean isVisibilityUnchanged() {
            SpecialEffectsController.Operation.State currentState = SpecialEffectsController.Operation.State.from(this.mOperation.getFragment().mView);
            SpecialEffectsController.Operation.State finalState = this.mOperation.getFinalState();
            return currentState == finalState || !(currentState == SpecialEffectsController.Operation.State.VISIBLE || finalState == SpecialEffectsController.Operation.State.VISIBLE);
        }

        /* access modifiers changed from: package-private */
        public void completeSpecialEffect() {
            this.mOperation.completeSpecialEffect(this.mSignal);
        }
    }

    private static class AnimationInfo extends SpecialEffectsInfo {
        private FragmentAnim.AnimationOrAnimator mAnimation;
        private boolean mIsPop;
        private boolean mLoadedAnim = false;

        AnimationInfo(SpecialEffectsController.Operation operation, CancellationSignal signal, boolean isPop) {
            super(operation, signal);
            this.mIsPop = isPop;
        }

        /* access modifiers changed from: package-private */
        public FragmentAnim.AnimationOrAnimator getAnimation(Context context) {
            if (this.mLoadedAnim) {
                return this.mAnimation;
            }
            FragmentAnim.AnimationOrAnimator loadAnimation = FragmentAnim.loadAnimation(context, getOperation().getFragment(), getOperation().getFinalState() == SpecialEffectsController.Operation.State.VISIBLE, this.mIsPop);
            this.mAnimation = loadAnimation;
            this.mLoadedAnim = true;
            return loadAnimation;
        }
    }

    private static class TransitionInfo extends SpecialEffectsInfo {
        private final boolean mOverlapAllowed;
        private final Object mSharedElementTransition;
        private final Object mTransition;

        TransitionInfo(SpecialEffectsController.Operation operation, CancellationSignal signal, boolean isPop, boolean providesSharedElementTransition) {
            super(operation, signal);
            Object obj;
            Object obj2;
            boolean z;
            if (operation.getFinalState() == SpecialEffectsController.Operation.State.VISIBLE) {
                if (isPop) {
                    obj2 = operation.getFragment().getReenterTransition();
                } else {
                    obj2 = operation.getFragment().getEnterTransition();
                }
                this.mTransition = obj2;
                if (isPop) {
                    z = operation.getFragment().getAllowReturnTransitionOverlap();
                } else {
                    z = operation.getFragment().getAllowEnterTransitionOverlap();
                }
                this.mOverlapAllowed = z;
            } else {
                if (isPop) {
                    obj = operation.getFragment().getReturnTransition();
                } else {
                    obj = operation.getFragment().getExitTransition();
                }
                this.mTransition = obj;
                this.mOverlapAllowed = true;
            }
            if (!providesSharedElementTransition) {
                this.mSharedElementTransition = null;
            } else if (isPop) {
                this.mSharedElementTransition = operation.getFragment().getSharedElementReturnTransition();
            } else {
                this.mSharedElementTransition = operation.getFragment().getSharedElementEnterTransition();
            }
        }

        /* access modifiers changed from: package-private */
        public Object getTransition() {
            return this.mTransition;
        }

        /* access modifiers changed from: package-private */
        public boolean isOverlapAllowed() {
            return this.mOverlapAllowed;
        }

        public boolean hasSharedElementTransition() {
            return this.mSharedElementTransition != null;
        }

        public Object getSharedElementTransition() {
            return this.mSharedElementTransition;
        }

        /* access modifiers changed from: package-private */
        public FragmentTransitionImpl getHandlingImpl() {
            FragmentTransitionImpl transitionImpl = getHandlingImpl(this.mTransition);
            FragmentTransitionImpl sharedElementTransitionImpl = getHandlingImpl(this.mSharedElementTransition);
            if (transitionImpl == null || sharedElementTransitionImpl == null || transitionImpl == sharedElementTransitionImpl) {
                return transitionImpl != null ? transitionImpl : sharedElementTransitionImpl;
            }
            throw new IllegalArgumentException("Mixing framework transitions and AndroidX transitions is not allowed. Fragment " + getOperation().getFragment() + " returned Transition " + this.mTransition + " which uses a different Transition  type than its shared element transition " + this.mSharedElementTransition);
        }

        private FragmentTransitionImpl getHandlingImpl(Object transition) {
            if (transition == null) {
                return null;
            }
            if (FragmentTransition.PLATFORM_IMPL != null && FragmentTransition.PLATFORM_IMPL.canHandle(transition)) {
                return FragmentTransition.PLATFORM_IMPL;
            }
            if (FragmentTransition.SUPPORT_IMPL != null && FragmentTransition.SUPPORT_IMPL.canHandle(transition)) {
                return FragmentTransition.SUPPORT_IMPL;
            }
            throw new IllegalArgumentException("Transition " + transition + " for fragment " + getOperation().getFragment() + " is not a valid framework Transition or AndroidX Transition");
        }
    }
}
