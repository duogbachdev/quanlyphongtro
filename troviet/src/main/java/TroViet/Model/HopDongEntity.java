/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TroViet.Model;

import TroViet.DAO.HopDongEnum;

/**
 *
 * @author ADMIN
 */
public class HopDongEntity {

    private long id;
    private long idMaPhongTro;
    private long idNguoiDung;
    private String ngayBatDauThue;
    private String ngayKetThucThue;
    private float tienDatCoc;
    private float giaPhong;
    private float giaDien;
    private float giaNuoc;
    private float giaInternet;
    private float giaRac;
    private int trash;
    private HopDongEnum status;
    private String ngayTao;
    private String nguoiTao;
    private String ngaySua;
    private String nguoiSua;

    public HopDongEnum getStatus() {
        return status;
    }

    public void setStatus(HopDongEnum status) {
        this.status = status;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public String getNguoiTao() {
        return nguoiTao;
    }

    public String getNgaySua() {
        return ngaySua;
    }

    public String getNguoiSua() {
        return nguoiSua;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public void setNguoiTao(String nguoiTao) {
        this.nguoiTao = nguoiTao;
    }

    public void setNgaySua(String ngaySua) {
        this.ngaySua = ngaySua;
    }

    public void setNguoiSua(String nguoiSua) {
        this.nguoiSua = nguoiSua;
    }

    public long getId() {
        return id;
    }

    public long getIdMaPhongTro() {
        return idMaPhongTro;
    }

    public long getIdNguoiDung() {
        return idNguoiDung;
    }

    public String getNgayBatDauThue() {
        return ngayBatDauThue;
    }

    public String getNgayKetThucThue() {
        return ngayKetThucThue;
    }

    public float getTienDatCoc() {
        return tienDatCoc;
    }

    public float getGiaPhong() {
        return giaPhong;
    }

    public float getGiaDien() {
        return giaDien;
    }

    public float getGiaNuoc() {
        return giaNuoc;
    }

    public float getGiaInternet() {
        return giaInternet;
    }

    public float getGiaRac() {
        return giaRac;
    }

    public int getTrash() {
        return trash;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setIdMaPhongTro(long idMaPhongTro) {
        this.idMaPhongTro = idMaPhongTro;
    }

    public void setIdNguoiDung(long idNguoiDung) {
        this.idNguoiDung = idNguoiDung;
    }

    public void setNgayBatDauThue(String ngayBatDauThue) {
        this.ngayBatDauThue = ngayBatDauThue;
    }

    public void setNgayKetThucThue(String ngayKetThucThue) {
        this.ngayKetThucThue = ngayKetThucThue;
    }

    public void setTienDatCoc(float tienDatCoc) {
        this.tienDatCoc = tienDatCoc;
    }

    public void setGiaPhong(float giaPhong) {
        this.giaPhong = giaPhong;
    }

    public void setGiaDien(float giaDien) {
        this.giaDien = giaDien;
    }

    public void setGiaNuoc(float giaNuoc) {
        this.giaNuoc = giaNuoc;
    }

    public void setGiaInternet(float giaInternet) {
        this.giaInternet = giaInternet;
    }

    public void setGiaRac(float giaRac) {
        this.giaRac = giaRac;
    }

    public void setTrash(int trash) {
        this.trash = trash;
    }

}
