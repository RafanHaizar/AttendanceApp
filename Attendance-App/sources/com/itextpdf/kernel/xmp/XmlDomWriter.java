package com.itextpdf.kernel.xmp;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import kotlin.text.Typography;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class XmlDomWriter {
    protected boolean fCanonical;
    protected PrintWriter fOut;
    protected boolean fXML11;

    public XmlDomWriter() {
    }

    public XmlDomWriter(boolean canonical) {
        this.fCanonical = canonical;
    }

    public void setCanonical(boolean canonical) {
        this.fCanonical = canonical;
    }

    public void setOutput(OutputStream stream, String encoding) {
        if (encoding == null) {
            encoding = "UTF8";
        }
        this.fOut = new PrintWriter(new OutputStreamWriter(stream, Charset.forName(encoding)));
    }

    public void write(Node node) {
        if (node != null) {
            short type = node.getNodeType();
            switch (type) {
                case 1:
                    this.fOut.print(Typography.less);
                    this.fOut.print(node.getNodeName());
                    Attr[] attrs = sortAttributes(node.getAttributes());
                    for (Attr attr : attrs) {
                        this.fOut.print(' ');
                        this.fOut.print(attr.getNodeName());
                        this.fOut.print("=\"");
                        normalizeAndPrint(attr.getNodeValue(), true);
                        this.fOut.print(Typography.quote);
                    }
                    this.fOut.print(Typography.greater);
                    this.fOut.flush();
                    for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
                        write(child);
                    }
                    break;
                case 3:
                    normalizeAndPrint(node.getNodeValue(), false);
                    this.fOut.flush();
                    break;
                case 4:
                    if (this.fCanonical) {
                        normalizeAndPrint(node.getNodeValue(), false);
                    } else {
                        this.fOut.print("<![CDATA[");
                        this.fOut.print(node.getNodeValue());
                        this.fOut.print("]]>");
                    }
                    this.fOut.flush();
                    break;
                case 5:
                    if (!this.fCanonical) {
                        this.fOut.print(Typography.amp);
                        this.fOut.print(node.getNodeName());
                        this.fOut.print(';');
                        this.fOut.flush();
                        break;
                    } else {
                        for (Node child2 = node.getFirstChild(); child2 != null; child2 = child2.getNextSibling()) {
                            write(child2);
                        }
                        break;
                    }
                case 7:
                    this.fOut.print("<?");
                    this.fOut.print(node.getNodeName());
                    String data = node.getNodeValue();
                    if (data != null && data.length() > 0) {
                        this.fOut.print(' ');
                        this.fOut.print(data);
                    }
                    this.fOut.print("?>");
                    this.fOut.flush();
                    break;
                case 8:
                    if (!this.fCanonical) {
                        this.fOut.print("<!--");
                        String comment = node.getNodeValue();
                        if (comment != null && comment.length() > 0) {
                            this.fOut.print(comment);
                        }
                        this.fOut.print("-->");
                        this.fOut.flush();
                        break;
                    }
                    break;
                case 9:
                    Document document = (Document) node;
                    this.fXML11 = false;
                    if (!this.fCanonical) {
                        this.fOut.print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                        this.fOut.print("\n");
                        this.fOut.flush();
                        write(document.getDoctype());
                    }
                    write(document.getDocumentElement());
                    break;
                case 10:
                    DocumentType doctype = (DocumentType) node;
                    this.fOut.print("<!DOCTYPE ");
                    this.fOut.print(doctype.getName());
                    String publicId = doctype.getPublicId();
                    String systemId = doctype.getSystemId();
                    if (publicId != null) {
                        this.fOut.print(" PUBLIC '");
                        this.fOut.print(publicId);
                        this.fOut.print("' '");
                        this.fOut.print(systemId);
                        this.fOut.print('\'');
                    } else if (systemId != null) {
                        this.fOut.print(" SYSTEM '");
                        this.fOut.print(systemId);
                        this.fOut.print('\'');
                    }
                    String internalSubset = doctype.getInternalSubset();
                    if (internalSubset != null) {
                        this.fOut.println(" [");
                        this.fOut.print(internalSubset);
                        this.fOut.print(']');
                    }
                    this.fOut.println(Typography.greater);
                    break;
            }
            if (type == 1) {
                this.fOut.print("</");
                this.fOut.print(node.getNodeName());
                this.fOut.print(Typography.greater);
                this.fOut.flush();
            }
        }
    }

    /* access modifiers changed from: protected */
    public Attr[] sortAttributes(NamedNodeMap attrs) {
        int len = attrs != null ? attrs.getLength() : 0;
        Attr[] array = new Attr[len];
        for (int i = 0; i < len; i++) {
            array[i] = (Attr) attrs.item(i);
        }
        for (int i2 = 0; i2 < len - 1; i2++) {
            String name = array[i2].getNodeName();
            int index = i2;
            for (int j = i2 + 1; j < len; j++) {
                String curName = array[j].getNodeName();
                if (curName.compareTo(name) < 0) {
                    name = curName;
                    index = j;
                }
            }
            if (index != i2) {
                Attr temp = array[i2];
                array[i2] = array[index];
                array[index] = temp;
            }
        }
        return array;
    }

    /* access modifiers changed from: protected */
    public void normalizeAndPrint(String s, boolean isAttValue) {
        int len = s != null ? s.length() : 0;
        for (int i = 0; i < len; i++) {
            normalizeAndPrint(s.charAt(i), isAttValue);
        }
    }

    /* access modifiers changed from: protected */
    public void normalizeAndPrint(char c, boolean isAttValue) {
        switch (c) {
            case 10:
                if (this.fCanonical) {
                    this.fOut.print("&#xA;");
                    return;
                }
                break;
            case 13:
                this.fOut.print("&#xD;");
                return;
            case '\"':
                if (isAttValue) {
                    this.fOut.print("&quot;");
                    return;
                } else {
                    this.fOut.print("\"");
                    return;
                }
            case '&':
                this.fOut.print("&amp;");
                return;
            case '<':
                this.fOut.print("&lt;");
                return;
            case '>':
                this.fOut.print("&gt;");
                return;
        }
        if ((!this.fXML11 || ((c < 1 || c > 31 || c == 9 || c == 10) && ((c < 127 || c > 159) && c != 8232))) && (!isAttValue || !(c == 9 || c == 10))) {
            this.fOut.print(c);
            return;
        }
        this.fOut.print("&#x");
        this.fOut.print(Integer.toHexString(c).toUpperCase());
        this.fOut.print(";");
    }
}
