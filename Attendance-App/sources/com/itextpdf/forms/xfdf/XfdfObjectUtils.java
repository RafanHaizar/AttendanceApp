package com.itextpdf.forms.xfdf;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfArray;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.colorspace.PdfDeviceCs;
import com.itextpdf.p026io.source.ByteUtils;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

final class XfdfObjectUtils {
    private XfdfObjectUtils() {
    }

    static Rectangle convertRectFromString(String rectString) {
        StringTokenizer st = new StringTokenizer(rectString, ",");
        List<String> coordsList = new ArrayList<>();
        while (st.hasMoreTokens()) {
            coordsList.add(st.nextToken());
        }
        if (coordsList.size() == 2) {
            return new Rectangle(Float.parseFloat(coordsList.get(0)), Float.parseFloat(coordsList.get(1)));
        }
        if (coordsList.size() == 4) {
            return new Rectangle(Float.parseFloat(coordsList.get(0)), Float.parseFloat(coordsList.get(1)), Float.parseFloat(coordsList.get(2)), Float.parseFloat(coordsList.get(3)));
        }
        return null;
    }

    static PdfArray convertFringeFromString(String fringeString) {
        StringTokenizer st = new StringTokenizer(fringeString, ",");
        List<String> fringeList = new ArrayList<>();
        while (st.hasMoreTokens()) {
            fringeList.add(st.nextToken());
        }
        float[] fringe = new float[4];
        if (fringeList.size() == 4) {
            for (int i = 0; i < 4; i++) {
                fringe[i] = Float.parseFloat(fringeList.get(i));
            }
        }
        return new PdfArray(fringe);
    }

    static String convertRectToString(Rectangle rect) {
        return convertFloatToString(rect.getX()) + ", " + convertFloatToString(rect.getY()) + ", " + convertFloatToString(rect.getX() + rect.getWidth()) + ", " + convertFloatToString(rect.getY() + rect.getHeight());
    }

    static String convertFloatToString(float coord) {
        return new String(ByteUtils.getIsoBytes((double) coord), StandardCharsets.UTF_8);
    }

    static float[] convertQuadPointsFromCoordsString(String coordsString) {
        StringTokenizer st = new StringTokenizer(coordsString, ",");
        List<String> quadPointsList = new ArrayList<>();
        while (st.hasMoreTokens()) {
            quadPointsList.add(st.nextToken());
        }
        if (quadPointsList.size() != 8) {
            return new float[0];
        }
        float[] quadPoints = new float[8];
        for (int i = 0; i < 8; i++) {
            quadPoints[i] = Float.parseFloat(quadPointsList.get(i));
        }
        return quadPoints;
    }

    static String convertQuadPointsToCoordsString(float[] quadPoints) {
        StringBuilder stb = new StringBuilder(floatToPaddedString(quadPoints[0]));
        for (int i = 1; i < 8; i++) {
            stb.append(", ").append(floatToPaddedString(quadPoints[i]));
        }
        return stb.toString();
    }

    private static String floatToPaddedString(float number) {
        return new String(ByteUtils.getIsoBytes((double) number), StandardCharsets.UTF_8);
    }

    static int convertFlagsFromString(String flagsString) {
        int result = 0;
        StringTokenizer st = new StringTokenizer(flagsString, ",");
        List<String> flagsList = new ArrayList<>();
        while (st.hasMoreTokens()) {
            flagsList.add(st.nextToken().toLowerCase());
        }
        Map<String, Integer> flagMap = new HashMap<>();
        flagMap.put(XfdfConstants.INVISIBLE, 1);
        flagMap.put("hidden", 2);
        flagMap.put(XfdfConstants.PRINT, 4);
        flagMap.put(XfdfConstants.NO_ZOOM, 8);
        flagMap.put(XfdfConstants.NO_ROTATE, 16);
        flagMap.put(XfdfConstants.NO_VIEW, 32);
        flagMap.put(XfdfConstants.READ_ONLY, 64);
        flagMap.put(XfdfConstants.LOCKED, 128);
        flagMap.put(XfdfConstants.TOGGLE_NO_VIEW, 256);
        for (String flag : flagsList) {
            if (flagMap.containsKey(flag)) {
                result += flagMap.get(flag).intValue();
            }
        }
        return result;
    }

