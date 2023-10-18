package com.google.android.material.snackbar;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.lang.ref.WeakReference;

class SnackbarManager {
    private static final int LONG_DURATION_MS = 2750;
    static final int MSG_TIMEOUT = 0;
    private static final int SHORT_DURATION_MS = 1500;
    private static SnackbarManager snackbarManager;
    private SnackbarRecord currentSnackbar;
    private final Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        public boolean handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    SnackbarManager.this.handleTimeout((SnackbarRecord) message.obj);
                    return true;
                default:
                    return false;
            }
        }
    });
    private final Object lock = new Object();
    private SnackbarRecord nextSnackbar;

    interface Callback {
        void dismiss(int i);

        void show();
    }

    static SnackbarManager getInstance() {
        if (snackbarManager == null) {
            snackbarManager = new SnackbarManager();
        }
        return snackbarManager;
    }

    private SnackbarManager() {
    }

    public void show(int duration, Callback callback) {
        synchronized (this.lock) {
            if (isCurrentSnackbarLocked(callback)) {
                this.currentSnackbar.duration = duration;
                this.handler.removeCallbacksAndMessages(this.currentSnackbar);
                scheduleTimeoutLocked(this.currentSnackbar);
                return;
            }
            if (isNextSnackbarLocked(callback)) {
                this.nextSnackbar.duration = duration;
            } else {
                this.nextSnackbar = new SnackbarRecord(duration, callback);
            }
            SnackbarRecord snackbarRecord = this.currentSnackbar;
            if (snackbarRecord == null || !cancelSnackbarLocked(snackbarRecord, 4)) {
                this.currentSnackbar = null;
                showNextSnackbarLocked();
            }
        }
    }

    public void dismiss(Callback callback, int event) {
        synchronized (this.lock) {
            if (isCurrentSnackbarLocked(callback)) {
                cancelSnackbarLocked(this.currentSnackbar, event);
            } else if (isNextSnackbarLocked(callback)) {
                cancelSnackbarLocked(this.nextSnackbar, event);
            }
        }
    }

    public void onDismissed(Callback callback) {
        synchronized (this.lock) {
            if (isCurrentSnackbarLocked(callback)) {
                this.currentSnackbar = null;
                if (this.nextSnackbar != null) {
                    showNextSnackbarLocked();
                }
            }
        }
    }

    public void onShown(Callback callback) {
        synchronized (this.lock) {
            if (isCurrentSnackbarLocked(callback)) {
                scheduleTimeoutLocked(this.currentSnackbar);
            }
        }
    }

    public void pauseTimeout(Callback callback) {
        synchronized (this.lock) {
            if (isCurrentSnackbarLocked(callback) && !this.currentSnackbar.paused) {
                this.currentSnackbar.paused = true;
                this.handler.removeCallbacksAndMessages(this.currentSnackbar);
            }
        }
    }

    public void restoreTimeoutIfPaused(Callback callback) {
        synchronized (this.lock) {
            if (isCurrentSnackbarLocked(callback) && this.currentSnackbar.paused) {
                this.currentSnackbar.paused = false;
                scheduleTimeoutLocked(this.currentSnackbar);
            }
        }
    }

    public boolean isCurrent(Callback callback) {
        boolean isCurrentSnackbarLocked;
        synchronized (this.lock) {
            isCurrentSnackbarLocked = isCurrentSnackbarLocked(callback);
        }
        return isCurrentSnackbarLocked;
    }

    public boolean isCurrentOrNext(Callback callback) {
        boolean z;
        synchronized (this.lock) {
            if (!isCurrentSnackbarLocked(callback)) {
                if (!isNextSnackbarLocked(callback)) {
                    z = false;
                }
            }
            z = true;
        }
        return z;
    }

    private static class SnackbarRecord {
        final WeakReference<Callback> callback;
        int duration;
        boolean paused;

        SnackbarRecord(int duration2, Callback callback2) {
            this.callback = new WeakReference<>(callback2);
            this.duration = duration2;
        }

        /* access modifiers changed from: package-private */
        public boolean isSnackbar(Callback callback2) {
            return callback2 != null && this.callback.get() == callback2;
        }
    }

    private void showNextSnackbarLocked() {
        SnackbarRecord snackbarRecord = this.nextSnackbar;
        if (snackbarRecord != null) {
            this.currentSnackbar = snackbarRecord;
            this.nextSnackbar = null;
            Callback callback = (Callback) snackbarRecord.callback.get();
            if (callback != null) {
                callback.show();
            } else {
                this.currentSnackbar = null;
            }
        }
    }

    private boolean cancelSnackbarLocked(SnackbarRecord record, int event) {
        Callback callback = (Callback) record.callback.get();
        if (callback == null) {
            return false;
        }
        this.handler.removeCallbacksAndMessages(record);
        callback.dismiss(event);
        return true;
    }

    private boolean isCurrentSnackbarLocked(Callback callback) {
        SnackbarRecord snackbarRecord = this.currentSnackbar;
        return snackbarRecord != null && snackbarRecord.isSnackbar(callback);
    }

    private boolean isNextSnackbarLocked(Callback callback) {
        SnackbarRecord snackbarRecord = this.nextSnackbar;
        return snackbarRecord != null && snackbarRecord.isSnackbar(callback);
    }

    private void scheduleTimeoutLocked(SnackbarRecord r) {
        if (r.duration != -2) {
            int durationMs = LONG_DURATION_MS;
            if (r.duration > 0) {
                durationMs = r.duration;
            } else if (r.duration == -1) {
                durationMs = SHORT_DURATION_MS;
            }
            this.handler.removeCallbacksAndMessages(r);
            Handler handler2 = this.handler;
            handler2.sendMessageDelayed(Message.obtain(handler2, 0, r), (long) durationMs);
        }
    }

    /* access modifiers changed from: package-private */
    public void handleTimeout(SnackbarRecord record) {
        synchronized (this.lock) {
            if (this.currentSnackbar == record || this.nextSnackbar == record) {
                cancelSnackbarLocked(record, 2);
            }
        }
    }
}
