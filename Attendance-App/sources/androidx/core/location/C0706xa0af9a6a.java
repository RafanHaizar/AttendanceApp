package androidx.core.location;

import android.os.Bundle;
import androidx.core.location.LocationManagerCompat;

/* renamed from: androidx.core.location.LocationManagerCompat$LocationListenerTransport$$ExternalSyntheticLambda5 */
/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class C0706xa0af9a6a implements Runnable {
    public final /* synthetic */ LocationManagerCompat.LocationListenerTransport f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ Bundle f$3;

    public /* synthetic */ C0706xa0af9a6a(LocationManagerCompat.LocationListenerTransport locationListenerTransport, String str, int i, Bundle bundle) {
        this.f$0 = locationListenerTransport;
        this.f$1 = str;
        this.f$2 = i;
        this.f$3 = bundle;
    }

    public final void run() {
        this.f$0.mo15806xdbe6a717(this.f$1, this.f$2, this.f$3);
    }
}
