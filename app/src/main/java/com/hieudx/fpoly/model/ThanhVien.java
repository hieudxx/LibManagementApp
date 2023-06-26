package com.hieudx.fpoly.model;

public class ThanhVien {
    private int maTV;
    private String hoTen;
    private String namSinh;
    private int tien;

    public int getTien() {
        return tien;
    }

    public void setTien(int tien) {
        this.tien = tien;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(String namSinh) {
        this.namSinh = namSinh;
    }

    public ThanhVien(int maTV, String hoTen, String namSinh, int tien) {
        this.maTV = maTV;
        this.hoTen = hoTen;
        this.namSinh = namSinh;
        this.tien = tien;
    }

}
