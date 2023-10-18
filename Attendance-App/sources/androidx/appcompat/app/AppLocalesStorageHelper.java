package androidx.appcompat.app;

import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.util.Xml;
import androidx.appcompat.app.AppCompatDelegate;
import com.itextpdf.p026io.font.PdfEncodings;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

class AppLocalesStorageHelper {
    static final String APPLICATION_LOCALES_RECORD_FILE = "androidx.appcompat.app.AppCompatDelegate.application_locales_record_file";
    static final String APP_LOCALES_META_DATA_HOLDER_SERVICE_NAME = "androidx.appcompat.app.AppLocalesMetadataHolderService";
    static final String LOCALE_RECORD_ATTRIBUTE_TAG = "application_locales";
    static final String LOCALE_RECORD_FILE_TAG = "locales";
    static final String TAG = "AppLocalesStorageHelper";

    private AppLocalesStorageHelper() {
    }

    static String readLocales(Context context) {
        String appLocales = "";
        try {
            FileInputStream fis = context.openFileInput(APPLICATION_LOCALES_RECORD_FILE);
            try {
                XmlPullParser parser = Xml.newPullParser();
                parser.setInput(fis, PdfEncodings.UTF8);
                int outerDepth = parser.getDepth();
                while (true) {
                    int next = parser.next();
                    int type = next;
                    if (next == 1 || (type == 3 && parser.getDepth() <= outerDepth)) {
                        break;
                    } else if (type != 3) {
                        if (type != 4) {
                            if (parser.getName().equals(LOCALE_RECORD_FILE_TAG)) {
                                appLocales = parser.getAttributeValue((String) null, LOCALE_RECORD_ATTRIBUTE_TAG);
                                break;
                            }
                        }
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                    }
                }
            } catch (IOException | XmlPullParserException e2) {
                Log.w(TAG, "Reading app Locales : Unable to parse through file :androidx.appcompat.app.AppCompatDelegate.application_locales_record_file");
                if (fis != null) {
                    fis.close();
                }
            } catch (Throwable th) {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e3) {
                    }
                }
                throw th;
            }
            if (!appLocales.isEmpty()) {
                Log.d(TAG, "Reading app Locales : Locales read from file: androidx.appcompat.app.AppCompatDelegate.application_locales_record_file , appLocales: " + appLocales);
            } else {
                context.deleteFile(APPLICATION_LOCALES_RECORD_FILE);
            }
            return appLocales;
        } catch (FileNotFoundException e4) {
            Log.w(TAG, "Reading app Locales : Locales record file not found: androidx.appcompat.app.AppCompatDelegate.application_locales_record_file");
            return appLocales;
        }
    }

    static void persistLocales(Context context, String locales) {
        if (locales.equals("")) {
            context.deleteFile(APPLICATION_LOCALES_RECORD_FILE);
            return;
        }
        try {
            FileOutputStream fos = context.openFileOutput(APPLICATION_LOCALES_RECORD_FILE, 0);
            XmlSerializer serializer = Xml.newSerializer();
            try {
                serializer.setOutput(fos, (String) null);
                serializer.startDocument(PdfEncodings.UTF8, true);
                serializer.startTag((String) null, LOCALE_RECORD_FILE_TAG);
                serializer.attribute((String) null, LOCALE_RECORD_ATTRIBUTE_TAG, locales);
                serializer.endTag((String) null, LOCALE_RECORD_FILE_TAG);
                serializer.endDocument();
                Log.d(TAG, "Storing App Locales : app-locales: " + locales + " persisted successfully.");
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                    }
                }
            } catch (Exception e2) {
                Log.w(TAG, "Storing App Locales : Failed to persist app-locales: " + locales, e2);
                if (fos != null) {
                    fos.close();
                }
            } catch (Throwable th) {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e3) {
                    }
                }
                throw th;
            }
        } catch (FileNotFoundException e4) {
            Log.w(TAG, String.format("Storing App Locales : FileNotFoundException: Cannot open file %s for writing ", new Object[]{APPLICATION_LOCALES_RECORD_FILE}));
        }
    }

    static void syncLocalesToFramework(Context context) {
        if (Build.VERSION.SDK_INT >= 33) {
            ComponentName app_locales_component = new ComponentName(context, APP_LOCALES_META_DATA_HOLDER_SERVICE_NAME);
            if (context.getPackageManager().getComponentEnabledSetting(app_locales_component) != 1) {
                if (AppCompatDelegate.getApplicationLocales().isEmpty()) {
                    String appLocales = readLocales(context);
                    Object localeManager = context.getSystemService("locale");
                    if (localeManager != null) {
                        AppCompatDelegate.Api33Impl.localeManagerSetApplicationLocales(localeManager, AppCompatDelegate.Api24Impl.localeListForLanguageTags(appLocales));
                    }
                }
                context.getPackageManager().setComponentEnabledSetting(app_locales_component, 1, 1);
            }
        }
    }

    static class ThreadPerTaskExecutor implements Executor {
        ThreadPerTaskExecutor() {
        }

        public void execute(Runnable r) {
            new Thread(r).start();
        }
    }

    static class SerialExecutor implements Executor {
        Runnable mActive;
        final Executor mExecutor;
        private final Object mLock = new Object();
        final Queue<Runnable> mTasks = new ArrayDeque();

        SerialExecutor(Executor executor) {
            this.mExecutor = executor;
        }

        public void execute(Runnable r) {
            synchronized (this.mLock) {
                this.mTasks.add(new AppLocalesStorageHelper$SerialExecutor$$ExternalSyntheticLambda0(this, r));
                if (this.mActive == null) {
                    scheduleNext();
                }
            }
        }

        /* access modifiers changed from: package-private */
        /* renamed from: lambda$execute$0$androidx-appcompat-app-AppLocalesStorageHelper$SerialExecutor */
        public /* synthetic */ void mo10197xd188c474(Runnable r) {
            try {
                r.run();
            } finally {
                scheduleNext();
            }
        }

        /* access modifiers changed from: protected */
        public void scheduleNext() {
            synchronized (this.mLock) {
                Runnable poll = this.mTasks.poll();
                this.mActive = poll;
                if (poll != null) {
                    this.mExecutor.execute(poll);
                }
            }
        }
    }
}
