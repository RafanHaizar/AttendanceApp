package p004pl.droidsonroids.gif;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/* renamed from: pl.droidsonroids.gif.GifRenderingExecutor */
final class GifRenderingExecutor extends ScheduledThreadPoolExecutor {

    /* renamed from: pl.droidsonroids.gif.GifRenderingExecutor$InstanceHolder */
    private static final class InstanceHolder {
        /* access modifiers changed from: private */
        public static final GifRenderingExecutor INSTANCE = new GifRenderingExecutor();

        private InstanceHolder() {
        }
    }

    static GifRenderingExecutor getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private GifRenderingExecutor() {
        super(1, new ThreadPoolExecutor.DiscardPolicy());
    }
}
