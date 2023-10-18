package androidx.emoji2.text;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;

public final class TypefaceEmojiSpan extends EmojiSpan {
    private static Paint sDebugPaint;

    public TypefaceEmojiSpan(EmojiMetadata metadata) {
        super(metadata);
    }

    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        float f = x;
        if (EmojiCompat.get().isEmojiSpanIndicatorEnabled()) {
            canvas.drawRect(x, (float) top, f + ((float) getWidth()), (float) bottom, getDebugPaint());
        } else {
            int i = top;
            int i2 = bottom;
        }
        Canvas canvas2 = canvas;
        getMetadata().draw(canvas, x, (float) y, paint);
    }

    private static Paint getDebugPaint() {
        if (sDebugPaint == null) {
            TextPaint textPaint = new TextPaint();
            sDebugPaint = textPaint;
            textPaint.setColor(EmojiCompat.get().getEmojiSpanIndicatorColor());
            sDebugPaint.setStyle(Paint.Style.FILL);
        }
        return sDebugPaint;
    }
}
