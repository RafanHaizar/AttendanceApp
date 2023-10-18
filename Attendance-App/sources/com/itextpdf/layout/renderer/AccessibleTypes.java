package com.itextpdf.layout.renderer;

import com.itextpdf.kernel.pdf.tagging.StandardNamespaces;
import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import java.util.HashSet;
import java.util.Set;

class AccessibleTypes {
    static int BlockLevel = 2;
    static int Grouping = 1;
    static int Illustration = 4;
    static int InlineLevel = 3;
    static int Unknown = 0;
    static Set<String> blockLevelRoles = new HashSet();
    static Set<String> groupingRoles = new HashSet();
    static Set<String> illustrationRoles = new HashSet();
    static Set<String> inlineLevelRoles = new HashSet();

    AccessibleTypes() {
    }

    static {
        groupingRoles.add(StandardRoles.PART);
        groupingRoles.add(StandardRoles.ART);
        groupingRoles.add(StandardRoles.SECT);
        groupingRoles.add(StandardRoles.DIV);
        groupingRoles.add(StandardRoles.BLOCKQUOTE);
        groupingRoles.add(StandardRoles.CAPTION);
        groupingRoles.add(StandardRoles.TOC);
        groupingRoles.add(StandardRoles.TOCI);
        groupingRoles.add(StandardRoles.INDEX);
        groupingRoles.add(StandardRoles.NONSTRUCT);
        groupingRoles.add(StandardRoles.PRIVATE);
        groupingRoles.add(StandardRoles.ASIDE);
        blockLevelRoles.add(StandardRoles.f1511P);
        blockLevelRoles.add("H");
        blockLevelRoles.add(StandardRoles.f1503H1);
        blockLevelRoles.add(StandardRoles.f1504H2);
        blockLevelRoles.add(StandardRoles.f1505H3);
        blockLevelRoles.add(StandardRoles.f1506H4);
        blockLevelRoles.add(StandardRoles.f1507H5);
        blockLevelRoles.add(StandardRoles.f1508H6);
        blockLevelRoles.add("L");
        blockLevelRoles.add(StandardRoles.LBL);
        blockLevelRoles.add(StandardRoles.f1510LI);
        blockLevelRoles.add(StandardRoles.LBODY);
        blockLevelRoles.add(StandardRoles.TABLE);
        blockLevelRoles.add(StandardRoles.f1517TR);
        blockLevelRoles.add(StandardRoles.f1516TH);
        blockLevelRoles.add(StandardRoles.f1515TD);
        blockLevelRoles.add(StandardRoles.TITLE);
        blockLevelRoles.add(StandardRoles.FENOTE);
        blockLevelRoles.add(StandardRoles.SUB);
        blockLevelRoles.add(StandardRoles.CAPTION);
        inlineLevelRoles.add(StandardRoles.SPAN);
        inlineLevelRoles.add(StandardRoles.QUOTE);
        inlineLevelRoles.add(StandardRoles.NOTE);
        inlineLevelRoles.add(StandardRoles.REFERENCE);
        inlineLevelRoles.add(StandardRoles.BIBENTRY);
        inlineLevelRoles.add(StandardRoles.CODE);
        inlineLevelRoles.add(StandardRoles.LINK);
        inlineLevelRoles.add(StandardRoles.ANNOT);
        inlineLevelRoles.add(StandardRoles.RUBY);
        inlineLevelRoles.add(StandardRoles.WARICHU);
        inlineLevelRoles.add(StandardRoles.f1512RB);
        inlineLevelRoles.add(StandardRoles.f1514RT);
        inlineLevelRoles.add(StandardRoles.f1513RP);
        inlineLevelRoles.add(StandardRoles.f1519WT);
        inlineLevelRoles.add(StandardRoles.f1518WP);
        inlineLevelRoles.add(StandardRoles.f1501EM);
        inlineLevelRoles.add(StandardRoles.STRONG);
        illustrationRoles.add(StandardRoles.FIGURE);
        illustrationRoles.add(StandardRoles.FORMULA);
        illustrationRoles.add(StandardRoles.FORM);
    }

    static int identifyType(String role) {
        if (groupingRoles.contains(role)) {
            return Grouping;
        }
        if (blockLevelRoles.contains(role) || StandardNamespaces.isHnRole(role)) {
            return BlockLevel;
        }
        if (inlineLevelRoles.contains(role)) {
            return InlineLevel;
        }
        if (illustrationRoles.contains(role)) {
            return Illustration;
        }
        return Unknown;
    }
}
