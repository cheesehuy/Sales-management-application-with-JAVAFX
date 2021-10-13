package BanHang;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import org.hibernate.Session;
import org.hibernate.query.Query;
import entities.*;
import QLBH.*;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.persistence.criteria.CriteriaQuery;
import javax.swing.JOptionPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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

public class BanHangController implements Initializable {
	public static BanHangController instance;

	public BanHangController() {
		instance = this;
	}

	public static BanHangController getInstance() {
		return instance;
	}

	@FXML
	private ComboBox<String> timkiemloaisp;
	@FXML
	private Button xoakh;
	@FXML
	private Label thongbao;
	@FXML
	private TextField tongtien;

	@FXML
	private TextField giamgia;

	@FXML
	private Label khachhangg;

	@FXML
	private Label diemtichluy;

	@FXML
	private TextField khachtra;

	@FXML
	private TextField tienthua;
	@FXML
	private TableView<Sanpham> TableSP;

	@FXML
	private TableView<Chitiethoadon> hoadon;

	@FXML
	private TableColumn giatien;

	@FXML
	private TableColumn<Chitiethoadon, Sanpham> masanpham1;

	@FXML
	private TableColumn loaisanpham;

	@FXML
	private TableColumn loaisanpham1;

	@FXML
	private TableColumn donvi1;

	@FXML
	private TableColumn giatien1;

	@FXML
	private TableColumn tensanpham;

	@FXML
	private TableColumn tensanpham1;

	@FXML
	private TableColumn donvitnh1;

	@FXML
	private TableColumn donvitnh;
	@FXML
	private TableColumn thanhtien;

	@FXML
	private TableColumn xoaSP;

	@FXML
	private TableColumn hinhanhsanpham;
	@FXML
	private Button thanhtoan;

	@FXML
	private Button timkhachhang;

	@FXML
	private Button chinhsachkhuyenmai;

	@FXML
	private Label thongbaokhachhang;

	@FXML
	private Label thongbaokhachtra;

	@FXML
	private Label thongbaosanpham;

	@FXML
	private AnchorPane ap;

	public void falsedisable() {
		ap.setDisable(false);
	}

	public void truedisable() {
		ap.setDisable(true);
	}

	private boolean KiemTraTienKhachTra() {
		Pattern p = Pattern.compile("[0-9]+");
		Matcher m = p.matcher(khachtra.getText());
		if (m.find() && m.group().equals(khachtra.getText())) {
			thongbaokhachtra.setText(null);
			return true;
		} else {
			thongbaokhachtra.setText("Vui lòng điền số tiền khách trả hợp lệ");
			return false;
		}
	}

	private boolean KiemTraTienKhachTra1() {
		if (khachtra.getText().isEmpty()) {
			thongbaokhachtra.setText("Vui lòng điền số tiền khách trả hợp lệ");
			return false;
		} else if(khachtra.getText().equals(0)){
			thongbaokhachtra.setText("Vui lòng điền số tiền khách trả hợp lệ");
			return false;
			
		}
		thongbaokhachtra.setText(null);
		return true;
	}

	private boolean KiemTraSanPhamHoaDon() {
		if (hoadon.getItems().isEmpty()) {
			thongbaosanpham.setText("Chưa có sản phẩm trong hóa đơn");
			return false;
		} else {
			thongbaosanpham.setText(null);
			return true;
		}
	}
	//

	ObservableList<Sanpham> danhmuchoadon = FXCollections.observableArrayList();

	private void setCellValueFromTabletoTexfFieldd() {
		TableSP.setOnMouseClicked(event -> {
			event();

		});
	}
	private boolean KiemTraKt() {
		Pattern p = Pattern.compile("^[A-Za-zÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ ]+$");
		Matcher m = p.matcher(khachtra.getText());
		if (m.find() && m.group().equals(khachtra.getText())) {
			
			thongbaokhachtra.setText("Vui lòng điền số tiền hợp lệ");
			return false;
		} else {
			thongbaokhachtra.setText(null);
			return true;
		}
		}
	
