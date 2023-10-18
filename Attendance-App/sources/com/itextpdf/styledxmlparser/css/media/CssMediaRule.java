package com.itextpdf.styledxmlparser.css.media;

import com.itextpdf.styledxmlparser.css.CssNestedAtRule;
import com.itextpdf.styledxmlparser.css.CssRuleName;
import com.itextpdf.styledxmlparser.css.CssRuleSet;
import com.itextpdf.styledxmlparser.css.CssStatement;
import com.itextpdf.styledxmlparser.node.INode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CssMediaRule extends CssNestedAtRule {
    private List<MediaQuery> mediaQueries;

    public CssMediaRule(String ruleParameters) {
        super(CssRuleName.MEDIA, ruleParameters);
        this.mediaQueries = MediaQueryParser.parseMediaQueries(ruleParameters);
    }

    public List<CssRuleSet> getCssRuleSets(INode element, MediaDeviceDescription deviceDescription) {
        List<CssRuleSet> result = new ArrayList<>();
        Iterator<MediaQuery> it = this.mediaQueries.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            } else if (it.next().matches(deviceDescription)) {
                for (CssStatement childStatement : this.body) {
                    result.addAll(childStatement.getCssRuleSets(element, deviceDescription));
                }
            }
        }
        return result;
    }

    public boolean matchMediaDevice(MediaDeviceDescription deviceDescription) {
        for (MediaQuery mediaQuery : this.mediaQueries) {
            if (mediaQuery.matches(deviceDescription)) {
                return true;
            }
        }
        return false;
    }
}
