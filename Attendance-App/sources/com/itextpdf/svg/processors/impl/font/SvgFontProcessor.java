package com.itextpdf.svg.processors.impl.font;

import com.itextpdf.layout.font.FontInfo;
import com.itextpdf.layout.font.Range;
import com.itextpdf.p026io.font.FontProgramFactory;
import com.itextpdf.p026io.font.PdfEncodings;
import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.css.CssFontFaceRule;
import com.itextpdf.styledxmlparser.css.ICssResolver;
import com.itextpdf.styledxmlparser.css.font.CssFontFace;
import com.itextpdf.svg.css.impl.SvgStyleResolver;
import com.itextpdf.svg.processors.impl.SvgProcessorContext;
import java.util.Collection;
import java.util.Iterator;
import org.slf4j.LoggerFactory;

public class SvgFontProcessor {
    private SvgProcessorContext context;

    public SvgFontProcessor(SvgProcessorContext context2) {
        this.context = context2;
    }

    public void addFontFaceFonts(ICssResolver cssResolver) {
        if (cssResolver instanceof SvgStyleResolver) {
            for (CssFontFaceRule fontFace : ((SvgStyleResolver) cssResolver).getFonts()) {
                boolean findSupportedSrc = false;
                CssFontFace ff = CssFontFace.create(fontFace.getProperties());
                if (ff != null) {
                    Iterator<CssFontFace.CssFontFaceSrc> it = ff.getSources().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        if (createFont(ff.getFontFamily(), it.next(), fontFace.resolveUnicodeRange())) {
                            findSupportedSrc = true;
                            break;
                        }
                    }
                }
                if (!findSupportedSrc) {
                    LoggerFactory.getLogger((Class<?>) SvgFontProcessor.class).error(MessageFormatUtil.format("Unable to retrieve font:\n {0}", fontFace));
                }
            }
        }
    }

    private boolean createFont(String fontFamily, CssFontFace.CssFontFaceSrc src, Range unicodeRange) {
        if (!CssFontFace.isSupportedFontFormat(src.getFormat())) {
            return false;
        }
        if (src.isLocal()) {
            Collection<FontInfo> fonts = this.context.getFontProvider().getFontSet().get(src.getSrc());
            if (fonts.size() <= 0) {
                return false;
            }
            for (FontInfo fi : fonts) {
                this.context.addTemporaryFont(fi, fontFamily);
            }
            return true;
        }
        try {
            byte[] bytes = this.context.getResourceResolver().retrieveBytesFromResource(src.getSrc());
            if (bytes != null) {
                this.context.addTemporaryFont(FontProgramFactory.createFont(bytes, false), PdfEncodings.IDENTITY_H, fontFamily, unicodeRange);
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }
}
