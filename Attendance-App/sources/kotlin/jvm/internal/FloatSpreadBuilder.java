package kotlin.jvm.internal;

import kotlin.Metadata;

@Metadata(mo112d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0014\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\u0002J\f\u0010\f\u001a\u00020\u0004*\u00020\u0002H\u0014R\u000e\u0010\u0006\u001a\u00020\u0002X\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, mo113d2 = {"Lkotlin/jvm/internal/FloatSpreadBuilder;", "Lkotlin/jvm/internal/PrimitiveSpreadBuilder;", "", "size", "", "(I)V", "values", "add", "", "value", "", "toArray", "getSize", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: PrimitiveSpreadBuilders.kt */
public final class FloatSpreadBuilder extends PrimitiveSpreadBuilder<float[]> {
    private final float[] values;

    public FloatSpreadBuilder(int size) {
        super(size);
        this.values = new float[size];
    }

    /* access modifiers changed from: protected */
    public int getSize(float[] $this$getSize) {
        Intrinsics.checkNotNullParameter($this$getSize, "<this>");
        return $this$getSize.length;
    }

    public final void add(float value) {
        float[] fArr = this.values;
        int position = getPosition();
        setPosition(position + 1);
        fArr[position] = value;
    }

    public final float[] toArray() {
        return (float[]) toArray(this.values, new float[size()]);
    }
}
