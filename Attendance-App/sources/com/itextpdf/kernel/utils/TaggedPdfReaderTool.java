package com.itextpdf.kernel.utils;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.canvas.parser.EventType;
import com.itextpdf.kernel.pdf.canvas.parser.data.IEventData;
import com.itextpdf.kernel.pdf.canvas.parser.data.TextRenderInfo;
import com.itextpdf.kernel.pdf.canvas.parser.listener.IEventListener;
import com.itextpdf.kernel.pdf.canvas.parser.listener.ITextExtractionStrategy;
import com.itextpdf.kernel.pdf.canvas.parser.listener.LocationTextExtractionStrategy;
import com.itextpdf.kernel.pdf.tagging.IStructureNode;
import com.itextpdf.kernel.pdf.tagging.PdfMcr;
import com.itextpdf.kernel.pdf.tagging.PdfStructElem;
import com.itextpdf.kernel.pdf.tagging.PdfStructTreeRoot;
import com.itextpdf.p026io.font.PdfEncodings;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TaggedPdfReaderTool {
    protected PdfDocument document;
    protected OutputStreamWriter out;
    protected Map<PdfDictionary, Map<Integer, String>> parsedTags = new HashMap();
    protected String rootTag;

    public TaggedPdfReaderTool(PdfDocument document2) {
        this.document = document2;
    }

    public static boolean isValidCharacterValue(int c) {
        return c == 9 || c == 10 || c == 13 || (c >= 32 && c <= 55295) || ((c >= 57344 && c <= 65533) || (c >= 65536 && c <= 1114111));
    }

    public void convertToXml(OutputStream os) throws IOException {
        convertToXml(os, PdfEncodings.UTF8);
    }

    public void convertToXml(OutputStream os, String charset) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(os, Charset.forName(charset));
        this.out = outputStreamWriter;
        if (this.rootTag != null) {
            outputStreamWriter.write("<" + this.rootTag + ">" + System.lineSeparator());
        }
        PdfStructTreeRoot structTreeRoot = this.document.getStructTreeRoot();
        if (structTreeRoot != null) {
            inspectKids(structTreeRoot.getKids());
            if (this.rootTag != null) {
                this.out.write("</" + this.rootTag + ">");
            }
            this.out.flush();
            this.out.close();
            return;
        }
        throw new PdfException(PdfException.DocumentDoesntContainStructTreeRoot);
    }

    public TaggedPdfReaderTool setRootTag(String rootTagName) {
        this.rootTag = rootTagName;
        return this;
    }

    /* access modifiers changed from: protected */
    public void inspectKids(List<IStructureNode> kids) {
        if (kids != null) {
            for (IStructureNode kid : kids) {
                inspectKid(kid);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void inspectKid(IStructureNode kid) {
        try {
            if (kid instanceof PdfStructElem) {
                PdfStructElem structElemKid = (PdfStructElem) kid;
                String tag = fixTagName(structElemKid.getRole().getValue());
                this.out.write("<");
                this.out.write(tag);
                inspectAttributes(structElemKid);
                this.out.write(">" + System.lineSeparator());
                PdfString alt = structElemKid.getAlt();
                if (alt != null) {
                    this.out.write("<alt><![CDATA[");
                    this.out.write(alt.getValue().replaceAll("[\\000]*", ""));
                    this.out.write("]]></alt>" + System.lineSeparator());
                }
                inspectKids(structElemKid.getKids());
                this.out.write("</");
                this.out.write(tag);
                this.out.write(">" + System.lineSeparator());
            } else if (kid instanceof PdfMcr) {
                parseTag((PdfMcr) kid);
            } else {
                this.out.write(" <flushedKid/> ");
            }
        } catch (IOException e) {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.UnknownIOException, (Throwable) e);
        }
    }

    /* access modifiers changed from: protected */
    public void inspectAttributes(PdfStructElem kid) {
        PdfDictionary attrDict;
        PdfObject attrObj = kid.getAttributes(false);
        if (attrObj != null) {
            if (attrObj instanceof PdfArray) {
                attrDict = ((PdfArray) attrObj).getAsDictionary(0);
            } else {
                attrDict = (PdfDictionary) attrObj;
            }
            try {
                for (PdfName key : attrDict.keySet()) {
                    this.out.write(32);
                    String attrName = key.getValue();
                    this.out.write(Character.toLowerCase(attrName.charAt(0)) + attrName.substring(1));
                    this.out.write("=\"");
                    this.out.write(attrDict.get(key, false).toString());
                    this.out.write("\"");
                }
            } catch (IOException e) {
                throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.UnknownIOException, (Throwable) e);
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v14, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v3, resolved type: java.lang.String} */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void parseTag(com.itextpdf.kernel.pdf.tagging.PdfMcr r9) {
        /*
            r8 = this;
            int r0 = r9.getMcid()
            com.itextpdf.kernel.pdf.PdfDictionary r1 = r9.getPageObject()
            java.lang.String r2 = ""
            r3 = -1
            if (r0 == r3) goto L_0x0060
            java.util.Map<com.itextpdf.kernel.pdf.PdfDictionary, java.util.Map<java.lang.Integer, java.lang.String>> r3 = r8.parsedTags
            boolean r3 = r3.containsKey(r1)
            if (r3 != 0) goto L_0x003a
            com.itextpdf.kernel.utils.TaggedPdfReaderTool$MarkedContentEventListener r3 = new com.itextpdf.kernel.utils.TaggedPdfReaderTool$MarkedContentEventListener
            r4 = 0
            r3.<init>(r8, r4)
            com.itextpdf.kernel.pdf.canvas.parser.PdfCanvasProcessor r4 = new com.itextpdf.kernel.pdf.canvas.parser.PdfCanvasProcessor
            r4.<init>(r3)
            com.itextpdf.kernel.pdf.PdfDocument r5 = r8.document
            com.itextpdf.kernel.pdf.PdfPage r5 = r5.getPage((com.itextpdf.kernel.pdf.PdfDictionary) r1)
            byte[] r6 = r5.getContentBytes()
            com.itextpdf.kernel.pdf.PdfResources r7 = r5.getResources()
            r4.processContent(r6, r7)
            java.util.Map<com.itextpdf.kernel.pdf.PdfDictionary, java.util.Map<java.lang.Integer, java.lang.String>> r6 = r8.parsedTags
            java.util.Map r7 = r3.getMcidContent()
            r6.put(r1, r7)
        L_0x003a:
            java.util.Map<com.itextpdf.kernel.pdf.PdfDictionary, java.util.Map<java.lang.Integer, java.lang.String>> r3 = r8.parsedTags
            java.lang.Object r3 = r3.get(r1)
            java.util.Map r3 = (java.util.Map) r3
            java.lang.Integer r4 = java.lang.Integer.valueOf(r0)
            boolean r3 = r3.containsKey(r4)
            if (r3 == 0) goto L_0x007a
            java.util.Map<com.itextpdf.kernel.pdf.PdfDictionary, java.util.Map<java.lang.Integer, java.lang.String>> r3 = r8.parsedTags
            java.lang.Object r3 = r3.get(r1)
            java.util.Map r3 = (java.util.Map) r3
            java.lang.Integer r4 = java.lang.Integer.valueOf(r0)
            java.lang.Object r3 = r3.get(r4)
            r2 = r3
            java.lang.String r2 = (java.lang.String) r2
            goto L_0x007a
        L_0x0060:
            r3 = r9
            com.itextpdf.kernel.pdf.tagging.PdfObjRef r3 = (com.itextpdf.kernel.pdf.tagging.PdfObjRef) r3
            com.itextpdf.kernel.pdf.PdfDictionary r4 = r3.getReferencedObject()
            boolean r5 = r4.isDictionary()
            if (r5 == 0) goto L_0x007a
            r5 = r4
            com.itextpdf.kernel.pdf.PdfDictionary r5 = (com.itextpdf.kernel.pdf.PdfDictionary) r5
            com.itextpdf.kernel.pdf.PdfName r6 = com.itextpdf.kernel.pdf.PdfName.Subtype
            com.itextpdf.kernel.pdf.PdfName r5 = r5.getAsName(r6)
            java.lang.String r2 = r5.toString()
        L_0x007a:
            java.io.OutputStreamWriter r3 = r8.out     // Catch:{ IOException -> 0x0086 }
            r4 = 1
            java.lang.String r4 = escapeXML(r2, r4)     // Catch:{ IOException -> 0x0086 }
            r3.write(r4)     // Catch:{ IOException -> 0x0086 }
            return
        L_0x0086:
            r3 = move-exception
            com.itextpdf.io.IOException r4 = new com.itextpdf.io.IOException
            java.lang.String r5 = "Unknown I/O exception."
            r4.<init>((java.lang.String) r5, (java.lang.Throwable) r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.utils.TaggedPdfReaderTool.parseTag(com.itextpdf.kernel.pdf.tagging.PdfMcr):void");
    }

    protected static String fixTagName(String tag) {
        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < tag.length(); k++) {
            char c = tag.charAt(k);
            boolean nameMiddle = false;
            boolean nameStart = c == ':' || (c >= 'A' && c <= 'Z') || c == '_' || ((c >= 'a' && c <= 'z') || ((c >= 192 && c <= 214) || ((c >= 216 && c <= 246) || ((c >= 248 && c <= 767) || ((c >= 880 && c <= 893) || ((c >= 895 && c <= 8191) || ((c >= 8204 && c <= 8205) || ((c >= 8304 && c <= 8591) || ((c >= 11264 && c <= 12271) || ((c >= 12289 && c <= 55295) || ((c >= 63744 && c <= 64975) || (c >= 65008 && c <= 65533))))))))))));
            if (c == '-' || c == '.' || ((c >= '0' && c <= '9') || c == 183 || ((c >= 768 && c <= 879) || ((c >= 8255 && c <= 8256) || nameStart)))) {
                nameMiddle = true;
            }
            if (k == 0) {
                if (!nameStart) {
                    c = '_';
                }
            } else if (!nameMiddle) {
                c = '-';
            }
            sb.append(c);
        }
        return sb.toString();
    }

    protected static String escapeXML(String s, boolean onlyASCII) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            switch (c) {
                case '\"':
                    sb.append("&quot;");
                    break;
                case '&':
                    sb.append("&amp;");
                    break;
                case '\'':
                    sb.append("&apos;");
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                default:
                    if (isValidCharacterValue(c)) {
                        if (onlyASCII && c > 127) {
                            sb.append("&#").append(c).append(';');
                            break;
                        } else {
                            sb.append((char) c);
                            break;
                        }
                    } else {
                        break;
                    }
            }
        }
        return sb.toString();
    }

    private class MarkedContentEventListener implements IEventListener {
        private Map<Integer, ITextExtractionStrategy> contentByMcid;

        private MarkedContentEventListener() {
            this.contentByMcid = new HashMap();
        }

        /* synthetic */ MarkedContentEventListener(TaggedPdfReaderTool x0, C14451 x1) {
            this();
        }

        public Map<Integer, String> getMcidContent() {
            Map<Integer, String> content = new HashMap<>();
            for (Integer intValue : this.contentByMcid.keySet()) {
                int id = intValue.intValue();
                content.put(Integer.valueOf(id), this.contentByMcid.get(Integer.valueOf(id)).getResultantText());
            }
            return content;
        }

        public void eventOccurred(IEventData data, EventType type) {
            switch (C14451.$SwitchMap$com$itextpdf$kernel$pdf$canvas$parser$EventType[type.ordinal()]) {
                case 1:
                    int mcid = ((TextRenderInfo) data).getMcid();
                    if (mcid != -1) {
                        ITextExtractionStrategy textExtractionStrategy = this.contentByMcid.get(Integer.valueOf(mcid));
                        if (textExtractionStrategy == null) {
                            textExtractionStrategy = new LocationTextExtractionStrategy();
                            this.contentByMcid.put(Integer.valueOf(mcid), textExtractionStrategy);
                        }
                        textExtractionStrategy.eventOccurred(data, type);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }

        public Set<EventType> getSupportedEvents() {
            return null;
        }
    }

    /* renamed from: com.itextpdf.kernel.utils.TaggedPdfReaderTool$1 */
    static /* synthetic */ class C14451 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$kernel$pdf$canvas$parser$EventType;

        static {
            int[] iArr = new int[EventType.values().length];
            $SwitchMap$com$itextpdf$kernel$pdf$canvas$parser$EventType = iArr;
            try {
                iArr[EventType.RENDER_TEXT.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
        }
    }
}
