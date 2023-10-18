package androidx.localbroadcastmanager.content;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import java.util.ArrayList;
import java.util.HashMap;

public final class LocalBroadcastManager {
    private static final boolean DEBUG = false;
    static final int MSG_EXEC_PENDING_BROADCASTS = 1;
    private static final String TAG = "LocalBroadcastManager";
    private static LocalBroadcastManager mInstance;
    private static final Object mLock = new Object();
    private final HashMap<String, ArrayList<ReceiverRecord>> mActions = new HashMap<>();
    private final Context mAppContext;
    private final Handler mHandler;
    private final ArrayList<BroadcastRecord> mPendingBroadcasts = new ArrayList<>();
    private final HashMap<BroadcastReceiver, ArrayList<ReceiverRecord>> mReceivers = new HashMap<>();

    private static final class ReceiverRecord {
        boolean broadcasting;
        boolean dead;
        final IntentFilter filter;
        final BroadcastReceiver receiver;

        ReceiverRecord(IntentFilter _filter, BroadcastReceiver _receiver) {
            this.filter = _filter;
            this.receiver = _receiver;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder(128);
            builder.append("Receiver{");
            builder.append(this.receiver);
            builder.append(" filter=");
            builder.append(this.filter);
            if (this.dead) {
                builder.append(" DEAD");
            }
            builder.append("}");
            return builder.toString();
        }
    }

    private static final class BroadcastRecord {
        final Intent intent;
        final ArrayList<ReceiverRecord> receivers;

        BroadcastRecord(Intent _intent, ArrayList<ReceiverRecord> _receivers) {
            this.intent = _intent;
            this.receivers = _receivers;
        }
    }

    public static LocalBroadcastManager getInstance(Context context) {
        LocalBroadcastManager localBroadcastManager;
        synchronized (mLock) {
            if (mInstance == null) {
                mInstance = new LocalBroadcastManager(context.getApplicationContext());
            }
            localBroadcastManager = mInstance;
        }
        return localBroadcastManager;
    }

