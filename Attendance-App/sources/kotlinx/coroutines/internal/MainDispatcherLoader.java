package kotlinx.coroutines.internal;

import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import kotlin.Metadata;
import kotlin.sequences.SequencesKt;
import kotlinx.coroutines.MainCoroutineDispatcher;

@Metadata(mo112d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\u0006H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00068\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\b"}, mo113d2 = {"Lkotlinx/coroutines/internal/MainDispatcherLoader;", "", "()V", "FAST_SERVICE_LOADER_ENABLED", "", "dispatcher", "Lkotlinx/coroutines/MainCoroutineDispatcher;", "loadMainDispatcher", "kotlinx-coroutines-core"}, mo114k = 1, mo115mv = {1, 6, 0}, mo117xi = 48)
/* compiled from: MainDispatchers.kt */
public final class MainDispatcherLoader {
    private static final boolean FAST_SERVICE_LOADER_ENABLED = SystemPropsKt.systemProp("kotlinx.coroutines.fast.service.loader", true);
    public static final MainDispatcherLoader INSTANCE;
    public static final MainCoroutineDispatcher dispatcher;

    private MainDispatcherLoader() {
    }

    static {
        MainDispatcherLoader mainDispatcherLoader = new MainDispatcherLoader();
        INSTANCE = mainDispatcherLoader;
        dispatcher = mainDispatcherLoader.loadMainDispatcher();
    }

    private final MainCoroutineDispatcher loadMainDispatcher() {
        List<MainDispatcherFactory> $this$maxByOrNull$iv;
        Object maxElem$iv;
        try {
            if (FAST_SERVICE_LOADER_ENABLED) {
                $this$maxByOrNull$iv = FastServiceLoader.INSTANCE.loadMainDispatcherFactory$kotlinx_coroutines_core();
            } else {
                $this$maxByOrNull$iv = SequencesKt.toList(SequencesKt.asSequence(ServiceLoader.load(MainDispatcherFactory.class, MainDispatcherFactory.class.getClassLoader()).iterator()));
            }
            Iterator iterator$iv = $this$maxByOrNull$iv.iterator();
            if (!iterator$iv.hasNext()) {
                maxElem$iv = null;
            } else {
                maxElem$iv = iterator$iv.next();
                if (iterator$iv.hasNext()) {
                    int maxValue$iv = ((MainDispatcherFactory) maxElem$iv).getLoadPriority();
                    do {
                        Object e$iv = iterator$iv.next();
                        int v$iv = ((MainDispatcherFactory) e$iv).getLoadPriority();
                        if (maxValue$iv < v$iv) {
                            maxElem$iv = e$iv;
                            maxValue$iv = v$iv;
                        }
                    } while (iterator$iv.hasNext());
                }
            }
            MainDispatcherFactory mainDispatcherFactory = (MainDispatcherFactory) maxElem$iv;
            MainCoroutineDispatcher tryCreateDispatcher = mainDispatcherFactory == null ? null : MainDispatchersKt.tryCreateDispatcher(mainDispatcherFactory, $this$maxByOrNull$iv);
            if (tryCreateDispatcher == null) {
                return MainDispatchersKt.createMissingDispatcher$default((Throwable) null, (String) null, 3, (Object) null);
            }
            return tryCreateDispatcher;
        } catch (Throwable e) {
            return MainDispatchersKt.createMissingDispatcher$default(e, (String) null, 2, (Object) null);
        }
    }
}
