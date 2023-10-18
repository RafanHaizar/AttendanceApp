package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewOutlineProvider;
import androidx.appcompat.C0503R;
import androidx.constraintlayout.motion.widget.Debug;
import androidx.constraintlayout.motion.widget.FloatLayout;
import androidx.constraintlayout.widget.C0657R;
import androidx.core.view.GravityCompat;

public class MotionLabel extends View implements FloatLayout {
    private static final int MONOSPACE = 3;
    private static final int SANS = 1;
    private static final int SERIF = 2;
    static String TAG = "MotionLabel";
    private boolean mAutoSize = false;
    private int mAutoSizeTextType = 0;
    float mBackgroundPanX = Float.NaN;
    float mBackgroundPanY = Float.NaN;
    private float mBaseTextSize = Float.NaN;
    private float mDeltaLeft;
    private float mFloatHeight;
    private float mFloatWidth;
    private String mFontFamily;
    private int mGravity = 8388659;
    private Layout mLayout;
    boolean mNotBuilt = true;
    Matrix mOutlinePositionMatrix;
    private int mPaddingBottom = 1;
    private int mPaddingLeft = 1;
    private int mPaddingRight = 1;
    private int mPaddingTop = 1;
    TextPaint mPaint = new TextPaint();
    Path mPath = new Path();
    RectF mRect;
    float mRotate = Float.NaN;
    /* access modifiers changed from: private */
    public float mRound = Float.NaN;
    /* access modifiers changed from: private */
    public float mRoundPercent = 0.0f;
    private int mStyleIndex;
    Paint mTempPaint;
    Rect mTempRect;
    private String mText = "Hello World";
    private Drawable mTextBackground;
    private Bitmap mTextBackgroundBitmap;
    private Rect mTextBounds = new Rect();
    private int mTextFillColor = 65535;
    private int mTextOutlineColor = 65535;
    private float mTextOutlineThickness = 0.0f;
    private float mTextPanX = 0.0f;
    private float mTextPanY = 0.0f;
    private BitmapShader mTextShader;
    private Matrix mTextShaderMatrix;
    private float mTextSize = 48.0f;
    private int mTextureEffect = 0;
    private float mTextureHeight = Float.NaN;
    private float mTextureWidth = Float.NaN;
    private CharSequence mTransformed;
    private int mTypefaceIndex;
    private boolean mUseOutline = false;
    ViewOutlineProvider mViewOutlineProvider;
    float mZoom = Float.NaN;
    Paint paintCache = new Paint();
    float paintTextSize;

    public MotionLabel(Context context) {
        super(context);
        init(context, (AttributeSet) null);
    }

