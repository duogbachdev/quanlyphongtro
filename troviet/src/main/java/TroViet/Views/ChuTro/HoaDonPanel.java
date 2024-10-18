/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package TroViet.Views.ChuTro;

import static TroViet.Connect.SQL_Connection.con;
import TroViet.DAO.HoaDonDAO;
import TroViet.DAO.HopDongDAO;
import TroViet.DAO.HoaDonEnum;
import TroViet.DAO.SessionDAO;
import TroViet.Model.HoaDon;
import TroViet.Model.SessionEntity;
import TroViet.Model.UserEntity;
import TroViet.Utils.SessionManager;
import static TroViet.Utils.SessionManager.setSession;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class HoaDonPanel extends javax.swing.JPanel {

    /**
     * Creates new form HoaDonPanel
     */
    DefaultTableModel tblModel;
    HoaDonDAO hdonDAO = new HoaDonDAO();
    HopDongDAO hdongDAO = new HopDongDAO();
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    LocalDateTime currentDateTime = LocalDateTime.now();
    DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    String formattedDateTime = currentDateTime.format(formatterTime);
    UserEntity userSS = SessionManager.getSession().getUser();
    static SessionDAO sessionDao = new SessionDAO();

    public HoaDonPanel() throws SQLException {
        initComponents();
        initTable();
        fillHoaDon();
    }

    public void initTable() throws SQLException {
        tblModel = (DefaultTableModel) tblHoaDon.getModel();
        String[] columns = {"ID hóa đơn", "ID hợp đồng", "Ngày BĐ", "Ngày KT", "Tiền điện", "Tiền nước", "Tiền internet", "Tiền rác", "Tiền phòng", "Khấu trừ", "Nợ", "TC", "Tình trạng"};
        tblModel.setColumnIdentifiers(columns);
        tblHoaDon.setModel(tblModel);
        
        statusComboBox1.addItem("Tất cả");
        statusComboBox1.addItem(HoaDonEnum.Paid.getName());
        statusComboBox1.addItem(HoaDonEnum.Unpaid.getName());

        fillHoaDon();

        statusComboBox1.addActionListener(e -> {
            try {
                String selectedStatus = (String) statusComboBox1.getSelectedItem();
                System.out.println(selectedStatus);
                fillHoaDon(selectedStatus);
            } catch (SQLException ex) {
                Logger.getLogger(HoaDonPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
    }
    
    public void fillHoaDon(String selectedStatus) throws SQLException {
        tblModel.setRowCount(0);
         String query;
           if (selectedStatus == HoaDonEnum.Paid.getName()) {
             query = "select  hdon.Id, hdong.Id, hdon.NgayBatDau, hdon.NgayKetThuc,\n"
                + "((hdon.SoDienMoi - hdon.SoDienCu)*hdong.GiaDien) as 'Tien dien', \n"
                + "((hdon.SoNuocMoi - hdon.SoNuocCu)*hdong.GiaNuoc) as 'Tien nuoc', \n"
                + "(hdong.GiaInternet), \n"
                + "(hdong.GiaPhong), \n"
                + "(hdon.KhauTru), \n"
                + "(hdon.TienNo),\n"
                + "( ((hdon.SoDienMoi - hdon.SoDienCu)*hdong.GiaDien) + ((hdon.SoNuocMoi - hdon.SoNuocCu)*hdong.GiaNuoc) +\n"
                + "(hdong.GiaInternet)  + (hdong.GiaPhong) + (hdon.TienNo) - (hdon.KhauTru)) as 'Tong cong', \n"
                + "hdon.Status\n"
                + "from HoaDon hdon join HopDong hdong on hdon.IdHopDong= hdong.Id where hdon.Trash !=0 and hdon.Status = " + HoaDonEnum.Paid.getId();
            } else if (selectedStatus == HoaDonEnum.Unpaid.getName()) {
                query = "select  hdon.Id, hdong.Id, hdon.NgayBatDau, hdon.NgayKetThuc,\n"
                + "((hdon.SoDienMoi - hdon.SoDienCu)*hdong.GiaDien) as 'Tien dien', \n"
                + "((hdon.SoNuocMoi - hdon.SoNuocCu)*hdong.GiaNuoc) as 'Tien nuoc', \n"
                + "(hdong.GiaInternet), \n"
                + "(hdong.GiaPhong), \n"
                + "(hdon.KhauTru), \n"
                + "(hdon.TienNo),\n"
                + "( ((hdon.SoDienMoi - hdon.SoDienCu)*hdong.GiaDien) + ((hdon.SoNuocMoi - hdon.SoNuocCu)*hdong.GiaNuoc) +\n"
                + "(hdong.GiaInternet) + (hdong.GiaPhong) + (hdon.TienNo) - (hdon.KhauTru)) as 'Tong cong', \n"
                + "hdon.Status\n"
                + "from HoaDon hdon join HopDong hdong on hdon.IdHopDong= hdong.Id where hdon.Trash !=0 and hdon.Status = " + HoaDonEnum.Unpaid.getId();
            } else {
                query = "select  hdon.Id, hdong.Id, hdon.NgayBatDau, hdon.NgayKetThuc,\n"
                + "((hdon.SoDienMoi - hdon.SoDienCu)*hdong.GiaDien) as 'Tien dien', \n"
                + "((hdon.SoNuocMoi - hdon.SoNuocCu)*hdong.GiaNuoc) as 'Tien nuoc', \n"
                + "(hdong.GiaInternet), \n"
                + "(hdong.GiaPhong), \n"
                + "(hdon.KhauTru), \n"
                + "(hdon.TienNo),\n"
                + "( ((hdon.SoDienMoi - hdon.SoDienCu)*hdong.GiaDien) + ((hdon.SoNuocMoi - hdon.SoNuocCu)*hdong.GiaNuoc) +\n"
                + "(hdong.GiaInternet) + (hdong.GiaPhong) + (hdon.TienNo) - (hdon.KhauTru)) as 'Tong cong', \n"
                + "hdon.Status\n"
                + "from HoaDon hdon join HopDong hdong on hdon.IdHopDong= hdong.Id where hdon.Trash !=0;";
            }
        
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Vector<Object> vec = new Vector<Object>();
            vec.add(rs.getLong(1)); //id
            vec.add(rs.getLong(2)); //id hợp đồng
            vec.add(rs.getDate(3)); // ngày bd
            vec.add(rs.getDate(4)); // ngày kt
            vec.add(formatter.format(rs.getFloat(5)));  //tiền điện
            vec.add(formatter.format(rs.getFloat(6))); //tiền nước
            vec.add(formatter.format(rs.getFloat(7))); //internet
            vec.add(formatter.format(rs.getFloat(8))); //tiền rác
            vec.add(formatter.format(rs.getFloat(9))); //tiền phòng
            vec.add(formatter.format(rs.getFloat(10))); //khấu trừ
            vec.add(formatter.format(rs.getFloat(11))); //nợ
            vec.add(formatter.format(rs.getFloat(12))); //tổng cộng
            vec.add(HoaDonEnum.getById(rs.getString(13))); //status
            tblModel.addRow(vec);
        }
    }

    public void fillHoaDon() throws SQLException {
        tblModel.setRowCount(0);
        String query = "select  hdon.Id, hdong.Id, hdon.NgayBatDau, hdon.NgayKetThuc,\n"
                + "((hdon.SoDienMoi - hdon.SoDienCu)*hdong.GiaDien) as 'Tien dien', \n"
                + "((hdon.SoNuocMoi - hdon.SoNuocCu)*hdong.GiaNuoc) as 'Tien nuoc', \n"
                + "(hdong.GiaInternet), \n"
                + "(hdong.GiaPhong), \n"
                + "(hdon.KhauTru), \n"
                + "(hdon.TienNo),\n"
                + "( ((hdon.SoDienMoi - hdon.SoDienCu)*hdong.GiaDien) + ((hdon.SoNuocMoi - hdon.SoNuocCu)*hdong.GiaNuoc) +\n"
                + "(hdong.GiaInternet)  + (hdong.GiaPhong) + (hdon.TienNo) - (hdon.KhauTru)) as 'Tong cong', \n"
                + "hdon.Status\n"
                + "from HoaDon hdon join HopDong hdong on hdon.IdHopDong= hdong.Id where hdon.Trash !=0;";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Vector<Object> vec = new Vector<Object>();
            vec.add(rs.getLong(1)); //id
            vec.add(rs.getLong(2)); //id hợp đồng
            vec.add(rs.getDate(3)); // ngày bd
            vec.add(rs.getDate(4)); // ngày kt
            vec.add(formatter.format(rs.getFloat(5)));  //tiền điện
            vec.add(formatter.format(rs.getFloat(6))); //tiền nước
            vec.add(formatter.format(rs.getFloat(7))); //internet
            vec.add(formatter.format(rs.getFloat(8))); //tiền rác
            vec.add(formatter.format(rs.getFloat(9))); //tiền phòng
            vec.add(formatter.format(rs.getFloat(10))); //khấu trừ
            vec.add(formatter.format(rs.getFloat(11))); //nợ
            vec.add(formatter.format(rs.getFloat(12))); //tổng cộng
            vec.add(HoaDonEnum.getById(rs.getString(13))); //status
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

    public void ResetForm() {
        txtTimKiem.setText("");
        lblIDHoaDon.setText("XX");
        lblTongCong.setText("XX");
        txtIDHopDong.setText("");
        jdcNgayTao.setDate(null);
        jdcNgayBD.setDate(null);
        jdcNgayKT.setDate(null);
        txtSoDienCu.setText("");
        txtSoDienMoi.setText("");
        txtSoNuocCu.setText("");
        txtSoNuocMoi.setText("");
        txtKhauTru.setText("");
        txtTienNo.setText("");
        txtTienInternet.setText("");
      
        btnGStatus.clearSelection();
        txtGiaPhong.setText("");
    }

    public HoaDon getModelHDon() {
        HoaDon hdon = new HoaDon();
        long id = Long.parseLong(lblIDHoaDon.getText());
        long idHopDong = Long.parseLong(txtIDHopDong.getText());
        String ngayBD = sdf.format(jdcNgayBD.getDate());
        String ngayKT = sdf.format(jdcNgayKT.getDate());
        float soDienCu = Float.parseFloat(txtSoDienCu.getText());
        float soDienMoi = Float.parseFloat(txtSoDienMoi.getText());
        float soNuocCu = Float.parseFloat(txtSoNuocCu.getText());
        float soNuocMoi = Float.parseFloat(txtSoNuocMoi.getText());
        float khauTru = Float.parseFloat(txtKhauTru.getText());
        float no = Float.parseFloat(txtTienNo.getText());
        float tongCong = Float.parseFloat(lblTongCong.getText());
        String ngayTao = sdf.format(jdcNgayTao.getDate());
        String nguoiTao = SessionManager.getSession().getUser().getHoTen();
        HoaDonEnum status;
        if (rbChuaThanhToan.isSelected()) {
            status = HoaDonEnum.Unpaid;
        } else {
            status = HoaDonEnum.Paid;
        }
        hdon.setId(id);
        hdon.setIdHopDong(idHopDong);
        hdon.setNgayBD(ngayBD);
        hdon.setNgayKT(ngayKT);
        hdon.setSoDienCu(soDienCu);
        hdon.setSoDienMoi(soDienMoi);
        hdon.setSoNuocCu(soNuocCu);
        hdon.setSoNuocMoi(soNuocMoi);
        hdon.setKhauTru(khauTru);
        hdon.setNo(no);
        hdon.setTongCong(tongCong);
        hdon.setNgayTao(ngayTao);
        hdon.setNguoiTao(nguoiTao);
        hdon.setNgaySua(formattedDateTime);
        hdon.setNguoiSua(SessionManager.getSession().getUser().getHoTen());
        hdon.setTrash(1);
        hdon.setStatus(status);
        return hdon;
    }

//    public Date parseStringToDate(String dateString) {
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//        try {
//            Date date = formatter.parse(dateString);
//            return date;
//        } catch (ParseException e) {
//            return null;
//        }
//    }
//    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGStatus = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        txtTimKiem = new javax.swing.JTextField();
        btnTimKiem = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblIDHoaDon = new javax.swing.JLabel();
        txtIDHopDong = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jdcNgayTao = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jdcNgayBD = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        jdcNgayKT = new com.toedter.calendar.JDateChooser();
        jLabel8 = new javax.swing.JLabel();
        txtSoDienCu = new javax.swing.JTextField();
        txtSoDienMoi = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtSoNuocCu = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtSoNuocMoi = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtTienInternet = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtKhauTru = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtTienNo = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        rbDaThanhtoan = new javax.swing.JRadioButton();
        rbChuaThanhToan = new javax.swing.JRadioButton();
        lblTongCong = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtGiaPhong = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnThungRac = new javax.swing.JButton();
        btnGui = new javax.swing.JButton();
        btnIn = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        statusComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHoaDon = new javax.swing.JTable();

        setBackground(new java.awt.Color(40, 46, 62));

        jPanel1.setBackground(new java.awt.Color(46, 56, 86));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 205, 31), 2), "Tìm kiếm ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 205, 31))); // NOI18N

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTimKiem.setForeground(new java.awt.Color(40, 46, 62));
        txtTimKiem.setText("Nhập mã hợp đồng để tìm kiếm hóa đơn!");
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(102, 102, 102)
                .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(103, 103, 103))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(207, 243, 243));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(40, 46, 62));
        jLabel1.setText("HÓA ĐƠN");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(40, 46, 62));
        jLabel2.setText("Mã hóa đơn:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(40, 46, 62));
        jLabel3.setText("Mã hợp đồng:");

        lblIDHoaDon.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblIDHoaDon.setForeground(new java.awt.Color(255, 205, 31));
        lblIDHoaDon.setText("XX");

        txtIDHopDong.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtIDHopDong.setForeground(new java.awt.Color(40, 46, 62));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(40, 46, 62));
        jLabel5.setText("Ngày tạo:");

        jdcNgayTao.setBackground(new java.awt.Color(255, 255, 255));
        jdcNgayTao.setForeground(new java.awt.Color(40, 46, 62));
        jdcNgayTao.setDateFormatString("dd-MM-yyyy");
        jdcNgayTao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(40, 46, 62));
        jLabel6.setText("Ngày BĐ:");

        jdcNgayBD.setBackground(new java.awt.Color(255, 255, 255));
        jdcNgayBD.setForeground(new java.awt.Color(40, 46, 62));
        jdcNgayBD.setDateFormatString("dd-MM-yyyy");
        jdcNgayBD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(40, 46, 62));
        jLabel7.setText("Ngày KT:");

        jdcNgayKT.setBackground(new java.awt.Color(255, 255, 255));
        jdcNgayKT.setForeground(new java.awt.Color(40, 46, 62));
        jdcNgayKT.setDateFormatString("dd-MM-yyyy");
        jdcNgayKT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(40, 46, 62));
        jLabel8.setText("Số điện cũ:");

        txtSoDienCu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSoDienCu.setForeground(new java.awt.Color(40, 46, 62));
        txtSoDienCu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSoDienCuMouseClicked(evt);
            }
        });

        txtSoDienMoi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSoDienMoi.setForeground(new java.awt.Color(40, 46, 62));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(40, 46, 62));
        jLabel9.setText("Số điện mới:");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(40, 46, 62));
        jLabel10.setText("Số nước cũ:");

        txtSoNuocCu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSoNuocCu.setForeground(new java.awt.Color(40, 46, 62));
        txtSoNuocCu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSoNuocCuMouseClicked(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(40, 46, 62));
        jLabel11.setText("Số nước mới:");

        txtSoNuocMoi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSoNuocMoi.setForeground(new java.awt.Color(40, 46, 62));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(40, 46, 62));
        jLabel12.setText("Tiền internet:");

        txtTienInternet.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTienInternet.setForeground(new java.awt.Color(40, 46, 62));
        txtTienInternet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTienInternetMouseClicked(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(40, 46, 62));
        jLabel14.setText("Khấu trừ:");

        txtKhauTru.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtKhauTru.setForeground(new java.awt.Color(40, 46, 62));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(40, 46, 62));
        jLabel15.setText("Nợ:");

        txtTienNo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTienNo.setForeground(new java.awt.Color(40, 46, 62));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(40, 46, 62));
        jLabel16.setText("Tổng cộng:");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(40, 46, 62));
        jLabel17.setText("Tình trạng:");

        rbDaThanhtoan.setBackground(new java.awt.Color(207, 243, 243));
        btnGStatus.add(rbDaThanhtoan);
        rbDaThanhtoan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rbDaThanhtoan.setForeground(new java.awt.Color(40, 46, 62));
        rbDaThanhtoan.setText("Đã thanh toán");

        rbChuaThanhToan.setBackground(new java.awt.Color(207, 243, 243));
        btnGStatus.add(rbChuaThanhToan);
        rbChuaThanhToan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rbChuaThanhToan.setForeground(new java.awt.Color(40, 46, 62));
        rbChuaThanhToan.setText("Chưa thanh toán");

        lblTongCong.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTongCong.setForeground(new java.awt.Color(255, 205, 31));
        lblTongCong.setText("XX");
        lblTongCong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTongCongMouseClicked(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(40, 46, 62));
        jLabel18.setText("Giá phòng:");

        txtGiaPhong.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGiaPhong.setForeground(new java.awt.Color(40, 46, 62));
        txtGiaPhong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtGiaPhongMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel16))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtIDHopDong, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jdcNgayBD, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSoDienCu, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtSoNuocCu, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTienInternet, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtKhauTru, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblTongCong)))
                            .addComponent(jLabel1))
                        .addGap(49, 49, 49)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblIDHoaDon))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel11)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtSoNuocMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel9)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel5))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtSoDienMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jdcNgayKT, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jdcNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel15)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtTienNo, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel18)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtGiaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(jLabel17)
                        .addGap(49, 49, 49)
                        .addComponent(rbDaThanhtoan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rbChuaThanhToan)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(lblIDHoaDon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jdcNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtIDHopDong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jdcNgayBD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jdcNgayKT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtSoDienCu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtSoDienMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtSoNuocCu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtSoNuocMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtTienInternet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtKhauTru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(txtTienNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(lblTongCong)
                    .addComponent(jLabel18)
                    .addComponent(txtGiaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(rbDaThanhtoan)
                    .addComponent(rbChuaThanhToan))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(46, 56, 86));

        btnThem.setBackground(new java.awt.Color(255, 205, 31));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThem.setForeground(new java.awt.Color(40, 46, 62));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TroViet/Icon/add.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(255, 205, 31));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSua.setForeground(new java.awt.Color(40, 46, 62));
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TroViet/Icon/edit.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(255, 205, 31));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(40, 46, 62));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TroViet/Icon/litter.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnThungRac.setBackground(new java.awt.Color(255, 205, 31));
        btnThungRac.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThungRac.setForeground(new java.awt.Color(40, 46, 62));
        btnThungRac.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TroViet/Icon/delete.png"))); // NOI18N
        btnThungRac.setText("Thùng rác");
        btnThungRac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThungRacActionPerformed(evt);
            }
        });

        btnGui.setBackground(new java.awt.Color(255, 205, 31));
        btnGui.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnGui.setForeground(new java.awt.Color(40, 46, 62));
        btnGui.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TroViet/Icon/paper-plane.png"))); // NOI18N
        btnGui.setText("Gửi");
        btnGui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuiActionPerformed(evt);
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

        btnReset.setBackground(new java.awt.Color(255, 205, 31));
        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnReset.setForeground(new java.awt.Color(40, 46, 62));
        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/TroViet/Icon/return.png"))); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        statusComboBox1.setBackground(new java.awt.Color(255, 205, 31));
        statusComboBox1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        statusComboBox1.setForeground(new java.awt.Color(40, 46, 62));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnThungRac))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnReset, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnGui, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnIn, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(statusComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThungRac, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGui, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnIn, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(statusComboBox1))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        tblHoaDon.setBackground(new java.awt.Color(207, 243, 243));
        tblHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        // TODO add your handling code here:
        try {
            long selectedId = getSelectedID();
            if (selectedId < 0) {
                JOptionPane.showMessageDialog(null, "Chọn hóa đơn cần chọn!");
            }
            String query = "select  hdon.Id, hdong.Id, hdon.NgayBatDau, hdon.NgayKetThuc, hdon.NgayTao, hdon.SoDienMoi, hdon.SoDienCu, \n"
                    + "((hdon.SoDienMoi - hdon.SoDienCu)*hdong.GiaDien) as 'Tien dien', \n"
                    + "hdon.SoNuocMoi, hdon.SoNuocCu,\n"
                    + "((hdon.SoNuocMoi - hdon.SoNuocCu)*hdong.GiaNuoc) as 'Tien nuoc', \n"
                    + "(hdong.GiaInternet), (hdong.GiaRac), (hdong.GiaPhong), (hdon.KhauTru), (hdon.TienNo),\n"
                    + "( ((hdon.SoDienMoi - hdon.SoDienCu)*hdong.GiaDien) + ((hdon.SoNuocMoi - hdon.SoNuocCu)*hdong.GiaNuoc) +\n"
                    + "(hdong.GiaInternet) + (hdong.GiaRac) + (hdong.GiaPhong) + (hdon.TienNo) - (hdon.KhauTru)) as 'Tong cong', hdon.Status\n"
                    + "from HoaDon hdon join HopDong hdong on hdon.IdHopDong= hdong.Id\n"
                    + "where hdon.Id = ?;";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setLong(1, selectedId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lblIDHoaDon.setText(rs.getLong(1) + "");
                txtIDHopDong.setText(rs.getLong(2) + "");
                jdcNgayTao.setDate(rs.getDate(5));
                jdcNgayBD.setDate(rs.getDate(3));
                jdcNgayKT.setDate(rs.getDate(4));
                txtSoDienCu.setText(rs.getFloat(7) + "");
                txtSoDienMoi.setText(rs.getFloat(6) + "");
                txtSoNuocCu.setText(rs.getFloat(10) + "");
                txtSoNuocMoi.setText(rs.getFloat(9) + "");
                txtTienInternet.setText(rs.getFloat(12) + "");
                txtGiaPhong.setText(rs.getFloat(14) + "");
                txtKhauTru.setText(rs.getFloat(15) + "");
                txtTienNo.setText(rs.getFloat(16) + "");
                lblTongCong.setText(rs.getFloat(17) + "");
                if (rs.getInt(18) == 1) {
                    rbDaThanhtoan.setSelected(true);
                } else if (rs.getInt(18) == 0) {
                    rbChuaThanhToan.setSelected(true);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        try {
            ResetForm();
            fillHoaDon();
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnResetActionPerformed

    private void txtTienInternetMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTienInternetMouseClicked
        // TODO add your handling code here:
        try {
            if (hdongDAO.isHopDongExists(Long.parseLong(txtIDHopDong.getText())) == false) {
                JOptionPane.showMessageDialog(null, "Hợp đồng không tồn tại. Vui lòng kiểm tra lại!");
                return;
            } else {
                try {
                    float tienInternet = hdongDAO.get(Long.parseLong(txtIDHopDong.getText())).getGiaInternet();
                    txtTienInternet.setText(tienInternet + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(HoaDonPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtTienInternetMouseClicked

    private void txtGiaPhongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtGiaPhongMouseClicked
        // TODO add your handling code here:
        try {
            if (hdongDAO.isHopDongExists(Long.parseLong(txtIDHopDong.getText())) == false) {
                JOptionPane.showMessageDialog(null, "Hợp đồng không tồn tại. Vui lòng kiểm tra lại!");
                return;
            } else {
                try {
                    float tienPhong = hdongDAO.get(Long.parseLong(txtIDHopDong.getText())).getGiaPhong();
                    txtGiaPhong.setText(tienPhong + "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtGiaPhongMouseClicked

    private void lblTongCongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTongCongMouseClicked
        // TODO add your handling code here:
        try {
            if (hdongDAO.isHopDongExists(Long.parseLong(txtIDHopDong.getText())) == false) {
                JOptionPane.showMessageDialog(null, "Hợp đồng không tồn tại. Vui lòng kiểm tra lại!");
                return;
            } else {
                try {
                    float soDienCu = Float.parseFloat(txtSoDienCu.getText());
                    float soDienMoi = Float.parseFloat(txtSoDienMoi.getText());
                    float giaDien = hdongDAO.get(Long.parseLong(txtIDHopDong.getText())).getGiaDien();
                    float tienDien = (soDienMoi - soDienCu) * giaDien;

                    float soNuocCu = Float.parseFloat(txtSoNuocCu.getText());
                    float soNuocMoi = Float.parseFloat(txtSoNuocMoi.getText());
                    float giaNuoc = hdongDAO.get(Long.parseLong(txtIDHopDong.getText())).getGiaNuoc();
                    float tienNuoc = (soNuocMoi - soNuocCu) * giaNuoc;

                    float tienInternet = hdongDAO.get(Long.parseLong(txtIDHopDong.getText())).getGiaInternet();
                    float khauTru = Float.parseFloat(txtKhauTru.getText());
                    float no = Float.parseFloat(txtTienNo.getText());
                    float tienPhong = hdongDAO.get(Long.parseLong(txtIDHopDong.getText())).getGiaPhong();

                    Float tongCong = (tienDien + tienNuoc + tienInternet  + tienPhong + no) - khauTru;

                    lblTongCong.setText(tongCong + "");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            Logger.getLogger(HoaDonPanel.class.getName()).log(Level.SEVERE, null, e);
        }


    }//GEN-LAST:event_lblTongCongMouseClicked

    private void txtSoDienCuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSoDienCuMouseClicked
        // TODO add your handling code here:
        try {
            if (hdongDAO.isHopDongExists(Long.parseLong(txtIDHopDong.getText())) == false) {
                JOptionPane.showMessageDialog(null, "Hợp đồng không tồn tại. Vui lòng kiểm tra lại!");
                return;
            } else {
                try {
                    String query = "select top 1 * from HoaDon where IdHopDong = ? and Trash!=0 order by NgayKetThuc desc;";
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setLong(1, Long.parseLong(txtIDHopDong.getText()));
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        float soDienMoi = rs.getFloat("SoDienMoi");
                        txtSoDienCu.setText(soDienMoi + "");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(HoaDonPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtSoDienCuMouseClicked

    private void txtSoNuocCuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSoNuocCuMouseClicked
        // TODO add your handling code here:
        try {
            if (hdongDAO.isHopDongExists(Long.parseLong(txtIDHopDong.getText())) == false) {
                JOptionPane.showMessageDialog(null, "Hợp đồng không tồn tại. Vui lòng kiểm tra lại!");
                return;
            } else {
                try {
                    String query = "select top 1 * from HoaDon where IdHopDong = ? and Trash!=0 order by NgayKetThuc desc;";
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setLong(1, Long.parseLong(txtIDHopDong.getText()));
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        float soNuocMoi = rs.getFloat("soNuocMoi");
                        txtSoNuocCu.setText(soNuocMoi + "");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(HoaDonPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtSoNuocCuMouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
        try {
            if (hdongDAO.isHopDongExists(Long.parseLong(txtIDHopDong.getText())) == false) {
                JOptionPane.showMessageDialog(null, "Hợp đồng không tồn tại. Vui lòng kiểm tra lại!");
                return;
            } else {
                try {
                    long idHopDong = Long.parseLong(txtIDHopDong.getText());
                    String ngayBD = sdf.format(jdcNgayBD.getDate());
                    String ngayKT = sdf.format(jdcNgayKT.getDate());
                    float soDienCu = Float.parseFloat(txtSoDienCu.getText());
                    float soDienMoi = Float.parseFloat(txtSoDienMoi.getText());
                    float soNuocCu = Float.parseFloat(txtSoNuocCu.getText());
                    float soNuocMoi = Float.parseFloat(txtSoNuocMoi.getText());
                    float khauTru = Float.parseFloat(txtKhauTru.getText());
                    float no = Float.parseFloat(txtTienNo.getText());
                    float tongCong = Float.parseFloat(lblTongCong.getText());
                    String ngayTao = sdf.format(jdcNgayTao.getDate());
                    String nguoiTao = SessionManager.getSession().getUser().getHoTen();
                    HoaDonEnum status;
                    if (rbDaThanhtoan.isSelected()) {
                        status = HoaDonEnum.Paid;
                    } else if (rbChuaThanhToan.isSelected()) {
                        status = HoaDonEnum.Unpaid;
                    }

                    if (ngayBD == null || ngayKT == null || ngayTao == null) {
                        JOptionPane.showMessageDialog(null, "Vui lòng chọn đầy đủ ngày tạo, ngày BĐ, ngày KT của hóa đơn!");
                        return;
                    }

                    int ret = JOptionPane.showConfirmDialog(null, "Thêm mới hóa đơn?", "Thông báo", JOptionPane.YES_NO_OPTION);
                    if (ret == JOptionPane.NO_OPTION) {
                        return;
                    }

                    HoaDon hdon = new HoaDon();
                    hdon.setIdHopDong(idHopDong);
                    hdon.setNgayBD(ngayBD);
                    hdon.setNgayKT(ngayKT);
                    hdon.setSoDienCu(soDienCu);
                    hdon.setSoDienMoi(soDienMoi);
                    hdon.setSoNuocCu(soNuocCu);
                    hdon.setSoNuocMoi(soNuocMoi);
                    hdon.setKhauTru(khauTru);
                    hdon.setNo(no);
                    hdon.setTongCong(tongCong);
                    hdon.setNgayTao(ngayTao);
                    hdon.setNguoiTao(nguoiTao);
                    hdon.setStatus(HoaDonEnum.Unpaid);

                    try {
                        hdonDAO.add(hdon);
                        ResetForm();
                        JOptionPane.showMessageDialog(null, "Thêm dữ liệu thành công");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Thêm dữ liệu thất bại");
                    }
                    fillHoaDon();
                    SessionEntity ss = new SessionEntity();
                    ss.setUser(userSS);
                    ss.setMessage("Thêm hóa đơn");
                    ss.setStartTime(new Timestamp(System.currentTimeMillis()));
                    sessionDao.save(ss);
                    SessionEntity sss = sessionDao.getLast(userSS.getId());
                    setSession(sss);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin của hóa đơn, các chỉ số và giá tiền đều phải là số!");
                    return;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        // TODO add your handling code here:
        try {
            int ret = JOptionPane.showConfirmDialog(null, "Cập nhật hóa đơn?", "Cập nhật", JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.NO_OPTION) {
                return;
            }
            HoaDon hdon = getModelHDon();
            if (hdonDAO.updateByID(hdon) > 0) {
                JOptionPane.showMessageDialog(this, "Cập nhật thông tin hóa đơn thành công!");
                fillHoaDon();
                ResetForm();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhập thông tin hóa đơn thất bại");
            }
            SessionEntity ss = new SessionEntity();
            ss.setUser(userSS);
            ss.setMessage("Cập nhật hóa đơn");
            ss.setStartTime(new Timestamp(System.currentTimeMillis()));
            sessionDao.save(ss);
            SessionEntity sss = sessionDao.getLast(userSS.getId());
            setSession(sss);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        Long selectedId = getSelectedID();
        try {
            if (JOptionPane.showConfirmDialog(null, "Xác nhận xóa?", "Xóa hóa đơn", ERROR_MESSAGE) != YES_OPTION) {
                return;
            }
            hdonDAO.deleteFromRecycle(selectedId);
            fillHoaDon();
            SessionEntity ss = new SessionEntity();
            ss.setUser(userSS);
            ss.setMessage("Chuyển hóa đơn vào thùng rác");
            ss.setStartTime(new Timestamp(System.currentTimeMillis()));
            sessionDao.save(ss);
            SessionEntity sss = sessionDao.getLast(userSS.getId());
            setSession(sss);
        } catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnThungRacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThungRacActionPerformed
        // TODO add your handling code here:
        try {
            ThungRacHoaDon tr = new ThungRacHoaDon();
            tr.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnThungRacActionPerformed

    private void btnGuiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuiActionPerformed
        GuiHoaDonChuTro ghd = new GuiHoaDonChuTro();
        ghd.setVisible(true);
    }//GEN-LAST:event_btnGuiActionPerformed

    private void btnInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInActionPerformed
        // TODO add your handling code here:
        try {
            float giaDien = 0, giaNuoc = 0;
            String query = "select GiaDien, GiaNuoc from HopDong where Id = ?;";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setLong(1, Long.parseLong(txtIDHopDong.getText()));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                giaDien = Float.parseFloat(rs.getString(1));
                giaNuoc = Float.parseFloat(rs.getString(2));
            }

            long id = Long.parseLong(lblIDHoaDon.getText());
            long idHopDong = Long.parseLong(txtIDHopDong.getText());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String ngayBD = sdf.format(jdcNgayBD.getDate());
            String ngayKT = sdf.format(jdcNgayKT.getDate());
            String ngayTao = sdf.format(jdcNgayTao.getDate());
            String nguoiTao = SessionManager.getSession().getUser().getHoTen();
            float soDienCu = Float.parseFloat(txtSoDienCu.getText());
            float soDienMoi = Float.parseFloat(txtSoDienMoi.getText());
            float soKw = soDienMoi - soDienCu;
            String tienDien = formatter.format(soKw * giaDien);
            float soNuocCu = Float.parseFloat(txtSoNuocCu.getText());
            float sonuocMoi = Float.parseFloat(txtSoNuocMoi.getText());
            float soKhoiNuoc = sonuocMoi - soNuocCu;
            String tienNuoc = formatter.format(soKhoiNuoc * giaNuoc);
            String tienInternet = formatter.format(Float.parseFloat(txtTienInternet.getText()));
            String khauTru = formatter.format(Float.parseFloat(txtKhauTru.getText()));
            String no = formatter.format(Float.parseFloat(txtTienNo.getText()));
            String giaPhong = formatter.format(Float.parseFloat(txtGiaPhong.getText()));
            String tongCong = formatter.format(Float.parseFloat(lblTongCong.getText()));

            BaseFont bf = BaseFont.createFont("Handjet-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font font = new Font(bf, 14, Font.NORMAL, BaseColor.BLACK);
            Font fontt = new Font(bf, 12, Font.NORMAL, BaseColor.BLACK);
            Font font_name = new Font(bf, 24, Font.NORMAL, BaseColor.BLACK);
            
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Xuất file PDF");

            // Tạo tên file mặc định
            String[] parts = ngayBD.split("/"); // Cắt chuỗi thành mảng các phần tử dựa trên dấu "/"
            String thang = parts[1]; // Lấy phần tử thứ hai trong mảng là tháng
            String tenFile = "HoaDonThang" + thang + "_HopDong" + txtIDHopDong.getText() + ".pdf";
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
                    Document document = new Document(PageSize.A6) {
                    };

                    File fileToSave = fileChooser.getSelectedFile();
                    String fileName = fileToSave.getName();
                    if (!fileName.endsWith(".pdf")) {
                        // Gắn phần mở rộng ".pdf" vào tên file nếu chưa có
                        fileToSave = new File(fileToSave.getAbsolutePath() + ".pdf");
                    }
                    PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(fileToSave));
                    document.open();

                    Paragraph header = new Paragraph("Trọ Việt", font);
                    Paragraph name = new Paragraph("HÓA ĐƠN TIỀN TRỌ", font_name);

                    Paragraph ngay = new Paragraph("Ngày BĐ: " + ngayBD + "       Ngày KT: " + ngayKT, font);
                    Paragraph nguoiThu = new Paragraph("Ngày tạo: " + ngayTao + "       Người tạo: " + nguoiTao, font);
                    Paragraph thongtin = new Paragraph("Mã hóa đơn: " + id + "         Mã hợp đồng: " + idHopDong, font);

                    PdfPTable tbl = new PdfPTable(4);

                    tbl.addCell(new Phrase("Tên", font));
                    tbl.addCell(new Phrase("SL", font));
                    tbl.addCell(new Phrase("ĐG (VND)", font));
                    tbl.addCell(new Phrase("TT (VND)", font));

                    tbl.addCell(new Phrase("S.Điện mới", fontt));
                    tbl.addCell(new Phrase(soDienMoi + "", fontt));
                    tbl.addCell(new Phrase("", fontt));
                    tbl.addCell(new Phrase("", fontt));

                    tbl.addCell(new Phrase("S.Điện cũ", fontt));
                    tbl.addCell(new Phrase(soDienCu + "", fontt));
                    tbl.addCell(new Phrase("", fontt));
                    tbl.addCell(new Phrase("", fontt));

                    tbl.addCell(new Phrase("Tiền điện", fontt));
                    tbl.addCell(new Phrase(soKw + "", fontt));
                    tbl.addCell(new Phrase(formatter.format(giaDien), fontt));
                    tbl.addCell(new Phrase(tienDien, fontt));

                    tbl.addCell(new Phrase("S.Nc mới", fontt));
                    tbl.addCell(new Phrase(sonuocMoi + "", fontt));
                    tbl.addCell(new Phrase("", fontt));
                    tbl.addCell(new Phrase("", fontt));

                    tbl.addCell(new Phrase("S.Nc cũ", fontt));
                    tbl.addCell(new Phrase(soNuocCu + "", fontt));
                    tbl.addCell(new Phrase("", fontt));
                    tbl.addCell(new Phrase("", fontt));

                    tbl.addCell(new Phrase("Tiền nước", fontt));
                    tbl.addCell(new Phrase(soKhoiNuoc + "", fontt));
                    tbl.addCell(new Phrase(formatter.format(giaNuoc), fontt));
                    tbl.addCell(new Phrase(tienNuoc, fontt));

                    tbl.addCell(new Phrase("Internet", fontt));
                    tbl.addCell(new Phrase("1 tháng", fontt));
                    tbl.addCell(new Phrase(tienInternet, fontt));
                    tbl.addCell(new Phrase(tienInternet, fontt));

                    tbl.addCell(new Phrase("Tiền rác", fontt));
                    tbl.addCell(new Phrase("1 tháng", fontt));


                    PdfPCell khautru = new PdfPCell(new Phrase("Khấu trừ", fontt));
                    khautru.setColspan(3);
                    tbl.addCell(khautru);
                    tbl.addCell(new Phrase(khauTru, fontt));

                    PdfPCell noo = new PdfPCell(new Phrase("Nợ", fontt));
                    noo.setColspan(3);
                    tbl.addCell(noo);
                    tbl.addCell(new Phrase(no, fontt));

                    PdfPCell tongcong = new PdfPCell(new Phrase("Tổng cộng", fontt));
                    tongcong.setColspan(3);
                    tbl.addCell(tongcong);
                    tbl.addCell(new Phrase(tongCong, fontt));

                    header.setAlignment(Element.ALIGN_CENTER);
                    name.setAlignment(Element.ALIGN_CENTER);
                    name.setSpacingAfter(5);
                    ngay.setAlignment(Element.ALIGN_JUSTIFIED);
                    nguoiThu.setAlignment(Element.ALIGN_JUSTIFIED);
                    thongtin.setSpacingAfter(5);

                    int numberOfPages = writer.getPageNumber();
                    Paragraph numberP = new Paragraph(numberOfPages + "", fontt);
                    numberP.setAlignment(Element.ALIGN_CENTER);

                    document.add(header);
                    document.add(name);
                    document.add(ngay);
                    document.add(nguoiThu);
                    document.add(thongtin);
//                    document.add(duongke);
                    document.add(tbl);
                    document.add(numberP);

                    document.close();
                    writer.close();

                    JOptionPane.showMessageDialog(null, "Xuất file PDF thành công");
                } catch (DocumentException | FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Lỗi: " + ex.getMessage());
                }
            }
            SessionEntity ss = new SessionEntity();
            ss.setUser(userSS);
            ss.setMessage("In hóa đơn");
            ss.setStartTime(new Timestamp(System.currentTimeMillis()));
            sessionDao.save(ss);
            SessionEntity sss = sessionDao.getLast(userSS.getId());
            setSession(sss);
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(HoaDonPanel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HoaDonPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnInActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        try {
            tblModel.setRowCount(0);
            String query = "select  hdon.Id, hdong.Id, hdon.NgayBatDau, hdon.NgayKetThuc,\n"
                    + "((hdon.SoDienMoi - hdon.SoDienCu)*hdong.GiaDien) as 'Tien dien', \n"
                    + "((hdon.SoNuocMoi - hdon.SoNuocCu)*hdong.GiaNuoc) as 'Tien nuoc', \n"
                    + "(hdong.GiaInternet), \n"
                    + "(hdong.GiaRac), \n"
                    + "(hdong.GiaPhong), \n"
                    + "(hdon.KhauTru), \n"
                    + "(hdon.TienNo),\n"
                    + "( ((hdon.SoDienMoi - hdon.SoDienCu)*hdong.GiaDien) + ((hdon.SoNuocMoi - hdon.SoNuocCu)*hdong.GiaNuoc) +\n"
                    + "(hdong.GiaInternet) + (hdong.GiaRac) + (hdong.GiaPhong) + (hdon.TienNo) - (hdon.KhauTru)) as 'Tong cong', \n"
                    + "hdon.Status\n"
                    + "from HoaDon hdon join HopDong hdong on hdon.IdHopDong= hdong.Id where hdon.IdHopDong=?;";
            long idHopDong = Long.parseLong(txtTimKiem.getText());
            PreparedStatement ps = con.prepareStatement(query);
            ps.setLong(1, idHopDong);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Vector<Object> vec = new Vector<Object>();
                vec.add(rs.getLong(1)); //id
                vec.add(rs.getLong(2)); //id hợp đồng
                vec.add(rs.getDate(3)); // ngày bd
                vec.add(rs.getDate(4)); // ngày kt
                vec.add(formatter.format(rs.getFloat(5)));  //tiền điện
                vec.add(formatter.format(rs.getFloat(6))); //tiền nước
                vec.add(formatter.format(rs.getFloat(7))); //internet
                vec.add(formatter.format(rs.getFloat(8))); //tiền rác
                vec.add(formatter.format(rs.getFloat(9))); //tiền phòng
                vec.add(formatter.format(rs.getFloat(10))); //khấu trừ
                vec.add(formatter.format(rs.getFloat(11))); //nợ
                vec.add(formatter.format(rs.getFloat(12))); //tổng cộng
                vec.add(HoaDonEnum.getById(rs.getString(13))); //status
                tblModel.addRow(vec);
            }
            SessionEntity ss = new SessionEntity();
            ss.setUser(userSS);
            ss.setMessage("Tìm hóa đơn");
            ss.setStartTime(new Timestamp(System.currentTimeMillis()));
            sessionDao.save(ss);
            SessionEntity sss = sessionDao.getLast(userSS.getId());
            setSession(sss);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void txtTimKiemFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusGained
        // TODO add your handling code here:
//        txtTimKiem.setText("Nhập mã hợp đồng để tìm kiếm hóa đơn!");
        if (txtTimKiem.getText().equals("Nhập mã hợp đồng để tìm kiếm hóa đơn!")) {
            txtTimKiem.setText("");
        }
    }//GEN-LAST:event_txtTimKiemFocusGained

    private void txtTimKiemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimKiemFocusLost
        // TODO add your handling code here:
        if (txtTimKiem.getText().isEmpty()) {
            txtTimKiem.setText("Nhập mã hợp đồng để tìm kiếm hóa đơn!");
        }
    }//GEN-LAST:event_txtTimKiemFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btnGStatus;
    private javax.swing.JButton btnGui;
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
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jdcNgayBD;
    private com.toedter.calendar.JDateChooser jdcNgayKT;
    private com.toedter.calendar.JDateChooser jdcNgayTao;
    private javax.swing.JLabel lblIDHoaDon;
    private javax.swing.JLabel lblTongCong;
    private javax.swing.JRadioButton rbChuaThanhToan;
    private javax.swing.JRadioButton rbDaThanhtoan;
    private javax.swing.JComboBox<String> statusComboBox1;
    private javax.swing.JTable tblHoaDon;
    private javax.swing.JTextField txtGiaPhong;
    private javax.swing.JTextField txtIDHopDong;
    private javax.swing.JTextField txtKhauTru;
    private javax.swing.JTextField txtSoDienCu;
    private javax.swing.JTextField txtSoDienMoi;
    private javax.swing.JTextField txtSoNuocCu;
    private javax.swing.JTextField txtSoNuocMoi;
    private javax.swing.JTextField txtTienInternet;
    private javax.swing.JTextField txtTienNo;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