    private LocalBroadcastManager(Context context) {
        this.mAppContext = context;
        this.mHandler = new Handler(context.getMainLooper()) {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        LocalBroadcastManager.this.executePendingBroadcasts();
                        return;
                    default:
                        super.handleMessage(msg);
                        return;
                }
            }
        };
    }

    public void registerReceiver(BroadcastReceiver receiver, IntentFilter filter) {
        synchronized (this.mReceivers) {
            ReceiverRecord entry = new ReceiverRecord(filter, receiver);
            ArrayList<ReceiverRecord> filters = this.mReceivers.get(receiver);
            if (filters == null) {
                filters = new ArrayList<>(1);
                this.mReceivers.put(receiver, filters);
            }
            filters.add(entry);
            for (int i = 0; i < filter.countActions(); i++) {
                String action = filter.getAction(i);
                ArrayList<ReceiverRecord> entries = this.mActions.get(action);
                if (entries == null) {
                    entries = new ArrayList<>(1);
                    this.mActions.put(action, entries);
                }
                entries.add(entry);
            }
        }
    }

    public void unregisterReceiver(BroadcastReceiver receiver) {
        synchronized (this.mReceivers) {
            ArrayList<ReceiverRecord> filters = this.mReceivers.remove(receiver);
            if (filters != null) {
                for (int i = filters.size() - 1; i >= 0; i--) {
                    ReceiverRecord filter = filters.get(i);
                    filter.dead = true;
                    for (int j = 0; j < filter.filter.countActions(); j++) {
                        String action = filter.filter.getAction(j);
                        ArrayList<ReceiverRecord> receivers = this.mActions.get(action);
                        if (receivers != null) {
                            for (int k = receivers.size() - 1; k >= 0; k--) {
                                ReceiverRecord rec = receivers.get(k);
                                if (rec.receiver == receiver) {
                                    rec.dead = true;
                                    receivers.remove(k);
                                }
                            }
                            if (receivers.size() <= 0) {
                                this.mActions.remove(action);
                            }
                        }
                    }
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0178, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x017d, code lost:
        return false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean sendBroadcast(android.content.Intent r19) {
        /*
            r18 = this;
            r1 = r18
            r2 = r19
            java.util.HashMap<android.content.BroadcastReceiver, java.util.ArrayList<androidx.localbroadcastmanager.content.LocalBroadcastManager$ReceiverRecord>> r3 = r1.mReceivers
            monitor-enter(r3)
            java.lang.String r5 = r19.getAction()     // Catch:{ all -> 0x017f }
            android.content.Context r0 = r1.mAppContext     // Catch:{ all -> 0x017f }
            android.content.ContentResolver r0 = r0.getContentResolver()     // Catch:{ all -> 0x017f }
            java.lang.String r0 = r2.resolveTypeIfNeeded(r0)     // Catch:{ all -> 0x017f }
            android.net.Uri r8 = r19.getData()     // Catch:{ all -> 0x017f }
            java.lang.String r4 = r19.getScheme()     // Catch:{ all -> 0x017f }
            r11 = r4
            java.util.Set r9 = r19.getCategories()     // Catch:{ all -> 0x017f }
            int r4 = r19.getFlags()     // Catch:{ all -> 0x017f }
            r4 = r4 & 8
            if (r4 == 0) goto L_0x002d
            r4 = 1
            goto L_0x002e
        L_0x002d:
            r4 = 0
        L_0x002e:
            r14 = r4
            if (r14 == 0) goto L_0x005d
            java.lang.String r4 = "LocalBroadcastManager"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x017f }
            r6.<init>()     // Catch:{ all -> 0x017f }
            java.lang.String r7 = "Resolving type "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ all -> 0x017f }
            java.lang.StringBuilder r6 = r6.append(r0)     // Catch:{ all -> 0x017f }
            java.lang.String r7 = " scheme "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ all -> 0x017f }
            java.lang.StringBuilder r6 = r6.append(r11)     // Catch:{ all -> 0x017f }
            java.lang.String r7 = " of intent "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ all -> 0x017f }
            java.lang.StringBuilder r6 = r6.append(r2)     // Catch:{ all -> 0x017f }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x017f }
            android.util.Log.v(r4, r6)     // Catch:{ all -> 0x017f }
        L_0x005d:
            java.util.HashMap<java.lang.String, java.util.ArrayList<androidx.localbroadcastmanager.content.LocalBroadcastManager$ReceiverRecord>> r4 = r1.mActions     // Catch:{ all -> 0x017f }
            java.lang.String r6 = r19.getAction()     // Catch:{ all -> 0x017f }
            java.lang.Object r4 = r4.get(r6)     // Catch:{ all -> 0x017f }
            java.util.ArrayList r4 = (java.util.ArrayList) r4     // Catch:{ all -> 0x017f }
            r15 = r4
            if (r15 == 0) goto L_0x017a
            if (r14 == 0) goto L_0x0086
            java.lang.String r4 = "LocalBroadcastManager"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x017f }
            r6.<init>()     // Catch:{ all -> 0x017f }
            java.lang.String r7 = "Action list: "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ all -> 0x017f }
            java.lang.StringBuilder r6 = r6.append(r15)     // Catch:{ all -> 0x017f }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x017f }
            android.util.Log.v(r4, r6)     // Catch:{ all -> 0x017f }
        L_0x0086:
            r4 = 0
            r6 = 0
            r10 = r4
            r7 = r6
        L_0x008a:
            int r4 = r15.size()     // Catch:{ all -> 0x017f }
            if (r7 >= r4) goto L_0x0145
            java.lang.Object r4 = r15.get(r7)     // Catch:{ all -> 0x017f }
            androidx.localbroadcastmanager.content.LocalBroadcastManager$ReceiverRecord r4 = (androidx.localbroadcastmanager.content.LocalBroadcastManager.ReceiverRecord) r4     // Catch:{ all -> 0x017f }
            r6 = r4
            if (r14 == 0) goto L_0x00b3
            java.lang.String r4 = "LocalBroadcastManager"
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x017f }
            r12.<init>()     // Catch:{ all -> 0x017f }
            java.lang.String r13 = "Matching against filter "
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ all -> 0x017f }
            android.content.IntentFilter r13 = r6.filter     // Catch:{ all -> 0x017f }
            java.lang.StringBuilder r12 = r12.append(r13)     // Catch:{ all -> 0x017f }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x017f }
            android.util.Log.v(r4, r12)     // Catch:{ all -> 0x017f }
        L_0x00b3:
            boolean r4 = r6.broadcasting     // Catch:{ all -> 0x017f }
            if (r4 == 0) goto L_0x00ce
            if (r14 == 0) goto L_0x00c7
            java.lang.String r4 = "LocalBroadcastManager"
            java.lang.String r12 = "  Filter's target already added"
            android.util.Log.v(r4, r12)     // Catch:{ all -> 0x017f }
            r17 = r0
            r16 = r7
            r0 = r10
            goto L_0x013e
        L_0x00c7:
            r17 = r0
            r16 = r7
            r0 = r10
            goto L_0x013e
        L_0x00ce:
            android.content.IntentFilter r4 = r6.filter     // Catch:{ all -> 0x017f }
            java.lang.String r12 = "LocalBroadcastManager"
            r13 = r6
            r6 = r0
            r16 = r7
            r7 = r11
            r17 = r0
            r0 = r10
            r10 = r12
            int r4 = r4.match(r5, r6, r7, r8, r9, r10)     // Catch:{ all -> 0x017f }
            if (r4 < 0) goto L_0x0110
            if (r14 == 0) goto L_0x00ff
            java.lang.String r6 = "LocalBroadcastManager"
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x017f }
            r7.<init>()     // Catch:{ all -> 0x017f }
            java.lang.String r10 = "  Filter matched!  match=0x"
            java.lang.StringBuilder r7 = r7.append(r10)     // Catch:{ all -> 0x017f }
            java.lang.String r10 = java.lang.Integer.toHexString(r4)     // Catch:{ all -> 0x017f }
            java.lang.StringBuilder r7 = r7.append(r10)     // Catch:{ all -> 0x017f }
            java.lang.String r7 = r7.toString()     // Catch:{ all -> 0x017f }
            android.util.Log.v(r6, r7)     // Catch:{ all -> 0x017f }
        L_0x00ff:
            if (r0 != 0) goto L_0x0108
            java.util.ArrayList r6 = new java.util.ArrayList     // Catch:{ all -> 0x017f }
            r6.<init>()     // Catch:{ all -> 0x017f }
            r10 = r6
            goto L_0x0109
        L_0x0108:
            r10 = r0
        L_0x0109:
            r10.add(r13)     // Catch:{ all -> 0x017f }
            r0 = 1
            r13.broadcasting = r0     // Catch:{ all -> 0x017f }
            goto L_0x013f
        L_0x0110:
            if (r14 == 0) goto L_0x013e
            switch(r4) {
                case -4: goto L_0x0123;
                case -3: goto L_0x0120;
                case -2: goto L_0x011d;
                case -1: goto L_0x0119;
                default: goto L_0x0115;
            }     // Catch:{ all -> 0x017f }
        L_0x0115:
            java.lang.String r6 = "unknown reason"
            goto L_0x0126
        L_0x0119:
            java.lang.String r6 = "type"
            goto L_0x0126
        L_0x011d:
            java.lang.String r6 = "data"
            goto L_0x0126
        L_0x0120:
            java.lang.String r6 = "action"
            goto L_0x0126
        L_0x0123:
            java.lang.String r6 = "category"
        L_0x0126:
            java.lang.String r7 = "LocalBroadcastManager"
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x017f }
            r10.<init>()     // Catch:{ all -> 0x017f }
            java.lang.String r12 = "  Filter did not match: "
            java.lang.StringBuilder r10 = r10.append(r12)     // Catch:{ all -> 0x017f }
            java.lang.StringBuilder r10 = r10.append(r6)     // Catch:{ all -> 0x017f }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x017f }
            android.util.Log.v(r7, r10)     // Catch:{ all -> 0x017f }
        L_0x013e:
            r10 = r0
        L_0x013f:
            int r7 = r16 + 1
            r0 = r17
            goto L_0x008a
        L_0x0145:
            r17 = r0
            r16 = r7
            r0 = r10
            if (r0 == 0) goto L_0x017c
            r4 = 0
        L_0x014d:
            int r6 = r0.size()     // Catch:{ all -> 0x017f }
            if (r4 >= r6) goto L_0x015f
            java.lang.Object r6 = r0.get(r4)     // Catch:{ all -> 0x017f }
            androidx.localbroadcastmanager.content.LocalBroadcastManager$ReceiverRecord r6 = (androidx.localbroadcastmanager.content.LocalBroadcastManager.ReceiverRecord) r6     // Catch:{ all -> 0x017f }
            r7 = 0
            r6.broadcasting = r7     // Catch:{ all -> 0x017f }
            int r4 = r4 + 1
            goto L_0x014d
        L_0x015f:
            java.util.ArrayList<androidx.localbroadcastmanager.content.LocalBroadcastManager$BroadcastRecord> r4 = r1.mPendingBroadcasts     // Catch:{ all -> 0x017f }
            androidx.localbroadcastmanager.content.LocalBroadcastManager$BroadcastRecord r6 = new androidx.localbroadcastmanager.content.LocalBroadcastManager$BroadcastRecord     // Catch:{ all -> 0x017f }
            r6.<init>(r2, r0)     // Catch:{ all -> 0x017f }
            r4.add(r6)     // Catch:{ all -> 0x017f }
            android.os.Handler r4 = r1.mHandler     // Catch:{ all -> 0x017f }
            r6 = 1
            boolean r4 = r4.hasMessages(r6)     // Catch:{ all -> 0x017f }
            if (r4 != 0) goto L_0x0177
            android.os.Handler r4 = r1.mHandler     // Catch:{ all -> 0x017f }
            r4.sendEmptyMessage(r6)     // Catch:{ all -> 0x017f }
        L_0x0177:
            monitor-exit(r3)     // Catch:{ all -> 0x017f }
            r3 = 1
            return r3
        L_0x017a:
            r17 = r0
        L_0x017c:
            monitor-exit(r3)     // Catch:{ all -> 0x017f }
            r0 = 0
            return r0
        L_0x017f:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x017f }
            goto L_0x0183
        L_0x0182:
            throw r0
        L_0x0183:
            goto L_0x0182
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.localbroadcastmanager.content.LocalBroadcastManager.sendBroadcast(android.content.Intent):boolean");
    }

    public void sendBroadcastSync(Intent intent) {
        if (sendBroadcast(intent)) {
            executePendingBroadcasts();
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x001b, code lost:
        r1 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001d, code lost:
        if (r1 >= r0.length) goto L_0x0001;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:15:0x001f, code lost:
        r2 = r0[r1];
        r3 = r2.receivers.size();
        r4 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0028, code lost:
        if (r4 >= r3) goto L_0x0042;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x002a, code lost:
        r5 = r2.receivers.get(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0034, code lost:
        if (r5.dead != false) goto L_0x003f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0036, code lost:
        r5.receiver.onReceive(r9.mAppContext, r2.intent);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x003f, code lost:
        r4 = r4 + 1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0042, code lost:
        r1 = r1 + 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void executePendingBroadcasts() {
        /*
            r9 = this;
            r0 = 0
        L_0x0001:
            java.util.HashMap<android.content.BroadcastReceiver, java.util.ArrayList<androidx.localbroadcastmanager.content.LocalBroadcastManager$ReceiverRecord>> r1 = r9.mReceivers
            monitor-enter(r1)
            java.util.ArrayList<androidx.localbroadcastmanager.content.LocalBroadcastManager$BroadcastRecord> r2 = r9.mPendingBroadcasts     // Catch:{ all -> 0x0046 }
            int r2 = r2.size()     // Catch:{ all -> 0x0046 }
            if (r2 > 0) goto L_0x000e
            monitor-exit(r1)     // Catch:{ all -> 0x0046 }
            return
        L_0x000e:
            androidx.localbroadcastmanager.content.LocalBroadcastManager$BroadcastRecord[] r0 = new androidx.localbroadcastmanager.content.LocalBroadcastManager.BroadcastRecord[r2]     // Catch:{ all -> 0x0046 }
            java.util.ArrayList<androidx.localbroadcastmanager.content.LocalBroadcastManager$BroadcastRecord> r3 = r9.mPendingBroadcasts     // Catch:{ all -> 0x0049 }
            r3.toArray(r0)     // Catch:{ all -> 0x0049 }
            java.util.ArrayList<androidx.localbroadcastmanager.content.LocalBroadcastManager$BroadcastRecord> r3 = r9.mPendingBroadcasts     // Catch:{ all -> 0x0049 }
            r3.clear()     // Catch:{ all -> 0x0049 }
            monitor-exit(r1)     // Catch:{ all -> 0x0049 }
            r1 = 0
        L_0x001c:
            int r2 = r0.length
            if (r1 >= r2) goto L_0x0045
            r2 = r0[r1]
            java.util.ArrayList<androidx.localbroadcastmanager.content.LocalBroadcastManager$ReceiverRecord> r3 = r2.receivers
            int r3 = r3.size()
            r4 = 0
        L_0x0028:
            if (r4 >= r3) goto L_0x0042
            java.util.ArrayList<androidx.localbroadcastmanager.content.LocalBroadcastManager$ReceiverRecord> r5 = r2.receivers
            java.lang.Object r5 = r5.get(r4)
            androidx.localbroadcastmanager.content.LocalBroadcastManager$ReceiverRecord r5 = (androidx.localbroadcastmanager.content.LocalBroadcastManager.ReceiverRecord) r5
            boolean r6 = r5.dead
            if (r6 != 0) goto L_0x003f
            android.content.BroadcastReceiver r6 = r5.receiver
            android.content.Context r7 = r9.mAppContext
            android.content.Intent r8 = r2.intent
            r6.onReceive(r7, r8)
        L_0x003f:
            int r4 = r4 + 1
            goto L_0x0028
        L_0x0042:
            int r1 = r1 + 1
            goto L_0x001c
        L_0x0045:
            goto L_0x0001
        L_0x0046:
            r2 = move-exception
        L_0x0047:
            monitor-exit(r1)     // Catch:{ all -> 0x0049 }
            throw r2
        L_0x0049:
            r2 = move-exception
            goto L_0x0047
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.localbroadcastmanager.content.LocalBroadcastManager.executePendingBroadcasts():void");
    }
}
