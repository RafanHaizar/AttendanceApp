package com.itextpdf.forms.xfa;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.xfdf.XfdfConstants;
import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.VersionConforming;
import com.itextpdf.kernel.xmp.XmlDomWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XfaForm {
    private static final int INIT_SERIALIZER_BUFFER_SIZE = 16384;
    public static final String XFA_DATA_SCHEMA = "http://www.xfa.org/schema/xfa-data/1.0/";
    private AcroFieldsSearch acroFieldsSom;
    private Node datasetsNode;
    private Xml2SomDatasets datasetsSom;
    private Document domDocument;
    private Node templateNode;
    private boolean xfaPresent;

    public XfaForm() {
        this((InputStream) new ByteArrayInputStream("<?xml version=\"1.0\" encoding=\"UTF-8\"?><xdp:xdp xmlns:xdp=\"http://ns.adobe.com/xdp/\"><template xmlns=\"http://www.xfa.org/schema/xfa-template/3.3/\"></template><xfa:datasets xmlns:xfa=\"http://www.xfa.org/schema/xfa-data/1.0/\"><xfa:data></xfa:data></xfa:datasets></xdp:xdp>".getBytes(StandardCharsets.UTF_8)));
    }

    public XfaForm(InputStream inputStream) {
        this.xfaPresent = false;
        try {
            initXfaForm(inputStream);
        } catch (Exception e) {
            throw new PdfException((Throwable) e);
        }
    }

    public XfaForm(Document domDocument2) {
        this.xfaPresent = false;
        setDomDocument(domDocument2);
    }

    public XfaForm(PdfDictionary acroFormDictionary) {
        this.xfaPresent = false;
        PdfObject xfa = acroFormDictionary.get(PdfName.XFA);
        if (xfa != null) {
            try {
                initXfaForm(xfa);
            } catch (Exception e) {
                throw new PdfException((Throwable) e);
            }
        }
    }

    public XfaForm(PdfDocument pdfDocument) {
        this.xfaPresent = false;
        PdfObject xfa = getXfaObject(pdfDocument);
        if (xfa != null) {
            try {
                initXfaForm(xfa);
            } catch (Exception e) {
                throw new PdfException((Throwable) e);
            }
        }
    }

    public static void setXfaForm(XfaForm form, PdfDocument pdfDocument) throws IOException {
        setXfaForm(form, PdfAcroForm.getAcroForm(pdfDocument, true));
    }

    public static void setXfaForm(XfaForm form, PdfAcroForm acroForm) throws IOException {
        if (form == null || acroForm == null || acroForm.getPdfDocument() == null) {
            throw new IllegalArgumentException("XfaForm, PdfAcroForm and PdfAcroForm's document shall not be null");
        }
        PdfDocument document = acroForm.getPdfDocument();
        if (!VersionConforming.validatePdfVersionForDeprecatedFeatureLogError(document, PdfVersion.PDF_2_0, VersionConforming.DEPRECATED_XFA_FORMS)) {
            PdfObject xfa = getXfaObject(acroForm);
            if (xfa != null && xfa.isArray()) {
                PdfArray ar = (PdfArray) xfa;
                int t = -1;
                int d = -1;
                for (int k = 0; k < ar.size(); k += 2) {
                    PdfString s = ar.getAsString(k);
                    if ("template".equals(s.toString())) {
                        t = k + 1;
                    }
                    if ("datasets".equals(s.toString())) {
                        d = k + 1;
                    }
                }
                if (t > -1 && d > -1) {
                    PdfStream tStream = new PdfStream(serializeDocument(form.templateNode));
                    tStream.setCompressionLevel(document.getWriter().getCompressionLevel());
                    ar.set(t, tStream);
                    PdfStream dStream = new PdfStream(serializeDocument(form.datasetsNode));
                    dStream.setCompressionLevel(document.getWriter().getCompressionLevel());
                    ar.set(d, dStream);
                    ar.setModified();
                    ar.flush();
                    acroForm.put(PdfName.XFA, new PdfArray(ar));
                    acroForm.setModified();
                    if (!((PdfDictionary) acroForm.getPdfObject()).isIndirect()) {
                        document.getCatalog().setModified();
                        return;
                    }
                    return;
                }
            }
            PdfStream stream = new PdfStream(serializeDocument(form.domDocument));
            stream.setCompressionLevel(document.getWriter().getCompressionLevel());
            stream.flush();
            acroForm.put(PdfName.XFA, stream);
            acroForm.setModified();
            if (!((PdfDictionary) acroForm.getPdfObject()).isIndirect()) {
                document.getCatalog().setModified();
            }
        }
    }

    public static Map<String, Node> extractXFANodes(Document domDocument2) {
        Map<String, Node> xfaNodes = new HashMap<>();
        Node n = domDocument2.getFirstChild();
        while (n.getChildNodes().getLength() == 0) {
            n = n.getNextSibling();
        }
        for (Node n2 = n.getFirstChild(); n2 != null; n2 = n2.getNextSibling()) {
            if (n2.getNodeType() == 1) {
                xfaNodes.put(n2.getLocalName(), n2);
            }
        }
        return xfaNodes;
    }

    public void write(PdfDocument document) throws IOException {
        setXfaForm(this, document);
    }

    public void write(PdfAcroForm acroForm) throws IOException {
        setXfaForm(this, acroForm);
    }

    public void setXfaFieldValue(String name, String value) {
        String name2;
        if (isXfaPresent() && (name2 = findFieldName(name)) != null) {
            String shortName = Xml2Som.getShortName(name2);
            Node xn = findDatasetsNode(shortName);
            if (xn == null) {
                xn = this.datasetsSom.insertNode(getDatasetsNode(), shortName);
            }
            setNodeText(xn, value);
        }
    }

    public String getXfaFieldValue(String name) {
        String name2;
        if (!isXfaPresent() || (name2 = findFieldName(name)) == null) {
            return null;
        }
        return getNodeText(findDatasetsNode(Xml2Som.getShortName(name2)));
    }

    public boolean isXfaPresent() {
        return this.xfaPresent;
    }

    public String findFieldName(String name) {
        Xml2SomDatasets xml2SomDatasets;
        if (this.acroFieldsSom == null && this.xfaPresent && (xml2SomDatasets = this.datasetsSom) != null) {
            this.acroFieldsSom = new AcroFieldsSearch(xml2SomDatasets.getName2Node().keySet());
        }
        AcroFieldsSearch acroFieldsSearch = this.acroFieldsSom;
        if (acroFieldsSearch == null || !this.xfaPresent) {
            return null;
        }
        return acroFieldsSearch.getAcroShort2LongName().containsKey(name) ? this.acroFieldsSom.getAcroShort2LongName().get(name) : this.acroFieldsSom.inverseSearchGlobal(Xml2Som.splitParts(name));
    }

    public String findDatasetsName(String name) {
        return this.datasetsSom.getName2Node().containsKey(name) ? name : this.datasetsSom.inverseSearchGlobal(Xml2Som.splitParts(name));
    }

    public Node findDatasetsNode(String name) {
        String name2;
        if (name == null || (name2 = findDatasetsName(name)) == null) {
            return null;
        }
        return this.datasetsSom.getName2Node().get(name2);
    }

    public static String getNodeText(Node n) {
        return n == null ? "" : getNodeText(n, "");
    }

    public void setNodeText(Node n, String text) {
        if (n != null) {
            while (true) {
                Node firstChild = n.getFirstChild();
                Node nc = firstChild;
                if (firstChild == null) {
                    break;
                }
                n.removeChild(nc);
            }
            if (n.getAttributes().getNamedItemNS(XFA_DATA_SCHEMA, "dataNode") != null) {
                n.getAttributes().removeNamedItemNS(XFA_DATA_SCHEMA, "dataNode");
            }
            n.appendChild(this.domDocument.createTextNode(text));
        }
    }

    public Document getDomDocument() {
        return this.domDocument;
    }

    public void setDomDocument(Document domDocument2) {
        this.domDocument = domDocument2;
        extractNodes();
    }

    public Node getDatasetsNode() {
        return this.datasetsNode;
    }

    public void fillXfaForm(File file) throws IOException {
        fillXfaForm(file, false);
    }

    public void fillXfaForm(File file, boolean readOnly) throws IOException {
        fillXfaForm((InputStream) new FileInputStream(file), readOnly);
    }

    public void fillXfaForm(InputStream is) throws IOException {
        fillXfaForm(is, false);
    }

    public void fillXfaForm(InputStream is, boolean readOnly) throws IOException {
        fillXfaForm(new InputSource(is), readOnly);
    }

    public void fillXfaForm(InputSource is) throws IOException {
        fillXfaForm(is, false);
    }

    public void fillXfaForm(InputSource is, boolean readOnly) throws IOException {
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            db.setEntityResolver(new SafeEmptyEntityResolver());
            fillXfaForm((Node) db.parse(is).getDocumentElement(), readOnly);
        } catch (ParserConfigurationException e) {
            throw new PdfException((Throwable) e);
        } catch (SAXException e2) {
            throw new PdfException((Throwable) e2);
        }
    }

    public void fillXfaForm(Node node) {
        fillXfaForm(node, false);
    }

    public void fillXfaForm(Node node, boolean readOnly) {
        if (readOnly) {
            NodeList nodeList = this.domDocument.getElementsByTagName(XfdfConstants.FIELD);
            for (int i = 0; i < nodeList.getLength(); i++) {
                ((Element) nodeList.item(i)).setAttribute("access", "readOnly");
            }
        }
        NodeList allChilds = this.datasetsNode.getChildNodes();
        int len = allChilds.getLength();
        Node data = null;
        int k = 0;
        while (true) {
            if (k >= len) {
                break;
            }
            Node n = allChilds.item(k);
            if (n.getNodeType() == 1 && n.getLocalName().equals("data") && XFA_DATA_SCHEMA.equals(n.getNamespaceURI())) {
                data = n;
                break;
            }
            k++;
        }
        if (data == null) {
            data = this.datasetsNode.getOwnerDocument().createElementNS(XFA_DATA_SCHEMA, "xfa:data");
            this.datasetsNode.appendChild(data);
        }
        if (data.getChildNodes().getLength() == 0) {
            data.appendChild(this.domDocument.importNode(node, true));
        } else {
            Node firstNode = getFirstElementNode(data);
            if (firstNode != null) {
                data.replaceChild(this.domDocument.importNode(node, true), firstNode);
            }
        }
        extractNodes();
    }

    private static String getNodeText(Node n, String name) {
        for (Node n2 = n.getFirstChild(); n2 != null; n2 = n2.getNextSibling()) {
            if (n2.getNodeType() == 1) {
                name = getNodeText(n2, name);
            } else if (n2.getNodeType() == 3) {
                name = name + n2.getNodeValue();
            }
        }
        return name;
    }

    private static PdfObject getXfaObject(PdfDocument pdfDocument) {
        PdfDictionary af = ((PdfDictionary) pdfDocument.getCatalog().getPdfObject()).getAsDictionary(PdfName.AcroForm);
        if (af == null) {
            return null;
        }
        return af.get(PdfName.XFA);
    }

    private static PdfObject getXfaObject(PdfAcroForm acroForm) {
        if (acroForm == null || acroForm.getPdfObject() == null) {
            return null;
        }
        return ((PdfDictionary) acroForm.getPdfObject()).get(PdfName.XFA);
    }

    private static byte[] serializeDocument(Node n) throws IOException {
        XmlDomWriter xw = new XmlDomWriter(false);
        ByteArrayOutputStream fout = new ByteArrayOutputStream(16384);
        xw.setOutput(fout, (String) null);
        xw.write(n);
        fout.close();
        return fout.toByteArray();
    }

    private void initXfaForm(PdfObject xfa) throws IOException, ParserConfigurationException, SAXException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        if (xfa.isArray()) {
            PdfArray ar = (PdfArray) xfa;
            for (int k = 1; k < ar.size(); k += 2) {
                PdfObject ob = ar.get(k);
                if (ob instanceof PdfStream) {
                    bout.write(((PdfStream) ob).getBytes());
                }
            }
        } else if (xfa instanceof PdfStream) {
            bout.write(((PdfStream) xfa).getBytes());
        }
        bout.close();
        initXfaForm((InputStream) new ByteArrayInputStream(bout.toByteArray()));
    }

    private void initXfaForm(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
        fact.setNamespaceAware(true);
        DocumentBuilder db = fact.newDocumentBuilder();
        db.setEntityResolver(new SafeEmptyEntityResolver());
        setDomDocument(db.parse(inputStream));
        this.xfaPresent = true;
    }

    private void extractNodes() {
        Map<String, Node> xfaNodes = extractXFANodes(this.domDocument);
        if (xfaNodes.containsKey("template")) {
            this.templateNode = xfaNodes.get("template");
        }
        if (xfaNodes.containsKey("datasets")) {
            Node node = xfaNodes.get("datasets");
            this.datasetsNode = node;
            Node dataNode = findDataNode(node);
            this.datasetsSom = new Xml2SomDatasets(dataNode != null ? dataNode : this.datasetsNode.getFirstChild());
        }
        if (this.datasetsNode == null) {
            createDatasetsNode(this.domDocument.getFirstChild());
        }
    }

    private void createDatasetsNode(Node n) {
        while (n != null && n.getChildNodes().getLength() == 0) {
            n = n.getNextSibling();
        }
        if (n != null) {
            Element e = n.getOwnerDocument().createElement("xfa:datasets");
            e.setAttribute("xmlns:xfa", XFA_DATA_SCHEMA);
            this.datasetsNode = e;
            n.appendChild(e);
        }
    }

    private Node getFirstElementNode(Node src) {
        NodeList list = src.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            if (list.item(i).getNodeType() == 1) {
                return list.item(i);
            }
        }
        return null;
    }

    private Node findDataNode(Node datasetsNode2) {
        NodeList childNodes = datasetsNode2.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i).getNodeName().equals("xfa:data")) {
                return childNodes.item(i);
            }
        }
        return null;
    }

    private static class SafeEmptyEntityResolver implements EntityResolver {
        private SafeEmptyEntityResolver() {
        }

        public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
            return new InputSource(new StringReader(""));
        }
    }
}
