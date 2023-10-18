package p004pl.droidsonroids.gif.transforms;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;

/* renamed from: pl.droidsonroids.gif.transforms.CornerRadiusTransform */
public class CornerRadiusTransform implements Transform {
    private float mCornerRadius;
    private final RectF mDstRectF = new RectF();
    private Shader mShader;

    public CornerRadiusTransform(float cornerRadius) {
        setCornerRadiusSafely(cornerRadius);
    }

    public void setCornerRadius(float cornerRadius) {
        setCornerRadiusSafely(cornerRadius);
    }

    private void setCornerRadiusSafely(float cornerRadius) {
        float cornerRadius2 = Math.max(0.0f, cornerRadius);
        if (cornerRadius2 != this.mCornerRadius) {
            this.mCornerRadius = cornerRadius2;
            this.mShader = null;
        }
    }

    public float getCornerRadius() {
        return this.mCornerRadius;
    }

    public RectF getBounds() {
        return this.mDstRectF;
    }

    public void onBoundsChange(Rect bounds) {
        this.mDstRectF.set(bounds);
        this.mShader = null;
    }

    public void onDraw(Canvas canvas, Paint paint, Bitmap buffer) {
        if (this.mCornerRadius == 0.0f) {
            canvas.drawBitmap(buffer, (Rect) null, this.mDstRectF, paint);
            return;
        }
        if (this.mShader == null) {
            this.mShader = new BitmapShader(buffer, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            Matrix shaderMatrix = new Matrix();
            shaderMatrix.setTranslate(this.mDstRectF.left, this.mDstRectF.top);
            shaderMatrix.preScale(this.mDstRectF.width() / ((float) buffer.getWidth()), this.mDstRectF.height() / ((float) buffer.getHeight()));
            this.mShader.setLocalMatrix(shaderMatrix);
        }
        paint.setShader(this.mShader);
        RectF rectF = this.mDstRectF;
        float f = this.mCornerRadius;
        canvas.drawRoundRect(rectF, f, f, paint);
    }
}
