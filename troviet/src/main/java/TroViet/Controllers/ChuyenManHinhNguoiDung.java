/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TroViet.Controllers;

import TroViet.Views.NguoiDung.DanhGiaPanel;
import TroViet.Views.NguoiDung.HoaDonPanel;
import TroViet.Views.NguoiDung.HopDongPanel;
import TroViet.Views.NguoiDung.LichSuHDPanel;
import TroViet.Views.NguoiDung.ThongTinCaNhanPanel;
import TroViet.Views.NguoiDung.TrangChuPanel;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenu;
import javax.swing.JPanel;

/**
 *
 * @author WINDOWS
 */
public class ChuyenManHinhNguoiDung {
    private JPanel root;
    private String kindSelected = "";
    private List<DanhMuc> listItem = null;

    public ChuyenManHinhNguoiDung(JPanel root) {
        this.root = root;
    }
    
    public void setView(JMenu menuItem) throws SQLException {
        kindSelected = "TrangChu";
        root.removeAll();
        root.setLayout(new BorderLayout());
        root.add(new TrangChuPanel());
    }
    
    public void setEvent(List<DanhMuc> listItem) {
        this.listItem = listItem;
        for (DanhMuc item : listItem) {
            item.getMenuItem().addMouseListener(new LabelEvent(item.getKind(), item.getMenuItem()));
        }
    }
    
    class LabelEvent implements MouseListener {
        private JPanel node;
        private String kind;
        private JMenu menuItem;

        public LabelEvent(String kind, JMenu menuItem) {
            this.kind = kind;
            this.menuItem = menuItem;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                switch (kind) {
                    case "TrangChu":
                        node = new TrangChuPanel();
                        break;
                    case "DanhGia":
                        node = new DanhGiaPanel();
                        break;
                    case "HopDong":
                        node = new HopDongPanel();
                        break;
                    case "HoaDon":
                        node = new HoaDonPanel();
                        break;
                    case "ThongTinCaNhan":
                        node = new ThongTinCaNhanPanel();
                        break;
                    case "LichSuHoatDong":
                        node = new LichSuHDPanel();
                        break;
                    default:
                        node = new TrangChuPanel();
                }
                root.removeAll();
                root.setLayout(new BorderLayout());
                root.add(node);
                root.validate();
                root.repaint();
            } catch (SQLException ex) {
                Logger.getLogger(ChuyenManHinhNguoiDung.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
            kindSelected = kind;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
        
        
    }
}
