package ThongKe;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import entities.*;
import QLBH.HibernateUtils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javassist.expr.NewArray;

public class TKDTtheongayController implements Initializable {
	@FXML
	private LineChart<String, Number> line_chart;

	@FXML
	private CategoryAxis ngay;

	@FXML
	private NumberAxis doanhthu;
	@FXML
	private ComboBox<String> cbb_thang;

	@FXML
	private BarChart<String, Number> bar_chart;

	@FXML
	private CategoryAxis Sanpham;

	@FXML
	private NumberAxis Soluongbanra;
	@FXML
	private ComboBox<String> listthongke;

	@FXML
	private AnchorPane mainpane;

	@FXML
	void ListThongKe(ActionEvent event) throws IOException {
		if (listthongke.getValue() == "Thống kê khác") {
			AnchorPane pane = FXMLLoader.load(getClass().getResource("/ThongKe/thongkedoanhthu.fxml"));
			mainpane.getChildren().setAll(pane);
		} else if (listthongke.getValue() == "Thống kê lợi nhuận") {
			AnchorPane pane = FXMLLoader.load(getClass().getResource("/ThongKe/thongkeloinhuan.fxml"));
			mainpane.getChildren().setAll(pane);
		}
	}

	@FXML
	void ThongKeThang(ActionEvent event) {
		if (cbb_thang.getValue() == "01") {
			thongketheongay(1);
		} else if (cbb_thang.getValue() == "02") {
			thongketheongay(2);
		} else if (cbb_thang.getValue() == "03") {
			thongketheongay(3);
		} else if (cbb_thang.getValue() == "04") {
			thongketheongay(4);
		} else if (cbb_thang.getValue() == "05") {
			thongketheongay(5);
		} else if (cbb_thang.getValue() == "06") {
			thongketheongay(6);
		} else if (cbb_thang.getValue() == "07") {
			thongketheongay(7);
		} else if (cbb_thang.getValue() == "08") {
			thongketheongay(8);
		} else if (cbb_thang.getValue() == "09") {
			thongketheongay(9);
		} else if (cbb_thang.getValue() == "10") {
			thongketheongay(10);
		} else if (cbb_thang.getValue() == "11") {
			thongketheongay(11);
		} else if (cbb_thang.getValue() == "12") {
			thongketheongay(12);
		}
	}

	public void thongketheongay(int thang) {
		Session session = HibernateUtils.getSessionFactory().openSession();
		XYChart.Series series = new XYChart.Series();
		try {
			String hql = "SELECT SUM(tonggia),thoigianmua as date FROM Hoadon WHERE month(thoigianmua)=:thoigianmua GROUP BY DATE(thoigianmua) ORDER BY DATE(thoigianmua)";
			Query query = session.createQuery(hql);
			query.setParameter("thoigianmua", thang);
			List<Object[]> hd = query.list();
			line_chart.getData().clear();
			for (Object[] hoadon : hd) {
				ngay.setLabel("Ngày");
				doanhthu.setLabel("Doanh thu bán được trong một ngày");
				Double tonggia = (Double) hoadon[0];
				String date = String.valueOf((LocalDateTime) hoadon[1]);
				String d = String.valueOf(date.substring(6, 10));
				series.getData().add(new XYChart.Data(d, tonggia));
			}
			line_chart.setTitle("Thống kê theo ngày của tháng " + thang);
			line_chart.getData().addAll(series);
			line_chart.setAnimated(false);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	LocalDateTime t1 = LocalDateTime.now();

	public void ThongKeSanPham(int ngay, int thang) {
		Session session = HibernateUtils.getSessionFactory().openSession();
		try {
			XYChart.Series series = new XYChart.Series();
			String hql = "SELECT SP.tensanpham,SUM(CT.soluong) FROM Chitiethoadon CT INNER JOIN CT.sanpham SP INNER JOIN CT.hoadon H WHERE dayofmonth(H.thoigianmua)=:date and month(H.thoigianmua)=:month GROUP BY SP.tensanpham ORDER BY CT.soluong";
			Query query = session.createQuery(hql);
			query.setParameter("date", ngay);
			query.setParameter("month", thang);
			List<Object[]> hd = query.list();
			bar_chart.getData().clear();
			bar_chart.setTitle("Thống kê sản phẩm bán được trong một ngày");
			for (Object[] thongke : hd) {
				String tensanpham = (String) thongke[0];
				long soluong = (long) thongke[1];
				series.getData().add(new XYChart.Data(tensanpham, soluong));
			}
			bar_chart.getData().addAll(series);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> combobox = FXCollections.observableArrayList("01", "02", "03", "04", "05", "06", "07", "08", "09",
				"10", "11", "12");
		ObservableList<String> combobox1 = FXCollections.observableArrayList("Thống kê theo ngày", "Thống kê khác",
				"Thống kê lợi nhuận");
		cbb_thang.setItems(combobox);
		listthongke.setItems(combobox1);
		listthongke.getSelectionModel().select("Thống kê theo ngày");
		//Sanpham.setLabel("Sản phẩm bán ra");
		Soluongbanra.setLabel("Số lượng bán ra ");
		bar_chart.setTitle("Thống kê số lượng sản phẩm bán ra trong một ngày");
		String getDatenow = String.valueOf(t1);
		int day = Integer.parseInt(getDatenow.substring(8, 10));
		int thang = Integer.parseInt(getDatenow.substring(5, 7));
		String thang1 = getDatenow.substring(5, 7);
		thongketheongay(thang);
		ThongKeSanPham(day, thang);
		cbb_thang.getSelectionModel().select(thang1);;
	}

}
