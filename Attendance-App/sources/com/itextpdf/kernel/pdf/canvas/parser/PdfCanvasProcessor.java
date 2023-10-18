package com.itextpdf.kernel.pdf.canvas.parser;

import com.itextpdf.forms.xfdf.XfdfConstants;
import com.itextpdf.kernel.KernelLogMessageConstant;
import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.colors.CalGray;
import com.itextpdf.kernel.colors.CalRgb;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.colors.DeviceGray;
import com.itextpdf.kernel.colors.DeviceN;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.colors.IccBased;
import com.itextpdf.kernel.colors.Indexed;
import com.itextpdf.kernel.colors.Lab;
import com.itextpdf.kernel.colors.PatternColor;
import com.itextpdf.kernel.colors.Separation;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Matrix;
import com.itextpdf.kernel.geom.NoninvertibleTransformException;
import com.itextpdf.kernel.geom.Path;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfIndirectReference;
import com.itextpdf.kernel.pdf.PdfLiteral;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfResources;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.canvas.CanvasTag;
import com.itextpdf.kernel.pdf.canvas.parser.data.AbstractRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.data.ClippingPathInfo;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.ImageRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.data.PathRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.listener.IEventListener;
import com.itextpdf.kernel.pdf.canvas.parser.util.PdfCanvasParser;
import com.itextpdf.kernel.pdf.colorspace.PdfCieBasedCs;
import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
import com.itextpdf.kernel.pdf.colorspace.PdfPattern;
import com.itextpdf.kernel.pdf.colorspace.PdfSpecialCs;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.p026io.source.PdfTokenizer;
import com.itextpdf.p026io.source.RandomAccessFileOrArray;
import com.itextpdf.p026io.source.RandomAccessSourceFactory;
import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.svg.SvgConstants;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.slf4j.LoggerFactory;

public class PdfCanvasProcessor {
    public static final String DEFAULT_OPERATOR = "DefaultOperator";
    private Map<Integer, WeakReference<PdfFont>> cachedFonts;
    protected int clippingRule;
    protected Path currentPath;
    protected final IEventListener eventListener;
    /* access modifiers changed from: private */
    public final Stack<ParserGraphicsState> gsStack;
    protected boolean isClip;
    /* access modifiers changed from: private */
    public Stack<CanvasTag> markedContentStack;
    private Map<String, IContentOperator> operators;
    private Stack<PdfResources> resourcesStack;
    protected final Set<EventType> supportedEvents;
    /* access modifiers changed from: private */
    public Matrix textLineMatrix;
    /* access modifiers changed from: private */
    public Matrix textMatrix;
    private Map<PdfName, IXObjectDoHandler> xobjectDoHandlers;

    public PdfCanvasProcessor(IEventListener eventListener2) {
        this.currentPath = new Path();
        this.gsStack = new Stack<>();
        this.cachedFonts = new HashMap();
        this.markedContentStack = new Stack<>();
        this.eventListener = eventListener2;
        this.supportedEvents = eventListener2.getSupportedEvents();
        this.operators = new HashMap();
        populateOperators();
        this.xobjectDoHandlers = new HashMap();
        populateXObjectDoHandlers();
        reset();
    }

    public PdfCanvasProcessor(IEventListener eventListener2, Map<String, IContentOperator> additionalContentOperators) {
        this(eventListener2);
        for (Map.Entry<String, IContentOperator> entry : additionalContentOperators.entrySet()) {
            registerContentOperator(entry.getKey(), entry.getValue());
        }
    }

    public IXObjectDoHandler registerXObjectDoHandler(PdfName xobjectSubType, IXObjectDoHandler handler) {
        return this.xobjectDoHandlers.put(xobjectSubType, handler);
    }

    public IContentOperator registerContentOperator(String operatorString, IContentOperator operator) {
        return this.operators.put(operatorString, operator);
    }

    public Collection<String> getRegisteredOperatorStrings() {
        return new ArrayList(this.operators.keySet());
    }

    public void reset() {
        this.gsStack.removeAllElements();
        this.gsStack.push(new ParserGraphicsState());
        this.textMatrix = null;
        this.textLineMatrix = null;
        this.resourcesStack = new Stack<>();
        this.isClip = false;
        this.currentPath = new Path();
    }

    public ParserGraphicsState getGraphicsState() {
        return this.gsStack.peek();
    }

    public void processContent(byte[] contentBytes, PdfResources resources) {
        if (resources != null) {
            this.resourcesStack.push(resources);
            PdfCanvasParser ps = new PdfCanvasParser(new PdfTokenizer(new RandomAccessFileOrArray(new RandomAccessSourceFactory().createSource(contentBytes))), resources);
            List<PdfObject> operands = new ArrayList<>();
            while (ps.parse(operands).size() > 0) {
                try {
                    invokeOperator((PdfLiteral) operands.get(operands.size() - 1), operands);
                } catch (IOException e) {
                    throw new PdfException(PdfException.CannotParseContentStream, (Throwable) e);
                }
            }
            this.resourcesStack.pop();
            return;
        }
        throw new PdfException(PdfException.ResourcesCannotBeNull);
    }

    public void processPageContent(PdfPage page) {
        initClippingPath(page);
        ParserGraphicsState gs = getGraphicsState();
        eventOccurred(new ClippingPathInfo(gs, gs.getClippingPath(), gs.getCtm()), EventType.CLIP_PATH_CHANGED);
        processContent(page.getContentBytes(), page.getResources());
    }

    public IEventListener getEventListener() {
        return this.eventListener;
    }

