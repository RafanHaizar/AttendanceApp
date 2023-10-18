package androidx.core.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.xmlpull.v1.XmlPullParserException;

public class FileProvider extends ContentProvider {
    private static final String ATTR_NAME = "name";
    private static final String ATTR_PATH = "path";
    private static final String[] COLUMNS = {"_display_name", "_size"};
    private static final File DEVICE_ROOT = new File("/");
    private static final String DISPLAYNAME_FIELD = "displayName";
    private static final String META_DATA_FILE_PROVIDER_PATHS = "android.support.FILE_PROVIDER_PATHS";
    private static final String TAG_CACHE_PATH = "cache-path";
    private static final String TAG_EXTERNAL = "external-path";
    private static final String TAG_EXTERNAL_CACHE = "external-cache-path";
    private static final String TAG_EXTERNAL_FILES = "external-files-path";
    private static final String TAG_EXTERNAL_MEDIA = "external-media-path";
    private static final String TAG_FILES_PATH = "files-path";
    private static final String TAG_ROOT_PATH = "root-path";
    private static final HashMap<String, PathStrategy> sCache = new HashMap<>();
    private int mResourceId;
    private PathStrategy mStrategy;

    interface PathStrategy {
        File getFileForUri(Uri uri);

        Uri getUriForFile(File file);
    }

    public FileProvider() {
        this.mResourceId = 0;
    }

    protected FileProvider(int resourceId) {
        this.mResourceId = resourceId;
    }

    public boolean onCreate() {
        return true;
    }

    public void attachInfo(Context context, ProviderInfo info) {
        super.attachInfo(context, info);
        if (info.exported) {
            throw new SecurityException("Provider must not be exported");
        } else if (info.grantUriPermissions) {
            String authority = info.authority.split(";")[0];
            HashMap<String, PathStrategy> hashMap = sCache;
            synchronized (hashMap) {
                hashMap.remove(authority);
            }
            this.mStrategy = getPathStrategy(context, authority, this.mResourceId);
        } else {
            throw new SecurityException("Provider must grant uri permissions");
        }
    }

    public static Uri getUriForFile(Context context, String authority, File file) {
        return getPathStrategy(context, authority, 0).getUriForFile(file);
    }

    public static Uri getUriForFile(Context context, String authority, File file, String displayName) {
        return getUriForFile(context, authority, file).buildUpon().appendQueryParameter(DISPLAYNAME_FIELD, displayName).build();
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String[] projection2;
        Uri uri2 = uri;
        File file = this.mStrategy.getFileForUri(uri);
        String displayName = uri.getQueryParameter(DISPLAYNAME_FIELD);
        if (projection == null) {
            projection2 = COLUMNS;
        } else {
            projection2 = projection;
        }
        String[] cols = new String[projection2.length];
        Object[] values = new Object[projection2.length];
        int i = 0;
        for (String col : projection2) {
            if ("_display_name".equals(col)) {
                cols[i] = "_display_name";
                int i2 = i + 1;
                values[i] = displayName == null ? file.getName() : displayName;
                i = i2;
            } else if ("_size".equals(col)) {
                cols[i] = "_size";
                values[i] = Long.valueOf(file.length());
                i++;
            }
        }
        String[] cols2 = copyOf(cols, i);
        Object[] values2 = copyOf(values, i);
        MatrixCursor cursor = new MatrixCursor(cols2, 1);
        cursor.addRow(values2);
        return cursor;
    }

    public String getType(Uri uri) {
        String mime;
        File file = this.mStrategy.getFileForUri(uri);
        int lastDot = file.getName().lastIndexOf(46);
        if (lastDot < 0 || (mime = MimeTypeMap.getSingleton().getMimeTypeFromExtension(file.getName().substring(lastDot + 1))) == null) {
            return "application/octet-stream";
        }
        return mime;
    }

