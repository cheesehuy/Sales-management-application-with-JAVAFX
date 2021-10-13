package QLBH;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import entities.*;
import BanHang.BanHangController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GiaoDienNhanvienController{
	
	public static GiaoDienNhanvienController instance;

	public GiaoDienNhanvienController() {
		instance = this;
	}

	public static GiaoDienNhanvienController getInstance() {
		return instance;
	}
	
	public void falsedisable() {
		maintonv.setDisable(false);
	}
	public void truedisable() {
		maintonv.setDisable(true);
	}

    @FXML
    private Button banhang;

    @FXML
    private Button danhmuc;

    @FXML
    private Button khachhang;

    @FXML
    private Label username;

    @FXML
    private ImageView imgnhanvien;

    @FXML
    private Button dangxuat;

    @FXML
    private AnchorPane mainpane;
    
    @FXML
    private AnchorPane maintonv;

    @FXML
    private ImageView load;

    @FXML
    private ImageView close;

    @FXML
    private ImageView minimize;

    private static double xoffset =0; 
	private static double yoffset =0; 
    
    @FXML
	void DangXuat(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("loginform.fxml"));
		Parent tmp;
		tmp = loader.load();
		Scene scene = new Scene(tmp);
		scene.setFill(null);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene.getStylesheets().add(getClass().getResource("QLBH.css").toExternalForm());
		scene.setOnMousePressed(new EventHandler<MouseEvent>() {
			  @Override public void handle(MouseEvent mouseEvent) {
			    // record a delta distance for the drag and drop operation.
			    xoffset = stage.getX() - mouseEvent.getScreenX();
			    yoffset = stage.getY() - mouseEvent.getScreenY();
			  }
			});
			scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			  @Override public void handle(MouseEvent mouseEvent) {
			    stage.setX(mouseEvent.getScreenX() + xoffset);
			    stage.setY(mouseEvent.getScreenY() + yoffset);
			  }
			});
			stage.getIcons().add(new Image(QLBH.class.getResourceAsStream("backgroundSGU.png")));
			stage.setScene(scene);
			stage.setResizable(false);
			
		
	
		stage.show();
	}

    
    public void LoadData(Taikhoannv taikhoan) throws Exception {
    	username.setText("Xin chào " + taikhoan.getusername());
		username.setUnderline(true);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/DanhMuc/lichsubanhang.fxml"));
		AnchorPane pane = loader.load();
		mainpane.getChildren().setAll(pane);
		byte[] getImageInBytes = taikhoan.getNhanvien().getImage();
		try {
			FileOutputStream fos = new FileOutputStream(new File("photo1.jpg"));
			fos.write(getImageInBytes);
			fos.close();
			Image image = new Image("file:photo1.jpg", imgnhanvien.getFitHeight(), imgnhanvien.getFitHeight(), true,
					true);
			imgnhanvien.setImage(image);
		} catch (Exception e) {
			e.printStackTrace();
		}
		username.setOnMouseClicked(event -> {
			profileNhanvien(taikhoan);

		});

		banhang.setOnMouseClicked(event -> {
			SceneBanHang(taikhoan);
		});
		
		khachhang.setOnMouseClicked(event -> {
			SceneKhachHang();
		});
		danhmuc.setOnMouseClicked(event -> {
			SceneLichSuBanHang();
		});
    }
    
    public void SceneBanHang(Taikhoannv taikhoan) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/BanHang/banhang.fxml"));
			AnchorPane pane = loader.load();
			mainpane.getChildren().setAll(pane);
			BanHangController banHangController = loader.getController();
			banHangController.loadData(taikhoan);
			System.out.println(taikhoan);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e);
		}
	}
    
    public void SceneKhachHang() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/KhachHang/khachhang.fxml"));
			AnchorPane pane = loader.load();
			mainpane.getChildren().setAll(pane);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			// e.printStackTrace();
		}
	}
    
    public void profileNhanvien(Taikhoannv taikhoan) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("profilenhanvien.fxml"));
			Parent tmp;
			tmp = loader.load();
			Scene scene = new Scene(tmp);
			Stage stage = new Stage();
			scene.getStylesheets().add(getClass().getResource("QLBH.css").toExternalForm());
			scene.setOnMousePressed(new EventHandler<MouseEvent>() {
				  @Override public void handle(MouseEvent mouseEvent) {
				    // record a delta distance for the drag and drop operation.
				    xoffset = stage.getX() - mouseEvent.getScreenX();
				    yoffset = stage.getY() - mouseEvent.getScreenY();
				  }
				});
				scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
				  @Override public void handle(MouseEvent mouseEvent) {
				    stage.setX(mouseEvent.getScreenX() + xoffset);
				    stage.setY(mouseEvent.getScreenY() + yoffset);
				  }
				});
			ProfilesNhanvienController profiles = loader.getController();
			profiles.loadData(taikhoan);
			stage.hide();
			stage.initStyle(StageStyle.UNDECORATED);
			GiaoDienNhanvienController.getInstance().truedisable();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public void SceneLichSuBanHang() {
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/DanhMuc/lichsubanhang.fxml"));
			AnchorPane pane = loader.load();
			mainpane.getChildren().setAll(pane);
		} catch (IOException e) {
			System.out.println(e);
			
		}
    }

    @FXML
	void close(MouseEvent event) {
		Stage stage = (Stage) close.getScene().getWindow();
		stage.close();
	}

	@FXML
	void minimize(MouseEvent event) {
		Stage stage = (Stage) minimize.getScene().getWindow();
		stage.setIconified(true);
	}

}