	private boolean KiemTraKt1() {
		Pattern p = Pattern.compile("[0]+");
		Matcher m = p.matcher(khachtra.getText());
		if (m.find() && m.group().equals(khachtra.getText())) {
			thongbaokhachtra.setText("Vui lòng điền số tiền phù hợp  ");
			return false;
			
		} else {
			thongbaokhachtra.setText(null);
			return true;
		}
	}

	public void loadData(Taikhoannv taikhoan) throws IOException {
		
		thanhtoan.setOnMouseClicked(event -> {
			if (KiemTraSanPhamHoaDon() & KiemTraTienKhachTra() & KiemTraKt1() &KiemTraKt()) {
				if (Float.parseFloat(khachtra.getText()) < Float.parseFloat(tongtien.getText())) {
					khachtra.setText("");
					thongbaokhachtra.setText("Không đủ tiền , nhập lại");
					
					return;

				} else {
					float tientrakhach = Float.parseFloat(khachtra.getText()) - Float.parseFloat(tongtien.getText());
					tienthua.setText(String.valueOf(tientrakhach));
					thongbaokhachtra.setText(null);
				}

				try {
				Alert alert = new Alert(AlertType.INFORMATION);
				Session session = HibernateUtils.getSessionFactory().openSession();
				for (Sanpham spp1 : TableSP.getItems()) {
					for (Chitiethoadon chitiethoadon123 : hoadon.getItems()) {
						if (spp1.getMasanpham() == chitiethoadon123.getSanpham().getMasanpham()) {
							if (chitiethoadon123.getSoluong() > spp1.getDonvitinh()) {
								alert.setContentText(spp1.getTensanpham() + "Đã hết hàng");
								alert.showAndWait();

								return;
							}
						}
					}
				}

				Float tongtienmua = Float.parseFloat(tongtien.getText());
				KhachHang khachhang = new KhachHang();
				Nhanvien nhanvien = new Nhanvien();
				LocalDateTime dateTime = LocalDateTime.now();
				nhanvien = session.get(Nhanvien.class, taikhoan.getNhanvien().getManv());
				Hoadon hoadonn = new Hoadon(dateTime, tongtienmua, nhanvien, null);
				try {
					session.beginTransaction();
					session.save(hoadonn);
					session.getTransaction().commit();
				} catch (RuntimeException error) {
					alert.setContentText("Lỗi " + error);
					alert.showAndWait();
					session.getTransaction().rollback();
				}
				for (Sanpham spp2 : TableSP.getItems()) {
					for (Chitiethoadon chitiethoadon123 : hoadon.getItems()) {
						if (spp2.getMasanpham() == chitiethoadon123.getSanpham().getMasanpham()) {
							if (chitiethoadon123.getSoluong() <= spp2.getDonvitinh()) {
								Sanpham capnhatSanpham = new Sanpham();

								capnhatSanpham = session.get(Sanpham.class, spp2.getMasanpham());
								Sanpham spp = new Sanpham();
								int masp = chitiethoadon123.getSanpham().getMasanpham();
								spp = session.get(Sanpham.class, masp);
								int soluong = chitiethoadon123.getSoluong();
								double thanhtien = chitiethoadon123.getThanhtien();
								int soluongconlai = capnhatSanpham.getDonvitinh() - soluong;
								Chitiethoadon chitiethoadon = new Chitiethoadon(soluong, hoadonn, spp, thanhtien);
								try {
									session.beginTransaction();
									capnhatSanpham.setDonvitinh(soluongconlai);
									session.save(capnhatSanpham);
									System.out.println(hoadonn.getMahoadon());
									hoadonn = session.get(Hoadon.class, hoadonn.getMahoadon());
									session.save(chitiethoadon);
									// session.save(spp);
									session.getTransaction().commit();

								} catch (RuntimeException error) {
									System.out.println(error);
									error.printStackTrace();
									// alert.setContentText("Thêm chi tiet thất bại !");
									// alert.showAndWait();
									session.getTransaction().rollback();
								}
							}

						}
					}
				}
				alert.setContentText("Tạo hóa đơn thành công !");
				alert.showAndWait();
				// KIEM TRA HOA DON KHONG CO KHACH HANG
				try {
					if (khachhangg.getText() == null) {
						hoadon.getItems().clear();
						tongtien.setText(String.valueOf(0));
						khachtra.setText(String.valueOf(0));
						giamgia.setText(String.valueOf(0));
						tienthua.setText(null);
						khachhangg.setText(null);
						diemtichluy.setText(null);
						refeshTable();
						// thongbaokhachtra.setText(null);

					}
					// KIEM TRA HOA DON CO KHACH HANG

					else if (khachhangg.getText() != null) {

						int makh = Integer.parseInt(khachhangg.getText());
						khachhang = session.get(KhachHang.class, makh);
						float tongtienn = Float.parseFloat(tongtien.getText());
						System.out.println(tongtien.getText());
						int tichdiem = Math.round(tongtienn)/10000;
						System.out.println(tichdiem);
						int tongg = khachhang.getDiemtichluy() + tichdiem;
						session.beginTransaction();
						hoadonn.setKhachhang(khachhang);
						khachhang.setDiemtichluy(tongg);
						session.save(khachhang);
						session.getTransaction().commit();
						hoadon.getItems().clear();
						tongtien.setText(String.valueOf(0));
						khachtra.setText(String.valueOf(0));
						giamgia.setText(String.valueOf(0));
						tienthua.setText(null);
						khachhangg.setText(null);
						diemtichluy.setText(null);
						refeshTable();
						// thongbaokhachtra.setText(null);
						
					}

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(e);
				}
				// reset

				/// IN HOA DON
				try {

					final String DB_URL = "jdbc:mysql://localhost/qlbh?serverTimezone=Asia/Ho_Chi_Minh";
					Connection conn = DriverManager.getConnection(DB_URL, "root", "");
					InputStream in = new FileInputStream(new File(
							"C:\\Users\\Admin\\eclipse-workspace\\QLBH\\src\\main\\java\\BanHang\\InHoaDon1.jrxml"));
					JasperDesign jd = JRXmlLoader.load(in);
					String sql = "SELECT H.mahoadon,H.manv,H.makh,H.tonggia,H.thoigianmua,SP.tensanpham,SP.loaisanpham,SP.giatien,CTHD.thanhtien,CTHD.soluong FROM Hoadon H,Chitiethoadon CTHD,Sanpham SP WHERE H.mahoadon = CTHD.mahoadon and CTHD.masanpham = SP.masanpham and H.mahoadon ='"
							+ hoadonn.getMahoadon() + "'";
					JRDesignQuery newQuery1 = new JRDesignQuery();
					newQuery1.setText(sql);
					jd.setQuery(newQuery1);
					JasperReport jr = JasperCompileManager.compileReport(jd);
					HashMap<String, Object> para = new HashMap<>();
					String mahoadon = String.valueOf(hoadonn.getMahoadon());
					String tonggia = String.valueOf(hoadonn.getTonggia());
					para.put("mahoadon", mahoadon);
					para.put("tonggia", tonggia);
					JasperPrint j = JasperFillManager.fillReport(jr, para, conn);
					JasperViewer.viewReport(j, false);
					OutputStream os = new FileOutputStream(new File("C:\\Users\\Admin\\Desktop\\IN"));
					JasperExportManager.exportReportToPdfStream(j, os);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Lỗi" + e);
				}

			}catch (Exception e) {
				// TODO: handle exception
				System.out.print(e);
			}
			}
		});
	}
	

//

