package com.itextpdf.layout.font;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Range {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private SubRange[] ranges;

    private Range() {
    }

    Range(List<SubRange> ranges2) {
        if (ranges2.size() != 0) {
            this.ranges = normalizeSubRanges(ranges2);
            return;
        }
        throw new IllegalArgumentException("Ranges shall not be empty");
    }

    public boolean contains(int n) {
        int low = 0;
        int high = this.ranges.length - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            if (this.ranges[mid].compareTo(n) < 0) {
                low = mid + 1;
            } else if (this.ranges[mid].compareTo(n) <= 0) {
                return true;
            } else {
                high = mid - 1;
            }
        }
        return false;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return Arrays.equals(this.ranges, ((Range) o).ranges);
    }

    public int hashCode() {
        return Arrays.hashCode(this.ranges);
    }

    public String toString() {
        return Arrays.toString(this.ranges);
    }

    private static SubRange[] normalizeSubRanges(List<SubRange> ranges2) {
        Collections.sort(ranges2);
        List<SubRange> union = new ArrayList<>(ranges2.size());
        if (ranges2.size() > 0) {
            SubRange curr = ranges2.get(0);
            union.add(curr);
            for (int i = 1; i < ranges2.size(); i++) {
                SubRange next = ranges2.get(i);
                if (next.low > curr.high) {
                    curr = next;
                    union.add(curr);
                } else if (next.high > curr.high) {
                    curr.high = next.high;
                }
            }
            return (SubRange[]) union.toArray(new SubRange[0]);
        }
        throw new AssertionError();
    }

    static class SubRange implements Comparable<SubRange> {
        int high;
        int low;

        SubRange(int low2, int high2) {
            this.low = low2;
            this.high = high2;
        }

        public int compareTo(SubRange o) {
            return this.low - o.low;
        }

        public int compareTo(int n) {
            if (n < this.low) {
                return 1;
            }
            if (n > this.high) {
                return -1;
            }
            return 0;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            SubRange subRange = (SubRange) o;
            if (this.low == subRange.low && this.high == subRange.high) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (this.low * 31) + this.high;
        }

        public String toString() {
            return "(" + this.low + "; " + this.high + ')';
        }
    }

    static class FullRange extends Range {
        FullRange() {
            super();
        }

        public boolean contains(int uni) {
            return true;
        }

        public boolean equals(Object o) {
            return this == o;
        }

        public int hashCode() {
            return 1;
        }

        public String toString() {
            return "[FullRange]";
        }
    }
}
