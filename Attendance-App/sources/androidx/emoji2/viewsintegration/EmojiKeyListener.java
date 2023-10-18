package androidx.emoji2.viewsintegration;

import android.text.Editable;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import androidx.emoji2.text.EmojiCompat;

final class EmojiKeyListener implements KeyListener {
    private final EmojiCompatHandleKeyDownHelper mEmojiCompatHandleKeyDownHelper;
    private final KeyListener mKeyListener;

    EmojiKeyListener(KeyListener keyListener) {
        this(keyListener, new EmojiCompatHandleKeyDownHelper());
    }

    EmojiKeyListener(KeyListener keyListener, EmojiCompatHandleKeyDownHelper emojiCompatKeydownHelper) {
        this.mKeyListener = keyListener;
        this.mEmojiCompatHandleKeyDownHelper = emojiCompatKeydownHelper;
    }

    public int getInputType() {
        return this.mKeyListener.getInputType();
    }

    public boolean onKeyDown(View view, Editable content, int keyCode, KeyEvent event) {
        return this.mEmojiCompatHandleKeyDownHelper.handleKeyDown(content, keyCode, event) || this.mKeyListener.onKeyDown(view, content, keyCode, event);
    }

    public boolean onKeyUp(View view, Editable text, int keyCode, KeyEvent event) {
        return this.mKeyListener.onKeyUp(view, text, keyCode, event);
    }

    public boolean onKeyOther(View view, Editable text, KeyEvent event) {
        return this.mKeyListener.onKeyOther(view, text, event);
    }

    public void clearMetaKeyState(View view, Editable content, int states) {
        this.mKeyListener.clearMetaKeyState(view, content, states);
    }

    public static class EmojiCompatHandleKeyDownHelper {
        public boolean handleKeyDown(Editable editable, int keyCode, KeyEvent event) {
            return EmojiCompat.handleOnKeyDown(editable, keyCode, event);
        }
    }
}
