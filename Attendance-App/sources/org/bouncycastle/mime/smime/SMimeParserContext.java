package org.bouncycastle.mime.smime;

import org.bouncycastle.mime.MimeParserContext;
import org.bouncycastle.operator.DigestCalculatorProvider;

public class SMimeParserContext implements MimeParserContext {
    private final String defaultContentTransferEncoding;
    private final DigestCalculatorProvider digestCalculatorProvider;

    public SMimeParserContext(String str, DigestCalculatorProvider digestCalculatorProvider2) {
        this.defaultContentTransferEncoding = str;
        this.digestCalculatorProvider = digestCalculatorProvider2;
    }

    public String getDefaultContentTransferEncoding() {
        return this.defaultContentTransferEncoding;
    }

    public DigestCalculatorProvider getDigestCalculatorProvider() {
        return this.digestCalculatorProvider;
    }
}
