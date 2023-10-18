package com.itextpdf.styledxmlparser.css.media;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class MediaQuery {
    private List<MediaExpression> expressions;
    private boolean not;
    private boolean only;
    private String type;

    MediaQuery(String type2, List<MediaExpression> expressions2, boolean only2, boolean not2) {
        this.type = type2;
        this.expressions = new ArrayList(expressions2);
        this.only = only2;
        this.not = not2;
    }

    public boolean matches(MediaDeviceDescription deviceDescription) {
        boolean expressionResult = false;
        boolean typeMatches = this.type == null || MediaType.ALL.equals(this.type) || Objects.equals(this.type, deviceDescription.getType());
        boolean matchesExpressions = true;
        Iterator<MediaExpression> it = this.expressions.iterator();
        while (true) {
            if (it.hasNext()) {
                if (!it.next().matches(deviceDescription)) {
                    matchesExpressions = false;
                    break;
                }
            } else {
                break;
            }
        }
        boolean expressionResult2 = typeMatches && matchesExpressions;
        if (!this.not) {
            return expressionResult2;
        }
        if (!expressionResult2) {
            expressionResult = true;
        }
        return expressionResult;
    }
}
