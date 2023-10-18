package com.itextpdf.p026io.font;

import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.p026io.font.CFFFont;
import com.itextpdf.p026io.source.RandomAccessFileOrArray;
import com.itextpdf.p026io.util.GenericArray;
import com.itextpdf.styledxmlparser.css.media.MediaRuleConstants;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import kotlin.text.Typography;

/* renamed from: com.itextpdf.io.font.CFFFontSubset */
public class CFFFontSubset extends CFFFont {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final byte ENDCHAR_OP = 14;
    static final byte RETURN_OP = 11;
    static final String[] SubrsEscapeFuncs = {"RESERVED_0", "RESERVED_1", "RESERVED_2", MediaRuleConstants.AND, "or", "not", "RESERVED_6", "RESERVED_7", "RESERVED_8", "abs", "add", "sub", "div", "RESERVED_13", "neg", "eq", "RESERVED_16", "RESERVED_17", "drop", "RESERVED_19", "put", "get", "ifelse", "random", "mul", "RESERVED_25", "sqrt", "dup", "exch", "index", "roll", "RESERVED_31", "RESERVED_32", "RESERVED_33", "hflex", "flex", "hflex1", "flex1", "RESERVED_REST"};
    static final String[] SubrsFunctions = {"RESERVED_0", "hstem", "RESERVED_2", "vstem", "vmoveto", "rlineto", "hlineto", "vlineto", "rrcurveto", "RESERVED_9", "callsubr", "return", "escape", "RESERVED_13", "endchar", "RESERVED_15", "RESERVED_16", "RESERVED_17", "hstemhm", "hintmask", "cntrmask", "rmoveto", "hmoveto", "vstemhm", "rcurveline", "rlinecurve", "vvcurveto", "hhcurveto", "shortint", "callgsubr", "vhcurveto", "hvcurveto"};
    Set<Integer> FDArrayUsed = new HashSet();
    int GBias = 0;
    Set<Integer> GlyphsUsed;
    byte[] NewCharStringsIndex;
    byte[] NewGSubrsIndex;
    byte[][] NewLSubrsIndex;
    byte[] NewSubrsIndexNonCID;
    int NumOfHints = 0;
    LinkedList<CFFFont.Item> OutputList;
    List<Integer> glyphsInList;
    Set<Integer> hGSubrsUsed = new HashSet();
    GenericArray<Set<Integer>> hSubrsUsed;
    Set<Integer> hSubrsUsedNonCID = new HashSet();
    List<Integer> lGSubrsUsed = new ArrayList();
    GenericArray<List<Integer>> lSubrsUsed;
    List<Integer> lSubrsUsedNonCID = new ArrayList();

    public CFFFontSubset(byte[] cff, Set<Integer> GlyphsUsed2) {
        super(cff);
        this.GlyphsUsed = GlyphsUsed2;
        this.glyphsInList = new ArrayList(GlyphsUsed2);
        for (int i = 0; i < this.fonts.length; i++) {
            seek(this.fonts[i].charstringsOffset);
            this.fonts[i].nglyphs = getCard16();
            seek(this.stringIndexOffset);
            this.fonts[i].nstrings = getCard16() + standardStrings.length;
            this.fonts[i].charstringsOffsets = getIndex(this.fonts[i].charstringsOffset);
            if (this.fonts[i].fdselectOffset >= 0) {
                readFDSelect(i);
                BuildFDArrayUsed(i);
            }
            if (this.fonts[i].isCID) {
                ReadFDArray(i);
            }
            this.fonts[i].CharsetLength = CountCharset(this.fonts[i].charsetOffset, this.fonts[i].nglyphs);
        }
    }

    /* access modifiers changed from: package-private */
    public int CountCharset(int Offset, int NumofGlyphs) {
        seek(Offset);
        switch (getCard8()) {
            case 0:
                return (NumofGlyphs * 2) + 1;
            case 1:
                return (CountRange(NumofGlyphs, 1) * 3) + 1;
            case 2:
                return (CountRange(NumofGlyphs, 2) * 4) + 1;
            default:
                return 0;
        }
    }

    /* access modifiers changed from: package-private */
    public int CountRange(int NumofGlyphs, int Type) {
        int nLeft;
        int num = 0;
        int i = 1;
        while (i < NumofGlyphs) {
            num++;
            char card16 = getCard16();
            if (Type == 1) {
                nLeft = getCard8();
            } else {
                nLeft = getCard16();
            }
            i += nLeft + 1;
        }
        return num;
    }

    /* access modifiers changed from: protected */
    public void readFDSelect(int Font) {
        int NumOfGlyphs = this.fonts[Font].nglyphs;
        int[] FDSelect = new int[NumOfGlyphs];
        seek(this.fonts[Font].fdselectOffset);
        this.fonts[Font].FDSelectFormat = getCard8();
        switch (this.fonts[Font].FDSelectFormat) {
            case 0:
                for (int i = 0; i < NumOfGlyphs; i++) {
                    FDSelect[i] = getCard8();
                }
                this.fonts[Font].FDSelectLength = this.fonts[Font].nglyphs + 1;
                break;
            case 3:
                int nRanges = getCard16();
                int l = 0;
                int first = getCard16();
                for (int i2 = 0; i2 < nRanges; i2++) {
                    int fd = getCard8();
                    int last = getCard16();
                    int steps = last - first;
                    for (int k = 0; k < steps; k++) {
                        FDSelect[l] = fd;
                        l++;
                    }
                    first = last;
                }
                this.fonts[Font].FDSelectLength = (nRanges * 3) + 3 + 2;
                break;
        }
        this.fonts[Font].FDSelect = FDSelect;
    }

    /* access modifiers changed from: protected */
    public void BuildFDArrayUsed(int Font) {
        int[] FDSelect = this.fonts[Font].FDSelect;
        for (Integer glyphsInList1 : this.glyphsInList) {
            this.FDArrayUsed.add(Integer.valueOf(FDSelect[glyphsInList1.intValue()]));
        }
    }

    /* access modifiers changed from: protected */
    public void ReadFDArray(int Font) {
        seek(this.fonts[Font].fdarrayOffset);
        this.fonts[Font].FDArrayCount = getCard16();
        this.fonts[Font].FDArrayOffsize = getCard8();
        if (this.fonts[Font].FDArrayOffsize < 4) {
            this.fonts[Font].FDArrayOffsize++;
        }
        this.fonts[Font].FDArrayOffsets = getIndex(this.fonts[Font].fdarrayOffset);
    }

    public byte[] Process(String fontName) {
        int j = 0;
        while (true) {
            try {
                if (j >= this.fonts.length) {
                    break;
                } else if (fontName.equals(this.fonts[j].name)) {
                    break;
                } else {
                    j++;
                }
            } catch (IOException e) {
                throw new com.itextpdf.p026io.IOException("I/O exception.", (Throwable) e);
            } catch (Throwable th) {
                try {
                    this.buf.close();
                } catch (Exception e2) {
                }
                throw th;
            }
        }
        if (j == this.fonts.length) {
            try {
                this.buf.close();
                return null;
            } catch (Exception e3) {
                return null;
            }
        } else {
            if (this.gsubrIndexOffset >= 0) {
                this.GBias = CalcBias(this.gsubrIndexOffset, j);
            }
            BuildNewCharString(j);
            BuildNewLGSubrs(j);
            byte[] BuildNewFile = BuildNewFile(j);
            try {
                this.buf.close();
            } catch (Exception e4) {
            }
            return BuildNewFile;
        }
    }

