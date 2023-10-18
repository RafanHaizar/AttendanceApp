package com.itextpdf.kernel.pdf.action;

import com.itextpdf.kernel.PdfException;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfBoolean;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfObject;
import com.itextpdf.kernel.pdf.PdfObjectWrapper;
import com.itextpdf.kernel.pdf.PdfStream;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.filespec.PdfFileSpec;
import com.itextpdf.kernel.pdf.filespec.PdfStringFS;
import com.itextpdf.kernel.pdf.navigation.PdfDestination;
import com.itextpdf.kernel.pdf.navigation.PdfExplicitDestination;
import com.itextpdf.kernel.pdf.navigation.PdfExplicitRemoteGoToDestination;
import com.itextpdf.kernel.pdf.navigation.PdfStringDestination;
import com.itextpdf.kernel.pdf.navigation.PdfStructureDestination;
import com.itextpdf.p026io.LogMessageConstant;
import java.util.Collection;
import java.util.List;
import org.slf4j.LoggerFactory;

public class PdfAction extends PdfObjectWrapper<PdfDictionary> {
    public static final int RESET_EXCLUDE = 1;
    public static final int SUBMIT_CANONICAL_FORMAT = 512;
    public static final int SUBMIT_COORDINATES = 16;
    public static final int SUBMIT_EMBED_FORM = 8196;
    public static final int SUBMIT_EXCLUDE = 1;
    public static final int SUBMIT_EXCL_F_KEY = 2048;
    public static final int SUBMIT_EXCL_NON_USER_ANNOTS = 1024;
    public static final int SUBMIT_HTML_FORMAT = 4;
    public static final int SUBMIT_HTML_GET = 8;
    public static final int SUBMIT_INCLUDE_ANNOTATIONS = 128;
    public static final int SUBMIT_INCLUDE_APPEND_SAVES = 64;
    public static final int SUBMIT_INCLUDE_NO_VALUE_FIELDS = 2;
    public static final int SUBMIT_PDF = 256;
    public static final int SUBMIT_XFDF = 32;
    private static final long serialVersionUID = -3945353673249710860L;

    public PdfAction() {
        this(new PdfDictionary());
        put(PdfName.Type, PdfName.Action);
    }

    public PdfAction(PdfDictionary pdfObject) {
        super(pdfObject);
        markObjectAsIndirect(getPdfObject());
    }

    public static PdfAction createGoTo(PdfDestination destination) {
        validateNotRemoteDestination(destination);
        return new PdfAction().put(PdfName.f1385S, PdfName.GoTo).put(PdfName.f1312D, destination.getPdfObject());
    }

    public static PdfAction createGoTo(String destination) {
        return createGoTo((PdfDestination) new PdfStringDestination(destination));
    }

    public static PdfAction createGoToR(PdfFileSpec fileSpec, PdfDestination destination, boolean newWindow) {
        return createGoToR(fileSpec, destination).put(PdfName.NewWindow, PdfBoolean.valueOf(newWindow));
    }

    public static PdfAction createGoToR(PdfFileSpec fileSpec, PdfDestination destination) {
        validateRemoteDestination(destination);
        return new PdfAction().put(PdfName.f1385S, PdfName.GoToR).put(PdfName.f1324F, fileSpec.getPdfObject()).put(PdfName.f1312D, destination.getPdfObject());
    }

    public static PdfAction createGoToR(String filename, int pageNum) {
        return createGoToR(filename, pageNum, false);
    }

    public static PdfAction createGoToR(String filename, int pageNum, boolean newWindow) {
        return createGoToR((PdfFileSpec) new PdfStringFS(filename), (PdfDestination) PdfExplicitRemoteGoToDestination.createFitH(pageNum, 10000.0f), newWindow);
    }

    public static PdfAction createGoToR(String filename, String destination, boolean newWindow) {
        return createGoToR((PdfFileSpec) new PdfStringFS(filename), (PdfDestination) new PdfStringDestination(destination), newWindow);
    }

