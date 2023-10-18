package com.google.android.material.badge;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import com.google.android.material.C1087R;
import com.google.android.material.drawable.DrawableUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import java.util.Locale;

public final class BadgeState {
    private static final String BADGE_RESOURCE_TAG = "badge";
    private static final int DEFAULT_MAX_BADGE_CHARACTER_COUNT = 4;
    final float badgeRadius;
    final float badgeWidePadding;
    final float badgeWithTextRadius;
    private final State currentState;
    private final State overridingState;

    BadgeState(Context context, int badgeResId, int defStyleAttr, int defStyleRes, State storedState) {
        CharSequence charSequence;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        Locale locale;
        State state = new State();
        this.currentState = state;
        storedState = storedState == null ? new State() : storedState;
        if (badgeResId != 0) {
            int unused = storedState.badgeResId = badgeResId;
        }
        TypedArray a = generateTypedArray(context, storedState.badgeResId, defStyleAttr, defStyleRes);
        Resources res = context.getResources();
        this.badgeRadius = (float) a.getDimensionPixelSize(C1087R.styleable.Badge_badgeRadius, res.getDimensionPixelSize(C1087R.dimen.mtrl_badge_radius));
        this.badgeWidePadding = (float) a.getDimensionPixelSize(C1087R.styleable.Badge_badgeWidePadding, res.getDimensionPixelSize(C1087R.dimen.mtrl_badge_long_text_horizontal_padding));
        this.badgeWithTextRadius = (float) a.getDimensionPixelSize(C1087R.styleable.Badge_badgeWithTextRadius, res.getDimensionPixelSize(C1087R.dimen.mtrl_badge_with_text_radius));
        int unused2 = state.alpha = storedState.alpha == -2 ? 255 : storedState.alpha;
        if (storedState.contentDescriptionNumberless == null) {
            charSequence = context.getString(C1087R.string.mtrl_badge_numberless_content_description);
        } else {
            charSequence = storedState.contentDescriptionNumberless;
        }
        CharSequence unused3 = state.contentDescriptionNumberless = charSequence;
        if (storedState.contentDescriptionQuantityStrings == 0) {
            i = C1087R.plurals.mtrl_badge_content_description;
        } else {
            i = storedState.contentDescriptionQuantityStrings;
        }
        int unused4 = state.contentDescriptionQuantityStrings = i;
        if (storedState.contentDescriptionExceedsMaxBadgeNumberRes == 0) {
            i2 = C1087R.string.mtrl_exceed_max_badge_number_content_description;
        } else {
            i2 = storedState.contentDescriptionExceedsMaxBadgeNumberRes;
        }
        int unused5 = state.contentDescriptionExceedsMaxBadgeNumberRes = i2;
        int i10 = 0;
        Boolean unused6 = state.isVisible = Boolean.valueOf(storedState.isVisible == null || storedState.isVisible.booleanValue());
        if (storedState.maxCharacterCount == -2) {
            i3 = a.getInt(C1087R.styleable.Badge_maxCharacterCount, 4);
        } else {
            i3 = storedState.maxCharacterCount;
        }
        int unused7 = state.maxCharacterCount = i3;
        if (storedState.number != -2) {
            int unused8 = state.number = storedState.number;
        } else if (a.hasValue(C1087R.styleable.Badge_number)) {
            int unused9 = state.number = a.getInt(C1087R.styleable.Badge_number, 0);
        } else {
            int unused10 = state.number = -1;
        }
        if (storedState.backgroundColor == null) {
            i4 = readColorFromAttributes(context, a, C1087R.styleable.Badge_backgroundColor);
        } else {
            i4 = storedState.backgroundColor.intValue();
        }
        Integer unused11 = state.backgroundColor = Integer.valueOf(i4);
        if (storedState.badgeTextColor != null) {
            Integer unused12 = state.badgeTextColor = storedState.badgeTextColor;
        } else if (a.hasValue(C1087R.styleable.Badge_badgeTextColor)) {
            Integer unused13 = state.badgeTextColor = Integer.valueOf(readColorFromAttributes(context, a, C1087R.styleable.Badge_badgeTextColor));
        } else {
            Integer unused14 = state.badgeTextColor = Integer.valueOf(new TextAppearance(context, C1087R.C1093style.TextAppearance_MaterialComponents_Badge).getTextColor().getDefaultColor());
        }
        if (storedState.badgeGravity == null) {
            i5 = a.getInt(C1087R.styleable.Badge_badgeGravity, 8388661);
        } else {
            i5 = storedState.badgeGravity.intValue();
        }
        Integer unused15 = state.badgeGravity = Integer.valueOf(i5);
        if (storedState.horizontalOffsetWithoutText == null) {
            i6 = a.getDimensionPixelOffset(C1087R.styleable.Badge_horizontalOffset, 0);
        } else {
            i6 = storedState.horizontalOffsetWithoutText.intValue();
        }
        Integer unused16 = state.horizontalOffsetWithoutText = Integer.valueOf(i6);
        if (storedState.verticalOffsetWithoutText == null) {
            i7 = a.getDimensionPixelOffset(C1087R.styleable.Badge_verticalOffset, 0);
        } else {
            i7 = storedState.verticalOffsetWithoutText.intValue();
        }
        Integer unused17 = state.verticalOffsetWithoutText = Integer.valueOf(i7);
        if (storedState.horizontalOffsetWithText == null) {
            i8 = a.getDimensionPixelOffset(C1087R.styleable.Badge_horizontalOffsetWithText, state.horizontalOffsetWithoutText.intValue());
        } else {
            i8 = storedState.horizontalOffsetWithText.intValue();
        }
        Integer unused18 = state.horizontalOffsetWithText = Integer.valueOf(i8);
        if (storedState.verticalOffsetWithText == null) {
            i9 = a.getDimensionPixelOffset(C1087R.styleable.Badge_verticalOffsetWithText, state.verticalOffsetWithoutText.intValue());
        } else {
            i9 = storedState.verticalOffsetWithText.intValue();
        }
        Integer unused19 = state.verticalOffsetWithText = Integer.valueOf(i9);
        Integer unused20 = state.additionalHorizontalOffset = Integer.valueOf(storedState.additionalHorizontalOffset == null ? 0 : storedState.additionalHorizontalOffset.intValue());
        Integer unused21 = state.additionalVerticalOffset = Integer.valueOf(storedState.additionalVerticalOffset != null ? storedState.additionalVerticalOffset.intValue() : i10);
        a.recycle();
        if (storedState.numberLocale == null) {
            if (Build.VERSION.SDK_INT >= 24) {
                locale = Locale.getDefault(Locale.Category.FORMAT);
            } else {
                locale = Locale.getDefault();
            }
            Locale unused22 = state.numberLocale = locale;
        } else {
            Locale unused23 = state.numberLocale = storedState.numberLocale;
        }
        this.overridingState = storedState;
    }