    /* access modifiers changed from: protected */
    public void populateOperators() {
        registerContentOperator(DEFAULT_OPERATOR, new IgnoreOperator());
        registerContentOperator("q", new PushGraphicsStateOperator());
        registerContentOperator(SvgConstants.Attributes.PATH_DATA_QUAD_CURVE_TO, new PopGraphicsStateOperator());
        registerContentOperator(CommonCssConstants.f1610CM, new ModifyCurrentTransformationMatrixOperator());
        registerContentOperator("Do", new DoOperator());
        registerContentOperator("BMC", new BeginMarkedContentOperator());
        registerContentOperator("BDC", new BeginMarkedContentDictionaryOperator());
        registerContentOperator("EMC", new EndMarkedContentOperator());
        Set<EventType> set = this.supportedEvents;
        if (set == null || set.contains(EventType.RENDER_TEXT) || this.supportedEvents.contains(EventType.RENDER_PATH) || this.supportedEvents.contains(EventType.CLIP_PATH_CHANGED)) {
            registerContentOperator(SvgConstants.Tags.f1648G, new SetGrayFillOperator());
            registerContentOperator("G", new SetGrayStrokeOperator());
            registerContentOperator("rg", new SetRGBFillOperator());
            registerContentOperator("RG", new SetRGBStrokeOperator());
            registerContentOperator("k", new SetCMYKFillOperator());
            registerContentOperator("K", new SetCMYKStrokeOperator());
            registerContentOperator("cs", new SetColorSpaceFillOperator());
            registerContentOperator("CS", new SetColorSpaceStrokeOperator());
            registerContentOperator("sc", new SetColorFillOperator());
            registerContentOperator("SC", new SetColorStrokeOperator());
            registerContentOperator("scn", new SetColorFillOperator());
            registerContentOperator("SCN", new SetColorStrokeOperator());
            registerContentOperator("gs", new ProcessGraphicsStateResourceOperator());
        }
        Set<EventType> set2 = this.supportedEvents;
        if (set2 == null || set2.contains(EventType.RENDER_IMAGE)) {
            registerContentOperator("EI", new EndImageOperator());
        }
        Set<EventType> set3 = this.supportedEvents;
        if (set3 == null || set3.contains(EventType.RENDER_TEXT) || this.supportedEvents.contains(EventType.BEGIN_TEXT) || this.supportedEvents.contains(EventType.END_TEXT)) {
            registerContentOperator("BT", new BeginTextOperator());
            registerContentOperator("ET", new EndTextOperator());
        }
        Set<EventType> set4 = this.supportedEvents;
        if (set4 == null || set4.contains(EventType.RENDER_TEXT)) {
            SetTextCharacterSpacingOperator tcOperator = new SetTextCharacterSpacingOperator();
            registerContentOperator("Tc", tcOperator);
            SetTextWordSpacingOperator twOperator = new SetTextWordSpacingOperator();
            registerContentOperator("Tw", twOperator);
            registerContentOperator("Tz", new SetTextHorizontalScalingOperator());
            SetTextLeadingOperator tlOperator = new SetTextLeadingOperator();
            registerContentOperator("TL", tlOperator);
            registerContentOperator("Tf", new SetTextFontOperator());
            registerContentOperator("Tr", new SetTextRenderModeOperator());
            registerContentOperator("Ts", new SetTextRiseOperator());
            TextMoveStartNextLineOperator tdOperator = new TextMoveStartNextLineOperator();
            registerContentOperator("Td", tdOperator);
            registerContentOperator(StandardRoles.f1515TD, new TextMoveStartNextLineWithLeadingOperator(tdOperator, tlOperator));
            registerContentOperator("Tm", new TextSetTextMatrixOperator());
            TextMoveNextLineOperator tstarOperator = new TextMoveNextLineOperator(tdOperator);
            registerContentOperator("T*", tstarOperator);
            ShowTextOperator tjOperator = new ShowTextOperator();
            registerContentOperator("Tj", tjOperator);
            MoveNextLineAndShowTextOperator tickOperator = new MoveNextLineAndShowTextOperator(tstarOperator, tjOperator);
            registerContentOperator("'", tickOperator);
            registerContentOperator("\"", new MoveNextLineAndShowTextWithSpacingOperator(twOperator, tcOperator, tickOperator));
            registerContentOperator("TJ", new ShowTextArrayOperator());
        }
        Set<EventType> set5 = this.supportedEvents;
        if (set5 == null || set5.contains(EventType.CLIP_PATH_CHANGED) || this.supportedEvents.contains(EventType.RENDER_PATH)) {
            registerContentOperator("w", new SetLineWidthOperator());
            registerContentOperator("J", new SetLineCapOperator());
            registerContentOperator("j", new SetLineJoinOperator());
            registerContentOperator(SvgConstants.Attributes.PATH_DATA_MOVE_TO, new SetMiterLimitOperator());
            registerContentOperator(SvgConstants.Attributes.f1634D, new SetLineDashPatternOperator());
            registerContentOperator(SvgConstants.Attributes.PATH_DATA_REL_MOVE_TO, new MoveToOperator());
            registerContentOperator(SvgConstants.Attributes.PATH_DATA_REL_LINE_TO, new LineToOperator());
            registerContentOperator(SvgConstants.Attributes.PATH_DATA_REL_CURVE_TO, new CurveOperator());
            registerContentOperator(SvgConstants.Attributes.PATH_DATA_REL_LINE_TO_V, new CurveFirstPointDuplicatedOperator());
            registerContentOperator(SvgConstants.Attributes.f1644Y, new CurveFourhPointDuplicatedOperator());
            registerContentOperator(SvgConstants.Attributes.PATH_DATA_REL_LINE_TO_H, new CloseSubpathOperator());
            registerContentOperator("re", new RectangleOperator());
            registerContentOperator(SvgConstants.Attributes.PATH_DATA_CURVE_TO_S, new PaintPathOperator(1, -1, false));
            registerContentOperator(SvgConstants.Attributes.PATH_DATA_REL_CURVE_TO_S, new PaintPathOperator(1, -1, true));
            registerContentOperator(XfdfConstants.f1185F, new PaintPathOperator(2, 1, false));
            registerContentOperator("F", new PaintPathOperator(2, 1, false));
            registerContentOperator("f*", new PaintPathOperator(2, 2, false));
            registerContentOperator(SvgConstants.Attributes.PATH_DATA_BEARING, new PaintPathOperator(3, 1, false));
            registerContentOperator("B*", new PaintPathOperator(3, 2, false));
            registerContentOperator(SvgConstants.Attributes.PATH_DATA_REL_BEARING, new PaintPathOperator(3, 1, true));
            registerContentOperator("b*", new PaintPathOperator(3, 2, true));
            registerContentOperator("n", new PaintPathOperator(0, -1, false));
            registerContentOperator("W", new ClipPathOperator(1));
            registerContentOperator("W*", new ClipPathOperator(2));
        }
    }

