package com.itextpdf.kernel.pdf.annot;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.PdfStream;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PdfSoundAnnotation extends PdfMarkupAnnotation {
    private static final long serialVersionUID = -2319779211858842136L;

    public PdfSoundAnnotation(Rectangle rect, PdfStream sound) {
        super(rect);
        put(PdfName.Sound, sound);
    }

    protected PdfSoundAnnotation(PdfDictionary pdfObject) {
        super(pdfObject);
    }

    public PdfSoundAnnotation(PdfDocument document, Rectangle rect, InputStream soundStream, float sampleRate, PdfName encoding, int channels, int sampleSizeInBits) throws IOException {
        super(rect);
        PdfStream sound = new PdfStream(document, correctWavFile(soundStream));
        sound.put(PdfName.f1376R, new PdfNumber((double) sampleRate));
        sound.put(PdfName.f1320E, encoding);
        sound.put(PdfName.f1293B, new PdfNumber(sampleSizeInBits));
        sound.put(PdfName.f1300C, new PdfNumber(channels));
        put(PdfName.Sound, sound);
    }

    public PdfName getSubtype() {
        return PdfName.Sound;
    }

    public PdfStream getSound() {
        return ((PdfDictionary) getPdfObject()).getAsStream(PdfName.Sound);
    }

    private static InputStream correctWavFile(InputStream is) throws IOException {
        String header = "";
        InputStream bufferedIn = new BufferedInputStream(is);
        bufferedIn.mark(0);
        for (int i = 0; i < 4; i++) {
            header = header + ((char) bufferedIn.read());
        }
        bufferedIn.reset();
        if ("RIFF".equals(header)) {
            bufferedIn.read();
        }
        return bufferedIn;
    }

    public PdfName getIconName() {
        return ((PdfDictionary) getPdfObject()).getAsName(PdfName.Name);
    }

    public PdfSoundAnnotation setIconName(PdfName name) {
        return (PdfSoundAnnotation) put(PdfName.Name, name);
    }
}