    private TypedArray generateTypedArray(Context context, int badgeResId, int defStyleAttr, int defStyleRes) {
        AttributeSet attrs = null;
        int style = 0;
        if (badgeResId != 0) {
            attrs = DrawableUtils.parseDrawableXml(context, badgeResId, BADGE_RESOURCE_TAG);
            style = attrs.getStyleAttribute();
        }
        if (style == 0) {
            style = defStyleRes;
        }
        return ThemeEnforcement.obtainStyledAttributes(context, attrs, C1087R.styleable.Badge, defStyleAttr, style, new int[0]);
    }

    /* access modifiers changed from: package-private */
    public State getOverridingState() {
        return this.overridingState;
    }

    /* access modifiers changed from: package-private */
    public boolean isVisible() {
        return this.currentState.isVisible.booleanValue();
    }

    /* access modifiers changed from: package-private */
    public void setVisible(boolean visible) {
        Boolean unused = this.overridingState.isVisible = Boolean.valueOf(visible);
        Boolean unused2 = this.currentState.isVisible = Boolean.valueOf(visible);
    }

    /* access modifiers changed from: package-private */
    public boolean hasNumber() {
        return this.currentState.number != -1;
    }

    /* access modifiers changed from: package-private */
    public int getNumber() {
        return this.currentState.number;
    }

    /* access modifiers changed from: package-private */
    public void setNumber(int number) {
        int unused = this.overridingState.number = number;
        int unused2 = this.currentState.number = number;
    }

    /* access modifiers changed from: package-private */
    public void clearNumber() {
        setNumber(-1);
    }

    /* access modifiers changed from: package-private */
    public int getAlpha() {
        return this.currentState.alpha;
    }

    /* access modifiers changed from: package-private */
    public void setAlpha(int alpha) {
        int unused = this.overridingState.alpha = alpha;
        int unused2 = this.currentState.alpha = alpha;
    }

    /* access modifiers changed from: package-private */
    public int getMaxCharacterCount() {
        return this.currentState.maxCharacterCount;
    }

    /* access modifiers changed from: package-private */
    public void setMaxCharacterCount(int maxCharacterCount) {
        int unused = this.overridingState.maxCharacterCount = maxCharacterCount;
        int unused2 = this.currentState.maxCharacterCount = maxCharacterCount;
    }

    /* access modifiers changed from: package-private */
    public int getBackgroundColor() {
        return this.currentState.backgroundColor.intValue();
    }

    /* access modifiers changed from: package-private */
    public void setBackgroundColor(int backgroundColor) {
        Integer unused = this.overridingState.backgroundColor = Integer.valueOf(backgroundColor);
        Integer unused2 = this.currentState.backgroundColor = Integer.valueOf(backgroundColor);
    }

