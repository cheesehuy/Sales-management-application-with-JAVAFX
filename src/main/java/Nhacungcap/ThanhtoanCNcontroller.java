package Nhacungcap;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import java.util.HashMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import javafx.application.Application;
import javafx.event.*;
import org.hibernate.*;
import QLBH.GiaoDienQLController;
import QLBH.HibernateUtils;
import entities.*;

public class ThanhtoanCNcontroller extends Application implements Initializable {
	@FXML
	private Label tfnoht;

	@FXML
	private TextField tfthanhtoan;

	@FXML
	private Label tfdate;

	@FXML
	private Label tfnoconlai;

	@FXML
	private Button bttaophieu;

	@FXML
	private Button btquaylai;

	@FXML
	private Label tenncc;

	@FXML
	private Label thongbao;

	@FXML
	private Label thongbao1;

	@FXML
	private ImageView exit;

	private boolean KiemTraDiaChi() {
		if (tfthanhtoan.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setContentText("Mời nhập vào ô thanh toán!!");
			alert.showAndWait();
			return false;
		}
		return true;
	}

	@FXML
	void exit(MouseEvent event) {
		Stage stage = (Stage) exit.getScene().getWindow();
		stage.close();
		GiaoDienQLController.getInstance().falsedisable();
	}

	@FXML
	void close(ActionEvent event) {
		Stage stage = (Stage) btquaylai.getScene().getWindow();
		stage.close();
		GiaoDienQLController.getInstance().falsedisable();
	}

	public void setThanhtoan(Nhacungcap nhacungcap) {
		tenncc.setText(nhacungcap.getTenncc());
		tfnoht.setText(String.valueOf(nhacungcap.getSotienno()));
		java.util.Date date = new java.util.Date();
		tfdate.setText(String.valueOf(date));
		tfthanhtoan.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
			try {
				if (e.getCode() == KeyCode.ENTER) {
					if (Integer.parseInt(tfthanhtoan.getText()) > Integer.parseInt(tfnoht.getText())) {
						tfthanhtoan.setText("");
						thongbao.setVisible(true);
						thongbao.setText("Không hợp lệ! Mời Nhập Lại!!!");
						tfnoconlai.setText(" ");
					} else if (Integer.parseInt(tfthanhtoan.getText()) <= Integer.parseInt(tfnoht.getText())) {
						tfnoconlai.setText(String
								.valueOf(Integer.parseInt(tfnoht.getText()) - Integer.parseInt(tfthanhtoan.getText())));
						tfnoconlai.setVisible(true);
						thongbao.setText(" ");
					} else if (tfthanhtoan.getText().isEmpty()) {
						thongbao.setText("Bạn không được bỏ trống ");
					}
				}
			} catch (Exception e2) {
				thongbao.setText("Vui lòng nhập số ");
			}
		});

		bttaophieu.setOnMouseClicked(event -> {
			if (KiemTraDiaChi()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Thanh Toán Công Nợ");
				Integer sotienno = Integer.parseInt(tfnoconlai.getText());
				Session session = HibernateUtils.getSessionFactory().openSession();
				try {
					session.beginTransaction();
					Nhacungcap nv = new Nhacungcap();
					nv = session.get(Nhacungcap.class, nhacungcap.getMancc());
					nv.setSotienno(sotienno);
					session.save(nv);
					session.getTransaction().commit();
					Stage stage = (Stage) bttaophieu.getScene().getWindow();
					stage.close();
					alert.setContentText("Cập nhật số tiền nợ thành công");
					alert.showAndWait();
					InPhieuThanhToanNo(nv);
					nhacungcapController.getInstance().ReloadNHACUNGCAP();
					GiaoDienQLController.getInstance().falsedisable();
				} catch (RuntimeException error) {
					System.out.print(error);
					alert.setContentText("Cập nhật số tiền nợ thất bại");
					alert.showAndWait();
					session.getTransaction().rollback();
				}
			}
		});
	}

	public void InPhieuThanhToanNo(Nhacungcap nhacungcap) {
		try {
			final String DB_URL = "jdbc:mysql://localhost/qlbh?serverTimezone=Asia/Ho_Chi_Minh";
			Connection conn = DriverManager.getConnection(DB_URL, "root", "");
			InputStream in = new FileInputStream(new File("Nhacungcap\\PhieuThanhToanNo.jrxml"));
			JasperDesign jd = JRXmlLoader.load(in);
			String sql = "SELECT tenncc,sotienno FROM nhacungcap WHERE mancc ='" + nhacungcap.getMancc() + "'";
			JRDesignQuery newQuery1 = new JRDesignQuery();
			newQuery1.setText(sql);
			jd.setQuery(newQuery1);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			HashMap<String, Object> para = new HashMap<>();
			String thanhtoan = String.valueOf(tfthanhtoan.getText());
			String noconlai = String.valueOf(tfnoconlai.getText());
			String thoigian = tfdate.getText();
			String sotienno = tfnoconlai.getText();
			para.put("thanhtoan", thanhtoan);
			para.put("noconlai", noconlai);
			para.put("thoigian", thoigian);
			para.put("sotienno", sotienno);
			JasperPrint j = JasperFillManager.fillReport(jr, para, conn);
			JasperViewer.viewReport(j, false);
			OutputStream os = new FileOutputStream(new File("C:\\Users\\Admin\\Desktop\\IN"));
			JasperExportManager.exportReportToPdfStream(j, os);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Lỗi" + e);
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	@Override
	public void start(Stage arg0) throws Exception {
	}

}
