package androidx.versionedparcelable;

import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.NetworkOnMainThreadException;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseBooleanArray;
import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class VersionedParcel {
    private static final int EX_BAD_PARCELABLE = -2;
    private static final int EX_ILLEGAL_ARGUMENT = -3;
    private static final int EX_ILLEGAL_STATE = -5;
    private static final int EX_NETWORK_MAIN_THREAD = -6;
    private static final int EX_NULL_POINTER = -4;
    private static final int EX_PARCELABLE = -9;
    private static final int EX_SECURITY = -1;
    private static final int EX_UNSUPPORTED_OPERATION = -7;
    private static final String TAG = "VersionedParcel";
    private static final int TYPE_BINDER = 5;
    private static final int TYPE_FLOAT = 8;
    private static final int TYPE_INTEGER = 7;
    private static final int TYPE_PARCELABLE = 2;
    private static final int TYPE_SERIALIZABLE = 3;
    private static final int TYPE_STRING = 4;
    private static final int TYPE_VERSIONED_PARCELABLE = 1;
    protected final ArrayMap<String, Class> mParcelizerCache;
    protected final ArrayMap<String, Method> mReadCache;
    protected final ArrayMap<String, Method> mWriteCache;

    /* access modifiers changed from: protected */
    public abstract void closeField();

    /* access modifiers changed from: protected */
    public abstract VersionedParcel createSubParcel();

    /* access modifiers changed from: protected */
    public abstract boolean readBoolean();

    /* access modifiers changed from: protected */
    public abstract Bundle readBundle();

    /* access modifiers changed from: protected */
    public abstract byte[] readByteArray();

    /* access modifiers changed from: protected */
    public abstract CharSequence readCharSequence();

    /* access modifiers changed from: protected */
    public abstract double readDouble();

    /* access modifiers changed from: protected */
    public abstract boolean readField(int i);

    /* access modifiers changed from: protected */
    public abstract float readFloat();

    /* access modifiers changed from: protected */
    public abstract int readInt();

    /* access modifiers changed from: protected */
    public abstract long readLong();

    /* access modifiers changed from: protected */
    public abstract <T extends Parcelable> T readParcelable();

    /* access modifiers changed from: protected */
    public abstract String readString();

    /* access modifiers changed from: protected */
    public abstract IBinder readStrongBinder();

    /* access modifiers changed from: protected */
    public abstract void setOutputField(int i);

    /* access modifiers changed from: protected */
    public abstract void writeBoolean(boolean z);

    /* access modifiers changed from: protected */
    public abstract void writeBundle(Bundle bundle);

    /* access modifiers changed from: protected */
    public abstract void writeByteArray(byte[] bArr);

    /* access modifiers changed from: protected */
    public abstract void writeByteArray(byte[] bArr, int i, int i2);

    /* access modifiers changed from: protected */
    public abstract void writeCharSequence(CharSequence charSequence);

    /* access modifiers changed from: protected */
    public abstract void writeDouble(double d);

    /* access modifiers changed from: protected */
    public abstract void writeFloat(float f);

    /* access modifiers changed from: protected */
    public abstract void writeInt(int i);

    /* access modifiers changed from: protected */
    public abstract void writeLong(long j);

    /* access modifiers changed from: protected */
    public abstract void writeParcelable(Parcelable parcelable);

    /* access modifiers changed from: protected */
    public abstract void writeString(String str);

    /* access modifiers changed from: protected */
    public abstract void writeStrongBinder(IBinder iBinder);

    /* access modifiers changed from: protected */
    public abstract void writeStrongInterface(IInterface iInterface);

    public VersionedParcel(ArrayMap<String, Method> readCache, ArrayMap<String, Method> writeCache, ArrayMap<String, Class> parcelizerCache) {
        this.mReadCache = readCache;
        this.mWriteCache = writeCache;
        this.mParcelizerCache = parcelizerCache;
    }

    public boolean isStream() {
        return false;
    }

    public void setSerializationFlags(boolean allowSerialization, boolean ignoreParcelables) {
    }

    public void writeStrongInterface(IInterface val, int fieldId) {
        setOutputField(fieldId);
        writeStrongInterface(val);
    }

    public void writeBundle(Bundle val, int fieldId) {
        setOutputField(fieldId);
        writeBundle(val);
    }

    public void writeBoolean(boolean val, int fieldId) {
        setOutputField(fieldId);
        writeBoolean(val);
    }

    public void writeByteArray(byte[] b, int fieldId) {
        setOutputField(fieldId);
        writeByteArray(b);
    }

    public void writeByteArray(byte[] b, int offset, int len, int fieldId) {
        setOutputField(fieldId);
        writeByteArray(b, offset, len);
    }

    public void writeCharSequence(CharSequence val, int fieldId) {
        setOutputField(fieldId);
        writeCharSequence(val);
    }

    public void writeInt(int val, int fieldId) {
        setOutputField(fieldId);
        writeInt(val);
    }

    public void writeLong(long val, int fieldId) {
        setOutputField(fieldId);
        writeLong(val);
    }

    public void writeFloat(float val, int fieldId) {
        setOutputField(fieldId);
        writeFloat(val);
    }

    public void writeDouble(double val, int fieldId) {
        setOutputField(fieldId);
        writeDouble(val);
    }

    public void writeString(String val, int fieldId) {
        setOutputField(fieldId);
        writeString(val);
    }

    public void writeStrongBinder(IBinder val, int fieldId) {
        setOutputField(fieldId);
        writeStrongBinder(val);
    }

    public void writeParcelable(Parcelable p, int fieldId) {
        setOutputField(fieldId);
        writeParcelable(p);
    }

    public boolean readBoolean(boolean def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readBoolean();
    }

    public int readInt(int def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readInt();
    }

    public long readLong(long def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readLong();
    }

    public float readFloat(float def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readFloat();
    }

    public double readDouble(double def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readDouble();
    }

    public String readString(String def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readString();
    }

    public IBinder readStrongBinder(IBinder def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readStrongBinder();
    }

    public byte[] readByteArray(byte[] def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readByteArray();
    }

    public <T extends Parcelable> T readParcelable(T def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readParcelable();
    }

    public Bundle readBundle(Bundle def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readBundle();
    }

    public void writeByte(byte val, int fieldId) {
        setOutputField(fieldId);
        writeInt(val);
    }

    public void writeSize(Size val, int fieldId) {
        setOutputField(fieldId);
        writeBoolean(val != null);
        if (val != null) {
            writeInt(val.getWidth());
            writeInt(val.getHeight());
        }
    }

    public void writeSizeF(SizeF val, int fieldId) {
        setOutputField(fieldId);
        writeBoolean(val != null);
        if (val != null) {
            writeFloat(val.getWidth());
            writeFloat(val.getHeight());
        }
    }

    public void writeSparseBooleanArray(SparseBooleanArray val, int fieldId) {
        setOutputField(fieldId);
        if (val == null) {
            writeInt(-1);
            return;
        }
        int n = val.size();
        writeInt(n);
        for (int i = 0; i < n; i++) {
            writeInt(val.keyAt(i));
            writeBoolean(val.valueAt(i));
        }
    }

    public void writeBooleanArray(boolean[] val, int fieldId) {
        setOutputField(fieldId);
        writeBooleanArray(val);
    }

    /* access modifiers changed from: protected */
    public void writeBooleanArray(boolean[] val) {
        if (val != null) {
            writeInt(n);
            for (boolean z : val) {
                writeInt(z ? 1 : 0);
            }
            return;
        }
        writeInt(-1);
    }

    public boolean[] readBooleanArray(boolean[] def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readBooleanArray();
    }

    /* access modifiers changed from: protected */
    public boolean[] readBooleanArray() {
        int n = readInt();
        if (n < 0) {
            return null;
        }
        boolean[] val = new boolean[n];
        for (int i = 0; i < n; i++) {
            val[i] = readInt() != 0;
        }
        return val;
    }

    public void writeCharArray(char[] val, int fieldId) {
        setOutputField(fieldId);
        if (val != null) {
            writeInt(n);
            for (char writeInt : val) {
                writeInt(writeInt);
            }
            return;
        }
        writeInt(-1);
    }

    public CharSequence readCharSequence(CharSequence def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readCharSequence();
    }

    public char[] readCharArray(char[] def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        int n = readInt();
        if (n < 0) {
            return null;
        }
        char[] val = new char[n];
        for (int i = 0; i < n; i++) {
            val[i] = (char) readInt();
        }
        return val;
    }

    public void writeIntArray(int[] val, int fieldId) {
        setOutputField(fieldId);
        writeIntArray(val);
    }

    /* access modifiers changed from: protected */
    public void writeIntArray(int[] val) {
        if (val != null) {
            writeInt(n);
            for (int writeInt : val) {
                writeInt(writeInt);
            }
            return;
        }
        writeInt(-1);
    }

    public int[] readIntArray(int[] def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readIntArray();
    }

    /* access modifiers changed from: protected */
    public int[] readIntArray() {
        int n = readInt();
        if (n < 0) {
            return null;
        }
        int[] val = new int[n];
        for (int i = 0; i < n; i++) {
            val[i] = readInt();
        }
        return val;
    }

    public void writeLongArray(long[] val, int fieldId) {
        setOutputField(fieldId);
        writeLongArray(val);
    }

    /* access modifiers changed from: protected */
    public void writeLongArray(long[] val) {
        if (val != null) {
            writeInt(n);
            for (long writeLong : val) {
                writeLong(writeLong);
            }
            return;
        }
        writeInt(-1);
    }

    public long[] readLongArray(long[] def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readLongArray();
    }

    /* access modifiers changed from: protected */
    public long[] readLongArray() {
        int n = readInt();
        if (n < 0) {
            return null;
        }
        long[] val = new long[n];
        for (int i = 0; i < n; i++) {
            val[i] = readLong();
        }
        return val;
    }

    public void writeFloatArray(float[] val, int fieldId) {
        setOutputField(fieldId);
        writeFloatArray(val);
    }

    /* access modifiers changed from: protected */
    public void writeFloatArray(float[] val) {
        if (val != null) {
            writeInt(n);
            for (float writeFloat : val) {
                writeFloat(writeFloat);
            }
            return;
        }
        writeInt(-1);
    }

    public float[] readFloatArray(float[] def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readFloatArray();
    }

    /* access modifiers changed from: protected */
    public float[] readFloatArray() {
        int n = readInt();
        if (n < 0) {
            return null;
        }
        float[] val = new float[n];
        for (int i = 0; i < n; i++) {
            val[i] = readFloat();
        }
        return val;
    }

    public void writeDoubleArray(double[] val, int fieldId) {
        setOutputField(fieldId);
        writeDoubleArray(val);
    }

    /* access modifiers changed from: protected */
    public void writeDoubleArray(double[] val) {
        if (val != null) {
            writeInt(n);
            for (double writeDouble : val) {
                writeDouble(writeDouble);
            }
            return;
        }
        writeInt(-1);
    }

    public double[] readDoubleArray(double[] def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readDoubleArray();
    }

    /* access modifiers changed from: protected */
    public double[] readDoubleArray() {
        int n = readInt();
        if (n < 0) {
            return null;
        }
        double[] val = new double[n];
        for (int i = 0; i < n; i++) {
            val[i] = readDouble();
        }
        return val;
    }

    public <T> void writeSet(Set<T> val, int fieldId) {
        writeCollection(val, fieldId);
    }

    public <T> void writeList(List<T> val, int fieldId) {
        writeCollection(val, fieldId);
    }

    public <K, V> void writeMap(Map<K, V> val, int fieldId) {
        setOutputField(fieldId);
        if (val == null) {
            writeInt(-1);
            return;
        }
        int size = val.size();
        writeInt(size);
        if (size != 0) {
            List<K> keySet = new ArrayList<>();
            List<V> valueSet = new ArrayList<>();
            for (Map.Entry<K, V> entry : val.entrySet()) {
                keySet.add(entry.getKey());
                valueSet.add(entry.getValue());
            }
            writeCollection(keySet);
            writeCollection(valueSet);
        }
    }

    private <T> void writeCollection(Collection<T> val, int fieldId) {
        setOutputField(fieldId);
        writeCollection(val);
    }

    private <T> void writeCollection(Collection<T> val) {
        if (val == null) {
            writeInt(-1);
            return;
        }
        int n = val.size();
        writeInt(n);
        if (n > 0) {
            int type = getType(val.iterator().next());
            writeInt(type);
            switch (type) {
                case 1:
                    for (T writeVersionedParcelable : val) {
                        writeVersionedParcelable(writeVersionedParcelable);
                    }
                    return;
                case 2:
                    for (T writeParcelable : val) {
                        writeParcelable(writeParcelable);
                    }
                    return;
                case 3:
                    for (T writeSerializable : val) {
                        writeSerializable(writeSerializable);
                    }
                    return;
                case 4:
                    for (T writeString : val) {
                        writeString(writeString);
                    }
                    return;
                case 5:
                    for (T writeStrongBinder : val) {
                        writeStrongBinder(writeStrongBinder);
                    }
                    return;
                case 7:
                    for (T intValue : val) {
                        writeInt(intValue.intValue());
                    }
                    return;
                case 8:
                    for (T floatValue : val) {
                        writeFloat(floatValue.floatValue());
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public <T> void writeArray(T[] val, int fieldId) {
        setOutputField(fieldId);
        writeArray(val);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:10:0x002a, code lost:
        writeString((java.lang.String) r5[r1]);
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0034, code lost:
        if (r1 >= r0) goto L_0x0058;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0036, code lost:
        writeSerializable((java.io.Serializable) r5[r1]);
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0040, code lost:
        if (r1 >= r0) goto L_0x0058;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x0042, code lost:
        writeParcelable((android.os.Parcelable) r5[r1]);
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x004c, code lost:
        if (r1 >= r0) goto L_0x0058;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004e, code lost:
        writeVersionedParcelable((androidx.versionedparcelable.VersionedParcelable) r5[r1]);
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:7:0x001c, code lost:
        if (r1 >= r0) goto L_0x0058;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:8:0x001e, code lost:
        writeStrongBinder((android.os.IBinder) r5[r1]);
        r1 = r1 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0028, code lost:
        if (r1 >= r0) goto L_0x0058;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> void writeArray(T[] r5) {
        /*
            r4 = this;
            if (r5 != 0) goto L_0x0007
            r0 = -1
            r4.writeInt(r0)
            return
        L_0x0007:
            int r0 = r5.length
            r1 = 0
            r4.writeInt(r0)
            if (r0 <= 0) goto L_0x0058
            r2 = 0
            r2 = r5[r2]
            int r2 = r4.getType(r2)
            r4.writeInt(r2)
            switch(r2) {
                case 1: goto L_0x004c;
                case 2: goto L_0x0040;
                case 3: goto L_0x0034;
                case 4: goto L_0x0028;
                case 5: goto L_0x001c;
                default: goto L_0x001b;
            }
        L_0x001b:
            goto L_0x0058
        L_0x001c:
            if (r1 >= r0) goto L_0x0058
            r3 = r5[r1]
            android.os.IBinder r3 = (android.os.IBinder) r3
            r4.writeStrongBinder(r3)
            int r1 = r1 + 1
            goto L_0x001c
        L_0x0028:
            if (r1 >= r0) goto L_0x0058
            r3 = r5[r1]
            java.lang.String r3 = (java.lang.String) r3
            r4.writeString(r3)
            int r1 = r1 + 1
            goto L_0x0028
        L_0x0034:
            if (r1 >= r0) goto L_0x0058
            r3 = r5[r1]
            java.io.Serializable r3 = (java.io.Serializable) r3
            r4.writeSerializable(r3)
            int r1 = r1 + 1
            goto L_0x0034
        L_0x0040:
            if (r1 >= r0) goto L_0x0058
            r3 = r5[r1]
            android.os.Parcelable r3 = (android.os.Parcelable) r3
            r4.writeParcelable(r3)
            int r1 = r1 + 1
            goto L_0x0040
        L_0x004c:
            if (r1 >= r0) goto L_0x0058
            r3 = r5[r1]
            androidx.versionedparcelable.VersionedParcelable r3 = (androidx.versionedparcelable.VersionedParcelable) r3
            r4.writeVersionedParcelable(r3)
            int r1 = r1 + 1
            goto L_0x004c
        L_0x0058:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.versionedparcelable.VersionedParcel.writeArray(java.lang.Object[]):void");
    }

    private <T> int getType(T t) {
        if (t instanceof String) {
            return 4;
        }
        if (t instanceof Parcelable) {
            return 2;
        }
        if (t instanceof VersionedParcelable) {
            return 1;
        }
        if (t instanceof Serializable) {
            return 3;
        }
        if (t instanceof IBinder) {
            return 5;
        }
        if (t instanceof Integer) {
            return 7;
        }
        if (t instanceof Float) {
            return 8;
        }
        throw new IllegalArgumentException(t.getClass().getName() + " cannot be VersionedParcelled");
    }

    public void writeVersionedParcelable(VersionedParcelable p, int fieldId) {
        setOutputField(fieldId);
        writeVersionedParcelable(p);
    }

    /* access modifiers changed from: protected */
    public void writeVersionedParcelable(VersionedParcelable p) {
        if (p == null) {
            writeString((String) null);
            return;
        }
        writeVersionedParcelableCreator(p);
        VersionedParcel subParcel = createSubParcel();
        writeToParcel(p, subParcel);
        subParcel.closeField();
    }

    private void writeVersionedParcelableCreator(VersionedParcelable p) {
        try {
            writeString(findParcelClass(p.getClass()).getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(p.getClass().getSimpleName() + " does not have a Parcelizer", e);
        }
    }

    public void writeSerializable(Serializable s, int fieldId) {
        setOutputField(fieldId);
        writeSerializable(s);
    }

    private void writeSerializable(Serializable s) {
        if (s == null) {
            writeString((String) null);
            return;
        }
        String name = s.getClass().getName();
        writeString(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(s);
            oos.close();
            writeByteArray(baos.toByteArray());
        } catch (IOException ioe) {
            throw new RuntimeException("VersionedParcelable encountered IOException writing serializable object (name = " + name + ")", ioe);
        }
    }

    public void writeException(Exception e, int fieldId) {
        setOutputField(fieldId);
        if (e == null) {
            writeNoException();
            return;
        }
        int code = 0;
        if ((e instanceof Parcelable) && e.getClass().getClassLoader() == Parcelable.class.getClassLoader()) {
            code = EX_PARCELABLE;
        } else if (e instanceof SecurityException) {
            code = -1;
        } else if (e instanceof BadParcelableException) {
            code = -2;
        } else if (e instanceof IllegalArgumentException) {
            code = -3;
        } else if (e instanceof NullPointerException) {
            code = -4;
        } else if (e instanceof IllegalStateException) {
            code = EX_ILLEGAL_STATE;
        } else if (e instanceof NetworkOnMainThreadException) {
            code = EX_NETWORK_MAIN_THREAD;
        } else if (e instanceof UnsupportedOperationException) {
            code = EX_UNSUPPORTED_OPERATION;
        }
        writeInt(code);
        if (code != 0) {
            writeString(e.getMessage());
            switch (code) {
                case EX_PARCELABLE /*-9*/:
                    writeParcelable((Parcelable) e);
                    return;
                default:
                    return;
            }
        } else if (e instanceof RuntimeException) {
            throw ((RuntimeException) e);
        } else {
            throw new RuntimeException(e);
        }
    }

    /* access modifiers changed from: protected */
    public void writeNoException() {
        writeInt(0);
    }

    public Exception readException(Exception def, int fieldId) {
        int code;
        if (readField(fieldId) && (code = readExceptionCode()) != 0) {
            return readException(code, readString());
        }
        return def;
    }

    private int readExceptionCode() {
        return readInt();
    }

    private Exception readException(int code, String msg) {
        return createException(code, msg);
    }

    protected static Throwable getRootCause(Throwable t) {
        while (t.getCause() != null) {
            t = t.getCause();
        }
        return t;
    }

    private Exception createException(int code, String msg) {
        switch (code) {
            case EX_PARCELABLE /*-9*/:
                return (Exception) readParcelable();
            case EX_UNSUPPORTED_OPERATION /*-7*/:
                return new UnsupportedOperationException(msg);
            case EX_NETWORK_MAIN_THREAD /*-6*/:
                return new NetworkOnMainThreadException();
            case EX_ILLEGAL_STATE /*-5*/:
                return new IllegalStateException(msg);
            case -4:
                return new NullPointerException(msg);
            case -3:
                return new IllegalArgumentException(msg);
            case -2:
                return new BadParcelableException(msg);
            case -1:
                return new SecurityException(msg);
            default:
                return new RuntimeException("Unknown exception code: " + code + " msg " + msg);
        }
    }

    public byte readByte(byte def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return (byte) (readInt() & 255);
    }

    public Size readSize(Size def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        if (readBoolean()) {
            return new Size(readInt(), readInt());
        }
        return null;
    }

    public SizeF readSizeF(SizeF def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        if (readBoolean()) {
            return new SizeF(readFloat(), readFloat());
        }
        return null;
    }

    public SparseBooleanArray readSparseBooleanArray(SparseBooleanArray def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        int n = readInt();
        if (n < 0) {
            return null;
        }
        SparseBooleanArray sa = new SparseBooleanArray(n);
        for (int i = 0; i < n; i++) {
            sa.put(readInt(), readBoolean());
        }
        return sa;
    }

    public <T> Set<T> readSet(Set<T> def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return (Set) readCollection(new ArraySet());
    }

    public <T> List<T> readList(List<T> def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return (List) readCollection(new ArrayList());
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private <T, S extends java.util.Collection<T>> S readCollection(S r4) {
        /*
            r3 = this;
            int r0 = r3.readInt()
            r1 = 0
            if (r0 >= 0) goto L_0x0008
            return r1
        L_0x0008:
            if (r0 == 0) goto L_0x0051
            int r2 = r3.readInt()
            if (r0 >= 0) goto L_0x0011
            return r1
        L_0x0011:
            switch(r2) {
                case 1: goto L_0x0045;
                case 2: goto L_0x0039;
                case 3: goto L_0x002d;
                case 4: goto L_0x0021;
                case 5: goto L_0x0015;
                default: goto L_0x0014;
            }
        L_0x0014:
            goto L_0x0051
        L_0x0015:
            if (r0 <= 0) goto L_0x0051
            android.os.IBinder r1 = r3.readStrongBinder()
            r4.add(r1)
            int r0 = r0 + -1
            goto L_0x0015
        L_0x0021:
            if (r0 <= 0) goto L_0x0051
            java.lang.String r1 = r3.readString()
            r4.add(r1)
            int r0 = r0 + -1
            goto L_0x0021
        L_0x002d:
            if (r0 <= 0) goto L_0x0051
            java.io.Serializable r1 = r3.readSerializable()
            r4.add(r1)
            int r0 = r0 + -1
            goto L_0x002d
        L_0x0039:
            if (r0 <= 0) goto L_0x0051
            android.os.Parcelable r1 = r3.readParcelable()
            r4.add(r1)
            int r0 = r0 + -1
            goto L_0x0039
        L_0x0045:
            if (r0 <= 0) goto L_0x0051
            androidx.versionedparcelable.VersionedParcelable r1 = r3.readVersionedParcelable()
            r4.add(r1)
            int r0 = r0 + -1
            goto L_0x0045
        L_0x0051:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.versionedparcelable.VersionedParcel.readCollection(java.util.Collection):java.util.Collection");
    }

    public <K, V> Map<K, V> readMap(Map<K, V> def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        int size = readInt();
        if (size < 0) {
            return null;
        }
        Map<K, V> map = new ArrayMap<>();
        if (size == 0) {
            return map;
        }
        List<K> keyList = new ArrayList<>();
        List<V> valueList = new ArrayList<>();
        readCollection(keyList);
        readCollection(valueList);
        for (int i = 0; i < size; i++) {
            map.put(keyList.get(i), valueList.get(i));
        }
        return map;
    }

    public <T> T[] readArray(T[] def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readArray(def);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> T[] readArray(T[] r5) {
        /*
            r4 = this;
            int r0 = r4.readInt()
            r1 = 0
            if (r0 >= 0) goto L_0x0008
            return r1
        L_0x0008:
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>(r0)
            if (r0 == 0) goto L_0x0056
            int r3 = r4.readInt()
            if (r0 >= 0) goto L_0x0016
            return r1
        L_0x0016:
            switch(r3) {
                case 1: goto L_0x004a;
                case 2: goto L_0x003e;
                case 3: goto L_0x0032;
                case 4: goto L_0x0026;
                case 5: goto L_0x001a;
                default: goto L_0x0019;
            }
        L_0x0019:
            goto L_0x0056
        L_0x001a:
            if (r0 <= 0) goto L_0x0056
            android.os.IBinder r1 = r4.readStrongBinder()
            r2.add(r1)
            int r0 = r0 + -1
            goto L_0x001a
        L_0x0026:
            if (r0 <= 0) goto L_0x0056
            java.lang.String r1 = r4.readString()
            r2.add(r1)
            int r0 = r0 + -1
            goto L_0x0026
        L_0x0032:
            if (r0 <= 0) goto L_0x0056
            java.io.Serializable r1 = r4.readSerializable()
            r2.add(r1)
            int r0 = r0 + -1
            goto L_0x0032
        L_0x003e:
            if (r0 <= 0) goto L_0x0056
            android.os.Parcelable r1 = r4.readParcelable()
            r2.add(r1)
            int r0 = r0 + -1
            goto L_0x003e
        L_0x004a:
            if (r0 <= 0) goto L_0x0056
            androidx.versionedparcelable.VersionedParcelable r1 = r4.readVersionedParcelable()
            r2.add(r1)
            int r0 = r0 + -1
            goto L_0x004a
        L_0x0056:
            java.lang.Object[] r1 = r2.toArray(r5)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.versionedparcelable.VersionedParcel.readArray(java.lang.Object[]):java.lang.Object[]");
    }

    public <T extends VersionedParcelable> T readVersionedParcelable(T def, int fieldId) {
        if (!readField(fieldId)) {
            return def;
        }
        return readVersionedParcelable();
    }

    /* access modifiers changed from: protected */
    public <T extends VersionedParcelable> T readVersionedParcelable() {
        String name = readString();
        if (name == null) {
            return null;
        }
        return readFromParcel(name, createSubParcel());
    }

    /* access modifiers changed from: protected */
    public Serializable readSerializable() {
        String name = readString();
        if (name == null) {
            return null;
        }
        try {
            return (Serializable) new ObjectInputStream(new ByteArrayInputStream(readByteArray())) {
                /* access modifiers changed from: protected */
                public Class<?> resolveClass(ObjectStreamClass osClass) throws IOException, ClassNotFoundException {
                    Class<?> c = Class.forName(osClass.getName(), false, getClass().getClassLoader());
                    if (c != null) {
                        return c;
                    }
                    return super.resolveClass(osClass);
                }
            }.readObject();
        } catch (IOException ioe) {
            throw new RuntimeException("VersionedParcelable encountered IOException reading a Serializable object (name = " + name + ")", ioe);
        } catch (ClassNotFoundException cnfe) {
            throw new RuntimeException("VersionedParcelable encountered ClassNotFoundException reading a Serializable object (name = " + name + ")", cnfe);
        }
    }

    /* access modifiers changed from: protected */
    public <T extends VersionedParcelable> T readFromParcel(String parcelCls, VersionedParcel versionedParcel) {
        try {
            return (VersionedParcelable) getReadMethod(parcelCls).invoke((Object) null, new Object[]{versionedParcel});
        } catch (IllegalAccessException e) {
            throw new RuntimeException("VersionedParcel encountered IllegalAccessException", e);
        } catch (InvocationTargetException e2) {
            if (e2.getCause() instanceof RuntimeException) {
                throw ((RuntimeException) e2.getCause());
            }
            throw new RuntimeException("VersionedParcel encountered InvocationTargetException", e2);
        } catch (NoSuchMethodException e3) {
            throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", e3);
        } catch (ClassNotFoundException e4) {
            throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", e4);
        }
    }

    /* access modifiers changed from: protected */
    public <T extends VersionedParcelable> void writeToParcel(T val, VersionedParcel versionedParcel) {
        try {
            getWriteMethod(val.getClass()).invoke((Object) null, new Object[]{val, versionedParcel});
        } catch (IllegalAccessException e) {
            throw new RuntimeException("VersionedParcel encountered IllegalAccessException", e);
        } catch (InvocationTargetException e2) {
            if (e2.getCause() instanceof RuntimeException) {
                throw ((RuntimeException) e2.getCause());
            }
            throw new RuntimeException("VersionedParcel encountered InvocationTargetException", e2);
        } catch (NoSuchMethodException e3) {
            throw new RuntimeException("VersionedParcel encountered NoSuchMethodException", e3);
        } catch (ClassNotFoundException e4) {
            throw new RuntimeException("VersionedParcel encountered ClassNotFoundException", e4);
        }
    }

    private Method getReadMethod(String parcelCls) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
        Method m = this.mReadCache.get(parcelCls);
        if (m != null) {
            return m;
        }
        long currentTimeMillis = System.currentTimeMillis();
        Class<VersionedParcel> cls = VersionedParcel.class;
        Method m2 = Class.forName(parcelCls, true, cls.getClassLoader()).getDeclaredMethod("read", new Class[]{cls});
        this.mReadCache.put(parcelCls, m2);
        return m2;
    }

    private Method getWriteMethod(Class baseCls) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException {
        Method m = this.mWriteCache.get(baseCls.getName());
        if (m != null) {
            return m;
        }
        Class cls = findParcelClass(baseCls);
        long currentTimeMillis = System.currentTimeMillis();
        Method m2 = cls.getDeclaredMethod("write", new Class[]{baseCls, VersionedParcel.class});
        this.mWriteCache.put(baseCls.getName(), m2);
        return m2;
    }

    private Class findParcelClass(Class<? extends VersionedParcelable> cls) throws ClassNotFoundException {
        Class ret = this.mParcelizerCache.get(cls.getName());
        if (ret != null) {
            return ret;
        }
        Class ret2 = Class.forName(String.format("%s.%sParcelizer", new Object[]{cls.getPackage().getName(), cls.getSimpleName()}), false, cls.getClassLoader());
        this.mParcelizerCache.put(cls.getName(), ret2);
        return ret2;
    }

    public static class ParcelException extends RuntimeException {
        public ParcelException(Throwable source) {
            super(source);
        }
    }
}
