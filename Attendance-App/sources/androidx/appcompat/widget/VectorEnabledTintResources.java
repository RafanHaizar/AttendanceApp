package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import org.xmlpull.v1.XmlPullParserException;

public class VectorEnabledTintResources extends ResourcesWrapper {
    public static final int MAX_SDK_WHERE_REQUIRED = 20;
    private static boolean sCompatVectorFromResourcesEnabled = false;
    private final WeakReference<Context> mContextRef;

    public /* bridge */ /* synthetic */ XmlResourceParser getAnimation(int i) throws Resources.NotFoundException {
        return super.getAnimation(i);
    }

    public /* bridge */ /* synthetic */ boolean getBoolean(int i) throws Resources.NotFoundException {
        return super.getBoolean(i);
    }

    public /* bridge */ /* synthetic */ int getColor(int i) throws Resources.NotFoundException {
        return super.getColor(i);
    }

    public /* bridge */ /* synthetic */ ColorStateList getColorStateList(int i) throws Resources.NotFoundException {
        return super.getColorStateList(i);
    }

    public /* bridge */ /* synthetic */ Configuration getConfiguration() {
        return super.getConfiguration();
    }

    public /* bridge */ /* synthetic */ float getDimension(int i) throws Resources.NotFoundException {
        return super.getDimension(i);
    }

    public /* bridge */ /* synthetic */ int getDimensionPixelOffset(int i) throws Resources.NotFoundException {
        return super.getDimensionPixelOffset(i);
    }

    public /* bridge */ /* synthetic */ int getDimensionPixelSize(int i) throws Resources.NotFoundException {
        return super.getDimensionPixelSize(i);
    }

    public /* bridge */ /* synthetic */ DisplayMetrics getDisplayMetrics() {
        return super.getDisplayMetrics();
    }

    public /* bridge */ /* synthetic */ Drawable getDrawable(int i, Resources.Theme theme) throws Resources.NotFoundException {
        return super.getDrawable(i, theme);
    }

    public /* bridge */ /* synthetic */ Drawable getDrawableForDensity(int i, int i2) throws Resources.NotFoundException {
        return super.getDrawableForDensity(i, i2);
    }

    public /* bridge */ /* synthetic */ Drawable getDrawableForDensity(int i, int i2, Resources.Theme theme) {
        return super.getDrawableForDensity(i, i2, theme);
    }

    public /* bridge */ /* synthetic */ float getFraction(int i, int i2, int i3) {
        return super.getFraction(i, i2, i3);
    }

    public /* bridge */ /* synthetic */ int getIdentifier(String str, String str2, String str3) {
        return super.getIdentifier(str, str2, str3);
    }

    public /* bridge */ /* synthetic */ int[] getIntArray(int i) throws Resources.NotFoundException {
        return super.getIntArray(i);
    }

    public /* bridge */ /* synthetic */ int getInteger(int i) throws Resources.NotFoundException {
        return super.getInteger(i);
    }

    public /* bridge */ /* synthetic */ XmlResourceParser getLayout(int i) throws Resources.NotFoundException {
        return super.getLayout(i);
    }

    public /* bridge */ /* synthetic */ Movie getMovie(int i) throws Resources.NotFoundException {
        return super.getMovie(i);
    }

    public /* bridge */ /* synthetic */ String getQuantityString(int i, int i2) throws Resources.NotFoundException {
        return super.getQuantityString(i, i2);
    }

    public /* bridge */ /* synthetic */ String getQuantityString(int i, int i2, Object[] objArr) throws Resources.NotFoundException {
        return super.getQuantityString(i, i2, objArr);
    }

    public /* bridge */ /* synthetic */ CharSequence getQuantityText(int i, int i2) throws Resources.NotFoundException {
        return super.getQuantityText(i, i2);
    }

    public /* bridge */ /* synthetic */ String getResourceEntryName(int i) throws Resources.NotFoundException {
        return super.getResourceEntryName(i);
    }

    public /* bridge */ /* synthetic */ String getResourceName(int i) throws Resources.NotFoundException {
        return super.getResourceName(i);
    }

    public /* bridge */ /* synthetic */ String getResourcePackageName(int i) throws Resources.NotFoundException {
        return super.getResourcePackageName(i);
    }