    /* access modifiers changed from: protected */
    public void paintPath(int operation, int rule) {
        ParserGraphicsState gs = getGraphicsState();
        eventOccurred(new PathRenderInfo(this.markedContentStack, gs, this.currentPath, operation, rule, this.isClip, this.clippingRule), EventType.RENDER_PATH);
        if (this.isClip) {
            this.isClip = false;
            gs.clip(this.currentPath, this.clippingRule);
            eventOccurred(new ClippingPathInfo(gs, gs.getClippingPath(), gs.getCtm()), EventType.CLIP_PATH_CHANGED);
        }
        this.currentPath = new Path();
    }

    /* access modifiers changed from: protected */
    public void invokeOperator(PdfLiteral operator, List<PdfObject> operands) {
        IContentOperator op = this.operators.get(operator.toString());
        if (op == null) {
            op = this.operators.get(DEFAULT_OPERATOR);
        }
        op.invoke(this, operator, operands);
    }

    /* access modifiers changed from: protected */
    public PdfStream getXObjectStream(PdfName xobjectName) {
        return getResources().getResource(PdfName.XObject).getAsStream(xobjectName);
    }

    /* access modifiers changed from: protected */
    public PdfResources getResources() {
        return this.resourcesStack.peek();
    }

    /* access modifiers changed from: protected */
    public void populateXObjectDoHandlers() {
        registerXObjectDoHandler(PdfName.Default, new IgnoreXObjectDoHandler());
        registerXObjectDoHandler(PdfName.Form, new FormXObjectDoHandler());
        Set<EventType> set = this.supportedEvents;
        if (set == null || set.contains(EventType.RENDER_IMAGE)) {
            registerXObjectDoHandler(PdfName.Image, new ImageXObjectDoHandler());
        }
    }

    /* access modifiers changed from: protected */
    public PdfFont getFont(PdfDictionary fontDict) {
        if (fontDict.getIndirectReference() == null) {
            return PdfFontFactory.createFont(fontDict);
        }
        int n = fontDict.getIndirectReference().getObjNumber();
        WeakReference<PdfFont> fontRef = this.cachedFonts.get(Integer.valueOf(n));
        PdfFont font = fontRef == null ? null : (PdfFont) fontRef.get();
        if (font != null) {
            return font;
        }
        PdfFont font2 = PdfFontFactory.createFont(fontDict);
        this.cachedFonts.put(Integer.valueOf(n), new WeakReference(font2));
        return font2;
    }

    /* access modifiers changed from: protected */
    public void beginMarkedContent(PdfName tag, PdfDictionary dict) {
        this.markedContentStack.push(new CanvasTag(tag).setProperties(dict));
    }

    /* access modifiers changed from: protected */
    public void endMarkedContent() {
        this.markedContentStack.pop();
    }

    /* access modifiers changed from: private */
    public void beginText() {
        eventOccurred((IEventData) null, EventType.BEGIN_TEXT);
    }

    /* access modifiers changed from: private */
    public void endText() {
        eventOccurred((IEventData) null, EventType.END_TEXT);
    }

    /* access modifiers changed from: protected */
    public void eventOccurred(IEventData data, EventType type) {
        Set<EventType> set = this.supportedEvents;
        if (set == null || set.contains(type)) {
            this.eventListener.eventOccurred(data, type);
        }
        if (data instanceof AbstractRenderInfo) {
            ((AbstractRenderInfo) data).releaseGraphicsState();
        }
    }

    /* access modifiers changed from: private */
    public void displayPdfString(PdfString string) {
        TextRenderInfo renderInfo = new TextRenderInfo(string, getGraphicsState(), this.textMatrix, this.markedContentStack);
        this.textMatrix = new Matrix(renderInfo.getUnscaledWidth(), 0.0f).multiply(this.textMatrix);
        eventOccurred(renderInfo, EventType.RENDER_TEXT);
    }

    /* access modifiers changed from: private */
    public void displayXObject(PdfName resourceName) {
        PdfStream xobjectStream = getXObjectStream(resourceName);
        IXObjectDoHandler handler = this.xobjectDoHandlers.get(xobjectStream.getAsName(PdfName.Subtype));
        if (handler == null) {
            handler = this.xobjectDoHandlers.get(PdfName.Default);
        }
        handler.handleXObject(this, this.markedContentStack, xobjectStream, resourceName);
    }

