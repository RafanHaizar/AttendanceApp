package androidx.appcompat.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;

class ActionBarBackgroundDrawable extends Drawable {
    final ActionBarContainer mContainer;

    public ActionBarBackgroundDrawable(ActionBarContainer container) {
        this.mContainer = container;
    }

    public void draw(Canvas canvas) {
        if (!this.mContainer.mIsSplit) {
            if (this.mContainer.mBackground != null) {
                this.mContainer.mBackground.draw(canvas);
            }
            if (this.mContainer.mStackedBackground != null && this.mContainer.mIsStacked) {
                this.mContainer.mStackedBackground.draw(canvas);
            }
        } else if (this.mContainer.mSplitBackground != null) {
            this.mContainer.mSplitBackground.draw(canvas);
        }
    }

    public void setAlpha(int alpha) {
    }

    public void setColorFilter(ColorFilter cf) {
    }

    public int getOpacity() {
        return 0;
    }

    public void getOutline(Outline outline) {
        if (this.mContainer.mIsSplit) {
            if (this.mContainer.mSplitBackground != null) {
                Api21Impl.getOutline(this.mContainer.mBackground, outline);
            }
        } else if (this.mContainer.mBackground != null) {
            Api21Impl.getOutline(this.mContainer.mBackground, outline);
        }
    }

    private static class Api21Impl {
        private Api21Impl() {
        }

        public static void getOutline(Drawable drawable, Outline outline) {
            drawable.getOutline(outline);
        }
    }
}
