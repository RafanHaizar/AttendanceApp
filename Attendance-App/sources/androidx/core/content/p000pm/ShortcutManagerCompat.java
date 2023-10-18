package androidx.core.content.p000pm;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import androidx.core.content.p000pm.ShortcutInfoCompat;
import androidx.core.content.p000pm.ShortcutInfoCompatSaver;
import androidx.core.graphics.drawable.IconCompat;
import androidx.core.util.Preconditions;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/* renamed from: androidx.core.content.pm.ShortcutManagerCompat */
public class ShortcutManagerCompat {
    static final String ACTION_INSTALL_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";
    private static final int DEFAULT_MAX_ICON_DIMENSION_DP = 96;
    private static final int DEFAULT_MAX_ICON_DIMENSION_LOWRAM_DP = 48;
    public static final String EXTRA_SHORTCUT_ID = "android.intent.extra.shortcut.ID";
    public static final int FLAG_MATCH_CACHED = 8;
    public static final int FLAG_MATCH_DYNAMIC = 2;
    public static final int FLAG_MATCH_MANIFEST = 1;
    public static final int FLAG_MATCH_PINNED = 4;
    static final String INSTALL_SHORTCUT_PERMISSION = "com.android.launcher.permission.INSTALL_SHORTCUT";
    private static final String SHORTCUT_LISTENER_INTENT_FILTER_ACTION = "androidx.core.content.pm.SHORTCUT_LISTENER";
    private static final String SHORTCUT_LISTENER_META_DATA_KEY = "androidx.core.content.pm.shortcut_listener_impl";
    private static volatile List<ShortcutInfoChangeListener> sShortcutInfoChangeListeners = null;
    private static volatile ShortcutInfoCompatSaver<?> sShortcutInfoCompatSaver = null;

    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: androidx.core.content.pm.ShortcutManagerCompat$ShortcutMatchFlags */
    public @interface ShortcutMatchFlags {
    }

