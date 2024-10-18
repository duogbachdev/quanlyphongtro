package TroViet.Utils;


import TroViet.DAO.SessionDAO;
import java.sql.SQLException;
import java.sql.Timestamp;
import TroViet.Model.SessionEntity;
import TroViet.Model.UserEntity;
import javax.swing.JOptionPane;






public class SessionManager {

    public static SessionEntity session;
    static SessionDAO sessionDao = new SessionDAO();

    public SessionManager() {
    }

    public static SessionEntity getSession() {
        return session;
    }

    public static void setSession(SessionEntity session) {
        SessionManager.session = session;
    }

    public static void create(UserEntity e) throws SQLException {
        if (e == null) {
            throw new SQLException("Nguoi dung khong hop le!");
        }
        SessionEntity ss = new SessionEntity();
        ss.setUser(e);
        ss.setMessage("login");
        ss.setStartTime(new Timestamp(System.currentTimeMillis()));
        sessionDao.save(ss);
        SessionEntity sss = sessionDao.getLast(e.getId());
        setSession(sss);
    }

    public static void update() throws SQLException {
        if (session == null) {
            throw new SQLException("Ban chua dang nhap!");
        }
        session.setMessage("logout");
        session.setEndTime(new Timestamp(System.currentTimeMillis()));
        sessionDao.update(session);
        setSession(null);
    }

}
