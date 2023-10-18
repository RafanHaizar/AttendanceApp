package androidx.core.content;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.CancellationSignal;

public final class ContentResolverCompat {
    private ContentResolverCompat() {
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v0, resolved type: android.os.CancellationSignal} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v2, resolved type: android.os.CancellationSignal} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v5, resolved type: android.os.CancellationSignal} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.database.Cursor query(android.content.ContentResolver r8, android.net.Uri r9, java.lang.String[] r10, java.lang.String r11, java.lang.String[] r12, java.lang.String r13, androidx.core.p001os.CancellationSignal r14) {
        /*
            if (r14 == 0) goto L_0x000a
            java.lang.Object r0 = r14.getCancellationSignalObject()     // Catch:{ Exception -> 0x0008 }
            goto L_0x000b
        L_0x0008:
            r0 = move-exception
            goto L_0x0019
        L_0x000a:
            r0 = 0
        L_0x000b:
            r7 = r0
            android.os.CancellationSignal r7 = (android.os.CancellationSignal) r7     // Catch:{ Exception -> 0x0008 }
            r1 = r8
            r2 = r9
            r3 = r10
            r4 = r11
            r5 = r12
            r6 = r13
            android.database.Cursor r0 = androidx.core.content.ContentResolverCompat.Api16Impl.query(r1, r2, r3, r4, r5, r6, r7)     // Catch:{ Exception -> 0x0008 }
            return r0
        L_0x0019:
            boolean r1 = r0 instanceof android.os.OperationCanceledException
            if (r1 == 0) goto L_0x0023
            androidx.core.os.OperationCanceledException r1 = new androidx.core.os.OperationCanceledException
            r1.<init>()
            throw r1
        L_0x0023:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.content.ContentResolverCompat.query(android.content.ContentResolver, android.net.Uri, java.lang.String[], java.lang.String, java.lang.String[], java.lang.String, androidx.core.os.CancellationSignal):android.database.Cursor");
    }

    static class Api16Impl {
        private Api16Impl() {
        }

        static Cursor query(ContentResolver contentResolver, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, CancellationSignal cancellationSignal) {
            return contentResolver.query(uri, projection, selection, selectionArgs, sortOrder, cancellationSignal);
        }
    }
}