    public /* bridge */ /* synthetic */ String getResourceTypeName(int i) throws Resources.NotFoundException {
        return super.getResourceTypeName(i);
    }

    public /* bridge */ /* synthetic */ String getString(int i) throws Resources.NotFoundException {
        return super.getString(i);
    }

    public /* bridge */ /* synthetic */ String getString(int i, Object[] objArr) throws Resources.NotFoundException {
        return super.getString(i, objArr);
    }

    public /* bridge */ /* synthetic */ String[] getStringArray(int i) throws Resources.NotFoundException {
        return super.getStringArray(i);
    }

    public /* bridge */ /* synthetic */ CharSequence getText(int i) throws Resources.NotFoundException {
        return super.getText(i);
    }

    public /* bridge */ /* synthetic */ CharSequence getText(int i, CharSequence charSequence) {
        return super.getText(i, charSequence);
    }

    public /* bridge */ /* synthetic */ CharSequence[] getTextArray(int i) throws Resources.NotFoundException {
        return super.getTextArray(i);
    }

    public /* bridge */ /* synthetic */ void getValue(int i, TypedValue typedValue, boolean z) throws Resources.NotFoundException {
        super.getValue(i, typedValue, z);
    }

    public /* bridge */ /* synthetic */ void getValue(String str, TypedValue typedValue, boolean z) throws Resources.NotFoundException {
        super.getValue(str, typedValue, z);
    }

    public /* bridge */ /* synthetic */ void getValueForDensity(int i, int i2, TypedValue typedValue, boolean z) throws Resources.NotFoundException {
        super.getValueForDensity(i, i2, typedValue, z);
    }

    public /* bridge */ /* synthetic */ XmlResourceParser getXml(int i) throws Resources.NotFoundException {
        return super.getXml(i);
    }

    public /* bridge */ /* synthetic */ TypedArray obtainAttributes(AttributeSet attributeSet, int[] iArr) {
        return super.obtainAttributes(attributeSet, iArr);
    }

    public /* bridge */ /* synthetic */ TypedArray obtainTypedArray(int i) throws Resources.NotFoundException {
        return super.obtainTypedArray(i);
    }

    public /* bridge */ /* synthetic */ InputStream openRawResource(int i) throws Resources.NotFoundException {
        return super.openRawResource(i);
    }

    public /* bridge */ /* synthetic */ InputStream openRawResource(int i, TypedValue typedValue) throws Resources.NotFoundException {
        return super.openRawResource(i, typedValue);
    }

    public /* bridge */ /* synthetic */ AssetFileDescriptor openRawResourceFd(int i) throws Resources.NotFoundException {
        return super.openRawResourceFd(i);
    }

    public /* bridge */ /* synthetic */ void parseBundleExtra(String str, AttributeSet attributeSet, Bundle bundle) throws XmlPullParserException {
        super.parseBundleExtra(str, attributeSet, bundle);
    }

    public /* bridge */ /* synthetic */ void parseBundleExtras(XmlResourceParser xmlResourceParser, Bundle bundle) throws XmlPullParserException, IOException {
        super.parseBundleExtras(xmlResourceParser, bundle);
    }

    public /* bridge */ /* synthetic */ void updateConfiguration(Configuration configuration, DisplayMetrics displayMetrics) {
        super.updateConfiguration(configuration, displayMetrics);
    }

    public static boolean shouldBeUsed() {
        return isCompatVectorFromResourcesEnabled() && Build.VERSION.SDK_INT <= 20;
    }

    public VectorEnabledTintResources(Context context, Resources res) {
        super(res);
        this.mContextRef = new WeakReference<>(context);
    }

    public Drawable getDrawable(int id) throws Resources.NotFoundException {
        Context context = (Context) this.mContextRef.get();
        if (context != null) {
            return ResourceManagerInternal.get().onDrawableLoadedFromResources(context, this, id);
        }
        return getDrawableCanonical(id);
    }

    public static void setCompatVectorFromResourcesEnabled(boolean enabled) {
        sCompatVectorFromResourcesEnabled = enabled;
    }

    public static boolean isCompatVectorFromResourcesEnabled() {
        return sCompatVectorFromResourcesEnabled;
    }
}
