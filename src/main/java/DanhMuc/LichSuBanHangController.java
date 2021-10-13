package DanhMuc;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;

import QLBH.GiaoDienNhanvienController;
import QLBH.GiaoDienQLController;
import QLBH.HibernateUtils;
import entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LichSuBanHangController implements Initializable{

    @FXML
    private Text txtTitle_store;

    @FXML
    private TableView<Hoadon> tableHoaDon;
   
    ObservableList<Hoadon> listPHD;

    @FXML
    private TableColumn mahoadon;

    @FXML
    private TableColumn thoigianmua;

    @FXML
    private TableColumn tonggia;

    @FXML
    private TableColumn makh;

    @FXML
    private TableColumn manv1;

    @FXML
    private TextField searchPHD;

    @FXML
    private Label thongbaoHD;

    @FXML
    private Label lbDanhMucPHD;


	private static double xoffset = 0;
	private static double yoffset = 0;
    
    @FXML
    void changeSceneHoadonDetail(ActionEvent event) throws IOException {
    	if(GiaoDienNhanvienController.getInstance() == null) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/DanhMuc/HoadonDetail.fxml"));
    		Parent hoadonViewParent = loader.load();
    		Stage stage = new Stage();
    		Scene scene = new Scene(hoadonViewParent);
    		scene.setOnMousePressed(new EventHandler<MouseEvent>() {
    			@Override
    			public void handle(MouseEvent mouseEvent) {
    				xoffset = stage.getX() - mouseEvent.getScreenX();
    				yoffset = stage.getY() - mouseEvent.getScreenY();
    			}
    		});
    		scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
    			@Override
    			public void handle(MouseEvent mouseEvent) {
    				stage.setX(mouseEvent.getScreenX() + xoffset);
    				stage.setY(mouseEvent.getScreenY() + yoffset);
    			}
    		});
    		stage.initStyle(StageStyle.UNDECORATED);
    		Hoadon selected = tableHoaDon.getSelectionModel().getSelectedItem();
    		HoadonDetailController DSNVController = loader.getController();
    		if(selected == null) {
    			 thongbaoHD.setVisible(true);
    			 thongbaoHD.setText("Không có phiếu hóa đơn được chọn!!!");
    			 System.out.print("Không có phiếu hóa đơn được chọn!!!");
    			 return;
    		 }
    		 else if(selected != null){
    			 DSNVController.setHoadon(selected);
    			 thongbaoHD.setVisible(false);
    			 
    		 }
    		stage.setTitle("Chi tiet hoa don");
    		stage.setScene(scene);
    		stage.show();
    		GiaoDienQLController.getInstance().truedisable();
    	}else {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/DanhMuc/HoadonDetail.fxml"));
    		Parent hoadonViewParent = loader.load();
    		Stage stage = new Stage();
    		Scene scene = new Scene(hoadonViewParent);
    		scene.setOnMousePressed(new EventHandler<MouseEvent>() {
    			@Override
    			public void handle(MouseEvent mouseEvent) {
    				xoffset = stage.getX() - mouseEvent.getScreenX();
    				yoffset = stage.getY() - mouseEvent.getScreenY();
    			}
    		});
    		scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
    			@Override
    			public void handle(MouseEvent mouseEvent) {
    				stage.setX(mouseEvent.getScreenX() + xoffset);
    				stage.setY(mouseEvent.getScreenY() + yoffset);
    			}
    		});
    		stage.initStyle(StageStyle.UNDECORATED);
    		Hoadon selected = tableHoaDon.getSelectionModel().getSelectedItem();
    		HoadonDetailController DSNVController = loader.getController();
    	//	DSNVController.setHoadon(selected);
    		if(selected == null) {
    			 thongbaoHD.setVisible(true);
    			 thongbaoHD.setText("Không có phiếu hóa đơn được chọn!!!");
    			 System.out.print("Không có phiếu hóa đơn được chọn!!!");
    			 return;
    		 }
    		 else if(selected != null){
    			 DSNVController.setHoadon(selected);
    			 thongbaoHD.setVisible(false);
    			 
    		 }
    		stage.setTitle("Chi tiet hoa don");
    		stage.setScene(scene);
    		stage.show();
    		GiaoDienNhanvienController.getInstance().truedisable();
    	}
    	

    }
    public ObservableList<Hoadon> getHoadon() {
		ObservableList<Hoadon> tableHoaDon = FXCollections.observableArrayList();
		Session session = HibernateUtils.getSessionFactory().openSession();
		CriteriaQuery<Hoadon> hd = session.getCriteriaBuilder().createQuery(Hoadon.class);
		hd.from(Hoadon.class);
		List<Hoadon> eList = session.createQuery(hd).getResultList();
		for (Hoadon ent : eList) {
			tableHoaDon.add(ent);
		}
		return tableHoaDon;
	}
    
    void searchPHD() {
		ObservableList<Hoadon> tbHoaDon = FXCollections.observableArrayList(getHoadon());

		FilteredList<Hoadon> filteredData = new FilteredList<>(tbHoaDon, b -> true);
		searchPHD.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(hoadon -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (String.valueOf(hoadon.getMahoadon()).indexOf(lowerCaseFilter) != -1) {
					return true; 
					
				} else
					return false;
			});
		});

		SortedList<Hoadon> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tableHoaDon.comparatorProperty());
		tableHoaDon.setItems(sortedData);
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		mahoadon.setCellValueFactory(new PropertyValueFactory<Hoadon, String>("mahoadon"));
		thoigianmua.setCellValueFactory(new PropertyValueFactory<Hoadon, String>("thoigianmua"));
		tonggia.setCellValueFactory(new PropertyValueFactory<Hoadon, Integer>("tonggia"));
		makh.setCellFactory(tableHoaDon -> new TableCell<Hoadon, KhachHang>() {
			@Override
			protected void updateItem(KhachHang item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText(Integer.toString(item.getMakh()));
				}
			}

		});
		manv1.setCellFactory(tableHoaDon -> new TableCell<Hoadon, Nhanvien>() {
			@Override
			protected void updateItem(Nhanvien item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText(Integer.toString(item.getManv()));
				}
			}

		});

		makh.setCellValueFactory(new PropertyValueFactory<>("khachhang"));
		manv1.setCellValueFactory(new PropertyValueFactory<>("nhanvien"));
		tableHoaDon.setItems(getHoadon());
		searchPHD();
	}
    

}