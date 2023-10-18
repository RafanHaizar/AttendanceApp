package androidx.core.location;

import androidx.core.location.LocationManagerCompat;
import java.util.concurrent.Executor;

/* renamed from: androidx.core.location.LocationManagerCompat$GpsStatusTransport$$ExternalSyntheticLambda2 */
/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class C0699x7b1274a8 implements Runnable {
    public final /* synthetic */ LocationManagerCompat.GpsStatusTransport f$0;
    public final /* synthetic */ Executor f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ C0699x7b1274a8(LocationManagerCompat.GpsStatusTransport gpsStatusTransport, Executor executor, int i) {
        this.f$0 = gpsStatusTransport;
        this.f$1 = executor;
        this.f$2 = i;
    }

    public final void run() {
        this.f$0.mo15792x11681223(this.f$1, this.f$2);
    }
}
