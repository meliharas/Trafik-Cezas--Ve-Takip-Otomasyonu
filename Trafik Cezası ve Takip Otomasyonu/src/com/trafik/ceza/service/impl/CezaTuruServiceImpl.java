package com.trafik.ceza.service.impl;

import com.trafik.ceza.db.DatabaseHelper;
import com.trafik.ceza.model.CezaTuru;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CezaTuruServiceImpl {

    public CezaTuru idIleBul(Integer id) throws Exception {
        String sql = "SELECT * FROM CezaTurleri WHERE ceza_turu_id = ?";
        CezaTuru cezaTuru = null;
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                cezaTuru = new CezaTuru();
                cezaTuru.setCezaTuruId(rs.getInt("ceza_turu_id"));
                cezaTuru.setAciklama(rs.getString("aciklama"));
                cezaTuru.setVarsayilanTutar(rs.getDouble("varsayilan_tutar"));
            }
        } catch (SQLException e) {
            System.err.println("Ceza Türü ID ile bulunurken hata: " + e.getMessage());
            throw new Exception("Ceza Türü bulunamadı: " + e.getMessage(), e);
        }
        return cezaTuru;
    }

    public List<CezaTuru> tumunuListele() throws Exception {
        List<CezaTuru> cezaTurleri = new ArrayList<>();
        String sql = "SELECT * FROM CezaTurleri ORDER BY aciklama";
        try (Connection conn = DatabaseHelper.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                CezaTuru cezaTuru = new CezaTuru();
                cezaTuru.setCezaTuruId(rs.getInt("ceza_turu_id"));
                cezaTuru.setAciklama(rs.getString("aciklama"));
                cezaTuru.setVarsayilanTutar(rs.getDouble("varsayilan_tutar"));
                cezaTurleri.add(cezaTuru);
            }
        } catch (SQLException e) {
            System.err.println("Tüm ceza türleri listelenirken hata: " + e.getMessage());
            throw new Exception("Ceza türleri listelenemedi: " + e.getMessage(), e);
        }
        return cezaTurleri;
    }

}