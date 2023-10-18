package com.itextpdf.kernel.pdf;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.crypto.BadPasswordException;
import com.itextpdf.p026io.LogMessageConstant;
import java.io.IOException;
import java.io.Serializable;
import org.slf4j.LoggerFactory;

public abstract class PdfObject implements Serializable {
    public static final byte ARRAY = 1;
    public static final byte BOOLEAN = 2;
    public static final byte DICTIONARY = 3;
    protected static final short FLUSHED = 1;
    protected static final short FORBID_RELEASE = 128;
    protected static final short FREE = 2;
    public static final byte INDIRECT_REFERENCE = 5;
    public static final byte LITERAL = 4;
    protected static final short MODIFIED = 8;
    protected static final short MUST_BE_FLUSHED = 32;
    protected static final short MUST_BE_INDIRECT = 64;
    public static final byte NAME = 6;
    public static final byte NULL = 7;
    public static final byte NUMBER = 8;
    protected static final short ORIGINAL_OBJECT_STREAM = 16;
    protected static final short READING = 4;
    protected static final short READ_ONLY = 256;
    public static final byte STREAM = 9;
    public static final byte STRING = 10;
    protected static final short UNENCRYPTED = 512;
    private static final long serialVersionUID = -3852543867469424720L;
    protected PdfIndirectReference indirectReference = null;
    private short state;

    public abstract byte getType();

    /* access modifiers changed from: protected */
    public abstract PdfObject newInstance();

    public final void flush() {
        flush(true);
    }

    public final void flush(boolean canBeInObjStm) {
        if (!isFlushed() && getIndirectReference() != null && !getIndirectReference().isFree()) {
            try {
                PdfDocument document = getIndirectReference().getDocument();
                if (document == null) {
                    return;
                }
                if (!document.isAppendMode() || isModified()) {
                    document.checkIsoConformance(this, IsoKey.PDF_OBJECT);
                    document.flushObject(this, canBeInObjStm && getType() != 9 && getType() != 5 && getIndirectReference().getGenNumber() == 0);
                    return;
                }
                LoggerFactory.getLogger((Class<?>) PdfObject.class).info(LogMessageConstant.PDF_OBJECT_FLUSHING_NOT_PERFORMED);
            } catch (IOException e) {
                throw new PdfException(PdfException.CannotFlushObject, e, this);
            }
        }
    }

    public PdfIndirectReference getIndirectReference() {
        return this.indirectReference;
    }

    public boolean isIndirect() {
        return this.indirectReference != null || checkState(64);
    }

    public PdfObject makeIndirect(PdfDocument document, PdfIndirectReference reference) {
        if (document == null || this.indirectReference != null) {
            return this;
        }
        if (document.getWriter() != null) {
            if (reference == null) {
                PdfIndirectReference createNextIndirectReference = document.createNextIndirectReference();
                this.indirectReference = createNextIndirectReference;
                createNextIndirectReference.setRefersTo(this);
            } else {
                reference.setState(MODIFIED);
                this.indirectReference = reference;
                reference.setRefersTo(this);
            }
            setState(FORBID_RELEASE);
            clearState(64);
            return this;
        }
        throw new PdfException(PdfException.ThereIsNoAssociatePdfWriterForMakingIndirects);
    }

    public PdfObject makeIndirect(PdfDocument document) {
        return makeIndirect(document, (PdfIndirectReference) null);
    }

    public boolean isFlushed() {
        PdfIndirectReference indirectReference2 = getIndirectReference();
        return indirectReference2 != null && indirectReference2.checkState(1);
    }

    public boolean isModified() {
        PdfIndirectReference indirectReference2 = getIndirectReference();
        return indirectReference2 != null && indirectReference2.checkState(MODIFIED);
    }

    public PdfObject clone() {
        PdfObject newObject = newInstance();
        if (this.indirectReference != null || checkState(64)) {
            newObject.setState(64);
        }
        newObject.copyContent(this, (PdfDocument) null);
        return newObject;
    }

    public PdfObject copyTo(PdfDocument document) {
        return copyTo(document, true);
    }

