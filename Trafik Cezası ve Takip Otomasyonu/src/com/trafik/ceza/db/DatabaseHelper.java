package com.trafik.ceza.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DatabaseHelper {
    private static final String DATABASE_URL = "jdbc:sqlite:trafik_ceza.db";

    public static final String APP_VERSION = "1.0.0-beta";

    public static Connection getConnection() throws SQLException {
        Connection newConnection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            newConnection = DriverManager.getConnection(DATABASE_URL);
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC sürücüsü bulunamadı: " + e.getMessage());
            throw new SQLException("SQLite JDBC sürücüsü bulunamadı.", e);
        } catch (SQLException e) {
            System.err.println("Veritabanı bağlantısı kurulamadı: " + e.getMessage());
            throw e;
        }
        return newConnection;
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            String createSahiplerTable = "CREATE TABLE IF NOT EXISTS Sahipler ("
                    + "sahip_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "ad TEXT NOT NULL,"
                    + "soyad TEXT NOT NULL,"
                    + "tc_kimlik TEXT UNIQUE NOT NULL,"
                    + "adres TEXT,"
                    + "telefon TEXT"
                    + ");";

            String createAraclarTable = "CREATE TABLE IF NOT EXISTS Araclar ("
                    + "arac_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "plaka TEXT UNIQUE NOT NULL,"
                    + "marka TEXT,"
                    + "model TEXT,"
                    + "yil INTEGER,"
                    + "sahip_id INTEGER,"
                    + "FOREIGN KEY (sahip_id) REFERENCES Sahipler(sahip_id)"
                    + ");";

            String createCezaTurleriTable = "CREATE TABLE IF NOT EXISTS CezaTurleri ("
                    + "ceza_turu_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "aciklama TEXT NOT NULL UNIQUE,"
                    + "varsayilan_tutar REAL NOT NULL"
                    + ");";

            String createCezalarTable = "CREATE TABLE IF NOT EXISTS Cezalar ("
                    + "ceza_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "arac_id INTEGER NOT NULL,"
                    + "ceza_turu_id INTEGER NOT NULL,"
                    + "ceza_tarihi TEXT NOT NULL,"
                    + "ceza_saati TEXT NOT NULL,"
                    + "ceza_yeri TEXT,"
                    + "kesilen_tutar REAL NOT NULL,"
                    + "odeme_durumu INTEGER DEFAULT 0,"
                    + "aciklama_detay TEXT,"
                    + "FOREIGN KEY (arac_id) REFERENCES Araclar(arac_id),"
                    + "FOREIGN KEY (ceza_turu_id) REFERENCES CezaTurleri(ceza_turu_id)"
                    + ");";

            String createOdemelerTable = "CREATE TABLE IF NOT EXISTS Odemeler ("
                    + "odeme_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "ceza_id INTEGER NOT NULL UNIQUE,"
                    + "odeme_tarihi TEXT NOT NULL,"
                    + "odenen_tutar REAL NOT NULL,"
                    + "odeme_yontemi TEXT,"
                    + "FOREIGN KEY (ceza_id) REFERENCES Cezalar(ceza_id)"
                    + ");";

            String createKullanicilarTable = "CREATE TABLE IF NOT EXISTS Kullanicilar ("
                    + "kullanici_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "kullanici_adi TEXT UNIQUE NOT NULL,"
                    + "sifre TEXT NOT NULL,"
                    + "ad TEXT,"
                    + "soyad TEXT,"
                    + "rol TEXT NOT NULL"
                    + ");";

            stmt.execute(createSahiplerTable);
            stmt.execute(createAraclarTable);
            stmt.execute(createCezaTurleriTable);
            stmt.execute(createCezalarTable);
            stmt.execute(createOdemelerTable);
            stmt.execute(createKullanicilarTable);

            addDefaultUserIfNotExists(stmt);
            addDefaultCezaTurleriIfNotExists(stmt);

            System.out.println("Veritabanı tabloları başarıyla oluşturuldu/kontrol edildi.");

        } catch (SQLException e) {
            System.err.println("Veritabanı başlatılırken hata oluştu: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void addDefaultUserIfNotExists(Statement stmt) throws SQLException {
        String checkAdmin = "SELECT COUNT(*) FROM Kullanicilar WHERE kullanici_adi = 'admin'";
        try (ResultSet rs = stmt.executeQuery(checkAdmin)) {
            if (rs.next() && rs.getInt(1) == 0) {
                String insertAdmin = "INSERT INTO Kullanicilar (kullanici_adi, sifre, ad, soyad, rol) VALUES "
                                   + "('admin', 'admin123', 'Sistem', 'Yöneticisi', 'Admin')";
                stmt.executeUpdate(insertAdmin);
                System.out.println("Varsayılan admin kullanıcısı eklendi.");
            }
        }
    }

    private static void addDefaultCezaTurleriIfNotExists(Statement stmt) throws SQLException {
        String[] cezaTurleri = {
            "Hız Limiti İhlali;150.00", "Kırmızı Işık İhlali;250.00", "Park Yasağı İhlali;100.00",
            "Emniyet Kemeri Takmama;120.00", "Alkollü Araç Kullanma;1200.00", "Muayenesiz Araç Kullanma;300.00"
        };

        for (String cezaTuruData : cezaTurleri) {
            String[] data = cezaTuruData.split(";");
            String aciklama = data[0];
            double tutar = Double.parseDouble(data[1]);

            String checkCezaTuru = "SELECT COUNT(*) FROM CezaTurleri WHERE aciklama = '" + aciklama.replace("'", "''") + "'";
            try (ResultSet rs = stmt.executeQuery(checkCezaTuru)) {
                if (rs.next() && rs.getInt(1) == 0) {
                    String insertCezaTuru = "INSERT INTO CezaTurleri (aciklama, varsayilan_tutar) VALUES "
                                       + "('" + aciklama.replace("'", "''") + "', " + tutar + ")";
                    stmt.executeUpdate(insertCezaTuru);
                }
            }
        }
        System.out.println("Varsayılan ceza türleri eklendi/kontrol edildi.");
    }

}