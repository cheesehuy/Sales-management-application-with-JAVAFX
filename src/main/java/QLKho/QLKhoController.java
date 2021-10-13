package QLKho;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import QLBH.GiaoDienQLController;
import QLBH.HibernateUtils;
import javax.imageio.ImageIO;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import entities.*;

public class QLKhoController implements Initializable {

	@FXML
	private TableColumn donvitinh;

	@FXML
	private TableColumn CapNhat;

	@FXML
	private TableColumn imageSp;

	@FXML
	private Button themsanpham;

	@FXML
	private TableColumn<Sanpham, Void> xoasp;

	@FXML
	private TableColumn donvi;

	@FXML
	private TableColumn giatien;

	@FXML
	private AnchorPane anchorpane1;

	@FXML
	private AnchorPane anchorpane2;

	@FXML
	private TableColumn tensanpham;

	@FXML
	private TableColumn masanpham;

	@FXML
	private TableColumn loaisanpham;
	@FXML
	private TextField Timkiem;

	ObservableList<Sanpham> listM;

	@FXML
	private Button lapphieudathang;

	@FXML
	private TextField tfloaisanpham;

	@FXML
	private Text idsanpham;
	@FXML
	private TableView<Sanpham> tableSP;

	public void falsedisable() {
		anchorpane1.setDisable(false);
	}

	public void truedisable() {
		anchorpane1.setDisable(true);
	}

	public static QLKhoController instance;

	public QLKhoController() {
		instance = this;
	}

	public static QLKhoController getInstance() {
		return instance;
	}

	ObservableList<Sanpham> table1 = FXCollections.observableArrayList(getSanpham());
	private static double xoffset = 0;
	private static double yoffset = 0;

	@FXML
	void ThemSP(ActionEvent event) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("/QLKho/themsanpham.fxml"));
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
		stage.show();
		GiaoDienQLController.getInstance().truedisable();

	}

	@FXML
	void reload(ActionEvent event) {

		initialize1();
	}

	private void ButtonXoaSP() {
		Callback<TableColumn<Sanpham, Void>, TableCell<Sanpham, Void>> cellFactory = new Callback<TableColumn<Sanpham, Void>, TableCell<Sanpham, Void>>() {
			@Override
			public TableCell<Sanpham, Void> call(final TableColumn<Sanpham, Void> param) {
				final TableCell<Sanpham, Void> cell = new TableCell<Sanpham, Void>() {
					private final Button btn = new Button("Xóa Sản Phẩm");
					{
						btn.setOnAction((ActionEvent event) -> {
							Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
							Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
							alert.setTitle("Xóa Sản Phẩm");
							alert.setContentText("Bạn có chắc xóa sản phẩm này ?");
							ButtonType okButton = new ButtonType("Yes");
							ButtonType noButton = new ButtonType("NO");
							alert.getButtonTypes().setAll(okButton, noButton);
							alert.showAndWait().ifPresent(type -> {
								if (type == okButton) {
									Sanpham sp = getTableView().getItems().get(getIndex());
									Session session = HibernateUtils.getSessionFactory().openSession();
									sp = session.get(Sanpham.class, sp.getMasanpham());
									try {
										session.beginTransaction();
										session.delete(sp);
										session.getTransaction().commit();
										ReloadSANPHAM();
										alert1.setContentText("Xóa sản phẩm thành công");
										alert1.showAndWait();
									} catch (RuntimeException error) {
										session.getTransaction().rollback();
									}
								} else {
									alert.close();
								}
							});
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

	private void ButtonChinhSuaAnh() {
		Callback<TableColumn<Sanpham, Void>, TableCell<Sanpham, Void>> cellFactory = new Callback<TableColumn<Sanpham, Void>, TableCell<Sanpham, Void>>() {
			@Override
			public TableCell<Sanpham, Void> call(final TableColumn<Sanpham, Void> param) {
				final TableCell<Sanpham, Void> cell = new TableCell<Sanpham, Void>() {

					private final Button btn = new Button("Cập nhật sản phẩm");

					{
						btn.setOnAction((ActionEvent event) -> {
							Parent root;
							try {
								FXMLLoader loader = new FXMLLoader(getClass().getResource("/QLKho/detailsanpham.fxml"));
								Parent CapnhatSP = loader.load();
								Stage stage = new Stage();
								Scene scene = new Scene(CapnhatSP);
								Sanpham sp = getTableView().getItems().get(getIndex());
								QLKho.detailsanphamcontroller CapNhatSP = loader.getController();
								CapNhatSP.setSanPham(sp);
								stage.setTitle("Cập nhật sản phẩm");
								stage.setScene(scene);
								stage.show();
							} catch (IOException e) {

								e.printStackTrace();

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
		CapNhat.setCellFactory(cellFactory);
	}

	void initialize1() {
		tensanpham.setCellValueFactory(new PropertyValueFactory<Sanpham, String>("tensanpham"));
		masanpham.setCellValueFactory(new PropertyValueFactory<Sanpham, Integer>("masanpham"));
		donvi.setCellValueFactory(new PropertyValueFactory<Sanpham, String>("donvi"));
		giatien.setCellValueFactory(new PropertyValueFactory<Sanpham, Integer>("giatien"));
		donvitinh.setCellValueFactory(new PropertyValueFactory<Sanpham, String>("donvitinh"));
		loaisanpham.setCellValueFactory(new PropertyValueFactory<Sanpham, String>("loaisanpham"));
		ButtonXoaSP();
		ButtonChinhSuaAnh();
		imageSp.setCellValueFactory(new PropertyValueFactory<Sanpham, Byte>("imagesp"));
		imageSp.setCellFactory(param -> new TableCell<Sanpham, byte[]>() {

			private ImageView imageView = new ImageView();

			@Override
			protected void updateItem(byte[] item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null || empty) {
					setText(null);
					setGraphic(null);
				} else {
					imageView.setImage(getImageFromBytes(item));
					imageView.setFitHeight(65);
					imageView.setFitWidth(65);

					BorderPane pane = new BorderPane();
					pane.setCenter(imageView);
					setGraphic(pane);
				}
				this.setItem(item);
			}
		});
		tableSP.setItems(getSanpham());
		Timkiem();

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

	@FXML
	private Button idluusp;

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
		initialize1();
	}

	public void loadData(Taikhoannv taikhoan) {
		lapphieudathang.setOnMouseClicked(event -> {
			ChangeSceneLapPhieu(taikhoan);
			System.out.println(taikhoan);
		});
	}

	public void ChangeSceneLapPhieu(Taikhoannv taikhoan) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("lapphieudathang.fxml"));
			Parent kiemtraViewParent = loader.load();
			Stage stage1 = new Stage();
			Scene scene1 = new Scene(kiemtraViewParent);
			scene1.setOnMousePressed(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					xoffset = stage1.getX() - mouseEvent.getScreenX();
					yoffset = stage1.getY() - mouseEvent.getScreenY();
				}
			});
			scene1.setOnMouseDragged(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent mouseEvent) {
					stage1.setX(mouseEvent.getScreenX() + xoffset);
					stage1.setY(mouseEvent.getScreenY() + yoffset);
				}
			});
			LapPhieuDatHangController lapPhieuDatHangController = loader.getController();
			lapPhieuDatHangController.loadData(taikhoan);
			stage1.initStyle(StageStyle.UNDECORATED);
			stage1.setTitle("Lap phieu dat hang");
			stage1.setScene(scene1);
			stage1.show();
			GiaoDienQLController.getInstance().truedisable();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		initialize1();
	}

}