    /* access modifiers changed from: package-private */
    public int getBadgeTextColor() {
        return this.currentState.badgeTextColor.intValue();
    }

    /* access modifiers changed from: package-private */
    public void setBadgeTextColor(int badgeTextColor) {
        Integer unused = this.overridingState.badgeTextColor = Integer.valueOf(badgeTextColor);
        Integer unused2 = this.currentState.badgeTextColor = Integer.valueOf(badgeTextColor);
    }

    /* access modifiers changed from: package-private */
    public int getBadgeGravity() {
        return this.currentState.badgeGravity.intValue();
    }

    /* access modifiers changed from: package-private */
    public void setBadgeGravity(int badgeGravity) {
        Integer unused = this.overridingState.badgeGravity = Integer.valueOf(badgeGravity);
        Integer unused2 = this.currentState.badgeGravity = Integer.valueOf(badgeGravity);
    }

    /* access modifiers changed from: package-private */
    public int getHorizontalOffsetWithoutText() {
        return this.currentState.horizontalOffsetWithoutText.intValue();
    }

    /* access modifiers changed from: package-private */
    public void setHorizontalOffsetWithoutText(int offset) {
        Integer unused = this.overridingState.horizontalOffsetWithoutText = Integer.valueOf(offset);
        Integer unused2 = this.currentState.horizontalOffsetWithoutText = Integer.valueOf(offset);
    }

    /* access modifiers changed from: package-private */
    public int getVerticalOffsetWithoutText() {
        return this.currentState.verticalOffsetWithoutText.intValue();
    }

    /* access modifiers changed from: package-private */
    public void setVerticalOffsetWithoutText(int offset) {
        Integer unused = this.overridingState.verticalOffsetWithoutText = Integer.valueOf(offset);
        Integer unused2 = this.currentState.verticalOffsetWithoutText = Integer.valueOf(offset);
    }

    /* access modifiers changed from: package-private */
    public int getHorizontalOffsetWithText() {
        return this.currentState.horizontalOffsetWithText.intValue();
    }

    /* access modifiers changed from: package-private */
    public void setHorizontalOffsetWithText(int offset) {
        Integer unused = this.overridingState.horizontalOffsetWithText = Integer.valueOf(offset);
        Integer unused2 = this.currentState.horizontalOffsetWithText = Integer.valueOf(offset);
    }

    /* access modifiers changed from: package-private */
    public int getVerticalOffsetWithText() {
        return this.currentState.verticalOffsetWithText.intValue();
    }

    /* access modifiers changed from: package-private */
    public void setVerticalOffsetWithText(int offset) {
        Integer unused = this.overridingState.verticalOffsetWithText = Integer.valueOf(offset);
        Integer unused2 = this.currentState.verticalOffsetWithText = Integer.valueOf(offset);
    }

    /* access modifiers changed from: package-private */
    public int getAdditionalHorizontalOffset() {
        return this.currentState.additionalHorizontalOffset.intValue();
    }

    /* access modifiers changed from: package-private */
    public void setAdditionalHorizontalOffset(int offset) {
        Integer unused = this.overridingState.additionalHorizontalOffset = Integer.valueOf(offset);
        Integer unused2 = this.currentState.additionalHorizontalOffset = Integer.valueOf(offset);
    }

    /* access modifiers changed from: package-private */
    public int getAdditionalVerticalOffset() {
        return this.currentState.additionalVerticalOffset.intValue();
    }

    /* access modifiers changed from: package-private */
    public void setAdditionalVerticalOffset(int offset) {
        Integer unused = this.overridingState.additionalVerticalOffset = Integer.valueOf(offset);
        Integer unused2 = this.currentState.additionalVerticalOffset = Integer.valueOf(offset);
    }

    /* access modifiers changed from: package-private */
    public CharSequence getContentDescriptionNumberless() {
        return this.currentState.contentDescriptionNumberless;
    }

    /* access modifiers changed from: package-private */
    public void setContentDescriptionNumberless(CharSequence contentDescriptionNumberless) {
        CharSequence unused = this.overridingState.contentDescriptionNumberless = contentDescriptionNumberless;
        CharSequence unused2 = this.currentState.contentDescriptionNumberless = contentDescriptionNumberless;
    }

    /* access modifiers changed from: package-private */
    public int getContentDescriptionQuantityStrings() {
        return this.currentState.contentDescriptionQuantityStrings;
    }

    /* access modifiers changed from: package-private */
    public void setContentDescriptionQuantityStringsResource(int stringsResource) {
        int unused = this.overridingState.contentDescriptionQuantityStrings = stringsResource;
        int unused2 = this.currentState.contentDescriptionQuantityStrings = stringsResource;
    }

    /* access modifiers changed from: package-private */
    public int getContentDescriptionExceedsMaxBadgeNumberStringResource() {
        return this.currentState.contentDescriptionExceedsMaxBadgeNumberRes;
    }

