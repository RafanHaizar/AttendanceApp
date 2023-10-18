package org.bouncycastle.crypto.digests;

import kotlin.UByte;
import org.bouncycastle.util.Memoable;

public class RIPEMD320Digest extends GeneralDigest {
    private static final int DIGEST_LENGTH = 40;

    /* renamed from: H0 */
    private int f248H0;

    /* renamed from: H1 */
    private int f249H1;

    /* renamed from: H2 */
    private int f250H2;

    /* renamed from: H3 */
    private int f251H3;

    /* renamed from: H4 */
    private int f252H4;

    /* renamed from: H5 */
    private int f253H5;

    /* renamed from: H6 */
    private int f254H6;

    /* renamed from: H7 */
    private int f255H7;

    /* renamed from: H8 */
    private int f256H8;

    /* renamed from: H9 */
    private int f257H9;

    /* renamed from: X */
    private int[] f258X = new int[16];
    private int xOff;

    public RIPEMD320Digest() {
        reset();
    }

    public RIPEMD320Digest(RIPEMD320Digest rIPEMD320Digest) {
        super((GeneralDigest) rIPEMD320Digest);
        doCopy(rIPEMD320Digest);
    }

    /* renamed from: RL */
    private int m70RL(int i, int i2) {
        return (i >>> (32 - i2)) | (i << i2);
    }

    private void doCopy(RIPEMD320Digest rIPEMD320Digest) {
        super.copyIn(rIPEMD320Digest);
        this.f248H0 = rIPEMD320Digest.f248H0;
        this.f249H1 = rIPEMD320Digest.f249H1;
        this.f250H2 = rIPEMD320Digest.f250H2;
        this.f251H3 = rIPEMD320Digest.f251H3;
        this.f252H4 = rIPEMD320Digest.f252H4;
        this.f253H5 = rIPEMD320Digest.f253H5;
        this.f254H6 = rIPEMD320Digest.f254H6;
        this.f255H7 = rIPEMD320Digest.f255H7;
        this.f256H8 = rIPEMD320Digest.f256H8;
        this.f257H9 = rIPEMD320Digest.f257H9;
        int[] iArr = rIPEMD320Digest.f258X;
        System.arraycopy(iArr, 0, this.f258X, 0, iArr.length);
        this.xOff = rIPEMD320Digest.xOff;
    }

    /* renamed from: f1 */
    private int m71f1(int i, int i2, int i3) {
        return (i ^ i2) ^ i3;
    }

    /* renamed from: f2 */
    private int m72f2(int i, int i2, int i3) {
        return ((i ^ -1) & i3) | (i2 & i);
    }

    /* renamed from: f3 */
    private int m73f3(int i, int i2, int i3) {
        return (i | (i2 ^ -1)) ^ i3;
    }

    /* renamed from: f4 */
    private int m74f4(int i, int i2, int i3) {
        return (i & i3) | (i2 & (i3 ^ -1));
    }

    /* renamed from: f5 */
    private int m75f5(int i, int i2, int i3) {
        return i ^ (i2 | (i3 ^ -1));
    }

