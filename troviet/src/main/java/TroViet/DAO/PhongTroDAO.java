/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TroViet.DAO;

import TroViet.Connect.SQL_Connection;
import TroViet.Model.DanhGia;
import TroViet.Model.PhongTro;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author WINDOWS
 */
public class PhongTroDAO extends SQL_Connection {
    
    public ArrayList<PhongTro> loadPhongTroData(String selectedStatus) throws SQLException {
        ArrayList<PhongTro> phongtro = new ArrayList<>();
        Statement statement = con.createStatement();
          String query;
            if (selectedStatus == PhongTroEnum.Not_Yet.getName()) {
              query = "SELECT * FROM PhongTro WHERE Status = " + PhongTroEnum.Not_Yet.getId();
            } else if(selectedStatus == PhongTroEnum.Rented.getName()) {
                query = "SELECT * FROM PhongTro WHERE Status = " + PhongTroEnum.Rented.getId();
            } else if(selectedStatus == PhongTroEnum.Waiting.getName()) {
                query = "SELECT * FROM PhongTro WHERE Status = " + PhongTroEnum.Waiting.getId();
            }else {
                query = "SELECT * FROM PhongTro WHERE trash != 0";
            }
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            PhongTro pt = PhongTro.getFromResultSet(rs);
            phongtro.add(pt);
        }
        return phongtro;
    }

    public ArrayList<PhongTro> troInTrangChu() {
        ArrayList<PhongTro> ptro = new ArrayList<>();
        try {
            String query = "select TenPhong, DienTich, GiaPhong, DiaChi, MoTa from PhongTro where PhongTro.idNguoiDung is null;";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PhongTro tro = new PhongTro();
                tro.setTenphong(rs.getString("TenPhong"));
                tro.setDientich(rs.getDouble("DienTich"));
                tro.setGiaphong(rs.getDouble("GiaPhong"));
                tro.setDiachi(rs.getString("DiaChi"));
                tro.setMota(rs.getString("MoTa"));
                ptro.add(tro);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ptro;
    }

    public ArrayList<PhongTro> getAll() throws SQLException {
        ArrayList<PhongTro> phongtro = new ArrayList<>();
        Statement statement = con.createStatement();
        String query = "SELECT * FROM PhongTro where trash !=0 order by Status desc";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            PhongTro pt = PhongTro.getFromResultSet(rs);
            phongtro.add(pt);
        }
        return phongtro;
    }

    public PhongTro get(Long id) throws SQLException {
        Statement statement = con.createStatement();
        String query = "SELECT * FROM PhongTro WHERE PhongTro.id = " + id;
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            PhongTro pt = PhongTro.getFromResultSet(rs);
            return pt;
        }
        return null;
    }

    public void save(PhongTro t) throws SQLException {
        if (t == null) {
            throw new SQLException("Phòng trọ trống");
        }
        String query = "INSERT INTO PhongTro (idMaLoaiPhong, idNguoiDung, idKhuvuc, TenPhong,DienTich, GiaPhong, DiaChi, MoTa, Status)"
                + " VALUES (?, ?, ?, ?, ?,?,?,?,?)";

        PreparedStatement stmt = con.prepareStatement(query);

        stmt.setInt(1, t.getIdMaloaiphong());
        stmt.setLong(2, t.getIdNguoidung());
        stmt.setInt(3, t.getIdKhuvuc());
        stmt.setString(4, t.getTenphong());
        stmt.setDouble(5, t.getDientich());
        stmt.setDouble(6, t.getGiaphong());
        stmt.setString(7, t.getDiachi());
        stmt.setString(8, t.getMota());
        stmt.setString(9, t.getStatus().getId());
        int row = stmt.executeUpdate();
    }
    
    public boolean dangKyPhong(long idNguoiDung, long idPhongTro) throws SQLException {
        String query = "update PhongTro set Status = 2, IdNguoiDung = ? where Id = ?;";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setLong(1, idNguoiDung);
        ps.setLong(2, idPhongTro);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected > 0; // Trả về true nếu cập nhật thành công, ngược lại trả về false
    }

    public void update(PhongTro t) throws SQLException {
        if (t == null) {
            throw new SQLException("Phòng Trọ rỗng");
        }
        String query = "UPDATE PhongTro SET IdMaLoaiPhong = ?, IdNguoiDung = ?, IdKhuVuc = ?, TenPhong = ?, DienTich = ?, GiaPhong = ?, DiaChi = ?, MoTa = ?, Status = ? WHERE Id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, t.getIdMaloaiphong());
        stmt.setLong(2, t.getIdNguoidung());
        stmt.setInt(3, t.getIdKhuvuc());
        stmt.setString(4, t.getTenphong());
        stmt.setDouble(5, t.getDientich());
        stmt.setDouble(6, t.getGiaphong());
        stmt.setString(7, t.getDiachi());
        stmt.setString(8, t.getMota());

        stmt.setString(9, t.getStatus().getId());
        stmt.setLong(10, t.getId());

        System.out.println("DEBUG - SQL Query: " + stmt.toString()); // In câu truy vấn SQL
        int rowsAffected = stmt.executeUpdate();

        System.out.println("DEBUG - Rows Affected: " + rowsAffected); // In số dòng bị ảnh hưởng
        if (rowsAffected > 0) {
            System.out.println("Dữ liệu đã được cập nhật thành công.");
        } else {
            System.out.println("Không tìm thấy dữ liệu phù hợp để cập nhật.");
        }
    }
    
    public int updateStatus(Long id) {
        try {
            String query = "update PhongTro set Status=1 where Id=?;";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setLong(1, id);
            if (ps.executeUpdate() > 0) {
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void deleteFromRecycle(Long id) throws SQLException {
        if (id == null) {
            throw new SQLException("Phòng Trọ rỗng");
        }
        String query = "UPDATE PhongTro SET trash = ?  WHERE PhongTro.id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, 0);
        stmt.setLong(2, id);
        stmt.executeUpdate();
    }

    public ArrayList<PhongTro> searchByKey(String keyword) throws SQLException {
        ArrayList<PhongTro> pt = new ArrayList<>();
        Statement statement = con.createStatement();
        String query = "SELECT * FROM PhongTro WHERE idMaLoaiPhong LIKE '%" + keyword + "%';";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            PhongTro phongtro = PhongTro.getFromResultSet(rs);
            pt.add(phongtro);
        }
        return pt;
    }

    public ArrayList<PhongTro> getAllFromRecycle() throws SQLException {
        ArrayList<PhongTro> phongtro = new ArrayList<>();
        Statement statement = con.createStatement();
        String query = "SELECT * FROM PhongTro where trash = 0";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            PhongTro pt = PhongTro.getFromResultSet(rs);
            phongtro.add(pt);
        }
        return phongtro;
    }

    public void deleteById(Long id) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("DELETE FROM PhongTro WHERE PhongTro.id = ?");
        stmt.setLong(1, id);
        stmt.executeUpdate();
    }

    public void RestoreFromRecycle(Long id) throws SQLException {
        if (id == null) {
            throw new SQLException("phòng trọ rỗng");
        }
        String query = "UPDATE PhongTro SET trash = ?  WHERE PhongTro.id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, 1);
        stmt.setLong(2, id);
        stmt.executeUpdate();
    }

    

}
