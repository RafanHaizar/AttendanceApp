package androidx.core.view.inputmethod;

import android.content.ClipDescription;
import android.net.Uri;
import android.os.Build;
import android.view.inputmethod.InputContentInfo;

public final class InputContentInfoCompat {
    private final InputContentInfoCompatImpl mImpl;

    private interface InputContentInfoCompatImpl {
        Uri getContentUri();

        ClipDescription getDescription();

        Object getInputContentInfo();

        Uri getLinkUri();

        void releasePermission();

        void requestPermission();
    }

    private static final class InputContentInfoCompatBaseImpl implements InputContentInfoCompatImpl {
        private final Uri mContentUri;
        private final ClipDescription mDescription;
        private final Uri mLinkUri;

        InputContentInfoCompatBaseImpl(Uri contentUri, ClipDescription description, Uri linkUri) {
            this.mContentUri = contentUri;
            this.mDescription = description;
            this.mLinkUri = linkUri;
        }

        public Uri getContentUri() {
            return this.mContentUri;
        }

        public ClipDescription getDescription() {
            return this.mDescription;
        }

        public Uri getLinkUri() {
            return this.mLinkUri;
        }

        public Object getInputContentInfo() {
            return null;
        }

        public void requestPermission() {
        }

        public void releasePermission() {
        }
    }

    private static final class InputContentInfoCompatApi25Impl implements InputContentInfoCompatImpl {
        final InputContentInfo mObject;

        InputContentInfoCompatApi25Impl(Object inputContentInfo) {
            this.mObject = (InputContentInfo) inputContentInfo;
        }

        InputContentInfoCompatApi25Impl(Uri contentUri, ClipDescription description, Uri linkUri) {
            this.mObject = new InputContentInfo(contentUri, description, linkUri);
        }

        public Uri getContentUri() {
            return this.mObject.getContentUri();
        }

        public ClipDescription getDescription() {
            return this.mObject.getDescription();
        }

        public Uri getLinkUri() {
            return this.mObject.getLinkUri();
        }

        public Object getInputContentInfo() {
            return this.mObject;
        }

        public void requestPermission() {
            this.mObject.requestPermission();
        }

        public void releasePermission() {
            this.mObject.releasePermission();
        }
    }

    public InputContentInfoCompat(Uri contentUri, ClipDescription description, Uri linkUri) {
        if (Build.VERSION.SDK_INT >= 25) {
            this.mImpl = new InputContentInfoCompatApi25Impl(contentUri, description, linkUri);
        } else {
            this.mImpl = new InputContentInfoCompatBaseImpl(contentUri, description, linkUri);
        }
    }

    private InputContentInfoCompat(InputContentInfoCompatImpl impl) {
        this.mImpl = impl;
    }

    public Uri getContentUri() {
        return this.mImpl.getContentUri();
    }

    public ClipDescription getDescription() {
        return this.mImpl.getDescription();
    }

    public Uri getLinkUri() {
        return this.mImpl.getLinkUri();
    }

    public static InputContentInfoCompat wrap(Object inputContentInfo) {
        if (inputContentInfo != null && Build.VERSION.SDK_INT >= 25) {
            return new InputContentInfoCompat(new InputContentInfoCompatApi25Impl(inputContentInfo));
        }
        return null;
    }

    public Object unwrap() {
        return this.mImpl.getInputContentInfo();
    }

    public void requestPermission() {
        this.mImpl.requestPermission();
    }

    public void releasePermission() {
        this.mImpl.releasePermission();
    }
}
