package com.itextpdf.kernel.pdf.canvas.parser.listener;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.listener.CharacterRenderInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexBasedLocationExtractionStrategy implements ILocationExtractionStrategy {
    private List<CharacterRenderInfo> parseResult = new ArrayList();
    private Pattern pattern;

    public RegexBasedLocationExtractionStrategy(String regex) {
        this.pattern = Pattern.compile(regex);
    }

    public RegexBasedLocationExtractionStrategy(Pattern pattern2) {
        this.pattern = pattern2;
    }

    public Collection<IPdfTextLocation> getResultantLocations() {
        Collections.sort(this.parseResult, new TextChunkLocationBasedComparator(new DefaultTextChunkLocationComparator()));
        List<IPdfTextLocation> retval = new ArrayList<>();
        CharacterRenderInfo.StringConversionInfo txt = CharacterRenderInfo.mapString(this.parseResult);
        Matcher mat = this.pattern.matcher(txt.text);
        while (mat.find()) {
            Integer startIndex = getStartIndex(txt.indexMap, mat.start(), txt.text);
            Integer endIndex = getEndIndex(txt.indexMap, mat.end() - 1);
            if (!(startIndex == null || endIndex == null || startIndex.intValue() > endIndex.intValue())) {
                for (Rectangle r : toRectangles(this.parseResult.subList(startIndex.intValue(), endIndex.intValue() + 1))) {
                    retval.add(new DefaultPdfTextLocation(0, r, mat.group(0)));
                }
            }
        }
        Collections.sort(retval, new Comparator<IPdfTextLocation>() {
            public int compare(IPdfTextLocation l1, IPdfTextLocation l2) {
                Rectangle o1 = l1.getRectangle();
                Rectangle o2 = l2.getRectangle();
                if (o1.getY() == o2.getY()) {
                    if (o1.getX() == o2.getX()) {
                        return 0;
                    }
                    if (o1.getX() < o2.getX()) {
                        return -1;
                    }
                    return 1;
                } else if (o1.getY() < o2.getY()) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        removeDuplicates(retval);
        return retval;
    }

    private void removeDuplicates(List<IPdfTextLocation> sortedList) {
        IPdfTextLocation lastItem = null;
        for (int i = sortedList.size() - 1; i >= 0; i--) {
            IPdfTextLocation currItem = sortedList.get(i);
            Rectangle currRect = currItem.getRectangle();
            if (lastItem != null && currRect.equalsWithEpsilon(lastItem.getRectangle())) {
                sortedList.remove(currItem);
            }
            lastItem = currItem;
        }
    }

    public void eventOccurred(IEventData data, EventType type) {
        if (data instanceof TextRenderInfo) {
            this.parseResult.addAll(toCRI((TextRenderInfo) data));
        }
    }

    public Set<EventType> getSupportedEvents() {
        return null;
    }

    /* access modifiers changed from: protected */
    public List<CharacterRenderInfo> toCRI(TextRenderInfo tri) {
        List<CharacterRenderInfo> cris = new ArrayList<>();
        for (TextRenderInfo subTri : tri.getCharacterRenderInfos()) {
            cris.add(new CharacterRenderInfo(subTri));
        }
        return cris;
    }

    /* access modifiers changed from: protected */
    public List<Rectangle> toRectangles(List<CharacterRenderInfo> cris) {
        List<Rectangle> retval = new ArrayList<>();
        if (cris.isEmpty()) {
            return retval;
        }
        int prev = 0;
        int curr = 0;
        while (curr < cris.size()) {
            while (curr < cris.size() && cris.get(curr).sameLine(cris.get(prev))) {
                curr++;
            }
            Rectangle resultRectangle = null;
            for (CharacterRenderInfo cri : cris.subList(prev, curr)) {
                resultRectangle = Rectangle.getCommonRectangle(resultRectangle, cri.getBoundingBox());
            }
            retval.add(resultRectangle);
            prev = curr;
        }
        return retval;
    }

    private static Integer getStartIndex(Map<Integer, Integer> indexMap, int index, String txt) {
        while (!indexMap.containsKey(Integer.valueOf(index)) && index < txt.length()) {
            index++;
        }
        return indexMap.get(Integer.valueOf(index));
    }

    private static Integer getEndIndex(Map<Integer, Integer> indexMap, int index) {
        while (!indexMap.containsKey(Integer.valueOf(index)) && index >= 0) {
            index--;
        }
        return indexMap.get(Integer.valueOf(index));
    }
}
