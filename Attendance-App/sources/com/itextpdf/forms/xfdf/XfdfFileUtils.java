package com.itextpdf.forms.xfdf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

final class XfdfFileUtils {
    private XfdfFileUtils() {
    }

    static Document createNewXfdfDocument() throws ParserConfigurationException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        documentBuilder.setEntityResolver(new SafeEmptyEntityResolver());
        return documentBuilder.newDocument();
    }

    static Document createXfdfDocumentFromStream(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        documentBuilder.setEntityResolver(new SafeEmptyEntityResolver());
        return documentBuilder.parse(inputStream);
    }

    static void saveXfdfDocumentToFile(Document document, OutputStream outputStream) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
        transformerFactory.setAttribute("http://javax.xml.XMLConstants/property/accessExternalDTD", "");
        transformerFactory.setAttribute("http://javax.xml.XMLConstants/property/accessExternalStylesheet", "");
        transformerFactory.newTransformer().transform(new DOMSource(document), new StreamResult(outputStream));
    }

    private static class SafeEmptyEntityResolver implements EntityResolver {
        private SafeEmptyEntityResolver() {
        }

        public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
            return new InputSource(new StringReader(""));
        }
    }
}
