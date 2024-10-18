package TroViet.Model;
public class KhuVuc {
    private int id;
    private String tenKhuVuc;
    private int tang, day;
    public KhuVuc() {
    }
    public KhuVuc(int id, String tenKhuVuc, int tang, int day) {
        this.id = id;
        this.tenKhuVuc = tenKhuVuc;
        this.tang = tang;
        this.day = day;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTenKhuVuc() {
        return tenKhuVuc;
    }
    public void setTenKhuVuc(String tenKhuVuc) {
        this.tenKhuVuc = tenKhuVuc;
    }
    public int getTang() {
        return tang;
    }
    public void setTang(int tang) {
        this.tang = tang;
    }
    public int getDay() {
        return day;
    }
    public void setDay(int day) {
        this.day = day;
    }
}
