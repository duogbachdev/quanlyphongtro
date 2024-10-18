/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TroViet.DAO;

/**
 *
 * @author ADMIN
 */
public enum HopDongEnum {
    Time_expired("0", "Hết hạn"),
    Using("1", "Đang sử dụng"),
    CANCEL("2", "Bị hủy");
    private String id, name;

    HopDongEnum(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static HopDongEnum getById(String id) {
        for (HopDongEnum e : values()) {
            if (e.id.equals(id)) {
                return e;
            }
        }
        return Time_expired;
    }

    public static HopDongEnum getByName(String name) {
        for (HopDongEnum e : values()) {
            if (e.name.equals(name)) {
                return e;
            }
        }
        return Time_expired;
    }
}
