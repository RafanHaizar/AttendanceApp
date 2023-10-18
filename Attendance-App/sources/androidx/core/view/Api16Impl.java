package androidx.core.view;

import android.view.View;
import com.itextpdf.svg.SvgConstants;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\bÃ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J \u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007¨\u0006\u000b"}, mo113d2 = {"Landroidx/core/view/Api16Impl;", "", "()V", "postOnAnimationDelayed", "", "view", "Landroid/view/View;", "action", "Ljava/lang/Runnable;", "delayInMillis", "", "core-ktx_release"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: View.kt */
final class Api16Impl {
    public static final Api16Impl INSTANCE = new Api16Impl();

    private Api16Impl() {
    }

    @JvmStatic
    public static final void postOnAnimationDelayed(View view, Runnable action, long delayInMillis) {
        Intrinsics.checkNotNullParameter(view, SvgConstants.Tags.VIEW);
        Intrinsics.checkNotNullParameter(action, "action");
        view.postOnAnimationDelayed(action, delayInMillis);
    }
}
