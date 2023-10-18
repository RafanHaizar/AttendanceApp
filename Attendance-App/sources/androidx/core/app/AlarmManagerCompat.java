package androidx.core.app;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Build;

public final class AlarmManagerCompat {
    public static void setAlarmClock(AlarmManager alarmManager, long triggerTime, PendingIntent showIntent, PendingIntent operation) {
        if (Build.VERSION.SDK_INT >= 21) {
            Api21Impl.setAlarmClock(alarmManager, Api21Impl.createAlarmClockInfo(triggerTime, showIntent), operation);
        } else {
            setExact(alarmManager, 0, triggerTime, operation);
        }
    }

    public static void setAndAllowWhileIdle(AlarmManager alarmManager, int type, long triggerAtMillis, PendingIntent operation) {
        if (Build.VERSION.SDK_INT >= 23) {
            Api23Impl.setAndAllowWhileIdle(alarmManager, type, triggerAtMillis, operation);
        } else {
            alarmManager.set(type, triggerAtMillis, operation);
        }
    }

    public static void setExact(AlarmManager alarmManager, int type, long triggerAtMillis, PendingIntent operation) {
        Api19Impl.setExact(alarmManager, type, triggerAtMillis, operation);
    }

    public static void setExactAndAllowWhileIdle(AlarmManager alarmManager, int type, long triggerAtMillis, PendingIntent operation) {
        if (Build.VERSION.SDK_INT >= 23) {
            Api23Impl.setExactAndAllowWhileIdle(alarmManager, type, triggerAtMillis, operation);
        } else {
            setExact(alarmManager, type, triggerAtMillis, operation);
        }
    }

    private AlarmManagerCompat() {
    }

    static class Api21Impl {
        private Api21Impl() {
        }

        static void setAlarmClock(AlarmManager alarmManager, Object info, PendingIntent operation) {
            alarmManager.setAlarmClock((AlarmManager.AlarmClockInfo) info, operation);
        }

        static AlarmManager.AlarmClockInfo createAlarmClockInfo(long triggerTime, PendingIntent showIntent) {
            return new AlarmManager.AlarmClockInfo(triggerTime, showIntent);
        }
    }

    static class Api23Impl {
        private Api23Impl() {
        }

        static void setAndAllowWhileIdle(AlarmManager alarmManager, int type, long triggerAtMillis, PendingIntent operation) {
            alarmManager.setAndAllowWhileIdle(type, triggerAtMillis, operation);
        }

        static void setExactAndAllowWhileIdle(AlarmManager alarmManager, int type, long triggerAtMillis, PendingIntent operation) {
            alarmManager.setExactAndAllowWhileIdle(type, triggerAtMillis, operation);
        }
    }

    static class Api19Impl {
        private Api19Impl() {
        }

        static void setExact(AlarmManager alarmManager, int type, long triggerAtMillis, PendingIntent operation) {
            alarmManager.setExact(type, triggerAtMillis, operation);
        }
    }
}
