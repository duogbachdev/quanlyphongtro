package TroViet.Model;
import TroViet.DAO.PhongTroEnum;
import java.security.Timestamp;
import java.sql.ResultSet;
import java.sql.SQLException;
public class PhongTro {
    protected long id;
    protected String tenphong,diachi,mota;
    protected double dientich,giaphong;
    protected Timestamp ngaytao,ngaysua;
    protected String nguoitao,nguoisua;
    protected int trash;
    private PhongTroEnum status;
    private int idMaloaiphong;
    private long idNguoidung;
    private int idKhuvuc;
    public PhongTro() {
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTenphong() {
        return tenphong;
    }
    public void setTenphong(String tenphong) {
        this.tenphong = tenphong;
    }
    public String getDiachi() {
        return diachi;
    }
    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }
    public String getMota() {
        return mota;
    }
    public void setMota(String mota) {
        this.mota = mota;
    }
    public double getDientich() {
        return dientich;
    }
    public void setDientich(double dientich) {
        this.dientich = dientich;
    }
    public double getGiaphong() {
        return giaphong;
    }
    public void setGiaphong(double giaphong) {
        this.giaphong = giaphong;
    }
    public Timestamp getNgaytao() {
        return ngaytao;
    }
    public void setNgaytao(Timestamp ngaytao) {
        this.ngaytao = ngaytao;
    }
    public Timestamp getNgaysua() {
        return ngaysua;
    }
    public void setNgaysua(Timestamp ngaysua) {
        this.ngaysua = ngaysua;
    }
    public String getNguoitao() {
        return nguoitao;
    }
    public void setNguoitao(String nguoitao) {
        this.nguoitao = nguoitao;
    }
    public String getNguoisua() {
        return nguoisua;
    }
    public void setNguoisua(String nguoisua) {
        this.nguoisua = nguoisua;
    }
    public int getTrash() {
        return trash;
    }
    public void setTrash(int trash) {
        this.trash = trash;
    }
    public PhongTroEnum getStatus() {
        return status;
    }
    public void setStatus(PhongTroEnum status) {
        this.status = status;
    }
    public int getIdMaloaiphong() {
        return idMaloaiphong;
    }
    public void setIdMaloaiphong(int idMaloaiphong) {
        this.idMaloaiphong = idMaloaiphong;
    }
    public long getIdNguoidung() {
        return idNguoidung;
    }
    public void setIdNguoidung(long idNguoidung) {
        this.idNguoidung = idNguoidung;
    }
    public int getIdKhuvuc() {
        return idKhuvuc;
    }
    public void setIdKhuvuc(int idKhuvuc) {
        this.idKhuvuc = idKhuvuc;
    }
    public static PhongTro getFromResultSet(ResultSet rs) throws SQLException{
        PhongTro s = new PhongTro();
        s.setId(rs.getLong("Id"));
        s.setIdMaloaiphong(rs.getInt("IdMaLoaiPhong"));
        s.setIdNguoidung(rs.getLong("IdNguoiDung"));
        s.setIdKhuvuc(rs.getInt("IdKhuVuc"));
        s.setTenphong(rs.getString("TenPhong"));
        s.setDientich(rs.getDouble("DienTich"));
        s.setGiaphong(rs.getDouble("GiaPhong"));
        s.setDiachi(rs.getString("DiaChi"));
        s.setMota(rs.getString("MoTa"));
        s.setNguoitao(rs.getString("NguoiTao"));
        s.setNguoisua(rs.getString("NguoiSua"));
        s.setTrash(rs.getInt("Trash"));
        s.setStatus(PhongTroEnum.getById(rs.getString("Status")));
        return s;
    }
    @Override
    public String toString() {
       return tenphong;
    }
}
