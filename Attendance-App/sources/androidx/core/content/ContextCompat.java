package androidx.core.content;

import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.DownloadManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.app.UiModeManager;
import android.app.WallpaperManager;
import android.app.admin.DevicePolicyManager;
import android.app.job.JobScheduler;
import android.app.usage.UsageStatsManager;
import android.appwidget.AppWidgetManager;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.RestrictionsManager;
import android.content.pm.LauncherApps;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.hardware.ConsumerIrManager;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraManager;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.hardware.usb.UsbManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaRouter;
import android.media.projection.MediaProjectionManager;
import android.media.session.MediaSessionManager;
import android.media.tv.TvInputManager;
import android.net.ConnectivityManager;
import android.net.nsd.NsdManager;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.nfc.NfcManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.os.Handler;
import android.os.PowerManager;
import android.os.Process;
import android.os.UserManager;
import android.os.Vibrator;
import android.os.storage.StorageManager;
import android.print.PrintManager;
import android.telecom.TelecomManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.CaptioningManager;
import android.view.inputmethod.InputMethodManager;
import android.view.textservice.TextServicesManager;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.p001os.BuildCompat;
import androidx.core.p001os.ExecutorCompat;
import androidx.core.util.ObjectsCompat;
import com.itextpdf.forms.xfdf.XfdfConstants;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.concurrent.Executor;

public class ContextCompat {
    private static final String DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION_SUFFIX = ".DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION";
    public static final int RECEIVER_EXPORTED = 2;
    public static final int RECEIVER_NOT_EXPORTED = 4;
    public static final int RECEIVER_VISIBLE_TO_INSTANT_APPS = 1;
    private static final String TAG = "ContextCompat";
    private static final Object sLock = new Object();
    private static final Object sSync = new Object();
    private static TypedValue sTempValue;

    @Retention(RetentionPolicy.SOURCE)
    public @interface RegisterReceiverFlags {
    }

    protected ContextCompat() {
    }

    public static String getAttributionTag(Context context) {
        if (Build.VERSION.SDK_INT >= 30) {
            return Api30Impl.getAttributionTag(context);
        }
        return null;
    }

    public static boolean startActivities(Context context, Intent[] intents) {
        return startActivities(context, intents, (Bundle) null);
    }

    public static boolean startActivities(Context context, Intent[] intents, Bundle options) {
        Api16Impl.startActivities(context, intents, options);
        return true;
    }

    public static void startActivity(Context context, Intent intent, Bundle options) {
        Api16Impl.startActivity(context, intent, options);
    }

