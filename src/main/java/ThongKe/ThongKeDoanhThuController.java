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

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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

public class ThongKeDoanhThuController implements Initializable {

	@FXML
	private LineChart<String, Number> linechart;

	@FXML
	private CategoryAxis ngay;

	@FXML
	private NumberAxis tienbanduoctrongmotngay;

	@FXML
	private Button bt;

	@FXML
	private PieChart piechart;

	@FXML
	private ComboBox<String> cbb_thang;

	@FXML
	private BarChart<String, Number> chart_congno;

	ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

	@FXML
	private Button sanphamtrongkho;

	@FXML
	private StackedBarChart<String, Number> sbarchart;

	@FXML
	private ComboBox<String> cbb_doanhthu;

	@FXML
	private ComboBox<String> listthongke;

	@FXML
	private AnchorPane mainpane;

	@FXML
	void ListThongKe(ActionEvent event) throws IOException {
		if (listthongke.getValue() == "Thống kê theo ngày") {
			AnchorPane pane = FXMLLoader.load(getClass().getResource("/ThongKe/thongkedoanhthutheongay.fxml"));
			mainpane.getChildren().setAll(pane);
		} else if (listthongke.getValue() == "Thống kê lợi nhuận") {
			AnchorPane pane = FXMLLoader.load(getClass().getResource("/ThongKe/thongkeloinhuan.fxml"));
			mainpane.getChildren().setAll(pane);

		}
	}

