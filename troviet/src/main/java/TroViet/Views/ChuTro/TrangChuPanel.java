/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package TroViet.Views.ChuTro;

import static TroViet.Connect.SQL_Connection.con;
import TroViet.DAO.PhongTroDAO;
import TroViet.Model.PhongTro;
import TroViet.Views.Login.NguoiThue1;
import java.awt.Window;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ui.UIUtils;

/**
 *
 * @author WINDOWS
 */
public class TrangChuPanel extends javax.swing.JPanel {

    DecimalFormat decimalFormat = new DecimalFormat("###,### VND");
    DefaultTableModel tblModel;
    PhongTroDAO phongTroDAO = new PhongTroDAO();
    
    public TrangChuPanel() throws SQLException {
        initComponents();
        initTable();
        fillTableTrangChu();
       
    }
    
    public void initTable() throws SQLException {
        tblModel = (DefaultTableModel) tblTrangChu.getModel();
        String[] columns = {"Tên phòng", "Diện tích", "Giá phòng", "Địa chỉ", "Mô tả"};
        tblModel.setColumnIdentifiers(columns);
         Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH) + 1; // +1 để đưa về định dạng tháng từ 1 đến 12
        lblThangTienChi.setText(String.valueOf(currentMonth));
        lblThangDoanhSo.setText(String.valueOf(currentMonth));
//        lblThangTienThuThang.setText(String.valueOf(currentMonth));
        int soKhachTroDangThue = getSoKhachTroDangThueFromDatabase();
        lblSoKhach.setText(soKhachTroDangThue + "");

        int SoHopDongSapHetHan = getSoHopDongSapHetHanFromDatabase();
        lblHopDong.setText(SoHopDongSapHetHan + "");

        int SoHoaDon = getHoaDon();
        lblHopDong.setText(SoHoaDon + "");

        double TongTienDatCoc = getTienDatCoc();
        String TongTienDatCocVND = decimalFormat.format(TongTienDatCoc);
        lblTienThu.setText(TongTienDatCocVND + "");

        double TienDoanhThu = getTienDoanhThu();
        String TienDoanhThuVND = decimalFormat.format(TienDoanhThu);
        lblDoanhSo.setText(TienDoanhThuVND);

        double TienChi = getTienChi();
        String TienChiVND = decimalFormat.format(TienChi);
        lblTienChi.setText(TienChiVND);

