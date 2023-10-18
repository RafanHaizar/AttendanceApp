package androidx.core.view.inputmethod;

import android.content.ClipData;
import android.content.ClipDescription;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputContentInfo;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Preconditions;
import androidx.core.view.ContentInfoCompat;
import androidx.core.view.ViewCompat;

public final class InputConnectionCompat {
    private static final String COMMIT_CONTENT_ACTION = "androidx.core.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT";
    private static final String COMMIT_CONTENT_CONTENT_URI_INTEROP_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_URI";
    private static final String COMMIT_CONTENT_CONTENT_URI_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_URI";
    private static final String COMMIT_CONTENT_DESCRIPTION_INTEROP_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
    private static final String COMMIT_CONTENT_DESCRIPTION_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_DESCRIPTION";
    private static final String COMMIT_CONTENT_FLAGS_INTEROP_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
    private static final String COMMIT_CONTENT_FLAGS_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_FLAGS";
    private static final String COMMIT_CONTENT_INTEROP_ACTION = "android.support.v13.view.inputmethod.InputConnectionCompat.COMMIT_CONTENT";
    private static final String COMMIT_CONTENT_LINK_URI_INTEROP_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
    private static final String COMMIT_CONTENT_LINK_URI_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_LINK_URI";
    private static final String COMMIT_CONTENT_OPTS_INTEROP_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
    private static final String COMMIT_CONTENT_OPTS_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_OPTS";
    private static final String COMMIT_CONTENT_RESULT_INTEROP_RECEIVER_KEY = "android.support.v13.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER";
    private static final String COMMIT_CONTENT_RESULT_RECEIVER_KEY = "androidx.core.view.inputmethod.InputConnectionCompat.CONTENT_RESULT_RECEIVER";
    private static final String EXTRA_INPUT_CONTENT_INFO = "androidx.core.view.extra.INPUT_CONTENT_INFO";
    public static final int INPUT_CONTENT_GRANT_READ_URI_PERMISSION = 1;
    private static final String LOG_TAG = "InputConnectionCompat";

    public interface OnCommitContentListener {
        boolean onCommitContent(InputContentInfoCompat inputContentInfoCompat, int i, Bundle bundle);
    }

    static boolean handlePerformPrivateCommand(String action, Bundle data, OnCommitContentListener onCommitContentListener) {
        boolean interop;
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        int i = 0;
        if (data == null) {
            return false;
        }
        if (TextUtils.equals(COMMIT_CONTENT_ACTION, action)) {
            interop = false;
        } else if (!TextUtils.equals(COMMIT_CONTENT_INTEROP_ACTION, action)) {
            return false;
        } else {
            interop = true;
        }
        ResultReceiver resultReceiver = null;
        boolean result = false;
        if (interop) {
            str = COMMIT_CONTENT_RESULT_INTEROP_RECEIVER_KEY;
        } else {
            str = COMMIT_CONTENT_RESULT_RECEIVER_KEY;
        }
        try {
            ResultReceiver resultReceiver2 = (ResultReceiver) data.getParcelable(str);
            if (interop) {
                str2 = COMMIT_CONTENT_CONTENT_URI_INTEROP_KEY;
            } else {
                str2 = COMMIT_CONTENT_CONTENT_URI_KEY;
            }
            Uri contentUri = (Uri) data.getParcelable(str2);
            if (interop) {
                str3 = COMMIT_CONTENT_DESCRIPTION_INTEROP_KEY;
            } else {
                str3 = COMMIT_CONTENT_DESCRIPTION_KEY;
            }
            ClipDescription description = (ClipDescription) data.getParcelable(str3);
            if (interop) {
                str4 = COMMIT_CONTENT_LINK_URI_INTEROP_KEY;
            } else {
                str4 = COMMIT_CONTENT_LINK_URI_KEY;
            }
            Uri linkUri = (Uri) data.getParcelable(str4);
            if (interop) {
                str5 = COMMIT_CONTENT_FLAGS_INTEROP_KEY;
            } else {
                str5 = COMMIT_CONTENT_FLAGS_KEY;
            }
            int flags = data.getInt(str5);
            if (interop) {
                str6 = COMMIT_CONTENT_OPTS_INTEROP_KEY;
            } else {
                str6 = COMMIT_CONTENT_OPTS_KEY;
            }
            Bundle opts = (Bundle) data.getParcelable(str6);
            if (!(contentUri == null || description == null)) {
                result = onCommitContentListener.onCommitContent(new InputContentInfoCompat(contentUri, description, linkUri), flags, opts);
            }
            if (resultReceiver2 != null) {
                if (result) {
                    i = 1;
                }
                resultReceiver2.send(i, (Bundle) null);
            }
            return result;
        } catch (Throwable th) {
            if (resultReceiver != null) {
                resultReceiver.send(0, (Bundle) null);
            }
            throw th;
        }
    }

