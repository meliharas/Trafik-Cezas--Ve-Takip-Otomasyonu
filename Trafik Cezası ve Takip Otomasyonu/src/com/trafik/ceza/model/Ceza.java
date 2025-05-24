package com.trafik.ceza.model;

public class Ceza {
    private int cezaId;
    private int aracId;
    private int cezaTuruId;
    private String cezaTarihi;
    private String cezaSaati;
    private String cezaYeri;
    private double kesilenTutar;
    private int odemeDurumu;
    private String aciklamaDetay;

    private Arac arac;
    private CezaTuru cezaTuru;

    public Ceza(int cezaId, int aracId, int cezaTuruId, String cezaTarihi, String cezaSaati,
                String cezaYeri, double kesilenTutar, int odemeDurumu, String aciklamaDetay) {
        this.cezaId = cezaId;
        this.aracId = aracId;
        this.cezaTuruId = cezaTuruId;
        this.cezaTarihi = cezaTarihi;
        this.cezaSaati = cezaSaati;
        this.cezaYeri = cezaYeri;
        this.kesilenTutar = kesilenTutar;
        this.odemeDurumu = odemeDurumu;
        this.aciklamaDetay = aciklamaDetay;
    }

    public Ceza(int aracId, int cezaTuruId, String cezaTarihi, String cezaSaati,
                String cezaYeri, double kesilenTutar, String aciklamaDetay) {
        this.aracId = aracId;
        this.cezaTuruId = cezaTuruId;
        this.cezaTarihi = cezaTarihi;
        this.cezaSaati = cezaSaati;
        this.cezaYeri = cezaYeri;
        this.kesilenTutar = kesilenTutar;
        this.odemeDurumu = 0;
        this.aciklamaDetay = aciklamaDetay;
    }
    
    public Ceza() {
    }

    public int getCezaId() {
        return cezaId;
    }

    public void setCezaId(int cezaId) {
        this.cezaId = cezaId;
    }

    public int getAracId() {
        return aracId;
    }

    public void setAracId(int aracId) {
        this.aracId = aracId;
    }

    public int getCezaTuruId() {
        return cezaTuruId;
    }

    public void setCezaTuruId(int cezaTuruId) {
        this.cezaTuruId = cezaTuruId;
    }

    public String getCezaTarihi() {
        return cezaTarihi;
    }

    public void setCezaTarihi(String cezaTarihi) {
        this.cezaTarihi = cezaTarihi;
    }

    public String getCezaSaati() {
        return cezaSaati;
    }

    public void setCezaSaati(String cezaSaati) {
        this.cezaSaati = cezaSaati;
    }

    public String getCezaYeri() {
        return cezaYeri;
    }

    public void setCezaYeri(String cezaYeri) {
        this.cezaYeri = cezaYeri;
    }

    public double getKesilenTutar() {
        return kesilenTutar;
    }

    public void setKesilenTutar(double kesilenTutar) {
        this.kesilenTutar = kesilenTutar;
    }

    public int getOdemeDurumu() {
        return odemeDurumu;
    }

    public void setOdemeDurumu(int odemeDurumu) {
        this.odemeDurumu = odemeDurumu;
    }

    public String getAciklamaDetay() {
        return aciklamaDetay;
    }

    public void setAciklamaDetay(String aciklamaDetay) {
        this.aciklamaDetay = aciklamaDetay;
    }

    public Arac getArac() {
        return arac;
    }

    public void setArac(Arac arac) {
        this.arac = arac;
    }

    public CezaTuru getCezaTuru() {
        return cezaTuru;
    }

    public void setCezaTuru(CezaTuru cezaTuru) {
        this.cezaTuru = cezaTuru;
    }
    
    public String getOdemeDurumuStr() {
        return odemeDurumu == 1 ? "Ödendi" : "Ödenmedi";
    }

    @Override
    public String toString() {
        return "Ceza ID: " + cezaId +
               "\nAraç ID: " + aracId + (arac != null ? " (" + arac.getPlaka() + ")" : "") +
               "\nCeza Türü ID: " + cezaTuruId + (cezaTuru != null ? " (" + cezaTuru.getAciklama() + ")" : "") +
               "\nCeza Tarihi: " + cezaTarihi + " " + cezaSaati +
               "\nCeza Yeri: " + cezaYeri +
               "\nKesilen Tutar: " + String.format("%.2f TL", kesilenTutar) +
               "\nÖdeme Durumu: " + getOdemeDurumuStr() +
               "\nAçıklama: " + aciklamaDetay;
    }
}