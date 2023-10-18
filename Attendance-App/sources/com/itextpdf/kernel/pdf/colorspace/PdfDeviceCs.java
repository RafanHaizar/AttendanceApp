package com.itextpdf.kernel.pdf.colorspace;

import com.itextpdf.kernel.pdf.PdfName;

public abstract class PdfDeviceCs extends PdfColorSpace {
    private static final long serialVersionUID = 6884911248656287064L;

    /* access modifiers changed from: protected */
    public boolean isWrappedObjectMustBeIndirect() {
        return false;
    }

    protected PdfDeviceCs(PdfName pdfObject) {
        super(pdfObject);
    }

    public static class Gray extends PdfDeviceCs {
        private static final long serialVersionUID = 2722906212276665191L;

        public Gray() {
            super(PdfName.DeviceGray);
        }

        public int getNumberOfComponents() {
            return 1;
        }
    }

    public static class Rgb extends PdfDeviceCs {
        private static final long serialVersionUID = -1605044540582561428L;

        public Rgb() {
            super(PdfName.DeviceRGB);
        }

        public int getNumberOfComponents() {
            return 3;
        }
    }

    public static class Cmyk extends PdfDeviceCs {
        private static final long serialVersionUID = 2615036909699704719L;

        public Cmyk() {
            super(PdfName.DeviceCMYK);
        }

        public int getNumberOfComponents() {
            return 4;
        }
    }
}
