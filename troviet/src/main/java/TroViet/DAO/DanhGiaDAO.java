/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TroViet.DAO;

import TroViet.Connect.SQL_Connection;
import TroViet.Model.DanhGia;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author WINDOWS
 */
public class DanhGiaDAO extends SQL_Connection{
    public ArrayList<DanhGia> getAll() throws SQLException {
        ArrayList<DanhGia> listDG = new ArrayList<>();
        String query = "select * from DanhGia";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            DanhGia dg = new DanhGia();
            dg.setId(rs.getLong(1));
            dg.setIdPhongTro(rs.getLong(2));
            dg.setIdNguoiDung(rs.getLong(3));
            dg.setDanhGia(rs.getInt(4));
            dg.setNoiDung(rs.getString(5));
            dg.setNguoiTao(rs.getString(7));
            listDG.add(dg);
        }
        return listDG;
    }
    
    public void add(DanhGia dgia) throws SQLException {
        String query = "insert into DanhGia (IdMaPhongTro, IdNguoiDung, DanhGia, NoiDung, NgayTao, NguoiTao) values (?,?,?,?,?,?);";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setLong(1,dgia.getIdPhongTro());
        ps.setLong(2, dgia.getIdNguoiDung());
        ps.setInt(3, dgia.getDanhGia());
        ps.setString(4, dgia.getNoiDung());
        ps.setString(5, dgia.getNgayTao());
        ps.setString(6, dgia.getNguoiTao());
        int row = ps.executeUpdate();
    }
    
    public static DanhGia getFromResultSet(ResultSet rs) throws SQLException {
        DanhGia dg = new DanhGia();
        dg.setId(rs.getLong("Id"));
        dg.setIdPhongTro(rs.getLong("IdMaPhongTro"));
        dg.setIdNguoiDung(rs.getLong("IdNguoiDung"));
        dg.setDanhGia(rs.getInt("DanhGia"));
        dg.setNoiDung(rs.getString("NoiDung"));
        dg.setNgayTao(rs.getString("NgayTao"));
        dg.setNguoiTao(rs.getString("NguoiTao"));
        return dg;
    }
    
    public DanhGia get(Long id) throws SQLException {
        Statement statement = con.createStatement();
        String query = "SELECT * FROM DanhGia WHERE Id = " + id;
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            DanhGia danhGia = getFromResultSet(rs);
            return danhGia;
        }
        return null;
    }
}
