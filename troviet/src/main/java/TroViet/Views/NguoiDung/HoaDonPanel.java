/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package TroViet.Views.NguoiDung;

import static TroViet.Connect.SQL_Connection.con;
import TroViet.DAO.HoaDonEnum;
import TroViet.DAO.HopDongDAO;
import TroViet.DAO.HopDongEnum;
import TroViet.Model.HopDongEntity;
import TroViet.Model.UserEntity;
import TroViet.Utils.SessionManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author WINDOWS
 */
public class HoaDonPanel extends javax.swing.JPanel {

    DefaultTableModel tblModel;
    UserEntity session = SessionManager.getSession().getUser();
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    LocalDateTime currentDateTime = LocalDateTime.now();
    DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedDateTime = currentDateTime.format(formatterTime);

    public HoaDonPanel() throws SQLException {
        initComponents();
        initTable();
        fillHoadon();
    }

    public void initTable() {
        tblModel = (DefaultTableModel) tblHoaDon.getModel();
        String[] columns = {"Mã hóa đơn" , "Mã hợp đồng", "Ngày BĐ", "Ngày KT", "Tổng cộng", "Tình trạng"};
        tblModel.setColumnIdentifiers(columns);
        tblHoaDon.setModel(tblModel);
    }
    
    public void fillHoadon() throws SQLException {
        tblModel.setRowCount(0);
        String query = "select hdon.Id, hdong.Id, hdon.NgayBatDau, hdon.NgayKetThuc,\n"
                + "hdon.SoDienMoi, hdon.SoDienCu, \n"
                + "((hdon.SoDienMoi - hdon.SoDienCu)*hdong.GiaDien) as 'Tien dien', \n"
                + "hdon.SoNuocMoi, hdon.SoNuocCu,\n"
                + "((hdon.SoNuocMoi - hdon.SoNuocCu)*hdong.GiaNuoc) as 'Tien nuoc', \n"
                + "(hdong.GiaInternet), (hdong.GiaRac), (hdong.GiaPhong), (hdon.KhauTru), (hdon.TienNo),\n"
                + "( ((hdon.SoDienMoi - hdon.SoDienCu)*hdong.GiaDien) + ((hdon.SoNuocMoi - hdon.SoNuocCu)*hdong.GiaNuoc) +\n"
                + "(hdong.GiaInternet) + (hdong.GiaRac) + (hdong.GiaPhong) + (hdon.TienNo) - (hdon.KhauTru)) as 'Tong cong', hdon.Status\n"
                + "from HoaDon hdon join HopDong hdong on hdon.IdHopDong = hdong.Id join NguoiDung nd on hdong.IdNguoiDung = nd.Id\n"
                + "where nd.Id =?;";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setLong(1, session.getId());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Vector<Object> vec = new Vector<Object>();
            vec.add(rs.getLong(1)); //id
            vec.add(rs.getLong(2)); //id hợp đồng
            vec.add(rs.getDate(3)); // ngày bd
            vec.add(rs.getDate(4)); // ngày kt
//            vec.add(formatter.format(rs.getFloat(7)));  //tiền điện
//            vec.add(formatter.format(rs.getFloat(10))); //tiền nước
//            vec.add(formatter.format(rs.getFloat(11))); //internet
//            vec.add(formatter.format(rs.getFloat(12))); //tiền rác
//            vec.add(formatter.format(rs.getFloat(13))); //tiền phòng
//            vec.add(formatter.format(rs.getFloat(14))); //khấu trừ
//            vec.add(formatter.format(rs.getFloat(15))); //nợ
            vec.add(formatter.format(rs.getFloat(16))); //tổng cộng
            vec.add(HoaDonEnum.getById(rs.getString(17))); //status
            tblModel.addRow(vec);
        }
    }
    
    public long getSelectedID() {
        long selectedRow = tblHoaDon.getSelectedRow();
        if (selectedRow < 0) {
            return -1;
        }
        long id = (long) tblHoaDon.getValueAt((int) selectedRow, 0);
        return id;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jdcNgayBD = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jdcNgayKT = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jdcNgayTao = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        lblNguoiTao = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblIdHoaDon = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblIdHopDong = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        txtSoDienMoi = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtSoDiencu = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtTienDien = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtSoNuocMoi = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtSoNuocCu = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtTienNuoc = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtTienInternet = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtTienRac = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtTienPhong = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel22 = new javax.swing.JLabel();
        txtNo = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtKhauTru = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel24 = new javax.swing.JLabel();
        lblTongCong = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        lblStatus = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();
        btnThanhToan = new javax.swing.JButton();

        setBackground(new java.awt.Color(40, 46, 62));

        jPanel1.setBackground(new java.awt.Color(46, 56, 86));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("PHIẾU HÓA ĐƠN");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TroViet/Icon/house (3).png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("TRỌ VIỆT");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Ngày bắt đầu:");

        jdcNgayBD.setBackground(new java.awt.Color(255, 255, 255));
        jdcNgayBD.setForeground(new java.awt.Color(40, 46, 62));
        jdcNgayBD.setDateFormatString("dd-MM-yyyy");
        jdcNgayBD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Ngày kết thúc:");

        jdcNgayKT.setBackground(new java.awt.Color(255, 255, 255));
        jdcNgayKT.setForeground(new java.awt.Color(40, 46, 62));
        jdcNgayKT.setDateFormatString("dd-MM-yyyy");
        jdcNgayKT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Ngày tạo:");

        jdcNgayTao.setBackground(new java.awt.Color(255, 255, 255));
        jdcNgayTao.setForeground(new java.awt.Color(40, 46, 62));
        jdcNgayTao.setDateFormatString("dd-MM-yyyy");
        jdcNgayTao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Người tạo:");

        lblNguoiTao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNguoiTao.setForeground(new java.awt.Color(255, 205, 31));
        lblNguoiTao.setText("Họ tên");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Mã hóa đơn:");

        lblIdHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblIdHoaDon.setForeground(new java.awt.Color(255, 205, 31));
        lblIdHoaDon.setText("ID hóa đơn");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Mã hợp đồng:");

        lblIdHopDong.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblIdHopDong.setForeground(new java.awt.Color(255, 205, 31));
        lblIdHopDong.setText("ID hợp đồng");

        jSeparator1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Số điện mới:");

        txtSoDienMoi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSoDienMoi.setForeground(new java.awt.Color(40, 46, 62));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Số điện cũ:");

        txtSoDiencu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSoDiencu.setForeground(new java.awt.Color(40, 46, 62));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Tiền điện:");

        txtTienDien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTienDien.setForeground(new java.awt.Color(40, 46, 62));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Số nước mới:");

        txtSoNuocMoi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSoNuocMoi.setForeground(new java.awt.Color(40, 46, 62));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Số nước cũ:");

        txtSoNuocCu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSoNuocCu.setForeground(new java.awt.Color(40, 46, 62));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Tiền nước:");

        txtTienNuoc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTienNuoc.setForeground(new java.awt.Color(40, 46, 62));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Tiền internet:");

        txtTienInternet.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTienInternet.setForeground(new java.awt.Color(40, 46, 62));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Tiền rác:");

        txtTienRac.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTienRac.setForeground(new java.awt.Color(40, 46, 62));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Tiền phòng:");

        txtTienPhong.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTienPhong.setForeground(new java.awt.Color(40, 46, 62));

        jSeparator2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Nợ:");

        txtNo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNo.setForeground(new java.awt.Color(40, 46, 62));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Khấu trừ:");

        txtKhauTru.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtKhauTru.setForeground(new java.awt.Color(40, 46, 62));

        jSeparator3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Tổng cộng:");

        lblTongCong.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTongCong.setForeground(new java.awt.Color(255, 205, 31));
        lblTongCong.setText("x xxx xxx");

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("Tình trạng:");

        lblStatus.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblStatus.setForeground(new java.awt.Color(255, 205, 31));
        lblStatus.setText("Chưa/Đã thanh toán");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(55, 55, 55)
                .addComponent(jLabel3)
                .addGap(30, 30, 30))
            .addComponent(jSeparator1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel6)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jdcNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel4)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jdcNgayBD, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel9)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblIdHoaDon)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblIdHopDong))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel7))
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jdcNgayKT, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(16, 16, 16)
                                                .addComponent(lblNguoiTao))))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addGap(30, 30, 30)
                                .addComponent(lblStatus))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTienDien, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(18, 18, 18)
                                .addComponent(txtSoDienMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtSoNuocMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTienNuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTienInternet, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTienRac, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtTienPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtSoDiencu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtSoNuocCu, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(20, Short.MAX_VALUE))
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jSeparator3)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(36, 36, 36))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addGap(71, 71, 71)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtKhauTru, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel24)
                        .addGap(18, 18, 18)
                        .addComponent(lblTongCong)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel4)
                        .addComponent(jdcNgayBD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(jdcNgayKT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jdcNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(lblNguoiTao)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(lblIdHoaDon))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(lblIdHopDong)))
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStatus)
                    .addComponent(jLabel26))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtSoDienMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(txtSoDiencu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtTienDien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtSoNuocMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(txtSoNuocCu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtTienNuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtTienInternet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtTienRac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtTienPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(txtNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(txtKhauTru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(lblTongCong))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        tblHoaDon.setBackground(new java.awt.Color(207, 243, 243));
        tblHoaDon.setForeground(new java.awt.Color(40, 46, 62));
        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
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
        tblHoaDon.setGridColor(new java.awt.Color(255, 255, 255));
        tblHoaDon.setSelectionBackground(new java.awt.Color(40, 46, 62));
        tblHoaDon.setSelectionForeground(new java.awt.Color(255, 205, 31));
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHoaDon);

        btnThanhToan.setBackground(new java.awt.Color(255, 205, 31));
        btnThanhToan.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnThanhToan.setForeground(new java.awt.Color(40, 46, 62));
        btnThanhToan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TroViet/Icon/operation.png"))); // NOI18N
        btnThanhToan.setText("Thanh toán");
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(252, 252, 252)
                        .addComponent(btnThanhToan))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 612, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(55, 55, 55)
                        .addComponent(btnThanhToan)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        // TODO add your handling code here:
        try {
            long selectedId = getSelectedID();
            if (selectedId < 0) {
                JOptionPane.showMessageDialog(null, "Chọn hóa đơn cần chọn!");
            }
            String query = "select hdon.Id, hdong.Id, hdon.NgayBatDau, hdon.NgayKetThuc, hdon.NgayTao, hdon.NguoiTao,\n"
                    + "hdon.SoDienMoi, hdon.SoDienCu, \n"
                    + "((hdon.SoDienMoi - hdon.SoDienCu)*hdong.GiaDien) as 'Tien dien', \n"
                    + "hdon.SoNuocMoi, hdon.SoNuocCu,\n"
                    + "((hdon.SoNuocMoi - hdon.SoNuocCu)*hdong.GiaNuoc) as 'Tien nuoc', \n"
                    + "(hdong.GiaInternet), (hdong.GiaRac), (hdong.GiaPhong), (hdon.KhauTru), (hdon.TienNo),\n"
                    + "( ((hdon.SoDienMoi - hdon.SoDienCu)*hdong.GiaDien) + ((hdon.SoNuocMoi - hdon.SoNuocCu)*hdong.GiaNuoc) +\n"
                    + "(hdong.GiaInternet) + (hdong.GiaRac) + (hdong.GiaPhong) + (hdon.TienNo) - (hdon.KhauTru)) as 'Tong cong', hdon.Status\n"
                    + "from HoaDon hdon join HopDong hdong on hdon.IdHopDong = hdong.Id join NguoiDung nd on hdong.IdNguoiDung = nd.Id\n"
                    + "where nd.Id =? and hdon.Id=?;";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setLong(1, session.getId());
            ps.setLong(2, selectedId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                jdcNgayBD.setDate(rs.getDate(3));
                jdcNgayKT.setDate(rs.getDate(4));
                jdcNgayTao.setDate(rs.getDate(5));
                lblNguoiTao.setText(rs.getString(6));
                lblIdHoaDon.setText(rs.getString(1));
                lblIdHopDong.setText(rs.getString(2));
                if(rs.getInt(19) == 1) {
                    lblStatus.setText("Đã thanh toán");
                } else {
                    lblStatus.setText("Chưa thanh toán");
                }
                
                txtSoDiencu.setText(rs.getFloat(8)+"");
                txtSoDienMoi.setText(rs.getFloat(7)+"");
                txtTienDien.setText(rs.getFloat(9)+"");
                txtSoNuocCu.setText(rs.getFloat(11)+"");
                txtSoNuocMoi.setText(rs.getFloat(10)+"");
                txtTienNuoc.setText(rs.getFloat(12)+"");
                txtTienInternet.setText(rs.getFloat(13)+"");
                txtTienRac.setText(rs.getFloat(14)+"");
                txtTienPhong.setText(rs.getFloat(15)+"");
                txtKhauTru.setText(rs.getFloat(16)+"");
                txtNo.setText(rs.getFloat(17)+"");
                lblTongCong.setText(rs.getFloat(18)+"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, "Khách thuê trọ thanh toán tiền vui lòng chuyển khoản qua số tài khoản sau: \n"
                                                                + "Ngân hàng: Vietcombank\n Số tài khoản: 0123456789 \n Tên: CHU TRO\n"
                                                                + "Nội dung: MaHoaDon_MaHopDong_TienNhaThangXX\n"
                                                                + "Xin cám ơn!");
    }//GEN-LAST:event_btnThanhToanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private com.toedter.calendar.JDateChooser jdcNgayBD;
    private com.toedter.calendar.JDateChooser jdcNgayKT;
    private com.toedter.calendar.JDateChooser jdcNgayTao;
    private javax.swing.JLabel lblIdHoaDon;
    private javax.swing.JLabel lblIdHopDong;
    private javax.swing.JLabel lblNguoiTao;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblTongCong;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTextField txtKhauTru;
    private javax.swing.JTextField txtNo;
    private javax.swing.JTextField txtSoDienMoi;
    private javax.swing.JTextField txtSoDiencu;
    private javax.swing.JTextField txtSoNuocCu;
    private javax.swing.JTextField txtSoNuocMoi;
    private javax.swing.JTextField txtTienDien;
    private javax.swing.JTextField txtTienInternet;
    private javax.swing.JTextField txtTienNuoc;
    private javax.swing.JTextField txtTienPhong;
    private javax.swing.JTextField txtTienRac;
    // End of variables declaration//GEN-END:variables

}
