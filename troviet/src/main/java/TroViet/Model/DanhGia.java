/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TroViet.Model;

/**
 *
 * @author WINDOWS
 */
public class DanhGia {
    private Long id, idPhongTro, idNguoiDung;
    private int DanhGia;
    private String noiDung;
    private String ngayTao;
    private String nguoiTao;
    private String ngaySua;
    private String nguoiSua;
    private int trash;
    private int status;

    public DanhGia() {
    }

    public DanhGia(Long id, Long idPhongTro, Long idNguoiDung, int DanhGia, String noiDung, String ngayTao, String nguoiTao, String ngaySua, String nguoiSua, int trash, int status) {
        this.id = id;
        this.idPhongTro = idPhongTro;
        this.idNguoiDung = idNguoiDung;
        this.DanhGia = DanhGia;
        this.noiDung = noiDung;
        this.ngayTao = ngayTao;
        this.nguoiTao = nguoiTao;
        this.ngaySua = ngaySua;
        this.nguoiSua = nguoiSua;
        this.trash = trash;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPhongTro() {
        return idPhongTro;
    }

    public void setIdPhongTro(Long idPhongTro) {
        this.idPhongTro = idPhongTro;
    }

    public Long getIdNguoiDung() {
        return idNguoiDung;
    }

    public void setIdNguoiDung(Long idNguoiDung) {
        this.idNguoiDung = idNguoiDung;
    }

    public int getDanhGia() {
        return DanhGia;
    }

    public void setDanhGia(int DanhGia) {
        this.DanhGia = DanhGia;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getNguoiTao() {
        return nguoiTao;
    }

    public void setNguoiTao(String nguoiTao) {
        this.nguoiTao = nguoiTao;
    }

    public String getNgaySua() {
        return ngaySua;
    }

    public void setNgaySua(String ngaySua) {
        this.ngaySua = ngaySua;
    }

    public String getNguoiSua() {
        return nguoiSua;
    }

    public void setNguoiSua(String nguoiSua) {
        this.nguoiSua = nguoiSua;
    }

    public int getTrash() {
        return trash;
    }

    public void setTrash(int trash) {
        this.trash = trash;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    
}
