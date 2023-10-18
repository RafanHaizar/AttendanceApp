package com.itextpdf.svg.processors.impl;

import com.itextpdf.svg.renderers.ISvgNodeRenderer;
import java.util.Stack;

public class ProcessorState {
    private Stack<ISvgNodeRenderer> stack = new Stack<>();

    public int size() {
        return this.stack.size();
    }

    public void push(ISvgNodeRenderer svgNodeRenderer) {
        this.stack.push(svgNodeRenderer);
    }

    public ISvgNodeRenderer pop() {
        return this.stack.pop();
    }

    public ISvgNodeRenderer top() {
        return this.stack.peek();
    }

    public boolean empty() {
        return this.stack.size() == 0;
    }
}
