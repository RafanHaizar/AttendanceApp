package com.itextpdf.kernel.geom;

import java.io.Serializable;

public class PageSize extends Rectangle implements Cloneable, Serializable {

    /* renamed from: A0 */
    public static PageSize f1260A0 = new PageSize(2384.0f, 3370.0f);

    /* renamed from: A1 */
    public static PageSize f1261A1 = new PageSize(1684.0f, 2384.0f);
    public static PageSize A10 = new PageSize(74.0f, 105.0f);

    /* renamed from: A2 */
    public static PageSize f1262A2 = new PageSize(1190.0f, 1684.0f);

    /* renamed from: A3 */
    public static PageSize f1263A3 = new PageSize(842.0f, 1190.0f);

    /* renamed from: A4 */
    public static PageSize f1264A4 = new PageSize(595.0f, 842.0f);

    /* renamed from: A5 */
    public static PageSize f1265A5 = new PageSize(420.0f, 595.0f);

    /* renamed from: A6 */
    public static PageSize f1266A6 = new PageSize(298.0f, 420.0f);

    /* renamed from: A7 */
    public static PageSize f1267A7 = new PageSize(210.0f, 298.0f);

    /* renamed from: A8 */
    public static PageSize f1268A8 = new PageSize(148.0f, 210.0f);

    /* renamed from: A9 */
    public static PageSize f1269A9 = new PageSize(105.0f, 547.0f);

    /* renamed from: B0 */
    public static PageSize f1270B0 = new PageSize(2834.0f, 4008.0f);

    /* renamed from: B1 */
    public static PageSize f1271B1 = new PageSize(2004.0f, 2834.0f);
    public static PageSize B10 = new PageSize(88.0f, 124.0f);

    /* renamed from: B2 */
    public static PageSize f1272B2 = new PageSize(1417.0f, 2004.0f);

    /* renamed from: B3 */
    public static PageSize f1273B3 = new PageSize(1000.0f, 1417.0f);

    /* renamed from: B4 */
    public static PageSize f1274B4 = new PageSize(708.0f, 1000.0f);

    /* renamed from: B5 */
    public static PageSize f1275B5 = new PageSize(498.0f, 708.0f);

    /* renamed from: B6 */
    public static PageSize f1276B6 = new PageSize(354.0f, 498.0f);

    /* renamed from: B7 */
    public static PageSize f1277B7 = new PageSize(249.0f, 354.0f);

    /* renamed from: B8 */
    public static PageSize f1278B8 = new PageSize(175.0f, 249.0f);

    /* renamed from: B9 */
    public static PageSize f1279B9 = new PageSize(124.0f, 175.0f);
    public static PageSize Default = f1264A4;
    public static PageSize EXECUTIVE = new PageSize(522.0f, 756.0f);
    public static PageSize LEDGER = new PageSize(1224.0f, 792.0f);
    public static PageSize LEGAL = new PageSize(612.0f, 1008.0f);
    public static PageSize LETTER = new PageSize(612.0f, 792.0f);
    public static PageSize TABLOID = new PageSize(792.0f, 1224.0f);
    private static final long serialVersionUID = 485375591249386160L;

    public PageSize(float width, float height) {
        super(0.0f, 0.0f, width, height);
    }

    public PageSize(Rectangle box) {
        super(box.getX(), box.getY(), box.getWidth(), box.getHeight());
    }

    public PageSize rotate() {
        return new PageSize(this.height, this.width);
    }

    public Rectangle clone() {
        return super.clone();
    }
}
