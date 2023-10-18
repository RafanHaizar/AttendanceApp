package androidx.lifecycle.viewmodel;

import androidx.lifecycle.viewmodel.CreationExtras;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0001¢\u0006\u0002\u0010\u0003J$\u0010\u0004\u001a\u0004\u0018\u0001H\u0005\"\u0004\b\u0000\u0010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0007H\u0002¢\u0006\u0002\u0010\bJ*\u0010\t\u001a\u00020\n\"\u0004\b\u0000\u0010\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u0002H\u00050\u00072\u0006\u0010\u000b\u001a\u0002H\u0005H\u0002¢\u0006\u0002\u0010\f¨\u0006\r"}, mo113d2 = {"Landroidx/lifecycle/viewmodel/MutableCreationExtras;", "Landroidx/lifecycle/viewmodel/CreationExtras;", "initialExtras", "(Landroidx/lifecycle/viewmodel/CreationExtras;)V", "get", "T", "key", "Landroidx/lifecycle/viewmodel/CreationExtras$Key;", "(Landroidx/lifecycle/viewmodel/CreationExtras$Key;)Ljava/lang/Object;", "set", "", "t", "(Landroidx/lifecycle/viewmodel/CreationExtras$Key;Ljava/lang/Object;)V", "lifecycle-viewmodel_release"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: CreationExtras.kt */
public final class MutableCreationExtras extends CreationExtras {
    public MutableCreationExtras() {
        this((CreationExtras) null, 1, (DefaultConstructorMarker) null);
    }

    public MutableCreationExtras(CreationExtras initialExtras) {
        Intrinsics.checkNotNullParameter(initialExtras, "initialExtras");
        getMap$lifecycle_viewmodel_release().putAll(initialExtras.getMap$lifecycle_viewmodel_release());
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ MutableCreationExtras(CreationExtras creationExtras, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? CreationExtras.Empty.INSTANCE : creationExtras);
    }

    public final <T> void set(CreationExtras.Key<T> key, T t) {
        Intrinsics.checkNotNullParameter(key, "key");
        getMap$lifecycle_viewmodel_release().put(key, t);
    }

    public <T> T get(CreationExtras.Key<T> key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return getMap$lifecycle_viewmodel_release().get(key);
    }
}
