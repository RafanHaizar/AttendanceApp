package androidx.emoji2.text;

import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.SystemClock;
import androidx.core.provider.FontRequest;
import androidx.core.provider.FontsContractCompat;
import androidx.core.util.Preconditions;
import androidx.emoji2.text.EmojiCompat;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

public class FontRequestEmojiCompatConfig extends EmojiCompat.Config {
    private static final FontProviderHelper DEFAULT_FONTS_CONTRACT = new FontProviderHelper();

    public static abstract class RetryPolicy {
        public abstract long getRetryDelay();
    }

    public static class ExponentialBackoffRetryPolicy extends RetryPolicy {
        private long mRetryOrigin;
        private final long mTotalMs;

        public ExponentialBackoffRetryPolicy(long totalMs) {
            this.mTotalMs = totalMs;
        }

        public long getRetryDelay() {
            if (this.mRetryOrigin == 0) {
                this.mRetryOrigin = SystemClock.uptimeMillis();
                return 0;
            }
            long elapsedMillis = SystemClock.uptimeMillis() - this.mRetryOrigin;
            if (elapsedMillis > this.mTotalMs) {
                return -1;
            }
            return Math.min(Math.max(elapsedMillis, 1000), this.mTotalMs - elapsedMillis);
        }
    }

    public FontRequestEmojiCompatConfig(Context context, FontRequest request) {
        super(new FontRequestMetadataLoader(context, request, DEFAULT_FONTS_CONTRACT));
    }

    public FontRequestEmojiCompatConfig(Context context, FontRequest request, FontProviderHelper fontProviderHelper) {
        super(new FontRequestMetadataLoader(context, request, fontProviderHelper));
    }

    public FontRequestEmojiCompatConfig setLoadingExecutor(Executor executor) {
        ((FontRequestMetadataLoader) getMetadataRepoLoader()).setExecutor(executor);
        return this;
    }

    @Deprecated
    public FontRequestEmojiCompatConfig setHandler(Handler handler) {
        if (handler == null) {
            return this;
        }
        setLoadingExecutor(ConcurrencyHelpers.convertHandlerToExecutor(handler));
        return this;
    }

    public FontRequestEmojiCompatConfig setRetryPolicy(RetryPolicy policy) {
        ((FontRequestMetadataLoader) getMetadataRepoLoader()).setRetryPolicy(policy);
        return this;
    }

    private static class FontRequestMetadataLoader implements EmojiCompat.MetadataRepoLoader {
        private static final String S_TRACE_BUILD_TYPEFACE = "EmojiCompat.FontRequestEmojiCompatConfig.buildTypeface";
        EmojiCompat.MetadataRepoLoaderCallback mCallback;
        private final Context mContext;
        private Executor mExecutor;
        private final FontProviderHelper mFontProviderHelper;
        private final Object mLock = new Object();
        private Handler mMainHandler;
        private Runnable mMainHandlerLoadCallback;
        private ThreadPoolExecutor mMyThreadPoolExecutor;
        private ContentObserver mObserver;
        private final FontRequest mRequest;
        private RetryPolicy mRetryPolicy;

        FontRequestMetadataLoader(Context context, FontRequest request, FontProviderHelper fontProviderHelper) {
            Preconditions.checkNotNull(context, "Context cannot be null");
            Preconditions.checkNotNull(request, "FontRequest cannot be null");
            this.mContext = context.getApplicationContext();
            this.mRequest = request;
            this.mFontProviderHelper = fontProviderHelper;
        }

        public void setExecutor(Executor executor) {
            synchronized (this.mLock) {
                this.mExecutor = executor;
            }
        }

        public void setRetryPolicy(RetryPolicy policy) {
            synchronized (this.mLock) {
                this.mRetryPolicy = policy;
            }
        }

        public void load(EmojiCompat.MetadataRepoLoaderCallback loaderCallback) {
            Preconditions.checkNotNull(loaderCallback, "LoaderCallback cannot be null");
            synchronized (this.mLock) {
                this.mCallback = loaderCallback;
            }
            loadInternal();
        }

