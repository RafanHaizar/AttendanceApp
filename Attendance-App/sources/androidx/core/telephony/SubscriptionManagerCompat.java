package androidx.core.telephony;

import android.os.Build;
import android.telephony.SubscriptionManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SubscriptionManagerCompat {
    private static Method sGetSlotIndexMethod;

    public static int getSlotIndex(int subId) {
        if (subId == -1) {
            return -1;
        }
        if (Build.VERSION.SDK_INT >= 29) {
            return Api29Impl.getSlotIndex(subId);
        }
        try {
            if (sGetSlotIndexMethod == null) {
                if (Build.VERSION.SDK_INT >= 26) {
                    sGetSlotIndexMethod = SubscriptionManager.class.getDeclaredMethod("getSlotIndex", new Class[]{Integer.TYPE});
                } else {
                    sGetSlotIndexMethod = SubscriptionManager.class.getDeclaredMethod("getSlotId", new Class[]{Integer.TYPE});
                }
                sGetSlotIndexMethod.setAccessible(true);
            }
            Integer slotIdx = (Integer) sGetSlotIndexMethod.invoke((Object) null, new Object[]{Integer.valueOf(subId)});
            if (slotIdx != null) {
                return slotIdx.intValue();
            }
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
        }
        return -1;
    }

    private SubscriptionManagerCompat() {
    }

    private static class Api29Impl {
        private Api29Impl() {
        }

        static int getSlotIndex(int subId) {
            return SubscriptionManager.getSlotIndex(subId);
        }
    }
}
