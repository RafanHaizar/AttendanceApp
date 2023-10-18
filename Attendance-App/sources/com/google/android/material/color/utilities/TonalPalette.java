package com.google.android.material.color.utilities;

import java.util.HashMap;
import java.util.Map;

public final class TonalPalette {
    Map<Integer, Integer> cache = new HashMap();
    double chroma;
    double hue;

    public static final TonalPalette fromInt(int argb) {
        Hct hct = Hct.fromInt(argb);
        return fromHueAndChroma(hct.getHue(), hct.getChroma());
    }

    public static final TonalPalette fromHueAndChroma(double hue2, double chroma2) {
        return new TonalPalette(hue2, chroma2);
    }

    private TonalPalette(double hue2, double chroma2) {
        this.hue = hue2;
        this.chroma = chroma2;
    }

    public int tone(int tone) {
        Integer color = this.cache.get(Integer.valueOf(tone));
        if (color == null) {
            color = Integer.valueOf(Hct.from(this.hue, this.chroma, (double) tone).toInt());
            this.cache.put(Integer.valueOf(tone), color);
        }
        return color.intValue();
    }
}
