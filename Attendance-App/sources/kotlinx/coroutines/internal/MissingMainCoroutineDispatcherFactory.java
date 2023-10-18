package kotlinx.coroutines.internal;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.MainCoroutineDispatcher;
import kotlinx.coroutines.internal.MainDispatcherFactory;

@Metadata(mo112d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\bÇ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000b"}, mo113d2 = {"Lkotlinx/coroutines/internal/MissingMainCoroutineDispatcherFactory;", "Lkotlinx/coroutines/internal/MainDispatcherFactory;", "()V", "loadPriority", "", "getLoadPriority", "()I", "createDispatcher", "Lkotlinx/coroutines/MainCoroutineDispatcher;", "allFactories", "", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: MainDispatchers.kt */
public final class MissingMainCoroutineDispatcherFactory implements MainDispatcherFactory {
    public static final MissingMainCoroutineDispatcherFactory INSTANCE = new MissingMainCoroutineDispatcherFactory();

    private MissingMainCoroutineDispatcherFactory() {
    }

    public String hintOnError() {
        return MainDispatcherFactory.DefaultImpls.hintOnError(this);
    }

    public int getLoadPriority() {
        return -1;
    }

    public MainCoroutineDispatcher createDispatcher(List<? extends MainDispatcherFactory> allFactories) {
        return new MissingMainCoroutineDispatcher((Throwable) null, (String) null, 2, (DefaultConstructorMarker) null);
    }
}
