package androidx.appcompat.widget;

import android.content.res.TypedArray;
import android.text.method.KeyListener;
import android.text.method.NumberKeyListener;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;
import androidx.appcompat.C0503R;
import androidx.emoji2.viewsintegration.EmojiEditTextHelper;

class AppCompatEmojiEditTextHelper {
    private final EmojiEditTextHelper mEmojiEditTextHelper;
    private final EditText mView;

    AppCompatEmojiEditTextHelper(EditText view) {
        this.mView = view;
        this.mEmojiEditTextHelper = new EmojiEditTextHelper(view, false);
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
    public boolean isEmojiCapableKeyListener(KeyListener currentKeyListener) {
        return !(currentKeyListener instanceof NumberKeyListener);
    }

    /* access modifiers changed from: package-private */
    public void setEnabled(boolean enabled) {
        this.mEmojiEditTextHelper.setEnabled(enabled);
    }

    /* access modifiers changed from: package-private */
    public boolean isEnabled() {
        return this.mEmojiEditTextHelper.isEnabled();
    }

    /* access modifiers changed from: package-private */
    public KeyListener getKeyListener(KeyListener keyListener) {
        if (isEmojiCapableKeyListener(keyListener)) {
            return this.mEmojiEditTextHelper.getKeyListener(keyListener);
        }
        return keyListener;
    }

    /* access modifiers changed from: package-private */
    public InputConnection onCreateInputConnection(InputConnection inputConnection, EditorInfo outAttrs) {
        return this.mEmojiEditTextHelper.onCreateInputConnection(inputConnection, outAttrs);
    }
}
