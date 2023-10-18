package com.google.android.material.color.utilities;

import java.util.HashMap;
import java.util.Map;

public final class QuantizerMap implements Quantizer {
    Map<Integer, Integer> colorToCount;

    public QuantizerResult quantize(int[] pixels, int colorCount) {
        HashMap<Integer, Integer> pixelByCount = new HashMap<>();
        for (int pixel : pixels) {
            Integer currentPixelCount = pixelByCount.get(Integer.valueOf(pixel));
            int newPixelCount = 1;
            if (currentPixelCount != null) {
                newPixelCount = 1 + currentPixelCount.intValue();
            }
            pixelByCount.put(Integer.valueOf(pixel), Integer.valueOf(newPixelCount));
        }
        this.colorToCount = pixelByCount;
        return new QuantizerResult(pixelByCount);
    }

    public Map<Integer, Integer> getColorToCount() {
        return this.colorToCount;
    }
}