        int hoadon = getHD();
        lblHoaDon.setText(hoadon + "");

         
    }
    
    public void fillTableTrangChu() throws SQLException {
        tblModel.setRowCount(0);
        String query = "select pt.TenPhong, pt.DienTich, pt.GiaPhong, pt.DiaChi, pt.MoTa, dg.DanhGia, dg.NoiDung\n" +
                            "from PhongTro pt join DanhGia dg on pt.Id = dg.IdMaPhongTro\n" +
                            "where pt.Status=0;";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Vector<Object> vec = new Vector<Object>();
            vec.add(rs.getString(1));
            vec.add(rs.getDouble(2)+"");
            vec.add(rs.getDouble(3)+"");
            vec.add(rs.getString(4));
            vec.add(rs.getString(5));
            vec.add(rs.getInt(6)+"");
            vec.add(rs.getString(7));
            tblModel.addRow(vec);
        }
    }
     public int  getSoKhachTroDangThueFromDatabase() throws SQLException {
        int soKhachTroDangThue = 0;
        String query = "SELECT COUNT(*) AS SoKhachTroDangThue FROM NguoiDung where VaiTro='Tenants';";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        if  (rs.next()) {
           soKhachTroDangThue = rs.getInt("SoKhachTroDangThue");
        }
        return soKhachTroDangThue;
    }
     
     public int  getSoHopDongSapHetHanFromDatabase() throws SQLException {
        int SoHopDongSapHetHan = 0;
          LocalDate currentDate = LocalDate.now();
        String query = "SELECT COUNT(*) AS SoHopDongSapHetHan FROM HopDong WHERE NgayKetThucThue >= ? AND NgayKetThucThue <= ?";
        PreparedStatement ps = con.prepareStatement(query);
          ps.setObject(1, currentDate);
            LocalDate endDate = currentDate.plusDays(7);
            ps.setObject(2, endDate);
        ResultSet rs = ps.executeQuery();
        if  (rs.next()) {
           SoHopDongSapHetHan = rs.getInt("SoHopDongSapHetHan");
        }
        return SoHopDongSapHetHan;
    }
    public int getHoaDon() throws SQLException {
        int SoHoaDon = 0;

        String query = "SELECT COUNT(*) AS SoHoaDon FROM HoaDon where status = 0 and Trash != 0";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            SoHoaDon = rs.getInt("SoHoaDon");
        }
        return SoHoaDon;
    }

    public double getTienDatCoc() throws SQLException {
        double TongTienDatCoc = 0;

        String query = "SELECT SUM(TienDatCoc) AS TongTienDatCoc FROM HopDong where status = 1 and Trash != 0";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            TongTienDatCoc = rs.getInt("TongTienDatCoc");
        }
        return TongTienDatCoc;
    }

    public double getTienDoanhThu() throws SQLException {
        double TienDoanhThu = 0;

        String query = "SELECT SUM(TongCong) AS TienDoanhThu FROM HoaDon where status = 1 and Trash != 0";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            TienDoanhThu = rs.getInt("TienDoanhThu");
        }
        return TienDoanhThu;
    }
    
    public double getTienChi() throws SQLException {
        double tienChi = 0;
        String query = "select sum( ((hdon.SoDienMoi - hdon.SoDienCu)*hdong.GiaDien) + ((hdon.SoNuocMoi - hdon.SoNuocCu)*hdong.GiaNuoc) + hdong.GiaInternet + hdong.GiaRac)\n"
                + "from HoaDon hdon join HopDong hdong on hdon.IdHopDong = hdong.Id where hdon.Trash!=0 and hdon.status = 1;";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            tienChi = rs.getInt(1);
        }
        return tienChi;
    }
    
    public int getHD() throws SQLException {
        int hoadon = 0;
        String query = "select count(*) from HoaDon where Status=0;";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            hoadon = rs.getInt(1);
        }
        return hoadon;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnTrangChu = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblThangDoanhSo = new javax.swing.JLabel();
        lblDoanhSo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        lblTienThu = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblThangTienChi = new javax.swing.JLabel();
        lblTienChi = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        lblSoKhach = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        lblHopDong = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        lblHoaDon = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTrangChu = new javax.swing.JTable();

        jpnTrangChu.setBackground(new java.awt.Color(40, 46, 62));
        jpnTrangChu.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 205, 31), 2, true));

        jPanel1.setBackground(new java.awt.Color(46, 56, 86));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 205, 31), 2));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("TỔNG DOANH THU");

        lblThangDoanhSo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblThangDoanhSo.setForeground(new java.awt.Color(255, 205, 31));
        lblThangDoanhSo.setText("01");

        lblDoanhSo.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblDoanhSo.setForeground(new java.awt.Color(255, 205, 31));
        lblDoanhSo.setText("100,000,000 VND");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblThangDoanhSo))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(lblDoanhSo)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblThangDoanhSo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDoanhSo)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(46, 56, 86));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 205, 31), 2));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("TIỀN ĐẶT CỌC");

        lblTienThu.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTienThu.setForeground(new java.awt.Color(255, 205, 31));
        lblTienThu.setText("100,000,000 VND");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(lblTienThu)
                .addGap(21, 21, 21))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTienThu)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(46, 56, 86));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 205, 31), 2));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("TIỀN CHI THÁNG");

        lblThangTienChi.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblThangTienChi.setForeground(new java.awt.Color(255, 205, 31));
        lblThangTienChi.setText("01");

        lblTienChi.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTienChi.setForeground(new java.awt.Color(255, 205, 31));
        lblTienChi.setText("100,000,000 VND");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(lblTienChi)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblThangTienChi)
                .addGap(38, 38, 38))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblThangTienChi))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTienChi)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(46, 56, 86));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 205, 31), 2));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("SỐ KHÁCH TRỌ ĐANG THUÊ");

        lblSoKhach.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblSoKhach.setForeground(new java.awt.Color(255, 205, 31));
        lblSoKhach.setText("XX");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(90, 90, 90)
                .addComponent(lblSoKhach)
                .addContainerGap(107, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(15, 15, 15))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSoKhach)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(46, 56, 86));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 205, 31), 2));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("SỐ HỢP ĐỒNG SẮP HẾT HẠN");

        lblHopDong.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblHopDong.setForeground(new java.awt.Color(255, 205, 31));
        lblHopDong.setText("XX");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(22, 22, 22))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(103, 103, 103)
                .addComponent(lblHopDong)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHopDong)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(46, 56, 86));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 205, 31), 2));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("HÓA ĐƠN CHƯA THANH TOÁN");
        jLabel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel14MouseClicked(evt);
            }
        });

        lblHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblHoaDon.setForeground(new java.awt.Color(255, 205, 31));
        lblHoaDon.setText("XX");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel14))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(98, 98, 98)
                        .addComponent(lblHoaDon)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHoaDon)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblTrangChu.setBackground(new java.awt.Color(207, 243, 243));
        tblTrangChu.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tblTrangChu.setForeground(new java.awt.Color(40, 46, 62));
        tblTrangChu.setModel(new javax.swing.table.DefaultTableModel(
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
        tblTrangChu.setGridColor(new java.awt.Color(255, 255, 255));
        tblTrangChu.setSelectionBackground(new java.awt.Color(46, 56, 86));
        tblTrangChu.setSelectionForeground(new java.awt.Color(255, 205, 31));
        jScrollPane1.setViewportView(tblTrangChu);

        javax.swing.GroupLayout jpnTrangChuLayout = new javax.swing.GroupLayout(jpnTrangChu);
        jpnTrangChu.setLayout(jpnTrangChuLayout);
        jpnTrangChuLayout.setHorizontalGroup(
            jpnTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnTrangChuLayout.createSequentialGroup()
                .addGroup(jpnTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jpnTrangChuLayout.createSequentialGroup()
                        .addGap(90, 90, 90)
                        .addGroup(jpnTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(79, 79, 79)
                        .addGroup(jpnTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(85, 85, 85)
                        .addGroup(jpnTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jpnTrangChuLayout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 921, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(101, Short.MAX_VALUE))
        );
        jpnTrangChuLayout.setVerticalGroup(
            jpnTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnTrangChuLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(jpnTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(58, 58, 58)
                .addGroup(jpnTrangChuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnTrangChu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnTrangChu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel14MouseClicked
try {
            HoaDonCtt ctt = new HoaDonCtt();
            ctt.setVisible(true);
           
        } catch (SQLException ex) {
          ex.printStackTrace();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel14MouseClicked

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
       try {
            LineChartDichVu demo = new LineChartDichVu("JFreeChart: LineChartDichVu.java");
            demo.pack();
            UIUtils.centerFrameOnScreen((Window) demo);
            demo.setVisible(true);        
        } catch (SQLException ex) {
            Logger.getLogger(TrangChuPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jPanel3MouseClicked

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
     TienDatCoc1 tdc = null;
        try {
            tdc = new TienDatCoc1();
        } catch (SQLException ex) {
            Logger.getLogger(TrangChuPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        tdc.setVisible(true);
    }//GEN-LAST:event_jPanel2MouseClicked

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        NguoiThue1 tr = new NguoiThue1();
        tr.setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel4MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel jpnTrangChu;
    private javax.swing.JLabel lblDoanhSo;
    private javax.swing.JLabel lblHoaDon;
    private javax.swing.JLabel lblHopDong;
    private javax.swing.JLabel lblSoKhach;
    private javax.swing.JLabel lblThangDoanhSo;
    private javax.swing.JLabel lblThangTienChi;
    private javax.swing.JLabel lblTienChi;
    private javax.swing.JLabel lblTienThu;
    private javax.swing.JTable tblTrangChu;
    // End of variables declaration//GEN-END:variables
}
