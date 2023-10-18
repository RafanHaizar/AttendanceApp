package com.google.android.material.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseBooleanArray;

public class ParcelableSparseBooleanArray extends SparseBooleanArray implements Parcelable {
    public static final Parcelable.Creator<ParcelableSparseBooleanArray> CREATOR = new Parcelable.Creator<ParcelableSparseBooleanArray>() {
        public ParcelableSparseBooleanArray createFromParcel(Parcel source) {
            int size = source.readInt();
            ParcelableSparseBooleanArray read = new ParcelableSparseBooleanArray(size);
            int[] keys = new int[size];
            boolean[] values = new boolean[size];
            source.readIntArray(keys);
            source.readBooleanArray(values);
            for (int i = 0; i < size; i++) {
                read.put(keys[i], values[i]);
            }
            return read;
        }

        public ParcelableSparseBooleanArray[] newArray(int size) {
            return new ParcelableSparseBooleanArray[size];
        }
    };

    public ParcelableSparseBooleanArray() {
    }

    public ParcelableSparseBooleanArray(int initialCapacity) {
        super(initialCapacity);
    }

    public ParcelableSparseBooleanArray(SparseBooleanArray sparseBooleanArray) {
        super(sparseBooleanArray.size());
        for (int i = 0; i < sparseBooleanArray.size(); i++) {
            put(sparseBooleanArray.keyAt(i), sparseBooleanArray.valueAt(i));
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        int[] keys = new int[size()];
        boolean[] values = new boolean[size()];
        for (int i = 0; i < size(); i++) {
            keys[i] = keyAt(i);
            values[i] = valueAt(i);
        }
        dest.writeInt(size());
        dest.writeIntArray(keys);
        dest.writeBooleanArray(values);
    }
}
