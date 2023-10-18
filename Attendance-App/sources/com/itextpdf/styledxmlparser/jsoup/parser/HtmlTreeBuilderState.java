package com.itextpdf.styledxmlparser.jsoup.parser;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.itextpdf.forms.xfdf.XfdfConstants;
import com.itextpdf.kernel.xmp.PdfConst;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.jsoup.helper.StringUtil;
import com.itextpdf.styledxmlparser.jsoup.nodes.Attribute;
import com.itextpdf.styledxmlparser.jsoup.nodes.Attributes;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import com.itextpdf.styledxmlparser.jsoup.nodes.DocumentType;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.jsoup.nodes.FormElement;
import com.itextpdf.styledxmlparser.jsoup.nodes.Node;
import com.itextpdf.styledxmlparser.jsoup.parser.Token;
import com.itextpdf.svg.SvgConstants;
import java.util.ArrayList;
import java.util.Iterator;
import org.bouncycastle.i18n.ErrorBundle;
import org.bouncycastle.jcajce.util.AnnotatedPrivateKey;

abstract class HtmlTreeBuilderState {
    static HtmlTreeBuilderState AfterAfterBody = new HtmlTreeBuilderState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "AfterAfterBody";
        }

        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (t.isComment()) {
                tb.insert(t.asComment());
                return true;
            } else if (t.isDoctype() || HtmlTreeBuilderState.isWhitespace(t) || (t.isStartTag() && t.asStartTag().name().equals("html"))) {
                return tb.process(t, InBody);
            } else {
                if (t.isEOF()) {
                    return true;
                }
                tb.error(this);
                tb.transition(InBody);
                return tb.process(t);
            }
        }
    };
    static HtmlTreeBuilderState AfterAfterFrameset = new HtmlTreeBuilderState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "AfterAfterFrameset";
        }

        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (t.isComment()) {
                tb.insert(t.asComment());
                return true;
            } else if (t.isDoctype() || HtmlTreeBuilderState.isWhitespace(t) || (t.isStartTag() && t.asStartTag().name().equals("html"))) {
                return tb.process(t, InBody);
            } else {
                if (t.isEOF()) {
                    return true;
                }
                if (t.isStartTag() && t.asStartTag().name().equals("noframes")) {
                    return tb.process(t, InHead);
                }
                tb.error(this);
                return false;
            }
        }
    };
    static HtmlTreeBuilderState AfterBody = new HtmlTreeBuilderState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "AfterBody";
        }

        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (HtmlTreeBuilderState.isWhitespace(t)) {
                return tb.process(t, InBody);
            }
            if (t.isComment()) {
                tb.insert(t.asComment());
                return true;
            } else if (t.isDoctype()) {
                tb.error(this);
                return false;
            } else if (t.isStartTag() && t.asStartTag().name().equals("html")) {
                return tb.process(t, InBody);
            } else {
                if (!t.isEndTag() || !t.asEndTag().name().equals("html")) {
                    if (t.isEOF()) {
                        return true;
                    }
                    tb.error(this);
                    tb.transition(InBody);
                    return tb.process(t);
                } else if (tb.isFragmentParsing()) {
                    tb.error(this);
                    return false;
                } else {
                    tb.transition(AfterAfterBody);
                    return true;
                }
            }
        }
    };
    static HtmlTreeBuilderState AfterFrameset = new HtmlTreeBuilderState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "AfterFrameset";
        }

        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (HtmlTreeBuilderState.isWhitespace(t)) {
                tb.insert(t.asCharacter());
                return true;
            } else if (t.isComment()) {
                tb.insert(t.asComment());
                return true;
            } else if (t.isDoctype()) {
                tb.error(this);
                return false;
            } else if (t.isStartTag() && t.asStartTag().name().equals("html")) {
                return tb.process(t, InBody);
            } else {
                if (t.isEndTag() && t.asEndTag().name().equals("html")) {
                    tb.transition(AfterAfterFrameset);
                    return true;
                } else if (t.isStartTag() && t.asStartTag().name().equals("noframes")) {
                    return tb.process(t, InHead);
                } else {
                    if (t.isEOF()) {
                        return true;
                    }
                    tb.error(this);
                    return false;
                }
            }
        }
    };
    static HtmlTreeBuilderState AfterHead = new HtmlTreeBuilderState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "AfterHead";
        }

        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            Token token = t;
            HtmlTreeBuilder htmlTreeBuilder = tb;
            if (HtmlTreeBuilderState.isWhitespace(t)) {
                htmlTreeBuilder.insert(t.asCharacter());
                return true;
            } else if (t.isComment()) {
                htmlTreeBuilder.insert(t.asComment());
                return true;
            } else if (t.isDoctype()) {
                htmlTreeBuilder.error(this);
                return true;
            } else if (t.isStartTag()) {
                Token.StartTag startTag = t.asStartTag();
                String name = startTag.name();
                if (name.equals("html")) {
                    return htmlTreeBuilder.process(token, InBody);
                }
                if (name.equals("body")) {
                    htmlTreeBuilder.insert(startTag);
                    htmlTreeBuilder.framesetOk(false);
                    htmlTreeBuilder.transition(InBody);
                    return true;
                } else if (name.equals("frameset")) {
                    htmlTreeBuilder.insert(startTag);
                    htmlTreeBuilder.transition(InFrameset);
                    return true;
                } else if (StringUtil.m276in(name, "base", "basefont", "bgsound", "link", "meta", "noframes", SvgConstants.Tags.SCRIPT, "style", "title")) {
                    htmlTreeBuilder.error(this);
                    Element head = tb.getHeadElement();
                    htmlTreeBuilder.push(head);
                    htmlTreeBuilder.process(token, InHead);
                    htmlTreeBuilder.removeFromStack(head);
                    return true;
                } else if (name.equals(XfdfConstants.HEAD)) {
                    htmlTreeBuilder.error(this);
                    return false;
                } else {
                    anythingElse(t, tb);
                    return true;
                }
            } else if (!t.isEndTag()) {
                anythingElse(t, tb);
                return true;
            } else if (StringUtil.m276in(t.asEndTag().name(), "body", "html")) {
                anythingElse(t, tb);
                return true;
            } else {
                htmlTreeBuilder.error(this);
                return false;
            }
        }

        private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            tb.processStartTag("body");
            tb.framesetOk(true);
            return tb.process(t);
        }
    };
    static HtmlTreeBuilderState BeforeHead = new HtmlTreeBuilderState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "BeforeHead";
        }

        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (HtmlTreeBuilderState.isWhitespace(t)) {
                return true;
            }
            if (t.isComment()) {
                tb.insert(t.asComment());
            } else if (t.isDoctype()) {
                tb.error(this);
                return false;
            } else if (t.isStartTag() && t.asStartTag().name().equals("html")) {
                return InBody.process(t, tb);
            } else {
                if (t.isStartTag() && t.asStartTag().name().equals(XfdfConstants.HEAD)) {
                    tb.setHeadElement(tb.insert(t.asStartTag()));
                    tb.transition(InHead);
                } else if (t.isEndTag() && StringUtil.m276in(t.asEndTag().name(), XfdfConstants.HEAD, "body", "html", "br")) {
                    tb.processStartTag(XfdfConstants.HEAD);
                    return tb.process(t);
                } else if (t.isEndTag()) {
                    tb.error(this);
                    return false;
                } else {
                    tb.processStartTag(XfdfConstants.HEAD);
                    return tb.process(t);
                }
            }
            return true;
        }
    };
    static HtmlTreeBuilderState BeforeHtml = new HtmlTreeBuilderState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "BeforeHtml";
        }

        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (t.isDoctype()) {
                tb.error(this);
                return false;
            }
            if (t.isComment()) {
                tb.insert(t.asComment());
            } else if (HtmlTreeBuilderState.isWhitespace(t)) {
                return true;
            } else {
                if (t.isStartTag() && t.asStartTag().name().equals("html")) {
                    tb.insert(t.asStartTag());
                    tb.transition(BeforeHead);
                } else if (t.isEndTag() && StringUtil.m276in(t.asEndTag().name(), XfdfConstants.HEAD, "body", "html", "br")) {
                    return anythingElse(t, tb);
                } else {
                    if (!t.isEndTag()) {
                        return anythingElse(t, tb);
                    }
                    tb.error(this);
                    return false;
                }
            }
            return true;
        }

        private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            tb.insertStartTag("html");
            tb.transition(BeforeHead);
            return tb.process(t);
        }
    };
    static HtmlTreeBuilderState ForeignContent = new HtmlTreeBuilderState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "ForeignContent";
        }

        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            return true;
        }
    };
    static HtmlTreeBuilderState InBody = new HtmlTreeBuilderState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "InBody";
        }

        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            boolean seenFormattingElement;
            ArrayList<Element> stack;
            Token token = t;
            HtmlTreeBuilder htmlTreeBuilder = tb;
            boolean z = true;
            boolean z2 = false;
            switch (C151124.f1625xd695eb0c[token.type.ordinal()]) {
                case 1:
                    htmlTreeBuilder.insert(t.asComment());
                    return true;
                case 2:
                    htmlTreeBuilder.error(this);
                    return false;
                case 3:
                    Token.StartTag startTag = t.asStartTag();
                    String name = startTag.name();
                    if (name.equals("a")) {
                        if (htmlTreeBuilder.getActiveFormattingElement("a") != null) {
                            htmlTreeBuilder.error(this);
                            htmlTreeBuilder.processEndTag("a");
                            Element remainingA = htmlTreeBuilder.getFromStack("a");
                            if (remainingA != null) {
                                htmlTreeBuilder.removeFromActiveFormattingElements(remainingA);
                                htmlTreeBuilder.removeFromStack(remainingA);
                            }
                        }
                        tb.reconstructFormattingElements();
                        htmlTreeBuilder.pushActiveFormattingElements(htmlTreeBuilder.insert(startTag));
                        return true;
                    } else if (StringUtil.inSorted(name, Constants.InBodyStartEmptyFormatters)) {
                        tb.reconstructFormattingElements();
                        htmlTreeBuilder.insertEmpty(startTag);
                        htmlTreeBuilder.framesetOk(false);
                        return true;
                    } else if (StringUtil.inSorted(name, Constants.InBodyStartPClosers)) {
                        if (htmlTreeBuilder.inButtonScope("p")) {
                            htmlTreeBuilder.processEndTag("p");
                        }
                        htmlTreeBuilder.insert(startTag);
                        return true;
                    } else if (name.equals("span")) {
                        tb.reconstructFormattingElements();
                        htmlTreeBuilder.insert(startTag);
                        return true;
                    } else if (name.equals("li")) {
                        htmlTreeBuilder.framesetOk(false);
                        ArrayList<Element> stack2 = tb.getStack();
                        int i = stack2.size() - 1;
                        while (true) {
                            if (i > 0) {
                                Element el = stack2.get(i);
                                if (el.nodeName().equals("li")) {
                                    htmlTreeBuilder.processEndTag("li");
                                } else if (!htmlTreeBuilder.isSpecial(el) || StringUtil.inSorted(el.nodeName(), Constants.InBodyStartLiBreakers)) {
                                    i--;
                                }
                            }
                        }
                        if (htmlTreeBuilder.inButtonScope("p") != 0) {
                            htmlTreeBuilder.processEndTag("p");
                        }
                        htmlTreeBuilder.insert(startTag);
                        return true;
                    } else if (name.equals("html")) {
                        htmlTreeBuilder.error(this);
                        Element html = tb.getStack().get(0);
                        Iterator<Attribute> it = startTag.getAttributes().iterator();
                        while (it.hasNext()) {
                            Attribute attribute = it.next();
                            if (!html.hasAttr(attribute.getKey())) {
                                html.attributes().put(attribute);
                            }
                        }
                        return true;
                    } else if (StringUtil.inSorted(name, Constants.InBodyStartToHead)) {
                        return htmlTreeBuilder.process(token, InHead);
                    } else {
                        if (name.equals("body")) {
                            htmlTreeBuilder.error(this);
                            ArrayList<Element> stack3 = tb.getStack();
                            if (stack3.size() == 1) {
                                return false;
                            }
                            if (stack3.size() > 2 && !stack3.get(1).nodeName().equals("body")) {
                                return false;
                            }
                            htmlTreeBuilder.framesetOk(false);
                            Element body = stack3.get(1);
                            Iterator<Attribute> it2 = startTag.getAttributes().iterator();
                            while (it2.hasNext()) {
                                Attribute attribute2 = it2.next();
                                if (!body.hasAttr(attribute2.getKey())) {
                                    body.attributes().put(attribute2);
                                }
                            }
                            return true;
                        } else if (name.equals("frameset")) {
                            htmlTreeBuilder.error(this);
                            ArrayList<Element> stack4 = tb.getStack();
                            if (stack4.size() == 1) {
                                return false;
                            }
                            if ((stack4.size() > 2 && !stack4.get(1).nodeName().equals("body")) || !tb.framesetOk()) {
                                return false;
                            }
                            Element second = stack4.get(1);
                            if (second.parent() != null) {
                                second.remove();
                            }
                            while (stack4.size() > 1) {
                                stack4.remove(stack4.size() - 1);
                            }
                            htmlTreeBuilder.insert(startTag);
                            htmlTreeBuilder.transition(InFrameset);
                            return true;
                        } else if (StringUtil.inSorted(name, Constants.Headings)) {
                            if (htmlTreeBuilder.inButtonScope("p")) {
                                htmlTreeBuilder.processEndTag("p");
                            }
                            if (StringUtil.inSorted(tb.currentElement().nodeName(), Constants.Headings)) {
                                htmlTreeBuilder.error(this);
                                tb.pop();
                            }
                            htmlTreeBuilder.insert(startTag);
                            return true;
                        } else if (StringUtil.inSorted(name, Constants.InBodyStartPreListing)) {
                            if (htmlTreeBuilder.inButtonScope("p")) {
                                htmlTreeBuilder.processEndTag("p");
                            }
                            htmlTreeBuilder.insert(startTag);
                            htmlTreeBuilder.framesetOk(false);
                            return true;
                        } else if (name.equals("form")) {
                            if (tb.getFormElement() != null) {
                                htmlTreeBuilder.error(this);
                                return false;
                            }
                            if (htmlTreeBuilder.inButtonScope("p")) {
                                htmlTreeBuilder.processEndTag("p");
                            }
                            htmlTreeBuilder.insertForm(startTag, true);
                            return true;
                        } else if (StringUtil.inSorted(name, Constants.DdDt)) {
                            htmlTreeBuilder.framesetOk(false);
                            ArrayList<Element> stack5 = tb.getStack();
                            int i2 = stack5.size() - 1;
                            while (true) {
                                if (i2 > 0) {
                                    Element el2 = stack5.get(i2);
                                    if (StringUtil.inSorted(el2.nodeName(), Constants.DdDt)) {
                                        htmlTreeBuilder.processEndTag(el2.nodeName());
                                    } else if (!htmlTreeBuilder.isSpecial(el2) || StringUtil.inSorted(el2.nodeName(), Constants.InBodyStartLiBreakers)) {
                                        i2--;
                                    }
                                }
                            }
                            if (htmlTreeBuilder.inButtonScope("p") != 0) {
                                htmlTreeBuilder.processEndTag("p");
                            }
                            htmlTreeBuilder.insert(startTag);
                            return true;
                        } else if (name.equals("plaintext")) {
                            if (htmlTreeBuilder.inButtonScope("p")) {
                                htmlTreeBuilder.processEndTag("p");
                            }
                            htmlTreeBuilder.insert(startTag);
                            htmlTreeBuilder.tokeniser.transition(TokeniserState.PLAINTEXT);
                            return true;
                        } else if (name.equals("button")) {
                            if (htmlTreeBuilder.inButtonScope("button")) {
                                htmlTreeBuilder.error(this);
                                htmlTreeBuilder.processEndTag("button");
                                htmlTreeBuilder.process(startTag);
                                return true;
                            }
                            tb.reconstructFormattingElements();
                            htmlTreeBuilder.insert(startTag);
                            htmlTreeBuilder.framesetOk(false);
                            return true;
                        } else if (StringUtil.inSorted(name, Constants.Formatters)) {
                            tb.reconstructFormattingElements();
                            htmlTreeBuilder.pushActiveFormattingElements(htmlTreeBuilder.insert(startTag));
                            return true;
                        } else if (name.equals("nobr")) {
                            tb.reconstructFormattingElements();
                            if (htmlTreeBuilder.inScope("nobr")) {
                                htmlTreeBuilder.error(this);
                                htmlTreeBuilder.processEndTag("nobr");
                                tb.reconstructFormattingElements();
                            }
                            htmlTreeBuilder.pushActiveFormattingElements(htmlTreeBuilder.insert(startTag));
                            return true;
                        } else if (StringUtil.inSorted(name, Constants.InBodyStartApplets)) {
                            tb.reconstructFormattingElements();
                            htmlTreeBuilder.insert(startTag);
                            tb.insertMarkerToFormattingElements();
                            htmlTreeBuilder.framesetOk(false);
                            return true;
                        } else if (name.equals("table")) {
                            if (tb.getDocument().quirksMode() != Document.QuirksMode.quirks && htmlTreeBuilder.inButtonScope("p")) {
                                htmlTreeBuilder.processEndTag("p");
                            }
                            htmlTreeBuilder.insert(startTag);
                            htmlTreeBuilder.framesetOk(false);
                            htmlTreeBuilder.transition(InTable);
                            return true;
                        } else if (name.equals("input")) {
                            tb.reconstructFormattingElements();
                            if (htmlTreeBuilder.insertEmpty(startTag).attr(PdfConst.Type).equalsIgnoreCase("hidden")) {
                                return true;
                            }
                            htmlTreeBuilder.framesetOk(false);
                            return true;
                        } else if (StringUtil.inSorted(name, Constants.InBodyStartMedia)) {
                            htmlTreeBuilder.insertEmpty(startTag);
                            return true;
                        } else if (name.equals("hr")) {
                            if (htmlTreeBuilder.inButtonScope("p")) {
                                htmlTreeBuilder.processEndTag("p");
                            }
                            htmlTreeBuilder.insertEmpty(startTag);
                            htmlTreeBuilder.framesetOk(false);
                            return true;
                        } else if (name.equals(SvgConstants.Tags.IMAGE)) {
                            if (htmlTreeBuilder.getFromStack(SvgConstants.Tags.SVG) == null) {
                                return htmlTreeBuilder.process(startTag.name("img"));
                            }
                            htmlTreeBuilder.insert(startTag);
                            return true;
                        } else if (name.equals("isindex")) {
                            htmlTreeBuilder.error(this);
                            if (tb.getFormElement() != null) {
                                return false;
                            }
                            htmlTreeBuilder.tokeniser.acknowledgeSelfClosingFlag();
                            htmlTreeBuilder.processStartTag("form");
                            if (startTag.attributes.hasKey("action")) {
                                tb.getFormElement().attr("action", startTag.attributes.get("action"));
                            }
                            htmlTreeBuilder.processStartTag("hr");
                            htmlTreeBuilder.processStartTag(AnnotatedPrivateKey.LABEL);
                            htmlTreeBuilder.process(new Token.Character().data(startTag.attributes.hasKey("prompt") ? startTag.attributes.get("prompt") : "This is a searchable index. Enter search keywords: "));
                            Attributes inputAttribs = new Attributes();
                            Iterator<Attribute> it3 = startTag.attributes.iterator();
                            while (it3.hasNext()) {
                                Attribute attr = it3.next();
                                if (!StringUtil.inSorted(attr.getKey(), Constants.InBodyStartInputAttribs)) {
                                    inputAttribs.put(attr);
                                }
                                Token token2 = t;
                            }
                            inputAttribs.put(XfdfConstants.NAME, "isindex");
                            htmlTreeBuilder.processStartTag("input", inputAttribs);
                            htmlTreeBuilder.processEndTag(AnnotatedPrivateKey.LABEL);
                            htmlTreeBuilder.processStartTag("hr");
                            htmlTreeBuilder.processEndTag("form");
                            return true;
                        } else if (name.equals("textarea")) {
                            htmlTreeBuilder.insert(startTag);
                            htmlTreeBuilder.tokeniser.transition(TokeniserState.Rcdata);
                            tb.markInsertionMode();
                            htmlTreeBuilder.framesetOk(false);
                            htmlTreeBuilder.transition(Text);
                            return true;
                        } else if (name.equals("xmp")) {
                            if (htmlTreeBuilder.inButtonScope("p")) {
                                htmlTreeBuilder.processEndTag("p");
                            }
                            tb.reconstructFormattingElements();
                            htmlTreeBuilder.framesetOk(false);
                            HtmlTreeBuilderState.handleRawtext(startTag, htmlTreeBuilder);
                            return true;
                        } else if (name.equals("iframe")) {
                            htmlTreeBuilder.framesetOk(false);
                            HtmlTreeBuilderState.handleRawtext(startTag, htmlTreeBuilder);
                            return true;
                        } else if (name.equals("noembed")) {
                            HtmlTreeBuilderState.handleRawtext(startTag, htmlTreeBuilder);
                            return true;
                        } else if (name.equals("select")) {
                            tb.reconstructFormattingElements();
                            htmlTreeBuilder.insert(startTag);
                            htmlTreeBuilder.framesetOk(false);
                            HtmlTreeBuilderState state = tb.state();
                            if (state.equals(InTable) || state.equals(InCaption) || state.equals(InTableBody) || state.equals(InRow) || state.equals(InCell)) {
                                htmlTreeBuilder.transition(InSelectInTable);
                                return true;
                            }
                            htmlTreeBuilder.transition(InSelect);
                            return true;
                        } else if (StringUtil.inSorted(name, Constants.InBodyStartOptions)) {
                            if (tb.currentElement().nodeName().equals("option")) {
                                htmlTreeBuilder.processEndTag("option");
                            }
                            tb.reconstructFormattingElements();
                            htmlTreeBuilder.insert(startTag);
                            return true;
                        } else if (StringUtil.inSorted(name, Constants.InBodyStartRuby)) {
                            if (!htmlTreeBuilder.inScope("ruby")) {
                                return true;
                            }
                            tb.generateImpliedEndTags();
                            if (!tb.currentElement().nodeName().equals("ruby")) {
                                htmlTreeBuilder.error(this);
                                htmlTreeBuilder.popStackToBefore("ruby");
                            }
                            htmlTreeBuilder.insert(startTag);
                            return true;
                        } else if (name.equals("math")) {
                            tb.reconstructFormattingElements();
                            htmlTreeBuilder.insert(startTag);
                            htmlTreeBuilder.tokeniser.acknowledgeSelfClosingFlag();
                            return true;
                        } else if (name.equals(SvgConstants.Tags.SVG)) {
                            tb.reconstructFormattingElements();
                            htmlTreeBuilder.insert(startTag);
                            htmlTreeBuilder.tokeniser.acknowledgeSelfClosingFlag();
                            return true;
                        } else if (StringUtil.inSorted(name, Constants.InBodyStartDrop)) {
                            htmlTreeBuilder.error(this);
                            return false;
                        } else {
                            tb.reconstructFormattingElements();
                            htmlTreeBuilder.insert(startTag);
                            return true;
                        }
                    }
                case 4:
                    Token.EndTag endTag = t.asEndTag();
                    String name2 = endTag.name();
                    if (StringUtil.inSorted(name2, Constants.InBodyEndAdoptionFormatters)) {
                        int i3 = 0;
                        while (i3 < 8) {
                            Element formatEl = htmlTreeBuilder.getActiveFormattingElement(name2);
                            if (formatEl == null) {
                                return anyOtherEndTag(t, tb);
                            }
                            if (!htmlTreeBuilder.onStack(formatEl)) {
                                htmlTreeBuilder.error(this);
                                htmlTreeBuilder.removeFromActiveFormattingElements(formatEl);
                                return z;
                            } else if (!htmlTreeBuilder.inScope(formatEl.nodeName())) {
                                htmlTreeBuilder.error(this);
                                return z2;
                            } else {
                                if (tb.currentElement() != formatEl) {
                                    htmlTreeBuilder.error(this);
                                }
                                Element furthestBlock = null;
                                Element commonAncestor = null;
                                boolean seenFormattingElement2 = false;
                                ArrayList<Element> stack6 = tb.getStack();
                                int stackSize = stack6.size();
                                int si = 0;
                                while (true) {
                                    if (si < stackSize && si < 64) {
                                        Element el3 = stack6.get(si);
                                        if (el3 == formatEl) {
                                            commonAncestor = stack6.get(si - 1);
                                            seenFormattingElement2 = true;
                                        } else if (seenFormattingElement2 && htmlTreeBuilder.isSpecial(el3)) {
                                            furthestBlock = el3;
                                        }
                                        si++;
                                    }
                                }
                                if (furthestBlock == null) {
                                    htmlTreeBuilder.popStackToClose(formatEl.nodeName());
                                    htmlTreeBuilder.removeFromActiveFormattingElements(formatEl);
                                    return z;
                                }
                                Element node = furthestBlock;
                                Element lastNode = furthestBlock;
                                int j = 0;
                                while (true) {
                                    if (j < 3) {
                                        if (htmlTreeBuilder.onStack(node)) {
                                            node = htmlTreeBuilder.aboveOnStack(node);
                                        }
                                        if (!htmlTreeBuilder.isInActiveFormattingElements(node)) {
                                            htmlTreeBuilder.removeFromStack(node);
                                            seenFormattingElement = seenFormattingElement2;
                                            stack = stack6;
                                        } else if (node == formatEl) {
                                            boolean z3 = seenFormattingElement2;
                                            ArrayList<Element> arrayList = stack6;
                                        } else {
                                            seenFormattingElement = seenFormattingElement2;
                                            stack = stack6;
                                            Element replacement = new Element(Tag.valueOf(node.nodeName()), tb.getBaseUri());
                                            htmlTreeBuilder.replaceActiveFormattingElement(node, replacement);
                                            htmlTreeBuilder.replaceOnStack(node, replacement);
                                            Element node2 = replacement;
                                            if (lastNode.parent() != null) {
                                                lastNode.remove();
                                            }
                                            node2.appendChild(lastNode);
                                            node = node2;
                                            lastNode = node2;
                                        }
                                        j++;
                                        stack6 = stack;
                                        seenFormattingElement2 = seenFormattingElement;
                                    } else {
                                        ArrayList<Element> arrayList2 = stack6;
                                    }
                                }
                                if (StringUtil.inSorted(commonAncestor.nodeName(), Constants.InBodyEndTableFosters)) {
                                    if (lastNode.parent() != null) {
                                        lastNode.remove();
                                    }
                                    htmlTreeBuilder.insertInFosterParent(lastNode);
                                } else {
                                    if (lastNode.parent() != null) {
                                        lastNode.remove();
                                    }
                                    commonAncestor.appendChild(lastNode);
                                }
                                Element adopter = new Element(formatEl.tag(), tb.getBaseUri());
                                adopter.attributes().addAll(formatEl.attributes());
                                Node[] childNodes = (Node[]) furthestBlock.childNodes().toArray(new Node[furthestBlock.childNodeSize()]);
                                int length = childNodes.length;
                                int i4 = 0;
                                while (i4 < length) {
                                    adopter.appendChild(childNodes[i4]);
                                    i4++;
                                    commonAncestor = commonAncestor;
                                }
                                furthestBlock.appendChild(adopter);
                                htmlTreeBuilder.removeFromActiveFormattingElements(formatEl);
                                htmlTreeBuilder.removeFromStack(formatEl);
                                htmlTreeBuilder.insertOnStackAfter(furthestBlock, adopter);
                                i3++;
                                z = true;
                                z2 = false;
                            }
                        }
                        return true;
                    } else if (StringUtil.inSorted(name2, Constants.InBodyEndClosers)) {
                        if (!htmlTreeBuilder.inScope(name2)) {
                            htmlTreeBuilder.error(this);
                            return false;
                        }
                        tb.generateImpliedEndTags();
                        if (!tb.currentElement().nodeName().equals(name2)) {
                            htmlTreeBuilder.error(this);
                        }
                        htmlTreeBuilder.popStackToClose(name2);
                        return true;
                    } else if (name2.equals("span")) {
                        return anyOtherEndTag(t, tb);
                    } else {
                        if (name2.equals("li")) {
                            if (!htmlTreeBuilder.inListItemScope(name2)) {
                                htmlTreeBuilder.error(this);
                                return false;
                            }
                            htmlTreeBuilder.generateImpliedEndTags(name2);
                            if (!tb.currentElement().nodeName().equals(name2)) {
                                htmlTreeBuilder.error(this);
                            }
                            htmlTreeBuilder.popStackToClose(name2);
                            return true;
                        } else if (name2.equals("body")) {
                            if (!htmlTreeBuilder.inScope("body")) {
                                htmlTreeBuilder.error(this);
                                return false;
                            }
                            htmlTreeBuilder.transition(AfterBody);
                            return true;
                        } else if (name2.equals("html")) {
                            if (htmlTreeBuilder.processEndTag("body")) {
                                return htmlTreeBuilder.process(endTag);
                            }
                            return true;
                        } else if (name2.equals("form")) {
                            Element currentForm = tb.getFormElement();
                            htmlTreeBuilder.setFormElement((FormElement) null);
                            if (currentForm == null || !htmlTreeBuilder.inScope(name2)) {
                                htmlTreeBuilder.error(this);
                                return false;
                            }
                            tb.generateImpliedEndTags();
                            if (!tb.currentElement().nodeName().equals(name2)) {
                                htmlTreeBuilder.error(this);
                            }
                            htmlTreeBuilder.removeFromStack(currentForm);
                            return true;
                        } else if (name2.equals("p")) {
                            if (!htmlTreeBuilder.inButtonScope(name2)) {
                                htmlTreeBuilder.error(this);
                                htmlTreeBuilder.processStartTag(name2);
                                return htmlTreeBuilder.process(endTag);
                            }
                            htmlTreeBuilder.generateImpliedEndTags(name2);
                            if (!tb.currentElement().nodeName().equals(name2)) {
                                htmlTreeBuilder.error(this);
                            }
                            htmlTreeBuilder.popStackToClose(name2);
                            return true;
                        } else if (StringUtil.inSorted(name2, Constants.DdDt)) {
                            if (!htmlTreeBuilder.inScope(name2)) {
                                htmlTreeBuilder.error(this);
                                return false;
                            }
                            htmlTreeBuilder.generateImpliedEndTags(name2);
                            if (!tb.currentElement().nodeName().equals(name2)) {
                                htmlTreeBuilder.error(this);
                            }
                            htmlTreeBuilder.popStackToClose(name2);
                            return true;
                        } else if (StringUtil.inSorted(name2, Constants.Headings)) {
                            if (!htmlTreeBuilder.inScope(Constants.Headings)) {
                                htmlTreeBuilder.error(this);
                                return false;
                            }
                            htmlTreeBuilder.generateImpliedEndTags(name2);
                            if (!tb.currentElement().nodeName().equals(name2)) {
                                htmlTreeBuilder.error(this);
                            }
                            htmlTreeBuilder.popStackToClose(Constants.Headings);
                            return true;
                        } else if (name2.equals("sarcasm")) {
                            return anyOtherEndTag(t, tb);
                        } else {
                            if (StringUtil.inSorted(name2, Constants.InBodyStartApplets)) {
                                if (htmlTreeBuilder.inScope(XfdfConstants.NAME)) {
                                    return true;
                                }
                                if (!htmlTreeBuilder.inScope(name2)) {
                                    htmlTreeBuilder.error(this);
                                    return false;
                                }
                                tb.generateImpliedEndTags();
                                if (!tb.currentElement().nodeName().equals(name2)) {
                                    htmlTreeBuilder.error(this);
                                }
                                htmlTreeBuilder.popStackToClose(name2);
                                tb.clearFormattingElementsToLastMarker();
                                return true;
                            } else if (!name2.equals("br")) {
                                return anyOtherEndTag(t, tb);
                            } else {
                                htmlTreeBuilder.error(this);
                                htmlTreeBuilder.processStartTag("br");
                                return false;
                            }
                        }
                    }
                case 5:
                    Token.Character c = t.asCharacter();
                    if (c.getData().equals(HtmlTreeBuilderState.nullString)) {
                        htmlTreeBuilder.error(this);
                        return false;
                    } else if (!tb.framesetOk() || !HtmlTreeBuilderState.isWhitespace((Token) c)) {
                        tb.reconstructFormattingElements();
                        htmlTreeBuilder.insert(c);
                        htmlTreeBuilder.framesetOk(false);
                        return true;
                    } else {
                        tb.reconstructFormattingElements();
                        htmlTreeBuilder.insert(c);
                        return true;
                    }
                default:
                    return true;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean anyOtherEndTag(Token t, HtmlTreeBuilder tb) {
            String name = t.asEndTag().name();
            ArrayList<Element> stack = tb.getStack();
            int pos = stack.size() - 1;
            while (true) {
                if (pos < 0) {
                    break;
                }
                Element node = stack.get(pos);
                if (node.nodeName().equals(name)) {
                    tb.generateImpliedEndTags(name);
                    if (!name.equals(tb.currentElement().nodeName())) {
                        tb.error(this);
                    }
                    tb.popStackToClose(name);
                } else if (tb.isSpecial(node)) {
                    tb.error(this);
                    return false;
                } else {
                    pos--;
                }
            }
            return true;
        }
    };
    static HtmlTreeBuilderState InCaption = new HtmlTreeBuilderState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "InCaption";
        }

        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (!t.isEndTag() || !t.asEndTag().name().equals("caption")) {
                if ((t.isStartTag() && StringUtil.m276in(t.asStartTag().name(), "caption", "col", "colgroup", "tbody", "td", "tfoot", "th", "thead", "tr")) || (t.isEndTag() && t.asEndTag().name().equals("table"))) {
                    tb.error(this);
                    if (tb.processEndTag("caption")) {
                        return tb.process(t);
                    }
                    return true;
                } else if (!t.isEndTag() || !StringUtil.m276in(t.asEndTag().name(), "body", "col", "colgroup", "html", "tbody", "td", "tfoot", "th", "thead", "tr")) {
                    return tb.process(t, InBody);
                } else {
                    tb.error(this);
                    return false;
                }
            } else if (!tb.inTableScope(t.asEndTag().name())) {
                tb.error(this);
                return false;
            } else {
                tb.generateImpliedEndTags();
                if (!tb.currentElement().nodeName().equals("caption")) {
                    tb.error(this);
                }
                tb.popStackToClose("caption");
                tb.clearFormattingElementsToLastMarker();
                tb.transition(InTable);
                return true;
            }
        }
    };
    static HtmlTreeBuilderState InCell = new HtmlTreeBuilderState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "InCell";
        }

        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (t.isEndTag()) {
                String name = t.asEndTag().name();
                if (StringUtil.m276in(name, "td", "th")) {
                    if (!tb.inTableScope(name)) {
                        tb.error(this);
                        tb.transition(InRow);
                        return false;
                    }
                    tb.generateImpliedEndTags();
                    if (!tb.currentElement().nodeName().equals(name)) {
                        tb.error(this);
                    }
                    tb.popStackToClose(name);
                    tb.clearFormattingElementsToLastMarker();
                    tb.transition(InRow);
                    return true;
                } else if (StringUtil.m276in(name, "body", "caption", "col", "colgroup", "html")) {
                    tb.error(this);
                    return false;
                } else if (!StringUtil.m276in(name, "table", "tbody", "tfoot", "thead", "tr")) {
                    return anythingElse(t, tb);
                } else {
                    if (!tb.inTableScope(name)) {
                        tb.error(this);
                        return false;
                    }
                    closeCell(tb);
                    return tb.process(t);
                }
            } else if (!t.isStartTag() || !StringUtil.m276in(t.asStartTag().name(), "caption", "col", "colgroup", "tbody", "td", "tfoot", "th", "thead", "tr")) {
                return anythingElse(t, tb);
            } else {
                if (tb.inTableScope("td") || tb.inTableScope("th")) {
                    closeCell(tb);
                    return tb.process(t);
                }
                tb.error(this);
                return false;
            }
        }

        private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            return tb.process(t, InBody);
        }

        private void closeCell(HtmlTreeBuilder tb) {
            if (tb.inTableScope("td")) {
                tb.processEndTag("td");
            } else {
                tb.processEndTag("th");
            }
        }
    };
    static HtmlTreeBuilderState InColumnGroup = new HtmlTreeBuilderState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "InColumnGroup";
        }

        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (HtmlTreeBuilderState.isWhitespace(t)) {
                tb.insert(t.asCharacter());
                return true;
            }
            switch (C151124.f1625xd695eb0c[t.type.ordinal()]) {
                case 1:
                    tb.insert(t.asComment());
                    break;
                case 2:
                    tb.error(this);
                    break;
                case 3:
                    Token.StartTag startTag = t.asStartTag();
                    String name = startTag.name();
                    if (name.equals("html")) {
                        return tb.process(t, InBody);
                    }
                    if (name.equals("col")) {
                        tb.insertEmpty(startTag);
                        break;
                    } else {
                        return anythingElse(t, tb);
                    }
                case 4:
                    if (t.asEndTag().name().equals("colgroup")) {
                        if (!tb.currentElement().nodeName().equals("html")) {
                            tb.pop();
                            tb.transition(InTable);
                            break;
                        } else {
                            tb.error(this);
                            return false;
                        }
                    } else {
                        return anythingElse(t, tb);
                    }
                case 6:
                    if (tb.currentElement().nodeName().equals("html")) {
                        return true;
                    }
                    return anythingElse(t, tb);
                default:
                    return anythingElse(t, tb);
            }
            return true;
        }

        private boolean anythingElse(Token t, TreeBuilder tb) {
            if (tb.processEndTag("colgroup")) {
                return tb.process(t);
            }
            return true;
        }
    };
    static HtmlTreeBuilderState InFrameset = new HtmlTreeBuilderState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "InFrameset";
        }

        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (HtmlTreeBuilderState.isWhitespace(t)) {
                tb.insert(t.asCharacter());
            } else if (t.isComment()) {
                tb.insert(t.asComment());
            } else if (t.isDoctype()) {
                tb.error(this);
                return false;
            } else if (t.isStartTag()) {
                Token.StartTag start = t.asStartTag();
                String name = start.name();
                if (name.equals("html")) {
                    return tb.process(start, InBody);
                }
                if (name.equals("frameset")) {
                    tb.insert(start);
                } else if (name.equals(TypedValues.AttributesType.S_FRAME)) {
                    tb.insertEmpty(start);
                } else if (name.equals("noframes")) {
                    return tb.process(start, InHead);
                } else {
                    tb.error(this);
                    return false;
                }
            } else if (!t.isEndTag() || !t.asEndTag().name().equals("frameset")) {
                if (!t.isEOF()) {
                    tb.error(this);
                    return false;
                } else if (!tb.currentElement().nodeName().equals("html")) {
                    tb.error(this);
                    return true;
                }
            } else if (tb.currentElement().nodeName().equals("html")) {
                tb.error(this);
                return false;
            } else {
                tb.pop();
                if (!tb.isFragmentParsing() && !tb.currentElement().nodeName().equals("frameset")) {
                    tb.transition(AfterFrameset);
                }
            }
            return true;
        }
    };
    static HtmlTreeBuilderState InHead = new HtmlTreeBuilderState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "InHead";
        }

        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (HtmlTreeBuilderState.isWhitespace(t)) {
                tb.insert(t.asCharacter());
                return true;
            }
            switch (C151124.f1625xd695eb0c[t.type.ordinal()]) {
                case 1:
                    tb.insert(t.asComment());
                    break;
                case 2:
                    tb.error(this);
                    return false;
                case 3:
                    Token.StartTag start = t.asStartTag();
                    String name = start.name();
                    if (name.equals("html")) {
                        return InBody.process(t, tb);
                    }
                    if (StringUtil.m276in(name, "base", "basefont", "bgsound", "command", "link")) {
                        Element el = tb.insertEmpty(start);
                        if (name.equals("base") && el.hasAttr("href")) {
                            tb.maybeSetBaseUri(el);
                            break;
                        }
                    } else if (name.equals("meta")) {
                        tb.insertEmpty(start);
                        break;
                    } else if (name.equals("title")) {
                        HtmlTreeBuilderState.handleRcData(start, tb);
                        break;
                    } else if (StringUtil.m276in(name, "noframes", "style")) {
                        HtmlTreeBuilderState.handleRawtext(start, tb);
                        break;
                    } else if (name.equals("noscript")) {
                        tb.insert(start);
                        tb.transition(InHeadNoscript);
                        break;
                    } else if (name.equals(SvgConstants.Tags.SCRIPT)) {
                        tb.tokeniser.transition(TokeniserState.ScriptData);
                        tb.markInsertionMode();
                        tb.transition(Text);
                        tb.insert(start);
                        break;
                    } else if (!name.equals(XfdfConstants.HEAD)) {
                        return anythingElse(t, tb);
                    } else {
                        tb.error(this);
                        return false;
                    }
                case 4:
                    String name2 = t.asEndTag().name();
                    if (name2.equals(XfdfConstants.HEAD)) {
                        tb.pop();
                        tb.transition(AfterHead);
                        break;
                    } else if (StringUtil.m276in(name2, "body", "html", "br")) {
                        return anythingElse(t, tb);
                    } else {
                        tb.error(this);
                        return false;
                    }
                default:
                    return anythingElse(t, tb);
            }
            return true;
        }

        private boolean anythingElse(Token t, TreeBuilder tb) {
            tb.processEndTag(XfdfConstants.HEAD);
            return tb.process(t);
        }
    };
    static HtmlTreeBuilderState InHeadNoscript = new HtmlTreeBuilderState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "InHeadNoscript";
        }

        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (t.isDoctype()) {
                tb.error(this);
                return true;
            } else if (t.isStartTag() && t.asStartTag().name().equals("html")) {
                return tb.process(t, InBody);
            } else {
                if (t.isEndTag() && t.asEndTag().name().equals("noscript")) {
                    tb.pop();
                    tb.transition(InHead);
                    return true;
                } else if (HtmlTreeBuilderState.isWhitespace(t) || t.isComment() || (t.isStartTag() && StringUtil.m276in(t.asStartTag().name(), "basefont", "bgsound", "link", "meta", "noframes", "style"))) {
                    return tb.process(t, InHead);
                } else {
                    if (t.isEndTag() && t.asEndTag().name().equals("br")) {
                        return anythingElse(t, tb);
                    }
                    if ((!t.isStartTag() || !StringUtil.m276in(t.asStartTag().name(), XfdfConstants.HEAD, "noscript")) && !t.isEndTag()) {
                        return anythingElse(t, tb);
                    }
                    tb.error(this);
                    return false;
                }
            }
        }

        private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            tb.error(this);
            tb.insert(new Token.Character().data(t.toString()));
            return true;
        }
    };
    static HtmlTreeBuilderState InRow = new HtmlTreeBuilderState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "InRow";
        }

        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (t.isStartTag()) {
                Token.StartTag startTag = t.asStartTag();
                String name = startTag.name();
                if (StringUtil.m276in(name, "th", "td")) {
                    tb.clearStackToTableRowContext();
                    tb.insert(startTag);
                    tb.transition(InCell);
                    tb.insertMarkerToFormattingElements();
                    return true;
                } else if (StringUtil.m276in(name, "caption", "col", "colgroup", "tbody", "tfoot", "thead", "tr")) {
                    return handleMissingTr(t, tb);
                } else {
                    return anythingElse(t, tb);
                }
            } else if (!t.isEndTag()) {
                return anythingElse(t, tb);
            } else {
                String name2 = t.asEndTag().name();
                if (name2.equals("tr")) {
                    if (!tb.inTableScope(name2)) {
                        tb.error(this);
                        return false;
                    }
                    tb.clearStackToTableRowContext();
                    tb.pop();
                    tb.transition(InTableBody);
                    return true;
                } else if (name2.equals("table")) {
                    return handleMissingTr(t, tb);
                } else {
                    if (StringUtil.m276in(name2, "tbody", "tfoot", "thead")) {
                        if (!tb.inTableScope(name2)) {
                            tb.error(this);
                            return false;
                        }
                        tb.processEndTag("tr");
                        return tb.process(t);
                    } else if (!StringUtil.m276in(name2, "body", "caption", "col", "colgroup", "html", "td", "th")) {
                        return anythingElse(t, tb);
                    } else {
                        tb.error(this);
                        return false;
                    }
                }
            }
        }

        private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            return tb.process(t, InTable);
        }

        private boolean handleMissingTr(Token t, TreeBuilder tb) {
            if (tb.processEndTag("tr")) {
                return tb.process(t);
            }
            return false;
        }
    };
    static HtmlTreeBuilderState InSelect = new HtmlTreeBuilderState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "InSelect";
        }

        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            switch (C151124.f1625xd695eb0c[t.type.ordinal()]) {
                case 1:
                    tb.insert(t.asComment());
                    return true;
                case 2:
                    tb.error(this);
                    return false;
                case 3:
                    Token.StartTag start = t.asStartTag();
                    String name = start.name();
                    if (name.equals("html")) {
                        return tb.process(start, InBody);
                    }
                    if (name.equals("option")) {
                        tb.processEndTag("option");
                        tb.insert(start);
                        return true;
                    } else if (name.equals("optgroup")) {
                        if (tb.currentElement().nodeName().equals("option")) {
                            tb.processEndTag("option");
                        } else if (tb.currentElement().nodeName().equals("optgroup")) {
                            tb.processEndTag("optgroup");
                        }
                        tb.insert(start);
                        return true;
                    } else if (name.equals("select")) {
                        tb.error(this);
                        return tb.processEndTag("select");
                    } else if (StringUtil.m276in(name, "input", "keygen", "textarea")) {
                        tb.error(this);
                        if (!tb.inSelectScope("select")) {
                            return false;
                        }
                        tb.processEndTag("select");
                        return tb.process(start);
                    } else if (name.equals(SvgConstants.Tags.SCRIPT)) {
                        return tb.process(t, InHead);
                    } else {
                        return anythingElse(t, tb);
                    }
                case 4:
                    String name2 = t.asEndTag().name();
                    if (name2.equals("optgroup")) {
                        if (tb.currentElement().nodeName().equals("option") && tb.aboveOnStack(tb.currentElement()) != null && tb.aboveOnStack(tb.currentElement()).nodeName().equals("optgroup")) {
                            tb.processEndTag("option");
                        }
                        if (tb.currentElement().nodeName().equals("optgroup")) {
                            tb.pop();
                            return true;
                        }
                        tb.error(this);
                        return true;
                    } else if (name2.equals("option")) {
                        if (tb.currentElement().nodeName().equals("option")) {
                            tb.pop();
                            return true;
                        }
                        tb.error(this);
                        return true;
                    } else if (!name2.equals("select")) {
                        return anythingElse(t, tb);
                    } else {
                        if (!tb.inSelectScope(name2)) {
                            tb.error(this);
                            return false;
                        }
                        tb.popStackToClose(name2);
                        tb.resetInsertionMode();
                        return true;
                    }
                case 5:
                    Token.Character c = t.asCharacter();
                    if (c.getData().equals(HtmlTreeBuilderState.nullString)) {
                        tb.error(this);
                        return false;
                    }
                    tb.insert(c);
                    return true;
                case 6:
                    if (tb.currentElement().nodeName().equals("html")) {
                        return true;
                    }
                    tb.error(this);
                    return true;
                default:
                    return anythingElse(t, tb);
            }
        }

        private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            tb.error(this);
            return false;
        }
    };
    static HtmlTreeBuilderState InSelectInTable = new HtmlTreeBuilderState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "InSelectInTable";
        }

        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (t.isStartTag() && StringUtil.m276in(t.asStartTag().name(), "caption", "table", "tbody", "tfoot", "thead", "tr", "td", "th")) {
                tb.error(this);
                tb.processEndTag("select");
                return tb.process(t);
            } else if (!t.isEndTag() || !StringUtil.m276in(t.asEndTag().name(), "caption", "table", "tbody", "tfoot", "thead", "tr", "td", "th")) {
                return tb.process(t, InSelect);
            } else {
                tb.error(this);
                if (!tb.inTableScope(t.asEndTag().name())) {
                    return false;
                }
                tb.processEndTag("select");
                return tb.process(t);
            }
        }
    };
    static HtmlTreeBuilderState InTable = new HtmlTreeBuilderState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "InTable";
        }

        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            Token token = t;
            HtmlTreeBuilder htmlTreeBuilder = tb;
            if (t.isCharacter()) {
                tb.newPendingTableCharacters();
                tb.markInsertionMode();
                htmlTreeBuilder.transition(InTableText);
                return htmlTreeBuilder.process(token);
            } else if (t.isComment()) {
                htmlTreeBuilder.insert(t.asComment());
                return true;
            } else if (t.isDoctype()) {
                htmlTreeBuilder.error(this);
                return false;
            } else if (t.isStartTag()) {
                Token.StartTag startTag = t.asStartTag();
                String name = startTag.name();
                if (name.equals("caption")) {
                    tb.clearStackToTableContext();
                    tb.insertMarkerToFormattingElements();
                    htmlTreeBuilder.insert(startTag);
                    htmlTreeBuilder.transition(InCaption);
                } else if (name.equals("colgroup")) {
                    tb.clearStackToTableContext();
                    htmlTreeBuilder.insert(startTag);
                    htmlTreeBuilder.transition(InColumnGroup);
                } else if (name.equals("col")) {
                    htmlTreeBuilder.processStartTag("colgroup");
                    return htmlTreeBuilder.process(token);
                } else if (StringUtil.m276in(name, "tbody", "tfoot", "thead")) {
                    tb.clearStackToTableContext();
                    htmlTreeBuilder.insert(startTag);
                    htmlTreeBuilder.transition(InTableBody);
                } else if (StringUtil.m276in(name, "td", "th", "tr")) {
                    htmlTreeBuilder.processStartTag("tbody");
                    return htmlTreeBuilder.process(token);
                } else if (name.equals("table")) {
                    htmlTreeBuilder.error(this);
                    if (htmlTreeBuilder.processEndTag("table")) {
                        return htmlTreeBuilder.process(token);
                    }
                } else if (StringUtil.m276in(name, "style", SvgConstants.Tags.SCRIPT)) {
                    return htmlTreeBuilder.process(token, InHead);
                } else {
                    if (name.equals("input")) {
                        if (!startTag.attributes.get(PdfConst.Type).equalsIgnoreCase("hidden")) {
                            return anythingElse(t, tb);
                        }
                        htmlTreeBuilder.insertEmpty(startTag);
                    } else if (!name.equals("form")) {
                        return anythingElse(t, tb);
                    } else {
                        htmlTreeBuilder.error(this);
                        if (tb.getFormElement() != null) {
                            return false;
                        }
                        htmlTreeBuilder.insertForm(startTag, false);
                    }
                }
                return true;
            } else if (t.isEndTag()) {
                String name2 = t.asEndTag().name();
                if (name2.equals("table")) {
                    if (!htmlTreeBuilder.inTableScope(name2)) {
                        htmlTreeBuilder.error(this);
                        return false;
                    }
                    htmlTreeBuilder.popStackToClose("table");
                    tb.resetInsertionMode();
                    return true;
                } else if (!StringUtil.m276in(name2, "body", "caption", "col", "colgroup", "html", "tbody", "td", "tfoot", "th", "thead", "tr")) {
                    return anythingElse(t, tb);
                } else {
                    htmlTreeBuilder.error(this);
                    return false;
                }
            } else if (!t.isEOF()) {
                return anythingElse(t, tb);
            } else {
                if (tb.currentElement().nodeName().equals("html")) {
                    htmlTreeBuilder.error(this);
                }
                return true;
            }
        }

        /* access modifiers changed from: package-private */
        public boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            tb.error(this);
            if (!StringUtil.m276in(tb.currentElement().nodeName(), "table", "tbody", "tfoot", "thead", "tr")) {
                return tb.process(t, InBody);
            }
            tb.setFosterInserts(true);
            boolean processed = tb.process(t, InBody);
            tb.setFosterInserts(false);
            return processed;
        }
    };
    static HtmlTreeBuilderState InTableBody = new HtmlTreeBuilderState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "InTableBody";
        }

        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            switch (C151124.f1625xd695eb0c[t.type.ordinal()]) {
                case 3:
                    Token.StartTag startTag = t.asStartTag();
                    String name = startTag.name();
                    if (name.equals("tr")) {
                        tb.clearStackToTableBodyContext();
                        tb.insert(startTag);
                        tb.transition(InRow);
                        return true;
                    } else if (StringUtil.m276in(name, "th", "td")) {
                        tb.error(this);
                        tb.processStartTag("tr");
                        return tb.process(startTag);
                    } else if (StringUtil.m276in(name, "caption", "col", "colgroup", "tbody", "tfoot", "thead")) {
                        return exitTableBody(t, tb);
                    } else {
                        return anythingElse(t, tb);
                    }
                case 4:
                    String name2 = t.asEndTag().name();
                    if (StringUtil.m276in(name2, "tbody", "tfoot", "thead")) {
                        if (!tb.inTableScope(name2)) {
                            tb.error(this);
                            return false;
                        }
                        tb.clearStackToTableBodyContext();
                        tb.pop();
                        tb.transition(InTable);
                        return true;
                    } else if (name2.equals("table")) {
                        return exitTableBody(t, tb);
                    } else {
                        if (!StringUtil.m276in(name2, "body", "caption", "col", "colgroup", "html", "td", "th", "tr")) {
                            return anythingElse(t, tb);
                        }
                        tb.error(this);
                        return false;
                    }
                default:
                    return anythingElse(t, tb);
            }
        }

        private boolean exitTableBody(Token t, HtmlTreeBuilder tb) {
            if (tb.inTableScope("tbody") || tb.inTableScope("thead") || tb.inScope("tfoot")) {
                tb.clearStackToTableBodyContext();
                tb.processEndTag(tb.currentElement().nodeName());
                return tb.process(t);
            }
            tb.error(this);
            return false;
        }

        private boolean anythingElse(Token t, HtmlTreeBuilder tb) {
            return tb.process(t, InTable);
        }
    };
    static HtmlTreeBuilderState InTableText = new HtmlTreeBuilderState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "InTableText";
        }

        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            switch (C151124.f1625xd695eb0c[t.type.ordinal()]) {
                case 5:
                    Token.Character c = t.asCharacter();
                    if (c.getData().equals(HtmlTreeBuilderState.nullString)) {
                        tb.error(this);
                        return false;
                    }
                    tb.getPendingTableCharacters().add(c.getData());
                    return true;
                default:
                    if (tb.getPendingTableCharacters().size() > 0) {
                        for (String character : tb.getPendingTableCharacters()) {
                            if (!HtmlTreeBuilderState.isWhitespace(character)) {
                                tb.error(this);
                                if (StringUtil.m276in(tb.currentElement().nodeName(), "table", "tbody", "tfoot", "thead", "tr")) {
                                    tb.setFosterInserts(true);
                                    tb.process(new Token.Character().data(character), InBody);
                                    tb.setFosterInserts(false);
                                } else {
                                    tb.process(new Token.Character().data(character), InBody);
                                }
                            } else {
                                tb.insert(new Token.Character().data(character));
                            }
                        }
                        tb.newPendingTableCharacters();
                    }
                    tb.transition(tb.originalState());
                    return tb.process(t);
            }
        }
    };
    static HtmlTreeBuilderState Initial = new HtmlTreeBuilderState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "Initial";
        }

        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (HtmlTreeBuilderState.isWhitespace(t)) {
                return true;
            }
            if (t.isComment()) {
                tb.insert(t.asComment());
            } else if (t.isDoctype()) {
                Token.Doctype d = t.asDoctype();
                tb.getDocument().appendChild(new DocumentType(d.getName(), d.getPublicIdentifier(), d.getSystemIdentifier(), tb.getBaseUri()));
                if (d.isForceQuirks()) {
                    tb.getDocument().quirksMode(Document.QuirksMode.quirks);
                }
                tb.transition(BeforeHtml);
            } else {
                tb.transition(BeforeHtml);
                return tb.process(t);
            }
            return true;
        }
    };
    static HtmlTreeBuilderState Text = new HtmlTreeBuilderState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "Text";
        }

        /* access modifiers changed from: package-private */
        public boolean process(Token t, HtmlTreeBuilder tb) {
            if (t.isCharacter()) {
                tb.insert(t.asCharacter());
                return true;
            } else if (t.isEOF()) {
                tb.error(this);
                tb.pop();
                tb.transition(tb.originalState());
                return tb.process(t);
            } else if (!t.isEndTag()) {
                return true;
            } else {
                tb.pop();
                tb.transition(tb.originalState());
                return true;
            }
        }
    };
    /* access modifiers changed from: private */
    public static String nullString = String.valueOf(0);

    /* access modifiers changed from: package-private */
    public abstract String getName();

    /* access modifiers changed from: package-private */
    public abstract boolean process(Token token, HtmlTreeBuilder htmlTreeBuilder);

    HtmlTreeBuilderState() {
    }

    /* renamed from: com.itextpdf.styledxmlparser.jsoup.parser.HtmlTreeBuilderState$24 */
    static /* synthetic */ class C151124 {

        /* renamed from: $SwitchMap$com$itextpdf$styledxmlparser$jsoup$parser$Token$TokenType */
        static final /* synthetic */ int[] f1625xd695eb0c;

        static {
            int[] iArr = new int[Token.TokenType.values().length];
            f1625xd695eb0c = iArr;
            try {
                iArr[Token.TokenType.Comment.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1625xd695eb0c[Token.TokenType.Doctype.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1625xd695eb0c[Token.TokenType.StartTag.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1625xd695eb0c[Token.TokenType.EndTag.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1625xd695eb0c[Token.TokenType.Character.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1625xd695eb0c[Token.TokenType.EOF.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    public String toString() {
        return getName();
    }

    /* access modifiers changed from: private */
    public static boolean isWhitespace(Token t) {
        if (t.isCharacter()) {
            return isWhitespace(t.asCharacter().getData());
        }
        return false;
    }

    /* access modifiers changed from: private */
    public static boolean isWhitespace(String data) {
        for (int i = 0; i < data.length(); i++) {
            if (!StringUtil.isWhitespace(data.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public static void handleRcData(Token.StartTag startTag, HtmlTreeBuilder tb) {
        tb.insert(startTag);
        tb.tokeniser.transition(TokeniserState.Rcdata);
        tb.markInsertionMode();
        tb.transition(Text);
    }

    /* access modifiers changed from: private */
    public static void handleRawtext(Token.StartTag startTag, HtmlTreeBuilder tb) {
        tb.insert(startTag);
        tb.tokeniser.transition(TokeniserState.Rawtext);
        tb.markInsertionMode();
        tb.transition(Text);
    }

    private static final class Constants {
        static final String[] DdDt = {"dd", "dt"};
        static final String[] Formatters = {SvgConstants.Attributes.PATH_DATA_REL_BEARING, "big", "code", CommonCssConstants.f1611EM, "font", "i", SvgConstants.Attributes.PATH_DATA_REL_CURVE_TO_S, CommonCssConstants.SMALL, "strike", "strong", "tt", "u"};
        static final String[] Headings = {"h1", "h2", "h3", "h4", "h5", "h6"};
        static final String[] InBodyEndAdoptionFormatters = {"a", SvgConstants.Attributes.PATH_DATA_REL_BEARING, "big", "code", CommonCssConstants.f1611EM, "font", "i", "nobr", SvgConstants.Attributes.PATH_DATA_REL_CURVE_TO_S, CommonCssConstants.SMALL, "strike", "strong", "tt", "u"};
        static final String[] InBodyEndClosers = {"address", "article", "aside", "blockquote", "button", CommonCssConstants.CENTER, ErrorBundle.DETAIL_ENTRY, "dir", "div", "dl", "fieldset", "figcaption", "figure", "footer", "header", "hgroup", "listing", CommonCssConstants.MENU, "nav", "ol", "pre", "section", ErrorBundle.SUMMARY_ENTRY, "ul"};
        static final String[] InBodyEndTableFosters = {"table", "tbody", "tfoot", "thead", "tr"};
        static final String[] InBodyStartApplets = {"applet", "marquee", "object"};
        static final String[] InBodyStartDrop = {"caption", "col", "colgroup", TypedValues.AttributesType.S_FRAME, XfdfConstants.HEAD, "tbody", "td", "tfoot", "th", "thead", "tr"};
        static final String[] InBodyStartEmptyFormatters = {"area", "br", "embed", "img", "keygen", "wbr"};
        static final String[] InBodyStartInputAttribs = {XfdfConstants.NAME, "action", "prompt"};
        static final String[] InBodyStartLiBreakers = {"address", "div", "p"};
        static final String[] InBodyStartMedia = {"param", PdfConst.Source, "track"};
        static final String[] InBodyStartOptions = {"optgroup", "option"};
        static final String[] InBodyStartPClosers = {"address", "article", "aside", "blockquote", CommonCssConstants.CENTER, ErrorBundle.DETAIL_ENTRY, "dir", "div", "dl", "fieldset", "figcaption", "figure", "footer", "header", "hgroup", CommonCssConstants.MENU, "nav", "ol", "p", "section", ErrorBundle.SUMMARY_ENTRY, "ul"};
        static final String[] InBodyStartPreListing = {"pre", "listing"};
        static final String[] InBodyStartRuby = {"rp", "rt"};
        static final String[] InBodyStartToHead = {"base", "basefont", "bgsound", "command", "link", "meta", "noframes", SvgConstants.Tags.SCRIPT, "style", "title"};

        private Constants() {
        }
    }
}
