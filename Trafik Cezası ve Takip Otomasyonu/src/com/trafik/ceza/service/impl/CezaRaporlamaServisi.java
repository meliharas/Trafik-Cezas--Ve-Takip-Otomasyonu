package com.trafik.ceza.service.impl;

import com.trafik.ceza.model.Ceza;
import com.trafik.ceza.service.AramaIslemleri;
import com.trafik.ceza.service.RaporlamaIslemleri;
import com.trafik.ceza.db.DatabaseHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CezaRaporlamaServisi implements AramaIslemleri<Ceza, String>, RaporlamaIslemleri<List<String>, String> {

    @Override
    public List<Ceza> kritereGoreAra(String plakaKriteri) throws Exception {
        List<Ceza> bulunanCezalar = new ArrayList<>();
        String sql = "SELECT c.*, a.plaka, ct.aciklama as ceza_turu_aciklama FROM Cezalar c " +
                     "JOIN Araclar a ON c.arac_id = a.arac_id " +
                     "JOIN CezaTurleri ct ON c.ceza_turu_id = ct.ceza_turu_id " +
                     "WHERE a.plaka LIKE ?";

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + plakaKriteri + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Ceza ceza = new Ceza();
                ceza.setCezaId(rs.getInt("ceza_id"));
                ceza.setAracId(rs.getInt("arac_id"));
                ceza.setCezaTuruId(rs.getInt("ceza_turu_id"));
                ceza.setCezaTarihi(rs.getString("ceza_tarihi"));
                ceza.setCezaSaati(rs.getString("ceza_saati"));
                ceza.setCezaYeri(rs.getString("ceza_yeri"));
                ceza.setKesilenTutar(rs.getDouble("kesilen_tutar"));
                ceza.setOdemeDurumu(rs.getInt("odeme_durumu"));
                ceza.setAciklamaDetay(rs.getString("aciklama_detay"));
                bulunanCezalar.add(ceza);
            }
        } catch (SQLException e) {
            System.err.println("Plakaya göre ceza arama sırasında hata: " + e.getMessage());
            throw new Exception("Ceza arama hatası: " + e.getMessage(), e);
        }
        return bulunanCezalar;
    }

    public List<Ceza> kritereGoreAra(String plakaKriteri, String tarihBaslangic, String tarihBitis) throws Exception {
        List<Ceza> bulunanCezalar = new ArrayList<>();
        String sql = "SELECT c.* FROM Cezalar c " +
                     "JOIN Araclar a ON c.arac_id = a.arac_id " +
                     "WHERE a.plaka LIKE ? AND c.ceza_tarihi BETWEEN ? AND ?";
        
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + plakaKriteri + "%");
            pstmt.setString(2, tarihBaslangic);
            pstmt.setString(3, tarihBitis);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                 Ceza ceza = new Ceza();
                ceza.setCezaId(rs.getInt("ceza_id"));
                ceza.setAracId(rs.getInt("arac_id"));
                ceza.setCezaTuruId(rs.getInt("ceza_turu_id"));
                ceza.setCezaTarihi(rs.getString("ceza_tarihi"));
                ceza.setCezaSaati(rs.getString("ceza_saati"));
                ceza.setCezaYeri(rs.getString("ceza_yeri"));
                ceza.setKesilenTutar(rs.getDouble("kesilen_tutar"));
                ceza.setOdemeDurumu(rs.getInt("odeme_durumu"));
                ceza.setAciklamaDetay(rs.getString("aciklama_detay"));
                bulunanCezalar.add(ceza);
            }
        } catch (SQLException e) {
            System.err.println("Plaka ve tarihe göre ceza arama sırasında hata: " + e.getMessage());
            throw new Exception("Ceza arama hatası (detaylı): " + e.getMessage(), e);
        }
        return bulunanCezalar;
    }


    @Override
    public List<String> raporOlustur(String raporParametresi) throws Exception {
        List<String> raporSatirlari = new ArrayList<>();
        raporSatirlari.add("--- RAPOR BAŞLANGICI ---");
        raporSatirlari.add("Rapor Parametresi: " + raporParametresi);

        if ("odenmemis_cezalar".equalsIgnoreCase(raporParametresi)) {
            String sql = "SELECT a.plaka, ct.aciklama, c.ceza_tarihi, c.kesilen_tutar FROM Cezalar c " +
                         "JOIN Araclar a ON c.arac_id = a.arac_id " +
                         "JOIN CezaTurleri ct ON c.ceza_turu_id = ct.ceza_turu_id " +
                         "WHERE c.odeme_durumu = 0 ORDER BY c.ceza_tarihi DESC";
            try (Connection conn = DatabaseHelper.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                
                raporSatirlari.add("Ödenmemiş Cezalar Listesi:");
                while (rs.next()) {
                    raporSatirlari.add(String.format("Plaka: %s, Ceza: %s, Tarih: %s, Tutar: %.2f TL",
                                       rs.getString("plaka"),
                                       rs.getString("aciklama"),
                                       rs.getString("ceza_tarihi"),
                                       rs.getDouble("kesilen_tutar")));
                }
                if (raporSatirlari.size() == 3) {
                    raporSatirlari.add("Ödenmemiş ceza bulunmamaktadır.");
                }

            } catch (SQLException e) {
                System.err.println("Ödenmemiş cezalar raporu oluşturulurken hata: " + e.getMessage());
                raporSatirlari.add("Rapor oluşturulurken bir hata meydana geldi.");
            }
        } else {
            raporSatirlari.add("Belirtilen parametre için rapor tanımı bulunamadı.");
        }

        raporSatirlari.add("--- RAPOR SONU ---");
        return raporSatirlari;
    }

}