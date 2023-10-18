package p004pl.droidsonroids.gif;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/* renamed from: pl.droidsonroids.gif.GifViewSavedState */
class GifViewSavedState extends View.BaseSavedState {
    public static final Parcelable.Creator<GifViewSavedState> CREATOR = new Parcelable.Creator<GifViewSavedState>() {
        public GifViewSavedState createFromParcel(Parcel in) {
            return new GifViewSavedState(in);
        }

        public GifViewSavedState[] newArray(int size) {
            return new GifViewSavedState[size];
        }
    };
    final long[][] mStates;

    GifViewSavedState(Parcelable superState, Drawable... drawables) {
        super(superState);
        this.mStates = new long[drawables.length][];
        for (int i = 0; i < drawables.length; i++) {
            GifDrawable gifDrawable = drawables[i];
            if (gifDrawable instanceof GifDrawable) {
                this.mStates[i] = gifDrawable.mNativeInfoHandle.getSavedState();
            } else {
                this.mStates[i] = null;
            }
        }
    }

    private GifViewSavedState(Parcel in) {
        super(in);
        this.mStates = new long[in.readInt()][];
        int i = 0;
        while (true) {
            long[][] jArr = this.mStates;
            if (i < jArr.length) {
                jArr[i] = in.createLongArray();
                i++;
            } else {
                return;
            }
        }
    }

    GifViewSavedState(Parcelable superState, long[] savedState) {
        super(superState);
        long[][] jArr = new long[1][];
        this.mStates = jArr;
        jArr[0] = savedState;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.mStates.length);
        for (long[] mState : this.mStates) {
            dest.writeLongArray(mState);
        }
    }

    /* access modifiers changed from: package-private */
    public void restoreState(Drawable drawable, int i) {
        if (this.mStates[i] != null && (drawable instanceof GifDrawable)) {
            GifDrawable gifDrawable = (GifDrawable) drawable;
            gifDrawable.startAnimation((long) gifDrawable.mNativeInfoHandle.restoreSavedState(this.mStates[i], gifDrawable.mBuffer));
        }
    }
}
