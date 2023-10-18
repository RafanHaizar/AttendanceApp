package com.itextpdf.styledxmlparser.jsoup.parser;

import com.itextpdf.styledxmlparser.jsoup.helper.Validate;
import com.itextpdf.styledxmlparser.jsoup.nodes.Attribute;
import com.itextpdf.styledxmlparser.jsoup.nodes.Attributes;
import com.itextpdf.styledxmlparser.jsoup.nodes.BooleanAttribute;

abstract class Token {
    TokenType type;

    enum TokenType {
        Doctype,
        StartTag,
        EndTag,
        Comment,
        Character,
        EOF
    }

    /* access modifiers changed from: package-private */
    public abstract Token reset();

    private Token() {
    }

    /* access modifiers changed from: package-private */
    public String tokenType() {
        return getClass().getSimpleName();
    }

    static void reset(StringBuilder sb) {
        if (sb != null) {
            sb.delete(0, sb.length());
        }
    }

    static final class Doctype extends Token {
        boolean forceQuirks = false;
        final StringBuilder name = new StringBuilder();
        final StringBuilder publicIdentifier = new StringBuilder();
        final StringBuilder systemIdentifier = new StringBuilder();

        Doctype() {
            super();
            this.type = TokenType.Doctype;
        }

        /* access modifiers changed from: package-private */
        public Token reset() {
            reset(this.name);
            reset(this.publicIdentifier);
            reset(this.systemIdentifier);
            this.forceQuirks = false;
            return this;
        }

        /* access modifiers changed from: package-private */
        public String getName() {
            return this.name.toString();
        }

        /* access modifiers changed from: package-private */
        public String getPublicIdentifier() {
            return this.publicIdentifier.toString();
        }

        public String getSystemIdentifier() {
            return this.systemIdentifier.toString();
        }

        public boolean isForceQuirks() {
            return this.forceQuirks;
        }
    }

    static abstract class Tag extends Token {
        Attributes attributes;
        private boolean hasEmptyAttributeValue = false;
        private boolean hasPendingAttributeValue = false;
        private String pendingAttributeName;
        private StringBuilder pendingAttributeValue = new StringBuilder();
        private String pendingAttributeValueS;
        boolean selfClosing = false;
        protected String tagName;

        Tag() {
            super();
        }

        /* access modifiers changed from: package-private */
        public Token reset() {
            this.tagName = null;
            this.pendingAttributeName = null;
            reset(this.pendingAttributeValue);
            this.pendingAttributeValueS = null;
            this.hasEmptyAttributeValue = false;
            this.hasPendingAttributeValue = false;
            this.selfClosing = false;
            this.attributes = null;
            return this;
        }

        /* access modifiers changed from: package-private */
        public final void newAttribute() {
            Attribute attribute;
            if (this.attributes == null) {
                this.attributes = new Attributes();
            }
            if (this.pendingAttributeName != null) {
                if (this.hasPendingAttributeValue) {
                    attribute = new Attribute(this.pendingAttributeName, this.pendingAttributeValue.length() > 0 ? this.pendingAttributeValue.toString() : this.pendingAttributeValueS);
                } else if (this.hasEmptyAttributeValue) {
                    attribute = new Attribute(this.pendingAttributeName, "");
                } else {
                    attribute = new BooleanAttribute(this.pendingAttributeName);
                }
                this.attributes.put(attribute);
            }
            this.pendingAttributeName = null;
            this.hasEmptyAttributeValue = false;
            this.hasPendingAttributeValue = false;
            reset(this.pendingAttributeValue);
            this.pendingAttributeValueS = null;
        }

        /* access modifiers changed from: package-private */
        public final void finaliseTag() {
            if (this.pendingAttributeName != null) {
                newAttribute();
            }
        }

        /* access modifiers changed from: package-private */
        public final String name() {
            String str = this.tagName;
            Validate.isFalse(str == null || str.length() == 0);
            return this.tagName;
        }

        /* access modifiers changed from: package-private */
        public final Tag name(String name) {
            this.tagName = name;
            return this;
        }

        /* access modifiers changed from: package-private */
        public final boolean isSelfClosing() {
            return this.selfClosing;
        }

        /* access modifiers changed from: package-private */
        public final Attributes getAttributes() {
            return this.attributes;
        }

        /* access modifiers changed from: package-private */
        public final void appendTagName(String append) {
            this.tagName = this.tagName == null ? append : this.tagName + append;
        }

        /* access modifiers changed from: package-private */
        public final void appendTagName(char append) {
            appendTagName(String.valueOf(append));
        }

