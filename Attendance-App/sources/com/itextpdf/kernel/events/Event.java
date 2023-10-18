package com.itextpdf.kernel.events;

public class Event {
    protected String type;

    public Event(String type2) {
        this.type = type2;
    }

    public String getType() {
        return this.type;
    }
}