    public Uri insert(Uri uri, ContentValues values) {
        throw new UnsupportedOperationException("No external inserts");
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("No external updates");
    }

    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return this.mStrategy.getFileForUri(uri).delete() ? 1 : 0;
    }

    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
        return ParcelFileDescriptor.open(this.mStrategy.getFileForUri(uri), modeToMode(mode));
    }

    private static PathStrategy getPathStrategy(Context context, String authority, int resourceId) {
        PathStrategy strat;
        HashMap<String, PathStrategy> hashMap = sCache;
        synchronized (hashMap) {
            strat = hashMap.get(authority);
            if (strat == null) {
                try {
                    strat = parsePathStrategy(context, authority, resourceId);
                    hashMap.put(authority, strat);
                } catch (IOException e) {
                    throw new IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", e);
                } catch (XmlPullParserException e2) {
                    throw new IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", e2);
                }
            }
        }
        return strat;
    }

    static XmlResourceParser getFileProviderPathsMetaData(Context context, String authority, ProviderInfo info, int resourceId) {
        if (info != null) {
            if (info.metaData == null && resourceId != 0) {
                info.metaData = new Bundle(1);
                info.metaData.putInt(META_DATA_FILE_PROVIDER_PATHS, resourceId);
            }
            XmlResourceParser in = info.loadXmlMetaData(context.getPackageManager(), META_DATA_FILE_PROVIDER_PATHS);
            if (in != null) {
                return in;
            }
            throw new IllegalArgumentException("Missing android.support.FILE_PROVIDER_PATHS meta-data");
        }
        throw new IllegalArgumentException("Couldn't find meta-data for provider with authority " + authority);
    }

    private static PathStrategy parsePathStrategy(Context context, String authority, int resourceId) throws IOException, XmlPullParserException {
        SimplePathStrategy strat = new SimplePathStrategy(authority);
        XmlResourceParser in = getFileProviderPathsMetaData(context, authority, context.getPackageManager().resolveContentProvider(authority, 128), resourceId);
        while (true) {
            int next = in.next();
            int type = next;
            if (next == 1) {
                return strat;
            }
            if (type == 2) {
                String tag = in.getName();
                String name = in.getAttributeValue((String) null, "name");
                String path = in.getAttributeValue((String) null, "path");
                File target = null;
                if (TAG_ROOT_PATH.equals(tag)) {
                    target = DEVICE_ROOT;
                } else if (TAG_FILES_PATH.equals(tag)) {
                    target = context.getFilesDir();
                } else if (TAG_CACHE_PATH.equals(tag)) {
                    target = context.getCacheDir();
                } else if (TAG_EXTERNAL.equals(tag)) {
                    target = Environment.getExternalStorageDirectory();
                } else if (TAG_EXTERNAL_FILES.equals(tag)) {
                    File[] externalFilesDirs = ContextCompat.getExternalFilesDirs(context, (String) null);
                    if (externalFilesDirs.length > 0) {
                        target = externalFilesDirs[0];
                    }
                } else if (TAG_EXTERNAL_CACHE.equals(tag)) {
                    File[] externalCacheDirs = ContextCompat.getExternalCacheDirs(context);
                    if (externalCacheDirs.length > 0) {
                        target = externalCacheDirs[0];
                    }
                } else if (Build.VERSION.SDK_INT >= 21 && TAG_EXTERNAL_MEDIA.equals(tag)) {
                    File[] externalMediaDirs = Api21Impl.getExternalMediaDirs(context);
                    if (externalMediaDirs.length > 0) {
                        target = externalMediaDirs[0];
                    }
                }
                if (target != null) {
                    strat.addRoot(name, buildPath(target, path));
                }
            }
        }
    }

    static class SimplePathStrategy implements PathStrategy {
        private final String mAuthority;
        private final HashMap<String, File> mRoots = new HashMap<>();

        SimplePathStrategy(String authority) {
            this.mAuthority = authority;
        }

        /* access modifiers changed from: package-private */
        public void addRoot(String name, File root) {
            if (!TextUtils.isEmpty(name)) {
                try {
                    this.mRoots.put(name, root.getCanonicalFile());
                } catch (IOException e) {
                    throw new IllegalArgumentException("Failed to resolve canonical path for " + root, e);
                }
            } else {
                throw new IllegalArgumentException("Name must not be empty");
            }
        }

        public Uri getUriForFile(File file) {
            String path;
            try {
                String path2 = file.getCanonicalPath();
                Map.Entry<String, File> mostSpecific = null;
                for (Map.Entry<String, File> root : this.mRoots.entrySet()) {
                    String rootPath = root.getValue().getPath();
                    if (path2.startsWith(rootPath) && (mostSpecific == null || rootPath.length() > mostSpecific.getValue().getPath().length())) {
                        mostSpecific = root;
                    }
                }
                if (mostSpecific != null) {
                    String rootPath2 = mostSpecific.getValue().getPath();
                    if (rootPath2.endsWith("/")) {
                        path = path2.substring(rootPath2.length());
                    } else {
                        path = path2.substring(rootPath2.length() + 1);
                    }
                    return new Uri.Builder().scheme("content").authority(this.mAuthority).encodedPath(Uri.encode(mostSpecific.getKey()) + '/' + Uri.encode(path, "/")).build();
                }
                throw new IllegalArgumentException("Failed to find configured root that contains " + path2);
            } catch (IOException e) {
                throw new IllegalArgumentException("Failed to resolve canonical path for " + file);
            }
        }

        public File getFileForUri(Uri uri) {
            String path = uri.getEncodedPath();
            int splitIndex = path.indexOf(47, 1);
            String tag = Uri.decode(path.substring(1, splitIndex));
            String path2 = Uri.decode(path.substring(splitIndex + 1));
            File root = this.mRoots.get(tag);
            if (root != null) {
                File file = new File(root, path2);
                try {
                    File file2 = file.getCanonicalFile();
                    if (file2.getPath().startsWith(root.getPath())) {
                        return file2;
                    }
                    throw new SecurityException("Resolved path jumped beyond configured root");
                } catch (IOException e) {
                    throw new IllegalArgumentException("Failed to resolve canonical path for " + file);
                }
            } else {
                throw new IllegalArgumentException("Unable to find configured root for " + uri);
            }
        }
    }

    private static int modeToMode(String mode) {
        if ("r".equals(mode)) {
            return 268435456;
        }
        if ("w".equals(mode) || "wt".equals(mode)) {
            return 738197504;
        }
        if ("wa".equals(mode)) {
            return 704643072;
        }
        if ("rw".equals(mode)) {
            return 939524096;
        }
        if ("rwt".equals(mode)) {
            return 1006632960;
        }
        throw new IllegalArgumentException("Invalid mode: " + mode);
    }

    private static File buildPath(File base, String... segments) {
        File cur = base;
        for (String segment : segments) {
            if (segment != null) {
                cur = new File(cur, segment);
            }
        }
        return cur;
    }

    private static String[] copyOf(String[] original, int newLength) {
        String[] result = new String[newLength];
        System.arraycopy(original, 0, result, 0, newLength);
        return result;
    }

    private static Object[] copyOf(Object[] original, int newLength) {
        Object[] result = new Object[newLength];
        System.arraycopy(original, 0, result, 0, newLength);
        return result;
    }

    static class Api21Impl {
        private Api21Impl() {
        }

        static File[] getExternalMediaDirs(Context context) {
            return context.getExternalMediaDirs();
        }
    }
}
