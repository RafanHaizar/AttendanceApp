package androidx.core.app;

import android.app.Notification;
import android.app.RemoteInput;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.widget.RemoteViews;
import androidx.collection.ArraySet;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.drawable.IconCompat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

class NotificationCompatBuilder implements NotificationBuilderWithBuilderAccessor {
    private final List<Bundle> mActionExtrasList = new ArrayList();
    private RemoteViews mBigContentView;
    private final Notification.Builder mBuilder;
    private final NotificationCompat.Builder mBuilderCompat;
    private RemoteViews mContentView;
    private final Context mContext;
    private final Bundle mExtras = new Bundle();
    private int mGroupAlertBehavior;
    private RemoteViews mHeadsUpContentView;

    NotificationCompatBuilder(NotificationCompat.Builder b) {
        List<String> people;
        List<String> people2;
        this.mBuilderCompat = b;
        this.mContext = b.mContext;
        if (Build.VERSION.SDK_INT >= 26) {
            this.mBuilder = new Notification.Builder(b.mContext, b.mChannelId);
        } else {
            this.mBuilder = new Notification.Builder(b.mContext);
        }
        Notification n = b.mNotification;
        this.mBuilder.setWhen(n.when).setSmallIcon(n.icon, n.iconLevel).setContent(n.contentView).setTicker(n.tickerText, b.mTickerView).setVibrate(n.vibrate).setLights(n.ledARGB, n.ledOnMS, n.ledOffMS).setOngoing((n.flags & 2) != 0).setOnlyAlertOnce((n.flags & 8) != 0).setAutoCancel((n.flags & 16) != 0).setDefaults(n.defaults).setContentTitle(b.mContentTitle).setContentText(b.mContentText).setContentInfo(b.mContentInfo).setContentIntent(b.mContentIntent).setDeleteIntent(n.deleteIntent).setFullScreenIntent(b.mFullScreenIntent, (n.flags & 128) != 0).setLargeIcon(b.mLargeIcon).setNumber(b.mNumber).setProgress(b.mProgressMax, b.mProgress, b.mProgressIndeterminate);
        if (Build.VERSION.SDK_INT < 21) {
            this.mBuilder.setSound(n.sound, n.audioStreamType);
        }
        this.mBuilder.setSubText(b.mSubText).setUsesChronometer(b.mUseChronometer).setPriority(b.mPriority);
        Iterator<NotificationCompat.Action> it = b.mActions.iterator();
        while (it.hasNext()) {
            addAction(it.next());
        }
        if (b.mExtras != null) {
            this.mExtras.putAll(b.mExtras);
        }
        if (Build.VERSION.SDK_INT < 20) {
            if (b.mLocalOnly) {
                this.mExtras.putBoolean(NotificationCompatExtras.EXTRA_LOCAL_ONLY, true);
            }
            if (b.mGroupKey != null) {
                this.mExtras.putString(NotificationCompatExtras.EXTRA_GROUP_KEY, b.mGroupKey);
                if (b.mGroupSummary) {
                    this.mExtras.putBoolean(NotificationCompatExtras.EXTRA_GROUP_SUMMARY, true);
                } else {
                    this.mExtras.putBoolean(NotificationManagerCompat.EXTRA_USE_SIDE_CHANNEL, true);
                }
            }
            if (b.mSortKey != null) {
                this.mExtras.putString(NotificationCompatExtras.EXTRA_SORT_KEY, b.mSortKey);
            }
        }
        this.mContentView = b.mContentView;
        this.mBigContentView = b.mBigContentView;
        this.mBuilder.setShowWhen(b.mShowWhen);
        if (Build.VERSION.SDK_INT < 21 && (people2 = combineLists(getPeople(b.mPersonList), b.mPeople)) != null && !people2.isEmpty()) {
            this.mExtras.putStringArray(NotificationCompat.EXTRA_PEOPLE, (String[]) people2.toArray(new String[people2.size()]));
        }
        if (Build.VERSION.SDK_INT >= 20) {
            this.mBuilder.setLocalOnly(b.mLocalOnly).setGroup(b.mGroupKey).setGroupSummary(b.mGroupSummary).setSortKey(b.mSortKey);
            this.mGroupAlertBehavior = b.mGroupAlertBehavior;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            this.mBuilder.setCategory(b.mCategory).setColor(b.mColor).setVisibility(b.mVisibility).setPublicVersion(b.mPublicVersion).setSound(n.sound, n.audioAttributes);
            if (Build.VERSION.SDK_INT < 28) {
                people = combineLists(getPeople(b.mPersonList), b.mPeople);
            } else {
                people = b.mPeople;
            }
            if (people != null && !people.isEmpty()) {
                for (String person : people) {
                    this.mBuilder.addPerson(person);
                }
            }
            this.mHeadsUpContentView = b.mHeadsUpContentView;
            if (b.mInvisibleActions.size() > 0) {
                Bundle carExtenderBundle = b.getExtras().getBundle("android.car.EXTENSIONS");
                carExtenderBundle = carExtenderBundle == null ? new Bundle() : carExtenderBundle;
                Bundle extenderBundleCopy = new Bundle(carExtenderBundle);
                Bundle listBundle = new Bundle();
                for (int i = 0; i < b.mInvisibleActions.size(); i++) {
                    listBundle.putBundle(Integer.toString(i), NotificationCompatJellybean.getBundleForAction(b.mInvisibleActions.get(i)));
                }
                carExtenderBundle.putBundle("invisible_actions", listBundle);
                extenderBundleCopy.putBundle("invisible_actions", listBundle);
                b.getExtras().putBundle("android.car.EXTENSIONS", carExtenderBundle);
                this.mExtras.putBundle("android.car.EXTENSIONS", extenderBundleCopy);
            }
        }
        if (Build.VERSION.SDK_INT >= 23 && b.mSmallIcon != null) {
            this.mBuilder.setSmallIcon(b.mSmallIcon);
        }
        if (Build.VERSION.SDK_INT >= 24) {
            this.mBuilder.setExtras(b.mExtras).setRemoteInputHistory(b.mRemoteInputHistory);
            if (b.mContentView != null) {
                this.mBuilder.setCustomContentView(b.mContentView);
            }
            if (b.mBigContentView != null) {
                this.mBuilder.setCustomBigContentView(b.mBigContentView);
            }
            if (b.mHeadsUpContentView != null) {
                this.mBuilder.setCustomHeadsUpContentView(b.mHeadsUpContentView);
            }
        }
        if (Build.VERSION.SDK_INT >= 26) {
            this.mBuilder.setBadgeIconType(b.mBadgeIcon).setSettingsText(b.mSettingsText).setShortcutId(b.mShortcutId).setTimeoutAfter(b.mTimeout).setGroupAlertBehavior(b.mGroupAlertBehavior);
            if (b.mColorizedSet) {
                this.mBuilder.setColorized(b.mColorized);
            }
            if (!TextUtils.isEmpty(b.mChannelId)) {
                this.mBuilder.setSound((Uri) null).setDefaults(0).setLights(0, 0, 0).setVibrate((long[]) null);
            }
        }
        if (Build.VERSION.SDK_INT >= 28) {
            Iterator<Person> it2 = b.mPersonList.iterator();
            while (it2.hasNext()) {
                this.mBuilder.addPerson(it2.next().toAndroidPerson());
            }
        }
        if (Build.VERSION.SDK_INT >= 29) {
            this.mBuilder.setAllowSystemGeneratedContextualActions(b.mAllowSystemGeneratedContextualActions);
            this.mBuilder.setBubbleMetadata(NotificationCompat.BubbleMetadata.toPlatform(b.mBubbleMetadata));
            if (b.mLocusId != null) {
                this.mBuilder.setLocusId(b.mLocusId.toLocusId());
            }
        }
        if (Build.VERSION.SDK_INT >= 31 && b.mFgsDeferBehavior != 0) {
            this.mBuilder.setForegroundServiceBehavior(b.mFgsDeferBehavior);
        }
        if (b.mSilent) {
            if (this.mBuilderCompat.mGroupSummary) {
                this.mGroupAlertBehavior = 2;
            } else {
                this.mGroupAlertBehavior = 1;
            }
            this.mBuilder.setVibrate((long[]) null);
            this.mBuilder.setSound((Uri) null);
            n.defaults &= -2;
            n.defaults &= -3;
            this.mBuilder.setDefaults(n.defaults);
            if (Build.VERSION.SDK_INT >= 26) {
                if (TextUtils.isEmpty(this.mBuilderCompat.mGroupKey)) {
                    this.mBuilder.setGroup(NotificationCompat.GROUP_KEY_SILENT);
                }
                this.mBuilder.setGroupAlertBehavior(this.mGroupAlertBehavior);
            }
        }
    }

