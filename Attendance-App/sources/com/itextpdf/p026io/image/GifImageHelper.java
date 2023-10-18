package com.itextpdf.p026io.image;

import com.itextpdf.p026io.font.PdfEncodings;
import com.itextpdf.p026io.util.StreamUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.itextpdf.io.image.GifImageHelper */
public final class GifImageHelper {
    static final int MAX_STACK_SIZE = 4096;

    /* renamed from: com.itextpdf.io.image.GifImageHelper$GifParameters */
    private static class GifParameters {
        int bgColor;
        int bgIndex;
        byte[] block = new byte[256];
        int blockSize = 0;
        int currentFrame;
        int delay = 0;
        int dispose = 0;
        byte[] fromData;
        URL fromUrl;
        boolean gctFlag;

        /* renamed from: ih */
        int f1218ih;
        GifImageData image;
        InputStream input;
        boolean interlace;

        /* renamed from: iw */
        int f1219iw;

        /* renamed from: ix */
        int f1220ix;

        /* renamed from: iy */
        int f1221iy;
        boolean lctFlag;
        int lctSize;
        int m_bpc;
        byte[] m_curr_table;
        int m_gbpc;
        byte[] m_global_table;
        int m_line_stride;
        byte[] m_local_table;
        byte[] m_out;
        int pixelAspect;
        byte[] pixelStack;
        byte[] pixels;
        short[] prefix;
        byte[] suffix;
        int transIndex;
        boolean transparency = false;

        public GifParameters(GifImageData image2) {
            this.image = image2;
        }
    }

    public static void processImage(GifImageData image) {
        processImage(image, -1);
    }

