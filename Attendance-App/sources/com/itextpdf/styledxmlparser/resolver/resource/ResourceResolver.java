package com.itextpdf.styledxmlparser.resolver.resource;

import com.itextpdf.kernel.pdf.xobject.PdfImageXObject;
import com.itextpdf.kernel.pdf.xobject.PdfXObject;
import com.itextpdf.p026io.codec.Base64;
import com.itextpdf.p026io.image.ImageDataFactory;
import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.p026io.util.UrlUtil;
import com.itextpdf.styledxmlparser.LogMessageConstant;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceResolver {
    public static final String BASE64IDENTIFIER = "base64";
    public static final String DATA_SCHEMA_PREFIX = "data:";
    private static final Logger logger = LoggerFactory.getLogger((Class<?>) ResourceResolver.class);
    private SimpleImageCache imageCache;
    private IResourceRetriever retriever;
    private UriResolver uriResolver;

    public ResourceResolver(String baseUri) {
        this(baseUri, (IResourceRetriever) null);
    }

    public ResourceResolver(String baseUri, IResourceRetriever retriever2) {
        this.uriResolver = new UriResolver(baseUri == null ? "" : baseUri);
        this.imageCache = new SimpleImageCache();
        if (retriever2 == null) {
            this.retriever = new DefaultResourceRetriever();
        } else {
            this.retriever = retriever2;
        }
    }

    public IResourceRetriever getRetriever() {
        return this.retriever;
    }

    public ResourceResolver setRetriever(IResourceRetriever retriever2) {
        this.retriever = retriever2;
        return this;
    }

    @Deprecated
    public PdfImageXObject retrieveImage(String src) {
        PdfXObject image = retrieveImageExtended(src);
        if (image instanceof PdfImageXObject) {
            return (PdfImageXObject) image;
        }
        return null;
    }

    public PdfXObject retrieveImageExtended(String src) {
        PdfXObject imageXObject;
        if (src != null) {
            if (isContains64Mark(src) && (imageXObject = tryResolveBase64ImageSource(src)) != null) {
                return imageXObject;
            }
            PdfXObject imageXObject2 = tryResolveUrlImageSource(src);
            if (imageXObject2 != null) {
                return imageXObject2;
            }
        }
        logger.error(MessageFormatUtil.format(LogMessageConstant.UNABLE_TO_RETRIEVE_IMAGE_WITH_GIVEN_BASE_URI, this.uriResolver.getBaseUri(), src));
        return null;
    }

    @Deprecated
    public InputStream retrieveStyleSheet(String uri) throws IOException {
        return this.retriever.getInputStreamByUrl(this.uriResolver.resolveAgainstBaseUri(uri));
    }

    @Deprecated
    public byte[] retrieveStream(String src) {
        try {
            return retrieveBytesFromResource(src);
        } catch (Exception e) {
            logger.error(MessageFormatUtil.format("Unable to retrieve stream with given base URI ({0}) and source path ({1})", this.uriResolver.getBaseUri(), src), (Throwable) e);
            return null;
        }
    }

    public byte[] retrieveBytesFromResource(String src) {
        byte[] bytes = retrieveBytesFromBase64Src(src);
        if (bytes != null) {
            return bytes;
        }
        try {
            return this.retriever.getByteArrayByUrl(this.uriResolver.resolveAgainstBaseUri(src));
        } catch (Exception e) {
            logger.error(MessageFormatUtil.format("Unable to retrieve stream with given base URI ({0}) and source path ({1})", this.uriResolver.getBaseUri(), src), (Throwable) e);
            return null;
        }
    }

    public InputStream retrieveResourceAsInputStream(String src) {
        byte[] bytes = retrieveBytesFromBase64Src(src);
        if (bytes != null) {
            return new ByteArrayInputStream(bytes);
        }
        try {
            return this.retriever.getInputStreamByUrl(this.uriResolver.resolveAgainstBaseUri(src));
        } catch (Exception e) {
            logger.error(MessageFormatUtil.format("Unable to retrieve stream with given base URI ({0}) and source path ({1})", this.uriResolver.getBaseUri(), src), (Throwable) e);
            return null;
        }
    }

    public boolean isDataSrc(String src) {
        return src.startsWith(DATA_SCHEMA_PREFIX) && src.contains(",");
    }

    public URL resolveAgainstBaseUri(String uri) throws MalformedURLException {
        return this.uriResolver.resolveAgainstBaseUri(uri);
    }

    public void resetCache() {
        this.imageCache.reset();
    }

    @Deprecated
    public boolean isImageTypeSupportedByImageDataFactory(String src) {
        try {
            return ImageDataFactory.isSupportedType(this.retriever.getByteArrayByUrl(UrlUtil.getFinalURL(this.uriResolver.resolveAgainstBaseUri(src))));
        } catch (Exception e) {
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public PdfXObject tryResolveBase64ImageSource(String src) {
        try {
            String fixedSrc = src.replaceAll("\\s", "");
            String fixedSrc2 = fixedSrc.substring(fixedSrc.indexOf(BASE64IDENTIFIER) + 7);
            PdfXObject imageXObject = this.imageCache.getImage(fixedSrc2);
            if (imageXObject != null) {
                return imageXObject;
            }
            PdfXObject imageXObject2 = new PdfImageXObject(ImageDataFactory.create(Base64.decode(fixedSrc2)));
            this.imageCache.putImage(fixedSrc2, imageXObject2);
            return imageXObject2;
        } catch (Exception e) {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public PdfXObject tryResolveUrlImageSource(String uri) {
        try {
            URL url = UrlUtil.getFinalURL(this.uriResolver.resolveAgainstBaseUri(uri));
            String imageResolvedSrc = url.toExternalForm();
            PdfXObject imageXObject = this.imageCache.getImage(imageResolvedSrc);
            if (imageXObject == null && (imageXObject = createImageByUrl(url)) != null) {
                this.imageCache.putImage(imageResolvedSrc, imageXObject);
            }
            return imageXObject;
        } catch (Exception e) {
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public PdfXObject createImageByUrl(URL url) throws Exception {
        byte[] bytes = this.retriever.getByteArrayByUrl(url);
        if (bytes == null) {
            return null;
        }
        return new PdfImageXObject(ImageDataFactory.create(bytes));
    }

    private byte[] retrieveBytesFromBase64Src(String src) {
        if (!isContains64Mark(src)) {
            return null;
        }
        try {
            String fixedSrc = src.replaceAll("\\s", "");
            return Base64.decode(fixedSrc.substring(fixedSrc.indexOf(BASE64IDENTIFIER) + 7));
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isContains64Mark(String src) {
        return src.contains(BASE64IDENTIFIER);
    }
}
