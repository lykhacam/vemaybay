package com.example.kobietten.model;

public class ChuyenBay {
    private String batdau;
    private String diemden;
    private String diemdi;
    private String ketthuc;
    private String tenhang;
    private String thoigian;
    private int tienve;

    public ChuyenBay() {
    }

    public ChuyenBay(String batdau, String diemden, String diemdi, String ketthuc, String tenhang, String thoigian, int tienve) {
        this.batdau = batdau;
        this.diemden = diemden;
        this.diemdi = diemdi;
        this.ketthuc = ketthuc;
        this.tenhang = tenhang;
        this.thoigian = thoigian;
        this.tienve = tienve;
    }

    public String getBatdau() {
        return batdau;
    }

    public void setBatdau(String batdau) {
        this.batdau = batdau;
    }

    public String getDiemden() {
        return diemden;
    }

    public void setDiemden(String diemden) {
        this.diemden = diemden;
    }

    public String getDiemdi() {
        return diemdi;
    }

    public void setDiemdi(String diemdi) {
        this.diemdi = diemdi;
    }

    public String getKetthuc() {
        return ketthuc;
    }

    public void setKetthuc(String ketthuc) {
        this.ketthuc = ketthuc;
    }

    public String getTenhang() {
        return tenhang;
    }

    public void setTenhang(String tenhang) {
        this.tenhang = tenhang;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public int getTienve() {
        return tienve;
    }

    public void setTienve(int tienve) {
        this.tienve = tienve;
    }

}
