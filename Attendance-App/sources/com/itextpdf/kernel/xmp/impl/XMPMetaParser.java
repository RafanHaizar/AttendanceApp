package com.itextpdf.kernel.xmp.impl;

import com.itextpdf.kernel.xmp.XMPConst;
import com.itextpdf.kernel.xmp.XMPError;
import com.itextpdf.kernel.xmp.XMPException;
import com.itextpdf.kernel.xmp.XMPMeta;
import com.itextpdf.kernel.xmp.options.ParseOptions;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMPMetaParser {
    private static final Object XMP_RDF = new Object();
    private static DocumentBuilderFactory factory = createDocumentBuilderFactory();

    private XMPMetaParser() {
    }

    public static XMPMeta parse(Object input, ParseOptions options) throws XMPException {
        ParameterAsserts.assertNotNull(input);
        ParseOptions options2 = options != null ? options : new ParseOptions();
        Object[] result = findRootNode(parseXml(input, options2), options2.getRequireXMPMeta(), new Object[3]);
        if (result == null || result[1] != XMP_RDF) {
            return new XMPMetaImpl();
        }
        XMPMetaImpl xmp = ParseRDF.parse((Node) result[0]);
        xmp.setPacketHeader((String) result[2]);
        if (!options2.getOmitNormalization()) {
            return XMPNormalizer.process(xmp, options2);
        }
        return xmp;
    }

    private static Document parseXml(Object input, ParseOptions options) throws XMPException {
        if (input instanceof InputStream) {
            return parseXmlFromInputStream((InputStream) input, options);
        }
        if (input instanceof byte[]) {
            return parseXmlFromBytebuffer(new ByteBuffer((byte[]) input), options);
        }
        return parseXmlFromString((String) input, options);
    }

    private static Document parseXmlFromInputStream(InputStream stream, ParseOptions options) throws XMPException {
        if (!options.getAcceptLatin1() && !options.getFixControlChars()) {
            return parseInputSource(new InputSource(stream));
        }
        try {
            return parseXmlFromBytebuffer(new ByteBuffer(stream), options);
        } catch (IOException e) {
            throw new XMPException("Error reading the XML-file", XMPError.BADSTREAM, e);
        }
    }

    private static Document parseXmlFromBytebuffer(ByteBuffer buffer, ParseOptions options) throws XMPException {
        try {
            return parseInputSource(new InputSource(buffer.getByteStream()));
        } catch (XMPException e) {
            if (e.getErrorCode() == 201 || e.getErrorCode() == 204) {
                if (options.getAcceptLatin1()) {
                    buffer = Latin1Converter.convert(buffer);
                }
                if (!options.getFixControlChars()) {
                    return parseInputSource(new InputSource(buffer.getByteStream()));
                }
                try {
                    return parseInputSource(new InputSource(new FixASCIIControlsReader(new InputStreamReader(buffer.getByteStream(), buffer.getEncoding()))));
                } catch (UnsupportedEncodingException e2) {
                    throw new XMPException("Unsupported Encoding", 9, e);
                }
            } else {
                throw e;
            }
        }
    }

    private static Document parseXmlFromString(String input, ParseOptions options) throws XMPException {
        try {
            return parseInputSource(new InputSource(new StringReader(input)));
        } catch (XMPException e) {
            if (e.getErrorCode() == 201 && options.getFixControlChars()) {
                return parseInputSource(new InputSource(new FixASCIIControlsReader(new StringReader(input))));
            }
            throw e;
        }
    }

    private static Document parseInputSource(InputSource source) throws XMPException {
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler((ErrorHandler) null);
            builder.setEntityResolver(new SafeEmptyEntityResolver());
            return builder.parse(source);
        } catch (SAXException e) {
            throw new XMPException("XML parsing failure", XMPError.BADXML, e);
        } catch (ParserConfigurationException e2) {
            throw new XMPException("XML Parser not correctly configured", 0, e2);
        } catch (IOException e3) {
            throw new XMPException("Error reading the XML-file", XMPError.BADSTREAM, e3);
        }
    }

    private static Object[] findRootNode(Node root, boolean xmpmetaRequired, Object[] result) {
        NodeList children = root.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node root2 = children.item(i);
            if (7 != root2.getNodeType() || !XMPConst.XMP_PI.equals(((ProcessingInstruction) root2).getTarget())) {
                if (!(3 == root2.getNodeType() || 7 == root2.getNodeType())) {
                    String rootNS = root2.getNamespaceURI();
                    String rootLocal = root2.getLocalName();
                    if ((XMPConst.TAG_XMPMETA.equals(rootLocal) || XMPConst.TAG_XAPMETA.equals(rootLocal)) && XMPConst.NS_X.equals(rootNS)) {
                        return findRootNode(root2, false, result);
                    }
                    if (xmpmetaRequired || !"RDF".equals(rootLocal) || !XMPConst.NS_RDF.equals(rootNS)) {
                        Object[] newResult = findRootNode(root2, xmpmetaRequired, result);
                        if (newResult != null) {
                            return newResult;
                        }
                    } else {
                        if (result != null) {
                            result[0] = root2;
                            result[1] = XMP_RDF;
                        }
                        return result;
                    }
                }
            } else if (result != null) {
                result[2] = ((ProcessingInstruction) root2).getData();
            }
        }
        return null;
    }

    private static DocumentBuilderFactory createDocumentBuilderFactory() {
        DocumentBuilderFactory factory2 = DocumentBuilderFactory.newInstance();
        factory2.setNamespaceAware(true);
        factory2.setIgnoringComments(true);
        try {
            factory2.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
        } catch (Exception e) {
        }
        return factory2;
    }

    private static class SafeEmptyEntityResolver implements EntityResolver {
        private SafeEmptyEntityResolver() {
        }

        public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
            return new InputSource(new StringReader(""));
        }
    }
}
