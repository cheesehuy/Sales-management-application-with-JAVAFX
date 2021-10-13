package Nhacungcap;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.net.URL;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.event.*;
import org.hibernate.*;
import QLBH.GiaoDienQLController;
import QLBH.HibernateUtils;
import entities.*;

public class ThemNCCController extends Application implements Initializable {
	@FXML
	private TextField tfncc;

	@FXML
	private TextField tftenncc;

	@FXML
	private TextField tfsdt;

	@FXML
	private TextField tfdiachi;

	@FXML
	private TextField tfemail;

	@FXML
	private TextField tfsotienno;

	@FXML
	private Button idsave;

	@FXML
	private Button idclose;

	@FXML
	private ImageView exit;

	@FXML
	void exit(MouseEvent event) {
		Stage stage = (Stage) exit.getScene().getWindow();
		stage.close();
		GiaoDienQLController.getInstance().falsedisable();
	}

	@FXML
	void close(ActionEvent event) {
		Stage stage = (Stage) idclose.getScene().getWindow();
		stage.close();
		GiaoDienQLController.getInstance().falsedisable();
	}

	@FXML
	void save(ActionEvent event) {
		if (KiemTraSDT() & KiemTraTenNCC() & KiemTraEmail() & KiemTraDiaChi() & KiemTraTien()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Them Nha cung cap");
			String tenncc = tftenncc.getText();
			String diachi = tfdiachi.getText();
			String sodienthoai = tfsdt.getText();
			String email = tfemail.getText();
			Integer sotienno = Integer.parseInt(tfsotienno.getText());
			Session session = HibernateUtils.getSessionFactory().openSession();
			try {
				Nhacungcap nv = new Nhacungcap(tenncc, diachi, sodienthoai, email, sotienno);
				session.beginTransaction();
				session.save(nv);
				session.getTransaction().commit();
				Stage stage = (Stage) idsave.getScene().getWindow();
				stage.close();
				alert.setContentText("Thêm nhà cung cấp thành công !");
				alert.showAndWait();
				nhacungcapController.getInstance().ReloadNHACUNGCAP();
				GiaoDienQLController.getInstance().falsedisable();
			} catch (RuntimeException error) {
				alert.setContentText("Thêm nhà cung cấp thất bại  !");
				alert.showAndWait();
				session.getTransaction().rollback();
			}
		}
	}

	@FXML
	private Label check_sdt;

	private boolean KiemTraSDT() {
		Pattern p = Pattern.compile("[0-9]+");
		Matcher m = p.matcher(tfsdt.getText());
		if (m.find() && m.group().equals(tfsdt.getText()) && tfsdt.getText().matches("\\d{10}|\\d{11}")) {
			check_sdt.setText(null);
			return true;
		} else if (tfsdt.getText().length() < 10) {
			check_sdt.setText("Số điện thoại không đủ 10 số");
			return false;
		} else {
			check_sdt.setText("Vui lòng điền số điện thoại hợp lệ");
			return false;
		}
	}

	public boolean isNumber(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@FXML
	private Label check_sotienno;

	private boolean KiemTraTien() {
		Pattern p = Pattern.compile("[0-9]+");
		Matcher m = p.matcher(tfsotienno.getText());
		if (m.find() && m.group().equals(tfsotienno.getText())) {
			check_sotienno.setText(null);
			return true;
		} else {
			check_sotienno.setText("Vui lòng điền số tiền hợp lệ");
			return false;
		}
	}

	@FXML
	private Label check_hoten;

	private boolean KiemTraTenNCC() {
		Pattern p = Pattern.compile("^[A-Za-zÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]+$");
		Matcher m = p.matcher(tftenncc.getText());
		if (m.find() && m.group().equals(tftenncc.getText())) {
			check_hoten.setText(null);
			return true;
		} else {
			check_hoten.setText("Vui lòng điền tên hợp lệ");
			return false;
		}
	}

	@FXML
	private Label check_email;

	private boolean KiemTraEmail() {
		Pattern p = Pattern.compile(
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher m = p.matcher(tfemail.getText());
		if (m.find() && m.group().equals(tfemail.getText())) {
			check_email.setText(null);
			return true;
		} else {
			check_email.setText("Vui lòng điền email hợp lệ theo quy ước ...@chữ.chữ");
			return false;
		}
	}

	@FXML
	private Label check_diachi;

	private boolean KiemTraDiaChi() {
		if (tfdiachi.getText().isEmpty()) {
			check_diachi.setText("Vui lòng điền địa chỉ phù hợp");
			return false;
		}
		check_diachi.setText(null);
		return true;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}
}
