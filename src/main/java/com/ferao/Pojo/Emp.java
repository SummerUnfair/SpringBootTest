package com.ferao.Pojo;

public class Emp {

    private String id ;
    private String addr_full;
    private String subtype;

    public Emp(String id, String addr_full, String subtype) {
        this.id = id;
        this.addr_full = addr_full;
        this.subtype = subtype;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddr_full() {
        return addr_full;
    }

    public void setAddr_full(String addr_full) {
        this.addr_full = addr_full;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "id='" + id + '\'' +
                ", addr_full='" + addr_full + '\'' +
                ", subtype='" + subtype + '\'' +
                '}';
    }
}
