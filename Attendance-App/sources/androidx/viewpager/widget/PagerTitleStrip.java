package androidx.viewpager.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.text.method.SingleLineTransformationMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;
import androidx.viewpager.widget.ViewPager;
import java.lang.ref.WeakReference;
import java.util.Locale;

@ViewPager.DecorView
public class PagerTitleStrip extends ViewGroup {
    private static final int[] ATTRS = {16842804, 16842901, 16842904, 16842927};
    private static final float SIDE_ALPHA = 0.6f;
    private static final int[] TEXT_ATTRS = {16843660};
    private static final int TEXT_SPACING = 16;
    TextView mCurrText;
    private int mGravity;
    private int mLastKnownCurrentPage;
    float mLastKnownPositionOffset;
    TextView mNextText;
    private int mNonPrimaryAlpha;
    private final PageListener mPageListener;
    ViewPager mPager;
    TextView mPrevText;
    private int mScaledTextSpacing;
    int mTextColor;
    private boolean mUpdatingPositions;
    private boolean mUpdatingText;
    private WeakReference<PagerAdapter> mWatchingAdapter;

    private static class SingleLineAllCapsTransform extends SingleLineTransformationMethod {
        private Locale mLocale;

        SingleLineAllCapsTransform(Context context) {
            this.mLocale = context.getResources().getConfiguration().locale;
        }

        public CharSequence getTransformation(CharSequence source, View view) {
            CharSequence source2 = super.getTransformation(source, view);
            if (source2 != null) {
                return source2.toString().toUpperCase(this.mLocale);
            }
            return null;
        }
    }

    private static void setSingleLineAllCaps(TextView text) {
        text.setTransformationMethod(new SingleLineAllCapsTransform(text.getContext()));
    }

    public PagerTitleStrip(Context context) {
        this(context, (AttributeSet) null);
    }