    private static List<String> combineLists(List<String> first, List<String> second) {
        if (first == null) {
            return second;
        }
        if (second == null) {
            return first;
        }
        ArraySet<String> people = new ArraySet<>(first.size() + second.size());
        people.addAll((Collection<? extends String>) first);
        people.addAll((Collection<? extends String>) second);
        return new ArrayList(people);
    }

    private static List<String> getPeople(List<Person> people) {
        if (people == null) {
            return null;
        }
        ArrayList<String> result = new ArrayList<>(people.size());
        for (Person person : people) {
            result.add(person.resolveToLegacyUri());
        }
        return result;
    }

    public Notification.Builder getBuilder() {
        return this.mBuilder;
    }

    /* access modifiers changed from: package-private */
    public Context getContext() {
        return this.mContext;
    }

    public Notification build() {
        RemoteViews styleContentView;
        Bundle extras;
        RemoteViews styleHeadsUpContentView;
        RemoteViews styleBigContentView;
        NotificationCompat.Style style = this.mBuilderCompat.mStyle;
        if (style != null) {
            style.apply(this);
        }
        if (style != null) {
            styleContentView = style.makeContentView(this);
        } else {
            styleContentView = null;
        }
        Notification n = buildInternal();
        if (styleContentView != null) {
            n.contentView = styleContentView;
        } else if (this.mBuilderCompat.mContentView != null) {
            n.contentView = this.mBuilderCompat.mContentView;
        }
        if (!(style == null || (styleBigContentView = style.makeBigContentView(this)) == null)) {
            n.bigContentView = styleBigContentView;
        }
        if (!(Build.VERSION.SDK_INT < 21 || style == null || (styleHeadsUpContentView = this.mBuilderCompat.mStyle.makeHeadsUpContentView(this)) == null)) {
            n.headsUpContentView = styleHeadsUpContentView;
        }
        if (!(style == null || (extras = NotificationCompat.getExtras(n)) == null)) {
            style.addCompatExtras(extras);
        }
        return n;
    }

