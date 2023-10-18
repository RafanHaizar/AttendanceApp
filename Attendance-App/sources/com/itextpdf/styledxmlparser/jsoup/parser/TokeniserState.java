package com.itextpdf.styledxmlparser.jsoup.parser;

import com.itextpdf.styledxmlparser.jsoup.parser.Token;
import com.itextpdf.svg.SvgConstants;
import java.util.Arrays;
import kotlin.text.Typography;

abstract class TokeniserState {
    static TokeniserState AfterAttributeName = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "AfterAttributeName";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            char c = r.consume();
            switch (c) {
                case 0:
                    t.error((TokeniserState) this);
                    t.tagPending.appendAttributeName((char) TokeniserState.replacementChar);
                    t.transition(AttributeName);
                    return;
                case 9:
                case 10:
                case 12:
                case 13:
                case ' ':
                    return;
                case '\"':
                case '\'':
                case '<':
                    t.error((TokeniserState) this);
                    t.tagPending.newAttribute();
                    t.tagPending.appendAttributeName(c);
                    t.transition(AttributeName);
                    return;
                case '/':
                    t.transition(SelfClosingStartTag);
                    return;
                case '=':
                    t.transition(BeforeAttributeValue);
                    return;
                case '>':
                    t.emitTagPending();
                    t.transition(Data);
                    return;
                case 65535:
                    t.eofError(this);
                    t.transition(Data);
                    return;
                default:
                    t.tagPending.newAttribute();
                    r.unconsume();
                    t.transition(AttributeName);
                    return;
            }
        }
    };
    static TokeniserState AfterAttributeValue_quoted = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "AfterAttributeValue_quoted";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            switch (r.consume()) {
                case 9:
                case 10:
                case 12:
                case 13:
                case ' ':
                    t.transition(BeforeAttributeName);
                    return;
                case '/':
                    t.transition(SelfClosingStartTag);
                    return;
                case '>':
                    t.emitTagPending();
                    t.transition(Data);
                    return;
                case 65535:
                    t.eofError(this);
                    t.transition(Data);
                    return;
                default:
                    t.error((TokeniserState) this);
                    r.unconsume();
                    t.transition(BeforeAttributeName);
                    return;
            }
        }
    };
    static TokeniserState AfterDoctypeName = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "AfterDoctypeName";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            if (r.isEmpty()) {
                t.eofError(this);
                t.doctypePending.forceQuirks = true;
                t.emitDoctypePending();
                t.transition(Data);
            } else if (r.matchesAny(9, 10, 13, 12, ' ')) {
                r.advance();
            } else if (r.matches((char) Typography.greater)) {
                t.emitDoctypePending();
                t.advanceTransition(Data);
            } else if (r.matchConsumeIgnoreCase("PUBLIC")) {
                t.transition(AfterDoctypePublicKeyword);
            } else if (r.matchConsumeIgnoreCase("SYSTEM")) {
                t.transition(AfterDoctypeSystemKeyword);
            } else {
                t.error((TokeniserState) this);
                t.doctypePending.forceQuirks = true;
                t.advanceTransition(BogusDoctype);
            }
        }
    };
    static TokeniserState AfterDoctypePublicIdentifier = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "AfterDoctypePublicIdentifier";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            switch (r.consume()) {
                case 9:
                case 10:
                case 12:
                case 13:
                case ' ':
                    t.transition(BetweenDoctypePublicAndSystemIdentifiers);
                    return;
                case '\"':
                    t.error((TokeniserState) this);
                    t.transition(DoctypeSystemIdentifier_doubleQuoted);
                    return;
                case '\'':
                    t.error((TokeniserState) this);
                    t.transition(DoctypeSystemIdentifier_singleQuoted);
                    return;
                case '>':
                    t.emitDoctypePending();
                    t.transition(Data);
                    return;
                case 65535:
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                    return;
                default:
                    t.error((TokeniserState) this);
                    t.doctypePending.forceQuirks = true;
                    t.transition(BogusDoctype);
                    return;
            }
        }
    };
    static TokeniserState AfterDoctypePublicKeyword = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "AfterDoctypePublicKeyword";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            switch (r.consume()) {
                case 9:
                case 10:
                case 12:
                case 13:
                case ' ':
                    t.transition(BeforeDoctypePublicIdentifier);
                    return;
                case '\"':
                    t.error((TokeniserState) this);
                    t.transition(DoctypePublicIdentifier_doubleQuoted);
                    return;
                case '\'':
                    t.error((TokeniserState) this);
                    t.transition(DoctypePublicIdentifier_singleQuoted);
                    return;
                case '>':
                    t.error((TokeniserState) this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                    return;
                case 65535:
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                    return;
                default:
                    t.error((TokeniserState) this);
                    t.doctypePending.forceQuirks = true;
                    t.transition(BogusDoctype);
                    return;
            }
        }
    };
    static TokeniserState AfterDoctypeSystemIdentifier = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "AfterDoctypeSystemIdentifier";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            switch (r.consume()) {
                case 9:
                case 10:
                case 12:
                case 13:
                case ' ':
                    return;
                case '>':
                    t.emitDoctypePending();
                    t.transition(Data);
                    return;
                case 65535:
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                    return;
                default:
                    t.error((TokeniserState) this);
                    t.transition(BogusDoctype);
                    return;
            }
        }
    };
    static TokeniserState AfterDoctypeSystemKeyword = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "AfterDoctypeSystemKeyword";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            switch (r.consume()) {
                case 9:
                case 10:
                case 12:
                case 13:
                case ' ':
                    t.transition(BeforeDoctypeSystemIdentifier);
                    return;
                case '\"':
                    t.error((TokeniserState) this);
                    t.transition(DoctypeSystemIdentifier_doubleQuoted);
                    return;
                case '\'':
                    t.error((TokeniserState) this);
                    t.transition(DoctypeSystemIdentifier_singleQuoted);
                    return;
                case '>':
                    t.error((TokeniserState) this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                    return;
                case 65535:
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                    return;
                default:
                    t.error((TokeniserState) this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    return;
            }
        }
    };
    static TokeniserState AttributeName = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "AttributeName";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            t.tagPending.appendAttributeName(r.consumeToAnySorted(TokeniserState.attributeNameCharsSorted).toLowerCase());
            char c = r.consume();
            switch (c) {
                case 0:
                    t.error((TokeniserState) this);
                    t.tagPending.appendAttributeName((char) TokeniserState.replacementChar);
                    return;
                case 9:
                case 10:
                case 12:
                case 13:
                case ' ':
                    t.transition(AfterAttributeName);
                    return;
                case '\"':
                case '\'':
                case '<':
                    t.error((TokeniserState) this);
                    t.tagPending.appendAttributeName(c);
                    return;
                case '/':
                    t.transition(SelfClosingStartTag);
                    return;
                case '=':
                    t.transition(BeforeAttributeValue);
                    return;
                case '>':
                    t.emitTagPending();
                    t.transition(Data);
                    return;
                case 65535:
                    t.eofError(this);
                    t.transition(Data);
                    return;
                default:
                    return;
            }
        }
    };
    static TokeniserState AttributeValue_doubleQuoted = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "AttributeValue_doubleQuoted";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            String value = r.consumeToAny(TokeniserState.attributeDoubleValueCharsSorted);
            if (value.length() > 0) {
                t.tagPending.appendAttributeValue(value);
            } else {
                t.tagPending.setEmptyAttributeValue();
            }
            switch (r.consume()) {
                case 0:
                    t.error((TokeniserState) this);
                    t.tagPending.appendAttributeValue((char) TokeniserState.replacementChar);
                    return;
                case '\"':
                    t.transition(AfterAttributeValue_quoted);
                    return;
                case '&':
                    char[] ref = t.consumeCharacterReference(Character.valueOf(Typography.quote), true);
                    if (ref != null) {
                        t.tagPending.appendAttributeValue(ref);
                        return;
                    } else {
                        t.tagPending.appendAttributeValue((char) Typography.amp);
                        return;
                    }
                case 65535:
                    t.eofError(this);
                    t.transition(Data);
                    return;
                default:
                    return;
            }
        }
    };
    static TokeniserState AttributeValue_singleQuoted = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "AttributeValue_singleQuoted";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            String value = r.consumeToAny(TokeniserState.attributeSingleValueCharsSorted);
            if (value.length() > 0) {
                t.tagPending.appendAttributeValue(value);
            } else {
                t.tagPending.setEmptyAttributeValue();
            }
            switch (r.consume()) {
                case 0:
                    t.error((TokeniserState) this);
                    t.tagPending.appendAttributeValue((char) TokeniserState.replacementChar);
                    return;
                case '&':
                    char[] ref = t.consumeCharacterReference('\'', true);
                    if (ref != null) {
                        t.tagPending.appendAttributeValue(ref);
                        return;
                    } else {
                        t.tagPending.appendAttributeValue((char) Typography.amp);
                        return;
                    }
                case '\'':
                    t.transition(AfterAttributeValue_quoted);
                    return;
                case 65535:
                    t.eofError(this);
                    t.transition(Data);
                    return;
                default:
                    return;
            }
        }
    };
    static TokeniserState AttributeValue_unquoted = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "AttributeValue_unquoted";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            String value = r.consumeToAnySorted(TokeniserState.attributeValueUnquoted);
            if (value.length() > 0) {
                t.tagPending.appendAttributeValue(value);
            }
            char c = r.consume();
            switch (c) {
                case 0:
                    t.error((TokeniserState) this);
                    t.tagPending.appendAttributeValue((char) TokeniserState.replacementChar);
                    return;
                case 9:
                case 10:
                case 12:
                case 13:
                case ' ':
                    t.transition(BeforeAttributeName);
                    return;
                case '\"':
                case '\'':
                case '<':
                case '=':
                case '`':
                    t.error((TokeniserState) this);
                    t.tagPending.appendAttributeValue(c);
                    return;
                case '&':
                    char[] ref = t.consumeCharacterReference(Character.valueOf(Typography.greater), true);
                    if (ref != null) {
                        t.tagPending.appendAttributeValue(ref);
                        return;
                    } else {
                        t.tagPending.appendAttributeValue((char) Typography.amp);
                        return;
                    }
                case '>':
                    t.emitTagPending();
                    t.transition(Data);
                    return;
                case 65535:
                    t.eofError(this);
                    t.transition(Data);
                    return;
                default:
                    return;
            }
        }
    };
    static TokeniserState BeforeAttributeName = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "BeforeAttributeName";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            char c = r.consume();
            switch (c) {
                case 0:
                    t.error((TokeniserState) this);
                    t.tagPending.newAttribute();
                    r.unconsume();
                    t.transition(AttributeName);
                    return;
                case 9:
                case 10:
                case 12:
                case 13:
                case ' ':
                    return;
                case '\"':
                case '\'':
                case '<':
                case '=':
                    t.error((TokeniserState) this);
                    t.tagPending.newAttribute();
                    t.tagPending.appendAttributeName(c);
                    t.transition(AttributeName);
                    return;
                case '/':
                    t.transition(SelfClosingStartTag);
                    return;
                case '>':
                    t.emitTagPending();
                    t.transition(Data);
                    return;
                case 65535:
                    t.eofError(this);
                    t.transition(Data);
                    return;
                default:
                    t.tagPending.newAttribute();
                    r.unconsume();
                    t.transition(AttributeName);
                    return;
            }
        }
    };
    static TokeniserState BeforeAttributeValue = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "BeforeAttributeValue";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            char c = r.consume();
            switch (c) {
                case 0:
                    t.error((TokeniserState) this);
                    t.tagPending.appendAttributeValue((char) TokeniserState.replacementChar);
                    t.transition(AttributeValue_unquoted);
                    return;
                case 9:
                case 10:
                case 12:
                case 13:
                case ' ':
                    return;
                case '\"':
                    t.transition(AttributeValue_doubleQuoted);
                    return;
                case '&':
                    r.unconsume();
                    t.transition(AttributeValue_unquoted);
                    return;
                case '\'':
                    t.transition(AttributeValue_singleQuoted);
                    return;
                case '<':
                case '=':
                case '`':
                    t.error((TokeniserState) this);
                    t.tagPending.appendAttributeValue(c);
                    t.transition(AttributeValue_unquoted);
                    return;
                case '>':
                    t.error((TokeniserState) this);
                    t.emitTagPending();
                    t.transition(Data);
                    return;
                case 65535:
                    t.eofError(this);
                    t.emitTagPending();
                    t.transition(Data);
                    return;
                default:
                    r.unconsume();
                    t.transition(AttributeValue_unquoted);
                    return;
            }
        }
    };
    static TokeniserState BeforeDoctypeName = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "BeforeDoctypeName";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            if (r.matchesLetter()) {
                t.createDoctypePending();
                t.transition(DoctypeName);
                return;
            }
            char c = r.consume();
            switch (c) {
                case 0:
                    t.error((TokeniserState) this);
                    t.createDoctypePending();
                    t.doctypePending.name.append(TokeniserState.replacementChar);
                    t.transition(DoctypeName);
                    return;
                case 9:
                case 10:
                case 12:
                case 13:
                case ' ':
                    return;
                case 65535:
                    t.eofError(this);
                    t.createDoctypePending();
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                    return;
                default:
                    t.createDoctypePending();
                    t.doctypePending.name.append(c);
                    t.transition(DoctypeName);
                    return;
            }
        }
    };
    static TokeniserState BeforeDoctypePublicIdentifier = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "BeforeDoctypePublicIdentifier";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            switch (r.consume()) {
                case 9:
                case 10:
                case 12:
                case 13:
                case ' ':
                    return;
                case '\"':
                    t.transition(DoctypePublicIdentifier_doubleQuoted);
                    return;
                case '\'':
                    t.transition(DoctypePublicIdentifier_singleQuoted);
                    return;
                case '>':
                    t.error((TokeniserState) this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                    return;
                case 65535:
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                    return;
                default:
                    t.error((TokeniserState) this);
                    t.doctypePending.forceQuirks = true;
                    t.transition(BogusDoctype);
                    return;
            }
        }
    };
    static TokeniserState BeforeDoctypeSystemIdentifier = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "BeforeDoctypeSystemIdentifier";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            switch (r.consume()) {
                case 9:
                case 10:
                case 12:
                case 13:
                case ' ':
                    return;
                case '\"':
                    t.transition(DoctypeSystemIdentifier_doubleQuoted);
                    return;
                case '\'':
                    t.transition(DoctypeSystemIdentifier_singleQuoted);
                    return;
                case '>':
                    t.error((TokeniserState) this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                    return;
                case 65535:
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                    return;
                default:
                    t.error((TokeniserState) this);
                    t.doctypePending.forceQuirks = true;
                    t.transition(BogusDoctype);
                    return;
            }
        }
    };
    static TokeniserState BetweenDoctypePublicAndSystemIdentifiers = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "BetweenDoctypePublicAndSystemIdentifiers";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            switch (r.consume()) {
                case 9:
                case 10:
                case 12:
                case 13:
                case ' ':
                    return;
                case '\"':
                    t.error((TokeniserState) this);
                    t.transition(DoctypeSystemIdentifier_doubleQuoted);
                    return;
                case '\'':
                    t.error((TokeniserState) this);
                    t.transition(DoctypeSystemIdentifier_singleQuoted);
                    return;
                case '>':
                    t.emitDoctypePending();
                    t.transition(Data);
                    return;
                case 65535:
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                    return;
                default:
                    t.error((TokeniserState) this);
                    t.doctypePending.forceQuirks = true;
                    t.transition(BogusDoctype);
                    return;
            }
        }
    };
    static TokeniserState BogusComment = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "BogusComment";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            r.unconsume();
            Token.Comment comment = new Token.Comment();
            comment.bogus = true;
            comment.data.append(r.consumeTo((char) Typography.greater));
            t.emit((Token) comment);
            t.advanceTransition(Data);
        }
    };
    static TokeniserState BogusDoctype = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "BogusDoctype";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            switch (r.consume()) {
                case '>':
                    t.emitDoctypePending();
                    t.transition(Data);
                    return;
                case 65535:
                    t.emitDoctypePending();
                    t.transition(Data);
                    return;
                default:
                    return;
            }
        }
    };
    static TokeniserState CdataSection = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "CdataSection";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            t.emit(r.consumeTo("]]>"));
            r.matchConsume("]]>");
            t.transition(Data);
        }
    };
    static TokeniserState CharacterReferenceInData = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "CharacterReferenceInData";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            TokeniserState.readCharRef(t, Data);
        }
    };
    static TokeniserState CharacterReferenceInRcdata = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "CharacterReferenceInRcdata";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            TokeniserState.readCharRef(t, Rcdata);
        }
    };
    static TokeniserState Comment = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "Comment";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            switch (r.current()) {
                case 0:
                    t.error((TokeniserState) this);
                    r.advance();
                    t.commentPending.data.append(TokeniserState.replacementChar);
                    return;
                case '-':
                    t.advanceTransition(CommentEndDash);
                    return;
                case 65535:
                    t.eofError(this);
                    t.emitCommentPending();
                    t.transition(Data);
                    return;
                default:
                    t.commentPending.data.append(r.consumeToAny('-', 0));
                    return;
            }
        }
    };
    static TokeniserState CommentEnd = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "CommentEnd";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            char c = r.consume();
            switch (c) {
                case 0:
                    t.error((TokeniserState) this);
                    t.commentPending.data.append("--").append(TokeniserState.replacementChar);
                    t.transition(Comment);
                    return;
                case '!':
                    t.error((TokeniserState) this);
                    t.transition(CommentEndBang);
                    return;
                case '-':
                    t.error((TokeniserState) this);
                    t.commentPending.data.append('-');
                    return;
                case '>':
                    t.emitCommentPending();
                    t.transition(Data);
                    return;
                case 65535:
                    t.eofError(this);
                    t.emitCommentPending();
                    t.transition(Data);
                    return;
                default:
                    t.error((TokeniserState) this);
                    t.commentPending.data.append("--").append(c);
                    t.transition(Comment);
                    return;
            }
        }
    };
    static TokeniserState CommentEndBang = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "CommentEndBang";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            char c = r.consume();
            switch (c) {
                case 0:
                    t.error((TokeniserState) this);
                    t.commentPending.data.append("--!").append(TokeniserState.replacementChar);
                    t.transition(Comment);
                    return;
                case '-':
                    t.commentPending.data.append("--!");
                    t.transition(CommentEndDash);
                    return;
                case '>':
                    t.emitCommentPending();
                    t.transition(Data);
                    return;
                case 65535:
                    t.eofError(this);
                    t.emitCommentPending();
                    t.transition(Data);
                    return;
                default:
                    t.commentPending.data.append("--!").append(c);
                    t.transition(Comment);
                    return;
            }
        }
    };
    static TokeniserState CommentEndDash = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "CommentEndDash";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            char c = r.consume();
            switch (c) {
                case 0:
                    t.error((TokeniserState) this);
                    t.commentPending.data.append('-').append(TokeniserState.replacementChar);
                    t.transition(Comment);
                    return;
                case '-':
                    t.transition(CommentEnd);
                    return;
                case 65535:
                    t.eofError(this);
                    t.emitCommentPending();
                    t.transition(Data);
                    return;
                default:
                    t.commentPending.data.append('-').append(c);
                    t.transition(Comment);
                    return;
            }
        }
    };
    static TokeniserState CommentStart = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "CommentStart";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            char c = r.consume();
            switch (c) {
                case 0:
                    t.error((TokeniserState) this);
                    t.commentPending.data.append(TokeniserState.replacementChar);
                    t.transition(Comment);
                    return;
                case '-':
                    t.transition(CommentStartDash);
                    return;
                case '>':
                    t.error((TokeniserState) this);
                    t.emitCommentPending();
                    t.transition(Data);
                    return;
                case 65535:
                    t.eofError(this);
                    t.emitCommentPending();
                    t.transition(Data);
                    return;
                default:
                    t.commentPending.data.append(c);
                    t.transition(Comment);
                    return;
            }
        }
    };
    static TokeniserState CommentStartDash = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "CommentStartDash";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            char c = r.consume();
            switch (c) {
                case 0:
                    t.error((TokeniserState) this);
                    t.commentPending.data.append(TokeniserState.replacementChar);
                    t.transition(Comment);
                    return;
                case '-':
                    t.transition(CommentStartDash);
                    return;
                case '>':
                    t.error((TokeniserState) this);
                    t.emitCommentPending();
                    t.transition(Data);
                    return;
                case 65535:
                    t.eofError(this);
                    t.emitCommentPending();
                    t.transition(Data);
                    return;
                default:
                    t.commentPending.data.append(c);
                    t.transition(Comment);
                    return;
            }
        }
    };
    static TokeniserState Data = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "Data";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            switch (r.current()) {
                case 0:
                    t.error((TokeniserState) this);
                    t.emit(r.consume());
                    return;
                case '&':
                    t.advanceTransition(CharacterReferenceInData);
                    return;
                case '<':
                    t.advanceTransition(TagOpen);
                    return;
                case 65535:
                    t.emit((Token) new Token.EOF());
                    return;
                default:
                    t.emit(r.consumeData());
                    return;
            }
        }
    };
    static TokeniserState Doctype = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "Doctype";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            switch (r.consume()) {
                case 9:
                case 10:
                case 12:
                case 13:
                case ' ':
                    t.transition(BeforeDoctypeName);
                    return;
                case '>':
                    break;
                case 65535:
                    t.eofError(this);
                    break;
                default:
                    t.error((TokeniserState) this);
                    t.transition(BeforeDoctypeName);
                    return;
            }
            t.error((TokeniserState) this);
            t.createDoctypePending();
            t.doctypePending.forceQuirks = true;
            t.emitDoctypePending();
            t.transition(Data);
        }
    };
    static TokeniserState DoctypeName = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "DoctypeName";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            if (r.matchesLetter()) {
                t.doctypePending.name.append(r.consumeLetterSequence().toLowerCase());
                return;
            }
            char c = r.consume();
            switch (c) {
                case 0:
                    t.error((TokeniserState) this);
                    t.doctypePending.name.append(TokeniserState.replacementChar);
                    return;
                case 9:
                case 10:
                case 12:
                case 13:
                case ' ':
                    t.transition(AfterDoctypeName);
                    return;
                case '>':
                    t.emitDoctypePending();
                    t.transition(Data);
                    return;
                case 65535:
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                    return;
                default:
                    t.doctypePending.name.append(c);
                    return;
            }
        }
    };
    static TokeniserState DoctypePublicIdentifier_doubleQuoted = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "DoctypePublicIdentifier_doubleQuoted";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            char c = r.consume();
            switch (c) {
                case 0:
                    t.error((TokeniserState) this);
                    t.doctypePending.publicIdentifier.append(TokeniserState.replacementChar);
                    return;
                case '\"':
                    t.transition(AfterDoctypePublicIdentifier);
                    return;
                case '>':
                    t.error((TokeniserState) this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                    return;
                case 65535:
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                    return;
                default:
                    t.doctypePending.publicIdentifier.append(c);
                    return;
            }
        }
    };
    static TokeniserState DoctypePublicIdentifier_singleQuoted = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "DoctypePublicIdentifier_singleQuoted";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            char c = r.consume();
            switch (c) {
                case 0:
                    t.error((TokeniserState) this);
                    t.doctypePending.publicIdentifier.append(TokeniserState.replacementChar);
                    return;
                case '\'':
                    t.transition(AfterDoctypePublicIdentifier);
                    return;
                case '>':
                    t.error((TokeniserState) this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                    return;
                case 65535:
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                    return;
                default:
                    t.doctypePending.publicIdentifier.append(c);
                    return;
            }
        }
    };
    static TokeniserState DoctypeSystemIdentifier_doubleQuoted = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "DoctypeSystemIdentifier_doubleQuoted";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            char c = r.consume();
            switch (c) {
                case 0:
                    t.error((TokeniserState) this);
                    t.doctypePending.systemIdentifier.append(TokeniserState.replacementChar);
                    return;
                case '\"':
                    t.transition(AfterDoctypeSystemIdentifier);
                    return;
                case '>':
                    t.error((TokeniserState) this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                    return;
                case 65535:
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                    return;
                default:
                    t.doctypePending.systemIdentifier.append(c);
                    return;
            }
        }
    };
    static TokeniserState DoctypeSystemIdentifier_singleQuoted = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "DoctypeSystemIdentifier_singleQuoted";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            char c = r.consume();
            switch (c) {
                case 0:
                    t.error((TokeniserState) this);
                    t.doctypePending.systemIdentifier.append(TokeniserState.replacementChar);
                    return;
                case '\'':
                    t.transition(AfterDoctypeSystemIdentifier);
                    return;
                case '>':
                    t.error((TokeniserState) this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                    return;
                case 65535:
                    t.eofError(this);
                    t.doctypePending.forceQuirks = true;
                    t.emitDoctypePending();
                    t.transition(Data);
                    return;
                default:
                    t.doctypePending.systemIdentifier.append(c);
                    return;
            }
        }
    };
    static TokeniserState EndTagOpen = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "EndTagOpen";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            if (r.isEmpty()) {
                t.eofError(this);
                t.emit("</");
                t.transition(Data);
            } else if (r.matchesLetter()) {
                t.createTagPending(false);
                t.transition(TagName);
            } else if (r.matches((char) Typography.greater)) {
                t.error((TokeniserState) this);
                t.advanceTransition(Data);
            } else {
                t.error((TokeniserState) this);
                t.advanceTransition(BogusComment);
            }
        }
    };
    static TokeniserState MarkupDeclarationOpen = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "MarkupDeclarationOpen";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            if (r.matchConsume("--")) {
                t.createCommentPending();
                t.transition(CommentStart);
            } else if (r.matchConsumeIgnoreCase("DOCTYPE")) {
                t.transition(Doctype);
            } else if (r.matchConsume("[CDATA[")) {
                t.transition(CdataSection);
            } else {
                t.error((TokeniserState) this);
                t.advanceTransition(BogusComment);
            }
        }
    };
    static TokeniserState PLAINTEXT = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "PLAINTEXT";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            switch (r.current()) {
                case 0:
                    t.error((TokeniserState) this);
                    r.advance();
                    t.emit((char) TokeniserState.replacementChar);
                    return;
                case 65535:
                    t.emit((Token) new Token.EOF());
                    return;
                default:
                    t.emit(r.consumeTo(0));
                    return;
            }
        }
    };
    static TokeniserState RCDATAEndTagName = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "RCDATAEndTagName";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            if (r.matchesLetter()) {
                String name = r.consumeLetterSequence();
                t.tagPending.appendTagName(name.toLowerCase());
                t.dataBuffer.append(name);
                return;
            }
            switch (r.consume()) {
                case 9:
                case 10:
                case 12:
                case 13:
                case ' ':
                    if (t.isAppropriateEndTagToken()) {
                        t.transition(BeforeAttributeName);
                        return;
                    } else {
                        anythingElse(t, r);
                        return;
                    }
                case '/':
                    if (t.isAppropriateEndTagToken()) {
                        t.transition(SelfClosingStartTag);
                        return;
                    } else {
                        anythingElse(t, r);
                        return;
                    }
                case '>':
                    if (t.isAppropriateEndTagToken()) {
                        t.emitTagPending();
                        t.transition(Data);
                        return;
                    }
                    anythingElse(t, r);
                    return;
                default:
                    anythingElse(t, r);
                    return;
            }
        }

        private void anythingElse(Tokeniser t, CharacterReader r) {
            t.emit("</" + t.dataBuffer.toString());
            r.unconsume();
            t.transition(Rcdata);
        }
    };
    static TokeniserState RCDATAEndTagOpen = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "RCDATAEndTagOpen";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            if (r.matchesLetter()) {
                t.createTagPending(false);
                t.tagPending.appendTagName(Character.toLowerCase(r.current()));
                t.dataBuffer.append(Character.toLowerCase(r.current()));
                t.advanceTransition(RCDATAEndTagName);
                return;
            }
            t.emit("</");
            t.transition(Rcdata);
        }
    };
    static TokeniserState Rawtext = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "Rawtext";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            TokeniserState.readData(t, r, this, RawtextLessthanSign);
        }
    };
    static TokeniserState RawtextEndTagName = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "RawtextEndTagName";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            TokeniserState.handleDataEndTag(t, r, Rawtext);
        }
    };
    static TokeniserState RawtextEndTagOpen = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "RawtextEndTagOpen";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            TokeniserState.readEndTag(t, r, RawtextEndTagName, Rawtext);
        }
    };
    static TokeniserState RawtextLessthanSign = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "RawtextLessthanSign";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            if (r.matches('/')) {
                t.createTempBuffer();
                t.advanceTransition(RawtextEndTagOpen);
                return;
            }
            t.emit((char) Typography.less);
            t.transition(Rawtext);
        }
    };
    static TokeniserState Rcdata = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "Rcdata";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            switch (r.current()) {
                case 0:
                    t.error((TokeniserState) this);
                    r.advance();
                    t.emit((char) TokeniserState.replacementChar);
                    return;
                case '&':
                    t.advanceTransition(CharacterReferenceInRcdata);
                    return;
                case '<':
                    t.advanceTransition(RcdataLessthanSign);
                    return;
                case 65535:
                    t.emit((Token) new Token.EOF());
                    return;
                default:
                    t.emit(r.consumeToAny(Typography.amp, Typography.less, 0));
                    return;
            }
        }
    };
    static TokeniserState RcdataLessthanSign = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "RcdataLessthanSign";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            if (r.matches('/')) {
                t.createTempBuffer();
                t.advanceTransition(RCDATAEndTagOpen);
            } else if (!r.matchesLetter() || t.appropriateEndTagName() == null || r.containsIgnoreCase("</" + t.appropriateEndTagName())) {
                t.emit("<");
                t.transition(Rcdata);
            } else {
                t.tagPending = t.createTagPending(false).name(t.appropriateEndTagName());
                t.emitTagPending();
                r.unconsume();
                t.transition(Data);
            }
        }
    };
    static TokeniserState ScriptData = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "ScriptData";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            TokeniserState.readData(t, r, this, ScriptDataLessthanSign);
        }
    };
    static TokeniserState ScriptDataDoubleEscapeEnd = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "ScriptDataDoubleEscapeEnd";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            TokeniserState.handleDataDoubleEscapeTag(t, r, ScriptDataEscaped, ScriptDataDoubleEscaped);
        }
    };
    static TokeniserState ScriptDataDoubleEscapeStart = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "ScriptDataDoubleEscapeStart";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            TokeniserState.handleDataDoubleEscapeTag(t, r, ScriptDataDoubleEscaped, ScriptDataEscaped);
        }
    };
    static TokeniserState ScriptDataDoubleEscaped = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "ScriptDataDoubleEscaped";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            char c = r.current();
            switch (c) {
                case 0:
                    t.error((TokeniserState) this);
                    r.advance();
                    t.emit((char) TokeniserState.replacementChar);
                    return;
                case '-':
                    t.emit(c);
                    t.advanceTransition(ScriptDataDoubleEscapedDash);
                    return;
                case '<':
                    t.emit(c);
                    t.advanceTransition(ScriptDataDoubleEscapedLessthanSign);
                    return;
                case 65535:
                    t.eofError(this);
                    t.transition(Data);
                    return;
                default:
                    t.emit(r.consumeToAny('-', Typography.less, 0));
                    return;
            }
        }
    };
    static TokeniserState ScriptDataDoubleEscapedDash = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "ScriptDataDoubleEscapedDash";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            char c = r.consume();
            switch (c) {
                case 0:
                    t.error((TokeniserState) this);
                    t.emit((char) TokeniserState.replacementChar);
                    t.transition(ScriptDataDoubleEscaped);
                    return;
                case '-':
                    t.emit(c);
                    t.transition(ScriptDataDoubleEscapedDashDash);
                    return;
                case '<':
                    t.emit(c);
                    t.transition(ScriptDataDoubleEscapedLessthanSign);
                    return;
                case 65535:
                    t.eofError(this);
                    t.transition(Data);
                    return;
                default:
                    t.emit(c);
                    t.transition(ScriptDataDoubleEscaped);
                    return;
            }
        }
    };
    static TokeniserState ScriptDataDoubleEscapedDashDash = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "ScriptDataDoubleEscapedDashDash";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            char c = r.consume();
            switch (c) {
                case 0:
                    t.error((TokeniserState) this);
                    t.emit((char) TokeniserState.replacementChar);
                    t.transition(ScriptDataDoubleEscaped);
                    return;
                case '-':
                    t.emit(c);
                    return;
                case '<':
                    t.emit(c);
                    t.transition(ScriptDataDoubleEscapedLessthanSign);
                    return;
                case '>':
                    t.emit(c);
                    t.transition(ScriptData);
                    return;
                case 65535:
                    t.eofError(this);
                    t.transition(Data);
                    return;
                default:
                    t.emit(c);
                    t.transition(ScriptDataDoubleEscaped);
                    return;
            }
        }
    };
    static TokeniserState ScriptDataDoubleEscapedLessthanSign = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "ScriptDataDoubleEscapedLessthanSign";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            if (r.matches('/')) {
                t.emit('/');
                t.createTempBuffer();
                t.advanceTransition(ScriptDataDoubleEscapeEnd);
                return;
            }
            t.transition(ScriptDataDoubleEscaped);
        }
    };
    static TokeniserState ScriptDataEndTagName = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "ScriptDataEndTagName";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            TokeniserState.handleDataEndTag(t, r, ScriptData);
        }
    };
    static TokeniserState ScriptDataEndTagOpen = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "ScriptDataEndTagOpen";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            TokeniserState.readEndTag(t, r, ScriptDataEndTagName, ScriptData);
        }
    };
    static TokeniserState ScriptDataEscapeStart = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "ScriptDataEscapeStart";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            if (r.matches('-')) {
                t.emit('-');
                t.advanceTransition(ScriptDataEscapeStartDash);
                return;
            }
            t.transition(ScriptData);
        }
    };
    static TokeniserState ScriptDataEscapeStartDash = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "ScriptDataEscapeStartDash";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            if (r.matches('-')) {
                t.emit('-');
                t.advanceTransition(ScriptDataEscapedDashDash);
                return;
            }
            t.transition(ScriptData);
        }
    };
    static TokeniserState ScriptDataEscaped = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "ScriptDataEscaped";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            if (r.isEmpty()) {
                t.eofError(this);
                t.transition(Data);
                return;
            }
            switch (r.current()) {
                case 0:
                    t.error((TokeniserState) this);
                    r.advance();
                    t.emit((char) TokeniserState.replacementChar);
                    return;
                case '-':
                    t.emit('-');
                    t.advanceTransition(ScriptDataEscapedDash);
                    return;
                case '<':
                    t.advanceTransition(ScriptDataEscapedLessthanSign);
                    return;
                default:
                    t.emit(r.consumeToAny('-', Typography.less, 0));
                    return;
            }
        }
    };
    static TokeniserState ScriptDataEscapedDash = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "ScriptDataEscapedDash";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            if (r.isEmpty()) {
                t.eofError(this);
                t.transition(Data);
                return;
            }
            char c = r.consume();
            switch (c) {
                case 0:
                    t.error((TokeniserState) this);
                    t.emit((char) TokeniserState.replacementChar);
                    t.transition(ScriptDataEscaped);
                    return;
                case '-':
                    t.emit(c);
                    t.transition(ScriptDataEscapedDashDash);
                    return;
                case '<':
                    t.transition(ScriptDataEscapedLessthanSign);
                    return;
                default:
                    t.emit(c);
                    t.transition(ScriptDataEscaped);
                    return;
            }
        }
    };
    static TokeniserState ScriptDataEscapedDashDash = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "ScriptDataEscapedDashDash";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            if (r.isEmpty()) {
                t.eofError(this);
                t.transition(Data);
                return;
            }
            char c = r.consume();
            switch (c) {
                case 0:
                    t.error((TokeniserState) this);
                    t.emit((char) TokeniserState.replacementChar);
                    t.transition(ScriptDataEscaped);
                    return;
                case '-':
                    t.emit(c);
                    return;
                case '<':
                    t.transition(ScriptDataEscapedLessthanSign);
                    return;
                case '>':
                    t.emit(c);
                    t.transition(ScriptData);
                    return;
                default:
                    t.emit(c);
                    t.transition(ScriptDataEscaped);
                    return;
            }
        }
    };
    static TokeniserState ScriptDataEscapedEndTagName = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "ScriptDataEscapedEndTagName";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            TokeniserState.handleDataEndTag(t, r, ScriptDataEscaped);
        }
    };
    static TokeniserState ScriptDataEscapedEndTagOpen = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "ScriptDataEscapedEndTagOpen";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            if (r.matchesLetter()) {
                t.createTagPending(false);
                t.tagPending.appendTagName(Character.toLowerCase(r.current()));
                t.dataBuffer.append(r.current());
                t.advanceTransition(ScriptDataEscapedEndTagName);
                return;
            }
            t.emit("</");
            t.transition(ScriptDataEscaped);
        }
    };
    static TokeniserState ScriptDataEscapedLessthanSign = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "ScriptDataEscapedLessthanSign";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            if (r.matchesLetter()) {
                t.createTempBuffer();
                t.dataBuffer.append(Character.toLowerCase(r.current()));
                t.emit("<" + r.current());
                t.advanceTransition(ScriptDataDoubleEscapeStart);
            } else if (r.matches('/')) {
                t.createTempBuffer();
                t.advanceTransition(ScriptDataEscapedEndTagOpen);
            } else {
                t.emit((char) Typography.less);
                t.transition(ScriptDataEscaped);
            }
        }
    };
    static TokeniserState ScriptDataLessthanSign = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "ScriptDataLessthanSign";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            switch (r.consume()) {
                case '!':
                    t.emit("<!");
                    t.transition(ScriptDataEscapeStart);
                    return;
                case '/':
                    t.createTempBuffer();
                    t.transition(ScriptDataEndTagOpen);
                    return;
                default:
                    t.emit("<");
                    r.unconsume();
                    t.transition(ScriptData);
                    return;
            }
        }
    };
    static TokeniserState SelfClosingStartTag = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "SelfClosingStartTag";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            switch (r.consume()) {
                case '>':
                    t.tagPending.selfClosing = true;
                    t.emitTagPending();
                    t.transition(Data);
                    return;
                case 65535:
                    t.eofError(this);
                    t.transition(Data);
                    return;
                default:
                    t.error((TokeniserState) this);
                    t.transition(BeforeAttributeName);
                    return;
            }
        }
    };
    static TokeniserState TagName = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "TagName";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            t.tagPending.appendTagName(r.consumeTagName().toLowerCase());
            switch (r.consume()) {
                case 0:
                    t.tagPending.appendTagName(TokeniserState.replacementStr);
                    return;
                case 9:
                case 10:
                case 12:
                case 13:
                case ' ':
                    t.transition(BeforeAttributeName);
                    return;
                case '/':
                    t.transition(SelfClosingStartTag);
                    return;
                case '>':
                    t.emitTagPending();
                    t.transition(Data);
                    return;
                case 65535:
                    t.eofError(this);
                    t.transition(Data);
                    return;
                default:
                    return;
            }
        }
    };
    static TokeniserState TagOpen = new TokeniserState() {
        /* access modifiers changed from: package-private */
        public String getName() {
            return "TagOpen";
        }

        /* access modifiers changed from: package-private */
        public void read(Tokeniser t, CharacterReader r) {
            switch (r.current()) {
                case '!':
                    t.advanceTransition(MarkupDeclarationOpen);
                    return;
                case '/':
                    t.advanceTransition(EndTagOpen);
                    return;
                case '?':
                    t.advanceTransition(BogusComment);
                    return;
                default:
                    if (r.matchesLetter()) {
                        t.createTagPending(true);
                        t.transition(TagName);
                        return;
                    }
                    t.error((TokeniserState) this);
                    t.emit((char) Typography.less);
                    t.transition(Data);
                    return;
            }
        }
    };
    /* access modifiers changed from: private */
    public static final char[] attributeDoubleValueCharsSorted;
    /* access modifiers changed from: private */
    public static final char[] attributeNameCharsSorted;
    /* access modifiers changed from: private */
    public static final char[] attributeSingleValueCharsSorted;
    /* access modifiers changed from: private */
    public static final char[] attributeValueUnquoted;
    private static final char eof = '￿';
    static final char nullChar = '\u0000';
    private static final char replacementChar = '�';
    /* access modifiers changed from: private */
    public static final String replacementStr = String.valueOf(replacementChar);

    /* access modifiers changed from: package-private */
    public abstract String getName();

    /* access modifiers changed from: package-private */
    public abstract void read(Tokeniser tokeniser, CharacterReader characterReader);

    TokeniserState() {
    }

    static {
        char[] cArr = {'\'', Typography.amp, 0};
        attributeSingleValueCharsSorted = cArr;
        char[] cArr2 = {Typography.quote, Typography.amp, 0};
        attributeDoubleValueCharsSorted = cArr2;
        char[] cArr3 = {9, 10, 13, 12, ' ', '/', '=', Typography.greater, 0, Typography.quote, '\'', Typography.less};
        attributeNameCharsSorted = cArr3;
        char[] cArr4 = {9, 10, 13, 12, ' ', Typography.amp, Typography.greater, 0, Typography.quote, '\'', Typography.less, '=', '`'};
        attributeValueUnquoted = cArr4;
        Arrays.sort(cArr);
        Arrays.sort(cArr2);
        Arrays.sort(cArr3);
        Arrays.sort(cArr4);
    }

    public String toString() {
        return getName();
    }

    /* access modifiers changed from: private */
    public static void handleDataEndTag(Tokeniser t, CharacterReader r, TokeniserState elseTransition) {
        if (r.matchesLetter()) {
            String name = r.consumeLetterSequence();
            t.tagPending.appendTagName(name.toLowerCase());
            t.dataBuffer.append(name);
            return;
        }
        boolean needsExitTransition = false;
        if (t.isAppropriateEndTagToken() && !r.isEmpty()) {
            char c = r.consume();
            switch (c) {
                case 9:
                case 10:
                case 12:
                case 13:
                case ' ':
                    t.transition(BeforeAttributeName);
                    break;
                case '/':
                    t.transition(SelfClosingStartTag);
                    break;
                case '>':
                    t.emitTagPending();
                    t.transition(Data);
                    break;
                default:
                    t.dataBuffer.append(c);
                    needsExitTransition = true;
                    break;
            }
        } else {
            needsExitTransition = true;
        }
        if (needsExitTransition) {
            t.emit("</" + t.dataBuffer.toString());
            t.transition(elseTransition);
        }
    }

    /* access modifiers changed from: private */
    public static void readData(Tokeniser t, CharacterReader r, TokeniserState current, TokeniserState advance) {
        switch (r.current()) {
            case 0:
                t.error(current);
                r.advance();
                t.emit((char) replacementChar);
                return;
            case '<':
                t.advanceTransition(advance);
                return;
            case 65535:
                t.emit((Token) new Token.EOF());
                return;
            default:
                t.emit(r.consumeToAny(Typography.less, 0));
                return;
        }
    }

    /* access modifiers changed from: private */
    public static void readCharRef(Tokeniser t, TokeniserState advance) {
        char[] c = t.consumeCharacterReference((Character) null, false);
        if (c == null) {
            t.emit((char) Typography.amp);
        } else {
            t.emit(c);
        }
        t.transition(advance);
    }

    /* access modifiers changed from: private */
    public static void readEndTag(Tokeniser t, CharacterReader r, TokeniserState a, TokeniserState b) {
        if (r.matchesLetter()) {
            t.createTagPending(false);
            t.transition(a);
            return;
        }
        t.emit("</");
        t.transition(b);
    }

    /* access modifiers changed from: private */
    public static void handleDataDoubleEscapeTag(Tokeniser t, CharacterReader r, TokeniserState primary, TokeniserState fallback) {
        if (r.matchesLetter()) {
            String name = r.consumeLetterSequence();
            t.dataBuffer.append(name.toLowerCase());
            t.emit(name);
            return;
        }
        char c = r.consume();
        switch (c) {
            case 9:
            case 10:
            case 12:
            case 13:
            case ' ':
            case '/':
            case '>':
                if (t.dataBuffer.toString().equals(SvgConstants.Tags.SCRIPT)) {
                    t.transition(primary);
                } else {
                    t.transition(fallback);
                }
                t.emit(c);
                return;
            default:
                r.unconsume();
                t.transition(fallback);
                return;
        }
    }
}
