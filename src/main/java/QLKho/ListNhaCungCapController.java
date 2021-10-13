package QLKho;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;

import javafx.scene.control.Label;
import QLBH.HibernateUtils;
import entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ListNhaCungCapController implements Initializable {

	@FXML
	private TableView<Nhacungcap> tb_NCC;

	@FXML
	private TableColumn id_NCC;

	@FXML
	private TableColumn name_NCC;

	@FXML
	private TableColumn diachi_NCC;

	@FXML
	private TableColumn sdt_NCC;

	@FXML
	private TableColumn email_NCC;

	@FXML
	private TableColumn tienno_NCC;

	@FXML
	private Label thongbao;
	@FXML
	private Button bt_NCC;
	@FXML
	private AnchorPane ap;
	@FXML
	private TextField tf_ncc;

	private Stage thisStage;

	private final LapPhieuDatHangController lapPhieuDatHangController;

	public ListNhaCungCapController(LapPhieuDatHangController lapPhieuDatHangController) {
		this.lapPhieuDatHangController = lapPhieuDatHangController;
		thisStage = new Stage();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("listnhacungcap.fxml"));
			loader.setController(this);
			thisStage.setScene(new Scene(loader.load()));
			thisStage.setTitle("Chọn nhà cung cấp");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showStage() {
		thisStage.showAndWait();
	}

	@FXML
	void ChonNCC(ActionEvent event) {
			Nhacungcap selected = tb_NCC.getSelectionModel().getSelectedItem();
			Stage stage1 = (Stage) ap.getScene().getWindow();
			if (selected == null) {
				thongbao.setVisible(true);
				thongbao.setText("Chưa chọn nhà cung cấp!!!");
				System.out.print("Chưa chọn nhà cung cấp!!!");
				return;
			} else if (selected != null) {
				lapPhieuDatHangController.setNhaCungCap(selected);
				thongbao.setVisible(false);
			}
			stage1.close();	
		}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		id_NCC.setCellValueFactory(new PropertyValueFactory<Nhacungcap, Integer>("mancc"));
		name_NCC.setCellValueFactory(new PropertyValueFactory<Nhacungcap, String>("tenncc"));
		sdt_NCC.setCellValueFactory(new PropertyValueFactory<Nhacungcap, Integer>("sodienthoai"));
		tienno_NCC.setCellValueFactory(new PropertyValueFactory<Nhacungcap, Integer>("sotienno"));
		email_NCC.setCellValueFactory(new PropertyValueFactory<Nhacungcap, String>("email"));
		diachi_NCC.setCellValueFactory(new PropertyValueFactory<Nhacungcap, String>("diachi"));
		tb_NCC.setItems(getNhacungcap());
		searchNCC();
	}

	public ObservableList<Nhacungcap> getNhacungcap() {
		ObservableList<Nhacungcap> tableKH = FXCollections.observableArrayList();
		Session session = HibernateUtils.getSessionFactory().openSession();

		CriteriaQuery<Nhacungcap> ncc = session.getCriteriaBuilder().createQuery(Nhacungcap.class);
		ncc.from(Nhacungcap.class);
		List<Nhacungcap> eList = session.createQuery(ncc).getResultList();
		for (Nhacungcap ent : eList) {
			tableKH.add(ent);
		}
		return tableKH;
	}

	void searchNCC() {
		ObservableList<Nhacungcap> table = FXCollections.observableArrayList(getNhacungcap());
		FilteredList<Nhacungcap> filterData = new FilteredList<>(table, p -> true);
		tf_ncc.textProperty().addListener((observable, oldvalue, newvalue) -> {
			filterData.setPredicate(ncc -> {
				String typetext = newvalue.toLowerCase();
				if (newvalue == null || newvalue.isEmpty()) {
					return true;
				}
				if (ncc.getTenncc().toLowerCase().indexOf(typetext) != -1) {
					return true;
				}
				if (String.valueOf(ncc.getSodienthoai()).indexOf(typetext) != -1) {
					return true;
				}
				return false;
			});
			SortedList<Nhacungcap> sortedList = new SortedList<>(filterData);
			sortedList.comparatorProperty().bind(tb_NCC.comparatorProperty());
			tb_NCC.setItems(sortedList);
		});
	}

}
