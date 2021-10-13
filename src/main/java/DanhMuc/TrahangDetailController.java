package DanhMuc;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import entities.*;
import QLBH.GiaoDienQLController;
import QLBH.HibernateUtils;

public class TrahangDetailController implements Initializable{

    @FXML
    private Label lbMaphieutra;

    @FXML
    private Label lbThoigiantra;

    @FXML
    private Label lbTongtien;

    @FXML
    private Label lbMancc;

    @FXML
    private Label lbManv;

    @FXML
    private Button backPT;

    @FXML
    private TableView <Chitietphieutra>tbChitietPhieuTra;

    @FXML
    private TableColumn<Chitietphieutra, Sanpham> tenhang;

    @FXML
    private TableColumn soluong;

    @FXML
    private TableColumn dongia;

    @FXML
    private TableColumn tongtien;
    
    @FXML
    private ImageView exit;
    
    @FXML public void exit(MouseEvent event) {
    	Stage stage = (Stage) exit.getScene().getWindow();
		stage.close();
		GiaoDienQLController.getInstance().falsedisable();
    }	

    @FXML
    void backPT(ActionEvent event) throws IOException {
    	Stage stage = (Stage) backPT.getScene().getWindow();
        stage.close();
        GiaoDienQLController.getInstance().falsedisable();
    }
    
    public void setPhieutrahang(Phieutrahang phieutrahang) {
    	lbMaphieutra.setText(String.valueOf(phieutrahang.getMaphieutra()));
    	lbThoigiantra.setText(String.valueOf(phieutrahang.getThoigian()));
    	if(phieutrahang.getNhacungcap() == null) {
    		lbMancc.setText("[Nhà cung cấp đã bị xóa]");
    	}else {
    		lbMancc.setText(phieutrahang.getNhacungcap().getTenncc());
    	}
    	if(phieutrahang.getNhanvien() == null) {
    		lbManv.setText("[Nhân viên đã bị xóa]");
    	}else {
    		lbManv.setText(phieutrahang.getNhanvien().getHovaten());
    	}
    	
    	lbTongtien.setText(String.valueOf(phieutrahang.getTongtien()));
    	IntitlizeChitietphieutra(phieutrahang);
    	getChitietphieutra(phieutrahang);
    }
    
    public ObservableList<Chitietphieutra> getChitietphieutra(Phieutrahang phieutrahang) {
    	int maphieutra = phieutrahang.getMaphieutra();
    	ObservableList<Chitietphieutra> tablePhieuTraHang = FXCollections.observableArrayList();
    	 Session session = HibernateUtils.getSessionFactory().openSession();
			
			CriteriaBuilder builder = session.getCriteriaBuilder();
			//SELECT C.soluong,SP.tensanpham,SP.giatien FROM chitietdathang C,sanpham SP WHERE C.masanpham = SP.masanpham
			CriteriaQuery<Chitietphieutra> query = builder.createQuery(Chitietphieutra.class);
			Root<Chitietphieutra> root = query.from(Chitietphieutra.class); // FROM
			Join<Chitietphieutra, Sanpham> SanphamJoin = root.join("sanpham", JoinType.INNER);
			Join<Chitietphieutra, Phieutrahang> PhieutraJoin = root.join("phieutrahang",JoinType.INNER);
			query.where(builder.equal(PhieutraJoin.get("maphieutra"), maphieutra));
			List<Chitietphieutra> ctpt = session.createQuery(query).getResultList();
		 for(Chitietphieutra b : ctpt) {
			 tablePhieuTraHang.add(b);
			 System.out.println(b);
		 }
		 return tablePhieuTraHang;
		
		 
    }

	private void IntitlizeChitietphieutra(Phieutrahang phieutrahang) {
		tenhang.setCellFactory(tbChitietPhieuTra -> new TableCell<Chitietphieutra, Sanpham>() {
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
		soluong.setCellValueFactory(new PropertyValueFactory<Chitietphieutra, Integer>("soluong"));
		tongtien.setCellValueFactory(new PropertyValueFactory<Chitietphieutra, Double>("thanhtien"));
		dongia.setCellValueFactory(new PropertyValueFactory<Chitietphieutra, Integer>("dongia"));
		tbChitietPhieuTra.setItems(getChitietphieutra(phieutrahang));
	}
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

}

