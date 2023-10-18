package com.inan.cmhs.attendance;

public class ClassItems {
    int id;

    public ClassItems(int id, String name, String section) {
        this.id = id;
        Name = name;
        Section = section;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    String Name,Section;



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSection() {
        return Section;
    }

    public void setSection(String section) {
        Section = section;
    }
}
