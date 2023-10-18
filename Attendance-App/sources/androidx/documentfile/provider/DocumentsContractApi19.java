package androidx.documentfile.provider;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.text.TextUtils;
import android.util.Log;
import com.itextpdf.forms.xfdf.XfdfConstants;

class DocumentsContractApi19 {
    private static final int FLAG_VIRTUAL_DOCUMENT = 512;
    private static final String TAG = "DocumentFile";

    public static boolean isVirtual(Context context, Uri self) {
        if (DocumentsContract.isDocumentUri(context, self) && (getFlags(context, self) & 512) != 0) {
            return true;
        }
        return false;
    }

    public static String getName(Context context, Uri self) {
        return queryForString(context, self, "_display_name", (String) null);
    }

    private static String getRawType(Context context, Uri self) {
        return queryForString(context, self, "mime_type", (String) null);
    }

    public static String getType(Context context, Uri self) {
        String rawType = getRawType(context, self);
        if ("vnd.android.document/directory".equals(rawType)) {
            return null;
        }
        return rawType;
    }

    public static long getFlags(Context context, Uri self) {
        return queryForLong(context, self, XfdfConstants.FLAGS, 0);
    }

    public static boolean isDirectory(Context context, Uri self) {
        return "vnd.android.document/directory".equals(getRawType(context, self));
    }

    public static boolean isFile(Context context, Uri self) {
        String type = getRawType(context, self);
        if ("vnd.android.document/directory".equals(type) || TextUtils.isEmpty(type)) {
            return false;
        }
        return true;
    }

    public static long lastModified(Context context, Uri self) {
        return queryForLong(context, self, "last_modified", 0);
    }

    public static long length(Context context, Uri self) {
        return queryForLong(context, self, "_size", 0);
    }

    public static boolean canRead(Context context, Uri self) {
        return context.checkCallingOrSelfUriPermission(self, 1) == 0 && !TextUtils.isEmpty(getRawType(context, self));
    }

    public static boolean canWrite(Context context, Uri self) {
        if (context.checkCallingOrSelfUriPermission(self, 2) != 0) {
            return false;
        }
        String type = getRawType(context, self);
        int flags = queryForInt(context, self, XfdfConstants.FLAGS, 0);
        if (TextUtils.isEmpty(type)) {
            return false;
        }
        if ((flags & 4) != 0) {
            return true;
        }
        if ("vnd.android.document/directory".equals(type) && (flags & 8) != 0) {
            return true;
        }
        if (TextUtils.isEmpty(type) || (flags & 2) == 0) {
            return false;
        }
        return true;
    }

    public static boolean exists(Context context, Uri self) {
        Cursor c = null;
        boolean z = false;
        try {
            c = context.getContentResolver().query(self, new String[]{"document_id"}, (String) null, (String[]) null, (String) null);
            if (c.getCount() > 0) {
                z = true;
            }
            return z;
        } catch (Exception e) {
            Log.w(TAG, "Failed query: " + e);
            return false;
        } finally {
            closeQuietly(c);
        }
    }

    private static String queryForString(Context context, Uri self, String column, String defaultValue) {
        Cursor c = null;
        try {
            c = context.getContentResolver().query(self, new String[]{column}, (String) null, (String[]) null, (String) null);
            if (c.moveToFirst() && !c.isNull(0)) {
                return c.getString(0);
            }
            closeQuietly(c);
            return defaultValue;
        } catch (Exception e) {
            Log.w(TAG, "Failed query: " + e);
            return defaultValue;
        } finally {
            closeQuietly(c);
        }
    }

    private static int queryForInt(Context context, Uri self, String column, int defaultValue) {
        return (int) queryForLong(context, self, column, (long) defaultValue);
    }

    private static long queryForLong(Context context, Uri self, String column, long defaultValue) {
        Cursor c = null;
        try {
            c = context.getContentResolver().query(self, new String[]{column}, (String) null, (String[]) null, (String) null);
            if (c.moveToFirst() && !c.isNull(0)) {
                return c.getLong(0);
            }
            closeQuietly(c);
            return defaultValue;
        } catch (Exception e) {
            Log.w(TAG, "Failed query: " + e);
            return defaultValue;
        } finally {
            closeQuietly(c);
        }
    }

    private static void closeQuietly(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception e) {
            }
        }
    }

    private DocumentsContractApi19() {
    }
}
