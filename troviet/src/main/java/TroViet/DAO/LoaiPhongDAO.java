package TroViet.DAO;
import TroViet.Model.LoaiPhong;
import TroViet.Connect.SQL_Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
public class LoaiPhongDAO extends SQL_Connection{
    public ArrayList<LoaiPhong> getAll() throws SQLException {
        ArrayList<LoaiPhong> listLoaiPhong = new ArrayList<>();
        
        String query = "select * from LoaiPhong;";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            LoaiPhong lp = new LoaiPhong();
            lp.setId(rs.getInt("Id"));
            lp.setTenLoaiPhong(rs.getString("TenLoaiPhong"));
            listLoaiPhong.add(lp);
        }
        return listLoaiPhong;
    }
    public LoaiPhong get(int id) throws SQLException {
        Statement st = con.createStatement();
        String query = "select * from LoaiPhong where Id = " + id;
        ResultSet rs = st.executeQuery(query);
        if (rs.next()) {
            LoaiPhong lp = new LoaiPhong();
            lp.setId(rs.getInt("Id"));
            lp.setTenLoaiPhong(rs.getString("TenLoaiPhong")); 
            return lp;
        }
        return null;
    }
    
    public void saveLP(LoaiPhong lp) throws SQLException {
        String query = "insert into LoaiPhong values (?);";
        PreparedStatement ps = con.prepareStatement(query);
//        ps.setInt(1, lp.getId());
        ps.setString(1, lp.getTenLoaiPhong());
        int result = ps.executeUpdate();
    }
    
    public String existsLP(String tenLoaiPhong) throws SQLException {
        String query = "select * from LoaiPhong where TenLoaiPhong = ?;";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, tenLoaiPhong);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String yes = rs.getString("TenLoaiPhong");
            return yes;
        }
        return null;
    }
    
    public int updateLPByID(LoaiPhong lp) throws SQLException {
        try {
            String query ="update LoaiPhong set TenLoaiPhong=? where Id=?;";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, lp.getTenLoaiPhong());
            ps.setInt(2, lp.getId());
            if (ps.executeUpdate() > 0) {
                return 1;
            }
        } catch (Exception e) {
            System.out.println("Success");
            e.printStackTrace();
        }
        if (lp == null) {
            throw new SQLException("Khuvuc rỗng");
        }
        return -1;
    }
    
    public void deleteLPById(int idLP) throws SQLException {
        PreparedStatement stmt = con.prepareStatement("DELETE FROM loaiPhong WHERE Id = ?");
        stmt.setLong(1, idLP);
        stmt.executeUpdate();
    }
}
