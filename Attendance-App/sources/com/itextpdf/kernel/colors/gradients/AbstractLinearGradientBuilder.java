package com.itextpdf.kernel.colors.gradients;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.PatternColor;
import com.itextpdf.kernel.colors.gradients.GradientColorStop;
import com.itextpdf.kernel.geom.AffineTransform;
import com.itextpdf.kernel.geom.NoninvertibleTransformException;
import com.itextpdf.kernel.geom.Point;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfNumber;
import com.itextpdf.kernel.pdf.colorspace.PdfPattern;
import com.itextpdf.kernel.pdf.colorspace.PdfShading;
import com.itextpdf.kernel.pdf.function.PdfFunction;
import com.itextpdf.p026io.LogMessageConstant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.slf4j.LoggerFactory;

public abstract class AbstractLinearGradientBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    protected static final double ZERO_EPSILON = 1.0E-10d;
    private GradientSpreadMethod spreadMethod = GradientSpreadMethod.NONE;
    private final List<GradientColorStop> stops = new ArrayList();

    /* access modifiers changed from: protected */
    public abstract Point[] getGradientVector(Rectangle rectangle, AffineTransform affineTransform);

    public AbstractLinearGradientBuilder addColorStop(GradientColorStop gradientColorStop) {
        if (gradientColorStop != null) {
            this.stops.add(gradientColorStop);
        }
        return this;
    }

    public AbstractLinearGradientBuilder setSpreadMethod(GradientSpreadMethod gradientSpreadMethod) {
        if (this.spreadMethod != null) {
            this.spreadMethod = gradientSpreadMethod;
        } else {
            this.spreadMethod = GradientSpreadMethod.NONE;
        }
        return this;
    }

    public List<GradientColorStop> getColorStops() {
        return new ArrayList(this.stops);
    }

    public GradientSpreadMethod getSpreadMethod() {
        return this.spreadMethod;
    }

    public Color buildColor(Rectangle targetBoundingBox, AffineTransform contextTransform, PdfDocument document) {
        Point[] baseCoordinatesVector = getGradientVector(targetBoundingBox, contextTransform);
        if (baseCoordinatesVector == null || this.stops.isEmpty()) {
            return null;
        }
        AffineTransform shadingTransform = new AffineTransform();
        if (contextTransform != null) {
            shadingTransform.concatenate(contextTransform);
        }
        AffineTransform gradientTransformation = getCurrentSpaceToGradientVectorSpaceTransformation(targetBoundingBox, contextTransform);
        if (gradientTransformation != null) {
            if (targetBoundingBox != null) {
                try {
                    targetBoundingBox = Rectangle.calculateBBox(Arrays.asList(new Point[]{gradientTransformation.inverseTransform(new Point((double) targetBoundingBox.getLeft(), (double) targetBoundingBox.getBottom()), (Point) null), gradientTransformation.inverseTransform(new Point((double) targetBoundingBox.getLeft(), (double) targetBoundingBox.getTop()), (Point) null), gradientTransformation.inverseTransform(new Point((double) targetBoundingBox.getRight(), (double) targetBoundingBox.getBottom()), (Point) null), gradientTransformation.inverseTransform(new Point((double) targetBoundingBox.getRight(), (double) targetBoundingBox.getTop()), (Point) null)}));
                } catch (NoninvertibleTransformException e) {
                    LoggerFactory.getLogger(getClass()).error(LogMessageConstant.UNABLE_TO_INVERT_GRADIENT_TRANSFORMATION);
                }
            }
            shadingTransform.concatenate(gradientTransformation);
        }
        PdfShading.Axial axial = createAxialShading(baseCoordinatesVector, this.stops, this.spreadMethod, targetBoundingBox);
        if (axial == null) {
            return null;
        }
        PdfPattern.Shading shading = new PdfPattern.Shading((PdfShading) axial);
        if (!shadingTransform.isIdentity()) {
            double[] matrix = new double[6];
            shadingTransform.getMatrix(matrix);
            shading.setMatrix(new PdfArray(matrix));
        }
        return new PatternColor(shading);
    }

    /* access modifiers changed from: protected */
    public AffineTransform getCurrentSpaceToGradientVectorSpaceTransformation(Rectangle targetBoundingBox, AffineTransform contextTransform) {
        return null;
    }

    protected static double[] evaluateCoveringDomain(Point[] coords, Rectangle toCover) {
        if (toCover == null) {
            return new double[]{0.0d, 1.0d};
        }
        AffineTransform transform = new AffineTransform();
        double d = 1.0d;
        double scale = 1.0d / coords[0].distance(coords[1]);
        double sin = (-(coords[1].getY() - coords[0].getY())) * scale;
        double cos = (coords[1].getX() - coords[0].getX()) * scale;
        if (Math.abs(cos) < ZERO_EPSILON) {
            cos = 0.0d;
            if (sin <= 0.0d) {
                d = -1.0d;
            }
            sin = d;
        } else if (Math.abs(sin) < ZERO_EPSILON) {
            sin = 0.0d;
            if (cos <= 0.0d) {
                d = -1.0d;
            }
            cos = d;
        }
        transform.concatenate(new AffineTransform(cos, sin, -sin, cos, 0.0d, 0.0d));
        transform.scale(scale, scale);
        transform.translate(-coords[0].getX(), -coords[0].getY());
        Point[] rectanglePoints = toCover.toPointsArray();
        Point point = null;
        double minX = transform.transform(rectanglePoints[0], (Point) null).getX();
        int i = 1;
        double maxX = minX;
        double d2 = scale;
        while (i < rectanglePoints.length) {
            double currentX = transform.transform(rectanglePoints[i], point).getX();
            minX = Math.min(minX, currentX);
            maxX = Math.max(maxX, currentX);
            i++;
            sin = sin;
            point = null;
        }
        return new double[]{minX, maxX};
    }

    protected static Point[] createCoordinatesForNewDomain(double[] newDomain, Point[] baseVector) {
        double xDiff = baseVector[1].getX() - baseVector[0].getX();
        double yDiff = baseVector[1].getY() - baseVector[0].getY();
        Point[] targetCoords = {baseVector[0].getLocation(), baseVector[1].getLocation()};
        targetCoords[0].translate(newDomain[0] * xDiff, newDomain[0] * yDiff);
        targetCoords[1].translate((newDomain[1] - 1.0d) * xDiff, (newDomain[1] - 1.0d) * yDiff);
        return targetCoords;
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x009b  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x00a0  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static com.itextpdf.kernel.pdf.colorspace.PdfShading.Axial createAxialShading(com.itextpdf.kernel.geom.Point[] r20, java.util.List<com.itextpdf.kernel.colors.gradients.GradientColorStop> r21, com.itextpdf.kernel.colors.gradients.GradientSpreadMethod r22, com.itextpdf.kernel.geom.Rectangle r23) {
        /*
            r0 = r20
            r1 = r22
            r2 = 1
            r3 = r0[r2]
            r4 = 0
            r5 = r0[r4]
            double r5 = r3.distance(r5)
            r3 = r21
            java.util.List r7 = normalizeStops(r3, r5)
            r8 = 2
            double[] r9 = new double[r8]
            r9 = {0, 4607182418800017408} // fill-array
            r10 = 0
            r11 = 4457293557087583675(0x3ddb7cdfd9d7bdbb, double:1.0E-10)
            int r13 = (r5 > r11 ? 1 : (r5 == r11 ? 0 : -1))
            if (r13 < 0) goto L_0x00a6
            int r13 = r7.size()
            if (r13 != r2) goto L_0x0030
            r13 = r23
            r16 = r5
            goto L_0x00aa
        L_0x0030:
            r13 = r23
            double[] r9 = evaluateCoveringDomain(r0, r13)
            com.itextpdf.kernel.colors.gradients.GradientSpreadMethod r8 = com.itextpdf.kernel.colors.gradients.GradientSpreadMethod.REPEAT
            if (r1 == r8) goto L_0x008c
            com.itextpdf.kernel.colors.gradients.GradientSpreadMethod r8 = com.itextpdf.kernel.colors.gradients.GradientSpreadMethod.REFLECT
            if (r1 != r8) goto L_0x0041
            r16 = r5
            goto L_0x008e
        L_0x0041:
            com.itextpdf.kernel.colors.gradients.GradientSpreadMethod r8 = com.itextpdf.kernel.colors.gradients.GradientSpreadMethod.PAD
            if (r1 != r8) goto L_0x004b
            adjustStopsForPadIfNeeded(r7, r9)
            r16 = r5
            goto L_0x0092
        L_0x004b:
            java.lang.Object r8 = r7.get(r4)
            com.itextpdf.kernel.colors.gradients.GradientColorStop r8 = (com.itextpdf.kernel.colors.gradients.GradientColorStop) r8
            double r14 = r8.getOffset()
            int r8 = r7.size()
            int r8 = r8 - r2
            java.lang.Object r8 = r7.get(r8)
            com.itextpdf.kernel.colors.gradients.GradientColorStop r8 = (com.itextpdf.kernel.colors.gradients.GradientColorStop) r8
            r16 = r5
            double r4 = r8.getOffset()
            double r18 = r4 - r14
            int r6 = (r18 > r11 ? 1 : (r18 == r11 ? 0 : -1))
            if (r6 < 0) goto L_0x008b
            r11 = r9[r2]
            int r6 = (r11 > r14 ? 1 : (r11 == r14 ? 0 : -1))
            if (r6 <= 0) goto L_0x008b
            r6 = 0
            r11 = r9[r6]
            int r8 = (r11 > r4 ? 1 : (r11 == r4 ? 0 : -1))
            if (r8 < 0) goto L_0x007a
            goto L_0x008b
        L_0x007a:
            r10 = r9[r6]
            double r10 = java.lang.Math.max(r10, r14)
            r9[r6] = r10
            r10 = r9[r2]
            double r10 = java.lang.Math.min(r10, r4)
            r9[r2] = r10
            goto L_0x0092
        L_0x008b:
            return r10
        L_0x008c:
            r16 = r5
        L_0x008e:
            java.util.List r7 = adjustNormalizedStopsToCoverDomain(r7, r9, r1)
        L_0x0092:
            r4 = 0
            r4 = r9[r4]
            r10 = r9[r2]
            int r2 = (r4 > r10 ? 1 : (r4 == r10 ? 0 : -1))
            if (r2 > 0) goto L_0x00a0
            com.itextpdf.kernel.geom.Point[] r2 = createCoordinatesForNewDomain(r9, r0)
            goto L_0x00fd
        L_0x00a0:
            java.lang.AssertionError r2 = new java.lang.AssertionError
            r2.<init>()
            throw r2
        L_0x00a6:
            r13 = r23
            r16 = r5
        L_0x00aa:
            com.itextpdf.kernel.colors.gradients.GradientSpreadMethod r4 = com.itextpdf.kernel.colors.gradients.GradientSpreadMethod.NONE
            if (r1 != r4) goto L_0x00af
            return r10
        L_0x00af:
            com.itextpdf.kernel.geom.Point[] r4 = new com.itextpdf.kernel.geom.Point[r8]
            com.itextpdf.kernel.geom.Point r5 = new com.itextpdf.kernel.geom.Point
            float r6 = r23.getLeft()
            double r10 = (double) r6
            float r6 = r23.getBottom()
            double r14 = (double) r6
            r5.<init>((double) r10, (double) r14)
            r6 = 0
            r4[r6] = r5
            com.itextpdf.kernel.geom.Point r5 = new com.itextpdf.kernel.geom.Point
            float r6 = r23.getRight()
            double r10 = (double) r6
            float r6 = r23.getBottom()
            double r14 = (double) r6
            r5.<init>((double) r10, (double) r14)
            r4[r2] = r5
            int r5 = r7.size()
            int r5 = r5 - r2
            java.lang.Object r5 = r7.get(r5)
            com.itextpdf.kernel.colors.gradients.GradientColorStop r5 = (com.itextpdf.kernel.colors.gradients.GradientColorStop) r5
            com.itextpdf.kernel.colors.gradients.GradientColorStop[] r6 = new com.itextpdf.kernel.colors.gradients.GradientColorStop[r8]
            com.itextpdf.kernel.colors.gradients.GradientColorStop r8 = new com.itextpdf.kernel.colors.gradients.GradientColorStop
            r10 = 0
            com.itextpdf.kernel.colors.gradients.GradientColorStop$OffsetType r12 = com.itextpdf.kernel.colors.gradients.GradientColorStop.OffsetType.RELATIVE
            r8.<init>((com.itextpdf.kernel.colors.gradients.GradientColorStop) r5, (double) r10, (com.itextpdf.kernel.colors.gradients.GradientColorStop.OffsetType) r12)
            r10 = 0
            r6[r10] = r8
            com.itextpdf.kernel.colors.gradients.GradientColorStop r8 = new com.itextpdf.kernel.colors.gradients.GradientColorStop
            r10 = 4607182418800017408(0x3ff0000000000000, double:1.0)
            com.itextpdf.kernel.colors.gradients.GradientColorStop$OffsetType r12 = com.itextpdf.kernel.colors.gradients.GradientColorStop.OffsetType.RELATIVE
            r8.<init>((com.itextpdf.kernel.colors.gradients.GradientColorStop) r5, (double) r10, (com.itextpdf.kernel.colors.gradients.GradientColorStop.OffsetType) r12)
            r6[r2] = r8
            java.util.List r7 = java.util.Arrays.asList(r6)
            r2 = r4
        L_0x00fd:
            com.itextpdf.kernel.pdf.colorspace.PdfShading$Axial r4 = new com.itextpdf.kernel.pdf.colorspace.PdfShading$Axial
            com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs$Rgb r5 = new com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs$Rgb
            r5.<init>()
            com.itextpdf.kernel.pdf.PdfArray r6 = createCoordsPdfArray(r2)
            com.itextpdf.kernel.pdf.PdfArray r8 = new com.itextpdf.kernel.pdf.PdfArray
            r8.<init>((double[]) r9)
            com.itextpdf.kernel.pdf.function.PdfFunction r10 = constructFunction(r7)
            r4.<init>(r5, r6, r8, r10)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.itextpdf.kernel.colors.gradients.AbstractLinearGradientBuilder.createAxialShading(com.itextpdf.kernel.geom.Point[], java.util.List, com.itextpdf.kernel.colors.gradients.GradientSpreadMethod, com.itextpdf.kernel.geom.Rectangle):com.itextpdf.kernel.pdf.colorspace.PdfShading$Axial");
    }

    private static List<GradientColorStop> normalizeStops(List<GradientColorStop> toNormalize, double baseVectorLength) {
        if (baseVectorLength < ZERO_EPSILON) {
            return Arrays.asList(new GradientColorStop[]{new GradientColorStop(toNormalize.get(toNormalize.size() - 1), 0.0d, GradientColorStop.OffsetType.RELATIVE)});
        }
        List<GradientColorStop> result = copyStopsAndNormalizeAbsoluteOffsets(toNormalize, baseVectorLength);
        normalizeFirstStopOffset(result);
        normalizeAutoStops(result);
        normalizeHintsOffsets(result);
        return result;
    }

    private static void normalizeHintsOffsets(List<GradientColorStop> result) {
        for (int i = 0; i < result.size() - 1; i++) {
            GradientColorStop stopColor = result.get(i);
            if (stopColor.getHintOffsetType() == GradientColorStop.HintOffsetType.RELATIVE_ON_GRADIENT) {
                double currentStopOffset = stopColor.getOffset();
                double nextStopOffset = result.get(i + 1).getOffset();
                if (currentStopOffset != nextStopOffset) {
                    stopColor.setHint((stopColor.getHintOffset() - currentStopOffset) / (nextStopOffset - currentStopOffset), GradientColorStop.HintOffsetType.RELATIVE_BETWEEN_COLORS);
                } else {
                    stopColor.setHint(0.0d, GradientColorStop.HintOffsetType.NONE);
                }
            }
        }
        result.get(result.size() - 1).setHint(0.0d, GradientColorStop.HintOffsetType.NONE);
    }

    private static void normalizeAutoStops(List<GradientColorStop> toNormalize) {
        double d;
        if (toNormalize.get(0).getOffsetType() == GradientColorStop.OffsetType.RELATIVE) {
            int firstAutoStopIndex = 1;
            GradientColorStop firstStopColor = toNormalize.get(0);
            double prevOffset = firstStopColor.getHintOffsetType() == GradientColorStop.HintOffsetType.RELATIVE_ON_GRADIENT ? firstStopColor.getHintOffset() : firstStopColor.getOffset();
            for (int i = 1; i < toNormalize.size(); i++) {
                GradientColorStop currentStop = toNormalize.get(i);
                if (currentStop.getOffsetType() != GradientColorStop.OffsetType.AUTO) {
                    if (firstAutoStopIndex < i) {
                        normalizeAutoStops(toNormalize, firstAutoStopIndex, i, prevOffset, currentStop.getOffset());
                    }
                    firstAutoStopIndex = i + 1;
                    if (currentStop.getHintOffsetType() == GradientColorStop.HintOffsetType.RELATIVE_ON_GRADIENT) {
                        d = currentStop.getHintOffset();
                    } else {
                        d = currentStop.getOffset();
                    }
                    prevOffset = d;
                } else if (currentStop.getHintOffsetType() == GradientColorStop.HintOffsetType.RELATIVE_ON_GRADIENT) {
                    double hintOffset = currentStop.getHintOffset();
                    normalizeAutoStops(toNormalize, firstAutoStopIndex, i + 1, prevOffset, hintOffset);
                    prevOffset = hintOffset;
                    firstAutoStopIndex = i + 1;
                }
            }
            if (firstAutoStopIndex < toNormalize.size()) {
                normalizeAutoStops(toNormalize, firstAutoStopIndex, toNormalize.size(), prevOffset, Math.max(1.0d, prevOffset));
                return;
            }
            return;
        }
        throw new AssertionError();
    }

    private static void normalizeAutoStops(List<GradientColorStop> toNormalizeList, int fromIndex, int toIndex, double prevOffset, double nextOffset) {
        if (toIndex >= fromIndex) {
            double min = (double) ((Math.min(toIndex, toNormalizeList.size() - 1) - fromIndex) + 1);
            Double.isNaN(min);
            double offsetShift = (nextOffset - prevOffset) / min;
            double currentOffset = prevOffset;
            int i = fromIndex;
            while (i < toIndex) {
                currentOffset += offsetShift;
                GradientColorStop currentAutoStop = toNormalizeList.get(i);
                if (currentAutoStop.getOffsetType() == GradientColorStop.OffsetType.AUTO) {
                    currentAutoStop.setOffset(currentOffset, GradientColorStop.OffsetType.RELATIVE);
                    i++;
                } else {
                    throw new AssertionError();
                }
            }
            return;
        }
        throw new AssertionError();
    }

    private static void normalizeFirstStopOffset(List<GradientColorStop> result) {
        GradientColorStop firstStop = result.get(0);
        if (firstStop.getOffsetType() == GradientColorStop.OffsetType.AUTO) {
            double firstStopOffset = 0.0d;
            Iterator<GradientColorStop> it = result.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                GradientColorStop stopColor = it.next();
                if (stopColor.getOffsetType() != GradientColorStop.OffsetType.RELATIVE) {
                    if (stopColor.getHintOffsetType() == GradientColorStop.HintOffsetType.RELATIVE_ON_GRADIENT) {
                        firstStopOffset = stopColor.getHintOffset();
                        break;
                    }
                } else {
                    firstStopOffset = stopColor.getOffset();
                    break;
                }
            }
            firstStop.setOffset(Math.min(0.0d, firstStopOffset), GradientColorStop.OffsetType.RELATIVE);
        }
    }

    private static List<GradientColorStop> copyStopsAndNormalizeAbsoluteOffsets(List<GradientColorStop> toNormalize, double baseVectorLength) {
        double lastUsedOffset = Double.NEGATIVE_INFINITY;
        List<GradientColorStop> copy = new ArrayList<>(toNormalize.size());
        for (GradientColorStop stop : toNormalize) {
            double offset = stop.getOffset();
            GradientColorStop.OffsetType offsetType = stop.getOffsetType();
            if (offsetType == GradientColorStop.OffsetType.ABSOLUTE) {
                offsetType = GradientColorStop.OffsetType.RELATIVE;
                offset /= baseVectorLength;
            }
            if (offsetType == GradientColorStop.OffsetType.RELATIVE) {
                if (offset < lastUsedOffset) {
                    offset = lastUsedOffset;
                }
                lastUsedOffset = offset;
            }
            GradientColorStop result = new GradientColorStop(stop, offset, offsetType);
            double hintOffset = stop.getHintOffset();
            GradientColorStop.HintOffsetType hintOffsetType = stop.getHintOffsetType();
            if (hintOffsetType == GradientColorStop.HintOffsetType.ABSOLUTE_ON_GRADIENT) {
                hintOffsetType = GradientColorStop.HintOffsetType.RELATIVE_ON_GRADIENT;
                hintOffset /= baseVectorLength;
            }
            if (hintOffsetType == GradientColorStop.HintOffsetType.RELATIVE_ON_GRADIENT) {
                if (hintOffset < lastUsedOffset) {
                    hintOffset = lastUsedOffset;
                }
                lastUsedOffset = hintOffset;
            }
            result.setHint(hintOffset, hintOffsetType);
            copy.add(result);
        }
        return copy;
    }

    private static void adjustStopsForPadIfNeeded(List<GradientColorStop> stopsToConstruct, double[] coordinatesDomain) {
        GradientColorStop firstStop = stopsToConstruct.get(0);
        if (coordinatesDomain[0] < firstStop.getOffset()) {
            stopsToConstruct.add(0, new GradientColorStop(firstStop, coordinatesDomain[0], GradientColorStop.OffsetType.RELATIVE));
        }
        GradientColorStop lastStop = stopsToConstruct.get(stopsToConstruct.size() - 1);
        if (coordinatesDomain[1] > lastStop.getOffset()) {
            stopsToConstruct.add(new GradientColorStop(lastStop, coordinatesDomain[1], GradientColorStop.OffsetType.RELATIVE));
        }
    }

    private static List<GradientColorStop> adjustNormalizedStopsToCoverDomain(List<GradientColorStop> normalizedStops, double[] targetDomain, GradientSpreadMethod spreadMethod2) {
        char c;
        int currentIterationIndex;
        double d;
        boolean isIterationInverse;
        int currentIterationIndex2;
        List<GradientColorStop> list = normalizedStops;
        GradientSpreadMethod gradientSpreadMethod = spreadMethod2;
        List<GradientColorStop> adjustedStops = new ArrayList<>();
        GradientColorStop lastColorStop = list.get(normalizedStops.size() - 1);
        double lastComputedOffset = lastColorStop.getOffset();
        double originalIntervalStart = list.get(0).getOffset();
        double originalIntervalLength = lastComputedOffset - originalIntervalStart;
        if (originalIntervalLength <= ZERO_EPSILON) {
            return Arrays.asList(new GradientColorStop[]{new GradientColorStop(lastColorStop, targetDomain[0], GradientColorStop.OffsetType.RELATIVE), new GradientColorStop(lastColorStop, targetDomain[1], GradientColorStop.OffsetType.RELATIVE)});
        }
        double startIntervalsShift = Math.floor((targetDomain[0] - originalIntervalStart) / originalIntervalLength);
        double iterationOffset = originalIntervalStart + (originalIntervalLength * startIntervalsShift);
        boolean isIterationInverse2 = gradientSpreadMethod == GradientSpreadMethod.REFLECT && Math.abs(startIntervalsShift) % 2.0d != 0.0d;
        if (isIterationInverse2) {
            c = 1;
            currentIterationIndex = normalizedStops.size() - 1;
        } else {
            c = 1;
            currentIterationIndex = 0;
        }
        double lastComputedOffset2 = iterationOffset;
        while (lastComputedOffset2 <= targetDomain[c]) {
            GradientColorStop currentStop = list.get(currentIterationIndex);
            if (isIterationInverse2) {
                d = (iterationOffset + lastComputedOffset) - currentStop.getOffset();
            } else {
                d = (iterationOffset + currentStop.getOffset()) - originalIntervalStart;
            }
            double lastComputedOffset3 = d;
            GradientColorStop lastColorStop2 = lastColorStop;
            double originalIntervalEnd = lastComputedOffset;
            double lastComputedOffset4 = lastComputedOffset3;
            GradientColorStop computedStop = new GradientColorStop(currentStop, lastComputedOffset4, GradientColorStop.OffsetType.RELATIVE);
            if (lastComputedOffset4 >= targetDomain[0] || adjustedStops.isEmpty()) {
                adjustedStops.add(computedStop);
            } else {
                adjustedStops.set(0, computedStop);
            }
            if (isIterationInverse2) {
                currentIterationIndex2 = currentIterationIndex - 1;
                if (currentIterationIndex2 < 0) {
                    iterationOffset += originalIntervalLength;
                    isIterationInverse2 = false;
                    currentIterationIndex2 = 1;
                }
            } else {
                currentIterationIndex2 = currentIterationIndex + 1;
                if (currentIterationIndex2 == normalizedStops.size()) {
                    iterationOffset += originalIntervalLength;
                    isIterationInverse2 = gradientSpreadMethod == GradientSpreadMethod.REFLECT;
                    currentIterationIndex2 = isIterationInverse2 ? normalizedStops.size() - 2 : 0;
                }
            }
            if (isIterationInverse2) {
                GradientColorStop nextColor = list.get(currentIterationIndex);
                isIterationInverse = isIterationInverse2;
                computedStop.setHint(1.0d - nextColor.getHintOffset(), nextColor.getHintOffsetType());
            } else {
                isIterationInverse = isIterationInverse2;
                computedStop.setHint(currentStop.getHintOffset(), currentStop.getHintOffsetType());
            }
            list = normalizedStops;
            gradientSpreadMethod = spreadMethod2;
            isIterationInverse2 = isIterationInverse;
            lastColorStop = lastColorStop2;
            lastComputedOffset2 = lastComputedOffset4;
            lastComputedOffset = originalIntervalEnd;
        }
        return adjustedStops;
    }

    private static PdfFunction constructFunction(List<GradientColorStop> toConstruct) {
        List<GradientColorStop> list = toConstruct;
        int functionsAmount = toConstruct.size() - 1;
        double[] bounds = new double[(functionsAmount - 1)];
        List<PdfFunction> type2Functions = new ArrayList<>(functionsAmount);
        GradientColorStop nextStop = list.get(0);
        double domainStart = nextStop.getOffset();
        for (int i = 1; i < functionsAmount; i++) {
            GradientColorStop currentStop = nextStop;
            nextStop = list.get(i);
            bounds[i - 1] = nextStop.getOffset();
            type2Functions.add(constructSingleGradientSegmentFunction(currentStop, nextStop));
        }
        GradientColorStop nextStop2 = list.get(toConstruct.size() - 1);
        type2Functions.add(constructSingleGradientSegmentFunction(nextStop, nextStop2));
        double domainEnd = nextStop2.getOffset();
        double[] encode = new double[(functionsAmount * 2)];
        for (int i2 = 0; i2 < encode.length; i2 += 2) {
            encode[i2] = 0.0d;
            encode[i2 + 1] = 1.0d;
        }
        double[] dArr = encode;
        return new PdfFunction.Type3(new PdfArray(new double[]{domainStart, domainEnd}), (PdfArray) null, type2Functions, new PdfArray(bounds), new PdfArray(encode));
    }

    private static PdfFunction constructSingleGradientSegmentFunction(GradientColorStop from, GradientColorStop to) {
        double exponent = 1.0d;
        float[] fromColor = from.getRgbArray();
        float[] toColor = to.getRgbArray();
        if (from.getHintOffsetType() == GradientColorStop.HintOffsetType.RELATIVE_BETWEEN_COLORS) {
            double hintOffset = from.getHintOffset();
            if (hintOffset <= ZERO_EPSILON) {
                fromColor = toColor;
            } else if (hintOffset >= 0.9999999999d) {
                toColor = fromColor;
            } else {
                exponent = Math.log(0.5d) / Math.log(hintOffset);
            }
        }
        return new PdfFunction.Type2(new PdfArray(new float[]{0.0f, 1.0f}), (PdfArray) null, new PdfArray(fromColor), new PdfArray(toColor), new PdfNumber(exponent));
    }

    private static PdfArray createCoordsPdfArray(Point[] coordsPoints) {
        if (coordsPoints == null || coordsPoints.length != 2) {
            throw new AssertionError();
        }
        return new PdfArray(new double[]{coordsPoints[0].getX(), coordsPoints[0].getY(), coordsPoints[1].getX(), coordsPoints[1].getY()});
    }
}
