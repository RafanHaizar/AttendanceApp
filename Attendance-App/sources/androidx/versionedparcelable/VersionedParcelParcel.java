package androidx.versionedparcelable;

import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.SparseIntArray;
import androidx.collection.ArrayMap;
import java.lang.reflect.Method;

class VersionedParcelParcel extends VersionedParcel {
    private static final boolean DEBUG = false;
    private static final String TAG = "VersionedParcelParcel";
    private int mCurrentField;
    private final int mEnd;
    private int mFieldId;
    private int mNextRead;
    private final int mOffset;
    private final Parcel mParcel;
    private final SparseIntArray mPositionLookup;
    private final String mPrefix;

    VersionedParcelParcel(Parcel p) {
        this(p, p.dataPosition(), p.dataSize(), "", new ArrayMap(), new ArrayMap(), new ArrayMap());
    }

    private VersionedParcelParcel(Parcel p, int offset, int end, String prefix, ArrayMap<String, Method> readCache, ArrayMap<String, Method> writeCache, ArrayMap<String, Class> parcelizerCache) {
        super(readCache, writeCache, parcelizerCache);
        this.mPositionLookup = new SparseIntArray();
        this.mCurrentField = -1;
        this.mNextRead = 0;
        this.mFieldId = -1;
        this.mParcel = p;
        this.mOffset = offset;
        this.mEnd = end;
        this.mNextRead = offset;
        this.mPrefix = prefix;
    }

    public boolean readField(int fieldId) {
        while (this.mNextRead < this.mEnd) {
            int i = this.mFieldId;
            if (i == fieldId) {
                return true;
            }
            if (String.valueOf(i).compareTo(String.valueOf(fieldId)) > 0) {
                return false;
            }
            this.mParcel.setDataPosition(this.mNextRead);
            int size = this.mParcel.readInt();
            this.mFieldId = this.mParcel.readInt();
            this.mNextRead += size;
        }
        if (this.mFieldId == fieldId) {
            return true;
        }
        return false;
    }

    public void setOutputField(int fieldId) {
        closeField();
        this.mCurrentField = fieldId;
        this.mPositionLookup.put(fieldId, this.mParcel.dataPosition());
        writeInt(0);
        writeInt(fieldId);
    }

    public void closeField() {
        int i = this.mCurrentField;
        if (i >= 0) {
            int currentFieldPosition = this.mPositionLookup.get(i);
            int position = this.mParcel.dataPosition();
            this.mParcel.setDataPosition(currentFieldPosition);
            this.mParcel.writeInt(position - currentFieldPosition);
            this.mParcel.setDataPosition(position);
        }
    }

    /* access modifiers changed from: protected */
    public VersionedParcel createSubParcel() {
        Parcel parcel = this.mParcel;
        int dataPosition = parcel.dataPosition();
        int i = this.mNextRead;
        if (i == this.mOffset) {
            i = this.mEnd;
        }
        return new VersionedParcelParcel(parcel, dataPosition, i, this.mPrefix + "  ", this.mReadCache, this.mWriteCache, this.mParcelizerCache);
    }

    public void writeByteArray(byte[] b) {
        if (b != null) {
            this.mParcel.writeInt(b.length);
            this.mParcel.writeByteArray(b);
            return;
        }
        this.mParcel.writeInt(-1);
    }

    public void writeByteArray(byte[] b, int offset, int len) {
        if (b != null) {
            this.mParcel.writeInt(b.length);
            this.mParcel.writeByteArray(b, offset, len);
            return;
        }
        this.mParcel.writeInt(-1);
    }

    public void writeInt(int val) {
        this.mParcel.writeInt(val);
    }

    public void writeLong(long val) {
        this.mParcel.writeLong(val);
    }

    public void writeFloat(float val) {
        this.mParcel.writeFloat(val);
    }

    public void writeDouble(double val) {
        this.mParcel.writeDouble(val);
    }

    public void writeString(String val) {
        this.mParcel.writeString(val);
    }

    public void writeStrongBinder(IBinder val) {
        this.mParcel.writeStrongBinder(val);
    }

    public void writeParcelable(Parcelable p) {
        this.mParcel.writeParcelable(p, 0);
    }

    public void writeBoolean(boolean val) {
        this.mParcel.writeInt(val);
    }

    public void writeStrongInterface(IInterface val) {
        this.mParcel.writeStrongInterface(val);
    }

    public void writeBundle(Bundle val) {
        this.mParcel.writeBundle(val);
    }

    /* access modifiers changed from: protected */
    public void writeCharSequence(CharSequence charSequence) {
        TextUtils.writeToParcel(charSequence, this.mParcel, 0);
    }

    /* access modifiers changed from: protected */
    public CharSequence readCharSequence() {
        return (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(this.mParcel);
    }

    public int readInt() {
        return this.mParcel.readInt();
    }

    public long readLong() {
        return this.mParcel.readLong();
    }

    public float readFloat() {
        return this.mParcel.readFloat();
    }

    public double readDouble() {
        return this.mParcel.readDouble();
    }

    public String readString() {
        return this.mParcel.readString();
    }

    public IBinder readStrongBinder() {
        return this.mParcel.readStrongBinder();
    }

    public byte[] readByteArray() {
        int len = this.mParcel.readInt();
        if (len < 0) {
            return null;
        }
        byte[] bytes = new byte[len];
        this.mParcel.readByteArray(bytes);
        return bytes;
    }

    public <T extends Parcelable> T readParcelable() {
        return this.mParcel.readParcelable(getClass().getClassLoader());
    }

    public Bundle readBundle() {
        return this.mParcel.readBundle(getClass().getClassLoader());
    }

    public boolean readBoolean() {
        return this.mParcel.readInt() != 0;
    }
}
