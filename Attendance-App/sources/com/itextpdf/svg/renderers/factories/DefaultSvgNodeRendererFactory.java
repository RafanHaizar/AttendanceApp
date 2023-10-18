package com.itextpdf.svg.renderers.factories;

import com.itextpdf.p026io.util.MessageFormatUtil;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.svg.exceptions.SvgLogMessageConstant;
import com.itextpdf.svg.exceptions.SvgProcessingException;
import com.itextpdf.svg.renderers.INoDrawSvgNodeRenderer;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import com.itextpdf.svg.renderers.impl.DefsSvgNodeRenderer;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import org.slf4j.LoggerFactory;

public class DefaultSvgNodeRendererFactory implements ISvgNodeRendererFactory {
    private Collection<String> ignoredTags;
    private Map<String, Class<? extends ISvgNodeRenderer>> rendererMap;

    public DefaultSvgNodeRendererFactory() {
        this(new DefaultSvgNodeRendererMapper());
    }

    @Deprecated
    public DefaultSvgNodeRendererFactory(ISvgNodeRendererMapper mapper) {
        this.rendererMap = new HashMap();
        this.ignoredTags = new HashSet();
        if (mapper != null) {
            this.rendererMap.putAll(mapper.getMapping());
            this.ignoredTags.addAll(mapper.getIgnoredTags());
            return;
        }
        ISvgNodeRendererMapper defaultMapper = new DefaultSvgNodeRendererMapper();
        this.rendererMap.putAll(defaultMapper.getMapping());
        this.ignoredTags.addAll(defaultMapper.getIgnoredTags());
    }

    public ISvgNodeRenderer createSvgNodeRendererForTag(IElementNode tag, ISvgNodeRenderer parent) {
        if (tag != null) {
            try {
                if (this.rendererMap.get(tag.name()) == null) {
                    LoggerFactory.getLogger(getClass()).warn(MessageFormatUtil.format(SvgLogMessageConstant.UNMAPPEDTAG, tag.name()));
                    return null;
                }
                ISvgNodeRenderer result = (ISvgNodeRenderer) this.rendererMap.get(tag.name()).newInstance();
                if (parent != null && !(result instanceof INoDrawSvgNodeRenderer) && !(parent instanceof DefsSvgNodeRenderer)) {
                    result.setParent(parent);
                }
                return result;
            } catch (ReflectiveOperationException ex) {
                throw new SvgProcessingException(SvgLogMessageConstant.COULDNOTINSTANTIATE, ex).setMessageParams(tag.name());
            }
        } else {
            throw new SvgProcessingException(SvgLogMessageConstant.TAGPARAMETERNULL);
        }
    }

    public boolean isTagIgnored(IElementNode tag) {
        return this.ignoredTags.contains(tag.name());
    }
}
