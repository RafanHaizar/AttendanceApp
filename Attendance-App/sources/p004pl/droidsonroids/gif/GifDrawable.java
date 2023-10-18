package p004pl.droidsonroids.gif;

import android.content.ContentResolver;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.SystemClock;
import android.widget.MediaController;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import p004pl.droidsonroids.gif.transforms.CornerRadiusTransform;
import p004pl.droidsonroids.gif.transforms.Transform;

/* renamed from: pl.droidsonroids.gif.GifDrawable */
public class GifDrawable extends Drawable implements Animatable, MediaController.MediaPlayerControl {
    final Bitmap mBuffer;
    private final Rect mDstRect;
    final ScheduledThreadPoolExecutor mExecutor;
    final InvalidationHandler mInvalidationHandler;
    final boolean mIsRenderingTriggeredOnDraw;
    volatile boolean mIsRunning;
    final ConcurrentLinkedQueue<AnimationListener> mListeners;
    final GifInfoHandle mNativeInfoHandle;
    long mNextFrameRenderTime;
    protected final Paint mPaint;
    private final RenderTask mRenderTask;
    ScheduledFuture<?> mRenderTaskSchedule;
    private int mScaledHeight;
    private int mScaledWidth;
    private final Rect mSrcRect;
    private ColorStateList mTint;
    private PorterDuffColorFilter mTintFilter;
    private PorterDuff.Mode mTintMode;
    private Transform mTransform;

    public GifDrawable(Resources res, int id) throws Resources.NotFoundException, IOException {
        this(res.openRawResourceFd(id));
        float densityScale = GifViewUtils.getDensityScale(res, id);
        this.mScaledHeight = (int) (((float) this.mNativeInfoHandle.getHeight()) * densityScale);
        this.mScaledWidth = (int) (((float) this.mNativeInfoHandle.getWidth()) * densityScale);
    }

    public GifDrawable(AssetManager assets, String assetName) throws IOException {
        this(assets.openFd(assetName));
    }

    public GifDrawable(String filePath) throws IOException {
        this(new GifInfoHandle(filePath), (GifDrawable) null, (ScheduledThreadPoolExecutor) null, true);
    }

    public GifDrawable(File file) throws IOException {
        this(file.getPath());
    }

    public GifDrawable(InputStream stream) throws IOException {
        this(new GifInfoHandle(stream), (GifDrawable) null, (ScheduledThreadPoolExecutor) null, true);
    }

    public GifDrawable(AssetFileDescriptor afd) throws IOException {
        this(new GifInfoHandle(afd), (GifDrawable) null, (ScheduledThreadPoolExecutor) null, true);
    }

    public GifDrawable(FileDescriptor fd) throws IOException {
        this(new GifInfoHandle(fd), (GifDrawable) null, (ScheduledThreadPoolExecutor) null, true);
    }

    public GifDrawable(byte[] bytes) throws IOException {
        this(new GifInfoHandle(bytes), (GifDrawable) null, (ScheduledThreadPoolExecutor) null, true);
    }

    public GifDrawable(ByteBuffer buffer) throws IOException {
        this(new GifInfoHandle(buffer), (GifDrawable) null, (ScheduledThreadPoolExecutor) null, true);
    }

    public GifDrawable(ContentResolver resolver, Uri uri) throws IOException {
        this(GifInfoHandle.openUri(resolver, uri), (GifDrawable) null, (ScheduledThreadPoolExecutor) null, true);
    }

    protected GifDrawable(InputSource inputSource, GifDrawable oldDrawable, ScheduledThreadPoolExecutor executor, boolean isRenderingTriggeredOnDraw, GifOptions options) throws IOException {
        this(inputSource.createHandleWith(options), oldDrawable, executor, isRenderingTriggeredOnDraw);
    }

