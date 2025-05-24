package com.trafik.ceza.view.main;

import com.trafik.ceza.db.DatabaseHelper;
import com.trafik.ceza.model.Kullanici;
import com.trafik.ceza.view.sahip.SahipYonetimFormu;
import com.trafik.ceza.view.arac.AracYonetimFormu;
import com.trafik.ceza.view.ceza.CezaIslemFormu;
import com.trafik.ceza.view.giris.GirisFormu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AnaMenuFormu extends JFrame {

    private Kullanici aktifKullanici;

    public AnaMenuFormu(Kullanici kullanici) {
        this.aktifKullanici = kullanici;
        setTitle("Trafik Ceza Takip Otomasyonu - Ana Menü (Kullanıcı: " + aktifKullanici.getKullaniciAdi() + " - Rol: " + aktifKullanici.getRol() + ")");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        headerPanel.setBackground(new Color(230, 230, 230));
        JLabel lblTitle = new JLabel("Trafik Ceza ve Araç Takip Sistemi");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(Color.BLACK);
        headerPanel.add(lblTitle);
        add(headerPanel, BorderLayout.NORTH);

        JPanel menuPanel = new JPanel();
        menuPanel.setBackground(new Color(248, 249, 250));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        menuPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 10, 15, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JButton btnSahipYonetimi = createMenuButton("Araç Sahip Yönetimi");
        btnSahipYonetimi.addActionListener(e -> openSahipYonetimFormu());
        gbc.gridx = 0; gbc.gridy = 0;
        menuPanel.add(btnSahipYonetimi, gbc);

        JButton btnAracYonetimi = createMenuButton("Araç Yönetimi");
        btnAracYonetimi.setEnabled(true);
        btnAracYonetimi.addActionListener(e -> openAracYonetimFormu());
        gbc.gridx = 0; gbc.gridy = 1;
        menuPanel.add(btnAracYonetimi, gbc);

        JButton btnCezaIslemleri = createMenuButton("Ceza İşlemleri ve Ödeme");
        btnCezaIslemleri.setEnabled(true);
        btnCezaIslemleri.addActionListener(e -> openCezaIslemFormu(aktifKullanici));
        gbc.gridx = 0; gbc.gridy = 2;
        menuPanel.add(btnCezaIslemleri, gbc);

        if (aktifKullanici != null && "sahip".equals(aktifKullanici.getRol())) {
            btnSahipYonetimi.setEnabled(false);
            btnAracYonetimi.setEnabled(false);
        } else if (aktifKullanici != null && "admin".equals(aktifKullanici.getRol())) {
            btnSahipYonetimi.setEnabled(true);
            btnAracYonetimi.setEnabled(true);
            btnCezaIslemleri.setEnabled(true);
        }


        add(menuPanel, BorderLayout.CENTER);

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        footerPanel.setBackground(new Color(220, 220, 220));
        JLabel lblFooter = new JLabel("Versiyon: " + DatabaseHelper.APP_VERSION + " | © 2024-2025");
        lblFooter.setForeground(Color.BLACK);
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        footerPanel.add(lblFooter);
        add(footerPanel, BorderLayout.SOUTH);
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 15));
        button.setPreferredSize(new Dimension(320, 55));
        button.setFocusPainted(false);
        button.setBackground(new Color(255, 165, 0));
        button.setForeground(Color.BLACK);
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        
        Color originalBgColor = button.getBackground();
        Color hoverBgColor = originalBgColor.darker();
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverBgColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalBgColor);
            }
        });
        return button;
    }

    private void openSahipYonetimFormu() {
        SwingUtilities.invokeLater(() -> {
            SahipYonetimFormu sahipFormu = new SahipYonetimFormu();
            sahipFormu.setVisible(true);
        });
    }

    private void openAracYonetimFormu() {
        SwingUtilities.invokeLater(() -> {
            AracYonetimFormu aracFormu = new AracYonetimFormu();
            aracFormu.setVisible(true);
        });
    }

    private void openCezaIslemFormu(Kullanici kullanici) {
        SwingUtilities.invokeLater(() -> {
            CezaIslemFormu cezaFormu = new CezaIslemFormu(kullanici);
            cezaFormu.setVisible(true);
        });
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Look and Feel ayarlanamadı: " + e.getMessage());
        }
        
        DatabaseHelper.initializeDatabase();

        SwingUtilities.invokeLater(() -> {
            GirisFormu girisFormu = new GirisFormu();
            girisFormu.setVisible(true);
        });
    }
}