	private void ButtonXoaSP() {

		Callback<TableColumn<Chitiethoadon, Void>, TableCell<Chitiethoadon, Void>> cellFactory = new Callback<TableColumn<Chitiethoadon, Void>, TableCell<Chitiethoadon, Void>>() {
			@Override
			public TableCell<Chitiethoadon, Void> call(final TableColumn<Chitiethoadon, Void> param) {
				final TableCell<Chitiethoadon, Void> cell = new TableCell<Chitiethoadon, Void>() {

					private final Button btn = new Button("Xóa");

					{
						///////////////////////////
						btn.setOnAction((ActionEvent event) -> {
							Chitiethoadon chitiethoadon = getTableView().getItems().get(getIndex());
							if (hoadon.getItems().contains(chitiethoadon)) {
								chitiethoadon.setSoluong(chitiethoadon.getSoluong() - 1);
								chitiethoadon.setThanhtien(
										chitiethoadon.getSanpham().getGiatien() * chitiethoadon.getSoluong());
								hoadon.refresh();
								if (chitiethoadon.getSoluong() == 0) {
									hoadon.getItems().remove(chitiethoadon);
									hoadon.refresh();
								}
								float sum = 0;
								for (Chitiethoadon chitiethoadon1 : hoadon.getItems()) {
									sum = sum + (chitiethoadon1.getSanpham().getGiatien() * chitiethoadon1.getSoluong());
								}			
								tongtien.setText(String.valueOf(sum));
								float tiengiam = Float.parseFloat(giamgia.getText()) / 100;
								float tongtiengiam = Float.parseFloat(tongtien.getText()) * (1 - tiengiam);
								tongtien.setText(String.valueOf(tongtiengiam));
								float tientrakhach = Float.parseFloat(khachtra.getText()) - Float.parseFloat(tongtien.getText());
								tienthua.setText(String.valueOf(tientrakhach));
								thongbaokhachtra.setText(null);
								// kiemtratftongtien();
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
		xoaSP.setCellFactory(cellFactory);
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
	
	public ObservableList<String> getLoaiSanpham() {
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
	
	public void refeshTable() {
		tensanpham.setCellValueFactory(new PropertyValueFactory<Sanpham, String>("tensanpham"));
		loaisanpham.setCellValueFactory(new PropertyValueFactory<Sanpham, String>("loaisanpham"));
		donvitnh.setCellValueFactory(new PropertyValueFactory<Sanpham, String>("donvitinh"));
		// String>("donvitinh"));
		giatien.setCellValueFactory(new PropertyValueFactory<Sanpham, Integer>("giatien"));
		// donvi.setCellValueFactory(new PropertyValueFactory<Sanpham,
		// String>("donvi"));
		hinhanhsanpham.setCellValueFactory(new PropertyValueFactory<Sanpham, Byte>("imagesp"));
		hinhanhsanpham.setCellFactory(param -> new TableCell<Sanpham, byte[]>() {

			private ImageView imageView = new ImageView();

			@Override
			protected void updateItem(byte[] item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null || empty) {
					setText(null);
					setGraphic(null);
				} else {
					imageView.setImage(getImageFromBytes(item));
					imageView.setFitHeight(100);
					imageView.setFitWidth(100);
					setGraphic(imageView);
				}
				this.setItem(item);
			}
		});
		TableSP.setItems(getSanpham());
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// set value for combobox
		//ObservableList<String> list = FXCollections.observableArrayList("snack", "drink", "food", "kem", "ALL");
		timkiemloaisp.setItems(getLoaiSanpham());
		timkiemloaisp.getItems().add("ALL");
		//

		tongtien.setText("0.0");
		khachtra.setText(String.valueOf(0));
		giamgia.setText(String.valueOf(0));

		setCellValueFromTabletoTexfFieldd();
		// masanpham.setCellValueFactory(new PropertyValueFactory<Sanpham,
		// Integer>("masanpham"));
		refeshTable();
		Timkiem();

		//
		// masanpham1.setCellValueFactory(new PropertyValueFactory<Chitiethoadon,
		// Sanpham>("sanpham"));
		masanpham1.setCellValueFactory(new PropertyValueFactory<>("sanpham"));
		masanpham1.setCellFactory(hoadon -> new TableCell<Chitiethoadon, Sanpham>() {
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

		tensanpham1.setCellValueFactory(new PropertyValueFactory<>("sanpham"));
		tensanpham1.setCellFactory(hoadon -> new TableCell<Chitiethoadon, Sanpham>() {
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

		loaisanpham1.setCellValueFactory(new PropertyValueFactory<>("sanpham"));
		loaisanpham1.setCellFactory(hoadon -> new TableCell<Chitiethoadon, Sanpham>() {
			@Override
			protected void updateItem(Sanpham item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText(item.getLoaisanpham());
				}
			}

		});
		donvitnh1.setCellValueFactory(new PropertyValueFactory<Chitiethoadon, Integer>("soluong"));

		giatien1.setCellValueFactory(new PropertyValueFactory<>("sanpham"));
		giatien1.setCellFactory(hoadon -> new TableCell<Chitiethoadon, Sanpham>() {
			@Override
			protected void updateItem(Sanpham item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText(String.valueOf(item.getGiatien()));
				}
			}

		});
		donvi1.setCellValueFactory(new PropertyValueFactory<>("sanpham"));
		donvi1.setCellFactory(hoadon -> new TableCell<Chitiethoadon, Sanpham>() {
			@Override
			protected void updateItem(Sanpham item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText(item.getDonvi());
				}
			}

		});
		ButtonXoaSP();

		thanhtien.setCellValueFactory(new PropertyValueFactory<Chitiethoadon, Double>("thanhtien"));

//	        
		khachtra.addEventHandler(KeyEvent.KEY_PRESSED, e -> {
			if(KiemTraKt()) {
			if (e.getCode() == KeyCode.ENTER) {
				if (Float.parseFloat(khachtra.getText()) < Float.parseFloat(tongtien.getText())) {
					khachtra.setText("");
					thongbaokhachtra.setText("Không đủ tiền , nhập lại");

				} else {
					float tientrakhach = Float.parseFloat(khachtra.getText()) - Float.parseFloat(tongtien.getText());
					tienthua.setText(String.valueOf(tientrakhach));
					thongbaokhachtra.setText(null);
				}
			}
		}});
		khachhangg.setText(null);
//

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
	//

	//
	private void event() {
		Sanpham sp = TableSP.getItems().get(TableSP.getSelectionModel().getSelectedIndex());
		addItem(0, sp, 0.0);
		hoadon.refresh();
	}

	public void addItem(int soluong, Sanpham sanpham, double thanhtien) {
		Chitiethoadon entry = hoadon.getItems().stream().filter(item -> item.getSanpham().equals(sanpham)).findAny()
				.orElseGet(() -> {
					// Sanpham newItem = new Sanpham(masanpham,tensanpham, loaisanpham,donvi,0, 0);
					Chitiethoadon newItem = new Chitiethoadon(soluong, sanpham, thanhtien);
					hoadon.getItems().add(newItem);
					return newItem;
				});
		entry.setSoluong(entry.getSoluong() + 1);
		// entry.setDonvitinh(entry.getDonvitinh() + donvitinh);
		// entry.setGiatien(entry.getGiatien() + giatien);
		entry.setThanhtien(entry.getSanpham().getGiatien() * entry.getSoluong());
		float sum = 0;
		for (Chitiethoadon chitiethoadon : hoadon.getItems()) {
			sum = sum + (chitiethoadon.getSanpham().getGiatien() * chitiethoadon.getSoluong());
		}			
		tongtien.setText(String.valueOf(sum));
		float tiengiam = Float.parseFloat(giamgia.getText()) / 100;
		float tongtiengiam = Float.parseFloat(tongtien.getText()) * (1 - tiengiam);
		tongtien.setText(String.valueOf(tongtiengiam));
		float tientrakhach = Float.parseFloat(khachtra.getText()) - Float.parseFloat(tongtien.getText());
		tienthua.setText(String.valueOf(tientrakhach));
		kiemtratftongtien();
		// TableSP.refresh();
		// entry.s
	}

	// tim kiem
	@FXML
	private TextField Timkiem;

	void Timkiem() {
		ObservableList<Sanpham> tableSP = FXCollections.observableArrayList(getSanpham());

		FilteredList<Sanpham> filteredData = new FilteredList<>(tableSP, b -> true);
		Timkiem.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(sanpham -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();

				if (sanpham.getTensanpham().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches username
				} else if (sanpham.getLoaisanpham().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches password
				} else
					return false; // Does not match.
			});
		});
		timkiemloaisp.valueProperty().addListener((observable, oldValue, newValue) -> {

			filteredData.setPredicate(sanpham -> {
				// Timkiem.setText(null);
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				if (newValue == "ALL") {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				// Filter matches username
				if (sanpham.getLoaisanpham().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; // Filter matches password
				} else
					return false; // Does not match.
			});
		});
		//
		timkiemloaisp.valueProperty().addListener((observable, oldValue, newValue) -> {
			Timkiem.textProperty().addListener((observable1, oldValue1, newValue1) -> {
				filteredData.setPredicate(sanpham -> {
					// Timkiem.setText(null);
					String lowerCaseFilter = newValue.toLowerCase();
					String lowerCaseFilter1 = newValue1.toLowerCase();
					if (newValue == null || newValue.isEmpty()) {
						return true;
					}
					if (newValue == "ALL" && sanpham.getTensanpham().toLowerCase().indexOf(lowerCaseFilter1) != -1) {
						return true;
					}

					// Filter matches username
					if (sanpham.getLoaisanpham().toLowerCase().indexOf(lowerCaseFilter) != -1
							&& sanpham.getTensanpham().toLowerCase().indexOf(lowerCaseFilter1) != -1) {
						return true; // Filter matches password
					} else
						return false; // Does not match.
				});
			});
		});
		//
		SortedList<Sanpham> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(TableSP.comparatorProperty());
		TableSP.setItems(sortedData);

	}

	@FXML
	void timkhachhang(ActionEvent event) {
		timkhachhangController controller2 = new timkhachhangController(this);
		controller2.showStage();
	}

	@FXML
	void chinhsachkhuyenmai(ActionEvent event) {

	}

	public void setkhachhang(KhachHang khachhang) {
		diemtichluy.setText(String.valueOf((khachhang.getDiemtichluy())));
		khachhangg.setText(String.valueOf(khachhang.getMakh()));
		if (Integer.parseInt(diemtichluy.getText()) > 1000) {
			giamgia.setText("20");
			int sum = 0;
			for (Chitiethoadon chitiethoadon1 : hoadon.getItems()) {
				sum = sum
						+ (chitiethoadon1.getSanpham().getGiatien() * chitiethoadon1.getSoluong());

			}		
			tongtien.setText(String.valueOf(sum));
			float tiengiam = Float.parseFloat(giamgia.getText()) / 100;
			float tongtiengiam = Float.parseFloat(tongtien.getText()) * (1 - tiengiam);
			tongtien.setText(String.valueOf(tongtiengiam));
			float tientrakhach = Float.parseFloat(khachtra.getText()) - Float.parseFloat(tongtien.getText());
			tienthua.setText(String.valueOf(tientrakhach));
			
		} else if (Integer.parseInt(diemtichluy.getText()) < 1000 && Integer.parseInt(diemtichluy.getText()) >= 500) {
			giamgia.setText("10");
			int sum = 0;
			for (Chitiethoadon chitiethoadon1 : hoadon.getItems()) {
				sum = sum
						+ (chitiethoadon1.getSanpham().getGiatien() * chitiethoadon1.getSoluong());

			}		
			tongtien.setText(String.valueOf(sum));
			float tiengiam = Float.parseFloat(giamgia.getText()) / 100;
			float tongtiengiam = Float.parseFloat(tongtien.getText()) * (1 - tiengiam);
			tongtien.setText(String.valueOf(tongtiengiam));
			float tientrakhach = Float.parseFloat(khachtra.getText()) - Float.parseFloat(tongtien.getText());
			tienthua.setText(String.valueOf(tientrakhach));
		} else if (Integer.parseInt(diemtichluy.getText()) < 500 && Integer.parseInt(diemtichluy.getText()) >= 100) {
			giamgia.setText("5");
			int sum = 0;
			for (Chitiethoadon chitiethoadon1 : hoadon.getItems()) {
				sum = sum
						+ (chitiethoadon1.getSanpham().getGiatien() * chitiethoadon1.getSoluong());

			}		
			tongtien.setText(String.valueOf(sum));
			float tiengiam = Float.parseFloat(giamgia.getText()) / 100;
			float tongtiengiam = Float.parseFloat(tongtien.getText()) * (1 - tiengiam);
			tongtien.setText(String.valueOf(tongtiengiam));
			float tientrakhach = Float.parseFloat(khachtra.getText()) - Float.parseFloat(tongtien.getText());
			tienthua.setText(String.valueOf(tientrakhach));
		} else
			giamgia.setText("0");
			int sum = 0;
			for (Chitiethoadon chitiethoadon1 : hoadon.getItems()) {
				sum = sum
						+ (chitiethoadon1.getSanpham().getGiatien() * chitiethoadon1.getSoluong());

			}		
			tongtien.setText(String.valueOf(sum));
			float tiengiam = Float.parseFloat(giamgia.getText()) / 100;
			float tongtiengiam = Float.parseFloat(tongtien.getText()) * (1 - tiengiam);
			tongtien.setText(String.valueOf(tongtiengiam));
			float tientrakhach = Float.parseFloat(khachtra.getText()) - Float.parseFloat(tongtien.getText());
			tienthua.setText(String.valueOf(tientrakhach));
	}

	private static double xoffset = 0;
	private static double yoffset = 0;
	@FXML
	public void imageClicked(MouseEvent event) throws IOException {
		if(GiaoDienNhanvienController.getInstance() == null) {
			
		
		Parent root = FXMLLoader.load(getClass().getResource("themkhachhang.fxml"));
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
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
		GiaoDienQLController.getInstance().truedisable();
		}
		else {
			Parent root = FXMLLoader.load(getClass().getResource("themkhachhang.fxml"));
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
			stage.initStyle(StageStyle.UNDECORATED);
			stage.setScene(scene);
			stage.setResizable(false);
			stage.show();	
			GiaoDienNhanvienController.getInstance().truedisable();
		}
	}

	@FXML
	void xoakh(ActionEvent event) {
		khachhangg.setText(null);
		diemtichluy.setText(null);
		giamgia.setText("0");
		int sum = 0;
		for (Chitiethoadon chitiethoadon1 : hoadon.getItems()) {
			sum = sum
					+ (chitiethoadon1.getSanpham().getGiatien() * chitiethoadon1.getSoluong());

		}		
		tongtien.setText(String.valueOf(sum));
		khachtra.setText(String.valueOf(0));
		giamgia.setText(String.valueOf(0));
		tienthua.setText(String.valueOf(0));
		thongbaokhachtra.setText(null);

		
	}

	void kiemtratftongtien() {
		if (Float.parseFloat(khachtra.getText()) >= Float.parseFloat(tongtien.getText())) {
			// float tiengiam = Float.parseFloat(giamgia.getText())/100;
			// float tongtiengiam = Float.parseFloat(tongtien.getText())*(1-tiengiam);
			float tientrakhach = Float.parseFloat(khachtra.getText()) - Float.parseFloat(tongtien.getText());
			tienthua.setText(String.valueOf(tientrakhach));
			thongbaokhachtra.setText(null);

		} else {
			khachtra.setText(String.valueOf(0));
			thongbaokhachtra.setText("Vui lòng nhập số tiền khách trả ");
			tienthua.setText("0");
		}
	}
}