    GifDrawable(GifInfoHandle gifInfoHandle, GifDrawable oldDrawable, ScheduledThreadPoolExecutor executor, boolean isRenderingTriggeredOnDraw) {
        this.mIsRunning = true;
        this.mNextFrameRenderTime = Long.MIN_VALUE;
        this.mDstRect = new Rect();
        this.mPaint = new Paint(6);
        this.mListeners = new ConcurrentLinkedQueue<>();
        RenderTask renderTask = new RenderTask(this);
        this.mRenderTask = renderTask;
        this.mIsRenderingTriggeredOnDraw = isRenderingTriggeredOnDraw;
        this.mExecutor = executor != null ? executor : GifRenderingExecutor.getInstance();
        this.mNativeInfoHandle = gifInfoHandle;
        Bitmap oldBitmap = null;
        if (oldDrawable != null) {
            synchronized (oldDrawable.mNativeInfoHandle) {
                if (!oldDrawable.mNativeInfoHandle.isRecycled() && oldDrawable.mNativeInfoHandle.getHeight() >= gifInfoHandle.getHeight() && oldDrawable.mNativeInfoHandle.getWidth() >= gifInfoHandle.getWidth()) {
                    oldDrawable.shutdown();
                    oldBitmap = oldDrawable.mBuffer;
                    oldBitmap.eraseColor(0);
                }
            }
        }
        if (oldBitmap == null) {
            this.mBuffer = Bitmap.createBitmap(gifInfoHandle.getWidth(), gifInfoHandle.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            this.mBuffer = oldBitmap;
        }
        this.mBuffer.setHasAlpha(true ^ gifInfoHandle.isOpaque());
        this.mSrcRect = new Rect(0, 0, gifInfoHandle.getWidth(), gifInfoHandle.getHeight());
        this.mInvalidationHandler = new InvalidationHandler(this);
        renderTask.doWork();
        this.mScaledWidth = gifInfoHandle.getWidth();
        this.mScaledHeight = gifInfoHandle.getHeight();
    }

    public void recycle() {
        shutdown();
        this.mBuffer.recycle();
    }

    private void shutdown() {
        this.mIsRunning = false;
        this.mInvalidationHandler.removeMessages(-1);
        this.mNativeInfoHandle.recycle();
    }

    public boolean isRecycled() {
        return this.mNativeInfoHandle.isRecycled();
    }

    public void invalidateSelf() {
        super.invalidateSelf();
        scheduleNextRender();
    }

    public int getIntrinsicHeight() {
        return this.mScaledHeight;
    }

    public int getIntrinsicWidth() {
        return this.mScaledWidth;
    }

    public void setAlpha(int alpha) {
        this.mPaint.setAlpha(alpha);
    }

    public void setColorFilter(ColorFilter cf) {
        this.mPaint.setColorFilter(cf);
    }

    public int getOpacity() {
        if (!this.mNativeInfoHandle.isOpaque() || this.mPaint.getAlpha() < 255) {
            return -2;
        }
        return -1;
    }

    public void start() {
        synchronized (this) {
            if (!this.mIsRunning) {
                this.mIsRunning = true;
                startAnimation(this.mNativeInfoHandle.restoreRemainder());
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void startAnimation(long lastFrameRemainder) {
        if (this.mIsRenderingTriggeredOnDraw) {
            this.mNextFrameRenderTime = 0;
            this.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0);
            return;
        }
        cancelPendingRenderTask();
        this.mRenderTaskSchedule = this.mExecutor.schedule(this.mRenderTask, Math.max(lastFrameRemainder, 0), TimeUnit.MILLISECONDS);
    }

    public void reset() {
        this.mExecutor.execute(new SafeRunnable(this) {
            public void doWork() {
                if (GifDrawable.this.mNativeInfoHandle.reset()) {
                    GifDrawable.this.start();
                }
            }
        });
    }

    public void stop() {
        synchronized (this) {
            if (this.mIsRunning) {
                this.mIsRunning = false;
                cancelPendingRenderTask();
                this.mNativeInfoHandle.saveRemainder();
            }
        }
    }

    private void cancelPendingRenderTask() {
        ScheduledFuture<?> scheduledFuture = this.mRenderTaskSchedule;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
        this.mInvalidationHandler.removeMessages(-1);
    }

    public boolean isRunning() {
        return this.mIsRunning;
    }

    public String getComment() {
        return this.mNativeInfoHandle.getComment();
    }

    public int getLoopCount() {
        return this.mNativeInfoHandle.getLoopCount();
    }

    public void setLoopCount(int loopCount) {
        this.mNativeInfoHandle.setLoopCount(loopCount);
    }

    public String toString() {
        return String.format(Locale.ENGLISH, "GIF: size: %dx%d, frames: %d, error: %d", new Object[]{Integer.valueOf(this.mNativeInfoHandle.getWidth()), Integer.valueOf(this.mNativeInfoHandle.getHeight()), Integer.valueOf(this.mNativeInfoHandle.getNumberOfFrames()), Integer.valueOf(this.mNativeInfoHandle.getNativeErrorCode())});
    }

    public int getNumberOfFrames() {
        return this.mNativeInfoHandle.getNumberOfFrames();
    }

    public GifError getError() {
        return GifError.fromCode(this.mNativeInfoHandle.getNativeErrorCode());
    }

    public static GifDrawable createFromResource(Resources res, int resourceId) {
        try {
            return new GifDrawable(res, resourceId);
        } catch (IOException e) {
            return null;
        }
    }

    public void setSpeed(float factor) {
        this.mNativeInfoHandle.setSpeedFactor(factor);
    }

    public void pause() {
        stop();
    }

    public int getDuration() {
        return this.mNativeInfoHandle.getDuration();
    }

    public int getCurrentPosition() {
        return this.mNativeInfoHandle.getCurrentPosition();
    }

    public void seekTo(final int position) {
        if (position >= 0) {
            this.mExecutor.execute(new SafeRunnable(this) {
                public void doWork() {
                    GifDrawable.this.mNativeInfoHandle.seekToTime(position, GifDrawable.this.mBuffer);
                    this.mGifDrawable.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0);
                }
            });
            return;
        }
        throw new IllegalArgumentException("Position is not positive");
    }

    public void seekToBlocking(int position) {
        if (position >= 0) {
            synchronized (this.mNativeInfoHandle) {
                this.mNativeInfoHandle.seekToTime(position, this.mBuffer);
            }
            this.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0);
            return;
        }
        throw new IllegalArgumentException("Position is not positive");
    }

    public void seekToFrame(final int frameIndex) {
        if (frameIndex >= 0) {
            this.mExecutor.execute(new SafeRunnable(this) {
                public void doWork() {
                    GifDrawable.this.mNativeInfoHandle.seekToFrame(frameIndex, GifDrawable.this.mBuffer);
                    GifDrawable.this.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0);
                }
            });
            return;
        }
        throw new IndexOutOfBoundsException("Frame index is not positive");
    }

