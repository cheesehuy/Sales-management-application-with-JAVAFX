package QLBH;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

import org.hibernate.Session;
import org.hibernate.query.Query;
import entities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class TrangChuController  implements Initializable{

	@FXML
    private LineChart<String,Number> line_chart;

    @FXML
    private CategoryAxis ngay;

    @FXML
    private NumberAxis doanhthu;
    @FXML
    private ComboBox<String> cbb_thang;
    
    @FXML
    private BarChart<String, Number> bar_chart;

    @FXML
    private CategoryAxis Sanpham;

    @FXML
    private NumberAxis Soluongbanra;
    @FXML
    private ComboBox<String> listthongke;
    
    @FXML
    private AnchorPane mainpane;
    
    @FXML
    private TableView<DT_1ngay> tbdoanhthu;

    @FXML
    private TableColumn<DT_1ngay, String> thangdoanhthu;
    
    @FXML
    private TableColumn<DT_1ngay, Double> doanhthu1ngay;
    
    @FXML
    private TableView<ThongKe_SP> tbthongkesp;

    @FXML
    private TableColumn<ThongKe_SP, String> sanpham;

    @FXML
    private TableColumn<ThongKe_SP, Integer> soluongbanra;

    
    
    
    @FXML
    void ThongKeThang(ActionEvent event) throws Exception {
    	if (cbb_thang.getValue() == "01") {
    		thongketheongay(1);
    		tbdoanhthu.setItems(chitietthongketheongay(1));
    	}
    	else if(cbb_thang.getValue() == "02") {
    		thongketheongay(2);
    		tbdoanhthu.setItems(chitietthongketheongay(2));
    	}
    	else if(cbb_thang.getValue() == "03") {
    		thongketheongay(3);
    		tbdoanhthu.setItems(chitietthongketheongay(3));
    	}
    	else if(cbb_thang.getValue() == "04") {
    		thongketheongay(4);
    		tbdoanhthu.setItems(chitietthongketheongay(4));
    	}
    	else if(cbb_thang.getValue() == "05") {
    		thongketheongay(5);
    		tbdoanhthu.setItems(chitietthongketheongay(5));
    	}
    	else if(cbb_thang.getValue() == "06") {
    		thongketheongay(6);
    		tbdoanhthu.setItems(chitietthongketheongay(6));
    	}
    	else if(cbb_thang.getValue() == "07") {
    		thongketheongay(7);
    		tbdoanhthu.setItems(chitietthongketheongay(7));
    	}
    	else if(cbb_thang.getValue() == "08") {
    		thongketheongay(8);
    		tbdoanhthu.setItems(chitietthongketheongay(8));
    	}
    	else if(cbb_thang.getValue() == "09") {
    		thongketheongay(9);
    		tbdoanhthu.setItems(chitietthongketheongay(9));
    	}
    	else if(cbb_thang.getValue() == "10") {
    		thongketheongay(10);
    		tbdoanhthu.setItems(chitietthongketheongay(10));
    	}
    	else if(cbb_thang.getValue() == "11") {
    		thongketheongay(11);
    		tbdoanhthu.setItems(chitietthongketheongay(11));
    	}
    	else if(cbb_thang.getValue() == "12") {
    		thongketheongay(12);
    		tbdoanhthu.setItems(chitietthongketheongay(12));
    	}
    }
    public ObservableList<DT_1ngay> chitietthongketheongay(int thang) throws IOException{
    	ObservableList<DT_1ngay> tbdoanhthu1 = FXCollections.observableArrayList();
    	Session session = HibernateUtils.getSessionFactory().openSession();
    	String hql = "SELECT SUM(tonggia),thoigianmua as date FROM Hoadon WHERE month(thoigianmua)=:thoigianmua GROUP BY DATE(thoigianmua) ORDER BY DATE(thoigianmua)";
    	Query query = session.createQuery(hql);
    	query.setParameter("thoigianmua", thang);
    	List<Object[]> hd = query.list(); 
    	for(Object[] doanhthu : hd) {
    		Double tong = (Double) doanhthu[0];
    		LocalDateTime ngay = (LocalDateTime) doanhthu[1];
    		DT_1ngay dt_1ngay = new DT_1ngay(ngay,tong);
    		tbdoanhthu1.add(dt_1ngay);
    	}
    	return tbdoanhthu1;
    }
    
    public ObservableList<ThongKe_SP> getThongKe_SPs(int ngay,int thang){
    	ObservableList<ThongKe_SP> tbdoanhthu1 = FXCollections.observableArrayList();
    	Session session = HibernateUtils.getSessionFactory().openSession();
    	String hql = "SELECT SP.tensanpham,SUM(CT.soluong) FROM Chitiethoadon CT INNER JOIN CT.sanpham SP INNER JOIN CT.hoadon H WHERE dayofmonth(H.thoigianmua)=:date and month(H.thoigianmua)=:month GROUP BY SP.tensanpham ORDER BY CT.soluong";
    	Query query = session.createQuery(hql);
		query.setParameter("date", ngay);
		query.setParameter("month", thang);
		List<Object[]> hd = query.list(); 
		for(Object[] TKSanpham : hd) {
    		String tensanpham = (String) TKSanpham[0];
    		Long soluong = (Long) TKSanpham[1];
    		ThongKe_SP tkesanpham = new ThongKe_SP(tensanpham,soluong);
    		tbdoanhthu1.add(tkesanpham);
    	}
		return tbdoanhthu1;
    }
    
    public void thongketheongay(int thang) {
    	Session session = HibernateUtils.getSessionFactory().openSession();
    	XYChart.Series series = new XYChart.Series();
    	try {
    	
    		String hql = "SELECT SUM(tonggia),thoigianmua as date FROM Hoadon WHERE month(thoigianmua)=:thoigianmua GROUP BY DATE(thoigianmua) ORDER BY DATE(thoigianmua)";
    		Query query = session.createQuery(hql);
    		query.setParameter("thoigianmua", thang);
    		List<Object[]> hd = query.list(); 
    		line_chart.getData().clear();
    		 for(Object[] hoadon : hd) {
    			 ngay.setLabel("Ngày");
    			 doanhthu.setLabel("Doanh thu bán được trong một ngày");
    			 Double tonggia = (Double) hoadon[0];
    			 String date = String.valueOf((LocalDateTime) hoadon[1]);
    			 String d = String.valueOf(date.substring(6, 10));
    			 series.getData().add(new XYChart.Data(d,tonggia));

    		 }
    		 line_chart.setTitle("Thống kê theo ngày của tháng "+thang);
    		 line_chart.getData().addAll(series);
    		 line_chart.setAnimated(false);
    	}catch (Exception e) {
			// TODO: handle exception
    		System.out.println(e);
		}
    		
    		
    }
    
    
    public void ThongKeSanPham(int ngay,int thang) {
    	Session session = HibernateUtils.getSessionFactory().openSession();
    	try {
    		XYChart.Series series = new XYChart.Series();
    		String hql = "SELECT SP.tensanpham,SUM(CT.soluong) FROM Chitiethoadon CT INNER JOIN CT.sanpham SP INNER JOIN CT.hoadon H WHERE dayofmonth(H.thoigianmua)=:date and month(H.thoigianmua)=:month GROUP BY SP.tensanpham ORDER BY CT.soluong";
    		Query query = session.createQuery(hql);
    		query.setParameter("date", ngay);
    		query.setParameter("month", thang);
    		List<Object[]> hd = query.list(); 
    		bar_chart.getData().clear();
    		 bar_chart.setTitle("Thống kê sản phẩm bán được trong một ngày");
    		  for(Object[] thongke : hd) {
    			  String tensanpham = (String) thongke[0];
    			  long soluong = (long) thongke[1];
    			  series.getData().add(new XYChart.Data(tensanpham,soluong));
    		  }
    		  bar_chart.getData().addAll(series);
    	}catch (Exception e) {
			// TODO: handle exception
    		System.out.println(e);
		}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources){
		// TODO Auto-generated method stub
		ObservableList<String> combobox = FXCollections.observableArrayList("01","02","03","04","05","06","07","08","09","10","11","12");
		cbb_thang.setItems(combobox);
		//thongketheongay();
		Sanpham.setLabel("Sản phẩm bán ra");
		Soluongbanra.setLabel("Số lượng bán ra ");
		bar_chart.setTitle("Thống kê số lượng sản phẩm bán ra trong một ngày");
		LocalDateTime t1 = LocalDateTime.now();
		String getDatenow = String.valueOf(t1);
		int day = Integer.parseInt(getDatenow.substring(8, 10));
		int thang = Integer.parseInt(getDatenow.substring(5, 7));
		String thang1 = getDatenow.substring(5, 7);
		ThongKeSanPham(day, thang);
		soluongbanra.setCellValueFactory(new PropertyValueFactory<ThongKe_SP, Integer>("soluong"));
		sanpham.setCellValueFactory(new PropertyValueFactory<ThongKe_SP, String>("tensanpham"));
		thangdoanhthu.setCellValueFactory(new PropertyValueFactory<DT_1ngay, String>("thang"));
		doanhthu1ngay.setCellValueFactory(new PropertyValueFactory<DT_1ngay, Double>("doanhthu"));
		cbb_thang.getSelectionModel().select(thang1);
		thongketheongay(thang);
		
		try {
			tbdoanhthu.setItems(chitietthongketheongay(thang));
			tbthongkesp.setItems(getThongKe_SPs(day, thang));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
			
	}
    
    

}