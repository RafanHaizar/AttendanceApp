package androidx.core.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class ContentLoadingProgressBar extends ProgressBar {
    private static final int MIN_DELAY_MS = 500;
    private static final int MIN_SHOW_TIME_MS = 500;
    private final Runnable mDelayedHide;
    private final Runnable mDelayedShow;
    boolean mDismissed;
    boolean mPostedHide;
    boolean mPostedShow;
    long mStartTime;

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$new$0$androidx-core-widget-ContentLoadingProgressBar  reason: not valid java name */
    public /* synthetic */ void m1316lambda$new$0$androidxcorewidgetContentLoadingProgressBar() {
        this.mPostedHide = false;
        this.mStartTime = -1;
        setVisibility(8);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: lambda$new$1$androidx-core-widget-ContentLoadingProgressBar  reason: not valid java name */
    public /* synthetic */ void m1317lambda$new$1$androidxcorewidgetContentLoadingProgressBar() {
        this.mPostedShow = false;
        if (!this.mDismissed) {
            this.mStartTime = System.currentTimeMillis();
            setVisibility(0);
        }
    }

    public ContentLoadingProgressBar(Context context) {
        this(context, (AttributeSet) null);
    }

    public ContentLoadingProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        this.mStartTime = -1;
        this.mPostedHide = false;
        this.mPostedShow = false;
        this.mDismissed = false;
        this.mDelayedHide = new ContentLoadingProgressBar$$ExternalSyntheticLambda2(this);
        this.mDelayedShow = new ContentLoadingProgressBar$$ExternalSyntheticLambda3(this);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        removeCallbacks();
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks();
    }

    private void removeCallbacks() {
        removeCallbacks(this.mDelayedHide);
        removeCallbacks(this.mDelayedShow);
    }

    public void hide() {
        post(new ContentLoadingProgressBar$$ExternalSyntheticLambda1(this));
    }

    /* access modifiers changed from: private */
    public void hideOnUiThread() {
        this.mDismissed = true;
        removeCallbacks(this.mDelayedShow);
        this.mPostedShow = false;
        long currentTimeMillis = System.currentTimeMillis();
        long j = this.mStartTime;
        long diff = currentTimeMillis - j;
        if (diff >= 500 || j == -1) {
            setVisibility(8);
        } else if (!this.mPostedHide) {
            postDelayed(this.mDelayedHide, 500 - diff);
            this.mPostedHide = true;
        }
    }

    public void show() {
        post(new ContentLoadingProgressBar$$ExternalSyntheticLambda0(this));
    }

    /* access modifiers changed from: private */
    public void showOnUiThread() {
        this.mStartTime = -1;
        this.mDismissed = false;
        removeCallbacks(this.mDelayedHide);
        this.mPostedHide = false;
        if (!this.mPostedShow) {
            postDelayed(this.mDelayedShow, 500);
            this.mPostedShow = true;
        }
    }
}
