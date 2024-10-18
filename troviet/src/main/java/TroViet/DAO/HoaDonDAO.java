/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TroViet.DAO;

import TroViet.Connect.SQL_Connection;
import TroViet.Model.HoaDon;
import java.beans.Statement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author WINDOWS
 */
public class HoaDonDAO extends SQL_Connection{
    public static HoaDon getFromResultSet(ResultSet rs) throws SQLException {
        HoaDon hdon = new HoaDon();
        hdon.setId(rs.getLong("Id"));
        hdon.setIdHopDong(rs.getLong("IdHopDong"));
        hdon.setNgayBD(rs.getString("NgayBatDau"));
        hdon.setNgayKT(rs.getString("NgayKetThuc"));
        hdon.setSoDienCu(rs.getFloat("SoDienCu"));
        hdon.setSoDienMoi(rs.getFloat("SoDienMoi"));
        hdon.setSoNuocCu(rs.getFloat("SoNuocCu"));
        hdon.setSoNuocMoi(rs.getFloat("SoNuocMoi"));
        hdon.setKhauTru(rs.getFloat("KhauTru"));
        hdon.setNo(rs.getFloat("No"));
        hdon.setTongCong(rs.getFloat("TongCong"));
        hdon.setNgayTao(rs.getString("NgayTao"));
        hdon.setNguoiTao(rs.getString("NguoiTao"));
        hdon.setNgaySua(rs.getString("NgaySua"));
        hdon.setNguoiSua(rs.getString("NguoiSua"));
        hdon.setTrash(rs.getInt("Trash"));
        hdon.setStatus(HoaDonEnum.getById((rs.getString("Status"))));
        return hdon;
    }
    
    public ArrayList<HoaDon> searchByID(long idHopDong) throws SQLException {
        ArrayList<HoaDon> listHDon = new ArrayList<>();
        String query = "select * from HoaDon where IdHopDong = ? and trash!=0;";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setLong(1, idHopDong);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            HoaDon hdon = getFromResultSet(rs);
            listHDon.add(hdon);
        }
        return listHDon;
    }
    
    public void add(HoaDon hdon) throws SQLException{
        String query = "insert into HoaDon (IdHopDong, NgayBatDau, NgayKetThuc, SoDienCu, SoDienMoi, SoNuocCu, SoNuocMoi, KhauTru, TienNo, TongCong, NgayTao, NguoiTao, Status)\n"
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setLong(1, hdon.getIdHopDong());
        ps.setString(2, hdon.getNgayBD());
        ps.setString(3, hdon.getNgayKT());
        ps.setFloat(4, hdon.getSoDienCu());
        ps.setFloat(5, hdon.getSoDienMoi());
        ps.setFloat(6, hdon.getSoNuocCu());
        ps.setFloat(7, hdon.getSoNuocMoi());
        ps.setFloat(8, hdon.getKhauTru());
        ps.setFloat(9, hdon.getNo());
        ps.setFloat(10, hdon.getTongCong());
        ps.setString(11, hdon.getNgayTao());
        ps.setString(12, hdon.getNguoiTao());
        ps.setString(13, hdon.getStatus().getId());
        int row = ps.executeUpdate();
    }
    
    public int updateByID(HoaDon hdon) {
        try {
            String query = "update HoaDon \n"
                    + "set IdHopDong=?, NgayBatDau=?, NgayKetThuc=?, SoDienCu=?, SoDienMoi=?, SoNuocCu=?, SoNuocMoi=?, KhauTru=?, TienNo=?, TongCong=?, NgaySua=?, NguoiSua=?, Trash=?, Status=?\n"
                    + "where Id=?;";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setLong(1, hdon.getIdHopDong());
            ps.setString(2, hdon.getNgayBD());
            ps.setString(3, hdon.getNgayKT());
            ps.setFloat(4, hdon.getSoDienCu());
            ps.setFloat(5, hdon.getSoDienMoi());
            ps.setFloat(6, hdon.getSoNuocCu());
            ps.setFloat(7, hdon.getSoNuocMoi());
            ps.setFloat(8, hdon.getKhauTru());
            ps.setFloat(9, hdon.getNo());
            ps.setFloat(10, hdon.getTongCong());
            ps.setString(11, hdon.getNgaySua());
            ps.setString(12, hdon.getNguoiSua());
            ps.setInt(13, hdon.getTrash());
            ps.setString(14, hdon.getStatus().getId());
            ps.setLong(15, hdon.getId());
            
            if (ps.executeUpdate()>0) {
                System.out.println("Success");
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public void deleteFromRecycle(Long id) throws SQLException {
        if (id == null) {
            throw new SQLException("ID trống");
        }
        String query = "UPDATE HoaDon SET Trash = ? WHERE Id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, 0);
        stmt.setLong(2, id);
        stmt.executeUpdate();
    }
    
    public void RestoreFromRecycle(Long id) throws SQLException {
        if (id == null) {
            throw new SQLException("ID rỗng");
        }
        String query = "UPDATE HoaDon SET trash = ? WHERE Id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, 1);
        stmt.setLong(2, id);
        stmt.executeUpdate();
    }
    
    public void deleteById(Long id) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("DELETE FROM HoaDon WHERE Id = ?");
        stmt.setLong(1, id);
        stmt.executeUpdate();
    }
}
