package TroViet.Views.ChuTro;

import static TroViet.Connect.SQL_Connection.con;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Window;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.UIUtils;
import org.jfree.chart.util.ShapeUtils;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import javax.swing.JButton;

public class LineChartDichVu extends ApplicationFrame {

    public LineChartDichVu(String title) throws SQLException {
        super(title);
        JPanel chartPanel = createDemoPanel();
        chartPanel.setPreferredSize(new Dimension(800, 600));
        setContentPane(chartPanel);
        chartPanel.setLayout(null);
        // Tạo JButton
        JButton closeButton = new JButton("X");
        closeButton.addActionListener(e -> {
            dispose(); // Đóng cửa sổ khi nút "X" được nhấp
        });
        
        // Thêm JButton vào panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
         buttonPanel.setBounds(750,0,40,30);
        chartPanel.add(buttonPanel);

        setDefaultCloseOperation(LineChartDichVu.DISPOSE_ON_CLOSE);
        setUndecorated(true);
    }

    private static CategoryDataset createDataset() throws SQLException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        String query = "SELECT MONTH(hdon.NgayBatDau) AS month,\n"
                + "sum((hdon.SoDienMoi - hdon.SoDienCu) * hdong.GiaDien) AS GiaDien ,\n"
                + "sum((hdon.SoNuocMoi - hdon.SoNuocCu) * hdong.GiaNuoc) AS GiaNuoc,\n"
                + "sum(hdong.GiaInternet) as GiaInternet,\n"
                + "sum(hdong.GiaRac) as GiaRac\n"
                + "FROM HoaDon hdon \n"
                + "JOIN HopDong hdong ON hdon.IdHopDong = hdong.Id \n"
                + "WHERE hdon.Trash != 0 AND hdon.status = 1 AND \n"
                + "YEAR(hdon.NgayBatDau) = 2023\n"
                + "group by  MONTH(hdon.NgayBatDau);";
        PreparedStatement ps = con.prepareStatement(query);
        ResultSet resultSet = ps.executeQuery();

        while (resultSet.next()) {
            int month = resultSet.getInt("month");
            double giaDien = resultSet.getDouble("GiaDien");
            double giaNuoc = resultSet.getDouble("GiaNuoc");
            double giaInternet = resultSet.getDouble("GiaInternet");
            double giaRac = resultSet.getDouble("GiaRac");
            dataset.addValue(giaDien, "Electricity Cost", String.valueOf(month));
            dataset.addValue(giaNuoc, "Water Cost", String.valueOf(month));
            dataset.addValue(giaInternet, "Network Cost", String.valueOf(month));
            dataset.addValue(giaRac, "Sanitation Cost", String.valueOf(month));
        }

        return dataset;
    }

    private static JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createLineChart("Thống kê tiền dịch vụ phòng trọ", null, "Count", dataset, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShapesVisible(1, true);
        renderer.setSeriesShape(1, ShapeUtils.createDiamond(10.0F));
        renderer.setDrawOutlines(true);
        renderer.setUseFillPaint(true);
        renderer.setDefaultFillPaint(Color.WHITE);
        plot.setBackgroundPaint(Color.black);

        return chart;
    }

    public static JPanel createDemoPanel() throws SQLException {
        JFreeChart chart = createChart(createDataset());
        return (JPanel) new ChartPanel(chart);
    }

//    public static void main(String[] args) throws SQLException {
//        LineChartDichVu demo = new LineChartDichVu("JFreeChart: LineChartDichVu.java");
//        demo.pack();
//        UIUtils.centerFrameOnScreen((Window) demo);
//        demo.setVisible(true);
//    }
}