    private ShortcutManagerCompat() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0036  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static boolean isRequestPinShortcutSupported(android.content.Context r6) {
        /*
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 26
            if (r0 < r1) goto L_0x0013
            java.lang.Class<android.content.pm.ShortcutManager> r0 = android.content.pm.ShortcutManager.class
            java.lang.Object r0 = r6.getSystemService(r0)
            android.content.pm.ShortcutManager r0 = (android.content.pm.ShortcutManager) r0
            boolean r0 = r0.isRequestPinShortcutSupported()
            return r0
        L_0x0013:
            java.lang.String r0 = "com.android.launcher.permission.INSTALL_SHORTCUT"
            int r1 = androidx.core.content.ContextCompat.checkSelfPermission(r6, r0)
            r2 = 0
            if (r1 == 0) goto L_0x001d
            return r2
        L_0x001d:
            android.content.pm.PackageManager r1 = r6.getPackageManager()
            android.content.Intent r3 = new android.content.Intent
            java.lang.String r4 = "com.android.launcher.action.INSTALL_SHORTCUT"
            r3.<init>(r4)
            java.util.List r1 = r1.queryBroadcastReceivers(r3, r2)
            java.util.Iterator r1 = r1.iterator()
        L_0x0030:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L_0x0050
            java.lang.Object r3 = r1.next()
            android.content.pm.ResolveInfo r3 = (android.content.pm.ResolveInfo) r3
            android.content.pm.ActivityInfo r4 = r3.activityInfo
            java.lang.String r4 = r4.permission
            boolean r5 = android.text.TextUtils.isEmpty(r4)
            if (r5 != 0) goto L_0x004e
            boolean r5 = r0.equals(r4)
            if (r5 == 0) goto L_0x004d
            goto L_0x004e
        L_0x004d:
            goto L_0x0030
        L_0x004e:
            r0 = 1
            return r0
        L_0x0050:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.content.p000pm.ShortcutManagerCompat.isRequestPinShortcutSupported(android.content.Context):boolean");
    }

    public static boolean requestPinShortcut(Context context, ShortcutInfoCompat shortcut, final IntentSender callback) {
        if (Build.VERSION.SDK_INT <= 31 && shortcut.isExcludedFromSurfaces(1)) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 26) {
            return ((ShortcutManager) context.getSystemService(ShortcutManager.class)).requestPinShortcut(shortcut.toShortcutInfo(), callback);
        }
        if (!isRequestPinShortcutSupported(context)) {
            return false;
        }
        Intent intent = shortcut.addToIntent(new Intent(ACTION_INSTALL_SHORTCUT));
        if (callback == null) {
            context.sendBroadcast(intent);
            return true;
        }
        context.sendOrderedBroadcast(intent, (String) null, new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                try {
                    callback.sendIntent(context, 0, (Intent) null, (IntentSender.OnFinished) null, (Handler) null);
                } catch (IntentSender.SendIntentException e) {
                }
            }
        }, (Handler) null, -1, (String) null, (Bundle) null);
        return true;
    }

    public static Intent createShortcutResultIntent(Context context, ShortcutInfoCompat shortcut) {
        Intent result = null;
        if (Build.VERSION.SDK_INT >= 26) {
            result = ((ShortcutManager) context.getSystemService(ShortcutManager.class)).createShortcutResultIntent(shortcut.toShortcutInfo());
        }
        if (result == null) {
            result = new Intent();
        }
        return shortcut.addToIntent(result);
    }

    public static List<ShortcutInfoCompat> getShortcuts(Context context, int matchFlags) {
        if (Build.VERSION.SDK_INT >= 30) {
            return ShortcutInfoCompat.fromShortcuts(context, ((ShortcutManager) context.getSystemService(ShortcutManager.class)).getShortcuts(matchFlags));
        }
        if (Build.VERSION.SDK_INT >= 25) {
            ShortcutManager manager = (ShortcutManager) context.getSystemService(ShortcutManager.class);
            List<ShortcutInfo> shortcuts = new ArrayList<>();
            if ((matchFlags & 1) != 0) {
                shortcuts.addAll(manager.getManifestShortcuts());
            }
            if ((matchFlags & 2) != 0) {
                shortcuts.addAll(manager.getDynamicShortcuts());
            }
            if ((matchFlags & 4) != 0) {
                shortcuts.addAll(manager.getPinnedShortcuts());
            }
            return ShortcutInfoCompat.fromShortcuts(context, shortcuts);
        }
        if ((matchFlags & 2) != 0) {
            try {
                return getShortcutInfoSaverInstance(context).getShortcuts();
            } catch (Exception e) {
            }
        }
        return Collections.emptyList();
    }

    public static boolean addDynamicShortcuts(Context context, List<ShortcutInfoCompat> shortcutInfoList) {
        List<ShortcutInfoCompat> clone = removeShortcutsExcludedFromSurface(shortcutInfoList, 1);
        if (Build.VERSION.SDK_INT <= 29) {
            convertUriIconsToBitmapIcons(context, clone);
        }
        if (Build.VERSION.SDK_INT >= 25) {
            ArrayList<ShortcutInfo> shortcuts = new ArrayList<>();
            for (ShortcutInfoCompat item : clone) {
                shortcuts.add(item.toShortcutInfo());
            }
            if (!((ShortcutManager) context.getSystemService(ShortcutManager.class)).addDynamicShortcuts(shortcuts)) {
                return false;
            }
        }
        getShortcutInfoSaverInstance(context).addShortcuts(clone);
        for (ShortcutInfoChangeListener listener : getShortcutInfoListeners(context)) {
            listener.onShortcutAdded(shortcutInfoList);
        }
        return true;
    }

    public static int getMaxShortcutCountPerActivity(Context context) {
        Preconditions.checkNotNull(context);
        if (Build.VERSION.SDK_INT >= 25) {
            return ((ShortcutManager) context.getSystemService(ShortcutManager.class)).getMaxShortcutCountPerActivity();
        }
        return 5;
    }

    public static boolean isRateLimitingActive(Context context) {
        Preconditions.checkNotNull(context);
        if (Build.VERSION.SDK_INT >= 25) {
            return ((ShortcutManager) context.getSystemService(ShortcutManager.class)).isRateLimitingActive();
        }
        return getShortcuts(context, 3).size() == getMaxShortcutCountPerActivity(context);
    }

    public static int getIconMaxWidth(Context context) {
        Preconditions.checkNotNull(context);
        if (Build.VERSION.SDK_INT >= 25) {
            return ((ShortcutManager) context.getSystemService(ShortcutManager.class)).getIconMaxWidth();
        }
        return getIconDimensionInternal(context, true);
    }

    public static int getIconMaxHeight(Context context) {
        Preconditions.checkNotNull(context);
        if (Build.VERSION.SDK_INT >= 25) {
            return ((ShortcutManager) context.getSystemService(ShortcutManager.class)).getIconMaxHeight();
        }
        return getIconDimensionInternal(context, false);
    }

    public static void reportShortcutUsed(Context context, String shortcutId) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(shortcutId);
        if (Build.VERSION.SDK_INT >= 25) {
            ((ShortcutManager) context.getSystemService(ShortcutManager.class)).reportShortcutUsed(shortcutId);
        }
        for (ShortcutInfoChangeListener listener : getShortcutInfoListeners(context)) {
            listener.onShortcutUsageReported(Collections.singletonList(shortcutId));
        }
    }

    public static boolean setDynamicShortcuts(Context context, List<ShortcutInfoCompat> shortcutInfoList) {
        Preconditions.checkNotNull(context);
        Preconditions.checkNotNull(shortcutInfoList);
        List<ShortcutInfoCompat> clone = removeShortcutsExcludedFromSurface(shortcutInfoList, 1);
        if (Build.VERSION.SDK_INT >= 25) {
            List<ShortcutInfo> shortcuts = new ArrayList<>(clone.size());
            for (ShortcutInfoCompat compat : clone) {
                shortcuts.add(compat.toShortcutInfo());
            }
            if (!((ShortcutManager) context.getSystemService(ShortcutManager.class)).setDynamicShortcuts(shortcuts)) {
                return false;
            }
        }
        getShortcutInfoSaverInstance(context).removeAllShortcuts();
        getShortcutInfoSaverInstance(context).addShortcuts(clone);
        for (ShortcutInfoChangeListener listener : getShortcutInfoListeners(context)) {
            listener.onAllShortcutsRemoved();
            listener.onShortcutAdded(shortcutInfoList);
        }
        return true;
    }

    public static List<ShortcutInfoCompat> getDynamicShortcuts(Context context) {
        if (Build.VERSION.SDK_INT >= 25) {
            List<ShortcutInfo> shortcuts = ((ShortcutManager) context.getSystemService(ShortcutManager.class)).getDynamicShortcuts();
            List<ShortcutInfoCompat> compats = new ArrayList<>(shortcuts.size());
            for (ShortcutInfo item : shortcuts) {
                compats.add(new ShortcutInfoCompat.Builder(context, item).build());
            }
            return compats;
        }
        try {
            return getShortcutInfoSaverInstance(context).getShortcuts();
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public static boolean updateShortcuts(Context context, List<ShortcutInfoCompat> shortcutInfoList) {
        List<ShortcutInfoCompat> clone = removeShortcutsExcludedFromSurface(shortcutInfoList, 1);
        if (Build.VERSION.SDK_INT <= 29) {
            convertUriIconsToBitmapIcons(context, clone);
        }
        if (Build.VERSION.SDK_INT >= 25) {
            ArrayList<ShortcutInfo> shortcuts = new ArrayList<>();
            for (ShortcutInfoCompat item : clone) {
                shortcuts.add(item.toShortcutInfo());
            }
            if (!((ShortcutManager) context.getSystemService(ShortcutManager.class)).updateShortcuts(shortcuts)) {
                return false;
            }
        }
        getShortcutInfoSaverInstance(context).addShortcuts(clone);
        for (ShortcutInfoChangeListener listener : getShortcutInfoListeners(context)) {
            listener.onShortcutUpdated(shortcutInfoList);
        }
        return true;
    }

    static boolean convertUriIconToBitmapIcon(Context context, ShortcutInfoCompat info) {
        Bitmap bitmap;
        IconCompat iconCompat;
        if (info.mIcon == null) {
            return false;
        }
        int type = info.mIcon.mType;
        if (type != 6 && type != 4) {
            return true;
        }
        InputStream is = info.mIcon.getUriInputStream(context);
        if (is == null || (bitmap = BitmapFactory.decodeStream(is)) == null) {
            return false;
        }
        if (type == 6) {
            iconCompat = IconCompat.createWithAdaptiveBitmap(bitmap);
        } else {
            iconCompat = IconCompat.createWithBitmap(bitmap);
        }
        info.mIcon = iconCompat;
        return true;
    }

    static void convertUriIconsToBitmapIcons(Context context, List<ShortcutInfoCompat> shortcutInfoList) {
        for (ShortcutInfoCompat info : new ArrayList<>(shortcutInfoList)) {
            if (!convertUriIconToBitmapIcon(context, info)) {
                shortcutInfoList.remove(info);
            }
        }
    }

    public static void disableShortcuts(Context context, List<String> shortcutIds, CharSequence disabledMessage) {
        if (Build.VERSION.SDK_INT >= 25) {
            ((ShortcutManager) context.getSystemService(ShortcutManager.class)).disableShortcuts(shortcutIds, disabledMessage);
        }
        getShortcutInfoSaverInstance(context).removeShortcuts(shortcutIds);
        for (ShortcutInfoChangeListener listener : getShortcutInfoListeners(context)) {
            listener.onShortcutRemoved(shortcutIds);
        }
    }

    public static void enableShortcuts(Context context, List<ShortcutInfoCompat> shortcutInfoList) {
        List<ShortcutInfoCompat> clone = removeShortcutsExcludedFromSurface(shortcutInfoList, 1);
        if (Build.VERSION.SDK_INT >= 25) {
            ArrayList<String> shortcutIds = new ArrayList<>(shortcutInfoList.size());
            for (ShortcutInfoCompat shortcut : clone) {
                shortcutIds.add(shortcut.mId);
            }
            ((ShortcutManager) context.getSystemService(ShortcutManager.class)).enableShortcuts(shortcutIds);
        }
        getShortcutInfoSaverInstance(context).addShortcuts(clone);
        for (ShortcutInfoChangeListener listener : getShortcutInfoListeners(context)) {
            listener.onShortcutAdded(shortcutInfoList);
        }
    }

    public static void removeDynamicShortcuts(Context context, List<String> shortcutIds) {
        if (Build.VERSION.SDK_INT >= 25) {
            ((ShortcutManager) context.getSystemService(ShortcutManager.class)).removeDynamicShortcuts(shortcutIds);
        }
        getShortcutInfoSaverInstance(context).removeShortcuts(shortcutIds);
        for (ShortcutInfoChangeListener listener : getShortcutInfoListeners(context)) {
            listener.onShortcutRemoved(shortcutIds);
        }
    }

    public static void removeAllDynamicShortcuts(Context context) {
        if (Build.VERSION.SDK_INT >= 25) {
            ((ShortcutManager) context.getSystemService(ShortcutManager.class)).removeAllDynamicShortcuts();
        }
        getShortcutInfoSaverInstance(context).removeAllShortcuts();
        for (ShortcutInfoChangeListener listener : getShortcutInfoListeners(context)) {
            listener.onAllShortcutsRemoved();
        }
    }

    public static void removeLongLivedShortcuts(Context context, List<String> shortcutIds) {
        if (Build.VERSION.SDK_INT < 30) {
            removeDynamicShortcuts(context, shortcutIds);
            return;
        }
        ((ShortcutManager) context.getSystemService(ShortcutManager.class)).removeLongLivedShortcuts(shortcutIds);
        getShortcutInfoSaverInstance(context).removeShortcuts(shortcutIds);
        for (ShortcutInfoChangeListener listener : getShortcutInfoListeners(context)) {
            listener.onShortcutRemoved(shortcutIds);
        }
    }

    /*  JADX ERROR: StackOverflow in pass: MarkFinallyVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    public static boolean pushDynamicShortcut(android.content.Context r7, androidx.core.content.p000pm.ShortcutInfoCompat r8) {
        /*
            androidx.core.util.Preconditions.checkNotNull(r7)
            androidx.core.util.Preconditions.checkNotNull(r8)
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 31
            r2 = 1
            if (r0 > r1) goto L_0x0030
            boolean r0 = r8.isExcludedFromSurfaces(r2)
            if (r0 == 0) goto L_0x0030
            java.util.List r0 = getShortcutInfoListeners(r7)
            java.util.Iterator r0 = r0.iterator()
        L_0x001b:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x002f
            java.lang.Object r1 = r0.next()
            androidx.core.content.pm.ShortcutInfoChangeListener r1 = (androidx.core.content.p000pm.ShortcutInfoChangeListener) r1
            java.util.List r3 = java.util.Collections.singletonList(r8)
            r1.onShortcutAdded(r3)
            goto L_0x001b
        L_0x002f:
            return r2
        L_0x0030:
            int r0 = getMaxShortcutCountPerActivity(r7)
            r1 = 0
            if (r0 != 0) goto L_0x0038
            return r1
        L_0x0038:
            int r3 = android.os.Build.VERSION.SDK_INT
            r4 = 29
            if (r3 > r4) goto L_0x0041
            convertUriIconToBitmapIcon(r7, r8)
        L_0x0041:
            int r3 = android.os.Build.VERSION.SDK_INT
            r4 = 30
            if (r3 < r4) goto L_0x0057
            java.lang.Class<android.content.pm.ShortcutManager> r3 = android.content.pm.ShortcutManager.class
            java.lang.Object r3 = r7.getSystemService(r3)
            android.content.pm.ShortcutManager r3 = (android.content.pm.ShortcutManager) r3
            android.content.pm.ShortcutInfo r4 = r8.toShortcutInfo()
            r3.pushDynamicShortcut(r4)
            goto L_0x0094
        L_0x0057:
            int r3 = android.os.Build.VERSION.SDK_INT
            r4 = 25
            if (r3 < r4) goto L_0x0094
            java.lang.Class<android.content.pm.ShortcutManager> r3 = android.content.pm.ShortcutManager.class
            java.lang.Object r3 = r7.getSystemService(r3)
            android.content.pm.ShortcutManager r3 = (android.content.pm.ShortcutManager) r3
            boolean r4 = r3.isRateLimitingActive()
            if (r4 == 0) goto L_0x006c
            return r1
        L_0x006c:
            java.util.List r4 = r3.getDynamicShortcuts()
            int r5 = r4.size()
            if (r5 < r0) goto L_0x0085
            java.lang.String[] r5 = new java.lang.String[r2]
            java.lang.String r6 = androidx.core.content.p000pm.ShortcutManagerCompat.Api25Impl.getShortcutInfoWithLowestRank(r4)
            r5[r1] = r6
            java.util.List r5 = java.util.Arrays.asList(r5)
            r3.removeDynamicShortcuts(r5)
        L_0x0085:
            android.content.pm.ShortcutInfo[] r5 = new android.content.pm.ShortcutInfo[r2]
            android.content.pm.ShortcutInfo r6 = r8.toShortcutInfo()
            r5[r1] = r6
            java.util.List r5 = java.util.Arrays.asList(r5)
            r3.addDynamicShortcuts(r5)
        L_0x0094:
            androidx.core.content.pm.ShortcutInfoCompatSaver r3 = getShortcutInfoSaverInstance(r7)
            java.util.List r4 = r3.getShortcuts()     // Catch:{ Exception -> 0x0106, all -> 0x00e1 }
            int r5 = r4.size()     // Catch:{ Exception -> 0x0106, all -> 0x00e1 }
            if (r5 < r0) goto L_0x00b1
            java.lang.String[] r5 = new java.lang.String[r2]     // Catch:{ Exception -> 0x0106, all -> 0x00e1 }
            java.lang.String r6 = getShortcutInfoCompatWithLowestRank(r4)     // Catch:{ Exception -> 0x0106, all -> 0x00e1 }
            r5[r1] = r6     // Catch:{ Exception -> 0x0106, all -> 0x00e1 }
            java.util.List r5 = java.util.Arrays.asList(r5)     // Catch:{ Exception -> 0x0106, all -> 0x00e1 }
            r3.removeShortcuts(r5)     // Catch:{ Exception -> 0x0106, all -> 0x00e1 }
        L_0x00b1:
            androidx.core.content.pm.ShortcutInfoCompat[] r5 = new androidx.core.content.p000pm.ShortcutInfoCompat[r2]     // Catch:{ Exception -> 0x0106, all -> 0x00e1 }
            r5[r1] = r8     // Catch:{ Exception -> 0x0106, all -> 0x00e1 }
            java.util.List r5 = java.util.Arrays.asList(r5)     // Catch:{ Exception -> 0x0106, all -> 0x00e1 }
            r3.addShortcuts(r5)     // Catch:{ Exception -> 0x0106, all -> 0x00e1 }
            java.util.List r1 = getShortcutInfoListeners(r7)
            java.util.Iterator r1 = r1.iterator()
        L_0x00c5:
            boolean r5 = r1.hasNext()
            if (r5 == 0) goto L_0x00d9
            java.lang.Object r5 = r1.next()
            androidx.core.content.pm.ShortcutInfoChangeListener r5 = (androidx.core.content.p000pm.ShortcutInfoChangeListener) r5
            java.util.List r6 = java.util.Collections.singletonList(r8)
            r5.onShortcutAdded(r6)
            goto L_0x00c5
        L_0x00d9:
            java.lang.String r1 = r8.getId()
            reportShortcutUsed(r7, r1)
            return r2
        L_0x00e1:
            r1 = move-exception
            java.util.List r2 = getShortcutInfoListeners(r7)
            java.util.Iterator r2 = r2.iterator()
        L_0x00ea:
            boolean r4 = r2.hasNext()
            if (r4 == 0) goto L_0x00fe
            java.lang.Object r4 = r2.next()
            androidx.core.content.pm.ShortcutInfoChangeListener r4 = (androidx.core.content.p000pm.ShortcutInfoChangeListener) r4
            java.util.List r5 = java.util.Collections.singletonList(r8)
            r4.onShortcutAdded(r5)
            goto L_0x00ea
        L_0x00fe:
            java.lang.String r2 = r8.getId()
            reportShortcutUsed(r7, r2)
            throw r1
        L_0x0106:
            r2 = move-exception
            java.util.List r2 = getShortcutInfoListeners(r7)
            java.util.Iterator r2 = r2.iterator()
        L_0x010f:
            boolean r4 = r2.hasNext()
            if (r4 == 0) goto L_0x0123
            java.lang.Object r4 = r2.next()
            androidx.core.content.pm.ShortcutInfoChangeListener r4 = (androidx.core.content.p000pm.ShortcutInfoChangeListener) r4
            java.util.List r5 = java.util.Collections.singletonList(r8)
            r4.onShortcutAdded(r5)
            goto L_0x010f
        L_0x0123:
            java.lang.String r2 = r8.getId()
            reportShortcutUsed(r7, r2)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.content.p000pm.ShortcutManagerCompat.pushDynamicShortcut(android.content.Context, androidx.core.content.pm.ShortcutInfoCompat):boolean");
    }

    private static String getShortcutInfoCompatWithLowestRank(List<ShortcutInfoCompat> shortcuts) {
        int rank = -1;
        String target = null;
        for (ShortcutInfoCompat s : shortcuts) {
            if (s.getRank() > rank) {
                target = s.getId();
                rank = s.getRank();
            }
        }
        return target;
    }

    static void setShortcutInfoCompatSaver(ShortcutInfoCompatSaver<Void> saver) {
        sShortcutInfoCompatSaver = saver;
    }

    static void setShortcutInfoChangeListeners(List<ShortcutInfoChangeListener> listeners) {
        sShortcutInfoChangeListeners = listeners;
    }

    static List<ShortcutInfoChangeListener> getShortcutInfoChangeListeners() {
        return sShortcutInfoChangeListeners;
    }

    private static int getIconDimensionInternal(Context context, boolean isHorizontal) {
        ActivityManager am = (ActivityManager) context.getSystemService("activity");
        int iconDimensionDp = Math.max(1, am == null || am.isLowRamDevice() ? 48 : 96);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) (((float) iconDimensionDp) * ((isHorizontal ? displayMetrics.xdpi : displayMetrics.ydpi) / 160.0f));
    }

    private static ShortcutInfoCompatSaver<?> getShortcutInfoSaverInstance(Context context) {
        if (sShortcutInfoCompatSaver == null) {
            if (Build.VERSION.SDK_INT >= 23) {
                try {
                    sShortcutInfoCompatSaver = (ShortcutInfoCompatSaver) Class.forName("androidx.sharetarget.ShortcutInfoCompatSaverImpl", false, ShortcutManagerCompat.class.getClassLoader()).getMethod("getInstance", new Class[]{Context.class}).invoke((Object) null, new Object[]{context});
                } catch (Exception e) {
                }
            }
            if (sShortcutInfoCompatSaver == null) {
                sShortcutInfoCompatSaver = new ShortcutInfoCompatSaver.NoopImpl();
            }
        }
        return sShortcutInfoCompatSaver;
    }

    private static List<ShortcutInfoChangeListener> getShortcutInfoListeners(Context context) {
        Bundle metaData;
        String shortcutListenerImplName;
        if (sShortcutInfoChangeListeners == null) {
            List<ShortcutInfoChangeListener> result = new ArrayList<>();
            if (Build.VERSION.SDK_INT >= 21) {
                PackageManager packageManager = context.getPackageManager();
                Intent activityIntent = new Intent(SHORTCUT_LISTENER_INTENT_FILTER_ACTION);
                activityIntent.setPackage(context.getPackageName());
                for (ResolveInfo resolveInfo : packageManager.queryIntentActivities(activityIntent, 128)) {
                    ActivityInfo activityInfo = resolveInfo.activityInfo;
                    if (!(activityInfo == null || (metaData = activityInfo.metaData) == null || (shortcutListenerImplName = metaData.getString(SHORTCUT_LISTENER_META_DATA_KEY)) == null)) {
                        try {
                            result.add((ShortcutInfoChangeListener) Class.forName(shortcutListenerImplName, false, ShortcutManagerCompat.class.getClassLoader()).getMethod("getInstance", new Class[]{Context.class}).invoke((Object) null, new Object[]{context}));
                        } catch (Exception e) {
                        }
                    }
                }
            }
            if (sShortcutInfoChangeListeners == null) {
                sShortcutInfoChangeListeners = result;
            }
        }
        return sShortcutInfoChangeListeners;
    }

    private static List<ShortcutInfoCompat> removeShortcutsExcludedFromSurface(List<ShortcutInfoCompat> shortcuts, int surfaces) {
        Objects.requireNonNull(shortcuts);
        if (Build.VERSION.SDK_INT > 31) {
            return shortcuts;
        }
        List<ShortcutInfoCompat> clone = new ArrayList<>(shortcuts);
        for (ShortcutInfoCompat si : shortcuts) {
            if (si.isExcludedFromSurfaces(surfaces)) {
                clone.remove(si);
            }
        }
        return clone;
    }

    /* renamed from: androidx.core.content.pm.ShortcutManagerCompat$Api25Impl */
    private static class Api25Impl {
        private Api25Impl() {
        }

        static String getShortcutInfoWithLowestRank(List<ShortcutInfo> shortcuts) {
            int rank = -1;
            String target = null;
            for (ShortcutInfo s : shortcuts) {
                if (s.getRank() > rank) {
                    target = s.getId();
                    rank = s.getRank();
                }
            }
            return target;
        }
    }
}
