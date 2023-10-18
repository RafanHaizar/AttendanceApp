package com.inan.cmhs.attendance;

public class StudentItems {
    private String roll,name,status;
    long sid;

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public StudentItems(long sid, String roll, String name) {
        this.sid=sid;
        this.roll = roll;
        this.name = name;
        status="";
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
