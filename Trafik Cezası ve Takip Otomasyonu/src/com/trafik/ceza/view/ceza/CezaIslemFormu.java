package com.trafik.ceza.view.ceza;

import com.trafik.ceza.model.Arac;
import com.trafik.ceza.model.Ceza;
import com.trafik.ceza.model.CezaTuru;
import com.trafik.ceza.model.Kullanici;
import com.trafik.ceza.service.impl.AracServiceImpl;
import com.trafik.ceza.service.impl.CezaServiceImpl;
import com.trafik.ceza.service.impl.CezaTuruServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CezaIslemFormu extends JFrame {

    private CezaServiceImpl cezaService;
    private AracServiceImpl aracService;
    private CezaTuruServiceImpl cezaTuruService;
    private Kullanici aktifKullanici;

    private JTextField txtCezaId, txtCezaTarihi, txtCezaSaati, txtCezaYeri, txtKesilenTutar, txtAciklamaDetay, txtAramaPlaka;
    private JComboBox<String> cmbAraclar, cmbCezaTurleri;
    private JCheckBox chkOdemeDurumu;
    private JButton btnEkle, btnGuncelle, btnSil, btnTemizle, btnAra;
    private JTable cezaTable;
    private DefaultTableModel tableModel;
    private JPanel aramaPanel;

    private Map<String, Integer> aracMap;
    private Map<String, Integer> cezaTuruMap;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    public CezaIslemFormu(Kullanici kullanici) {
        this.aktifKullanici = kullanici;
        cezaService = new CezaServiceImpl();
        aracService = new AracServiceImpl();
        cezaTuruService = new CezaTuruServiceImpl();
        aracMap = new HashMap<>();
        cezaTuruMap = new HashMap<>();

        setTitle("Ceza İşlem ve Takip Ekranı - Kullanıcı: " + aktifKullanici.getKullaniciAdi() + " (" + aktifKullanici.getRol() + ")");
        setSize(950, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        araclariYukle();
        cezaTurleriniYukle();
        yetkilendirmeUygula();
        cezalariListele(null);
    }

    private void yetkilendirmeUygula() {
        if (aktifKullanici == null) return;

        boolean isAdmin = "admin".equals(aktifKullanici.getRol());

        btnEkle.setEnabled(isAdmin);
        btnGuncelle.setEnabled(isAdmin);
        btnSil.setEnabled(isAdmin);
        btnTemizle.setEnabled(isAdmin);

        txtCezaTarihi.setEditable(isAdmin);
        txtCezaSaati.setEditable(isAdmin);
        txtCezaYeri.setEditable(isAdmin);
        txtKesilenTutar.setEditable(isAdmin);
        txtAciklamaDetay.setEditable(isAdmin);
        cmbCezaTurleri.setEnabled(isAdmin);
        chkOdemeDurumu.setEnabled(isAdmin);
        
        cmbAraclar.setEnabled(true);

        if (aramaPanel != null) {
            aramaPanel.setVisible(isAdmin);
        }
    }


    private void initComponents() {
        panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(245, 245, 245));

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        headerPanel.setBackground(new Color(240, 240, 240));
        String kullaniciBilgisi = aktifKullanici != null ? aktifKullanici.getKullaniciAdi() + " (" + aktifKullanici.getRol() + ")" : "";
        JLabel lblTitle = new JLabel("Ceza İşlem ve Takip Ekranı " + (aktifKullanici != null ? "- Kullanıcı: " + kullaniciBilgisi : ""));
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.BLACK);
        headerPanel.add(lblTitle);
        panel.add(headerPanel, BorderLayout.NORTH);
        
        JPanel centerContentPanel = new JPanel(new BorderLayout(15,15));
        centerContentPanel.setOpaque(false);


        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 13);

        addFormField(formPanel, gbc, 0, "Ceza ID:", txtCezaId = new JTextField(15), labelFont, fieldFont);
        txtCezaId.setEditable(false);
        addFormField(formPanel, gbc, 1, "Araç (Plaka):", cmbAraclar = new JComboBox<>(), labelFont, fieldFont);
        addFormField(formPanel, gbc, 2, "Ceza Türü:", cmbCezaTurleri = new JComboBox<>(), labelFont, fieldFont);
        cmbCezaTurleri.addActionListener(e -> seciliCezaTuruTutariniAyarla());
        addFormField(formPanel, gbc, 3, "Ceza Tarihi (YYYY-AA-GG):", txtCezaTarihi = new JTextField(15), labelFont, fieldFont);
        txtCezaTarihi.setText(dateFormat.format(new Date()));
        addFormField(formPanel, gbc, 4, "Ceza Saati (SS:DD):", txtCezaSaati = new JTextField(15), labelFont, fieldFont);
        txtCezaSaati.setText(timeFormat.format(new Date()));
        addFormField(formPanel, gbc, 5, "Ceza Yeri:", txtCezaYeri = new JTextField(15), labelFont, fieldFont);
        addFormField(formPanel, gbc, 6, "Kesilen Tutar (TL):", txtKesilenTutar = new JTextField(15), labelFont, fieldFont);
        addFormField(formPanel, gbc, 7, "Açıklama Detay:", txtAciklamaDetay = new JTextField(15), labelFont, fieldFont);
        
        gbc.gridx = 0; gbc.gridy = 8; gbc.anchor = GridBagConstraints.WEST;
        JLabel lblOdeme = new JLabel("Ödeme Durumu:"); lblOdeme.setFont(labelFont);
        formPanel.add(lblOdeme, gbc);
        chkOdemeDurumu = new JCheckBox("Ödendi"); chkOdemeDurumu.setFont(labelFont); chkOdemeDurumu.setOpaque(false);
        gbc.gridx = 1; gbc.gridy = 8; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.WEST; formPanel.add(chkOdemeDurumu, gbc); gbc.gridwidth = 1;


        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 10));
        buttonPanel.setOpaque(false);
        Color btnBgColor = new Color(225, 225, 225);
        btnEkle = createStyledButton("Ceza Ekle", btnBgColor, new Color(0, 128, 0));
        btnGuncelle = createStyledButton("Ceza Güncelle", btnBgColor, new Color(255, 140, 0));
        btnSil = createStyledButton("Ceza Sil", btnBgColor, Color.RED);
        btnTemizle = createStyledButton("Formu Temizle", btnBgColor, Color.BLUE);
        buttonPanel.add(btnEkle); buttonPanel.add(btnGuncelle); buttonPanel.add(btnSil); buttonPanel.add(btnTemizle);
        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 3; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonPanel, gbc); gbc.gridwidth = 1;

        aramaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        aramaPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        aramaPanel.setBackground(Color.WHITE);
        JLabel lblArama = new JLabel("Plakaya Göre Ara:"); lblArama.setFont(labelFont);
        aramaPanel.add(lblArama);
        txtAramaPlaka = new JTextField(15); txtAramaPlaka.setFont(fieldFont);
        aramaPanel.add(txtAramaPlaka);
        btnAra = createStyledButton("Ara", btnBgColor, Color.BLACK);
        aramaPanel.add(btnAra);
        JButton btnTumCezalar = createStyledButton("Tüm Cezaları Göster", btnBgColor, Color.BLACK);
        aramaPanel.add(btnTumCezalar);

        JPanel topCombinedPanel = new JPanel(new BorderLayout(0, 8));
        topCombinedPanel.setOpaque(false);
        topCombinedPanel.add(formPanel, BorderLayout.NORTH);
        topCombinedPanel.add(aramaPanel, BorderLayout.CENTER);
        
        centerContentPanel.add(topCombinedPanel, BorderLayout.NORTH);


        String[] columnNames = {"ID", "Plaka", "Ceza Türü", "Tarih", "Saat", "Yer", "Tutar", "Ödeme Durumu", "Açıklama"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        cezaTable = new JTable(tableModel);
        cezaTable.setFillsViewportHeight(true);
        cezaTable.setRowHeight(28);
        cezaTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cezaTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        cezaTable.getTableHeader().setBackground(new Color(230, 230, 230));
        cezaTable.getTableHeader().setForeground(Color.BLACK);
        cezaTable.setGridColor(new Color(222, 226, 230));
        cezaTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 249, 250));
                if (isSelected) {
                    c.setBackground(new Color(60, 90, 153));
                    c.setForeground(Color.WHITE);
                } else {
                    c.setForeground(Color.BLACK);
                }
                ((JComponent) c).setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(cezaTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230)));
        
        centerContentPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(centerContentPanel, BorderLayout.CENTER);
        add(panel);

        btnEkle.addActionListener(e -> cezaEkle());
        btnGuncelle.addActionListener(e -> cezaGuncelle());
        btnSil.addActionListener(e -> cezaSil());
        btnTemizle.addActionListener(e -> formuTemizle());
        btnAra.addActionListener(e -> cezaAra());
        btnTumCezalar.addActionListener(e -> cezalariListele(null));


        cezaTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = cezaTable.getSelectedRow();
                if (selectedRow >= 0) {
                    txtCezaId.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    
                    String plaka = tableModel.getValueAt(selectedRow, 1).toString();
                    for (int i = 0; i < cmbAraclar.getItemCount(); i++) {
                        if (cmbAraclar.getItemAt(i).startsWith(plaka)) {
                            cmbAraclar.setSelectedIndex(i);
                            break;
                        }
                    }

                    String cezaTuruAciklama = tableModel.getValueAt(selectedRow, 2).toString();
                     for (int i = 0; i < cmbCezaTurleri.getItemCount(); i++) {
                        if (cmbCezaTurleri.getItemAt(i).equals(cezaTuruAciklama)) {
                            cmbCezaTurleri.setSelectedIndex(i);
                            break;
                        }
                    }
                    
                    txtCezaTarihi.setText(tableModel.getValueAt(selectedRow, 3).toString());
                    txtCezaSaati.setText(tableModel.getValueAt(selectedRow, 4).toString());
                    txtCezaYeri.setText(tableModel.getValueAt(selectedRow, 5) != null ? tableModel.getValueAt(selectedRow, 5).toString() : "");
                    txtKesilenTutar.setText(tableModel.getValueAt(selectedRow, 6).toString().replace(" TL", ""));
                    chkOdemeDurumu.setSelected("Ödendi".equals(tableModel.getValueAt(selectedRow, 7).toString()));
                    txtAciklamaDetay.setText(tableModel.getValueAt(selectedRow, 8) != null ? tableModel.getValueAt(selectedRow, 8).toString() : "");
                }
            }
        });
    }

    private void araclariYukle() {
        try {
            List<Arac> aracListesi;
            if (aktifKullanici != null && "sahip".equals(aktifKullanici.getRol())) {
                aracListesi = aracService.sahibeGoreAracListele(aktifKullanici.getKullaniciId());
            } else {
                aracListesi = aracService.tumunuListele();
            }

            cmbAraclar.removeAllItems();
            aracMap.clear();
            cmbAraclar.addItem("Araç Seçiniz...");
            if (aracListesi != null) {
                for (Arac arac : aracListesi) {
                    String aracBilgisi = arac.getPlaka() + " (" + arac.getMarka() + " " + arac.getModel() + ")";
                    cmbAraclar.addItem(aracBilgisi);
                    aracMap.put(aracBilgisi, arac.getAracId());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Araçlar yüklenirken hata: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cezaTurleriniYukle() {
        try {
            List<CezaTuru> cezaTurleri = cezaTuruService.tumunuListele();
            cmbCezaTurleri.removeAllItems();
            cezaTuruMap.clear();
            cmbCezaTurleri.addItem("Ceza Türü Seçiniz...");
            for (CezaTuru tur : cezaTurleri) {
                cmbCezaTurleri.addItem(tur.getAciklama());
                cezaTuruMap.put(tur.getAciklama(), tur.getCezaTuruId());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ceza türleri yüklenirken hata: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void seciliCezaTuruTutariniAyarla() {
        if (cmbCezaTurleri.getSelectedIndex() > 0) {
            String seciliTurAciklama = (String) cmbCezaTurleri.getSelectedItem();
            Integer cezaTuruId = cezaTuruMap.get(seciliTurAciklama);
            if (cezaTuruId != null) {
                try {
                    CezaTuru seciliTur = cezaTuruService.idIleBul(cezaTuruId);
                    if (seciliTur != null) {
                        txtKesilenTutar.setText(String.valueOf(seciliTur.getVarsayilanTutar()));
                    }
                } catch (Exception e) {
                    txtKesilenTutar.setText("");
                }
            }
        } else {
            txtKesilenTutar.setText("");
        }
    }

    private void cezalariListele(List<Ceza> cezalar) {
        try {
            if (cezalar == null) {
                if (aktifKullanici != null && "sahip".equals(aktifKullanici.getRol())) {
                    cezalar = cezaService.sahibinCezalariniListele(aktifKullanici.getKullaniciId());
                } else {
                    cezalar = cezaService.tumunuListele();
                }
            }

            tableModel.setRowCount(0);
            if (cezalar != null) {
                for (Ceza ceza : cezalar) {
                    String plaka = (ceza.getArac() != null && ceza.getArac().getPlaka() != null) ? ceza.getArac().getPlaka() : "Bilinmiyor";
                    String cezaTuruAciklama = (ceza.getCezaTuru() != null && ceza.getCezaTuru().getAciklama() != null) ? ceza.getCezaTuru().getAciklama() : "Bilinmiyor";
                    
                    tableModel.addRow(new Object[]{
                            ceza.getCezaId(),
                            plaka,
                            cezaTuruAciklama,
                            ceza.getCezaTarihi(),
                            ceza.getCezaSaati(),
                            ceza.getCezaYeri(),
                            String.format("%.2f TL", ceza.getKesilenTutar()),
                            ceza.getOdemeDurumuStr(),
                            ceza.getAciklamaDetay()
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cezalar listelenirken hata: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean girdiKontrolu() {
        if (cmbAraclar.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Lütfen bir araç seçin.", "Giriş Hatası", JOptionPane.ERROR_MESSAGE); return false;
        }
        if (cmbCezaTurleri.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Lütfen bir ceza türü seçin.", "Giriş Hatası", JOptionPane.ERROR_MESSAGE); return false;
        }
        if (txtCezaTarihi.getText().trim().isEmpty() || !txtCezaTarihi.getText().trim().matches("\\d{4}-\\d{2}-\\d{2}")) {
            JOptionPane.showMessageDialog(this, "Ceza tarihi YYYY-AA-GG formatında olmalıdır.", "Giriş Hatası", JOptionPane.ERROR_MESSAGE); return false;
        }
        if (txtCezaSaati.getText().trim().isEmpty() || !txtCezaSaati.getText().trim().matches("\\d{2}:\\d{2}")) {
            JOptionPane.showMessageDialog(this, "Ceza saati SS:DD formatında olmalıdır.", "Giriş Hatası", JOptionPane.ERROR_MESSAGE); return false;
        }
        if (txtKesilenTutar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Kesilen tutar boş bırakılamaz.", "Giriş Hatası", JOptionPane.ERROR_MESSAGE); return false;
        }
        try {
            Double.parseDouble(txtKesilenTutar.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Kesilen tutar geçerli bir sayı olmalıdır.", "Giriş Hatası", JOptionPane.ERROR_MESSAGE); return false;
        }
        return true;
    }

    private void cezaEkle() {
        if (!girdiKontrolu()) return;
        try {
            String seciliAracBilgisi = (String) cmbAraclar.getSelectedItem();
            Integer aracId = aracMap.get(seciliAracBilgisi);
            String seciliCezaTuru = (String) cmbCezaTurleri.getSelectedItem();
            Integer cezaTuruId = cezaTuruMap.get(seciliCezaTuru);

            if (aracId == null || cezaTuruId == null) {
                JOptionPane.showMessageDialog(this, "Araç veya Ceza Türü seçimi geçersiz.", "Hata", JOptionPane.ERROR_MESSAGE); return;
            }

            Ceza ceza = new Ceza();
            ceza.setAracId(aracId);
            ceza.setCezaTuruId(cezaTuruId);
            ceza.setCezaTarihi(txtCezaTarihi.getText());
            ceza.setCezaSaati(txtCezaSaati.getText());
            ceza.setCezaYeri(txtCezaYeri.getText());
            ceza.setKesilenTutar(Double.parseDouble(txtKesilenTutar.getText()));
            ceza.setOdemeDurumu(chkOdemeDurumu.isSelected() ? 1 : 0);
            ceza.setAciklamaDetay(txtAciklamaDetay.getText());
            
            cezaService.ekle(ceza);
            cezalariListele(null);
            formuTemizle();
            JOptionPane.showMessageDialog(this, "Ceza başarıyla eklendi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ceza eklenirken hata: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cezaGuncelle() {
        if (txtCezaId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen güncellemek için tablodan bir ceza seçin.", "Uyarı", JOptionPane.WARNING_MESSAGE); return;
        }
        if (!girdiKontrolu()) return;
        try {
            String seciliAracBilgisi = (String) cmbAraclar.getSelectedItem();
            Integer aracId = aracMap.get(seciliAracBilgisi);
            String seciliCezaTuru = (String) cmbCezaTurleri.getSelectedItem();
            Integer cezaTuruId = cezaTuruMap.get(seciliCezaTuru);

            if (aracId == null || cezaTuruId == null) {
                JOptionPane.showMessageDialog(this, "Araç veya Ceza Türü seçimi geçersiz.", "Hata", JOptionPane.ERROR_MESSAGE); return;
            }

            Ceza ceza = new Ceza();
            ceza.setCezaId(Integer.parseInt(txtCezaId.getText()));
            ceza.setAracId(aracId);
            ceza.setCezaTuruId(cezaTuruId);
            ceza.setCezaTarihi(txtCezaTarihi.getText());
            ceza.setCezaSaati(txtCezaSaati.getText());
            ceza.setCezaYeri(txtCezaYeri.getText());
            ceza.setKesilenTutar(Double.parseDouble(txtKesilenTutar.getText()));
            ceza.setOdemeDurumu(chkOdemeDurumu.isSelected() ? 1 : 0);
            ceza.setAciklamaDetay(txtAciklamaDetay.getText());

            cezaService.guncelle(ceza);
            cezalariListele(null);
            formuTemizle();
            JOptionPane.showMessageDialog(this, "Ceza başarıyla güncellendi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ceza güncellenirken hata: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cezaSil() {
        if (txtCezaId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen silmek için tablodan bir ceza seçin.", "Uyarı", JOptionPane.WARNING_MESSAGE); return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Seçili cezayı silmek istediğinizden emin misiniz?", "Silme Onayı", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                cezaService.sil(Integer.parseInt(txtCezaId.getText()));
                cezalariListele(null);
                formuTemizle();
                JOptionPane.showMessageDialog(this, "Ceza başarıyla silindi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Ceza silinirken hata: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void cezaAra() {
        String plakaKriteri = txtAramaPlaka.getText().trim();
        if (plakaKriteri.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen aramak için bir plaka girin.", "Uyarı", JOptionPane.WARNING_MESSAGE);
            cezalariListele(null);
            return;
        }
        try {
            List<Ceza> bulunanCezalar = cezaService.plakayaGoreAra(plakaKriteri);
            if (bulunanCezalar.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Belirtilen plakaya ait ceza bulunamadı.", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
            }
            cezalariListele(bulunanCezalar);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ceza aranırken hata: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void formuTemizle() {
        txtCezaId.setText("");
        cmbAraclar.setSelectedIndex(0);
        cmbCezaTurleri.setSelectedIndex(0);
        txtCezaTarihi.setText(dateFormat.format(new Date()));
        txtCezaSaati.setText(timeFormat.format(new Date()));
        txtCezaYeri.setText("");
        txtKesilenTutar.setText("");
        chkOdemeDurumu.setSelected(false);
        txtAciklamaDetay.setText("");
        txtAramaPlaka.setText("");
        cezaTable.clearSelection();
    }
    
    private void addFormField(JPanel panel, GridBagConstraints gbc, int yPos, String labelText, JComponent field, Font labelFont, Font fieldFont) {
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        gbc.gridx = 0;
        gbc.gridy = yPos;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(label, gbc);

        field.setFont(fieldFont);
        if (field instanceof JComboBox) {
            ((JComboBox<String>) field).setPrototypeDisplayValue("Biraz Uzun Bir Metin İçin Genişlik");
        }
        gbc.gridx = 1;
        gbc.gridy = yPos;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(field, gbc);
    }

    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(180, 180, 180)),
            BorderFactory.createEmptyBorder(8, 18, 8, 18)
        ));
        
        final Color finalBgColor = bgColor;
        final Color hoverBgColor;
        if (finalBgColor.equals(new Color(225,225,225))) {
            hoverBgColor = new Color(210,210,210);
        } else {
            hoverBgColor = finalBgColor.darker();
        }

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(hoverBgColor);
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(finalBgColor);
            }
        });
        return button;
    }

    private JPanel panel;
}