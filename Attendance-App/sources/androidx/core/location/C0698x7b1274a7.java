package androidx.core.location;

import androidx.core.location.LocationManagerCompat;
import java.util.concurrent.Executor;

/* renamed from: androidx.core.location.LocationManagerCompat$GpsStatusTransport$$ExternalSyntheticLambda1 */
/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class C0698x7b1274a7 implements Runnable {
    public final /* synthetic */ LocationManagerCompat.GpsStatusTransport f$0;
    public final /* synthetic */ Executor f$1;

    public /* synthetic */ C0698x7b1274a7(LocationManagerCompat.GpsStatusTransport gpsStatusTransport, Executor executor) {
        this.f$0 = gpsStatusTransport;
        this.f$1 = executor;
    }

    public final void run() {
        this.f$0.mo15791xc3a89a22(this.f$1);
    }
}
