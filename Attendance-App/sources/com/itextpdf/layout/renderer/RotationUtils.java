package com.itextpdf.layout.renderer;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.layout.layout.LayoutArea;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.minmaxwidth.MinMaxWidth;
import com.itextpdf.layout.minmaxwidth.MinMaxWidthUtils;
import com.itextpdf.layout.minmaxwidth.RotationMinMaxWidth;
import java.util.HashMap;

final class RotationUtils {
    private RotationUtils() {
    }

    public static MinMaxWidth countRotationMinMaxWidth(MinMaxWidth minMaxWidth, AbstractRenderer renderer) {
        PropertiesBackup backup = new PropertiesBackup(renderer);
        Float rotation = backup.storeFloatProperty(55);
        if (rotation != null) {
            float angle = rotation.floatValue();
            LayoutResult layoutResult = renderer.layout(new LayoutContext(new LayoutArea(1, new Rectangle(minMaxWidth.getMaxWidth() + MinMaxWidthUtils.getEps(), 1000000.0f))));
            if (layoutResult.getOccupiedArea() != null) {
                Rectangle layoutBBox = layoutResult.getOccupiedArea().getBBox();
                if (MinMaxWidthUtils.isEqual((double) minMaxWidth.getMinWidth(), (double) minMaxWidth.getMaxWidth())) {
                    backup.restoreProperty(55);
                    float rotatedWidth = (float) RotationMinMaxWidth.calculateRotatedWidth(layoutBBox, (double) angle);
                    return new MinMaxWidth(rotatedWidth, rotatedWidth, 0.0f);
                }
                RotationMinMaxWidth rotationMinMaxWidth = RotationMinMaxWidth.calculate((double) angle, (double) (layoutResult.getOccupiedArea().getBBox().getWidth() * layoutResult.getOccupiedArea().getBBox().getHeight()), minMaxWidth);
                Float rotatedMinWidth = getLayoutRotatedWidth(renderer, (float) rotationMinMaxWidth.getMinWidthOrigin(), layoutBBox, (double) angle);
                if (rotatedMinWidth != null) {
                    if (rotatedMinWidth.floatValue() > rotationMinMaxWidth.getMaxWidth()) {
                        rotationMinMaxWidth.setChildrenMinWidth(rotatedMinWidth.floatValue());
                        Float rotatedMaxWidth = getLayoutRotatedWidth(renderer, (float) rotationMinMaxWidth.getMaxWidthOrigin(), layoutBBox, (double) angle);
                        if (rotatedMaxWidth == null || rotatedMaxWidth.floatValue() <= rotatedMinWidth.floatValue()) {
                            rotationMinMaxWidth.setChildrenMaxWidth(rotatedMinWidth.floatValue());
                        } else {
                            rotationMinMaxWidth.setChildrenMaxWidth(rotatedMaxWidth.floatValue());
                        }
                    } else {
                        rotationMinMaxWidth.setChildrenMinWidth(rotatedMinWidth.floatValue());
                    }
                    backup.restoreProperty(55);
                    return rotationMinMaxWidth;
                }
            }
        }
        backup.restoreProperty(55);
        return minMaxWidth;
    }

