package QLKho;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
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
import javafx.scene.input.MouseEvent;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.persistence.criteria.CriteriaQuery;
import javax.swing.JOptionPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.*;
import org.hibernate.*;
import org.hibernate.boot.*;
import org.hibernate.boot.registry.*;
import entities.*;
import QLBH.GiaoDienQLController;
import QLBH.HibernateUtils;


public class LapPhieuDatHangController implements Initializable {

	@FXML
	private Button huy;

	@FXML
	private TableColumn donvitinh;

	@FXML
	private TableColumn donvi;

	@FXML
	private TableColumn tensanpham;

	@FXML
	private TableColumn masanpham;

	@FXML
	private TableColumn loaisanpham;

	@FXML
	private TableColumn donvitinh1;

	@FXML
	private TableColumn donvi1;

	@FXML
	private TableColumn giatien1;

	@FXML
	private TableColumn tensanpham1;

	@FXML
	private TableColumn masanpham1;

	@FXML
	private TableColumn loaisanpham1;

	@FXML
	private TableColumn Tongtien;

	@FXML
	private TextField tf1;

	@FXML
	private TextField tf2;

	@FXML
	private TextField tf3;

	@FXML
	private TextField tf4;

	@FXML
	private TextField tf5;

	@FXML
	private TextField tf6;

	@FXML
	private TextField Timkiem;

	@FXML
	private Label thongbao4;

	@FXML
	private Label thongbao5;

	@FXML
	private Label thongbao6;

	@FXML
	private Label thongbaoo;
	
	@FXML
	private Label kiemtranhap;

	@FXML
	private Button idDatHang;

	@FXML
	private TextField tongtien;

	@FXML
	private Button lapphieu;

	@FXML
	private ComboBox<String> cbbthanhtoan;

	ObservableList<Sanpham> table = FXCollections.observableArrayList(getSanpham());

	@FXML
	private TableView<Sanpham> tableSP;

	@FXML
	private TableView<Chitietdathang> tableSP1;

	@FXML
	private Text name_ncc;

	@FXML
	private Text id_ncc;

	@FXML
	private TableColumn<Chitietdathang, Void> xoasp;

	@FXML
	private Button nhacungcap;

	@FXML
	void ChonNCC(ActionEvent event) {
		ListNhaCungCapController controller2 = new ListNhaCungCapController(this);
		controller2.showStage();
	}

	public void setNhaCungCap(Nhacungcap nhacungcap) {
		id_ncc.setText(String.valueOf(nhacungcap.getMancc()));
		name_ncc.setText(String.valueOf(nhacungcap.getTenncc()));
	}

	@FXML
	private ImageView exit1;

	@FXML
	void exit1(MouseEvent event) {
		Stage stage = (Stage) exit1.getScene().getWindow();
		stage.close();
		GiaoDienQLController.getInstance().falsedisable();

	}

	@FXML
	void CanCel(ActionEvent event) {
		Stage stage = (Stage) huy.getScene().getWindow();
		stage.close();
		GiaoDienQLController.getInstance().falsedisable();
	}

	void Timkiem() {
		ObservableList<Sanpham> TableSP = FXCollections.observableArrayList(getSanpham());
		FilteredList<Sanpham> filteredData = new FilteredList<>(TableSP, b -> true);
		Timkiem.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(sanpham -> {
				String lowerCaseFilter = newValue.toLowerCase();
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				if (sanpham.getTensanpham().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (sanpham.getDonvi().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else
					return false;
			});
		});
		SortedList<Sanpham> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(tableSP.comparatorProperty());
		tableSP.setItems(sortedData);

	}

	public ObservableList<Sanpham> getSanpham() {
		ObservableList<Sanpham> TableSP = FXCollections.observableArrayList();
		Session session = HibernateUtils.getSessionFactory().openSession();

		CriteriaQuery<Sanpham> sp = session.getCriteriaBuilder().createQuery(Sanpham.class);
		sp.from(Sanpham.class);
		List<Sanpham> eList = session.createQuery(sp).getResultList();
		for (Sanpham ent : eList) {
			TableSP.add(ent);
		}
		return TableSP;
	}

	void ReloadSANPHAM() {
		getSanpham();

	}

