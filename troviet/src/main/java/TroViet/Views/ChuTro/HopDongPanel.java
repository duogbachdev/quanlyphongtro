/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package TroViet.Views.ChuTro;

import static TroViet.Connect.SQL_Connection.con;
import TroViet.Views.ChuTro.ThungRacHopDong;
import TroViet.DAO.HopDongDAO;
import TroViet.DAO.HopDongEnum;
import TroViet.DAO.SessionDAO;
import TroViet.DAO.UserDAO;
import TroViet.Model.HopDongEntity;
import TroViet.Model.SessionEntity;
import TroViet.Model.UserEntity;
import TroViet.Utils.SessionManager;
import static TroViet.Utils.SessionManager.setSession;
import static TroViet.Views.ChuTro.HoaDonPanel.sessionDao;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.YES_OPTION;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author WINDOWS
 */
public class HopDongPanel extends javax.swing.JPanel {

 UserDAO userDAO = new UserDAO();
    DefaultTableModel tblModel;
    String user = SessionManager.getSession().getUser().getHoTen();
    HopDongDAO hopDongDao = new HopDongDAO();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    DecimalFormat tien = new DecimalFormat("###,###,### VND");
    LocalDateTime currentDateTime = LocalDateTime.now();
    DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedDateTime = currentDateTime.format(formatterTime);
    UserEntity userSS = SessionManager.getSession().getUser();
    static SessionDAO sessionDao = new SessionDAO();

