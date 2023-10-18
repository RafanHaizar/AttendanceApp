package com.itextpdf.layout.renderer;

import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import java.util.ArrayList;
import java.util.List;

final class TableBorderUtil {
    private TableBorderUtil() {
    }

    public static Border getCellSideBorder(Cell cellModel, int borderType) {
        Border cellModelSideBorder = (Border) cellModel.getProperty(borderType);
        if (cellModelSideBorder != null || cellModel.hasProperty(borderType)) {
            return cellModelSideBorder;
        }
        Border cellModelSideBorder2 = (Border) cellModel.getProperty(9);
        if (cellModelSideBorder2 != null || cellModel.hasProperty(9)) {
            return cellModelSideBorder2;
        }
        return (Border) cellModel.getDefaultProperty(9);
    }

    public static Border getWidestBorder(List<Border> borderList) {
        Border theWidestBorder = null;
        if (borderList.size() != 0) {
            for (Border border : borderList) {
                if (border != null && (theWidestBorder == null || border.getWidth() > theWidestBorder.getWidth())) {
                    theWidestBorder = border;
                }
            }
        }
        return theWidestBorder;
    }

    public static Border getWidestBorder(List<Border> borderList, int start, int end) {
        Border theWidestBorder = null;
        if (borderList.size() != 0) {
            for (Border border : borderList.subList(start, end)) {
                if (border != null && (theWidestBorder == null || border.getWidth() > theWidestBorder.getWidth())) {
                    theWidestBorder = border;
                }
            }
        }
        return theWidestBorder;
    }

    public static List<Border> createAndFillBorderList(Border border, int size) {
        List<Border> borderList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            borderList.add(border);
        }
        return borderList;
    }

    public static List<Border> createAndFillBorderList(List<Border> originalList, Border borderToCollapse, int size) {
        List<Border> borderList = new ArrayList<>();
        if (originalList != null) {
            borderList.addAll(originalList);
        }
        while (borderList.size() < size) {
            borderList.add(borderToCollapse);
        }
        int end = originalList == null ? size : Math.min(originalList.size(), size);
        for (int i = 0; i < end; i++) {
            if (borderList.get(i) == null || (borderToCollapse != null && borderList.get(i).getWidth() <= borderToCollapse.getWidth())) {
                borderList.set(i, borderToCollapse);
            }
        }
        return borderList;
    }
}
