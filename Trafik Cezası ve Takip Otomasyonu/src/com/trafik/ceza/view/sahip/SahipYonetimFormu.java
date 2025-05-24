package com.trafik.ceza.view.sahip;

import com.trafik.ceza.model.Sahip;
import com.trafik.ceza.service.impl.SahipServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class SahipYonetimFormu extends JFrame {

    private SahipServiceImpl sahipService;

    private JTextField txtAd, txtSoyad, txtTcKimlik, txtAdres, txtTelefon, txtArama, txtSahipId;
    private JButton btnEkle, btnGuncelle, btnSil, btnTemizle, btnAra;
    private JTable sahipTable;
    private DefaultTableModel tableModel;

    public SahipYonetimFormu() {
        sahipService = new SahipServiceImpl();

        setTitle("Sahip Yönetim Ekranı");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        sahipleriListele();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(248, 249, 250));

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        headerPanel.setBackground(new Color(240, 240, 240));
        JLabel lblTitle = new JLabel("Sahip Yönetim Ekranı");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.BLACK);
        headerPanel.add(lblTitle);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        JPanel centerContentPanel = new JPanel(new BorderLayout(15,15));
        centerContentPanel.setOpaque(false);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(222, 226, 230)),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        Font labelFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 13);

        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        JLabel lblSahipId = new JLabel("Sahip ID:"); lblSahipId.setFont(labelFont);
        formPanel.add(lblSahipId, gbc);
        txtSahipId = new JTextField(15); txtSahipId.setFont(fieldFont);
        txtSahipId.setEditable(false);
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 2;
        formPanel.add(txtSahipId, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.WEST;
        JLabel lblAd = new JLabel("Ad:"); lblAd.setFont(labelFont);
        formPanel.add(lblAd, gbc);
        txtAd = new JTextField(15); txtAd.setFont(fieldFont);
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 2;
        formPanel.add(txtAd, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.WEST;
        JLabel lblSoyad = new JLabel("Soyad:"); lblSoyad.setFont(labelFont);
        formPanel.add(lblSoyad, gbc);
        txtSoyad = new JTextField(15); txtSoyad.setFont(fieldFont);
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 2;
        formPanel.add(txtSoyad, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.WEST;
        JLabel lblTc = new JLabel("TC Kimlik:"); lblTc.setFont(labelFont);
        formPanel.add(lblTc, gbc);
        txtTcKimlik = new JTextField(15); txtTcKimlik.setFont(fieldFont);
        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 2;
        formPanel.add(txtTcKimlik, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 4; gbc.anchor = GridBagConstraints.WEST;
        JLabel lblAdres = new JLabel("Adres:"); lblAdres.setFont(labelFont);
        formPanel.add(lblAdres, gbc);
        txtAdres = new JTextField(15); txtAdres.setFont(fieldFont);
        gbc.gridx = 1; gbc.gridy = 4; gbc.gridwidth = 2;
        formPanel.add(txtAdres, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 5; gbc.anchor = GridBagConstraints.WEST;
        JLabel lblTelefon = new JLabel("Telefon:"); lblTelefon.setFont(labelFont);
        formPanel.add(lblTelefon, gbc);
        txtTelefon = new JTextField(15); txtTelefon.setFont(fieldFont);
        gbc.gridx = 1; gbc.gridy = 5; gbc.gridwidth = 2;
        formPanel.add(txtTelefon, gbc);
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
                BorderFactory.createLineBorder(new Color(222, 226, 230)),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        aramaPanel.setBackground(Color.WHITE);
        JLabel lblArama = new JLabel("Ara (Ad/Soyad/TC):"); lblArama.setFont(labelFont);
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


        String[] columnNames = {"ID", "Ad", "Soyad", "TC Kimlik", "Adres", "Telefon"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        sahipTable = new JTable(tableModel);
        sahipTable.setFillsViewportHeight(true);
        sahipTable.setRowHeight(28);
        sahipTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sahipTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        sahipTable.getTableHeader().setBackground(new Color(230, 230, 230));
        sahipTable.getTableHeader().setForeground(Color.BLACK);
        sahipTable.setGridColor(new Color(222, 226, 230));
        sahipTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
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


        JScrollPane scrollPane = new JScrollPane(sahipTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(222, 226, 230)));
        
        centerContentPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(centerContentPanel, BorderLayout.CENTER);
        add(mainPanel);

        btnEkle.addActionListener(e -> sahipEkle());
        btnGuncelle.addActionListener(e -> sahipGuncelle());
        btnSil.addActionListener(e -> sahipSil());
        btnTemizle.addActionListener(e -> formuTemizle());
        btnAra.addActionListener(e -> sahipAra());

        sahipTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int selectedRow = sahipTable.getSelectedRow();
                if (selectedRow >= 0) {
                    txtSahipId.setText(tableModel.getValueAt(selectedRow, 0).toString());
                    txtAd.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    txtSoyad.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    txtTcKimlik.setText(tableModel.getValueAt(selectedRow, 3).toString());
                    txtAdres.setText(tableModel.getValueAt(selectedRow, 4) != null ? tableModel.getValueAt(selectedRow, 4).toString() : "");
                    txtTelefon.setText(tableModel.getValueAt(selectedRow, 5) != null ? tableModel.getValueAt(selectedRow, 5).toString() : "");
                }
            }
        });
    }

    private void sahipleriListele() {
        try {
            List<Sahip> sahipler = sahipService.tumunuListele();
            tableModel.setRowCount(0);
            for (Sahip sahip : sahipler) {
                tableModel.addRow(new Object[]{
                        sahip.getSahipId(),
                        sahip.getAd(),
                        sahip.getSoyad(),
                        sahip.getTcKimlik(),
                        sahip.getAdres(),
                        sahip.getTelefon()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Sahipler listelenirken hata: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sahipEkle() {
        if (!girdiKontrolu()) return;
        try {
            Sahip sahip = new Sahip(txtAd.getText(), txtSoyad.getText(), txtTcKimlik.getText(), txtAdres.getText(), txtTelefon.getText());
            sahipService.ekle(sahip);
            sahipleriListele();
            formuTemizle();
            JOptionPane.showMessageDialog(this, "Sahip başarıyla eklendi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Sahip eklenirken hata: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sahipGuncelle() {
        if (txtSahipId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen güncellemek için tablodan bir sahip seçin.", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!girdiKontrolu()) return;
        try {
            Sahip sahip = new Sahip(Integer.parseInt(txtSahipId.getText()), txtAd.getText(), txtSoyad.getText(), txtTcKimlik.getText(), txtAdres.getText(), txtTelefon.getText());
            sahipService.guncelle(sahip);
            sahipleriListele();
            formuTemizle();
            JOptionPane.showMessageDialog(this, "Sahip başarıyla güncellendi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Sahip güncellenirken hata: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sahipSil() {
        if (txtSahipId.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen silmek için tablodan bir sahip seçin.", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Seçili sahibi silmek istediğinizden emin misiniz?", "Silme Onayı", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                sahipService.sil(Integer.parseInt(txtSahipId.getText()));
                sahipleriListele();
                formuTemizle();
                JOptionPane.showMessageDialog(this, "Sahip başarıyla silindi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Sahip silinirken hata: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private boolean girdiKontrolu() {
        if (txtAd.getText().trim().isEmpty() || txtSoyad.getText().trim().isEmpty() || txtTcKimlik.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ad, Soyad ve TC Kimlik alanları boş bırakılamaz!", "Giriş Hatası", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (txtTcKimlik.getText().trim().length() != 11 || !txtTcKimlik.getText().trim().matches("\\d+")) {
             JOptionPane.showMessageDialog(this, "TC Kimlik 11 haneli ve sadece rakamlardan oluşmalıdır!", "Giriş Hatası", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void formuTemizle() {
        txtSahipId.setText("");
        txtAd.setText("");
        txtSoyad.setText("");
        txtTcKimlik.setText("");
        txtAdres.setText("");
        txtTelefon.setText("");
        txtArama.setText("");
        sahipTable.clearSelection();
    }

    private void sahipAra() {
        String kriter = txtArama.getText();
        if (kriter.trim().isEmpty()) {
            sahipleriListele();
            return;
        }
        try {
            List<Sahip> sahipler = sahipService.kritereGoreAra(kriter);
            tableModel.setRowCount(0);
            if (sahipler.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Arama kriterine uygun sahip bulunamadı.", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
            } else {
                for (Sahip sahip : sahipler) {
                    tableModel.addRow(new Object[]{
                            sahip.getSahipId(),
                            sahip.getAd(),
                            sahip.getSoyad(),
                            sahip.getTcKimlik(),
                            sahip.getAdres(),
                            sahip.getTelefon()
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Sahipler aranırken hata: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180,180,180)),
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
            SahipYonetimFormu form = new SahipYonetimFormu();
            form.setVisible(true);
        });
    }
}