    public byte[] Process() {
        return Process(getNames()[0]);
    }

    /* access modifiers changed from: protected */
    public int CalcBias(int Offset, int Font) {
        seek(Offset);
        int nSubrs = getCard16();
        if (this.fonts[Font].CharstringType == 1) {
            return 0;
        }
        if (nSubrs < 1240) {
            return 107;
        }
        if (nSubrs < 33900) {
            return 1131;
        }
        return 32768;
    }

    /* access modifiers changed from: protected */
    public void BuildNewCharString(int FontIndex) throws IOException {
        this.NewCharStringsIndex = BuildNewIndex(this.fonts[FontIndex].charstringsOffsets, this.GlyphsUsed, ENDCHAR_OP);
    }

    /* access modifiers changed from: protected */
    public void BuildNewLGSubrs(int Font) throws IOException {
        if (this.fonts[Font].isCID) {
            this.hSubrsUsed = new GenericArray<>(this.fonts[Font].fdprivateOffsets.length);
            this.lSubrsUsed = new GenericArray<>(this.fonts[Font].fdprivateOffsets.length);
            this.NewLSubrsIndex = new byte[this.fonts[Font].fdprivateOffsets.length][];
            this.fonts[Font].PrivateSubrsOffset = new int[this.fonts[Font].fdprivateOffsets.length];
            this.fonts[Font].PrivateSubrsOffsetsArray = new int[this.fonts[Font].fdprivateOffsets.length][];
            List<Integer> FDInList = new ArrayList<>(this.FDArrayUsed);
            for (int j = 0; j < FDInList.size(); j++) {
                int FD = FDInList.get(j).intValue();
                this.hSubrsUsed.set(FD, new HashSet());
                this.lSubrsUsed.set(FD, new ArrayList());
                BuildFDSubrsOffsets(Font, FD);
                if (this.fonts[Font].PrivateSubrsOffset[FD] >= 0) {
                    BuildSubrUsed(Font, FD, this.fonts[Font].PrivateSubrsOffset[FD], this.fonts[Font].PrivateSubrsOffsetsArray[FD], this.hSubrsUsed.get(FD), this.lSubrsUsed.get(FD));
                    this.NewLSubrsIndex[FD] = BuildNewIndex(this.fonts[Font].PrivateSubrsOffsetsArray[FD], this.hSubrsUsed.get(FD), RETURN_OP);
                }
            }
        } else if (this.fonts[Font].privateSubrs >= 0) {
            this.fonts[Font].SubrsOffsets = getIndex(this.fonts[Font].privateSubrs);
            BuildSubrUsed(Font, -1, this.fonts[Font].privateSubrs, this.fonts[Font].SubrsOffsets, this.hSubrsUsedNonCID, this.lSubrsUsedNonCID);
        }
        BuildGSubrsUsed(Font);
        if (this.fonts[Font].privateSubrs >= 0) {
            this.NewSubrsIndexNonCID = BuildNewIndex(this.fonts[Font].SubrsOffsets, this.hSubrsUsedNonCID, RETURN_OP);
        }
        this.NewGSubrsIndex = BuildNewIndexAndCopyAllGSubrs(this.gsubrOffsets, RETURN_OP);
    }

    /* access modifiers changed from: protected */
    public void BuildFDSubrsOffsets(int Font, int FD) {
        this.fonts[Font].PrivateSubrsOffset[FD] = -1;
        seek(this.fonts[Font].fdprivateOffsets[FD]);
        while (getPosition() < this.fonts[Font].fdprivateOffsets[FD] + this.fonts[Font].fdprivateLengths[FD]) {
            getDictItem();
            if ("Subrs".equals(this.key)) {
                this.fonts[Font].PrivateSubrsOffset[FD] = ((Integer) this.args[0]).intValue() + this.fonts[Font].fdprivateOffsets[FD];
            }
        }
        if (this.fonts[Font].PrivateSubrsOffset[FD] >= 0) {
            this.fonts[Font].PrivateSubrsOffsetsArray[FD] = getIndex(this.fonts[Font].PrivateSubrsOffset[FD]);
        }
    }

    /* access modifiers changed from: protected */
    public void BuildSubrUsed(int Font, int FD, int SubrOffset, int[] SubrsOffsets, Set<Integer> hSubr, List<Integer> lSubr) {
        int i = Font;
        int i2 = FD;
        int[] iArr = SubrsOffsets;
        int LBias = CalcBias(SubrOffset, i);
        for (int i3 = 0; i3 < this.glyphsInList.size(); i3++) {
            int glyph = this.glyphsInList.get(i3).intValue();
            int Start = this.fonts[i].charstringsOffsets[glyph];
            int End = this.fonts[i].charstringsOffsets[glyph + 1];
            if (i2 >= 0) {
                EmptyStack();
                this.NumOfHints = 0;
                int GlyphFD = this.fonts[i].FDSelect[glyph];
                if (GlyphFD == i2) {
                    int i4 = GlyphFD;
                    ReadASubr(Start, End, this.GBias, LBias, hSubr, lSubr, SubrsOffsets);
                }
            } else {
                ReadASubr(Start, End, this.GBias, LBias, hSubr, lSubr, SubrsOffsets);
            }
        }
        for (int i5 = 0; i5 < lSubr.size(); i5++) {
            int Subr = lSubr.get(i5).intValue();
            if (Subr >= iArr.length - 1 || Subr < 0) {
            } else {
                int i6 = Subr;
                ReadASubr(iArr[Subr], iArr[Subr + 1], this.GBias, LBias, hSubr, lSubr, SubrsOffsets);
            }
        }
        List<Integer> list = lSubr;
    }