    public PdfObject copyTo(PdfDocument document, boolean allowDuplicating) {
        if (document != null) {
            PdfIndirectReference pdfIndirectReference = this.indirectReference;
            if (pdfIndirectReference != null) {
                if (pdfIndirectReference.getWriter() != null || checkState(64)) {
                    throw new PdfException(PdfException.CannotCopyIndirectObjectFromTheDocumentThatIsBeingWritten);
                } else if (!this.indirectReference.getReader().isOpenedWithFullPermission()) {
                    throw new BadPasswordException(BadPasswordException.PdfReaderNotOpenedWithOwnerPassword);
                }
            }
            return processCopying(document, allowDuplicating);
        }
        throw new PdfException(PdfException.DocumentForCopyToCannotBeNull);
    }

    public PdfObject setModified() {
        PdfIndirectReference pdfIndirectReference = this.indirectReference;
        if (pdfIndirectReference != null) {
            pdfIndirectReference.setState(MODIFIED);
            setState(FORBID_RELEASE);
        }
        return this;
    }

    public boolean isReleaseForbidden() {
        return checkState(FORBID_RELEASE);
    }

    public void release() {
        if (isReleaseForbidden()) {
            LoggerFactory.getLogger((Class<?>) PdfObject.class).warn(LogMessageConstant.FORBID_RELEASE_IS_SET);
            return;
        }
        PdfIndirectReference pdfIndirectReference = this.indirectReference;
        if (pdfIndirectReference != null && pdfIndirectReference.getReader() != null && !this.indirectReference.checkState(1)) {
            this.indirectReference.refersTo = null;
            this.indirectReference = null;
            setState(READ_ONLY);
        }
    }

    public boolean isNull() {
        return getType() == 7;
    }

    public boolean isBoolean() {
        return getType() == 2;
    }

    public boolean isNumber() {
        return getType() == 8;
    }

    public boolean isString() {
        return getType() == 10;
    }

    public boolean isName() {
        return getType() == 6;
    }

    public boolean isArray() {
        return getType() == 1;
    }

    public boolean isDictionary() {
        return getType() == 3;
    }

    public boolean isStream() {
        return getType() == 9;
    }

    public boolean isIndirectReference() {
        return getType() == 5;
    }

    public boolean isLiteral() {
        return getType() == 4;
    }

    /* access modifiers changed from: protected */
    public PdfObject setIndirectReference(PdfIndirectReference indirectReference2) {
        this.indirectReference = indirectReference2;
        return this;
    }

    /* access modifiers changed from: protected */
    public boolean checkState(short state2) {
        return (this.state & state2) == state2;
    }

    /* access modifiers changed from: protected */
    public PdfObject setState(short state2) {
        this.state = (short) (this.state | state2);
        return this;
    }

    /* access modifiers changed from: protected */
    public PdfObject clearState(short state2) {
        this.state = (short) (this.state & ((short) (state2 ^ -1)));
        return this;
    }

    /* access modifiers changed from: protected */
    public void copyContent(PdfObject from, PdfDocument document) {
        if (isFlushed()) {
            throw new PdfException(PdfException.CannotCopyFlushedObject, (Object) this);
        }
    }

    /* access modifiers changed from: package-private */
    public PdfObject processCopying(PdfDocument documentTo, boolean allowDuplicating) {
        if (documentTo != null) {
            PdfWriter writer = documentTo.getWriter();
            if (writer != null) {
                return writer.copyObject(this, documentTo, allowDuplicating);
            }
            throw new PdfException(PdfException.CannotCopyToDocumentOpenedInReadingMode);
        }
        PdfObject obj = this;
        if (obj.isIndirectReference()) {
            PdfObject refTo = ((PdfIndirectReference) obj).getRefersTo();
            obj = refTo != null ? refTo : obj;
        }
        if (!obj.isIndirect() || allowDuplicating) {
            return obj.clone();
        }
        return obj;
    }

    static boolean equalContent(PdfObject obj1, PdfObject obj2) {
        PdfObject direct1 = (obj1 == null || !obj1.isIndirectReference()) ? obj1 : ((PdfIndirectReference) obj1).getRefersTo(true);
        PdfObject direct2 = (obj2 == null || !obj2.isIndirectReference()) ? obj2 : ((PdfIndirectReference) obj2).getRefersTo(true);
        if (direct1 == null || !direct1.equals(direct2)) {
            return false;
        }
        return true;
    }
}
