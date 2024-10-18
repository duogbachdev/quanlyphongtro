/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TroViet.Controllers;

import TroViet.Views.ChuTro.HoaDonPanel;
import TroViet.Views.ChuTro.HoatDongNDPanel;
import TroViet.Views.ChuTro.HopDongPanel;
import TroViet.Views.ChuTro.KhuVucLoaiPhongPanel;
import TroViet.Views.ChuTro.LichSuHDPanel;
import TroViet.Views.ChuTro.NguoiDungPanel;
import TroViet.Views.ChuTro.PhongTroPanel;
import TroViet.Views.ChuTro.ThongTinCaNhanPanel;
import TroViet.Views.ChuTro.TrangChuPanel;
import TroViet.Views.ChuTro.TienDatCoc1;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 *
 * @author WINDOWS
 */
public class ChuyenManHinhChuTro {
    private JPanel root;
    private String kindSelected = "";
    private List<DanhMuc> listItem = null;

    public ChuyenManHinhChuTro(JPanel jpnRoot) {
        this.root = jpnRoot;
    }
    
    public void setView(JMenu menuItem) throws SQLException {
        kindSelected = "TrangChu";
        root.removeAll();
        root.setLayout(new BorderLayout());
        root.add(new TrangChuPanel());
    }
    
    public void setVieww(Button selectedButton) throws SQLException {
        kindSelected = "HopDong";
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
    
    public void setEventt(List<DanhMuc> listItem) {
        this.listItem = listItem;
        for (DanhMuc item : listItem) {
            item.getMenuItem().addMouseListener(new LabelEvent(item.getKind(), item.getSelectedButton()));
        }
    }

    class LabelEvent implements MouseListener {
        private JPanel node;
        private String kind;
        private JMenu menuItem;
        private Button selectedButton;

        public LabelEvent(String kind, JMenu menuItem) {
            this.kind = kind;
            this.menuItem = menuItem;
        }
        
        public LabelEvent(String kind, Button slButton) {
            this.kind = kind;
            this.selectedButton = slButton;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                switch (kind) {
                    case "TrangChu":
                        node = new TrangChuPanel();
                        break;
                    case "NguoiDung":
                        node = new NguoiDungPanel();
                        break;
                    case "HoatDongND":
                        node = new HoatDongNDPanel();
                        break;
                    case "KVLP":
                        node = new KhuVucLoaiPhongPanel();
                        break;
                    case "PhongTro":
                        node = new PhongTroPanel();
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
                Logger.getLogger(ChuyenManHinhChuTro.class.getName()).log(Level.SEVERE, null, ex);
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
