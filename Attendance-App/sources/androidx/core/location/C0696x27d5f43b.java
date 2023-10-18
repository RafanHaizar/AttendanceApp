package androidx.core.location;

import android.location.Location;
import androidx.core.util.Consumer;

/* renamed from: androidx.core.location.LocationManagerCompat$CancellableLocationListener$$ExternalSyntheticLambda1 */
/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class C0696x27d5f43b implements Runnable {
    public final /* synthetic */ Consumer f$0;
    public final /* synthetic */ Location f$1;

    public /* synthetic */ C0696x27d5f43b(Consumer consumer, Location location) {
        this.f$0 = consumer;
        this.f$1 = location;
    }

    public final void run() {
        this.f$0.accept(this.f$1);
    }
}
