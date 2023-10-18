package kotlin.collections.builders;

import java.io.Externalizable;
import java.io.InvalidObjectException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0000\u0018\u0000 \u00112\u00020\u0001:\u0001\u0011B\u0007\b\u0016¢\u0006\u0002\u0010\u0002B\u0019\u0012\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\rH\u0002J\u0010\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u0012\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004X\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, mo113d2 = {"Lkotlin/collections/builders/SerializedCollection;", "Ljava/io/Externalizable;", "()V", "collection", "", "tag", "", "(Ljava/util/Collection;I)V", "readExternal", "", "input", "Ljava/io/ObjectInput;", "readResolve", "", "writeExternal", "output", "Ljava/io/ObjectOutput;", "Companion", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: ListBuilder.kt */
public final class SerializedCollection implements Externalizable {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private static final long serialVersionUID = 0;
    public static final int tagList = 0;
    public static final int tagSet = 1;
    private Collection<?> collection;
    private final int tag;

    public SerializedCollection(Collection<?> collection2, int tag2) {
        Intrinsics.checkNotNullParameter(collection2, "collection");
        this.collection = collection2;
        this.tag = tag2;
    }

    public SerializedCollection() {
        this(CollectionsKt.emptyList(), 0);
    }

    public void writeExternal(ObjectOutput output) {
        Intrinsics.checkNotNullParameter(output, "output");
        output.writeByte(this.tag);
        output.writeInt(this.collection.size());
        for (Object element : this.collection) {
            output.writeObject(element);
        }
    }

    public void readExternal(ObjectInput input) {
        Collection<?> collection2;
        Intrinsics.checkNotNullParameter(input, "input");
        int flags = input.readByte();
        int tag2 = flags & 1;
        if ((flags & -2) == 0) {
            int size = input.readInt();
            if (size >= 0) {
                int i = 0;
                switch (tag2) {
                    case 0:
                        List createListBuilder = CollectionsKt.createListBuilder(size);
                        List $this$readExternal_u24lambda_u2d1 = createListBuilder;
                        while (i < size) {
                            int i2 = i;
                            $this$readExternal_u24lambda_u2d1.add(input.readObject());
                            i++;
                        }
                        collection2 = CollectionsKt.build(createListBuilder);
                        break;
                    case 1:
                        Set createSetBuilder = SetsKt.createSetBuilder(size);
                        Set $this$readExternal_u24lambda_u2d3 = createSetBuilder;
                        while (i < size) {
                            int i3 = i;
                            $this$readExternal_u24lambda_u2d3.add(input.readObject());
                            i++;
                        }
                        collection2 = SetsKt.build(createSetBuilder);
                        break;
                    default:
                        throw new InvalidObjectException("Unsupported collection type tag: " + tag2 + '.');
                }
                this.collection = collection2;
                return;
            }
            throw new InvalidObjectException("Illegal size value: " + size + '.');
        }
        throw new InvalidObjectException("Unsupported flags value: " + flags + '.');
    }

    private final Object readResolve() {
        return this.collection;
    }

    @Metadata(mo112d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006XT¢\u0006\u0002\n\u0000¨\u0006\b"}, mo113d2 = {"Lkotlin/collections/builders/SerializedCollection$Companion;", "", "()V", "serialVersionUID", "", "tagList", "", "tagSet", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
    /* compiled from: ListBuilder.kt */
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
