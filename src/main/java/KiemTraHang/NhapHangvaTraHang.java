package KiemTraHang;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javafx.stage.Stage;
import javafx.util.Callback;
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
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.swing.JOptionPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import org.hibernate.*;
import entities.*;
import QLBH.GiaoDienQLController;
import QLBH.HibernateUtils;

public class NhapHangvaTraHang implements Initializable {
	@FXML
	private TableView<Chitietdathang> tableChitietKiemtra;

	@FXML
	private Label madathangkt;

	@FXML
	private Label mancckt;

	@FXML
	private Label kiemtra;

	@FXML
	private Label thoigiandatkt;
	@FXML
	private Label tongtien;

	@FXML
	private TableColumn<Chitietdathang, Sanpham> tenhang;

	@FXML
	private TableColumn soluong;

	@FXML
	private TableColumn dongia;

	@FXML
	private Button select;

	@FXML
	private Button btphieunhap;

	@FXML
	private Button btphieutra;

	@FXML
	private TextField lydo;

	@FXML
	private TableColumn choice;

	@FXML
	private TableColumn<Chitietdathang, Sanpham> mahang;

	@FXML
	private TableView<Chitietphieutra> tbtrahang;

	@FXML
	private TableView<Chitietnhaphang> tbnhaphang;

	@FXML
	private TableColumn<Chitietphieutra, Sanpham> idtensp2;

	@FXML
	private TableColumn<Chitietphieutra, Sanpham> idmasp2;

	@FXML
	private TableColumn<Chitietphieutra, Integer> idsoluong2;
///
	@FXML
	private TableColumn<Chitietphieutra, Integer> iddongia2;
///	
	@FXML
	private TableColumn<Chitietphieutra, Double> idthanhtien2;

	@FXML
	private TableColumn xoasp2;

	@FXML
	private TableColumn<Chitietnhaphang, Sanpham> idtensp1;

	@FXML
	private TableColumn<Chitietnhaphang, Sanpham> idmasp1;

	@FXML
	private TableColumn<Chitietnhaphang, Double> idthanhtien1;

	@FXML
	private TableColumn xoasp1;

	@FXML
	private TableColumn<Chitietnhaphang, Integer> idsoluong1;
///
	@FXML
	private TableColumn<Chitietnhaphang, Integer> iddongia1;
	@FXML
	private Button btntra;

	@FXML
	private Button btnnhap;

	@FXML
	private Label thongbaoSP;

	@FXML
	private Label kiemtratrahang;

	@FXML
	private Label kiemtranhaphang;

	@FXML
	private ImageView exit;

	@FXML
	void exit(MouseEvent event) {
		Stage stage = (Stage) exit.getScene().getWindow();
		stage.close();
		GiaoDienQLController.getInstance().falsedisable();
	}

	@FXML
	void nhap(ActionEvent event) {
		try {
			event();
		} catch (Exception e) {
			kiemtra.setText("Bạn phải chọn vào sản phẩm");
		}
	}

	@FXML
	void tra(ActionEvent event) {
		try {
			event2();
		} catch (Exception e) {
			kiemtra.setText("Bạn phải chọn vào sản phẩm");

		}

	}

	public boolean Kiemtra() {
		if (tableChitietKiemtra.getItems().isEmpty()) {
			Session session = HibernateUtils.getSessionFactory().openSession();
			try {
				int madathang = Integer.parseInt(madathangkt.getText());
				Phieudathang pdh = new Phieudathang();
				pdh = session.get(Phieudathang.class, madathang);
				session.beginTransaction();
				pdh.setKiemtrahang(true);
				session.save(pdh);
				session.getTransaction().commit();
				kiemtra.setText(null);
			} catch (Exception e) {
				System.out.println(e);
			}
			return true;
		}
		kiemtra.setText("Kiểm tra sản phẩm vẫn chưa xong");
		return false;
	}

	public boolean KiemTraTraHang() {
		if (tbtrahang.getItems().isEmpty()) {
			kiemtratrahang.setText("Phiếu trả không có hàng được trả");
			return false;
		} else {
			kiemtratrahang.setText(null);
			return true;
		}
	}