    public MotionLabel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MotionLabel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setUpTheme(context, attrs);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, C0657R.styleable.MotionLabel);
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                if (attr == C0657R.styleable.MotionLabel_android_text) {
                    setText(a.getText(attr));
                } else if (attr == C0657R.styleable.MotionLabel_android_fontFamily) {
                    this.mFontFamily = a.getString(attr);
                } else if (attr == C0657R.styleable.MotionLabel_scaleFromTextSize) {
                    this.mBaseTextSize = (float) a.getDimensionPixelSize(attr, (int) this.mBaseTextSize);
                } else if (attr == C0657R.styleable.MotionLabel_android_textSize) {
                    this.mTextSize = (float) a.getDimensionPixelSize(attr, (int) this.mTextSize);
                } else if (attr == C0657R.styleable.MotionLabel_android_textStyle) {
                    this.mStyleIndex = a.getInt(attr, this.mStyleIndex);
                } else if (attr == C0657R.styleable.MotionLabel_android_typeface) {
                    this.mTypefaceIndex = a.getInt(attr, this.mTypefaceIndex);
                } else if (attr == C0657R.styleable.MotionLabel_android_textColor) {
                    this.mTextFillColor = a.getColor(attr, this.mTextFillColor);
                } else if (attr == C0657R.styleable.MotionLabel_borderRound) {
                    this.mRound = a.getDimension(attr, this.mRound);
                    if (Build.VERSION.SDK_INT >= 21) {
                        setRound(this.mRound);
                    }
                } else if (attr == C0657R.styleable.MotionLabel_borderRoundPercent) {
                    this.mRoundPercent = a.getFloat(attr, this.mRoundPercent);
                    if (Build.VERSION.SDK_INT >= 21) {
                        setRoundPercent(this.mRoundPercent);
                    }
                } else if (attr == C0657R.styleable.MotionLabel_android_gravity) {
                    setGravity(a.getInt(attr, -1));
                } else if (attr == C0657R.styleable.MotionLabel_android_autoSizeTextType) {
                    this.mAutoSizeTextType = a.getInt(attr, 0);
                } else if (attr == C0657R.styleable.MotionLabel_textOutlineColor) {
                    this.mTextOutlineColor = a.getInt(attr, this.mTextOutlineColor);
                    this.mUseOutline = true;
                } else if (attr == C0657R.styleable.MotionLabel_textOutlineThickness) {
                    this.mTextOutlineThickness = a.getDimension(attr, this.mTextOutlineThickness);
                    this.mUseOutline = true;
                } else if (attr == C0657R.styleable.MotionLabel_textBackground) {
                    this.mTextBackground = a.getDrawable(attr);
                    this.mUseOutline = true;
                } else if (attr == C0657R.styleable.MotionLabel_textBackgroundPanX) {
                    this.mBackgroundPanX = a.getFloat(attr, this.mBackgroundPanX);
                } else if (attr == C0657R.styleable.MotionLabel_textBackgroundPanY) {
                    this.mBackgroundPanY = a.getFloat(attr, this.mBackgroundPanY);
                } else if (attr == C0657R.styleable.MotionLabel_textPanX) {
                    this.mTextPanX = a.getFloat(attr, this.mTextPanX);
                } else if (attr == C0657R.styleable.MotionLabel_textPanY) {
                    this.mTextPanY = a.getFloat(attr, this.mTextPanY);
                } else if (attr == C0657R.styleable.MotionLabel_textBackgroundRotate) {
                    this.mRotate = a.getFloat(attr, this.mRotate);
                } else if (attr == C0657R.styleable.MotionLabel_textBackgroundZoom) {
                    this.mZoom = a.getFloat(attr, this.mZoom);
                } else if (attr == C0657R.styleable.MotionLabel_textureHeight) {
                    this.mTextureHeight = a.getDimension(attr, this.mTextureHeight);
                } else if (attr == C0657R.styleable.MotionLabel_textureWidth) {
                    this.mTextureWidth = a.getDimension(attr, this.mTextureWidth);
                } else if (attr == C0657R.styleable.MotionLabel_textureEffect) {
                    this.mTextureEffect = a.getInt(attr, this.mTextureEffect);
                }
            }
            a.recycle();
        }
        setupTexture();
        setupPath();
    }

    /* access modifiers changed from: package-private */
    public Bitmap blur(Bitmap bitmapOriginal, int factor) {
        Long valueOf = Long.valueOf(System.nanoTime());
        int w = bitmapOriginal.getWidth() / 2;
        int h = bitmapOriginal.getHeight() / 2;
        Bitmap ret = Bitmap.createScaledBitmap(bitmapOriginal, w, h, true);
        for (int i = 0; i < factor && w >= 32 && h >= 32; i++) {
            w /= 2;
            h /= 2;
            ret = Bitmap.createScaledBitmap(ret, w, h, true);
        }
        return ret;
    }

    private void setupTexture() {
        if (this.mTextBackground != null) {
            this.mTextShaderMatrix = new Matrix();
            int iw = this.mTextBackground.getIntrinsicWidth();
            int ih = this.mTextBackground.getIntrinsicHeight();
            int i = 128;
            if (iw <= 0) {
                int w = getWidth();
                if (w == 0) {
                    w = Float.isNaN(this.mTextureWidth) ? 128 : (int) this.mTextureWidth;
                }
                iw = w;
            }
            if (ih <= 0) {
                int h = getHeight();
                if (h == 0) {
                    if (!Float.isNaN(this.mTextureHeight)) {
                        i = (int) this.mTextureHeight;
                    }
                    h = i;
                }
                ih = h;
            }
            if (this.mTextureEffect != 0) {
                iw /= 2;
                ih /= 2;
            }
            this.mTextBackgroundBitmap = Bitmap.createBitmap(iw, ih, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(this.mTextBackgroundBitmap);
            this.mTextBackground.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            this.mTextBackground.setFilterBitmap(true);
            this.mTextBackground.draw(canvas);
            if (this.mTextureEffect != 0) {
                this.mTextBackgroundBitmap = blur(this.mTextBackgroundBitmap, 4);
            }
            this.mTextShader = new BitmapShader(this.mTextBackgroundBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        }
    }

    private void adjustTexture(float l, float t, float r, float b) {
        if (this.mTextShaderMatrix != null) {
            this.mFloatWidth = r - l;
            this.mFloatHeight = b - t;
            updateShaderMatrix();
        }
    }

    public void setGravity(int gravity) {
        if ((gravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK) == 0) {
            gravity |= GravityCompat.START;
        }
        if ((gravity & 112) == 0) {
            gravity |= 48;
        }
        int i = gravity & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK;
        int i2 = this.mGravity;
        if (i != (i2 & GravityCompat.RELATIVE_HORIZONTAL_GRAVITY_MASK)) {
        }
        if (gravity != i2) {
            invalidate();
        }
        this.mGravity = gravity;
        switch (gravity & 112) {
            case 48:
                this.mTextPanY = -1.0f;
                break;
            case 80:
                this.mTextPanY = 1.0f;
                break;
            default:
                this.mTextPanY = 0.0f;
                break;
        }
        switch (8388615 & gravity) {
            case 3:
            case GravityCompat.START /*8388611*/:
                this.mTextPanX = -1.0f;
                return;
            case 5:
            case GravityCompat.END /*8388613*/:
                this.mTextPanX = 1.0f;
                return;
            default:
                this.mTextPanX = 0.0f;
                return;
        }
    }

    private float getHorizontalOffset() {
        float scale = Float.isNaN(this.mBaseTextSize) ? 1.0f : this.mTextSize / this.mBaseTextSize;
        TextPaint textPaint = this.mPaint;
        String str = this.mText;
        return (((((Float.isNaN(this.mFloatWidth) ? (float) getMeasuredWidth() : this.mFloatWidth) - ((float) getPaddingLeft())) - ((float) getPaddingRight())) - (textPaint.measureText(str, 0, str.length()) * scale)) * (this.mTextPanX + 1.0f)) / 2.0f;
    }

    private float getVerticalOffset() {
        float scale = Float.isNaN(this.mBaseTextSize) ? 1.0f : this.mTextSize / this.mBaseTextSize;
        Paint.FontMetrics fm = this.mPaint.getFontMetrics();
        return ((((((Float.isNaN(this.mFloatHeight) ? (float) getMeasuredHeight() : this.mFloatHeight) - ((float) getPaddingTop())) - ((float) getPaddingBottom())) - ((fm.descent - fm.ascent) * scale)) * (1.0f - this.mTextPanY)) / 2.0f) - (fm.ascent * scale);
    }

    private void setUpTheme(Context context, AttributeSet attrs) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(C0503R.attr.colorPrimary, typedValue, true);
        TextPaint textPaint = this.mPaint;
        int i = typedValue.data;
        this.mTextFillColor = i;
        textPaint.setColor(i);
    }

    public void setText(CharSequence text) {
        this.mText = text.toString();
        invalidate();
    }

    /* access modifiers changed from: package-private */
    public void setupPath() {
        this.mPaddingLeft = getPaddingLeft();
        this.mPaddingRight = getPaddingRight();
        this.mPaddingTop = getPaddingTop();
        this.mPaddingBottom = getPaddingBottom();
        setTypefaceFromAttrs(this.mFontFamily, this.mTypefaceIndex, this.mStyleIndex);
        this.mPaint.setColor(this.mTextFillColor);
        this.mPaint.setStrokeWidth(this.mTextOutlineThickness);
        this.mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.mPaint.setFlags(128);
        setTextSize(this.mTextSize);
        this.mPaint.setAntiAlias(true);
    }

    /* access modifiers changed from: package-private */
    public void buildShape(float scale) {
        if (this.mUseOutline || scale != 1.0f) {
            this.mPath.reset();
            String str = this.mText;
            int len = str.length();
            this.mPaint.getTextBounds(str, 0, len, this.mTextBounds);
            this.mPaint.getTextPath(str, 0, len, 0.0f, 0.0f, this.mPath);
            if (scale != 1.0f) {
                Log.v(TAG, Debug.getLoc() + " scale " + scale);
                Matrix matrix = new Matrix();
                matrix.postScale(scale, scale);
                this.mPath.transform(matrix);
            }
            Rect rect = this.mTextBounds;
            rect.right--;
            this.mTextBounds.left++;
            this.mTextBounds.bottom++;
            Rect rect2 = this.mTextBounds;
            rect2.top--;
            RectF rect3 = new RectF();
            rect3.bottom = (float) getHeight();
            rect3.right = (float) getWidth();
            this.mNotBuilt = false;
        }
    }

    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        boolean normalScale = Float.isNaN(this.mBaseTextSize);
        float scaleText = normalScale ? 1.0f : this.mTextSize / this.mBaseTextSize;
        this.mFloatWidth = (float) (r - l);
        this.mFloatHeight = (float) (b - t);
        if (this.mAutoSize) {
            if (this.mTempRect == null) {
                this.mTempPaint = new Paint();
                this.mTempRect = new Rect();
                this.mTempPaint.set(this.mPaint);
                this.paintTextSize = this.mTempPaint.getTextSize();
            }
            Paint paint = this.mTempPaint;
            String str = this.mText;
            paint.getTextBounds(str, 0, str.length(), this.mTempRect);
            int tw = this.mTempRect.width();
            int th = (int) (((float) this.mTempRect.height()) * 1.3f);
            float vw = (this.mFloatWidth - ((float) this.mPaddingRight)) - ((float) this.mPaddingLeft);
            float vh = (this.mFloatHeight - ((float) this.mPaddingBottom)) - ((float) this.mPaddingTop);
            if (!normalScale) {
                scaleText = ((float) tw) * vh > ((float) th) * vw ? vw / ((float) tw) : vh / ((float) th);
            } else if (((float) tw) * vh > ((float) th) * vw) {
                this.mPaint.setTextSize((this.paintTextSize * vw) / ((float) tw));
            } else {
                this.mPaint.setTextSize((this.paintTextSize * vh) / ((float) th));
            }
        }
        if (this.mUseOutline != 0 || !normalScale) {
            adjustTexture((float) l, (float) t, (float) r, (float) b);
            buildShape(scaleText);
        }
    }

    public void layout(float l, float t, float r, float b) {
        this.mDeltaLeft = l - ((float) ((int) (l + 0.5f)));
        int w = ((int) (r + 0.5f)) - ((int) (l + 0.5f));
        int h = ((int) (b + 0.5f)) - ((int) (t + 0.5f));
        this.mFloatWidth = r - l;
        this.mFloatHeight = b - t;
        adjustTexture(l, t, r, b);
        if (getMeasuredHeight() == h && getMeasuredWidth() == w) {
            super.layout((int) (l + 0.5f), (int) (t + 0.5f), (int) (r + 0.5f), (int) (0.5f + b));
        } else {
            measure(View.MeasureSpec.makeMeasureSpec(w, 1073741824), View.MeasureSpec.makeMeasureSpec(h, 1073741824));
            super.layout((int) (l + 0.5f), (int) (t + 0.5f), (int) (r + 0.5f), (int) (0.5f + b));
        }
        if (this.mAutoSize) {
            if (this.mTempRect == null) {
                this.mTempPaint = new Paint();
                this.mTempRect = new Rect();
                this.mTempPaint.set(this.mPaint);
                this.paintTextSize = this.mTempPaint.getTextSize();
            }
            this.mFloatWidth = r - l;
            this.mFloatHeight = b - t;
            Paint paint = this.mTempPaint;
            String str = this.mText;
            paint.getTextBounds(str, 0, str.length(), this.mTempRect);
            int tw = this.mTempRect.width();
            float th = ((float) this.mTempRect.height()) * 1.3f;
            float vw = ((r - l) - ((float) this.mPaddingRight)) - ((float) this.mPaddingLeft);
            float vh = ((b - t) - ((float) this.mPaddingBottom)) - ((float) this.mPaddingTop);
            if (((float) tw) * vh > th * vw) {
                this.mPaint.setTextSize((this.paintTextSize * vw) / ((float) tw));
            } else {
                this.mPaint.setTextSize((this.paintTextSize * vh) / th);
            }
            if (this.mUseOutline || !Float.isNaN(this.mBaseTextSize)) {
                buildShape(Float.isNaN(this.mBaseTextSize) ? 1.0f : this.mTextSize / this.mBaseTextSize);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        float scale = Float.isNaN(this.mBaseTextSize) ? 1.0f : this.mTextSize / this.mBaseTextSize;
        super.onDraw(canvas);
        if (this.mUseOutline || scale != 1.0f) {
            if (this.mNotBuilt) {
                buildShape(scale);
            }
            if (this.mOutlinePositionMatrix == null) {
                this.mOutlinePositionMatrix = new Matrix();
            }
            if (this.mUseOutline) {
                this.paintCache.set(this.mPaint);
                this.mOutlinePositionMatrix.reset();
                float x = ((float) this.mPaddingLeft) + getHorizontalOffset();
                float y = ((float) this.mPaddingTop) + getVerticalOffset();
                this.mOutlinePositionMatrix.postTranslate(x, y);
                this.mOutlinePositionMatrix.preScale(scale, scale);
                this.mPath.transform(this.mOutlinePositionMatrix);
                if (this.mTextShader != null) {
                    this.mPaint.setFilterBitmap(true);
                    this.mPaint.setShader(this.mTextShader);
                } else {
                    this.mPaint.setColor(this.mTextFillColor);
                }
                this.mPaint.setStyle(Paint.Style.FILL);
                this.mPaint.setStrokeWidth(this.mTextOutlineThickness);
                canvas.drawPath(this.mPath, this.mPaint);
                if (this.mTextShader != null) {
                    this.mPaint.setShader((Shader) null);
                }
                this.mPaint.setColor(this.mTextOutlineColor);
                this.mPaint.setStyle(Paint.Style.STROKE);
                this.mPaint.setStrokeWidth(this.mTextOutlineThickness);
                canvas.drawPath(this.mPath, this.mPaint);
                this.mOutlinePositionMatrix.reset();
                this.mOutlinePositionMatrix.postTranslate(-x, -y);
                this.mPath.transform(this.mOutlinePositionMatrix);
                this.mPaint.set(this.paintCache);
                return;
            }
            float x2 = ((float) this.mPaddingLeft) + getHorizontalOffset();
            float y2 = ((float) this.mPaddingTop) + getVerticalOffset();
            this.mOutlinePositionMatrix.reset();
            this.mOutlinePositionMatrix.preTranslate(x2, y2);
            this.mPath.transform(this.mOutlinePositionMatrix);
            this.mPaint.setColor(this.mTextFillColor);
            this.mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            this.mPaint.setStrokeWidth(this.mTextOutlineThickness);
            canvas.drawPath(this.mPath, this.mPaint);
            this.mOutlinePositionMatrix.reset();
            this.mOutlinePositionMatrix.preTranslate(-x2, -y2);
            this.mPath.transform(this.mOutlinePositionMatrix);
            return;
        }
        canvas.drawText(this.mText, this.mDeltaLeft + ((float) this.mPaddingLeft) + getHorizontalOffset(), ((float) this.mPaddingTop) + getVerticalOffset(), this.mPaint);
    }

    public void setTextOutlineThickness(float width) {
        this.mTextOutlineThickness = width;
        this.mUseOutline = true;
        if (Float.isNaN(width)) {
            this.mTextOutlineThickness = 1.0f;
            this.mUseOutline = false;
        }
        invalidate();
    }

    public void setTextFillColor(int color) {
        this.mTextFillColor = color;
        invalidate();
    }

    public void setTextOutlineColor(int color) {
        this.mTextOutlineColor = color;
        this.mUseOutline = true;
        invalidate();
    }

    private void setTypefaceFromAttrs(String familyName, int typefaceIndex, int styleIndex) {
        Typeface tf;
        Typeface tf2 = null;
        if (familyName == null || (tf2 = Typeface.create(familyName, styleIndex)) == null) {
            switch (typefaceIndex) {
                case 1:
                    tf2 = Typeface.SANS_SERIF;
                    break;
                case 2:
                    tf2 = Typeface.SERIF;
                    break;
                case 3:
                    tf2 = Typeface.MONOSPACE;
                    break;
            }
            float f = 0.0f;
            boolean z = false;
            if (styleIndex > 0) {
                if (tf2 == null) {
                    tf = Typeface.defaultFromStyle(styleIndex);
                } else {
                    tf = Typeface.create(tf2, styleIndex);
                }
                setTypeface(tf);
                int need = ((tf != null ? tf.getStyle() : 0) ^ -1) & styleIndex;
                TextPaint textPaint = this.mPaint;
                if ((need & 1) != 0) {
                    z = true;
                }
                textPaint.setFakeBoldText(z);
                TextPaint textPaint2 = this.mPaint;
                if ((need & 2) != 0) {
                    f = -0.25f;
                }
                textPaint2.setTextSkewX(f);
                return;
            }
            this.mPaint.setFakeBoldText(false);
            this.mPaint.setTextSkewX(0.0f);
            setTypeface(tf2);
            return;
        }
        setTypeface(tf2);
    }

    public void setTypeface(Typeface tf) {
        if (this.mPaint.getTypeface() != tf) {
            this.mPaint.setTypeface(tf);
            if (this.mLayout != null) {
                this.mLayout = null;
                requestLayout();
                invalidate();
            }
        }
    }

    public Typeface getTypeface() {
        return this.mPaint.getTypeface();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height;
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        int width = View.MeasureSpec.getSize(widthMeasureSpec);
        int height2 = View.MeasureSpec.getSize(heightMeasureSpec);
        this.mAutoSize = false;
        this.mPaddingLeft = getPaddingLeft();
        this.mPaddingRight = getPaddingRight();
        this.mPaddingTop = getPaddingTop();
        this.mPaddingBottom = getPaddingBottom();
        if (widthMode != 1073741824 || heightMode != 1073741824) {
            TextPaint textPaint = this.mPaint;
            String str = this.mText;
            textPaint.getTextBounds(str, 0, str.length(), this.mTextBounds);
            if (widthMode != 1073741824) {
                width = (int) (((float) this.mTextBounds.width()) + 0.99999f);
            }
            width += this.mPaddingLeft + this.mPaddingRight;
            if (heightMode != 1073741824) {
                int desired = (int) (((float) this.mPaint.getFontMetricsInt((Paint.FontMetricsInt) null)) + 0.99999f);
                if (heightMode == Integer.MIN_VALUE) {
                    height = Math.min(height2, desired);
                } else {
                    height = desired;
                }
                height2 = height + this.mPaddingTop + this.mPaddingBottom;
            }
        } else if (this.mAutoSizeTextType != 0) {
            this.mAutoSize = true;
        }
        setMeasuredDimension(width, height2);
    }

    public void setRoundPercent(float round) {
        boolean change = this.mRoundPercent != round;
        this.mRoundPercent = round;
        if (round != 0.0f) {
            if (this.mPath == null) {
                this.mPath = new Path();
            }
            if (this.mRect == null) {
                this.mRect = new RectF();
            }
            if (Build.VERSION.SDK_INT >= 21) {
                if (this.mViewOutlineProvider == null) {
                    C06531 r2 = new ViewOutlineProvider() {
                        public void getOutline(View view, Outline outline) {
                            int w = MotionLabel.this.getWidth();
                            int h = MotionLabel.this.getHeight();
                            outline.setRoundRect(0, 0, w, h, (((float) Math.min(w, h)) * MotionLabel.this.mRoundPercent) / 2.0f);
                        }
                    };
                    this.mViewOutlineProvider = r2;
                    setOutlineProvider(r2);
                }
                setClipToOutline(true);
            }
            int w = getWidth();
            int h = getHeight();
            float r = (((float) Math.min(w, h)) * this.mRoundPercent) / 2.0f;
            this.mRect.set(0.0f, 0.0f, (float) w, (float) h);
            this.mPath.reset();
            this.mPath.addRoundRect(this.mRect, r, r, Path.Direction.CW);
        } else if (Build.VERSION.SDK_INT >= 21) {
            setClipToOutline(false);
        }
        if (change && Build.VERSION.SDK_INT >= 21) {
            invalidateOutline();
        }
    }

    public void setRound(float round) {
        if (Float.isNaN(round)) {
            this.mRound = round;
            float tmp = this.mRoundPercent;
            this.mRoundPercent = -1.0f;
            setRoundPercent(tmp);
            return;
        }
        boolean change = this.mRound != round;
        this.mRound = round;
        if (round != 0.0f) {
            if (this.mPath == null) {
                this.mPath = new Path();
            }
            if (this.mRect == null) {
                this.mRect = new RectF();
            }
            if (Build.VERSION.SDK_INT >= 21) {
                if (this.mViewOutlineProvider == null) {
                    C06542 r2 = new ViewOutlineProvider() {
                        public void getOutline(View view, Outline outline) {
                            outline.setRoundRect(0, 0, MotionLabel.this.getWidth(), MotionLabel.this.getHeight(), MotionLabel.this.mRound);
                        }
                    };
                    this.mViewOutlineProvider = r2;
                    setOutlineProvider(r2);
                }
                setClipToOutline(true);
            }
            this.mRect.set(0.0f, 0.0f, (float) getWidth(), (float) getHeight());
            this.mPath.reset();
            Path path = this.mPath;
            RectF rectF = this.mRect;
            float f = this.mRound;
            path.addRoundRect(rectF, f, f, Path.Direction.CW);
        } else if (Build.VERSION.SDK_INT >= 21) {
            setClipToOutline(false);
        }
        if (change && Build.VERSION.SDK_INT >= 21) {
            invalidateOutline();
        }
    }

    public float getRoundPercent() {
        return this.mRoundPercent;
    }

    public float getRound() {
        return this.mRound;
    }

    public void setTextSize(float size) {
        this.mTextSize = size;
        Log.v(TAG, Debug.getLoc() + "  " + size + " / " + this.mBaseTextSize);
        this.mPaint.setTextSize(Float.isNaN(this.mBaseTextSize) ? size : this.mBaseTextSize);
        buildShape(Float.isNaN(this.mBaseTextSize) ? 1.0f : this.mTextSize / this.mBaseTextSize);
        requestLayout();
        invalidate();
    }

    public int getTextOutlineColor() {
        return this.mTextOutlineColor;
    }

    public float getTextBackgroundPanX() {
        return this.mBackgroundPanX;
    }

    public float getTextBackgroundPanY() {
        return this.mBackgroundPanY;
    }

    public float getTextBackgroundZoom() {
        return this.mZoom;
    }

    public float getTextBackgroundRotate() {
        return this.mRotate;
    }

    public void setTextBackgroundPanX(float pan) {
        this.mBackgroundPanX = pan;
        updateShaderMatrix();
        invalidate();
    }

    public void setTextBackgroundPanY(float pan) {
        this.mBackgroundPanY = pan;
        updateShaderMatrix();
        invalidate();
    }

    public void setTextBackgroundZoom(float zoom) {
        this.mZoom = zoom;
        updateShaderMatrix();
        invalidate();
    }

    public void setTextBackgroundRotate(float rotation) {
        this.mRotate = rotation;
        updateShaderMatrix();
        invalidate();
    }

    private void updateShaderMatrix() {
        float rota = 0.0f;
        float panX = Float.isNaN(this.mBackgroundPanX) ? 0.0f : this.mBackgroundPanX;
        float panY = Float.isNaN(this.mBackgroundPanY) ? 0.0f : this.mBackgroundPanY;
        float zoom = Float.isNaN(this.mZoom) ? 1.0f : this.mZoom;
        if (!Float.isNaN(this.mRotate)) {
            rota = this.mRotate;
        }
        this.mTextShaderMatrix.reset();
        float iw = (float) this.mTextBackgroundBitmap.getWidth();
        float ih = (float) this.mTextBackgroundBitmap.getHeight();
        float sw = Float.isNaN(this.mTextureWidth) ? this.mFloatWidth : this.mTextureWidth;
        float sh = Float.isNaN(this.mTextureHeight) ? this.mFloatHeight : this.mTextureHeight;
        float scale = (iw * sh < ih * sw ? sw / iw : sh / ih) * zoom;
        this.mTextShaderMatrix.postScale(scale, scale);
        float gapx = sw - (scale * iw);
        float gapy = sh - (scale * ih);
        if (!Float.isNaN(this.mTextureHeight)) {
            gapy = this.mTextureHeight / 2.0f;
        }
        if (!Float.isNaN(this.mTextureWidth)) {
            gapx = this.mTextureWidth / 2.0f;
        }
        this.mTextShaderMatrix.postTranslate((((panX * gapx) + sw) - (scale * iw)) * 0.5f, (((panY * gapy) + sh) - (scale * ih)) * 0.5f);
        float f = panX;
        this.mTextShaderMatrix.postRotate(rota, sw / 2.0f, sh / 2.0f);
        this.mTextShader.setLocalMatrix(this.mTextShaderMatrix);
    }

    public float getTextPanX() {
        return this.mTextPanX;
    }

    public void setTextPanX(float textPanX) {
        this.mTextPanX = textPanX;
        invalidate();
    }

    public float getTextPanY() {
        return this.mTextPanY;
    }

    public void setTextPanY(float textPanY) {
        this.mTextPanY = textPanY;
        invalidate();
    }

    public float getTextureHeight() {
        return this.mTextureHeight;
    }

    public void setTextureHeight(float mTextureHeight2) {
        this.mTextureHeight = mTextureHeight2;
        updateShaderMatrix();
        invalidate();
    }

    public float getTextureWidth() {
        return this.mTextureWidth;
    }

    public void setTextureWidth(float mTextureWidth2) {
        this.mTextureWidth = mTextureWidth2;
        updateShaderMatrix();
        invalidate();
    }

    public float getScaleFromTextSize() {
        return this.mBaseTextSize;
    }

    public void setScaleFromTextSize(float size) {
        this.mBaseTextSize = size;
    }
}
