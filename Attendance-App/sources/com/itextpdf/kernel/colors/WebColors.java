package com.itextpdf.kernel.colors;

import androidx.recyclerview.widget.ItemTouchHelper;
import com.itextpdf.kernel.xmp.XMPError;
import com.itextpdf.p026io.LogMessageConstant;
import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import java.util.HashMap;
import java.util.StringTokenizer;
import org.bouncycastle.crypto.tls.CipherSuite;
import org.bouncycastle.math.Primes;
import org.slf4j.LoggerFactory;

public class WebColors extends HashMap<String, int[]> {
    public static final WebColors NAMES;
    private static final double RGB_MAX_VAL = 255.0d;
    private static final long serialVersionUID = 3542523100813372896L;

    static {
        WebColors webColors = new WebColors();
        NAMES = webColors;
        webColors.put("aliceblue", new int[]{240, 248, 255, 255});
        webColors.put("antiquewhite", new int[]{ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 235, 215, 255});
        webColors.put("aqua", new int[]{0, 255, 255, 255});
        webColors.put("aquamarine", new int[]{127, 255, 212, 255});
        webColors.put("azure", new int[]{240, 255, 255, 255});
        webColors.put("beige", new int[]{245, 245, 220, 255});
        webColors.put("bisque", new int[]{255, 228, CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA256, 255});
        webColors.put("black", new int[]{0, 0, 0, 255});
        webColors.put("blanchedalmond", new int[]{255, 235, 205, 255});
        webColors.put("blue", new int[]{0, 0, 255, 255});
        webColors.put("blueviolet", new int[]{CipherSuite.TLS_PSK_WITH_RC4_128_SHA, 43, 226, 255});
        webColors.put("brown", new int[]{CipherSuite.TLS_DH_DSS_WITH_AES_256_GCM_SHA384, 42, 42, 255});
        webColors.put("burlywood", new int[]{222, CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA256, CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA, 255});
        webColors.put("cadetblue", new int[]{95, CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256, 255});
        webColors.put("chartreuse", new int[]{127, 255, 0, 255});
        webColors.put("chocolate", new int[]{210, 105, 30, 255});
        webColors.put("coral", new int[]{255, 127, 80, 255});
        webColors.put("cornflowerblue", new int[]{100, CipherSuite.TLS_RSA_PSK_WITH_AES_256_CBC_SHA, 237, 255});
        webColors.put("cornsilk", new int[]{255, 248, 220, 255});
        webColors.put("crimson", new int[]{220, 20, 60, 255});
        webColors.put("cyan", new int[]{0, 255, 255, 255});
        webColors.put("darkblue", new int[]{0, 0, CipherSuite.TLS_PSK_WITH_3DES_EDE_CBC_SHA, 255});
        webColors.put("darkcyan", new int[]{0, CipherSuite.TLS_PSK_WITH_3DES_EDE_CBC_SHA, CipherSuite.TLS_PSK_WITH_3DES_EDE_CBC_SHA, 255});
        webColors.put("darkgoldenrod", new int[]{CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA256, CipherSuite.TLS_DH_RSA_WITH_CAMELLIA_256_CBC_SHA, 11, 255});
        webColors.put("darkgray", new int[]{CipherSuite.TLS_PSK_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_PSK_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_PSK_WITH_AES_256_GCM_SHA384, 255});
        webColors.put("darkgrey", new int[]{CipherSuite.TLS_PSK_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_PSK_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_PSK_WITH_AES_256_GCM_SHA384, 255});
        webColors.put("darkgreen", new int[]{0, 100, 0, 255});
        webColors.put("darkkhaki", new int[]{CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA256, CipherSuite.TLS_RSA_PSK_WITH_AES_256_CBC_SHA384, 107, 255});
        webColors.put("darkmagenta", new int[]{CipherSuite.TLS_PSK_WITH_3DES_EDE_CBC_SHA, 0, CipherSuite.TLS_PSK_WITH_3DES_EDE_CBC_SHA, 255});
        webColors.put("darkolivegreen", new int[]{85, 107, 47, 255});
        webColors.put("darkorange", new int[]{255, CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA, 0, 255});
        webColors.put("darkorchid", new int[]{CipherSuite.TLS_DHE_DSS_WITH_SEED_CBC_SHA, 50, XMPError.BADSTREAM, 255});
        webColors.put("darkred", new int[]{CipherSuite.TLS_PSK_WITH_3DES_EDE_CBC_SHA, 0, 0, 255});
        webColors.put("darksalmon", new int[]{233, CipherSuite.TLS_RSA_WITH_SEED_CBC_SHA, 122, 255});
        webColors.put("darkseagreen", new int[]{CipherSuite.TLS_DHE_PSK_WITH_3DES_EDE_CBC_SHA, 188, CipherSuite.TLS_DHE_PSK_WITH_3DES_EDE_CBC_SHA, 255});
        webColors.put("darkslateblue", new int[]{72, 61, CipherSuite.TLS_PSK_WITH_3DES_EDE_CBC_SHA, 255});
        webColors.put("darkslategray", new int[]{47, 79, 79, 255});
        webColors.put("darkslategrey", new int[]{47, 79, 79, 255});
        webColors.put("darkturquoise", new int[]{0, 206, 209, 255});
        webColors.put("darkviolet", new int[]{CipherSuite.TLS_RSA_PSK_WITH_AES_128_CBC_SHA, 0, Primes.SMALL_FACTOR_LIMIT, 255});
        webColors.put("deeppink", new int[]{255, 20, CipherSuite.TLS_RSA_PSK_WITH_3DES_EDE_CBC_SHA, 255});
        webColors.put("deepskyblue", new int[]{0, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA256, 255, 255});
        webColors.put("dimgray", new int[]{105, 105, 105, 255});
        webColors.put("dimgrey", new int[]{105, 105, 105, 255});
        webColors.put("dodgerblue", new int[]{30, CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA, 255, 255});
        webColors.put("firebrick", new int[]{CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA256, 34, 34, 255});
        webColors.put("floralwhite", new int[]{255, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 240, 255});
        webColors.put("forestgreen", new int[]{34, CipherSuite.TLS_PSK_WITH_3DES_EDE_CBC_SHA, 34, 255});
        webColors.put("fuchsia", new int[]{255, 0, 255, 255});
        webColors.put("gainsboro", new int[]{220, 220, 220, 255});
        webColors.put("ghostwhite", new int[]{248, 248, 255, 255});
        webColors.put("gold", new int[]{255, 215, 0, 255});
        webColors.put("goldenrod", new int[]{218, CipherSuite.TLS_DH_DSS_WITH_AES_256_GCM_SHA384, 32, 255});
        webColors.put("gray", new int[]{128, 128, 128, 255});
        webColors.put("grey", new int[]{128, 128, 128, 255});
        webColors.put("green", new int[]{0, 128, 0, 255});
        webColors.put("greenyellow", new int[]{CipherSuite.TLS_RSA_PSK_WITH_AES_256_GCM_SHA384, 255, 47, 255});
        webColors.put("honeydew", new int[]{240, 255, 240, 255});
        webColors.put("hotpink", new int[]{255, 105, 180, 255});
        webColors.put("indianred", new int[]{205, 92, 92, 255});
        webColors.put("indigo", new int[]{75, 0, 130, 255});
        webColors.put("ivory", new int[]{255, 255, 240, 255});
        webColors.put("khaki", new int[]{240, 230, CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA, 255});
        webColors.put("lavender", new int[]{230, 230, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 255});
        webColors.put("lavenderblush", new int[]{255, 240, 245, 255});
        webColors.put("lawngreen", new int[]{124, 252, 0, 255});
        webColors.put("lemonchiffon", new int[]{255, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 205, 255});
        webColors.put("lightblue", new int[]{CipherSuite.TLS_RSA_PSK_WITH_AES_256_GCM_SHA384, 216, 230, 255});
        webColors.put("lightcoral", new int[]{240, 128, 128, 255});
        webColors.put("lightcyan", new int[]{224, 255, 255, 255});
        webColors.put("lightgoldenrodyellow", new int[]{ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 210, 255});
        webColors.put("lightgreen", new int[]{CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA, 238, CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA, 255});
        webColors.put("lightgray", new int[]{Primes.SMALL_FACTOR_LIMIT, Primes.SMALL_FACTOR_LIMIT, Primes.SMALL_FACTOR_LIMIT, 255});
        webColors.put("lightgrey", new int[]{Primes.SMALL_FACTOR_LIMIT, Primes.SMALL_FACTOR_LIMIT, Primes.SMALL_FACTOR_LIMIT, 255});
        webColors.put("lightpink", new int[]{255, CipherSuite.TLS_RSA_PSK_WITH_AES_128_CBC_SHA256, CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA256, 255});
        webColors.put("lightsalmon", new int[]{255, CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256, 122, 255});
        webColors.put("lightseagreen", new int[]{32, CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA256, CipherSuite.TLS_DHE_PSK_WITH_AES_128_GCM_SHA256, 255});
        webColors.put("lightskyblue", new int[]{CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA, 206, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 255});
        webColors.put("lightslategray", new int[]{119, 136, CipherSuite.TLS_DHE_DSS_WITH_SEED_CBC_SHA, 255});
        webColors.put("lightslategrey", new int[]{119, 136, CipherSuite.TLS_DHE_DSS_WITH_SEED_CBC_SHA, 255});
        webColors.put("lightsteelblue", new int[]{CipherSuite.TLS_PSK_WITH_NULL_SHA256, CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA256, 222, 255});
        webColors.put("lightyellow", new int[]{255, 255, 224, 255});
        webColors.put("lime", new int[]{0, 255, 0, 255});
        webColors.put("limegreen", new int[]{50, 205, 50, 255});
        webColors.put("linen", new int[]{ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 240, 230, 255});
        webColors.put("magenta", new int[]{255, 0, 255, 255});
        webColors.put("maroon", new int[]{128, 0, 0, 255});
        webColors.put("mediumaquamarine", new int[]{102, 205, CipherSuite.TLS_DHE_PSK_WITH_AES_128_GCM_SHA256, 255});
        webColors.put("mediumblue", new int[]{0, 0, 205, 255});
        webColors.put("mediumorchid", new int[]{CipherSuite.TLS_RSA_WITH_CAMELLIA_128_CBC_SHA256, 85, Primes.SMALL_FACTOR_LIMIT, 255});
        webColors.put("mediumpurple", new int[]{CipherSuite.TLS_RSA_PSK_WITH_3DES_EDE_CBC_SHA, 112, 219, 255});
        webColors.put("mediumseagreen", new int[]{60, CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA384, 113, 255});
        webColors.put("mediumslateblue", new int[]{123, 104, 238, 255});
        webColors.put("mediumspringgreen", new int[]{0, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, CipherSuite.TLS_DHE_RSA_WITH_SEED_CBC_SHA, 255});
        webColors.put("mediumturquoise", new int[]{72, 209, XMPError.BADSTREAM, 255});
        webColors.put("mediumvioletred", new int[]{199, 21, CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA, 255});
        webColors.put("midnightblue", new int[]{25, 25, 112, 255});
        webColors.put("mintcream", new int[]{245, 255, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 255});
        webColors.put("mistyrose", new int[]{255, 228, 225, 255});
        webColors.put("moccasin", new int[]{255, 228, CipherSuite.TLS_DHE_PSK_WITH_NULL_SHA384, 255});
        webColors.put("navajowhite", new int[]{255, 222, CipherSuite.TLS_RSA_PSK_WITH_AES_256_GCM_SHA384, 255});
        webColors.put("navy", new int[]{0, 0, 128, 255});
        webColors.put("oldlace", new int[]{253, 245, 230, 255});
        webColors.put("olive", new int[]{128, 128, 0, 255});
        webColors.put("olivedrab", new int[]{107, CipherSuite.TLS_DHE_PSK_WITH_RC4_128_SHA, 35, 255});
        webColors.put("orange", new int[]{255, CipherSuite.TLS_DH_DSS_WITH_AES_256_GCM_SHA384, 0, 255});
        webColors.put("orangered", new int[]{255, 69, 0, 255});
        webColors.put("orchid", new int[]{218, 112, 214, 255});
        webColors.put("palegoldenrod", new int[]{238, 232, CipherSuite.TLS_DHE_PSK_WITH_AES_128_GCM_SHA256, 255});
        webColors.put("palegreen", new int[]{CipherSuite.TLS_DH_RSA_WITH_SEED_CBC_SHA, 251, CipherSuite.TLS_DH_RSA_WITH_SEED_CBC_SHA, 255});
        webColors.put("paleturquoise", new int[]{CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA384, 238, 238, 255});
        webColors.put("palevioletred", new int[]{219, 112, CipherSuite.TLS_RSA_PSK_WITH_3DES_EDE_CBC_SHA, 255});
        webColors.put("papayawhip", new int[]{255, 239, 213, 255});
        webColors.put("peachpuff", new int[]{255, 218, CipherSuite.TLS_RSA_PSK_WITH_NULL_SHA384, 255});
        webColors.put("peru", new int[]{205, CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA, 63, 255});
        webColors.put("pink", new int[]{255, 192, XMPError.BADXMP, 255});
        webColors.put("plum", new int[]{221, CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256, 221, 255});
        webColors.put("powderblue", new int[]{CipherSuite.TLS_PSK_WITH_NULL_SHA256, 224, 230, 255});
        webColors.put("purple", new int[]{128, 0, 128, 255});
        webColors.put("red", new int[]{255, 0, 0, 255});
        webColors.put("rosybrown", new int[]{188, CipherSuite.TLS_DHE_PSK_WITH_3DES_EDE_CBC_SHA, CipherSuite.TLS_DHE_PSK_WITH_3DES_EDE_CBC_SHA, 255});
        webColors.put("royalblue", new int[]{65, 105, 225, 255});
        webColors.put("saddlebrown", new int[]{CipherSuite.TLS_PSK_WITH_3DES_EDE_CBC_SHA, 69, 19, 255});
        webColors.put("salmon", new int[]{ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 128, 114, 255});
        webColors.put("sandybrown", new int[]{244, CipherSuite.TLS_DH_DSS_WITH_AES_128_GCM_SHA256, 96, 255});
        webColors.put("seagreen", new int[]{46, CipherSuite.TLS_PSK_WITH_3DES_EDE_CBC_SHA, 87, 255});
        webColors.put("seashell", new int[]{255, 245, 238, 255});
        webColors.put("sienna", new int[]{CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256, 82, 45, 255});
        webColors.put("silver", new int[]{192, 192, 192, 255});
        webColors.put("skyblue", new int[]{CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA, 206, 235, 255});
        webColors.put("slateblue", new int[]{106, 90, 205, 255});
        webColors.put("slategray", new int[]{112, 128, CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA, 255});
        webColors.put("slategrey", new int[]{112, 128, CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA, 255});
        webColors.put("snow", new int[]{255, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 255});
        webColors.put("springgreen", new int[]{0, 255, 127, 255});
        webColors.put("steelblue", new int[]{70, 130, 180, 255});
        webColors.put("tan", new int[]{210, 180, CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA, 255});
        webColors.put("teal", new int[]{0, 128, 128, 255});
        webColors.put("thistle", new int[]{216, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA256, 216, 255});
        webColors.put("tomato", new int[]{255, 99, 71, 255});
        webColors.put(CommonCssConstants.TRANSPARENT, new int[]{255, 255, 255, 0});
        webColors.put("turquoise", new int[]{64, 224, 208, 255});
        webColors.put("violet", new int[]{238, 130, 238, 255});
        webColors.put("wheat", new int[]{245, 222, CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA384, 255});
        webColors.put("white", new int[]{255, 255, 255, 255});
        webColors.put("whitesmoke", new int[]{245, 245, 245, 255});
        webColors.put("yellow", new int[]{255, 255, 0, 255});
        webColors.put("yellowgreen", new int[]{CipherSuite.TLS_DHE_RSA_WITH_SEED_CBC_SHA, 205, 50, 255});
    }

