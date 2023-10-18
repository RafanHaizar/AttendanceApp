package com.itextpdf.layout.tagging;

import com.itextpdf.kernel.pdf.tagging.StandardRoles;
import com.itextpdf.layout.IPropertyContainer;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class TableTaggingRule implements ITaggingRule {
    TableTaggingRule() {
    }

    public boolean onTagFinish(LayoutTaggingHelper taggingHelper, TaggingHintKey tableHintKey) {
        LayoutTaggingHelper layoutTaggingHelper = taggingHelper;
        TaggingHintKey taggingHintKey = tableHintKey;
        List<TaggingHintKey> kidKeys = taggingHelper.getAccessibleKidsHint(tableHintKey);
        Map<Integer, TreeMap<Integer, TaggingHintKey>> tableTags = new TreeMap<>();
        List<TaggingHintKey> tableCellTagsUnindexed = new ArrayList<>();
        List<TaggingHintKey> nonCellKids = new ArrayList<>();
        for (TaggingHintKey kidKey : kidKeys) {
            if (!StandardRoles.f1515TD.equals(kidKey.getAccessibleElement().getAccessibilityProperties().getRole()) && !StandardRoles.f1516TH.equals(kidKey.getAccessibleElement().getAccessibilityProperties().getRole())) {
                nonCellKids.add(kidKey);
            } else if (kidKey.getAccessibleElement() instanceof Cell) {
                Cell cell = (Cell) kidKey.getAccessibleElement();
                int rowInd = cell.getRow();
                int colInd = cell.getCol();
                TreeMap<Integer, TaggingHintKey> rowTags = tableTags.get(Integer.valueOf(rowInd));
                if (rowTags == null) {
                    rowTags = new TreeMap<>();
                    tableTags.put(Integer.valueOf(rowInd), rowTags);
                }
                rowTags.put(Integer.valueOf(colInd), kidKey);
            } else {
                tableCellTagsUnindexed.add(kidKey);
            }
        }
        boolean createTBody = true;
        if (tableHintKey.getAccessibleElement() instanceof Table) {
            Table modelElement = (Table) tableHintKey.getAccessibleElement();
            createTBody = (modelElement.getHeader() != null && !modelElement.isSkipFirstHeader()) || (modelElement.getFooter() != null && !modelElement.isSkipLastFooter());
        }
        TaggingDummyElement tbodyTag = new TaggingDummyElement(createTBody ? StandardRoles.TBODY : null);
        for (TaggingHintKey nonCellKid : nonCellKids) {
            String kidRole = nonCellKid.getAccessibleElement().getAccessibilityProperties().getRole();
            if (!StandardRoles.THEAD.equals(kidRole) && !StandardRoles.TFOOT.equals(kidRole)) {
                layoutTaggingHelper.moveKidHint(nonCellKid, taggingHintKey);
            }
        }
        for (TaggingHintKey nonCellKid2 : nonCellKids) {
            if (StandardRoles.THEAD.equals(nonCellKid2.getAccessibleElement().getAccessibilityProperties().getRole())) {
                layoutTaggingHelper.moveKidHint(nonCellKid2, taggingHintKey);
            }
        }
        layoutTaggingHelper.addKidsHint(taggingHintKey, (Collection<TaggingHintKey>) Collections.singletonList(LayoutTaggingHelper.getOrCreateHintKey(tbodyTag)), -1);
        for (TaggingHintKey nonCellKid3 : nonCellKids) {
            if (StandardRoles.TFOOT.equals(nonCellKid3.getAccessibleElement().getAccessibilityProperties().getRole())) {
                layoutTaggingHelper.moveKidHint(nonCellKid3, taggingHintKey);
            }
        }
        for (TreeMap<Integer, TaggingHintKey> rowTags2 : tableTags.values()) {
            TaggingDummyElement row = new TaggingDummyElement(StandardRoles.f1517TR);
            TaggingHintKey rowTagHint = LayoutTaggingHelper.getOrCreateHintKey(row);
            for (TaggingHintKey cellTagHint : rowTags2.values()) {
                layoutTaggingHelper.moveKidHint(cellTagHint, rowTagHint);
            }
            if (tableCellTagsUnindexed != null) {
                for (TaggingHintKey cellTagHint2 : tableCellTagsUnindexed) {
                    layoutTaggingHelper.moveKidHint(cellTagHint2, rowTagHint);
                }
                tableCellTagsUnindexed = null;
            }
            layoutTaggingHelper.addKidsHint((IPropertyContainer) tbodyTag, (Iterable<? extends IPropertyContainer>) Collections.singletonList(row), -1);
        }
        return true;
    }
}
