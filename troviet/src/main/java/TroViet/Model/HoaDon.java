/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TroViet.Model;

import TroViet.DAO.HoaDonEnum;
import java.util.Date;

/**
 *
 * @author WINDOWS
 */
public class HoaDon {
    long id, idHopDong;
    String ngayBD, ngayKT;
    float soDienCu, soDienMoi, soNuocCu, soNuocMoi, khauTru, no, tongCong;
    String ngayTao;
    String nguoiTao;
    String ngaySua;
    String nguoiSua;
    int trash;
    HoaDonEnum status;

    public HoaDon() {
    }

    public HoaDon(long id, long idHopDong, String ngayBD, String ngayKT, float soDienCu, float soDienMoi, float soNuocCu, float soNuocMoi, float khauTru, float no, float tongCong, String ngayTao, String nguoiTao, String ngaySua, String nguoiSua, int trash, HoaDonEnum status) {
        this.id = id;
        this.idHopDong = idHopDong;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.soDienCu = soDienCu;
        this.soDienMoi = soDienMoi;
        this.soNuocCu = soNuocCu;
        this.soNuocMoi = soNuocMoi;
        this.khauTru = khauTru;
        this.no = no;
        this.tongCong = tongCong;
        this.ngayTao = ngayTao;
        this.nguoiTao = nguoiTao;
        this.ngaySua = ngaySua;
        this.nguoiSua = nguoiSua;
        this.trash = trash;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdHopDong() {
        return idHopDong;
    }

    public void setIdHopDong(long idHopDong) {
        this.idHopDong = idHopDong;
    }

    public String getNgayBD() {
        return ngayBD;
    }

    public void setNgayBD(String ngayBD) {
        this.ngayBD = ngayBD;
    }

    public String getNgayKT() {
        return ngayKT;
    }

    public void setNgayKT(String ngayKT) {
        this.ngayKT = ngayKT;
    }

    public float getSoDienCu() {
        return soDienCu;
    }

    public void setSoDienCu(float soDienCu) {
        this.soDienCu = soDienCu;
    }

    public float getSoDienMoi() {
        return soDienMoi;
    }

    public void setSoDienMoi(float soDienMoi) {
        this.soDienMoi = soDienMoi;
    }

    public float getSoNuocCu() {
        return soNuocCu;
    }

    public void setSoNuocCu(float soNuocCu) {
        this.soNuocCu = soNuocCu;
    }

    public float getSoNuocMoi() {
        return soNuocMoi;
    }

    public void setSoNuocMoi(float soNuocMoi) {
        this.soNuocMoi = soNuocMoi;
    }

    public float getKhauTru() {
        return khauTru;
    }

    public void setKhauTru(float khauTru) {
        this.khauTru = khauTru;
    }

    public float getNo() {
        return no;
    }

    public void setNo(float no) {
        this.no = no;
    }

    public float getTongCong() {
        return tongCong;
    }

    public void setTongCong(float tongCong) {
        this.tongCong = tongCong;
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

    public HoaDonEnum getStatus() {
        return status;
    }

    public void setStatus(HoaDonEnum status) {
        this.status = status;
    }

    
}
