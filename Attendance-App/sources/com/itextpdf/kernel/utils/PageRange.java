package com.itextpdf.kernel.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageRange {
    private static final Pattern SEQUENCE_PATTERN = Pattern.compile("(\\d+)-(\\d+)?");
    private static final Pattern SINGLE_PAGE_PATTERN = Pattern.compile("(\\d+)");
    private List<IPageRangePart> sequences = new ArrayList();

    public interface IPageRangePart {
        List<Integer> getAllPagesInRange(int i);

        boolean isPageInRange(int i);
    }

    public PageRange() {
    }

    public PageRange(String pageRange) {
        for (String pageRangePart : pageRange.replaceAll("\\s+", "").split(",")) {
            IPageRangePart cond = getRangeObject(pageRangePart);
            if (cond != null) {
                this.sequences.add(cond);
            }
        }
    }

    private static IPageRangePart getRangeObject(String rangeDef) {
        if (rangeDef.contains("&")) {
            List<IPageRangePart> conditions = new ArrayList<>();
            for (String pageRangeCond : rangeDef.split("&")) {
                IPageRangePart cond = getRangeObject(pageRangeCond);
                if (cond != null) {
                    conditions.add(cond);
                }
            }
            if (conditions.size() > 0) {
                return new PageRangePartAnd((IPageRangePart[]) conditions.toArray(new IPageRangePart[0]));
            }
            return null;
        }
        Matcher matcher = SEQUENCE_PATTERN.matcher(rangeDef);
        Matcher matcher2 = matcher;
        if (matcher.matches()) {
            int start = Integer.parseInt(matcher2.group(1));
            if (matcher2.group(2) != null) {
                return new PageRangePartSequence(start, Integer.parseInt(matcher2.group(2)));
            }
            return new PageRangePartAfter(start);
        }
        Matcher matcher3 = SINGLE_PAGE_PATTERN.matcher(rangeDef);
        Matcher matcher4 = matcher3;
        if (matcher3.matches()) {
            return new PageRangePartSingle(Integer.parseInt(matcher4.group(1)));
        }
        if ("odd".equalsIgnoreCase(rangeDef)) {
            return PageRangePartOddEven.ODD;
        }
        if ("even".equalsIgnoreCase(rangeDef)) {
            return PageRangePartOddEven.EVEN;
        }
        return null;
    }

    public PageRange addPageRangePart(IPageRangePart part) {
        this.sequences.add(part);
        return this;
    }

    public PageRange addPageSequence(int startPageNumber, int endPageNumber) {
        return addPageRangePart(new PageRangePartSequence(startPageNumber, endPageNumber));
    }

    public PageRange addSinglePage(int pageNumber) {
        return addPageRangePart(new PageRangePartSingle(pageNumber));
    }

    public List<Integer> getQualifyingPageNums(int nbPages) {
        List<Integer> allPages = new ArrayList<>();
        for (IPageRangePart sequence : this.sequences) {
            allPages.addAll(sequence.getAllPagesInRange(nbPages));
        }
        return allPages;
    }

    public boolean isPageInRange(int pageNumber) {
        for (IPageRangePart sequence : this.sequences) {
            if (sequence.isPageInRange(pageNumber)) {
                return true;
            }
        }
        return false;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PageRange)) {
            return false;
        }
        return this.sequences.equals(((PageRange) obj).sequences);
    }

    public int hashCode() {
        int hashCode = 0;
        for (IPageRangePart part : this.sequences) {
            hashCode += part.hashCode();
        }
        return hashCode;
    }

    public static class PageRangePartSingle implements IPageRangePart {
        private final int page;

        public PageRangePartSingle(int page2) {
            this.page = page2;
        }

        public List<Integer> getAllPagesInRange(int nbPages) {
            int i = this.page;
            if (i <= nbPages) {
                return Collections.singletonList(Integer.valueOf(i));
            }
            return Collections.emptyList();
        }

        public boolean isPageInRange(int pageNumber) {
            return this.page == pageNumber;
        }

        public boolean equals(Object obj) {
            if ((obj instanceof PageRangePartSingle) && this.page == ((PageRangePartSingle) obj).page) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return this.page;
        }
    }

    public static class PageRangePartSequence implements IPageRangePart {
        private final int end;
        private final int start;

        public PageRangePartSequence(int start2, int end2) {
            this.start = start2;
            this.end = end2;
        }

        public List<Integer> getAllPagesInRange(int nbPages) {
            List<Integer> allPages = new ArrayList<>();
            int pageInRange = this.start;
            while (pageInRange <= this.end && pageInRange <= nbPages) {
                allPages.add(Integer.valueOf(pageInRange));
                pageInRange++;
            }
            return allPages;
        }

        public boolean isPageInRange(int pageNumber) {
            return this.start <= pageNumber && pageNumber <= this.end;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof PageRangePartSequence)) {
                return false;
            }
            PageRangePartSequence other = (PageRangePartSequence) obj;
            if (this.start == other.start && this.end == other.end) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (this.start * 31) + this.end;
        }
    }

    public static class PageRangePartAfter implements IPageRangePart {
        private final int start;

        public PageRangePartAfter(int start2) {
            this.start = start2;
        }

        public List<Integer> getAllPagesInRange(int nbPages) {
            List<Integer> allPages = new ArrayList<>();
            for (int pageInRange = this.start; pageInRange <= nbPages; pageInRange++) {
                allPages.add(Integer.valueOf(pageInRange));
            }
            return allPages;
        }

        public boolean isPageInRange(int pageNumber) {
            return this.start <= pageNumber;
        }

        public boolean equals(Object obj) {
            if ((obj instanceof PageRangePartAfter) && this.start == ((PageRangePartAfter) obj).start) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (this.start * 31) - 1;
        }
    }

    public static class PageRangePartOddEven implements IPageRangePart {
        public static final PageRangePartOddEven EVEN = new PageRangePartOddEven(false);
        public static final PageRangePartOddEven ODD = new PageRangePartOddEven(true);
        private final boolean isOdd;
        private final int mod;

        private PageRangePartOddEven(boolean isOdd2) {
            this.isOdd = isOdd2;
            if (isOdd2) {
                this.mod = 1;
            } else {
                this.mod = 0;
            }
        }

        public List<Integer> getAllPagesInRange(int nbPages) {
            List<Integer> allPages = new ArrayList<>();
            int pageInRange = this.mod;
            if (pageInRange == 0) {
                pageInRange = 2;
            }
            while (pageInRange <= nbPages) {
                allPages.add(Integer.valueOf(pageInRange));
                pageInRange += 2;
            }
            return allPages;
        }

        public boolean isPageInRange(int pageNumber) {
            return pageNumber % 2 == this.mod;
        }

        public boolean equals(Object obj) {
            if ((obj instanceof PageRangePartOddEven) && this.isOdd == ((PageRangePartOddEven) obj).isOdd) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            if (this.isOdd) {
                return 127;
            }
            return 128;
        }
    }

    public static class PageRangePartAnd implements IPageRangePart {
        private final List<IPageRangePart> conditions;

        public PageRangePartAnd(IPageRangePart... conditions2) {
            ArrayList arrayList = new ArrayList();
            this.conditions = arrayList;
            arrayList.addAll(Arrays.asList(conditions2));
        }

        public List<Integer> getAllPagesInRange(int nbPages) {
            List<Integer> allPages = new ArrayList<>();
            if (!this.conditions.isEmpty()) {
                allPages.addAll(this.conditions.get(0).getAllPagesInRange(nbPages));
            }
            for (IPageRangePart cond : this.conditions) {
                allPages.retainAll(cond.getAllPagesInRange(nbPages));
            }
            return allPages;
        }

        public boolean isPageInRange(int pageNumber) {
            for (IPageRangePart cond : this.conditions) {
                if (!cond.isPageInRange(pageNumber)) {
                    return false;
                }
            }
            return true;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof PageRangePartAnd)) {
                return false;
            }
            return this.conditions.equals(((PageRangePartAnd) obj).conditions);
        }

        public int hashCode() {
            int hashCode = 0;
            for (IPageRangePart part : this.conditions) {
                hashCode += part.hashCode();
            }
            return hashCode;
        }
    }
}
