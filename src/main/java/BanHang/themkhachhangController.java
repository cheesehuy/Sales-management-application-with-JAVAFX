package BanHang;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.hibernate.Session;

import QLBH.GiaoDienNhanvienController;
import QLBH.GiaoDienQLController;
import QLBH.HibernateUtils;
import entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class themkhachhangController implements Initializable {

	@FXML
	private TextField numberphone;

	@FXML
	private TextField address;

	@FXML
	private TextField mail;

	@FXML
	private ComboBox<String> sex;

	@FXML
	private TextField name;

	@FXML
	private DatePicker birth;

	@FXML
	private Button them;

	@FXML
	private Button quaylai;
	
	@FXML
	private Label checkname;
	
	@FXML
	private Label checkaddress;
	
	@FXML
	private Label checkbirth;
	
	@FXML
	private Label checknumber;
	
	@FXML
	private Label checkgender;
	
	@FXML
	private Label checkmail;
	
	@FXML
	private ImageView exit;
	
	@FXML
	private AnchorPane ap;

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

	@FXML
	void themkhachhang(ActionEvent event) {
		if (kiemtrahoten() & kiemtradiachi() & KiemTraSDT() & KiemTraNgaySinh() & KiemTraGioiTinh() & kiemtramail() & GiaoDienNhanvienController.getInstance() == null) {
			AddKH();
			GiaoDienQLController.getInstance().falsedisable();
		}
		else if(kiemtrahoten() & kiemtradiachi() & KiemTraSDT() & KiemTraNgaySinh() & KiemTraGioiTinh() & kiemtramail() & GiaoDienQLController.getInstance() == null) {
			AddKH();
			GiaoDienNhanvienController.getInstance().falsedisable();
			
		}
		else if(kiemtrahoten() & kiemtradiachi() & KiemTraSDT() & KiemTraNgaySinh() & KiemTraGioiTinh() & kiemtramail()){
			AddKH();
			GiaoDienNhanvienController.getInstance().falsedisable();
			GiaoDienQLController.getInstance().falsedisable();
		}
		
	}
	
	public void AddKH() {
		Alert alert = new Alert(AlertType.INFORMATION);
		String hoten = name.getText();
		String diachi = address.getText();
		String sdt = numberphone.getText();
		LocalDate ngaysinh = birth.getValue();
		String gioitinh = sex.getValue();
		String email = mail.getText();
		Session session = HibernateUtils.getSessionFactory().openSession();
		KhachHang khachhang = new KhachHang(hoten, diachi, sdt, ngaysinh, gioitinh, email);
		try {
			session.beginTransaction();
			session.save(khachhang);
			session.getTransaction().commit();
			Stage stage = (Stage) ap.getScene().getWindow();
			stage.close();
			alert.setContentText("Thêm khách hàng thành công !");
			alert.showAndWait();
		} catch (RuntimeException error) {
			alert.setContentText("Thêm khách hàng thất bại  !");
			alert.showAndWait();
			session.getTransaction().rollback();
		}
		name.setText(null);
		address.setText(null);
		numberphone.setText(null);
		birth.setValue(null);
		sex.setValue(null);
		mail.setText(null);
	}

	private boolean kiemtrahoten() {
		Pattern p = Pattern.compile("^[A-Za-zÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]+$");
		Matcher m = p.matcher(name.getText());
		if (m.find() && m.group().equals(name.getText())) {
			checkname.setText(null);
			return true;
		} else {
			checkname.setText("Vui lòng điền tên hợp lệ");
			return false;
		}
	}

	private boolean kiemtradiachi() {
		if (address.getText().isEmpty()) {
			checkaddress.setText("Vui lòng điền tên hợp lệ");
			return false;
		} else {
			checkaddress.setText(null);
			return true;
		}
	}

	private boolean KiemTraSDT() {
		Pattern p = Pattern.compile("[0-9]+");
		Matcher m = p.matcher(numberphone.getText());
		if (m.find() && m.group().equals(numberphone.getText()) && numberphone.getText().matches("\\d{10}|\\d{11}")) {
			checknumber.setText(null);
			return true;
		} else if (numberphone.getText().length() < 10) {
			checknumber.setText("Số điện thoại không đủ 10 số");
			return false;
		} else {
			checknumber.setText("Vui lòng điền số điện thoại hợp lệ");
			return false;
		}
	}

	private boolean KiemTraNgaySinh() {
		if (birth.getValue() == null) {
			checkbirth.setText("Vui lòng điền số ngày ở hợp lệ");
			return false;
		}
		checkbirth.setText(null);
		return true;
	}

	private boolean KiemTraGioiTinh() {
		if (sex.getValue() == null) {
			checkgender.setText("Vui lòng chọn giới tính nhân viên");
			return false;
		}
		checkgender.setText(null);
		return true;
	}

	public static final Pattern VALIDEMAIL = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	private boolean kiemtramail() {

		Matcher matcher = VALIDEMAIL.matcher(mail.getText());
		if (matcher.find() && matcher.group().equals(mail.getText())) {

			checkmail.setText(null);
			return true;

		}
		if (mail.getText().isEmpty()) {
			checkmail.setText("Mail chưa được nhập");
		} else
			checkmail.setText("Mail không hợp lệ");
		return false;

	}

	@FXML
	void quaylai(ActionEvent event) {
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ObservableList<String> list = FXCollections.observableArrayList("Nam", "Nữ");
		sex.setItems(list);

	}

}
