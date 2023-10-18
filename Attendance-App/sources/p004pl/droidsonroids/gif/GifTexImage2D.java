package p004pl.droidsonroids.gif;

import java.io.IOException;

/* renamed from: pl.droidsonroids.gif.GifTexImage2D */
public class GifTexImage2D {
    private final GifInfoHandle mGifInfoHandle;

    public GifTexImage2D(InputSource inputSource, GifOptions options) throws IOException {
        options = options == null ? new GifOptions() : options;
        GifInfoHandle open = inputSource.open();
        this.mGifInfoHandle = open;
        open.setOptions(options.inSampleSize, options.inIsOpaque);
        open.initTexImageDescriptor();
    }

    public int getFrameDuration(int index) {
        return this.mGifInfoHandle.getFrameDuration(index);
    }

    public void seekToFrame(int index) {
        this.mGifInfoHandle.seekToFrameGL(index);
    }

    public int getNumberOfFrames() {
        return this.mGifInfoHandle.getNumberOfFrames();
    }

    public int getCurrentFrameIndex() {
        return this.mGifInfoHandle.getCurrentFrameIndex();
    }

    public void setSpeed(float factor) {
        this.mGifInfoHandle.setSpeedFactor(factor);
    }

    public void glTexImage2D(int target, int level) {
        this.mGifInfoHandle.glTexImage2D(target, level);
    }

    public void glTexSubImage2D(int target, int level) {
        this.mGifInfoHandle.glTexSubImage2D(target, level);
    }

    public void startDecoderThread() {
        this.mGifInfoHandle.startDecoderThread();
    }

    public void stopDecoderThread() {
        this.mGifInfoHandle.stopDecoderThread();
    }

    public void recycle() {
        GifInfoHandle gifInfoHandle = this.mGifInfoHandle;
        if (gifInfoHandle != null) {
            gifInfoHandle.recycle();
        }
    }

    public int getWidth() {
        return this.mGifInfoHandle.getWidth();
    }

    public int getHeight() {
        return this.mGifInfoHandle.getHeight();
    }

    public int getDuration() {
        return this.mGifInfoHandle.getDuration();
    }

    /* access modifiers changed from: protected */
    public final void finalize() throws Throwable {
        try {
            recycle();
        } finally {
            super.finalize();
        }
    }
}
