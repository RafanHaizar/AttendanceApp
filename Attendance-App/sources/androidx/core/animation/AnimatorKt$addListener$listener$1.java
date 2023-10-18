package androidx.core.animation;

import android.animation.Animator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u0019\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\u0007\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016J\u0010\u0010\b\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0016¨\u0006\t"}, mo113d2 = {"androidx/core/animation/AnimatorKt$addListener$listener$1", "Landroid/animation/Animator$AnimatorListener;", "onAnimationCancel", "", "animator", "Landroid/animation/Animator;", "onAnimationEnd", "onAnimationRepeat", "onAnimationStart", "core-ktx_release"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 176)
/* compiled from: Animator.kt */
public final class AnimatorKt$addListener$listener$1 implements Animator.AnimatorListener {
    final /* synthetic */ Function1<Animator, Unit> $onCancel;
    final /* synthetic */ Function1<Animator, Unit> $onEnd;
    final /* synthetic */ Function1<Animator, Unit> $onRepeat;
    final /* synthetic */ Function1<Animator, Unit> $onStart;

    public AnimatorKt$addListener$listener$1(Function1<? super Animator, Unit> $onRepeat2, Function1<? super Animator, Unit> $onEnd2, Function1<? super Animator, Unit> $onCancel2, Function1<? super Animator, Unit> $onStart2) {
        this.$onRepeat = $onRepeat2;
        this.$onEnd = $onEnd2;
        this.$onCancel = $onCancel2;
        this.$onStart = $onStart2;
    }

    public void onAnimationRepeat(Animator animator) {
        Intrinsics.checkNotNullParameter(animator, "animator");
        this.$onRepeat.invoke(animator);
    }

    public void onAnimationEnd(Animator animator) {
        Intrinsics.checkNotNullParameter(animator, "animator");
        this.$onEnd.invoke(animator);
    }

    public void onAnimationCancel(Animator animator) {
        Intrinsics.checkNotNullParameter(animator, "animator");
        this.$onCancel.invoke(animator);
    }

    public void onAnimationStart(Animator animator) {
        Intrinsics.checkNotNullParameter(animator, "animator");
        this.$onStart.invoke(animator);
    }
}