    public static PdfAction createGoToR(String filename, String destination) {
        return createGoToR(filename, destination, false);
    }

    public static PdfAction createGoToE(PdfDestination destination, boolean newWindow, PdfTarget targetDictionary) {
        return createGoToE((PdfFileSpec) null, destination, newWindow, targetDictionary);
    }

    public static PdfAction createGoToE(PdfFileSpec fileSpec, PdfDestination destination, boolean newWindow, PdfTarget targetDictionary) {
        PdfAction action = new PdfAction().put(PdfName.f1385S, PdfName.GoToE).put(PdfName.NewWindow, PdfBoolean.valueOf(newWindow));
        if (fileSpec != null) {
            action.put(PdfName.f1324F, fileSpec.getPdfObject());
        }
        if (destination != null) {
            validateRemoteDestination(destination);
            action.put(PdfName.f1312D, destination.getPdfObject());
        } else {
            LoggerFactory.getLogger((Class<?>) PdfAction.class).warn(LogMessageConstant.EMBEDDED_GO_TO_DESTINATION_NOT_SPECIFIED);
        }
        if (targetDictionary != null) {
            action.put(PdfName.f1391T, targetDictionary.getPdfObject());
        }
        return action;
    }

    public static PdfAction createLaunch(PdfFileSpec fileSpec, boolean newWindow) {
        return createLaunch(fileSpec).put(PdfName.NewWindow, new PdfBoolean(newWindow));
    }

    public static PdfAction createLaunch(PdfFileSpec fileSpec) {
        PdfAction action = new PdfAction().put(PdfName.f1385S, PdfName.Launch);
        if (fileSpec != null) {
            action.put(PdfName.f1324F, fileSpec.getPdfObject());
        }
        return action;
    }

    public static PdfAction createThread(PdfFileSpec fileSpec, PdfObject destinationThread, PdfObject bead) {
        PdfAction action = new PdfAction().put(PdfName.f1385S, PdfName.Launch).put(PdfName.f1312D, destinationThread).put(PdfName.f1293B, bead);
        if (fileSpec != null) {
            action.put(PdfName.f1324F, fileSpec.getPdfObject());
        }
        return action;
    }

    public static PdfAction createThread(PdfFileSpec fileSpec) {
        return createThread(fileSpec, (PdfObject) null, (PdfObject) null);
    }

    public static PdfAction createURI(String uri) {
        return createURI(uri, false);
    }

    public static PdfAction createURI(String uri, boolean isMap) {
        return new PdfAction().put(PdfName.f1385S, PdfName.URI).put(PdfName.URI, new PdfString(uri)).put(PdfName.IsMap, PdfBoolean.valueOf(isMap));
    }

    public static PdfAction createSound(PdfStream sound) {
        return new PdfAction().put(PdfName.f1385S, PdfName.Sound).put(PdfName.Sound, sound);
    }

    public static PdfAction createSound(PdfStream sound, float volume, boolean synchronous, boolean repeat, boolean mix) {
        if (volume >= -1.0f && volume <= 1.0f) {
            return new PdfAction().put(PdfName.f1385S, PdfName.Sound).put(PdfName.Sound, sound).put(PdfName.Volume, new PdfNumber((double) volume)).put(PdfName.Synchronous, PdfBoolean.valueOf(synchronous)).put(PdfName.Repeat, PdfBoolean.valueOf(repeat)).put(PdfName.Mix, PdfBoolean.valueOf(mix));
        }
        throw new IllegalArgumentException("volume");
    }

    public static PdfAction createMovie(PdfAnnotation annotation, String title, PdfName operation) {
        PdfAction action = new PdfAction().put(PdfName.f1385S, PdfName.Movie).put(PdfName.f1391T, new PdfString(title)).put(PdfName.Operation, operation);
        if (annotation != null) {
            action.put(PdfName.Annotation, annotation.getPdfObject());
        }
        return action;
    }

