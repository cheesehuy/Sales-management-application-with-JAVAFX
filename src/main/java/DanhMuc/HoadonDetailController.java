package DanhMuc;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import net.bytebuddy.implementation.bytecode.Addition;
import javafx.stage.FileChooser;
import entities.*;

import java.util.List;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import org.hibernate.*;
import org.hibernate.cfg.*;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.*;
import org.hibernate.boot.registry.*;

import QLBH.GiaoDienNhanvienController;
import QLBH.GiaoDienQLController;
import QLBH.HibernateUtils;


public class HoadonDetailController implements Initializable {
	

	@FXML
    private Label lbMahoadon;

    @FXML
    private Label lbThoigianmua;

    @FXML
    private Label lbTonggia;

    @FXML
    private Label lbMakh;

    @FXML
    private Label lbManv;
    @FXML
    private Button back;
    
    @FXML
    private TableView<Chitiethoadon> tbChitietHoaDon;

    @FXML
    private TableColumn<Chitiethoadon,Sanpham> tenhang;

    @FXML
    private TableColumn soluong;

    @FXML
    private TableColumn dongia;

    @FXML
    private TableColumn thanhtien;
    
    @FXML
    private ImageView exit;
    
    @FXML
    private AnchorPane ap;
    
    @FXML public void exit(MouseEvent event) {
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
  
    
    public void setHoadon (Hoadon hoadon) {
    	
    	lbMahoadon.setText(String.valueOf((hoadon.getMahoadon())));
    	lbThoigianmua.setText(String.valueOf(hoadon.getThoigianmua()));

    	if(hoadon.getKhachhang() == null) {
    		lbMakh.setText("[khách lẻ]");
    	}
    	else {
    		lbMakh.setText((hoadon.getKhachhang()).getTenkh());
    	}
    	
    	if(hoadon.getNhanvien() == null) {
    		lbManv.setText("[Nhân viên đã bị xóa]");
    	}
    	else {
    		lbManv.setText((hoadon.getNhanvien()).getHovaten());
    	}
    	lbTonggia.setText(String.valueOf(hoadon.getTonggia()));
    	IntilizeChitietHoadon(hoadon);
    	getChitietHoadon(hoadon);
    }
    @FXML
    void goBack(ActionEvent e) {
    	try {
    		if( GiaoDienQLController.getInstance() ==null) {
        		Stage stage = (Stage) ap.getScene().getWindow();
        		stage.close();
        		GiaoDienNhanvienController.getInstance().falsedisable();
        		System.out.print(GiaoDienQLController.getInstance());
        		System.out.print(GiaoDienNhanvienController.getInstance());
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
    	} catch (Exception error) {
			// TODO: handle exception
    		error.printStackTrace();
		}
    }
    public ObservableList<Chitiethoadon> getChitietHoadon(Hoadon hoadon) {
    	int mahoadon = hoadon.getMahoadon();
    	ObservableList<Chitiethoadon> TableHD = FXCollections.observableArrayList();
    	 Session session = HibernateUtils.getSessionFactory().openSession();
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Chitiethoadon> query = builder.createQuery(Chitiethoadon.class);
			Root<Chitiethoadon> root = query.from(Chitiethoadon.class); // FROM
			Join<Chitiethoadon, Sanpham> SanphamJoin = root.join("sanpham", JoinType.INNER);
			Join<Chitiethoadon, Hoadon> HoadonJoin = root.join("hoadon",JoinType.INNER);
			query.where(builder.equal(HoadonJoin.get("mahoadon"), mahoadon));

			List<Chitiethoadon> cthd = session.createQuery(query).getResultList();
		 for(Chitiethoadon b : cthd) {
			 TableHD.add(b);
			 System.out.println(b);
		 }
		 return TableHD;
		
		 
    }

	public void IntilizeChitietHoadon(Hoadon hoadon) {
		tenhang.setCellFactory(tbChitietHoaDon -> new TableCell<Chitiethoadon, Sanpham>() {
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
		soluong.setCellValueFactory(new PropertyValueFactory<Chitiethoadon, Integer>("soluong"));
		thanhtien.setCellValueFactory(new PropertyValueFactory<Chitiethoadon, Double>("thanhtien"));
		dongia.setCellFactory(tbChitietHoaDon -> new TableCell<Chitiethoadon, Sanpham>() {
			@Override
			protected void updateItem(Sanpham item, boolean empty) {
				super.updateItem(item, empty);
				if (empty || item == null) {
					setText(null);
				} else {
					setText(Integer.toString(item.getGiatien()));
				}
			}
		});
		dongia.setCellValueFactory(new PropertyValueFactory<>("sanpham"));
		tbChitietHoaDon.setItems(getChitietHoadon(hoadon));
	}
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

    

}
