package com.trafik.ceza.model;

public class Sahip {
    private int sahipId;
    private String ad;
    private String soyad;
    private String tcKimlik;
    private String adres;
    private String telefon;

    public Sahip(int sahipId, String ad, String soyad, String tcKimlik, String adres, String telefon) {
        this.sahipId = sahipId;
        this.ad = ad;
        this.soyad = soyad;
        this.tcKimlik = tcKimlik;
        this.adres = adres;
        this.telefon = telefon;
    }

    public Sahip(String ad, String soyad, String tcKimlik, String adres, String telefon) {
        this.ad = ad;
        this.soyad = soyad;
        this.tcKimlik = tcKimlik;
        this.adres = adres;
        this.telefon = telefon;
    }
    
    public Sahip() {
    }

    public int getSahipId() {
        return sahipId;
    }

    public void setSahipId(int sahipId) {
        this.sahipId = sahipId;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getTcKimlik() {
        return tcKimlik;
    }

    public void setTcKimlik(String tcKimlik) {
        this.tcKimlik = tcKimlik;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    @Override
    public String toString() {
        return "Sahip ID: " + sahipId +
               "\nAd: " + ad +
               "\nSoyad: " + soyad +
               "\nTC Kimlik: " + tcKimlik +
               "\nAdres: " + adres +
               "\nTelefon: " + telefon;
    }
}