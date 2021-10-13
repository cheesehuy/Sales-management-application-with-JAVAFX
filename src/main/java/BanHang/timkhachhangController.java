package BanHang;



import static org.junit.Assume.assumeNoException;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import QLBH.GiaoDienNhanvienController;
import QLBH.GiaoDienQLController;
import QLBH.HibernateUtils;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class timkhachhangController implements Initializable{

    @FXML
    private TableColumn nsKH;

    @FXML
    private TableColumn diemtichluy;

    @FXML
    private TableColumn hvtKH;

    @FXML
    private TableColumn sdtKH;

    @FXML
    private TableColumn emailKH;

    @FXML
    private TableColumn idKH;

    @FXML
    private TableColumn gtKH;

    @FXML
    private TableView<KhachHang> tableKH;
    
    @FXML
    private TextField searchKH ;
    @FXML
    private Button them;
    
    @FXML
    private AnchorPane ap;
    
    private Stage thisStage;
    
    private final BanHangController banHangController;
    
    @FXML
    private Label thongbao;
    
    @FXML
    private ImageView exit;
    
    @FXML
    void exit(MouseEvent event) {
    	if( GiaoDienQLController.getInstance() ==null) {
    		Stage stage = (Stage) ap.getScene().getWindow();
    		stage.close();
    		GiaoDienNhanvienController.getInstance().falsedisable();
      	}
    	else if(GiaoDienNhanvienController.getInstance() ==null){
    	Stage stage = (Stage) ap.getScene().getWindow();
		stage.close();
		GiaoDienQLController.getInstance().falsedisable();
		}
    	else {
    		Stage stage = (Stage) ap.getScene().getWindow();
    		stage.close();
    		GiaoDienQLController.getInstance().falsedisable();
    		GiaoDienNhanvienController.getInstance().falsedisable();
    	}
    }
    
    
    
	public void initialize(URL location, ResourceBundle resources) {
		idKH.setCellValueFactory(new PropertyValueFactory<KhachHang, Integer>("makh"));
		hvtKH.setCellValueFactory(new PropertyValueFactory<KhachHang, String>("tenkh"));
		sdtKH.setCellValueFactory(new PropertyValueFactory<KhachHang, Integer>("sodienthoai"));
		nsKH.setCellValueFactory(new PropertyValueFactory<KhachHang, Integer>("ngaysinh"));
		gtKH.setCellValueFactory(new PropertyValueFactory<KhachHang, String>("gioitinh"));
		emailKH.setCellValueFactory(new PropertyValueFactory<KhachHang, String>("email"));
		diemtichluy.setCellValueFactory(new PropertyValueFactory<KhachHang, Integer>("diemtichluy"));
		tableKH.setItems(getKhachHang());
		searchKH();
			
	}
	
	public void OpenScence() {
		try {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("timkhachhang.fxml"));

         // Set this class as the controller
         loader.setController(this);

         // Load the scene
         Scene scene = new Scene(loader.load());
         thisStage.setScene(scene);

         // Setup the window/stage
         thisStage.setTitle("Chọn khách hàng");      
         // xóa viền window
         thisStage.initStyle(StageStyle.UNDECORATED);
         
         scene.setOnMousePressed(new EventHandler<MouseEvent>() {
			  @Override public void handle(MouseEvent mouseEvent) {
			    xoffset = thisStage.getX() - mouseEvent.getScreenX();
			    yoffset = thisStage.getY() - mouseEvent.getScreenY();
			  }
			});
			scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			  @Override public void handle(MouseEvent mouseEvent) {
			    thisStage.setX(mouseEvent.getScreenX() + xoffset);
			    thisStage.setY(mouseEvent.getScreenY() + yoffset);
			  }
			});
		}catch (Exception e) {
            e.printStackTrace();
	}
	}
	private static double xoffset =0; 
	private static double yoffset =0; 
	public timkhachhangController(BanHangController banHangController ) {
		 this.banHangController = banHangController;

	        // Create the new stage
	        thisStage = new Stage();
	        

	        // Load the FXML file
	        try {
	        	if(GiaoDienQLController.getInstance() == null) {
	        		 OpenScence();
	        	}
	        	else if(GiaoDienNhanvienController.getInstance() == null){
	        		 OpenScence();
	        	}else {
	        		OpenScence();
	        	}
	           

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	}
	
	
	public void showStage() {
        thisStage.showAndWait();
    }
	
	

	public ObservableList<KhachHang> getKhachHang() {
		ObservableList<KhachHang> tableKH = FXCollections.observableArrayList();
		Session session = HibernateUtils.getSessionFactory().openSession();

		CriteriaQuery<KhachHang> kh = session.getCriteriaBuilder().createQuery(KhachHang.class);
		kh.from(KhachHang.class);
		List<KhachHang> eList = session.createQuery(kh).getResultList();
		for (KhachHang ent : eList) {
			tableKH.add(ent);
		}
		return tableKH;
	}
	void searchKH() {
		ObservableList<KhachHang> table = FXCollections.observableArrayList(getKhachHang());
		FilteredList<KhachHang> filterData = new FilteredList<>(table, p -> true);
		searchKH.textProperty().addListener((observable, oldvalue, newvalue) -> {
			filterData.setPredicate(kh -> {
				if (newvalue == null || newvalue.isEmpty()) {
					return true;
				}
				String typetext = newvalue.toLowerCase();
				if (kh.getTenkh().toLowerCase().indexOf(typetext) != -1) {
					return true;
				}
				return false;

			});
			SortedList<KhachHang> sortedList = new SortedList<>(filterData);
			sortedList.comparatorProperty().bind(tableKH.comparatorProperty());
			tableKH.setItems(sortedList);
		});
	}
	
	
	//

	 
	 @FXML
	 void them(ActionEvent event) throws IOException  {
		 KhachHang selected = tableKH.getSelectionModel().getSelectedItem();
		 if(selected == null) {
			 thongbao.setVisible(true);
			 thongbao.setText("Không có khách hàng được chọn");
			 return;
		 }
		 else if(selected != null){
			 banHangController.setkhachhang(selected);
			 thongbao.setText(null);
		 }
		 Stage stage1 = (Stage) ap.getScene().getWindow();
    	 stage1.close();

	 }
	
}

