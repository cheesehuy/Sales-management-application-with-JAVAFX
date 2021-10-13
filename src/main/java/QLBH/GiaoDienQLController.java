package QLBH;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import org.hibernate.query.Query;
import org.hibernate.type.descriptor.sql.NVarcharTypeDescriptor;
import entities.*;
import BanHang.BanHangController;
import DanhMuc.HoadonDetailController;
import KhachHang.khachhangController;
import Nhanvien.NhanvienController;
import QLKho.QLKhoController;

import javax.persistence.criteria.CriteriaQuery;

import org.apache.derby.vti.Restriction.AND;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import KiemTraHang.KiemTraHangController;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

public class GiaoDienQLController implements Initializable {
	
	
	public static GiaoDienQLController instance;

	public GiaoDienQLController() {
		instance = this;
	}

	public static GiaoDienQLController getInstance() {
		return instance;
	}

	 @FXML
	 private AnchorPane mainto;
	
	@FXML
	private Button banhang;
	
	@FXML
    private ImageView load;
	@FXML
	private Button nhanvien;

	@FXML
	private Button thongke;

	@FXML
	private Button kho;

	@FXML
	private Button kiemtrahang;

	@FXML
	private Button danhmuc;

	@FXML
	private Button nhacungcap;

	@FXML
	private Button khachhang;

	@FXML
	private AnchorPane mainpane;

	@FXML
	private Label username;

	@FXML
	private ImageView imgnhanvien;

	@FXML
	private ImageView close;

	@FXML
	private ImageView minimize;
	
	@FXML
	private ImageView imgtrangchu;


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

	@FXML
	private Button dangxuat;

	public void LoadData(Taikhoannv taikhoan) {
		PauseTransition visiblePause = new PauseTransition(
		        Duration.seconds(3)
		);
		visiblePause.setOnFinished(
		        event -> load.setVisible(false)
		);
		visiblePause.play();

		username.setText("Xin chào " + taikhoan.getusername());
		username.setUnderline(true);
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

		kho.setOnMouseClicked(event -> {
			SceneQLKho(taikhoan);
		});

		khachhang.setOnMouseClicked(event -> {
			SceneKhachHang();
		});
		nhacungcap.setOnMouseClicked(event -> {
			SceneNhaCungCap();
		});
		danhmuc.setOnMouseClicked(event -> {
			SceneDanhMuc();
		});
		thongke.setOnMouseClicked(event -> {
			SceneThongKe();
		});
		kiemtrahang.setOnMouseClicked(event -> {
			SceneKiemTraHang(taikhoan);
		});
		imgtrangchu.setOnMouseClicked(event -> {
			SceneTrangChu();
		});
		nhanvien.setOnMouseClicked(event -> {
			SceneNhanvien();
		});
	}
	
	public void SceneNhanvien() {
		try {
			AnchorPane pane = FXMLLoader.load(getClass().getResource("/Nhanvien/nhanvien.fxml"));
			mainpane.getChildren().setAll(pane);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void profileNhanvien(Taikhoannv taikhoan) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("profilenhanvien.fxml"));
			Parent tmp;
			tmp = loader.load();
			Scene scene = new Scene(tmp);
			Stage stage = new Stage();
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
		
			stage.setResizable(false);
			
			ProfilesNhanvienController profiles = loader.getController();
			profiles.loadData(taikhoan);
			stage.hide();
			stage.setScene(scene);
			stage.show();
			GiaoDienQLController.getInstance().truedisable();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
		}
	}
	
	public void falsedisable() {
		mainto.setDisable(false);
	}
	public void truedisable() {
		mainto.setDisable(true);
	}

	public void SceneBanHang(Taikhoannv taikhoan) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/BanHang/banhang.fxml"));
			AnchorPane pane = loader.load();
			mainpane.getChildren().setAll(pane);
			BanHangController banHangController = loader.getController();
			banHangController.loadData(taikhoan);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e);
		}
	}
	
	public void SceneTrangChu() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/QLBH/TrangChu.fxml"));
			AnchorPane pane = loader.load();
			mainpane.getChildren().setAll(pane);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void SceneQLKho(Taikhoannv taikhoan) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/QLKho/QLKho.fxml"));
			AnchorPane pane = loader.load();
			mainpane.getChildren().setAll(pane);
			QLKhoController qlKhoController = loader.getController();
			qlKhoController.loadData(taikhoan);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void SceneKhachHang() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/KhachHang/khachhang.fxml"));
			AnchorPane pane = loader.load();

			mainpane.getChildren().setAll(pane);
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void SceneNhaCungCap() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Nhacungcap/nhacungcap.fxml"));
			AnchorPane pane = loader.load();

			mainpane.getChildren().setAll(pane);
		} catch (IOException e) {
			System.out.println(e);
			
		}
	}

	public void SceneDanhMuc() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/DanhMuc/danhmuc.fxml"));
			AnchorPane pane = loader.load();

			mainpane.getChildren().setAll(pane);
		} catch (IOException e) {
			System.out.println(e);
			
		}
	}

	public void SceneThongKe() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/ThongKe/thongkedoanhthutheongay.fxml"));
			AnchorPane pane = loader.load();

			mainpane.getChildren().setAll(pane);
		} catch (IOException e) {
			System.out.println(e);
			
		}
	}

	public void SceneKiemTraHang(Taikhoannv taikhoan) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/KiemTraHang/Kiemtrahang.fxml"));
			AnchorPane pane = loader.load();
			KiemTraHangController KTHang = loader.getController();
			KTHang.loadData(taikhoan);
			mainpane.getChildren().setAll(pane);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private static double xoffset =0; 
	private static double yoffset =0; 
	@FXML
	void DangXuat(ActionEvent event) throws IOException {
	try {
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
			stage.initStyle(StageStyle.TRANSPARENT);
		
		stage.setScene(scene);
		stage.show();
	} catch (Exception e) {
		// TODO: handle exception
	}
	
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		SceneTrangChu();
	}

}
