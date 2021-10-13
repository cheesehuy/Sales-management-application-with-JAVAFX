package QLKho;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.util.List;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import org.hibernate.*;
import entities.*;
import QLBH.HibernateUtils;


public class DathangDetailController implements Initializable {

	@FXML
	private Label lbMadathang;

	@FXML
	private Label lbThoigiandat;

	@FXML
	private Label lbTongtien;

	@FXML
	private Label lbMancc;

	@FXML
	private Button back1;

	@FXML
	private TableView tbChitietDatHang;

	@FXML
	private TableColumn<Chitietdathang, Sanpham> tenhang;

	@FXML
	private TableColumn soluong;

	@FXML
	private TableColumn dongia;

	@FXML
	private TableColumn tongtien;

	@FXML
	void trolai(ActionEvent e) throws IOException {
		Stage stage = (Stage) back1.getScene().getWindow();
		stage.close();
	}

	public ObservableList<Chitietdathang> getChitietdathang(Phieudathang phieudathang) {
		int Phieudathang = phieudathang.getMadathang();
		ObservableList<Chitietdathang> tablePhieuDatHang = FXCollections.observableArrayList();
		Session session = HibernateUtils.getSessionFactory().openSession();

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Chitietdathang> query = builder.createQuery(Chitietdathang.class);
		Root<Chitietdathang> root = query.from(Chitietdathang.class);
		Join<Chitietdathang, Sanpham> SanphamJoin = root.join("sanpham", JoinType.INNER);
		List<Chitietdathang> ctdh = session.createQuery(query).getResultList();
		for (Chitietdathang b : ctdh) {
			tablePhieuDatHang.add(b);
			System.out.println(b);
		}
		return tablePhieuDatHang;

	}

	public void IntitlizeChitietdathang(Phieudathang phieudathang) {
		tenhang.setCellFactory(tbChitietDatHang -> new TableCell<Chitietdathang, Sanpham>() {
			@Override
			protected void updateItem(Sanpham item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText(item.getTensanpham());
				}
			}
		});
		tenhang.setCellValueFactory(new PropertyValueFactory<>("sanpham"));
		soluong.setCellValueFactory(new PropertyValueFactory<Chitietdathang, Integer>("soluong"));
		dongia.setCellValueFactory(new PropertyValueFactory<Chitietdathang,Integer>("dongia"));
		

		tbChitietDatHang.setItems(getChitietdathang(phieudathang));
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	public void setPhieudathang(Phieudathang phieudathang) {
		lbMadathang.setText(String.valueOf((phieudathang.getMadathang())));
		lbThoigiandat.setText(String.valueOf(phieudathang.getThoigian()));
		lbMancc.setText(phieudathang.getNhacungcap().toString());
		IntitlizeChitietdathang(phieudathang);
	}
}
