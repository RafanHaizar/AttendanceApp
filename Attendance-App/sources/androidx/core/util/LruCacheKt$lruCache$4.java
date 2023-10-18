package androidx.core.util;

import android.util.LruCache;
import com.itextpdf.forms.xfdf.XfdfConstants;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000#\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0001J\u0017\u0010\u0002\u001a\u0004\u0018\u00018\u00012\u0006\u0010\u0003\u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010\u0004J/\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00028\u00002\u0006\u0010\t\u001a\u00028\u00012\b\u0010\n\u001a\u0004\u0018\u00018\u0001H\u0014¢\u0006\u0002\u0010\u000bJ\u001d\u0010\f\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00028\u00002\u0006\u0010\u000e\u001a\u00028\u0001H\u0014¢\u0006\u0002\u0010\u000f¨\u0006\u0010"}, mo113d2 = {"androidx/core/util/LruCacheKt$lruCache$4", "Landroid/util/LruCache;", "create", "key", "(Ljava/lang/Object;)Ljava/lang/Object;", "entryRemoved", "", "evicted", "", "oldValue", "newValue", "(ZLjava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)V", "sizeOf", "", "value", "(Ljava/lang/Object;Ljava/lang/Object;)I", "core-ktx_release"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 176)
/* compiled from: LruCache.kt */
public final class LruCacheKt$lruCache$4 extends LruCache<K, V> {
    final /* synthetic */ Function1<K, V> $create;
    final /* synthetic */ Function4<Boolean, K, V, V, Unit> $onEntryRemoved;
    final /* synthetic */ Function2<K, V, Integer> $sizeOf;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public LruCacheKt$lruCache$4(int $maxSize, Function2<? super K, ? super V, Integer> $sizeOf2, Function1<? super K, ? extends V> $create2, Function4<? super Boolean, ? super K, ? super V, ? super V, Unit> $onEntryRemoved2) {
        super($maxSize);
        this.$sizeOf = $sizeOf2;
        this.$create = $create2;
        this.$onEntryRemoved = $onEntryRemoved2;
    }

    /* access modifiers changed from: protected */
    public int sizeOf(K key, V value) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, XfdfConstants.VALUE);
        return this.$sizeOf.invoke(key, value).intValue();
    }

    /* access modifiers changed from: protected */
    public V create(K key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return this.$create.invoke(key);
    }

    /* access modifiers changed from: protected */
    public void entryRemoved(boolean evicted, K key, V oldValue, V newValue) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(oldValue, "oldValue");
        this.$onEntryRemoved.invoke(Boolean.valueOf(evicted), key, oldValue, newValue);
    }
}
