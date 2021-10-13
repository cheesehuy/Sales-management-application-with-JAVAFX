package ThongKe;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Session;
import org.hibernate.query.Query;

import QLBH.HibernateUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

public class ThongKeLoiNhuanController implements Initializable {

	@FXML
	private LineChart<String, Number> loinhuan;

	@FXML
	private CategoryAxis thang;

	@FXML
	private NumberAxis tienloinhuan;

	@FXML
	private ComboBox<String> listthongke;

	@FXML
	private AnchorPane mainpane;

	@FXML
	void ListThongKe(ActionEvent event) throws IOException {
		if (listthongke.getValue() == "Thống kê theo ngày") {
			AnchorPane pane = FXMLLoader.load(getClass().getResource("/ThongKe/thongkedoanhthutheongay.fxml"));
			mainpane.getChildren().setAll(pane);
		} else if (listthongke.getValue() == "Thống kê khác") {
			AnchorPane pane = FXMLLoader.load(getClass().getResource("/ThongKe/thongkedoanhthu.fxml"));
			mainpane.getChildren().setAll(pane);

		}
	}

	public void ThongKeLoiNhuan() {
		Session session = HibernateUtils.getSessionFactory().openSession();
		try {
			XYChart.Series series = new XYChart.Series();
			String hql = "SELECT SUM(tonggia),thoigianmua as month FROM Hoadon GROUP BY month(thoigianmua) ORDER BY month(thoigianmua)";
			String hql1 = "SELECT SUM(tongtien),thoigian as month FROM Phieudathang GROUP BY month(thoigian) ORDER BY month(thoigian)";
			Query query = session.createQuery(hql);
			Query query1 = session.createQuery(hql1);
			List<Object[]> hd = query.list();
			List<Object[]> chi = query1.list();
			loinhuan.getData().clear();
			for (Object[] loinhuan : hd) {
				for (Object[] dathang : chi) {
					Double tonggiathu = (Double) loinhuan[0];
					String monththu = String.valueOf((LocalDateTime) loinhuan[1]);
					String dthu = String.valueOf(monththu.substring(5, 7));
					Long tonggiachi = (long) dathang[0];
					String monthchi = String.valueOf((LocalDateTime) dathang[1]);
					String dchi = String.valueOf(monthchi.substring(5, 7));
					if (dthu.equals(dchi)) {
						Double loinhuan2 = tonggiathu - tonggiachi;
						series.getData().add(new XYChart.Data(dthu, loinhuan2));
					}
				}
			}
			loinhuan.getData().addAll(series);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> combobox2 = FXCollections.observableArrayList("Thống kê theo ngày", "Thống kê khác",
				"Thống kê lợi nhuận");
		listthongke.setItems(combobox2);
		thang.setLabel("Tháng");
		tienloinhuan.setLabel("Tiền lợi nhuận");
		listthongke.getSelectionModel().select("Thống kê lợi nhuận");
		ThongKeLoiNhuan();
	}

}
