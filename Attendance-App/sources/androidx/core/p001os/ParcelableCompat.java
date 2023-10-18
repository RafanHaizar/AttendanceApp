package androidx.core.p001os;

import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* renamed from: androidx.core.os.ParcelableCompat */
public final class ParcelableCompat {
    @Deprecated
    public static <T> Parcelable.Creator<T> newCreator(ParcelableCompatCreatorCallbacks<T> callbacks) {
        return new ParcelableCompatCreatorHoneycombMR2(callbacks);
    }

    /* renamed from: androidx.core.os.ParcelableCompat$ParcelableCompatCreatorHoneycombMR2 */
    static class ParcelableCompatCreatorHoneycombMR2<T> implements Parcelable.ClassLoaderCreator<T> {
        private final ParcelableCompatCreatorCallbacks<T> mCallbacks;

        ParcelableCompatCreatorHoneycombMR2(ParcelableCompatCreatorCallbacks<T> callbacks) {
            this.mCallbacks = callbacks;
        }

        public T createFromParcel(Parcel in) {
            return this.mCallbacks.createFromParcel(in, (ClassLoader) null);
        }

        public T createFromParcel(Parcel in, ClassLoader loader) {
            return this.mCallbacks.createFromParcel(in, loader);
        }

        public T[] newArray(int size) {
            return this.mCallbacks.newArray(size);
        }
    }

    private ParcelableCompat() {
    }
}