    public static DeviceRgb getRGBColor(String name) {
        float[] rgbaColor = getRGBAColor(name);
        if (rgbaColor == null) {
            return new DeviceRgb(0, 0, 0);
        }
        return new DeviceRgb(rgbaColor[0], rgbaColor[1], rgbaColor[2]);
    }

    public static float[] getRGBAColor(String name) {
        float[] color = null;
        try {
            String colorName = name.toLowerCase();
            boolean colorStrWithoutHash = missingHashColorFormat(colorName);
            if (!colorName.startsWith("#")) {
                if (!colorStrWithoutHash) {
                    if (colorName.startsWith("rgb(")) {
                        Object obj = "rgb(), \t\r\n\f";
                        float[] color2 = {0.0f, 0.0f, 0.0f, 1.0f};
                        parseRGBColors(color2, new StringTokenizer(colorName, "rgb(), \t\r\n\f"));
                        return color2;
                    }
                    if (colorName.startsWith("rgba(")) {
                        Object obj2 = "rgba(), \t\r\n\f";
                        StringTokenizer tok = new StringTokenizer(colorName, "rgba(), \t\r\n\f");
                        color = new float[]{0.0f, 0.0f, 0.0f, 1.0f};
                        parseRGBColors(color, tok);
                        if (tok.hasMoreTokens()) {
                            color[3] = getAlphaChannelValue(tok.nextToken());
                        }
                    } else {
                        WebColors webColors = NAMES;
                        if (webColors.containsKey(colorName)) {
                            int[] intColor = (int[]) webColors.get(colorName);
                            float[] color3 = {0.0f, 0.0f, 0.0f, 1.0f};
                            double d = (double) intColor[0];
                            Double.isNaN(d);
                            color3[0] = (float) (d / RGB_MAX_VAL);
                            double d2 = (double) intColor[1];
                            Double.isNaN(d2);
                            color3[1] = (float) (d2 / RGB_MAX_VAL);
                            double d3 = (double) intColor[2];
                            Double.isNaN(d3);
                            color3[2] = (float) (d3 / RGB_MAX_VAL);
                            return color3;
                        }
                    }
                    return color;
                }
            }
            if (!colorStrWithoutHash) {
                colorName = colorName.substring(1);
            }
            if (colorName.length() == 3) {
                String red = colorName.substring(0, 1);
                float[] color4 = {0.0f, 0.0f, 0.0f, 1.0f};
                double parseInt = (double) Integer.parseInt(red + red, 16);
                Double.isNaN(parseInt);
                color4[0] = (float) (parseInt / RGB_MAX_VAL);
                String green = colorName.substring(1, 2);
                double parseInt2 = (double) Integer.parseInt(green + green, 16);
                Double.isNaN(parseInt2);
                color4[1] = (float) (parseInt2 / RGB_MAX_VAL);
                String blue = colorName.substring(2);
                double parseInt3 = (double) Integer.parseInt(blue + blue, 16);
                Double.isNaN(parseInt3);
                color4[2] = (float) (parseInt3 / RGB_MAX_VAL);
                return color4;
            } else if (colorName.length() == 6) {
                float[] color5 = {0.0f, 0.0f, 0.0f, 1.0f};
                double parseInt4 = (double) Integer.parseInt(colorName.substring(0, 2), 16);
                Double.isNaN(parseInt4);
                color5[0] = (float) (parseInt4 / RGB_MAX_VAL);
                double parseInt5 = (double) Integer.parseInt(colorName.substring(2, 4), 16);
                Double.isNaN(parseInt5);
                color5[1] = (float) (parseInt5 / RGB_MAX_VAL);
                double parseInt6 = (double) Integer.parseInt(colorName.substring(4), 16);
                Double.isNaN(parseInt6);
                color5[2] = (float) (parseInt6 / RGB_MAX_VAL);
                return color5;
            } else {
                LoggerFactory.getLogger((Class<?>) WebColors.class).error(LogMessageConstant.UNKNOWN_COLOR_FORMAT_MUST_BE_RGB_OR_RRGGBB);
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private static void parseRGBColors(float[] color, StringTokenizer tok) {
        for (int k = 0; k < 3; k++) {
            if (tok.hasMoreTokens()) {
                color[k] = getRGBChannelValue(tok.nextToken());
                color[k] = Math.max(0.0f, color[k]);
                color[k] = Math.min(1.0f, color[k]);
            }
        }
    }

    private static boolean missingHashColorFormat(String colStr) {
        int len = colStr.length();
        if (len == 3 || len == 6) {
            return colStr.matches("[0-9a-f]{" + len + "}");
        }
        return false;
    }

    private static float getRGBChannelValue(String rgbChannel) {
        if (rgbChannel.endsWith(CommonCssConstants.PERCENTAGE)) {
            return parsePercentValue(rgbChannel);
        }
        double parseInt = (double) Integer.parseInt(rgbChannel);
        Double.isNaN(parseInt);
        return (float) (parseInt / RGB_MAX_VAL);
    }

    private static float getAlphaChannelValue(String rgbChannel) {
        float alpha;
        if (rgbChannel.endsWith(CommonCssConstants.PERCENTAGE)) {
            alpha = parsePercentValue(rgbChannel);
        } else {
            alpha = Float.parseFloat(rgbChannel);
        }
        return Math.min(1.0f, Math.max(0.0f, alpha));
    }

    private static float parsePercentValue(String rgbChannel) {
        double parseFloat = (double) Float.parseFloat(rgbChannel.substring(0, rgbChannel.length() - 1));
        Double.isNaN(parseFloat);
        return (float) (parseFloat / 100.0d);
    }
}
