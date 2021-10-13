package Nhanvien;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.apache.derby.vti.IFastPath;
import org.hibernate.Session;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import BanHang.BanHangController;
import QLBH.HibernateUtils;
import entities.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart.Data;
import javafx.stage.Stage;
import junit.framework.Assert;
import net.sf.jasperreports.data.ds.DataSourceDataAdapterService;

@javax.annotation.processing.Generated(value = "org.junit-tools-1.1.0")
public class NhanvienControllerTest extends Main{
	private NhanvienController createTestSubject() {
		return new NhanvienController();
	}
	
	@Test
	public void testGetNhanvien() throws Exception {
		NhanvienController testSubject;
		ObservableList<Nhanvien> result;
		testSubject = createTestSubject();
		result = testSubject.getNhanvien();
		assertNotNull(result);
		assertTrue("Hiển thị nhân viên thất bại",result.size()>0);
	}
	
	@Test
	public void KiemTraTenNV() {
		NhanvienController testSubject;
		testSubject = createTestSubject();
		String tennv ="Hoàng Tân";
		assertTrue("Kiểm tra nhân viên thất bại",testSubject.KiemTraTenNV(tennv));
	}
	@Test
	public void KiemSDTNV() {
		NhanvienController testSubject;
		testSubject = createTestSubject();
		String sdtnv ="0981232132";
		assertTrue("Kiểm tra số điện thoại thất bại",testSubject.KiemTraSDT(sdtnv));
	}
	@Test
	public void KiemTraCMND() {
		NhanvienController testSubject;
		testSubject = createTestSubject();
		String cmnd ="09123";
		assertTrue("Kiểm tra CMND Nhân viên thất bại",testSubject.KiemTraCMND(cmnd));
	}
	@Test
	public void KiemTraDiachi() {
		NhanvienController testSubject;
		testSubject = createTestSubject();
		String diachi ="109/12 Huỳnh Thị Hai";
		assertTrue("Kiểm tra địa chỉ thất bại",testSubject.KiemTraTenNV(diachi));
	}


	
	@Test
	public void testLuucapnhat() throws Exception {
		Session session = HibernateUtils.getSessionFactory().openSession();
		NhanvienController testSubject;
		testSubject = createTestSubject();
		Nhanvien nhanvien = new Nhanvien();
		nhanvien = session.get(Nhanvien.class,100071);
		session.beginTransaction();
		nhanvien.setHovaten("TNYN");
		nhanvien.setChucvu("Nhân viên");
		nhanvien.setGioitinh("Nữ");
		nhanvien.setDiachi("123");
		LocalDate dataDate = LocalDate.now();
		nhanvien.setNgaysinh(dataDate);
		nhanvien.setSdt("0123");
		nhanvien.setCmnd(123);
		nhanvien.setNgayvaolam(dataDate);
		session.save(nhanvien);
		session.getTransaction().commit();
		assertTrue("Cập nhật thất bại",session.isConnected() && session !=null);		
	}

	
/*	@Test
	@DisplayName("Tìm kiếm nhân viên với thông tin sai")
	public void TimKiemNhanvien() {
		Session session = HibernateUtils.getSessionFactory().openSession();
		NhanvienController timkiemnhanvien;
		timkiemnhanvien = createTestSubject();
		Nhanvien nhanvien = new Nhanvien();
		ArrayList<String> DSNVtimkiem = new ArrayList<>();
		ObservableList<Nhanvien> DSNV = timkiemnhanvien.getNhanvien();
		for(Nhanvien nv : DSNV) {
			DSNVtimkiem.add(nv.getHovaten());
		}	
		String tennhanvien ="zzz";
		ArrayList<String> DSNVTimkiem1 = new ArrayList<>();
		DSNVTimkiem1.add(tennhanvien);
		assertEquals(DSNVTimkiem1, DSNVtimkiem,"Hiển thị dữ liệu sai");
				
	}*/

}