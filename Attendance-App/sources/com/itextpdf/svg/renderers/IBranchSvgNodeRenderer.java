package com.itextpdf.svg.renderers;

import java.util.List;

public interface IBranchSvgNodeRenderer extends ISvgNodeRenderer {
    void addChild(ISvgNodeRenderer iSvgNodeRenderer);

    List<ISvgNodeRenderer> getChildren();
}
