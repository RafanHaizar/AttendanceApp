package kotlin.time;

import com.itextpdf.svg.SvgConstants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo112d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\f\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\u001a\u0018\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u0001\u001a\u0010\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\bH\u0001\u001a\f\u0010\u0007\u001a\u00020\b*\u00020\u0001H\u0001¨\u0006\t"}, mo113d2 = {"durationUnitByIsoChar", "Lkotlin/time/DurationUnit;", "isoChar", "", "isTimeComponent", "", "durationUnitByShortName", "shortName", "", "kotlin-stdlib"}, mo114k = 5, mo115mv = {1, 7, 1}, mo117xi = 49, mo118xs = "kotlin/time/DurationUnitKt")
/* compiled from: DurationUnit.kt */
class DurationUnitKt__DurationUnitKt extends DurationUnitKt__DurationUnitJvmKt {

    @Metadata(mo114k = 3, mo115mv = {1, 7, 1}, mo117xi = 48)
    /* compiled from: DurationUnit.kt */
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DurationUnit.values().length];
            iArr[DurationUnit.NANOSECONDS.ordinal()] = 1;
            iArr[DurationUnit.MICROSECONDS.ordinal()] = 2;
            iArr[DurationUnit.MILLISECONDS.ordinal()] = 3;
            iArr[DurationUnit.SECONDS.ordinal()] = 4;
            iArr[DurationUnit.MINUTES.ordinal()] = 5;
            iArr[DurationUnit.HOURS.ordinal()] = 6;
            iArr[DurationUnit.DAYS.ordinal()] = 7;
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final String shortName(DurationUnit $this$shortName) {
        Intrinsics.checkNotNullParameter($this$shortName, "<this>");
        switch (WhenMappings.$EnumSwitchMapping$0[$this$shortName.ordinal()]) {
            case 1:
                return "ns";
            case 2:
                return "us";
            case 3:
                return "ms";
            case 4:
                return SvgConstants.Attributes.PATH_DATA_REL_CURVE_TO_S;
            case 5:
                return SvgConstants.Attributes.PATH_DATA_REL_MOVE_TO;
            case 6:
                return SvgConstants.Attributes.PATH_DATA_REL_LINE_TO_H;
            case 7:
                return SvgConstants.Attributes.f1634D;
            default:
                throw new IllegalStateException(("Unknown unit: " + $this$shortName).toString());
        }
    }

    public static final DurationUnit durationUnitByShortName(String shortName) {
        Intrinsics.checkNotNullParameter(shortName, "shortName");
        switch (shortName.hashCode()) {
            case 100:
                if (shortName.equals(SvgConstants.Attributes.f1634D)) {
                    return DurationUnit.DAYS;
                }
                break;
            case 104:
                if (shortName.equals(SvgConstants.Attributes.PATH_DATA_REL_LINE_TO_H)) {
                    return DurationUnit.HOURS;
                }
                break;
            case 109:
                if (shortName.equals(SvgConstants.Attributes.PATH_DATA_REL_MOVE_TO)) {
                    return DurationUnit.MINUTES;
                }
                break;
            case 115:
                if (shortName.equals(SvgConstants.Attributes.PATH_DATA_REL_CURVE_TO_S)) {
                    return DurationUnit.SECONDS;
                }
                break;
            case 3494:
                if (shortName.equals("ms")) {
                    return DurationUnit.MILLISECONDS;
                }
                break;
            case 3525:
                if (shortName.equals("ns")) {
                    return DurationUnit.NANOSECONDS;
                }
                break;
            case 3742:
                if (shortName.equals("us")) {
                    return DurationUnit.MICROSECONDS;
                }
                break;
        }
        throw new IllegalArgumentException("Unknown duration unit short name: " + shortName);
    }

    public static final DurationUnit durationUnitByIsoChar(char isoChar, boolean isTimeComponent) {
        if (!isTimeComponent) {
            if (isoChar == 'D') {
                return DurationUnit.DAYS;
            }
            throw new IllegalArgumentException("Invalid or unsupported duration ISO non-time unit: " + isoChar);
        } else if (isoChar == 'H') {
            return DurationUnit.HOURS;
        } else {
            if (isoChar == 'M') {
                return DurationUnit.MINUTES;
            }
            if (isoChar == 'S') {
                return DurationUnit.SECONDS;
            }
            throw new IllegalArgumentException("Invalid duration ISO time unit: " + isoChar);
        }
    }
}
