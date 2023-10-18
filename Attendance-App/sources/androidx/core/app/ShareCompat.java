package androidx.core.app;

import android.app.Activity;
import android.content.ClipData;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.ActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;
import androidx.core.content.IntentCompat;
import androidx.core.util.Preconditions;
import java.util.ArrayList;

public final class ShareCompat {
    public static final String EXTRA_CALLING_ACTIVITY = "androidx.core.app.EXTRA_CALLING_ACTIVITY";
    public static final String EXTRA_CALLING_ACTIVITY_INTEROP = "android.support.v4.app.EXTRA_CALLING_ACTIVITY";
    public static final String EXTRA_CALLING_PACKAGE = "androidx.core.app.EXTRA_CALLING_PACKAGE";
    public static final String EXTRA_CALLING_PACKAGE_INTEROP = "android.support.v4.app.EXTRA_CALLING_PACKAGE";
    private static final String HISTORY_FILENAME_PREFIX = ".sharecompat_";

    private ShareCompat() {
    }

    public static String getCallingPackage(Activity calledActivity) {
        Intent intent = calledActivity.getIntent();
        String result = calledActivity.getCallingPackage();
        if (result != null || intent == null) {
            return result;
        }
        return getCallingPackage(intent);
    }

    static String getCallingPackage(Intent intent) {
        String result = intent.getStringExtra(EXTRA_CALLING_PACKAGE);
        if (result == null) {
            return intent.getStringExtra(EXTRA_CALLING_PACKAGE_INTEROP);
        }
        return result;
    }

    public static ComponentName getCallingActivity(Activity calledActivity) {
        Intent intent = calledActivity.getIntent();
        ComponentName result = calledActivity.getCallingActivity();
        if (result == null) {
            return getCallingActivity(intent);
        }
        return result;
    }

    static ComponentName getCallingActivity(Intent intent) {
        ComponentName result = (ComponentName) intent.getParcelableExtra(EXTRA_CALLING_ACTIVITY);
        if (result == null) {
            return (ComponentName) intent.getParcelableExtra(EXTRA_CALLING_ACTIVITY_INTEROP);
        }
        return result;
    }

    @Deprecated
    public static void configureMenuItem(MenuItem item, IntentBuilder shareIntent) {
        ShareActionProvider provider;
        ActionProvider itemProvider = item.getActionProvider();
        if (!(itemProvider instanceof ShareActionProvider)) {
            provider = new ShareActionProvider(shareIntent.getContext());
        } else {
            provider = (ShareActionProvider) itemProvider;
        }
        provider.setShareHistoryFileName(HISTORY_FILENAME_PREFIX + shareIntent.getContext().getClass().getName());
        provider.setShareIntent(shareIntent.getIntent());
        item.setActionProvider(provider);
    }

    @Deprecated
    public static void configureMenuItem(Menu menu, int menuItemId, IntentBuilder shareIntent) {
        MenuItem item = menu.findItem(menuItemId);
        if (item != null) {
            configureMenuItem(item, shareIntent);
            return;
        }
        throw new IllegalArgumentException("Could not find menu item with id " + menuItemId + " in the supplied menu");
    }

    public static class IntentBuilder {
        private ArrayList<String> mBccAddresses;
        private ArrayList<String> mCcAddresses;
        private CharSequence mChooserTitle;
        private final Context mContext;
        private final Intent mIntent;
        private ArrayList<Uri> mStreams;
        private ArrayList<String> mToAddresses;

        @Deprecated
        public static IntentBuilder from(Activity launchingActivity) {
            return new IntentBuilder(launchingActivity);
        }