    public PagerTitleStrip(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mLastKnownCurrentPage = -1;
        this.mLastKnownPositionOffset = -1.0f;
        this.mPageListener = new PageListener();
        TextView textView = new TextView(context);
        this.mPrevText = textView;
        addView(textView);
        TextView textView2 = new TextView(context);
        this.mCurrText = textView2;
        addView(textView2);
        TextView textView3 = new TextView(context);
        this.mNextText = textView3;
        addView(textView3);
        TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);
        int textAppearance = a.getResourceId(0, 0);
        if (textAppearance != 0) {
            TextViewCompat.setTextAppearance(this.mPrevText, textAppearance);
            TextViewCompat.setTextAppearance(this.mCurrText, textAppearance);
            TextViewCompat.setTextAppearance(this.mNextText, textAppearance);
        }
        int textSize = a.getDimensionPixelSize(1, 0);
        if (textSize != 0) {
            setTextSize(0, (float) textSize);
        }
        if (a.hasValue(2)) {
            int textColor = a.getColor(2, 0);
            this.mPrevText.setTextColor(textColor);
            this.mCurrText.setTextColor(textColor);
            this.mNextText.setTextColor(textColor);
        }
        this.mGravity = a.getInteger(3, 80);
        a.recycle();
        this.mTextColor = this.mCurrText.getTextColors().getDefaultColor();
        setNonPrimaryAlpha(SIDE_ALPHA);
        this.mPrevText.setEllipsize(TextUtils.TruncateAt.END);
        this.mCurrText.setEllipsize(TextUtils.TruncateAt.END);
        this.mNextText.setEllipsize(TextUtils.TruncateAt.END);
        boolean allCaps = false;
        if (textAppearance != 0) {
            TypedArray ta = context.obtainStyledAttributes(textAppearance, TEXT_ATTRS);
            allCaps = ta.getBoolean(0, false);
            ta.recycle();
        }
        if (allCaps) {
            setSingleLineAllCaps(this.mPrevText);
            setSingleLineAllCaps(this.mCurrText);
            setSingleLineAllCaps(this.mNextText);
        } else {
            this.mPrevText.setSingleLine();
            this.mCurrText.setSingleLine();
            this.mNextText.setSingleLine();
        }
        this.mScaledTextSpacing = (int) (16.0f * context.getResources().getDisplayMetrics().density);
    }

    public void setTextSpacing(int spacingPixels) {
        this.mScaledTextSpacing = spacingPixels;
        requestLayout();
    }

    public int getTextSpacing() {
        return this.mScaledTextSpacing;
    }

    public void setNonPrimaryAlpha(float alpha) {
        int i = ((int) (255.0f * alpha)) & 255;
        this.mNonPrimaryAlpha = i;
        int transparentColor = (i << 24) | (this.mTextColor & ViewCompat.MEASURED_SIZE_MASK);
        this.mPrevText.setTextColor(transparentColor);
        this.mNextText.setTextColor(transparentColor);
    }

    public void setTextColor(int color) {
        this.mTextColor = color;
        this.mCurrText.setTextColor(color);
        int transparentColor = (this.mNonPrimaryAlpha << 24) | (this.mTextColor & ViewCompat.MEASURED_SIZE_MASK);
        this.mPrevText.setTextColor(transparentColor);
        this.mNextText.setTextColor(transparentColor);
    }

    public void setTextSize(int unit, float size) {
        this.mPrevText.setTextSize(unit, size);
        this.mCurrText.setTextSize(unit, size);
        this.mNextText.setTextSize(unit, size);
    }

    public void setGravity(int gravity) {
        this.mGravity = gravity;
        requestLayout();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ViewParent parent = getParent();
        if (parent instanceof ViewPager) {
            ViewPager pager = (ViewPager) parent;
            PagerAdapter adapter = pager.getAdapter();
            pager.setInternalPageChangeListener(this.mPageListener);
            pager.addOnAdapterChangeListener(this.mPageListener);
            this.mPager = pager;
            WeakReference<PagerAdapter> weakReference = this.mWatchingAdapter;
            updateAdapter(weakReference != null ? (PagerAdapter) weakReference.get() : null, adapter);
            return;
        }
        throw new IllegalStateException("PagerTitleStrip must be a direct child of a ViewPager.");
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ViewPager viewPager = this.mPager;
        if (viewPager != null) {
            updateAdapter(viewPager.getAdapter(), (PagerAdapter) null);
            this.mPager.setInternalPageChangeListener((ViewPager.OnPageChangeListener) null);
            this.mPager.removeOnAdapterChangeListener(this.mPageListener);
            this.mPager = null;
        }
    }

    /* access modifiers changed from: package-private */
    public void updateText(int currentItem, PagerAdapter adapter) {
        int itemCount = adapter != null ? adapter.getCount() : 0;
        this.mUpdatingText = true;
        CharSequence text = null;
        if (currentItem >= 1 && adapter != null) {
            text = adapter.getPageTitle(currentItem - 1);
        }
        this.mPrevText.setText(text);
        this.mCurrText.setText((adapter == null || currentItem >= itemCount) ? null : adapter.getPageTitle(currentItem));
        CharSequence text2 = null;
        if (currentItem + 1 < itemCount && adapter != null) {
            text2 = adapter.getPageTitle(currentItem + 1);
        }
        this.mNextText.setText(text2);
        int childWidthSpec = View.MeasureSpec.makeMeasureSpec(Math.max(0, (int) (((float) ((getWidth() - getPaddingLeft()) - getPaddingRight())) * 0.8f)), Integer.MIN_VALUE);
        int childHeightSpec = View.MeasureSpec.makeMeasureSpec(Math.max(0, (getHeight() - getPaddingTop()) - getPaddingBottom()), Integer.MIN_VALUE);
        this.mPrevText.measure(childWidthSpec, childHeightSpec);
        this.mCurrText.measure(childWidthSpec, childHeightSpec);
        this.mNextText.measure(childWidthSpec, childHeightSpec);
        this.mLastKnownCurrentPage = currentItem;
        if (!this.mUpdatingPositions) {
            updateTextPositions(currentItem, this.mLastKnownPositionOffset, false);
        }
        this.mUpdatingText = false;
    }

    public void requestLayout() {
        if (!this.mUpdatingText) {
            super.requestLayout();
        }
    }

    /* access modifiers changed from: package-private */
    public void updateAdapter(PagerAdapter oldAdapter, PagerAdapter newAdapter) {
        if (oldAdapter != null) {
            oldAdapter.unregisterDataSetObserver(this.mPageListener);
            this.mWatchingAdapter = null;
        }
        if (newAdapter != null) {
            newAdapter.registerDataSetObserver(this.mPageListener);
            this.mWatchingAdapter = new WeakReference<>(newAdapter);
        }
        ViewPager viewPager = this.mPager;
        if (viewPager != null) {
            this.mLastKnownCurrentPage = -1;
            this.mLastKnownPositionOffset = -1.0f;
            updateText(viewPager.getCurrentItem(), newAdapter);
            requestLayout();
        }
    }

    /* access modifiers changed from: package-private */
    public void updateTextPositions(int i, float f, boolean z) {
        int i2;
        int i3;
        int i4;
        int i5 = i;
        float f2 = f;
        if (i5 != this.mLastKnownCurrentPage) {
            updateText(i5, this.mPager.getAdapter());
        } else if (!z && f2 == this.mLastKnownPositionOffset) {
            return;
        }
        this.mUpdatingPositions = true;
        int measuredWidth = this.mPrevText.getMeasuredWidth();
        int measuredWidth2 = this.mCurrText.getMeasuredWidth();
        int measuredWidth3 = this.mNextText.getMeasuredWidth();
        int i6 = measuredWidth2 / 2;
        int width = getWidth();
        int height = getHeight();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int i7 = paddingRight + i6;
        int i8 = (width - (paddingLeft + i6)) - i7;
        float f3 = 0.5f + f2;
        if (f3 > 1.0f) {
            f3 -= 1.0f;
        }
        int i9 = ((width - i7) - ((int) (((float) i8) * f3))) - i6;
        int i10 = measuredWidth2 + i9;
        int baseline = this.mPrevText.getBaseline();
        int baseline2 = this.mCurrText.getBaseline();
        int baseline3 = this.mNextText.getBaseline();
        int max = Math.max(Math.max(baseline, baseline2), baseline3);
        int i11 = max - baseline;
        int i12 = max - baseline2;
        int i13 = max - baseline3;
        int i14 = measuredWidth3;
        int max2 = Math.max(Math.max(this.mPrevText.getMeasuredHeight() + i11, this.mCurrText.getMeasuredHeight() + i12), this.mNextText.getMeasuredHeight() + i13);
        switch (this.mGravity & 112) {
            case 16:
                int i15 = (((height - paddingTop) - paddingBottom) - max2) / 2;
                i4 = i11 + i15;
                i2 = i12 + i15;
                i3 = i15 + i13;
                break;
            case 80:
                int i16 = (height - paddingBottom) - max2;
                i4 = i11 + i16;
                i2 = i12 + i16;
                i3 = i16 + i13;
                break;
            default:
                i4 = i11 + paddingTop;
                i2 = i12 + paddingTop;
                i3 = paddingTop + i13;
                break;
        }
        TextView textView = this.mCurrText;
        textView.layout(i9, i2, i10, textView.getMeasuredHeight() + i2);
        int min = Math.min(paddingLeft, (i9 - this.mScaledTextSpacing) - measuredWidth);
        TextView textView2 = this.mPrevText;
        textView2.layout(min, i4, measuredWidth + min, textView2.getMeasuredHeight() + i4);
        int max3 = Math.max((width - paddingRight) - i14, i10 + this.mScaledTextSpacing);
        TextView textView3 = this.mNextText;
        textView3.layout(max3, i3, max3 + i14, textView3.getMeasuredHeight() + i3);
        this.mLastKnownPositionOffset = f;
        this.mUpdatingPositions = false;
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int textHeight;
        if (View.MeasureSpec.getMode(widthMeasureSpec) == 1073741824) {
            int heightPadding = getPaddingTop() + getPaddingBottom();
            int childHeightSpec = getChildMeasureSpec(heightMeasureSpec, heightPadding, -2);
            int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
            int childWidthSpec = getChildMeasureSpec(widthMeasureSpec, (int) (((float) widthSize) * 0.2f), -2);
            this.mPrevText.measure(childWidthSpec, childHeightSpec);
            this.mCurrText.measure(childWidthSpec, childHeightSpec);
            this.mNextText.measure(childWidthSpec, childHeightSpec);
            if (View.MeasureSpec.getMode(heightMeasureSpec) == 1073741824) {
                textHeight = View.MeasureSpec.getSize(heightMeasureSpec);
            } else {
                textHeight = Math.max(getMinHeight(), this.mCurrText.getMeasuredHeight() + heightPadding);
            }
            setMeasuredDimension(widthSize, View.resolveSizeAndState(textHeight, heightMeasureSpec, this.mCurrText.getMeasuredState() << 16));
            return;
        }
        throw new IllegalStateException("Must measure with an exact width");
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        if (this.mPager != null) {
            float offset = this.mLastKnownPositionOffset;
            if (offset < 0.0f) {
                offset = 0.0f;
            }
            updateTextPositions(this.mLastKnownCurrentPage, offset, true);
        }
    }

    /* access modifiers changed from: package-private */
    public int getMinHeight() {
        Drawable bg = getBackground();
        if (bg != null) {
            return bg.getIntrinsicHeight();
        }
        return 0;
    }

    private class PageListener extends DataSetObserver implements ViewPager.OnPageChangeListener, ViewPager.OnAdapterChangeListener {
        private int mScrollState;

        PageListener() {
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (positionOffset > 0.5f) {
                position++;
            }
            PagerTitleStrip.this.updateTextPositions(position, positionOffset, false);
        }

        public void onPageSelected(int position) {
            if (this.mScrollState == 0) {
                PagerTitleStrip pagerTitleStrip = PagerTitleStrip.this;
                pagerTitleStrip.updateText(pagerTitleStrip.mPager.getCurrentItem(), PagerTitleStrip.this.mPager.getAdapter());
                float f = 0.0f;
                if (PagerTitleStrip.this.mLastKnownPositionOffset >= 0.0f) {
                    f = PagerTitleStrip.this.mLastKnownPositionOffset;
                }
                float offset = f;
                PagerTitleStrip pagerTitleStrip2 = PagerTitleStrip.this;
                pagerTitleStrip2.updateTextPositions(pagerTitleStrip2.mPager.getCurrentItem(), offset, true);
            }
        }

        public void onPageScrollStateChanged(int state) {
            this.mScrollState = state;
        }

        public void onAdapterChanged(ViewPager viewPager, PagerAdapter oldAdapter, PagerAdapter newAdapter) {
            PagerTitleStrip.this.updateAdapter(oldAdapter, newAdapter);
        }

        public void onChanged() {
            PagerTitleStrip pagerTitleStrip = PagerTitleStrip.this;
            pagerTitleStrip.updateText(pagerTitleStrip.mPager.getCurrentItem(), PagerTitleStrip.this.mPager.getAdapter());
            float f = 0.0f;
            if (PagerTitleStrip.this.mLastKnownPositionOffset >= 0.0f) {
                f = PagerTitleStrip.this.mLastKnownPositionOffset;
            }
            float offset = f;
            PagerTitleStrip pagerTitleStrip2 = PagerTitleStrip.this;
            pagerTitleStrip2.updateTextPositions(pagerTitleStrip2.mPager.getCurrentItem(), offset, true);
        }
    }
}
