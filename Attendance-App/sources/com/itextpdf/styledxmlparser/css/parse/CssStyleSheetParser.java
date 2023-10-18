package com.itextpdf.styledxmlparser.css.parse;

import com.itextpdf.styledxmlparser.PortUtil;
import com.itextpdf.styledxmlparser.css.CssStyleSheet;
import com.itextpdf.styledxmlparser.css.parse.syntax.CssParserStateController;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public final class CssStyleSheetParser {
    private CssStyleSheetParser() {
    }

    public static CssStyleSheet parse(InputStream stream, String baseUrl) throws IOException {
        CssParserStateController controller = new CssParserStateController(baseUrl);
        Reader br = PortUtil.wrapInBufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        char[] buffer = new char[8192];
        while (true) {
            int read = br.read(buffer, 0, buffer.length);
            int length = read;
            if (read <= 0) {
                return controller.getParsingResult();
            }
            for (int i = 0; i < length; i++) {
                controller.process(buffer[i]);
            }
        }
    }

    public static CssStyleSheet parse(InputStream stream) throws IOException {
        return parse(stream, (String) null);
    }

    public static CssStyleSheet parse(String data, String baseUrl) {
        try {
            return parse((InputStream) new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)), baseUrl);
        } catch (IOException e) {
            return null;
        }
    }

    public static CssStyleSheet parse(String data) {
        return parse(data, (String) null);
    }
}
