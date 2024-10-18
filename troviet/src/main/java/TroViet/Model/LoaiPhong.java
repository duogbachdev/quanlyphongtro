package TroViet.Model;
public class LoaiPhong {
    private int id;
    private String tenLoaiPhong;
    public LoaiPhong() {
    }
    public LoaiPhong(int id, String tenLoaiPhong) {
        this.id = id;
        this.tenLoaiPhong = tenLoaiPhong;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTenLoaiPhong() {
        return tenLoaiPhong;
    }
    public void setTenLoaiPhong(String tenLoaiPhong) {
        this.tenLoaiPhong = tenLoaiPhong;
    }
}
