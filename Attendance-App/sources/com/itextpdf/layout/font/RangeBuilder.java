package com.itextpdf.layout.font;

import com.itextpdf.layout.font.Range;
import java.util.ArrayList;
import java.util.List;

public class RangeBuilder {
    private static final Range fullRangeSingleton = new Range.FullRange();
    private List<Range.SubRange> ranges;

    static Range getFullRange() {
        return fullRangeSingleton;
    }

    public RangeBuilder() {
        this.ranges = new ArrayList();
    }

    public RangeBuilder(int low, int high) {
        this.ranges = new ArrayList();
        addRange(low, high);
    }

    public RangeBuilder(int n) {
        this(n, n);
    }

    public RangeBuilder(char low, char high) {
        this((int) low, (int) high);
    }

    public RangeBuilder(char ch) {
        this((int) ch);
    }

    public RangeBuilder addRange(int low, int high) {
        if (high >= low) {
            this.ranges.add(new Range.SubRange(low, high));
            return this;
        }
        throw new IllegalArgumentException("'from' shall be less than 'to'");
    }

    public RangeBuilder addRange(char low, char high) {
        return addRange((int) low, (int) high);
    }

    public RangeBuilder addRange(int n) {
        return addRange(n, n);
    }

    public RangeBuilder addRange(char ch) {
        return addRange((int) ch);
    }

    public Range create() {
        return new Range(this.ranges);
    }
}
