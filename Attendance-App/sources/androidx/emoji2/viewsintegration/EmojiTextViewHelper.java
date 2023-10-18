package androidx.emoji2.viewsintegration;

import android.text.InputFilter;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.SparseArray;
import android.widget.TextView;
import androidx.core.util.Preconditions;
import androidx.emoji2.text.EmojiCompat;

public final class EmojiTextViewHelper {
    private final HelperInternal mHelper;

    public EmojiTextViewHelper(TextView textView) {
        this(textView, true);
    }

    public EmojiTextViewHelper(TextView textView, boolean expectInitializedEmojiCompat) {
        Preconditions.checkNotNull(textView, "textView cannot be null");
        if (!expectInitializedEmojiCompat) {
            this.mHelper = new SkippingHelper19(textView);
        } else {
            this.mHelper = new HelperInternal19(textView);
        }
    }

    public void updateTransformationMethod() {
        this.mHelper.updateTransformationMethod();
    }

    public InputFilter[] getFilters(InputFilter[] filters) {
        return this.mHelper.getFilters(filters);
    }

    public TransformationMethod wrapTransformationMethod(TransformationMethod transformationMethod) {
        return this.mHelper.wrapTransformationMethod(transformationMethod);
    }

    public void setEnabled(boolean enabled) {
        this.mHelper.setEnabled(enabled);
    }

    public void setAllCaps(boolean allCaps) {
        this.mHelper.setAllCaps(allCaps);
    }

    public boolean isEnabled() {
        return this.mHelper.isEnabled();
    }

    static class HelperInternal {
        HelperInternal() {
        }

        /* access modifiers changed from: package-private */
        public void updateTransformationMethod() {
        }

        /* access modifiers changed from: package-private */
        public InputFilter[] getFilters(InputFilter[] filters) {
            return filters;
        }

        /* access modifiers changed from: package-private */
        public TransformationMethod wrapTransformationMethod(TransformationMethod transformationMethod) {
            return transformationMethod;
        }

        /* access modifiers changed from: package-private */
        public void setAllCaps(boolean allCaps) {
        }

        /* access modifiers changed from: package-private */
        public void setEnabled(boolean processEmoji) {
        }

        public boolean isEnabled() {
            return false;
        }
    }

    private static class SkippingHelper19 extends HelperInternal {
        private final HelperInternal19 mHelperDelegate;

        SkippingHelper19(TextView textView) {
            this.mHelperDelegate = new HelperInternal19(textView);
        }

        private boolean skipBecauseEmojiCompatNotInitialized() {
            return !EmojiCompat.isConfigured();
        }

        /* access modifiers changed from: package-private */
        public void updateTransformationMethod() {
            if (!skipBecauseEmojiCompatNotInitialized()) {
                this.mHelperDelegate.updateTransformationMethod();
            }
        }

        /* access modifiers changed from: package-private */
        public InputFilter[] getFilters(InputFilter[] filters) {
            if (skipBecauseEmojiCompatNotInitialized()) {
                return filters;
            }
            return this.mHelperDelegate.getFilters(filters);
        }

        /* access modifiers changed from: package-private */
        public TransformationMethod wrapTransformationMethod(TransformationMethod transformationMethod) {
            if (skipBecauseEmojiCompatNotInitialized()) {
                return transformationMethod;
            }
            return this.mHelperDelegate.wrapTransformationMethod(transformationMethod);
        }

        /* access modifiers changed from: package-private */
        public void setAllCaps(boolean allCaps) {
            if (!skipBecauseEmojiCompatNotInitialized()) {
                this.mHelperDelegate.setAllCaps(allCaps);
            }
        }

        /* access modifiers changed from: package-private */
        public void setEnabled(boolean processEmoji) {
            if (skipBecauseEmojiCompatNotInitialized()) {
                this.mHelperDelegate.setEnabledUnsafe(processEmoji);
            } else {
                this.mHelperDelegate.setEnabled(processEmoji);
            }
        }

