/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package TroViet.DAO;

/**
 *
 * @author WINDOWS
 */
public enum HoaDonEnum {
    Paid("1", "Đã thanh toán"),
    Unpaid("0", "Chưa thanh toán");
    private String id, name;

    HoaDonEnum(String id, String name) {
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

    public static HoaDonEnum getById(String id) {
        for (HoaDonEnum e : values()) {
            if (e.id.equals(id)) {
                return e;
            }
        }
        return Unpaid;
    }

    public static HoaDonEnum getByName(String name) {
        for (HoaDonEnum e : values()) {
            if (e.name.equals(name)) {
                return e;
            }
        }
        return Unpaid;
    }
}
