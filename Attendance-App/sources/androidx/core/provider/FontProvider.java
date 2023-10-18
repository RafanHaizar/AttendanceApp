package androidx.core.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.CancellationSignal;
import androidx.core.content.res.FontResourcesParserCompat;
import androidx.core.provider.FontsContractCompat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class FontProvider {
    private static final Comparator<byte[]> sByteArrayComparator = new FontProvider$$ExternalSyntheticLambda0();

    private FontProvider() {
    }

    static FontsContractCompat.FontFamilyResult getFontFamilyResult(Context context, FontRequest request, CancellationSignal cancellationSignal) throws PackageManager.NameNotFoundException {
        ProviderInfo providerInfo = getProvider(context.getPackageManager(), request, context.getResources());
        if (providerInfo == null) {
            return FontsContractCompat.FontFamilyResult.create(1, (FontsContractCompat.FontInfo[]) null);
        }
        return FontsContractCompat.FontFamilyResult.create(0, query(context, request, providerInfo.authority, cancellationSignal));
    }

    static ProviderInfo getProvider(PackageManager packageManager, FontRequest request, Resources resources) throws PackageManager.NameNotFoundException {
        String providerAuthority = request.getProviderAuthority();
        ProviderInfo info = packageManager.resolveContentProvider(providerAuthority, 0);
        if (info == null) {
            throw new PackageManager.NameNotFoundException("No package found for authority: " + providerAuthority);
        } else if (info.packageName.equals(request.getProviderPackage())) {
            List<byte[]> signatures = convertToByteArrayList(packageManager.getPackageInfo(info.packageName, 64).signatures);
            Collections.sort(signatures, sByteArrayComparator);
            List<List<byte[]>> requestCertificatesList = getCertificates(request, resources);
            for (int i = 0; i < requestCertificatesList.size(); i++) {
                List<byte[]> requestSignatures = new ArrayList<>(requestCertificatesList.get(i));
                Collections.sort(requestSignatures, sByteArrayComparator);
                if (equalsByteArrayList(signatures, requestSignatures)) {
                    return info;
                }
            }
            return null;
        } else {
            throw new PackageManager.NameNotFoundException("Found content provider " + providerAuthority + ", but package was not " + request.getProviderPackage());
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:52:0x011e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static androidx.core.provider.FontsContractCompat.FontInfo[] query(android.content.Context r23, androidx.core.provider.FontRequest r24, java.lang.String r25, android.os.CancellationSignal r26) {
        /*
            r1 = r25
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r2 = r0
            android.net.Uri$Builder r0 = new android.net.Uri$Builder
            r0.<init>()
            java.lang.String r3 = "content"
            android.net.Uri$Builder r0 = r0.scheme(r3)
            android.net.Uri$Builder r0 = r0.authority(r1)
            android.net.Uri r11 = r0.build()
            android.net.Uri$Builder r0 = new android.net.Uri$Builder
            r0.<init>()
            android.net.Uri$Builder r0 = r0.scheme(r3)
            android.net.Uri$Builder r0 = r0.authority(r1)
            java.lang.String r3 = "file"
            android.net.Uri$Builder r0 = r0.appendPath(r3)
            android.net.Uri r3 = r0.build()
            r12 = 0
            java.lang.String r4 = "_id"
            java.lang.String r5 = "file_id"
            java.lang.String r6 = "font_ttc_index"
            java.lang.String r7 = "font_variation_settings"
            java.lang.String r8 = "font_weight"
            java.lang.String r9 = "font_italic"
            java.lang.String r10 = "result_code"
            java.lang.String[] r6 = new java.lang.String[]{r4, r5, r6, r7, r8, r9, r10}     // Catch:{ all -> 0x0119 }
            android.content.ContentResolver r4 = r23.getContentResolver()     // Catch:{ all -> 0x0119 }
            java.lang.String r7 = "query = ?"
            r0 = 1
            java.lang.String[] r8 = new java.lang.String[r0]     // Catch:{ all -> 0x0119 }
            java.lang.String r5 = r24.getQuery()     // Catch:{ all -> 0x0119 }
            r13 = 0
            r8[r13] = r5     // Catch:{ all -> 0x0119 }
            r9 = 0
            r5 = r11
            r10 = r26
            android.database.Cursor r5 = androidx.core.provider.FontProvider.Api16Impl.query(r4, r5, r6, r7, r8, r9, r10)     // Catch:{ all -> 0x0119 }
            r12 = r5
            if (r12 == 0) goto L_0x0106
            int r5 = r12.getCount()     // Catch:{ all -> 0x0119 }
            if (r5 <= 0) goto L_0x0106
            java.lang.String r5 = "result_code"
            int r5 = r12.getColumnIndex(r5)     // Catch:{ all -> 0x0119 }
            java.util.ArrayList r7 = new java.util.ArrayList     // Catch:{ all -> 0x0119 }
            r7.<init>()     // Catch:{ all -> 0x0119 }
            r2 = r7
            java.lang.String r7 = "_id"
            int r7 = r12.getColumnIndex(r7)     // Catch:{ all -> 0x0119 }
            java.lang.String r8 = "file_id"
            int r8 = r12.getColumnIndex(r8)     // Catch:{ all -> 0x0119 }
            java.lang.String r9 = "font_ttc_index"
            int r9 = r12.getColumnIndex(r9)     // Catch:{ all -> 0x0119 }
            java.lang.String r10 = "font_weight"
            int r10 = r12.getColumnIndex(r10)     // Catch:{ all -> 0x0119 }
            java.lang.String r14 = "font_italic"
            int r14 = r12.getColumnIndex(r14)     // Catch:{ all -> 0x0119 }
        L_0x0094:
            boolean r15 = r12.moveToNext()     // Catch:{ all -> 0x0119 }
            if (r15 == 0) goto L_0x0101
            r15 = -1
            if (r5 == r15) goto L_0x00a7
            int r16 = r12.getInt(r5)     // Catch:{ all -> 0x00a2 }
            goto L_0x00a9
        L_0x00a2:
            r0 = move-exception
            r17 = r3
            goto L_0x011c
        L_0x00a7:
            r16 = 0
        L_0x00a9:
            r17 = r16
            if (r9 == r15) goto L_0x00b2
            int r16 = r12.getInt(r9)     // Catch:{ all -> 0x00a2 }
            goto L_0x00b4
        L_0x00b2:
            r16 = 0
        L_0x00b4:
            r18 = r16
            if (r8 != r15) goto L_0x00c7
            long r19 = r12.getLong(r7)     // Catch:{ all -> 0x00a2 }
            r21 = r19
            r0 = r21
            android.net.Uri r19 = android.content.ContentUris.withAppendedId(r11, r0)     // Catch:{ all -> 0x00a2 }
            r0 = r19
            goto L_0x00d1
        L_0x00c7:
            long r0 = r12.getLong(r8)     // Catch:{ all -> 0x0119 }
            android.net.Uri r19 = android.content.ContentUris.withAppendedId(r3, r0)     // Catch:{ all -> 0x0119 }
            r0 = r19
        L_0x00d1:
            if (r10 == r15) goto L_0x00d8
            int r1 = r12.getInt(r10)     // Catch:{ all -> 0x00a2 }
            goto L_0x00da
        L_0x00d8:
            r1 = 400(0x190, float:5.6E-43)
        L_0x00da:
            if (r14 == r15) goto L_0x00e5
            int r15 = r12.getInt(r14)     // Catch:{ all -> 0x00a2 }
            r13 = 1
            if (r15 != r13) goto L_0x00e6
            r15 = 1
            goto L_0x00e7
        L_0x00e5:
            r13 = 1
        L_0x00e6:
            r15 = 0
        L_0x00e7:
            r13 = r17
            r17 = r3
            r3 = r18
            r18 = r4
            androidx.core.provider.FontsContractCompat$FontInfo r4 = androidx.core.provider.FontsContractCompat.FontInfo.create(r0, r3, r1, r15, r13)     // Catch:{ all -> 0x00ff }
            r2.add(r4)     // Catch:{ all -> 0x00ff }
            r1 = r25
            r3 = r17
            r4 = r18
            r0 = 1
            r13 = 0
            goto L_0x0094
        L_0x00ff:
            r0 = move-exception
            goto L_0x011c
        L_0x0101:
            r17 = r3
            r18 = r4
            goto L_0x010a
        L_0x0106:
            r17 = r3
            r18 = r4
        L_0x010a:
            if (r12 == 0) goto L_0x010f
            r12.close()
        L_0x010f:
            r0 = 0
            androidx.core.provider.FontsContractCompat$FontInfo[] r0 = new androidx.core.provider.FontsContractCompat.FontInfo[r0]
            java.lang.Object[] r0 = r2.toArray(r0)
            androidx.core.provider.FontsContractCompat$FontInfo[] r0 = (androidx.core.provider.FontsContractCompat.FontInfo[]) r0
            return r0
        L_0x0119:
            r0 = move-exception
            r17 = r3
        L_0x011c:
            if (r12 == 0) goto L_0x0121
            r12.close()
        L_0x0121:
            goto L_0x0123
        L_0x0122:
            throw r0
        L_0x0123:
            goto L_0x0122
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.provider.FontProvider.query(android.content.Context, androidx.core.provider.FontRequest, java.lang.String, android.os.CancellationSignal):androidx.core.provider.FontsContractCompat$FontInfo[]");
    }

    private static List<List<byte[]>> getCertificates(FontRequest request, Resources resources) {
        if (request.getCertificates() != null) {
            return request.getCertificates();
        }
        return FontResourcesParserCompat.readCerts(resources, request.getCertificatesArrayResId());
    }

    static /* synthetic */ int lambda$static$0(byte[] l, byte[] r) {
        if (l.length != r.length) {
            return l.length - r.length;
        }
        for (int i = 0; i < l.length; i++) {
            if (l[i] != r[i]) {
                return l[i] - r[i];
            }
        }
        return 0;
    }

    private static boolean equalsByteArrayList(List<byte[]> signatures, List<byte[]> requestSignatures) {
        if (signatures.size() != requestSignatures.size()) {
            return false;
        }
        for (int i = 0; i < signatures.size(); i++) {
            if (!Arrays.equals(signatures.get(i), requestSignatures.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static List<byte[]> convertToByteArrayList(Signature[] signatures) {
        List<byte[]> shaList = new ArrayList<>();
        for (Signature signature : signatures) {
            shaList.add(signature.toByteArray());
        }
        return shaList;
    }

    static class Api16Impl {
        private Api16Impl() {
        }

        static Cursor query(ContentResolver contentResolver, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder, Object cancellationSignal) {
            return contentResolver.query(uri, projection, selection, selectionArgs, sortOrder, (CancellationSignal) cancellationSignal);
        }
    }
}