        /* access modifiers changed from: package-private */
        public void loadInternal() {
            synchronized (this.mLock) {
                if (this.mCallback != null) {
                    if (this.mExecutor == null) {
                        ThreadPoolExecutor createBackgroundPriorityExecutor = ConcurrencyHelpers.createBackgroundPriorityExecutor("emojiCompat");
                        this.mMyThreadPoolExecutor = createBackgroundPriorityExecutor;
                        this.mExecutor = createBackgroundPriorityExecutor;
                    }
                    this.mExecutor.execute(new C0793xc2d47b97(this));
                }
            }
        }

        private FontsContractCompat.FontInfo retrieveFontInfo() {
            try {
                FontsContractCompat.FontFamilyResult result = this.mFontProviderHelper.fetchFonts(this.mContext, this.mRequest);
                if (result.getStatusCode() == 0) {
                    FontsContractCompat.FontInfo[] fonts = result.getFonts();
                    if (fonts != null && fonts.length != 0) {
                        return fonts[0];
                    }
                    throw new RuntimeException("fetchFonts failed (empty result)");
                }
                throw new RuntimeException("fetchFonts failed (" + result.getStatusCode() + ")");
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException("provider not found", e);
            }
        }

        private void scheduleRetry(Uri uri, long waitMs) {
            synchronized (this.mLock) {
                Handler handler = this.mMainHandler;
                if (handler == null) {
                    handler = ConcurrencyHelpers.mainHandlerAsync();
                    this.mMainHandler = handler;
                }
                if (this.mObserver == null) {
                    C07921 r2 = new ContentObserver(handler) {
                        public void onChange(boolean selfChange, Uri uri) {
                            FontRequestMetadataLoader.this.loadInternal();
                        }
                    };
                    this.mObserver = r2;
                    this.mFontProviderHelper.registerObserver(this.mContext, uri, r2);
                }
                if (this.mMainHandlerLoadCallback == null) {
                    this.mMainHandlerLoadCallback = new C0794xc2d47b98(this);
                }
                handler.postDelayed(this.mMainHandlerLoadCallback, waitMs);
            }
        }

        private void cleanUp() {
            synchronized (this.mLock) {
                this.mCallback = null;
                ContentObserver contentObserver = this.mObserver;
                if (contentObserver != null) {
                    this.mFontProviderHelper.unregisterObserver(this.mContext, contentObserver);
                    this.mObserver = null;
                }
                Handler handler = this.mMainHandler;
                if (handler != null) {
                    handler.removeCallbacks(this.mMainHandlerLoadCallback);
                }
                this.mMainHandler = null;
                ThreadPoolExecutor threadPoolExecutor = this.mMyThreadPoolExecutor;
                if (threadPoolExecutor != null) {
                    threadPoolExecutor.shutdown();
                }
                this.mExecutor = null;
                this.mMyThreadPoolExecutor = null;
            }
        }

