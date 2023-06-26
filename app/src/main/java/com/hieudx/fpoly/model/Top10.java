package com.hieudx.fpoly.model;

public class Top10 {
    private String tenSach;
    private int soLuong;

    public String getTenSach() {
        return tenSach;
    }

    public Top10() {
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public Top10(String tenSach, int soLuong) {
        this.tenSach = tenSach;
        this.soLuong = soLuong;
    }
}