    public HopDongPanel() {
        initComponents();
        initTable();
        fillToTableOrder();
        try {
            hopDongDao.ngayKetThucThue();
        } catch (SQLException ex) {
            Logger.getLogger(HopDongPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void initTable() {
        tblModel = (DefaultTableModel) tblHopDong.getModel();
        String[] columns = {"Mã hợp đồng", "Mã phòng trọ", "Mã người dùng", "Ngày BĐ", "Ngày KT", "Tình trạng", "Giá phòng"};
        tblModel.setColumnIdentifiers(columns);
    }

    public void fillToTableOrder() {
        tblModel.setRowCount(0);
        try {
            for (HopDongEntity hopDong : hopDongDao.getAll()) {
                Vector<Object> vec = new Vector<Object>();
                vec.add(hopDong.getId());
                vec.add(hopDong.getIdMaPhongTro());
                vec.add(hopDong.getIdNguoiDung());
                vec.add(hopDong.getNgayBatDauThue());
                vec.add(hopDong.getNgayKetThucThue());
                vec.add(hopDong.getStatus());
                vec.add(formatter.format(hopDong.getGiaPhong()));
                tblModel.addRow(vec);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public long getSelectedId() {
        long selectedRow = tblHopDong.getSelectedRow();
        if (selectedRow < 0) {
            return -1;
        }
        long id = (long) tblHopDong.getValueAt((int) selectedRow, 0);
        return id;
    }

    public void ResetTable() {
        lblMaHopDong.setText("");
        txtMPT.setText("");
        txtMaNguoiDung.setText("");
        jdcNBDT.setDate(null);
        jdcNKTT.setDate(null);
        txtDatCoc.setText("");
        txtGiaInternet.setText("");
        txtGiaDien.setText("");
        txtGiaNuoc.setText("");
        txtGiaPhong.setText("");
        txtGiaNuoc.setText("");
        txtGiaRac.setText("");
        txtStatus.setText("");
        jdcNgayTao.setDate(null);
    }

    public HopDongEntity getModelSP() throws SQLException {
        HopDongEntity hopDong = new HopDongEntity();

        String MPT = txtMPT.getText();
        String MND = txtMaNguoiDung.getText();
        String NBDT = sdf.format(jdcNBDT.getDate());
        String NKKT = sdf.format(jdcNKTT.getDate());
        long maPhongTro = Long.parseLong(MPT);
        long maNguoiDung = Long.parseLong(MND);
        long maHopDong = Long.parseLong(lblMaHopDong.getText());
        float tienDatCoc = Float.parseFloat(txtDatCoc.getText());
        float giaDien = Float.parseFloat(txtGiaDien.getText());
        float giaInternet = Float.parseFloat(txtGiaInternet.getText());
        float giaPhong = Float.parseFloat(txtGiaPhong.getText());
        float giaNuoc = Float.parseFloat(txtGiaNuoc.getText());
        float giaRac = Float.parseFloat(txtGiaRac.getText());
        hopDong.setId(maHopDong);
        hopDong.setIdMaPhongTro(maPhongTro);
        hopDong.setIdNguoiDung(maNguoiDung);
        hopDong.setNgayBatDauThue(NBDT);
        hopDong.setNgayKetThucThue(NKKT);
        hopDong.setTienDatCoc(tienDatCoc);
        hopDong.setGiaDien(giaDien);
        hopDong.setGiaPhong(giaPhong);
        hopDong.setGiaNuoc(giaNuoc);
        hopDong.setGiaRac(giaRac);
        hopDong.setGiaInternet(giaInternet);
        hopDong.setStatus(HopDongEnum.Using);
        hopDong.setTrash(1);
        hopDong.setNgayTao(sdf.format(jdcNgayTao.getDate()));
        hopDong.setNguoiTao(user);
        hopDong.setNgaySua(formattedDateTime);
        hopDong.setNguoiSua(user);
        return hopDong;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtMPT = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtMaNguoiDung = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jdcNBDT = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jdcNKTT = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        txtDatCoc = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtGiaPhong = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtGiaDien = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtGiaNuoc = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtStatus = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jdcNgayTao = new com.toedter.calendar.JDateChooser();
        jLabel12 = new javax.swing.JLabel();
        txtGiaInternet = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        txtGiaRac = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        lbl = new javax.swing.JLabel();
        lblMaHopDong = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnThungRac = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        btnIn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHopDong = new javax.swing.JTable();

        setBackground(new java.awt.Color(40, 46, 62));

        jPanel1.setBackground(new java.awt.Color(46, 56, 86));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 205, 31)), "Tìm kiếm", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 205, 31))); // NOI18N

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiem.setForeground(new java.awt.Color(40, 46, 62));
        txtTimKiem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTimKiemFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTimKiemFocusLost(evt);
            }
        });

        btnTimKiem.setBackground(new java.awt.Color(255, 205, 31));
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTimKiem.setForeground(new java.awt.Color(40, 46, 62));
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TroViet/Icon/magnifier.png"))); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(197, 197, 197)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addComponent(btnTimKiem)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(207, 243, 243));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(40, 46, 62));
        jLabel1.setText("HỢP ĐỒNG");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(40, 46, 62));
        jLabel2.setText("Mã phòng trọ:");

        txtMPT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(40, 46, 62));
        jLabel3.setText("Mã người dùng:");

        txtMaNguoiDung.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(40, 46, 62));
        jLabel4.setText("Ngày BĐ thuê:");

        jdcNBDT.setDateFormatString("dd-MM-yyyy");
        jdcNBDT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(40, 46, 62));
        jLabel5.setText("Ngày KT thuê:");

        jdcNKTT.setDateFormatString("dd-MM-yyyy");
        jdcNKTT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(40, 46, 62));
        jLabel6.setText("Đặt cọc:");

        txtDatCoc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(40, 46, 62));
        jLabel7.setText("Giá phòng:");

        txtGiaPhong.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(40, 46, 62));
        jLabel8.setText("Giá điện:");

        txtGiaDien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(40, 46, 62));
        jLabel9.setText("Giá nước");

        txtGiaNuoc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(40, 46, 62));
        jLabel10.setText("Tình trạng:");

        txtStatus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(40, 46, 62));
        jLabel11.setText("Ngày tạo:");

        jdcNgayTao.setDateFormatString("dd-MM-yyyy");
        jdcNgayTao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(40, 46, 62));
        jLabel12.setText("Giá internet:");

        txtGiaInternet.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(40, 46, 62));
        jLabel13.setText("Giá rác:");

        txtGiaRac.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jPanel4.setBackground(new java.awt.Color(46, 56, 86));

        lbl.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lbl.setForeground(new java.awt.Color(255, 205, 31));
        lbl.setText("MÃ HĐ:");

        lblMaHopDong.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblMaHopDong.setForeground(new java.awt.Color(255, 205, 31));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(lbl)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblMaHopDong)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl)
                    .addComponent(lblMaHopDong))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtMPT, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(18, 18, 18)
                                        .addComponent(jdcNBDT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtDatCoc, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                        .addComponent(txtGiaDien))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtGiaInternet)
                                    .addComponent(txtStatus))))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 45, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(117, 117, 117)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtMaNguoiDung)
                    .addComponent(jdcNKTT, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(txtGiaPhong)
                    .addComponent(txtGiaNuoc)
                    .addComponent(txtGiaRac)
                    .addComponent(jdcNgayTao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(42, 42, 42))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtMPT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txtMaNguoiDung, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jdcNBDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jdcNKTT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtDatCoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(txtGiaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtGiaDien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtGiaNuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtGiaInternet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(txtGiaRac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11))
                    .addComponent(jdcNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(46, 56, 86));

        btnThem.setBackground(new java.awt.Color(255, 205, 31));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnThem.setForeground(new java.awt.Color(40, 46, 62));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TroViet/Icon/add.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(255, 205, 31));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSua.setForeground(new java.awt.Color(40, 46, 62));
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TroViet/Icon/edit.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(255, 205, 31));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(40, 46, 62));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TroViet/Icon/litter.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnThungRac.setBackground(new java.awt.Color(255, 205, 31));
        btnThungRac.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnThungRac.setForeground(new java.awt.Color(40, 46, 62));
        btnThungRac.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TroViet/Icon/delete.png"))); // NOI18N
        btnThungRac.setText("Thùng rác");
        btnThungRac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThungRacActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(255, 205, 31));
        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnReset.setForeground(new java.awt.Color(40, 46, 62));
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TroViet/Icon/return.png"))); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        btnIn.setBackground(new java.awt.Color(255, 205, 31));
        btnIn.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnIn.setForeground(new java.awt.Color(40, 46, 62));
        btnIn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TroViet/Icon/print.png"))); // NOI18N
        btnIn.setText("In");
        btnIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnThungRac))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnIn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnThem, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(btnSua, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnXoa, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(btnThungRac, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnIn, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblHopDong.setBackground(new java.awt.Color(207, 243, 243));
        tblHopDong.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblHopDong.setForeground(new java.awt.Color(40, 46, 62));
        tblHopDong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblHopDong.setGridColor(new java.awt.Color(255, 255, 255));
        tblHopDong.setSelectionBackground(new java.awt.Color(46, 56, 86));
        tblHopDong.setSelectionForeground(new java.awt.Color(255, 205, 31));
        tblHopDong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHopDongMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHopDong);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1047, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        Long selectedId = getSelectedId();
        try {
            if (JOptionPane.showConfirmDialog(null, "Xác nhận xóa?", "Xóa hợp đồng", ERROR_MESSAGE) != YES_OPTION) {
                return;
            }
            hopDongDao.deleteFromRecycle(selectedId);
            fillToTableOrder();
            SessionEntity ss = new SessionEntity();
            ss.setUser(userSS);
            ss.setMessage("Xóa hợp đồng");
            ss.setStartTime(new Timestamp(System.currentTimeMillis()));
            sessionDao.save(ss);
            SessionEntity sss = sessionDao.getLast(userSS.getId());
            setSession(sss);
        } catch (Exception e) {
            System.out.println(e);
        }

    }//GEN-LAST:event_btnXoaActionPerformed
  private void SendMaill(String emailUser){
          try {
             String username = "thanhcmps26487@fpt.edu.vn";
             String password = "galpngwtpigrkmik";
            Properties p = new Properties();
            p.put("mail.smtp.auth", "true");
            p.put("mail.smtp.starttls.enable", "true");
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.put("mail.smtp.port", 587);
            
            String accountName = username;
            String accountPassword = password;
            Session s;
            s = Session.getInstance(p,
                    new javax.mail.Authenticator() {
                      protected PasswordAuthentication getPasswordAuthentication(){
                          return new javax.mail.PasswordAuthentication(accountName,accountPassword);
                      }
                    });
            
            String form = username;
            String to = emailUser;
            String subject = "Bạn đã đăng kí phòng thành công";
            String body = "Cảm ơn quý khách.Quý khách đã đặt phòng phòng thành công";
            Message msg = new MimeMessage(s);
            msg.setFrom(new InternetAddress(form));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            msg.setSubject(subject);
            msg.setText(body);
            
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(body,"text/plain");
               Multipart mp = new MimeMultipart();
               mp.addBodyPart(textPart);
              
               
            
            msg.setContent(mp);
            Transport.send(msg);
            JOptionPane.showMessageDialog(this, "Gửi mail đăng kí hợp đồng thành công");
          
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed

        try {

            String hopDongIdText = lblMaHopDong.getText().trim();
            if (!hopDongIdText.isEmpty()) {
                try {
                    long hopDongId = Long.parseLong(hopDongIdText);
                    if (hopDongDao.isHopDongExists(hopDongId)) {
                        JOptionPane.showMessageDialog(null, "Hợp đồng đã tồn tại.");
                        return;
                    }
                } catch (NumberFormatException | SQLException e) {

                    e.printStackTrace();
                }
            }
            int ret = JOptionPane.showConfirmDialog(null, "Thêm mới hợp đồng?", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.NO_OPTION) {
                return;
            }
            Date ngayBatDau = jdcNBDT.getDate();
            Date ngayKetThuc = jdcNKTT.getDate();

            if (ngayBatDau == null || ngayKetThuc == null) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn ngày bắt đầu và ngày kết thúc.");
                return;
            }

            HopDongEntity hopDong = new HopDongEntity();
            String MPT = txtMPT.getText();
            String MND = txtMaNguoiDung.getText();
            String NBDT = sdf.format(jdcNBDT.getDate());
            String NKKT = sdf.format(jdcNKTT.getDate());

            try {
                if (hopDongDao.isIdMaPhongTroExists(Long.parseLong(MPT))) {
                    JOptionPane.showMessageDialog(null, "Mã hợp đồng đã tồn tại.");
                    return;
                }
                if (hopDongDao.isIdNguoiDungTroExists(Long.parseLong(MND))) {
                    JOptionPane.showMessageDialog(null, "Mã người dùng đã tồn tại.");
                    return;
                }
            } catch (SQLException ex) {
                Logger.getLogger(HopDongPanel.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (MPT.isEmpty() || MND.isEmpty() || NBDT.isEmpty() || NKKT.isEmpty() || txtDatCoc.getText().isEmpty()
                    || txtGiaDien.getText().isEmpty() || txtGiaInternet.getText().isEmpty()
                    || txtGiaPhong.getText().isEmpty() || txtGiaNuoc.getText().isEmpty()
                    || txtGiaRac.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin.");
                return;
            }
            long maPhongTro = Long.parseLong(MPT);
            long maNguoiDung = Long.parseLong(MND);
            float tienDatCoc = Float.parseFloat(txtDatCoc.getText());
            float giaDien = Float.parseFloat(txtGiaDien.getText());
            float giaInternet = Float.parseFloat(txtGiaInternet.getText());
            float giaPhong = Float.parseFloat(txtGiaPhong.getText());
            float giaNuoc = Float.parseFloat(txtGiaNuoc.getText());
            float giaRac = Float.parseFloat(txtGiaRac.getText());
            hopDong.setIdMaPhongTro(maPhongTro);
            hopDong.setIdNguoiDung(maNguoiDung);
            hopDong.setNgayBatDauThue(NBDT);
            hopDong.setNgayKetThucThue(NKKT);
            hopDong.setTienDatCoc(tienDatCoc);
            hopDong.setGiaDien(giaDien);
            hopDong.setGiaPhong(giaPhong);
            hopDong.setGiaNuoc(giaNuoc);
            hopDong.setGiaRac(giaRac);
            hopDong.setGiaInternet(giaInternet);
            hopDong.setStatus(HopDongEnum.Using);
            hopDong.setTrash(1);
            hopDong.setNgayTao(formattedDateTime);
            hopDong.setNguoiTao(user);
            hopDong.setNgaySua("");
            hopDong.setNguoiSua("");
            try {
                hopDongDao.save(hopDong);
                ResetTable();
                JOptionPane.showMessageDialog(null, "Thêm dữ liệu thành công");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Thêm dữ liệu thất bại");
            }
            fillToTableOrder();
            SessionEntity ss = new SessionEntity();
            ss.setUser(userSS);
            ss.setMessage("Thêm hợp đồng");
            ss.setStartTime(new Timestamp(System.currentTimeMillis()));
            sessionDao.save(ss);
            SessionEntity sss = sessionDao.getLast(userSS.getId());
            setSession(sss);
             UserEntity user = userDAO.get(maNguoiDung);
            String emailUser = user.getEmail();
            SendMaill(emailUser);
        } catch (SQLException ex) {
            Logger.getLogger(HopDongPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        try {
            int ret = JOptionPane.showConfirmDialog(null, "Bạn có muốn cập nhập hợp đồng không?", "Thông báo", JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.NO_OPTION) {
                return;
            }
            HopDongEntity sp = getModelSP();
            if (hopDongDao.updateOrderById(sp) > 0) {
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin hợp đồng thành công!");
                fillToTableOrder();
                ResetTable();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhập thông tin hợp đồng thất bại");
            }
            SessionEntity ss = new SessionEntity();
            ss.setUser(userSS);
            ss.setMessage("Cập nhật hợp đồng");
            ss.setStartTime(new Timestamp(System.currentTimeMillis()));
            sessionDao.save(ss);
            SessionEntity sss = sessionDao.getLast(userSS.getId());
            setSession(sss);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnThungRacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThungRacActionPerformed
        // TODO add your handling code here:
        ThungRacHopDong tr = new ThungRacHopDong();
        tr.setVisible(true);
    }//GEN-LAST:event_btnThungRacActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        ResetTable();
        fillToTableOrder();
    }//GEN-LAST:event_btnResetActionPerformed

    private void tblHopDongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHopDongMouseClicked
        // TODO add your handling code here:
        try {
            long selectedId = getSelectedId();
            if (selectedId < 0) {
                JOptionPane.showMessageDialog(null, "Chọn hợp đồng cần edit");
            }
            HopDongEntity hopDong = hopDongDao.get(selectedId);
            if (hopDong == null) {
                JOptionPane.showMessageDialog(null, "Hợp đồng bạn chọn không hợp lệ");
            }
            lblMaHopDong.setText(hopDong.getId() + "");
            txtMPT.setText(hopDong.getIdMaPhongTro() + "");
            txtMaNguoiDung.setText(hopDong.getIdNguoiDung() + "");
            java.util.Date ngayBD = new SimpleDateFormat("yyyy-MM-dd").parse(hopDong.getNgayBatDauThue());
            jdcNBDT.setDate(ngayBD);
            java.util.Date ngayKT = new SimpleDateFormat("yyyy-MM-dd").parse(hopDong.getNgayKetThucThue());
            jdcNKTT.setDate(ngayKT);
            txtDatCoc.setText(hopDong.getTienDatCoc() + "");
            txtGiaInternet.setText(hopDong.getGiaInternet() + "");
            txtGiaDien.setText(hopDong.getGiaDien() + "");
            txtGiaNuoc.setText(hopDong.getGiaNuoc() + "");
            txtGiaPhong.setText(hopDong.getGiaPhong() + "");
            txtGiaNuoc.setText(hopDong.getGiaNuoc() + "");
            txtGiaRac.setText(hopDong.getGiaRac() + "");
            java.util.Date ngayTao = new SimpleDateFormat("yyyy-MM-dd").parse(hopDong.getNgayTao());
            jdcNgayTao.setDate(ngayTao);
            txtStatus.setText(HopDongEnum.getById(hopDong.getStatus().getId()).getName());
        } catch (SQLException ex) {
            Logger.getLogger(HopDongPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(HopDongPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tblHopDongMouseClicked

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        tblModel = (DefaultTableModel) tblHopDong.getModel();
        tblModel.setRowCount(0);
        try {
            for (HopDongEntity hopDong : hopDongDao.searchByKey(String.valueOf(txtTimKiem.getText()))) {
                Vector<Object> vec = new Vector<Object>();
                vec.add(hopDong.getId());
                vec.add(hopDong.getIdMaPhongTro());
                vec.add(hopDong.getIdNguoiDung());
                vec.add(hopDong.getNgayBatDauThue());
                vec.add(hopDong.getNgayKetThucThue());
                vec.add(hopDong.getStatus());
                vec.add(formatter.format(hopDong.getGiaPhong()));
                tblModel.addRow(vec);
            }
            SessionEntity ss = new SessionEntity();
            ss.setUser(userSS);
            ss.setMessage("Tìm kiếm hợp đồng");
            ss.setStartTime(new Timestamp(System.currentTimeMillis()));
            sessionDao.save(ss);
            SessionEntity sss = sessionDao.getLast(userSS.getId());
            setSession(sss);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void txtTimKiemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusGained
        // TODO add your handling code here:
        if (txtTimKiem.getText().equals("Nhập mã hợp đồng để tìm kiếm hợp đồng!")) {
            txtTimKiem.setText("");
        }
    }//GEN-LAST:event_txtTimKiemFocusGained

    private void txtTimKiemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusLost
        // TODO add your handling code here:
        if (txtTimKiem.getText().isEmpty()) {
            txtTimKiem.setText("Nhập mã hợp đồng để tìm kiếm hợp đồng!");
        }
    }//GEN-LAST:event_txtTimKiemFocusLost

    private void btnInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInActionPerformed
        // TODO add your handling code here:
        try {
            String tenChuTro = null, diaChiCT = null, soDTCT = null;
            String tenNguoiThue = null, diaChiNT = null, SoDTNT = null;
            String diaChiTro = null, dienTich = null;
            String maPhong = txtMPT.getText();
            String datCoc = tien.format(Float.parseFloat(txtDatCoc.getText()));
            String giaPhong = tien.format(Float.parseFloat(txtGiaPhong.getText()));
            String giaDien = tien.format(Float.parseFloat(txtGiaDien.getText()));
            String giaNuoc = tien.format(Float.parseFloat(txtGiaNuoc.getText()));
            String giaInternet = tien.format(Float.parseFloat(txtGiaInternet.getText()));
            String giaRac = tien.format(Float.parseFloat(txtGiaRac.getText()));

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String ngayBatDau = sdf.format(jdcNBDT.getDate());
            String ngayKetThuc = sdf.format(jdcNKTT.getDate());
            String ngayTao = sdf.format(jdcNgayTao.getDate());

            long idChuTro = SessionManager.getSession().getUser().getId();
            String query = "select HoTen, DiaChi, DienThoai from NguoiDung where Id = ?;";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setLong(1, idChuTro);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                tenChuTro = rs.getString(1);
                diaChiCT = rs.getString(2);
                soDTCT = rs.getString(3);
            }

            long idNguoiDung = Long.parseLong(txtMaNguoiDung.getText());
            String queryND = "select HoTen, DiaChi, DienThoai from NguoiDung where Id = ?;";
            PreparedStatement psND = con.prepareStatement(queryND);
            psND.setLong(1, idNguoiDung);
            ResultSet rsND = psND.executeQuery();
            while (rsND.next()) {
                tenNguoiThue = rsND.getString(1);
                diaChiNT = rsND.getString(2);
                SoDTNT = rsND.getString(3);
            }

            long phong = Long.parseLong(txtMPT.getText());
            String queryP = "select DiaChi, DienTich from PhongTro where Id = ?;";
            PreparedStatement psP = con.prepareStatement(queryP);
            psP.setLong(1, phong);
            ResultSet rsP = psP.executeQuery();
            while (rsP.next()) {
                diaChiTro = rsP.getString(1);
                dienTich = rsP.getString(2);
            }

            BaseFont bf = BaseFont.createFont("RobotoSlab-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            com.itextpdf.text.Font font_Dam = new com.itextpdf.text.Font(bf, 14, com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
            com.itextpdf.text.Font font_BT = new com.itextpdf.text.Font(bf, 12, com.itextpdf.text.Font.NORMAL, BaseColor.BLACK);
            com.itextpdf.text.Font font_To = new com.itextpdf.text.Font(bf, 18, com.itextpdf.text.Font.BOLD, BaseColor.BLACK);

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("In hợp đồng");

            // Tạo tên file mặc định
//            String[] parts = ngayBD.split("/"); // Cắt chuỗi thành mảng các phần tử dựa trên dấu "/"
//            String thang = parts[1]; // Lấy phần tử thứ hai trong mảng là tháng
            String tenFile = "HopDong_Phong" + maPhong + ".pdf";
            System.out.println(tenFile);
            String defaultFileName = tenFile;

            // Đặt tên file mặc định cho JFileChooser
            fileChooser.setSelectedFile(new File(defaultFileName));

            // Chỉ chấp nhận file có phần mở rộng là ".pdf"
            FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF files", "pdf");
            fileChooser.setFileFilter(filter);

            int returnValue = fileChooser.showSaveDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                try {
                    Document document = new Document(PageSize.A4, 50, 50,50,50) {
                    };

                    File fileToSave = fileChooser.getSelectedFile();
                    String fileName = fileToSave.getName();
                    if (!fileName.endsWith(".pdf")) {
                        // Gắn phần mở rộng ".pdf" vào tên file nếu chưa có
                        fileToSave = new File(fileToSave.getAbsolutePath() + ".pdf");
                    }
                    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
                    document.open();

                    Paragraph tieuDe1 = new Paragraph("CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM", font_BT);
                    Paragraph tieuDe2 = new Paragraph("Độc lập - Tự do - Hạnh phúc", font_BT);

                    Paragraph tieuDe3 = new Paragraph("HỢP ĐỒNG CHO THUÊ NHÀ", font_To);

                    Paragraph tieuDe4 = new Paragraph("BÊN CHO THUÊ - (BÊN A):", font_Dam);
                    Paragraph tenBenA = new Paragraph("        Chủ trọ: " + tenChuTro, font_BT);
                    Paragraph diaChiBenA = new Paragraph("        Địa chỉ: " + diaChiCT, font_BT);
                    Paragraph dtBenA = new Paragraph("        Số điện thoại: " + soDTCT, font_BT);

                    Paragraph tieuDe5 = new Paragraph("BÊN THUÊ - (BÊN B):", font_Dam);
                    Paragraph tenBenB = new Paragraph("        Ông (Bà): " + tenNguoiThue, font_BT);
//                    Paragraph diaChiBenB = new Paragraph("        Địa chỉ: " + diaChiNT, font_BT);
//                    Paragraph dtBenB = new Paragraph("        Số điện thoại: " + SoDTNT, font_BT);

                    Paragraph tieuDe6 = new Paragraph("Hai bên cùng thỏa thuận giao kết hợp đồng với những nội dung như sau:", font_Dam);
                    Paragraph tieuDe7 = new Paragraph("ĐIỀU 1: KHÔNG GIAN CHO THUÊ VÀ CHI PHÍ THUÊ", font_Dam);
                    Paragraph phongThue = new Paragraph(" - Bên A đồng ý cho Bên B thuê: Phòng số " + maPhong, font_BT);
                    Paragraph diaChi = new Paragraph(" - Trong tòa nhà số: " + diaChiTro + " - Diện tích: " + dienTich + "m2", font_BT);
                    Paragraph thoiGian = new Paragraph(" - Thời hạn hợp đồng: Từ " + ngayBatDau + " đến " + ngayKetThuc, font_BT);
                    Paragraph thoiGianTao = new Paragraph(" - Ngày tạo hợp đồng:  " + ngayTao, font_BT);
                    Paragraph tienThue = new Paragraph(" - Tiền thuê phòng hàng tháng: " + giaPhong, font_BT);
                    Paragraph chiPhi = new Paragraph(" - Các chi phí dịch vụ kèm theo: ", font_BT);
                    PdfPTable tbl = new PdfPTable(2);
                    tbl.addCell(new Phrase("Tên dịch vụ", font_BT));
                    tbl.addCell(new Phrase("Đơn giá", font_BT));
                    tbl.addCell(new Phrase("Giá điện", font_BT));
                    tbl.addCell(new Phrase(giaDien + "/kW", font_BT));
                    tbl.addCell(new Phrase("Giá nước", font_BT));
                    tbl.addCell(new Phrase(giaNuoc + "/Khối", font_BT));
                    tbl.addCell(new Phrase("Giá internet", font_BT));
                    tbl.addCell(new Phrase(giaInternet + "/Tháng", font_BT));
                    tbl.addCell(new Phrase("Giá rác", font_BT));
                    tbl.addCell(new Phrase(giaRac + "/Tháng", font_BT));

                    Paragraph tieuDe8 = new Paragraph("ĐIỀU 2: TIỀN ĐẶT CỌC & THANH TOÁN", font_Dam);
                    Paragraph coc = new Paragraph(" - Bên B đặt cọc cho Bên A số tiền là: " + giaPhong, font_BT);
                    Paragraph noiQuy1 = new Paragraph(" - Số tiền đặt cọc này sẽ được hoàn lại khi kết thúc hợp đồng và"
                            + " khi Bên B đã thanh toán đầy đủ tiền phòng và các chi phí dịch vụ kèm theo mà Bên B đã sử dụng."
                            + " Trường hợp Bên B làm hư hại cơ sở vật chất thì số tiền này xem như tiền bồi thường, sửa chữa cho Bên A.", font_BT);
                    Paragraph noiQuy2 = new Paragraph(" - Bên B phải thanh toán tiền phòng và các chi phí dịch vụ kèm theo cho Bên A"
                            + " bằng tiền mặt hoặc chuyển khoản từ ngày 05 đến ngày 10 hàng tháng. Bên A sẽ thông báo tiền phòng và "
                            + " chi phí dịch vụ kèm theo từ ngày 01 đến ngày 04 hàng tháng.", font_BT);
                    Paragraph noiQuy3 = new Paragraph(" - Nếu Bên B không thanh toán đúng hạn (Chậm nhất là ngày 10 mỗi tháng) mà không thông báo, "
                            + " Bên A có quyền chấm dứt hợp đồng thuê nhà đối với Bên B, lấy lại phòng và không hoàn trả cọc.", font_BT);

                    Paragraph tieuDe9 = new Paragraph("ĐIỀU 3: TRÁCH NHIỆM CỦA CÁC BÊN", font_Dam);
                    Paragraph trachNhiem = new Paragraph("   * Trách nhiệm của bên A:\n"
                            + " - Tạo mọi điều kiện thuận lợi để bên B thực hiện theo hợp đồng.\n"
                            + " - Cung cấp nguồn điện, nước, wifi cho bên B sử dụng.\n"
                            + "   * Trách nhiệm của bên B:\n"
                            + " - Thanh toán đầy đủ các khoản tiền theo đúng thỏa thuận.\n"
                            + " - Bảo quản các trang thiết bị và cơ sở vật chất của bên A trang bị cho ban đầu (làm hỏng phải sửa, mất phải đền).\n"
                            + " - Không được tự ý sửa chữa, cải tạo cơ sở vật chất khi chưa được sự đồng ý của bên A.\n"
                            + " - Giữ gìn vệ sinh trong và ngoài khuôn viên của phòng trọ.\n"
                            + " - Bên B phải chấp hành mọi quy định của pháp luật Nhà nước và quy định của địa phương.\n"
                            + " - Nếu bên B cho khách ở qua đêm thì phải báo và được sự đồng ý của chủ nhà đồng thời phải chịu trách nhiệm về các hành vi vi phạm pháp luật của khách trong thời gian ở lại.", font_BT);

                    Paragraph tieuDe10 = new Paragraph("ĐIỀU 4: TRÁCH NHIỆM CHUNG", font_Dam);
                    Paragraph trachNhiemChung = new Paragraph(" - Hai bên phải tạo điều kiện cho nhau thực hiện hợp đồng.\n"
                            + " - Trong thời gian hợp đồng còn hiệu lực nếu bên nào vi phạm các điều khoản đã thỏa thuận thì bên còn lại có quyền đơn phương chấm dứt hợp đồng; nếu sự vi phạm hợp đồng đó gây tổn thất cho bên bị vi phạm hợp đồng thì bên vi phạm hợp đồng phải bồi thường thiệt hại.\n"
                            + " - Một trong hai bên muốn chấm dứt hợp đồng trước thời hạn thì phải báo trước cho bên kia ít nhất 30 ngày và hai bên phải có sự thống nhất.\n"
                            + " - Bên A phải trả lại tiền đặt cọc cho bên B.\n"
                            + " - Bên nào vi phạm điều khoản chung thì phải chịu trách nhiệm trước pháp luật.\n"
                            + " - Hợp đồng được lập thành 02 bản có giá trị pháp lý như nhau, mỗi bên giữ một bản.", font_BT);
                    
                    
                    PdfPTable table = new PdfPTable(2);
                    table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                    
                    PdfPCell cell1 = new PdfPCell(new com.itextpdf.text.Paragraph("Đại diện Bên B",font_Dam));
                    cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell1.setBorder(0);
                    table.addCell(cell1);

                    PdfPCell cell2 = new PdfPCell(new com.itextpdf.text.Paragraph("Đại diện Bên A", font_Dam));
                    cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell2.setBorder(0);
                    table.addCell(cell2);
                    
//                    table.addCell(new Phrase("Đại diện Bên B", font_BT));
//                    table.addCell(new Phrase("Đại diện Bên A", font_BT));
                    
                    
                    
                    tieuDe1.setAlignment(Element.ALIGN_CENTER);
                    tieuDe2.setAlignment(Element.ALIGN_CENTER);
                    tieuDe3.setAlignment(Element.ALIGN_CENTER);
                    tbl.setSpacingBefore(8);
                    tbl.setSpacingAfter(5);
                    table.setSpacingBefore(10);
                    table.setSpacingAfter(10);
                    
                    
//
                    
//                    int numberOfPages = writer.getPageNumber();
//                    Paragraph numberP = new Paragraph(numberOfPages + "", font_BT);
//                    numberP.setAlignment(Element.ALIGN_CENTER);
                    
                    // Thêm số trang vào tài liệu
//            int numberOfPages = writer.getPageNumber();
//            
//            Paragraph pageNumber = new Paragraph("Số trang: " + numberOfPages, font_BT);
//            pageNumber.setAlignment(Element.ALIGN_RIGHT);
                    
//                    numberP.setSpacingAfter(10); // Đặt khoảng cách dưới của đoạn văn bản
//
//                    // Lấy kích thước trang PDF
//                    Rectangle pageSize = document.getPageSize();
//                    float pageWidth = pageSize.getWidth();

//                    // Tính toán thụt trái và thụt phải để đưa đoạn văn bản vào giữa cuối trang
//                    float indentLeft = (pageWidth - numberP.getWidth()) / 2;
//                    float indentRight = (pageWidth - numberP.getWidth()) / 2;

//                    numberP.setIndentationLeft(indentLeft); // Đặt thụt trái của đoạn văn bản
//                    numberP.setIndentationRight(indentRight); // Đặt thụt phải của đoạn văn bản

                    

                    document.add(tieuDe1);
                    document.add(tieuDe2);
                    document.add(tieuDe3);
                    document.add(tieuDe4);
                    document.add(tenBenA);
                    document.add(diaChiBenA);
                    document.add(dtBenA);
                    document.add(tieuDe5);
                    document.add(tenBenB);
//                    document.add(diaChiBenB);
//                    document.add(dtBenB);
                    document.add(tieuDe6);
                    document.add(tieuDe7);
                    document.add(phongThue);
                    document.add(diaChi);
                    document.add(thoiGian);
                    document.add(thoiGianTao);
                    document.add(tienThue);
                    document.add(tbl);
                    document.add(tieuDe8);
                    document.add(coc);
                    document.add(noiQuy1);
                    document.add(noiQuy2);
                    document.add(noiQuy3);
                    document.add(tieuDe9);
                    document.add(trachNhiem);
                    document.add(tieuDe10);
                    document.add(trachNhiemChung);
                    document.add(table);

//                    document.add(pageNumber);
                    
                    
                    writer.flush();
                    document.close();
                    writer.close();

                    JOptionPane.showMessageDialog(null, "Xuất file PDF thành công");
                } catch (DocumentException | FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnInActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnIn;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnThungRac;
    private javax.swing.JButton btnTimKiem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdcNBDT;
    private com.toedter.calendar.JDateChooser jdcNKTT;
    private com.toedter.calendar.JDateChooser jdcNgayTao;
    private javax.swing.JLabel lbl;
    private javax.swing.JLabel lblMaHopDong;
    private javax.swing.JTable tblHopDong;
    private javax.swing.JTextField txtDatCoc;
    private javax.swing.JTextField txtGiaDien;
    private javax.swing.JTextField txtGiaInternet;
    private javax.swing.JTextField txtGiaNuoc;
    private javax.swing.JTextField txtGiaPhong;
    private javax.swing.JTextField txtGiaRac;
    private javax.swing.JTextField txtMPT;
    private javax.swing.JTextField txtMaNguoiDung;
    private javax.swing.JTextField txtStatus;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables

}