    private void addAction(NotificationCompat.Action action) {
        Notification.Action.Builder actionBuilder;
        Bundle actionExtras;
        if (Build.VERSION.SDK_INT >= 20) {
            IconCompat iconCompat = action.getIconCompat();
            if (Build.VERSION.SDK_INT >= 23) {
                actionBuilder = new Notification.Action.Builder(iconCompat != null ? iconCompat.toIcon() : null, action.getTitle(), action.getActionIntent());
            } else {
                actionBuilder = new Notification.Action.Builder(iconCompat != null ? iconCompat.getResId() : 0, action.getTitle(), action.getActionIntent());
            }
            if (action.getRemoteInputs() != null) {
                for (RemoteInput remoteInput : RemoteInput.fromCompat(action.getRemoteInputs())) {
                    actionBuilder.addRemoteInput(remoteInput);
                }
            }
            if (action.getExtras() != null) {
                actionExtras = new Bundle(action.getExtras());
            } else {
                actionExtras = new Bundle();
            }
            actionExtras.putBoolean("android.support.allowGeneratedReplies", action.getAllowGeneratedReplies());
            if (Build.VERSION.SDK_INT >= 24) {
                actionBuilder.setAllowGeneratedReplies(action.getAllowGeneratedReplies());
            }
            actionExtras.putInt("android.support.action.semanticAction", action.getSemanticAction());
            if (Build.VERSION.SDK_INT >= 28) {
                actionBuilder.setSemanticAction(action.getSemanticAction());
            }
            if (Build.VERSION.SDK_INT >= 29) {
                actionBuilder.setContextual(action.isContextual());
            }
            if (Build.VERSION.SDK_INT >= 31) {
                actionBuilder.setAuthenticationRequired(action.isAuthenticationRequired());
            }
            actionExtras.putBoolean("android.support.action.showsUserInterface", action.getShowsUserInterface());
            actionBuilder.addExtras(actionExtras);
            this.mBuilder.addAction(actionBuilder.build());
            return;
        }
        this.mActionExtrasList.add(NotificationCompatJellybean.writeActionAndGetExtras(this.mBuilder, action));
    }

