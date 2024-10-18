package TroViet.DAO;
import TroViet.Model.KhuVuc;
import TroViet.Connect.SQL_Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
public class KhuVucDAO extends SQL_Connection{
    public ArrayList<KhuVuc> getALL() throws SQLException {
        ArrayList<KhuVuc> listKhuVuc = new ArrayList<>();
        
        String query = "select * from KhuVuc;";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            KhuVuc kv = new KhuVuc();
            kv.setId(rs.getInt("Id"));
            kv.setTenKhuVuc(rs.getString("TenKhuVuc"));
            kv.setTang(rs.getInt("Tang"));
            kv.setDay(rs.getInt("DayNhaTro"));
            listKhuVuc.add(kv);
        }
        return listKhuVuc;
    }
    public KhuVuc get(int id) throws SQLException {
        Statement st = con.createStatement();
        String query = "select * from KhuVuc where Id = " + id;
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            KhuVuc kv = new KhuVuc();
            kv.setId(rs.getInt("Id"));
            kv.setTenKhuVuc(rs.getString("TenKhuVuc"));
            kv.setTang(rs.getInt("Tang"));
            kv.setDay(rs.getInt("DayNhaTro")); 
            return kv;
        }
        return null;
    }
    
    public void saveKV(KhuVuc kv) throws SQLException {
        String query = "insert into KhuVuc values (?,?,?);";
        PreparedStatement ps = con.prepareStatement(query);
//        ps.setInt(1, kv.getId());
        ps.setString(1, kv.getTenKhuVuc());
        ps.setInt(2, kv.getTang());
        ps.setInt(3, kv.getDay());
        int result = ps.executeUpdate();
    }
    
    public String existsKV(String tenKhuVuc) throws SQLException {
        String query = "select * from KhuVuc where TenKhuVuc = ?;";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, tenKhuVuc);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String yes = rs.getString("TenKhuVuc");
            return yes;
        }
        return null;
    }
    public int updateKVByID(KhuVuc kv) throws SQLException {
        try {
            String query ="update KhuVuc set TenKhuVuc=?, Tang=?, DayNhaTro=? where Id=?;";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, kv.getTenKhuVuc());
            ps.setInt(2, kv.getTang());
            ps.setInt(3, kv.getDay());
            ps.setInt(4, kv.getId());
            if (ps.executeUpdate() > 0) {
                return 1;
            }
        } catch (Exception e) {
            System.out.println("Success");
            e.printStackTrace();
        }
        if (kv == null) {
            throw new SQLException("Khuvuc rỗng");
        }
        return -1;
    }
    
    public void movedToBin(int idKV) throws SQLException {
        String query = "UPDATE KhuVuc SET trash = ? WHERE Id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, 0);
        stmt.setLong(2, idKV);
        stmt.executeUpdate();
    }
    
    public ArrayList<KhuVuc> getAllFromBin() throws SQLException {
        ArrayList<KhuVuc> listKV = new ArrayList<>();
        Statement statement = con.createStatement();
        String query = "SELECT * FROM Khuvuc where Trash = 0 ";
        ResultSet rs = statement.executeQuery(query);
        while (rs.next()) {
            KhuVuc kv = new KhuVuc();
            kv.setId(rs.getInt("Id"));
            kv.setTenKhuVuc(rs.getString("TenKhuVuc"));
            kv.setTang(rs.getInt("Tang"));
            kv.setDay(rs.getInt("DayNhaTro"));
            listKV.add(kv);
        }
        return listKV;
    }
    
    public void restoredFromBin(int idKV) throws SQLException {
        String query = "UPDATE KhuVuc SET trash = ? WHERE Id = ?";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setInt(1, 1);
        stmt.setLong(2, idKV);
        stmt.executeUpdate();
    }
    
    public void deleteKVById(int idKV) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("DELETE FROM KhuVuc WHERE Id = ?");
        stmt.setLong(1, idKV);
        stmt.executeUpdate();
    }
}