    public static void processImage(GifImageData image, int lastFrameNumber) {
        GifParameters gif = new GifParameters(image);
        try {
            if (image.getData() == null) {
                image.loadData();
            }
            process(new ByteArrayInputStream(image.getData()), gif, lastFrameNumber);
        } catch (IOException e) {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.GifImageException, (Throwable) e);
        }
    }

    private static void process(InputStream stream, GifParameters gif, int lastFrameNumber) throws IOException {
        gif.input = stream;
        readHeader(gif);
        readContents(gif, lastFrameNumber);
        if (gif.currentFrame <= lastFrameNumber) {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.CannotFind1Frame).setMessageParams(Integer.valueOf(lastFrameNumber));
        }
    }

    private static void readHeader(GifParameters gif) throws IOException {
        StringBuilder id = new StringBuilder("");
        for (int i = 0; i < 6; i++) {
            id.append((char) gif.input.read());
        }
        if (id.toString().startsWith("GIF8")) {
            readLSD(gif);
            if (gif.gctFlag) {
                gif.m_global_table = readColorTable(gif.m_gbpc, gif);
                return;
            }
            return;
        }
        throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.GifSignatureNotFound);
    }

    private static void readLSD(GifParameters gif) throws IOException {
        gif.image.setLogicalWidth((float) readShort(gif));
        gif.image.setLogicalHeight((float) readShort(gif));
        int packed = gif.input.read();
        gif.gctFlag = (packed & 128) != 0;
        gif.m_gbpc = (packed & 7) + 1;
        gif.bgIndex = gif.input.read();
        gif.pixelAspect = gif.input.read();
    }

    private static int readShort(GifParameters gif) throws IOException {
        return gif.input.read() | (gif.input.read() << 8);
    }

    private static int readBlock(GifParameters gif) throws IOException {
        gif.blockSize = gif.input.read();
        if (gif.blockSize <= 0) {
            gif.blockSize = 0;
            return 0;
        }
        gif.blockSize = gif.input.read(gif.block, 0, gif.blockSize);
        return gif.blockSize;
    }

    private static byte[] readColorTable(int bpc, GifParameters gif) throws IOException {
        byte[] table = new byte[((1 << newBpc(bpc)) * 3)];
        StreamUtil.readFully(gif.input, table, 0, (1 << bpc) * 3);
        return table;
    }

    private static int newBpc(int bpc) {
        switch (bpc) {
            case 1:
            case 2:
            case 4:
                return bpc;
            case 3:
                return 4;
            default:
                return 8;
        }
    }

    private static void readContents(GifParameters gif, int lastFrameNumber) throws IOException {
        boolean done = false;
        gif.currentFrame = 0;
        while (!done) {
            switch (gif.input.read()) {
                case 33:
                    switch (gif.input.read()) {
                        case 249:
                            readGraphicControlExt(gif);
                            break;
                        case 255:
                            readBlock(gif);
                            skip(gif);
                            break;
                        default:
                            skip(gif);
                            break;
                    }
                case 44:
                    readFrame(gif);
                    if (gif.currentFrame == lastFrameNumber) {
                        done = true;
                    }
                    gif.currentFrame++;
                    break;
                default:
                    done = true;
                    break;
            }
        }
    }

    private static void readFrame(GifParameters gif) throws IOException {
        GifParameters gifParameters = gif;
        gifParameters.f1220ix = readShort(gif);
        gifParameters.f1221iy = readShort(gif);
        gifParameters.f1219iw = readShort(gif);
        gifParameters.f1218ih = readShort(gif);
        int packed = gifParameters.input.read();
        gifParameters.lctFlag = (packed & 128) != 0;
        gifParameters.interlace = (packed & 64) != 0;
        gifParameters.lctSize = 2 << (packed & 7);
        gifParameters.m_bpc = newBpc(gifParameters.m_gbpc);
        if (gifParameters.lctFlag) {
            gifParameters.m_curr_table = readColorTable((packed & 7) + 1, gifParameters);
            gifParameters.m_bpc = newBpc((packed & 7) + 1);
        } else {
            gifParameters.m_curr_table = gifParameters.m_global_table;
        }
        if (gifParameters.transparency && gifParameters.transIndex >= gifParameters.m_curr_table.length / 3) {
            gifParameters.transparency = false;
        }
        if (gifParameters.transparency && gifParameters.m_bpc == 1) {
            byte[] tp = new byte[12];
            System.arraycopy(gifParameters.m_curr_table, 0, tp, 0, 6);
            gifParameters.m_curr_table = tp;
            gifParameters.m_bpc = 2;
        }
        if (!decodeImageData(gif)) {
            skip(gif);
        }
        try {
            Object[] colorspace = {"/Indexed", "/DeviceRGB", Integer.valueOf((gifParameters.m_curr_table.length / 3) - 1), PdfEncodings.convertToString(gifParameters.m_curr_table, (String) null)};
            Map<String, Object> ad = new HashMap<>();
            ad.put("ColorSpace", colorspace);
            RawImageData img = new RawImageData(gifParameters.m_out, ImageType.GIF);
            RawImageHelper.updateRawImageParameters(img, gifParameters.f1219iw, gifParameters.f1218ih, 1, gifParameters.m_bpc, gifParameters.m_out);
            RawImageHelper.updateImageAttributes(img, ad);
            gifParameters.image.addFrame(img);
            if (gifParameters.transparency) {
                img.setTransparency(new int[]{gifParameters.transIndex, gifParameters.transIndex});
            }
        } catch (Exception e) {
            throw new com.itextpdf.p026io.IOException(com.itextpdf.p026io.IOException.GifImageException, (Throwable) e);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v11, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v12, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v14, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r27v3, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v11, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r24v3, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v18, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r28v3, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v23, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r24v5, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v34, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v35, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v29, resolved type: byte} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v30, resolved type: byte} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean decodeImageData(com.itextpdf.p026io.image.GifImageHelper.GifParameters r33) throws java.io.IOException {
        /*
            r0 = r33
            r1 = -1
            int r2 = r0.f1219iw
            int r3 = r0.f1218ih
            int r2 = r2 * r3
            r3 = 0
            short[] r4 = r0.prefix
            r5 = 4096(0x1000, float:5.74E-42)
            if (r4 != 0) goto L_0x0014
            short[] r4 = new short[r5]
            r0.prefix = r4
        L_0x0014:
            byte[] r4 = r0.suffix
            if (r4 != 0) goto L_0x001c
            byte[] r4 = new byte[r5]
            r0.suffix = r4
        L_0x001c:
            byte[] r4 = r0.pixelStack
            if (r4 != 0) goto L_0x0026
            r4 = 4097(0x1001, float:5.741E-42)
            byte[] r4 = new byte[r4]
            r0.pixelStack = r4
        L_0x0026:
            int r4 = r0.f1219iw
            int r6 = r0.m_bpc
            int r4 = r4 * r6
            int r4 = r4 + 7
            r6 = 8
            int r4 = r4 / r6
            r0.m_line_stride = r4
            int r4 = r0.m_line_stride
            int r7 = r0.f1218ih
            int r4 = r4 * r7
            byte[] r4 = new byte[r4]
            r0.m_out = r4
            r4 = 1
            boolean r7 = r0.interlace
            r8 = 1
            if (r7 == 0) goto L_0x0044
            goto L_0x0045
        L_0x0044:
            r6 = 1
        L_0x0045:
            r7 = 0
            r9 = 0
            java.io.InputStream r10 = r0.input
            int r10 = r10.read()
            int r11 = r8 << r10
            int r12 = r11 + 1
            int r13 = r11 + 2
            r14 = r1
            int r15 = r10 + 1
            int r16 = r8 << r15
            int r16 = r16 + -1
            r17 = 0
            r5 = r17
        L_0x005e:
            r17 = 0
            if (r5 >= r11) goto L_0x0073
            short[] r8 = r0.prefix
            r8[r5] = r17
            byte[] r8 = r0.suffix
            r20 = r3
            byte r3 = (byte) r5
            r8[r5] = r3
            int r5 = r5 + 1
            r3 = r20
            r8 = 1
            goto L_0x005e
        L_0x0073:
            r20 = r3
            r3 = r17
            r8 = r17
            r21 = r17
            r22 = r17
            r23 = r17
            r24 = 0
            r30 = r21
            r21 = r3
            r3 = r30
            r31 = r23
            r23 = r4
            r4 = r31
            r32 = r24
            r24 = r5
            r5 = r32
        L_0x0093:
            if (r5 >= r2) goto L_0x01eb
            if (r8 != 0) goto L_0x0188
            if (r4 >= r15) goto L_0x00c4
            if (r22 != 0) goto L_0x00af
            int r22 = readBlock(r33)
            if (r22 > 0) goto L_0x00ad
            r18 = 1
            r26 = r1
            r25 = r2
            r29 = r3
            r3 = r18
            goto L_0x01f3
        L_0x00ad:
            r21 = 0
        L_0x00af:
            r25 = r2
            byte[] r2 = r0.block
            byte r2 = r2[r21]
            r2 = r2 & 255(0xff, float:3.57E-43)
            int r2 = r2 << r4
            int r17 = r17 + r2
            int r4 = r4 + 8
            r2 = 1
            int r21 = r21 + 1
            int r22 = r22 + -1
            r2 = r25
            goto L_0x0093
        L_0x00c4:
            r25 = r2
            r2 = r17 & r16
            int r17 = r17 >> r15
            int r4 = r4 - r15
            if (r2 > r13) goto L_0x0178
            if (r2 != r12) goto L_0x00d9
            r26 = r1
            r28 = r2
            r29 = r3
            r27 = r4
            goto L_0x0180
        L_0x00d9:
            if (r2 != r11) goto L_0x00eb
            int r15 = r10 + 1
            r19 = 1
            int r24 = r19 << r15
            int r16 = r24 + -1
            int r13 = r11 + 2
            r14 = r1
            r24 = r2
            r2 = r25
            goto L_0x0093
        L_0x00eb:
            if (r14 != r1) goto L_0x0108
            r26 = r1
            byte[] r1 = r0.pixelStack
            int r24 = r8 + 1
            r27 = r4
            byte[] r4 = r0.suffix
            byte r4 = r4[r2]
            r1[r8] = r4
            r14 = r2
            r3 = r2
            r8 = r24
            r1 = r26
            r4 = r27
            r24 = r2
            r2 = r25
            goto L_0x0093
        L_0x0108:
            r26 = r1
            r27 = r4
            r1 = r2
            if (r2 != r13) goto L_0x011c
            byte[] r4 = r0.pixelStack
            int r24 = r8 + 1
            r28 = r2
            byte r2 = (byte) r3
            r4[r8] = r2
            r2 = r14
            r8 = r24
            goto L_0x011e
        L_0x011c:
            r28 = r2
        L_0x011e:
            if (r2 <= r11) goto L_0x0135
            byte[] r4 = r0.pixelStack
            int r24 = r8 + 1
            r29 = r3
            byte[] r3 = r0.suffix
            byte r3 = r3[r2]
            r4[r8] = r3
            short[] r3 = r0.prefix
            short r2 = r3[r2]
            r8 = r24
            r3 = r29
            goto L_0x011e
        L_0x0135:
            r29 = r3
            byte[] r3 = r0.suffix
            byte r3 = r3[r2]
            r3 = r3 & 255(0xff, float:3.57E-43)
            r4 = 4096(0x1000, float:5.74E-42)
            if (r13 < r4) goto L_0x014b
            r24 = r2
            r29 = r3
            r3 = r20
            r4 = r27
            goto L_0x01f3
        L_0x014b:
            byte[] r4 = r0.pixelStack
            int r24 = r8 + 1
            r28 = r2
            byte r2 = (byte) r3
            r4[r8] = r2
            short[] r2 = r0.prefix
            short r4 = (short) r14
            r2[r13] = r4
            byte[] r2 = r0.suffix
            byte r4 = (byte) r3
            r2[r13] = r4
            int r13 = r13 + 1
            r2 = r13 & r16
            if (r2 != 0) goto L_0x016d
            r2 = 4096(0x1000, float:5.74E-42)
            if (r13 >= r2) goto L_0x016f
            int r15 = r15 + 1
            int r16 = r16 + r13
            goto L_0x016f
        L_0x016d:
            r2 = 4096(0x1000, float:5.74E-42)
        L_0x016f:
            r4 = r1
            r14 = r4
            r8 = r24
            r4 = r27
            r24 = r28
            goto L_0x0190
        L_0x0178:
            r26 = r1
            r28 = r2
            r29 = r3
            r27 = r4
        L_0x0180:
            r3 = r20
            r4 = r27
            r24 = r28
            goto L_0x01f3
        L_0x0188:
            r26 = r1
            r25 = r2
            r29 = r3
            r2 = 4096(0x1000, float:5.74E-42)
        L_0x0190:
            int r8 = r8 + -1
            int r5 = r5 + 1
            byte[] r1 = r0.pixelStack
            byte r1 = r1[r8]
            setPixel(r9, r7, r1, r0)
            int r9 = r9 + 1
            int r1 = r0.f1219iw
            if (r9 < r1) goto L_0x01e3
            r9 = 0
            int r7 = r7 + r6
            int r1 = r0.f1218ih
            if (r7 < r1) goto L_0x01db
            boolean r1 = r0.interlace
            if (r1 == 0) goto L_0x01ce
        L_0x01ab:
            r1 = 1
            int r23 = r23 + 1
            switch(r23) {
                case 2: goto L_0x01c2;
                case 3: goto L_0x01be;
                case 4: goto L_0x01ba;
                default: goto L_0x01b1;
            }
        L_0x01b1:
            int r1 = r0.f1218ih
            r18 = 1
            int r1 = r1 + -1
            r6 = 0
            r7 = r1
            goto L_0x01c4
        L_0x01ba:
            r1 = 1
            r6 = 2
            r7 = r1
            goto L_0x01c4
        L_0x01be:
            r1 = 2
            r6 = 4
            r7 = r1
            goto L_0x01c4
        L_0x01c2:
            r1 = 4
            r7 = r1
        L_0x01c4:
            int r1 = r0.f1218ih
            if (r7 >= r1) goto L_0x01ab
            r2 = r25
            r1 = r26
            goto L_0x0093
        L_0x01ce:
            int r1 = r0.f1218ih
            r18 = 1
            int r7 = r1 + -1
            r6 = 0
            r2 = r25
            r1 = r26
            goto L_0x0093
        L_0x01db:
            r18 = 1
            r2 = r25
            r1 = r26
            goto L_0x0093
        L_0x01e3:
            r18 = 1
            r2 = r25
            r1 = r26
            goto L_0x0093
        L_0x01eb:
            r26 = r1
            r25 = r2
            r29 = r3
            r3 = r20
        L_0x01f3:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.image.GifImageHelper.decodeImageData(com.itextpdf.io.image.GifImageHelper$GifParameters):boolean");
    }

    private static void setPixel(int x, int y, int v, GifParameters gif) {
        if (gif.m_bpc == 8) {
            gif.m_out[(gif.f1219iw * y) + x] = (byte) v;
            return;
        }
        int pos = (gif.m_line_stride * y) + (x / (8 / gif.m_bpc));
        byte[] bArr = gif.m_out;
        bArr[pos] = (byte) (bArr[pos] | ((byte) (v << ((8 - (gif.m_bpc * (x % (8 / gif.m_bpc)))) - gif.m_bpc))));
    }

    private static void readGraphicControlExt(GifParameters gif) throws IOException {
        gif.input.read();
        int packed = gif.input.read();
        gif.dispose = (packed & 28) >> 2;
        boolean z = true;
        if (gif.dispose == 0) {
            gif.dispose = 1;
        }
        if ((packed & 1) == 0) {
            z = false;
        }
        gif.transparency = z;
        gif.delay = readShort(gif) * 10;
        gif.transIndex = gif.input.read();
        gif.input.read();
    }

    private static void skip(GifParameters gif) throws IOException {
        do {
            readBlock(gif);
        } while (gif.blockSize > 0);
    }
}