        /* access modifiers changed from: package-private */
        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0013, code lost:
            if (r1 != 2) goto L_0x0034;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0015, code lost:
            r2 = r8.mLock;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0017, code lost:
            monitor-enter(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:?, code lost:
            r3 = r8.mRetryPolicy;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x001a, code lost:
            if (r3 == null) goto L_0x002f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x001c, code lost:
            r3 = r3.getRetryDelay();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0024, code lost:
            if (r3 < 0) goto L_0x002f;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0026, code lost:
            scheduleRetry(r0.getUri(), r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x002d, code lost:
            monitor-exit(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x002e, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x002f, code lost:
            monitor-exit(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0034, code lost:
            if (r1 != 0) goto L_0x0079;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:30:?, code lost:
            androidx.core.p001os.TraceCompat.beginSection(S_TRACE_BUILD_TYPEFACE);
            r2 = r8.mFontProviderHelper.buildTypeface(r8.mContext, r0);
            r3 = androidx.core.graphics.TypefaceCompatUtil.mmap(r8.mContext, (android.os.CancellationSignal) null, r0.getUri());
         */
        /* JADX WARNING: Code restructure failed: missing block: B:31:0x004e, code lost:
            if (r3 == null) goto L_0x006c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:32:0x0050, code lost:
            if (r2 == null) goto L_0x006c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:33:0x0052, code lost:
            r4 = androidx.emoji2.text.MetadataRepo.create(r2, r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:35:?, code lost:
            androidx.core.p001os.TraceCompat.endSection();
            r2 = r8.mLock;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:36:0x005c, code lost:
            monitor-enter(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:38:?, code lost:
            r3 = r8.mCallback;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:39:0x005f, code lost:
            if (r3 == null) goto L_0x0064;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:40:0x0061, code lost:
            r3.onLoaded(r4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:41:0x0064, code lost:
            monitor-exit(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:43:?, code lost:
            cleanUp();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:51:0x0073, code lost:
            throw new java.lang.RuntimeException("Unable to open file.");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:52:0x0074, code lost:
            r2 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:54:?, code lost:
            androidx.core.p001os.TraceCompat.endSection();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:55:0x0078, code lost:
            throw r2;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:57:0x0097, code lost:
            throw new java.lang.RuntimeException("fetchFonts result is not OK. (" + r1 + ")");
         */
        /* JADX WARNING: Code restructure failed: missing block: B:58:0x0098, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:59:0x0099, code lost:
            r1 = r0;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:60:0x009c, code lost:
            monitor-enter(r8.mLock);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:62:?, code lost:
            r0 = r8.mCallback;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:63:0x009f, code lost:
            if (r0 != null) goto L_0x00a1;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:64:0x00a1, code lost:
            r0.onFailed(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:66:0x00a5, code lost:
            cleanUp();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:83:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:84:?, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:?, code lost:
            r0 = retrieveFontInfo();
            r1 = r0.getResultCode();
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void createMetadata() {
            /*
                r8 = this;
                java.lang.Object r0 = r8.mLock
                monitor-enter(r0)
                androidx.emoji2.text.EmojiCompat$MetadataRepoLoaderCallback r1 = r8.mCallback     // Catch:{ all -> 0x00ac }
                if (r1 != 0) goto L_0x0009
                monitor-exit(r0)     // Catch:{ all -> 0x00ac }
                return
            L_0x0009:
                monitor-exit(r0)     // Catch:{ all -> 0x00ac }
                androidx.core.provider.FontsContractCompat$FontInfo r0 = r8.retrieveFontInfo()     // Catch:{ all -> 0x0098 }
                int r1 = r0.getResultCode()     // Catch:{ all -> 0x0098 }
                r2 = 2
                if (r1 != r2) goto L_0x0034
                java.lang.Object r2 = r8.mLock     // Catch:{ all -> 0x0098 }
                monitor-enter(r2)     // Catch:{ all -> 0x0098 }
                androidx.emoji2.text.FontRequestEmojiCompatConfig$RetryPolicy r3 = r8.mRetryPolicy     // Catch:{ all -> 0x0031 }
                if (r3 == 0) goto L_0x002f
                long r3 = r3.getRetryDelay()     // Catch:{ all -> 0x0031 }
                r5 = 0
                int r7 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
                if (r7 < 0) goto L_0x002f
                android.net.Uri r5 = r0.getUri()     // Catch:{ all -> 0x0031 }
                r8.scheduleRetry(r5, r3)     // Catch:{ all -> 0x0031 }
                monitor-exit(r2)     // Catch:{ all -> 0x0031 }
                return
            L_0x002f:
                monitor-exit(r2)     // Catch:{ all -> 0x0031 }
                goto L_0x0034
            L_0x0031:
                r3 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x0031 }
                throw r3     // Catch:{ all -> 0x0098 }
            L_0x0034:
                if (r1 != 0) goto L_0x0079
                java.lang.String r2 = "EmojiCompat.FontRequestEmojiCompatConfig.buildTypeface"
                androidx.core.p001os.TraceCompat.beginSection(r2)     // Catch:{ all -> 0x0074 }
                androidx.emoji2.text.FontRequestEmojiCompatConfig$FontProviderHelper r2 = r8.mFontProviderHelper     // Catch:{ all -> 0x0074 }
                android.content.Context r3 = r8.mContext     // Catch:{ all -> 0x0074 }
                android.graphics.Typeface r2 = r2.buildTypeface(r3, r0)     // Catch:{ all -> 0x0074 }
                android.content.Context r3 = r8.mContext     // Catch:{ all -> 0x0074 }
                android.net.Uri r4 = r0.getUri()     // Catch:{ all -> 0x0074 }
                r5 = 0
                java.nio.ByteBuffer r3 = androidx.core.graphics.TypefaceCompatUtil.mmap(r3, r5, r4)     // Catch:{ all -> 0x0074 }
                if (r3 == 0) goto L_0x006c
                if (r2 == 0) goto L_0x006c
                androidx.emoji2.text.MetadataRepo r4 = androidx.emoji2.text.MetadataRepo.create((android.graphics.Typeface) r2, (java.nio.ByteBuffer) r3)     // Catch:{ all -> 0x0074 }
                androidx.core.p001os.TraceCompat.endSection()     // Catch:{ all -> 0x0098 }
                java.lang.Object r2 = r8.mLock     // Catch:{ all -> 0x0098 }
                monitor-enter(r2)     // Catch:{ all -> 0x0098 }
                androidx.emoji2.text.EmojiCompat$MetadataRepoLoaderCallback r3 = r8.mCallback     // Catch:{ all -> 0x0069 }
                if (r3 == 0) goto L_0x0064
                r3.onLoaded(r4)     // Catch:{ all -> 0x0069 }
            L_0x0064:
                monitor-exit(r2)     // Catch:{ all -> 0x0069 }
                r8.cleanUp()     // Catch:{ all -> 0x0098 }
                goto L_0x00a8
            L_0x0069:
                r3 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x0069 }
                throw r3     // Catch:{ all -> 0x0098 }
            L_0x006c:
                java.lang.RuntimeException r4 = new java.lang.RuntimeException     // Catch:{ all -> 0x0074 }
                java.lang.String r5 = "Unable to open file."
                r4.<init>(r5)     // Catch:{ all -> 0x0074 }
                throw r4     // Catch:{ all -> 0x0074 }
            L_0x0074:
                r2 = move-exception
                androidx.core.p001os.TraceCompat.endSection()     // Catch:{ all -> 0x0098 }
                throw r2     // Catch:{ all -> 0x0098 }
            L_0x0079:
                java.lang.RuntimeException r2 = new java.lang.RuntimeException     // Catch:{ all -> 0x0098 }
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0098 }
                r3.<init>()     // Catch:{ all -> 0x0098 }
                java.lang.String r4 = "fetchFonts result is not OK. ("
                java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0098 }
                java.lang.StringBuilder r3 = r3.append(r1)     // Catch:{ all -> 0x0098 }
                java.lang.String r4 = ")"
                java.lang.StringBuilder r3 = r3.append(r4)     // Catch:{ all -> 0x0098 }
                java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0098 }
                r2.<init>(r3)     // Catch:{ all -> 0x0098 }
                throw r2     // Catch:{ all -> 0x0098 }
            L_0x0098:
                r0 = move-exception
                r1 = r0
                java.lang.Object r2 = r8.mLock
                monitor-enter(r2)
                androidx.emoji2.text.EmojiCompat$MetadataRepoLoaderCallback r0 = r8.mCallback     // Catch:{ all -> 0x00a9 }
                if (r0 == 0) goto L_0x00a4
                r0.onFailed(r1)     // Catch:{ all -> 0x00a9 }
            L_0x00a4:
                monitor-exit(r2)     // Catch:{ all -> 0x00a9 }
                r8.cleanUp()
            L_0x00a8:
                return
            L_0x00a9:
                r0 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x00a9 }
                throw r0
            L_0x00ac:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x00ac }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.emoji2.text.FontRequestEmojiCompatConfig.FontRequestMetadataLoader.createMetadata():void");
        }
    }

    public static class FontProviderHelper {
        public FontsContractCompat.FontFamilyResult fetchFonts(Context context, FontRequest request) throws PackageManager.NameNotFoundException {
            return FontsContractCompat.fetchFonts(context, (CancellationSignal) null, request);
        }

        public Typeface buildTypeface(Context context, FontsContractCompat.FontInfo font) throws PackageManager.NameNotFoundException {
            return FontsContractCompat.buildTypeface(context, (CancellationSignal) null, new FontsContractCompat.FontInfo[]{font});
        }

        public void registerObserver(Context context, Uri uri, ContentObserver observer) {
            context.getContentResolver().registerContentObserver(uri, false, observer);
        }

        public void unregisterObserver(Context context, ContentObserver observer) {
            context.getContentResolver().unregisterContentObserver(observer);
        }
    }
}