        /* access modifiers changed from: package-private */
        public final void appendAttributeName(String append) {
            this.pendingAttributeName = this.pendingAttributeName == null ? append : this.pendingAttributeName + append;
        }

        /* access modifiers changed from: package-private */
        public final void appendAttributeName(char append) {
            appendAttributeName(String.valueOf(append));
        }

        /* access modifiers changed from: package-private */
        public final void appendAttributeValue(String append) {
            ensureAttributeValue();
            if (this.pendingAttributeValue.length() == 0) {
                this.pendingAttributeValueS = append;
            } else {
                this.pendingAttributeValue.append(append);
            }
        }

        /* access modifiers changed from: package-private */
        public final void appendAttributeValue(char append) {
            ensureAttributeValue();
            this.pendingAttributeValue.append(append);
        }

        /* access modifiers changed from: package-private */
        public final void appendAttributeValue(char[] append) {
            ensureAttributeValue();
            this.pendingAttributeValue.append(append);
        }

        /* access modifiers changed from: package-private */
        public final void setEmptyAttributeValue() {
            this.hasEmptyAttributeValue = true;
        }

        private void ensureAttributeValue() {
            this.hasPendingAttributeValue = true;
            String str = this.pendingAttributeValueS;
            if (str != null) {
                this.pendingAttributeValue.append(str);
                this.pendingAttributeValueS = null;
            }
        }
    }

    static final class StartTag extends Tag {
        StartTag() {
            this.attributes = new Attributes();
            this.type = TokenType.StartTag;
        }

        /* access modifiers changed from: package-private */
        public Token reset() {
            super.reset();
            this.attributes = new Attributes();
            return this;
        }

        /* access modifiers changed from: package-private */
        public StartTag nameAttr(String name, Attributes attributes) {
            this.tagName = name;
            this.attributes = attributes;
            return this;
        }

        public String toString() {
            if (this.attributes == null || this.attributes.size() <= 0) {
                return "<" + name() + ">";
            }
            return "<" + name() + " " + this.attributes.toString() + ">";
        }
    }

    static final class EndTag extends Tag {
        EndTag() {
            this.type = TokenType.EndTag;
        }

        public String toString() {
            return "</" + name() + ">";
        }
    }

    static final class Comment extends Token {
        boolean bogus = false;
        final StringBuilder data = new StringBuilder();

        /* access modifiers changed from: package-private */
        public Token reset() {
            reset(this.data);
            this.bogus = false;
            return this;
        }

        Comment() {
            super();
            this.type = TokenType.Comment;
        }

        /* access modifiers changed from: package-private */
        public String getData() {
            return this.data.toString();
        }

        public String toString() {
            return "<!--" + getData() + "-->";
        }
    }

    static final class Character extends Token {
        private String data;

        Character() {
            super();
            this.type = TokenType.Character;
        }

        /* access modifiers changed from: package-private */
        public Token reset() {
            this.data = null;
            return this;
        }

        /* access modifiers changed from: package-private */
        public Character data(String data2) {
            this.data = data2;
            return this;
        }

        /* access modifiers changed from: package-private */
        public String getData() {
            return this.data;
        }

        public String toString() {
            return getData();
        }
    }

    static final class EOF extends Token {
        EOF() {
            super();
            this.type = TokenType.EOF;
        }

        /* access modifiers changed from: package-private */
        public Token reset() {
            return this;
        }
    }

    /* access modifiers changed from: package-private */
    public final boolean isDoctype() {
        return this.type == TokenType.Doctype;
    }

    /* access modifiers changed from: package-private */
    public final Doctype asDoctype() {
        return (Doctype) this;
    }

    /* access modifiers changed from: package-private */
    public final boolean isStartTag() {
        return this.type == TokenType.StartTag;
    }

    /* access modifiers changed from: package-private */
    public final StartTag asStartTag() {
        return (StartTag) this;
    }

    /* access modifiers changed from: package-private */
    public final boolean isEndTag() {
        return this.type == TokenType.EndTag;
    }

    /* access modifiers changed from: package-private */
    public final EndTag asEndTag() {
        return (EndTag) this;
    }

    /* access modifiers changed from: package-private */
    public final boolean isComment() {
        return this.type == TokenType.Comment;
    }

    /* access modifiers changed from: package-private */
    public final Comment asComment() {
        return (Comment) this;
    }

    /* access modifiers changed from: package-private */
    public final boolean isCharacter() {
        return this.type == TokenType.Character;
    }

    /* access modifiers changed from: package-private */
    public final Character asCharacter() {
        return (Character) this;
    }

    /* access modifiers changed from: package-private */
    public final boolean isEOF() {
        return this.type == TokenType.EOF;
    }
}
