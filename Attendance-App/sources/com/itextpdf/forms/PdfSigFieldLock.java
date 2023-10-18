package com.itextpdf.forms;

import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;
import com.itextpdf.kernel.pdf.PdfString;

public class PdfSigFieldLock extends PdfObjectWrapper<PdfDictionary> {

    public enum LockAction {
        ALL,
        INCLUDE,
        EXCLUDE
    }

    public enum LockPermissions {
        NO_CHANGES_ALLOWED,
        FORM_FILLING,
        FORM_FILLING_AND_ANNOTATION
    }

    public PdfSigFieldLock() {
        this(new PdfDictionary());
    }

    public PdfSigFieldLock(PdfDictionary dict) {
        super(dict);
        ((PdfDictionary) getPdfObject()).put(PdfName.Type, PdfName.SigFieldLock);
    }

    public PdfSigFieldLock setDocumentPermissions(LockPermissions permissions) {
        ((PdfDictionary) getPdfObject()).put(PdfName.f1367P, getLockPermission(permissions));
        return this;
    }

    public PdfSigFieldLock setFieldLock(LockAction action, String... fields) {
        PdfArray fieldsArray = new PdfArray();
        for (String field : fields) {
            fieldsArray.add(new PdfString(field));
        }
        ((PdfDictionary) getPdfObject()).put(PdfName.Action, getLockActionValue(action));
        ((PdfDictionary) getPdfObject()).put(PdfName.Fields, fieldsArray);
        return this;
    }

    public static PdfName getLockActionValue(LockAction action) {
        switch (C14061.$SwitchMap$com$itextpdf$forms$PdfSigFieldLock$LockAction[action.ordinal()]) {
            case 1:
                return PdfName.All;
            case 2:
                return PdfName.Include;
            case 3:
                return PdfName.Exclude;
            default:
                return PdfName.All;
        }
    }

    /* renamed from: com.itextpdf.forms.PdfSigFieldLock$1 */
    static /* synthetic */ class C14061 {
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$forms$PdfSigFieldLock$LockAction;
        static final /* synthetic */ int[] $SwitchMap$com$itextpdf$forms$PdfSigFieldLock$LockPermissions;

        static {
            int[] iArr = new int[LockPermissions.values().length];
            $SwitchMap$com$itextpdf$forms$PdfSigFieldLock$LockPermissions = iArr;
            try {
                iArr[LockPermissions.NO_CHANGES_ALLOWED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$itextpdf$forms$PdfSigFieldLock$LockPermissions[LockPermissions.FORM_FILLING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$itextpdf$forms$PdfSigFieldLock$LockPermissions[LockPermissions.FORM_FILLING_AND_ANNOTATION.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            int[] iArr2 = new int[LockAction.values().length];
            $SwitchMap$com$itextpdf$forms$PdfSigFieldLock$LockAction = iArr2;
            try {
                iArr2[LockAction.ALL.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$itextpdf$forms$PdfSigFieldLock$LockAction[LockAction.INCLUDE.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$com$itextpdf$forms$PdfSigFieldLock$LockAction[LockAction.EXCLUDE.ordinal()] = 3;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    public static PdfNumber getLockPermission(LockPermissions permissions) {
        switch (C14061.$SwitchMap$com$itextpdf$forms$PdfSigFieldLock$LockPermissions[permissions.ordinal()]) {
            case 1:
                return new PdfNumber(1);
            case 2:
                return new PdfNumber(2);
            case 3:
                return new PdfNumber(3);
            default:
                return new PdfNumber(0);
        }
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return true;
    }
}
