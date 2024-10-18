/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TroViet.Model;

import TroViet.DAO.UserRole;

public class UserEntity {

    private Long id;
    private String hoTen;
    private String email;
    private String DienThoai;
    private String diaChi;
    private String password;
    protected UserRole role;
    private int trash;
    private UserRole status;
    private String ngayTao;
    private String nguoiTao;
    private String ngaySua;
    private String nguoiSua;

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDienThoai(String DienThoai) {
        this.DienThoai = DienThoai;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setTrash(int trash) {
        this.trash = trash;
    }

    public void setStatus(UserRole status) {
        this.status = status;
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

    public Long getId() {
        return id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getEmail() {
        return email;
    }

    public String getDienThoai() {
        return DienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public int getTrash() {
        return trash;
    }

    public UserRole getStatus() {
        return status;
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

}