	private void setCellValueFromTabletoTexfFieldd() {
		tableSP.setOnMouseClicked(event -> {
			Sanpham sp = tableSP.getItems().get(tableSP.getSelectionModel().getSelectedIndex());
			tf1.setText(sp.getTensanpham());
			tf2.setText(Integer.toString(sp.getMasanpham()));
			tf3.setText(sp.getDonvi());
			tf6.setText(sp.getLoaisanpham());
		});
	}

		private void ButtonXoaSP() {
			Callback<TableColumn<Chitietdathang, Void>, TableCell<Chitietdathang, Void>> cellFactory = new Callback<TableColumn<Chitietdathang, Void>, TableCell<Chitietdathang, Void>>() {
				@Override
				public TableCell<Chitietdathang, Void> call(final TableColumn<Chitietdathang, Void> param) {
					final TableCell<Chitietdathang, Void> cell = new TableCell<Chitietdathang, Void>() {
						private final Button btn = new Button("Xóa");
						{
							btn.setOnAction((ActionEvent event) -> {
								Chitietdathang chitietdathang = getTableView().getItems().get(getIndex());
								if (tableSP1.getItems().contains(chitietdathang)) {
									chitietdathang.setSoluong(chitietdathang.getSoluong() - chitietdathang.getSoluong());
									tableSP1.refresh();
									if (chitietdathang.getSoluong() == 0) {
										tableSP1.getItems().remove(chitietdathang);
										tableSP1.refresh();
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
			xoasp.setCellFactory(cellFactory);
		}

	@FXML
	void DatHang(ActionEvent event) {
		if (KiemTraGia() & KiemTraSL()) {
			Sanpham sp = tableSP.getItems().get(tableSP.getSelectionModel().getSelectedIndex());
			String tensanpham = tf1.getText();
			int masanpham = Integer.parseInt(tf2.getText());
			String donvi = tf3.getText();
			String loaisanpham = tf6.getText();
			int donvitinh = Integer.parseInt(tf4.getText());
			int giatien = Integer.parseInt(tf5.getText());
			addItem(donvitinh, sp, 0.0,giatien);
			tableSP1.refresh();
			tf4.setText(null);
			tf5.setText(null);
		}
	}

	public void addItem(int soluong, Sanpham sanpham, double thanhtien,int dongia) {
		Chitietdathang entry = tableSP1.getItems().stream().filter(item -> item.getSanpham().equals(sanpham)).findAny()
				.orElseGet(() -> {
					Chitietdathang newItem = new Chitietdathang(0, sanpham, thanhtien,dongia);
					tableSP1.getItems().add(newItem);
					return newItem;
				});
		int giatien = Integer.parseInt(tf5.getText());
		entry.setSoluong(soluong + entry.getSoluong());
		entry.setDongia(dongia);
		entry.setThanhtien(entry.getDongia() * entry.getSoluong());
		int sum = 0;
		for (Chitietdathang chitietdathang : tableSP1.getItems()) {
			sum = (int) (sum + (chitietdathang.getDongia() * chitietdathang.getSoluong()));
		}
		tongtien.setText(String.valueOf(sum));
		QLKhoController.getInstance().ReloadSANPHAM();
	}

	public void loadData(Taikhoannv taikhoan) {
		int manhanvien = taikhoan.getNhanvien().getManv();
		lapphieu.setOnMouseClicked(event -> {
			if (KiemTraPT() & KiemTraNCC()&KiemTraNhap()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				Session session = HibernateUtils.getSessionFactory().openSession();
				int manv = taikhoan.getNhanvien().getManv();
				int tongtienphieudathang = Integer.parseInt(tongtien.getText());
				Nhanvien nhanvien = new Nhanvien();
				nhanvien = session.get(Nhanvien.class, manv);
				LocalDateTime thoigiandat = LocalDateTime.now();
				int ma_ncc = Integer.parseInt(id_ncc.getText());
				Nhacungcap ncc = new Nhacungcap();
				ncc = session.get(Nhacungcap.class, ma_ncc);
				Phieudathang phieudathang = new Phieudathang(thoigiandat, tongtienphieudathang, ncc, nhanvien, false);
				if (cbbthanhtoan.getValue() == "Trực tiếp") {
					try {
						session.beginTransaction();
						session.save(phieudathang);
						session.getTransaction().commit();
					} catch (Exception e) {
						alert.setContentText("Lỗi " + e);
						alert.showAndWait();
						session.getTransaction().rollback();
					}
					for (Chitietdathang chitietdathang : tableSP1.getItems()) {
						Sanpham spp = new Sanpham();
						int masp = chitietdathang.getSanpham().getMasanpham();
						spp = session.get(Sanpham.class, masp);
						int soluong = chitietdathang.getSoluong();
						double thanhtien = chitietdathang.getThanhtien();
						int dongia = chitietdathang.getDongia();
						phieudathang = session.get(Phieudathang.class, phieudathang.getMadathang());
						Chitietdathang chitietdathang1 = new Chitietdathang(phieudathang, spp, soluong, thanhtien,dongia);
						try {
							session.beginTransaction();
							session.save(chitietdathang1);
							session.getTransaction().commit();
						} catch (RuntimeException error) {
							System.out.println(error);
							alert.setContentText("Lập phiếu đặt hàng thất bại  !");
							alert.showAndWait();
							session.getTransaction().rollback();
						}

					}
					alert.setContentText("Lập phiếu đặt hàng thành công ! ! ! ");
					alert.showAndWait();
					InPhieudathang(phieudathang);
				} else if (cbbthanhtoan.getValue() == "Tạo công nợ") {
					Nhacungcap nhacungcap = new Nhacungcap();
					nhacungcap = session.get(Nhacungcap.class, ma_ncc);
					try {

						session.beginTransaction();
						session.save(phieudathang);
						nhacungcap.setSotienno(nhacungcap.getSotienno() + Integer.parseInt(String.valueOf(tongtienphieudathang)));
						session.save(nhacungcap);
						session.getTransaction().commit();
						alert.setContentText("Cập nhật công nợ thành công  ");
						alert.showAndWait();
					} catch (Exception e) {
						alert.setContentText("Lỗi " + e);
						alert.showAndWait();
						session.getTransaction().rollback();
					}
					for (Chitietdathang chitietdathang : tableSP1.getItems()) {
						Sanpham spp = new Sanpham();
						int masp = chitietdathang.getSanpham().getMasanpham();
						spp = session.get(Sanpham.class, masp);
						int soluong = chitietdathang.getSoluong();
						double thanhtien = chitietdathang.getThanhtien();
						int dongia = chitietdathang.getDongia();
						phieudathang = session.get(Phieudathang.class, phieudathang.getMadathang());
						Chitietdathang chitietdathang1 = new Chitietdathang(phieudathang, spp, soluong, thanhtien,dongia);
						try {
							session.beginTransaction();
							session.save(chitietdathang1);
							session.getTransaction().commit();

						} catch (RuntimeException error) {
							System.out.println(error);
							alert.setContentText("Lập phiếu đặt hàng thất bại  !");
							alert.showAndWait();
							session.getTransaction().rollback();
						}

					}
					alert.setContentText("Lập phiếu đặt hàng thành công ! ! ! ");
					alert.showAndWait();
					InPhieudathang(phieudathang);
				} else {
					alert.setContentText("bạn phải chọn phương thức thanh toán  !");
					alert.showAndWait();
				}
				GiaoDienQLController.getInstance().falsedisable();
				QLKhoController.getInstance().ReloadSANPHAM();
				tableSP1.getItems().clear();
			}

		});
	}

	public void InPhieudathang(Phieudathang phieudathang) {
		try {
			final String DB_URL = "jdbc:mysql://localhost/qlbh?serverTimezone=Asia/Ho_Chi_Minh";
			Connection conn = DriverManager.getConnection(DB_URL, "root", "");
			InputStream in = new FileInputStream(
					new File("C:\\Users\\Admin\\eclipse-workspace\\QLBH\\src\\main\\java\\QLKho\\Phieudathang.jrxml"));
			JasperDesign jd = JRXmlLoader.load(in);
			String sql = "SELECT PDH.madathang,NCC.tenncc,PDH.tongtien,PDH.thoigian,SP.tensanpham,SP.loaisanpham,CTDH.thanhtien,CTDH.soluong FROM dathang PDH,Chitietdathang CTDH,Sanpham SP,Nhacungcap NCC WHERE PDH.madathang = CTDH.madathang and CTDH.masanpham = SP.masanpham and PDH.mancc = NCC.mancc and PDH.madathang ='"
					+ phieudathang.getMadathang() + "'";
			JRDesignQuery newQuery1 = new JRDesignQuery();
			newQuery1.setText(sql);
			jd.setQuery(newQuery1);
			JasperReport jr = JasperCompileManager.compileReport(jd);
			HashMap<String, Object> para = new HashMap<>();
			String madathang = String.valueOf(phieudathang.getMadathang());
			String tongtien = String.valueOf(phieudathang.getTongtien());
			para.put("madathang", madathang);
			para.put("tongtien", tongtien);
			JasperPrint j = JasperFillManager.fillReport(jr, para, conn);
			JasperViewer.viewReport(j, false);
			OutputStream os = new FileOutputStream(new File("C:\\Users\\Admin\\Desktop\\IN"));
			JasperExportManager.exportReportToPdfStream(j, os);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Lỗi" + e);
		}
	}

	private boolean KiemTraNCC() {
		Pattern p = Pattern.compile("^[A-Za-zÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]+$");
		Matcher m = p.matcher(name_ncc.getText());
		if (m.find() && m.group().equals(name_ncc.getText())) {
			thongbaoo.setText(null);
			return true;
		} else {
			thongbaoo.setText("Vui lòng chọn nhà cung cấp");
			return false;
		}
	}

	private boolean KiemTraNhap() {
		if (tableSP1.getItems().isEmpty()) {
			kiemtranhap.setText("Vui lòng chọn sản phẩm bên trái và nhập đủ thông tin đặt");
			return false;
		} else {
			kiemtranhap.setText(null);
			return true;
		}
	}
	
	private boolean KiemTraPT() {
		if (cbbthanhtoan.getValue() == null) {
			thongbao6.setText("Chưa chọn phương thức thanh toán");
			return false;
		}
		thongbao6.setText(null);
		return true;

	}

	private boolean KiemTraSL() {
		Pattern p = Pattern.compile("[0-9]+");
		Matcher m = p.matcher(tf4.getText());
		if (m.find() && m.group().equals(tf4.getText())) {
			thongbao4.setText(null);
			return true;
		} else {
			thongbao4.setText("Vui lòng điền số phù hợp");
			return false;
		}
	}

	private boolean KiemTraGia() {
		Pattern p = Pattern.compile("[0-9]+");
		Matcher m = p.matcher(tf5.getText());
		if (m.find() && m.group().equals(tf5.getText())) {
			thongbao5.setText(null);
			return true;
		} else {
			thongbao5.setText("Vui lòng điền số phù hợp");
			return false;
		}
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		ObservableList<String> list = FXCollections.observableArrayList("Trực tiếp", "Tạo công nợ");
		cbbthanhtoan.setItems(list);
		setCellValueFromTabletoTexfFieldd();
		tensanpham.setCellValueFactory(new PropertyValueFactory<Sanpham, String>("tensanpham"));
		masanpham.setCellValueFactory(new PropertyValueFactory<Sanpham, Integer>("masanpham"));
		donvi.setCellValueFactory(new PropertyValueFactory<Sanpham, String>("donvi"));
		donvitinh.setCellValueFactory(new PropertyValueFactory<Sanpham, Integer>("donvitinh"));
		tensanpham1.setCellValueFactory(new PropertyValueFactory<>("sanpham"));
		tensanpham1.setCellFactory(tableSP1 -> new TableCell<Chitietdathang, Sanpham>() {
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
		masanpham1.setCellValueFactory(new PropertyValueFactory<>("sanpham"));
		masanpham1.setCellFactory(tableSP1 -> new TableCell<Chitietdathang, Sanpham>() {
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
		loaisanpham1.setCellValueFactory(new PropertyValueFactory<>("sanpham"));
		loaisanpham1.setCellFactory(tableSP1 -> new TableCell<Chitietdathang, Sanpham>() {
			@Override
			protected void updateItem(Sanpham item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText(String.valueOf(item.getLoaisanpham()));
				}
			}
		});
		donvi1.setCellValueFactory(new PropertyValueFactory<>("sanpham"));
		donvi1.setCellFactory(tableSP1 -> new TableCell<Chitietdathang, Sanpham>() {
			@Override
			protected void updateItem(Sanpham item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText(String.valueOf(item.getDonvi()));
				}
			}
		});

		giatien1.setCellValueFactory(new PropertyValueFactory<Chitietdathang,Integer>("dongia"));
		donvitinh1.setCellValueFactory(new PropertyValueFactory<Chitietdathang, Integer>("soluong"));
		Tongtien.setCellValueFactory(new PropertyValueFactory<Chitietdathang, Double>("thanhtien"));
		tableSP.setItems(getSanpham());
		Timkiem();
		ButtonXoaSP();
	}
}
