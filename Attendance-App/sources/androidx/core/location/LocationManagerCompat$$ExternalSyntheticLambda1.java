package androidx.core.location;

import android.location.Location;
import androidx.core.util.Consumer;

/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class LocationManagerCompat$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ Consumer f$0;
    public final /* synthetic */ Location f$1;

    public /* synthetic */ LocationManagerCompat$$ExternalSyntheticLambda1(Consumer consumer, Location location) {
        this.f$0 = consumer;
        this.f$1 = location;
    }

    public final void run() {
        this.f$0.accept(this.f$1);
    }
}