    public static PdfAction createHide(PdfAnnotation annotation, boolean hidden) {
        return new PdfAction().put(PdfName.f1385S, PdfName.Hide).put(PdfName.f1391T, annotation.getPdfObject()).put(PdfName.f1331H, PdfBoolean.valueOf(hidden));
    }

    public static PdfAction createHide(PdfAnnotation[] annotations, boolean hidden) {
        return new PdfAction().put(PdfName.f1385S, PdfName.Hide).put(PdfName.f1391T, getPdfArrayFromAnnotationsList(annotations)).put(PdfName.f1331H, PdfBoolean.valueOf(hidden));
    }

    public static PdfAction createHide(String text, boolean hidden) {
        return new PdfAction().put(PdfName.f1385S, PdfName.Hide).put(PdfName.f1391T, new PdfString(text)).put(PdfName.f1331H, PdfBoolean.valueOf(hidden));
    }

    public static PdfAction createHide(String[] text, boolean hidden) {
        return new PdfAction().put(PdfName.f1385S, PdfName.Hide).put(PdfName.f1391T, getArrayFromStringList(text)).put(PdfName.f1331H, PdfBoolean.valueOf(hidden));
    }

    public static PdfAction createNamed(PdfName namedAction) {
        return new PdfAction().put(PdfName.f1385S, PdfName.Named).put(PdfName.f1357N, namedAction);
    }

    public static PdfAction createSetOcgState(List<PdfActionOcgState> states) {
        return createSetOcgState(states, false);
    }

    public static PdfAction createSetOcgState(List<PdfActionOcgState> states, boolean preserveRb) {
        PdfArray stateArr = new PdfArray();
        for (PdfActionOcgState state : states) {
            stateArr.addAll((Collection<PdfObject>) state.getObjectList());
        }
        return new PdfAction().put(PdfName.f1385S, PdfName.SetOCGState).put(PdfName.State, stateArr).put(PdfName.PreserveRB, PdfBoolean.valueOf(preserveRb));
    }

    public static PdfAction createRendition(String file, PdfFileSpec fileSpec, String mimeType, PdfAnnotation screenAnnotation) {
        return new PdfAction().put(PdfName.f1385S, PdfName.Rendition).put(PdfName.f1365OP, new PdfNumber(0)).put(PdfName.f1290AN, screenAnnotation.getPdfObject()).put(PdfName.f1376R, new PdfRendition(file, fileSpec, mimeType).getPdfObject());
    }

    public static PdfAction createJavaScript(String javaScript) {
        return new PdfAction().put(PdfName.f1385S, PdfName.JavaScript).put(PdfName.f1343JS, new PdfString(javaScript));
    }

    public static PdfAction createSubmitForm(String file, Object[] names, int flags) {
        PdfAction action = new PdfAction();
        action.put(PdfName.f1385S, PdfName.SubmitForm);
        PdfDictionary urlFileSpec = new PdfDictionary();
        urlFileSpec.put(PdfName.f1324F, new PdfString(file));
        urlFileSpec.put(PdfName.f1326FS, PdfName.URL);
        action.put(PdfName.f1324F, urlFileSpec);
        if (names != null) {
            action.put(PdfName.Fields, buildArray(names));
        }
        action.put(PdfName.Flags, new PdfNumber(flags));
        return action;
    }

    public static PdfAction createResetForm(Object[] names, int flags) {
        PdfAction action = new PdfAction();
        action.put(PdfName.f1385S, PdfName.ResetForm);
        if (names != null) {
            action.put(PdfName.Fields, buildArray(names));
        }
        action.put(PdfName.Flags, new PdfNumber(flags));
        return action;
    }

