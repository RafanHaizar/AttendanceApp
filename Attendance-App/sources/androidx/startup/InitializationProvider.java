package androidx.startup;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class InitializationProvider extends ContentProvider {
    public final boolean onCreate() {
        Context context = getContext();
        if (context == null) {
            throw new StartupException("Context cannot be null");
        } else if (context.getApplicationContext() != null) {
            AppInitializer.getInstance(context).discoverAndInitialize();
            return true;
        } else {
            StartupLogger.m218w("Deferring initialization because `applicationContext` is null.");
            return true;
        }
    }

    public final Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        throw new IllegalStateException("Not allowed.");
    }

    public final String getType(Uri uri) {
        throw new IllegalStateException("Not allowed.");
    }

    public final Uri insert(Uri uri, ContentValues values) {
        throw new IllegalStateException("Not allowed.");
    }

    public final int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new IllegalStateException("Not allowed.");
    }

    public final int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new IllegalStateException("Not allowed.");
    }
}
