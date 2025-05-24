package com.trafik.ceza.service.impl;

import com.trafik.ceza.db.DatabaseHelper;
import com.trafik.ceza.model.Arac;
import com.trafik.ceza.service.AracService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AracServiceImpl implements AracService {

    @Override
    public void ekle(Arac arac) throws Exception {
        String sql = "INSERT INTO Araclar (plaka, marka, model, yil, sahip_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, arac.getPlaka());
            pstmt.setString(2, arac.getMarka());
            pstmt.setString(3, arac.getModel());
            pstmt.setInt(4, arac.getYil());
            pstmt.setInt(5, arac.getSahipId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Araç eklenirken hata: " + e.getMessage());
            throw new Exception("Araç eklenemedi: " + e.getMessage(), e);
        }
    }

    @Override
    public void guncelle(Arac arac) throws Exception {
        String sql = "UPDATE Araclar SET plaka = ?, marka = ?, model = ?, yil = ?, sahip_id = ? WHERE arac_id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, arac.getPlaka());
            pstmt.setString(2, arac.getMarka());
            pstmt.setString(3, arac.getModel());
            pstmt.setInt(4, arac.getYil());
            pstmt.setInt(5, arac.getSahipId());
            pstmt.setInt(6, arac.getAracId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Araç güncellenirken hata: " + e.getMessage());
            throw new Exception("Araç güncellenemedi: " + e.getMessage(), e);
        }
    }

    @Override
    public void sil(Integer id) throws Exception {
        String sql = "DELETE FROM Araclar WHERE arac_id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Araç silinirken hata: " + e.getMessage());
            throw new Exception("Araç silinemedi: " + e.getMessage(), e);
        }
    }

    @Override
    public Arac idIleBul(Integer id) throws Exception {
        String sql = "SELECT * FROM Araclar WHERE arac_id = ?";
        Arac arac = null;
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                arac = new Arac();
                arac.setAracId(rs.getInt("arac_id"));
                arac.setPlaka(rs.getString("plaka"));
                arac.setMarka(rs.getString("marka"));
                arac.setModel(rs.getString("model"));
                arac.setYil(rs.getInt("yil"));
                arac.setSahipId(rs.getInt("sahip_id"));
            }
        } catch (SQLException e) {
            System.err.println("Araç ID ile bulunurken hata: " + e.getMessage());
            throw new Exception("Araç bulunamadı: " + e.getMessage(), e);
        }
        return arac;
    }

    @Override
    public List<Arac> tumunuListele() throws Exception {
        List<Arac> araclar = new ArrayList<>();
        String sql = "SELECT * FROM Araclar ORDER BY plaka";
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Arac arac = new Arac();
                arac.setAracId(rs.getInt("arac_id"));
                arac.setPlaka(rs.getString("plaka"));
                arac.setMarka(rs.getString("marka"));
                arac.setModel(rs.getString("model"));
                arac.setYil(rs.getInt("yil"));
                arac.setSahipId(rs.getInt("sahip_id"));
                araclar.add(arac);
            }
        } catch (SQLException e) {
            System.err.println("Tüm araçlar listelenirken hata: " + e.getMessage());
            throw new Exception("Araçlar listelenemedi: " + e.getMessage(), e);
        }
        return araclar;
    }

    @Override
    public List<Arac> kritereGoreAra(String kriter) throws Exception {
        List<Arac> araclar = new ArrayList<>();
        String sql = "SELECT * FROM Araclar WHERE plaka LIKE ? OR marka LIKE ? OR model LIKE ? ORDER BY plaka";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            String aramaKriteri = "%" + kriter + "%";
            pstmt.setString(1, aramaKriteri);
            pstmt.setString(2, aramaKriteri);
            pstmt.setString(3, aramaKriteri);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Arac arac = new Arac();
                arac.setAracId(rs.getInt("arac_id"));
                arac.setPlaka(rs.getString("plaka"));
                arac.setMarka(rs.getString("marka"));
                arac.setModel(rs.getString("model"));
                arac.setYil(rs.getInt("yil"));
                arac.setSahipId(rs.getInt("sahip_id"));
                araclar.add(arac);
            }
        } catch (SQLException e) {
            System.err.println("Araçlar aranırken hata: " + e.getMessage());
            throw new Exception("Araçlar aranamadı: " + e.getMessage(), e);
        }
        return araclar;
    }

    @Override
    public List<Arac> sahibeGoreAracListele(int sahipId) throws Exception {
        List<Arac> araclar = new ArrayList<>();
        String sql = "SELECT * FROM Araclar WHERE sahip_id = ? ORDER BY plaka";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, sahipId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Arac arac = new Arac();
                arac.setAracId(rs.getInt("arac_id"));
                arac.setPlaka(rs.getString("plaka"));
                arac.setMarka(rs.getString("marka"));
                arac.setModel(rs.getString("model"));
                arac.setYil(rs.getInt("yil"));
                arac.setSahipId(rs.getInt("sahip_id"));
                araclar.add(arac);
            }
        } catch (SQLException e) {
            System.err.println("Sahibe göre araçlar listelenirken hata: " + e.getMessage());
            throw new Exception("Sahibe göre araçlar listelenemedi: " + e.getMessage(), e);
        }
        return araclar;
    }

    @Override
    public List<Arac> yilaGoreAracListele(int yil) throws Exception {
        List<Arac> araclar = new ArrayList<>();
        String sql = "SELECT * FROM Araclar WHERE yil = ? ORDER BY plaka";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, yil);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Arac arac = new Arac();
                arac.setAracId(rs.getInt("arac_id"));
                arac.setPlaka(rs.getString("plaka"));
                arac.setMarka(rs.getString("marka"));
                arac.setModel(rs.getString("model"));
                arac.setYil(rs.getInt("yil"));
                arac.setSahipId(rs.getInt("sahip_id"));
                araclar.add(arac);
            }
        } catch (SQLException e) {
            System.err.println("Yıla göre araçlar listelenirken hata: " + e.getMessage());
            throw new Exception("Yıla göre araçlar listelenemedi: " + e.getMessage(), e);
        }
        return araclar;
    }
}