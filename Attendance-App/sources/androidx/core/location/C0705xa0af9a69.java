package androidx.core.location;

import android.location.Location;
import androidx.core.location.LocationManagerCompat;

/* renamed from: androidx.core.location.LocationManagerCompat$LocationListenerTransport$$ExternalSyntheticLambda4 */
/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class C0705xa0af9a69 implements Runnable {
    public final /* synthetic */ LocationManagerCompat.LocationListenerTransport f$0;
    public final /* synthetic */ Location f$1;

    public /* synthetic */ C0705xa0af9a69(LocationManagerCompat.LocationListenerTransport locationListenerTransport, Location location) {
        this.f$0 = locationListenerTransport;
        this.f$1 = location;
    }

    public final void run() {
        this.f$0.mo15802xa8d50b3d(this.f$1);
    }
}
