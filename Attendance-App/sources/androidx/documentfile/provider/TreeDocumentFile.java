package androidx.documentfile.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.util.Log;
import java.util.ArrayList;

class TreeDocumentFile extends DocumentFile {
    private Context mContext;
    private Uri mUri;

    TreeDocumentFile(DocumentFile parent, Context context, Uri uri) {
        super(parent);
        this.mContext = context;
        this.mUri = uri;
    }

    public DocumentFile createFile(String mimeType, String displayName) {
        Uri result = createFile(this.mContext, this.mUri, mimeType, displayName);
        if (result != null) {
            return new TreeDocumentFile(this, this.mContext, result);
        }
        return null;
    }

    private static Uri createFile(Context context, Uri self, String mimeType, String displayName) {
        try {
            return DocumentsContract.createDocument(context.getContentResolver(), self, mimeType, displayName);
        } catch (Exception e) {
            return null;
        }
    }

    public DocumentFile createDirectory(String displayName) {
        Uri result = createFile(this.mContext, this.mUri, "vnd.android.document/directory", displayName);
        if (result != null) {
            return new TreeDocumentFile(this, this.mContext, result);
        }
        return null;
    }

    public Uri getUri() {
        return this.mUri;
    }

    public String getName() {
        return DocumentsContractApi19.getName(this.mContext, this.mUri);
    }

    public String getType() {
        return DocumentsContractApi19.getType(this.mContext, this.mUri);
    }

    public boolean isDirectory() {
        return DocumentsContractApi19.isDirectory(this.mContext, this.mUri);
    }

    public boolean isFile() {
        return DocumentsContractApi19.isFile(this.mContext, this.mUri);
    }

    public boolean isVirtual() {
        return DocumentsContractApi19.isVirtual(this.mContext, this.mUri);
    }

    public long lastModified() {
        return DocumentsContractApi19.lastModified(this.mContext, this.mUri);
    }

    public long length() {
        return DocumentsContractApi19.length(this.mContext, this.mUri);
    }

    public boolean canRead() {
        return DocumentsContractApi19.canRead(this.mContext, this.mUri);
    }

    public boolean canWrite() {
        return DocumentsContractApi19.canWrite(this.mContext, this.mUri);
    }

    public boolean delete() {
        try {
            return DocumentsContract.deleteDocument(this.mContext.getContentResolver(), this.mUri);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean exists() {
        return DocumentsContractApi19.exists(this.mContext, this.mUri);
    }

    public DocumentFile[] listFiles() {
        ContentResolver resolver = this.mContext.getContentResolver();
        Uri uri = this.mUri;
        Uri childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(uri, DocumentsContract.getDocumentId(uri));
        ArrayList arrayList = new ArrayList();
        Cursor c = null;
        try {
            c = resolver.query(childrenUri, new String[]{"document_id"}, (String) null, (String[]) null, (String) null);
            while (c.moveToNext()) {
                arrayList.add(DocumentsContract.buildDocumentUriUsingTree(this.mUri, c.getString(0)));
            }
        } catch (Exception e) {
            Log.w("DocumentFile", "Failed query: " + e);
        } catch (Throwable th) {
            closeQuietly((AutoCloseable) null);
            throw th;
        }
        closeQuietly(c);
        Uri[] result = (Uri[]) arrayList.toArray(new Uri[arrayList.size()]);
        DocumentFile[] resultFiles = new DocumentFile[result.length];
        for (int i = 0; i < result.length; i++) {
            resultFiles[i] = new TreeDocumentFile(this, this.mContext, result[i]);
        }
        return resultFiles;
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

    public boolean renameTo(String displayName) {
        try {
            Uri result = DocumentsContract.renameDocument(this.mContext.getContentResolver(), this.mUri, displayName);
            if (result == null) {
                return false;
            }
            this.mUri = result;
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
