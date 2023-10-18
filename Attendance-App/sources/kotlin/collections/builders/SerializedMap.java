package kotlin.collections.builders;

import java.io.Externalizable;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0007\b\u0016¢\u0006\u0002\u0010\u0002B\u0015\u0012\u000e\u0010\u0003\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0002J\u0010\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0016\u0010\u0003\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u0004X\u000e¢\u0006\u0002\n\u0000¨\u0006\u0010"}, mo113d2 = {"Lkotlin/collections/builders/SerializedMap;", "Ljava/io/Externalizable;", "()V", "map", "", "(Ljava/util/Map;)V", "readExternal", "", "input", "Ljava/io/ObjectInput;", "readResolve", "", "writeExternal", "output", "Ljava/io/ObjectOutput;", "Companion", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: MapBuilder.kt */
final class SerializedMap implements Externalizable {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private static final long serialVersionUID = 0;
    private Map<?, ?> map;

    public SerializedMap(Map<?, ?> map2) {
        Intrinsics.checkNotNullParameter(map2, "map");
        this.map = map2;
    }

    public SerializedMap() {
        this(MapsKt.emptyMap());
    }

    public void writeExternal(ObjectOutput output) {
        Intrinsics.checkNotNullParameter(output, "output");
        output.writeByte(0);
        output.writeInt(this.map.size());
        for (Map.Entry entry : this.map.entrySet()) {
            output.writeObject(entry.getKey());
            output.writeObject(entry.getValue());
        }
    }

    public void readExternal(ObjectInput input) {
        Intrinsics.checkNotNullParameter(input, "input");
        int flags = input.readByte();
        if (flags == 0) {
            int size = input.readInt();
            if (size >= 0) {
                Map createMapBuilder = MapsKt.createMapBuilder(size);
                Map $this$readExternal_u24lambda_u2d1 = createMapBuilder;
                for (int i = 0; i < size; i++) {
                    int i2 = i;
                    $this$readExternal_u24lambda_u2d1.put(input.readObject(), input.readObject());
                }
                this.map = MapsKt.build(createMapBuilder);
                return;
            }
            throw new InvalidObjectException("Illegal size value: " + size + '.');
        }
        throw new InvalidObjectException("Unsupported flags value: " + flags);
    }

    private final Object readResolve() {
        return this.map;
    }

    @Metadata(mo112d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000¨\u0006\u0005"}, mo113d2 = {"Lkotlin/collections/builders/SerializedMap$Companion;", "", "()V", "serialVersionUID", "", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
    /* compiled from: MapBuilder.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
