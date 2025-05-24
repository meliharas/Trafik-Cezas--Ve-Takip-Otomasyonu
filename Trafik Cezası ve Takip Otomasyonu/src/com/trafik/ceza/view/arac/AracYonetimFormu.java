package com.trafik.ceza.view.arac;

import com.trafik.ceza.model.Arac;
import com.trafik.ceza.model.Sahip;
import com.trafik.ceza.service.impl.AracServiceImpl;
import com.trafik.ceza.service.impl.SahipServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AracYonetimFormu extends JFrame {

    private AracServiceImpl aracService;
    private SahipServiceImpl sahipService;

    private JTextField txtPlaka, txtMarka, txtModel, txtYil, txtArama, txtAracId;
    private JComboBox<String> cmbSahipler;
    private Map<String, Integer> sahipMap;

    private JButton btnEkle, btnGuncelle, btnSil, btnTemizle, btnAra;
    private JTable aracTable;
    private DefaultTableModel tableModel;

    public AracYonetimFormu() {
        aracService = new AracServiceImpl();
        sahipService = new SahipServiceImpl();
        sahipMap = new HashMap<>();

        setTitle("Araç Yönetim Ekranı");
        setSize(850, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        sahipleriYukle();
        araclariListele();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(245, 245, 245));

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        headerPanel.setBackground(new Color(240, 240, 240));
        JLabel lblTitle = new JLabel("Araç Yönetim Ekranı");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.BLACK);
        headerPanel.add(lblTitle);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

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

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        JLabel lblAracId = new JLabel("Araç ID:"); lblAracId.setFont(labelFont);
        formPanel.add(lblAracId, gbc);
        txtAracId = new JTextField(15); txtAracId.setFont(fieldFont);
        txtAracId.setEditable(false);
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 2;
        formPanel.add(txtAracId, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        JLabel lblPlaka = new JLabel("Plaka:"); lblPlaka.setFont(labelFont);
        formPanel.add(lblPlaka, gbc);
        txtPlaka = new JTextField(15); txtPlaka.setFont(fieldFont);
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 2;
        formPanel.add(txtPlaka, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST;
        JLabel lblMarka = new JLabel("Marka:"); lblMarka.setFont(labelFont);
        formPanel.add(lblMarka, gbc);
        txtMarka = new JTextField(15); txtMarka.setFont(fieldFont);
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 2;
        formPanel.add(txtMarka, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.WEST;
        JLabel lblModel = new JLabel("Model:"); lblModel.setFont(labelFont);
        formPanel.add(lblModel, gbc);
        txtModel = new JTextField(15); txtModel.setFont(fieldFont);
        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 2;
        formPanel.add(txtModel, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.WEST;
        JLabel lblYil = new JLabel("Yıl:"); lblYil.setFont(labelFont);
        formPanel.add(lblYil, gbc);
        txtYil = new JTextField(15); txtYil.setFont(fieldFont);
        gbc.gridx = 1; gbc.gridy = 4; gbc.gridwidth = 2;
        formPanel.add(txtYil, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 5; gbc.anchor = GridBagConstraints.WEST;
        JLabel lblSahip = new JLabel("Sahip:"); lblSahip.setFont(labelFont);
        formPanel.add(lblSahip, gbc);
        cmbSahipler = new JComboBox<>(); cmbSahipler.setFont(fieldFont);
        gbc.gridx = 1; gbc.gridy = 5; gbc.gridwidth = 2;
        formPanel.add(cmbSahipler, gbc);
        gbc.gridwidth = 1;

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 10));
        buttonPanel.setOpaque(false);
        Color btnBgColor = new Color(225, 225, 225);
        btnEkle = createStyledButton("Ekle", btnBgColor, new Color(0, 128, 0));
        btnGuncelle = createStyledButton("Güncelle", btnBgColor, new Color(255, 140, 0));
        btnSil = createStyledButton("Sil", btnBgColor, Color.RED);
        btnTemizle = createStyledButton("Temizle", btnBgColor, Color.BLUE);
        buttonPanel.add(btnEkle);
        buttonPanel.add(btnGuncelle);
        buttonPanel.add(btnSil);
        buttonPanel.add(btnTemizle);
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(buttonPanel, gbc);
        gbc.gridwidth = 1;


        JPanel aramaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        aramaPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        aramaPanel.setBackground(Color.WHITE);
        JLabel lblArama = new JLabel("Ara (Plaka/Marka/Model):"); lblArama.setFont(labelFont);
        aramaPanel.add(lblArama);
        txtArama = new JTextField(20); txtArama.setFont(fieldFont);
        aramaPanel.add(txtArama);
        btnAra = createStyledButton("Ara", btnBgColor, Color.BLACK);
        aramaPanel.add(btnAra);
        
        JPanel topCombinedPanel = new JPanel(new BorderLayout(0, 8));
        topCombinedPanel.setOpaque(false);
        topCombinedPanel.add(formPanel, BorderLayout.NORTH);
        topCombinedPanel.add(aramaPanel, BorderLayout.CENTER);
        
        centerContentPanel.add(topCombinedPanel, BorderLayout.NORTH);

        String[] columnNames = {"ID", "Plaka", "Marka", "Model", "Yıl", "Sahip ID", "Sahip Adı Soyadı"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        aracTable = new JTable(tableModel);
        aracTable.setFillsViewportHeight(true);
        aracTable.setRowHeight(28);
        aracTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        aracTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        aracTable.getTableHeader().setBackground(new Color(230, 230, 230));
        aracTable.getTableHeader().setForeground(Color.BLACK);
        aracTable.setGridColor(new Color(222, 226, 230));
        aracTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
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
        
        JScrollPane scrollPane = new JScrollPane(aracTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230)));

        centerContentPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(centerContentPanel, BorderLayout.CENTER);
        add(mainPanel);

        btnEkle.addActionListener(e -> aracEkle());
        btnGuncelle.addActionListener(e -> aracGuncelle());
        btnSil.addActionListener(e -> aracSil());
        btnTemizle.addActionListener(e -> formuTemizle());
        btnAra.addActionListener(e -> aracAra());

        aracTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = aracTable.getSelectedRow();
                if (selectedRow >= 0) {
                    txtAracId.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    txtPlaka.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    txtMarka.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    txtModel.setText(tableModel.getValueAt(selectedRow, 3).toString());
                    txtYil.setText(tableModel.getValueAt(selectedRow, 4).toString());
                    
                    String sahipAdSoyad = tableModel.getValueAt(selectedRow, 6).toString();
                    for (int i = 0; i < cmbSahipler.getItemCount(); i++) {
                        if (cmbSahipler.getItemAt(i).equals(sahipAdSoyad)) {
                            cmbSahipler.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void sahipleriYukle() {
        try {
            List<Sahip> sahipler = sahipService.tumunuListele();
            cmbSahipler.removeAllItems();
            sahipMap.clear();
            cmbSahipler.addItem("Sahip Seçiniz...");
            for (Sahip sahip : sahipler) {
                String sahipAdi = sahip.getAd() + " " + sahip.getSoyad() + " (" + sahip.getTcKimlik() + ")";
                cmbSahipler.addItem(sahipAdi);
                sahipMap.put(sahipAdi, sahip.getSahipId());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Sahipler yüklenirken hata: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void araclariListele() {
        try {
            List<Arac> araclar = aracService.tumunuListele();
            tableModel.setRowCount(0);
            for (Arac arac : araclar) {
                Sahip sahip = sahipService.idIleBul(arac.getSahipId());
                String sahipAdiSoyadi = sahip != null ? (sahip.getAd() + " " + sahip.getSoyad()) : "Bilinmiyor";
                tableModel.addRow(new Object[]{
                        arac.getAracId(),
                        arac.getPlaka(),
                        arac.getMarka(),
                        arac.getModel(),
                        arac.getYil(),
                        arac.getSahipId(),
                        sahipAdiSoyadi
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Araçlar listelenirken hata: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean girdiKontrolu() {
        if (txtPlaka.getText().trim().isEmpty() || txtMarka.getText().trim().isEmpty() || 
            txtModel.getText().trim().isEmpty() || txtYil.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Plaka, Marka, Model ve Yıl alanları boş bırakılamaz!", "Giriş Hatası", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Integer.parseInt(txtYil.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Yıl geçerli bir sayı olmalıdır!", "Giriş Hatası", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (cmbSahipler.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Lütfen bir sahip seçiniz!", "Giriş Hatası", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void aracEkle() {
        if (!girdiKontrolu()) return;
        try {
            String seciliSahipAdi = (String) cmbSahipler.getSelectedItem();
            Integer sahipId = sahipMap.get(seciliSahipAdi);
            if (sahipId == null) {
                 JOptionPane.showMessageDialog(this, "Geçerli bir sahip seçilemedi.", "Hata", JOptionPane.ERROR_MESSAGE);
                 return;
            }

            Arac arac = new Arac(txtPlaka.getText(), txtMarka.getText(), txtModel.getText(),
                                 Integer.parseInt(txtYil.getText()), sahipId);
            aracService.ekle(arac);
            araclariListele();
            formuTemizle();
            JOptionPane.showMessageDialog(this, "Araç başarıyla eklendi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Araç eklenirken hata: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void aracGuncelle() {
        if (txtAracId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen güncellemek için tablodan bir araç seçin.", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!girdiKontrolu()) return;
        try {
            String seciliSahipAdi = (String) cmbSahipler.getSelectedItem();
            Integer sahipId = sahipMap.get(seciliSahipAdi);
             if (sahipId == null) {
                 JOptionPane.showMessageDialog(this, "Geçerli bir sahip seçilemedi.", "Hata", JOptionPane.ERROR_MESSAGE);
                 return;
            }

            Arac arac = new Arac(Integer.parseInt(txtAracId.getText()), txtPlaka.getText(), txtMarka.getText(),
                                 txtModel.getText(), Integer.parseInt(txtYil.getText()), sahipId);
            aracService.guncelle(arac);
            araclariListele();
            formuTemizle();
            JOptionPane.showMessageDialog(this, "Araç başarıyla güncellendi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Araç güncellenirken hata: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void aracSil() {
        if (txtAracId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen silmek için tablodan bir araç seçin.", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Seçili aracı silmek istediğinizden emin misiniz?", "Silme Onayı", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                aracService.sil(Integer.parseInt(txtAracId.getText()));
                araclariListele();
                formuTemizle();
                JOptionPane.showMessageDialog(this, "Araç başarıyla silindi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Araç silinirken hata: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void formuTemizle() {
        txtAracId.setText("");
        txtPlaka.setText("");
        txtMarka.setText("");
        txtModel.setText("");
        txtYil.setText("");
        cmbSahipler.setSelectedIndex(0);
        txtArama.setText("");
        aracTable.clearSelection();
    }

    private void aracAra() {
        String kriter = txtArama.getText();
        if (kriter.trim().isEmpty()) {
            araclariListele();
            return;
        }
        try {
            List<Arac> araclar = aracService.kritereGoreAra(kriter);
            tableModel.setRowCount(0);
            if (araclar.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Arama kriterine uygun araç bulunamadı.", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
            } else {
                for (Arac arac : araclar) {
                    Sahip sahip = sahipService.idIleBul(arac.getSahipId());
                    String sahipAdiSoyadi = sahip != null ? (sahip.getAd() + " " + sahip.getSoyad()) : "Bilinmiyor";
                    tableModel.addRow(new Object[]{
                            arac.getAracId(), arac.getPlaka(), arac.getMarka(), arac.getModel(),
                            arac.getYil(), arac.getSahipId(), sahipAdiSoyadi
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Araçlar aranırken hata: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AracYonetimFormu form = new AracYonetimFormu();
            form.setVisible(true);
        });
    }
}