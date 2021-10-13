package DanhMuc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;
import java.util.ResourceBundle;

import org.hibernate.Session;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import Nhanvien.NhanvienController;
import QLBH.HibernateUtils;
import entities.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.stage.Stage;
@javax.annotation.processing.Generated(value = "org.junit-tools-1.1.0")
public class DanhmucontrollerTest {
	private danhmucController createTestSubject() {
		return new danhmucController();
	}
	
	@Test
	public void testgetHoadon() throws Exception {
		danhmucController testSubject;
		ObservableList<Hoadon> result;
		testSubject = createTestSubject();
		result = testSubject.getHoadon();
		assertNotNull(result);
		assertTrue("Hiển thị hóa đơn thành công",result.size()>0);
	}
	
	@Test
	public void testgetPhieuDatHang() throws Exception {
		danhmucController testSubject;
		ObservableList<Phieudathang> result;
		testSubject = createTestSubject();
		result = testSubject.getPhieudathang();
		assertNotNull(result);
		assertTrue("Hiển thị phiếu đặt hàng thành công",result.size()>0);
	}
	
	@Test
	public void testgetPhieuTraHang() throws Exception {
		danhmucController testSubject;
		ObservableList<Phieutrahang> result;
		testSubject = createTestSubject();
		result = testSubject.getPhieutrahang();
		assertNotNull(result);
		assertTrue("Hiển thị phiếu trả hàng thành công",result.size()>0);
	}
	
	@Test
	public void testgetPhieuNhapHang() throws Exception {
		danhmucController testSubject;
		ObservableList<Phieunhaphang> result;
		testSubject = createTestSubject();
		result = testSubject.getPhieunhaphang();
		assertNotNull(result);
		assertTrue("Hiển thị phiếu nhập hàng thành công",result.size()>0);
	}
}
