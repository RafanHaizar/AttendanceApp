package com.inan.cmhs.attendance;

public class ClassItems {
    String Name;
    String Section;

    /* renamed from: id */
    int f1135id;

    public ClassItems(int id, String name, String section) {
        this.f1135id = id;
        this.Name = name;
        this.Section = section;
    }

    public int getId() {
        return this.f1135id;
    }

    public void setId(int id) {
        this.f1135id = id;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getSection() {
        return this.Section;
    }

    public void setSection(String section) {
        this.Section = section;
    }
}
