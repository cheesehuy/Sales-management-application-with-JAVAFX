package QLKho;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.*;
import org.hibernate.*;
import QLBH.HibernateUtils;
import entities.*;

public class detailsanphamcontroller implements Initializable {

	private FileChooser filechooser;
	private Image image;
	private Blob image1;
	private File file;

	@FXML
	private AnchorPane ap;

	@FXML
	private Button idreload;

	@FXML
	private Label thongbao1;

	@FXML
	private Label thongbao2;

	@FXML
	private Label thongbao3;

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
	private TextField idsp;

	@FXML
	private ComboBox<String> donvi;

	@FXML
	private ComboBox<String> loaisanpham;

	@FXML
	private Button reset;

	public void setSanPham(Sanpham sanpham) {
		idsp.setText(String.valueOf(sanpham.getMasanpham()));
		tensanpham.setText(sanpham.getTensanpham());
		donvi.setValue(sanpham.getDonvi());
		soluong.setText(String.valueOf(sanpham.getDonvitinh()));
		giatien.setText(String.valueOf(sanpham.getGiatien()));
		loaisanpham.setValue(sanpham.getLoaisanpham());
		imageSP.setImage(getImageFromBytes(sanpham.getImagesp()));
		reset.setOnMouseClicked(event -> {

			idsp.setText(Integer.toString(sanpham.getMasanpham()));
			tensanpham.setText(sanpham.getTensanpham());
			donvi.setValue(sanpham.getDonvi());
			loaisanpham.setValue(sanpham.getLoaisanpham());
			giatien.setText(String.valueOf(sanpham.getGiatien()));
			soluong.setText(String.valueOf(sanpham.getDonvitinh()));
		});
	}

	private Image getImageFromBytes(byte[] imgBytes) {
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(imgBytes);
			BufferedImage bufferedImage = ImageIO.read(inputStream);
			return SwingFXUtils.toFXImage(bufferedImage, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@FXML
	void LuuSP(ActionEvent event) throws IOException {
		if (KiemTraTen() & KiemTraSL() & KiemTraGia()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			int IDSanPham = Integer.parseInt(idsp.getText());
			String capnhattensp = tensanpham.getText();
			String capnhatdvsp = donvi.getValue();
			int capnhatslsp = Integer.parseInt(soluong.getText());
			int capnhatgtsp = Integer.parseInt(giatien.getText());
			String capnhatlsp = loaisanpham.getValue();
			Session session = HibernateUtils.getSessionFactory().openSession();
			byte[] bFile = null;
			if (bFile == null) {
				BufferedImage bImage = SwingFXUtils.fromFXImage(imageSP.getImage(), null);
				ByteArrayOutputStream s = new ByteArrayOutputStream();
				ImageIO.write(bImage, "png", s);
				bFile = s.toByteArray();
			}
			Sanpham sp = new Sanpham(capnhattensp, capnhatlsp, capnhatdvsp, capnhatgtsp, capnhatslsp, bFile);
			sp = session.get(Sanpham.class, IDSanPham);
			try {
				session.beginTransaction();

				if (sp != null) {
					sp.setTensanpham(capnhattensp);
					sp.setLoaisanpham(capnhatlsp);
					sp.setDonvi(capnhatdvsp);
					sp.setGiatien(capnhatgtsp);
					sp.setDonvitinh(capnhatslsp);
					sp.setImagesp(bFile);
					session.save(sp);
					Stage stage = (Stage) ap.getScene().getWindow();
					stage.close();
				}
				session.getTransaction().commit();
				QLKhoController.getInstance().initialize1();
				alert.setContentText("Cập nhật sản phẩm thành công");
				alert.showAndWait();
			} catch (RuntimeException error) {
				session.getTransaction().rollback();
				alert.setContentText("Cập nhật sản phẩm thất bại");

			}

		}
	}

	private boolean KiemTraTen() {
		Pattern p = Pattern.compile("^[A-Za-zÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]+$");
		Matcher m = p.matcher(tensanpham.getText());
		if (m.find() && m.group().equals(tensanpham.getText())) {
			thongbao1.setText(null);
			return true;
		} else {
			thongbao1.setText("Vui lòng điền tên sản phẩm phù hợp");
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
			thongbao2.setText("Vui lòng điền số");
			return false;
		}
	}

	private boolean KiemTraGia() {
		Pattern p = Pattern.compile("[0-9]+");
		Matcher m = p.matcher(giatien.getText());
		if (m.find() && m.group().equals(giatien.getText())) {
			thongbao3.setText(null);
			return true;
		} else {
			thongbao3.setText("Vui lòng điền số");
			return false;
		}
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
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<String> list = FXCollections.observableArrayList("Cái ", "Chai ", "Bình ", "Bịch", "Hộp", "Ly",
				"Nắm", "123");
		ObservableList<String> list1 = FXCollections.observableArrayList("nước giải khát", "snack", "thức ăn nhanh");
		loaisanpham.setItems(list1);
		donvi.setItems(list);

	}

}