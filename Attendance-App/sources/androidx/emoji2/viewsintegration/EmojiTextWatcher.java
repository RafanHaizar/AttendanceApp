package androidx.emoji2.viewsintegration;

import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.widget.EditText;
import androidx.emoji2.text.EmojiCompat;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

final class EmojiTextWatcher implements TextWatcher {
    private final EditText mEditText;
    private int mEmojiReplaceStrategy = 0;
    private boolean mEnabled;
    private final boolean mExpectInitializedEmojiCompat;
    private EmojiCompat.InitCallback mInitCallback;
    private int mMaxEmojiCount = Integer.MAX_VALUE;

    EmojiTextWatcher(EditText editText, boolean expectInitializedEmojiCompat) {
        this.mEditText = editText;
        this.mExpectInitializedEmojiCompat = expectInitializedEmojiCompat;
        this.mEnabled = true;
    }

    /* access modifiers changed from: package-private */
    public void setMaxEmojiCount(int maxEmojiCount) {
        this.mMaxEmojiCount = maxEmojiCount;
    }

    /* access modifiers changed from: package-private */
    public int getMaxEmojiCount() {
        return this.mMaxEmojiCount;
    }

    /* access modifiers changed from: package-private */
    public int getEmojiReplaceStrategy() {
        return this.mEmojiReplaceStrategy;
    }

    /* access modifiers changed from: package-private */
    public void setEmojiReplaceStrategy(int replaceStrategy) {
        this.mEmojiReplaceStrategy = replaceStrategy;
    }

    public void onTextChanged(CharSequence charSequence, int start, int before, int after) {
        if (!this.mEditText.isInEditMode() && !shouldSkipForDisabledOrNotConfigured() && before <= after && (charSequence instanceof Spannable)) {
            switch (EmojiCompat.get().getLoadState()) {
                case 0:
                case 3:
                    EmojiCompat.get().registerInitCallback(getInitCallback());
                    return;
                case 1:
                    EmojiCompat.get().process((Spannable) charSequence, start, start + after, this.mMaxEmojiCount, this.mEmojiReplaceStrategy);
                    return;
                default:
                    return;
            }
        }
    }

    private boolean shouldSkipForDisabledOrNotConfigured() {
        return !this.mEnabled || (!this.mExpectInitializedEmojiCompat && !EmojiCompat.isConfigured());
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void afterTextChanged(Editable s) {
    }

    private EmojiCompat.InitCallback getInitCallback() {
        if (this.mInitCallback == null) {
            this.mInitCallback = new InitCallbackImpl(this.mEditText);
        }
        return this.mInitCallback;
    }

    public boolean isEnabled() {
        return this.mEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        if (this.mEnabled != isEnabled) {
            if (this.mInitCallback != null) {
                EmojiCompat.get().unregisterInitCallback(this.mInitCallback);
            }
            this.mEnabled = isEnabled;
            if (isEnabled) {
                processTextOnEnablingEvent(this.mEditText, EmojiCompat.get().getLoadState());
            }
        }
    }

    private static class InitCallbackImpl extends EmojiCompat.InitCallback {
        private final Reference<EditText> mViewRef;

        InitCallbackImpl(EditText editText) {
            this.mViewRef = new WeakReference(editText);
        }

        public void onInitialized() {
            super.onInitialized();
            EmojiTextWatcher.processTextOnEnablingEvent(this.mViewRef.get(), 1);
        }
    }

    static void processTextOnEnablingEvent(EditText editText, int currentLoadState) {
        if (currentLoadState == 1 && editText != null && editText.isAttachedToWindow()) {
            Editable text = editText.getEditableText();
            int selectionStart = Selection.getSelectionStart(text);
            int selectionEnd = Selection.getSelectionEnd(text);
            EmojiCompat.get().process(text);
            EmojiInputFilter.updateSelection(text, selectionStart, selectionEnd);
        }
    }
}