        public boolean isEnabled() {
            return this.mHelperDelegate.isEnabled();
        }
    }

    private static class HelperInternal19 extends HelperInternal {
        private final EmojiInputFilter mEmojiInputFilter;
        private boolean mEnabled = true;
        private final TextView mTextView;

        HelperInternal19(TextView textView) {
            this.mTextView = textView;
            this.mEmojiInputFilter = new EmojiInputFilter(textView);
        }

        /* access modifiers changed from: package-private */
        public void updateTransformationMethod() {
            this.mTextView.setTransformationMethod(wrapTransformationMethod(this.mTextView.getTransformationMethod()));
        }

        private void updateFilters() {
            this.mTextView.setFilters(getFilters(this.mTextView.getFilters()));
        }

        /* access modifiers changed from: package-private */
        public InputFilter[] getFilters(InputFilter[] filters) {
            if (!this.mEnabled) {
                return removeEmojiInputFilterIfPresent(filters);
            }
            return addEmojiInputFilterIfMissing(filters);
        }

        private InputFilter[] addEmojiInputFilterIfMissing(InputFilter[] filters) {
            for (EmojiInputFilter emojiInputFilter : filters) {
                if (emojiInputFilter == this.mEmojiInputFilter) {
                    return filters;
                }
            }
            InputFilter[] newFilters = new InputFilter[(filters.length + 1)];
            System.arraycopy(filters, 0, newFilters, 0, count);
            newFilters[count] = this.mEmojiInputFilter;
            return newFilters;
        }

        private InputFilter[] removeEmojiInputFilterIfPresent(InputFilter[] filters) {
            SparseArray<InputFilter> filterSet = getEmojiInputFilterPositionArray(filters);
            if (filterSet.size() == 0) {
                return filters;
            }
            int inCount = filters.length;
            InputFilter[] result = new InputFilter[(filters.length - filterSet.size())];
            int destPosition = 0;
            for (int srcPosition = 0; srcPosition < inCount; srcPosition++) {
                if (filterSet.indexOfKey(srcPosition) < 0) {
                    result[destPosition] = filters[srcPosition];
                    destPosition++;
                }
            }
            return result;
        }

        private SparseArray<InputFilter> getEmojiInputFilterPositionArray(InputFilter[] filters) {
            SparseArray<InputFilter> result = new SparseArray<>(1);
            for (int pos = 0; pos < filters.length; pos++) {
                if (filters[pos] instanceof EmojiInputFilter) {
                    result.put(pos, filters[pos]);
                }
            }
            return result;
        }

        /* access modifiers changed from: package-private */
        public TransformationMethod wrapTransformationMethod(TransformationMethod transformationMethod) {
            if (this.mEnabled) {
                return wrapForEnabled(transformationMethod);
            }
            return unwrapForDisabled(transformationMethod);
        }

        private TransformationMethod unwrapForDisabled(TransformationMethod transformationMethod) {
            if (transformationMethod instanceof EmojiTransformationMethod) {
                return ((EmojiTransformationMethod) transformationMethod).getOriginalTransformationMethod();
            }
            return transformationMethod;
        }

        private TransformationMethod wrapForEnabled(TransformationMethod transformationMethod) {
            if (!(transformationMethod instanceof EmojiTransformationMethod) && !(transformationMethod instanceof PasswordTransformationMethod)) {
                return new EmojiTransformationMethod(transformationMethod);
            }
            return transformationMethod;
        }

        /* access modifiers changed from: package-private */
        public void setAllCaps(boolean allCaps) {
            if (allCaps) {
                updateTransformationMethod();
            }
        }

        /* access modifiers changed from: package-private */
        public void setEnabled(boolean enabled) {
            this.mEnabled = enabled;
            updateTransformationMethod();
            updateFilters();
        }

        public boolean isEnabled() {
            return this.mEnabled;
        }

        /* access modifiers changed from: package-private */
        public void setEnabledUnsafe(boolean processEmoji) {
            this.mEnabled = processEmoji;
        }
    }
}
