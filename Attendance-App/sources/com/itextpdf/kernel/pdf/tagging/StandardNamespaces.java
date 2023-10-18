package com.itextpdf.kernel.pdf.tagging;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class StandardNamespaces {
    private static final String MATH_ML = "http://www.w3.org/1998/Math/MathML";
    public static final String PDF_1_7 = "http://iso.org/pdf/ssn";
    public static final String PDF_2_0 = "http://iso.org/pdf2/ssn";
    private static final Set<String> STD_STRUCT_NAMESPACE_1_7_TYPES = new HashSet(Arrays.asList(new String[]{StandardRoles.DOCUMENT, StandardRoles.PART, StandardRoles.DIV, StandardRoles.f1511P, "H", StandardRoles.f1503H1, StandardRoles.f1504H2, StandardRoles.f1505H3, StandardRoles.f1506H4, StandardRoles.f1507H5, StandardRoles.f1508H6, StandardRoles.LBL, StandardRoles.SPAN, StandardRoles.LINK, StandardRoles.ANNOT, StandardRoles.FORM, StandardRoles.RUBY, StandardRoles.f1512RB, StandardRoles.f1514RT, StandardRoles.f1513RP, StandardRoles.WARICHU, StandardRoles.f1519WT, StandardRoles.f1518WP, "L", StandardRoles.f1510LI, StandardRoles.LBODY, StandardRoles.TABLE, StandardRoles.f1517TR, StandardRoles.f1516TH, StandardRoles.f1515TD, StandardRoles.THEAD, StandardRoles.TBODY, StandardRoles.TFOOT, StandardRoles.CAPTION, StandardRoles.FIGURE, StandardRoles.FORMULA, StandardRoles.SECT, StandardRoles.ART, StandardRoles.BLOCKQUOTE, StandardRoles.TOC, StandardRoles.TOCI, StandardRoles.INDEX, StandardRoles.NONSTRUCT, StandardRoles.PRIVATE, StandardRoles.QUOTE, StandardRoles.NOTE, StandardRoles.REFERENCE, StandardRoles.BIBENTRY, StandardRoles.CODE}));
    private static final Set<String> STD_STRUCT_NAMESPACE_2_0_TYPES = new HashSet(Arrays.asList(new String[]{StandardRoles.DOCUMENT, StandardRoles.DOCUMENTFRAGMENT, StandardRoles.PART, StandardRoles.DIV, StandardRoles.ASIDE, StandardRoles.TITLE, StandardRoles.SUB, StandardRoles.f1511P, "H", StandardRoles.LBL, StandardRoles.f1501EM, StandardRoles.STRONG, StandardRoles.SPAN, StandardRoles.LINK, StandardRoles.ANNOT, StandardRoles.FORM, StandardRoles.RUBY, StandardRoles.f1512RB, StandardRoles.f1514RT, StandardRoles.f1513RP, StandardRoles.WARICHU, StandardRoles.f1519WT, StandardRoles.f1518WP, StandardRoles.FENOTE, "L", StandardRoles.f1510LI, StandardRoles.LBODY, StandardRoles.TABLE, StandardRoles.f1517TR, StandardRoles.f1516TH, StandardRoles.f1515TD, StandardRoles.THEAD, StandardRoles.TBODY, StandardRoles.TFOOT, StandardRoles.CAPTION, StandardRoles.FIGURE, StandardRoles.FORMULA, StandardRoles.ARTIFACT}));

    public static String getDefault() {
        return PDF_1_7;
    }

    public static boolean isKnownDomainSpecificNamespace(PdfNamespace namespace) {
        return MATH_ML.equals(namespace.getNamespaceName());
    }

    public static boolean roleBelongsToStandardNamespace(String role, String standardNamespaceName) {
        if (PDF_1_7.equals(standardNamespaceName)) {
            return STD_STRUCT_NAMESPACE_1_7_TYPES.contains(role);
        }
        if (!PDF_2_0.equals(standardNamespaceName)) {
            return false;
        }
        if (STD_STRUCT_NAMESPACE_2_0_TYPES.contains(role) || isHnRole(role)) {
            return true;
        }
        return false;
    }

    public static boolean isHnRole(String role) {
        if (role.startsWith("H") && role.length() > 1 && role.charAt(1) != '0') {
            try {
                if (Integer.parseInt(role.substring(1, role.length())) > 0) {
                    return true;
                }
                return false;
            } catch (Exception e) {
            }
        }
        return false;
    }
}
