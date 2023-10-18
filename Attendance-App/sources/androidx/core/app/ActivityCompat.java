package androidx.core.app;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.Display;
import android.view.DragEvent;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.core.content.LocusIdCompat;
import androidx.core.p001os.BuildCompat;
import androidx.core.view.DragAndDropPermissionsCompat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ActivityCompat extends ContextCompat {
    private static PermissionCompatDelegate sDelegate;

    public interface OnRequestPermissionsResultCallback {
        void onRequestPermissionsResult(int i, String[] strArr, int[] iArr);
    }

    public interface PermissionCompatDelegate {
        boolean onActivityResult(Activity activity, int i, int i2, Intent intent);

        boolean requestPermissions(Activity activity, String[] strArr, int i);
    }

    public interface RequestPermissionsRequestCodeValidator {
        void validateRequestPermissionsRequestCode(int i);
    }

    protected ActivityCompat() {
    }

    public static void setPermissionCompatDelegate(PermissionCompatDelegate delegate) {
        sDelegate = delegate;
    }

    public static PermissionCompatDelegate getPermissionCompatDelegate() {
        return sDelegate;
    }

    @Deprecated
    public static boolean invalidateOptionsMenu(Activity activity) {
        activity.invalidateOptionsMenu();
        return true;
    }

    public static void startActivityForResult(Activity activity, Intent intent, int requestCode, Bundle options) {
        Api16Impl.startActivityForResult(activity, intent, requestCode, options);
    }

    public static void startIntentSenderForResult(Activity activity, IntentSender intent, int requestCode, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, Bundle options) throws IntentSender.SendIntentException {
        Api16Impl.startIntentSenderForResult(activity, intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags, options);
    }

    public static void finishAffinity(Activity activity) {
        Api16Impl.finishAffinity(activity);
    }

    public static void finishAfterTransition(Activity activity) {
        if (Build.VERSION.SDK_INT >= 21) {
            Api21Impl.finishAfterTransition(activity);
        } else {
            activity.finish();
        }
    }

    public static Uri getReferrer(Activity activity) {
        if (Build.VERSION.SDK_INT >= 22) {
            return Api22Impl.getReferrer(activity);
        }
        Intent intent = activity.getIntent();
        Uri referrer = (Uri) intent.getParcelableExtra("android.intent.extra.REFERRER");
        if (referrer != null) {
            return referrer;
        }
        String referrerName = intent.getStringExtra("android.intent.extra.REFERRER_NAME");
        if (referrerName != null) {
            return Uri.parse(referrerName);
        }
        return null;
    }

    public static <T extends View> T requireViewById(Activity activity, int id) {
        if (Build.VERSION.SDK_INT >= 28) {
            return (View) Api28Impl.requireViewById(activity, id);
        }
        T view = activity.findViewById(id);
        if (view != null) {
            return view;
        }
        throw new IllegalArgumentException("ID does not reference a View inside this Activity");
    }

    public static void setEnterSharedElementCallback(Activity activity, SharedElementCallback callback) {
        SharedElementCallback frameworkCallback;
        if (Build.VERSION.SDK_INT >= 21) {
            if (callback != null) {
                frameworkCallback = new SharedElementCallback21Impl(callback);
            } else {
                frameworkCallback = null;
            }
            Api21Impl.setEnterSharedElementCallback(activity, frameworkCallback);
        }
    }

    public static void setExitSharedElementCallback(Activity activity, SharedElementCallback callback) {
        SharedElementCallback frameworkCallback;
        if (Build.VERSION.SDK_INT >= 21) {
            if (callback != null) {
                frameworkCallback = new SharedElementCallback21Impl(callback);
            } else {
                frameworkCallback = null;
            }
            Api21Impl.setExitSharedElementCallback(activity, frameworkCallback);
        }
    }

    public static void postponeEnterTransition(Activity activity) {
        if (Build.VERSION.SDK_INT >= 21) {
            Api21Impl.postponeEnterTransition(activity);
        }
    }

    public static void startPostponedEnterTransition(Activity activity) {
        if (Build.VERSION.SDK_INT >= 21) {
            Api21Impl.startPostponedEnterTransition(activity);
        }
    }

    public static void requestPermissions(final Activity activity, String[] permissions, final int requestCode) {
        PermissionCompatDelegate permissionCompatDelegate = sDelegate;
        if (permissionCompatDelegate == null || !permissionCompatDelegate.requestPermissions(activity, permissions, requestCode)) {
            Set<Integer> indicesOfPermissionsToRemove = new HashSet<>();
            int i = 0;
            while (i < permissions.length) {
                if (!TextUtils.isEmpty(permissions[i])) {
                    if (!BuildCompat.isAtLeastT() && TextUtils.equals(permissions[i], "android.permission.POST_NOTIFICATIONS")) {
                        indicesOfPermissionsToRemove.add(Integer.valueOf(i));
                    }
                    i++;
                } else {
                    throw new IllegalArgumentException("Permission request for permissions " + Arrays.toString(permissions) + " must not contain null or empty values");
                }
            }
            int i2 = indicesOfPermissionsToRemove.size();
            final String[] permissionsArray = i2 > 0 ? new String[(permissions.length - i2)] : permissions;
            if (i2 > 0) {
                if (i2 != permissions.length) {
                    int modifiedIndex = 0;
                    for (int i3 = 0; i3 < permissions.length; i3++) {
                        if (!indicesOfPermissionsToRemove.contains(Integer.valueOf(i3))) {
                            permissionsArray[modifiedIndex] = permissions[i3];
                            modifiedIndex++;
                        }
                    }
                } else {
                    return;
                }
            }
            if (Build.VERSION.SDK_INT >= 23) {
                if (activity instanceof RequestPermissionsRequestCodeValidator) {
                    ((RequestPermissionsRequestCodeValidator) activity).validateRequestPermissionsRequestCode(requestCode);
                }
                Api23Impl.requestPermissions(activity, permissions, requestCode);
            } else if (activity instanceof OnRequestPermissionsResultCallback) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        int[] grantResults = new int[permissionsArray.length];
                        PackageManager packageManager = activity.getPackageManager();
                        String packageName = activity.getPackageName();
                        int permissionCount = permissionsArray.length;
                        for (int i = 0; i < permissionCount; i++) {
                            grantResults[i] = packageManager.checkPermission(permissionsArray[i], packageName);
                        }
                        ((OnRequestPermissionsResultCallback) activity).onRequestPermissionsResult(requestCode, permissionsArray, grantResults);
                    }
                });
            }
        }
    }

    public static boolean shouldShowRequestPermissionRationale(Activity activity, String permission) {
        if ((BuildCompat.isAtLeastT() || !TextUtils.equals("android.permission.POST_NOTIFICATIONS", permission)) && Build.VERSION.SDK_INT >= 23) {
            return Api23Impl.shouldShowRequestPermissionRationale(activity, permission);
        }
        return false;
    }

    public static boolean isLaunchedFromBubble(Activity activity) {
        if (Build.VERSION.SDK_INT >= 31) {
            return Api31Impl.isLaunchedFromBubble(activity);
        }
        if (Build.VERSION.SDK_INT == 30) {
            if (Api30Impl.getDisplay(activity) == null || Api30Impl.getDisplay(activity).getDisplayId() == 0) {
                return false;
            }
            return true;
        } else if (Build.VERSION.SDK_INT != 29 || activity.getWindowManager().getDefaultDisplay() == null || activity.getWindowManager().getDefaultDisplay().getDisplayId() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public static DragAndDropPermissionsCompat requestDragAndDropPermissions(Activity activity, DragEvent dragEvent) {
        return DragAndDropPermissionsCompat.request(activity, dragEvent);
    }

    public static void recreate(Activity activity) {
        if (Build.VERSION.SDK_INT >= 28) {
            activity.recreate();
        } else {
            new Handler(activity.getMainLooper()).post(new ActivityCompat$$ExternalSyntheticLambda0(activity));
        }
    }

    static /* synthetic */ void lambda$recreate$0(Activity activity) {
        if (!activity.isFinishing() && !ActivityRecreator.recreate(activity)) {
            activity.recreate();
        }
    }

    public static void setLocusContext(Activity activity, LocusIdCompat locusId, Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 30) {
            Api30Impl.setLocusContext(activity, locusId, bundle);
        }
    }

    static class SharedElementCallback21Impl extends SharedElementCallback {
        private final SharedElementCallback mCallback;

        SharedElementCallback21Impl(SharedElementCallback callback) {
            this.mCallback = callback;
        }

        public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
            this.mCallback.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
        }

        public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
            this.mCallback.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
        }

        public void onRejectSharedElements(List<View> rejectedSharedElements) {
            this.mCallback.onRejectSharedElements(rejectedSharedElements);
        }

        public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
            this.mCallback.onMapSharedElements(names, sharedElements);
        }

        public Parcelable onCaptureSharedElementSnapshot(View sharedElement, Matrix viewToGlobalMatrix, RectF screenBounds) {
            return this.mCallback.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds);
        }

        public View onCreateSnapshotView(Context context, Parcelable snapshot) {
            return this.mCallback.onCreateSnapshotView(context, snapshot);
        }

        public void onSharedElementsArrived(List<String> sharedElementNames, List<View> sharedElements, SharedElementCallback.OnSharedElementsReadyListener listener) {
            this.mCallback.onSharedElementsArrived(sharedElementNames, sharedElements, new C0679xa7ca8857(listener));
        }
    }

    static class Api30Impl {
        private Api30Impl() {
        }

        static void setLocusContext(Activity activity, LocusIdCompat locusId, Bundle bundle) {
            activity.setLocusContext(locusId == null ? null : locusId.toLocusId(), bundle);
        }

        static Display getDisplay(ContextWrapper contextWrapper) {
            return contextWrapper.getDisplay();
        }
    }

    static class Api31Impl {
        private Api31Impl() {
        }

        static boolean isLaunchedFromBubble(Activity activity) {
            return activity.isLaunchedFromBubble();
        }
    }

    static class Api16Impl {
        private Api16Impl() {
        }

        static void startActivityForResult(Activity activity, Intent intent, int requestCode, Bundle options) {
            activity.startActivityForResult(intent, requestCode, options);
        }

        static void startIntentSenderForResult(Activity activity, IntentSender intent, int requestCode, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, Bundle options) throws IntentSender.SendIntentException {
            activity.startIntentSenderForResult(intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags, options);
        }

        static void finishAffinity(Activity activity) {
            activity.finishAffinity();
        }
    }

    static class Api21Impl {
        private Api21Impl() {
        }

        static void finishAfterTransition(Activity activity) {
            activity.finishAfterTransition();
        }

        static void setEnterSharedElementCallback(Activity activity, SharedElementCallback callback) {
            activity.setEnterSharedElementCallback(callback);
        }

        static void setExitSharedElementCallback(Activity activity, SharedElementCallback callback) {
            activity.setExitSharedElementCallback(callback);
        }

        static void postponeEnterTransition(Activity activity) {
            activity.postponeEnterTransition();
        }

        static void startPostponedEnterTransition(Activity activity) {
            activity.startPostponedEnterTransition();
        }
    }

    static class Api22Impl {
        private Api22Impl() {
        }

        static Uri getReferrer(Activity activity) {
            return activity.getReferrer();
        }
    }

    static class Api28Impl {
        private Api28Impl() {
        }

        static <T> T requireViewById(Activity activity, int id) {
            return activity.requireViewById(id);
        }
    }

    static class Api23Impl {
        private Api23Impl() {
        }

        static void requestPermissions(Activity activity, String[] permissions, int requestCode) {
            activity.requestPermissions(permissions, requestCode);
        }

        static boolean shouldShowRequestPermissionRationale(Activity activity, String permission) {
            return activity.shouldShowRequestPermissionRationale(permission);
        }

        /* access modifiers changed from: package-private */
        public static void onSharedElementsReady(Object onSharedElementsReadyListener) {
            ((SharedElementCallback.OnSharedElementsReadyListener) onSharedElementsReadyListener).onSharedElementsReady();
        }
    }
}
