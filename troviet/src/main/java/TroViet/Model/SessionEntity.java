package TroViet.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


public class SessionEntity {
    private Long  idUser;
    private int id;
    private Timestamp startTime, endTime;
    private UserEntity user;
    private String message;

    public SessionEntity() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public void setUser(UserEntity user) {
        this.user = user;
        if (user != null) {
            this.idUser = user.getId();
        }
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static SessionEntity getFromResultSet(ResultSet rs) throws SQLException {
        SessionEntity s = new SessionEntity();
        s.setId(rs.getInt("id"));
        s.setIdUser(rs.getLong("idUser"));
        s.setMessage(rs.getNString("message"));
        s.setStartTime(rs.getTimestamp("startTime"));
        s.setEndTime(rs.getTimestamp("endTime"));
        return s;
    }

    @Override
    public String toString() {
        return "Session{" + "id=" + id + ", idUser=" + idUser + ", startTime=" + startTime + ", endTime=" + endTime + ", message=" + message + '}';
    }

}
