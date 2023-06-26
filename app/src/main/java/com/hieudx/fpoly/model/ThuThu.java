package com.hieudx.fpoly.model;

public class ThuThu {
    private String maTT;
    private String hoTen;
    private String matKhau;
    private String vaiTro;

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public ThuThu() {
    }

    public ThuThu(String maTT, String hoTen, String matKhau, String vaiTro) {
        this.maTT = maTT;
        this.hoTen = hoTen;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
    }
}
