package com.trafik.ceza.view.giris;

import com.trafik.ceza.db.DatabaseHelper;
import com.trafik.ceza.model.Kullanici;
import com.trafik.ceza.view.main.AnaMenuFormu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GirisFormu extends JFrame {
    private JTextField kullaniciAdiField;
    private JPasswordField sifreField;
    private JButton girisButton;
    private JLabel mesajLabel;
    private JComboBox<String> girisTuruCombo;
    private JLabel kullaniciAdiLabel;
    private JLabel sifreLabel;

    public GirisFormu() {
        setTitle("Sistem Girişi");
        setSize(450, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(0,0));
        mainPanel.setBackground(new Color(248, 249, 250));
        setContentPane(mainPanel);

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        headerPanel.setBackground(new Color(230, 230, 230));
        JLabel lblAppTitle = new JLabel("Trafik Ceza Takip Sistemi");
        lblAppTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblAppTitle.setForeground(Color.BLACK);
        headerPanel.add(lblAppTitle);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(30, 40, 30, 40),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        )));
        fieldsPanel.setBackground(Color.WHITE);
        mainPanel.add(fieldsPanel, BorderLayout.CENTER);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Giriş Türü
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        JLabel lblGirisTuru = new JLabel("Giriş Türü:"); lblGirisTuru.setFont(labelFont);
        fieldsPanel.add(lblGirisTuru, gbc);

        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        String[] girisTurleri = {"Sahip/Kullanıcı Girişi", "Admin Girişi"};
        girisTuruCombo = new JComboBox<>(girisTurleri); girisTuruCombo.setFont(fieldFont);
        girisTuruCombo.setBackground(Color.WHITE);
        fieldsPanel.add(girisTuruCombo, gbc);

        // Kullanıcı Adı Etiketi ve Alanı
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        kullaniciAdiLabel = new JLabel("TC Kimlik No:"); kullaniciAdiLabel.setFont(labelFont);
        fieldsPanel.add(kullaniciAdiLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        kullaniciAdiField = new JTextField(15); kullaniciAdiField.setFont(fieldFont);
        fieldsPanel.add(kullaniciAdiField, gbc);

        // Şifre Etiketi ve Alanı
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        sifreLabel = new JLabel("Şifre:"); sifreLabel.setFont(labelFont);
        fieldsPanel.add(sifreLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 2; gbc.weightx = 1.0;
        sifreField = new JPasswordField(15); sifreField.setFont(fieldFont);
        fieldsPanel.add(sifreField, gbc);

        sifreLabel.setVisible(false);
        sifreField.setVisible(false);

        girisTuruCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String secilenTur = (String) girisTuruCombo.getSelectedItem();
                if ("Admin Girişi".equals(secilenTur)) {
                    kullaniciAdiLabel.setText("Kullanıcı Adı:");
                    sifreLabel.setVisible(true);
                    sifreField.setVisible(true);
                } else {
                    kullaniciAdiLabel.setText("TC Kimlik No:");
                    sifreLabel.setVisible(false);
                    sifreField.setVisible(false);
                    sifreField.setText("");
                }
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        mesajLabel = new JLabel(" ");
        mesajLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.weightx = 0;
        gbc.insets = new Insets(15, 8, 5, 8);
        fieldsPanel.add(mesajLabel, gbc);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 8, 10, 8);
        girisButton = new JButton("Giriş Yap");
        girisButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        girisButton.setBackground(new Color(0, 123, 255));
        girisButton.setForeground(Color.BLACK);
        girisButton.setFocusPainted(false);
        girisButton.setPreferredSize(new Dimension(100, 40));
        girisButton.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        Color originalBgColor = girisButton.getBackground();
        Color hoverBgColor = originalBgColor.darker();
        girisButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                girisButton.setBackground(hoverBgColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                girisButton.setBackground(originalBgColor);
            }
        });
        fieldsPanel.add(girisButton, gbc);

        girisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String secilenGirisTuru = (String) girisTuruCombo.getSelectedItem();
                String kullaniciAdiGiris = kullaniciAdiField.getText();
                String sifreGiris = "";

                if ("Admin Girişi".equals(secilenGirisTuru)) {
                    sifreGiris = new String(sifreField.getPassword());
                }

                Kullanici kullanici = kullaniciDogrula(kullaniciAdiGiris, sifreGiris, secilenGirisTuru);

                if (kullanici != null) {
                    mesajLabel.setText("Giriş başarılı! Rol: " + kullanici.getRol());
                    mesajLabel.setForeground(Color.GREEN);
                    SwingUtilities.invokeLater(() -> {
                        AnaMenuFormu anaMenu = new AnaMenuFormu(kullanici);
                        anaMenu.setVisible(true);
                        dispose();
                    });
                } else {
                    mesajLabel.setText("Kullanıcı adı veya şifre hatalı!");
                    mesajLabel.setForeground(Color.RED);
                }
            }
        });
    }

    private Kullanici kullaniciDogrula(String girisKimligi, String sifre, String girisTuru) {
        if ("Admin Girişi".equals(girisTuru)) {
            if ("admin".equals(girisKimligi) && "admin".equals(sifre)) {
                return new Kullanici(0, "admin", "admin", "Admin", "Kullanıcı", "admin");
            } else {
                mesajLabel.setText("Admin kullanıcı adı veya şifre hatalı!");
                return null;
            }
        } else if ("Sahip/Kullanıcı Girişi".equals(girisTuru)) {
            String sql = "SELECT sahip_id, ad, soyad, tc_kimlik FROM sahipler WHERE tc_kimlik = ?";
            try (Connection conn = DatabaseHelper.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, girisKimligi);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    return new Kullanici(
                            rs.getInt("sahip_id"),
                            rs.getString("tc_kimlik"),
                            "",
                            rs.getString("ad"),
                            rs.getString("soyad"),
                            "sahip"
                    );
                } else {
                    mesajLabel.setText("TC Kimlik No bulunamadı!");
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                mesajLabel.setText("Veritabanı hatası (Sahip): " + e.getMessage());
                return null;
            }
        }
        mesajLabel.setText("Geçersiz giriş türü!");
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GirisFormu girisFormu = new GirisFormu();
            girisFormu.setVisible(true);
        });
    }
}