    /* access modifiers changed from: protected */
    public Notification buildInternal() {
        if (Build.VERSION.SDK_INT >= 26) {
            return this.mBuilder.build();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            Notification notification = this.mBuilder.build();
            if (this.mGroupAlertBehavior != 0) {
                if (!(notification.getGroup() == null || (notification.flags & 512) == 0 || this.mGroupAlertBehavior != 2)) {
                    removeSoundAndVibration(notification);
                }
                if (notification.getGroup() != null && (notification.flags & 512) == 0 && this.mGroupAlertBehavior == 1) {
                    removeSoundAndVibration(notification);
                }
            }
            return notification;
        } else if (Build.VERSION.SDK_INT >= 21) {
            this.mBuilder.setExtras(this.mExtras);
            Notification notification2 = this.mBuilder.build();
            RemoteViews remoteViews = this.mContentView;
            if (remoteViews != null) {
                notification2.contentView = remoteViews;
            }
            RemoteViews remoteViews2 = this.mBigContentView;
            if (remoteViews2 != null) {
                notification2.bigContentView = remoteViews2;
            }
            RemoteViews remoteViews3 = this.mHeadsUpContentView;
            if (remoteViews3 != null) {
                notification2.headsUpContentView = remoteViews3;
            }
            if (this.mGroupAlertBehavior != 0) {
                if (!(notification2.getGroup() == null || (notification2.flags & 512) == 0 || this.mGroupAlertBehavior != 2)) {
                    removeSoundAndVibration(notification2);
                }
                if (notification2.getGroup() != null && (notification2.flags & 512) == 0 && this.mGroupAlertBehavior == 1) {
                    removeSoundAndVibration(notification2);
                }
            }
            return notification2;
        } else if (Build.VERSION.SDK_INT >= 20) {
            this.mBuilder.setExtras(this.mExtras);
            Notification notification3 = this.mBuilder.build();
            RemoteViews remoteViews4 = this.mContentView;
            if (remoteViews4 != null) {
                notification3.contentView = remoteViews4;
            }
            RemoteViews remoteViews5 = this.mBigContentView;
            if (remoteViews5 != null) {
                notification3.bigContentView = remoteViews5;
            }
            if (this.mGroupAlertBehavior != 0) {
                if (!(notification3.getGroup() == null || (notification3.flags & 512) == 0 || this.mGroupAlertBehavior != 2)) {
                    removeSoundAndVibration(notification3);
                }
                if (notification3.getGroup() != null && (notification3.flags & 512) == 0 && this.mGroupAlertBehavior == 1) {
                    removeSoundAndVibration(notification3);
                }
            }
            return notification3;
        } else {
            SparseArray<Bundle> actionExtrasMap = NotificationCompatJellybean.buildActionExtrasMap(this.mActionExtrasList);
            if (actionExtrasMap != null) {
                this.mExtras.putSparseParcelableArray(NotificationCompatExtras.EXTRA_ACTION_EXTRAS, actionExtrasMap);
            }
            this.mBuilder.setExtras(this.mExtras);
            Notification notification4 = this.mBuilder.build();
            RemoteViews remoteViews6 = this.mContentView;
            if (remoteViews6 != null) {
                notification4.contentView = remoteViews6;
            }
            RemoteViews remoteViews7 = this.mBigContentView;
            if (remoteViews7 != null) {
                notification4.bigContentView = remoteViews7;
            }
            return notification4;
        }
    }

    private void removeSoundAndVibration(Notification notification) {
        notification.sound = null;
        notification.vibrate = null;
        notification.defaults &= -2;
        notification.defaults &= -3;
    }
}