    public static void setAdditionalAction(PdfObjectWrapper<PdfDictionary> wrapper, PdfName key, PdfAction action) {
        PdfDictionary dic;
        PdfObject obj = wrapper.getPdfObject().get(PdfName.f1288AA);
        boolean aaExists = obj != null && obj.isDictionary();
        if (aaExists) {
            dic = (PdfDictionary) obj;
        } else {
            dic = new PdfDictionary();
        }
        dic.put(key, action.getPdfObject());
        dic.setModified();
        wrapper.getPdfObject().put(PdfName.f1288AA, dic);
        if (!aaExists || !dic.isIndirect()) {
            wrapper.getPdfObject().setModified();
        }
    }

    public void next(PdfAction nextAction) {
        PdfObject currentNextAction = ((PdfDictionary) getPdfObject()).get(PdfName.Next);
        if (currentNextAction == null) {
            put(PdfName.Next, nextAction.getPdfObject());
        } else if (currentNextAction.isDictionary()) {
            PdfArray array = new PdfArray(currentNextAction);
            array.add(nextAction.getPdfObject());
            put(PdfName.Next, array);
        } else {
            ((PdfArray) currentNextAction).add(nextAction.getPdfObject());
        }
    }

    public PdfAction put(PdfName key, PdfObject value) {
        ((PdfDictionary) getPdfObject()).put(key, value);
        setModified();
        return this;
    }

    public void flush() {
        super.flush();
    }

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return true;
    }

    private static PdfArray getPdfArrayFromAnnotationsList(PdfAnnotation[] wrappers) {
        PdfArray arr = new PdfArray();
        for (PdfAnnotation wrapper : wrappers) {
            arr.add(wrapper.getPdfObject());
        }
        return arr;
    }

    private static PdfArray getArrayFromStringList(String[] strings) {
        PdfArray arr = new PdfArray();
        for (String string : strings) {
            arr.add(new PdfString(string));
        }
        return arr;
    }

    private static PdfArray buildArray(Object[] names) {
        PdfArray array = new PdfArray();
        for (String str : names) {
            if (str instanceof String) {
                array.add(new PdfString(str));
            } else if (str instanceof PdfAnnotation) {
                array.add(str.getPdfObject());
            } else {
                throw new PdfException("The array must contain string or PDFAnnotation");
            }
        }
        return array;
    }

    private static void validateRemoteDestination(PdfDestination destination) {
        if (destination instanceof PdfExplicitDestination) {
            if (((PdfArray) destination.getPdfObject()).get(0).isDictionary()) {
                throw new IllegalArgumentException("Explicit destinations shall specify page number in remote go-to actions instead of page dictionary");
            }
        } else if (destination instanceof PdfStructureDestination) {
            PdfObject firstObj = ((PdfArray) destination.getPdfObject()).get(0);
            if (firstObj.isDictionary()) {
                PdfString id = ((PdfDictionary) firstObj).getAsString(PdfName.f1341ID);
                if (id != null) {
                    LoggerFactory.getLogger((Class<?>) PdfAction.class).warn(LogMessageConstant.STRUCTURE_ELEMENT_REPLACED_BY_ITS_ID_IN_STRUCTURE_DESTINATION);
                    ((PdfArray) destination.getPdfObject()).set(0, id);
                    destination.getPdfObject().setModified();
                    return;
                }
                throw new IllegalArgumentException("Structure destinations shall specify structure element ID in remote go-to actions. Structure element that has no ID is specified instead");
            }
        }
    }

    public static void validateNotRemoteDestination(PdfDestination destination) {
        Class<PdfAction> cls = PdfAction.class;
        if (destination instanceof PdfExplicitRemoteGoToDestination) {
            LoggerFactory.getLogger((Class<?>) cls).warn(LogMessageConstant.INVALID_DESTINATION_TYPE);
        } else if ((destination instanceof PdfExplicitDestination) && ((PdfArray) destination.getPdfObject()).get(0).isNumber()) {
            LoggerFactory.getLogger((Class<?>) cls).warn(LogMessageConstant.INVALID_DESTINATION_TYPE);
        }
    }
}
