package Pojo;

public class AddrInfo {

    private String addr_full;
    private String subtype;

    public AddrInfo(String addr_full, String subtype) {
        this.addr_full = addr_full;
        this.subtype = subtype;
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
        return "AddrInfo{" +
                "addr_full='" + addr_full + '\'' +
                ", subtype='" + subtype + '\'' +
                '}';
    }
}