    public static Float retrieveRotatedLayoutWidth(float availableWidth, AbstractRenderer renderer) {
        float f = availableWidth;
        AbstractRenderer abstractRenderer = renderer;
        PropertiesBackup backup = new PropertiesBackup(abstractRenderer);
        Float rotation = backup.storeFloatProperty(55);
        if (rotation != null && abstractRenderer.getProperty(77) == null) {
            float angle = rotation.floatValue();
            backup.storeProperty(27);
            backup.storeProperty(85);
            backup.storeProperty(84);
            MinMaxWidth minMaxWidth = renderer.getMinMaxWidth();
            float length = ((minMaxWidth.getMaxWidth() + minMaxWidth.getMinWidth()) / 2.0f) + MinMaxWidthUtils.getEps();
            LayoutResult layoutResult = abstractRenderer.layout(new LayoutContext(new LayoutArea(1, new Rectangle(length, 1000000.0f))));
            backup.restoreProperty(27);
            backup.restoreProperty(85);
            backup.restoreProperty(84);
            Rectangle additions = new Rectangle(0.0f, 0.0f);
            abstractRenderer.applyPaddings(additions, true);
            abstractRenderer.applyBorderBox(additions, true);
            abstractRenderer.applyMargins(additions, true);
            if (layoutResult.getOccupiedArea() != null) {
                float f2 = length;
                RotationMinMaxWidth result = RotationMinMaxWidth.calculate((double) angle, (double) (layoutResult.getOccupiedArea().getBBox().getWidth() * layoutResult.getOccupiedArea().getBBox().getHeight()), minMaxWidth, (double) f);
                if (result != null) {
                    backup.restoreProperty(55);
                    if (result.getMaxWidthHeight() > result.getMinWidthHeight()) {
                        double minWidthOrigin = result.getMinWidthOrigin();
                        double width = (double) additions.getWidth();
                        Double.isNaN(width);
                        double d = minWidthOrigin - width;
                        double eps = (double) MinMaxWidthUtils.getEps();
                        Double.isNaN(eps);
                        return Float.valueOf((float) (d + eps));
                    }
                    double maxWidthOrigin = result.getMaxWidthOrigin();
                    double width2 = (double) additions.getWidth();
                    Double.isNaN(width2);
                    double d2 = maxWidthOrigin - width2;
                    double eps2 = (double) MinMaxWidthUtils.getEps();
                    Double.isNaN(eps2);
                    return Float.valueOf((float) (d2 + eps2));
                }
            }
        }
        backup.restoreProperty(55);
        return abstractRenderer.retrieveWidth(f);
    }

    private static Float getLayoutRotatedWidth(AbstractRenderer renderer, float availableWidth, Rectangle previousBBox, double angle) {
        if (MinMaxWidthUtils.isEqual((double) availableWidth, (double) previousBBox.getWidth())) {
            return Float.valueOf((float) RotationMinMaxWidth.calculateRotatedWidth(previousBBox, angle));
        }
        LayoutResult result = renderer.layout(new LayoutContext(new LayoutArea(1, new Rectangle(MinMaxWidthUtils.getEps() + availableWidth, 1000000.0f))));
        if (result.getOccupiedArea() != null) {
            return Float.valueOf((float) RotationMinMaxWidth.calculateRotatedWidth(result.getOccupiedArea().getBBox(), angle));
        }
        return null;
    }

    private static class PropertiesBackup {
        private HashMap<Integer, PropertyBackup> propertiesBackup = new HashMap<>();
        private AbstractRenderer renderer;

        public PropertiesBackup(AbstractRenderer renderer2) {
            this.renderer = renderer2;
        }

        public Float storeFloatProperty(int property) {
            Float value = this.renderer.getPropertyAsFloat(property);
            if (value != null) {
                this.propertiesBackup.put(Integer.valueOf(property), new PropertyBackup(value, this.renderer.hasOwnProperty(property)));
                this.renderer.setProperty(property, (Object) null);
            }
            return value;
        }

        public <T> T storeProperty(int property) {
            T value = this.renderer.getProperty(property);
            if (value != null) {
                this.propertiesBackup.put(Integer.valueOf(property), new PropertyBackup(value, this.renderer.hasOwnProperty(property)));
                this.renderer.setProperty(property, (Object) null);
            }
            return value;
        }

        public void restoreProperty(int property) {
            PropertyBackup backup = this.propertiesBackup.remove(Integer.valueOf(property));
            if (backup == null) {
                return;
            }
            if (backup.isOwnedByRender()) {
                this.renderer.setProperty(property, backup.getValue());
            } else {
                this.renderer.deleteOwnProperty(property);
            }
        }

        private static class PropertyBackup {
            private boolean isOwnedByRender;
            private Object propertyValue;

            public PropertyBackup(Object propertyValue2, boolean isOwnedByRender2) {
                this.propertyValue = propertyValue2;
                this.isOwnedByRender = isOwnedByRender2;
            }

            public Object getValue() {
                return this.propertyValue;
            }

            public boolean isOwnedByRender() {
                return this.isOwnedByRender;
            }
        }
    }
}
