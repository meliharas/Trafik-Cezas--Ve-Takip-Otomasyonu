package com.trafik.ceza.model;

public class Kullanici {
    private int kullaniciId;
    private String kullaniciAdi;
    private String sifre;
    private String ad;
    private String soyad;
    private String rol;

    public Kullanici(int kullaniciId, String kullaniciAdi, String sifre, String ad, String soyad, String rol) {
        this.kullaniciId = kullaniciId;
        this.kullaniciAdi = kullaniciAdi;
        this.sifre = sifre;
        this.ad = ad;
        this.soyad = soyad;
        this.rol = rol;
    }

    public Kullanici(String kullaniciAdi, String sifre, String ad, String soyad, String rol) {
        this.kullaniciAdi = kullaniciAdi;
        this.sifre = sifre;
        this.ad = ad;
        this.soyad = soyad;
        this.rol = rol;
    }

    public Kullanici() {
    }

    public int getKullaniciId() {
        return kullaniciId;
    }

    public void setKullaniciId(int kullaniciId) {
        this.kullaniciId = kullaniciId;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Kullanıcı ID: " + kullaniciId +
               "\nKullanıcı Adı: " + kullaniciAdi +
               "\nAd: " + ad +
               "\nSoyad: " + soyad +
               "\nRol: " + rol;
    }
}