	void thongkesptrongkho() {
		Session session = HibernateUtils.getSessionFactory().openSession();
		try {
			String hql = "FROM Sanpham";
			Query query = session.createQuery(hql);
			List<Sanpham> hd = query.list();
			for (Sanpham sanpham : hd) {
				data.add(new PieChart.Data(sanpham.getTensanpham(), sanpham.getDonvitinh()));
			}
			piechart.setTitle("Thống kê sản phẩm trong kho");
			piechart.setData(data);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@FXML
	void ThangThongKe(ActionEvent event) {
		if (cbb_thang.getValue() == "01") {
			loadStackedBarChart(1);
		} else if (cbb_thang.getValue() == "02") {
			loadStackedBarChart(2);
		} else if (cbb_thang.getValue() == "03") {
			loadStackedBarChart(3);
		} else if (cbb_thang.getValue() == "04") {
			loadStackedBarChart(4);
		} else if (cbb_thang.getValue() == "05") {
			loadStackedBarChart(5);
		} else if (cbb_thang.getValue() == "06") {
			loadStackedBarChart(6);
		} else if (cbb_thang.getValue() == "07") {
			loadStackedBarChart(7);
		} else if (cbb_thang.getValue() == "08") {
			loadStackedBarChart(8);
		} else if (cbb_thang.getValue() == "09") {
			loadStackedBarChart(9);
		} else if (cbb_thang.getValue() == "10") {
			loadStackedBarChart(10);
		} else if (cbb_thang.getValue() == "11") {
			loadStackedBarChart(11);
		} else if (cbb_thang.getValue() == "12") {
			loadStackedBarChart(12);
		}

	}

	void doanhthumotthangtheodothi() {
		Session session = HibernateUtils.getSessionFactory().openSession();
		try {
			XYChart.Series series = new XYChart.Series();
			XYChart.Series series1 = new XYChart.Series();
			String hql = "SELECT SUM(tonggia),thoigianmua as month FROM Hoadon GROUP BY month(thoigianmua) ORDER BY month(thoigianmua)";
			String hql1 = "SELECT SUM(tongtien),thoigian as month FROM Phieudathang GROUP BY month(thoigian) ORDER BY month(thoigian)";
			Query query = session.createQuery(hql);
			Query query1 = session.createQuery(hql1);
			List<Object[]> hd = query.list();
			List<Object[]> chi = query1.list();
			linechart.getData().clear();
			for (Object[] hoadon : hd) {
				ngay.setLabel("Tháng");
				tienbanduoctrongmotngay.setLabel("Số tiền trong một tháng");
				Double tonggia = (Double) hoadon[0];
				String month = String.valueOf((LocalDateTime) hoadon[1]);
				String d = String.valueOf(month.substring(5, 7));
				series.getData().add(new XYChart.Data(d, tonggia));
				linechart.setAnimated(false);
			}
			linechart.getData().addAll(series);
			series.setName("Doanh thu");
			linechart.setTitle("Doanh thu và Chi theo tháng");
			for (Object[] dathang : chi) {
				long tonggia = (long) dathang[0];
				String month = String.valueOf((LocalDateTime) dathang[1]);
				String d = String.valueOf(month.substring(5, 7));
				series1.getData().add(new XYChart.Data(d, tonggia));
				linechart.setAnimated(false);
			}
			linechart.getData().addAll(series1);
			series1.setName("Chi");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public ObservableList<Object> getDoanhThuTheoThang() {
		Session session = HibernateUtils.getSessionFactory().openSession();
		ObservableList<Object> tb_hoadon = FXCollections.observableArrayList();
		String hql = "SELECT SUM(tonggia),thoigianmua as month FROM Hoadon GROUP BY month(thoigianmua) ORDER BY month(thoigianmua)";
		Query query = session.createQuery(hql);
		List<Object> hd = query.list();
		for (Object hoadon : hd) {
			tb_hoadon.add(hoadon);
		}
		return tb_hoadon;
	}

	public void loadStackedBarChart(int thang) {
		Session session = HibernateUtils.getSessionFactory().openSession();
		ObservableList<Object[]> tb_hoadon = FXCollections.observableArrayList();
		String hql = "SELECT SP.tensanpham,SUM(CT.soluong) FROM Chitiethoadon CT INNER JOIN CT.sanpham SP INNER JOIN CT.hoadon H WHERE month(H.thoigianmua)= :thoigian GROUP BY SP.tensanpham ORDER BY CT.soluong";
		XYChart.Series series = new XYChart.Series();
		try {
			Query query = session.createQuery(hql);
			query.setParameter("thoigian", thang);
			List<Object[]> hd = query.list();
			sbarchart.getData().clear();
			sbarchart.setTitle("Thống kê sản phẩm bán được trong tháng " + " " + thang);
			for (Object[] thongke : hd) {
				String tensanpham = (String) thongke[0];
				long soluong = (long) thongke[1];
				System.out.println(tensanpham);
				System.out.println(soluong);
				series.getData().add(new XYChart.Data(tensanpham, soluong));
			}
			sbarchart.getData().add(series);
			sbarchart.setAnimated(false);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void loadCongNoNhaCungCap() {
		Session session = HibernateUtils.getSessionFactory().openSession();
		ObservableList<Nhacungcap> tb_congno = FXCollections.observableArrayList();
		chart_congno.getData().clear();
		chart_congno.setTitle("Thống kê công nợ của nhà cung cấp");
		try {
			XYChart.Series series = new XYChart.Series();
			String hql = "FROM Nhacungcap";
			Query query = session.createQuery(hql);
			List<Nhacungcap> hd = query.list();
			for (Nhacungcap nhacungcap : hd) {
				series.getData().add(new XYChart.Data(nhacungcap.getTenncc(), nhacungcap.getSotienno()));
			}
			chart_congno.getData().add(series);
			chart_congno.setAnimated(false);
		} catch (Exception e) {
		}
	}

	public ObservableList<Hoadon> gethoadon() {
		ObservableList<Hoadon> Hoadon = FXCollections.observableArrayList();
		Session session = HibernateUtils.getSessionFactory().openSession();
		CriteriaQuery<Hoadon> hoadon = session.getCriteriaBuilder().createQuery(Hoadon.class);
		hoadon.from(Hoadon.class);
		List<Hoadon> eList = session.createQuery(hoadon).getResultList();
		for (Hoadon ent : eList) {
			Hoadon.add(ent);
		}
		return Hoadon;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> combobox = FXCollections.observableArrayList("01", "02", "03", "04", "05", "06", "07", "08", "09",
				"10", "11", "12");
		cbb_thang.setItems(combobox);
		ObservableList<String> combobox2 = FXCollections.observableArrayList("Thống kê theo ngày", "Thống kê khác",
				"Thống kê lợi nhuận");
		listthongke.setItems(combobox2);
		listthongke.getSelectionModel().select("Thống kê khác");
		thongkesptrongkho();
		loadCongNoNhaCungCap();
		LocalDateTime t1 = LocalDateTime.now();
		String getDatenow = String.valueOf(t1);
		int thang = Integer.parseInt(getDatenow.substring(5, 7));
		loadStackedBarChart(thang);
		String thang1 = getDatenow.substring(5, 7);
		cbb_thang.getSelectionModel().select(thang1);
		doanhthumotthangtheodothi();
	}

}
