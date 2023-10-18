package com.google.android.material.color.utilities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class QuantizerWsmeans {
    private static final int MAX_ITERATIONS = 10;
    private static final double MIN_MOVEMENT_DISTANCE = 3.0d;

    private QuantizerWsmeans() {
    }

    private static final class Distance implements Comparable<Distance> {
        double distance = -1.0d;
        int index = -1;

        Distance() {
        }

        public int compareTo(Distance other) {
            return Double.valueOf(this.distance).compareTo(Double.valueOf(other.distance));
        }
    }

    public static Map<Integer, Integer> quantize(int[] inputPixels, int[] startingClusters, int maxColors) {
        PointProvider pointProvider;
        PointProvider pointProvider2;
        int pointCount;
        double[] componentCSums;
        PointProvider pointProvider3;
        double[] componentBSums;
        double[] componentASums;
        int[] iArr = inputPixels;
        int[] iArr2 = startingClusters;
        Map<Integer, Integer> pixelToCount = new HashMap<>();
        double[][] points = new double[iArr.length][];
        int[] pixels = new int[iArr.length];
        PointProvider pointProvider4 = new PointProviderLab();
        int pointCount2 = 0;
        for (int inputPixel : iArr) {
            Integer pixelCount = pixelToCount.get(Integer.valueOf(inputPixel));
            if (pixelCount == null) {
                points[pointCount2] = pointProvider4.fromInt(inputPixel);
                pixels[pointCount2] = inputPixel;
                pointCount2++;
                pixelToCount.put(Integer.valueOf(inputPixel), 1);
            } else {
                pixelToCount.put(Integer.valueOf(inputPixel), Integer.valueOf(pixelCount.intValue() + 1));
            }
        }
        int[] counts = new int[pointCount2];
        for (int i = 0; i < pointCount2; i++) {
            counts[i] = pixelToCount.get(Integer.valueOf(pixels[i])).intValue();
        }
        int clusterCount = Math.min(maxColors, pointCount2);
        if (iArr2.length != 0) {
            clusterCount = Math.min(clusterCount, iArr2.length);
        }
        double[][] clusters = new double[clusterCount][];
        int clustersCreated = 0;
        for (int i2 = 0; i2 < iArr2.length; i2++) {
            clusters[i2] = pointProvider4.fromInt(iArr2[i2]);
            clustersCreated++;
        }
        int additionalClustersNeeded = clusterCount - clustersCreated;
        if (additionalClustersNeeded > 0) {
            for (int i3 = 0; i3 < additionalClustersNeeded; i3++) {
            }
        }
        int[] clusterIndices = new int[pointCount2];
        int i4 = 0;
        while (i4 < pointCount2) {
            double random = Math.random();
            double d = (double) clusterCount;
            Double.isNaN(d);
            clusterIndices[i4] = (int) Math.floor(random * d);
            i4++;
            int[] iArr3 = inputPixels;
            int[] iArr4 = startingClusters;
        }
        int[][] indexMatrix = new int[clusterCount][];
        for (int i5 = 0; i5 < clusterCount; i5++) {
            indexMatrix[i5] = new int[clusterCount];
        }
        Distance[][] distanceToIndexMatrix = new Distance[clusterCount][];
        for (int i6 = 0; i6 < clusterCount; i6++) {
            distanceToIndexMatrix[i6] = new Distance[clusterCount];
            for (int j = 0; j < clusterCount; j++) {
                distanceToIndexMatrix[i6][j] = new Distance();
            }
        }
        int[] pixelCountSums = new int[clusterCount];
        int iteration = 0;
        while (true) {
            Map<Integer, Integer> pixelToCount2 = pixelToCount;
            if (iteration >= 10) {
                Distance[][] distanceArr = distanceToIndexMatrix;
                double[][] dArr = points;
                int[] iArr5 = pixels;
                pointProvider = pointProvider4;
                int[] iArr6 = counts;
                int i7 = clustersCreated;
                int i8 = additionalClustersNeeded;
                int[] iArr7 = clusterIndices;
                int i9 = iteration;
                int clustersCreated2 = pointCount2;
                break;
            }
            int i10 = 0;
            while (i10 < clusterCount) {
                int[] pixels2 = pixels;
                int j2 = i10 + 1;
                while (j2 < clusterCount) {
                    int clustersCreated3 = clustersCreated;
                    double distance = pointProvider4.distance(clusters[i10], clusters[j2]);
                    distanceToIndexMatrix[j2][i10].distance = distance;
                    distanceToIndexMatrix[j2][i10].index = i10;
                    distanceToIndexMatrix[i10][j2].distance = distance;
                    distanceToIndexMatrix[i10][j2].index = j2;
                    j2++;
                    int i11 = maxColors;
                    clustersCreated = clustersCreated3;
                    additionalClustersNeeded = additionalClustersNeeded;
                }
                int clustersCreated4 = clustersCreated;
                int additionalClustersNeeded2 = additionalClustersNeeded;
                Arrays.sort(distanceToIndexMatrix[i10]);
                for (int j3 = 0; j3 < clusterCount; j3++) {
                    indexMatrix[i10][j3] = distanceToIndexMatrix[i10][j3].index;
                }
                i10++;
                int i12 = maxColors;
                clustersCreated = clustersCreated4;
                pixels = pixels2;
                additionalClustersNeeded = additionalClustersNeeded2;
            }
            int[] pixels3 = pixels;
            int clustersCreated5 = clustersCreated;
            int additionalClustersNeeded3 = additionalClustersNeeded;
            int pointsMoved = 0;
            int i13 = 0;
            while (i13 < pointCount2) {
                double[] point = points[i13];
                int previousClusterIndex = clusterIndices[i13];
                double[] previousCluster = clusters[previousClusterIndex];
                double previousDistance = pointProvider4.distance(point, previousCluster);
                double minimumDistance = previousDistance;
                int[][] indexMatrix2 = indexMatrix;
                int newClusterIndex = -1;
                double[] dArr2 = previousCluster;
                int j4 = 0;
                while (j4 < clusterCount) {
                    Distance[][] distanceToIndexMatrix2 = distanceToIndexMatrix;
                    int pointCount3 = pointCount2;
                    int[] counts2 = counts;
                    if (distanceToIndexMatrix[previousClusterIndex][j4].distance < 4.0d * previousDistance) {
                        double distance2 = pointProvider4.distance(point, clusters[j4]);
                        if (distance2 < minimumDistance) {
                            minimumDistance = distance2;
                            newClusterIndex = j4;
                        }
                    }
                    j4++;
                    pointCount2 = pointCount3;
                    distanceToIndexMatrix = distanceToIndexMatrix2;
                    counts = counts2;
                }
                Distance[][] distanceToIndexMatrix3 = distanceToIndexMatrix;
                int pointCount4 = pointCount2;
                int[] counts3 = counts;
                if (newClusterIndex != -1 && Math.abs(Math.sqrt(minimumDistance) - Math.sqrt(previousDistance)) > MIN_MOVEMENT_DISTANCE) {
                    pointsMoved++;
                    clusterIndices[i13] = newClusterIndex;
                }
                i13++;
                indexMatrix = indexMatrix2;
                pointCount2 = pointCount4;
                distanceToIndexMatrix = distanceToIndexMatrix3;
                counts = counts3;
            }
            int[][] indexMatrix3 = indexMatrix;
            Distance[][] distanceToIndexMatrix4 = distanceToIndexMatrix;
            int pointsMoved2 = pointCount2;
            int[] counts4 = counts;
            if (pointsMoved == 0 && iteration != 0) {
                double[][] dArr3 = points;
                pointProvider = pointProvider4;
                int[] iArr8 = clusterIndices;
                int i14 = pointsMoved2;
                break;
            }
            double[] componentASums2 = new double[clusterCount];
            double[] componentBSums2 = new double[clusterCount];
            double[] componentCSums2 = new double[clusterCount];
            char c = 0;
            Arrays.fill(pixelCountSums, 0);
            int i15 = 0;
            while (true) {
                pointCount = pointsMoved2;
                if (i15 >= pointCount) {
                    break;
                }
                int clusterIndex = clusterIndices[i15];
                double[] point2 = points[i15];
                int count = counts4[i15];
                pixelCountSums[clusterIndex] = pixelCountSums[clusterIndex] + count;
                double d2 = componentASums2[clusterIndex];
                double d3 = point2[c];
                double d4 = (double) count;
                Double.isNaN(d4);
                componentASums2[clusterIndex] = d2 + (d3 * d4);
                double d5 = componentBSums2[clusterIndex];
                double d6 = point2[1];
                int pointsMoved3 = pointsMoved;
                double d7 = (double) count;
                Double.isNaN(d7);
                componentBSums2[clusterIndex] = d5 + (d6 * d7);
                double d8 = componentCSums2[clusterIndex];
                double d9 = point2[2];
                int[] clusterIndices2 = clusterIndices;
                double d10 = (double) count;
                Double.isNaN(d10);
                componentCSums2[clusterIndex] = d8 + (d9 * d10);
                i15++;
                clusterIndices = clusterIndices2;
                iteration = iteration;
                points = points;
                pointsMoved = pointsMoved3;
                c = 0;
                pointsMoved2 = pointCount;
            }
            double[][] points2 = points;
            int i16 = i15;
            int[] clusterIndices3 = clusterIndices;
            int iteration2 = iteration;
            int i17 = 0;
            while (i17 < clusterCount) {
                int count2 = pixelCountSums[i17];
                if (count2 == 0) {
                    clusters[i17] = new double[]{0.0d, 0.0d, 0.0d};
                    componentASums = componentASums2;
                    componentBSums = componentBSums2;
                    componentCSums = componentCSums2;
                    pointProvider3 = pointProvider4;
                } else {
                    double d11 = componentASums2[i17];
                    double d12 = (double) count2;
                    Double.isNaN(d12);
                    double a = d11 / d12;
                    double d13 = componentBSums2[i17];
                    componentASums = componentASums2;
                    componentBSums = componentBSums2;
                    double d14 = (double) count2;
                    Double.isNaN(d14);
                    double b = d13 / d14;
                    double d15 = componentCSums2[i17];
                    componentCSums = componentCSums2;
                    pointProvider3 = pointProvider4;
                    double d16 = (double) count2;
                    Double.isNaN(d16);
                    clusters[i17][0] = a;
                    clusters[i17][1] = b;
                    clusters[i17][2] = d15 / d16;
                }
                i17++;
                componentASums2 = componentASums;
                componentBSums2 = componentBSums;
                pointProvider4 = pointProvider3;
                componentCSums2 = componentCSums;
            }
            double[] dArr4 = componentBSums2;
            double[] dArr5 = componentCSums2;
            PointProvider pointProvider5 = pointProvider4;
            iteration = iteration2 + 1;
            int i18 = maxColors;
            pointCount2 = pointCount;
            pixelToCount = pixelToCount2;
            clustersCreated = clustersCreated5;
            pixels = pixels3;
            additionalClustersNeeded = additionalClustersNeeded3;
            clusterIndices = clusterIndices3;
            indexMatrix = indexMatrix3;
            points = points2;
            distanceToIndexMatrix = distanceToIndexMatrix4;
            counts = counts4;
        }
        Map<Integer, Integer> argbToPopulation = new HashMap<>();
        int i19 = 0;
        while (i19 < clusterCount) {
            int count3 = pixelCountSums[i19];
            if (count3 == 0) {
                pointProvider2 = pointProvider;
            } else {
                pointProvider2 = pointProvider;
                int possibleNewCluster = pointProvider2.toInt(clusters[i19]);
                if (!argbToPopulation.containsKey(Integer.valueOf(possibleNewCluster))) {
                    argbToPopulation.put(Integer.valueOf(possibleNewCluster), Integer.valueOf(count3));
                }
            }
            i19++;
            pointProvider = pointProvider2;
        }
        return argbToPopulation;
    }
}
