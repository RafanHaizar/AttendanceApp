package com.itextpdf.styledxmlparser.resolver.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public interface IResourceRetriever {
    byte[] getByteArrayByUrl(URL url) throws IOException;

    InputStream getInputStreamByUrl(URL url) throws IOException;
}
