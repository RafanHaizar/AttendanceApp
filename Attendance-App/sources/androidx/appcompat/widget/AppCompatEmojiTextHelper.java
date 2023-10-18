package androidx.appcompat.widget;

import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.appcompat.C0503R;
import androidx.emoji2.viewsintegration.EmojiTextViewHelper;

class AppCompatEmojiTextHelper {
    private final EmojiTextViewHelper mEmojiTextViewHelper;
    private final TextView mView;

    AppCompatEmojiTextHelper(TextView view) {
        this.mView = view;
        this.mEmojiTextViewHelper = new EmojiTextViewHelper(view, false);
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: package-private */
    public void loadFromAttributes(AttributeSet attrs, int defStyleAttr) {
        TypedArray a = this.mView.getContext().obtainStyledAttributes(attrs, C0503R.styleable.AppCompatTextView, defStyleAttr, 0);
        boolean enabled = true;
        try {
            if (a.hasValue(C0503R.styleable.AppCompatTextView_emojiCompatEnabled)) {
                enabled = a.getBoolean(C0503R.styleable.AppCompatTextView_emojiCompatEnabled, true);
            }
            a.recycle();
            setEnabled(enabled);
        } catch (Throwable th) {
            a.recycle();
            throw th;
        }
    }

    /* access modifiers changed from: package-private */
    public void setEnabled(boolean enabled) {
        this.mEmojiTextViewHelper.setEnabled(enabled);
    }

    public boolean isEnabled() {
        return this.mEmojiTextViewHelper.isEnabled();
    }

    /* access modifiers changed from: package-private */
    public InputFilter[] getFilters(InputFilter[] filters) {
        return this.mEmojiTextViewHelper.getFilters(filters);
    }

    /* access modifiers changed from: package-private */
    public void setAllCaps(boolean allCaps) {
        this.mEmojiTextViewHelper.setAllCaps(allCaps);
    }

    public TransformationMethod wrapTransformationMethod(TransformationMethod transformationMethod) {
        return this.mEmojiTextViewHelper.wrapTransformationMethod(transformationMethod);
    }
}
