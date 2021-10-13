package Nhanvien;
import java.sql.Date;
import java.time.LocalDate;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import QLBH.GiaoDienQLController;
import QLBH.HibernateUtils;
import entities.*;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.HibernateException;
import org.hibernate.Session;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NhanvienController implements Initializable {

	public static NhanvienController instance;

	public NhanvienController() {
		instance = this;
	}

	public static NhanvienController getInstance() {
		return instance;
	}

	private Image image;
	@FXML
	private Button idaddNV;

	@FXML
	private AnchorPane anchorpane;

	@FXML
	private TableView<Nhanvien> tableNV;

	@FXML
	private TableColumn<Nhanvien,String> hovaten;

	@FXML
	private TableColumn<Nhanvien,Date> ngaysinh;

	@FXML
	private TableColumn<Nhanvien,String> chucvu;

	@FXML
	private TableColumn<Nhanvien,String> sdt;

	@FXML
	private TableColumn<Nhanvien,Integer> cmnd;

	@FXML
	private TableColumn<Nhanvien,String> diachi;

	@FXML
	private Button idreload;

	@FXML
	private TextField hovaten_nv;

	@FXML
	private DatePicker ns_nv;

	@FXML
	private DatePicker ngayvaolam;

	@FXML
	private TextField sdt_nv;

	@FXML
	private TextField cmnd_nv;

	@FXML
	private TextField diachi_nv;

	@FXML
	private Button xoa_nv;

	@FXML
	private Button capnhat_nv;

	@FXML
	private TextField id_nv;

	@FXML
	private ImageView imgnhanvien;

	@FXML
	private Button luucapnhat;

	@FXML
	private Button reset;

	@FXML
	private TextField search;

	@FXML
	private ComboBox<String> Listnhanvien;

	@FXML
	private Label check_hovaten;

	@FXML
	private Label check_ngaysinh;

	@FXML
	private Label check_sdt;

	@FXML
	private Label check_cmnd;

	@FXML
	private Label check_diachi;

	@FXML
	private ComboBox<String> cb_chucvu;

	@FXML
	private ComboBox<String> cb_gioitinh;

	public boolean KiemTraTenNV(String tennhanvien) {
		Pattern p = Pattern.compile("^[A-Za-zÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]+$");
		Matcher m = p.matcher(tennhanvien);
		if (m.find() && m.group().equals(tennhanvien)) {
			check_hovaten.setText(null);
			return true;
		} else {
			check_hovaten.setText("Vui lòng điền tên hợp lệ");
			return false;
		}
	}

	public boolean KiemTraSDT(String sdt) {
		Pattern p = Pattern.compile("[0-9]+");
		Matcher m = p.matcher(sdt);
		if (m.find() && m.group().equals(sdt) && sdt.matches("\\d{10}|\\d{11}")) {
			check_sdt.setText(null);
			return true;
		} else if (sdt_nv.getText().length() < 10) {
			check_sdt.setText("Số điện thoại không đủ 10 số");
			return false;
		} else {
			check_sdt.setText("Vui lòng điền số điện thoại hợp lệ");
			return false;
		}
	}


	public boolean KiemTraCMND(String cmnd) {
		Pattern p = Pattern.compile("[0-9]+");
		Matcher m = p.matcher(cmnd);
		if (m.find() && m.group().equals(cmnd)) {
			check_cmnd.setText(null);
			return true;
		} else {
			check_cmnd.setText("Vui lòng điền cmnd hợp lệ");
			return false;
		}
	}
	
	

	public boolean KiemTraDiaChi(String diachi) {
		if (diachi.isEmpty()) {
			return false;
		}
		check_diachi.setText(null);
		return true;
	}

	@FXML
	void Reset(ActionEvent event) {
		Nhanvien nv = tableNV.getItems().get(tableNV.getSelectionModel().getSelectedIndex());
		id_nv.setText(Integer.toString(nv.getManv()));
		hovaten_nv.setText(nv.getHovaten());
		ns_nv.setValue(nv.getNgaysinh());
		cb_chucvu.getSelectionModel().select(nv.getChucvu());
		cb_gioitinh.getSelectionModel().select(nv.getGioitinh());
		sdt_nv.setText(nv.getSdt());
		diachi_nv.setText(nv.getDiachi());
		cmnd_nv.setText(String.valueOf(nv.getCmnd()));
		ngayvaolam.setValue(nv.getNgayvaolam());
	}

	private static double xoffset = 0;
	private static double yoffset = 0;

	@FXML
	private void ThemNV(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/Nhanvien/themnhanvien.fxml"));
		Scene scene = new Scene(root);
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
		stage.setScene(scene);
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setTitle("Them nhan vien");
		stage.show();
		GiaoDienQLController.getInstance().truedisable();
	}

	@FXML
	void luucapnhat(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Cập nhật ");
		int idnv = (Integer.parseInt(id_nv.getText()));
		String hovatennv = hovaten_nv.getText();
		LocalDate ngaysinhnv = ns_nv.getValue();
		String chucvunv = cb_chucvu.getValue();
		String gioitinhnv = cb_gioitinh.getValue();
		LocalDate nvl = ngayvaolam.getValue();
		String sdtnv = sdt_nv.getText();
		String cmndnv = cmnd_nv.getText();
		String diachinv = diachi_nv.getText();
		if (KiemTraTenNV(hovatennv) & KiemTraSDT(sdtnv) & KiemTraCMND(cmndnv) &KiemTraDiaChi(diachinv)) {
			Session session = HibernateUtils.getSessionFactory().openSession();
			try {
				int cmnd = Integer.parseInt(cmndnv);
				session.beginTransaction();
				Nhanvien nv2 = new Nhanvien();
				nv2 = session.get(Nhanvien.class, idnv);
				nv2.setHovaten(hovatennv);
				nv2.setChucvu(chucvunv);
				nv2.setGioitinh(gioitinhnv);
				nv2.setDiachi(diachinv);
				nv2.setNgaysinh(ngaysinhnv);
				nv2.setSdt(sdtnv);
				nv2.setCmnd(cmnd);
				nv2.setNgayvaolam(nvl);
				session.save(nv2);
				alert.setContentText("Cập nhật nhân viên thành công !");
				alert.showAndWait();
				SetFalseEditable();
				session.getTransaction().commit();
				initializeNHANVIEN();
			} catch (RuntimeException error) {
				session.getTransaction().rollback();
			}
		}
	}
	
	void SetFalseEditable() {
		luucapnhat.setVisible(false);
		reset.setVisible(false);
		hovaten_nv.setEditable(false);
		ns_nv.setEditable(false);
		cb_chucvu.setEditable(false);
		cb_gioitinh.setEditable(false);
		cmnd_nv.setEditable(false);
		sdt_nv.setEditable(false);
		diachi_nv.setEditable(false);
		ngayvaolam.setEditable(false);
	}

	@FXML
	void search() {
		ObservableList<Nhanvien> table = FXCollections.observableArrayList(getNhanvien());
		FilteredList<Nhanvien> filterData = new FilteredList<>(table, p -> true);
		search.textProperty().addListener((observable, oldvalue, newvalue) -> {
			filterData.setPredicate(nv -> {
				String typetext = newvalue.toLowerCase();
				if (newvalue == null || newvalue.isEmpty()) {
					return true;
				}
				
				else if (nv.getHovaten().toLowerCase().indexOf(typetext) != -1) {
					return true;
				}
				else if (nv.getChucvu().toLowerCase().indexOf(typetext) != -1) {
					return true;
				}
				return false;
			});
			SortedList<Nhanvien> sortedList = new SortedList<>(filterData);
			sortedList.comparatorProperty().bind(tableNV.comparatorProperty());
			tableNV.setItems(sortedList);
		});
	}

	@FXML
	void CapNhatNhanvien(ActionEvent event) {
		hovaten_nv.setEditable(true);
		ns_nv.setEditable(true);
		cmnd_nv.setEditable(true);
		sdt_nv.setEditable(true);
		diachi_nv.setEditable(true);
		luucapnhat.setVisible(true);
		ngayvaolam.setEditable(true);
		reset.setVisible(true);
	}

	public void falsedisable() {
		anchorpane.setDisable(false);
	}

	public void truedisable() {
		anchorpane.setDisable(true);
	}

	@FXML
	void XoaNhanvien(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Current project is modified");
		alert.setContentText("Bạn có muốn xóa nhân viên?");
		ButtonType okButton = new ButtonType("Yes");
		ButtonType noButton = new ButtonType("NO");
		alert.getButtonTypes().setAll(okButton, noButton);
		alert.showAndWait().ifPresent(type -> {
			if (type == okButton) {
				int t1 = (Integer.parseInt(id_nv.getText()));
				Session session = HibernateUtils.getSessionFactory().openSession();
				Nhanvien nv = new Nhanvien(t1);
				nv = session.get(Nhanvien.class, t1);
				try {
					session.beginTransaction();
					List<Hoadon> hoadons = nv.getHoadon();
					List<Phieudathang> phieudathangs = nv.getPhieudathang();
					List<Phieunhaphang> phieunhaphangs = nv.getPhieunhaphang();
					List<Phieutrahang> phieutrahangs = nv.getPhieutrahang();
					for (Hoadon hoadon : hoadons) {
						hoadon.setNhanvien(null);
					}
					for (Phieudathang phieudathang : phieudathangs) {
						phieudathang.setNhanvien(null);
					}
					for (Phieunhaphang phieunhaphang : phieunhaphangs) {
						phieunhaphang.setNhanvien(null);
					}
					for (Phieutrahang phieutrahang : phieutrahangs) {
						phieutrahang.setNhanvien(null);
					}
					session.delete(nv);
					alert1.setContentText("Xóa nhân viên thành công");
					alert1.showAndWait();		
					session.getTransaction().commit();
				} catch (HibernateException error) {
					System.out.println(error);
					session.getTransaction().rollback();
				}
				initializeNHANVIEN();
				SetTextNull();
				
			} else  {
				alert.close();
			}
		});
	}
	
	public void SetTextNull() {
		id_nv.setText("");
		hovaten_nv.setText("");
		ns_nv.setValue(null);
		cb_chucvu.setValue(null);
		sdt_nv.setText("");
		cmnd_nv.setText("");
		diachi_nv.setText("");
		cb_gioitinh.setValue(null);
		imgnhanvien.setImage(null);
		ngayvaolam.setValue(null);
		capnhat_nv.setVisible(false);
		xoa_nv.setVisible(false);
	}

	public void initializeNHANVIEN() {
		hovaten.setCellValueFactory(new PropertyValueFactory<Nhanvien, String>("hovaten"));
		ngaysinh.setCellValueFactory(new PropertyValueFactory<Nhanvien, Date>("ngaysinh"));
		chucvu.setCellValueFactory(new PropertyValueFactory<Nhanvien, String>("chucvu"));
		sdt.setCellValueFactory(new PropertyValueFactory<Nhanvien, String>("sdt"));
		cmnd.setCellValueFactory(new PropertyValueFactory<Nhanvien, Integer>("cmnd"));
		diachi.setCellValueFactory(new PropertyValueFactory<Nhanvien, String>("diachi"));
		tableNV.setItems(getNhanvien());
		search();
	}

	public ObservableList<Nhanvien> getNhanvien() {
		ObservableList<Nhanvien> TableNV = FXCollections.observableArrayList();
		Session session = HibernateUtils.getSessionFactory().openSession();
		CriteriaQuery<Nhanvien> nv = session.getCriteriaBuilder().createQuery(Nhanvien.class);
		nv.from(Nhanvien.class);
		List<Nhanvien> eList = session.createQuery(nv).getResultList();
		for (Nhanvien ent : eList) {
			TableNV.add(ent);
		}
		return TableNV;
	}

	void reloadNHANVIEN() {
		initializeNHANVIEN();
	}

	@FXML
	void reload(ActionEvent event) {
		initializeNHANVIEN();
	}

	private void setCellValueFromTabletoTexfField() {
		tableNV.setOnMouseClicked(event -> {
			capnhat_nv.setVisible(true);
			xoa_nv.setVisible(true);
			Nhanvien nv = tableNV.getItems().get(tableNV.getSelectionModel().getSelectedIndex());
			id_nv.setText(Integer.toString(nv.getManv()));
			hovaten_nv.setText(nv.getHovaten());
			ns_nv.setValue(nv.getNgaysinh());
			ngayvaolam.setValue(nv.getNgayvaolam());
			cb_chucvu.getSelectionModel().select(nv.getChucvu());
			cb_gioitinh.getSelectionModel().select(nv.getGioitinh());
			sdt_nv.setText(nv.getSdt());
			diachi_nv.setText(nv.getDiachi());
			cmnd_nv.setText(String.valueOf(nv.getCmnd()));
			byte[] getImageInBytes = nv.getImage();
			try {
				FileOutputStream fos = new FileOutputStream(new File("photo.jpg"));
				fos.write(getImageInBytes);
				fos.close();
				image = new Image("file:photo.jpg", imgnhanvien.getFitHeight(), imgnhanvien.getFitHeight(), true, true);
				imgnhanvien.setImage(image);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initializeNHANVIEN();
		setCellValueFromTabletoTexfField();
		search();
		capnhat_nv.setVisible(false);
		xoa_nv.setVisible(false);
		ObservableList<String> list = FXCollections.observableArrayList("Nhân viên", "Quản lý");
		ObservableList<String> list1 = FXCollections.observableArrayList("Nam", "Nữ");
		cb_chucvu.setItems(list);
		cb_gioitinh.setItems(list1);
	}
}