    private void unpackWord(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) i;
        bArr[i2 + 1] = (byte) (i >>> 8);
        bArr[i2 + 2] = (byte) (i >>> 16);
        bArr[i2 + 3] = (byte) (i >>> 24);
    }

    public Memoable copy() {
        return new RIPEMD320Digest(this);
    }

    public int doFinal(byte[] bArr, int i) {
        finish();
        unpackWord(this.f248H0, bArr, i);
        unpackWord(this.f249H1, bArr, i + 4);
        unpackWord(this.f250H2, bArr, i + 8);
        unpackWord(this.f251H3, bArr, i + 12);
        unpackWord(this.f252H4, bArr, i + 16);
        unpackWord(this.f253H5, bArr, i + 20);
        unpackWord(this.f254H6, bArr, i + 24);
        unpackWord(this.f255H7, bArr, i + 28);
        unpackWord(this.f256H8, bArr, i + 32);
        unpackWord(this.f257H9, bArr, i + 36);
        reset();
        return 40;
    }

    public String getAlgorithmName() {
        return "RIPEMD320";
    }

    public int getDigestSize() {
        return 40;
    }

    /* access modifiers changed from: protected */
    public void processBlock() {
        int i = this.f248H0;
        int i2 = this.f249H1;
        int i3 = this.f250H2;
        int i4 = this.f251H3;
        int i5 = this.f252H4;
        int i6 = this.f253H5;
        int i7 = this.f254H6;
        int i8 = this.f255H7;
        int i9 = this.f256H8;
        int i10 = this.f257H9;
        int RL = m70RL(i + m71f1(i2, i3, i4) + this.f258X[0], 11) + i5;
        int RL2 = m70RL(i3, 10);
        int RL3 = m70RL(i5 + m71f1(RL, i2, RL2) + this.f258X[1], 14) + i4;
        int RL4 = m70RL(i2, 10);
        int RL5 = m70RL(i4 + m71f1(RL3, RL, RL4) + this.f258X[2], 15) + RL2;
        int RL6 = m70RL(RL, 10);
        int RL7 = m70RL(RL2 + m71f1(RL5, RL3, RL6) + this.f258X[3], 12) + RL4;
        int RL8 = m70RL(RL3, 10);
        int RL9 = m70RL(RL4 + m71f1(RL7, RL5, RL8) + this.f258X[4], 5) + RL6;
        int RL10 = m70RL(RL5, 10);
        int RL11 = m70RL(RL6 + m71f1(RL9, RL7, RL10) + this.f258X[5], 8) + RL8;
        int RL12 = m70RL(RL7, 10);
        int RL13 = m70RL(RL8 + m71f1(RL11, RL9, RL12) + this.f258X[6], 7) + RL10;
        int RL14 = m70RL(RL9, 10);
        int RL15 = m70RL(RL10 + m71f1(RL13, RL11, RL14) + this.f258X[7], 9) + RL12;
        int RL16 = m70RL(RL11, 10);
        int RL17 = m70RL(RL12 + m71f1(RL15, RL13, RL16) + this.f258X[8], 11) + RL14;
        int RL18 = m70RL(RL13, 10);
        int RL19 = m70RL(RL14 + m71f1(RL17, RL15, RL18) + this.f258X[9], 13) + RL16;
        int RL20 = m70RL(RL15, 10);
        int RL21 = m70RL(RL16 + m71f1(RL19, RL17, RL20) + this.f258X[10], 14) + RL18;
        int RL22 = m70RL(RL17, 10);
        int RL23 = m70RL(RL18 + m71f1(RL21, RL19, RL22) + this.f258X[11], 15) + RL20;
        int RL24 = m70RL(RL19, 10);
        int RL25 = m70RL(RL20 + m71f1(RL23, RL21, RL24) + this.f258X[12], 6) + RL22;
        int RL26 = m70RL(RL21, 10);
        int RL27 = m70RL(RL22 + m71f1(RL25, RL23, RL26) + this.f258X[13], 7) + RL24;
        int RL28 = m70RL(RL23, 10);
        int RL29 = m70RL(RL24 + m71f1(RL27, RL25, RL28) + this.f258X[14], 9) + RL26;
        int RL30 = m70RL(RL25, 10);
        int RL31 = m70RL(RL26 + m71f1(RL29, RL27, RL30) + this.f258X[15], 8) + RL28;
        int RL32 = m70RL(RL27, 10);
        int RL33 = m70RL(i6 + m75f5(i7, i8, i9) + this.f258X[5] + 1352829926, 8) + i10;
        int RL34 = m70RL(i8, 10);
        int RL35 = m70RL(i10 + m75f5(RL33, i7, RL34) + this.f258X[14] + 1352829926, 9) + i9;
        int RL36 = m70RL(i7, 10);
        int RL37 = m70RL(i9 + m75f5(RL35, RL33, RL36) + this.f258X[7] + 1352829926, 9) + RL34;
        int RL38 = m70RL(RL33, 10);
        int RL39 = m70RL(RL34 + m75f5(RL37, RL35, RL38) + this.f258X[0] + 1352829926, 11) + RL36;
        int RL40 = m70RL(RL35, 10);
        int RL41 = m70RL(RL36 + m75f5(RL39, RL37, RL40) + this.f258X[9] + 1352829926, 13) + RL38;
        int RL42 = m70RL(RL37, 10);
        int RL43 = m70RL(RL38 + m75f5(RL41, RL39, RL42) + this.f258X[2] + 1352829926, 15) + RL40;
        int RL44 = m70RL(RL39, 10);
        int RL45 = m70RL(RL40 + m75f5(RL43, RL41, RL44) + this.f258X[11] + 1352829926, 15) + RL42;
        int RL46 = m70RL(RL41, 10);
        int RL47 = m70RL(RL42 + m75f5(RL45, RL43, RL46) + this.f258X[4] + 1352829926, 5) + RL44;
        int RL48 = m70RL(RL43, 10);
        int RL49 = m70RL(RL44 + m75f5(RL47, RL45, RL48) + this.f258X[13] + 1352829926, 7) + RL46;
        int RL50 = m70RL(RL45, 10);
        int RL51 = m70RL(RL46 + m75f5(RL49, RL47, RL50) + this.f258X[6] + 1352829926, 7) + RL48;
        int RL52 = m70RL(RL47, 10);
        int RL53 = m70RL(RL48 + m75f5(RL51, RL49, RL52) + this.f258X[15] + 1352829926, 8) + RL50;
        int RL54 = m70RL(RL49, 10);
        int RL55 = m70RL(RL50 + m75f5(RL53, RL51, RL54) + this.f258X[8] + 1352829926, 11) + RL52;
        int RL56 = m70RL(RL51, 10);
        int RL57 = m70RL(RL52 + m75f5(RL55, RL53, RL56) + this.f258X[1] + 1352829926, 14) + RL54;
        int RL58 = m70RL(RL53, 10);
        int RL59 = m70RL(RL54 + m75f5(RL57, RL55, RL58) + this.f258X[10] + 1352829926, 14) + RL56;
        int RL60 = m70RL(RL55, 10);
        int RL61 = m70RL(RL56 + m75f5(RL59, RL57, RL60) + this.f258X[3] + 1352829926, 12) + RL58;
        int RL62 = m70RL(RL57, 10);
        int RL63 = m70RL(RL58 + m75f5(RL61, RL59, RL62) + this.f258X[12] + 1352829926, 6) + RL60;
        int RL64 = m70RL(RL59, 10);
        int RL65 = m70RL(RL28 + m72f2(RL63, RL29, RL32) + this.f258X[7] + 1518500249, 7) + RL30;
        int RL66 = m70RL(RL29, 10);
        int RL67 = m70RL(RL30 + m72f2(RL65, RL63, RL66) + this.f258X[4] + 1518500249, 6) + RL32;
        int RL68 = m70RL(RL63, 10);
        int RL69 = m70RL(RL32 + m72f2(RL67, RL65, RL68) + this.f258X[13] + 1518500249, 8) + RL66;
        int RL70 = m70RL(RL65, 10);
        int RL71 = m70RL(RL66 + m72f2(RL69, RL67, RL70) + this.f258X[1] + 1518500249, 13) + RL68;
        int RL72 = m70RL(RL67, 10);
        int RL73 = m70RL(RL68 + m72f2(RL71, RL69, RL72) + this.f258X[10] + 1518500249, 11) + RL70;
        int RL74 = m70RL(RL69, 10);
        int RL75 = m70RL(RL70 + m72f2(RL73, RL71, RL74) + this.f258X[6] + 1518500249, 9) + RL72;
        int RL76 = m70RL(RL71, 10);
        int RL77 = m70RL(RL72 + m72f2(RL75, RL73, RL76) + this.f258X[15] + 1518500249, 7) + RL74;
        int RL78 = m70RL(RL73, 10);
        int RL79 = m70RL(RL74 + m72f2(RL77, RL75, RL78) + this.f258X[3] + 1518500249, 15) + RL76;
        int RL80 = m70RL(RL75, 10);
        int RL81 = m70RL(RL76 + m72f2(RL79, RL77, RL80) + this.f258X[12] + 1518500249, 7) + RL78;
        int RL82 = m70RL(RL77, 10);
        int RL83 = m70RL(RL78 + m72f2(RL81, RL79, RL82) + this.f258X[0] + 1518500249, 12) + RL80;
        int RL84 = m70RL(RL79, 10);
        int RL85 = m70RL(RL80 + m72f2(RL83, RL81, RL84) + this.f258X[9] + 1518500249, 15) + RL82;
        int RL86 = m70RL(RL81, 10);
        int RL87 = m70RL(RL82 + m72f2(RL85, RL83, RL86) + this.f258X[5] + 1518500249, 9) + RL84;
        int RL88 = m70RL(RL83, 10);
        int RL89 = m70RL(RL84 + m72f2(RL87, RL85, RL88) + this.f258X[2] + 1518500249, 11) + RL86;
        int RL90 = m70RL(RL85, 10);
        int RL91 = m70RL(RL86 + m72f2(RL89, RL87, RL90) + this.f258X[14] + 1518500249, 7) + RL88;
        int RL92 = m70RL(RL87, 10);
        int RL93 = m70RL(RL88 + m72f2(RL91, RL89, RL92) + this.f258X[11] + 1518500249, 13) + RL90;
        int RL94 = m70RL(RL89, 10);
        int RL95 = m70RL(RL90 + m72f2(RL93, RL91, RL94) + this.f258X[8] + 1518500249, 12) + RL92;
        int RL96 = m70RL(RL91, 10);
        int RL97 = m70RL(RL60 + m74f4(RL31, RL61, RL64) + this.f258X[6] + 1548603684, 9) + RL62;
        int RL98 = m70RL(RL61, 10);
        int RL99 = m70RL(RL62 + m74f4(RL97, RL31, RL98) + this.f258X[11] + 1548603684, 13) + RL64;
        int RL100 = m70RL(RL31, 10);
        int RL101 = m70RL(RL64 + m74f4(RL99, RL97, RL100) + this.f258X[3] + 1548603684, 15) + RL98;
        int RL102 = m70RL(RL97, 10);
        int RL103 = m70RL(RL98 + m74f4(RL101, RL99, RL102) + this.f258X[7] + 1548603684, 7) + RL100;
        int RL104 = m70RL(RL99, 10);
        int RL105 = m70RL(RL100 + m74f4(RL103, RL101, RL104) + this.f258X[0] + 1548603684, 12) + RL102;
        int RL106 = m70RL(RL101, 10);
        int RL107 = m70RL(RL102 + m74f4(RL105, RL103, RL106) + this.f258X[13] + 1548603684, 8) + RL104;
        int RL108 = m70RL(RL103, 10);
        int RL109 = m70RL(RL104 + m74f4(RL107, RL105, RL108) + this.f258X[5] + 1548603684, 9) + RL106;
        int RL110 = m70RL(RL105, 10);
        int RL111 = m70RL(RL106 + m74f4(RL109, RL107, RL110) + this.f258X[10] + 1548603684, 11) + RL108;
        int RL112 = m70RL(RL107, 10);
        int RL113 = m70RL(RL108 + m74f4(RL111, RL109, RL112) + this.f258X[14] + 1548603684, 7) + RL110;
        int RL114 = m70RL(RL109, 10);
        int RL115 = m70RL(RL110 + m74f4(RL113, RL111, RL114) + this.f258X[15] + 1548603684, 7) + RL112;
        int RL116 = m70RL(RL111, 10);
        int RL117 = m70RL(RL112 + m74f4(RL115, RL113, RL116) + this.f258X[8] + 1548603684, 12) + RL114;
        int RL118 = m70RL(RL113, 10);
        int RL119 = m70RL(RL114 + m74f4(RL117, RL115, RL118) + this.f258X[12] + 1548603684, 7) + RL116;
        int RL120 = m70RL(RL115, 10);
        int RL121 = m70RL(RL116 + m74f4(RL119, RL117, RL120) + this.f258X[4] + 1548603684, 6) + RL118;
        int RL122 = m70RL(RL117, 10);
        int RL123 = m70RL(RL118 + m74f4(RL121, RL119, RL122) + this.f258X[9] + 1548603684, 15) + RL120;
        int RL124 = m70RL(RL119, 10);
        int RL125 = m70RL(RL120 + m74f4(RL123, RL121, RL124) + this.f258X[1] + 1548603684, 13) + RL122;
        int RL126 = m70RL(RL121, 10);
        int RL127 = m70RL(RL122 + m74f4(RL125, RL123, RL126) + this.f258X[2] + 1548603684, 11) + RL124;
        int RL128 = m70RL(RL123, 10);
        int RL129 = m70RL(RL92 + m73f3(RL95, RL93, RL128) + this.f258X[3] + 1859775393, 11) + RL94;
        int RL130 = m70RL(RL93, 10);
        int RL131 = m70RL(RL94 + m73f3(RL129, RL95, RL130) + this.f258X[10] + 1859775393, 13) + RL128;
        int RL132 = m70RL(RL95, 10);
        int RL133 = m70RL(RL128 + m73f3(RL131, RL129, RL132) + this.f258X[14] + 1859775393, 6) + RL130;
        int RL134 = m70RL(RL129, 10);
        int RL135 = m70RL(RL130 + m73f3(RL133, RL131, RL134) + this.f258X[4] + 1859775393, 7) + RL132;
        int RL136 = m70RL(RL131, 10);
        int RL137 = m70RL(RL132 + m73f3(RL135, RL133, RL136) + this.f258X[9] + 1859775393, 14) + RL134;
        int RL138 = m70RL(RL133, 10);
        int RL139 = m70RL(RL134 + m73f3(RL137, RL135, RL138) + this.f258X[15] + 1859775393, 9) + RL136;
        int RL140 = m70RL(RL135, 10);
        int RL141 = m70RL(RL136 + m73f3(RL139, RL137, RL140) + this.f258X[8] + 1859775393, 13) + RL138;
        int RL142 = m70RL(RL137, 10);
        int RL143 = m70RL(RL138 + m73f3(RL141, RL139, RL142) + this.f258X[1] + 1859775393, 15) + RL140;
        int RL144 = m70RL(RL139, 10);
        int RL145 = m70RL(RL140 + m73f3(RL143, RL141, RL144) + this.f258X[2] + 1859775393, 14) + RL142;
        int RL146 = m70RL(RL141, 10);
        int RL147 = m70RL(RL142 + m73f3(RL145, RL143, RL146) + this.f258X[7] + 1859775393, 8) + RL144;
        int RL148 = m70RL(RL143, 10);
        int RL149 = m70RL(RL144 + m73f3(RL147, RL145, RL148) + this.f258X[0] + 1859775393, 13) + RL146;
        int RL150 = m70RL(RL145, 10);
        int RL151 = m70RL(RL146 + m73f3(RL149, RL147, RL150) + this.f258X[6] + 1859775393, 6) + RL148;
        int RL152 = m70RL(RL147, 10);
        int RL153 = m70RL(RL148 + m73f3(RL151, RL149, RL152) + this.f258X[13] + 1859775393, 5) + RL150;
        int RL154 = m70RL(RL149, 10);
        int RL155 = m70RL(RL150 + m73f3(RL153, RL151, RL154) + this.f258X[11] + 1859775393, 12) + RL152;
        int RL156 = m70RL(RL151, 10);
        int RL157 = m70RL(RL152 + m73f3(RL155, RL153, RL156) + this.f258X[5] + 1859775393, 7) + RL154;
        int RL158 = m70RL(RL153, 10);
        int RL159 = m70RL(RL154 + m73f3(RL157, RL155, RL158) + this.f258X[12] + 1859775393, 5) + RL156;
        int RL160 = m70RL(RL155, 10);
        int RL161 = m70RL(RL124 + m73f3(RL127, RL125, RL96) + this.f258X[15] + 1836072691, 9) + RL126;
        int RL162 = m70RL(RL125, 10);
        int RL163 = m70RL(RL126 + m73f3(RL161, RL127, RL162) + this.f258X[5] + 1836072691, 7) + RL96;
        int RL164 = m70RL(RL127, 10);
        int RL165 = m70RL(RL96 + m73f3(RL163, RL161, RL164) + this.f258X[1] + 1836072691, 15) + RL162;
        int RL166 = m70RL(RL161, 10);
        int RL167 = m70RL(RL162 + m73f3(RL165, RL163, RL166) + this.f258X[3] + 1836072691, 11) + RL164;
        int RL168 = m70RL(RL163, 10);
        int RL169 = m70RL(RL164 + m73f3(RL167, RL165, RL168) + this.f258X[7] + 1836072691, 8) + RL166;
        int RL170 = m70RL(RL165, 10);
        int RL171 = m70RL(RL166 + m73f3(RL169, RL167, RL170) + this.f258X[14] + 1836072691, 6) + RL168;
        int RL172 = m70RL(RL167, 10);
        int RL173 = m70RL(RL168 + m73f3(RL171, RL169, RL172) + this.f258X[6] + 1836072691, 6) + RL170;
        int RL174 = m70RL(RL169, 10);
        int RL175 = m70RL(RL170 + m73f3(RL173, RL171, RL174) + this.f258X[9] + 1836072691, 14) + RL172;
        int RL176 = m70RL(RL171, 10);
        int RL177 = m70RL(RL172 + m73f3(RL175, RL173, RL176) + this.f258X[11] + 1836072691, 12) + RL174;
        int RL178 = m70RL(RL173, 10);
        int RL179 = m70RL(RL174 + m73f3(RL177, RL175, RL178) + this.f258X[8] + 1836072691, 13) + RL176;
        int RL180 = m70RL(RL175, 10);
        int RL181 = m70RL(RL176 + m73f3(RL179, RL177, RL180) + this.f258X[12] + 1836072691, 5) + RL178;
        int RL182 = m70RL(RL177, 10);
        int RL183 = m70RL(RL178 + m73f3(RL181, RL179, RL182) + this.f258X[2] + 1836072691, 14) + RL180;
        int RL184 = m70RL(RL179, 10);
        int RL185 = m70RL(RL180 + m73f3(RL183, RL181, RL184) + this.f258X[10] + 1836072691, 13) + RL182;
        int RL186 = m70RL(RL181, 10);
        int RL187 = m70RL(RL182 + m73f3(RL185, RL183, RL186) + this.f258X[0] + 1836072691, 13) + RL184;
        int RL188 = m70RL(RL183, 10);
        int RL189 = m70RL(RL184 + m73f3(RL187, RL185, RL188) + this.f258X[4] + 1836072691, 7) + RL186;
        int RL190 = m70RL(RL185, 10);
        int RL191 = m70RL(RL186 + m73f3(RL189, RL187, RL190) + this.f258X[13] + 1836072691, 5) + RL188;
        int RL192 = m70RL(RL187, 10);
        int RL193 = m70RL(((RL188 + m74f4(RL159, RL157, RL160)) + this.f258X[1]) - 1894007588, 11) + RL158;
        int RL194 = m70RL(RL157, 10);
        int RL195 = m70RL(((RL158 + m74f4(RL193, RL159, RL194)) + this.f258X[9]) - 1894007588, 12) + RL160;
        int RL196 = m70RL(RL159, 10);
        int RL197 = m70RL(((RL160 + m74f4(RL195, RL193, RL196)) + this.f258X[11]) - 1894007588, 14) + RL194;
        int RL198 = m70RL(RL193, 10);
        int RL199 = m70RL(((RL194 + m74f4(RL197, RL195, RL198)) + this.f258X[10]) - 1894007588, 15) + RL196;
        int RL200 = m70RL(RL195, 10);
        int RL201 = m70RL(((RL196 + m74f4(RL199, RL197, RL200)) + this.f258X[0]) - 1894007588, 14) + RL198;
        int RL202 = m70RL(RL197, 10);
        int RL203 = m70RL(((RL198 + m74f4(RL201, RL199, RL202)) + this.f258X[8]) - 1894007588, 15) + RL200;
        int RL204 = m70RL(RL199, 10);
        int RL205 = m70RL(((RL200 + m74f4(RL203, RL201, RL204)) + this.f258X[12]) - 1894007588, 9) + RL202;
        int RL206 = m70RL(RL201, 10);
        int RL207 = m70RL(((RL202 + m74f4(RL205, RL203, RL206)) + this.f258X[4]) - 1894007588, 8) + RL204;
        int RL208 = m70RL(RL203, 10);
        int RL209 = m70RL(((RL204 + m74f4(RL207, RL205, RL208)) + this.f258X[13]) - 1894007588, 9) + RL206;
        int RL210 = m70RL(RL205, 10);
        int RL211 = m70RL(((RL206 + m74f4(RL209, RL207, RL210)) + this.f258X[3]) - 1894007588, 14) + RL208;
        int RL212 = m70RL(RL207, 10);
        int RL213 = m70RL(((RL208 + m74f4(RL211, RL209, RL212)) + this.f258X[7]) - 1894007588, 5) + RL210;
        int RL214 = m70RL(RL209, 10);
        int RL215 = m70RL(((RL210 + m74f4(RL213, RL211, RL214)) + this.f258X[15]) - 1894007588, 6) + RL212;
        int RL216 = m70RL(RL211, 10);
        int RL217 = m70RL(((RL212 + m74f4(RL215, RL213, RL216)) + this.f258X[14]) - 1894007588, 8) + RL214;
        int RL218 = m70RL(RL213, 10);
        int RL219 = m70RL(((RL214 + m74f4(RL217, RL215, RL218)) + this.f258X[5]) - 1894007588, 6) + RL216;
        int RL220 = m70RL(RL215, 10);
        int RL221 = m70RL(((RL216 + m74f4(RL219, RL217, RL220)) + this.f258X[6]) - 1894007588, 5) + RL218;
        int RL222 = m70RL(RL217, 10);
        int RL223 = m70RL(((RL218 + m74f4(RL221, RL219, RL222)) + this.f258X[2]) - 1894007588, 12) + RL220;
        int RL224 = m70RL(RL219, 10);
        int RL225 = m70RL(RL156 + m72f2(RL191, RL189, RL192) + this.f258X[8] + 2053994217, 15) + RL190;
        int RL226 = m70RL(RL189, 10);
        int RL227 = m70RL(RL190 + m72f2(RL225, RL191, RL226) + this.f258X[6] + 2053994217, 5) + RL192;
        int RL228 = m70RL(RL191, 10);
        int RL229 = m70RL(RL192 + m72f2(RL227, RL225, RL228) + this.f258X[4] + 2053994217, 8) + RL226;
        int RL230 = m70RL(RL225, 10);
        int RL231 = m70RL(RL226 + m72f2(RL229, RL227, RL230) + this.f258X[1] + 2053994217, 11) + RL228;
        int RL232 = m70RL(RL227, 10);
        int RL233 = m70RL(RL228 + m72f2(RL231, RL229, RL232) + this.f258X[3] + 2053994217, 14) + RL230;
        int RL234 = m70RL(RL229, 10);
        int RL235 = m70RL(RL230 + m72f2(RL233, RL231, RL234) + this.f258X[11] + 2053994217, 14) + RL232;
        int RL236 = m70RL(RL231, 10);
        int RL237 = m70RL(RL232 + m72f2(RL235, RL233, RL236) + this.f258X[15] + 2053994217, 6) + RL234;
        int RL238 = m70RL(RL233, 10);
        int RL239 = m70RL(RL234 + m72f2(RL237, RL235, RL238) + this.f258X[0] + 2053994217, 14) + RL236;
        int RL240 = m70RL(RL235, 10);
        int RL241 = m70RL(RL236 + m72f2(RL239, RL237, RL240) + this.f258X[5] + 2053994217, 6) + RL238;
        int RL242 = m70RL(RL237, 10);
        int RL243 = m70RL(RL238 + m72f2(RL241, RL239, RL242) + this.f258X[12] + 2053994217, 9) + RL240;
        int RL244 = m70RL(RL239, 10);
        int RL245 = m70RL(RL240 + m72f2(RL243, RL241, RL244) + this.f258X[2] + 2053994217, 12) + RL242;
        int RL246 = m70RL(RL241, 10);
        int RL247 = m70RL(RL242 + m72f2(RL245, RL243, RL246) + this.f258X[13] + 2053994217, 9) + RL244;
        int RL248 = m70RL(RL243, 10);
        int RL249 = m70RL(RL244 + m72f2(RL247, RL245, RL248) + this.f258X[9] + 2053994217, 12) + RL246;
        int RL250 = m70RL(RL245, 10);
        int RL251 = m70RL(RL246 + m72f2(RL249, RL247, RL250) + this.f258X[7] + 2053994217, 5) + RL248;
        int RL252 = m70RL(RL247, 10);
        int RL253 = m70RL(RL248 + m72f2(RL251, RL249, RL252) + this.f258X[10] + 2053994217, 15) + RL250;
        int RL254 = m70RL(RL249, 10);
        int RL255 = m70RL(RL250 + m72f2(RL253, RL251, RL254) + this.f258X[14] + 2053994217, 8) + RL252;
        int RL256 = m70RL(RL251, 10);
        int RL257 = m70RL(((RL220 + m75f5(RL223, RL253, RL224)) + this.f258X[4]) - 1454113458, 9) + RL222;
        int RL258 = m70RL(RL253, 10);
        int RL259 = m70RL(((RL222 + m75f5(RL257, RL223, RL258)) + this.f258X[0]) - 1454113458, 15) + RL224;
        int RL260 = m70RL(RL223, 10);
        int RL261 = m70RL(((RL224 + m75f5(RL259, RL257, RL260)) + this.f258X[5]) - 1454113458, 5) + RL258;
        int RL262 = m70RL(RL257, 10);
        int RL263 = m70RL(((RL258 + m75f5(RL261, RL259, RL262)) + this.f258X[9]) - 1454113458, 11) + RL260;
        int RL264 = m70RL(RL259, 10);
        int RL265 = m70RL(((RL260 + m75f5(RL263, RL261, RL264)) + this.f258X[7]) - 1454113458, 6) + RL262;
        int RL266 = m70RL(RL261, 10);
        int RL267 = m70RL(((RL262 + m75f5(RL265, RL263, RL266)) + this.f258X[12]) - 1454113458, 8) + RL264;
        int RL268 = m70RL(RL263, 10);
        int RL269 = m70RL(((RL264 + m75f5(RL267, RL265, RL268)) + this.f258X[2]) - 1454113458, 13) + RL266;
        int RL270 = m70RL(RL265, 10);
        int RL271 = m70RL(((RL266 + m75f5(RL269, RL267, RL270)) + this.f258X[10]) - 1454113458, 12) + RL268;
        int RL272 = m70RL(RL267, 10);
        int RL273 = m70RL(((RL268 + m75f5(RL271, RL269, RL272)) + this.f258X[14]) - 1454113458, 5) + RL270;
        int RL274 = m70RL(RL269, 10);
        int RL275 = m70RL(((RL270 + m75f5(RL273, RL271, RL274)) + this.f258X[1]) - 1454113458, 12) + RL272;
        int RL276 = m70RL(RL271, 10);
        int RL277 = m70RL(((RL272 + m75f5(RL275, RL273, RL276)) + this.f258X[3]) - 1454113458, 13) + RL274;
        int RL278 = m70RL(RL273, 10);
        int RL279 = m70RL(((RL274 + m75f5(RL277, RL275, RL278)) + this.f258X[8]) - 1454113458, 14) + RL276;
        int RL280 = m70RL(RL275, 10);
        int RL281 = m70RL(((RL276 + m75f5(RL279, RL277, RL280)) + this.f258X[11]) - 1454113458, 11) + RL278;
        int RL282 = m70RL(RL277, 10);
        int RL283 = m70RL(((RL278 + m75f5(RL281, RL279, RL282)) + this.f258X[6]) - 1454113458, 8) + RL280;
        int RL284 = m70RL(RL279, 10);
        int RL285 = m70RL(((RL280 + m75f5(RL283, RL281, RL284)) + this.f258X[15]) - 1454113458, 5) + RL282;
        int RL286 = m70RL(RL281, 10);
        int RL287 = m70RL(((RL282 + m75f5(RL285, RL283, RL286)) + this.f258X[13]) - 1454113458, 6) + RL284;
        int RL288 = m70RL(RL283, 10);
        int RL289 = m70RL(RL252 + m71f1(RL255, RL221, RL256) + this.f258X[12], 8) + RL254;
        int RL290 = m70RL(RL221, 10);
        int RL291 = m70RL(RL254 + m71f1(RL289, RL255, RL290) + this.f258X[15], 5) + RL256;
        int RL292 = m70RL(RL255, 10);
        int RL293 = m70RL(RL256 + m71f1(RL291, RL289, RL292) + this.f258X[10], 12) + RL290;
        int RL294 = m70RL(RL289, 10);
        int RL295 = m70RL(RL290 + m71f1(RL293, RL291, RL294) + this.f258X[4], 9) + RL292;
        int RL296 = m70RL(RL291, 10);
        int RL297 = m70RL(RL292 + m71f1(RL295, RL293, RL296) + this.f258X[1], 12) + RL294;
        int RL298 = m70RL(RL293, 10);
        int RL299 = m70RL(RL294 + m71f1(RL297, RL295, RL298) + this.f258X[5], 5) + RL296;
        int RL300 = m70RL(RL295, 10);
        int RL301 = m70RL(RL296 + m71f1(RL299, RL297, RL300) + this.f258X[8], 14) + RL298;
        int RL302 = m70RL(RL297, 10);
        int RL303 = m70RL(RL298 + m71f1(RL301, RL299, RL302) + this.f258X[7], 6) + RL300;
        int RL304 = m70RL(RL299, 10);
        int RL305 = m70RL(RL300 + m71f1(RL303, RL301, RL304) + this.f258X[6], 8) + RL302;
        int RL306 = m70RL(RL301, 10);
        int RL307 = m70RL(RL302 + m71f1(RL305, RL303, RL306) + this.f258X[2], 13) + RL304;
        int RL308 = m70RL(RL303, 10);
        int RL309 = m70RL(RL304 + m71f1(RL307, RL305, RL308) + this.f258X[13], 6) + RL306;
        int RL310 = m70RL(RL305, 10);
        int RL311 = m70RL(RL306 + m71f1(RL309, RL307, RL310) + this.f258X[14], 5) + RL308;
        int RL312 = m70RL(RL307, 10);
        int RL313 = m70RL(RL308 + m71f1(RL311, RL309, RL312) + this.f258X[0], 15) + RL310;
        int RL314 = m70RL(RL309, 10);
        int RL315 = m70RL(RL310 + m71f1(RL313, RL311, RL314) + this.f258X[3], 13) + RL312;
        int RL316 = m70RL(RL311, 10);
        int RL317 = m70RL(RL312 + m71f1(RL315, RL313, RL316) + this.f258X[9], 11) + RL314;
        int RL318 = m70RL(RL313, 10);
        int RL319 = m70RL(RL314 + m71f1(RL317, RL315, RL318) + this.f258X[11], 11) + RL316;
        int RL320 = m70RL(RL315, 10);
        this.f248H0 += RL284;
        this.f249H1 += RL287;
        this.f250H2 += RL285;
        this.f251H3 += RL288;
        this.f252H4 += RL318;
        this.f253H5 += RL316;
        this.f254H6 += RL319;
        this.f255H7 += RL317;
        this.f256H8 += RL320;
        this.f257H9 += RL286;
        this.xOff = 0;
        int i11 = 0;
        while (true) {
            int[] iArr = this.f258X;
            if (i11 != iArr.length) {
                iArr[i11] = 0;
                i11++;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void processLength(long j) {
        if (this.xOff > 14) {
            processBlock();
        }
        int[] iArr = this.f258X;
        iArr[14] = (int) (-1 & j);
        iArr[15] = (int) (j >>> 32);
    }

    /* access modifiers changed from: protected */
    public void processWord(byte[] bArr, int i) {
        int[] iArr = this.f258X;
        int i2 = this.xOff;
        int i3 = i2 + 1;
        this.xOff = i3;
        iArr[i2] = ((bArr[i + 3] & UByte.MAX_VALUE) << 24) | (bArr[i] & UByte.MAX_VALUE) | ((bArr[i + 1] & UByte.MAX_VALUE) << 8) | ((bArr[i + 2] & UByte.MAX_VALUE) << Tnaf.POW_2_WIDTH);
        if (i3 == 16) {
            processBlock();
        }
    }

    public void reset() {
        super.reset();
        this.f248H0 = 1732584193;
        this.f249H1 = -271733879;
        this.f250H2 = -1732584194;
        this.f251H3 = 271733878;
        this.f252H4 = -1009589776;
        this.f253H5 = 1985229328;
        this.f254H6 = -19088744;
        this.f255H7 = -1985229329;
        this.f256H8 = 19088743;
        this.f257H9 = 1009589775;
        this.xOff = 0;
        int i = 0;
        while (true) {
            int[] iArr = this.f258X;
            if (i != iArr.length) {
                iArr[i] = 0;
                i++;
            } else {
                return;
            }
        }
    }

    public void reset(Memoable memoable) {
        doCopy((RIPEMD320Digest) memoable);
    }
}