    /* access modifiers changed from: protected */
    public void BuildGSubrsUsed(int Font) {
        int LBias;
        int j;
        int i = Font;
        int SizeOfNonCIDSubrsUsed = 0;
        if (this.fonts[i].privateSubrs >= 0) {
            int LBias2 = CalcBias(this.fonts[i].privateSubrs, i);
            SizeOfNonCIDSubrsUsed = this.lSubrsUsedNonCID.size();
            LBias = LBias2;
        } else {
            LBias = 0;
        }
        int SizeOfNonCIDSubrsUsed2 = SizeOfNonCIDSubrsUsed;
        for (int i2 = 0; i2 < this.lGSubrsUsed.size(); i2++) {
            int Subr = this.lGSubrsUsed.get(i2).intValue();
            if (Subr < this.gsubrOffsets.length - 1 && Subr >= 0) {
                int Start = this.gsubrOffsets[Subr];
                int End = this.gsubrOffsets[Subr + 1];
                if (this.fonts[i].isCID) {
                    ReadASubr(Start, End, this.GBias, 0, this.hGSubrsUsed, this.lGSubrsUsed, (int[]) null);
                } else {
                    ReadASubr(Start, End, this.GBias, LBias, this.hSubrsUsedNonCID, this.lSubrsUsedNonCID, this.fonts[i].SubrsOffsets);
                    if (SizeOfNonCIDSubrsUsed2 < this.lSubrsUsedNonCID.size()) {
                        int j2 = SizeOfNonCIDSubrsUsed2;
                        while (j2 < this.lSubrsUsedNonCID.size()) {
                            int LSubr = this.lSubrsUsedNonCID.get(j2).intValue();
                            if (LSubr >= this.fonts[i].SubrsOffsets.length - 1 || LSubr < 0) {
                                j = j2;
                            } else {
                                int i3 = LSubr;
                                j = j2;
                                ReadASubr(this.fonts[i].SubrsOffsets[LSubr], this.fonts[i].SubrsOffsets[LSubr + 1], this.GBias, LBias, this.hSubrsUsedNonCID, this.lSubrsUsedNonCID, this.fonts[i].SubrsOffsets);
                            }
                            j2 = j + 1;
                        }
                        int i4 = j2;
                        SizeOfNonCIDSubrsUsed2 = this.lSubrsUsedNonCID.size();
                    }
                }
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v2, resolved type: java.lang.Integer} */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0078, code lost:
        if (r0.equals("callgsubr") != false) goto L_0x0090;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void ReadASubr(int r16, int r17, int r18, int r19, java.util.Set<java.lang.Integer> r20, java.util.List<java.lang.Integer> r21, int[] r22) {
        /*
            r15 = this;
            r6 = r15
            r7 = r20
            r15.EmptyStack()
            r8 = 0
            r6.NumOfHints = r8
            r15.seek(r16)
        L_0x000c:
            int r0 = r15.getPosition()
            r9 = r17
            if (r0 >= r9) goto L_0x0149
            r15.ReadCommand()
            int r10 = r15.getPosition()
            r0 = 0
            int r1 = r6.arg_count
            r2 = 1
            if (r1 <= 0) goto L_0x002a
            java.lang.Object[] r1 = r6.args
            int r3 = r6.arg_count
            int r3 = r3 - r2
            r0 = r1[r3]
            r11 = r0
            goto L_0x002b
        L_0x002a:
            r11 = r0
        L_0x002b:
            int r12 = r6.arg_count
            r15.HandelStack()
            java.lang.String r0 = r6.key
            if (r0 == 0) goto L_0x0145
            java.lang.String r0 = r6.key
            int r1 = r0.hashCode()
            switch(r1) {
                case -1473033741: goto L_0x0085;
                case -1284671851: goto L_0x007b;
                case -1038692485: goto L_0x0072;
                case -171694704: goto L_0x0068;
                case 99586865: goto L_0x005e;
                case 112516159: goto L_0x0053;
                case 753849732: goto L_0x0048;
                case 1213700086: goto L_0x003e;
                default: goto L_0x003d;
            }
        L_0x003d:
            goto L_0x008f
        L_0x003e:
            java.lang.String r1 = "hstemhm"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x003d
            r2 = 4
            goto L_0x0090
        L_0x0048:
            java.lang.String r1 = "vstemhm"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x003d
            r2 = 5
            goto L_0x0090
        L_0x0053:
            java.lang.String r1 = "vstem"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x003d
            r2 = 3
            goto L_0x0090
        L_0x005e:
            java.lang.String r1 = "hstem"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x003d
            r2 = 2
            goto L_0x0090
        L_0x0068:
            java.lang.String r1 = "callsubr"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x003d
            r2 = 0
            goto L_0x0090
        L_0x0072:
            java.lang.String r1 = "callgsubr"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x003d
            goto L_0x0090
        L_0x007b:
            java.lang.String r1 = "cntrmask"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x003d
            r2 = 7
            goto L_0x0090
        L_0x0085:
            java.lang.String r1 = "hintmask"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x003d
            r2 = 6
            goto L_0x0090
        L_0x008f:
            r2 = -1
        L_0x0090:
            switch(r2) {
                case 0: goto L_0x0106;
                case 1: goto L_0x00c0;
                case 2: goto L_0x00b5;
                case 3: goto L_0x00b5;
                case 4: goto L_0x00b5;
                case 5: goto L_0x00b5;
                case 6: goto L_0x0097;
                case 7: goto L_0x0097;
                default: goto L_0x0093;
            }
        L_0x0093:
            r14 = r21
            goto L_0x0147
        L_0x0097:
            int r0 = r6.NumOfHints
            int r1 = r12 / 2
            int r0 = r0 + r1
            r6.NumOfHints = r0
            int r1 = r0 / 8
            int r0 = r0 % 8
            if (r0 != 0) goto L_0x00a6
            if (r1 != 0) goto L_0x00a8
        L_0x00a6:
            int r1 = r1 + 1
        L_0x00a8:
            r0 = 0
        L_0x00a9:
            if (r0 >= r1) goto L_0x00b1
            r15.getCard8()
            int r0 = r0 + 1
            goto L_0x00a9
        L_0x00b1:
            r14 = r21
            goto L_0x0147
        L_0x00b5:
            int r0 = r6.NumOfHints
            int r1 = r12 / 2
            int r0 = r0 + r1
            r6.NumOfHints = r0
            r14 = r21
            goto L_0x0147
        L_0x00c0:
            if (r12 <= 0) goto L_0x0103
            r0 = r11
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r0 = r0.intValue()
            int r13 = r0 + r18
            java.util.Set<java.lang.Integer> r0 = r6.hGSubrsUsed
            java.lang.Integer r1 = java.lang.Integer.valueOf(r13)
            boolean r0 = r0.contains(r1)
            if (r0 != 0) goto L_0x00e9
            java.util.Set<java.lang.Integer> r0 = r6.hGSubrsUsed
            java.lang.Integer r1 = java.lang.Integer.valueOf(r13)
            r0.add(r1)
            java.util.List<java.lang.Integer> r0 = r6.lGSubrsUsed
            java.lang.Integer r1 = java.lang.Integer.valueOf(r13)
            r0.add(r1)
        L_0x00e9:
            int[] r0 = r6.gsubrOffsets
            r1 = r0[r13]
            int[] r0 = r6.gsubrOffsets
            int r2 = r13 + 1
            r2 = r0[r2]
            r0 = r15
            r3 = r19
            r4 = r18
            r5 = r22
            r0.CalcHints(r1, r2, r3, r4, r5)
            r15.seek(r10)
            r14 = r21
            goto L_0x0147
        L_0x0103:
            r14 = r21
            goto L_0x0147
        L_0x0106:
            if (r12 <= 0) goto L_0x0142
            r0 = r11
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r0 = r0.intValue()
            int r13 = r0 + r19
            java.lang.Integer r0 = java.lang.Integer.valueOf(r13)
            boolean r0 = r7.contains(r0)
            if (r0 != 0) goto L_0x012c
            java.lang.Integer r0 = java.lang.Integer.valueOf(r13)
            r7.add(r0)
            java.lang.Integer r0 = java.lang.Integer.valueOf(r13)
            r14 = r21
            r14.add(r0)
            goto L_0x012e
        L_0x012c:
            r14 = r21
        L_0x012e:
            r1 = r22[r13]
            int r0 = r13 + 1
            r2 = r22[r0]
            r0 = r15
            r3 = r19
            r4 = r18
            r5 = r22
            r0.CalcHints(r1, r2, r3, r4, r5)
            r15.seek(r10)
            goto L_0x0147
        L_0x0142:
            r14 = r21
            goto L_0x0147
        L_0x0145:
            r14 = r21
        L_0x0147:
            goto L_0x000c
        L_0x0149:
            r14 = r21
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.font.CFFFontSubset.ReadASubr(int, int, int, int, java.util.Set, java.util.List, int[]):void");
    }

    /* access modifiers changed from: protected */
    public void HandelStack() {
        int StackHandel = StackOpp();
        if (StackHandel >= 2) {
            EmptyStack();
        } else if (StackHandel == 1) {
            PushStack();
        } else {
            int StackHandel2 = StackHandel * -1;
            for (int i = 0; i < StackHandel2; i++) {
                PopStack();
            }
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int StackOpp() {
        /*
            r6 = this;
            java.lang.String r0 = r6.key
            int r1 = r0.hashCode()
            r2 = 2
            r3 = 1
            r4 = 0
            r5 = -1
            switch(r1) {
                case -1191590954: goto L_0x0111;
                case -1038692485: goto L_0x0107;
                case -938285885: goto L_0x00fb;
                case -934396624: goto L_0x00ef;
                case -171694704: goto L_0x00e5;
                case 3244: goto L_0x00da;
                case 3555: goto L_0x00ce;
                case 96370: goto L_0x00c3;
                case 96417: goto L_0x00b9;
                case 96727: goto L_0x00ae;
                case 99473: goto L_0x00a3;
                case 99839: goto L_0x0097;
                case 102230: goto L_0x008b;
                case 108484: goto L_0x007e;
                case 108944: goto L_0x0071;
                case 109267: goto L_0x0064;
                case 111375: goto L_0x0058;
                case 114240: goto L_0x004c;
                case 3092207: goto L_0x0040;
                case 3127384: goto L_0x0034;
                case 3506301: goto L_0x0028;
                case 3538208: goto L_0x001b;
                case 100346066: goto L_0x000f;
                default: goto L_0x000d;
            }
        L_0x000d:
            goto L_0x011b
        L_0x000f:
            java.lang.String r1 = "index"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = 17
            goto L_0x011c
        L_0x001b:
            java.lang.String r1 = "sqrt"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = 15
            goto L_0x011c
        L_0x0028:
            java.lang.String r1 = "roll"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = 1
            goto L_0x011c
        L_0x0034:
            java.lang.String r1 = "exch"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = 16
            goto L_0x011c
        L_0x0040:
            java.lang.String r1 = "drop"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = 9
            goto L_0x011c
        L_0x004c:
            java.lang.String r1 = "sub"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = 6
            goto L_0x011c
        L_0x0058:
            java.lang.String r1 = "put"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = 2
            goto L_0x011c
        L_0x0064:
            java.lang.String r1 = "not"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = 19
            goto L_0x011c
        L_0x0071:
            java.lang.String r1 = "neg"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = 14
            goto L_0x011c
        L_0x007e:
            java.lang.String r1 = "mul"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = 8
            goto L_0x011c
        L_0x008b:
            java.lang.String r1 = "get"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = 18
            goto L_0x011c
        L_0x0097:
            java.lang.String r1 = "dup"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = 22
            goto L_0x011c
        L_0x00a3:
            java.lang.String r1 = "div"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = 7
            goto L_0x011c
        L_0x00ae:
            java.lang.String r1 = "and"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = 10
            goto L_0x011c
        L_0x00b9:
            java.lang.String r1 = "add"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = 5
            goto L_0x011c
        L_0x00c3:
            java.lang.String r1 = "abs"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = 13
            goto L_0x011c
        L_0x00ce:
            java.lang.String r1 = "or"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = 11
            goto L_0x011c
        L_0x00da:
            java.lang.String r1 = "eq"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = 12
            goto L_0x011c
        L_0x00e5:
            java.lang.String r1 = "callsubr"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = 3
            goto L_0x011c
        L_0x00ef:
            java.lang.String r1 = "return"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = 20
            goto L_0x011c
        L_0x00fb:
            java.lang.String r1 = "random"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = 21
            goto L_0x011c
        L_0x0107:
            java.lang.String r1 = "callgsubr"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = 4
            goto L_0x011c
        L_0x0111:
            java.lang.String r1 = "ifelse"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x000d
            r0 = 0
            goto L_0x011c
        L_0x011b:
            r0 = -1
        L_0x011c:
            switch(r0) {
                case 0: goto L_0x0125;
                case 1: goto L_0x0123;
                case 2: goto L_0x0123;
                case 3: goto L_0x0122;
                case 4: goto L_0x0122;
                case 5: goto L_0x0122;
                case 6: goto L_0x0122;
                case 7: goto L_0x0122;
                case 8: goto L_0x0122;
                case 9: goto L_0x0122;
                case 10: goto L_0x0122;
                case 11: goto L_0x0122;
                case 12: goto L_0x0122;
                case 13: goto L_0x0121;
                case 14: goto L_0x0121;
                case 15: goto L_0x0121;
                case 16: goto L_0x0121;
                case 17: goto L_0x0121;
                case 18: goto L_0x0121;
                case 19: goto L_0x0121;
                case 20: goto L_0x0121;
                case 21: goto L_0x0120;
                case 22: goto L_0x0120;
                default: goto L_0x011f;
            }
        L_0x011f:
            return r2
        L_0x0120:
            return r3
        L_0x0121:
            return r4
        L_0x0122:
            return r5
        L_0x0123:
            r0 = -2
            return r0
        L_0x0125:
            r0 = -3
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.font.CFFFontSubset.StackOpp():int");
    }

    /* access modifiers changed from: protected */
    public void EmptyStack() {
        for (int i = 0; i < this.arg_count; i++) {
            this.args[i] = null;
        }
        this.arg_count = 0;
    }

    /* access modifiers changed from: protected */
    public void PopStack() {
        if (this.arg_count > 0) {
            this.args[this.arg_count - 1] = null;
            this.arg_count--;
        }
    }

    /* access modifiers changed from: protected */
    public void PushStack() {
        this.arg_count++;
    }

    /* access modifiers changed from: protected */
    public void ReadCommand() {
        this.key = null;
        boolean gotKey = false;
        while (!gotKey) {
            char b0 = getCard8();
            if (b0 == 28) {
                this.args[this.arg_count] = Integer.valueOf((getCard8() << 8) | getCard8());
                this.arg_count++;
            } else if (b0 >= ' ' && b0 <= 246) {
                this.args[this.arg_count] = Integer.valueOf(b0 - 139);
                this.arg_count++;
            } else if (b0 >= 247 && b0 <= 250) {
                this.args[this.arg_count] = Integer.valueOf(((b0 - 247) * 256) + getCard8() + 108);
                this.arg_count++;
            } else if (b0 >= 251 && b0 <= 254) {
                this.args[this.arg_count] = Integer.valueOf((((-(b0 - 251)) * 256) - getCard8()) - 108);
                this.arg_count++;
            } else if (b0 == 255) {
                this.args[this.arg_count] = Integer.valueOf((getCard8() << 24) | (getCard8() << 16) | (getCard8() << 8) | getCard8());
                this.arg_count++;
            } else if (b0 <= 31 && b0 != 28) {
                gotKey = true;
                if (b0 == 12) {
                    int b1 = getCard8();
                    String[] strArr = SubrsEscapeFuncs;
                    if (b1 > strArr.length - 1) {
                        b1 = strArr.length - 1;
                    }
                    this.key = strArr[b1];
                } else {
                    this.key = SubrsFunctions[b0];
                }
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v2, resolved type: java.lang.Integer} */
    /* access modifiers changed from: protected */
    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x006b, code lost:
        if (r0.equals("callgsubr") != false) goto L_0x0083;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int CalcHints(int r13, int r14, int r15, int r16, int[] r17) {
        /*
            r12 = this;
            r6 = r12
            r12.seek(r13)
        L_0x0004:
            int r0 = r12.getPosition()
            r7 = r14
            if (r0 >= r7) goto L_0x00fb
            r12.ReadCommand()
            int r8 = r12.getPosition()
            r0 = 0
            int r1 = r6.arg_count
            r2 = 1
            if (r1 <= 0) goto L_0x0021
            java.lang.Object[] r1 = r6.args
            int r3 = r6.arg_count
            int r3 = r3 - r2
            r0 = r1[r3]
            r9 = r0
            goto L_0x0022
        L_0x0021:
            r9 = r0
        L_0x0022:
            int r10 = r6.arg_count
            r12.HandelStack()
            java.lang.String r0 = r6.key
            int r1 = r0.hashCode()
            switch(r1) {
                case -1473033741: goto L_0x0078;
                case -1284671851: goto L_0x006e;
                case -1038692485: goto L_0x0065;
                case -171694704: goto L_0x005b;
                case 99586865: goto L_0x0051;
                case 112516159: goto L_0x0046;
                case 753849732: goto L_0x003b;
                case 1213700086: goto L_0x0031;
                default: goto L_0x0030;
            }
        L_0x0030:
            goto L_0x0082
        L_0x0031:
            java.lang.String r1 = "hstemhm"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0030
            r2 = 4
            goto L_0x0083
        L_0x003b:
            java.lang.String r1 = "vstemhm"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0030
            r2 = 5
            goto L_0x0083
        L_0x0046:
            java.lang.String r1 = "vstem"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0030
            r2 = 3
            goto L_0x0083
        L_0x0051:
            java.lang.String r1 = "hstem"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0030
            r2 = 2
            goto L_0x0083
        L_0x005b:
            java.lang.String r1 = "callsubr"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0030
            r2 = 0
            goto L_0x0083
        L_0x0065:
            java.lang.String r1 = "callgsubr"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0030
            goto L_0x0083
        L_0x006e:
            java.lang.String r1 = "cntrmask"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0030
            r2 = 7
            goto L_0x0083
        L_0x0078:
            java.lang.String r1 = "hintmask"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0030
            r2 = 6
            goto L_0x0083
        L_0x0082:
            r2 = -1
        L_0x0083:
            switch(r2) {
                case 0: goto L_0x00d1;
                case 1: goto L_0x00a5;
                case 2: goto L_0x009d;
                case 3: goto L_0x009d;
                case 4: goto L_0x009d;
                case 5: goto L_0x009d;
                case 6: goto L_0x0088;
                case 7: goto L_0x0088;
                default: goto L_0x0086;
            }
        L_0x0086:
            goto L_0x00f9
        L_0x0088:
            int r0 = r6.NumOfHints
            int r1 = r0 / 8
            int r0 = r0 % 8
            if (r0 != 0) goto L_0x0092
            if (r1 != 0) goto L_0x0094
        L_0x0092:
            int r1 = r1 + 1
        L_0x0094:
            r0 = 0
        L_0x0095:
            if (r0 >= r1) goto L_0x00f9
            r12.getCard8()
            int r0 = r0 + 1
            goto L_0x0095
        L_0x009d:
            int r0 = r6.NumOfHints
            int r1 = r10 / 2
            int r0 = r0 + r1
            r6.NumOfHints = r0
            goto L_0x00f9
        L_0x00a5:
            if (r10 <= 0) goto L_0x00f9
            boolean r0 = r9 instanceof java.lang.Integer
            if (r0 == 0) goto L_0x00cb
            r0 = r9
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r0 = r0.intValue()
            int r11 = r0 + r16
            int[] r0 = r6.gsubrOffsets
            r1 = r0[r11]
            int[] r0 = r6.gsubrOffsets
            int r2 = r11 + 1
            r2 = r0[r2]
            r0 = r12
            r3 = r15
            r4 = r16
            r5 = r17
            r0.CalcHints(r1, r2, r3, r4, r5)
            r12.seek(r8)
            goto L_0x00f9
        L_0x00cb:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        L_0x00d1:
            if (r10 <= 0) goto L_0x00f9
            boolean r0 = r9 instanceof java.lang.Integer
            if (r0 == 0) goto L_0x00f3
            r0 = r9
            java.lang.Integer r0 = (java.lang.Integer) r0
            int r0 = r0.intValue()
            int r11 = r0 + r15
            r1 = r17[r11]
            int r0 = r11 + 1
            r2 = r17[r0]
            r0 = r12
            r3 = r15
            r4 = r16
            r5 = r17
            r0.CalcHints(r1, r2, r3, r4, r5)
            r12.seek(r8)
            goto L_0x00f9
        L_0x00f3:
            java.lang.AssertionError r0 = new java.lang.AssertionError
            r0.<init>()
            throw r0
        L_0x00f9:
            goto L_0x0004
        L_0x00fb:
            int r0 = r6.NumOfHints
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.p026io.font.CFFFontSubset.CalcHints(int, int, int, int, int[]):int");
    }

    /* access modifiers changed from: protected */
    public byte[] BuildNewIndex(int[] Offsets, Set<Integer> Used, byte OperatorForUnusedEntries) throws IOException {
        int unusedCount = 0;
        int Offset = 0;
        int[] NewOffsets = new int[Offsets.length];
        for (int i = 0; i < Offsets.length; i++) {
            NewOffsets[i] = Offset;
            if (Used.contains(Integer.valueOf(i))) {
                Offset += Offsets[i + 1] - Offsets[i];
            } else {
                unusedCount++;
            }
        }
        byte[] NewObjects = new byte[(Offset + unusedCount)];
        int unusedOffset = 0;
        for (int i2 = 0; i2 < Offsets.length - 1; i2++) {
            int start = NewOffsets[i2];
            int end = NewOffsets[i2 + 1];
            NewOffsets[i2] = start + unusedOffset;
            if (start != end) {
                this.buf.seek((long) Offsets[i2]);
                this.buf.readFully(NewObjects, start + unusedOffset, end - start);
            } else {
                NewObjects[start + unusedOffset] = OperatorForUnusedEntries;
                unusedOffset++;
            }
        }
        int length = Offsets.length - 1;
        NewOffsets[length] = NewOffsets[length] + unusedOffset;
        return AssembleIndex(NewOffsets, NewObjects);
    }

    /* access modifiers changed from: protected */
    public byte[] BuildNewIndexAndCopyAllGSubrs(int[] Offsets, byte OperatorForUnusedEntries) throws IOException {
        int Offset = 0;
        int[] NewOffsets = new int[Offsets.length];
        for (int i = 0; i < Offsets.length - 1; i++) {
            NewOffsets[i] = Offset;
            Offset += Offsets[i + 1] - Offsets[i];
        }
        NewOffsets[Offsets.length - 1] = Offset;
        byte[] NewObjects = new byte[(Offset + 0 + 1)];
        int unusedOffset = 0;
        for (int i2 = 0; i2 < Offsets.length - 1; i2++) {
            int start = NewOffsets[i2];
            int end = NewOffsets[i2 + 1];
            NewOffsets[i2] = start + unusedOffset;
            if (start != end) {
                this.buf.seek((long) Offsets[i2]);
                this.buf.readFully(NewObjects, start + unusedOffset, end - start);
            } else {
                NewObjects[start + unusedOffset] = OperatorForUnusedEntries;
                unusedOffset++;
            }
        }
        int length = Offsets.length - 1;
        NewOffsets[length] = NewOffsets[length] + unusedOffset;
        return AssembleIndex(NewOffsets, NewObjects);
    }

    /* access modifiers changed from: protected */
    public byte[] AssembleIndex(int[] NewOffsets, byte[] NewObjects) {
        byte Offsize;
        int[] iArr = NewOffsets;
        byte[] bArr = NewObjects;
        char Count = (char) (iArr.length - 1);
        int Size = iArr[iArr.length - 1];
        if (Size < 255) {
            Offsize = 1;
        } else if (Size < 65535) {
            Offsize = 2;
        } else if (Size < 16777215) {
            Offsize = 3;
        } else {
            Offsize = 4;
        }
        byte[] NewIndex = new byte[(((Count + 1) * Offsize) + 3 + bArr.length)];
        int Place = 0 + 1;
        NewIndex[0] = (byte) ((Count >> 8) & 255);
        int Place2 = Place + 1;
        NewIndex[Place] = (byte) (Count & 255);
        int Place3 = Place2 + 1;
        NewIndex[Place2] = Offsize;
        int i = 0;
        for (int newOffset : iArr) {
            int Num = (newOffset - iArr[0]) + 1;
            int i2 = Offsize;
            while (i2 > 0) {
                NewIndex[Place3] = (byte) ((Num >>> ((i2 - 1) << 3)) & 255);
                i2--;
                Place3++;
            }
        }
        int length = bArr.length;
        while (i < length) {
            NewIndex[Place3] = bArr[i];
            i++;
            Place3++;
        }
        return NewIndex;
    }

    /* access modifiers changed from: protected */
    public byte[] BuildNewFile(int Font) {
        int i = Font;
        this.OutputList = new LinkedList<>();
        CopyHeader();
        BuildIndexHeader(1, 1, 1);
        this.OutputList.addLast(new CFFFont.UInt8Item((char) (this.fonts[i].name.length() + 1)));
        this.OutputList.addLast(new CFFFont.StringItem(this.fonts[i].name));
        BuildIndexHeader(1, 2, 1);
        CFFFont.OffsetItem topdictIndex1Ref = new CFFFont.IndexOffsetItem(2);
        this.OutputList.addLast(topdictIndex1Ref);
        CFFFont.IndexBaseItem topdictBase = new CFFFont.IndexBaseItem();
        this.OutputList.addLast(topdictBase);
        CFFFont.OffsetItem charsetRef = new CFFFont.DictOffsetItem();
        CFFFont.OffsetItem charstringsRef = new CFFFont.DictOffsetItem();
        CFFFont.OffsetItem fdarrayRef = new CFFFont.DictOffsetItem();
        CFFFont.OffsetItem fdselectRef = new CFFFont.DictOffsetItem();
        CFFFont.OffsetItem privateRef = new CFFFont.DictOffsetItem();
        if (!this.fonts[i].isCID) {
            this.OutputList.addLast(new CFFFont.DictNumberItem(this.fonts[i].nstrings));
            this.OutputList.addLast(new CFFFont.DictNumberItem(this.fonts[i].nstrings + 1));
            this.OutputList.addLast(new CFFFont.DictNumberItem(0));
            this.OutputList.addLast(new CFFFont.UInt8Item(12));
            this.OutputList.addLast(new CFFFont.UInt8Item(30));
            this.OutputList.addLast(new CFFFont.DictNumberItem(this.fonts[i].nglyphs));
            this.OutputList.addLast(new CFFFont.UInt8Item(12));
            this.OutputList.addLast(new CFFFont.UInt8Item(Typography.quote));
        }
        seek(this.topdictOffsets[i]);
        while (getPosition() < this.topdictOffsets[i + 1]) {
            int p1 = getPosition();
            getDictItem();
            int p2 = getPosition();
            if (!"Encoding".equals(this.key) && !StandardRoles.PRIVATE.equals(this.key) && !"FDSelect".equals(this.key) && !"FDArray".equals(this.key) && !"charset".equals(this.key) && !"CharStrings".equals(this.key)) {
                this.OutputList.addLast(new CFFFont.RangeItem(this.buf, p1, p2 - p1));
            }
        }
        CreateKeys(fdarrayRef, fdselectRef, charsetRef, charstringsRef);
        this.OutputList.addLast(new CFFFont.IndexMarkerItem(topdictIndex1Ref, topdictBase));
        if (this.fonts[i].isCID) {
            this.OutputList.addLast(getEntireIndexRange(this.stringIndexOffset));
        } else {
            CreateNewStringIndex(Font);
        }
        this.OutputList.addLast(new CFFFont.RangeItem(new RandomAccessFileOrArray(this.rasFactory.createSource(this.NewGSubrsIndex)), 0, this.NewGSubrsIndex.length));
        if (this.fonts[i].isCID) {
            this.OutputList.addLast(new CFFFont.MarkerItem(fdselectRef));
            if (this.fonts[i].fdselectOffset >= 0) {
                this.OutputList.addLast(new CFFFont.RangeItem(this.buf, this.fonts[i].fdselectOffset, this.fonts[i].FDSelectLength));
            } else {
                CreateFDSelect(fdselectRef, this.fonts[i].nglyphs);
            }
            this.OutputList.addLast(new CFFFont.MarkerItem(charsetRef));
            this.OutputList.addLast(new CFFFont.RangeItem(this.buf, this.fonts[i].charsetOffset, this.fonts[i].CharsetLength));
            if (this.fonts[i].fdarrayOffset >= 0) {
                this.OutputList.addLast(new CFFFont.MarkerItem(fdarrayRef));
                Reconstruct(Font);
            } else {
                CreateFDArray(fdarrayRef, privateRef, i);
            }
        } else {
            CreateFDSelect(fdselectRef, this.fonts[i].nglyphs);
            CreateCharset(charsetRef, this.fonts[i].nglyphs);
            CreateFDArray(fdarrayRef, privateRef, i);
        }
        if (this.fonts[i].privateOffset >= 0) {
            CFFFont.IndexBaseItem PrivateBase = new CFFFont.IndexBaseItem();
            this.OutputList.addLast(PrivateBase);
            this.OutputList.addLast(new CFFFont.MarkerItem(privateRef));
            CFFFont.OffsetItem Subr = new CFFFont.DictOffsetItem();
            CreateNonCIDPrivate(i, Subr);
            CreateNonCIDSubrs(i, PrivateBase, Subr);
        }
        this.OutputList.addLast(new CFFFont.MarkerItem(charstringsRef));
        this.OutputList.addLast(new CFFFont.RangeItem(new RandomAccessFileOrArray(this.rasFactory.createSource(this.NewCharStringsIndex)), 0, this.NewCharStringsIndex.length));
        int[] currentOffset = {0};
        Iterator it = this.OutputList.iterator();
        while (it.hasNext()) {
            ((CFFFont.Item) it.next()).increment(currentOffset);
        }
        Iterator it2 = this.OutputList.iterator();
        while (it2.hasNext()) {
            ((CFFFont.Item) it2.next()).xref();
        }
        byte[] b = new byte[currentOffset[0]];
        Iterator it3 = this.OutputList.iterator();
        while (it3.hasNext()) {
            ((CFFFont.Item) it3.next()).emit(b);
        }
        return b;
    }

    /* access modifiers changed from: protected */
    public void CopyHeader() {
        seek(0);
        char card8 = getCard8();
        char card82 = getCard8();
        int hdrSize = getCard8();
        char card83 = getCard8();
        this.nextIndexOffset = hdrSize;
        this.OutputList.addLast(new CFFFont.RangeItem(this.buf, 0, hdrSize));
    }

    /* access modifiers changed from: protected */
    public void BuildIndexHeader(int Count, int Offsize, int First) {
        this.OutputList.addLast(new CFFFont.UInt16Item((char) Count));
        this.OutputList.addLast(new CFFFont.UInt8Item((char) Offsize));
        switch (Offsize) {
            case 1:
                this.OutputList.addLast(new CFFFont.UInt8Item((char) First));
                return;
            case 2:
                this.OutputList.addLast(new CFFFont.UInt16Item((char) First));
                return;
            case 3:
                this.OutputList.addLast(new CFFFont.UInt24Item((char) First));
                return;
            case 4:
                this.OutputList.addLast(new CFFFont.UInt32Item((char) First));
                return;
            default:
                return;
        }
    }

    /* access modifiers changed from: protected */
    public void CreateKeys(CFFFont.OffsetItem fdarrayRef, CFFFont.OffsetItem fdselectRef, CFFFont.OffsetItem charsetRef, CFFFont.OffsetItem charstringsRef) {
        this.OutputList.addLast(fdarrayRef);
        this.OutputList.addLast(new CFFFont.UInt8Item(12));
        this.OutputList.addLast(new CFFFont.UInt8Item(Typography.dollar));
        this.OutputList.addLast(fdselectRef);
        this.OutputList.addLast(new CFFFont.UInt8Item(12));
        this.OutputList.addLast(new CFFFont.UInt8Item('%'));
        this.OutputList.addLast(charsetRef);
        this.OutputList.addLast(new CFFFont.UInt8Item(15));
        this.OutputList.addLast(charstringsRef);
        this.OutputList.addLast(new CFFFont.UInt8Item(17));
    }

    /* access modifiers changed from: protected */
    public void CreateNewStringIndex(int Font) {
        byte stringsIndexOffSize;
        String fdFontName = this.fonts[Font].name + "-OneRange";
        if (fdFontName.length() > 127) {
            fdFontName = fdFontName.substring(0, 127);
        }
        String extraStrings = "AdobeIdentity" + fdFontName;
        int origStringsLen = this.stringOffsets[this.stringOffsets.length - 1] - this.stringOffsets[0];
        int stringsBaseOffset = this.stringOffsets[0] - 1;
        if (extraStrings.length() + origStringsLen <= 255) {
            stringsIndexOffSize = 1;
        } else if (extraStrings.length() + origStringsLen <= 65535) {
            stringsIndexOffSize = 2;
        } else if (extraStrings.length() + origStringsLen <= 16777215) {
            stringsIndexOffSize = 3;
        } else {
            stringsIndexOffSize = 4;
        }
        this.OutputList.addLast(new CFFFont.UInt16Item((char) ((this.stringOffsets.length - 1) + 3)));
        this.OutputList.addLast(new CFFFont.UInt8Item((char) stringsIndexOffSize));
        for (int stringOffset : this.stringOffsets) {
            this.OutputList.addLast(new CFFFont.IndexOffsetItem(stringsIndexOffSize, stringOffset - stringsBaseOffset));
        }
        int currentStringsOffset = (this.stringOffsets[this.stringOffsets.length - 1] - stringsBaseOffset) + "Adobe".length();
        this.OutputList.addLast(new CFFFont.IndexOffsetItem(stringsIndexOffSize, currentStringsOffset));
        int currentStringsOffset2 = currentStringsOffset + "Identity".length();
        this.OutputList.addLast(new CFFFont.IndexOffsetItem(stringsIndexOffSize, currentStringsOffset2));
        this.OutputList.addLast(new CFFFont.IndexOffsetItem(stringsIndexOffSize, currentStringsOffset2 + fdFontName.length()));
        this.OutputList.addLast(new CFFFont.RangeItem(this.buf, this.stringOffsets[0], origStringsLen));
        this.OutputList.addLast(new CFFFont.StringItem(extraStrings));
    }

    /* access modifiers changed from: protected */
    public void CreateFDSelect(CFFFont.OffsetItem fdselectRef, int nglyphs) {
        this.OutputList.addLast(new CFFFont.MarkerItem(fdselectRef));
        this.OutputList.addLast(new CFFFont.UInt8Item(3));
        this.OutputList.addLast(new CFFFont.UInt16Item(1));
        this.OutputList.addLast(new CFFFont.UInt16Item(0));
        this.OutputList.addLast(new CFFFont.UInt8Item(0));
        this.OutputList.addLast(new CFFFont.UInt16Item((char) nglyphs));
    }

    /* access modifiers changed from: protected */
    public void CreateCharset(CFFFont.OffsetItem charsetRef, int nglyphs) {
        this.OutputList.addLast(new CFFFont.MarkerItem(charsetRef));
        this.OutputList.addLast(new CFFFont.UInt8Item(2));
        this.OutputList.addLast(new CFFFont.UInt16Item(1));
        this.OutputList.addLast(new CFFFont.UInt16Item((char) (nglyphs - 1)));
    }

    /* access modifiers changed from: protected */
    public void CreateFDArray(CFFFont.OffsetItem fdarrayRef, CFFFont.OffsetItem privateRef, int Font) {
        this.OutputList.addLast(new CFFFont.MarkerItem(fdarrayRef));
        BuildIndexHeader(1, 1, 1);
        CFFFont.OffsetItem privateIndex1Ref = new CFFFont.IndexOffsetItem(1);
        this.OutputList.addLast(privateIndex1Ref);
        CFFFont.IndexBaseItem privateBase = new CFFFont.IndexBaseItem();
        this.OutputList.addLast(privateBase);
        int NewSize = this.fonts[Font].privateLength;
        int OrgSubrsOffsetSize = CalcSubrOffsetSize(this.fonts[Font].privateOffset, this.fonts[Font].privateLength);
        if (OrgSubrsOffsetSize != 0) {
            NewSize += 5 - OrgSubrsOffsetSize;
        }
        this.OutputList.addLast(new CFFFont.DictNumberItem(NewSize));
        this.OutputList.addLast(privateRef);
        this.OutputList.addLast(new CFFFont.UInt8Item(18));
        this.OutputList.addLast(new CFFFont.IndexMarkerItem(privateIndex1Ref, privateBase));
    }

    /* access modifiers changed from: package-private */
    public void Reconstruct(int Font) {
        CFFFont.OffsetItem[] fdPrivate = new CFFFont.DictOffsetItem[(this.fonts[Font].FDArrayOffsets.length - 1)];
        CFFFont.IndexBaseItem[] fdPrivateBase = new CFFFont.IndexBaseItem[this.fonts[Font].fdprivateOffsets.length];
        CFFFont.OffsetItem[] fdSubrs = new CFFFont.DictOffsetItem[this.fonts[Font].fdprivateOffsets.length];
        ReconstructFDArray(Font, fdPrivate);
        ReconstructPrivateDict(Font, fdPrivate, fdPrivateBase, fdSubrs);
        ReconstructPrivateSubrs(Font, fdPrivateBase, fdSubrs);
    }

    /* access modifiers changed from: package-private */
    public void ReconstructFDArray(int Font, CFFFont.OffsetItem[] fdPrivate) {
        BuildIndexHeader(this.fonts[Font].FDArrayCount, this.fonts[Font].FDArrayOffsize, 1);
        CFFFont.OffsetItem[] fdOffsets = new CFFFont.IndexOffsetItem[(this.fonts[Font].FDArrayOffsets.length - 1)];
        for (int i = 0; i < this.fonts[Font].FDArrayOffsets.length - 1; i++) {
            fdOffsets[i] = new CFFFont.IndexOffsetItem(this.fonts[Font].FDArrayOffsize);
            this.OutputList.addLast(fdOffsets[i]);
        }
        CFFFont.IndexBaseItem fdArrayBase = new CFFFont.IndexBaseItem();
        this.OutputList.addLast(fdArrayBase);
        for (int k = 0; k < this.fonts[Font].FDArrayOffsets.length - 1; k++) {
            seek(this.fonts[Font].FDArrayOffsets[k]);
            while (getPosition() < this.fonts[Font].FDArrayOffsets[k + 1]) {
                int p1 = getPosition();
                getDictItem();
                int p2 = getPosition();
                if (StandardRoles.PRIVATE.equals(this.key)) {
                    int NewSize = ((Integer) this.args[0]).intValue();
                    int OrgSubrsOffsetSize = CalcSubrOffsetSize(this.fonts[Font].fdprivateOffsets[k], this.fonts[Font].fdprivateLengths[k]);
                    if (OrgSubrsOffsetSize != 0) {
                        NewSize += 5 - OrgSubrsOffsetSize;
                    }
                    this.OutputList.addLast(new CFFFont.DictNumberItem(NewSize));
                    fdPrivate[k] = new CFFFont.DictOffsetItem();
                    this.OutputList.addLast(fdPrivate[k]);
                    this.OutputList.addLast(new CFFFont.UInt8Item(18));
                    seek(p2);
                } else {
                    this.OutputList.addLast(new CFFFont.RangeItem(this.buf, p1, p2 - p1));
                }
            }
            this.OutputList.addLast(new CFFFont.IndexMarkerItem(fdOffsets[k], fdArrayBase));
        }
    }

    /* access modifiers changed from: package-private */
    public void ReconstructPrivateDict(int Font, CFFFont.OffsetItem[] fdPrivate, CFFFont.IndexBaseItem[] fdPrivateBase, CFFFont.OffsetItem[] fdSubrs) {
        for (int i = 0; i < this.fonts[Font].fdprivateOffsets.length; i++) {
            this.OutputList.addLast(new CFFFont.MarkerItem(fdPrivate[i]));
            fdPrivateBase[i] = new CFFFont.IndexBaseItem();
            this.OutputList.addLast(fdPrivateBase[i]);
            seek(this.fonts[Font].fdprivateOffsets[i]);
            while (getPosition() < this.fonts[Font].fdprivateOffsets[i] + this.fonts[Font].fdprivateLengths[i]) {
                int p1 = getPosition();
                getDictItem();
                int p2 = getPosition();
                if ("Subrs".equals(this.key)) {
                    fdSubrs[i] = new CFFFont.DictOffsetItem();
                    this.OutputList.addLast(fdSubrs[i]);
                    this.OutputList.addLast(new CFFFont.UInt8Item(19));
                } else {
                    this.OutputList.addLast(new CFFFont.RangeItem(this.buf, p1, p2 - p1));
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void ReconstructPrivateSubrs(int Font, CFFFont.IndexBaseItem[] fdPrivateBase, CFFFont.OffsetItem[] fdSubrs) {
        for (int i = 0; i < this.fonts[Font].fdprivateLengths.length; i++) {
            if (fdSubrs[i] != null && this.fonts[Font].PrivateSubrsOffset[i] >= 0) {
                this.OutputList.addLast(new CFFFont.SubrMarkerItem(fdSubrs[i], fdPrivateBase[i]));
                if (this.NewLSubrsIndex[i] != null) {
                    this.OutputList.addLast(new CFFFont.RangeItem(new RandomAccessFileOrArray(this.rasFactory.createSource(this.NewLSubrsIndex[i])), 0, this.NewLSubrsIndex[i].length));
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public int CalcSubrOffsetSize(int Offset, int Size) {
        int OffsetSize = 0;
        seek(Offset);
        while (getPosition() < Offset + Size) {
            int p1 = getPosition();
            getDictItem();
            int p2 = getPosition();
            if ("Subrs".equals(this.key)) {
                OffsetSize = (p2 - p1) - 1;
            }
        }
        return OffsetSize;
    }

    /* access modifiers changed from: protected */
    public int countEntireIndexRange(int indexOffset) {
        seek(indexOffset);
        int count = getCard16();
        if (count == 0) {
            return 2;
        }
        int indexOffSize = getCard8();
        seek(indexOffset + 2 + 1 + (count * indexOffSize));
        return ((count + 1) * indexOffSize) + 3 + (getOffset(indexOffSize) - 1);
    }

    /* access modifiers changed from: package-private */
    public void CreateNonCIDPrivate(int Font, CFFFont.OffsetItem Subr) {
        seek(this.fonts[Font].privateOffset);
        while (getPosition() < this.fonts[Font].privateOffset + this.fonts[Font].privateLength) {
            int p1 = getPosition();
            getDictItem();
            int p2 = getPosition();
            if ("Subrs".equals(this.key)) {
                this.OutputList.addLast(Subr);
                this.OutputList.addLast(new CFFFont.UInt8Item(19));
            } else {
                this.OutputList.addLast(new CFFFont.RangeItem(this.buf, p1, p2 - p1));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void CreateNonCIDSubrs(int Font, CFFFont.IndexBaseItem PrivateBase, CFFFont.OffsetItem Subrs) {
        this.OutputList.addLast(new CFFFont.SubrMarkerItem(Subrs, PrivateBase));
        if (this.NewSubrsIndexNonCID != null) {
            this.OutputList.addLast(new CFFFont.RangeItem(new RandomAccessFileOrArray(this.rasFactory.createSource(this.NewSubrsIndexNonCID)), 0, this.NewSubrsIndexNonCID.length));
        }
    }
}
