package androidx.emoji2.text;

import android.os.Build;
import android.text.PrecomputedText;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import androidx.core.text.PrecomputedTextCompat;
import java.util.stream.IntStream;

class UnprecomputeTextOnModificationSpannable implements Spannable {
    private Spannable mDelegate;
    private boolean mSafeToWrite = false;

    UnprecomputeTextOnModificationSpannable(Spannable delegate) {
        this.mDelegate = delegate;
    }

    UnprecomputeTextOnModificationSpannable(Spanned delegate) {
        this.mDelegate = new SpannableString(delegate);
    }

    UnprecomputeTextOnModificationSpannable(CharSequence delegate) {
        this.mDelegate = new SpannableString(delegate);
    }

    private void ensureSafeWrites() {
        Spannable old = this.mDelegate;
        if (!this.mSafeToWrite && precomputedTextDetector().isPrecomputedText(old)) {
            this.mDelegate = new SpannableString(old);
        }
        this.mSafeToWrite = true;
    }

    /* access modifiers changed from: package-private */
    public Spannable getUnwrappedSpannable() {
        return this.mDelegate;
    }

    public void setSpan(Object o, int i, int i1, int i2) {
        ensureSafeWrites();
        this.mDelegate.setSpan(o, i, i1, i2);
    }

    public void removeSpan(Object o) {
        ensureSafeWrites();
        this.mDelegate.removeSpan(o);
    }

    public <T> T[] getSpans(int i, int i1, Class<T> aClass) {
        return this.mDelegate.getSpans(i, i1, aClass);
    }

    public int getSpanStart(Object o) {
        return this.mDelegate.getSpanStart(o);
    }

    public int getSpanEnd(Object o) {
        return this.mDelegate.getSpanEnd(o);
    }

    public int getSpanFlags(Object o) {
        return this.mDelegate.getSpanFlags(o);
    }

    public int nextSpanTransition(int i, int i1, Class aClass) {
        return this.mDelegate.nextSpanTransition(i, i1, aClass);
    }

    public int length() {
        return this.mDelegate.length();
    }

    public char charAt(int i) {
        return this.mDelegate.charAt(i);
    }

    public CharSequence subSequence(int i, int i1) {
        return this.mDelegate.subSequence(i, i1);
    }

    public String toString() {
        return this.mDelegate.toString();
    }

    public IntStream chars() {
        return CharSequenceHelper_API24.chars(this.mDelegate);
    }

    public IntStream codePoints() {
        return CharSequenceHelper_API24.codePoints(this.mDelegate);
    }

    private static class CharSequenceHelper_API24 {
        private CharSequenceHelper_API24() {
        }

        static IntStream codePoints(CharSequence charSequence) {
            return charSequence.codePoints();
        }

        static IntStream chars(CharSequence charSequence) {
            return charSequence.chars();
        }
    }

    static PrecomputedTextDetector precomputedTextDetector() {
        return Build.VERSION.SDK_INT < 28 ? new PrecomputedTextDetector() : new PrecomputedTextDetector_28();
    }

    static class PrecomputedTextDetector {
        PrecomputedTextDetector() {
        }

        /* access modifiers changed from: package-private */
        public boolean isPrecomputedText(CharSequence text) {
            return text instanceof PrecomputedTextCompat;
        }
    }

    static class PrecomputedTextDetector_28 extends PrecomputedTextDetector {
        PrecomputedTextDetector_28() {
        }

        /* access modifiers changed from: package-private */
        public boolean isPrecomputedText(CharSequence text) {
            return (text instanceof PrecomputedText) || (text instanceof PrecomputedTextCompat);
        }
    }
}