        public IntentBuilder(Context launchingContext) {
            this.mContext = (Context) Preconditions.checkNotNull(launchingContext);
            Intent action = new Intent().setAction("android.intent.action.SEND");
            this.mIntent = action;
            action.putExtra(ShareCompat.EXTRA_CALLING_PACKAGE, launchingContext.getPackageName());
            action.putExtra(ShareCompat.EXTRA_CALLING_PACKAGE_INTEROP, launchingContext.getPackageName());
            action.addFlags(524288);
            Activity activity = null;
            Context context = launchingContext;
            while (true) {
                if (!(context instanceof ContextWrapper)) {
                    break;
                } else if (context instanceof Activity) {
                    activity = (Activity) context;
                    break;
                } else {
                    context = ((ContextWrapper) context).getBaseContext();
                }
            }
            if (activity != null) {
                ComponentName componentName = activity.getComponentName();
                this.mIntent.putExtra(ShareCompat.EXTRA_CALLING_ACTIVITY, componentName);
                this.mIntent.putExtra(ShareCompat.EXTRA_CALLING_ACTIVITY_INTEROP, componentName);
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x002c, code lost:
            if (r0.size() > 1) goto L_0x0030;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public android.content.Intent getIntent() {
            /*
                r5 = this;
                java.util.ArrayList<java.lang.String> r0 = r5.mToAddresses
                r1 = 0
                if (r0 == 0) goto L_0x000c
                java.lang.String r2 = "android.intent.extra.EMAIL"
                r5.combineArrayExtra((java.lang.String) r2, (java.util.ArrayList<java.lang.String>) r0)
                r5.mToAddresses = r1
            L_0x000c:
                java.util.ArrayList<java.lang.String> r0 = r5.mCcAddresses
                if (r0 == 0) goto L_0x0017
                java.lang.String r2 = "android.intent.extra.CC"
                r5.combineArrayExtra((java.lang.String) r2, (java.util.ArrayList<java.lang.String>) r0)
                r5.mCcAddresses = r1
            L_0x0017:
                java.util.ArrayList<java.lang.String> r0 = r5.mBccAddresses
                if (r0 == 0) goto L_0x0022
                java.lang.String r2 = "android.intent.extra.BCC"
                r5.combineArrayExtra((java.lang.String) r2, (java.util.ArrayList<java.lang.String>) r0)
                r5.mBccAddresses = r1
            L_0x0022:
                java.util.ArrayList<android.net.Uri> r0 = r5.mStreams
                r1 = 0
                if (r0 == 0) goto L_0x002f
                int r0 = r0.size()
                r2 = 1
                if (r0 <= r2) goto L_0x002f
                goto L_0x0030
            L_0x002f:
                r2 = 0
            L_0x0030:
                r0 = r2
                java.lang.String r2 = "android.intent.extra.STREAM"
                if (r0 != 0) goto L_0x0068
                android.content.Intent r3 = r5.mIntent
                java.lang.String r4 = "android.intent.action.SEND"
                r3.setAction(r4)
                java.util.ArrayList<android.net.Uri> r3 = r5.mStreams
                if (r3 == 0) goto L_0x005c
                boolean r3 = r3.isEmpty()
                if (r3 != 0) goto L_0x005c
                android.content.Intent r3 = r5.mIntent
                java.util.ArrayList<android.net.Uri> r4 = r5.mStreams
                java.lang.Object r1 = r4.get(r1)
                android.os.Parcelable r1 = (android.os.Parcelable) r1
                r3.putExtra(r2, r1)
                android.content.Intent r1 = r5.mIntent
                java.util.ArrayList<android.net.Uri> r2 = r5.mStreams
                androidx.core.app.ShareCompat.Api16Impl.migrateExtraStreamToClipData(r1, r2)
                goto L_0x007e
            L_0x005c:
                android.content.Intent r1 = r5.mIntent
                r1.removeExtra(r2)
                android.content.Intent r1 = r5.mIntent
                androidx.core.app.ShareCompat.Api16Impl.removeClipData(r1)
                goto L_0x007e
            L_0x0068:
                android.content.Intent r1 = r5.mIntent
                java.lang.String r3 = "android.intent.action.SEND_MULTIPLE"
                r1.setAction(r3)
                android.content.Intent r1 = r5.mIntent
                java.util.ArrayList<android.net.Uri> r3 = r5.mStreams
                r1.putParcelableArrayListExtra(r2, r3)
                android.content.Intent r1 = r5.mIntent
                java.util.ArrayList<android.net.Uri> r2 = r5.mStreams
                androidx.core.app.ShareCompat.Api16Impl.migrateExtraStreamToClipData(r1, r2)
            L_0x007e:
                android.content.Intent r1 = r5.mIntent
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.core.app.ShareCompat.IntentBuilder.getIntent():android.content.Intent");
        }

        /* access modifiers changed from: package-private */
        public Context getContext() {
            return this.mContext;
        }

        private void combineArrayExtra(String extra, ArrayList<String> add) {
            String[] currentAddresses = this.mIntent.getStringArrayExtra(extra);
            int currentLength = currentAddresses != null ? currentAddresses.length : 0;
            String[] finalAddresses = new String[(add.size() + currentLength)];
            add.toArray(finalAddresses);
            if (currentAddresses != null) {
                System.arraycopy(currentAddresses, 0, finalAddresses, add.size(), currentLength);
            }
            this.mIntent.putExtra(extra, finalAddresses);
        }

        private void combineArrayExtra(String extra, String[] add) {
            Intent intent = getIntent();
            String[] old = intent.getStringArrayExtra(extra);
            int oldLength = old != null ? old.length : 0;
            String[] result = new String[(add.length + oldLength)];
            if (old != null) {
                System.arraycopy(old, 0, result, 0, oldLength);
            }
            System.arraycopy(add, 0, result, oldLength, add.length);
            intent.putExtra(extra, result);
        }

        public Intent createChooserIntent() {
            return Intent.createChooser(getIntent(), this.mChooserTitle);
        }

        public void startChooser() {
            this.mContext.startActivity(createChooserIntent());
        }

        public IntentBuilder setChooserTitle(CharSequence title) {
            this.mChooserTitle = title;
            return this;
        }

        public IntentBuilder setChooserTitle(int resId) {
            return setChooserTitle(this.mContext.getText(resId));
        }

        public IntentBuilder setType(String mimeType) {
            this.mIntent.setType(mimeType);
            return this;
        }

        public IntentBuilder setText(CharSequence text) {
            this.mIntent.putExtra("android.intent.extra.TEXT", text);
            return this;
        }

        public IntentBuilder setHtmlText(String htmlText) {
            this.mIntent.putExtra(IntentCompat.EXTRA_HTML_TEXT, htmlText);
            if (!this.mIntent.hasExtra("android.intent.extra.TEXT")) {
                setText(Html.fromHtml(htmlText));
            }
            return this;
        }

        public IntentBuilder setStream(Uri streamUri) {
            this.mStreams = null;
            if (streamUri != null) {
                addStream(streamUri);
            }
            return this;
        }

        public IntentBuilder addStream(Uri streamUri) {
            if (this.mStreams == null) {
                this.mStreams = new ArrayList<>();
            }
            this.mStreams.add(streamUri);
            return this;
        }

        public IntentBuilder setEmailTo(String[] addresses) {
            if (this.mToAddresses != null) {
                this.mToAddresses = null;
            }
            this.mIntent.putExtra("android.intent.extra.EMAIL", addresses);
            return this;
        }

        public IntentBuilder addEmailTo(String address) {
            if (this.mToAddresses == null) {
                this.mToAddresses = new ArrayList<>();
            }
            this.mToAddresses.add(address);
            return this;
        }

        public IntentBuilder addEmailTo(String[] addresses) {
            combineArrayExtra("android.intent.extra.EMAIL", addresses);
            return this;
        }

        public IntentBuilder setEmailCc(String[] addresses) {
            this.mIntent.putExtra("android.intent.extra.CC", addresses);
            return this;
        }

        public IntentBuilder addEmailCc(String address) {
            if (this.mCcAddresses == null) {
                this.mCcAddresses = new ArrayList<>();
            }
            this.mCcAddresses.add(address);
            return this;
        }

        public IntentBuilder addEmailCc(String[] addresses) {
            combineArrayExtra("android.intent.extra.CC", addresses);
            return this;
        }

        public IntentBuilder setEmailBcc(String[] addresses) {
            this.mIntent.putExtra("android.intent.extra.BCC", addresses);
            return this;
        }

        public IntentBuilder addEmailBcc(String address) {
            if (this.mBccAddresses == null) {
                this.mBccAddresses = new ArrayList<>();
            }
            this.mBccAddresses.add(address);
            return this;
        }

        public IntentBuilder addEmailBcc(String[] addresses) {
            combineArrayExtra("android.intent.extra.BCC", addresses);
            return this;
        }

        public IntentBuilder setSubject(String subject) {
            this.mIntent.putExtra("android.intent.extra.SUBJECT", subject);
            return this;
        }
    }

    public static class IntentReader {
        private static final String TAG = "IntentReader";
        private final ComponentName mCallingActivity;
        private final String mCallingPackage;
        private final Context mContext;
        private final Intent mIntent;
        private ArrayList<Uri> mStreams;

        @Deprecated
        public static IntentReader from(Activity activity) {
            return new IntentReader(activity);
        }

        public IntentReader(Activity activity) {
            this((Context) Preconditions.checkNotNull(activity), activity.getIntent());
        }

        public IntentReader(Context context, Intent intent) {
            this.mContext = (Context) Preconditions.checkNotNull(context);
            this.mIntent = (Intent) Preconditions.checkNotNull(intent);
            this.mCallingPackage = ShareCompat.getCallingPackage(intent);
            this.mCallingActivity = ShareCompat.getCallingActivity(intent);
        }

        public boolean isShareIntent() {
            String action = this.mIntent.getAction();
            return "android.intent.action.SEND".equals(action) || "android.intent.action.SEND_MULTIPLE".equals(action);
        }

        public boolean isSingleShare() {
            return "android.intent.action.SEND".equals(this.mIntent.getAction());
        }

        public boolean isMultipleShare() {
            return "android.intent.action.SEND_MULTIPLE".equals(this.mIntent.getAction());
        }

        public String getType() {
            return this.mIntent.getType();
        }

        public CharSequence getText() {
            return this.mIntent.getCharSequenceExtra("android.intent.extra.TEXT");
        }

        public String getHtmlText() {
            String result = this.mIntent.getStringExtra(IntentCompat.EXTRA_HTML_TEXT);
            if (result != null) {
                return result;
            }
            CharSequence text = getText();
            if (text instanceof Spanned) {
                return Html.toHtml((Spanned) text);
            }
            if (text != null) {
                return Api16Impl.escapeHtml(text);
            }
            return result;
        }

        private static void withinStyle(StringBuilder out, CharSequence text, int start, int end) {
            int i = start;
            while (i < end) {
                char c = text.charAt(i);
                if (c == '<') {
                    out.append("&lt;");
                } else if (c == '>') {
                    out.append("&gt;");
                } else if (c == '&') {
                    out.append("&amp;");
                } else if (c > '~' || c < ' ') {
                    out.append("&#").append(c).append(";");
                } else if (c == ' ') {
                    while (i + 1 < end && text.charAt(i + 1) == ' ') {
                        out.append("&nbsp;");
                        i++;
                    }
                    out.append(' ');
                } else {
                    out.append(c);
                }
                i++;
            }
        }

        public Uri getStream() {
            return (Uri) this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
        }

        public Uri getStream(int index) {
            if (this.mStreams == null && isMultipleShare()) {
                this.mStreams = this.mIntent.getParcelableArrayListExtra("android.intent.extra.STREAM");
            }
            ArrayList<Uri> arrayList = this.mStreams;
            if (arrayList != null) {
                return arrayList.get(index);
            }
            if (index == 0) {
                return (Uri) this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
            }
            throw new IndexOutOfBoundsException("Stream items available: " + getStreamCount() + " index requested: " + index);
        }

        public int getStreamCount() {
            if (this.mStreams == null && isMultipleShare()) {
                this.mStreams = this.mIntent.getParcelableArrayListExtra("android.intent.extra.STREAM");
            }
            ArrayList<Uri> arrayList = this.mStreams;
            if (arrayList != null) {
                return arrayList.size();
            }
            return this.mIntent.hasExtra("android.intent.extra.STREAM") ? 1 : 0;
        }

        public String[] getEmailTo() {
            return this.mIntent.getStringArrayExtra("android.intent.extra.EMAIL");
        }

        public String[] getEmailCc() {
            return this.mIntent.getStringArrayExtra("android.intent.extra.CC");
        }

        public String[] getEmailBcc() {
            return this.mIntent.getStringArrayExtra("android.intent.extra.BCC");
        }

        public String getSubject() {
            return this.mIntent.getStringExtra("android.intent.extra.SUBJECT");
        }

        public String getCallingPackage() {
            return this.mCallingPackage;
        }

        public ComponentName getCallingActivity() {
            return this.mCallingActivity;
        }

        public Drawable getCallingActivityIcon() {
            if (this.mCallingActivity == null) {
                return null;
            }
            try {
                return this.mContext.getPackageManager().getActivityIcon(this.mCallingActivity);
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG, "Could not retrieve icon for calling activity", e);
                return null;
            }
        }

        public Drawable getCallingApplicationIcon() {
            if (this.mCallingPackage == null) {
                return null;
            }
            try {
                return this.mContext.getPackageManager().getApplicationIcon(this.mCallingPackage);
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG, "Could not retrieve icon for calling application", e);
                return null;
            }
        }

        public CharSequence getCallingApplicationLabel() {
            if (this.mCallingPackage == null) {
                return null;
            }
            PackageManager pm = this.mContext.getPackageManager();
            try {
                return pm.getApplicationLabel(pm.getApplicationInfo(this.mCallingPackage, 0));
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(TAG, "Could not retrieve label for calling application", e);
                return null;
            }
        }
    }

    private static class Api16Impl {
        private Api16Impl() {
        }

        static void migrateExtraStreamToClipData(Intent intent, ArrayList<Uri> streams) {
            CharSequence text = intent.getCharSequenceExtra("android.intent.extra.TEXT");
            String htmlText = intent.getStringExtra(IntentCompat.EXTRA_HTML_TEXT);
            ClipData clipData = new ClipData((CharSequence) null, new String[]{intent.getType()}, new ClipData.Item(text, htmlText, (Intent) null, streams.get(0)));
            int end = streams.size();
            for (int i = 1; i < end; i++) {
                clipData.addItem(new ClipData.Item(streams.get(i)));
            }
            intent.setClipData(clipData);
            intent.addFlags(1);
        }

        static void removeClipData(Intent intent) {
            intent.setClipData((ClipData) null);
            intent.setFlags(intent.getFlags() & -2);
        }

        static String escapeHtml(CharSequence text) {
            return Html.escapeHtml(text);
        }
    }
}
