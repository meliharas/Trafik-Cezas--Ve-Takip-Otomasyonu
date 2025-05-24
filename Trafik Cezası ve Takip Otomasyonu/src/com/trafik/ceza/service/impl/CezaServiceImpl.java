package com.trafik.ceza.service.impl;

import com.trafik.ceza.db.DatabaseHelper;
import com.trafik.ceza.model.Arac;
import com.trafik.ceza.model.Ceza;
import com.trafik.ceza.model.CezaTuru;
import com.trafik.ceza.service.KayitIslemleri;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CezaServiceImpl implements KayitIslemleri<Ceza, Integer> {


    @Override
    public void ekle(Ceza ceza) throws Exception {
        String sql = "INSERT INTO Cezalar (arac_id, ceza_turu_id, ceza_tarihi, ceza_saati, ceza_yeri, kesilen_tutar, odeme_durumu, aciklama_detay) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ceza.getAracId());
            pstmt.setInt(2, ceza.getCezaTuruId());
            pstmt.setString(3, ceza.getCezaTarihi());
            pstmt.setString(4, ceza.getCezaSaati());
            pstmt.setString(5, ceza.getCezaYeri());
            pstmt.setDouble(6, ceza.getKesilenTutar());
            pstmt.setInt(7, ceza.getOdemeDurumu());
            pstmt.setString(8, ceza.getAciklamaDetay());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ceza eklenirken hata: " + e.getMessage());
            throw new Exception("Ceza eklenemedi: " + e.getMessage(), e);
        }
    }

    @Override
    public void guncelle(Ceza ceza) throws Exception {
        String sql = "UPDATE Cezalar SET arac_id = ?, ceza_turu_id = ?, ceza_tarihi = ?, ceza_saati = ?, " +
                     "ceza_yeri = ?, kesilen_tutar = ?, odeme_durumu = ?, aciklama_detay = ? WHERE ceza_id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, ceza.getAracId());
            pstmt.setInt(2, ceza.getCezaTuruId());
            pstmt.setString(3, ceza.getCezaTarihi());
            pstmt.setString(4, ceza.getCezaSaati());
            pstmt.setString(5, ceza.getCezaYeri());
            pstmt.setDouble(6, ceza.getKesilenTutar());
            pstmt.setInt(7, ceza.getOdemeDurumu());
            pstmt.setString(8, ceza.getAciklamaDetay());
            pstmt.setInt(9, ceza.getCezaId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ceza güncellenirken hata: " + e.getMessage());
            throw new Exception("Ceza güncellenemedi: " + e.getMessage(), e);
        }
    }

    @Override
    public void sil(Integer id) throws Exception {
        String sql = "DELETE FROM Cezalar WHERE ceza_id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ceza silinirken hata: " + e.getMessage());
            throw new Exception("Ceza silinemedi: " + e.getMessage(), e);
        }
    }

    private Ceza mapResultSetToCeza(ResultSet rs) throws SQLException {
        Ceza ceza = new Ceza();
        ceza.setCezaId(rs.getInt("c_ceza_id"));
        ceza.setAracId(rs.getInt("c_arac_id"));
        ceza.setCezaTuruId(rs.getInt("c_ceza_turu_id"));
        ceza.setCezaTarihi(rs.getString("c_ceza_tarihi"));
        ceza.setCezaSaati(rs.getString("c_ceza_saati"));
        ceza.setCezaYeri(rs.getString("c_ceza_yeri"));
        ceza.setKesilenTutar(rs.getDouble("c_kesilen_tutar"));
        ceza.setOdemeDurumu(rs.getInt("c_odeme_durumu"));
        ceza.setAciklamaDetay(rs.getString("c_aciklama_detay"));

        Arac arac = new Arac();
        arac.setAracId(rs.getInt("c_arac_id"));
        arac.setPlaka(rs.getString("a_plaka"));
        ceza.setArac(arac);

        CezaTuru cezaTuru = new CezaTuru();
        cezaTuru.setCezaTuruId(rs.getInt("c_ceza_turu_id"));
        cezaTuru.setAciklama(rs.getString("ct_aciklama"));
        ceza.setCezaTuru(cezaTuru);

        return ceza;
    }
    
    private final String SELECT_CEZA_WITH_DETAILS_SQL = 
        "SELECT c.ceza_id as c_ceza_id, c.arac_id as c_arac_id, c.ceza_turu_id as c_ceza_turu_id, " +
        "c.ceza_tarihi as c_ceza_tarihi, c.ceza_saati as c_ceza_saati, c.ceza_yeri as c_ceza_yeri, " +
        "c.kesilen_tutar as c_kesilen_tutar, c.odeme_durumu as c_odeme_durumu, c.aciklama_detay as c_aciklama_detay, " +
        "a.plaka as a_plaka, ct.aciklama as ct_aciklama " +
        "FROM Cezalar c " +
        "JOIN Araclar a ON c.arac_id = a.arac_id " +
        "JOIN CezaTurleri ct ON c.ceza_turu_id = ct.ceza_turu_id ";

    @Override
    public Ceza idIleBul(Integer id) throws Exception {
        String sql = SELECT_CEZA_WITH_DETAILS_SQL + "WHERE c.ceza_id = ?";
        Ceza ceza = null;
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ceza = mapResultSetToCeza(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Ceza ID ile bulunurken hata: " + e.getMessage());
            throw new Exception("Ceza bulunamadı: " + e.getMessage(), e);
        }
        return ceza;
    }

    @Override
    public List<Ceza> tumunuListele() throws Exception {
        List<Ceza> cezalar = new ArrayList<>();
        String sql = SELECT_CEZA_WITH_DETAILS_SQL + "ORDER BY c.ceza_tarihi DESC, c.ceza_saati DESC";
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                cezalar.add(mapResultSetToCeza(rs));
            }
        } catch (SQLException e) {
            System.err.println("Tüm cezalar listelenirken hata: " + e.getMessage());
            throw new Exception("Cezalar listelenemedi: " + e.getMessage(), e);
        }
        return cezalar;
    }

    public List<Ceza> aracIdIleListele(int aracId) throws Exception {
        List<Ceza> cezalar = new ArrayList<>();
        String sql = SELECT_CEZA_WITH_DETAILS_SQL + "WHERE c.arac_id = ? ORDER BY c.ceza_tarihi DESC, c.ceza_saati DESC";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, aracId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    cezalar.add(mapResultSetToCeza(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Araca ait cezalar listelenirken hata: " + e.getMessage());
            throw new Exception("Araca ait cezalar listelenemedi: " + e.getMessage(), e);
        }
        return cezalar;
    }
    
    public List<Ceza> plakayaGoreAra(String plaka) throws Exception {
        List<Ceza> cezalar = new ArrayList<>();
        String sql = SELECT_CEZA_WITH_DETAILS_SQL + "WHERE a.plaka LIKE ? ORDER BY c.ceza_tarihi DESC, c.ceza_saati DESC";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + plaka + "%");
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    cezalar.add(mapResultSetToCeza(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Plakaya göre ceza arama sırasında hata: " + e.getMessage());
            throw new Exception("Plakaya göre ceza arama hatası: " + e.getMessage(), e);
        }
        return cezalar;
    }
public List<Ceza> sahibinCezalariniListele(int sahipId) throws Exception {
    List<Ceza> cezalar = new ArrayList<>();
    String sql = SELECT_CEZA_WITH_DETAILS_SQL + "WHERE a.sahip_id = ? ORDER BY c.ceza_tarihi DESC, c.ceza_saati DESC";
    try (Connection conn = DatabaseHelper.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, sahipId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    cezalar.add(mapResultSetToCeza(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Sahibe ait cezalar listelenirken hata: " + e.getMessage());
            throw new Exception("Sahibe ait cezalar listelenemedi: " + e.getMessage(), e);
        }
        return cezalar;
    }

    public void odemeDurumuGuncelle(int cezaId, int yeniDurum) throws Exception {
        String sql = "UPDATE Cezalar SET odeme_durumu = ? WHERE ceza_id = ?";
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, yeniDurum);
            pstmt.setInt(2, cezaId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new Exception("Ceza bulunamadı veya ödeme durumu güncellenemedi.");
            }
        } catch (SQLException e) {
            System.err.println("Ceza ödeme durumu güncellenirken hata: " + e.getMessage());
            throw new Exception("Ceza ödeme durumu güncellenemedi: " + e.getMessage(), e);
        }
    }
}