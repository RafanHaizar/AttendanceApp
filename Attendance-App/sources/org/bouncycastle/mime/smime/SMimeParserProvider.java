package org.bouncycastle.mime.smime;

import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.mime.BasicMimeParser;
import org.bouncycastle.mime.Headers;
import org.bouncycastle.mime.MimeParser;
import org.bouncycastle.mime.MimeParserContext;
import org.bouncycastle.mime.MimeParserProvider;
import org.bouncycastle.operator.DigestCalculatorProvider;

public class SMimeParserProvider implements MimeParserProvider {
    private final String defaultContentTransferEncoding;
    private final DigestCalculatorProvider digestCalculatorProvider;

    public SMimeParserProvider(String str, DigestCalculatorProvider digestCalculatorProvider2) {
        this.defaultContentTransferEncoding = str;
        this.digestCalculatorProvider = digestCalculatorProvider2;
    }

    public MimeParser createParser(InputStream inputStream) throws IOException {
        return new BasicMimeParser((MimeParserContext) new SMimeParserContext(this.defaultContentTransferEncoding, this.digestCalculatorProvider), inputStream);
    }

    public MimeParser createParser(Headers headers, InputStream inputStream) throws IOException {
        return new BasicMimeParser(new SMimeParserContext(this.defaultContentTransferEncoding, this.digestCalculatorProvider), headers, inputStream);
    }
}
