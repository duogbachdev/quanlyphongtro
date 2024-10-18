/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TroViet.DAO;

/**
 *
 * @author ADMIN
 */
public enum UserRole {
    Inactive("Inactive", "inactive", 3),
    Admin("Admin", "admin", 2),
    User("User", "user", 1),
    Tenants("Tenants", "tenants", 0);
    private String id, name;
    private int priority;

    UserRole(String id, String name, int priority) {
        this.id = id;
        this.name = name;
        this.priority = priority;
    }

    public static UserRole getById(String id) {
        for (UserRole e : values()) {
            if (e.id.equals(id)) {
                return e;
            }
        }
        return User;
    }

    public static UserRole getByName(String name) {
        for (UserRole e : values()) {
            if (e.name.equals(name)) {
                return e;
            }
        }
        return User;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int compare(UserRole other) {
        return priority - other.priority;
    }
}
