package com.itextpdf.styledxmlparser.jsoup.helper;

import com.itextpdf.styledxmlparser.jsoup.PortUtil;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.jsoup.nodes.XmlDeclaration;
import com.itextpdf.styledxmlparser.jsoup.parser.Parser;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DataUtil {
    private static final int UNICODE_BOM = 65279;
    static final int boundaryLength = 32;
    private static final int bufferSize = 131072;
    private static final Pattern charsetPattern = Pattern.compile("(?i)\\bcharset=\\s*(?:\"|')?([^\\s,;\"']*)");
    static final String defaultCharset = "UTF-8";
    private static final char[] mimeBoundaryChars = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    private DataUtil() {
    }

    public static Document load(File in, String charsetName, String baseUri) throws IOException {
        return parseByteData(readFileToByteBuffer(in), charsetName, baseUri, Parser.htmlParser());
    }

    public static Document load(InputStream in, String charsetName, String baseUri) throws IOException {
        return parseByteData(readToByteBuffer(in), charsetName, baseUri, Parser.htmlParser());
    }

    public static Document load(InputStream in, String charsetName, String baseUri, Parser parser) throws IOException {
        return parseByteData(readToByteBuffer(in), charsetName, baseUri, parser);
    }

    static void crossStreams(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[131072];
        while (true) {
            int read = in.read(buffer);
            int len = read;
            if (read != -1) {
                out.write(buffer, 0, len);
            } else {
                return;
            }
        }
    }

    static Document parseByteData(ByteBuffer byteData, String charsetName, String baseUri, Parser parser) {
        String docData;
        Document doc = null;
        String charsetName2 = detectCharsetFromBom(byteData, charsetName);
        if (charsetName2 == null) {
            docData = Charset.forName("UTF-8").decode(byteData).toString();
            doc = parser.parseInput(docData, baseUri);
            Element meta = doc.select("meta[http-equiv=content-type], meta[charset]").first();
            String foundCharset = null;
            if (meta != null) {
                if (meta.hasAttr("http-equiv")) {
                    foundCharset = getCharsetFromContentType(meta.attr("content"));
                }
                if (foundCharset == null && meta.hasAttr("charset")) {
                    foundCharset = meta.attr("charset");
                }
            }
            if (foundCharset == null && (doc.childNode(0) instanceof XmlDeclaration)) {
                XmlDeclaration prolog = (XmlDeclaration) doc.childNode(0);
                if (prolog.name().equals("xml")) {
                    foundCharset = prolog.attr("encoding");
                }
            }
            String foundCharset2 = validateCharset(foundCharset);
            if (foundCharset2 != null && !foundCharset2.equals("UTF-8")) {
                String foundCharset3 = foundCharset2.trim().replaceAll("[\"']", "");
                charsetName2 = foundCharset3;
                byteData.rewind();
                docData = Charset.forName(foundCharset3).decode(byteData).toString();
                doc = null;
            }
        } else {
            Validate.notEmpty(charsetName2, "Must set charset arg to character set of file to parse. Set to null to attempt to detect from HTML");
            docData = Charset.forName(charsetName2).decode(byteData).toString();
        }
        if (doc != null) {
            return doc;
        }
        Document doc2 = parser.parseInput(docData, baseUri);
        doc2.outputSettings().charset(charsetName2);
        return doc2;
    }

    static ByteBuffer readToByteBuffer(InputStream inStream, int maxSize) throws IOException {
        boolean capped = true;
        Validate.isTrue(maxSize >= 0, "maxSize must be 0 (unlimited) or larger");
        if (maxSize <= 0) {
            capped = false;
        }
        byte[] buffer = new byte[131072];
        ByteArrayOutputStream outStream = new ByteArrayOutputStream(131072);
        int remaining = maxSize;
        while (true) {
            int read = inStream.read(buffer);
            if (read == -1) {
                break;
            }
            if (capped) {
                if (read > remaining) {
                    outStream.write(buffer, 0, remaining);
                    break;
                }
                remaining -= read;
            }
            outStream.write(buffer, 0, read);
        }
        return ByteBuffer.wrap(outStream.toByteArray());
    }

    static ByteBuffer readToByteBuffer(InputStream inStream) throws IOException {
        return readToByteBuffer(inStream, 0);
    }

    static ByteBuffer readFileToByteBuffer(File file) throws IOException {
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = PortUtil.getReadOnlyRandomAccesFile(file);
            byte[] bytes = new byte[((int) randomAccessFile.length())];
            randomAccessFile.readFully(bytes);
            return ByteBuffer.wrap(bytes);
        } finally {
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
        }
    }

    static ByteBuffer emptyByteBuffer() {
        return ByteBuffer.allocate(0);
    }

    static String getCharsetFromContentType(String contentType) {
        if (contentType == null) {
            return null;
        }
        Matcher m = charsetPattern.matcher(contentType);
        if (PortUtil.isSuccessful(m)) {
            return validateCharset(m.group(1).trim().replace("charset=", ""));
        }
        return null;
    }

    private static String validateCharset(String cs) {
        if (cs == null || cs.length() == 0) {
            return null;
        }
        String cs2 = cs.trim().replaceAll("[\"']", "");
        if (PortUtil.charsetIsSupported(cs2)) {
            return cs2;
        }
        StringBuilder upperCase = new StringBuilder();
        for (int i = 0; i < cs2.length(); i++) {
            upperCase.append(Character.toUpperCase(cs2.charAt(i)));
        }
        String cs3 = upperCase.toString();
        if (PortUtil.charsetIsSupported(cs3)) {
            return cs3;
        }
        return null;
    }

    static String mimeBoundary() {
        StringBuilder mime = new StringBuilder(32);
        Random rand = new Random();
        for (int i = 0; i < 32; i++) {
            char[] cArr = mimeBoundaryChars;
            mime.append(cArr[rand.nextInt(cArr.length)]);
        }
        return mime.toString();
    }

    private static String detectCharsetFromBom(ByteBuffer byteData, String charsetName) {
        byteData.mark();
        byte[] bom = new byte[4];
        if (byteData.remaining() >= bom.length) {
            byteData.get(bom);
            byteData.rewind();
        }
        if ((bom[0] == 0 && bom[1] == 0 && bom[2] == -2 && bom[3] == -1) || (bom[0] == -1 && bom[1] == -2 && bom[2] == 0 && bom[3] == 0)) {
            return "UTF-32";
        }
        if ((bom[0] == -2 && bom[1] == -1) || (bom[0] == -1 && bom[1] == -2)) {
            return "UTF-16";
        }
        if (bom[0] != -17 || bom[1] != -69 || bom[2] != -65) {
            return charsetName;
        }
        byteData.position(3);
        return "UTF-8";
    }
}
