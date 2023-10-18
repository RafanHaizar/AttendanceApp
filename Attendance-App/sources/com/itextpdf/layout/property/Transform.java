package com.itextpdf.layout.property;

import com.itextpdf.kernel.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class Transform {
    private List<SingleTransform> multipleTransform;

    public Transform(int length) {
        this.multipleTransform = new ArrayList(length);
    }

    public void addSingleTransform(SingleTransform singleTransform) {
        this.multipleTransform.add(singleTransform);
    }

    private List<SingleTransform> getMultipleTransform() {
        return this.multipleTransform;
    }

    public static AffineTransform getAffineTransform(Transform t, float width, float height) {
        float f;
        List<SingleTransform> multipleTransform2 = t.getMultipleTransform();
        AffineTransform affineTransform = new AffineTransform();
        for (int k = multipleTransform2.size() - 1; k >= 0; k--) {
            SingleTransform transform = multipleTransform2.get(k);
            float[] floats = new float[6];
            for (int i = 0; i < 4; i++) {
                floats[i] = transform.getFloats()[i];
            }
            int i2 = 4;
            while (i2 < 6) {
                if (transform.getUnitValues()[i2 - 4].getUnitType() == 1) {
                    f = transform.getUnitValues()[i2 - 4].getValue();
                } else {
                    f = (transform.getUnitValues()[i2 - 4].getValue() / 100.0f) * (i2 == 4 ? width : height);
                }
                floats[i2] = f;
                i2++;
            }
            affineTransform.preConcatenate(new AffineTransform(floats));
        }
        return affineTransform;
    }

    public static class SingleTransform {

        /* renamed from: a */
        private float f1539a;

        /* renamed from: b */
        private float f1540b;

        /* renamed from: c */
        private float f1541c;

        /* renamed from: d */
        private float f1542d;

        /* renamed from: tx */
        private UnitValue f1543tx;

        /* renamed from: ty */
        private UnitValue f1544ty;

        public SingleTransform() {
            this.f1539a = 1.0f;
            this.f1540b = 0.0f;
            this.f1541c = 0.0f;
            this.f1542d = 1.0f;
            this.f1543tx = new UnitValue(1, 0.0f);
            this.f1544ty = new UnitValue(1, 0.0f);
        }

        public SingleTransform(float a, float b, float c, float d, UnitValue tx, UnitValue ty) {
            this.f1539a = a;
            this.f1540b = b;
            this.f1541c = c;
            this.f1542d = d;
            this.f1543tx = tx;
            this.f1544ty = ty;
        }

        public float[] getFloats() {
            return new float[]{this.f1539a, this.f1540b, this.f1541c, this.f1542d};
        }

        public UnitValue[] getUnitValues() {
            return new UnitValue[]{this.f1543tx, this.f1544ty};
        }
    }
}
