package com.google.android.material.color.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class Score {
    private static final double CUTOFF_CHROMA = 15.0d;
    private static final double CUTOFF_EXCITED_PROPORTION = 0.01d;
    private static final double CUTOFF_TONE = 10.0d;
    private static final double TARGET_CHROMA = 48.0d;
    private static final double WEIGHT_CHROMA_ABOVE = 0.3d;
    private static final double WEIGHT_CHROMA_BELOW = 0.1d;
    private static final double WEIGHT_PROPORTION = 0.7d;

    private Score() {
    }

    public static List<Integer> score(Map<Integer, Integer> colorsToPopulation) {
        List<Integer> filteredColors;
        Map<Integer, Double> filteredColorsToScore;
        Map<Integer, Cam16> colorsToCam;
        double[] hueProportions;
        double populationSum = 0.0d;
        for (Map.Entry<Integer, Integer> entry : colorsToPopulation.entrySet()) {
            double intValue = (double) entry.getValue().intValue();
            Double.isNaN(intValue);
            populationSum += intValue;
        }
        Map<Integer, Cam16> colorsToCam2 = new HashMap<>();
        double[] hueProportions2 = new double[361];
        for (Map.Entry<Integer, Integer> entry2 : colorsToPopulation.entrySet()) {
            int color = entry2.getKey().intValue();
            double population = (double) entry2.getValue().intValue();
            Double.isNaN(population);
            Cam16 cam = Cam16.fromInt(color);
            colorsToCam2.put(Integer.valueOf(color), cam);
            int hue = (int) Math.round(cam.getHue());
            hueProportions2[hue] = hueProportions2[hue] + (population / populationSum);
        }
        Map<Integer, Double> colorsToExcitedProportion = new HashMap<>();
        for (Map.Entry<Integer, Cam16> entry3 : colorsToCam2.entrySet()) {
            int color2 = entry3.getKey().intValue();
            int hue2 = (int) Math.round(entry3.getValue().getHue());
            double excitedProportion = 0.0d;
            for (int j = hue2 - 15; j < hue2 + 15; j++) {
                excitedProportion += hueProportions2[MathUtils.sanitizeDegreesInt(j)];
            }
            colorsToExcitedProportion.put(Integer.valueOf(color2), Double.valueOf(excitedProportion));
        }
        Map<Integer, Double> colorsToScore = new HashMap<>();
        for (Map.Entry<Integer, Cam16> entry4 : colorsToCam2.entrySet()) {
            int color3 = entry4.getKey().intValue();
            Cam16 cam2 = entry4.getValue();
            colorsToScore.put(Integer.valueOf(color3), Double.valueOf((100.0d * colorsToExcitedProportion.get(Integer.valueOf(color3)).doubleValue() * WEIGHT_PROPORTION) + ((cam2.getChroma() - TARGET_CHROMA) * (cam2.getChroma() < TARGET_CHROMA ? WEIGHT_CHROMA_BELOW : WEIGHT_CHROMA_ABOVE))));
            populationSum = populationSum;
        }
        List<Integer> filteredColors2 = filter(colorsToExcitedProportion, colorsToCam2);
        Map<Integer, Double> filteredColorsToScore2 = new HashMap<>();
        for (Integer intValue2 : filteredColors2) {
            int color4 = intValue2.intValue();
            filteredColorsToScore2.put(Integer.valueOf(color4), colorsToScore.get(Integer.valueOf(color4)));
        }
        List<Map.Entry<Integer, Double>> entryList = new ArrayList<>(filteredColorsToScore2.entrySet());
        Collections.sort(entryList, new ScoredComparator());
        List<Integer> colorsByScoreDescending = new ArrayList<>();
        for (Map.Entry<Integer, Double> entry5 : entryList) {
            Cam16 cam3 = colorsToCam2.get(Integer.valueOf(entry5.getKey().intValue()));
            boolean duplicateHue = false;
            Iterator<Integer> it = colorsByScoreDescending.iterator();
            while (true) {
                if (!it.hasNext()) {
                    filteredColors = filteredColors2;
                    filteredColorsToScore = filteredColorsToScore2;
                    colorsToCam = colorsToCam2;
                    hueProportions = hueProportions2;
                    break;
                }
                filteredColors = filteredColors2;
                filteredColorsToScore = filteredColorsToScore2;
                colorsToCam = colorsToCam2;
                hueProportions = hueProportions2;
                if (MathUtils.differenceDegrees(cam3.getHue(), colorsToCam2.get(it.next()).getHue()) < CUTOFF_CHROMA) {
                    duplicateHue = true;
                    break;
                }
                filteredColors2 = filteredColors;
                filteredColorsToScore2 = filteredColorsToScore;
                colorsToCam2 = colorsToCam;
                hueProportions2 = hueProportions;
            }
            if (duplicateHue) {
                filteredColors2 = filteredColors;
                filteredColorsToScore2 = filteredColorsToScore;
                colorsToCam2 = colorsToCam;
                hueProportions2 = hueProportions;
            } else {
                colorsByScoreDescending.add(entry5.getKey());
                filteredColors2 = filteredColors;
                filteredColorsToScore2 = filteredColorsToScore;
                colorsToCam2 = colorsToCam;
                hueProportions2 = hueProportions;
            }
        }
        Map<Integer, Double> map = filteredColorsToScore2;
        Map<Integer, Cam16> map2 = colorsToCam2;
        double[] dArr = hueProportions2;
        if (colorsByScoreDescending.isEmpty()) {
            colorsByScoreDescending.add(-12417548);
        }
        return colorsByScoreDescending;
    }

    private static List<Integer> filter(Map<Integer, Double> colorsToExcitedProportion, Map<Integer, Cam16> colorsToCam) {
        List<Integer> filtered = new ArrayList<>();
        for (Map.Entry<Integer, Cam16> entry : colorsToCam.entrySet()) {
            int color = entry.getKey().intValue();
            double proportion = colorsToExcitedProportion.get(Integer.valueOf(color)).doubleValue();
            if (entry.getValue().getChroma() >= CUTOFF_CHROMA && ColorUtils.lstarFromArgb(color) >= 10.0d && proportion >= CUTOFF_EXCITED_PROPORTION) {
                filtered.add(Integer.valueOf(color));
            }
        }
        return filtered;
    }

    static class ScoredComparator implements Comparator<Map.Entry<Integer, Double>> {
        public int compare(Map.Entry<Integer, Double> entry1, Map.Entry<Integer, Double> entry2) {
            return -entry1.getValue().compareTo(entry2.getValue());
        }
    }
}
