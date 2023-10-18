package com.itextpdf.p026io.image;

import com.itextpdf.p026io.image.Jpeg2000ImageData;
import com.itextpdf.p026io.util.StreamUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/* renamed from: com.itextpdf.io.image.Jpeg2000ImageHelper */
final class Jpeg2000ImageHelper {
    private static final int JP2_BPCC = 1651532643;
    private static final int JP2_COLR = 1668246642;
    private static final int JP2_DBTL = 1685348972;
    private static final int JP2_FTYP = 1718909296;
    private static final int JP2_IHDR = 1768449138;
    private static final int JP2_JP = 1783636000;
    private static final int JP2_JP2 = 1785737760;
    private static final int JP2_JP2C = 1785737827;
    private static final int JP2_JP2H = 1785737832;
    private static final int JP2_URL = 1970433056;
    private static final int JPIP_JPIP = 1785751920;
    private static final int JPX_JPXB = 1785755746;

    Jpeg2000ImageHelper() {
    }

    /* renamed from: com.itextpdf.io.image.Jpeg2000ImageHelper$Jpeg2000Box */
    private static class Jpeg2000Box {
        int length;
        int type;

        private Jpeg2000Box() {
        }
    }

    /* renamed from: com.itextpdf.io.image.Jpeg2000ImageHelper$ZeroBoxSizeException */
    private static class ZeroBoxSizeException extends IOException {
        ZeroBoxSizeException(String s) {
            super(s);
        }
    }

    public static void processImage(ImageData image) {
        if (image.getOriginalType() == ImageType.JPEG2000) {
            processParameters((Jpeg2000ImageData) image);
            image.setFilter("JPXDecode");
            return;
        }
        throw new IllegalArgumentException("JPEG2000 image expected");
    }

    private static void processParameters(Jpeg2000ImageData jp2) {
        jp2.parameters = new Jpeg2000ImageData.Parameters();
        try {
            if (jp2.getData() == null) {
                jp2.loadData();
            }
            InputStream jpeg2000Stream = new ByteArrayInputStream(jp2.getData());
            Jpeg2000Box box = new Jpeg2000Box();
            box.length = cio_read(4, jpeg2000Stream);
            if (box.length == 12) {
                jp2.parameters.isJp2 = true;
                box.type = cio_read(4, jpeg2000Stream);
                if (JP2_JP != box.type) {
                    throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.ExpectedJpMarker);
                } else if (218793738 == cio_read(4, jpeg2000Stream)) {
                    jp2_read_boxhdr(box, jpeg2000Stream);
                    if (JP2_FTYP == box.type) {
                        StreamUtil.skip(jpeg2000Stream, 8);
                        for (int i = 4; i < box.length / 4; i++) {
                            if (cio_read(4, jpeg2000Stream) == JPX_JPXB) {
                                jp2.parameters.isJpxBaseline = true;
                            }
                        }
                        jp2_read_boxhdr(box, jpeg2000Stream);
                        do {
                            if (JP2_JP2H != box.type) {
                                if (box.type != JP2_JP2C) {
                                    StreamUtil.skip(jpeg2000Stream, (long) (box.length - 8));
                                    jp2_read_boxhdr(box, jpeg2000Stream);
                                } else {
                                    throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.ExpectedJp2hMarker);
                                }
                            }
                        } while (JP2_JP2H != box.type);
                        jp2_read_boxhdr(box, jpeg2000Stream);
                        if (JP2_IHDR == box.type) {
                            jp2.setHeight((float) cio_read(4, jpeg2000Stream));
                            jp2.setWidth((float) cio_read(4, jpeg2000Stream));
                            jp2.parameters.numOfComps = cio_read(2, jpeg2000Stream);
                            jp2.setBpc(cio_read(1, jpeg2000Stream));
                            StreamUtil.skip(jpeg2000Stream, 3);
                            jp2_read_boxhdr(box, jpeg2000Stream);
                            if (box.type == JP2_BPCC) {
                                jp2.parameters.bpcBoxData = new byte[(box.length - 8)];
                                jpeg2000Stream.read(jp2.parameters.bpcBoxData, 0, box.length - 8);
                            } else if (box.type == JP2_COLR) {
                                do {
                                    if (jp2.parameters.colorSpecBoxes == null) {
                                        jp2.parameters.colorSpecBoxes = new ArrayList();
                                    }
                                    jp2.parameters.colorSpecBoxes.add(jp2_read_colr(box, jpeg2000Stream));
                                    try {
                                        jp2_read_boxhdr(box, jpeg2000Stream);
                                    } catch (ZeroBoxSizeException e) {
                                    }
                                } while (JP2_COLR == box.type);
                            }
                        } else {
                            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.ExpectedIhdrMarker);
                        }
                    } else {
                        throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.ExpectedFtypMarker);
                    }
                } else {
                    throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.ErrorWithJpMarker);
                }
            } else if (box.length == -11534511) {
                StreamUtil.skip(jpeg2000Stream, 4);
                int x1 = cio_read(4, jpeg2000Stream);
                int y1 = cio_read(4, jpeg2000Stream);
                int x0 = cio_read(4, jpeg2000Stream);
                int y0 = cio_read(4, jpeg2000Stream);
                StreamUtil.skip(jpeg2000Stream, 16);
                jp2.setColorSpace(cio_read(2, jpeg2000Stream));
                jp2.setBpc(8);
                jp2.setHeight((float) (y1 - y0));
                jp2.setWidth((float) (x1 - x0));
            } else {
                throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.InvalidJpeg2000File);
            }
        } catch (IOException e2) {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.Jpeg2000ImageException, (Throwable) e2);
        }
    }

    private static Jpeg2000ImageData.ColorSpecBox jp2_read_colr(Jpeg2000Box box, InputStream jpeg2000Stream) throws IOException {
        int readBytes = 8;
        Jpeg2000ImageData.ColorSpecBox colorSpecBox = new Jpeg2000ImageData.ColorSpecBox();
        for (int i = 0; i < 3; i++) {
            colorSpecBox.add(Integer.valueOf(cio_read(1, jpeg2000Stream)));
            readBytes++;
        }
        if (colorSpecBox.getMeth() == 1) {
            colorSpecBox.add(Integer.valueOf(cio_read(4, jpeg2000Stream)));
            readBytes += 4;
        } else {
            colorSpecBox.add(0);
        }
        if (box.length - readBytes > 0) {
            byte[] colorProfile = new byte[(box.length - readBytes)];
            jpeg2000Stream.read(colorProfile, 0, box.length - readBytes);
            colorSpecBox.setColorProfile(colorProfile);
        }
        return colorSpecBox;
    }

    private static void jp2_read_boxhdr(Jpeg2000Box box, InputStream jpeg2000Stream) throws IOException {
        box.length = cio_read(4, jpeg2000Stream);
        box.type = cio_read(4, jpeg2000Stream);
        if (box.length == 1) {
            if (cio_read(4, jpeg2000Stream) == 0) {
                box.length = cio_read(4, jpeg2000Stream);
                if (box.length == 0) {
                    throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.UnsupportedBoxSizeEqEq0);
                }
                return;
            }
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.CannotHandleBoxSizesHigherThan2_32);
        } else if (box.length == 0) {
            throw new ZeroBoxSizeException("Unsupported box size == 0");
        }
    }

    private static int cio_read(int n, InputStream jpeg2000Stream) throws IOException {
        int v = 0;
        for (int i = n - 1; i >= 0; i--) {
            v += jpeg2000Stream.read() << (i << 3);
        }
        return v;
    }
}
