package com.trafik.ceza.model;

public class Odeme {
    private int odemeId;
    private int cezaId;
    private String odemeTarihi;
    private double odenenTutar;
    private String odemeYontemi;

    private Ceza ceza;

    public Odeme(int odemeId, int cezaId, String odemeTarihi, double odenenTutar, String odemeYontemi) {
        this.odemeId = odemeId;
        this.cezaId = cezaId;
        this.odemeTarihi = odemeTarihi;
        this.odenenTutar = odenenTutar;
        this.odemeYontemi = odemeYontemi;
    }

    public Odeme(int cezaId, String odemeTarihi, double odenenTutar, String odemeYontemi) {
        this.cezaId = cezaId;
        this.odemeTarihi = odemeTarihi;
        this.odenenTutar = odenenTutar;
        this.odemeYontemi = odemeYontemi;
    }

    public Odeme() {
    }

    public int getOdemeId() {
        return odemeId;
    }

    public void setOdemeId(int odemeId) {
        this.odemeId = odemeId;
    }

    public int getCezaId() {
        return cezaId;
    }

    public void setCezaId(int cezaId) {
        this.cezaId = cezaId;
    }

    public String getOdemeTarihi() {
        return odemeTarihi;
    }

    public void setOdemeTarihi(String odemeTarihi) {
        this.odemeTarihi = odemeTarihi;
    }

    public double getOdenenTutar() {
        return odenenTutar;
    }

    public void setOdenenTutar(double odenenTutar) {
        this.odenenTutar = odenenTutar;
    }

    public String getOdemeYontemi() {
        return odemeYontemi;
    }

    public void setOdemeYontemi(String odemeYontemi) {
        this.odemeYontemi = odemeYontemi;
    }

    public Ceza getCeza() {
        return ceza;
    }

    public void setCeza(Ceza ceza) {
        this.ceza = ceza;
    }

    @Override
    public String toString() {
        return "Ödeme ID: " + odemeId +
               "\nCeza ID: " + cezaId +
               "\nÖdeme Tarihi: " + odemeTarihi +
               "\nÖdenen Tutar: " + String.format("%.2f TL", odenenTutar) +
               "\nÖdeme Yöntemi: " + odemeYontemi;
    }
}