package p004pl.droidsonroids.gif;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.net.Uri;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import p004pl.droidsonroids.gif.GifDrawableInit;
import p004pl.droidsonroids.gif.InputSource;

/* renamed from: pl.droidsonroids.gif.GifDrawableInit */
public abstract class GifDrawableInit<T extends GifDrawableInit<T>> {
    private ScheduledThreadPoolExecutor mExecutor;
    private InputSource mInputSource;
    private boolean mIsRenderingTriggeredOnDraw = true;
    private GifDrawable mOldDrawable;
    private final GifOptions mOptions = new GifOptions();

    /* access modifiers changed from: protected */
    public abstract T self();

    public T sampleSize(int sampleSize) {
        this.mOptions.setInSampleSize(sampleSize);
        return self();
    }

    public GifDrawable build() throws IOException {
        InputSource inputSource = this.mInputSource;
        if (inputSource != null) {
            return inputSource.createGifDrawable(this.mOldDrawable, this.mExecutor, this.mIsRenderingTriggeredOnDraw, this.mOptions);
        }
        throw new NullPointerException("Source is not set");
    }

    public T with(GifDrawable drawable) {
        this.mOldDrawable = drawable;
        return self();
    }

    public T threadPoolSize(int threadPoolSize) {
        this.mExecutor = new ScheduledThreadPoolExecutor(threadPoolSize);
        return self();
    }

    public T taskExecutor(ScheduledThreadPoolExecutor executor) {
        this.mExecutor = executor;
        return self();
    }

    public T renderingTriggeredOnDraw(boolean isRenderingTriggeredOnDraw) {
        this.mIsRenderingTriggeredOnDraw = isRenderingTriggeredOnDraw;
        return self();
    }

    public T setRenderingTriggeredOnDraw(boolean isRenderingTriggeredOnDraw) {
        return renderingTriggeredOnDraw(isRenderingTriggeredOnDraw);
    }

    public T options(GifOptions options) {
        this.mOptions.setFrom(options);
        return self();
    }

    public T from(InputStream inputStream) {
        this.mInputSource = new InputSource.InputStreamSource(inputStream);
        return self();
    }

    public T from(AssetFileDescriptor assetFileDescriptor) {
        this.mInputSource = new InputSource.AssetFileDescriptorSource(assetFileDescriptor);
        return self();
    }

    public T from(FileDescriptor fileDescriptor) {
        this.mInputSource = new InputSource.FileDescriptorSource(fileDescriptor);
        return self();
    }

    public T from(AssetManager assetManager, String assetName) {
        this.mInputSource = new InputSource.AssetSource(assetManager, assetName);
        return self();
    }

    public T from(ContentResolver contentResolver, Uri uri) {
        this.mInputSource = new InputSource.UriSource(contentResolver, uri);
        return self();
    }

    public T from(File file) {
        this.mInputSource = new InputSource.FileSource(file);
        return self();
    }

    public T from(String filePath) {
        this.mInputSource = new InputSource.FileSource(filePath);
        return self();
    }

    public T from(byte[] bytes) {
        this.mInputSource = new InputSource.ByteArraySource(bytes);
        return self();
    }

    public T from(ByteBuffer byteBuffer) {
        this.mInputSource = new InputSource.DirectByteBufferSource(byteBuffer);
        return self();
    }

    public T from(Resources resources, int resourceId) {
        this.mInputSource = new InputSource.ResourcesSource(resources, resourceId);
        return self();
    }

    public InputSource getInputSource() {
        return this.mInputSource;
    }

    public GifDrawable getOldDrawable() {
        return this.mOldDrawable;
    }

    public ScheduledThreadPoolExecutor getExecutor() {
        return this.mExecutor;
    }

    public boolean isRenderingTriggeredOnDraw() {
        return this.mIsRenderingTriggeredOnDraw;
    }

    public GifOptions getOptions() {
        return this.mOptions;
    }
}