    /* access modifiers changed from: package-private */
    public void setContentDescriptionExceedsMaxBadgeNumberStringResource(int stringsResource) {
        int unused = this.overridingState.contentDescriptionExceedsMaxBadgeNumberRes = stringsResource;
        int unused2 = this.currentState.contentDescriptionExceedsMaxBadgeNumberRes = stringsResource;
    }

    /* access modifiers changed from: package-private */
    public Locale getNumberLocale() {
        return this.currentState.numberLocale;
    }

    /* access modifiers changed from: package-private */
    public void setNumberLocale(Locale locale) {
        Locale unused = this.overridingState.numberLocale = locale;
        Locale unused2 = this.currentState.numberLocale = locale;
    }

    private static int readColorFromAttributes(Context context, TypedArray a, int index) {
        return MaterialResources.getColorStateList(context, a, index).getDefaultColor();
    }

    public static final class State implements Parcelable {
        private static final int BADGE_NUMBER_NONE = -1;
        public static final Parcelable.Creator<State> CREATOR = new Parcelable.Creator<State>() {
            public State createFromParcel(Parcel in) {
                return new State(in);
            }

            public State[] newArray(int size) {
                return new State[size];
            }
        };
        private static final int NOT_SET = -2;
        /* access modifiers changed from: private */
        public Integer additionalHorizontalOffset;
        /* access modifiers changed from: private */
        public Integer additionalVerticalOffset;
        /* access modifiers changed from: private */
        public int alpha = 255;
        /* access modifiers changed from: private */
        public Integer backgroundColor;
        /* access modifiers changed from: private */
        public Integer badgeGravity;
        /* access modifiers changed from: private */
        public int badgeResId;
        /* access modifiers changed from: private */
        public Integer badgeTextColor;
        /* access modifiers changed from: private */
        public int contentDescriptionExceedsMaxBadgeNumberRes;
        /* access modifiers changed from: private */
        public CharSequence contentDescriptionNumberless;
        /* access modifiers changed from: private */
        public int contentDescriptionQuantityStrings;
        /* access modifiers changed from: private */
        public Integer horizontalOffsetWithText;
        /* access modifiers changed from: private */
        public Integer horizontalOffsetWithoutText;
        /* access modifiers changed from: private */
        public Boolean isVisible = true;
        /* access modifiers changed from: private */
        public int maxCharacterCount = -2;
        /* access modifiers changed from: private */
        public int number = -2;
        /* access modifiers changed from: private */
        public Locale numberLocale;
        /* access modifiers changed from: private */
        public Integer verticalOffsetWithText;
        /* access modifiers changed from: private */
        public Integer verticalOffsetWithoutText;

        public State() {
        }

        State(Parcel in) {
            this.badgeResId = in.readInt();
            this.backgroundColor = (Integer) in.readSerializable();
            this.badgeTextColor = (Integer) in.readSerializable();
            this.alpha = in.readInt();
            this.number = in.readInt();
            this.maxCharacterCount = in.readInt();
            this.contentDescriptionNumberless = in.readString();
            this.contentDescriptionQuantityStrings = in.readInt();
            this.badgeGravity = (Integer) in.readSerializable();
            this.horizontalOffsetWithoutText = (Integer) in.readSerializable();
            this.verticalOffsetWithoutText = (Integer) in.readSerializable();
            this.horizontalOffsetWithText = (Integer) in.readSerializable();
            this.verticalOffsetWithText = (Integer) in.readSerializable();
            this.additionalHorizontalOffset = (Integer) in.readSerializable();
            this.additionalVerticalOffset = (Integer) in.readSerializable();
            this.isVisible = (Boolean) in.readSerializable();
            this.numberLocale = (Locale) in.readSerializable();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.badgeResId);
            dest.writeSerializable(this.backgroundColor);
            dest.writeSerializable(this.badgeTextColor);
            dest.writeInt(this.alpha);
            dest.writeInt(this.number);
            dest.writeInt(this.maxCharacterCount);
            CharSequence charSequence = this.contentDescriptionNumberless;
            dest.writeString(charSequence == null ? null : charSequence.toString());
            dest.writeInt(this.contentDescriptionQuantityStrings);
            dest.writeSerializable(this.badgeGravity);
            dest.writeSerializable(this.horizontalOffsetWithoutText);
            dest.writeSerializable(this.verticalOffsetWithoutText);
            dest.writeSerializable(this.horizontalOffsetWithText);
            dest.writeSerializable(this.verticalOffsetWithText);
            dest.writeSerializable(this.additionalHorizontalOffset);
            dest.writeSerializable(this.additionalVerticalOffset);
            dest.writeSerializable(this.isVisible);
            dest.writeSerializable(this.numberLocale);
        }
    }
}
