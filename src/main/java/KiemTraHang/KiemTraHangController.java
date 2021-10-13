package KiemTraHang;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import QLBH.GiaoDienQLController;
import QLBH.HibernateUtils;
import entities.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class KiemTraHangController implements Initializable {

	public static KiemTraHangController instance;

	public KiemTraHangController() {
		instance = this;
	}

	public static KiemTraHangController getInstance() {
		return instance;
	}

	@FXML
	private TableView<Phieudathang> phieudathangkt;
	@FXML
	private TableColumn<Phieudathang,Integer> madathangkt;

	@FXML
	private TableColumn<Phieudathang,String> thoigiandatkt;

	@FXML
	private Label thongbaoKT;

	@FXML
	private TableColumn<Phieudathang,Integer> tongtienkt;

	@FXML
	private TableColumn<Phieudathang, Boolean> select;

	@FXML
	private TableColumn<Phieudathang, Nhacungcap> mancckt;

	@FXML
	private Button chitiet;

	ObservableList<Phieudathang> listPDHkt;

	@FXML
	private AnchorPane ap;

	public void falsedisable() {
		ap.setDisable(false);
	}

	public void truedisable() {
		ap.setDisable(true);
	}

	public ObservableList<Phieudathang> getPhieudathangkt() {
		ObservableList<Phieudathang> phieudathangkt = FXCollections.observableArrayList();
		Session session = HibernateUtils.getSessionFactory().openSession();
		CriteriaQuery<Phieudathang> pdhkt = session.getCriteriaBuilder().createQuery(Phieudathang.class);
		pdhkt.from(Phieudathang.class);
		List<Phieudathang> eList = session.createQuery(pdhkt).getResultList();
		for (Phieudathang ent : eList) {
			phieudathangkt.add(ent);
		}
		return phieudathangkt;
	}
	private static double xoffset = 0;
	private static double yoffset = 0;

	public void loadData(Taikhoannv taikhoan) {
		chitiet.setOnMouseClicked(event -> {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("NhapHangvaTraHang.fxml"));
				Parent kiemtraViewParent = loader.load();
				Stage stage1 = new Stage();
				Scene scene1 = new Scene(kiemtraViewParent);
				scene1.setOnMousePressed(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {
						xoffset = stage1.getX() - mouseEvent.getScreenX();
						yoffset = stage1.getY() - mouseEvent.getScreenY();
					}
				});
				scene1.setOnMouseDragged(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mouseEvent) {
						stage1.setX(mouseEvent.getScreenX() + xoffset);
						stage1.setY(mouseEvent.getScreenY() + yoffset);
					}
				});
				stage1.initStyle(StageStyle.UNDECORATED);
				Phieudathang selected = phieudathangkt.getSelectionModel().getSelectedItem();
				NhapHangvaTraHang Dathang1 = loader.getController();
				if (selected == null) {
					thongbaoKT.setText("Không có phiếu đặt hàng được chọn!!!");
					return;
				} else if (selected != null && selected.getKiemtrahang() == true) {
						thongbaoKT.setText("Phiếu đặt hàng đã được kiểm tra!!!");
						return;
					}
				Dathang1.setKiemtrahang(selected);
				Dathang1.loadData(taikhoan);
				stage1.setTitle("Kiem tra hang");
				stage1.setScene(scene1);
				stage1.show();
				GiaoDienQLController.getInstance().truedisable();
			} catch (Exception e) {
				System.out.print(e);
			}
		});
	}

	public void khoitao() {
		madathangkt.setCellValueFactory(new PropertyValueFactory<Phieudathang, Integer>("madathang"));
		thoigiandatkt.setCellValueFactory(new PropertyValueFactory<Phieudathang, String>("thoigian"));
		tongtienkt.setCellValueFactory(new PropertyValueFactory<Phieudathang, Integer>("tongtien"));
		mancckt.setCellValueFactory(new PropertyValueFactory<>("nhacungcap"));
		mancckt.setCellFactory(phieudathangkt -> new TableCell<Phieudathang, Nhacungcap>() {
			@Override
			protected void updateItem(Nhacungcap item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText(String.valueOf(item.getMancc()));
				}
			}
		});
		select.setCellValueFactory(c -> new SimpleBooleanProperty(c.getValue().getKiemtrahang()));
		select.setCellFactory(tc -> new CheckBoxTableCell<>());
		phieudathangkt.setItems(getPhieudathangkt());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		khoitao();
	}

}
