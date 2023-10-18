package kotlin.collections;

import java.util.RandomAccess;
import kotlin.Metadata;

@Metadata(mo112d1 = {"\u0000\u001f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000b*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\u00060\u0003j\u0002`\u0004J\u0011\u0010\t\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u0002H\u0002J\u0016\u0010\u000b\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\u0006H\u0002¢\u0006\u0002\u0010\rJ\u0010\u0010\u000e\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0002H\u0016J\b\u0010\u000f\u001a\u00020\u0002H\u0016J\u0010\u0010\u0010\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u0002H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\b¨\u0006\u0011"}, mo113d2 = {"kotlin/collections/ArraysKt___ArraysJvmKt$asList$7", "Lkotlin/collections/AbstractList;", "", "Ljava/util/RandomAccess;", "Lkotlin/collections/RandomAccess;", "size", "", "getSize", "()I", "contains", "element", "get", "index", "(I)Ljava/lang/Boolean;", "indexOf", "isEmpty", "lastIndexOf", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: _ArraysJvm.kt */
public final class ArraysKt___ArraysJvmKt$asList$7 extends AbstractList<Boolean> implements RandomAccess {
    final /* synthetic */ boolean[] $this_asList;

    ArraysKt___ArraysJvmKt$asList$7(boolean[] $receiver) {
        this.$this_asList = $receiver;
    }

    public final /* bridge */ boolean contains(Object element) {
        if (!(element instanceof Boolean)) {
            return false;
        }
        return contains(((Boolean) element).booleanValue());
    }

    public final /* bridge */ int indexOf(Object element) {
        if (!(element instanceof Boolean)) {
            return -1;
        }
        return indexOf(((Boolean) element).booleanValue());
    }

    public final /* bridge */ int lastIndexOf(Object element) {
        if (!(element instanceof Boolean)) {
            return -1;
        }
        return lastIndexOf(((Boolean) element).booleanValue());
    }

    public int getSize() {
        return this.$this_asList.length;
    }

    public boolean isEmpty() {
        return this.$this_asList.length == 0;
    }

    public boolean contains(boolean element) {
        return ArraysKt.contains(this.$this_asList, element);
    }

    public Boolean get(int index) {
        return Boolean.valueOf(this.$this_asList[index]);
    }

    public int indexOf(boolean element) {
        return ArraysKt.indexOf(this.$this_asList, element);
    }

    public int lastIndexOf(boolean element) {
        return ArraysKt.lastIndexOf(this.$this_asList, element);
    }
}