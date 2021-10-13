package QLKho;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.criteria.CriteriaQuery;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import org.hibernate.*;
import org.hibernate.query.Query;

import QLBH.GiaoDienQLController;
import QLBH.HibernateUtils;
import entities.*;

public class ThemSanPhamController extends Application implements Initializable {
	private FileChooser filechooser;
	private Image image;
	private File file;

	@FXML
	private TextField tensanpham;

	@FXML
	private TextField soluong;

	@FXML
	private TextField giatien;

	@FXML
	private ImageView imageSP;

	@FXML
	private Button themanhsp;

	@FXML
	private Button luusp;

	@FXML
	private Button huySP;

	@FXML
	private AnchorPane ap;

	@FXML
	private Label thongbao;

	@FXML
	private Label thongbao1;

	@FXML
	private Label thongbao2;

	@FXML
	private Label thongbao3;

	@FXML
	private Label thongbao4;

	@FXML
	private Label thongbao5;

	@FXML
	private ComboBox<String> loaisanpham;

	@FXML
	private ComboBox<String> donvi;

	@FXML
	private ImageView exit;

	@FXML
	void exit(MouseEvent event) {
		Stage stage = (Stage) exit.getScene().getWindow();
		stage.close();
		GiaoDienQLController.getInstance().falsedisable();
	}

	@FXML
	void LuuSP(ActionEvent event) throws Exception {
		if (KiemTraTen() & KiemTraSL() & KiemTraDonVi() & KiemTraHinh() & KiemTraGia() & KiemTraLoai()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Thêm sản phẩm");
			int giatiensp = Integer.parseInt(giatien.getText());
			int slsp = Integer.parseInt(soluong.getText());
			String tensp = tensanpham.getText();
			String donvisp = donvi.getValue();
			String loaisp = loaisanpham.getValue();
			Session session = HibernateUtils.getSessionFactory().openSession();
			FileInputStream fis = new FileInputStream(file);
			byte[] bFile = new byte[(int) (file.length())];
			fis.read(bFile);
			Sanpham sp = new Sanpham(tensp, loaisp, donvisp, giatiensp, slsp, bFile);
			try {
				session.beginTransaction();
				session.save(sp);
				session.getTransaction().commit();
				alert.setContentText("Thêm sản phẩm thành công !");
				alert.showAndWait();
				Stage stage = (Stage) luusp.getScene().getWindow();
				stage.close();
				QLKhoController.getInstance().initialize1();
				GiaoDienQLController.getInstance().falsedisable();
				QLKhoController.getInstance().ReloadSANPHAM();
			} catch (RuntimeException error) {
				session.getTransaction().rollback();
			}
		}

	}

	private boolean KiemTraHinh() throws Exception {
		if (imageSP.getImage() == null) {
			thongbao5.setText("Vui lòng thêm ảnh");
			return false;
		}
		thongbao5.setText(null);
		return true;
	}

	private boolean KiemTraTen() {
		if (tensanpham.getText().isEmpty()) {
			thongbao.setText("Vui lòng điền tên sản phẩm");
			return false;
		}
		thongbao.setText(null);
		return true;
	}

	private boolean KiemTraGia() {
		Pattern p = Pattern.compile("[0-9]+");
		Matcher m = p.matcher(giatien.getText());
		if (m.find() && m.group().equals(giatien.getText())) {
			thongbao3.setText(null);
			return true;
		} else {
			thongbao3.setText("Vui lòng điền số phù hợp");
			return false;
		}
	}

	private boolean KiemTraSL() {
		Pattern p = Pattern.compile("[0-9]+");
		Matcher m = p.matcher(soluong.getText());
		if (m.find() && m.group().equals(soluong.getText())) {
			thongbao2.setText(null);
			return true;
		} else {
			thongbao2.setText("Vui lòng điền số phù hợp");
			return false;
		}
	}

	private boolean KiemTraDonVi() {
		if (donvi.getValue() == null) {
			thongbao1.setText("Chưa chọn đơn vị");
			return false;
		}
		thongbao1.setText(null);
		return true;

	}

	private boolean KiemTraLoai() {
		if (loaisanpham.getValue() == null) {
			thongbao4.setText("Chưa chọn loại");
			return false;
		}
		thongbao4.setText(null);
		return true;

	}

	@FXML
	void ThemAnhSP(ActionEvent event) {
		Stage stage = (Stage) ap.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png");
		fileChooser.getExtensionFilters().add(imageFilter);
		file = fileChooser.showOpenDialog(stage);
		if (file != null) {
			image = new Image(file.toURI().toString(), 100, 150, false, true);
			imageSP.setImage(image);
		}
	}

	@FXML
	void huySP(ActionEvent event) {
		Stage stage = (Stage) huySP.getScene().getWindow();
		stage.close();
		GiaoDienQLController.getInstance().falsedisable();	
	}
	public ObservableList<String> getSanpham() {
		ObservableList<String> TableSP = FXCollections.observableArrayList();
		Session session = HibernateUtils.getSessionFactory().openSession();

		String hql = "SELECT SP.loaisanpham FROM Sanpham SP GROUP BY SP.loaisanpham";
		Query query = session.createQuery(hql);
		List<String> loaisanpham = query.list();
		for (String list : loaisanpham) {
			TableSP.add(list);
		}
		return TableSP;
	}
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> list = FXCollections.observableArrayList("Cái ", "Chai ", "Bình ", "Bịch", "Hộp", "Ly",
				"Nắm", "123");
		loaisanpham.setItems(getSanpham());
		loaisanpham.getItems().add("");
		//loaisanpham.getItems().addAll("nước giải khát", "snack", "thức ăn nhanh", "");
		loaisanpham.setCellFactory(lv -> {
			ListCell<String> cell = new ListCell<String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (empty) {
						setText(null);
					} else {
						if (item.isEmpty()) {
							setText("Thêm loại sản phẩm...");
						} else {
							setText(item);
						}
					}
				}
			};

			cell.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> {
				if (cell.getItem().isEmpty() && !cell.isEmpty()) {
					TextInputDialog dialog = new TextInputDialog();
					dialog.setContentText("Nhập loại sản phẩm");
					dialog.showAndWait().ifPresent(text -> {
						int index = loaisanpham.getItems().size() - 1;
						loaisanpham.getItems().add(index, text);
						loaisanpham.getSelectionModel().select(index);
					});
					evt.consume();
				}
			});

			return cell;
			
		});
		donvi.setItems(list);

	}

	@Override
	public void start(Stage arg0) throws Exception {
	}

}