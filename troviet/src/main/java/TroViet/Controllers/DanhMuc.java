/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TroViet.Controllers;

import java.awt.Button;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 *
 * @author WINDOWS
 */
public class DanhMuc {
    private String kind;
    private JMenu menuItem;
    private Button selectedButton;
    
    public DanhMuc() {
    }

    public DanhMuc(String kind, JMenu menuItem) {
        this.kind = kind;
        this.menuItem = menuItem;
    }

    public DanhMuc(String kind, Button selectedButton) {
        this.kind = kind;
        this.selectedButton = selectedButton;
    }

    public Button getSelectedButton() {
        return selectedButton;
    }

    public void setSelectedButton(Button selectedButton) {
        this.selectedButton = selectedButton;
    }
    
    

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public JMenu getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(JMenu menuItem) {
        this.menuItem = menuItem;
    }
}
