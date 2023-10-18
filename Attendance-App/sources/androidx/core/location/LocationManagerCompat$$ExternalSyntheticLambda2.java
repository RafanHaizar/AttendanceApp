package androidx.core.location;

import androidx.core.location.LocationManagerCompat;
import androidx.core.p001os.CancellationSignal;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class LocationManagerCompat$$ExternalSyntheticLambda2 implements CancellationSignal.OnCancelListener {
    public final /* synthetic */ LocationManagerCompat.CancellableLocationListener f$0;

    public /* synthetic */ LocationManagerCompat$$ExternalSyntheticLambda2(LocationManagerCompat.CancellableLocationListener cancellableLocationListener) {
        this.f$0 = cancellableLocationListener;
    }

    public final void onCancel() {
        this.f$0.cancel();
    }
}
