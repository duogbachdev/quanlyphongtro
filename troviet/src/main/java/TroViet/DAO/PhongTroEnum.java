
package TroViet.DAO;

public enum PhongTroEnum {
    Not_Yet("0", "Còn trống"),
    Rented("1", "Đã thuê"),
    Waiting ("2", "Chờ duyệt");
    private String id, name;

    PhongTroEnum(String id, String name) {
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

    public static PhongTroEnum getById(String id) {
        for (PhongTroEnum e : values()) {
            if (e.id.equals(id)) {
                return e;
            }
        }
        return Not_Yet;
    }

    public static PhongTroEnum getByName(String name) {
        for (PhongTroEnum e : values()) {
            if (e.name.equals(name)) {
                return e;
            }
        }
        return Not_Yet;
    }
}