package TroViet.DAO;

import TroViet.Connect.SQL_Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import TroViet.Model.SessionEntity;

    public class SessionDAO extends SQL_Connection {

    UserDAO userDao = new UserDAO();
//    String url = "jdbc:sqlserver://localhost:1433;databaseName=TroViet";
//    String username = "sa";
//    String password = "123";
//
//    private Connection connection;
//
//    public SessionDAO() {
//        try {
//            connection = DriverManager.getConnection(url, username, password);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public static SessionEntity session;

    public static SessionEntity getSession() {
        return session;
    }

    public static SessionEntity getFromResultSet(ResultSet rs) throws SQLException {
        SessionEntity s = new SessionEntity();
        s.setId(rs.getInt("id"));
        s.setIdUser(rs.getLong("IdNguoiDung"));
        s.setMessage(rs.getNString("NoiDung"));
        s.setStartTime(rs.getTimestamp("ThoiGianDangNhap"));
        s.setEndTime(rs.getTimestamp("ThoiGianDangXuat"));
        return s;
    }

    public ArrayList<SessionEntity> getAll() throws SQLException {
        ArrayList<SessionEntity> sessions = new ArrayList<>();
        Statement statement = con.createStatement();
        String query = "SELECT * FROM session  ORDER BY session.ThoiGianDangNhap DESC";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            SessionEntity session = getFromResultSet(rs);
            session.setUser(userDao.get(session.getIdUser()));
            sessions.add(session);
        }
        return sessions;
    }

    public SessionEntity get(Long id) throws SQLException {
        Statement statement = con.createStatement();
        String query = "SELECT * FROM session WHERE id = " + id;
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            SessionEntity session = getFromResultSet(rs);
            session.setUser(userDao.get(session.getIdUser()));
            return session;
        }
        return null;
    }

    public ArrayList<SessionEntity> getSessionUser(Long idUser) throws SQLException {
        ArrayList<SessionEntity> listSession = new ArrayList<>();
        String query = "SELECT * FROM session WHERE IdNguoiDung = ? ORDER BY session.ThoiGianDangNhap DESC";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setLong(1, idUser);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            SessionEntity session = getFromResultSet(rs);
            session.setUser(userDao.get(session.getIdUser()));
            listSession.add(session);
        }
        return listSession;
    }

    public ArrayList<SessionEntity> getSession(Long id) throws SQLException {
        ArrayList<SessionEntity> sessions = new ArrayList<>();
        Statement statement = con.createStatement();
        String query = "SELECT * FROM session WHERE IdNguoiDung = " + id + " ORDER BY session.ThoiGianDangNhap DESC";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            SessionEntity session = getFromResultSet(rs);
            session.setUser(userDao.get(session.getIdUser()));
            sessions.add(session);
        }
        return sessions;
    }

    public void save(SessionEntity t) throws SQLException {
        if (t == null) {
            throw new SQLException("Shipment rỗng");
        }
        String query = "INSERT INTO session (IdNguoiDung, ThoiGianDangNhap, ThoiGianDangXuat , NoiDung) VALUES (?, ?, ?, ?)";

        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setLong(1, t.getIdUser());
        stmt.setTimestamp(2, t.getStartTime());
        stmt.setTimestamp(3, t.getEndTime());
        stmt.setNString(4, t.getMessage());
        int row = stmt.executeUpdate();
    }

    public void update(SessionEntity t) throws SQLException {
        if (t == null) {
            throw new SQLException("Entity rỗng");
        }
        String query = "UPDATE session SET ThoiGianDangNhap = ?, ThoiGianDangXuat = ?, NoiDung = ? WHERE session.id = ?";

        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setTimestamp(1, t.getStartTime());
        stmt.setTimestamp(2, t.getEndTime());
        stmt.setNString(3, t.getMessage());
        stmt.setInt(4, t.getId());
        int row = stmt.executeUpdate();
    }

    public void delete(SessionEntity t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void deleteById(Long id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SessionEntity getLast(Long IdNguoiDung) throws SQLException {
        Statement statement = con.createStatement();
        String query = "SELECT top 1* FROM session WHERE IdNguoiDung = " + IdNguoiDung
                + " ORDER BY id DESC ";
        ResultSet rs = statement.executeQuery(query);
        if (rs.next()) {
            SessionEntity session = getFromResultSet(rs);
            session.setUser(userDao.get(session.getIdUser()));
            return session;
        }
        return null;
    }

    public ArrayList<SessionEntity> getAll(Timestamp start, Timestamp end) throws SQLException {
        ArrayList<SessionEntity> sessions = new ArrayList<>();
        String query = "SELECT * FROM session WHERE NoiDung = ? AND DATE(ThoiGianDangNhap) >= DATE(?) AND DATE(ThoiGianDangNhap) <= DATE(?) ORDER BY session.ThoiGianDangNhap DESC";
        PreparedStatement statement = con.prepareStatement(query);
        statement.setNString(1, "logout");
        statement.setTimestamp(2, start);
        statement.setTimestamp(3, end);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            SessionEntity session = getFromResultSet(rs);
            session.setUser(userDao.get(session.getIdUser()));
            sessions.add(session);
        }
        return sessions;
    }

}
