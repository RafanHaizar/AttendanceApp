package kotlin.text;

import kotlin.Deprecated;
import kotlin.DeprecatedSinceKotlin;
import kotlin.Metadata;
import kotlin.ReplaceWith;

@Metadata(mo112d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\f\n\u0002\b-\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u0016\u0010\u0014\u001a\u00020\u00048\u0006XT¢\u0006\b\n\u0000\u0012\u0004\b\u0015\u0010\u0002R\u0016\u0010\u0016\u001a\u00020\u00048\u0006XT¢\u0006\b\n\u0000\u0012\u0004\b\u0017\u0010\u0002R\u000e\u0010\u0018\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010%\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010'\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u0016\u0010)\u001a\u00020\u00048\u0006XT¢\u0006\b\n\u0000\u0012\u0004\b*\u0010\u0002R\u0016\u0010+\u001a\u00020\u00048\u0006XT¢\u0006\b\n\u0000\u0012\u0004\b,\u0010\u0002R\u000e\u0010-\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010.\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010/\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000¨\u00061"}, mo113d2 = {"Lkotlin/text/Typography;", "", "()V", "almostEqual", "", "amp", "bullet", "cent", "copyright", "dagger", "degree", "dollar", "doubleDagger", "doublePrime", "ellipsis", "euro", "greater", "greaterOrEqual", "half", "leftDoubleQuote", "leftGuillemet", "getLeftGuillemet$annotations", "leftGuillemete", "getLeftGuillemete$annotations", "leftSingleQuote", "less", "lessOrEqual", "lowDoubleQuote", "lowSingleQuote", "mdash", "middleDot", "nbsp", "ndash", "notEqual", "paragraph", "plusMinus", "pound", "prime", "quote", "registered", "rightDoubleQuote", "rightGuillemet", "getRightGuillemet$annotations", "rightGuillemete", "getRightGuillemete$annotations", "rightSingleQuote", "section", "times", "tm", "kotlin-stdlib"}, mo114k = 1, mo115mv = {1, 7, 1}, mo117xi = 48)
/* compiled from: Typography.kt */
public final class Typography {
    public static final Typography INSTANCE = new Typography();
    public static final char almostEqual = '≈';
    public static final char amp = '&';
    public static final char bullet = '•';
    public static final char cent = '¢';
    public static final char copyright = '©';
    public static final char dagger = '†';
    public static final char degree = '°';
    public static final char dollar = '$';
    public static final char doubleDagger = '‡';
    public static final char doublePrime = '″';
    public static final char ellipsis = '…';
    public static final char euro = '€';
    public static final char greater = '>';
    public static final char greaterOrEqual = '≥';
    public static final char half = '½';
    public static final char leftDoubleQuote = '“';
    public static final char leftGuillemet = '«';
    public static final char leftGuillemete = '«';
    public static final char leftSingleQuote = '‘';
    public static final char less = '<';
    public static final char lessOrEqual = '≤';
    public static final char lowDoubleQuote = '„';
    public static final char lowSingleQuote = '‚';
    public static final char mdash = '—';
    public static final char middleDot = '·';
    public static final char nbsp = ' ';
    public static final char ndash = '–';
    public static final char notEqual = '≠';
    public static final char paragraph = '¶';
    public static final char plusMinus = '±';
    public static final char pound = '£';
    public static final char prime = '′';
    public static final char quote = '\"';
    public static final char registered = '®';
    public static final char rightDoubleQuote = '”';
    public static final char rightGuillemet = '»';
    public static final char rightGuillemete = '»';
    public static final char rightSingleQuote = '’';
    public static final char section = '§';
    public static final char times = '×';

    /* renamed from: tm */
    public static final char f3tm = '™';

    public static /* synthetic */ void getLeftGuillemet$annotations() {
    }

    @Deprecated(message = "This constant has a typo in the name. Use leftGuillemet instead.", replaceWith = @ReplaceWith(expression = "Typography.leftGuillemet", imports = {}))
    @DeprecatedSinceKotlin(warningSince = "1.6")
    public static /* synthetic */ void getLeftGuillemete$annotations() {
    }

    public static /* synthetic */ void getRightGuillemet$annotations() {
    }

    @Deprecated(message = "This constant has a typo in the name. Use rightGuillemet instead.", replaceWith = @ReplaceWith(expression = "Typography.rightGuillemet", imports = {}))
    @DeprecatedSinceKotlin(warningSince = "1.6")
    public static /* synthetic */ void getRightGuillemete$annotations() {
    }

    private Typography() {
    }
}
