package com.itextpdf.svg.processors.impl;

import com.itextpdf.styledxmlparser.css.ICssResolver;
import com.itextpdf.styledxmlparser.node.IElementNode;
import com.itextpdf.styledxmlparser.node.INode;
import com.itextpdf.styledxmlparser.node.ITextNode;
import com.itextpdf.svg.SvgConstants;
import com.itextpdf.svg.css.SvgCssContext;
import com.itextpdf.svg.css.impl.SvgStyleResolver;
import com.itextpdf.svg.exceptions.SvgLogMessageConstant;
import com.itextpdf.svg.exceptions.SvgProcessingException;
import com.itextpdf.svg.processors.ISvgConverterProperties;
import com.itextpdf.svg.processors.ISvgProcessor;
import com.itextpdf.svg.processors.ISvgProcessorResult;
import com.itextpdf.svg.processors.impl.font.SvgFontProcessor;
import com.itextpdf.svg.renderers.IBranchSvgNodeRenderer;
import com.itextpdf.svg.renderers.INoDrawSvgNodeRenderer;
import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import com.itextpdf.svg.renderers.factories.DefaultSvgNodeRendererFactory;
import com.itextpdf.svg.renderers.factories.ISvgNodeRendererFactory;
import com.itextpdf.svg.renderers.impl.DefsSvgNodeRenderer;
import com.itextpdf.svg.renderers.impl.ISvgTextNodeRenderer;
import com.itextpdf.svg.renderers.impl.LinearGradientSvgNodeRenderer;
import com.itextpdf.svg.renderers.impl.StopSvgNodeRenderer;
import com.itextpdf.svg.renderers.impl.TextLeafSvgNodeRenderer;
import com.itextpdf.svg.renderers.impl.TextSvgBranchRenderer;
import com.itextpdf.svg.utils.SvgTextUtil;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class DefaultSvgProcessor implements ISvgProcessor {
    private SvgProcessorContext context;
    private SvgCssContext cssContext;
    private ICssResolver cssResolver;
    private Map<String, ISvgNodeRenderer> namedObjects;
    private ProcessorState processorState;
    private ISvgNodeRendererFactory rendererFactory;

    public ISvgProcessorResult process(INode root, ISvgConverterProperties converterProps) throws SvgProcessingException {
        if (root != null) {
            if (converterProps == null) {
                converterProps = new SvgConverterProperties();
            }
            performSetup(root, converterProps);
            IElementNode svgRoot = findFirstElement(root, SvgConstants.Tags.SVG);
            if (svgRoot != null) {
                executeDepthFirstTraversal(svgRoot);
                return new SvgProcessorResult(this.namedObjects, createResultAndClean(), this.context);
            }
            throw new SvgProcessingException(SvgLogMessageConstant.NOROOT);
        }
        throw new SvgProcessingException(SvgLogMessageConstant.INODEROOTISNULL);
    }

    @Deprecated
    public ISvgProcessorResult process(INode root) throws SvgProcessingException {
        return process(root, (ISvgConverterProperties) null);
    }

    /* access modifiers changed from: package-private */
    public void performSetup(INode root, ISvgConverterProperties converterProps) {
        this.processorState = new ProcessorState();
        if (converterProps.getRendererFactory() != null) {
            this.rendererFactory = converterProps.getRendererFactory();
        } else {
            this.rendererFactory = new DefaultSvgNodeRendererFactory();
        }
        SvgProcessorContext svgProcessorContext = new SvgProcessorContext(converterProps);
        this.context = svgProcessorContext;
        this.cssResolver = new SvgStyleResolver(root, svgProcessorContext);
        new SvgFontProcessor(this.context).addFontFaceFonts(this.cssResolver);
        this.namedObjects = new HashMap();
        this.cssContext = new SvgCssContext();
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000f, code lost:
        r0 = (com.itextpdf.styledxmlparser.node.IElementNode) r6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void executeDepthFirstTraversal(com.itextpdf.styledxmlparser.node.INode r6) {
        /*
            r5 = this;
            boolean r0 = r6 instanceof com.itextpdf.styledxmlparser.node.IElementNode
            if (r0 == 0) goto L_0x0046
            com.itextpdf.svg.renderers.factories.ISvgNodeRendererFactory r0 = r5.rendererFactory
            r1 = r6
            com.itextpdf.styledxmlparser.node.IElementNode r1 = (com.itextpdf.styledxmlparser.node.IElementNode) r1
            boolean r0 = r0.isTagIgnored(r1)
            if (r0 != 0) goto L_0x0046
            r0 = r6
            com.itextpdf.styledxmlparser.node.IElementNode r0 = (com.itextpdf.styledxmlparser.node.IElementNode) r0
            com.itextpdf.svg.renderers.factories.ISvgNodeRendererFactory r1 = r5.rendererFactory
            r2 = 0
            com.itextpdf.svg.renderers.ISvgNodeRenderer r1 = r1.createSvgNodeRendererForTag(r0, r2)
            if (r1 == 0) goto L_0x0046
            com.itextpdf.styledxmlparser.css.ICssResolver r2 = r5.cssResolver
            com.itextpdf.svg.css.SvgCssContext r3 = r5.cssContext
            java.util.Map r2 = r2.resolveStyles(r6, r3)
            r0.setStyles(r2)
            r1.setAttributesAndStyles(r2)
            com.itextpdf.svg.processors.impl.ProcessorState r3 = r5.processorState
            r3.push(r1)
            java.util.List r3 = r6.childNodes()
            java.util.Iterator r3 = r3.iterator()
        L_0x0036:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0046
            java.lang.Object r4 = r3.next()
            com.itextpdf.styledxmlparser.node.INode r4 = (com.itextpdf.styledxmlparser.node.INode) r4
            r5.visit(r4)
            goto L_0x0036
        L_0x0046:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.svg.processors.impl.DefaultSvgProcessor.executeDepthFirstTraversal(com.itextpdf.styledxmlparser.node.INode):void");
    }

    private ISvgNodeRenderer createResultAndClean() {
        return this.processorState.pop();
    }

    private void visit(INode node) {
        Map<String, String> styles;
        if (node instanceof IElementNode) {
            IElementNode element = (IElementNode) node;
            if (!this.rendererFactory.isTagIgnored(element)) {
                ISvgNodeRenderer parentRenderer = this.processorState.top();
                ISvgNodeRenderer renderer = this.rendererFactory.createSvgNodeRendererForTag(element, parentRenderer);
                if (renderer != null) {
                    if (!(this.cssResolver instanceof SvgStyleResolver) || !onlyNativeStylesShouldBeResolved(element)) {
                        styles = this.cssResolver.resolveStyles(node, this.cssContext);
                    } else {
                        styles = ((SvgStyleResolver) this.cssResolver).resolveNativeStyles(node, this.cssContext);
                    }
                    element.setStyles(styles);
                    renderer.setAttributesAndStyles(styles);
                    String attribute = renderer.getAttribute("id");
                    if (attribute != null) {
                        this.namedObjects.put(attribute, renderer);
                    }
                    if (renderer instanceof StopSvgNodeRenderer) {
                        if (parentRenderer instanceof LinearGradientSvgNodeRenderer) {
                            ((LinearGradientSvgNodeRenderer) parentRenderer).addChild(renderer);
                        }
                    } else if (!(renderer instanceof INoDrawSvgNodeRenderer) && !(parentRenderer instanceof DefsSvgNodeRenderer)) {
                        if (parentRenderer instanceof IBranchSvgNodeRenderer) {
                            ((IBranchSvgNodeRenderer) parentRenderer).addChild(renderer);
                        } else if ((parentRenderer instanceof TextSvgBranchRenderer) && (renderer instanceof ISvgTextNodeRenderer)) {
                            ((TextSvgBranchRenderer) parentRenderer).addChild((ISvgTextNodeRenderer) renderer);
                        }
                    }
                    this.processorState.push(renderer);
                }
                for (INode childNode : element.childNodes()) {
                    visit(childNode);
                }
                if (renderer != null) {
                    this.processorState.pop();
                }
            }
        } else if (processAsText(node)) {
            processText((ITextNode) node);
        }
    }

    private static boolean onlyNativeStylesShouldBeResolved(IElementNode element) {
        return !SvgConstants.Tags.LINEAR_GRADIENT.equals(element.name()) && !SvgConstants.Tags.MARKER.equals(element.name()) && isElementNested(element, SvgConstants.Tags.DEFS) && !isElementNested(element, SvgConstants.Tags.MARKER);
    }

    private static boolean isElementNested(IElementNode element, String parentElementNameForSearch) {
        if (!(element.parentNode() instanceof IElementNode)) {
            return false;
        }
        IElementNode parentElement = (IElementNode) element.parentNode();
        if (parentElement.name().equals(parentElementNameForSearch)) {
            return true;
        }
        if (element.parentNode() != null) {
            return isElementNested(parentElement, parentElementNameForSearch);
        }
        return false;
    }

    private boolean processAsText(INode node) {
        return node instanceof ITextNode;
    }

    private void processText(ITextNode textNode) {
        ISvgNodeRenderer parentRenderer = this.processorState.top();
        if (parentRenderer instanceof TextSvgBranchRenderer) {
            String wholeText = textNode.wholeText();
            if (!"".equals(wholeText) && !SvgTextUtil.isOnlyWhiteSpace(wholeText)) {
                TextLeafSvgNodeRenderer textLeaf = new TextLeafSvgNodeRenderer();
                textLeaf.setParent(parentRenderer);
                textLeaf.setAttribute(SvgConstants.Attributes.TEXT_CONTENT, wholeText);
                ((TextSvgBranchRenderer) parentRenderer).addChild(textLeaf);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public IElementNode findFirstElement(INode node, String tagName) {
        LinkedList<INode> q = new LinkedList<>();
        q.add(node);
        while (!q.isEmpty()) {
            INode currentNode = q.getFirst();
            q.removeFirst();
            if (currentNode == null) {
                return null;
            }
            if ((currentNode instanceof IElementNode) && ((IElementNode) currentNode).name() != null && ((IElementNode) currentNode).name().equals(tagName)) {
                return (IElementNode) currentNode;
            }
            for (INode child : currentNode.childNodes()) {
                if (child instanceof IElementNode) {
                    q.add(child);
                }
            }
        }
        return null;
    }
}
