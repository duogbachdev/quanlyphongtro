package TroViet.DAO;

import TroViet.Connect.SQL_Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import TroViet.Model.HopDongEntity;

public class HopDongDAO extends SQL_Connection {

//    String url = "jdbc:sqlserver://localhost:1433;databaseName=TroViet";
//    String username = "sa";
//    String password = "123";
//
//    private Connection connection;
//
//    public HopDongDAO() {
//        try {
//            connection = DriverManager.getConnection(url, username, password);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    public void save(HopDongEntity t) throws SQLException {

        if (t == null) {
            throw new SQLException("HopDong rỗng");
        }
        String query = "{CALL InsertHopDong(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        PreparedStatement stmt = con.prepareStatement(query);

        stmt.setLong(1, t.getIdMaPhongTro());
        stmt.setLong(2, t.getIdNguoiDung());
        stmt.setString(3, t.getNgayBatDauThue());
        stmt.setString(4, t.getNgayKetThucThue());
        stmt.setFloat(5, t.getTienDatCoc());
        stmt.setFloat(6, t.getGiaPhong());
        stmt.setFloat(7, t.getGiaDien());
        stmt.setFloat(8, t.getGiaNuoc());
        stmt.setFloat(9, t.getGiaInternet());
        stmt.setFloat(10, t.getGiaRac());
        stmt.setString(11, t.getNgayTao());
        stmt.setString(12, t.getNguoiTao());
        stmt.setString(13, t.getNgayBatDauThue()); // Set null for NgaySua
        stmt.setString(14, "thanh"); // Set null for NguoiSua
        stmt.setInt(15, t.getTrash());
        stmt.setString(16, t.getStatus().getId());
//    stmt.setLong(1, 1);
//        stmt.setLong(2, 1);
//        stmt.setString(3, "2023-07-15");
//        stmt.setString(4, "2023-07-15");
//        stmt.setFloat(5, 1);
//        stmt.setFloat(6, 1);
//        stmt.setFloat(7, 1);
//        stmt.setFloat(8, 1);
//        stmt.setFloat(9, 1);
//        stmt.setFloat(10, 1);
//        stmt.setString(11, "2023-07-15");
//        stmt.setString(12, "thanh123");
//        stmt.setString(13, "2023-07-15"); // Set null for NgaySua
//        stmt.setString(14, "thanh"); // Set null for NguoiSua
//        stmt.setInt(15, 1);
//        stmt.setString(16, t.getStatus().getId());
        int row = stmt.executeUpdate();
    }

    public int updateOrderById(HopDongEntity t) throws SQLException {
        try {
            if (t == null) {
                throw new SQLException("HopDongEntity rỗng");
            }
//            String query = "{CALL UpdateHopDong(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
            String query = "UPDATE HopDong SET IdMaPhongTro = ?, IdNguoiDung = ?,NgayBatDauThue = ?, NgayKetThucThue = ?, TienDatCoc = ?, GiaPhong = ?, "
                    + "GiaDien = ?, GiaNuoc = ?, GiaInternet = ?, GiaRac = ?, NgaySua = ?, NguoiSua = ?, Trash = ?, Status = ? "
                    + "WHERE Id = ?";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setLong(1, t.getIdMaPhongTro());
            stmt.setLong(2, t.getIdNguoiDung());
            stmt.setString(3, t.getNgayBatDauThue());
            stmt.setString(4, t.getNgayKetThucThue());
            stmt.setFloat(5, t.getTienDatCoc());
            stmt.setFloat(6, t.getGiaPhong());
            stmt.setFloat(7, t.getGiaDien());
            stmt.setFloat(8, t.getGiaNuoc());
            stmt.setFloat(9, t.getGiaInternet());
            stmt.setFloat(10, t.getGiaRac());
            stmt.setString(11, t.getNgayBatDauThue()); // Set null for NgaySua
            stmt.setString(12, t.getNguoiSua()); // Set null for NguoiSua
            stmt.setInt(13, t.getTrash());
            stmt.setString(14, t.getStatus().getId());
            stmt.setLong(15, t.getId());
            if (stmt.executeUpdate() > 0) {
                System.out.println("Success");
                return 1;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (t == null) {
            throw new SQLException("HopDong rỗng");
        }

        return -1;
    }

    public void deleteFromRecycle(Long id) throws SQLException {
        if (id == null) {
            throw new SQLException("Order rỗng");
        }
        String query = "UPDATE HopDong SET trash = ? and status = 0 WHERE Id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, 0);
        stmt.setLong(2, id);
        stmt.executeUpdate();
    }

    public static HopDongEntity getFromResultSet(ResultSet rs) throws SQLException {
        HopDongEntity o = new HopDongEntity();
        o.setId(rs.getLong("Id"));
        o.setIdMaPhongTro(rs.getLong("IdMaPhongTro"));
        o.setIdNguoiDung(rs.getLong("IdNguoiDung"));
        o.setNgayBatDauThue(rs.getString("NgayBatDauThue"));
        o.setNgayKetThucThue(rs.getString("NgayKetThucThue"));
        o.setTienDatCoc(rs.getFloat("TienDatCoc"));
        o.setGiaPhong(rs.getFloat("GiaPhong"));
        o.setGiaDien(rs.getFloat("GiaDien"));
        o.setGiaNuoc(rs.getFloat("GiaNuoc"));
        o.setGiaInternet(rs.getFloat("GiaInternet"));
        o.setGiaRac(rs.getFloat("GiaRac"));
        o.setNgayTao(rs.getString("NgayTao"));
        o.setNguoiTao(rs.getString("NguoiTao"));
        o.setNgaySua(rs.getString("NgaySua"));
        o.setNguoiSua(rs.getString("NguoiSua"));
        o.setTrash(rs.getInt("Trash"));
        o.setStatus(HopDongEnum.getById(rs.getString("status")));

        return o;
    }

    public HopDongEntity get(Long id) throws SQLException {
        Statement statement = con.createStatement();
        String query = "SELECT * FROM HopDong WHERE Id = " + id;
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            HopDongEntity hopDong = getFromResultSet(rs);
//            order.setMonth(monthDao.get(order.getMonthId()));
//            order.setService(serviceDao.get(order.getServiceId()));
//            order.setApartment(apartmentDao.get(order.getApartmentId()));
            return hopDong;
        }
        return null;
    }

    public boolean isHopDongExists(Long id) throws SQLException {
        Statement statement = con.createStatement();
        String query = "SELECT COUNT(*) FROM HopDong WHERE Id = " + id;
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            int count = rs.getInt(1);
            return count > 0;
        }
        return false;
    }
    public boolean isIdMaPhongTroExists(Long id) throws SQLException {
        Statement statement = con.createStatement();
        String query = "SELECT IdMaPhongTro FROM HopDong WHERE Trash = 1 and Status = 1 and  IdMaPhongTro = " + id;
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            int count = rs.getInt(1);
            return count > 0;
        }
        return false;
    }
    public boolean isIdNguoiDungTroExists(Long id) throws SQLException {
        Statement statement = con.createStatement();
        String query = "SELECT IdNguoiDung FROM HopDong WHERE Trash = 1 and Status = 1 and  IdNguoiDung = " + id;
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            int count = rs.getInt(1);
            return count > 0;
        }
        return false;
    }
    public ArrayList<HopDongEntity> getAll() throws SQLException {
        ArrayList<HopDongEntity> orders = new ArrayList<>();
        Statement statement = con.createStatement();
        String query = "SELECT * FROM HopDong where trash !=0 and status = 1 ";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            HopDongEntity order = getFromResultSet(rs);

            orders.add(order);
        }
        return orders;
    }

    public ArrayList<HopDongEntity> getAllFromRecycle() throws SQLException {
        ArrayList<HopDongEntity> orders = new ArrayList<>();
        Statement statement = con.createStatement();
        String query = "SELECT * FROM HopDong where trash = 0 ";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            HopDongEntity order = getFromResultSet(rs);

            orders.add(order);
        }
        return orders;
    }

    public void ngayKetThucThue() throws SQLException {

        Statement statement = con.createStatement();
        String query = "UPDATE HopDong SET Status = 1 WHERE GETDATE() >= NgayKetThucThue";
        int rowsUpdated = statement.executeUpdate(query);

        statement.close();

    }

    public ArrayList<HopDongEntity> searchByKey(String word) throws SQLException {
        ArrayList<HopDongEntity> orders = new ArrayList<>();
        Statement statement = con.createStatement();
        String query = "SELECT * FROM HopDong WHERE Id = '" + word + "' AND status = 1 AND trash != 0";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            HopDongEntity order = getFromResultSet(rs);

            orders.add(order);
        }
        return orders;
    }

    public void deleteById(Long id) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("DELETE FROM HopDong WHERE Id = ?");
        stmt.setLong(1, id);
        stmt.executeUpdate();
    }

    public void RestoreFromRecycle(Long id) throws SQLException {
        if (id == null) {
            throw new SQLException("HopDong rỗng");
        }
        String query = "UPDATE HopDong SET trash = ? , Status = 1  WHERE Id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, 1);
        stmt.setLong(2, id);
        stmt.executeUpdate();
    }

    public ArrayList<HopDongEntity> getHopDongByEmailUser(String email) throws SQLException {
        ArrayList<HopDongEntity> hopDong = new ArrayList<>();
//    String query = "SELECT TOP 1 * " +
//                   "FROM HopDong " +
//                   "JOIN NguoiDung ON HopDong.IdNguoiDung = NguoiDung.Id " +
//                   "WHERE NguoiDung.Email = ? AND HopDong.Trash = 1 " +
//                   "ORDER BY HopDong.NgayTao DESC;";

        String query = "SELECT TOP 1 * FROM HopDong WHERE HopDong.IdMaPhongTro IN (SELECT PhongTro.Id FROM PhongTro join NguoiDung on PhongTro.IdNguoiDung = NguoiDung.Id WHERE NguoiDung.Email = ?) AND HopDong.Trash = 1 AND HopDong.Status = 1 ORDER BY HopDong.NgayTao DESC";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                HopDongEntity hopDongs = getFromResultSet(rs);
                hopDong.add(hopDongs);
             
            }

        }

        return hopDong;
    }

}
