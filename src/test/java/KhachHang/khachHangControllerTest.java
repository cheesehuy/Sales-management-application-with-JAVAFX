package KhachHang;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;
import java.util.ResourceBundle;

import org.hibernate.Session;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import DanhMuc.danhmucController;
import Nhanvien.NhanvienController;
import QLBH.HibernateUtils;
import entities.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.stage.Stage;
@javax.annotation.processing.Generated(value = "org.junit-tools-1.1.0")
public class khachHangControllerTest {
	private khachhangController createTestSubject() {
		return new khachhangController();
	}
	
	@Test
	public void testgetKhachHang() throws Exception {
		khachhangController testSubject;
		ObservableList<KhachHang> result;
		testSubject = createTestSubject();
		result = testSubject.getKhachHang();
		assertNotNull(result);
		assertTrue("Hiển thị danh sách khách hàng thành công",result.size()>0);
	}
}