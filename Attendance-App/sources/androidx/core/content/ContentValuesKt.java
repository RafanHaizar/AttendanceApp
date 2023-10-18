package androidx.core.content;

import android.content.ContentValues;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Typography;

@Metadata(mo112d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\u0000\n\u0002\b\u0002\u001a;\u0010\u0000\u001a\u00020\u00012.\u0010\u0002\u001a\u0018\u0012\u0014\b\u0001\u0012\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u00040\u0003\"\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0004¢\u0006\u0002\u0010\u0007¨\u0006\b"}, mo113d2 = {"contentValuesOf", "Landroid/content/ContentValues;", "pairs", "", "Lkotlin/Pair;", "", "", "([Lkotlin/Pair;)Landroid/content/ContentValues;", "core-ktx_release"}, mo114k = 2, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: ContentValues.kt */
public final class ContentValuesKt {
    public static final ContentValues contentValuesOf(Pair<String, ? extends Object>... pairs) {
        Intrinsics.checkNotNullParameter(pairs, "pairs");
        ContentValues contentValues = new ContentValues(pairs.length);
        ContentValues $this$contentValuesOf_u24lambda_u2d0 = contentValues;
        for (Pair<String, ? extends Object> pair : pairs) {
            String key = pair.component1();
            Object value = pair.component2();
            if (value == null) {
                $this$contentValuesOf_u24lambda_u2d0.putNull(key);
            } else if (value instanceof String) {
                $this$contentValuesOf_u24lambda_u2d0.put(key, (String) value);
            } else if (value instanceof Integer) {
                $this$contentValuesOf_u24lambda_u2d0.put(key, (Integer) value);
            } else if (value instanceof Long) {
                $this$contentValuesOf_u24lambda_u2d0.put(key, (Long) value);
            } else if (value instanceof Boolean) {
                $this$contentValuesOf_u24lambda_u2d0.put(key, (Boolean) value);
            } else if (value instanceof Float) {
                $this$contentValuesOf_u24lambda_u2d0.put(key, (Float) value);
            } else if (value instanceof Double) {
                $this$contentValuesOf_u24lambda_u2d0.put(key, (Double) value);
            } else if (value instanceof byte[]) {
                $this$contentValuesOf_u24lambda_u2d0.put(key, (byte[]) value);
            } else if (value instanceof Byte) {
                $this$contentValuesOf_u24lambda_u2d0.put(key, (Byte) value);
            } else if (value instanceof Short) {
                $this$contentValuesOf_u24lambda_u2d0.put(key, (Short) value);
            } else {
                throw new IllegalArgumentException("Illegal value type " + value.getClass().getCanonicalName() + " for key \"" + key + Typography.quote);
            }
        }
        return contentValues;
    }
}
