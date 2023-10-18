package com.itextpdf.styledxmlparser.jsoup.parser;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.itextpdf.forms.xfdf.XfdfConstants;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.jsoup.helper.StringUtil;
import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
import com.itextpdf.styledxmlparser.jsoup.nodes.Comment;
import com.itextpdf.styledxmlparser.jsoup.nodes.DataNode;
import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import com.itextpdf.styledxmlparser.jsoup.nodes.Element;
import com.itextpdf.styledxmlparser.jsoup.nodes.FormElement;
import com.itextpdf.styledxmlparser.jsoup.nodes.Node;
import com.itextpdf.styledxmlparser.jsoup.nodes.TextNode;
import com.itextpdf.styledxmlparser.jsoup.parser.Token;
import com.itextpdf.styledxmlparser.jsoup.select.Elements;
import com.itextpdf.svg.SvgConstants;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bouncycastle.i18n.ErrorBundle;

public class HtmlTreeBuilder extends TreeBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String[] TagSearchButton = {"button"};
    private static final String[] TagSearchEndTags = {"dd", "dt", "li", "option", "optgroup", "p", "rp", "rt"};
    private static final String[] TagSearchList = {"ol", "ul"};
    private static final String[] TagSearchSelectScope = {"optgroup", "option"};
    private static final String[] TagSearchSpecial = {"address", "applet", "area", "article", "aside", "base", "basefont", "bgsound", "blockquote", "body", "br", "button", "caption", CommonCssConstants.CENTER, "col", "colgroup", "command", "dd", ErrorBundle.DETAIL_ENTRY, "dir", "div", "dl", "dt", "embed", "fieldset", "figcaption", "figure", "footer", "form", TypedValues.AttributesType.S_FRAME, "frameset", "h1", "h2", "h3", "h4", "h5", "h6", XfdfConstants.HEAD, "header", "hgroup", "hr", "html", "iframe", "img", "input", "isindex", "li", "link", "listing", "marquee", CommonCssConstants.MENU, "meta", "nav", "noembed", "noframes", "noscript", "object", "ol", "p", "param", "plaintext", "pre", SvgConstants.Tags.SCRIPT, "section", "select", "style", ErrorBundle.SUMMARY_ENTRY, "table", "tbody", "td", "textarea", "tfoot", "th", "thead", "title", "tr", "ul", "wbr", "xmp"};
    private static final String[] TagSearchTableScope = {"html", "table"};
    public static final String[] TagsSearchInScope = {"applet", "caption", "html", "table", "td", "th", "marquee", "object"};
    private boolean baseUriSetFromDoc = false;
    private Element contextElement;
    private Token.EndTag emptyEnd = new Token.EndTag();
    private FormElement formElement;
    private ArrayList<Element> formattingElements = new ArrayList<>();
    private boolean fosterInserts = false;
    private boolean fragmentParsing = false;
    private boolean framesetOk = true;
    private Element headElement;
    private HtmlTreeBuilderState originalState;
    private List<String> pendingTableCharacters = new ArrayList();
    private String[] specificScopeTarget = {null};
    private HtmlTreeBuilderState state;

    HtmlTreeBuilder() {
    }

    /* access modifiers changed from: package-private */
    public Document parse(String input, String baseUri, ParseErrorList errors) {
        this.state = HtmlTreeBuilderState.Initial;
        this.baseUriSetFromDoc = false;
        return super.parse(input, baseUri, errors);
    }

    /* access modifiers changed from: package-private */
    public List<Node> parseFragment(String inputFragment, Element context, String baseUri, ParseErrorList errors) {
        this.state = HtmlTreeBuilderState.Initial;
        initialiseParse(inputFragment, baseUri, errors);
        this.contextElement = context;
        this.fragmentParsing = true;
        Element root = null;
        if (context != null) {
            if (context.ownerDocument() != null) {
                this.doc.quirksMode(context.ownerDocument().quirksMode());
            }
            String contextTag = context.tagName();
            if (StringUtil.m276in(contextTag, "title", "textarea")) {
                this.tokeniser.transition(TokeniserState.Rcdata);
            } else if (StringUtil.m276in(contextTag, "iframe", "noembed", "noframes", "style", "xmp")) {
                this.tokeniser.transition(TokeniserState.Rawtext);
            } else if (contextTag.equals(SvgConstants.Tags.SCRIPT)) {
                this.tokeniser.transition(TokeniserState.ScriptData);
            } else if (contextTag.equals("noscript")) {
                this.tokeniser.transition(TokeniserState.Data);
            } else if (contextTag.equals("plaintext")) {
                this.tokeniser.transition(TokeniserState.Data);
            } else {
                this.tokeniser.transition(TokeniserState.Data);
            }
            root = new Element(Tag.valueOf("html"), baseUri);
            this.doc.appendChild(root);
            this.stack.add(root);
            resetInsertionMode();
            Elements contextChain = context.parents();
            contextChain.add(0, context);
            Iterator it = contextChain.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Element parent = (Element) it.next();
                if (parent instanceof FormElement) {
                    this.formElement = (FormElement) parent;
                    break;
                }
            }
        }
        runParser();
        if (context == null || root == null) {
            return this.doc.childNodes();
        }
        return root.childNodes();
    }

    /* access modifiers changed from: protected */
    public boolean process(Token token) {
        this.currentToken = token;
        return this.state.process(token, this);
    }

    /* access modifiers changed from: package-private */
    public boolean process(Token token, HtmlTreeBuilderState state2) {
        this.currentToken = token;
        return state2.process(token, this);
    }

    /* access modifiers changed from: package-private */
    public void transition(HtmlTreeBuilderState state2) {
        this.state = state2;
    }

    /* access modifiers changed from: package-private */
    public HtmlTreeBuilderState state() {
        return this.state;
    }

    /* access modifiers changed from: package-private */
    public void markInsertionMode() {
        this.originalState = this.state;
    }

    /* access modifiers changed from: package-private */
    public HtmlTreeBuilderState originalState() {
        return this.originalState;
    }

    /* access modifiers changed from: package-private */
    public void framesetOk(boolean framesetOk2) {
        this.framesetOk = framesetOk2;
    }

    /* access modifiers changed from: package-private */
    public boolean framesetOk() {
        return this.framesetOk;
    }

    /* access modifiers changed from: package-private */
    public Document getDocument() {
        return this.doc;
    }

    /* access modifiers changed from: package-private */
    public String getBaseUri() {
        return this.baseUri;
    }

    /* access modifiers changed from: package-private */
    public void maybeSetBaseUri(Element base) {
        if (!this.baseUriSetFromDoc) {
            String href = base.absUrl("href");
            if (href.length() != 0) {
                this.baseUri = href;
                this.baseUriSetFromDoc = true;
                this.doc.setBaseUri(href);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isFragmentParsing() {
        return this.fragmentParsing;
    }

    /* access modifiers changed from: package-private */
    public void error(HtmlTreeBuilderState state2) {
        if (this.errors.canAddError()) {
            this.errors.add(new ParseError(this.reader.pos(), "Unexpected token [{0}] when in state [{1}]", this.currentToken.tokenType(), state2));
        }
    }

    /* access modifiers changed from: package-private */
    public Element insert(Token.StartTag startTag) {
        if (startTag.isSelfClosing()) {
            Element el = insertEmpty(startTag);
            this.stack.add(el);
            this.tokeniser.transition(TokeniserState.Data);
            this.tokeniser.emit((Token) ((Token.Tag) this.emptyEnd.reset()).name(el.tagName()));
            return el;
        }
        Element el2 = new Element(Tag.valueOf(startTag.name()), this.baseUri, startTag.attributes);
        insert(el2);
        return el2;
    }

    /* access modifiers changed from: package-private */
    public Element insertStartTag(String startTagName) {
        Element el = new Element(Tag.valueOf(startTagName), this.baseUri);
        insert(el);
        return el;
    }

    /* access modifiers changed from: package-private */
    public void insert(Element el) {
        insertNode(el);
        this.stack.add(el);
    }

    /* access modifiers changed from: package-private */
    public Element insertEmpty(Token.StartTag startTag) {
        Tag tag = Tag.valueOf(startTag.name());
        Element el = new Element(tag, this.baseUri, startTag.attributes);
        insertNode(el);
        if (startTag.isSelfClosing()) {
            if (!tag.isKnownTag()) {
                tag.setSelfClosing();
                this.tokeniser.acknowledgeSelfClosingFlag();
            } else if (tag.isSelfClosing()) {
                this.tokeniser.acknowledgeSelfClosingFlag();
            }
        }
        return el;
    }

    /* access modifiers changed from: package-private */
    public FormElement insertForm(Token.StartTag startTag, boolean onStack) {
        FormElement el = new FormElement(Tag.valueOf(startTag.name()), this.baseUri, startTag.attributes);
        setFormElement(el);
        insertNode(el);
        if (onStack) {
            this.stack.add(el);
        }
        return el;
    }

    /* access modifiers changed from: package-private */
    public void insert(Token.Comment commentToken) {
        insertNode(new Comment(commentToken.getData(), this.baseUri));
    }

    /* access modifiers changed from: package-private */
    public void insert(Token.Character characterToken) {
        Node node;
        String tagName = currentElement().tagName();
        if (tagName.equals(SvgConstants.Tags.SCRIPT) || tagName.equals("style")) {
            node = new DataNode(characterToken.getData(), this.baseUri);
        } else {
            node = new TextNode(characterToken.getData(), this.baseUri);
        }
        currentElement().appendChild(node);
    }

    private void insertNode(Node node) {
        FormElement formElement2;
        if (this.stack.size() == 0) {
            this.doc.appendChild(node);
        } else if (isFosterInserts()) {
            insertInFosterParent(node);
        } else {
            currentElement().appendChild(node);
        }
        if ((node instanceof Element) && ((Element) node).tag().isFormListed() && (formElement2 = this.formElement) != null) {
            formElement2.addElement((Element) node);
        }
    }

    /* access modifiers changed from: package-private */
    public Element pop() {
        return (Element) this.stack.remove(this.stack.size() - 1);
    }

    /* access modifiers changed from: package-private */
    public void push(Element element) {
        this.stack.add(element);
    }

    /* access modifiers changed from: package-private */
    public ArrayList<Element> getStack() {
        return this.stack;
    }

    /* access modifiers changed from: package-private */
    public boolean onStack(Element el) {
        return isElementInQueue(this.stack, el);
    }

    private boolean isElementInQueue(ArrayList<Element> queue, Element element) {
        for (int pos = queue.size() - 1; pos >= 0; pos--) {
            if (queue.get(pos) == element) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public Element getFromStack(String elName) {
        for (int pos = this.stack.size() - 1; pos >= 0; pos--) {
            Element next = (Element) this.stack.get(pos);
            if (next.nodeName().equals(elName)) {
                return next;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public boolean removeFromStack(Element el) {
        for (int pos = this.stack.size() - 1; pos >= 0; pos--) {
            if (((Element) this.stack.get(pos)) == el) {
                this.stack.remove(pos);
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public void popStackToClose(String elName) {
        int pos = this.stack.size() - 1;
        while (pos >= 0) {
            this.stack.remove(pos);
            if (!((Element) this.stack.get(pos)).nodeName().equals(elName)) {
                pos--;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void popStackToClose(String... elNames) {
        int pos = this.stack.size() - 1;
        while (pos >= 0) {
            this.stack.remove(pos);
            if (!StringUtil.m276in(((Element) this.stack.get(pos)).nodeName(), elNames)) {
                pos--;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void popStackToBefore(String elName) {
        int pos = this.stack.size() - 1;
        while (pos >= 0 && !((Element) this.stack.get(pos)).nodeName().equals(elName)) {
            this.stack.remove(pos);
            pos--;
        }
    }

    /* access modifiers changed from: package-private */
    public void clearStackToTableContext() {
        clearStackToContext("table");
    }

    /* access modifiers changed from: package-private */
    public void clearStackToTableBodyContext() {
        clearStackToContext("tbody", "tfoot", "thead");
    }

    /* access modifiers changed from: package-private */
    public void clearStackToTableRowContext() {
        clearStackToContext("tr");
    }

    private void clearStackToContext(String... nodeNames) {
        int pos = this.stack.size() - 1;
        while (pos >= 0) {
            Element next = (Element) this.stack.get(pos);
            if (!StringUtil.m276in(next.nodeName(), nodeNames) && !next.nodeName().equals("html")) {
                this.stack.remove(pos);
                pos--;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public Element aboveOnStack(Element el) {
        if (onStack(el)) {
            for (int pos = this.stack.size() - 1; pos >= 0; pos--) {
                if (((Element) this.stack.get(pos)) == el) {
                    return (Element) this.stack.get(pos - 1);
                }
            }
            return null;
        }
        throw new AssertionError();
    }

    /* access modifiers changed from: package-private */
    public void insertOnStackAfter(Element after, Element in) {
        int i = this.stack.lastIndexOf(after);
        Validate.isTrue(i != -1);
        this.stack.add(i + 1, in);
    }

    /* access modifiers changed from: package-private */
    public void replaceOnStack(Element out, Element in) {
        replaceInQueue(this.stack, out, in);
    }

    private void replaceInQueue(ArrayList<Element> queue, Element out, Element in) {
        int i = queue.lastIndexOf(out);
        Validate.isTrue(i != -1);
        queue.set(i, in);
    }

    /* access modifiers changed from: package-private */
    public void resetInsertionMode() {
        boolean last = false;
        int pos = this.stack.size() - 1;
        while (pos >= 0) {
            Element node = (Element) this.stack.get(pos);
            if (pos == 0) {
                last = true;
                node = this.contextElement;
            }
            String name = node.nodeName();
            if ("select".equals(name)) {
                transition(HtmlTreeBuilderState.InSelect);
                return;
            } else if ("td".equals(name) || ("th".equals(name) && !last)) {
                transition(HtmlTreeBuilderState.InCell);
                return;
            } else if ("tr".equals(name)) {
                transition(HtmlTreeBuilderState.InRow);
                return;
            } else if ("tbody".equals(name) || "thead".equals(name) || "tfoot".equals(name)) {
                transition(HtmlTreeBuilderState.InTableBody);
                return;
            } else if ("caption".equals(name)) {
                transition(HtmlTreeBuilderState.InCaption);
                return;
            } else if ("colgroup".equals(name)) {
                transition(HtmlTreeBuilderState.InColumnGroup);
                return;
            } else if ("table".equals(name)) {
                transition(HtmlTreeBuilderState.InTable);
                return;
            } else if (XfdfConstants.HEAD.equals(name)) {
                transition(HtmlTreeBuilderState.InBody);
                return;
            } else if ("body".equals(name)) {
                transition(HtmlTreeBuilderState.InBody);
                return;
            } else if ("frameset".equals(name)) {
                transition(HtmlTreeBuilderState.InFrameset);
                return;
            } else if ("html".equals(name)) {
                transition(HtmlTreeBuilderState.BeforeHead);
                return;
            } else if (last) {
                transition(HtmlTreeBuilderState.InBody);
                return;
            } else {
                pos--;
            }
        }
    }

    private boolean inSpecificScope(String targetName, String[] baseTypes, String[] extraTypes) {
        String[] strArr = this.specificScopeTarget;
        strArr[0] = targetName;
        return inSpecificScope(strArr, baseTypes, extraTypes);
    }

    private boolean inSpecificScope(String[] targetNames, String[] baseTypes, String[] extraTypes) {
        for (int pos = this.stack.size() - 1; pos >= 0; pos--) {
            String elName = ((Element) this.stack.get(pos)).nodeName();
            if (StringUtil.m276in(elName, targetNames)) {
                return true;
            }
            if (StringUtil.m276in(elName, baseTypes)) {
                return false;
            }
            if (extraTypes != null && StringUtil.m276in(elName, extraTypes)) {
                return false;
            }
        }
        Validate.fail("Should not be reachable");
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean inScope(String[] targetNames) {
        return inSpecificScope(targetNames, TagsSearchInScope, (String[]) null);
    }

    /* access modifiers changed from: package-private */
    public boolean inScope(String targetName) {
        return inScope(targetName, (String[]) null);
    }

    /* access modifiers changed from: package-private */
    public boolean inScope(String targetName, String[] extras) {
        return inSpecificScope(targetName, TagsSearchInScope, extras);
    }

    /* access modifiers changed from: package-private */
    public boolean inListItemScope(String targetName) {
        return inScope(targetName, TagSearchList);
    }

    /* access modifiers changed from: package-private */
    public boolean inButtonScope(String targetName) {
        return inScope(targetName, TagSearchButton);
    }

    /* access modifiers changed from: package-private */
    public boolean inTableScope(String targetName) {
        return inSpecificScope(targetName, TagSearchTableScope, (String[]) null);
    }

    /* access modifiers changed from: package-private */
    public boolean inSelectScope(String targetName) {
        for (int pos = this.stack.size() - 1; pos >= 0; pos--) {
            String elName = ((Element) this.stack.get(pos)).nodeName();
            if (elName.equals(targetName)) {
                return true;
            }
            if (!StringUtil.m276in(elName, TagSearchSelectScope)) {
                return false;
            }
        }
        Validate.fail("Should not be reachable");
        return false;
    }

    /* access modifiers changed from: package-private */
    public void setHeadElement(Element headElement2) {
        this.headElement = headElement2;
    }

    /* access modifiers changed from: package-private */
    public Element getHeadElement() {
        return this.headElement;
    }

    /* access modifiers changed from: package-private */
    public boolean isFosterInserts() {
        return this.fosterInserts;
    }

    /* access modifiers changed from: package-private */
    public void setFosterInserts(boolean fosterInserts2) {
        this.fosterInserts = fosterInserts2;
    }

    /* access modifiers changed from: package-private */
    public FormElement getFormElement() {
        return this.formElement;
    }

    /* access modifiers changed from: package-private */
    public void setFormElement(FormElement formElement2) {
        this.formElement = formElement2;
    }

    /* access modifiers changed from: package-private */
    public void newPendingTableCharacters() {
        this.pendingTableCharacters = new ArrayList();
    }

    /* access modifiers changed from: package-private */
    public List<String> getPendingTableCharacters() {
        return this.pendingTableCharacters;
    }

    /* access modifiers changed from: package-private */
    public void setPendingTableCharacters(List<String> pendingTableCharacters2) {
        this.pendingTableCharacters = pendingTableCharacters2;
    }

    /* access modifiers changed from: package-private */
    public void generateImpliedEndTags(String excludeTag) {
        while (excludeTag != null && !currentElement().nodeName().equals(excludeTag) && StringUtil.m276in(currentElement().nodeName(), TagSearchEndTags)) {
            pop();
        }
    }

    /* access modifiers changed from: package-private */
    public void generateImpliedEndTags() {
        generateImpliedEndTags((String) null);
    }

    /* access modifiers changed from: package-private */
    public boolean isSpecial(Element el) {
        return StringUtil.m276in(el.nodeName(), TagSearchSpecial);
    }

    /* access modifiers changed from: package-private */
    public Element lastFormattingElement() {
        if (this.formattingElements.size() <= 0) {
            return null;
        }
        ArrayList<Element> arrayList = this.formattingElements;
        return arrayList.get(arrayList.size() - 1);
    }

    /* access modifiers changed from: package-private */
    public Element removeLastFormattingElement() {
        int size = this.formattingElements.size();
        if (size > 0) {
            return this.formattingElements.remove(size - 1);
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void pushActiveFormattingElements(Element in) {
        int numSeen = 0;
        int pos = this.formattingElements.size() - 1;
        while (true) {
            if (pos >= 0) {
                Element el = this.formattingElements.get(pos);
                if (el == null) {
                    break;
                }
                if (isSameFormattingElement(in, el)) {
                    numSeen++;
                }
                if (numSeen == 3) {
                    this.formattingElements.remove(pos);
                    break;
                }
                pos--;
            } else {
                break;
            }
        }
        this.formattingElements.add(in);
    }

    private boolean isSameFormattingElement(Element a, Element b) {
        return a.nodeName().equals(b.nodeName()) && a.attributes().equals(b.attributes());
    }

    /* access modifiers changed from: package-private */
    public void reconstructFormattingElements() {
        Element last = lastFormattingElement();
        if (last != null && !onStack(last)) {
            Element entry = last;
            int size = this.formattingElements.size();
            int pos = size - 1;
            boolean skip = false;
            while (true) {
                if (pos == 0) {
                    skip = true;
                    break;
                }
                pos--;
                entry = this.formattingElements.get(pos);
                if (entry != null) {
                    if (onStack(entry)) {
                        break;
                    }
                } else {
                    break;
                }
            }
            do {
                if (!skip) {
                    pos++;
                    entry = this.formattingElements.get(pos);
                }
                Validate.notNull(entry);
                skip = false;
                Element newEl = insertStartTag(entry.nodeName());
                newEl.attributes().addAll(entry.attributes());
                this.formattingElements.set(pos, newEl);
            } while (pos != size - 1);
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:1:0x0001 A[LOOP:0: B:1:0x0001->B:4:0x000d, LOOP_START] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void clearFormattingElementsToLastMarker() {
        /*
            r1 = this;
        L_0x0001:
            java.util.ArrayList<com.itextpdf.styledxmlparser.jsoup.nodes.Element> r0 = r1.formattingElements
            boolean r0 = r0.isEmpty()
            if (r0 != 0) goto L_0x0011
            com.itextpdf.styledxmlparser.jsoup.nodes.Element r0 = r1.removeLastFormattingElement()
            if (r0 != 0) goto L_0x0010
            goto L_0x0011
        L_0x0010:
            goto L_0x0001
        L_0x0011:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.styledxmlparser.jsoup.parser.HtmlTreeBuilder.clearFormattingElementsToLastMarker():void");
    }

    /* access modifiers changed from: package-private */
    public void removeFromActiveFormattingElements(Element el) {
        for (int pos = this.formattingElements.size() - 1; pos >= 0; pos--) {
            if (this.formattingElements.get(pos) == el) {
                this.formattingElements.remove(pos);
                return;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isInActiveFormattingElements(Element el) {
        return isElementInQueue(this.formattingElements, el);
    }

    /* access modifiers changed from: package-private */
    public Element getActiveFormattingElement(String nodeName) {
        for (int pos = this.formattingElements.size() - 1; pos >= 0; pos--) {
            Element next = this.formattingElements.get(pos);
            if (next == null) {
                return null;
            }
            if (next.nodeName().equals(nodeName)) {
                return next;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void replaceActiveFormattingElement(Element out, Element in) {
        replaceInQueue(this.formattingElements, out, in);
    }

    /* access modifiers changed from: package-private */
    public void insertMarkerToFormattingElements() {
        this.formattingElements.add((Object) null);
    }

    /* access modifiers changed from: package-private */
    public void insertInFosterParent(Node in) {
        Element fosterParent;
        Element lastTable = getFromStack("table");
        boolean isLastTableParent = false;
        if (lastTable == null) {
            fosterParent = (Element) this.stack.get(0);
        } else if (lastTable.parent() != null) {
            fosterParent = (Element) lastTable.parent();
            isLastTableParent = true;
        } else {
            fosterParent = aboveOnStack(lastTable);
        }
        if (isLastTableParent) {
            Validate.notNull(lastTable);
            lastTable.before(in);
            return;
        }
        fosterParent.appendChild(in);
    }

    public String toString() {
        return "TreeBuilder{currentToken=" + this.currentToken + ", state=" + this.state + ", currentElement=" + currentElement() + '}';
    }
}
