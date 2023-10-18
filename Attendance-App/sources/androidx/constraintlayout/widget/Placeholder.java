package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.core.widgets.ConstraintWidget;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Placeholder extends View {
    private View mContent = null;
    private int mContentId = -1;
    private int mEmptyVisibility = 4;

    public Placeholder(Context context) {
        super(context);
        init((AttributeSet) null);
    }

    public Placeholder(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public Placeholder(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public Placeholder(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        super.setVisibility(this.mEmptyVisibility);
        this.mContentId = -1;
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, C0657R.styleable.ConstraintLayout_placeholder);
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                if (attr == C0657R.styleable.ConstraintLayout_placeholder_content) {
                    this.mContentId = a.getResourceId(attr, this.mContentId);
                } else if (attr == C0657R.styleable.ConstraintLayout_placeholder_placeholder_emptyVisibility) {
                    this.mEmptyVisibility = a.getInt(attr, this.mEmptyVisibility);
                }
            }
            a.recycle();
        }
    }

    public void setEmptyVisibility(int visibility) {
        this.mEmptyVisibility = visibility;
    }

    public int getEmptyVisibility() {
        return this.mEmptyVisibility;
    }

    public View getContent() {
        return this.mContent;
    }

    public void onDraw(Canvas canvas) {
        if (isInEditMode()) {
            canvas.drawRGB(223, 223, 223);
            Paint paint = new Paint();
            paint.setARGB(255, 210, 210, 210);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTypeface(Typeface.create(Typeface.DEFAULT, 0));
            Rect r = new Rect();
            canvas.getClipBounds(r);
            paint.setTextSize((float) r.height());
            int cHeight = r.height();
            int cWidth = r.width();
            paint.setTextAlign(Paint.Align.LEFT);
            paint.getTextBounds("?", 0, "?".length(), r);
            canvas.drawText("?", ((((float) cWidth) / 2.0f) - (((float) r.width()) / 2.0f)) - ((float) r.left), ((((float) cHeight) / 2.0f) + (((float) r.height()) / 2.0f)) - ((float) r.bottom), paint);
        }
    }

    public void updatePreLayout(ConstraintLayout container) {
        if (this.mContentId == -1 && !isInEditMode()) {
            setVisibility(this.mEmptyVisibility);
        }
        View findViewById = container.findViewById(this.mContentId);
        this.mContent = findViewById;
        if (findViewById != null) {
            ((ConstraintLayout.LayoutParams) findViewById.getLayoutParams()).isInPlaceholder = true;
            this.mContent.setVisibility(0);
            setVisibility(0);
        }
    }

    public void setContentId(int id) {
        View v;
        if (this.mContentId != id) {
            View view = this.mContent;
            if (view != null) {
                view.setVisibility(0);
                ((ConstraintLayout.LayoutParams) this.mContent.getLayoutParams()).isInPlaceholder = false;
                this.mContent = null;
            }
            this.mContentId = id;
            if (id != -1 && (v = ((View) getParent()).findViewById(id)) != null) {
                v.setVisibility(8);
            }
        }
    }

    public void updatePostMeasure(ConstraintLayout container) {
        if (this.mContent != null) {
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) getLayoutParams();
            ConstraintLayout.LayoutParams layoutParamsContent = (ConstraintLayout.LayoutParams) this.mContent.getLayoutParams();
            layoutParamsContent.widget.setVisibility(0);
            if (layoutParams.widget.getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.FIXED) {
                layoutParams.widget.setWidth(layoutParamsContent.widget.getWidth());
            }
            if (layoutParams.widget.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.FIXED) {
                layoutParams.widget.setHeight(layoutParamsContent.widget.getHeight());
            }
            layoutParamsContent.widget.setVisibility(8);
        }
    }
}
