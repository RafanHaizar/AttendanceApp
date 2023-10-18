package com.itextpdf.styledxmlparser.css.validate.impl.datatype;

import com.itextpdf.styledxmlparser.css.CommonCssConstants;
import com.itextpdf.styledxmlparser.css.validate.ICssDataTypeValidator;

public class CssTransformValidator implements ICssDataTypeValidator {
    public boolean isValid(String objectString) {
        if ("none".equals(objectString)) {
            return true;
        }
        for (String component : objectString.split("\\)")) {
            if (!isValidComponent(component)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidComponent(String objectString) {
        String str = objectString;
        if ("none".equals(str) || str.indexOf(40) <= 0) {
            return false;
        }
        String function = str.substring(0, str.indexOf(40)).trim();
        String args = str.substring(str.indexOf(40) + 1);
        if (CommonCssConstants.MATRIX.equals(function) || CommonCssConstants.SCALE.equals(function) || CommonCssConstants.SCALE_X.equals(function) || CommonCssConstants.SCALE_Y.equals(function)) {
            String[] arg = args.split(",");
            if ((arg.length == 6 && CommonCssConstants.MATRIX.equals(function)) || (((arg.length == 1 || arg.length == 2) && CommonCssConstants.SCALE.equals(function)) || (arg.length == 1 && (CommonCssConstants.SCALE_X.equals(function) || CommonCssConstants.SCALE_Y.equals(function))))) {
                int i = 0;
                while (i < arg.length) {
                    try {
                        Float.parseFloat(arg[i].trim());
                        i++;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }
                if (i == arg.length) {
                    return true;
                }
            }
            return false;
        } else if (CommonCssConstants.TRANSLATE.equals(function) || CommonCssConstants.TRANSLATE_X.equals(function) || CommonCssConstants.TRANSLATE_Y.equals(function)) {
            String[] arg2 = args.split(",");
            if (arg2.length != 1 && (arg2.length != 2 || !CommonCssConstants.TRANSLATE.equals(function))) {
                return false;
            }
            for (String a : arg2) {
                if (!isValidForTranslate(a)) {
                    return false;
                }
            }
            return true;
        } else if (CommonCssConstants.ROTATE.equals(function)) {
            try {
                if (Float.parseFloat(args) == 0.0f) {
                    return true;
                }
            } catch (NumberFormatException e2) {
            }
            int deg = args.indexOf(100);
            int rad = args.indexOf(114);
            if ((deg <= 0 || !args.substring(deg).equals(CommonCssConstants.DEG)) && (rad <= 0 || !args.substring(rad).equals(CommonCssConstants.RAD))) {
                return false;
            }
            try {
                Double.parseDouble(args.substring(0, deg > 0 ? deg : rad));
                return true;
            } catch (NumberFormatException e3) {
                return false;
            }
        } else {
            if (CommonCssConstants.SKEW.equals(function) || CommonCssConstants.SKEW_X.equals(function) || CommonCssConstants.SKEW_Y.equals(function)) {
                String[] arg3 = args.split(",");
                if (arg3.length == 1 || (arg3.length == 2 && CommonCssConstants.SKEW.equals(function))) {
                    int k = 0;
                    while (k < arg3.length) {
                        try {
                            if (Float.parseFloat(arg3[k]) != 0.0f) {
                                return false;
                            }
                        } catch (NumberFormatException e4) {
                        }
                        int deg2 = arg3[k].indexOf(100);
                        int rad2 = arg3[k].indexOf(114);
                        if (deg2 < 0 && rad2 < 0) {
                            return false;
                        }
                        if ((deg2 > 0 && !arg3[k].substring(deg2).equals(CommonCssConstants.DEG) && rad2 < 0) || (rad2 > 0 && !arg3[k].substring(rad2).equals(CommonCssConstants.RAD))) {
                            return false;
                        }
                        try {
                            Float.parseFloat(arg3[k].trim().substring(0, rad2 > 0 ? rad2 : deg2));
                            k++;
                        } catch (NumberFormatException e5) {
                            return false;
                        }
                    }
                    return true;
                }
            }
            return false;
        }
    }

    private static boolean isValidForTranslate(String string) {
        if (string == null) {
            return false;
        }
        int pos = 0;
        while (pos < string.length() && (string.charAt(pos) == '+' || string.charAt(pos) == '-' || string.charAt(pos) == '.' || (string.charAt(pos) >= '0' && string.charAt(pos) <= '9'))) {
            pos++;
        }
        if (pos <= 0) {
            return false;
        }
        try {
            Float.parseFloat(string.substring(0, pos));
            if (Float.parseFloat(string.substring(0, pos)) == 0.0f || string.substring(pos).equals(CommonCssConstants.f1616PT) || string.substring(pos).equals(CommonCssConstants.f1613IN) || string.substring(pos).equals(CommonCssConstants.f1610CM) || string.substring(pos).equals("q") || string.substring(pos).equals(CommonCssConstants.f1614MM) || string.substring(pos).equals(CommonCssConstants.f1615PC) || string.substring(pos).equals(CommonCssConstants.f1617PX) || string.substring(pos).equals(CommonCssConstants.PERCENTAGE)) {
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