    public static boolean commitContent(InputConnection inputConnection, EditorInfo editorInfo, InputContentInfoCompat inputContentInfo, int flags, Bundle opts) {
        boolean interop;
        String str;
        String str2;
        String str3;
        String str4;
        String str5;
        String str6;
        if (Build.VERSION.SDK_INT >= 25) {
            return Api25Impl.commitContent(inputConnection, (InputContentInfo) inputContentInfo.unwrap(), flags, opts);
        }
        switch (EditorInfoCompat.getProtocol(editorInfo)) {
            case 2:
                interop = true;
                break;
            case 3:
            case 4:
                interop = false;
                break;
            default:
                return false;
        }
        Bundle params = new Bundle();
        if (interop) {
            str = COMMIT_CONTENT_CONTENT_URI_INTEROP_KEY;
        } else {
            str = COMMIT_CONTENT_CONTENT_URI_KEY;
        }
        params.putParcelable(str, inputContentInfo.getContentUri());
        if (interop) {
            str2 = COMMIT_CONTENT_DESCRIPTION_INTEROP_KEY;
        } else {
            str2 = COMMIT_CONTENT_DESCRIPTION_KEY;
        }
        params.putParcelable(str2, inputContentInfo.getDescription());
        if (interop) {
            str3 = COMMIT_CONTENT_LINK_URI_INTEROP_KEY;
        } else {
            str3 = COMMIT_CONTENT_LINK_URI_KEY;
        }
        params.putParcelable(str3, inputContentInfo.getLinkUri());
        if (interop) {
            str4 = COMMIT_CONTENT_FLAGS_INTEROP_KEY;
        } else {
            str4 = COMMIT_CONTENT_FLAGS_KEY;
        }
        params.putInt(str4, flags);
        if (interop) {
            str5 = COMMIT_CONTENT_OPTS_INTEROP_KEY;
        } else {
            str5 = COMMIT_CONTENT_OPTS_KEY;
        }
        params.putParcelable(str5, opts);
        if (interop) {
            str6 = COMMIT_CONTENT_INTEROP_ACTION;
        } else {
            str6 = COMMIT_CONTENT_ACTION;
        }
        return inputConnection.performPrivateCommand(str6, params);
    }

    @Deprecated
    public static InputConnection createWrapper(InputConnection inputConnection, EditorInfo editorInfo, OnCommitContentListener onCommitContentListener) {
        ObjectsCompat.requireNonNull(inputConnection, "inputConnection must be non-null");
        ObjectsCompat.requireNonNull(editorInfo, "editorInfo must be non-null");
        ObjectsCompat.requireNonNull(onCommitContentListener, "onCommitContentListener must be non-null");
        if (Build.VERSION.SDK_INT >= 25) {
            final OnCommitContentListener listener = onCommitContentListener;
            return new InputConnectionWrapper(inputConnection, false) {
                public boolean commitContent(InputContentInfo inputContentInfo, int flags, Bundle opts) {
                    if (listener.onCommitContent(InputContentInfoCompat.wrap(inputContentInfo), flags, opts)) {
                        return true;
                    }
                    return super.commitContent(inputContentInfo, flags, opts);
                }
            };
        } else if (EditorInfoCompat.getContentMimeTypes(editorInfo).length == 0) {
            return inputConnection;
        } else {
            final OnCommitContentListener listener2 = onCommitContentListener;
            return new InputConnectionWrapper(inputConnection, false) {
                public boolean performPrivateCommand(String action, Bundle data) {
                    if (InputConnectionCompat.handlePerformPrivateCommand(action, data, listener2)) {
                        return true;
                    }
                    return super.performPrivateCommand(action, data);
                }
            };
        }
    }

    public static InputConnection createWrapper(View view, InputConnection inputConnection, EditorInfo editorInfo) {
        return createWrapper(inputConnection, editorInfo, createOnCommitContentListenerUsingPerformReceiveContent(view));
    }

    private static OnCommitContentListener createOnCommitContentListenerUsingPerformReceiveContent(View view) {
        Preconditions.checkNotNull(view);
        return new InputConnectionCompat$$ExternalSyntheticLambda0(view);
    }

    static /* synthetic */ boolean lambda$createOnCommitContentListenerUsingPerformReceiveContent$0(View view, InputContentInfoCompat inputContentInfo, int flags, Bundle opts) {
        Bundle bundle;
        Bundle extras = opts;
        if (Build.VERSION.SDK_INT >= 25 && (flags & 1) != 0) {
            try {
                inputContentInfo.requestPermission();
                InputContentInfo inputContentInfoFmk = (InputContentInfo) inputContentInfo.unwrap();
                if (opts != null) {
                    bundle = new Bundle(opts);
                }
                extras = bundle;
                extras.putParcelable(EXTRA_INPUT_CONTENT_INFO, inputContentInfoFmk);
            } catch (Exception e) {
                Log.w(LOG_TAG, "Can't insert content from IME; requestPermission() failed", e);
                return false;
            }
        }
        if (ViewCompat.performReceiveContent(view, new ContentInfoCompat.Builder(new ClipData(inputContentInfo.getDescription(), new ClipData.Item(inputContentInfo.getContentUri())), 2).setLinkUri(inputContentInfo.getLinkUri()).setExtras(extras).build()) == null) {
            return true;
        }
        return false;
    }

    static class Api25Impl {
        private Api25Impl() {
        }

        static boolean commitContent(InputConnection inputConnection, InputContentInfo inputContentInfo, int i, Bundle bundle) {
            return inputConnection.commitContent(inputContentInfo, i, bundle);
        }
    }
}