	public boolean KiemTraNhapHang() {
		if (tbnhaphang.getItems().isEmpty()) {
			kiemtranhaphang.setText("Phiếu nhập không có hàng được nhập");
			return false;
		} else {
			kiemtranhaphang.setText(null);
			return true;
		}
	}

	@FXML
	void selected(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("thanhtoancongno.fxml"));
			Parent root1 = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root1));
			stage.setTitle("Thanh Toán Công Nợ");
			stage.show();
		} catch (Exception e) {
		}

	}

	public ObservableList<Chitietdathang> getChitietdathang(Phieudathang phieudathang) {
		int Phieudathang = phieudathang.getMadathang();
		ObservableList<Chitietdathang> tableChitietkiemtra = FXCollections.observableArrayList();
		Session session = HibernateUtils.getSessionFactory().openSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Chitietdathang> query = builder.createQuery(Chitietdathang.class);
		Root<Chitietdathang> root = query.from(Chitietdathang.class); // FROM
		Join<Chitietdathang, Sanpham> SanphamJoin = root.join("sanpham", JoinType.INNER);
		Join<Chitietdathang, Phieudathang> DatHangJoin = root.join("phieudathang", JoinType.INNER);
		query.where(builder.equal(DatHangJoin.get("madathang"), Phieudathang));
		List<Chitietdathang> ctdh = session.createQuery(query).getResultList();
		for (Chitietdathang b : ctdh) {
			tableChitietkiemtra.add(b);
			System.out.println(b);
		}
		return tableChitietkiemtra;
	}

	public void setKiemtrahang(Phieudathang phieudathang) {
		madathangkt.setText(String.valueOf(phieudathang.getMadathang()));
		thoigiandatkt.setText(String.valueOf(phieudathang.getThoigian()));
		mancckt.setText(String.valueOf(phieudathang.getNhacungcap().toString()));
		tongtien.setText(String.valueOf(phieudathang.getTongtien()));
		IntitlizeChitietdathang(phieudathang);
	}

	public void loadData(Taikhoannv taikhoan) {
		btphieunhap.setOnMouseClicked(event -> {
			if (Kiemtra() & KiemTraNhapHang()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				Session session = HibernateUtils.getSessionFactory().openSession();
				Nhacungcap nhacungcap = new Nhacungcap();
				int mancc = Integer.parseInt(mancckt.getText());
				nhacungcap = session.get(Nhacungcap.class, mancc);
				int tongtien = 0;
				for (Chitietnhaphang ct : tbnhaphang.getItems()) {
					tongtien = (int) (tongtien + ct.getThanhtien());
				}
				LocalDateTime dateTime = LocalDateTime.now();
				Nhanvien nhanvien = new Nhanvien();
				nhanvien = session.get(Nhanvien.class, taikhoan.getNhanvien().getManv());
				Phieunhaphang phieunhaphang = new Phieunhaphang(dateTime, tongtien, nhacungcap, nhanvien);
				try {
					session.beginTransaction();
					session.save(phieunhaphang);
					session.getTransaction().commit();
					alert.setContentText("Thêm phiếu nhập hàng thành công!");
					alert.showAndWait();
				} catch (RuntimeException error) {
					alert.setContentText("Lỗi " + error);
					alert.showAndWait();
					session.getTransaction().rollback();
				}
				for (Chitietnhaphang ct : tbnhaphang.getItems()) {
					int soluongnhap = ct.getSoluong();
					double thanhtien = ct.getThanhtien();
					Sanpham sanpham = new Sanpham();
					int dongia = ct.getDongia();
					sanpham = session.get(Sanpham.class, ct.getSanpham().getMasanpham());
					Chitietnhaphang chitietnhaphang = new Chitietnhaphang(phieunhaphang, sanpham, soluongnhap,
							thanhtien, dongia);
					int soluong = ct.getSanpham().getDonvitinh() + sanpham.getDonvitinh();
					try {
						session.beginTransaction();
						sanpham.setDonvitinh(soluong);
						session.save(chitietnhaphang);
						session.save(sanpham);
						session.getTransaction().commit();
						alert.setContentText("Cập nhật số lượng thành công!");
						alert.showAndWait();
					} catch (RuntimeException error) {
						System.out.println(error);
						alert.setContentText("Thêm  thất bại!");
						alert.showAndWait();
						session.getTransaction().rollback();
					}
				}
				tbnhaphang.getItems().clear();
				tbnhaphang.refresh();
				KiemTraHangController.getInstance().khoitao();
				InPhieuNhapHang(phieunhaphang);
			}
		});
		btphieutra.setOnMouseClicked(event -> {
			if (Kiemtra() & KiemTraTraHang()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				Session session = HibernateUtils.getSessionFactory().openSession();
				Nhacungcap nhacungcap = new Nhacungcap();
				int mancc = Integer.parseInt(mancckt.getText());
				nhacungcap = session.get(Nhacungcap.class, mancc);
				int tongtien = 0;
				for (Chitietphieutra ct : tbtrahang.getItems()) {
					tongtien = (int) (tongtien + ct.getThanhtien());
				}
				Nhanvien nhanvien = new Nhanvien();
				nhanvien = session.get(Nhanvien.class, taikhoan.getNhanvien().getManv());
				LocalDateTime dateTime = LocalDateTime.now();
				Phieutrahang phieutrahang = new Phieutrahang(dateTime, tongtien, nhacungcap, nhanvien);
				try {
					session.beginTransaction();
					session.save(phieutrahang);
					session.getTransaction().commit();
				} catch (RuntimeException error) {
					alert.setContentText("Lỗi " + error);
					alert.showAndWait();
					session.getTransaction().rollback();
				}
				for (Chitietphieutra ct : tbtrahang.getItems()) {
					String lydo1 = lydo.getText();
					int soluongnhap = ct.getSoluong();
					double thanhtien = ct.getThanhtien();
					int dongia = ct.getDongia();
					Sanpham sanpham = new Sanpham();
					sanpham = session.get(Sanpham.class, ct.getSanpham().getMasanpham());
					Chitietphieutra chitietphieutra = new Chitietphieutra(phieutrahang, sanpham, soluongnhap, lydo1,
							thanhtien, dongia);
					try {
						session.beginTransaction();
						session.save(chitietphieutra);
						session.getTransaction().commit();
						alert.setContentText("Thêm phiếu trả hàng thành công!");
						alert.showAndWait();
					} catch (RuntimeException error) {
						System.out.println(error);
						alert.setContentText("Thêm  thất bại   !");
						alert.showAndWait();
						session.getTransaction().rollback();
					}
				}
				tbtrahang.getItems().clear();
				tbtrahang.refresh();
				KiemTraHangController.getInstance().khoitao();
				InPhieutrahang(phieutrahang);
			}

		});
	}

	public void InPhieutrahang(Phieutrahang phieutrahang) {
		try {
			final String DB_URL = "jdbc:mysql://localhost/qlbh?serverTimezone=Asia/Ho_Chi_Minh";
			Connection conn = DriverManager.getConnection(DB_URL, "root", "");
			InputStream in = new FileInputStream(new File(
					"C:\\Users\\Admin\\eclipse-workspace\\QLBH\\src\\main\\java\\KiemTraHang\\PhieuTraHang.jrxml"));
			JasperDesign jd = JRXmlLoader.load(in);
			String sql = "SELECT PTH.maphieutra,NCC.tenncc,PTH.tongtien,PTH.thoigian,SP.tensanpham,SP.loaisanpham,CTPT.thanhtien,CTPT.soluong,CTPT.lydo FROM phieutra PTH,Chitietphieutra CTPT,Sanpham SP,Nhacungcap NCC WHERE PTH.maphieutra = CTPT.maphieutra and CTPT.masanpham = SP.masanpham and PTH.mancc = NCC.mancc and PTH.maphieutra ='"
					+ phieutrahang.getMaphieutra() + "'";
			JRDesignQuery newQuery1 = new JRDesignQuery();
			newQuery1.setText(sql);
			jd.setQuery(newQuery1);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			HashMap<String, Object> para = new HashMap<>();
			String maphieutra = String.valueOf(phieutrahang.getMaphieutra());
			String tongtien = String.valueOf(phieutrahang.getTongtien());
			para.put("maphieutra", maphieutra);
			para.put("tongtien", tongtien);
			JasperPrint j = JasperFillManager.fillReport(jr, para, conn);
			JasperViewer.viewReport(j, false);
			OutputStream os = new FileOutputStream(new File("C:\\Users\\Admin\\Desktop\\IN"));
			JasperExportManager.exportReportToPdfStream(j, os);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Lỗi" + e);
		}
	}

	public void InPhieuNhapHang(Phieunhaphang phieunhaphang) {
		try {
			final String DB_URL = "jdbc:mysql://localhost/qlbh?serverTimezone=Asia/Ho_Chi_Minh";
			Connection conn = DriverManager.getConnection(DB_URL, "root", "");
			InputStream in = new FileInputStream(new File(
					"C:\\Users\\Admin\\eclipse-workspace\\QLBH\\src\\main\\java\\KiemTraHang\\PhieuNhapHang.jrxml"));
			JasperDesign jd = JRXmlLoader.load(in);
			String sql = "SELECT PNH.manhaphang,NCC.tenncc,PNH.tongtien,PNH.thoigian,SP.tensanpham,SP.loaisanpham,CTNH.dongia,CTNH.thanhtien,CTNH.soluong FROM nhaphang PNH,Chitietnhaphang CTNH,Sanpham SP,Nhacungcap NCC WHERE PNH.manhaphang = CTNH.manhaphang and CTNH.masanpham = SP.masanpham and PNH.mancc = NCC.mancc and PNH.manhaphang ='"
					+ phieunhaphang.getManhaphang() + "'";
			JRDesignQuery newQuery1 = new JRDesignQuery();
			newQuery1.setText(sql);
			jd.setQuery(newQuery1);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			HashMap<String, Object> para = new HashMap<>();
			String manhaphang = String.valueOf(phieunhaphang.getManhaphang());
			String tongtien = String.valueOf(phieunhaphang.getTongtien());
			para.put("manhaphang", manhaphang);
			para.put("tongtien", tongtien);
			JasperPrint j = JasperFillManager.fillReport(jr, para, conn);
			JasperViewer.viewReport(j, false);
			OutputStream os = new FileOutputStream(new File("C:\\Users\\Admin\\Desktop\\IN"));
			JasperExportManager.exportReportToPdfStream(j, os);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Lỗi" + e);
		}
	}

	private void ButtonXoaSP() {
		Callback<TableColumn<Chitietnhaphang, Void>, TableCell<Chitietnhaphang, Void>> cellFactory = new Callback<TableColumn<Chitietnhaphang, Void>, TableCell<Chitietnhaphang, Void>>() {
			@Override
			public TableCell<Chitietnhaphang, Void> call(final TableColumn<Chitietnhaphang, Void> param) {
				final TableCell<Chitietnhaphang, Void> cell = new TableCell<Chitietnhaphang, Void>() {
					private final Button btn = new Button("Xóa");
					{
						btn.setOnAction((ActionEvent event) -> {
							Chitietnhaphang chitietnhaphang = getTableView().getItems().get(getIndex());
							if (tbnhaphang.getItems().contains(chitietnhaphang)) {
								tbnhaphang.getItems().remove(chitietnhaphang);
								int soluongsp = chitietnhaphang.getSoluong();
								int dongia = chitietnhaphang.getDongia();
								Chitietdathang ctdh = new Chitietdathang(soluongsp, chitietnhaphang.getSanpham(),
										dongia);
								tableChitietKiemtra.getItems().addAll(ctdh);
								tableChitietKiemtra.refresh();
								tbnhaphang.refresh();
								if (chitietnhaphang.getSoluong() == 0) {
									tbnhaphang.getItems().remove(chitietnhaphang);
									tbnhaphang.refresh();
								}
							}
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};
		xoasp1.setCellFactory(cellFactory);
	}

private void ButtonXoaSP2() {
		Callback<TableColumn<Chitietphieutra, Void>, TableCell<Chitietphieutra, Void>> cellFactory = new Callback<TableColumn<Chitietphieutra, Void>, TableCell<Chitietphieutra, Void>>() {
			@Override
			public TableCell<Chitietphieutra, Void> call(final TableColumn<Chitietphieutra, Void> param) {
				final TableCell<Chitietphieutra, Void> cell = new TableCell<Chitietphieutra, Void>() {
					private final Button btn = new Button("Xóa");
					{
						btn.setOnAction((ActionEvent event) -> {
							Chitietphieutra chitietphieutra = getTableView().getItems().get(getIndex());
							if (tbtrahang.getItems().contains(chitietphieutra)) {
								tbtrahang.getItems().remove(chitietphieutra);
								int soluongsp = chitietphieutra.getSoluong();
								int dongia = chitietphieutra.getDongia();
								Chitietdathang ctdh = new Chitietdathang(soluongsp, chitietphieutra.getSanpham(),
										dongia);
								tableChitietKiemtra.getItems().addAll(ctdh);
								tableChitietKiemtra.refresh();
								tbnhaphang.refresh();
								if (chitietphieutra.getSoluong() == 0) {
									tbtrahang.getItems().remove(chitietphieutra);
									tbtrahang.refresh();
								}
							}
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};
		xoasp2.setCellFactory(cellFactory);
	}

	//
//	private void setCellValue()  {
//        tableChitietKiemtra.setOnMouseClicked(event -> {
//            event();
//            event2();
//        });
//    }
	private void event() {
		Chitietdathang sp = tableChitietKiemtra.getItems()
				.get(tableChitietKiemtra.getSelectionModel().getSelectedIndex());
		int masanpham = sp.getSanpham().getMasanpham();
		String tensanpham = sp.getSanpham().getTensanpham();
		String loaisanpham = sp.getSanpham().getLoaisanpham();
		int soluong = sp.getSoluong();
		String donvi = sp.getSanpham().getDonvi();
		int giatien = sp.getDongia();
		Sanpham sp1 = new Sanpham(masanpham, tensanpham, loaisanpham, donvi, soluong);
		addItem(soluong, sp1, 0.0, giatien);
		tableChitietKiemtra.getItems().remove(sp);
		tableChitietKiemtra.refresh();
	}

	public void addItem(int soluong, Sanpham sanpham, double thanhtien, int dongia) {
		Chitietnhaphang entry = tbnhaphang.getItems().stream().filter(item -> item.getSanpham().equals(sanpham))
				.findAny().orElseGet(() -> {
					Chitietnhaphang newItem = new Chitietnhaphang(sanpham, soluong, thanhtien, dongia);
					tbnhaphang.getItems().add(newItem);
					return newItem;
				});
		entry.setThanhtien(entry.getDongia() * entry.getSoluong());

	}

	////
	private void event2() {
		Chitietdathang sp = tableChitietKiemtra.getItems()
				.get(tableChitietKiemtra.getSelectionModel().getSelectedIndex());
		int masanpham = sp.getSanpham().getMasanpham();
		String tensanpham = sp.getSanpham().getTensanpham();
		String loaisanpham = sp.getSanpham().getLoaisanpham();
		int soluong = sp.getSoluong();
		String donvi = sp.getSanpham().getDonvi();
		int giatien = sp.getDongia();
		Sanpham sp1 = new Sanpham(masanpham, tensanpham, loaisanpham, donvi, soluong);
		addItem2(soluong, sp1, 0.0, giatien);
		tableChitietKiemtra.getItems().remove(sp);
		tableChitietKiemtra.refresh();
	}

	public void addItem2(int soluong, Sanpham sanpham, double thanhtien, int dongia) {
		Chitietphieutra entry = tbtrahang.getItems().stream().filter(item -> item.getSanpham().equals(sanpham))
				.findAny().orElseGet(() -> {
					System.out.print(dongia);
					Chitietphieutra newItem = new Chitietphieutra(sanpham, soluong, thanhtien, dongia);
					tbtrahang.getItems().add(newItem);
					return newItem;
				});
		entry.setThanhtien(entry.getDongia() * entry.getSoluong());

	}

	////
	public void IntitlizeChitietnhaphang() {
		idtensp1.setCellFactory(tbnhaphang -> new TableCell<Chitietnhaphang, Sanpham>() {
			@Override
			protected void updateItem(Sanpham item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText(item.getTensanpham());
				}
			}
		});
		idtensp1.setCellValueFactory(new PropertyValueFactory<>("sanpham"));
		idmasp1.setCellValueFactory(new PropertyValueFactory<>("sanpham"));
		idmasp1.setCellFactory(tbnhaphang -> new TableCell<Chitietnhaphang, Sanpham>() {
			@Override
			protected void updateItem(Sanpham item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText(String.valueOf(item.getMasanpham()));
				}
			}
		});
		idsoluong1.setCellValueFactory(new PropertyValueFactory<Chitietnhaphang, Integer>("soluong"));
		iddongia1.setCellValueFactory(new PropertyValueFactory<Chitietnhaphang, Integer>("dongia"));
		idthanhtien1.setCellValueFactory(new PropertyValueFactory<Chitietnhaphang, Double>("thanhtien"));
		ButtonXoaSP();
	}

	public void IntitlizeChitiettrahang() {
		idtensp2.setCellFactory(tbnhaphang -> new TableCell<Chitietphieutra, Sanpham>() {
			@Override
			protected void updateItem(Sanpham item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText(item.getTensanpham());
				}
			}
		});
		idtensp2.setCellValueFactory(new PropertyValueFactory<>("sanpham"));
		idmasp2.setCellValueFactory(new PropertyValueFactory<>("sanpham"));
		idmasp2.setCellFactory(tbnhaphang -> new TableCell<Chitietphieutra, Sanpham>() {
			@Override
			protected void updateItem(Sanpham item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText(String.valueOf(item.getMasanpham()));
				}
			}
		});
		idsoluong2.setCellValueFactory(new PropertyValueFactory<Chitietphieutra, Integer>("soluong"));
		iddongia2.setCellValueFactory(new PropertyValueFactory<Chitietphieutra, Integer>("dongia"));
		idthanhtien2.setCellValueFactory(new PropertyValueFactory<Chitietphieutra, Double>("thanhtien"));
		ButtonXoaSP2();
	}

	public void IntitlizeChitietdathang(Phieudathang phieudathang) {
		tenhang.setCellFactory(tableChitietKiemtra -> new TableCell<Chitietdathang, Sanpham>() {
			@Override
			protected void updateItem(Sanpham item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText(item.getTensanpham());
				}
			}
		});
		tenhang.setCellValueFactory(new PropertyValueFactory<>("sanpham"));
		soluong.setCellValueFactory(new PropertyValueFactory<Chitietdathang, Integer>("soluong"));
		dongia.setCellValueFactory(new PropertyValueFactory<Chitietdathang, Integer>("dongia"));
		TableColumn<Chitietdathang, Boolean> choice = new TableColumn<Chitietdathang, Boolean>("Membership");
		mahang.setCellValueFactory(new PropertyValueFactory<>("sanpham"));
		mahang.setCellFactory(tableChitietKiemtra -> new TableCell<Chitietdathang, Sanpham>() {
			@Override
			protected void updateItem(Sanpham item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText(String.valueOf(item.getMasanpham()));
				}
			}
		});
		tableChitietKiemtra.setEditable(true);
		tableChitietKiemtra.getColumns().add(choice);
		tableChitietKiemtra.setItems(getChitietdathang(phieudathang));
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		IntitlizeChitietnhaphang();
		IntitlizeChitiettrahang();
		// TODO Auto-generated method stub

	}
}