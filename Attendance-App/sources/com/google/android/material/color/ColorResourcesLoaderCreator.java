package com.google.android.material.color;

import android.content.Context;
import android.content.res.loader.AssetsProvider;
import android.content.res.loader.ResourcesLoader;
import android.content.res.loader.ResourcesProvider;
import android.os.ParcelFileDescriptor;
import android.system.Os;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;

final class ColorResourcesLoaderCreator {
    private static final String TAG = ColorResourcesLoaderCreator.class.getSimpleName();

    private ColorResourcesLoaderCreator() {
    }

    static ResourcesLoader create(Context context, Map<Integer, Integer> colorMapping) {
        ParcelFileDescriptor pfd;
        try {
            byte[] contentBytes = ColorResourcesTableCreator.create(context, colorMapping);
            Log.i(TAG, "Table created, length: " + contentBytes.length);
            if (contentBytes.length == 0) {
                return null;
            }
            FileDescriptor arscFile = null;
            try {
                arscFile = Os.memfd_create("temp.arsc", 0);
                OutputStream pipeWriter = new FileOutputStream(arscFile);
                try {
                    pipeWriter.write(contentBytes);
                    pfd = ParcelFileDescriptor.dup(arscFile);
                    ResourcesLoader colorsLoader = new ResourcesLoader();
                    colorsLoader.addProvider(ResourcesProvider.loadFromTable(pfd, (AssetsProvider) null));
                    if (pfd != null) {
                        pfd.close();
                    }
                    pipeWriter.close();
                    if (arscFile != null) {
                        Os.close(arscFile);
                    }
                    return colorsLoader;
                } catch (Throwable th) {
                    pipeWriter.close();
                    throw th;
                }
            } catch (Throwable th2) {
                if (arscFile != null) {
                    Os.close(arscFile);
                }
                throw th2;
            }
            throw colorsLoader;
        } catch (Exception e) {
            Log.e(TAG, "Failed to create the ColorResourcesTableCreator.", e);
            return null;
        }
    }
}