    static String convertFlagsToString(PdfAnnotation pdfAnnotation) {
        List<String> flagsList = new ArrayList<>();
        StringBuilder stb = new StringBuilder();
        if (pdfAnnotation.hasFlag(1)) {
            flagsList.add(XfdfConstants.INVISIBLE);
        }
        if (pdfAnnotation.hasFlag(2)) {
            flagsList.add("hidden");
        }
        if (pdfAnnotation.hasFlag(4)) {
            flagsList.add(XfdfConstants.PRINT);
        }
        if (pdfAnnotation.hasFlag(8)) {
            flagsList.add(XfdfConstants.NO_ZOOM);
        }
        if (pdfAnnotation.hasFlag(16)) {
            flagsList.add(XfdfConstants.NO_ROTATE);
        }
        if (pdfAnnotation.hasFlag(32)) {
            flagsList.add(XfdfConstants.NO_VIEW);
        }
        if (pdfAnnotation.hasFlag(64)) {
            flagsList.add(XfdfConstants.READ_ONLY);
        }
        if (pdfAnnotation.hasFlag(128)) {
            flagsList.add(XfdfConstants.LOCKED);
        }
        if (pdfAnnotation.hasFlag(256)) {
            flagsList.add(XfdfConstants.TOGGLE_NO_VIEW);
        }
        for (String flag : flagsList) {
            stb.append(flag).append(",");
        }
        String result = stb.toString();
        if (result.length() > 0) {
            return result.substring(0, result.length() - 1);
        }
        return null;
    }

    static String convertColorToString(float[] colors) {
        if (colors.length == 3) {
            return "#" + convertColorFloatToHex(colors[0]) + convertColorFloatToHex(colors[1]) + convertColorFloatToHex(colors[2]);
        }
        return null;
    }

    static String convertColorToString(Color color) {
        float[] colors = color.getColorValue();
        if (colors == null || colors.length != 3) {
            return null;
        }
        return "#" + convertColorFloatToHex(colors[0]) + convertColorFloatToHex(colors[1]) + convertColorFloatToHex(colors[2]);
    }

    private static String convertColorFloatToHex(float colorFloat) {
        StringBuilder append = new StringBuilder().append("0");
        double d = (double) (255.0f * colorFloat);
        Double.isNaN(d);
        String result = append.append(Integer.toHexString((int) (d + 0.5d)).toUpperCase()).toString();
        return result.substring(result.length() - 2);
    }

    static String convertIdToHexString(String idString) {
        StringBuilder stb = new StringBuilder();
        for (char ch : idString.toCharArray()) {
            stb.append(Integer.toHexString(ch).toUpperCase());
        }
        return stb.toString();
    }

    static Color convertColorFromString(String hexColor) {
        return Color.makeColor(new PdfDeviceCs.Rgb(), convertColorFloatsFromString(hexColor));
    }

    static float[] convertColorFloatsFromString(String colorHexString) {
        float[] result = new float[3];
        String colorString = colorHexString.substring(colorHexString.indexOf(35) + 1);
        if (colorString.length() == 6) {
            for (int i = 0; i < 3; i++) {
                result[i] = (float) Integer.parseInt(colorString.substring(i * 2, (i * 2) + 2), 16);
            }
        }
        return result;
    }

    static String convertVerticesToString(float[] vertices) {
        if (vertices.length <= 0) {
            return null;
        }
        StringBuilder stb = new StringBuilder();
        stb.append(vertices[0]);
        for (int i = 1; i < vertices.length; i++) {
            stb.append(", ").append(vertices[i]);
        }
        return stb.toString();
    }

    static String convertFringeToString(float[] fringeArray) {
        if (fringeArray.length != 4) {
            return null;
        }
        StringBuilder stb = new StringBuilder();
        stb.append(fringeArray[0]);
        for (int i = 1; i < 4; i++) {
            stb.append(", ").append(fringeArray[i]);
        }
        return stb.toString();
    }

    static float[] convertVerticesFromString(String verticesString) {
        StringTokenizer st = new StringTokenizer(verticesString, ",;");
        List<String> verticesList = new ArrayList<>();
        while (st.hasMoreTokens()) {
            verticesList.add(st.nextToken());
        }
        float[] vertices = new float[verticesList.size()];
        for (int i = 0; i < verticesList.size(); i++) {
            vertices[i] = Float.parseFloat(verticesList.get(i));
        }
        return vertices;
    }

    static String convertLineStartToString(float[] line) {
        if (line.length == 4) {
            return line[0] + "," + line[1];
        }
        return null;
    }

    static String convertLineEndToString(float[] line) {
        if (line.length == 4) {
            return line[2] + "," + line[3];
        }
        return null;
    }
}
