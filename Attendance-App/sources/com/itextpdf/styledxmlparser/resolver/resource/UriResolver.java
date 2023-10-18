package com.itextpdf.styledxmlparser.resolver.resource;

import com.itextpdf.p026io.util.MessageFormatUtil;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UriResolver {
    private URL baseUrl;
    private boolean isLocalBaseUri;

    public UriResolver(String baseUri) {
        if (baseUri != null) {
            resolveBaseUrlOrPath(baseUri);
            return;
        }
        throw new IllegalArgumentException("baseUri");
    }

    public String getBaseUri() {
        return this.baseUrl.toExternalForm();
    }

    public URL resolveAgainstBaseUri(String uriString) throws MalformedURLException {
        URL resolvedUrl = null;
        String uriString2 = UriEncodeUtil.encode(uriString.trim());
        if (this.isLocalBaseUri && !uriString2.startsWith("file:")) {
            try {
                Path path = Paths.get(uriString2, new String[0]);
                if (path.isAbsolute()) {
                    resolvedUrl = path.toUri().toURL();
                }
            } catch (Exception e) {
            }
        }
        if (resolvedUrl == null) {
            return new URL(this.baseUrl, uriString2);
        }
        return resolvedUrl;
    }

    public boolean isLocalBaseUri() {
        return this.isLocalBaseUri;
    }

    private void resolveBaseUrlOrPath(String base) {
        String base2 = base.trim();
        URL baseUriAsUrl = baseUriAsUrl(UriEncodeUtil.encode(base2));
        this.baseUrl = baseUriAsUrl;
        if (baseUriAsUrl == null) {
            this.baseUrl = uriAsFileUrl(base2);
        }
        if (this.baseUrl == null) {
            throw new IllegalArgumentException(MessageFormatUtil.format("Invalid base URI: {0}", base2));
        }
    }

    private URL baseUriAsUrl(String baseUriString) {
        URL baseAsUrl = null;
        try {
            URI baseUri = new URI(baseUriString);
            if (baseUri.isAbsolute()) {
                baseAsUrl = baseUri.toURL();
                if ("file".equals(baseUri.getScheme())) {
                    this.isLocalBaseUri = true;
                }
            }
        } catch (Exception e) {
        }
        return baseAsUrl;
    }

    private URL uriAsFileUrl(String baseUriString) {
        URL baseAsFileUrl = null;
        try {
            Path path = Paths.get(baseUriString, new String[0]);
            if (isPathRooted(path, baseUriString)) {
                baseAsFileUrl = new URI("file:///" + encode(path, path.toAbsolutePath().normalize().toString())).toURL();
            } else {
                baseAsFileUrl = new URL(Paths.get("", new String[0]).toUri().toURL(), encode(path, baseUriString));
            }
            this.isLocalBaseUri = true;
        } catch (Exception e) {
        }
        return baseAsFileUrl;
    }

    private String encode(Path path, String str) {
        String str2 = UriEncodeUtil.encode(str.replace("\\", "/"));
        if (Files.isDirectory(path, new LinkOption[0]) && !str2.endsWith("/")) {
            str2 = str2 + "/";
        }
        return str2.replaceFirst("/*\\\\*", "");
    }

    private boolean isPathRooted(Path path, String str) {
        return path.isAbsolute() || str.startsWith("/");
    }
}
