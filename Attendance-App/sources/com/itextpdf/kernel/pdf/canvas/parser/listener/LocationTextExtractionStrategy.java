package com.itextpdf.kernel.pdf.canvas.parser.listener;

import com.itextpdf.kernel.geom.LineSegment;
import com.itextpdf.kernel.geom.Matrix;
import com.itextpdf.kernel.geom.Vector;
import com.itextpdf.kernel.pdf.canvas.CanvasTag;
import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LocationTextExtractionStrategy implements ITextExtractionStrategy {
    private static boolean DUMP_STATE = false;
    private TextRenderInfo lastTextRenderInfo;
    private final List<TextChunk> locationalResult;
    private boolean rightToLeftRunDirection;
    private final ITextChunkLocationStrategy tclStrat;
    private boolean useActualText;

    public interface ITextChunkLocationStrategy {
        ITextChunkLocation createLocation(TextRenderInfo textRenderInfo, LineSegment lineSegment);
    }

    public LocationTextExtractionStrategy() {
        this(new ITextChunkLocationStrategy() {
            public ITextChunkLocation createLocation(TextRenderInfo renderInfo, LineSegment baseline) {
                return new TextChunkLocationDefaultImp(baseline.getStartPoint(), baseline.getEndPoint(), renderInfo.getSingleSpaceWidth());
            }
        });
    }

    public LocationTextExtractionStrategy(ITextChunkLocationStrategy strat) {
        this.locationalResult = new ArrayList();
        this.useActualText = false;
        this.rightToLeftRunDirection = false;
        this.tclStrat = strat;
    }

    public LocationTextExtractionStrategy setUseActualText(boolean useActualText2) {
        this.useActualText = useActualText2;
        return this;
    }

    public LocationTextExtractionStrategy setRightToLeftRunDirection(boolean rightToLeftRunDirection2) {
        this.rightToLeftRunDirection = rightToLeftRunDirection2;
        return this;
    }

    public boolean isUseActualText() {
        return this.useActualText;
    }

    public void eventOccurred(IEventData data, EventType type) {
        if (type.equals(EventType.RENDER_TEXT)) {
            TextRenderInfo renderInfo = (TextRenderInfo) data;
            LineSegment segment = renderInfo.getBaseline();
            if (renderInfo.getRise() != 0.0f) {
                segment = segment.transformBy(new Matrix(0.0f, -renderInfo.getRise()));
            }
            if (this.useActualText) {
                TextRenderInfo textRenderInfo = this.lastTextRenderInfo;
                CanvasTag lastTagWithActualText = textRenderInfo != null ? findLastTagWithActualText(textRenderInfo.getCanvasTagHierarchy()) : null;
                if (lastTagWithActualText == null || lastTagWithActualText != findLastTagWithActualText(renderInfo.getCanvasTagHierarchy())) {
                    String actualText = renderInfo.getActualText();
                    this.locationalResult.add(new TextChunk(actualText != null ? actualText : renderInfo.getText(), this.tclStrat.createLocation(renderInfo, segment)));
                } else {
                    List<TextChunk> list = this.locationalResult;
                    TextChunk lastTextChunk = list.get(list.size() - 1);
                    TextChunk merged = new TextChunk(lastTextChunk.getText(), this.tclStrat.createLocation(renderInfo, new LineSegment(new Vector(Math.min(lastTextChunk.getLocation().getStartLocation().get(0), segment.getStartPoint().get(0)), Math.min(lastTextChunk.getLocation().getStartLocation().get(1), segment.getStartPoint().get(1)), Math.min(lastTextChunk.getLocation().getStartLocation().get(2), segment.getStartPoint().get(2))), new Vector(Math.max(lastTextChunk.getLocation().getEndLocation().get(0), segment.getEndPoint().get(0)), Math.max(lastTextChunk.getLocation().getEndLocation().get(1), segment.getEndPoint().get(1)), Math.max(lastTextChunk.getLocation().getEndLocation().get(2), segment.getEndPoint().get(2))))));
                    List<TextChunk> list2 = this.locationalResult;
                    list2.set(list2.size() - 1, merged);
                }
            } else {
                this.locationalResult.add(new TextChunk(renderInfo.getText(), this.tclStrat.createLocation(renderInfo, segment)));
            }
            this.lastTextRenderInfo = renderInfo;
        }
    }

    public Set<EventType> getSupportedEvents() {
        return null;
    }

    public String getResultantText() {
        if (DUMP_STATE) {
            dumpState();
        }
        List<TextChunk> textChunks = new ArrayList<>(this.locationalResult);
        sortWithMarks(textChunks);
        StringBuilder sb = new StringBuilder();
        TextChunk lastChunk = null;
        for (TextChunk chunk : textChunks) {
            if (lastChunk == null) {
                sb.append(chunk.text);
            } else if (chunk.sameLine(lastChunk)) {
                if (isChunkAtWordBoundary(chunk, lastChunk) && !startsWithSpace(chunk.text) && !endsWithSpace(lastChunk.text)) {
                    sb.append(' ');
                }
                sb.append(chunk.text);
            } else {
                sb.append(10);
                sb.append(chunk.text);
            }
            lastChunk = chunk;
        }
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public boolean isChunkAtWordBoundary(TextChunk chunk, TextChunk previousChunk) {
        return chunk.getLocation().isAtWordBoundary(previousChunk.getLocation());
    }

    private boolean startsWithSpace(String str) {
        return str.length() != 0 && str.charAt(0) == ' ';
    }

    private boolean endsWithSpace(String str) {
        return str.length() != 0 && str.charAt(str.length() - 1) == ' ';
    }

    private void dumpState() {
        for (TextChunk location : this.locationalResult) {
            location.printDiagnostics();
            System.out.println();
        }
    }

    private CanvasTag findLastTagWithActualText(List<CanvasTag> canvasTagHierarchy) {
        for (CanvasTag tag : canvasTagHierarchy) {
            if (tag.getActualText() != null) {
                return tag;
            }
        }
        return null;
    }

    private void sortWithMarks(List<TextChunk> textChunks) {
        Map<TextChunk, TextChunkMarks> marks = new HashMap<>();
        List<TextChunk> toSort = new ArrayList<>();
        for (int markInd = 0; markInd < textChunks.size(); markInd++) {
            ITextChunkLocation location = textChunks.get(markInd).getLocation();
            if (location.getStartLocation().equals(location.getEndLocation())) {
                boolean foundBaseToAttachTo = false;
                int baseInd = 0;
                while (true) {
                    if (baseInd >= textChunks.size()) {
                        break;
                    }
                    if (markInd != baseInd) {
                        ITextChunkLocation baseLocation = textChunks.get(baseInd).getLocation();
                        if (!baseLocation.getStartLocation().equals(baseLocation.getEndLocation()) && TextChunkLocationDefaultImp.containsMark(baseLocation, location)) {
                            TextChunkMarks currentMarks = marks.get(textChunks.get(baseInd));
                            if (currentMarks == null) {
                                currentMarks = new TextChunkMarks();
                                marks.put(textChunks.get(baseInd), currentMarks);
                            }
                            if (markInd < baseInd) {
                                currentMarks.preceding.add(textChunks.get(markInd));
                            } else {
                                currentMarks.succeeding.add(textChunks.get(markInd));
                            }
                            foundBaseToAttachTo = true;
                        }
                    }
                    baseInd++;
                }
                if (!foundBaseToAttachTo) {
                    toSort.add(textChunks.get(markInd));
                }
            } else {
                toSort.add(textChunks.get(markInd));
            }
        }
        Collections.sort(toSort, new TextChunkLocationBasedComparator(new DefaultTextChunkLocationComparator(!this.rightToLeftRunDirection)));
        textChunks.clear();
        for (TextChunk current : toSort) {
            TextChunkMarks currentMarks2 = marks.get(current);
            if (currentMarks2 != null) {
                if (!this.rightToLeftRunDirection) {
                    for (int j = 0; j < currentMarks2.preceding.size(); j++) {
                        textChunks.add(currentMarks2.preceding.get(j));
                    }
                } else {
                    for (int j2 = currentMarks2.succeeding.size() - 1; j2 >= 0; j2--) {
                        textChunks.add(currentMarks2.succeeding.get(j2));
                    }
                }
            }
            textChunks.add(current);
            if (currentMarks2 != null) {
                if (!this.rightToLeftRunDirection) {
                    for (int j3 = 0; j3 < currentMarks2.succeeding.size(); j3++) {
                        textChunks.add(currentMarks2.succeeding.get(j3));
                    }
                } else {
                    for (int j4 = currentMarks2.preceding.size() - 1; j4 >= 0; j4--) {
                        textChunks.add(currentMarks2.preceding.get(j4));
                    }
                }
            }
        }
    }

    private static class TextChunkMarks {
        List<TextChunk> preceding;
        List<TextChunk> succeeding;

        private TextChunkMarks() {
            this.preceding = new ArrayList();
            this.succeeding = new ArrayList();
        }
    }
}
