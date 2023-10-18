package androidx.constraintlayout.utils.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.widget.C0657R;

public class MockView extends View {
    private int mDiagonalsColor = Color.argb(255, 0, 0, 0);
    private boolean mDrawDiagonals = true;
    private boolean mDrawLabel = true;
    private int mMargin = 4;
    private Paint mPaintDiagonals = new Paint();
    private Paint mPaintText = new Paint();
    private Paint mPaintTextBackground = new Paint();
    protected String mText = null;
    private int mTextBackgroundColor = Color.argb(255, 50, 50, 50);
    private Rect mTextBounds = new Rect();
    private int mTextColor = Color.argb(255, 200, 200, 200);

    public MockView(Context context) {
        super(context);
        init(context, (AttributeSet) null);
    }

    public MockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, C0657R.styleable.MockView);
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                if (attr == C0657R.styleable.MockView_mock_label) {
                    this.mText = a.getString(attr);
                } else if (attr == C0657R.styleable.MockView_mock_showDiagonals) {
                    this.mDrawDiagonals = a.getBoolean(attr, this.mDrawDiagonals);
                } else if (attr == C0657R.styleable.MockView_mock_diagonalsColor) {
                    this.mDiagonalsColor = a.getColor(attr, this.mDiagonalsColor);
                } else if (attr == C0657R.styleable.MockView_mock_labelBackgroundColor) {
                    this.mTextBackgroundColor = a.getColor(attr, this.mTextBackgroundColor);
                } else if (attr == C0657R.styleable.MockView_mock_labelColor) {
                    this.mTextColor = a.getColor(attr, this.mTextColor);
                } else if (attr == C0657R.styleable.MockView_mock_showLabel) {
                    this.mDrawLabel = a.getBoolean(attr, this.mDrawLabel);
                }
            }
            a.recycle();
        }
        if (this.mText == null) {
            try {
                this.mText = context.getResources().getResourceEntryName(getId());
            } catch (Exception e) {
            }
        }
        this.mPaintDiagonals.setColor(this.mDiagonalsColor);
        this.mPaintDiagonals.setAntiAlias(true);
        this.mPaintText.setColor(this.mTextColor);
        this.mPaintText.setAntiAlias(true);
        this.mPaintTextBackground.setColor(this.mTextBackgroundColor);
        this.mMargin = Math.round(((float) this.mMargin) * (getResources().getDisplayMetrics().xdpi / 160.0f));
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        int h = getHeight();
        if (this.mDrawDiagonals) {
            w--;
            h--;
            Canvas canvas2 = canvas;
            canvas2.drawLine(0.0f, 0.0f, (float) w, (float) h, this.mPaintDiagonals);
            Canvas canvas3 = canvas;
            canvas3.drawLine(0.0f, (float) h, (float) w, 0.0f, this.mPaintDiagonals);
            canvas2.drawLine(0.0f, 0.0f, (float) w, 0.0f, this.mPaintDiagonals);
            canvas3.drawLine((float) w, 0.0f, (float) w, (float) h, this.mPaintDiagonals);
            canvas2.drawLine((float) w, (float) h, 0.0f, (float) h, this.mPaintDiagonals);
            canvas3.drawLine(0.0f, (float) h, 0.0f, 0.0f, this.mPaintDiagonals);
        }
        String str = this.mText;
        if (str != null && this.mDrawLabel) {
            this.mPaintText.getTextBounds(str, 0, str.length(), this.mTextBounds);
            float tx = ((float) (w - this.mTextBounds.width())) / 2.0f;
            float ty = (((float) (h - this.mTextBounds.height())) / 2.0f) + ((float) this.mTextBounds.height());
            this.mTextBounds.offset((int) tx, (int) ty);
            Rect rect = this.mTextBounds;
            rect.set(rect.left - this.mMargin, this.mTextBounds.top - this.mMargin, this.mTextBounds.right + this.mMargin, this.mTextBounds.bottom + this.mMargin);
            canvas.drawRect(this.mTextBounds, this.mPaintTextBackground);
            canvas.drawText(this.mText, tx, ty, this.mPaintText);
        }
    }
}
