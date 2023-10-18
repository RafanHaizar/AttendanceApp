package com.inan.cmhs.attendance;

public class StudentItems {
    private String name;
    private String roll;
    long sid;
    private String status = "";

    public long getSid() {
        return this.sid;
    }

    public void setSid(long sid2) {
        this.sid = sid2;
    }

    public StudentItems(long sid2, String roll2, String name2) {
        this.sid = sid2;
        this.roll = roll2;
        this.name = name2;
    }

    public String getRoll() {
        return this.roll;
    }

    public void setRoll(String roll2) {
        this.roll = roll2;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name2) {
        this.name = name2;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status2) {
        this.status = status2;
    }
}