    public static File getDataDir(Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return Api24Impl.getDataDir(context);
        }
        String dataDir = context.getApplicationInfo().dataDir;
        if (dataDir != null) {
            return new File(dataDir);
        }
        return null;
    }

    public static File[] getObbDirs(Context context) {
        return Api19Impl.getObbDirs(context);
    }

    public static File[] getExternalFilesDirs(Context context, String type) {
        return Api19Impl.getExternalFilesDirs(context, type);
    }

    public static File[] getExternalCacheDirs(Context context) {
        return Api19Impl.getExternalCacheDirs(context);
    }

    public static Drawable getDrawable(Context context, int id) {
        if (Build.VERSION.SDK_INT >= 21) {
            return Api21Impl.getDrawable(context, id);
        }
        return context.getResources().getDrawable(id);
    }

    public static ColorStateList getColorStateList(Context context, int id) {
        return ResourcesCompat.getColorStateList(context.getResources(), id, context.getTheme());
    }

    public static int getColor(Context context, int id) {
        if (Build.VERSION.SDK_INT >= 23) {
            return Api23Impl.getColor(context, id);
        }
        return context.getResources().getColor(id);
    }

    public static int checkSelfPermission(Context context, String permission) {
        ObjectsCompat.requireNonNull(permission, "permission must be non-null");
        if (BuildCompat.isAtLeastT() || !TextUtils.equals("android.permission.POST_NOTIFICATIONS", permission)) {
            return context.checkPermission(permission, Process.myPid(), Process.myUid());
        }
        if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
            return 0;
        }
        return -1;
    }

    public static File getNoBackupFilesDir(Context context) {
        if (Build.VERSION.SDK_INT >= 21) {
            return Api21Impl.getNoBackupFilesDir(context);
        }
        return createFilesDir(new File(context.getApplicationInfo().dataDir, "no_backup"));
    }

    public static File getCodeCacheDir(Context context) {
        if (Build.VERSION.SDK_INT >= 21) {
            return Api21Impl.getCodeCacheDir(context);
        }
        return createFilesDir(new File(context.getApplicationInfo().dataDir, "code_cache"));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002e, code lost:
        return r4;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.io.File createFilesDir(java.io.File r4) {
        /*
            java.lang.Object r0 = sSync
            monitor-enter(r0)
            boolean r1 = r4.exists()     // Catch:{ all -> 0x002f }
            if (r1 != 0) goto L_0x002d
            boolean r1 = r4.mkdirs()     // Catch:{ all -> 0x002f }
            if (r1 == 0) goto L_0x0011
            monitor-exit(r0)     // Catch:{ all -> 0x002f }
            return r4
        L_0x0011:
            java.lang.String r1 = "ContextCompat"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x002f }
            r2.<init>()     // Catch:{ all -> 0x002f }
            java.lang.String r3 = "Unable to create files subdir "
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x002f }
            java.lang.String r3 = r4.getPath()     // Catch:{ all -> 0x002f }
            java.lang.StringBuilder r2 = r2.append(r3)     // Catch:{ all -> 0x002f }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x002f }
            android.util.Log.w(r1, r2)     // Catch:{ all -> 0x002f }
        L_0x002d:
            monitor-exit(r0)     // Catch:{ all -> 0x002f }
            return r4
        L_0x002f:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x002f }
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.content.ContextCompat.createFilesDir(java.io.File):java.io.File");
    }

    public static Context createDeviceProtectedStorageContext(Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return Api24Impl.createDeviceProtectedStorageContext(context);
        }
        return null;
    }

    public static boolean isDeviceProtectedStorage(Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return Api24Impl.isDeviceProtectedStorage(context);
        }
        return false;
    }

    public static Executor getMainExecutor(Context context) {
        if (Build.VERSION.SDK_INT >= 28) {
            return Api28Impl.getMainExecutor(context);
        }
        return ExecutorCompat.create(new Handler(context.getMainLooper()));
    }

    public static void startForegroundService(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= 26) {
            Api26Impl.startForegroundService(context, intent);
        } else {
            context.startService(intent);
        }
    }

    public static <T> T getSystemService(Context context, Class<T> serviceClass) {
        if (Build.VERSION.SDK_INT >= 23) {
            return Api23Impl.getSystemService(context, serviceClass);
        }
        String serviceName = getSystemServiceName(context, serviceClass);
        if (serviceName != null) {
            return context.getSystemService(serviceName);
        }
        return null;
    }

    public static Intent registerReceiver(Context context, BroadcastReceiver receiver, IntentFilter filter, int flags) {
        return registerReceiver(context, receiver, filter, (String) null, (Handler) null, flags);
    }

    public static Intent registerReceiver(Context context, BroadcastReceiver receiver, IntentFilter filter, String broadcastPermission, Handler scheduler, int flags) {
        if ((flags & 1) == 0 || (flags & 4) == 0) {
            if ((flags & 1) != 0) {
                flags |= 2;
            }
            if ((flags & 2) == 0 && (flags & 4) == 0) {
                throw new IllegalArgumentException("One of either RECEIVER_EXPORTED or RECEIVER_NOT_EXPORTED is required");
            } else if ((flags & 2) != 0 && (flags & 4) != 0) {
                throw new IllegalArgumentException("Cannot specify both RECEIVER_EXPORTED and RECEIVER_NOT_EXPORTED");
            } else if (BuildCompat.isAtLeastT()) {
                return Api33Impl.registerReceiver(context, receiver, filter, broadcastPermission, scheduler, flags);
            } else {
                if (Build.VERSION.SDK_INT >= 26) {
                    return Api26Impl.registerReceiver(context, receiver, filter, broadcastPermission, scheduler, flags);
                }
                if ((flags & 4) == 0 || broadcastPermission != null) {
                    return context.registerReceiver(receiver, filter, broadcastPermission, scheduler);
                }
                return context.registerReceiver(receiver, filter, obtainAndCheckReceiverPermission(context), scheduler);
            }
        } else {
            throw new IllegalArgumentException("Cannot specify both RECEIVER_VISIBLE_TO_INSTANT_APPS and RECEIVER_NOT_EXPORTED");
        }
    }

    public static String getSystemServiceName(Context context, Class<?> serviceClass) {
        if (Build.VERSION.SDK_INT >= 23) {
            return Api23Impl.getSystemServiceName(context, serviceClass);
        }
        return LegacyServiceMapHolder.SERVICES.get(serviceClass);
    }

    static String obtainAndCheckReceiverPermission(Context obj) {
        String permission = obj.getPackageName() + DYNAMIC_RECEIVER_NOT_EXPORTED_PERMISSION_SUFFIX;
        if (PermissionChecker.checkSelfPermission(obj, permission) == 0) {
            return permission;
        }
        throw new RuntimeException("Permission " + permission + " is required by your application to receive broadcasts, please add it to your manifest");
    }

    private static final class LegacyServiceMapHolder {
        static final HashMap<Class<?>, String> SERVICES;

        private LegacyServiceMapHolder() {
        }

        static {
            HashMap<Class<?>, String> hashMap = new HashMap<>();
            SERVICES = hashMap;
            if (Build.VERSION.SDK_INT >= 22) {
                hashMap.put(SubscriptionManager.class, "telephony_subscription_service");
                hashMap.put(UsageStatsManager.class, "usagestats");
            }
            if (Build.VERSION.SDK_INT >= 21) {
                hashMap.put(AppWidgetManager.class, "appwidget");
                hashMap.put(BatteryManager.class, "batterymanager");
                hashMap.put(CameraManager.class, "camera");
                hashMap.put(JobScheduler.class, "jobscheduler");
                hashMap.put(LauncherApps.class, "launcherapps");
                hashMap.put(MediaProjectionManager.class, "media_projection");
                hashMap.put(MediaSessionManager.class, "media_session");
                hashMap.put(RestrictionsManager.class, "restrictions");
                hashMap.put(TelecomManager.class, "telecom");
                hashMap.put(TvInputManager.class, "tv_input");
            }
            hashMap.put(AppOpsManager.class, "appops");
            hashMap.put(CaptioningManager.class, "captioning");
            hashMap.put(ConsumerIrManager.class, "consumer_ir");
            hashMap.put(PrintManager.class, XfdfConstants.PRINT);
            hashMap.put(BluetoothManager.class, "bluetooth");
            hashMap.put(DisplayManager.class, CommonCssConstants.DISPLAY);
            hashMap.put(UserManager.class, "user");
            hashMap.put(InputManager.class, "input");
            hashMap.put(MediaRouter.class, "media_router");
            hashMap.put(NsdManager.class, "servicediscovery");
            hashMap.put(AccessibilityManager.class, "accessibility");
            hashMap.put(AccountManager.class, "account");
            hashMap.put(ActivityManager.class, "activity");
            hashMap.put(AlarmManager.class, NotificationCompat.CATEGORY_ALARM);
            hashMap.put(AudioManager.class, "audio");
            hashMap.put(ClipboardManager.class, "clipboard");
            hashMap.put(ConnectivityManager.class, "connectivity");
            hashMap.put(DevicePolicyManager.class, "device_policy");
            hashMap.put(DownloadManager.class, "download");
            hashMap.put(DropBoxManager.class, "dropbox");
            hashMap.put(InputMethodManager.class, "input_method");
            hashMap.put(KeyguardManager.class, "keyguard");
            hashMap.put(LayoutInflater.class, "layout_inflater");
            hashMap.put(LocationManager.class, "location");
            hashMap.put(NfcManager.class, "nfc");
            hashMap.put(NotificationManager.class, "notification");
            hashMap.put(PowerManager.class, "power");
            hashMap.put(SearchManager.class, "search");
            hashMap.put(SensorManager.class, "sensor");
            hashMap.put(StorageManager.class, "storage");
            hashMap.put(TelephonyManager.class, "phone");
            hashMap.put(TextServicesManager.class, "textservices");
            hashMap.put(UiModeManager.class, "uimode");
            hashMap.put(UsbManager.class, "usb");
            hashMap.put(Vibrator.class, "vibrator");
            hashMap.put(WallpaperManager.class, "wallpaper");
            hashMap.put(WifiP2pManager.class, "wifip2p");
            hashMap.put(WifiManager.class, "wifi");
            hashMap.put(WindowManager.class, "window");
        }
    }

    static class Api16Impl {
        private Api16Impl() {
        }

        static void startActivities(Context obj, Intent[] intents, Bundle options) {
            obj.startActivities(intents, options);
        }

        static void startActivity(Context obj, Intent intent, Bundle options) {
            obj.startActivity(intent, options);
        }
    }

    static class Api19Impl {
        private Api19Impl() {
        }

        static File[] getExternalCacheDirs(Context obj) {
            return obj.getExternalCacheDirs();
        }

        static File[] getExternalFilesDirs(Context obj, String type) {
            return obj.getExternalFilesDirs(type);
        }

        static File[] getObbDirs(Context obj) {
            return obj.getObbDirs();
        }
    }

    static class Api21Impl {
        private Api21Impl() {
        }

        static Drawable getDrawable(Context obj, int id) {
            return obj.getDrawable(id);
        }

        static File getNoBackupFilesDir(Context obj) {
            return obj.getNoBackupFilesDir();
        }

        static File getCodeCacheDir(Context obj) {
            return obj.getCodeCacheDir();
        }
    }

    static class Api23Impl {
        private Api23Impl() {
        }

        static int getColor(Context obj, int id) {
            return obj.getColor(id);
        }

        static <T> T getSystemService(Context obj, Class<T> serviceClass) {
            return obj.getSystemService(serviceClass);
        }

        static String getSystemServiceName(Context obj, Class<?> serviceClass) {
            return obj.getSystemServiceName(serviceClass);
        }
    }

    static class Api24Impl {
        private Api24Impl() {
        }

        static File getDataDir(Context obj) {
            return obj.getDataDir();
        }

        static Context createDeviceProtectedStorageContext(Context obj) {
            return obj.createDeviceProtectedStorageContext();
        }

        static boolean isDeviceProtectedStorage(Context obj) {
            return obj.isDeviceProtectedStorage();
        }
    }

    static class Api26Impl {
        private Api26Impl() {
        }

        static Intent registerReceiver(Context obj, BroadcastReceiver receiver, IntentFilter filter, String broadcastPermission, Handler scheduler, int flags) {
            if ((flags & 4) == 0 || broadcastPermission != null) {
                return obj.registerReceiver(receiver, filter, broadcastPermission, scheduler, flags & 1);
            }
            return obj.registerReceiver(receiver, filter, ContextCompat.obtainAndCheckReceiverPermission(obj), scheduler);
        }

        static ComponentName startForegroundService(Context obj, Intent service) {
            return obj.startForegroundService(service);
        }
    }

    static class Api28Impl {
        private Api28Impl() {
        }

        static Executor getMainExecutor(Context obj) {
            return obj.getMainExecutor();
        }
    }

    static class Api30Impl {
        private Api30Impl() {
        }

        static String getAttributionTag(Context obj) {
            return obj.getAttributionTag();
        }
    }

    static class Api33Impl {
        private Api33Impl() {
        }

        static Intent registerReceiver(Context obj, BroadcastReceiver receiver, IntentFilter filter, String broadcastPermission, Handler scheduler, int flags) {
            return obj.registerReceiver(receiver, filter, broadcastPermission, scheduler, flags);
        }
    }
}
