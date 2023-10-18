package p004pl.droidsonroids.gif;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.view.Surface;
import android.view.TextureView;
import p004pl.droidsonroids.gif.GifTextureView;

/* renamed from: pl.droidsonroids.gif.PlaceholderDrawingSurfaceTextureListener */
class PlaceholderDrawingSurfaceTextureListener implements TextureView.SurfaceTextureListener {
    private final GifTextureView.PlaceholderDrawListener mDrawer;

    PlaceholderDrawingSurfaceTextureListener(GifTextureView.PlaceholderDrawListener drawer) {
        this.mDrawer = drawer;
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        Surface surface = new Surface(surfaceTexture);
        Canvas canvas = surface.lockCanvas((Rect) null);
        this.mDrawer.onDrawPlaceholder(canvas);
        surface.unlockCanvasAndPost(canvas);
        surface.release();
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int width, int height) {
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }
}