    /* access modifiers changed from: private */
    public void displayImage(Stack<CanvasTag> canvasTagHierarchy, PdfStream imageStream, PdfName resourceName, boolean isInline) {
        Stack<CanvasTag> stack = canvasTagHierarchy;
        eventOccurred(new ImageRenderInfo(stack, getGraphicsState(), getGraphicsState().getCtm(), imageStream, resourceName, getResources().getResource(PdfName.ColorSpace), isInline), EventType.RENDER_IMAGE);
    }

    /* access modifiers changed from: private */
    public void applyTextAdjust(float tj) {
        this.textMatrix = new Matrix(((-tj) / 1000.0f) * getGraphicsState().getFontSize() * (getGraphicsState().getHorizontalScaling() / 100.0f), 0.0f).multiply(this.textMatrix);
    }

    private void initClippingPath(PdfPage page) {
        Path clippingPath = new Path();
        clippingPath.rectangle(page.getCropBox());
        getGraphicsState().setClippingPath(clippingPath);
    }

    private static class IgnoreOperator implements IContentOperator {
        private IgnoreOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> list) {
        }
    }

    private static class ShowTextArrayOperator implements IContentOperator {
        private ShowTextArrayOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            Iterator<PdfObject> it = ((PdfArray) operands.get(0)).iterator();
            while (it.hasNext()) {
                PdfObject entryObj = it.next();
                if (entryObj instanceof PdfString) {
                    processor.displayPdfString((PdfString) entryObj);
                } else {
                    processor.applyTextAdjust(((PdfNumber) entryObj).floatValue());
                }
            }
        }
    }

    private static class MoveNextLineAndShowTextWithSpacingOperator implements IContentOperator {
        private final MoveNextLineAndShowTextOperator moveNextLineAndShowText;
        private final SetTextCharacterSpacingOperator setTextCharacterSpacing;
        private final SetTextWordSpacingOperator setTextWordSpacing;

        public MoveNextLineAndShowTextWithSpacingOperator(SetTextWordSpacingOperator setTextWordSpacing2, SetTextCharacterSpacingOperator setTextCharacterSpacing2, MoveNextLineAndShowTextOperator moveNextLineAndShowText2) {
            this.setTextWordSpacing = setTextWordSpacing2;
            this.setTextCharacterSpacing = setTextCharacterSpacing2;
            this.moveNextLineAndShowText = moveNextLineAndShowText2;
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            List<PdfObject> twOperands = new ArrayList<>(1);
            twOperands.add(0, (PdfNumber) operands.get(0));
            this.setTextWordSpacing.invoke(processor, (PdfLiteral) null, twOperands);
            List<PdfObject> tcOperands = new ArrayList<>(1);
            tcOperands.add(0, (PdfNumber) operands.get(1));
            this.setTextCharacterSpacing.invoke(processor, (PdfLiteral) null, tcOperands);
            List<PdfObject> tickOperands = new ArrayList<>(1);
            tickOperands.add(0, (PdfString) operands.get(2));
            this.moveNextLineAndShowText.invoke(processor, (PdfLiteral) null, tickOperands);
        }
    }

    private static class MoveNextLineAndShowTextOperator implements IContentOperator {
        private final ShowTextOperator showText;
        private final TextMoveNextLineOperator textMoveNextLine;

        public MoveNextLineAndShowTextOperator(TextMoveNextLineOperator textMoveNextLine2, ShowTextOperator showText2) {
            this.textMoveNextLine = textMoveNextLine2;
            this.showText = showText2;
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            this.textMoveNextLine.invoke(processor, (PdfLiteral) null, new ArrayList(0));
            this.showText.invoke(processor, (PdfLiteral) null, operands);
        }
    }

    private static class ShowTextOperator implements IContentOperator {
        private ShowTextOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            processor.displayPdfString((PdfString) operands.get(0));
        }
    }

    private static class TextMoveNextLineOperator implements IContentOperator {
        private final TextMoveStartNextLineOperator moveStartNextLine;

        public TextMoveNextLineOperator(TextMoveStartNextLineOperator moveStartNextLine2) {
            this.moveStartNextLine = moveStartNextLine2;
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> list) {
            List<PdfObject> tdoperands = new ArrayList<>(2);
            tdoperands.add(0, new PdfNumber(0));
            tdoperands.add(1, new PdfNumber((double) (-processor.getGraphicsState().getLeading())));
            this.moveStartNextLine.invoke(processor, (PdfLiteral) null, tdoperands);
        }
    }

    private static class TextSetTextMatrixOperator implements IContentOperator {
        private TextSetTextMatrixOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            PdfCanvasProcessor pdfCanvasProcessor = processor;
            List<PdfObject> list = operands;
            Matrix unused = pdfCanvasProcessor.textLineMatrix = new Matrix(((PdfNumber) list.get(0)).floatValue(), ((PdfNumber) list.get(1)).floatValue(), ((PdfNumber) list.get(2)).floatValue(), ((PdfNumber) list.get(3)).floatValue(), ((PdfNumber) list.get(4)).floatValue(), ((PdfNumber) list.get(5)).floatValue());
            Matrix unused2 = pdfCanvasProcessor.textMatrix = processor.textLineMatrix;
        }
    }

    private static class TextMoveStartNextLineWithLeadingOperator implements IContentOperator {
        private final TextMoveStartNextLineOperator moveStartNextLine;
        private final SetTextLeadingOperator setTextLeading;

        public TextMoveStartNextLineWithLeadingOperator(TextMoveStartNextLineOperator moveStartNextLine2, SetTextLeadingOperator setTextLeading2) {
            this.moveStartNextLine = moveStartNextLine2;
            this.setTextLeading = setTextLeading2;
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            float ty = ((PdfNumber) operands.get(1)).floatValue();
            List<PdfObject> tlOperands = new ArrayList<>(1);
            tlOperands.add(0, new PdfNumber((double) (-ty)));
            this.setTextLeading.invoke(processor, (PdfLiteral) null, tlOperands);
            this.moveStartNextLine.invoke(processor, (PdfLiteral) null, operands);
        }
    }

    private static class TextMoveStartNextLineOperator implements IContentOperator {
        private TextMoveStartNextLineOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            Matrix unused = processor.textMatrix = new Matrix(((PdfNumber) operands.get(0)).floatValue(), ((PdfNumber) operands.get(1)).floatValue()).multiply(processor.textLineMatrix);
            Matrix unused2 = processor.textLineMatrix = processor.textMatrix;
        }
    }

    private static class SetTextFontOperator implements IContentOperator {
        private SetTextFontOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            float size = ((PdfNumber) operands.get(1)).floatValue();
            processor.getGraphicsState().setFont(processor.getFont(processor.getResources().getResource(PdfName.Font).getAsDictionary((PdfName) operands.get(0))));
            processor.getGraphicsState().setFontSize(size);
        }
    }

    private static class SetTextRenderModeOperator implements IContentOperator {
        private SetTextRenderModeOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            processor.getGraphicsState().setTextRenderingMode(((PdfNumber) operands.get(0)).intValue());
        }
    }

    private static class SetTextRiseOperator implements IContentOperator {
        private SetTextRiseOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            processor.getGraphicsState().setTextRise(((PdfNumber) operands.get(0)).floatValue());
        }
    }

    private static class SetTextLeadingOperator implements IContentOperator {
        private SetTextLeadingOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            processor.getGraphicsState().setLeading(((PdfNumber) operands.get(0)).floatValue());
        }
    }

    private static class SetTextHorizontalScalingOperator implements IContentOperator {
        private SetTextHorizontalScalingOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            processor.getGraphicsState().setHorizontalScaling(((PdfNumber) operands.get(0)).floatValue());
        }
    }

    private static class SetTextCharacterSpacingOperator implements IContentOperator {
        private SetTextCharacterSpacingOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            processor.getGraphicsState().setCharSpacing(((PdfNumber) operands.get(0)).floatValue());
        }
    }

    private static class SetTextWordSpacingOperator implements IContentOperator {
        private SetTextWordSpacingOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            processor.getGraphicsState().setWordSpacing(((PdfNumber) operands.get(0)).floatValue());
        }
    }

    private static class ProcessGraphicsStateResourceOperator implements IContentOperator {
        private ProcessGraphicsStateResourceOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            PdfName dictionaryName = (PdfName) operands.get(0);
            PdfDictionary extGState = processor.getResources().getResource(PdfName.ExtGState);
            if (extGState != null) {
                PdfDictionary gsDic = extGState.getAsDictionary(dictionaryName);
                if (gsDic == null && (gsDic = extGState.getAsStream(dictionaryName)) == null) {
                    throw new PdfException(PdfException._1IsAnUnknownGraphicsStateDictionary).setMessageParams(dictionaryName);
                }
                PdfArray fontParameter = gsDic.getAsArray(PdfName.Font);
                if (fontParameter != null) {
                    PdfFont font = processor.getFont(fontParameter.getAsDictionary(0));
                    float size = fontParameter.getAsNumber(1).floatValue();
                    processor.getGraphicsState().setFont(font);
                    processor.getGraphicsState().setFontSize(size);
                }
                processor.getGraphicsState().updateFromExtGState(new PdfExtGState(gsDic.clone(Collections.singletonList(PdfName.Font))));
                return;
            }
            throw new PdfException(PdfException.ResourcesDoNotContainExtgstateEntryUnableToProcessOperator1).setMessageParams(operator);
        }
    }

    private static class PushGraphicsStateOperator implements IContentOperator {
        private PushGraphicsStateOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> list) {
            processor.gsStack.push(new ParserGraphicsState((ParserGraphicsState) processor.gsStack.peek()));
        }
    }

    private static class ModifyCurrentTransformationMatrixOperator implements IContentOperator {
        private ModifyCurrentTransformationMatrixOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            List<PdfObject> list = operands;
            try {
                processor.getGraphicsState().updateCtm(new Matrix(((PdfNumber) list.get(0)).floatValue(), ((PdfNumber) list.get(1)).floatValue(), ((PdfNumber) list.get(2)).floatValue(), ((PdfNumber) list.get(3)).floatValue(), ((PdfNumber) list.get(4)).floatValue(), ((PdfNumber) list.get(5)).floatValue()));
            } catch (PdfException exception) {
                if (exception.getCause() instanceof NoninvertibleTransformException) {
                    LoggerFactory.getLogger((Class<?>) PdfCanvasProcessor.class).error(MessageFormatUtil.format(LogMessageConstant.FAILED_TO_PROCESS_A_TRANSFORMATION_MATRIX, new Object[0]));
                    return;
                }
                throw exception;
            }
        }
    }

    /* access modifiers changed from: private */
    public static Color getColor(PdfColorSpace pdfColorSpace, List<PdfObject> operands, PdfResources resources) {
        PdfObject pdfObject;
        PdfPattern pattern;
        if (pdfColorSpace.getPdfObject().isIndirectReference()) {
            pdfObject = ((PdfIndirectReference) pdfColorSpace.getPdfObject()).getRefersTo();
        } else {
            pdfObject = pdfColorSpace.getPdfObject();
        }
        if (pdfObject.isName()) {
            if (PdfName.DeviceGray.equals(pdfObject)) {
                return new DeviceGray(getColorants(operands)[0]);
            }
            if (PdfName.Pattern.equals(pdfObject) && (operands.get(0) instanceof PdfName) && (pattern = resources.getPattern((PdfName) operands.get(0))) != null) {
                return new PatternColor(pattern);
            }
            if (PdfName.DeviceRGB.equals(pdfObject)) {
                float[] c = getColorants(operands);
                return new DeviceRgb(c[0], c[1], c[2]);
            } else if (PdfName.DeviceCMYK.equals(pdfObject)) {
                float[] c2 = getColorants(operands);
                return new DeviceCmyk(c2[0], c2[1], c2[2], c2[3]);
            }
        } else if (pdfObject.isArray()) {
            PdfName csType = ((PdfArray) pdfObject).getAsName(0);
            if (PdfName.CalGray.equals(csType)) {
                return new CalGray((PdfCieBasedCs.CalGray) pdfColorSpace, getColorants(operands)[0]);
            }
            if (PdfName.CalRGB.equals(csType)) {
                return new CalRgb((PdfCieBasedCs.CalRgb) pdfColorSpace, getColorants(operands));
            }
            if (PdfName.Lab.equals(csType)) {
                return new Lab((PdfCieBasedCs.Lab) pdfColorSpace, getColorants(operands));
            }
            if (PdfName.ICCBased.equals(csType)) {
                return new IccBased((PdfCieBasedCs.IccBased) pdfColorSpace, getColorants(operands));
            }
            if (PdfName.Indexed.equals(csType)) {
                return new Indexed(pdfColorSpace, (int) getColorants(operands)[0]);
            }
            if (PdfName.Separation.equals(csType)) {
                return new Separation((PdfSpecialCs.Separation) pdfColorSpace, getColorants(operands)[0]);
            }
            if (PdfName.DeviceN.equals(csType)) {
                return new DeviceN((PdfSpecialCs.DeviceN) pdfColorSpace, getColorants(operands));
            }
            if (PdfName.Pattern.equals(csType)) {
                List<PdfObject> underlyingOperands = new ArrayList<>(operands);
                PdfObject patternName = underlyingOperands.remove(operands.size() - 2);
                PdfColorSpace underlyingCs = ((PdfSpecialCs.UncoloredTilingPattern) pdfColorSpace).getUnderlyingColorSpace();
                if (patternName instanceof PdfName) {
                    PdfPattern pattern2 = resources.getPattern((PdfName) patternName);
                    if ((pattern2 instanceof PdfPattern.Tiling) && !((PdfPattern.Tiling) pattern2).isColored()) {
                        return new PatternColor((PdfPattern.Tiling) pattern2, underlyingCs, getColorants(underlyingOperands));
                    }
                }
            }
        }
        LoggerFactory.getLogger((Class<?>) PdfCanvasProcessor.class).warn(MessageFormatUtil.format(KernelLogMessageConstant.UNABLE_TO_PARSE_COLOR_WITHIN_COLORSPACE, Arrays.toString(operands.toArray()), pdfColorSpace.getPdfObject()));
        return null;
    }

    /* access modifiers changed from: private */
    public static Color getColor(int nOperands, List<PdfObject> operands) {
        float[] c = new float[nOperands];
        for (int i = 0; i < nOperands; i++) {
            c[i] = ((PdfNumber) operands.get(i)).floatValue();
        }
        switch (nOperands) {
            case 1:
                return new DeviceGray(c[0]);
            case 3:
                return new DeviceRgb(c[0], c[1], c[2]);
            case 4:
                return new DeviceCmyk(c[0], c[1], c[2], c[3]);
            default:
                return null;
        }
    }

    private static float[] getColorants(List<PdfObject> operands) {
        float[] c = new float[(operands.size() - 1)];
        for (int i = 0; i < operands.size() - 1; i++) {
            c[i] = ((PdfNumber) operands.get(i)).floatValue();
        }
        return c;
    }

    protected static class PopGraphicsStateOperator implements IContentOperator {
        protected PopGraphicsStateOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> list) {
            processor.gsStack.pop();
            ParserGraphicsState gs = processor.getGraphicsState();
            processor.eventOccurred(new ClippingPathInfo(gs, gs.getClippingPath(), gs.getCtm()), EventType.CLIP_PATH_CHANGED);
        }
    }

    private static class SetGrayFillOperator implements IContentOperator {
        private SetGrayFillOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            processor.getGraphicsState().setFillColor(PdfCanvasProcessor.getColor(1, operands));
        }
    }

    private static class SetGrayStrokeOperator implements IContentOperator {
        private SetGrayStrokeOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            processor.getGraphicsState().setStrokeColor(PdfCanvasProcessor.getColor(1, operands));
        }
    }

    private static class SetRGBFillOperator implements IContentOperator {
        private SetRGBFillOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            processor.getGraphicsState().setFillColor(PdfCanvasProcessor.getColor(3, operands));
        }
    }

    private static class SetRGBStrokeOperator implements IContentOperator {
        private SetRGBStrokeOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            processor.getGraphicsState().setStrokeColor(PdfCanvasProcessor.getColor(3, operands));
        }
    }

    private static class SetCMYKFillOperator implements IContentOperator {
        private SetCMYKFillOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            processor.getGraphicsState().setFillColor(PdfCanvasProcessor.getColor(4, operands));
        }
    }

    private static class SetCMYKStrokeOperator implements IContentOperator {
        private SetCMYKStrokeOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            processor.getGraphicsState().setStrokeColor(PdfCanvasProcessor.getColor(4, operands));
        }
    }

    private static class SetColorSpaceFillOperator implements IContentOperator {
        private SetColorSpaceFillOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            processor.getGraphicsState().setFillColor(Color.makeColor(determineColorSpace((PdfName) operands.get(0), processor)));
        }

        static PdfColorSpace determineColorSpace(PdfName colorSpace, PdfCanvasProcessor processor) {
            if (PdfColorSpace.directColorSpaces.contains(colorSpace)) {
                return PdfColorSpace.makeColorSpace(colorSpace);
            }
            return PdfColorSpace.makeColorSpace(((PdfDictionary) processor.getResources().getPdfObject()).getAsDictionary(PdfName.ColorSpace).get(colorSpace));
        }
    }

    private static class SetColorSpaceStrokeOperator implements IContentOperator {
        private SetColorSpaceStrokeOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            processor.getGraphicsState().setStrokeColor(Color.makeColor(SetColorSpaceFillOperator.determineColorSpace((PdfName) operands.get(0), processor)));
        }
    }

    private static class SetColorFillOperator implements IContentOperator {
        private SetColorFillOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            processor.getGraphicsState().setFillColor(PdfCanvasProcessor.getColor(processor.getGraphicsState().getFillColor().getColorSpace(), operands, processor.getResources()));
        }
    }

    private static class SetColorStrokeOperator implements IContentOperator {
        private SetColorStrokeOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            processor.getGraphicsState().setStrokeColor(PdfCanvasProcessor.getColor(processor.getGraphicsState().getStrokeColor().getColorSpace(), operands, processor.getResources()));
        }
    }

    private static class BeginTextOperator implements IContentOperator {
        private BeginTextOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> list) {
            Matrix unused = processor.textMatrix = new Matrix();
            Matrix unused2 = processor.textLineMatrix = processor.textMatrix;
            processor.beginText();
        }
    }

    private static class EndTextOperator implements IContentOperator {
        private EndTextOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> list) {
            Matrix unused = processor.textMatrix = null;
            Matrix unused2 = processor.textLineMatrix = null;
            processor.endText();
        }
    }

    private static class BeginMarkedContentOperator implements IContentOperator {
        private BeginMarkedContentOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            processor.beginMarkedContent((PdfName) operands.get(0), (PdfDictionary) null);
        }
    }

    private static class BeginMarkedContentDictionaryOperator implements IContentOperator {
        private BeginMarkedContentDictionaryOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            processor.beginMarkedContent((PdfName) operands.get(0), getPropertiesDictionary(operands.get(1), processor.getResources()));
        }

        /* access modifiers changed from: package-private */
        public PdfDictionary getPropertiesDictionary(PdfObject operand1, PdfResources resources) {
            if (operand1.isDictionary()) {
                return (PdfDictionary) operand1;
            }
            PdfName dictionaryName = (PdfName) operand1;
            PdfDictionary properties = resources.getResource(PdfName.Properties);
            if (properties == null) {
                LoggerFactory.getLogger((Class<?>) PdfCanvasProcessor.class).warn(MessageFormatUtil.format(LogMessageConstant.PDF_REFERS_TO_NOT_EXISTING_PROPERTY_DICTIONARY, PdfName.Properties));
                return null;
            } else if (properties.getAsDictionary(dictionaryName) != null) {
                return properties.getAsDictionary(dictionaryName);
            } else {
                LoggerFactory.getLogger((Class<?>) PdfCanvasProcessor.class).warn(MessageFormatUtil.format(LogMessageConstant.PDF_REFERS_TO_NOT_EXISTING_PROPERTY_DICTIONARY, dictionaryName));
                return null;
            }
        }
    }

    private static class EndMarkedContentOperator implements IContentOperator {
        private EndMarkedContentOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> list) {
            processor.endMarkedContent();
        }
    }

    private static class DoOperator implements IContentOperator {
        private DoOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            processor.displayXObject((PdfName) operands.get(0));
        }
    }

    private static class EndImageOperator implements IContentOperator {
        private EndImageOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            processor.displayImage(processor.markedContentStack, (PdfStream) operands.get(0), (PdfName) null, true);
        }
    }

    private static class SetLineWidthOperator implements IContentOperator {
        private SetLineWidthOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral oper, List<PdfObject> operands) {
            processor.getGraphicsState().setLineWidth(((PdfNumber) operands.get(0)).floatValue());
        }
    }

    private static class SetLineCapOperator implements IContentOperator {
        private SetLineCapOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral oper, List<PdfObject> operands) {
            processor.getGraphicsState().setLineCapStyle(((PdfNumber) operands.get(0)).intValue());
        }
    }

    private static class SetLineJoinOperator implements IContentOperator {
        private SetLineJoinOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral oper, List<PdfObject> operands) {
            processor.getGraphicsState().setLineJoinStyle(((PdfNumber) operands.get(0)).intValue());
        }
    }

    private static class SetMiterLimitOperator implements IContentOperator {
        private SetMiterLimitOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral oper, List<PdfObject> operands) {
            processor.getGraphicsState().setMiterLimit(((PdfNumber) operands.get(0)).floatValue());
        }
    }

    private static class SetLineDashPatternOperator implements IContentOperator {
        private SetLineDashPatternOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral oper, List<PdfObject> operands) {
            processor.getGraphicsState().setDashPattern(new PdfArray((List<? extends PdfObject>) Arrays.asList(new PdfObject[]{operands.get(0), operands.get(1)})));
        }
    }

    private static class FormXObjectDoHandler implements IXObjectDoHandler {
        private FormXObjectDoHandler() {
        }

        public void handleXObject(PdfCanvasProcessor processor, Stack<CanvasTag> stack, PdfStream xObjectStream, PdfName xObjectName) {
            PdfResources resources;
            PdfCanvasProcessor pdfCanvasProcessor = processor;
            PdfStream pdfStream = xObjectStream;
            PdfDictionary resourcesDic = pdfStream.getAsDictionary(PdfName.Resources);
            if (resourcesDic == null) {
                resources = processor.getResources();
            } else {
                resources = new PdfResources(resourcesDic);
            }
            byte[] contentBytes = xObjectStream.getBytes();
            PdfArray matrix = pdfStream.getAsArray(PdfName.Matrix);
            new PushGraphicsStateOperator().invoke(pdfCanvasProcessor, (PdfLiteral) null, (List<PdfObject>) null);
            if (matrix != null) {
                processor.getGraphicsState().updateCtm(new Matrix(matrix.getAsNumber(0).floatValue(), matrix.getAsNumber(1).floatValue(), matrix.getAsNumber(2).floatValue(), matrix.getAsNumber(3).floatValue(), matrix.getAsNumber(4).floatValue(), matrix.getAsNumber(5).floatValue()));
            }
            pdfCanvasProcessor.processContent(contentBytes, resources);
            new PopGraphicsStateOperator().invoke(pdfCanvasProcessor, (PdfLiteral) null, (List<PdfObject>) null);
        }
    }

    private static class ImageXObjectDoHandler implements IXObjectDoHandler {
        private ImageXObjectDoHandler() {
        }

        public void handleXObject(PdfCanvasProcessor processor, Stack<CanvasTag> canvasTagHierarchy, PdfStream xObjectStream, PdfName resourceName) {
            processor.displayImage(canvasTagHierarchy, xObjectStream, resourceName, false);
        }
    }

    private static class IgnoreXObjectDoHandler implements IXObjectDoHandler {
        private IgnoreXObjectDoHandler() {
        }

        public void handleXObject(PdfCanvasProcessor processor, Stack<CanvasTag> stack, PdfStream xObjectStream, PdfName xObjectName) {
        }
    }

    private static class MoveToOperator implements IContentOperator {
        private MoveToOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            processor.currentPath.moveTo(((PdfNumber) operands.get(0)).floatValue(), ((PdfNumber) operands.get(1)).floatValue());
        }
    }

    private static class LineToOperator implements IContentOperator {
        private LineToOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            processor.currentPath.lineTo(((PdfNumber) operands.get(0)).floatValue(), ((PdfNumber) operands.get(1)).floatValue());
        }
    }

    private static class CurveOperator implements IContentOperator {
        private CurveOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            List<PdfObject> list = operands;
            processor.currentPath.curveTo(((PdfNumber) list.get(0)).floatValue(), ((PdfNumber) list.get(1)).floatValue(), ((PdfNumber) list.get(2)).floatValue(), ((PdfNumber) list.get(3)).floatValue(), ((PdfNumber) list.get(4)).floatValue(), ((PdfNumber) list.get(5)).floatValue());
        }
    }

    private static class CurveFirstPointDuplicatedOperator implements IContentOperator {
        private CurveFirstPointDuplicatedOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            processor.currentPath.curveTo(((PdfNumber) operands.get(0)).floatValue(), ((PdfNumber) operands.get(1)).floatValue(), ((PdfNumber) operands.get(2)).floatValue(), ((PdfNumber) operands.get(3)).floatValue());
        }
    }

    private static class CurveFourhPointDuplicatedOperator implements IContentOperator {
        private CurveFourhPointDuplicatedOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            processor.currentPath.curveFromTo(((PdfNumber) operands.get(0)).floatValue(), ((PdfNumber) operands.get(1)).floatValue(), ((PdfNumber) operands.get(2)).floatValue(), ((PdfNumber) operands.get(3)).floatValue());
        }
    }

    private static class CloseSubpathOperator implements IContentOperator {
        private CloseSubpathOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> list) {
            processor.currentPath.closeSubpath();
        }
    }

    private static class RectangleOperator implements IContentOperator {
        private RectangleOperator() {
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> operands) {
            processor.currentPath.rectangle(((PdfNumber) operands.get(0)).floatValue(), ((PdfNumber) operands.get(1)).floatValue(), ((PdfNumber) operands.get(2)).floatValue(), ((PdfNumber) operands.get(3)).floatValue());
        }
    }

    private static class PaintPathOperator implements IContentOperator {
        private boolean close;
        private int operation;
        private int rule;

        public PaintPathOperator(int operation2, int rule2, boolean close2) {
            this.operation = operation2;
            this.rule = rule2;
            this.close = close2;
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> list) {
            if (this.close) {
                processor.currentPath.closeSubpath();
            }
            processor.paintPath(this.operation, this.rule);
        }
    }

    private static class ClipPathOperator implements IContentOperator {
        private int rule;

        public ClipPathOperator(int rule2) {
            this.rule = rule2;
        }

        public void invoke(PdfCanvasProcessor processor, PdfLiteral operator, List<PdfObject> list) {
            processor.isClip = true;
            processor.clippingRule = this.rule;
        }
    }
}
