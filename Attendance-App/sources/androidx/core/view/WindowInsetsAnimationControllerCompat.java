package androidx.core.view;

import android.view.WindowInsetsAnimationController;
import androidx.core.graphics.Insets;

public final class WindowInsetsAnimationControllerCompat {
    private final Impl mImpl;

    WindowInsetsAnimationControllerCompat(WindowInsetsAnimationController controller) {
        this.mImpl = new Impl30(controller);
    }

    public Insets getHiddenStateInsets() {
        return this.mImpl.getHiddenStateInsets();
    }

    public Insets getShownStateInsets() {
        return this.mImpl.getShownStateInsets();
    }

    public Insets getCurrentInsets() {
        return this.mImpl.getCurrentInsets();
    }

    public float getCurrentFraction() {
        return this.mImpl.getCurrentFraction();
    }

    public float getCurrentAlpha() {
        return this.mImpl.getCurrentAlpha();
    }

    public int getTypes() {
        return this.mImpl.getTypes();
    }

    public void setInsetsAndAlpha(Insets insets, float alpha, float fraction) {
        this.mImpl.setInsetsAndAlpha(insets, alpha, fraction);
    }

    public void finish(boolean shown) {
        this.mImpl.finish(shown);
    }

    public boolean isReady() {
        return !isFinished() && !isCancelled();
    }

    public boolean isFinished() {
        return this.mImpl.isFinished();
    }

    public boolean isCancelled() {
        return this.mImpl.isCancelled();
    }

    private static class Impl {
        Impl() {
        }

        public Insets getHiddenStateInsets() {
            return Insets.NONE;
        }

        public Insets getShownStateInsets() {
            return Insets.NONE;
        }

        public Insets getCurrentInsets() {
            return Insets.NONE;
        }

        public float getCurrentFraction() {
            return 0.0f;
        }

        public float getCurrentAlpha() {
            return 0.0f;
        }

        public int getTypes() {
            return 0;
        }

        public void setInsetsAndAlpha(Insets insets, float alpha, float fraction) {
        }

        /* access modifiers changed from: package-private */
        public void finish(boolean shown) {
        }

        /* access modifiers changed from: package-private */
        public boolean isFinished() {
            return false;
        }

        /* access modifiers changed from: package-private */
        public boolean isCancelled() {
            return true;
        }
    }

    private static class Impl30 extends Impl {
        private final WindowInsetsAnimationController mController;

        Impl30(WindowInsetsAnimationController controller) {
            this.mController = controller;
        }

        public Insets getHiddenStateInsets() {
            return Insets.toCompatInsets(this.mController.getHiddenStateInsets());
        }

        public Insets getShownStateInsets() {
            return Insets.toCompatInsets(this.mController.getShownStateInsets());
        }

        public Insets getCurrentInsets() {
            return Insets.toCompatInsets(this.mController.getCurrentInsets());
        }

        public float getCurrentFraction() {
            return this.mController.getCurrentFraction();
        }

        public float getCurrentAlpha() {
            return this.mController.getCurrentAlpha();
        }

        public int getTypes() {
            return this.mController.getTypes();
        }

        public void setInsetsAndAlpha(Insets insets, float alpha, float fraction) {
            this.mController.setInsetsAndAlpha(insets == null ? null : insets.toPlatformInsets(), alpha, fraction);
        }

        /* access modifiers changed from: package-private */
        public void finish(boolean shown) {
            this.mController.finish(shown);
        }

        /* access modifiers changed from: package-private */
        public boolean isFinished() {
            return this.mController.isFinished();
        }

        /* access modifiers changed from: package-private */
        public boolean isCancelled() {
            return this.mController.isCancelled();
        }
    }
}
