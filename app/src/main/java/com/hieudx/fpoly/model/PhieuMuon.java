package com.hieudx.fpoly.model;

public class PhieuMuon {
    private int maPM;
    private String maTT;
    private int maTV;

    private int maSach;
    private int tienThue;
    private String ngay;
    private int traSach;

    private String tenTT;
    private String tenTV;
    private String tenSach;

    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPM) {
        this.maPM = maPM;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getTienThue() {
        return tienThue;
    }

    public void setTienThue(int tienThue) {
        this.tienThue = tienThue;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public int getTraSach() {
        return traSach;
    }

    public void setTraSach(int traSach) {
        this.traSach = traSach;
    }

    public String getTenTT() {
        return tenTT;
    }

    public void setTenTT(String tenTT) {
        this.tenTT = tenTT;
    }

    public String getTenTV() {
        return tenTV;
    }

    public void setTenTV(String tenTV) {
        this.tenTV = tenTV;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    //    pm.maPM, pm.maTV, tv.hoTen, pm.maTT, tt.hoTen, pm.maSach, s.tenSach, pm.ngay, pm.traSach, pm.tienThue
    public PhieuMuon(int maPM, int maTV, String tenTV, String maTT, String tenTT, int maSach, String tenSach, String ngay, int traSach, int tienThue) {
        this.maPM = maPM;
        this.maTT = maTT;
        this.maTV = maTV;
        this.maSach = maSach;
        this.tienThue = tienThue;
        this.ngay = ngay;
        this.traSach = traSach;
        this.tenTT = tenTT;
        this.tenTV = tenTV;
        this.tenSach = tenSach;
    }

    public PhieuMuon(int maTV, String maTT, int maSach, String ngay, int traSach, int tienThue) {
        this.maTT = maTT;
        this.maTV = maTV;
        this.maSach = maSach;
        this.tienThue = tienThue;
        this.ngay = ngay;
        this.traSach = traSach;
    }


}
