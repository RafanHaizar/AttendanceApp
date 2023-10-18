package androidx.core.view;

import android.app.Activity;
import android.os.Build;
import android.view.DragAndDropPermissions;
import android.view.DragEvent;

public final class DragAndDropPermissionsCompat {
    private final DragAndDropPermissions mDragAndDropPermissions;

    private DragAndDropPermissionsCompat(DragAndDropPermissions dragAndDropPermissions) {
        this.mDragAndDropPermissions = dragAndDropPermissions;
    }

    public static DragAndDropPermissionsCompat request(Activity activity, DragEvent dragEvent) {
        DragAndDropPermissions dragAndDropPermissions;
        if (Build.VERSION.SDK_INT < 24 || (dragAndDropPermissions = Api24Impl.requestDragAndDropPermissions(activity, dragEvent)) == null) {
            return null;
        }
        return new DragAndDropPermissionsCompat(dragAndDropPermissions);
    }

    public void release() {
        if (Build.VERSION.SDK_INT >= 24) {
            Api24Impl.release(this.mDragAndDropPermissions);
        }
    }

    static class Api24Impl {
        private Api24Impl() {
        }

        static DragAndDropPermissions requestDragAndDropPermissions(Activity activity, DragEvent event) {
            return activity.requestDragAndDropPermissions(event);
        }

        static void release(DragAndDropPermissions dragAndDropPermissions) {
            dragAndDropPermissions.release();
        }
    }
}