    public Bitmap seekToFrameAndGet(int frameIndex) {
        Bitmap bitmap;
        if (frameIndex >= 0) {
            synchronized (this.mNativeInfoHandle) {
                this.mNativeInfoHandle.seekToFrame(frameIndex, this.mBuffer);
                bitmap = getCurrentFrame();
            }
            this.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0);
            return bitmap;
        }
        throw new IndexOutOfBoundsException("Frame index is not positive");
    }

    public Bitmap seekToPositionAndGet(int position) {
        Bitmap bitmap;
        if (position >= 0) {
            synchronized (this.mNativeInfoHandle) {
                this.mNativeInfoHandle.seekToTime(position, this.mBuffer);
                bitmap = getCurrentFrame();
            }
            this.mInvalidationHandler.sendEmptyMessageAtTime(-1, 0);
            return bitmap;
        }
        throw new IllegalArgumentException("Position is not positive");
    }

    public boolean isPlaying() {
        return this.mIsRunning;
    }

    public int getBufferPercentage() {
        return 100;
    }

    public boolean canPause() {
        return true;
    }

    public boolean canSeekBackward() {
        return getNumberOfFrames() > 1;
    }

    public boolean canSeekForward() {
        return getNumberOfFrames() > 1;
    }

    public int getAudioSessionId() {
        return 0;
    }

    public int getFrameByteCount() {
        return this.mBuffer.getRowBytes() * this.mBuffer.getHeight();
    }

    public long getAllocationByteCount() {
        return this.mNativeInfoHandle.getAllocationByteCount() + ((long) this.mBuffer.getAllocationByteCount());
    }

    public long getMetadataAllocationByteCount() {
        return this.mNativeInfoHandle.getMetadataByteCount();
    }

    public long getInputSourceByteCount() {
        return this.mNativeInfoHandle.getSourceLength();
    }

    public void getPixels(int[] pixels) {
        this.mBuffer.getPixels(pixels, 0, this.mNativeInfoHandle.getWidth(), 0, 0, this.mNativeInfoHandle.getWidth(), this.mNativeInfoHandle.getHeight());
    }

    public int getPixel(int x, int y) {
        if (x >= this.mNativeInfoHandle.getWidth()) {
            throw new IllegalArgumentException("x must be < width");
        } else if (y < this.mNativeInfoHandle.getHeight()) {
            return this.mBuffer.getPixel(x, y);
        } else {
            throw new IllegalArgumentException("y must be < height");
        }
    }

    /* access modifiers changed from: protected */
    public void onBoundsChange(Rect bounds) {
        this.mDstRect.set(bounds);
        Transform transform = this.mTransform;
        if (transform != null) {
            transform.onBoundsChange(bounds);
        }
    }

    public void draw(Canvas canvas) {
        boolean clearColorFilter;
        if (this.mTintFilter == null || this.mPaint.getColorFilter() != null) {
            clearColorFilter = false;
        } else {
            this.mPaint.setColorFilter(this.mTintFilter);
            clearColorFilter = true;
        }
        Transform transform = this.mTransform;
        if (transform == null) {
            canvas.drawBitmap(this.mBuffer, this.mSrcRect, this.mDstRect, this.mPaint);
        } else {
            transform.onDraw(canvas, this.mPaint, this.mBuffer);
        }
        if (clearColorFilter) {
            this.mPaint.setColorFilter((ColorFilter) null);
        }
    }

    private void scheduleNextRender() {
        if (this.mIsRenderingTriggeredOnDraw && this.mIsRunning) {
            long j = this.mNextFrameRenderTime;
            if (j != Long.MIN_VALUE) {
                long renderDelay = Math.max(0, j - SystemClock.uptimeMillis());
                this.mNextFrameRenderTime = Long.MIN_VALUE;
                this.mExecutor.remove(this.mRenderTask);
                this.mRenderTaskSchedule = this.mExecutor.schedule(this.mRenderTask, renderDelay, TimeUnit.MILLISECONDS);
            }
        }
    }

    public final Paint getPaint() {
        return this.mPaint;
    }

    public int getAlpha() {
        return this.mPaint.getAlpha();
    }

    public void setFilterBitmap(boolean filter) {
        this.mPaint.setFilterBitmap(filter);
        invalidateSelf();
    }

    public void setDither(boolean dither) {
        this.mPaint.setDither(dither);
        invalidateSelf();
    }

    public void addAnimationListener(AnimationListener listener) {
        this.mListeners.add(listener);
    }

    public boolean removeAnimationListener(AnimationListener listener) {
        return this.mListeners.remove(listener);
    }

    public ColorFilter getColorFilter() {
        return this.mPaint.getColorFilter();
    }

    public Bitmap getCurrentFrame() {
        Bitmap bitmap = this.mBuffer;
        Bitmap copy = bitmap.copy(bitmap.getConfig(), this.mBuffer.isMutable());
        copy.setHasAlpha(this.mBuffer.hasAlpha());
        return copy;
    }

    private PorterDuffColorFilter updateTintFilter(ColorStateList tint, PorterDuff.Mode tintMode) {
        if (tint == null || tintMode == null) {
            return null;
        }
        return new PorterDuffColorFilter(tint.getColorForState(getState(), 0), tintMode);
    }

    public void setTintList(ColorStateList tint) {
        this.mTint = tint;
        this.mTintFilter = updateTintFilter(tint, this.mTintMode);
        invalidateSelf();
    }

    public void setTintMode(PorterDuff.Mode tintMode) {
        this.mTintMode = tintMode;
        this.mTintFilter = updateTintFilter(this.mTint, tintMode);
        invalidateSelf();
    }

    /* access modifiers changed from: protected */
    public boolean onStateChange(int[] stateSet) {
        PorterDuff.Mode mode;
        ColorStateList colorStateList = this.mTint;
        if (colorStateList == null || (mode = this.mTintMode) == null) {
            return false;
        }
        this.mTintFilter = updateTintFilter(colorStateList, mode);
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x0006, code lost:
        r0 = r1.mTint;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isStateful() {
        /*
            r1 = this;
            boolean r0 = super.isStateful()
            if (r0 != 0) goto L_0x0013
            android.content.res.ColorStateList r0 = r1.mTint
            if (r0 == 0) goto L_0x0011
            boolean r0 = r0.isStateful()
            if (r0 == 0) goto L_0x0011
            goto L_0x0013
        L_0x0011:
            r0 = 0
            goto L_0x0014
        L_0x0013:
            r0 = 1
        L_0x0014:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p004pl.droidsonroids.gif.GifDrawable.isStateful():boolean");
    }

    public boolean setVisible(boolean visible, boolean restart) {
        boolean changed = super.setVisible(visible, restart);
        if (!this.mIsRenderingTriggeredOnDraw) {
            if (visible) {
                if (restart) {
                    reset();
                }
                if (changed) {
                    start();
                }
            } else if (changed) {
                stop();
            }
        }
        return changed;
    }

    public int getCurrentFrameIndex() {
        return this.mNativeInfoHandle.getCurrentFrameIndex();
    }

    public int getCurrentLoop() {
        int currentLoop = this.mNativeInfoHandle.getCurrentLoop();
        if (currentLoop == 0 || currentLoop < this.mNativeInfoHandle.getLoopCount()) {
            return currentLoop;
        }
        return currentLoop - 1;
    }

    public boolean isAnimationCompleted() {
        return this.mNativeInfoHandle.isAnimationCompleted();
    }

    public int getFrameDuration(int index) {
        return this.mNativeInfoHandle.getFrameDuration(index);
    }

    public void setCornerRadius(float cornerRadius) {
        CornerRadiusTransform cornerRadiusTransform = new CornerRadiusTransform(cornerRadius);
        this.mTransform = cornerRadiusTransform;
        cornerRadiusTransform.onBoundsChange(this.mDstRect);
    }

    public float getCornerRadius() {
        Transform transform = this.mTransform;
        if (transform instanceof CornerRadiusTransform) {
            return ((CornerRadiusTransform) transform).getCornerRadius();
        }
        return 0.0f;
    }

    public void setTransform(Transform transform) {
        this.mTransform = transform;
        if (transform != null) {
            transform.onBoundsChange(this.mDstRect);
        }
    }

    public Transform getTransform() {
        return this.mTransform;
    }
}
