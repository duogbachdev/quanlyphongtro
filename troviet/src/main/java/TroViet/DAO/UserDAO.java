/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TroViet.DAO;

import TroViet.Connect.SQL_Connection;
import java.sql.*;
import java.time.LocalDateTime;
import javax.swing.JOptionPane;
import TroViet.Model.UserEntity;
import org.mindrot.jbcrypt.BCrypt;
import TroViet.Utils.SessionManager;
import java.util.ArrayList;

/**
 *
 * @author WINDOWS
 */
public class UserDAO extends SQL_Connection {

//    String url = "jdbc:sqlserver://localhost:1433;databaseName=TroViet";
//    String username = "sa";
//    String password = "123";
//
//    private Connection connection;
//
//    public UserDAO() {
//        try {
//            connection = DriverManager.getConnection(url, username, password);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    public void saveAccountToDatabase(String hoTen, String dienThoai, String email, String diaChi, String matKhau, String vaiTro) {
//          String user = SessionManager.getSession().getUser().getHoTen();
        try {
            LocalDateTime currentTime = LocalDateTime.now();
            String sql = "INSERT INTO NguoiDung (HoTen, DienThoai, Email, DiaChi, MatKhau, VaiTro, NgayTao, NguoiTao) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, hoTen);
            statement.setString(2, dienThoai);
            statement.setString(3, email);
            statement.setString(4, diaChi);
            statement.setString(5, matKhau);
            statement.setString(6, vaiTro);
            statement.setObject(7, currentTime);
            statement.setString(8, hoTen); // Thay thế "your_nguoitao_value" bằng giá trị tương ứng
             // Thay thế "your_nguoisua_value" bằng giá trị tương ứng

            statement.executeUpdate();

            System.out.println("success");
        } catch (SQLException ex) {
            System.out.println("falll");
            ex.printStackTrace();
        }
    }

    public void saveNewPassword(String email, String newPassword) {
        String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        try {
            String query = "UPDATE NguoiDung SET MatKhau = ? WHERE Email = ?";
            PreparedStatement statement = con.prepareStatement(query);
            statement.setString(1, hashedPassword);
            statement.setString(2, email);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserEntity getUserEntity(String username) {
        UserEntity ad = new UserEntity();
        try {
            String query = "Select * from NguoiDung where HoTen = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ad.setHoTen(rs.getNString(2));
                ad.setPassword(rs.getNString(6));

                return ad;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static UserEntity getFromResultSet(ResultSet rs) throws SQLException {
        UserEntity e = new UserEntity();
        e.setId(rs.getLong("Id"));
        e.setHoTen(rs.getString("HoTen"));
        e.setDienThoai(rs.getString("DienThoai"));
        e.setEmail(rs.getString("Email"));
        e.setDiaChi(rs.getString("DiaChi"));
        e.setPassword(rs.getString("MatKhau"));
        e.setRole(UserRole.getById(rs.getString("VaiTro")));

        return e;
    }

    public UserEntity findByUsername(String name) throws SQLException {
        PreparedStatement statement = null;
        ResultSet rs = null;
        UserEntity nguoiDung = null;

        try {
            String query = "SELECT * FROM NguoiDung WHERE NguoiDung.HoTen = ?";
            statement = con.prepareStatement(query);
            statement.setString(1, name);
            rs = statement.executeQuery();

            if (rs.next()) {
                nguoiDung = getFromResultSet(rs);
            }
        } finally {

        }

        return nguoiDung;
    }

    public UserEntity findByPhone(String phone) throws SQLException {
        PreparedStatement statement = null;
        ResultSet rs = null;
        UserEntity nguoiDung = null;

        try {
            String query = "SELECT * FROM NguoiDung WHERE NguoiDung.DienThoai = ?";
            statement = con.prepareStatement(query);
            statement.setString(1, phone);
            rs = statement.executeQuery();

            if (rs.next()) {

                nguoiDung = getFromResultSet(rs);

            }
        } finally {

        }

        return nguoiDung;
    }

    public boolean checkLoginUserEntity(String username, String password) throws SQLException {
        UserEntity ad = getUserEntity(username);

        if (ad != null) {
            if (ad.getPassword().equals(password) && ad.getRole().getName().equals("Chủ trọ")) {
//                QLCHMainFrame mainViewUserEntity = new QLCHMainFrame();
//                mainViewUserEntity.setVisible(true);
                JOptionPane.showMessageDialog(null, "Đăng nhập với vai trò Ban Quản Lý thành công!");
                return true;
            }
        }

        JOptionPane.showMessageDialog(null, "Đăng nhập thất bại!");
        return false;
    }

    public UserEntity get(Long id) throws SQLException {
        Statement statement = con.createStatement();
        String query = "SELECT * FROM NguoiDung WHERE NguoiDung.id = " + id;
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            UserEntity NguoiDung = getFromResultSet(rs);
            return NguoiDung;
        }
        return null;
    }

    public UserEntity get(int id) throws SQLException {
        Statement statement = con.createStatement();
        String query = "SELECT * FROM NguoiDung WHERE NguoiDung.id = " + id;
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            UserEntity NguoiDung = getFromResultSet(rs);
            return NguoiDung;
        }
        return null;
    }

    public boolean isEmailExists(String email) throws SQLException {
        PreparedStatement statement = null;
        ResultSet rs = null;
        UserEntity nguoiDung = null;

        String query = "SELECT COUNT(*) FROM NguoiDung WHERE Email = ?";
        statement = con.prepareStatement(query);
        statement.setString(1, email);

        rs = statement.executeQuery();

        if (rs.next()) {
            int count = rs.getInt(1);
            return count > 0;
        }
        return false;
    }
    
    public boolean isPhoneExists(String phone) throws SQLException {
        String query = "SELECT COUNT(*) FROM NguoiDung WHERE DienThoai = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, phone);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            int count = rs.getInt(1);
            return count>0;
        }
        return false;
    }
    
    public int updateUser(UserEntity user) throws SQLException {
        try {
            String query = "UPDATE NguoiDung SET HoTen=?, DiaChi=?, Email=?, DienThoai=? WHERE Id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, user.getHoTen());
            ps.setString(2, user.getDiaChi());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getDienThoai());
            ps.setLong(5, user.getId());
            if (ps.executeUpdate() > 0) {
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (user == null) {
            throw new SQLException("User rỗng");
        }
        return -1;
    }
    
    public int updateRole(Long id) {
        try {
            String query = "update NguoiDung set VaiTro='Tenants' where Id=?;";
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

    public String getPasswordByID(long id) throws SQLException {
        String query = "select MatKhau from NguoiDung where ID = " + id;
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        if(rs.next()) {
            return rs.getString(1);
        }
        return null;
    }
    
    public boolean updatePassword(Long id, String newPassword) throws SQLException {
        // Cập nhật mật khẩu mới vào CSDL
        String updateSql = "UPDATE NguoiDung SET MatKhau = ? WHERE Id = ?";
        PreparedStatement updateStatement = con.prepareStatement(updateSql);
        updateStatement.setString(1, newPassword);
        updateStatement.setLong(2, id);
        int rowsAffected = updateStatement.executeUpdate();

        return rowsAffected > 0; // Trả về true nếu cập nhật thành công, ngược lại trả về false
    }
    
    public ArrayList<UserEntity> getAll() throws SQLException {
        ArrayList<UserEntity> orders = new ArrayList<>();
        Statement statement = con.createStatement();
        String query = "SELECT * FROM NguoiDung where trash !=0 ";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            UserEntity order = getFromResultSet(rs);
            orders.add(order);
        }
        return orders;
    }
    
    public ArrayList<UserEntity> searchByKey(String word) throws SQLException {
        ArrayList<UserEntity> orders = new ArrayList<>();
        Statement statement = con.createStatement();
        String query = "SELECT * FROM NguoiDung WHERE Id = '" + word + "'OR DienThoai = '" + word + "' AND status = 1 AND trash != 0";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            UserEntity order = getFromResultSet(rs);
            orders.add(order);
        }
        return orders;
    }
    
    public int SuaND(UserEntity t)throws SQLDataException, SQLException{
        try {
            LocalDateTime currentTime = LocalDateTime.now();
            if (t == null) {
                throw new SQLException("NguoiDungEntity rỗng");
            }
            String query = "UPDATE NguoiDung SET HoTen = ?, DienThoai= ?, Email= ?, DiaChi= ?, VaiTro=?, NgaySua=?, NguoiSua=? WHERE Id = ?;";
            PreparedStatement stmt = con.prepareStatement(query);

            stmt.setString(1, t.getHoTen());
            stmt.setString(2, t.getDienThoai());
            stmt.setString(3, t.getEmail());
            stmt.setString(4, t.getDiaChi());
            stmt.setString(5, t.getRole().toString());
            stmt.setLong(8, t.getId());
            stmt.setObject(6, currentTime);
            stmt.setString(7, SessionManager.getSession().getUser().getHoTen());
         if (stmt.executeUpdate() > 0) {
                System.out.println("Success");
                return 1;
            }   
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (t == null) {
            throw new SQLException("NguoiDung rỗng");
        }
        return -1;
    }
    
    public void deleteFromRecycle(Long id) throws SQLException {
        if (id == null) {
            throw new SQLException("Order rỗng");
        }
        String query = "UPDATE NguoiDung SET trash = ? WHERE Id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, 0);
        stmt.setLong(2, id);
        stmt.executeUpdate();
    }
    
    public ArrayList<UserEntity> getAllFromRecycle() throws SQLException {
        ArrayList<UserEntity> orders = new ArrayList<>();
        Statement statement = con.createStatement();
        String query = "SELECT * FROM NguoiDung where trash = 0 ";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            UserEntity order = getFromResultSet(rs);
            orders.add(order);
        }
        return orders;
    }
    
    public void RestoreFromRecycle(Long id) throws SQLException {
        if (id == null) {
            throw new SQLException("NguoiDung rỗng");
        }
        String query = "UPDATE NguoiDung SET trash = ? , Status = 1  WHERE Id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, 1);
        stmt.setLong(2, id);
        stmt.executeUpdate();
    }
    
    public void deleteById(Long id) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("DELETE FROM NguoiDung WHERE Id = ?");
        stmt.setLong(1, id);
        stmt.executeUpdate();
    }
}
