package androidx.core.location;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import com.android.tools.r8.annotations.SynthesizedClassV2;
import java.util.List;

public interface LocationListenerCompat extends LocationListener {
    void onFlushComplete(int i);

    void onLocationChanged(List<Location> list);

    void onProviderDisabled(String str);

    void onProviderEnabled(String str);

    void onStatusChanged(String str, int i, Bundle bundle);

    @SynthesizedClassV2(kind = 8, versionHash = "ea87655719898b9807d7a88878e9de051d12af172d2fab563c9881b5e404e7d4")
    /* renamed from: androidx.core.location.LocationListenerCompat$-CC  reason: invalid class name */
    public final /* synthetic */ class CC {
        public static void $default$onStatusChanged(LocationListenerCompat _this, String provider, int status, Bundle extras) {
        }

        public static void $default$onProviderEnabled(LocationListenerCompat _this, String provider) {
        }

        public static void $default$onProviderDisabled(LocationListenerCompat _this, String provider) {
        }

        public static void $default$onLocationChanged(LocationListenerCompat _this, List locations) {
            int size = locations.size();
            for (int i = 0; i < size; i++) {
                _this.onLocationChanged((Location) locations.get(i));
            }
        }

        public static void $default$onFlushComplete(LocationListenerCompat _this, int requestCode) {
        }
    }
}
