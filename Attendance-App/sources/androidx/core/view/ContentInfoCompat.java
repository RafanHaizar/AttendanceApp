package androidx.core.view;

import android.content.ClipData;
import android.content.ClipDescription;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.ContentInfo;
import androidx.core.util.Preconditions;
import androidx.core.util.Predicate;
import com.itextpdf.kernel.xmp.PdfConst;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ContentInfoCompat {
    public static final int FLAG_CONVERT_TO_PLAIN_TEXT = 1;
    public static final int SOURCE_APP = 0;
    public static final int SOURCE_AUTOFILL = 4;
    public static final int SOURCE_CLIPBOARD = 1;
    public static final int SOURCE_DRAG_AND_DROP = 3;
    public static final int SOURCE_INPUT_METHOD = 2;
    public static final int SOURCE_PROCESS_TEXT = 5;
    private final Compat mCompat;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Flags {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Source {
    }

    private interface BuilderCompat {
        ContentInfoCompat build();

        void setClip(ClipData clipData);

        void setExtras(Bundle bundle);

        void setFlags(int i);

        void setLinkUri(Uri uri);

        void setSource(int i);
    }

    private interface Compat {
        ClipData getClip();

        Bundle getExtras();

        int getFlags();

        Uri getLinkUri();

        int getSource();

        ContentInfo getWrapped();
    }

    static String sourceToString(int source) {
        switch (source) {
            case 0:
                return "SOURCE_APP";
            case 1:
                return "SOURCE_CLIPBOARD";
            case 2:
                return "SOURCE_INPUT_METHOD";
            case 3:
                return "SOURCE_DRAG_AND_DROP";
            case 4:
                return "SOURCE_AUTOFILL";
            case 5:
                return "SOURCE_PROCESS_TEXT";
            default:
                return String.valueOf(source);
        }
    }

    static String flagsToString(int flags) {
        if ((flags & 1) != 0) {
            return "FLAG_CONVERT_TO_PLAIN_TEXT";
        }
        return String.valueOf(flags);
    }

    ContentInfoCompat(Compat compat) {
        this.mCompat = compat;
    }

    public static ContentInfoCompat toContentInfoCompat(ContentInfo platContentInfo) {
        return new ContentInfoCompat(new Compat31Impl(platContentInfo));
    }

    public ContentInfo toContentInfo() {
        return (ContentInfo) Objects.requireNonNull(this.mCompat.getWrapped());
    }

    public String toString() {
        return this.mCompat.toString();
    }

    public ClipData getClip() {
        return this.mCompat.getClip();
    }

    public int getSource() {
        return this.mCompat.getSource();
    }

    public int getFlags() {
        return this.mCompat.getFlags();
    }

    public Uri getLinkUri() {
        return this.mCompat.getLinkUri();
    }

    public Bundle getExtras() {
        return this.mCompat.getExtras();
    }

    public Pair<ContentInfoCompat, ContentInfoCompat> partition(Predicate<ClipData.Item> itemPredicate) {
        ClipData clip = this.mCompat.getClip();
        ContentInfoCompat contentInfoCompat = null;
        if (clip.getItemCount() == 1) {
            boolean matched = itemPredicate.test(clip.getItemAt(0));
            ContentInfoCompat contentInfoCompat2 = matched ? this : null;
            if (!matched) {
                contentInfoCompat = this;
            }
            return Pair.create(contentInfoCompat2, contentInfoCompat);
        }
        Pair<ClipData, ClipData> split = partition(clip, itemPredicate);
        if (split.first == null) {
            return Pair.create((Object) null, this);
        }
        if (split.second == null) {
            return Pair.create(this, (Object) null);
        }
        return Pair.create(new Builder(this).setClip((ClipData) split.first).build(), new Builder(this).setClip((ClipData) split.second).build());
    }

    static Pair<ClipData, ClipData> partition(ClipData clip, Predicate<ClipData.Item> itemPredicate) {
        ArrayList<ClipData.Item> acceptedItems = null;
        ArrayList<ClipData.Item> remainingItems = null;
        for (int i = 0; i < clip.getItemCount(); i++) {
            ClipData.Item item = clip.getItemAt(i);
            if (itemPredicate.test(item)) {
                acceptedItems = acceptedItems == null ? new ArrayList<>() : acceptedItems;
                acceptedItems.add(item);
            } else {
                remainingItems = remainingItems == null ? new ArrayList<>() : remainingItems;
                remainingItems.add(item);
            }
        }
        if (acceptedItems == null) {
            return Pair.create((Object) null, clip);
        }
        if (remainingItems == null) {
            return Pair.create(clip, (Object) null);
        }
        return Pair.create(buildClipData(clip.getDescription(), acceptedItems), buildClipData(clip.getDescription(), remainingItems));
    }

    static ClipData buildClipData(ClipDescription description, List<ClipData.Item> items) {
        ClipData clip = new ClipData(new ClipDescription(description), items.get(0));
        for (int i = 1; i < items.size(); i++) {
            clip.addItem(items.get(i));
        }
        return clip;
    }

    public static Pair<ContentInfo, ContentInfo> partition(ContentInfo payload, java.util.function.Predicate<ClipData.Item> itemPredicate) {
        return Api31Impl.partition(payload, itemPredicate);
    }

    private static final class Api31Impl {
        private Api31Impl() {
        }

        public static Pair<ContentInfo, ContentInfo> partition(ContentInfo payload, java.util.function.Predicate<ClipData.Item> itemPredicate) {
            ClipData clip = payload.getClip();
            ContentInfo contentInfo = null;
            if (clip.getItemCount() == 1) {
                boolean matched = itemPredicate.test(clip.getItemAt(0));
                ContentInfo contentInfo2 = matched ? payload : null;
                if (!matched) {
                    contentInfo = payload;
                }
                return Pair.create(contentInfo2, contentInfo);
            }
            Objects.requireNonNull(itemPredicate);
            Pair<ClipData, ClipData> split = ContentInfoCompat.partition(clip, (Predicate<ClipData.Item>) new ContentInfoCompat$Api31Impl$$ExternalSyntheticLambda0(itemPredicate));
            if (split.first == null) {
                return Pair.create((Object) null, payload);
            }
            if (split.second == null) {
                return Pair.create(payload, (Object) null);
            }
            return Pair.create(new ContentInfo.Builder(payload).setClip((ClipData) split.first).build(), new ContentInfo.Builder(payload).setClip((ClipData) split.second).build());
        }
    }

    private static final class CompatImpl implements Compat {
        private final ClipData mClip;
        private final Bundle mExtras;
        private final int mFlags;
        private final Uri mLinkUri;
        private final int mSource;

        CompatImpl(BuilderCompatImpl b) {
            this.mClip = (ClipData) Preconditions.checkNotNull(b.mClip);
            this.mSource = Preconditions.checkArgumentInRange(b.mSource, 0, 5, PdfConst.Source);
            this.mFlags = Preconditions.checkFlagsArgument(b.mFlags, 1);
            this.mLinkUri = b.mLinkUri;
            this.mExtras = b.mExtras;
        }

        public ContentInfo getWrapped() {
            return null;
        }

        public ClipData getClip() {
            return this.mClip;
        }

        public int getSource() {
            return this.mSource;
        }

        public int getFlags() {
            return this.mFlags;
        }

        public Uri getLinkUri() {
            return this.mLinkUri;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public String toString() {
            String str = "";
            StringBuilder append = new StringBuilder().append("ContentInfoCompat{clip=").append(this.mClip.getDescription()).append(", source=").append(ContentInfoCompat.sourceToString(this.mSource)).append(", flags=").append(ContentInfoCompat.flagsToString(this.mFlags)).append(this.mLinkUri == null ? str : ", hasLinkUri(" + this.mLinkUri.toString().length() + ")");
            if (this.mExtras != null) {
                str = ", hasExtras";
            }
            return append.append(str).append("}").toString();
        }
    }

    private static final class Compat31Impl implements Compat {
        private final ContentInfo mWrapped;

        Compat31Impl(ContentInfo wrapped) {
            this.mWrapped = (ContentInfo) Preconditions.checkNotNull(wrapped);
        }

        public ContentInfo getWrapped() {
            return this.mWrapped;
        }

        public ClipData getClip() {
            return this.mWrapped.getClip();
        }

        public int getSource() {
            return this.mWrapped.getSource();
        }

        public int getFlags() {
            return this.mWrapped.getFlags();
        }

        public Uri getLinkUri() {
            return this.mWrapped.getLinkUri();
        }

        public Bundle getExtras() {
            return this.mWrapped.getExtras();
        }

        public String toString() {
            return "ContentInfoCompat{" + this.mWrapped + "}";
        }
    }

    public static final class Builder {
        private final BuilderCompat mBuilderCompat;

        public Builder(ContentInfoCompat other) {
            if (Build.VERSION.SDK_INT >= 31) {
                this.mBuilderCompat = new BuilderCompat31Impl(other);
            } else {
                this.mBuilderCompat = new BuilderCompatImpl(other);
            }
        }

        public Builder(ClipData clip, int source) {
            if (Build.VERSION.SDK_INT >= 31) {
                this.mBuilderCompat = new BuilderCompat31Impl(clip, source);
            } else {
                this.mBuilderCompat = new BuilderCompatImpl(clip, source);
            }
        }

        public Builder setClip(ClipData clip) {
            this.mBuilderCompat.setClip(clip);
            return this;
        }

        public Builder setSource(int source) {
            this.mBuilderCompat.setSource(source);
            return this;
        }

        public Builder setFlags(int flags) {
            this.mBuilderCompat.setFlags(flags);
            return this;
        }

        public Builder setLinkUri(Uri linkUri) {
            this.mBuilderCompat.setLinkUri(linkUri);
            return this;
        }

        public Builder setExtras(Bundle extras) {
            this.mBuilderCompat.setExtras(extras);
            return this;
        }

        public ContentInfoCompat build() {
            return this.mBuilderCompat.build();
        }
    }

    private static final class BuilderCompatImpl implements BuilderCompat {
        ClipData mClip;
        Bundle mExtras;
        int mFlags;
        Uri mLinkUri;
        int mSource;

        BuilderCompatImpl(ClipData clip, int source) {
            this.mClip = clip;
            this.mSource = source;
        }

        BuilderCompatImpl(ContentInfoCompat other) {
            this.mClip = other.getClip();
            this.mSource = other.getSource();
            this.mFlags = other.getFlags();
            this.mLinkUri = other.getLinkUri();
            this.mExtras = other.getExtras();
        }

        public void setClip(ClipData clip) {
            this.mClip = clip;
        }

        public void setSource(int source) {
            this.mSource = source;
        }

        public void setFlags(int flags) {
            this.mFlags = flags;
        }

        public void setLinkUri(Uri linkUri) {
            this.mLinkUri = linkUri;
        }

        public void setExtras(Bundle extras) {
            this.mExtras = extras;
        }

        public ContentInfoCompat build() {
            return new ContentInfoCompat(new CompatImpl(this));
        }
    }

    private static final class BuilderCompat31Impl implements BuilderCompat {
        private final ContentInfo.Builder mPlatformBuilder;

        BuilderCompat31Impl(ClipData clip, int source) {
            this.mPlatformBuilder = new ContentInfo.Builder(clip, source);
        }

        BuilderCompat31Impl(ContentInfoCompat other) {
            this.mPlatformBuilder = new ContentInfo.Builder(other.toContentInfo());
        }

        public void setClip(ClipData clip) {
            this.mPlatformBuilder.setClip(clip);
        }

        public void setSource(int source) {
            this.mPlatformBuilder.setSource(source);
        }

        public void setFlags(int flags) {
            this.mPlatformBuilder.setFlags(flags);
        }

        public void setLinkUri(Uri linkUri) {
            this.mPlatformBuilder.setLinkUri(linkUri);
        }

        public void setExtras(Bundle extras) {
            this.mPlatformBuilder.setExtras(extras);
        }

        public ContentInfoCompat build() {
            return new ContentInfoCompat(new Compat31Impl(this.mPlatformBuilder.build()));
        }
    }
}
