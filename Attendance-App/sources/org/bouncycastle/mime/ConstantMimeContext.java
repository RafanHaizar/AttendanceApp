package org.bouncycastle.mime;

import java.io.IOException;
import java.io.InputStream;

public class ConstantMimeContext implements MimeContext, MimeMultipartContext {
    public InputStream applyContext(Headers headers, InputStream inputStream) throws IOException {
        return inputStream;
    }

    public MimeContext createContext(int i) throws IOException {
        return this;
    }
}
