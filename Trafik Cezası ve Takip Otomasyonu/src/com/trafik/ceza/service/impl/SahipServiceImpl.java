package com.trafik.ceza.service.impl;

import com.trafik.ceza.db.DatabaseHelper;
import com.trafik.ceza.model.Sahip;
import com.trafik.ceza.service.KayitIslemleri;
import com.trafik.ceza.service.AramaIslemleri;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SahipServiceImpl implements KayitIslemleri<Sahip, Integer>, AramaIslemleri<Sahip, String> {

    @Override
    public void ekle(Sahip sahip) throws Exception {
        String sql = "INSERT INTO Sahipler (ad, soyad, tc_kimlik, adres, telefon) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, sahip.getAd());
            pstmt.setString(2, sahip.getSoyad());
            pstmt.setString(3, sahip.getTcKimlik());
            pstmt.setString(4, sahip.getAdres());
            pstmt.setString(5, sahip.getTelefon());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Sahip eklenirken hata: " + e.getMessage());
            throw new Exception("Sahip eklenemedi: " + e.getMessage(), e);
        }
    }

    @Override
    public void guncelle(Sahip sahip) throws Exception {
        String sql = "UPDATE Sahipler SET ad = ?, soyad = ?, tc_kimlik = ?, adres = ?, telefon = ? WHERE sahip_id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, sahip.getAd());
            pstmt.setString(2, sahip.getSoyad());
            pstmt.setString(3, sahip.getTcKimlik());
            pstmt.setString(4, sahip.getAdres());
            pstmt.setString(5, sahip.getTelefon());
            pstmt.setInt(6, sahip.getSahipId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Sahip güncellenirken hata: " + e.getMessage());
            throw new Exception("Sahip güncellenemedi: " + e.getMessage(), e);
        }
    }

    @Override
    public void sil(Integer id) throws Exception {
        String sql = "DELETE FROM Sahipler WHERE sahip_id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Sahip silinirken hata: " + e.getMessage());
            throw new Exception("Sahip silinemedi: " + e.getMessage(), e);
        }
    }

    @Override
    public Sahip idIleBul(Integer id) throws Exception {
        String sql = "SELECT * FROM Sahipler WHERE sahip_id = ?";
        Sahip sahip = null;
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                sahip = new Sahip();
                sahip.setSahipId(rs.getInt("sahip_id"));
                sahip.setAd(rs.getString("ad"));
                sahip.setSoyad(rs.getString("soyad"));
                sahip.setTcKimlik(rs.getString("tc_kimlik"));
                sahip.setAdres(rs.getString("adres"));
                sahip.setTelefon(rs.getString("telefon"));
            }
        } catch (SQLException e) {
            System.err.println("Sahip ID ile bulunurken hata: " + e.getMessage());
            throw new Exception("Sahip bulunamadı: " + e.getMessage(), e);
        }
        return sahip;
    }

    @Override
    public List<Sahip> tumunuListele() throws Exception {
        List<Sahip> sahipler = new ArrayList<>();
        String sql = "SELECT * FROM Sahipler ORDER BY ad, soyad";
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Sahip sahip = new Sahip();
                sahip.setSahipId(rs.getInt("sahip_id"));
                sahip.setAd(rs.getString("ad"));
                sahip.setSoyad(rs.getString("soyad"));
                sahip.setTcKimlik(rs.getString("tc_kimlik"));
                sahip.setAdres(rs.getString("adres"));
                sahip.setTelefon(rs.getString("telefon"));
                sahipler.add(sahip);
            }
        } catch (SQLException e) {
            System.err.println("Tüm sahipler listelenirken hata: " + e.getMessage());
            throw new Exception("Sahipler listelenemedi: " + e.getMessage(), e);
        }
        return sahipler;
    }

    @Override
    public List<Sahip> kritereGoreAra(String kriter) throws Exception {
        List<Sahip> sahipler = new ArrayList<>();
        String sql = "SELECT * FROM Sahipler WHERE ad LIKE ? OR soyad LIKE ? OR tc_kimlik LIKE ? ORDER BY ad, soyad";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String aramaKriteri = "%" + kriter + "%";
            pstmt.setString(1, aramaKriteri);
            pstmt.setString(2, aramaKriteri);
            pstmt.setString(3, aramaKriteri);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Sahip sahip = new Sahip();
                sahip.setSahipId(rs.getInt("sahip_id"));
                sahip.setAd(rs.getString("ad"));
                sahip.setSoyad(rs.getString("soyad"));
                sahip.setTcKimlik(rs.getString("tc_kimlik"));
                sahip.setAdres(rs.getString("adres"));
                sahip.setTelefon(rs.getString("telefon"));
                sahipler.add(sahip);
            }
        } catch (SQLException e) {
            System.err.println("Sahipler aranırken hata: " + e.getMessage());
            throw new Exception("Sahipler aranamadı: " + e.getMessage(), e);
        }
        return sahipler;
    }
}